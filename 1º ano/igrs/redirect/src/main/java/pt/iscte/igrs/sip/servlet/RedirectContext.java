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
import pt.iscte.igrs.sip.state.impl.NotRegistered;

public class RedirectContext extends SipServlet {

	public static Map<String, User> registar;

	private static Map<String, State> states;

	private static Logger logger = Logger.getLogger(RedirectContext.class.getName());

	public RedirectContext() {
		registar = new HashMap<String, User>();
		states = new HashMap<String, State>();
	}

	public void doRequest(SipServletRequest request) throws ServletException, IOException {
		String method = request.getMethod();
		int response = 0;

		State state = null;
		String aor = request.getTo().getURI().toString();
		if (getStates().containsKey(aor)) {
			state = getStates().get(aor);
		} else {
			state = new NotRegistered();
		}

		if ("REGISTER".equals(method)) {
			response = state.doRegister(request);
		}

		request.createResponse(response).send();
	}

	public static Map<String, State> getStates() {
		return states;
	}

}
