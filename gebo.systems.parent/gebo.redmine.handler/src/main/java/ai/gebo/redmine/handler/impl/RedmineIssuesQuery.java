/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.redmine.handler.impl;

import java.util.List;

import ai.gebo.redmine.client.model.GetIssues200Response;

/**
 * A Redmine issues list query, by filter name. It keeps the one positional
 * call of the generated client (sixty parameters) in a single place. The
 * values use the Redmine short filter syntax: {@code *} any, {@code !*} none,
 * {@code o} open, {@code c} closed, {@code 1|2} either, {@code >=2024-01-01},
 * {@code ><2024-01-01|2024-12-31}, {@code me} for the account itself.
 */
public class RedmineIssuesQuery {
	private String projectId = null;
	private String subprojectId = null;
	private String statusId = null;
	private String parentId = null;
	private String issueId = null;
	private String trackerId = null;
	private String priorityId = null;
	private String assignedToId = null;
	private String createdOn = null;
	private String updatedOn = null;
	private String anySearchable = null;
	private String sort = null;
	private List<String> include = null;

	public static RedmineIssuesQuery forProject(String projectId) {
		RedmineIssuesQuery query = new RedmineIssuesQuery();
		query.projectId = projectId;
		return query;
	}

	public static RedmineIssuesQuery anyProject() {
		return new RedmineIssuesQuery();
	}

	public RedmineIssuesQuery subprojects(String value) {
		this.subprojectId = value;
		return this;
	}

	public RedmineIssuesQuery status(String value) {
		this.statusId = value;
		return this;
	}

	/**
	 * Text the issues must contain in their subject, description or notes, as a
	 * Redmine filter ({@code ~words}: every word must be found).
	 */
	public RedmineIssuesQuery anySearchable(String value) {
		this.anySearchable = value;
		return this;
	}

	public RedmineIssuesQuery parent(String value) {
		this.parentId = value;
		return this;
	}

	public RedmineIssuesQuery issues(String value) {
		this.issueId = value;
		return this;
	}

	public RedmineIssuesQuery tracker(String value) {
		this.trackerId = value;
		return this;
	}

	public RedmineIssuesQuery priority(String value) {
		this.priorityId = value;
		return this;
	}

	public RedmineIssuesQuery assignedTo(String value) {
		this.assignedToId = value;
		return this;
	}

	public RedmineIssuesQuery createdOn(String value) {
		this.createdOn = value;
		return this;
	}

	public RedmineIssuesQuery updatedOn(String value) {
		this.updatedOn = value;
		return this;
	}

	public RedmineIssuesQuery sort(String value) {
		this.sort = value;
		return this;
	}

	public RedmineIssuesQuery include(List<String> value) {
		this.include = value;
		return this;
	}

	public GetIssues200Response execute(RedmineConnection connection, int offset, int limit) {
		return connection.issues().getIssues(RedmineConnection.JSON, // format
				offset, // offset
				limit, // limit
				null, // nometa
				null, // X-Redmine-Switch-User
				null, // X-Redmine-Nometa
				sort, // sort
				include, // include
				issueId, // issue_id
				projectId, // project_id
				subprojectId, // subproject_id
				trackerId, // tracker_id
				statusId, // status_id
				priorityId, // priority_id
				assignedToId, // assigned_to_id
				null, // author_id
				null, // author.group
				null, // author.role
				null, // member_of_group
				null, // assigned_to_role
				null, // fixed_version_id
				null, // fixed_version.due_date
				null, // fixed_version.status
				null, // category_id
				parentId, // parent_id
				null, // child_id
				null, // cf_x
				null, // subject
				null, // description
				null, // notes
				createdOn, // created_on
				updatedOn, // updated_on
				null, // closed_on
				null, // start_date
				null, // due_date
				null, // estimated_hours
				null, // spent_time
				null, // done_ratio
				null, // is_private
				null, // attachment
				null, // attachment_description
				null, // watcher_id
				null, // updated_by
				null, // last_updated_by
				null, // project.status
				null, // relates
				null, // duplicates
				null, // duplicated
				null, // blocks
				null, // blocked
				null, // precedes
				null, // follows
				null, // copied_to
				null, // copied_from
				anySearchable, // any_searchable
				null, // query_id
				null, // page
				null, // f[]
				null, // op
				null); // v
	}

	@Override
	public String toString() {
		return "{project_id=" + projectId + ", subproject_id=" + subprojectId + ", status_id=" + statusId
				+ ", parent_id=" + parentId + ", issue_id=" + issueId + ", tracker_id=" + trackerId + ", priority_id="
				+ priorityId + ", assigned_to_id=" + assignedToId + ", created_on=" + createdOn + ", updated_on="
				+ updatedOn + ", any_searchable=" + anySearchable + ", sort=" + sort + "}";
	}
}
