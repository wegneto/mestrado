package pt.iscte.igrs.sip.state.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.sip.B2buaHelper;
import javax.servlet.sip.SipFactory;
import javax.servlet.sip.SipServlet;
import javax.servlet.sip.SipServletRequest;
import javax.servlet.sip.SipServletResponse;
import javax.servlet.sip.SipSession;
import javax.servlet.sip.SipURI;

import pt.iscte.igrs.sip.servlet.RedirectContext;
import pt.iscte.igrs.sip.state.State;

public class Active extends State {

	private static Logger logger = Logger.getLogger(Active.class.getName());

	@Override
	public void doInvite(SipServletRequest request, ServletContext servletContext)
			throws ServletException, IOException {
		logger.info("Requisição para iniciar conferência");

		String from = request.getFrom().getURI().toString();

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
		forkedRequest.send();
	}

	public void doSuccessResponse(SipServletResponse response, ServletContext servletContext)
			throws ServletException, IOException {
		SipServletRequest ackRequest = response.createAck();
		ackRequest.send();

		String from = response.getFrom().getURI().toString();
		RedirectContext.states.put(from, new InConference());

		// create and sends OK for the first call leg
		SipSession session = response.getRequest().getSession();
		B2buaHelper helper = response.getRequest().getB2buaHelper();
		SipSession linkedSession = helper.getLinkedSession(session);
		
		SipServletResponse responseToOriginalRequest = helper.createResponseToOriginalRequest(linkedSession, response.getStatus(), "OK");
		if (response.getContent() != null && response.getContentType() != null) {
			responseToOriginalRequest.setContent(response.getContent(), response.getContentType());
		}
		responseToOriginalRequest.send();
	}

}
