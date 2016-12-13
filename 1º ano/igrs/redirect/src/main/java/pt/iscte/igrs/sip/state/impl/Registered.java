package pt.iscte.igrs.sip.state.impl;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.sip.Address;
import javax.servlet.sip.SipServletRequest;
import javax.servlet.sip.SipServletResponse;
import javax.servlet.sip.SipSession;

import pt.iscte.igrs.sip.servlet.RedirectContext;
import pt.iscte.igrs.sip.state.State;

public class Registered extends State {

	private static Logger logger = Logger.getLogger(NotRegistered.class.getName());

	@Override
	public void doRegister(SipServletRequest request) throws ServletException, IOException {
		Address address = request.getAddressHeader("Contact");
		int expires = request.getExpires();
		if (expires < 0) {
			expires = address.getExpires();
		}

		String aor = request.getTo().getURI().toString();

		if (expires == 0) {
			RedirectContext.registar.remove(aor);
			RedirectContext.states.put(aor, new NotRegistered());
			request.createResponse(SipServletResponse.SC_OK).send();
		} else if (expires > 0) {
			request.createResponse(SipServletResponse.SC_BAD_REQUEST).send();
		}
	}

	@Override
	public void doMessage(SipServletRequest request) throws ServletException, IOException {
		String from = request.getFrom().getURI().toString();
		String to = request.getTo().getURI().toString();

		if (to.equals("sip:conference@acme.pt")) {
			String message = request.getContent().toString();
			if (message.matches("^ativar [\\w]*")) {
				String roomId = message.split("\\s+")[1];

				RedirectContext.activeRooms.put(from, roomId);
				RedirectContext.states.put(from, new Active());

				request.createResponse(SipServletResponse.SC_OK).send();
			} else {
				request.createResponse(SipServletResponse.SC_BAD_REQUEST).send();
			}
		}

	}

}
