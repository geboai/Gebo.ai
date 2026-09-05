/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.security.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * Gebo.ai comment agent Represents a group of users. This class is stored as a
 * document in MongoDB.
 */
@Document
@Data
public class UsersGroup {

	/**
	 * The unique identifier for the user group.
	 *
	 * <p>
	 * Also the ACL principal: {@code AclGrantedAccessorServiceImpl} keys every grant
	 * made to this group as {@code group:<code>}. Changing it on a group that
	 * already exists therefore does not rename the group - it orphans every grant
	 * ever made to it. Treat it as immutable once set, and see {@link #extCode} for
	 * the identifier a group carries in the system it came from.
	 * </p>
	 */
	@Id
	String code = null;

	/**
	 * A textual description of the user group.
	 */
	String description = null;

	/**
	 * A list containing the user IDs that belong to this group.
	 */
	List<String> userIds = null;

	/**
	 * Free-form metadata about the group, deliberately not part of its JSON
	 * representation.
	 *
	 * <p>
	 * {@code @JsonIgnore} is load-bearing for a group provisioned from an identity
	 * provider: the OAuth2 onboarding records here which provider, directory and
	 * object the group stands for, and consults that record before joining an
	 * identity to an existing group. Keeping the map off the wire means nothing
	 * posting to {@code UsersAdminController} can forge the provenance, and so
	 * cannot make a hand-made group impersonate a directory group.
	 * </p>
	 */
	@JsonIgnore
	private Map<String, Object> customInfos = new HashMap<>();

	/**
	 * The identifier this group has in the system it came from, when it did not
	 * originate here - a directory group id, a role name, an LDAP DN.
	 *
	 * <p>
	 * {@link #code} cannot serve that purpose. It has to be unique across every
	 * source and stable for the life of the group (see above), while an external
	 * identifier is neither: two directories can use the same one. This field is
	 * what lets a UI show the value the source system shows while the code stays the
	 * platform's own key.
	 * </p>
	 */
	private String extCode = null;

}