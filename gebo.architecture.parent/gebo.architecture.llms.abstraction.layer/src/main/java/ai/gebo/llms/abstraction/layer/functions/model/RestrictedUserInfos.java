/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.functions.model;

import ai.gebo.security.model.User;
import ai.gebo.security.repository.UserRepository.UserInfos;

/**
 * Gebo.ai comment agent
 * This class represents a restricted version of user information, encapsulating basic user details.
 */
public class RestrictedUserInfos {
	private String username = null, name = null, sourname = null, email = null;

	/**
	 * Static factory method to create an instance of RestrictedUserInfos from a UserInfos object.
	 *
	 * @param infos the UserInfos object containing user details
	 * @return a new instance of RestrictedUserInfos with the information populated from the provided UserInfos object
	 */
	public static RestrictedUserInfos of(UserInfos infos) {
		RestrictedUserInfos ui = new RestrictedUserInfos();
		ui.name = infos.getName();
		ui.sourname = infos.getSourname();
		ui.username = infos.getUsername();
		ui.email = infos.getUsername();
		return ui;
	}

	/**
	 * Static factory method to create an instance of RestrictedUserInfos from a User object.
	 *
	 * @param infos the User object containing user details
	 * @return a new instance of RestrictedUserInfos with the information populated from the provided User object
	 */
	public static RestrictedUserInfos of(User infos) {
		RestrictedUserInfos ui = new RestrictedUserInfos();
		ui.name = infos.getName();
		ui.sourname = infos.getSourname();
		ui.username = infos.getUsername();
		ui.email = infos.getUsername();
		return ui;
	}

	/**
	 * Retrieves the username.
	 *
	 * @return the username of the user
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username the username to set for the user
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Retrieves the name.
	 *
	 * @return the name of the user
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the name to set for the user
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Retrieves the surname.
	 *
	 * @return the surname of the user
	 */
	public String getSourname() {
		return sourname;
	}

	/**
	 * Sets the surname.
	 *
	 * @param sourname the surname to set for the user
	 */
	public void setSourname(String sourname) {
		this.sourname = sourname;
	}

	/**
	 * Retrieves the email.
	 *
	 * @return the email of the user
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the email to set for the user
	 */
	public void setEmail(String email) {
		this.email = email;
	}
}