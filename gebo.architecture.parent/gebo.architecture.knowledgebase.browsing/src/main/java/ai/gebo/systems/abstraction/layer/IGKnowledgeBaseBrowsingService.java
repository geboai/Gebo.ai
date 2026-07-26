/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.systems.abstraction.layer;

import java.util.Collection;
import java.util.List;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.knlowledgebase.model.contents.GAbstractVirtualFilesystemObject;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.systems.abstraction.layer.model.KnowledgeBaseBrowsingServiceSelectedReferences;
import ai.gebo.systems.abstraction.layer.model.KnowledgeBaseContext;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * Gebo.ai comment agent
 * This interface defines methods for browsing a knowledge base system
 * and converting between different types of references and objects.
 */
public interface IGKnowledgeBaseBrowsingService extends IGVirtualFilesystemBrowsingService<KnowledgeBaseContext> {
    
    /**
     * Retrieves the selected references from a list of virtual filesystem references.
     *
     * @param references a list of references to virtual filesystem objects.
     * @return KnowledgeBaseBrowsingServiceSelectedReferences representing the selected references.
     * @throws VirtualFilesystemBrowsingException if reference browsing fails.
     * @throws GeboPersistenceException if there is an issue with persistence operations.
     */
    public KnowledgeBaseBrowsingServiceSelectedReferences getReferences(
            @NotNull @Valid List<VFilesystemReference> references)
            throws VirtualFilesystemBrowsingException, GeboPersistenceException;

    /**
     * Converts selected references into a list of virtual filesystem references.
     *
     * @param references the selected references from the knowledge base browsing service.
     * @return a list of virtual filesystem references.
     * @throws VirtualFilesystemBrowsingException if conversion fails.
     * @throws GeboPersistenceException if there is an issue with persistence operations.
     */
    public List<VFilesystemReference> getVFilesystemReferenceRappresentation(
            @NotNull @Valid KnowledgeBaseBrowsingServiceSelectedReferences references)
            throws VirtualFilesystemBrowsingException, GeboPersistenceException;

    /**
     * Converts a GKnowledgeBase object into a virtual filesystem reference.
     *
     * @param kb the knowledge base object to convert.
     * @return a virtual filesystem reference.
     * @throws GeboPersistenceException if there is an issue with persistence operations.
     */
    public VFilesystemReference toReference(GKnowledgeBase kb) throws GeboPersistenceException;

    /**
     * Converts a GProject object into a virtual filesystem reference.
     *
     * @param pj the project object to convert.
     * @return a virtual filesystem reference.
     * @throws GeboPersistenceException if there is an issue with persistence operations.
     */
    public VFilesystemReference toReference(GProject pj) throws GeboPersistenceException;

    /**
     * Converts a GProjectEndpoint object into a virtual filesystem reference.
     *
     * @param pj the project endpoint object to convert.
     * @return a virtual filesystem reference.
     * @throws GeboPersistenceException if there is an issue with persistence operations.
     */
    public VFilesystemReference toReference(GProjectEndpoint pj) throws GeboPersistenceException;

    /**
     * Converts a list of abstract virtual filesystem objects into a collection of
     * virtual filesystem references.
     *
     * @param virtualfsObjects a list of abstract virtual filesystem objects.
     * @return a collection of virtual filesystem references.
     * @throws GeboPersistenceException if there is an issue with persistence operations.
     */
    public Collection<VFilesystemReference> toReferences(List<GAbstractVirtualFilesystemObject> virtualfsObjects) throws GeboPersistenceException;
}