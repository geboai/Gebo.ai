/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.buildsystems.abstraction.layer.functions;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.knlowledgebase.model.contents.GDependencyTree;
import ai.gebo.knlowledgebase.model.contents.GSoftwareArtifact;
import ai.gebo.knowledgebase.repositories.DependencyTreeRepository;
import ai.gebo.knowledgebase.repositories.SoftwareArtifactsRepository;

/**
 * Gebo.ai comment agent
 * Service class for searching software artifacts and managing dependencies.
 */
@Service
class GArtifactSearcherService {
	
	// Logger for logging information and errors
	static final Logger LOGGER = LoggerFactory.getLogger(GArtifactSearcherService.class);
	
	// Repository for handling dependency tree operations
	@Autowired
	DependencyTreeRepository dependencyTreeRepository;
	
	// Repository for handling software artifact operations
	@Autowired
	SoftwareArtifactsRepository artifactsRepository;
	
	// ObjectMapper instance for JSON operations
	static ObjectMapper mapper = new ObjectMapper();

	/**
	 * Default constructor for GArtifactSearcherService
	 */
	public GArtifactSearcherService() {

	}

	/**
	 * Generates example software artifacts based on search parameters and knowledge bases.
	 * 
	 * @param artifactSearch The parameters for searching software artifacts
	 * @param knowledgeBases List of knowledge base codes to be considered
	 * @return List of generated example software artifacts
	 */
	List<GSoftwareArtifact> generateExamples(GSoftwareArtifactSearchParameters artifactSearch,
			List<String> knowledgeBases) {
		List<GSoftwareArtifact> examples = new ArrayList<GSoftwareArtifact>();
		for (String kb : knowledgeBases) {
			GSoftwareArtifact artifact = new GSoftwareArtifact();
			artifact.setPackageManager(artifactSearch.getPackageManager());
			artifact.setModuleId(artifactSearch.getModuleIdOrGroupId());
			artifact.setArtifactId(artifactSearch.getArtifactId());
			artifact.setRootKnowledgebaseCode(kb);
			examples.add(artifact);
		}
		return examples;
	}

	/**
	 * Finds software artifacts based on search parameters and knowledge bases.
	 * 
	 * @param artifactSearch The parameters for searching software artifacts
	 * @param knowledgeBases List of knowledge base codes to be considered
	 * @return List of matching software artifacts
	 */
	List<GSoftwareArtifact> findSoftwareArtifacts(GSoftwareArtifactSearchParameters artifactSearch,
			List<String> knowledgeBases) {
		List<GSoftwareArtifact> out = new ArrayList<GSoftwareArtifact>();
		generateExamples(artifactSearch, knowledgeBases).stream().forEach(x -> {
			out.addAll(artifactsRepository.findByQbe(x));
		});
		return out;
	}

	/**
	 * Finds all software artifacts in the specified knowledge bases.
	 * 
	 * @param artifactSearch The parameters for searching software artifacts
	 * @param knowledgeBases List of knowledge base codes to be considered
	 * @return List of all software artifacts found
	 */
	List<GSoftwareArtifact> findAllArtifacts(GSoftwareArtifactSearchParameters artifactSearch,
			List<String> knowledgeBases) {
		return artifactsRepository.findByRootKnowledgebaseCodeIn(knowledgeBases);
	}

	/**
	 * Finds the full dependency tree for a given software artifact.
	 * 
	 * @param artifactSearch The parameters for searching software artifacts
	 * @param knowledgeBases List of knowledge base codes to be considered
	 * @return The full dependency tree for the specified artifact
	 */
	GDependencyTree findFullDependenciesTree(GSoftwareArtifactSearchParameters artifactSearch,
			List<String> knowledgeBases) {
		return null;
	}

	/**
	 * Finds all software artifacts that depend on the specified artifact.
	 * 
	 * @param artifactSearch The parameters for searching software artifacts
	 * @param knowledgeBases List of knowledge base codes to be considered
	 * @return List of software artifacts depending on the specified artifact
	 */
	List<GSoftwareArtifact> findAllArtifactsDependingFrom(GSoftwareArtifactSearchParameters artifactSearch,
			List<String> knowledgeBases) {

		return null;
	}

}