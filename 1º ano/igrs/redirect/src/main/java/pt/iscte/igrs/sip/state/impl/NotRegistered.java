package pt.iscte.igrs.sip.state.impl;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.sip.Address;
import javax.servlet.sip.SipServletRequest;
import javax.servlet.sip.SipServletResponse;
import javax.servlet.sip.SipURI;

import pt.iscte.igrs.sip.Registrar;
import pt.iscte.igrs.sip.model.User;
import pt.iscte.igrs.sip.servlet.RedirectContext;
import pt.iscte.igrs.sip.state.State;

public class NotRegistered extends State {

	private static Logger logger = Logger.getLogger(NotRegistered.class.getName());

	@Override
	public void doRegister(SipServletRequest request) throws ServletException, IOException {
		String host = "";
		if (request.getTo().getURI().isSipURI()) {
			host = ((SipURI) request.getTo().getURI()).getHost();
		}

		if (host.equals("acme.pt")) {
			Address address = request.getAddressHeader("Contact");

			int expires = request.getExpires();
			if (expires < 0) {
				expires = address.getExpires();
			}

			if (expires > 0) {
				User user = new User();
				user.setId(UUID.randomUUID());		
				user.setUsername(((SipURI) request.getTo().getURI()).getUser());
				user.setAddressOfRecord(request.getTo().getURI());
				user.setContact(address.getURI());
				
				RedirectContext.registar.put(user.getAddressOfRecord().toString(), user);
				RedirectContext.registar.put(user.getContact().toString(), user);
				RedirectContext.states.put(user.getAddressOfRecord().toString(), new Registered());
				
				request.createResponse(SipServletResponse.SC_OK).send();
			} else {
				request.createResponse(SipServletResponse.SC_BAD_REQUEST).send();
			}
		}

		request.createResponse(SipServletResponse.SC_FORBIDDEN).send();
	}

}
