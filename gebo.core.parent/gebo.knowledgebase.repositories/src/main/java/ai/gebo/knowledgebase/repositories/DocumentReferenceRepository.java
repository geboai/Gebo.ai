/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.knowledgebase.repositories;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.query.TextCriteria;

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.annotations.GObjectReference;
import jakarta.validation.constraints.NotNull;

/**
 * Repository interface for managing {@link GDocumentReference} entities.
 * Extends IGAbstractVirtualFilesystemObjectRepository to inherit basic
 * repository functionalities specific to virtual filesystem objects.
 * 
 * AI generated comments
 */
public interface DocumentReferenceRepository extends IGAbstractVirtualFilesystemObjectRepository<GDocumentReference> {

	/**
	 * Returns the managed type of the repository, which is GDocumentReference.class
	 * 
	 * @return Class type of GDocumentReference
	 */
	@Override
	default Class<GDocumentReference> getManagedType() {
		return GDocumentReference.class;
	}

	/**
	 * Finds a stream of GDocumentReference by matching their paths.
	 *
	 * @param paths A list of absolute paths to match against.
	 * @return Stream of GDocumentReference that match the given paths.
	 */
	public Stream<GDocumentReference> findByAbsolutePathIn(List<String> paths);

	/**
	 * Finds a stream of GDocumentReference by matching a specific file archetype
	 * ID.
	 *
	 * @param fileArchetypeId The ID of the file archetype to match.
	 * @return Stream of GDocumentReference that match the given file archetype ID.
	 */
	public Stream<GDocumentReference> findByGeboFileArchetypeId(String fileArchetypeId);

	/**
	 * Finds a stream of GDocumentReference by matching both a file archetype ID and
	 * a list of root knowledgebase codes.
	 *
	 * @param fileArchetypeId       The ID of the file archetype to match.
	 * @param rootKnowledgebaseCode A list of root knowledgebase codes to match.
	 * @return Stream of GDocumentReference that match the given criteria.
	 */
	public Stream<GDocumentReference> findByGeboFileArchetypeIdAndRootKnowledgebaseCodeIn(String fileArchetypeId,
			List<String> rootKnowledgebaseCode);

	/**
	 * Finds a stream of GDocumentReference by matching a list of file archetype IDs
	 * and a list of root knowledgebase codes.
	 *
	 * @param fileArchetypeId       A list of file archetype IDs to match.
	 * @param rootKnowledgebaseCode A list of root knowledgebase codes to match.
	 * @return Stream of GDocumentReference that match the given criteria.
	 */
	public Stream<GDocumentReference> findByGeboFileArchetypeIdInAndRootKnowledgebaseCodeIn(
			List<String> fileArchetypeId, List<String> rootKnowledgebaseCode);

	/**
	 * Counts GDocumentReference by matching the project endpoint's reference class
	 * name and code.
	 *
	 * @param name The name of the project endpoint reference class.
	 * @param code The code of the project endpoint reference.
	 * @return The count of matching GDocumentReference objects.
	 */
	public long countByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCode(String name, String code);

	/**
	 * Finds DocumentReferenceView by matching a list of codes.
	 *
	 * @param id A list of IDs to match against.
	 * @return List of DocumentReferenceView objects that match the given IDs.
	 */
	public List<DocumentReferenceView> findDocumentReferenceViewByCodeIn(List<String> id);

	/**
	 * Finds a pageable result of DocumentReferenceView by matching root
	 * knowledgebase codes and a text criteria for name.
	 *
	 * @param rootKnowledgeBaseCode A list of root knowledgebase codes to match.
	 * @param textCriteria          Text criteria to match against the name.
	 * @param pageable              Pageable object to determine the pagination of
	 *                              results.
	 * @return A page of DocumentReferenceView matching the criteria.
	 */
	public Page<DocumentReferenceView> findDocumentReferenceViewByRootKnowledgebaseCodeInAndName(
			List<String> rootKnowledgeBaseCode, TextCriteria textCriteria, Pageable pageable);

	/**
	 * Finds a pageable result of DocumentReferenceView by matching root
	 * knowledgebase codes and if the name contains a specific text.
	 *
	 * @param rootKnowledgeBaseCode A list of root knowledgebase codes to match.
	 * @param name                  Name text fragment to match.
	 * @param pageable              Pageable object to determine the pagination of
	 *                              results.
	 * @return A page of DocumentReferenceView with names containing the specified
	 *         text.
	 */
	public Page<DocumentReferenceView> findDocumentReferenceViewByRootKnowledgebaseCodeInAndNameContains(
			List<String> rootKnowledgeBaseCode, @NotNull String name, Pageable pageable);

	/**
	 * Finds DocumentReferenceView by matching root knowledgebase codes and a text
	 * criteria for name.
	 *
	 * @param knowledgeBaseCodes A list of root knowledgebase codes to match.
	 * @param textCriteria       Text criteria to match against the name.
	 * @return List of DocumentReferenceView matching the criteria.
	 */
	public List<DocumentReferenceView> findDocumentReferenceViewByRootKnowledgebaseCodeInAndName(
			@NotNull List<String> knowledgeBaseCodes, TextCriteria textCriteria);

	/**
	 * Finds DocumentReferenceView by matching root knowledgebase codes and if the
	 * name contains a specific text.
	 *
	 * @param knowledgeBaseCodes A list of root knowledgebase codes to match.
	 * @param name               Name text fragment to match.
	 * @return List of DocumentReferenceView with names containing the specified
	 *         text.
	 */
	public List<DocumentReferenceView> findDocumentReferenceViewByRootKnowledgebaseCodeInAndNameContains(
			@NotNull List<String> knowledgeBaseCodes, @NotNull String name);

	/**
	 * Finds a stream of GDocumentReference by matching a parent virtual folder code
	 * and list of names.
	 *
	 * @param code  The code of the parent virtual folder.
	 * @param names A list of names to match against.
	 * @return Stream of GDocumentReference that match the given criteria.
	 */
	public Stream<GDocumentReference> findByParentVirtualFolderCodeAndNameIn(String code, List<String> names);

	/***************************************************
	 * Number of documents by endpoint
	 * 
	 * @param endpoint
	 * @return
	 */
	public default long countByProjectEndpoint(GProjectEndpoint endpoint) {
		return this.countByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCode(
				endpoint.getClass().getName(), endpoint.getCode());
	}
}