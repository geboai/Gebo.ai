/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.jira.handler.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import ai.gebo.atlassian.jira.handler.impl.model.JiraNativePositionObject;
import ai.gebo.atlassian.jira.handler.impl.model.JiraNavigationCoordinates;
import ai.gebo.atlassian.jira.handler.impl.model.JiraPathComponent;
import ai.gebo.atlassian.jira.handler.impl.model.JiraPathNodeType;
import ai.gebo.jira.cloud.client.model.IssueBean;
import ai.gebo.jira.cloud.client.model.Project;
import ai.gebo.model.virtualfs.BrowseParam;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.PathInfoMetaType;
import ai.gebo.model.virtualfs.VFilesystemReference;

/**
 * AI generated comments
 * 
 * Utility class for handling navigation and transformation between Jira entities
 * and virtual filesystem representations.
 */
public class JiraNavigationUtil {
	/** Field name constant for issue summary */
	public static final String ISSUE_FIELD_SUMMARY = "summary";
	/** Field name constant for issue name */
	public static final String ISSUE_FIELD_NAME = "name";
	/** Prefix used to identify project keys in paths */
	public static final String PROJECT_PREFIX = "PROJECT-KEY:";
	/** Prefix used to identify issue keys in paths */
	public static final String ISSUE_PREFIX = "ISSUE-KEY:";
	/** Separator used in path construction */
	public static final String PATH_SEPARATOR = "|";
	/** List of standard issue fields used for navigation */
	static final List<String> ISSUES_FIELDS = List.of(ISSUE_FIELD_SUMMARY, ISSUE_FIELD_NAME, "description", "issuetype",
			"status", "priority", "assignee", "reporter", "created", "updated", "attachment");
	/** Reference identifier for projects */
	public static final String PROJECT_REFERENCE = "PROJECT-REFERENCE";

	/**
	 * Converts a Jira Project to a GVirtualFilesystemRoot object.
	 * 
	 * @param project The Jira project to convert
	 * @return A virtual filesystem root representing the project
	 */
	static GVirtualFilesystemRoot toRoot(Project project) {
		project.getId();
		project.getKey();
		project.getName();
		GVirtualFilesystemRoot root = new GVirtualFilesystemRoot();
		root.setAbsolutePath(PROJECT_PREFIX + project.getKey());
		root.setCode(PROJECT_PREFIX + project.getKey());
		root.setDescription(project.getName());
		root.setDateModified(project.getArchivedDate());
		return root;
	}

	/**
	 * Determines if a filesystem root represents a Jira project.
	 * 
	 * @param root The filesystem root to check
	 * @return true if the root represents a Jira project, false otherwise
	 */
	public static boolean isProject(GVirtualFilesystemRoot root) {
		return root.getCode() != null && root.getCode().startsWith(PROJECT_PREFIX);
	}

	/**
	 * Extracts the project code from a filesystem root.
	 * 
	 * @param root The filesystem root containing a project code
	 * @return The project code, or null if not a project root
	 */
	public static String getProjectCode(GVirtualFilesystemRoot root) {
		return root.getCode().startsWith(PROJECT_PREFIX) ? root.getCode().substring(PROJECT_PREFIX.length()) : null;
	}

	/**
	 * Converts a Jira issue to a PathInfo object within a filesystem root.
	 * 
	 * @param root The filesystem root containing the issue
	 * @param x The Jira issue to convert
	 * @return A PathInfo representation of the issue
	 */
	public static PathInfo toPathInfo(GVirtualFilesystemRoot root, IssueBean x) {
		PathInfo pathinfo = new PathInfo();
		
		pathinfo.name = getName(x);
		pathinfo.folder = true;
		pathinfo.metaType = PathInfoMetaType.FOLDER;
		pathinfo.absolutePath = ISSUE_PREFIX + x.getKey();
		return pathinfo;
	}

