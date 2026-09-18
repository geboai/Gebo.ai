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
import java.util.Map;

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
		// Carried so that the read-modify-write every caller of
		// IGUsersAdminService.findUserByUsername() performs round-trips it instead of
		// silently dropping it. See the field.
		this.customInfos = u.getCustomInfos();
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

	/**
	 * Free-form metadata about the user, mirroring {@link User#getCustomInfos()}.
	 *
	 * <p>
	 * It is here because {@link ai.gebo.security.services.IGUsersAdminService} is
	 * the only supported way to write a user, and without this field there was no
	 * way to write custom infos through it at all: a caller had to reach past the
	 * service to the {@code UserRepository}, which works only where the user store
	 * is local and bypasses the auditing every other write goes through. The OAuth2
	 * onboarding chain, which records on each user which provider and directory it
	 * was provisioned from, is the caller that needed it.
	 * </p>
	 *
	 * <p>
	 * Unlike {@link UsersGroup#getCustomInfos()} this one is <b>not</b>
	 * {@code @JsonIgnore}d, for two reasons: it has to survive the trip over
	 * {@code UsersAdminClusterController} in a microservices deployment, and nothing
	 * here is a security control - a group's custom infos decide whether an identity
	 * may be joined to that group, a user's only describe where the user came from.
	 * {@code null} means "leave whatever is stored alone"; see
	 * {@link User#assignValues(EditableUser)}.
	 * </p>
	 */
	private Map<String, Object> customInfos = null;

}