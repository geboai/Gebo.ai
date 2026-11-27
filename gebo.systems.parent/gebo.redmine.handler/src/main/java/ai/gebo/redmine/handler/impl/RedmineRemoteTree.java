/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.redmine.handler.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientResponseException;

import ai.gebo.redmine.client.model.Attachment;
import ai.gebo.redmine.client.model.GetIssues200Response;
import ai.gebo.redmine.client.model.GetProjects200Response;
import ai.gebo.redmine.client.model.IdName;
import ai.gebo.redmine.client.model.Issue;
import ai.gebo.redmine.client.model.IssueChild;
import ai.gebo.redmine.client.model.IssueSummary;
import ai.gebo.redmine.client.model.Project;
import ai.gebo.redmine.client.model.ProjectSummary;
import ai.gebo.redmine.client.model.RedmineFile;
import ai.gebo.redmine.client.model.WikiPage;
import ai.gebo.redmine.client.model.WikiPageSummary;
import ai.gebo.redmine.handler.model.RedmineNativePositionObject;
import ai.gebo.redmine.handler.model.RedminePathComponent;
import ai.gebo.redmine.handler.model.RedminePathNodeType;
import ai.gebo.redmine.handler.model.RedmineSection;

/**
 * The Redmine virtual filesystem over one connection: the projects (roots),
 * the nodes a path step addresses and the children of a node. Browsing,
 * ingestion and search read Redmine only through it, so they see the same
 * tree. It caches what one job or one browsing call reads more than once.
 */
public class RedmineRemoteTree {
	private static final Logger LOGGER = LoggerFactory.getLogger(RedmineRemoteTree.class);
	/** The largest page the Redmine REST API returns. */
	static final int REDMINE_PAGE_SIZE = 100;
	static final List<String> ISSUE_CONTENT_INCLUDES = List.of("journals", "attachments", "children", "relations");
	static final List<String> ISSUE_CHILDREN_INCLUDES = List.of("attachments", "children");
	static final List<String> WIKI_INCLUDES = List.of("attachments");
	private final RedmineConnection connection;
	private final Map<String, List<String>> projectModules = new HashMap<String, List<String>>();
	private final Map<String, List<WikiPageSummary>> wikiPages = new HashMap<String, List<WikiPageSummary>>();
	private boolean identityChecked = false;

	public RedmineRemoteTree(RedmineConnection connection) {
		this.connection = connection;
	}

	public RedmineConnection getConnection() {
		return connection;
	}

	public String getBaseUri() {
		return connection.getBaseUri();
	}

	/**
	 * Redmine reads refused or missing credentials as the anonymous user: its lists
	 * (projects, issues, search, the contents of a public project) then answer 200
	 * with what anonymous may see, usually nothing, so a revoked key or a changed
	 * password would look like an empty Redmine and an ingestion would drop every
	 * document it had read. The account is asked for once per tree before the first
	 * read: a refused identity fails with the 401 of /my/account.json instead.
	 */
	public synchronized void requireIdentity() {
		if (identityChecked) {
			return;
		}
		LOGGER.debug("Checking the Redmine identity on {}", connection.getBaseUri());
		connection.myAccount().getMyAccount(RedmineConnection.JSON, null);
		identityChecked = true;
		LOGGER.debug("Redmine identity on {} accepted", connection.getBaseUri());
	}

	public static boolean isNotFound(Throwable th) {
		return th instanceof RestClientResponseException e && e.getStatusCode().value() == 404;
	}

