package pt.iscte.igrs.sip.state.impl;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.sip.Address;
import javax.servlet.sip.SipFactory;
import javax.servlet.sip.SipServlet;
import javax.servlet.sip.SipServletRequest;
import javax.servlet.sip.SipServletResponse;
import javax.servlet.sip.SipSession;

import pt.iscte.igrs.sip.model.User;
import pt.iscte.igrs.sip.servlet.RedirectContext;
import pt.iscte.igrs.sip.state.State;

public class Answered extends State {

	private static Logger logger = Logger.getLogger(Answered.class.getName());

	public void doSuccessResponse(SipServletResponse response, ServletContext servletContext)
			throws ServletException, IOException {
		SipServletRequest ackRequest = response.createAck();
		ackRequest.setContent(response.getContent(), response.getContentType());
		ackRequest.send();

		User user = (User) response.getRequest().getAttribute("stateOwner");
		RedirectContext.states.put(user.getAddressOfRecord().toString(), new JoinedConference());

		// TODO send INFO(0) to the conference owner
		logger.info("TODO: SEND INFO(0) TO: " + response.getRequest().getAttribute("replyTo"));

		User replyTo = RedirectContext.registar.get(response.getRequest().getAttribute("replyTo"));
		SipSession session = RedirectContext.sessions.get(replyTo.getAddressOfRecord().toString());
		SipServletRequest infoRequest = session.createRequest("INFO");

		//infoRequest.setContent(RedirectContext.content, RedirectContext.contentType);

		infoRequest.setAttribute("stateOwner", replyTo);
		infoRequest.send();

	}

}
