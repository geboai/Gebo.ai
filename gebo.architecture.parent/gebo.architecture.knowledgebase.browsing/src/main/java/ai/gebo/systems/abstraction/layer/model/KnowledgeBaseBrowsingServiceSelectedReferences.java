/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.systems.abstraction.layer.model;

import java.util.List;

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Gebo.ai comment agent
 * Represents a service for selecting references within a knowledge base browsing context. 
 * This final class is immutable and utilizes Lombok for boilerplate code reduction such as constructors and getters.
 */
@AllArgsConstructor
@Getter
public final class KnowledgeBaseBrowsingServiceSelectedReferences {
	
	// A list of knowledge bases that are part of the selected references.
	final List<GKnowledgeBase> knowledgeBases;
	
	// A list of projects related to the knowledge base references.
	final List<GProject> projects;
	
	// A list of project endpoints associated with the projects.
	final List<GProjectEndpoint> endpoints;
	
	// A list of virtual folders linked with the knowledge base references.
	final List<GVirtualFolder> virtualFolders;
	
	// A list of document references contained within the knowledge bases.
	final List<GDocumentReference> documents;

} 