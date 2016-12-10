package pt.iscte.igrs.sip.state.impl;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.sip.Address;
import javax.servlet.sip.SipFactory;
import javax.servlet.sip.SipServlet;
import javax.servlet.sip.SipServletRequest;
import javax.servlet.sip.SipServletResponse;

import pt.iscte.igrs.sip.state.State;

public class ParticipantInvited extends State {

	@Override
	public void doSuccessResponse(SipServletResponse response, ServletContext servletContext) throws ServletException, IOException {
		SipServletRequest ackRequest = response.createAck();
		ackRequest.setContent(response.getContent(), response.getContentType());
		ackRequest.send();
		
		SipFactory sipFactory = (SipFactory) servletContext.getAttribute(SipServlet.SIP_FACTORY);

		Address addressTo = sipFactory.createAddress("sip:sala@acme.pt:5070");
		SipServletRequest newRequest = sipFactory.createRequest(response.getApplicationSession(), "INVITE",
				response.getTo(), addressTo);

		if (response.getContent() != null) {
			newRequest.setContent(response.getContent(), response.getContentType());
		}
		newRequest.getSession().setAttribute("ownerRequest", response.getSession().getAttribute("ownerRequest"));
		newRequest.send();
	}

}
