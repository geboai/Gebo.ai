/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.systems.abstraction.layer;

import java.util.List;

import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;
import ai.gebo.architecture.patterns.IGRuntimeConfigurationDao;
import ai.gebo.architecture.persistence.GDynamicConfigurationSourceAdapter;
import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystem;

/**
 * AI generated comments
 * Interface for managing configuration data access objects (DAOs) specific to content management systems.
 *
 * @param <SystemType> The type of the content management system
 */
public interface IGContentManagementSystemConfigurationDao<SystemType extends GContentManagementSystem>
		extends IGRuntimeConfigurationDao<SystemType> {

	/**
	 * Finds a content management system configuration by its code.
	 *
	 * @param code The code to search for
	 * @return The system type that matches the code
	 */
	public default SystemType findByCode(String code) {
		return this.findByPredicate((x) -> {
			// Check if the code matches, ignoring case
			return code != null && x.getCode() != null && x.getCode().equalsIgnoreCase(code);
		});
	}

	/**
	 * Adapter class to integrate with a specific repository type.
	 *
	 * @param <T>          The content management system type
	 * @param <RepoType>   The type of the repository
	 */
	static class Adapter<T extends GContentManagementSystem, RepoType extends IGBaseMongoDBRepository<T>>
			extends GAbstractRuntimeConfigurationDao<T> implements IGContentManagementSystemConfigurationDao<T> {
		
		/**
		 * Constructs an Adapter with the given repository.
		 *
		 * @param repo The repository to use for dynamic configuration
		 */
		public Adapter(RepoType repo) {
			super(List.of(), new GDynamicConfigurationSourceAdapter<T, RepoType>(repo) {
			});
		}
	};

	/**
	 * Adapter class for handling static configuration lists.
	 *
	 * @param <T> The content management system type
	 */
	static class ListAdapter<T extends GContentManagementSystem> extends GAbstractRuntimeConfigurationDao<T>
			implements IGContentManagementSystemConfigurationDao<T> {

		/**
		 * Constructs a ListAdapter using a list of static configurations.
		 * 
		 * @param staticConfigs The static configurations list
		 */
		public ListAdapter(List<T> staticConfigs) {
			super(staticConfigs, null); // staticConfigs are used without dynamic configuration source
		}

	}

	/**
	 * Factory method to create a configuration DAO for a given repository.
	 *
	 * @param repo The repository to use
	 * @param <T> The content management system type
	 * @param <RepoType> The type of the repository
	 * @return A new Adapter instance configured with the repository
	 */
	public static <T extends GContentManagementSystem, RepoType extends IGBaseMongoDBRepository<T>> IGContentManagementSystemConfigurationDao<T> of(
			RepoType repo) {
		return new Adapter(repo);
	}

	/**
	 * Factory method to create a configuration DAO for a list of systems.
	 *
	 * @param list The list of content management systems
	 * @param <T> The content management system type
	 * @return A new ListAdapter instance configured with the list
	 */
	public static <T extends GContentManagementSystem> IGContentManagementSystemConfigurationDao<T> of(List<T> list) {
		return new ListAdapter<T>(list);
	}

	/**
	 * Factory method to create a configuration DAO for a single system.
	 *
	 * @param element The content management system
	 * @param <T> The content management system type
	 * @return A new ListAdapter instance configured with the single element
	 */
	public static <T extends GContentManagementSystem> IGContentManagementSystemConfigurationDao<T> of(T element) {
		return new ListAdapter<T>(List.of(element));
	}
}