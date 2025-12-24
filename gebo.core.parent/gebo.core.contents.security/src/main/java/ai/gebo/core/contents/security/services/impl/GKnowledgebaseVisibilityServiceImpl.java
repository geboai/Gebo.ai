/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.core.contents.security.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.core.contents.security.services.IGKnowledgebaseVisibilityService;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knowledgebase.repositories.KnowledgeBaseRepository;
import ai.gebo.security.repository.UserRepository.UserInfos;
import ai.gebo.security.services.IGSecurityService;

/**
 * Service implementation for managing the visibility of knowledge bases.
 * AI generated comments
 */
@Service
public class GKnowledgebaseVisibilityServiceImpl implements IGKnowledgebaseVisibilityService {
	
	// Repository for accessing knowledge base data
	@Autowired
	KnowledgeBaseRepository kbRepository;
	
	// Security service for managing access control
	@Autowired
	IGSecurityService securityService;

	/**
	 * Default constructor.
	 */
	public GKnowledgebaseVisibilityServiceImpl() {

	}

	/**
	 * Retrieves all knowledge bases that are visible to the current user.
	 *
	 * @return List of visible knowledge bases.
	 */
	@Override
	public List<GKnowledgeBase> allVisibleKnowledgebases() {
		return securityService.filterAccessible(kbRepository.findAll(), true);
	}

	/**
	 * Retrieves visible knowledge bases and their child knowledge bases based on provided root codes.
	 *
	 * @param rootkbCodes - List of root knowledge base codes.
	 * @return List of visible knowledge bases including children.
	 */
	@Override
	public List<GKnowledgeBase> visiblesAndChildKnowledgebases(List<String> rootkbCodes) {
		List<GKnowledgeBase> list = kbRepository.findByKnowledgeBaseCodesAndChildKnowledgeBases(rootkbCodes);
		return securityService.filterAccessible(list, true);
	}

	/**
	 * Retrieves personal knowledge bases of the current user.
	 *
	 * @return List of personal knowledge bases.
	 */
	@Override
	public List<GKnowledgeBase> getPersonalKnowledgebases() {
		UserInfos user = securityService.getCurrentUser();
		List<GKnowledgeBase> kb = kbRepository.findByUsername(user.getUsername());
		return securityService.filterAccessible(kb, false);
	}

	/**
	 * Retrieves visible root knowledge bases based on provided root codes.
	 *
	 * @param rootkbCodes - List of root knowledge base codes.
	 * @return List of visible root knowledge bases.
	 */
	@Override
	public List<GKnowledgeBase> visiblesRootKnowledgebases(List<String> rootkbCodes) {
		List<GKnowledgeBase> list = kbRepository.findByCodeInAndParentKnowledgebaseCodeIsNull(rootkbCodes);
		return securityService.filterAccessible(list, true);
	}

	/**
	 * Retrieves all visible root knowledge bases.
	 *
	 * @return List of all visible root knowledge bases.
	 */
	@Override
	public List<GKnowledgeBase> allVisibleRootKnowledgebases() {
		List<GKnowledgeBase> list = kbRepository.findByParentKnowledgebaseCodeIsNull();
		return securityService.filterAccessible(list, true);
	}

}