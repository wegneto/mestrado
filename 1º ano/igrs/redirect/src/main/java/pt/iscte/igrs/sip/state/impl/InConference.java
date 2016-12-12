package pt.iscte.igrs.sip.state.impl;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.sip.Address;
import javax.servlet.sip.B2buaHelper;
import javax.servlet.sip.SipFactory;
import javax.servlet.sip.SipServlet;
import javax.servlet.sip.SipServletRequest;
import javax.servlet.sip.SipServletResponse;
import javax.servlet.sip.SipSession;

import pt.iscte.igrs.sip.model.User;
import pt.iscte.igrs.sip.servlet.RedirectContext;
import pt.iscte.igrs.sip.state.State;

public class InConference extends State {

	private static Logger logger = Logger.getLogger(InConference.class.getName());

	public void doInfo(SipServletRequest request, ServletContext servletContext) throws ServletException, IOException {
		String messageContent = new String((byte[]) request.getContent());
		int signal = Integer.valueOf(messageContent.substring("Signal=".length(), "Signal=".length() + 1).trim());
		String contact = RedirectContext.contactList.get(signal);
		
		//TODO Verify if the user is registered
		User user = RedirectContext.registar.get(contact);

		SipFactory sipFactory = (SipFactory) servletContext.getAttribute(SipServlet.SIP_FACTORY);

		Address addressFrom = sipFactory.createAddress(request.getFrom().getURI());
		Address addressTo = sipFactory.createAddress(user.getContact());
		SipServletRequest inviteRequest = sipFactory.createRequest(request.getApplicationSession(), "INVITE",
				addressFrom, addressTo);

		if (request.getContent() != null) {
			inviteRequest.setContent(request.getContent(), request.getContentType());
		}
		inviteRequest.setAttribute("stateOwner", user);
		inviteRequest.send();
		
		RedirectContext.states.put(request.getFrom().getURI().toString(), new WaitingAnswer());
		RedirectContext.states.put(user.getAddressOfRecord().toString(), new Invited());

		request.createResponse(SipServletResponse.SC_OK).send();
	}

	public void doBye(SipServletRequest request) throws ServletException, IOException {
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
		
		String from = request.getFrom().getURI().toString();
		RedirectContext.states.put(from, new Active());
	}

}
