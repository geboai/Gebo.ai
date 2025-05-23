/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.buildsystems.abstraction.layer.impl;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import ai.gebo.architecture.buildsystems.abstraction.layer.IGArtifactMetaInfosRenderer;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.knlowledgebase.model.contents.GDependencyTree;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;

/**
 * Gebo.ai comment agent
 * Service implementation class that provides functionality to render artifact metadata information.
 */
@Service
public class GArtifactMetaInfosRendererImpl implements IGArtifactMetaInfosRenderer {

    // ObjectMapper instance for JSON serialization
    private static ObjectMapper mapper = new ObjectMapper();
    static {
        // Include non-null values only in the JSON output
        mapper.setSerializationInclusion(Include.NON_NULL);
        // Enable pretty printing of JSON output
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    /**
     * Constructor for GArtifactMetaInfosRendererImpl.
     */
    public GArtifactMetaInfosRendererImpl() {
        // Constructor implementation can be expanded if needed
    }

    /**
     * Renders the given artifact metadata information into a JSON-based GDocumentReference.
     *
     * @param artifactMetainfos The metadata information of the artifact.
     * @param parent The parent virtual folder where the generated document will reside.
     * @return A GDocumentReference representing the rendered artifact metadata.
     * @throws GeboContentHandlerSystemException if JSON processing fails.
     */
    @Override
    public GDocumentReference render(GDependencyTree artifactMetainfos, GVirtualFolder parent)
            throws GeboContentHandlerSystemException {
        // Initialize the generated document reference
        GDocumentReference generated = null;
        if (artifactMetainfos != null) {
            // Construct the filename for the JSON document
            StringBuffer fileName = new StringBuffer("gebo-");
            if (artifactMetainfos.getPackageManager() != null) {
                fileName.append(artifactMetainfos.getPackageManager() + "-");
            }
            fileName.append("artifact-infos.json");

            // Set up the GDocumentReference with initial metadata
            generated = new GDocumentReference();
            generated.setGeboFileArchetypeId(GDependencyTree.GEBO_ARTIFACT_ARCHETYPE_ID);
            generated.setCode(parent.getCode() + "/" + fileName.toString());
            generated.setContentType(MediaType.APPLICATION_JSON_VALUE);
            generated.setExtension(".json");
            generated.setParentVirtualFolderCode(parent.getCode());
            generated.setRootKnowledgebaseCode(parent.getRootKnowledgebaseCode());
            generated.setProjectEndpointReference(parent.getProjectEndpointReference());
            generated.setName(fileName.toString());

            try {
                // Convert the artifact metadata to a pretty-printed JSON string
                String text = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(artifactMetainfos);
                generated.setArtificiallyGeneratedContent(text);
            } catch (JsonProcessingException e) {
                // Handle JSON processing exceptions
                throw new GeboContentHandlerSystemException("cant't create document in render(...)", e);
            }
        }
        return generated;
    }
}