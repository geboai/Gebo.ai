/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.core.contents.security.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import ai.gebo.acl.AclGrantType;
import ai.gebo.acl.ContentAccessPolicy;
import ai.gebo.acl.IAclGrantedAccessor;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.core.contents.security.services.VirtualFilesystemQuery;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.knowledgebase.repositories.KnowledgeBaseRepository;
import ai.gebo.knowledgebase.repositories.ProjectRepository;
import ai.gebo.knowledgebase.repositories.VirtualFolderRepository;
import ai.gebo.security.services.IGSecurityService;

/**
 * Pins the paged queries of the visible folders and documents: always inside the
 * query's knowledge bases, never deleted, the user's ACL aliases when the platform
 * uses them, each other criterion only when set.
 */
class VisibleVirtualFilesystemBrowsingTest {

	private IGSecurityService security;
	private MongoOperations mongo;
	private GKnowledgebaseVisibilityServiceImpl service;

	@BeforeEach
	void setUp() {
		security = mock(IGSecurityService.class);
		mongo = mock(MongoOperations.class);
		service = new GKnowledgebaseVisibilityServiceImpl(mock(KnowledgeBaseRepository.class), security,
				mock(ProjectRepository.class), mock(IGPersistentObjectManager.class), mock(VirtualFolderRepository.class),
				mock(DocumentReferenceRepository.class), mongo);
	}

	private void aclBasedUser(List<Integer> aliases) {
		when(security.isCurrentUserAdmin()).thenReturn(false);
		when(security.getPlatformContentAccessPolicy()).thenReturn(ContentAccessPolicy.ACL_BASED);
		IAclGrantedAccessor accessor = mock(IAclGrantedAccessor.class);
		when(accessor.getAllOwnedAclAliases()).thenReturn(aliases);
		when(security.getCurrentAclGrantedAccessor(AclGrantType.READ)).thenReturn(accessor);
	}

	@Test
	void theCriteriaKeepTheScopeTheAclAndTheGivenFilters() {
		aclBasedUser(List.of(3, 4));
		VirtualFilesystemQuery query = VirtualFilesystemQuery.builder().knowledgeBaseCodes(List.of("kb1"))
				.projectEndpointCode("ep").nameContains("a.b (c)").uniqueIds(List.of(7L)).build();

		String json = new Query(service.visibleCriteria(query)).getQueryObject().toJson();

		assertTrue(json.contains("\"rootKnowledgebaseCode\": {\"$in\": [\"kb1\"]}"), json);
		assertTrue(json.contains("\"deleted\": {\"$ne\": true}"), json);
		assertTrue(json.contains("\"projectEndpointReference.code\": \"ep\""), json);
		assertTrue(json.contains("\"aclAliases\": {\"$in\": [3, 4]}"), json);
		assertTrue(json.contains("\"uniqueId\": {\"$in\": [7]}"), json);
		// the name part is quoted, not a pattern
		assertTrue(json.contains("\\\\Qa.b (c)\\\\E"), json);
		assertFalse(json.contains("parentProjectCode"), json);
		assertFalse(json.contains("parentVirtualFolderCode"), json);
	}

	@Test
	void anAdminHasNoAclCriterionAndRootsOnlyMeansInNoFolder() {
		when(security.isCurrentUserAdmin()).thenReturn(true);
		VirtualFilesystemQuery query = VirtualFilesystemQuery.builder().knowledgeBaseCodes(List.of("kb1")).rootsOnly(true)
				.build();

		String json = new Query(service.visibleCriteria(query)).getQueryObject().toJson();

		assertFalse(json.contains("aclAliases"), json);
		assertTrue(json.contains("\"parentVirtualFolderCode\": null"), json);
	}

	@Test
	void withoutKnowledgeBasesNothingIsQueried() {
		assertNull(service.visibleCriteria(VirtualFilesystemQuery.builder().build()));
		Page<GDocumentReference> page = service.browseVisibleDocuments(new VirtualFilesystemQuery(), PageRequest.of(0, 50));
		assertEquals(0, page.getTotalElements());
		assertEquals(0, service.countVisibleDocuments(new VirtualFilesystemQuery()));
		verifyNoInteractions(mongo);
	}

	@Test
	void aPageIsReadWithItsTotal() {
		when(security.isCurrentUserAdmin()).thenReturn(true);
		GDocumentReference document = new GDocumentReference();
		document.setCode("d1");
		when(mongo.count(any(Query.class), eq(GDocumentReference.class))).thenReturn(200L);
		when(mongo.find(any(Query.class), eq(GDocumentReference.class))).thenReturn(List.of(document));

		Page<GDocumentReference> page = service.browseVisibleDocuments(
				VirtualFilesystemQuery.builder().knowledgeBaseCodes(List.of("kb1")).build(), PageRequest.of(2, 50));

		assertEquals(200L, page.getTotalElements());
		assertEquals(List.of(document), page.getContent());
		ArgumentCaptor<Query> read = ArgumentCaptor.forClass(Query.class);
		verify(mongo).find(read.capture(), eq(GDocumentReference.class));
		assertEquals(100L, read.getValue().getSkip());
		assertEquals(50, read.getValue().getLimit());
	}
}
