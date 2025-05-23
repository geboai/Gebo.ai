/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.fastsetup.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.fastsetup.model.GeboKnowledgeBaseSetupStatus;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.model.base.GObjectRef;

/**
 * Service class for setting up and retrieving the status of the Gebo Knowledge Base.
 * AI generated comments
 */
@Service
public class GeboFastKnowledgeBaseSetupStatusService {
	@Autowired
	IGPersistentObjectManager persistentObjectManager;
	@Autowired
	DocumentReferenceRepository docrefRepo;

	/**
	 * Default constructor for GeboFastKnowledgeBaseSetupStatusService.
	 */
	public GeboFastKnowledgeBaseSetupStatusService() {

	}

	/**
	 * Retrieves the complete setup status of the Gebo Knowledge Base.
	 *
	 * @return GeboKnowledgeBaseSetupStatus object containing setup information.
	 * @throws GeboPersistenceException if there is an error accessing the persistent storage.
	 */
	public GeboKnowledgeBaseSetupStatus getCompleteKnowledgeBaseSetupStatus() throws GeboPersistenceException {
		GeboKnowledgeBaseSetupStatus status = new GeboKnowledgeBaseSetupStatus();
		status.knowledgeBases = persistentObjectManager.countAll(GKnowledgeBase.class);
		status.projects = persistentObjectManager.countAll(GProject.class);
		status.endpoints = persistentObjectManager.countAllExtendingType(GProjectEndpoint.class);
		List<GProjectEndpoint> published = persistentObjectManager.findAllByQbeSettingFunction(GProjectEndpoint.class,
				(GProjectEndpoint endpoint) -> {
					endpoint.setPublished(true);
				});
		status.endpointsPublished = published.size();
		status.documentReferences = persistentObjectManager.countAll(GDocumentReference.class);
		status.isSetup = status.knowledgeBases > 0 && status.projects > 0 && status.endpoints > 0
				&& status.endpointsPublished > 0 && status.documentReferences > 0;
		return status;
	}

	/**
	 * Inner class representing a row of Gebo content processing information.
	 */
	public static class GeboContentProcessRow {
		public GKnowledgeBase knowledgeBase = null;
		public GProject project = null;
		public GProjectEndpoint endpoint = null;
		public GObjectRef<GProjectEndpoint> endpointObjectRef = null;
		public long contentsCount = 0l; // Number of contents associated with a project endpoint
	}

	/**
	 * Retrieves a list of rows containing information about content processing for each knowledge base, project, and endpoint.
	 *
	 * @return List of GeboContentProcessRow representing content processing data.
	 * @throws GeboPersistenceException if there is an error accessing the persistent storage.
	 */
	public List<GeboContentProcessRow> getContentProcessRows() throws GeboPersistenceException {
		List<GeboContentProcessRow> data = new ArrayList<GeboContentProcessRow>();
		List<GKnowledgeBase> kbs = persistentObjectManager.findAll(GKnowledgeBase.class);
		List<GProject> projects = persistentObjectManager.findAll(GProject.class);
		List<GProjectEndpoint> endpoints = persistentObjectManager.findAllExtendingType(GProjectEndpoint.class);
		for (final GKnowledgeBase kb : kbs) {
			GeboContentProcessRow item = new GeboContentProcessRow();
			item.knowledgeBase = kb;
			Stream<GProject> pchilds = projects.stream().filter(x -> x.getRootKnowledgeBaseCode().equals(kb.getCode()));
			List<GeboContentProcessRow> _pjs = new ArrayList<GeboContentProcessRow>();
			pchilds.forEach(x -> {
				GeboContentProcessRow _item = new GeboContentProcessRow();
				_item.knowledgeBase = kb;
				_item.project = x;

				Stream<GProjectEndpoint> childendp = endpoints.stream()
						.filter(y -> y.getParentProjectCode() != null && y.getParentProjectCode().equals(x.getCode()));
				childendp.forEach(u -> {
					GeboContentProcessRow nitem = new GeboContentProcessRow();
					nitem.knowledgeBase = kb;
					nitem.project = x;
					nitem.endpoint = u;
					nitem.endpointObjectRef = GObjectRef.of(u);
					nitem.contentsCount = docrefRepo
							.countByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCode(
									u.getClass().getName(), u.getCode());
					data.add(nitem);
				});
				data.add(_item);
			});
			data.add(item);
		}
		return data;
	}

	/**
	 * Creates a map from a list of GBaseObjects using their codes as keys.
	 *
	 * @param <T>   Type extending GBaseObject.
	 * @param list  List of objects to be mapped.
	 * @return Map with object codes as keys and objects as values.
	 */
	private static <T extends GBaseObject> Map<String, T> map(List<T> list) {
		Map<String, T> out = new HashMap<String, T>();
		for (T t : list) {
			out.put(t.getCode(), t);
		}

		return out;
	}
}