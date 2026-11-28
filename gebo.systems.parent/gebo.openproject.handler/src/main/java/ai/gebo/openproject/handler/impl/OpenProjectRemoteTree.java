/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openproject.handler.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientResponseException;

import ai.gebo.openproject.handler.model.OpenProjectNativePositionObject;
import ai.gebo.openproject.handler.model.OpenProjectPathComponent;
import ai.gebo.openproject.handler.model.OpenProjectPathNodeType;
import ai.gebo.openproject.handler.model.OpenProjectSection;

/**
 * The OpenProject contents of one connection seen as a tree: projects hold the
 * Work packages, Documents, Meetings and News sections; a work package holds its
 * rendered content, its attachments and its child work packages of the same
 * project; documents and meetings hold their rendered content and attachments;
 * news are single documents. One tree lives for one job or one browsing call and
 * caches what it read (project ids, the work packages of a project, user names).
 */
public class OpenProjectRemoteTree {
	private static final Logger LOGGER = LoggerFactory.getLogger(OpenProjectRemoteTree.class);
	/** Every status: the work package lists of a project default to the open ones. */
	static final String ANY_STATUS = "{\"status\":{\"operator\":\"*\",\"values\":[]}}";
	/** The work packages of the project itself: subprojects are roots of their own. */
	static final String NO_SUBPROJECTS = "{\"subprojectId\":{\"operator\":\"!*\",\"values\":[]}}";
	private final OpenProjectConnection connection;
	private final Map<String, Map<String, Object>> projects = new HashMap<String, Map<String, Object>>();
	private final Map<String, List<Map<String, Object>>> workPackages = new HashMap<String, List<Map<String, Object>>>();
	private final Map<String, String> userNames = new HashMap<String, String>();
	private boolean identityChecked = false;

	public OpenProjectRemoteTree(OpenProjectConnection connection) {
		this.connection = connection;
	}

	public OpenProjectConnection getConnection() {
		return connection;
	}

	public String getBaseUri() {
		return connection.getBaseUri();
	}

