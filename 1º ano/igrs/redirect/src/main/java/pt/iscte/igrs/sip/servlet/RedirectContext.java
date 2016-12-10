package pt.iscte.igrs.sip.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.sip.SipServlet;
import javax.servlet.sip.SipServletRequest;

import pt.iscte.igrs.sip.model.User;
import pt.iscte.igrs.sip.state.State;
import pt.iscte.igrs.sip.state.impl.NonRegistered;

public class RedirectContext extends SipServlet {

	private State state;

	public static Map<String, User> registar;

	private static Logger logger = Logger.getLogger(RedirectContext.class.getName());

	public RedirectContext() {
		setState(new NonRegistered());
		registar = new HashMap<String, User>();
	}

	public void doRequest(SipServletRequest request) throws ServletException, IOException {
		String method = request.getMethod();
		int response = 0;

		if ("REGISTER".equals(method)) {
			response = state.doRegister(request);
		}

		request.createResponse(response).send();
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

}
