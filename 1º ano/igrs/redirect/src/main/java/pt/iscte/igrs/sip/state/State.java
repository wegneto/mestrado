package pt.iscte.igrs.sip.state;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.sip.SipServletRequest;
import javax.servlet.sip.SipServletResponse;

public abstract class State {
	
	public int doRegister(SipServletRequest request) throws ServletException, IOException  {
		return SipServletResponse.SC_BAD_REQUEST;
	}

}
