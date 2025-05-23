/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.knowledgebase.repositories;

import java.util.stream.Stream;

import org.springframework.data.repository.NoRepositoryBean;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.knlowledgebase.model.contents.GAbstractVirtualFilesystemObject;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.base.GObjectRef;

/**
 * AI generated comments
 * 
 * Repository interface for managing operations on virtual filesystem objects, extending 
 * basic MongoDB repository functionalities.
 * 
 * @param <Type> The specific type of virtual filesystem object handled by the repository
 */
@NoRepositoryBean
public interface IGAbstractVirtualFilesystemObjectRepository<Type extends GAbstractVirtualFilesystemObject>
		extends IGBaseMongoDBRepository<Type> {

	/**
	 * Finds all objects by the parent project's code.
	 *
	 * @param parentProjectCode the code of the parent project
	 * @return a stream of objects under the specified project
	 */
	public Stream<Type> findByParentProjectCode(String parentProjectCode);

	/**
	 * Finds all objects by the root knowledge base's code.
	 *
	 * @param rootKnowledgebaseCode the code of the root knowledge base
	 * @return a stream of objects under the specified knowledge base
	 */
	public Stream<Type> findByRootKnowledgebaseCode(String rootKnowledgebaseCode);

	/**
	 * Finds all objects by the parent virtual folder's code.
	 *
	 * @param code the code of the parent virtual folder
	 * @return a stream of objects under the specified virtual folder
	 */
	public Stream<Type> findByParentVirtualFolderCode(String code);

	/**
	 * Finds all objects by project endpoint's reference class name and code.
	 *
	 * @param className the class name of the project endpoint reference
	 * @param code the reference code of the project endpoint
	 * @return a stream of objects matching the specified reference
	 */
	public Stream<Type> findByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCode(String className,
			String code);

	/**
	 * Finds all objects by project endpoint's reference class name, code, 
	 * that are not deleted and do not match the specified job ID.
	 * 
	 * @param name the class name of the project endpoint reference
	 * @param code the reference code of the project endpoint
	 * @param lastJobId the job ID to be excluded
	 * @return a stream of objects matching the specified criteria
	 */
	public Stream<Type> findByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCodeAndDeletedFalseAndLastesJobIdNot(
			String name, String code, String lastJobId);

	/**
	 * Finds all objects by a project endpoint object.
	 *
	 * @param endpoint the project endpoint object
	 * @return a stream of objects related to the specified project endpoint
	 */
	public default Stream<Type> findByProjectEndpoint(GProjectEndpoint endpoint) {
		GObjectRef<GProjectEndpoint> ref = GObjectRef.of(endpoint);
		return findByProjectEndpointRef(ref);
	}

	/**
	 * Finds all objects by a reference to the project endpoint.
	 *
	 * @param ref reference object for the project endpoint
	 * @return a stream of objects based on the project endpoint reference
	 */
	public default Stream<Type> findByProjectEndpointRef(GObjectRef<GProjectEndpoint> ref) {
		return findByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCode(ref.getClassName(),
				ref.getCode());
	}

	/**
	 * Deletes all objects related to a specific project endpoint.
	 *
	 * @param endpoint the project endpoint to delete objects for
	 */
	public default void deleteByProjectEndpoint(GProjectEndpoint endpoint) {
		GObjectRef<GProjectEndpoint> ref = GObjectRef.of(endpoint);
		deleteByProjectEndpointRef(ref);
	}

	/**
	 * Deletes all objects by a reference to the project endpoint.
	 *
	 * @param ref reference object for the project endpoint
	 */
	public default void deleteByProjectEndpointRef(GObjectRef<GProjectEndpoint> ref) {
		deleteByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCode(ref.getClassName(), ref.getCode());
	}

	/**
	 * Deletes objects by project endpoint's reference class name and code.
	 * 
	 * @param className the class name of the project endpoint reference
	 * @param code the reference code of the project endpoint
	 */
	public void deleteByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCode(String className, String code);

	/**
	 * Finds all objects by project endpoint's reference class name and code, 
	 * where the parent virtual folder code is null.
	 *
	 * @param className the class name of the project endpoint reference
	 * @param code the reference code of the project endpoint
	 * @return a stream of objects matching the specified criteria
	 */
	public Stream<Type> findByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCodeAndParentVirtualFolderCodeIsNull(
			String className, String code);

	/**
	 * Finds all objects by project endpoint's reference class name, code, 
	 * and specific parent virtual folder code.
	 *
	 * @param className the class name of the project endpoint reference
	 * @param code the reference code of the project endpoint
	 * @param parentVirtualFolderCode the code of the parent virtual folder
	 * @return a stream of objects under the specified criteria
	 */
	public Stream<Type> findByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCodeAndParentVirtualFolderCode(
			String className, String code, String parentVirtualFolderCode);

	/**
	 * Deletes all objects by the parent project's code.
	 *
	 * @param code the code of the parent project
	 */
	public void deleteByParentProjectCode(String code);

	/**
	 * Deletes all objects by the root knowledge base's code.
	 *
	 * @param code the code of the root knowledge base
	 */
	public void deleteByRootKnowledgebaseCode(String code);
}