	/**
	 * Every project the account can see, paged to the end.
	 */
	public List<ProjectSummary> listProjects() {
		requireIdentity();
		List<ProjectSummary> projects = new ArrayList<ProjectSummary>();
		int offset = 0;
		while (true) {
			GetProjects200Response page = connection.projects().getProjects(RedmineConnection.JSON, offset,
					REDMINE_PAGE_SIZE, null, null, null, null, null, null, null, null, null, null, null, null, null,
					null, null, null, null, null, null);
			List<ProjectSummary> items = page != null && page.getProjects() != null ? page.getProjects() : List.of();
			projects.addAll(items);
			offset += items.size();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Redmine projects page read: {} items, {} of {}", items.size(), offset,
						page != null ? page.getTotalCount() : null);
			}
			if (items.isEmpty() || page.getTotalCount() == null || offset >= page.getTotalCount()) {
				break;
			}
		}
		return projects;
	}

	/**
	 * The project node, remembering which Redmine modules the project enables.
	 */
	public RedmineNativePositionObject project(String projectId) {
		requireIdentity();
		Project project = connection.projects().getProject(RedmineConnection.JSON, projectId, null,
				List.of("enabled_modules")).getProject();
		List<String> modules = new ArrayList<String>();
		if (project.getEnabledModules() != null) {
			for (IdName module : project.getEnabledModules()) {
				modules.add(module.getName());
			}
		}
		projectModules.put(projectId, modules);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Redmine project {} ({}) enables {}", projectId, project.getName(), modules);
		}
		return RedmineNodes.project(getBaseUri(), project.getIdentifier() != null ? project.getIdentifier() : projectId,
				project.getName());
	}

	public Issue issue(Integer issueId, List<String> includes) {
		return connection.issues().getIssue(RedmineConnection.JSON, issueId, null, includes).getIssue();
	}

	public WikiPage wikiPage(String projectId, String title, List<String> includes) {
		return connection.wiki().getWikiPage(RedmineConnection.JSON, projectId, title, null, includes, null)
				.getWikiPage();
	}

	public Attachment attachment(Integer attachmentId) {
		return connection.attachments().getAttachment(RedmineConnection.JSON, attachmentId, null).getAttachment();
	}

	public InputStream download(Integer attachmentId) throws IOException {
		return connection.downloadAttachment(attachmentId);
	}

	/**
	 * The node a path step addresses, read from Redmine.
	 */
	public RedmineNativePositionObject node(String projectId, RedminePathComponent step) {
		String base = getBaseUri();
		switch (step.type) {
		case PROJECT:
			return project(step.id);
		case SECTION:
			return RedmineNodes.section(base, projectId, RedmineSection.valueOf(step.id));
		case ISSUE: {
			Issue issue = issue(Integer.valueOf(step.id), null);
			return RedmineNodes.issue(base, projectId, issue.getId(), issue.getSubject());
		}
		case ISSUE_CONTENT: {
			Issue issue = issue(Integer.valueOf(step.id), null);
			return RedmineNodes.issueContent(base, projectId, issue.getId(), issue.getSubject(), issue.getUpdatedOn());
		}
		case WIKI_PAGE: {
			WikiPage page = wikiPage(projectId, step.id, null);
			return RedmineNodes.wikiPage(base, projectId, page.getTitle());
		}
		case WIKI_CONTENT: {
			WikiPage page = wikiPage(projectId, step.id, null);
			return RedmineNodes.wikiContent(base, projectId, page.getTitle(), page.getUpdatedOn());
		}
		case ATTACHMENT:
			return RedmineNodes.attachment(base, projectId, attachment(Integer.valueOf(step.id)));
		case FILE: {
			Attachment file = attachment(Integer.valueOf(step.id));
			return RedmineNodes.fileNode(RedminePathNodeType.FILE, base, projectId, file.getId(), file.getFilename(),
					file.getContentType(), file.getFilesize(), file.getCreatedOn());
		}
		default:
			throw new IllegalArgumentException("Unknown Redmine path step " + step);
		}
	}

	/**
	 * The children of a node: a project holds its enabled sections; the Issues
	 * section its top level issues (sub-issues are under their parents); an
	 * issue its content, attachments and sub-issues; the Wiki section its top
	 * level pages; a page its content, attachments and child pages; the Files
	 * section its files. Contents and files have no children.
	 */
	public List<RedmineNativePositionObject> children(String projectId, RedminePathNodeType type, String id) {
		requireIdentity();
		String base = getBaseUri();
		List<RedmineNativePositionObject> children = new ArrayList<RedmineNativePositionObject>();
		switch (type) {
		case PROJECT: {
			List<String> modules = projectModules.get(projectId);
			if (modules == null) {
				project(projectId);
				modules = projectModules.get(projectId);
			}
			for (RedmineSection section : RedmineSection.values()) {
				if (modules.contains(section.getRedmineModule())) {
					children.add(RedmineNodes.section(base, projectId, section));
				}
			}
		}
			break;
		case SECTION:
			switch (RedmineSection.valueOf(id)) {
			case ISSUES:
				for (IssueSummary issue : topLevelIssues(projectId)) {
					children.add(RedmineNodes.issue(base, projectId, issue.getId(), issue.getSubject()));
				}
				break;
			case WIKI:
				for (WikiPageSummary page : wikiPages(projectId)) {
					if (page.getParent() == null || page.getParent().getTitle() == null) {
						children.add(RedmineNodes.wikiPage(base, projectId, page.getTitle()));
					}
				}
				break;
			case FILES: {
				List<RedmineFile> files = connection.files().getFiles(RedmineConnection.JSON, projectId, null, null)
						.getFiles();
				if (files != null) {
					for (RedmineFile file : files) {
						children.add(RedmineNodes.file(base, projectId, file));
					}
				}
			}
				break;
			}
			break;
		case ISSUE: {
			Issue issue = issue(Integer.valueOf(id), ISSUE_CHILDREN_INCLUDES);
			children.add(RedmineNodes.issueContent(base, projectId, issue.getId(), issue.getSubject(),
					issue.getUpdatedOn()));
			if (issue.getAttachments() != null) {
				for (Attachment attachment : issue.getAttachments()) {
					children.add(RedmineNodes.attachment(base, projectId, attachment));
				}
			}
			if (issue.getChildren() != null) {
				for (IssueChild child : issue.getChildren()) {
					children.add(RedmineNodes.issue(base, projectId, child.getId(), child.getSubject()));
				}
			}
		}
			break;
		case WIKI_PAGE: {
			WikiPage page = wikiPage(projectId, id, WIKI_INCLUDES);
			children.add(RedmineNodes.wikiContent(base, projectId, page.getTitle(), page.getUpdatedOn()));
			if (page.getAttachments() != null) {
				for (Attachment attachment : page.getAttachments()) {
					children.add(RedmineNodes.attachment(base, projectId, attachment));
				}
			}
			for (WikiPageSummary child : wikiPages(projectId)) {
				if (child.getParent() != null && page.getTitle().equals(child.getParent().getTitle())) {
					children.add(RedmineNodes.wikiPage(base, projectId, child.getTitle()));
				}
			}
		}
			break;
		default:
			break;
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Redmine {} {} of project {} has {} children", type, id, projectId, children.size());
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("Redmine {} {} children: {}", type, id, children);
		}
		return children;
	}

	/**
	 * The open and closed issues of the project itself (not of its subprojects,
	 * which are roots of their own) that have no parent issue, paged to the end.
	 */
	public List<IssueSummary> topLevelIssues(String projectId) {
		requireIdentity();
		List<IssueSummary> issues = new ArrayList<IssueSummary>();
		int offset = 0;
		while (true) {
			GetIssues200Response page = RedmineIssuesQuery.forProject(projectId).subprojects("!*").status("*")
					.parent("!*").sort("id").execute(connection, offset, REDMINE_PAGE_SIZE);
			List<IssueSummary> items = page != null && page.getIssues() != null ? page.getIssues() : List.of();
			issues.addAll(items);
			offset += items.size();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Redmine issues page of project {} read: {} items, {} of {}", projectId, items.size(),
						offset, page != null ? page.getTotalCount() : null);
			}
			if (items.isEmpty() || page.getTotalCount() == null || offset >= page.getTotalCount()) {
				break;
			}
		}
		return issues;
	}

	private List<WikiPageSummary> wikiPages(String projectId) {
		List<WikiPageSummary> pages = wikiPages.get(projectId);
		if (pages == null) {
			try {
				pages = connection.wiki().getWikiPages(RedmineConnection.JSON, projectId, null).getWikiPages();
			} catch (RestClientResponseException e) {
				if (!isNotFound(e)) {
					throw e;
				}
				// a project with the wiki module enabled but no page yet answers 404
				LOGGER.debug("The project {} has no wiki page", projectId);
			}
			if (pages == null) {
				pages = List.of();
			}
			wikiPages.put(projectId, pages);
		}
		return pages;
	}
}
