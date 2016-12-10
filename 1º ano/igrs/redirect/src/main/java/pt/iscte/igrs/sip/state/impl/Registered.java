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
	public int doRegister(SipServletRequest request) throws ServletException, IOException {
		logger.info("Requisição para deresgistar: " + request.getTo());
		
		int response = SipServletResponse.SC_NOT_FOUND;
		
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

			response = SipServletResponse.SC_OK;
		} else if (expires > 0) {
			logger.info("O parâmetro \"expires\" é maior que 0.");
			response = SipServletResponse.SC_BAD_REQUEST;
		}
		
		return response;

	}
	
}
