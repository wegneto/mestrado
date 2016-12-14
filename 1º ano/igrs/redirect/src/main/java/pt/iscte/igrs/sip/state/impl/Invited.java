package pt.iscte.igrs.sip.state.impl;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.sip.Address;
import javax.servlet.sip.B2buaHelper;
import javax.servlet.sip.SipFactory;
import javax.servlet.sip.SipServlet;
import javax.servlet.sip.SipServletRequest;
import javax.servlet.sip.SipServletResponse;

import pt.iscte.igrs.sip.model.ConferenceRoom;
import pt.iscte.igrs.sip.model.User;
import pt.iscte.igrs.sip.servlet.RedirectContext;
import pt.iscte.igrs.sip.state.State;

public class Invited extends State {
	
	private static Logger logger = Logger.getLogger(Invited.class.getName());
	
	@Override
	public void doSuccessResponse(SipServletResponse response, ServletContext servletContext) throws ServletException, IOException {
		User user = (User) response.getRequest().getAttribute("stateOwner");
		RedirectContext.states.put(user.getAddressOfRecord().toString(), new Answered());
		
		SipServletRequest ackRequest = response.createAck();
		ackRequest.setContent(response.getContent(), response.getContentType());
		ackRequest.send();
		
		SipFactory sipFactory = (SipFactory) servletContext.getAttribute(SipServlet.SIP_FACTORY);
		
		String from = response.getFrom().getURI().toString();
		ConferenceRoom confRoom = RedirectContext.activeRooms.get(from);
		confRoom.getUsers().add(user);

		Address addressTo = sipFactory.createAddress("sip:" + confRoom.getName() + "@acme.pt:5070");
		SipServletRequest newRequest = sipFactory.createRequest(response.getApplicationSession(), "INVITE",
				response.getTo(), addressTo);

		if (response.getContent() != null) {
			newRequest.setContent(response.getContent(), response.getContentType());
		}
		newRequest.setAttribute("replyTo", from);
		newRequest.setAttribute("stateOwner", user);
		
		RedirectContext.sessions.put(user.getAddressOfRecord().toString(), response.getRequest().getSession());
		
		B2buaHelper helper = newRequest.getB2buaHelper();
		helper.linkSipSessions(response.getRequest().getSession(), newRequest.getSession());
		
		newRequest.send();
	}

}
