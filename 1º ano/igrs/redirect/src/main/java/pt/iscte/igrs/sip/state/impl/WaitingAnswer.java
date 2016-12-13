package pt.iscte.igrs.sip.state.impl;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.sip.SipServletResponse;

import pt.iscte.igrs.sip.servlet.RedirectContext;
import pt.iscte.igrs.sip.state.State;

public class WaitingAnswer extends State {

	private static Logger logger = Logger.getLogger(WaitingAnswer.class.getName());

	@Override
	public void doSuccessResponse(SipServletResponse response, ServletContext servletContext)
			throws ServletException, IOException {
		RedirectContext.states.put(response.getRequest().getTo().getURI().toString(), new InConference());
	}

}