	/**
	 * Asks OpenProject who the account is, once per tree: a refused API key fails
	 * here with its 401 before any list is read.
	 */
	public synchronized void requireIdentity() {
		if (identityChecked) {
			return;
		}
		LOGGER.debug("Checking the OpenProject identity on {}", connection.getBaseUri());
		Map<String, Object> me = connection.get("/users/me");
		identityChecked = true;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("OpenProject identity on {} accepted: {}", connection.getBaseUri(),
					OpenProjectHal.string(me, "login"));
		}
	}

	public static boolean isNotFound(Throwable th) {
		return th instanceof RestClientResponseException e && e.getStatusCode().value() == 404;
	}

	/** Not found or not allowed: what OpenProject answers for a module a project does not enable. */
	static boolean isNotAvailable(Throwable th) {
		return th instanceof RestClientResponseException e
				&& (e.getStatusCode().value() == 404 || e.getStatusCode().value() == 403);
	}

	/**
	 * Every project the account can see.
	 */
	public List<Map<String, Object>> listProjects() {
		requireIdentity();
		List<Map<String, Object>> list = connection.all("/projects", null);
		for (Map<String, Object> project : list) {
			String identifier = OpenProjectHal.string(project, "identifier");
			if (identifier != null) {
				projects.put(identifier, project);
			}
		}
		return list;
	}

	private Map<String, Object> projectResource(String identifier) {
		Map<String, Object> project = projects.get(identifier);
		if (project == null) {
			requireIdentity();
			project = connection.get("/projects/" + OpenProjectNodes.encode(identifier));
			projects.put(identifier, project);
		}
		return project;
	}

	/** The numeric id of a project, which the collection filters want. */
	public String projectNumericId(String identifier) {
		return OpenProjectHal.string(projectResource(identifier), "id");
	}

	/** The numeric id of a project, null when the account cannot see it. */
	public String visibleProjectNumericId(String identifier) {
		try {
			return projectNumericId(identifier);
		} catch (RestClientResponseException e) {
			if (!isNotAvailable(e)) {
				throw e;
			}
			return null;
		}
	}

	/**
	 * The identifier of the project a resource links, e.g. a work package: the
	 * links carry the numeric id only.
	 */
	public String projectIdentifierOf(Map<String, Object> resource) {
		String numericId = OpenProjectHal.linkId(resource, "project");
		if (numericId == null) {
			return null;
		}
		for (Map.Entry<String, Map<String, Object>> project : projects.entrySet()) {
			if (numericId.equals(OpenProjectHal.string(project.getValue(), "id"))) {
				return project.getKey();
			}
		}
		Map<String, Object> project = connection.get("/projects/" + numericId);
		String identifier = OpenProjectHal.text(project, "identifier");
		if (identifier != null) {
			projects.put(identifier, project);
		}
		return identifier;
	}

	/**
	 * The ids of a collection by lower case name, e.g. "/types", "/statuses" or
	 * "/priorities".
	 */
	public Map<String, String> idsByName(String apiPath) {
		Map<String, String> ids = new HashMap<String, String>();
		for (Map<String, Object> item : OpenProjectHal.elements(connection.get(apiPath))) {
			String name = OpenProjectHal.text(item, "name");
			if (name != null) {
				ids.put(name.toLowerCase(java.util.Locale.ROOT), OpenProjectHal.string(item, "id"));
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("OpenProject {} by name: {}", apiPath, ids);
		}
		return ids;
	}

	/**
	 * The first work packages of every visible project matching a filters
	 * parameter, the most recently updated first.
	 */
	public List<Map<String, Object>> searchWorkPackages(String filters, int limit) {
		requireIdentity();
		Map<String, String> query = new java.util.LinkedHashMap<String, String>();
		query.put("filters", filters);
		query.put("sortBy", OpenProjectWorkPackagesQueryTranslator.SORT_BY);
		return connection.first("/work_packages", query, Math.max(1, limit));
	}

	public OpenProjectNativePositionObject project(String identifier) {
		Map<String, Object> project = projectResource(identifier);
		return OpenProjectNodes.project(getBaseUri(), identifier, OpenProjectHal.text(project, "name"));
	}

	public Map<String, Object> workPackage(String id) {
		requireIdentity();
		return connection.get("/work_packages/" + id);
	}

	public Map<String, Object> document(String id) {
		requireIdentity();
		return connection.get("/documents/" + id);
	}

	public Map<String, Object> meeting(String id) {
		requireIdentity();
		return connection.get("/meetings/" + id);
	}

	public Map<String, Object> news(String id) {
		requireIdentity();
		return connection.get("/news/" + id);
	}

	public Map<String, Object> attachment(String id) {
		requireIdentity();
		return connection.get("/attachments/" + id);
	}

	public InputStream download(String attachmentId) throws IOException {
		requireIdentity();
		return connection.downloadAttachment(attachmentId);
	}

	/** The comments and changes of a work package, oldest first. */
	public List<Map<String, Object>> activities(String workPackageId) {
		return OpenProjectHal.elements(connection.get("/work_packages/" + workPackageId + "/activities"));
	}

	/**
	 * The relations a work package is part of, from either side. Read through
	 * /relations with the involved filter: /work_packages/{id}/relations is
	 * deprecated and answers a plain text redirect on OpenProject 17.
	 */
	public List<Map<String, Object>> relations(String workPackageId) {
		String filters = "[{\"involved\":{\"operator\":\"=\",\"values\":[\"" + workPackageId + "\"]}}]";
		List<Map<String, Object>> list = connection.all("/relations", Map.of("filters", filters));
		LOGGER.debug("OpenProject work package {}: {} relations", workPackageId, list.size());
		return list;
	}

	/**
	 * The attachments of a container, e.g. "/work_packages/12"; none when the
	 * container does not expose them.
	 */
	public List<Map<String, Object>> attachments(String containerPath) {
		try {
			return OpenProjectHal.elements(connection.get(containerPath + "/attachments"));
		} catch (RestClientResponseException e) {
			if (!isNotAvailable(e)) {
				throw e;
			}
			LOGGER.debug("{} exposes no attachments ({})", containerPath, e.getStatusCode());
			return List.of();
		}
	}

	/**
	 * The agenda items of a meeting; none on OpenProject releases without the
	 * agenda items API.
	 */
	public List<Map<String, Object>> agendaItems(String meetingId) {
		try {
			return OpenProjectHal.elements(connection.get("/meetings/" + meetingId + "/agenda_items"));
		} catch (RestClientResponseException e) {
			if (!isNotAvailable(e)) {
				throw e;
			}
			LOGGER.debug("The meeting {} has no readable agenda items ({})", meetingId, e.getStatusCode());
			return List.of();
		}
	}

	/**
	 * The name of a user from a link of a resource (author, assignee, user of an
	 * activity): the link title when OpenProject gives it, else the user read once
	 * per tree; "user #id" when the account cannot see the user.
	 */
	public String userName(Map<String, Object> resource, String link) {
		String title = OpenProjectHal.linkTitle(resource, link);
		if (title != null) {
			return title;
		}
		String id = OpenProjectHal.linkId(resource, link);
		if (id == null) {
			return null;
		}
		return userNames.computeIfAbsent(id, key -> {
			try {
				String name = OpenProjectHal.text(connection.get("/users/" + key), "name");
				return name != null ? name : "user #" + key;
			} catch (RestClientResponseException e) {
				LOGGER.debug("The OpenProject user {} is not readable ({})", key, e.getStatusCode());
				return "user #" + key;
			}
		});
	}

	private List<Map<String, Object>> projectCollection(String apiPath, String identifier) {
		String filters = "[{\"project_id\":{\"operator\":\"=\",\"values\":[\"" + projectNumericId(identifier) + "\"]}}]";
		try {
			return connection.all(apiPath, Map.of("filters", filters));
		} catch (RestClientResponseException e) {
			if (!isNotAvailable(e)) {
				throw e;
			}
			LOGGER.debug("{} not available in the project {} ({})", apiPath, identifier, e.getStatusCode());
			return List.of();
		}
	}

	/**
	 * Every work package of the project itself, open and closed, read once per
	 * tree.
	 */
	public List<Map<String, Object>> workPackagesOf(String identifier) {
		List<Map<String, Object>> list = workPackages.get(identifier);
		if (list == null) {
			requireIdentity();
			try {
				list = connection.all("/projects/" + OpenProjectNodes.encode(identifier) + "/work_packages",
						Map.of("filters", "[" + ANY_STATUS + "," + NO_SUBPROJECTS + "]"));
			} catch (RestClientResponseException e) {
				if (!isNotAvailable(e)) {
					throw e;
				}
				LOGGER.debug("No work packages in the project {} ({})", identifier, e.getStatusCode());
				list = List.of();
			}
			workPackages.put(identifier, list);
		}
		return list;
	}

	/**
	 * The work packages shown at the top of the project: no parent, or a parent in
	 * another project. The API refuses a "no parent" filter, so this is decided here.
	 */
	public List<Map<String, Object>> topLevelWorkPackages(String identifier) {
		List<Map<String, Object>> all = workPackagesOf(identifier);
		Set<String> ids = new HashSet<String>();
		for (Map<String, Object> wp : all) {
			ids.add(OpenProjectHal.string(wp, "id"));
		}
		List<Map<String, Object>> top = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> wp : all) {
			String parent = OpenProjectHal.linkId(wp, "parent");
			if (parent == null || !ids.contains(parent)) {
				top.add(wp);
			}
		}
		return top;
	}

	public List<Map<String, Object>> childWorkPackages(String identifier, String parentId) {
		List<Map<String, Object>> children = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> wp : workPackagesOf(identifier)) {
			if (parentId.equals(OpenProjectHal.linkId(wp, "parent"))) {
				children.add(wp);
			}
		}
		return children;
	}

	public List<Map<String, Object>> documentsOf(String identifier) {
		requireIdentity();
		return projectCollection("/documents", identifier);
	}

	public List<Map<String, Object>> meetingsOf(String identifier) {
		requireIdentity();
		return projectCollection("/meetings", identifier);
	}

	public List<Map<String, Object>> newsOf(String identifier) {
		requireIdentity();
		return projectCollection("/news", identifier);
	}

	/**
	 * The node a path step addresses, read from OpenProject.
	 */
	public OpenProjectNativePositionObject node(String projectId, OpenProjectPathComponent step) {
		String base = getBaseUri();
		switch (step.type) {
		case PROJECT:
			return project(step.id);
		case SECTION:
			return OpenProjectNodes.section(base, projectId, OpenProjectSection.valueOf(step.id));
		case WORK_PACKAGE:
			return OpenProjectNodes.workPackage(base, projectId, workPackage(step.id));
		case WORK_PACKAGE_CONTENT:
			return OpenProjectNodes.workPackageContent(base, projectId, workPackage(step.id));
		case DOCUMENT:
			return OpenProjectNodes.document(base, projectId, document(step.id));
		case DOCUMENT_CONTENT:
			return OpenProjectNodes.documentContent(base, projectId, document(step.id));
		case MEETING:
			return OpenProjectNodes.meeting(base, projectId, meeting(step.id));
		case MEETING_CONTENT:
			return OpenProjectNodes.meetingContent(base, projectId, meeting(step.id));
		case NEWS:
			return OpenProjectNodes.news(base, projectId, news(step.id));
		case ATTACHMENT:
			return OpenProjectNodes.attachment(base, projectId, attachment(step.id));
		default:
			throw new IllegalArgumentException("Unknown OpenProject path step " + step);
		}
	}

	/**
	 * The children of a node: a project holds its four sections; each section
	 * the items of its module (the top level work packages for Work packages); a
	 * work package its content, attachments and children; a document or a
	 * meeting its content and attachments. Contents, news and attachments have
	 * no children.
	 */
	public List<OpenProjectNativePositionObject> children(String projectId, OpenProjectPathNodeType type, String id) {
		requireIdentity();
		String base = getBaseUri();
		List<OpenProjectNativePositionObject> children = new ArrayList<OpenProjectNativePositionObject>();
		switch (type) {
		case PROJECT:
			for (OpenProjectSection section : OpenProjectSection.values()) {
				children.add(OpenProjectNodes.section(base, projectId, section));
			}
			break;
		case SECTION:
			switch (OpenProjectSection.valueOf(id)) {
			case WORK_PACKAGES:
				for (Map<String, Object> wp : topLevelWorkPackages(projectId)) {
					children.add(OpenProjectNodes.workPackage(base, projectId, wp));
				}
				break;
			case DOCUMENTS:
				for (Map<String, Object> document : documentsOf(projectId)) {
					children.add(OpenProjectNodes.document(base, projectId, document));
				}
				break;
			case MEETINGS:
				for (Map<String, Object> meeting : meetingsOf(projectId)) {
					children.add(OpenProjectNodes.meeting(base, projectId, meeting));
				}
				break;
			case NEWS:
				for (Map<String, Object> news : newsOf(projectId)) {
					children.add(OpenProjectNodes.news(base, projectId, news));
				}
				break;
			}
			break;
		case WORK_PACKAGE: {
			Map<String, Object> wp = workPackage(id);
			children.add(OpenProjectNodes.workPackageContent(base, projectId, wp));
			addAttachments(children, base, projectId, "/work_packages/" + id);
			for (Map<String, Object> child : childWorkPackages(projectId, id)) {
				children.add(OpenProjectNodes.workPackage(base, projectId, child));
			}
		}
			break;
		case DOCUMENT:
			children.add(OpenProjectNodes.documentContent(base, projectId, document(id)));
			addAttachments(children, base, projectId, "/documents/" + id);
			break;
		case MEETING:
			children.add(OpenProjectNodes.meetingContent(base, projectId, meeting(id)));
			addAttachments(children, base, projectId, "/meetings/" + id);
			break;
		default:
			break;
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("OpenProject {} {} of project {} has {} children", type, id, projectId, children.size());
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("OpenProject {} {} children: {}", type, id, children);
		}
		return children;
	}

	private void addAttachments(List<OpenProjectNativePositionObject> children, String base, String projectId,
			String containerPath) {
		for (Map<String, Object> attachment : attachments(containerPath)) {
			children.add(OpenProjectNodes.attachment(base, projectId, attachment));
		}
	}
}
