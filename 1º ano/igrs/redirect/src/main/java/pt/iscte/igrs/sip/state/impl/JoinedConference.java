package pt.iscte.igrs.sip.state.impl;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.sip.SipServletRequest;
import javax.servlet.sip.SipServletResponse;

import pt.iscte.igrs.sip.state.State;

public class JoinedConference extends State {

	private static Logger logger = Logger.getLogger(JoinedConference.class.getName());

	// TODO
	public void doBye(SipServletRequest request) throws ServletException, IOException {
		SipServletResponse sipServletResponse = request.createResponse(SipServletResponse.SC_OK);
		sipServletResponse.send();
	}
}
