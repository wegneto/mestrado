package org.mobicents.servlet.sip.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.sip.Address;
import javax.servlet.sip.B2buaHelper;
import javax.servlet.sip.SipFactory;
import javax.servlet.sip.SipServlet;
import javax.servlet.sip.SipServletRequest;
import javax.servlet.sip.SipServletResponse;
import javax.servlet.sip.SipSession;
import javax.servlet.sip.SipURI;

public class Redirect extends SipServlet {

	private static final long serialVersionUID = 1L;

	static private Map<String, String> Binding;

	private static Map<String, String> salasAtivas;
	
	private static Logger logger = Logger.getLogger(Redirect.class.getName());

	public Redirect() {
		super();
		Binding = new HashMap<String, String>();
		salasAtivas = new HashMap<String, String>();
	}

	/**
	 * Acts as a registrar and location service for REGISTER messages
	 * 
	 * @param request
	 *            The SIP message received by the AS
	 */
	protected void doRegister(SipServletRequest request) throws ServletException, IOException {
		String aor = getSIPuri(request.getHeader("To"));
		String contact = getSIPuriPort(request.getHeader("Contact"));
		SipServletResponse response = null;

		if (aor.contains("@acme.pt")) {
			int expires = 0;
			String expStr = request.getHeader("Expires");
			if (expStr != null) {
				expires = Integer.parseInt(expStr);
			} else {
				expires = getSIPExpires(request.getHeader("Contact"));
			}

			if (expires > 0) {
				logger.info("REGISTER: Registando AoR: " + aor);
				Binding.put(aor, contact);
			} else {
				logger.info("REGISTER: Deregistando AoR: " + aor);
				Binding.remove(aor);
			}
			response = request.createResponse(200);
		} else {
			logger.info("REGISTER: AoR " + aor + " sem permissão para se registar ao servidor.");
			response = request.createResponse(403);
		}

		response.send();
	}

	protected void doMessage(SipServletRequest request) throws ServletException, IOException {
		String to = getSIPuri(request.getHeader("To"));
		String from = getSIPuri(request.getHeader("From"));

		if (from.contains("@acme.pt") && Binding.containsKey(from)) {

			if (to.equals("sip:conference@acme.pt")) {
				logger.info("MESSAGE: Mensagem enviada para o AoR sip:conference@acme.pt");

				String message = request.getContent().toString();

				if (message.matches("^ativar [\\w]*")) {
					String sala = message.split("\\s+")[1];
					logger.info("MESSAGE: Ativando sala " + sala + " para o utilizador " + from);
					salasAtivas.put(from, sala);
				} else if (message.matches("^desativar")) {
					logger.info("MESSAGE: Desativando sala para o utilizador " + from);
					salasAtivas.remove(from);
				} else {
					request.createResponse(400).send();
				}

				request.createResponse(200).send();
			}

		} else {
			logger.info("MESSAGE: Usuário " + from + " não tem permissão para enviar mensagens.");
			request.createResponse(SipServletResponse.SC_NOT_FOUND).send();
		}
	}

	protected void doInfo(SipServletRequest request) throws ServletException, IOException {
		String messageContent = new String((byte[]) request.getContent());
		int signal = Integer.valueOf(messageContent.substring("Signal=".length(), "Signal=".length() + 1).trim());

		switch (signal) {
		case 1:
			SipFactory sipFactory = (SipFactory) getServletContext().getAttribute(SIP_FACTORY);

			Address addressTo = sipFactory.createAddress("sip:bob@acme.pt:5062");
			SipServletRequest newRequest = sipFactory.createRequest(request.getApplicationSession(), "INVITE",
					request.getFrom(), addressTo);

			if (request.getContent() != null) {
				newRequest.setContent(request.getContent(), request.getContentType());
			}
			newRequest.getSession().setAttribute("participant", Boolean.TRUE);
			newRequest.getSession().setAttribute("OriginalRequest", request);
			newRequest.send();

			request.createResponse(SipServletResponse.SC_OK).send();

			break;

		default:

			request.createResponse(SipServletResponse.SC_NOT_FOUND).send();
			break;
		}
	}

