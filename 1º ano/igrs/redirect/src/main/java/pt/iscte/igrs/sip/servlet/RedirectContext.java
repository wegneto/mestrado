package pt.iscte.igrs.sip.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.sip.SipServlet;
import javax.servlet.sip.SipServletRequest;
import javax.servlet.sip.SipServletResponse;

import pt.iscte.igrs.sip.model.User;
import pt.iscte.igrs.sip.state.State;
import pt.iscte.igrs.sip.state.impl.NotRegistered;

public class RedirectContext extends SipServlet {

	public static Map<String, User> registar;

	private static Map<String, State> states;

	public static Map<String, String> activeRooms;

	private static Logger logger = Logger.getLogger(RedirectContext.class.getName());

	public RedirectContext() {
		registar = new HashMap<String, User>();
		states = new HashMap<String, State>();
		activeRooms = new HashMap<String, String>();
	}

	public void doRequest(SipServletRequest request) throws ServletException, IOException {
		String method = request.getMethod();

		State state = null;
		String from = request.getFrom().getURI().toString();
		if (getStates().containsKey(from)) {
			state = getStates().get(from);
		} else {
			state = new NotRegistered();
		}

		if ("REGISTER".equals(method)) {
			state.doRegister(request);
		} else if ("MESSAGE".equals(method)) {
			state.doMessage(request);
		} else if ("INVITE".equals(method)) {
			state.doInvite(request, getServletContext());
		} else if ("BYE".equals(method)) {
			state.doBye(request);
		}

		logger.info("Usuários registados: " + registar.size());
		logger.info("Estados ativos: " + states.size());
		logger.info("Salas ativas: " + activeRooms.size());
	}

	public void doResponse(SipServletResponse response) throws ServletException, IOException {
		State state = null;
		String from = response.getFrom().getURI().toString();
		if (getStates().containsKey(from)) {
			state = getStates().get(from);
		} else {
			state = new NotRegistered();
		}

		int status = response.getStatus();
		if (status < 200) {
			doProvisionalResponse(response);
		} else {
			if (status < 300) {
				state.doSuccessResponse(response);
			} else if (status < 400) {
				doRedirectResponse(response);
			} else {
				doErrorResponse(response);
			}
		}
	}

	public static Map<String, State> getStates() {
		return states;
	}

}
