/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.security.model;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an editable version of a user with fields that can be modified.
 * 
 * Gebo.ai comment agent
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditableUser {

	/**
	 * Constructs an EditableUser by copying properties from an existing User
	 * object.
	 *
	 * @param u the User object whose details are to be copied.
	 */
	public EditableUser(User u) {
		this.name = u.getName();
		this.disabled = u.getDisabled();
		this.username = u.getUsername();
		this.roles = u.getRoles();
		this.sourname = u.getSourname();
		this.authProvider = u.getProvider();
		this.langCode = u.getLangCode();
	}

	@NotNull
	private String name = null;

	@NotNull
	private String sourname = null;

	@NotNull
	private String username;

	private Boolean disabled = null;

	@NotNull
	private List<String> roles = null;

	@NotNull
	private AuthProvider authProvider = null;
	private String langCode = null;


}