	/**
	 * 
	 * @param request
	 *            The SIP message received by the AS
	 */
	protected void doInvite(SipServletRequest request) throws ServletException, IOException {
		SipServletResponse response = null;
		String from = getSIPuri(request.getHeader("From")); // Get the To AoR

		if (Binding.containsKey(from)) {

			if (salasAtivas.containsKey(from)) {
				// Usuário tem sala ativa
				SipFactory sipFactory = (SipFactory) getServletContext().getAttribute(SIP_FACTORY);
				String nomeSala = salasAtivas.get(from);

				Map<String, List<String>> headers = new HashMap<String, List<String>>();
				List<String> toHeaderSet = new ArrayList<String>();
				toHeaderSet.add("sip:" + nomeSala + "@acme.pt");
				headers.put("To", toHeaderSet);

				B2buaHelper helper = request.getB2buaHelper();
				SipServletRequest forkedRequest = helper.createRequest(request, true, headers);
				SipURI sipUri = (SipURI) sipFactory.createURI("sip:" + nomeSala + "@acme.pt:5070");

				forkedRequest.setRequestURI(sipUri);
				forkedRequest.getSession().setAttribute("originalRequest", request);
				forkedRequest.getSession().setAttribute("owner", Boolean.TRUE);
				forkedRequest.send();
			} else {
				// Usuário não tem sala ativa = 401
				response = request.createResponse(SipServletResponse.SC_UNAUTHORIZED);
				response.send();
			}
		} else {
			// Usuário não registado = 404
			response = request.createResponse(SipServletResponse.SC_NOT_FOUND);
			response.send();
		}
	}

	/**
	 * @param response
	 *            The SIP message received by the AS
	 */
	protected void doResponse(SipServletResponse response) throws ServletException, IOException {
		super.doResponse(response);
	}

	protected void doBye(SipServletRequest request) throws ServletException, IOException {
		// we send the OK directly to the first call leg
		SipServletResponse sipServletResponse = request.createResponse(SipServletResponse.SC_OK);
		sipServletResponse.send();

		// we forward the BYE
		SipSession session = request.getSession();
		B2buaHelper helper = request.getB2buaHelper();
		SipSession linkedSession = helper.getLinkedSession(session);
		SipServletRequest forkedRequest = linkedSession.createRequest("BYE");
		forkedRequest.send();

		if (session != null && session.isValid()) {
			session.invalidate();
		}

		return;
	}

	protected void doSuccessResponse(SipServletResponse response) throws ServletException, IOException {
		
		boolean participant = response.getSession().getAttribute("participant") != null;
		boolean owner = response.getSession().getAttribute("owner") != null;
		boolean sbrubles = response.getSession().getAttribute("sbrubles") != null;
		
		String to = getSIPuri(response.getHeader("To"));

		//if (to.equals("sip:bob@acme.pt")) {
		if (participant) {
			logger.info("***************** PARTICIPANTE");
			SipServletRequest ackRequest = response.createAck();
			ackRequest.setContent(response.getContent(), response.getContentType());
			ackRequest.send();
			
			SipFactory sipFactory = (SipFactory) getServletContext().getAttribute(SIP_FACTORY);

			Address addressTo = sipFactory.createAddress("sip:sala@acme.pt:5070");
			SipServletRequest newRequest = sipFactory.createRequest(response.getApplicationSession(), "INVITE",
					response.getTo(), addressTo);

			if (response.getContent() != null) {
				newRequest.setContent(response.getContent(), response.getContentType());
			}
			newRequest.getSession().setAttribute("sbrubles", Boolean.TRUE);
			newRequest.getSession().setAttribute("originalRequest", response.getSession().getAttribute("OriginalRequest"));
			newRequest.send();

		//} else if (response.getMethod().indexOf("INVITE") != -1) {
		} else if (owner) {
			logger.info("***************** OWNER");
			// if this is a response to an INVITE we ack it and forward the OK
			SipServletRequest ackRequest = response.createAck();
			ackRequest.send();

			// create and sends OK for the first call leg
			SipServletRequest originalRequest = (SipServletRequest) response.getSession()
					.getAttribute("originalRequest");
			SipServletResponse responseToOriginalRequest = originalRequest.createResponse(response.getStatus());

			responseToOriginalRequest.setContentLength(response.getContentLength());
			if (response.getContent() != null && response.getContentType() != null) {
				responseToOriginalRequest.setContent(response.getContent(), response.getContentType());
			}
			responseToOriginalRequest.send();
		} else if (sbrubles) {
			logger.info("***************** SBRUBLES");
			SipServletRequest ackRequest = response.createAck();
			ackRequest.setContent(response.getContent(), response.getContentType());
			ackRequest.send();
		}
	}

	/**
	 * Auxiliary function for extracting SPI URIs
	 * 
	 * @param uri
	 *            A URI with optional extra attributes
	 * @return SIP URI
	 */
	protected String getSIPuri(String uri) {
		String f = uri.substring(uri.indexOf("<") + 1, uri.indexOf(">"));
		int indexCollon = f.indexOf(":", f.indexOf("@"));
		if (indexCollon != -1) {
			f = f.substring(0, indexCollon);
		}
		return f;
	}

	/**
	 * Auxiliary function for extracting SPI URIs
	 * 
	 * @param uri
	 *            A URI with optional extra attributes
	 * @return SIP URI and port
	 */
	protected String getSIPuriPort(String uri) {
		String f = uri.substring(uri.indexOf("<") + 1, uri.indexOf(">"));
		return f;
	}

	protected int getSIPExpires(String uri) {
		String expires = uri.substring(uri.indexOf("expires") + 8);
		return Integer.valueOf(expires);
	}

}
