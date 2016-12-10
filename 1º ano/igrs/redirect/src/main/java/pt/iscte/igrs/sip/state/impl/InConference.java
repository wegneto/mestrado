package pt.iscte.igrs.sip.state.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.sip.B2buaHelper;
import javax.servlet.sip.SipServletRequest;
import javax.servlet.sip.SipServletResponse;
import javax.servlet.sip.SipSession;

import pt.iscte.igrs.sip.servlet.RedirectContext;
import pt.iscte.igrs.sip.state.State;

public class InConference extends State {
	
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
		RedirectContext.getStates().put(from, new Active());
	}

}
