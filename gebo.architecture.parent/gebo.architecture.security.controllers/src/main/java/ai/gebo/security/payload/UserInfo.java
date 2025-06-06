package ai.gebo.security.payload;

import java.util.ArrayList;
import java.util.List;

/**
 * Inner class representing user information, including username and roles.
 */
public class UserInfo {
	private String username;
	private List<String> roles = new ArrayList<String>();

	/**
	 * Gets the username of the user.
	 * 
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username of the user.
	 * 
	 * @param email the email to be set as username
	 */
	public void setUsername(String email) {
		this.username = email;
	}

	/**
	 * Gets the list of roles assigned to the user.
	 * 
	 * @return list of roles
	 */
	public List<String> getRoles() {
		return roles;
	}

	/**
	 * Sets the roles for the user.
	 * 
	 * @param roles list of roles to be assigned
	 */
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
}