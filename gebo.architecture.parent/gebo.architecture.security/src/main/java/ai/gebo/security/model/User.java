/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.security.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotNull;

/**
 * This class represents a user entity in the security model. It includes user
 * details such as name, username, password, provider, roles, etc. Each user is
 * uniquely identified by their username in the MongoDB document.
 * 
 * AI generated comments
 */
@Document
public class User {
	private String name = null;
	private String sourname = null;

	@Id
	private String username;

	private String imageUrl;

	private Boolean emailVerified = false;

	private Boolean disabled = null;

	@JsonIgnore
	private String password;

	@NotNull
	private AuthProvider provider;

	private List<String> roles = new ArrayList<String>();

	/**
	 * Gets the name of the user.
	 * 
	 * @return the user's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the user.
	 * 
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the username of the user.
	 * 
	 * @return the user's username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username of the user.
	 * 
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the image URL of the user.
	 * 
	 * @return the user's image URL
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * Sets the image URL of the user.
	 * 
	 * @param imageUrl the image URL to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * Checks if the user's email is verified.
	 * 
	 * @return true if the email is verified, false otherwise
	 */
	public Boolean getEmailVerified() {
		return emailVerified;
	}

	/**
	 * Sets the email verification status of the user.
	 * 
	 * @param emailVerified the email verification status to set
	 */
	public void setEmailVerified(Boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	/**
	 * Gets the password of the user. Note: This field is ignored during JSON
	 * serialization.
	 * 
	 * @return the user's password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password of the user.
	 * 
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the authentication provider used by the user.
	 * 
	 * @return the user's authentication provider
	 */
	public AuthProvider getProvider() {
		return provider;
	}

	/**
	 * Sets the authentication provider for the user.
	 * 
	 * @param provider the authentication provider to set
	 */
	public void setProvider(AuthProvider provider) {
		this.provider = provider;
	}

	/**
	 * Gets the list of roles assigned to the user.
	 * 
	 * @return the user's roles
	 */
	public List<String> getRoles() {
		return roles;
	}

	/**
	 * Sets the list of roles assigned to the user.
	 * 
	 * @param roles the list of roles to set
	 */
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	/**
	 * This inner class represents a user's granted authority. It implements the
	 * GrantedAuthority interface from Spring Security.
	 */
	public static class GAuth implements GrantedAuthority {
		String authority;

		/**
		 * Default constructor for GAuth.
		 */
		public GAuth() {
		}

		/**
		 * Constructs a GAuth with the specified authority.
		 * 
		 * @param s the authority string
		 */
		public GAuth(String s) {
			this.authority = s;
		}

		/**
		 * Returns the authority granted to the user.
		 * 
		 * @return the authority
		 */
		@Override
		public String getAuthority() {
			return authority;
		}

		/**
		 * Sets the authority granted to the user.
		 * 
		 * @param authority the authority to set
		 */
		public void setAuthority(String authority) {
			this.authority = authority;
		}

		/**
		 * Converts a list of strings to a list of GAuth objects. Each string in the
		 * input list is converted into a GAuth object.
		 * 
		 * @param c the list of authority strings
		 * @return the list of GAuth objects
		 */
		public static List<GAuth> of(List<String> c) {
			List<GAuth> g = new ArrayList<User.GAuth>();
			if (c != null) {
				for (String string : c) {
					g.add(new GAuth(string));
				}
			}
			return g;
		}
	}

	/**
	 * Gets the surname of the user.
	 * 
	 * @return the user's surname
	 */
	public String getSourname() {
		return sourname;
	}

	/**
	 * Sets the surname of the user.
	 * 
	 * @param sourname the surname to set
	 */
	public void setSourname(String sourname) {
		this.sourname = sourname;
	}

	/**
	 * Checks if the user's account is disabled.
	 * 
	 * @return true if the account is disabled, false otherwise
	 */
	public Boolean getDisabled() {
		return disabled;
	}

	/**
	 * Sets the disabled status of the user's account.
	 * 
	 * @param disabled the disabled status to set
	 */
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	/**
	 * Assigns values from an EditableUser to this User object. Ensures the username
	 * is consistent between the two.
	 * 
	 * @param u the EditableUser object to assign values from
	 * @throws IllegalStateException if there is a mismatch in the username
	 */
	public void assignValues(EditableUser u) {
		if (this.username == null)
			this.username = u.getUsername()!=null?u.getUsername().toLowerCase():null;
		else {
			// Check if the username is consistent or non-empty
			if (this.username != null && ((u.getUsername() == null || u.getUsername().trim().length() == 0)
					|| (!u.getUsername().equals(username)))) {
				throw new IllegalStateException(
						"assignValues has to be called with parameter and User with same email field");
			}
		}
		this.name = u.getName();
		this.provider = u.getAuthProvider();
		this.sourname = u.getSourname();
		this.disabled = u.getDisabled();
		this.roles = u.getRoles();
	}
}