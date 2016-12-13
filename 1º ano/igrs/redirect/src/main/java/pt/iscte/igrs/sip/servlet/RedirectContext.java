package pt.iscte.igrs.sip.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.sip.SipServlet;
import javax.servlet.sip.SipServletRequest;
import javax.servlet.sip.SipServletResponse;
import javax.servlet.sip.SipSession;

import pt.iscte.igrs.sip.model.User;
import pt.iscte.igrs.sip.state.State;
import pt.iscte.igrs.sip.state.impl.NotRegistered;

public class RedirectContext extends SipServlet {

	public static Map<String, User> registar;

	public static Map<String, State> states;

	public static Map<String, String> activeRooms;

	public static Map<Integer, String> contactList;
	
	public static Map<String, SipSession> sessions;
	
	private static Logger logger = Logger.getLogger(RedirectContext.class.getName());

	public RedirectContext() {
		registar = new HashMap<String, User>();
		states = new HashMap<String, State>();
		activeRooms = new HashMap<String, String>();
		contactList = new HashMap<Integer, String>();
		contactList.put(1, "sip:bob@acme.pt");
		sessions = new HashMap<String, SipSession>(); 
	}
	
	public void doRequest(SipServletRequest request) throws ServletException, IOException {
		State state = getState(request);
		String method = request.getMethod();
		
		logger.info("Estados ativos: " + states);
		
		if ("REGISTER".equals(method)) {
			state.doRegister(request);
		} else if ("MESSAGE".equals(method)) {
			state.doMessage(request);
		} else if ("INVITE".equals(method)) {
			state.doInvite(request, getServletContext());
		} else if ("BYE".equals(method)) {
			state.doBye(request);
		} else if ("INFO".equals(method)) {
			state.doInfo(request, getServletContext());
		}

		//showInfo();
	}

	public void doResponse(SipServletResponse response) throws ServletException, IOException {
		State state = getState(response.getRequest());
		
		logger.info("Estados ativos: " + states);

		int status = response.getStatus();
		if (status < 200) {
			doProvisionalResponse(response);
		} else {
			if (status < 300) {
				state.doSuccessResponse(response, getServletContext());
			} else if (status < 400) {
				doRedirectResponse(response);
			} else {
				doErrorResponse(response);
			}
		}
		
		//showInfo();
	}
	
	private void showInfo() {
		logger.info("Quantidade de usuários registados: " + registar.size());
		logger.info("Usuários registados: " + registar);
		
		logger.info("Quantidade de estados ativos: " + states.size());
		logger.info("Estados ativos: " + states);

		logger.info("Quantidade de salas ativas: " + activeRooms.size());
		logger.info("Salas ativas: " + activeRooms);
	}

	private State getState(SipServletRequest request) { 
		String stateOwner = "";
		if (request.getAttribute("stateOwner") != null) {
			User user = (User) request.getAttribute("stateOwner");
			stateOwner = user.getAddressOfRecord().toString(); 
		} else {
			stateOwner = request.getFrom().getURI().toString();
		}
		
		State state = null;
		if (states.containsKey(stateOwner)) {
			state = states.get(stateOwner);
		} else {
			state = new NotRegistered();
		}
		
		return state;
	}

}
