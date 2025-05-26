/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ai.gebo.security.model.User;

/**
 * Gebo.ai comment agent UserRepository interface for accessing User data in a
 * MongoDB database. Extends MongoRepository to provide basic CRUD operations on
 * User collection.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

	/**
	 * Interface representing a subset of User information. Typically used for
	 * projections.
	 */
	public static interface UserInfos {
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

		/**
		 * Retrieves the roles associated with the user.
		 * 
		 * @return A list of roles.
		 */
		java.util.List<String> getRoles();

		public static UserInfos of(final User user) {
			return new UserInfos() {
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
					return user.getSourname();				}

				@Override
				public List<String> getRoles() {
					
					return user.getRoles();
				}
			};
			
		}

	}

	/**
	 * Finds a User by their username.
	 * 
	 * @param email The username to search for.
	 * @return An Optional containing the found User, or empty if not found.
	 */
	Optional<User> findByUsername(String email);

	/**
	 * Finds Users by their roles.
	 * 
	 * @param role The role to search for.
	 * @return A list of Users with the specified role.
	 */
	java.util.List<User> findByRoles(String role);

	/**
	 * Checks if a User exists by their username.
	 * 
	 * @param email The username to check.
	 * @return True if a User with the username exists, false otherwise.
	 */
	Boolean existsByUsername(String email);

	/**
	 * Finds a pageable list of UserInfos that match the given example.
	 * 
	 * @param example  An example User for querying.
	 * @param pageable The pagination information.
	 * @return A page of UserInfos.
	 */
	Page<UserInfos> findBy(Example<User> example, Pageable pageable);

}