	/**
	 * Extracts the last issue key from a path.
	 * 
	 * @param path The path containing issue keys
	 * @return The last issue key in the path, or null if none found
	 */
	public static String lastIssueKey(PathInfo path) {
		int index = path.absolutePath.lastIndexOf(ISSUE_PREFIX);
		if (index >= 0) {
			index += ISSUE_PREFIX.length();
			return path.absolutePath.substring(index);
		}
		return null;
	}

	/**
	 * Converts a Jira issue to a PathInfo object within the context of a browse parameter.
	 * 
	 * @param param The browse parameter providing context
	 * @param x The Jira issue to convert
	 * @return A PathInfo representation of the issue
	 */
	public static PathInfo toPathInfo(BrowseParam param, IssueBean x) {
		if (param.path == null) {
			return toPathInfo(param.root, x);
		}
		PathInfo pathinfo = new PathInfo();
		pathinfo.name = getName(x);
		pathinfo.folder = true;
		pathinfo.metaType = PathInfoMetaType.FOLDER;
		pathinfo.absolutePath = param.path.absolutePath + PATH_SEPARATOR + ISSUE_PREFIX + x.getKey();
		return pathinfo;
	}

	/**
	 * Converts a filesystem reference to Jira navigation coordinates.
	 * 
	 * @param path The filesystem reference to convert
	 * @return Jira navigation coordinates corresponding to the path
	 */
	public static JiraNavigationCoordinates toJiraNavigationCoordinats(VFilesystemReference path) {
		JiraNavigationCoordinates coordinates = new JiraNavigationCoordinates();
		coordinates.setRoot(path.root);
		if (path.path != null) {
			StringTokenizer tokenizer = new StringTokenizer(path.path.absolutePath, PATH_SEPARATOR);
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();
				if (token.startsWith(ISSUE_PREFIX)) {
					String key = token.substring(ISSUE_PREFIX.length());
					PathInfo pathInfo = new PathInfo();
					pathInfo.absolutePath = ISSUE_PREFIX + key;
					pathInfo.folder = true;
					coordinates.getBrowsingSteps().add(pathInfo);
					JiraPathComponent jiraPathComponent = new JiraPathComponent();
					jiraPathComponent.id = key;
					jiraPathComponent.type = JiraPathNodeType.TICKET;
					coordinates.getBrowsingStepsCustom().add(jiraPathComponent);
				}
			}
		}
		return coordinates;
	}

	/**
	 * Converts a list of Jira native position objects to navigation coordinates.
	 * 
	 * @param childCoordinates The list of native position objects to convert
	 * @return Jira navigation coordinates, or null if the input list is empty
	 */
	public static JiraNavigationCoordinates toJiraNavigationCoordinates(
			List<JiraNativePositionObject> childCoordinates) {
		if (childCoordinates.isEmpty())
			return null;
		JiraNavigationCoordinates coordinates = new JiraNavigationCoordinates();
		JiraNativePositionObject first = childCoordinates.get(0);
		GVirtualFilesystemRoot root = new GVirtualFilesystemRoot();
		root.setCode(PROJECT_PREFIX + first.getCode());
		coordinates.setRoot(root);
		for (int i = 1; i < childCoordinates.size(); i++) {
			JiraNativePositionObject step = childCoordinates.get(i);
			PathInfo path = new PathInfo();
			path.absolutePath = ISSUE_PREFIX + step.getCode();
			coordinates.getBrowsingSteps().add(path);
			JiraPathComponent customPath = new JiraPathComponent();
			customPath.type = JiraPathNodeType.TICKET;
			customPath.id = step.getCode();
			coordinates.getBrowsingStepsCustom().add(customPath);
		}
		return coordinates;
	}

	/**
	 * Extracts a displayable name from a Jira issue.
	 * 
	 * @param issue The Jira issue to get a name for
	 * @return The name or summary of the issue, or "unknown" if not available
	 */
	public static String getName(IssueBean issue) {
		if (issue.getFields() != null) {
			Object name = issue.getFields().get(ISSUE_FIELD_NAME);
			if (name == null)
				name = issue.getFields().get(ISSUE_FIELD_SUMMARY);
			if (name != null)
				return name.toString();
		}
		return "unknown";
	}
}