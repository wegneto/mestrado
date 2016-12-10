package pt.iscte.igrs.sip.state.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.sip.Address;
import javax.servlet.sip.B2buaHelper;
import javax.servlet.sip.SipFactory;
import javax.servlet.sip.SipServlet;
import javax.servlet.sip.SipServletRequest;
import javax.servlet.sip.SipServletResponse;
import javax.servlet.sip.SipURI;

import pt.iscte.igrs.sip.servlet.RedirectContext;
import pt.iscte.igrs.sip.state.State;

public class Active extends State {

	private static Logger logger = Logger.getLogger(Active.class.getName());

	@Override
	public int doInvite(SipServletRequest request, ServletContext servletContext) throws ServletException, IOException {
		logger.info("Requisição para iniciar conferência");

		int responseCode = 0;
		String from = request.getFrom().getURI().toString();

		if (RedirectContext.registar.containsKey(from)) {

			if (RedirectContext.activeRooms.containsKey(from)) {
				// Usuário tem sala ativa
				SipFactory sipFactory = (SipFactory) servletContext.getAttribute(SipServlet.SIP_FACTORY);
				String nomeSala = RedirectContext.activeRooms.get(from);

				Map<String, List<String>> headers = new HashMap<String, List<String>>();
				List<String> toHeaderSet = new ArrayList<String>();
				toHeaderSet.add("sip:" + nomeSala + "@acme.pt");
				headers.put("To", toHeaderSet);

				B2buaHelper helper = request.getB2buaHelper();
				SipServletRequest forkedRequest = helper.createRequest(request, true, headers);
				SipURI sipUri = (SipURI) sipFactory.createURI("sip:" + nomeSala + "@acme.pt:5070");

				forkedRequest.setRequestURI(sipUri);
				forkedRequest.getSession().setAttribute("originalRequest", request);
				forkedRequest.send();
			} else {
				// Usuário não tem sala ativa = 401
				logger.info("Não existe sala ativa para este usuário");
				responseCode = SipServletResponse.SC_UNAUTHORIZED;
			}
		} else {
			// Usuário não registado = 404
			logger.info("Usuário não registado");
			responseCode = SipServletResponse.SC_NOT_FOUND;
		}

		return responseCode;
	}

	public void doSuccessResponse(SipServletResponse response) throws ServletException, IOException {
		String from = response.getFrom().getURI().toString();
		
		if (response.getMethod().indexOf("INVITE") != -1) {
			// if this is a response to an INVITE we ack it and forward the OK
			SipServletRequest ackRequest = response.createAck();
			ackRequest.send();
			
			RedirectContext.getStates().put(from, new InConference());

			// create and sends OK for the first call leg
			SipServletRequest originalRequest = (SipServletRequest) response.getSession()
					.getAttribute("originalRequest");
			SipServletResponse responseToOriginalRequest = originalRequest.createResponse(response.getStatus());

			responseToOriginalRequest.setContentLength(response.getContentLength());
			if (response.getContent() != null && response.getContentType() != null) {
				responseToOriginalRequest.setContent(response.getContent(), response.getContentType());
			}
			responseToOriginalRequest.send();
		}
	}

}
