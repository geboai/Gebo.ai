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

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A subset of a user's information - the shape in which a user is handed to the
 * rest of the platform.
 *
 * <p>
 * <b>It used to be nested inside {@code UserRepository}</b>, a {@code MongoRepository},
 * which meant that the ~30 modules that merely wanted to <i>name a user</i> had to
 * import a Mongo repository type to do it. That is the wrong dependency: it is a
 * model, not a persistence detail, and it belongs with {@code User} and
 * {@code UsersGroup} - i.e. in the module that becomes
 * {@code security.interface-models}, which a service without a user store still
 * carries.
 * </p>
 *
 * <p>
 * It remains what Spring Data projects {@code UserRepository.findBy(...)} into; a
 * projection interface may be declared anywhere.
 * </p>
 *
 * Gebo.ai comment agent
 */
public interface UserInfos {

	/**
	 * Gets the username of the user.
	 *
	 * @return The username.
	 */
	String getUsername();

	/**
	 * Gets the name of the user.
	 *
	 * @return The user's name.
	 */
	String getName();

	/**
	 * Gets the sourname of the user.
	 *
	 * @return The user's surname.
	 */
	String getSourname();

	/**
	 * Indicates if the user is disabled.
	 *
	 * @return True if disabled, false otherwise.
	 */
	Boolean getDisabled();

	@JsonIgnore
	Map<String, Object> getCustomInfos();

	/**
	 * Retrieves the roles associated with the user.
	 *
	 * @return A list of roles.
	 */
	List<String> getRoles();

	/**
	 * A view of a {@link User} as {@link UserInfos}.
	 *
	 * @param user the user
	 * @return the user's infos
	 */
	public static UserInfos of(final User user) {
		return new UserInfos() {
			@Override
			@JsonIgnore
			public Map<String, Object> getCustomInfos() {

				return user.getCustomInfos();
			}

			@Override
			public Boolean getDisabled() {

				return user.getDisabled();
			}

			@Override
			public String getUsername() {

				return user.getUsername();
			}

			@Override
			public String getName() {
				return user.getName();
			}

			@Override
			public String getSourname() {
				return user.getSourname();
			}

			@Override
			public List<String> getRoles() {

				return user.getRoles();
			}
		};

	}
}
