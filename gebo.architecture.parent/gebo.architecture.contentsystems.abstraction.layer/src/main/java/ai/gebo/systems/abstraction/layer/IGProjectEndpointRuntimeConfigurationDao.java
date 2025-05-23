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
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knowledgebase.repositories.IGBaseMongoDBProjectEndpointRepository;

/**
 * AI generated comments
 * Interface for managing runtime configurations of project endpoints.
 * Extends the IGRuntimeConfigurationDao interface for generic project endpoint types.
 *
 * @param <ProjectEndpointType> the type of the project endpoint
 */
public interface IGProjectEndpointRuntimeConfigurationDao<ProjectEndpointType extends GProjectEndpoint>
		extends IGRuntimeConfigurationDao<ProjectEndpointType> {
	
	/**
	 * Finds a project endpoint by its code. The search is done in a case-insensitive manner.
	 * 
	 * @param code the unique code of the project endpoint
	 * @return the project endpoint with the specified code, or null if not found
	 */
	public default ProjectEndpointType findByCode(String code) {
		return this.findByPredicate((x) -> {
			return code != null && x.getCode() != null && x.getCode().equalsIgnoreCase(code);
		});
	}

	/**
	 * An adapter class for managing static configuration lists of project endpoints.
	 * 
	 * @param <T> the type of project endpoint
	 */
	static class ListAdapter<T extends GProjectEndpoint> extends GAbstractRuntimeConfigurationDao<T>
			implements IGProjectEndpointRuntimeConfigurationDao<T> {

		/**
		 * Constructs a new ListAdapter with the given static configurations.
		 * 
		 * @param staticConfigs the list of static configurations
		 */
		public ListAdapter(List<T> staticConfigs) {
			super(staticConfigs, null);
		}
	}

	/**
	 * An adapter class for dynamic project endpoint configurations using a repository.
	 * 
	 * @param <T> the type of project endpoint
	 * @param <RepoType> the type of the repository for project endpoint
	 */
	static class Adapter<T extends GProjectEndpoint, RepoType extends IGBaseMongoDBProjectEndpointRepository<T>>
			extends GAbstractRuntimeConfigurationDao<T> implements IGProjectEndpointRuntimeConfigurationDao<T> {
		
		/**
		 * Constructs a new Adapter using the provided repository.
		 * 
		 * @param repo the repository to fetch dynamic configurations from
		 */
		public Adapter(RepoType repo) {
			super(List.of(), new GDynamicConfigurationSourceAdapter<T, RepoType>(repo) {
			});
		}
	}

	/**
	 * Creates an instance of IGProjectEndpointRuntimeConfigurationDao using a specific repository.
	 * 
	 * @param <T> the type of project endpoint
	 * @param <RepoType> the type of the repository
	 * @param repo the repository for the project endpoints
	 * @return an implementation of IGProjectEndpointRuntimeConfigurationDao
	 */
	public static <T extends GProjectEndpoint, RepoType extends IGBaseMongoDBProjectEndpointRepository<T>> IGProjectEndpointRuntimeConfigurationDao<T> of(
			RepoType repo) {
		return new Adapter(repo);
	}

	/**
	 * Creates an instance of IGProjectEndpointRuntimeConfigurationDao using a list of endpoints.
	 * 
	 * @param <T> the type of project endpoint
	 * @param list the list of project endpoints
	 * @return an implementation of IGProjectEndpointRuntimeConfigurationDao
	 */
	public static <T extends GProjectEndpoint> IGProjectEndpointRuntimeConfigurationDao<T> of(List<T> list) {
		return new ListAdapter<T>(list);
	}

	/**
	 * Creates an instance of IGProjectEndpointRuntimeConfigurationDao using a single project endpoint.
	 * 
	 * @param <T> the type of project endpoint
	 * @param element the single project endpoint
	 * @return an implementation of IGProjectEndpointRuntimeConfigurationDao
	 */
	public static <T extends GProjectEndpoint> IGProjectEndpointRuntimeConfigurationDao<T> of(T element) {
		return new ListAdapter<T>(List.of(element)); 
	}
}