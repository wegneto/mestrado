package pt.iscte.igrs.sip.state.impl;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.sip.Address;
import javax.servlet.sip.SipServletRequest;
import javax.servlet.sip.SipServletResponse;
import javax.servlet.sip.SipURI;

import pt.iscte.igrs.sip.servlet.RedirectContext;
import pt.iscte.igrs.sip.state.State;

public class Registered extends State {

	private static Logger logger = Logger.getLogger(NotRegistered.class.getName());

	@Override
	public int doRegister(SipServletRequest request) throws ServletException, IOException {
		logger.info("Requisição para deresgistar: " + request.getTo());

		int responseCode = SipServletResponse.SC_NOT_FOUND;

		Address address = request.getAddressHeader("Contact");
		int expires = request.getExpires();
		if (expires < 0) {
			expires = address.getExpires();
		}

		String aor = request.getTo().getURI().toString();

		if (expires == 0 && RedirectContext.registar.containsKey(aor)) {
			RedirectContext.registar.remove(aor);

			logger.info("Definindo o proximo estado para: " + aor);
			RedirectContext.getStates().put(aor, new NotRegistered());

			logger.info("Usuário deregistado com sucesso: " + aor);

			responseCode = SipServletResponse.SC_OK;
		} else if (expires > 0) {
			logger.info("O parâmetro \"expires\" é maior que 0.");
			responseCode = SipServletResponse.SC_BAD_REQUEST;
		}

		return responseCode;
	}

	@Override
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
				if (message.matches("^ativar [\\w]*")) {
					String sala = message.split("\\s+")[1];
					logger.info("MESSAGE: Ativando sala " + sala + " para o utilizador " + from);
					RedirectContext.activeRooms.put(from, sala);

					logger.info("Definindo o proximo estado para: " + from);
					RedirectContext.getStates().put(from, new Active());

					responseCode = SipServletResponse.SC_OK;
				} else {
					responseCode = SipServletResponse.SC_BAD_REQUEST;
				}
			}
		}

		return responseCode;
	}

}
