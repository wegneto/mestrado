package pt.iscte.igrs.sip.state.impl;

import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.sip.Address;
import javax.servlet.sip.ServletParseException;
import javax.servlet.sip.SipServletRequest;
import javax.servlet.sip.SipServletResponse;
import javax.servlet.sip.SipURI;
import javax.servlet.sip.URI;

import pt.iscte.igrs.sip.model.User;
import pt.iscte.igrs.sip.servlet.RedirectContext;
import pt.iscte.igrs.sip.state.State;

public class NonRegistered extends State {

	private static Logger logger = Logger.getLogger(NonRegistered.class.getName());

	@Override
	public int doRegister(SipServletRequest request) throws ServletException, IOException {
		logger.info("Requisição para resgistar: " + request.getTo());

		int response = SipServletResponse.SC_FORBIDDEN;
		String host = "";

		if (request.getTo().getURI().isSipURI()) {
			host = ((SipURI) request.getTo().getURI()).getHost();
		}

		if (host.equals("acme.pt")) {
			logger.info("Usuário pertence ao domínio 'acme.pt'");

			Address address = request.getAddressHeader("Contact");

			int expires = request.getExpires();
			if (expires < 0) {
				expires = address.getExpires();
			}

			if (expires > 0) {
				User user = new User();
				user.setUsername(((SipURI) request.getTo().getURI()).getUser());
				user.setAddressOfRecord(request.getTo().getURI());
				user.setContact(address.getURI());
				
				RedirectContext.registar.put(user.getAddressOfRecord().toString(), user);
				
				request.setAttribute("state", "registered");
				
				logger.info("Usuário registado com sucesso: " + user);

				response = SipServletResponse.SC_OK;
			}
		}

		return response;
	}

}
