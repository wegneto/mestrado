package pt.iscte.igrs.sip.state;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.sip.SipServletRequest;
import javax.servlet.sip.SipServletResponse;
import javax.servlet.sip.SipURI;

import pt.iscte.igrs.sip.servlet.RedirectContext;
import pt.iscte.igrs.sip.state.impl.Active;
import pt.iscte.igrs.sip.state.impl.Registered;

public abstract class State {

	private static Logger logger = Logger.getLogger(Active.class.getName());

	public int doRegister(SipServletRequest request) throws ServletException, IOException {
		return SipServletResponse.SC_BAD_REQUEST;
	}

	public int doMessage(SipServletRequest request) throws ServletException, IOException {
		logger.info("Mensagem enviada pelo usuário: " + request.getFrom());
		logger.info("Mensagem enviada para: " + request.getTo());

		int responseCode = SipServletResponse.SC_UNAUTHORIZED;

		String host = "";
		if (request.getFrom().getURI().isSipURI()) {
			host = ((SipURI) request.getFrom().getURI()).getHost();
		}

		String from = request.getFrom().getURI().toString();

		if (host.equals("acme.pt") && RedirectContext.registar.containsKey(from)) {
			logger.info("Usuário pertence ao domínio 'acme.pt'");

			responseCode = 0;

			String to = request.getTo().getURI().toString();

			if (to.equals("sip:conference@acme.pt")) {
				String message = request.getContent().toString();
				if (message.matches("^desativar")) {
					logger.info("MESSAGE: Destivando sala para o utilizador " + from);
					RedirectContext.activeRooms.remove(from);

					logger.info("Definindo o proximo estado para: " + from);
					RedirectContext.getStates().put(from, new Registered());

					responseCode = SipServletResponse.SC_OK;
				} else {
					responseCode = SipServletResponse.SC_BAD_REQUEST;
				}
			}
		}

		return responseCode;
	}

	public int doInvite(SipServletRequest request, ServletContext servletContext) throws ServletException, IOException {
		return SipServletResponse.SC_UNAUTHORIZED;
	}
	
	public void doSuccessResponse(SipServletResponse response) throws ServletException, IOException {
	}
	
	public void doBye(SipServletRequest request) throws ServletException, IOException {
	}

}
