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
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import ai.gebo.acl.AclGrantType;
import ai.gebo.acl.ContentAccessPolicy;
import ai.gebo.acl.IAclGrantedAccessor;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.core.contents.security.services.IGKnowledgebaseVisibilityService;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.knowledgebase.repositories.KnowledgeBaseRepository;
import ai.gebo.knowledgebase.repositories.ProjectRepository;
import ai.gebo.knowledgebase.repositories.VirtualFolderRepository;
import ai.gebo.security.model.UserInfos;
import ai.gebo.security.services.IGSecurityService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

/**
 * Service implementation for managing the visibility of knowledge bases. AI
 * generated comments
 */
@Service
@AllArgsConstructor
public class GKnowledgebaseVisibilityServiceImpl implements IGKnowledgebaseVisibilityService {

	// Repository for accessing knowledge base data
	final KnowledgeBaseRepository kbRepository;
	// Security service for managing access control
	final IGSecurityService securityService;

	final ProjectRepository pjRepository;

	final IGPersistentObjectManager persistentObjectManager;
	final VirtualFolderRepository virtualFoldersRepository;
	final DocumentReferenceRepository documentsRepository;

	/**
	 * Retrieves all knowledge bases that are visible to the current user.
	 *
	 * @return List of visible knowledge bases.
	 */
	@Override
	public List<GKnowledgeBase> allVisibleKnowledgebases() {
		return securityService.filterCanDoAction(kbRepository.findAll(), true, AclGrantType.READ);
	}

	/**
	 * Retrieves visible knowledge bases and their child knowledge bases based on
	 * provided root codes.
	 *
	 * @param rootkbCodes - List of root knowledge base codes.
	 * @return List of visible knowledge bases including children.
	 */
	@Override
	public List<GKnowledgeBase> visiblesAndChildKnowledgebases(List<String> rootkbCodes) {
		List<GKnowledgeBase> list = kbRepository.findByKnowledgeBaseCodesAndChildKnowledgeBases(rootkbCodes);
		return securityService.filterCanDoAction(list, true, AclGrantType.READ);
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
		return securityService.filterCanDoAction(list, true, AclGrantType.READ);
	}

	/**
	 * Retrieves all visible root knowledge bases.
	 *
	 * @return List of all visible root knowledge bases.
	 */
	@Override
	public List<GKnowledgeBase> allVisibleRootKnowledgebases() {
		List<GKnowledgeBase> list = kbRepository.findByParentKnowledgebaseCodeIsNull();
		return securityService.filterCanDoAction(list, true, AclGrantType.READ);
	}

	@Override
	public List<GKnowledgeBase> getVisibleKnowledgeBaseByCodes(@NotNull @NotEmpty List<String> codes) {
		List<GKnowledgeBase> list = kbRepository.findAllById(codes);
		return securityService.filterCanDoAction(list, true, AclGrantType.READ);
	}

	@Override
	public List<GProject> getVisibleProjectsByKnowledgeBaseCode(@NotNull @NotEmpty String kbCode) {
		Stream<GProject> stream = pjRepository.findByRootKnowledgeBaseCode(kbCode);
		List<GProject> list = stream.toList();
		return securityService.filterCanDoAction(list, true, AclGrantType.READ);
	}

	@Override
	public List<GProject> getVisibleProjectsByParentProjectCode(@NotNull @NotEmpty String pjCode) {
		Stream<GProject> stream = pjRepository.findByParentProjectCode(pjCode);
		List<GProject> list = stream.toList();
		return securityService.filterCanDoAction(list, true, AclGrantType.READ);
	}

	@Override
	public List<GProjectEndpoint> getVisibleProjectsEndpointByParentProjectCode(@NotNull @NotEmpty String pjCode) {
		try {
			List<GProjectEndpoint> endpoints = persistentObjectManager
					.findAllByQbeSettingFunction(GProjectEndpoint.class, (endpoint) -> {
						endpoint.setParentProjectCode(pjCode);
					});
			return securityService.filterAclCanDoAction(endpoints, true, AclGrantType.READ);
		} catch (GeboPersistenceException e) {
			throw new RuntimeException("Exception accessing all endpoints child of: " + pjCode, e);
		} finally {
		}

	}

	@Override
	public List<GVirtualFolder> getVisibleProjectEndpointRootsByParentEndpoint(String code, String className) {
		boolean isAdmin = securityService.isCurrentUserAdmin();
		boolean useAcl = !isAdmin && securityService.getPlatformContentAccessPolicy() == ContentAccessPolicy.ACL_BASED;
		if (useAcl) {
			IAclGrantedAccessor aclInfos = securityService.getCurrentAclGrantedAccessor(AclGrantType.READ);
			return virtualFoldersRepository
					.findByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCodeAndParentVirtualFolderCodeIsNullAndAclAliasesIn(
							className, code, aclInfos.getAllOwnedAclAliases())
					.toList();
		} else {
			return virtualFoldersRepository
					.findByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCodeAndParentVirtualFolderCodeIsNull(
							className, code)
					.toList();
		}

	}

	@Override
	public List<GVirtualFolder> getVisibleChildVirtualFolders(String parentVirtualFolderCode) {
		boolean isAdmin = securityService.isCurrentUserAdmin();
		boolean useAcl = !isAdmin && securityService.getPlatformContentAccessPolicy() == ContentAccessPolicy.ACL_BASED;
		if (useAcl) {
			IAclGrantedAccessor aclInfos = securityService.getCurrentAclGrantedAccessor(AclGrantType.READ);
			return virtualFoldersRepository.findByParentVirtualFolderCodeAndAclAliasesIn(parentVirtualFolderCode,
					aclInfos.getAllOwnedAclAliases()).toList();
		} else {
			return virtualFoldersRepository.findByParentVirtualFolderCode(parentVirtualFolderCode).toList();
		}

	}

	@Override
	public List<GDocumentReference> getVisibleChildDocuments(String parentVirtualFolderCode) {
		boolean isAdmin = securityService.isCurrentUserAdmin();
		boolean useAcl = !isAdmin && securityService.getPlatformContentAccessPolicy() == ContentAccessPolicy.ACL_BASED;
		if (useAcl) {
			IAclGrantedAccessor aclInfos = securityService.getCurrentAclGrantedAccessor(AclGrantType.READ);
			return documentsRepository.findByParentVirtualFolderCodeAndAclAliasesIn(parentVirtualFolderCode,
					aclInfos.getAllOwnedAclAliases()).toList();
		} else {
			return documentsRepository.findByParentVirtualFolderCode(parentVirtualFolderCode).toList();
		}
	}

}