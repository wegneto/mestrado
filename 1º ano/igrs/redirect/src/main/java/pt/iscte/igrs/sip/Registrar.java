package pt.iscte.igrs.sip;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.sip.URI;

import pt.iscte.igrs.sip.model.User;

public class Registrar {

	private static Registrar instance;

	private static Map<URI, User> register = new HashMap<URI, User>();

	private Registrar() {
	}

	public static synchronized Registrar getInstance() {
		if (instance == null) {
			instance = new Registrar();
		}
		return instance;
	}

	public void insert(User user) {
		register.put(user.getAddressOfRecord(), user);
	}

	public User get(URI aor) {
		return register.get(aor);
	}

	public void remove(URI aor) {
		register.remove(aor);
	}

	public Map<URI, User> getAll() {
		return register;
	}

}
