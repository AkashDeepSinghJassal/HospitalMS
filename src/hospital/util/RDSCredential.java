package hospital.util;

import java.io.Serializable;

public class RDSCredential implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String user;
	private String password;
	private String url;
	private String database;

	public RDSCredential(String url, String database, String user, String password) {
		this.url = url;
		this.user = user;
		this.password = password;
		this.database = database;
	}

	public String getCredentials() {
		return String.format("jdbc:mysql://%s/%s?user=%s&password=%s", url, database, user, password);
	}
}
