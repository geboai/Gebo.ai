/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.systems.abstraction.layer.impl;

import java.util.Optional;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.core.messages.GAbstractContentMessageFragmentPayload;
import ai.gebo.knlowledgebase.model.contents.GAbstractVirtualFilesystemObject;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knowledgebase.repositories.KnowledgeBaseRepository;
import ai.gebo.knowledgebase.repositories.ProjectRepository;
import ai.gebo.model.base.GBaseVersionableObject;
import ai.gebo.model.base.GObjectRef;
import ai.gebo.systems.abstraction.layer.IGDocumentReferenceEnricherMapFactory;

/**
 * Service implementation of the {@link IGDocumentReferenceEnricherMapFactory} interface.
 * Provides functionality to enrich document references with hierarchical information.
 * 
 * @AI generated comments
 */
@Service
public class GDocumentReferenceEnricherMapFactoryImpl implements IGDocumentReferenceEnricherMapFactory {
    private final static Logger LOGGER = LoggerFactory.getLogger(GDocumentReferenceEnricherMapFactoryImpl.class);

    @Autowired
    KnowledgeBaseRepository kbRepo; // Repository for accessing Knowledge Base information

    @Autowired
    ProjectRepository pjRepo; // Repository for accessing Project information

    /**
     * Function implementation to enrich a GBaseVersionableObject with hierarchy information.
     */
    public static class EnrichWithHierarchyInfos implements Function<GBaseVersionableObject, GBaseVersionableObject> {
        GProject project = null;
        GKnowledgeBase knowledgeBase = null;
        GProjectEndpoint endpoint = null;
        GObjectRef<GProjectEndpoint> eref = null;

        /**
         * Constructs an instance of EnrichWithHierarchyInfos.
         * @param project The project associated with the object.
         * @param knowledgeBase The knowledge base associated with the object.
         * @param endpoint The endpoint associated with the object.
         */
        EnrichWithHierarchyInfos(GProject project, GKnowledgeBase knowledgeBase, GProjectEndpoint endpoint) {
            this.project = project;
            this.knowledgeBase = knowledgeBase;
            this.endpoint = endpoint;
            this.eref = GObjectRef.of(endpoint);
        }

        /**
         * Applies enrichment logic to the provided GBaseVersionableObject.
         * @param t The object to be enriched.
         * @return The enriched object.
         */
        @Override
        public GBaseVersionableObject apply(GBaseVersionableObject t) {
            if (t instanceof GAbstractVirtualFilesystemObject) {
                GAbstractVirtualFilesystemObject avfs = (GAbstractVirtualFilesystemObject) t;
                avfs.setRootKnowledgebaseCode(knowledgeBase.getCode());
                avfs.setParentProjectCode(project.getCode());
                avfs.setProjectEndpointReference(eref);
            }

            return t;
        }
    }

    /**
     * Function implementation to enrich a GAbstractContentMessageFragmentPayload with various properties.
     */
    public static class EnrichPayloadsMapper implements Function<GAbstractContentMessageFragmentPayload, GAbstractContentMessageFragmentPayload> {
        GProject project = null;
        GKnowledgeBase knowledgeBase = null;
        GProjectEndpoint endpoint = null;
        GObjectRef<GProjectEndpoint> eref = null;

        /**
         * Constructs an instance of EnrichPayloadsMapper.
         * @param project The project associated with the payload.
         * @param knowledgeBase The knowledge base associated with the payload.
         * @param endpoint The endpoint associated with the payload.
         */
        EnrichPayloadsMapper(GProject project, GKnowledgeBase knowledgeBase, GProjectEndpoint endpoint) {
            this.project = project;
            this.knowledgeBase = knowledgeBase;
            this.endpoint = endpoint.clone();
            this.eref = GObjectRef.of(endpoint);
        }

        /**
         * Applies enrichment logic to the provided payload.
         * @param t The payload to be enriched.
         * @return The enriched payload.
         */
        @Override
        public GAbstractContentMessageFragmentPayload apply(GAbstractContentMessageFragmentPayload t) {
            t.setProject(project);
            t.setKnowledgeBase(knowledgeBase);
            t.setEndPoint(endpoint);
            return t;
        }
    }

    /**
     * Returns a set of enrichers for a given GProjectEndpoint.
     * @param endpoint The endpoint for which enrichers are to be provided.
     * @return EnricherMappers containing the enrichers for hierarchy info and payloads.
     * @throws GeboContentHandlerSystemException if project or knowledge base cannot be found.
     */
    @Override
    public EnricherMappers mappers(GProjectEndpoint endpoint) throws GeboContentHandlerSystemException {
        Optional<GProject> parentProject = pjRepo.findById(endpoint.getParentProjectCode());
        if (parentProject.isPresent()) {
            GProject project = parentProject.get();
            Optional<GKnowledgeBase> rootKb = kbRepo.findById(project.getRootKnowledgeBaseCode());
            if (rootKb.isPresent()) {
                GKnowledgeBase kb = rootKb.get();
                EnricherMappers mappers = new EnricherMappers(
                        new EnrichWithHierarchyInfos(project, kb, endpoint), 
                        new EnrichPayloadsMapper(project, kb, endpoint));
                return mappers;
            } else {
                throw new GeboContentHandlerSystemException(
                    "The knowledge base with code :" + project.getRootKnowledgeBaseCode() + " does not exist");
            }
        } else {
            throw new GeboContentHandlerSystemException(
                "The project with code :" + endpoint.getParentProjectCode() + " does not exist");
        }
    }
}