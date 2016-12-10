package pt.iscte.igrs.sip.state.impl;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.sip.SipServletRequest;
import javax.servlet.sip.SipServletResponse;
import javax.servlet.sip.SipURI;

import pt.iscte.igrs.sip.servlet.RedirectContext;
import pt.iscte.igrs.sip.state.State;

public class Active extends State {
	
	private static Logger logger = Logger.getLogger(Active.class.getName());

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
		
		return responseCode;
	}
	
}
