package pt.iscte.igrs.sip.model;

import javax.servlet.sip.URI;

public class User {

	private String username;

	private URI addressOfRecord;

	private URI contact;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public URI getAddressOfRecord() {
		return addressOfRecord;
	}

	public void setAddressOfRecord(URI addressOfRecord) {
		this.addressOfRecord = addressOfRecord;
	}

	public URI getContact() {
		return contact;
	}

	public void setContact(URI contact) {
		this.contact = contact;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", addressOfRecord=" + addressOfRecord + ", contact=" + contact + "]";
	}

}
