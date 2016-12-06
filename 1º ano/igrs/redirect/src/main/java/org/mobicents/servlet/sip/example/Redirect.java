/*
 * $Id: EchoServlet.java,v 1.5 2003/06/22 12:32:15 fukuda Exp $
 */
package org.mobicents.servlet.sip.example;

import java.util.*;
import java.io.IOException;

import javax.servlet.sip.SipServlet;
import javax.servlet.sip.SipServletRequest;
import javax.servlet.sip.SipServletResponse;
import javax.servlet.ServletException;

/**
 */
public class Redirect extends SipServlet {

	private static final long serialVersionUID = 1L;

	static private Map<String, String> Binding;

	public Redirect() {
		super();
		Binding = new HashMap<String, String>();
	}

	/**
	 * Acts as a registrar and location service for REGISTER messages
	 * 
	 * @param request
	 *            The SIP message received by the AS
	 */
	protected void doRegister(SipServletRequest request) throws ServletException, IOException {
		String aor = getSIPuri(request.getHeader("To"));
		String contact = getSIPuriPort(request.getHeader("Contact"));
		SipServletResponse response = null;

		if (aor.contains("@acme.pt")) {
			int expires = getSIPExpires(request.getHeader("Contact"));
			if (expires > 0) {
				log("REGISTER: Registando AoR: " + aor);
				Binding.put(aor, contact);	
			} else {
				log("REGISTER: Deregistando AoR: " + aor);
				Binding.remove(aor);
			}
			response = request.createResponse(200);
		} else {
			log("REGISTER: AoR " + aor + " sem permissão para se registar ao servidor.");
			response = request.createResponse(403);
		}

		response.send();

		// Some logs to show the content of the Registrar database.
		log("*** REGISTER:***");
		Iterator<Map.Entry<String, String>> it = Binding.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> pairs = (Map.Entry<String, String>) it.next();
			System.out.println(pairs.getKey() + " = " + pairs.getValue());
		}
		log("*** REGISTER:***");
	}
	
	protected void doMessage(SipServletRequest request) throws ServletException, IOException {
		String aor = getSIPuri(request.getHeader("From"));
		SipServletResponse response = null;

		if (aor.contains("@acme.pt") && Binding.containsKey(aor)) {
			if (request.getContentLength() > 5) {
				String message = request.getContent().toString();
				if (message.indexOf("desativar") != -1) {
					log("MESSAGE: Desativando sala");
				} else {
					log("MESSAGE: Ativando sala");
				}
			}
			
			response = request.createResponse(200);
		} else {
			log("MESSAGE: Usuário " + aor + " não tem permissão para enviar mensagens.");
			response = request.createResponse(401);
		}
		
		response.send();
	}

	/**
	 * Sends SIP replies to INVITE messages - 300 if registred - 404 if not
	 * registred
	 * 
	 * @param request
	 *            The SIP message received by the AS
	 */
	protected void doInvite(SipServletRequest request) throws ServletException, IOException {

		// Some logs to show the content of the Registrar database.
		log("*** INVITE:***");
		Iterator<Map.Entry<String, String>> it = Binding.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> pairs = (Map.Entry<String, String>) it.next();
			System.out.println(pairs.getKey() + " = " + pairs.getValue());
		}
		log("*** INVITE:***");

		String aor = getSIPuri(request.getHeader("To")); // Get the To AoR
		if (!Binding.containsKey(aor)) { // To AoR not in the database, reply
											// 404
			SipServletResponse response;
			response = request.createResponse(404);
		} else {
			SipServletResponse response = request.createResponse(300);
			// Get the To AoR contact from the database and add it to the
			// response
			response.setHeader("Contact", Binding.get(aor));
			response.send();
		}
	}

	// Two dummy functions that just do super.x
	/**
	 * @param response
	 *            The SIP message received by the AS
	 */
	protected void doResponse(SipServletResponse response) throws ServletException, IOException {
		log("SimpleProxy: doResponse: invalidating session");
		super.doResponse(response);
	}

	protected void doBye(SipServletRequest request) throws ServletException, IOException {
		log("SimpleProxy: doBye: invalidate session when responses is received.");
		super.doBye(request);
	}

	/**
	 * Auxiliary function for extracting SPI URIs
	 * 
	 * @param uri
	 *            A URI with optional extra attributes
	 * @return SIP URI
	 */
	protected String getSIPuri(String uri) {
		String f = uri.substring(uri.indexOf("<") + 1, uri.indexOf(">"));
		int indexCollon = f.indexOf(":", f.indexOf("@"));
		if (indexCollon != -1) {
			f = f.substring(0, indexCollon);
		}
		return f;
	}

	/**
	 * Auxiliary function for extracting SPI URIs
	 * 
	 * @param uri
	 *            A URI with optional extra attributes
	 * @return SIP URI and port
	 */
	protected String getSIPuriPort(String uri) {
		String f = uri.substring(uri.indexOf("<") + 1, uri.indexOf(">"));
		return f;
	}
	
	protected int getSIPExpires(String uri) {
		String expires = uri.substring(uri.indexOf("expires")+8);
		return Integer.valueOf(expires);
	}

}
