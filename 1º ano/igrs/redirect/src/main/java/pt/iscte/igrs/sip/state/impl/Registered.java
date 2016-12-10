package pt.iscte.igrs.sip.state.impl;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.sip.Address;
import javax.servlet.sip.SipServletRequest;
import javax.servlet.sip.SipServletResponse;

import pt.iscte.igrs.sip.servlet.RedirectContext;
import pt.iscte.igrs.sip.state.State;

public class Registered extends State {

	private static Logger logger = Logger.getLogger(NotRegistered.class.getName());

	@Override
	public void doRegister(SipServletRequest request) throws ServletException, IOException {
		logger.info("Requisição para deresgistar: " + request.getTo());

		Address address = request.getAddressHeader("Contact");
		int expires = request.getExpires();
		if (expires < 0) {
			expires = address.getExpires();
		}

		String aor = request.getTo().getURI().toString();

		if (expires == 0) {
			RedirectContext.registar.remove(aor);

			logger.info("Definindo o proximo estado para: " + aor);
			RedirectContext.states.put(aor, new NotRegistered());

			logger.info("Usuário deregistado com sucesso: " + aor);

			request.createResponse(SipServletResponse.SC_OK).send();
		} else if (expires > 0) {
			logger.info("O parâmetro \"expires\" é maior que 0.");
			request.createResponse(SipServletResponse.SC_BAD_REQUEST).send();
		}
	}

	@Override
	public void doMessage(SipServletRequest request) throws ServletException, IOException {
		logger.info("Mensagem enviada pelo usuário: " + request.getFrom());
		logger.info("Mensagem enviada para: " + request.getTo());

		String from = request.getFrom().getURI().toString();
		String to = request.getTo().getURI().toString();

		if (to.equals("sip:conference@acme.pt")) {
			String message = request.getContent().toString();
			if (message.matches("^ativar [\\w]*")) {
				String sala = message.split("\\s+")[1];
				logger.info("MESSAGE: Ativando sala " + sala + " para o utilizador " + from);
				RedirectContext.activeRooms.put(from, sala);

				logger.info("Definindo o proximo estado para: " + from);
				RedirectContext.states.put(from, new Active());

				request.createResponse(SipServletResponse.SC_OK).send();
			} else {
				request.createResponse(SipServletResponse.SC_BAD_REQUEST).send();
			}
		}

	}

}
