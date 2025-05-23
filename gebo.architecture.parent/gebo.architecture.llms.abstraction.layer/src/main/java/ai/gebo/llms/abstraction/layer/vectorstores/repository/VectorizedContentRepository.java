/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.vectorstores.repository;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;

import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.llms.abstraction.layer.vectorstores.model.GVectorizedContent;
import ai.gebo.llms.abstraction.layer.vectorstores.model.GVectorizedContent.GVectorizedContentId;
import ai.gebo.model.base.GObjectRef;

/**
 * AI generated comments
 * Repository interface for managing GVectorizedContent entities in MongoDB.
 * Provides methods for querying and modifying vectorized content.
 */
public interface VectorizedContentRepository extends MongoRepository<GVectorizedContent, GVectorizedContentId> {
    
    /**
     * Finds a stream of GVectorizedContent by the class name and code of the project endpoint reference.
     *
     * @param className The class name of the project endpoint reference.
     * @param code The code of the project endpoint reference.
     * @return A stream of matching GVectorizedContent.
     */
    public Stream<GVectorizedContent> findByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCode(
            String className, String code);

    /**
     * Deletes all GVectorizedContent records by project endpoint reference class name and code.
     *
     * @param className The class name of the project endpoint reference.
     * @param code The code of the project endpoint reference.
     */
    public void deleteByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCode(String className, String code);

    /**
     * Finds a stream of GVectorizedContent by a GProjectEndpoint object.
     *
     * @param endpoint The GProjectEndpoint object.
     * @return A stream of matching GVectorizedContent.
     */
    public default Stream<GVectorizedContent> findByProjectEndpoint(GProjectEndpoint endpoint) {
        GObjectRef<GProjectEndpoint> reference = GObjectRef.of(endpoint);
        return findByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCode(reference.getClassName(),
                reference.getCode());
    }

    /**
     * Deletes all GVectorizedContent by a GProjectEndpoint object.
     *
     * @param endpoint The GProjectEndpoint object.
     */
    public default void deleteByProjectEndpoint(GProjectEndpoint endpoint) {
        GObjectRef<GProjectEndpoint> reference = GObjectRef.of(endpoint);
        deleteByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCode(reference.getClassName(),
                reference.getCode());
    }

    /**
     * Finds a stream of GVectorizedContent by a vector store ID and a list of document reference codes.
     *
     * @param vectorStoreId The ID of the vector store.
     * @param ids The list of document reference codes.
     * @return A stream of matching GVectorizedContent.
     */
    public Stream<GVectorizedContent> findByIdVectorStoreIdAndIdDocReferenceCodeIn(String vectorStoreId,
            List<String> ids);

    /**
     * Finds a stream of GVectorizedContent by a list of document reference codes.
     *
     * @param ids The list of document reference codes.
     * @return A stream of matching GVectorizedContent.
     */
    public Stream<GVectorizedContent> findByIdDocReferenceCodeIn(List<String> ids);

    /**
     * Finds a list of GVectorizedContent by a document reference code.
     *
     * @param code The document reference code.
     * @return A list of matching GVectorizedContent.
     */
    public List<GVectorizedContent> findByIdDocReferenceCode(String code);

    /**
     * Finds a stream of GVectorizedContent by a parent project code.
     *
     * @param code The parent project code.
     * @return A stream of matching GVectorizedContent.
     */
    public Stream<GVectorizedContent> findByParentProjectCode(String code);

    /**
     * Finds a stream of GVectorizedContent by a root knowledgebase code.
     *
     * @param code The root knowledgebase code.
     * @return A stream of matching GVectorizedContent.
     */
    public Stream<GVectorizedContent> findByRootKnowledgebaseCode(String code);

    /**
     * Deletes GVectorizedContent by a list of document reference codes.
     *
     * @param ids The list of document reference codes.
     */
    public void deleteByIdDocReferenceCodeIn(List<String> ids);

    /**
     * Deletes GVectorizedContent by a parent project code.
     *
     * @param code The parent project code.
     */
    public void deleteByParentProjectCode(String code);

    /**
     * Deletes GVectorizedContent by a root knowledgebase code.
     *
     * @param code The root knowledgebase code.
     */
    public void deleteByRootKnowledgebaseCode(String code);

    /**
     * Counts the number of GVectorizedContent by a vector store ID.
     *
     * @param vectorStoreId The ID of the vector store.
     * @return The count of matching GVectorizedContent.
     */
    public long countByIdVectorStoreId(String vectorStoreId);

    /**
     * Counts the number of GVectorizedContent by a vector store ID and a root knowledgebase code.
     *
     * @param vectorStoreId The ID of the vector store.
     * @param code The root knowledgebase code.
     * @return The count of matching GVectorizedContent.
     */
    public long countByIdVectorStoreIdAndRootKnowledgebaseCode(String vectorStoreId, String code);

    /**
     * Counts the number of GVectorizedContent by a vector store ID and a parent project code.
     *
     * @param vectorStoreId The ID of the vector store.
     * @param code The parent project code.
     * @return The count of matching GVectorizedContent.
     */
    public long countByIdVectorStoreIdAndParentProjectCode(String vectorStoreId, String code);

}