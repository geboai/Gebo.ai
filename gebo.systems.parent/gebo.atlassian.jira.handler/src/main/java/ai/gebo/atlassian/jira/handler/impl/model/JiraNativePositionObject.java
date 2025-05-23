/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.jira.handler.impl.model;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.HashMap;

import ai.gebo.atlassian.jira.handler.impl.GJiraRemoteVirtualFilesystemConsumingServiceImpl;
import ai.gebo.atlassian.jira.handler.impl.JiraNavigationUtil;
import ai.gebo.jira.cloud.client.model.Comment;
import ai.gebo.jira.cloud.client.model.IssueBean;
import ai.gebo.jira.cloud.client.model.Project;
import ai.gebo.systems.abstraction.layer.model.AbstractNativePositionObject;

/**
 * AI generated comments
 * 
 * Represents a Jira object (project, issue, comment, or attachment) in the virtual filesystem.
 * This class extends AbstractNativePositionObject to provide Jira-specific implementation
 * for navigating and accessing Jira resources in a file system-like structure.
 */
public class JiraNativePositionObject extends AbstractNativePositionObject {

	private String code = null;
	private String name = null;
	private String url = null;
	private boolean folder = false;
	private HashMap<String, Object> resourceReferenceMetaInfos = new HashMap<String, Object>();
	private String resourceContentType = null;
	private Date resourceModificationTime;
	private boolean resource;
	private Long resourceFileSize = null;
	private Project project;

	private IssueBean issueAsFolder;
	private IssueBean issueAsResource;
	private Comment comment;
	private JiraAttachment attachment;

	/**
	 * Converts an OffsetDateTime to a Date object.
	 * 
	 * @param lastModifiedDateTime The OffsetDateTime to convert
	 * @return A Date object representing the same instant, or null if input is null
	 */
	private static Date toDate(OffsetDateTime lastModifiedDateTime) {
		if (lastModifiedDateTime == null)
			return null;
		else {
			Instant instant = lastModifiedDateTime.toInstant();
			return Date.from(instant);
		}
	}

	/**
	 * Gets the code (identifier) of this Jira object.
	 * 
	 * @return The code value (key for projects/issues, ID for comments/attachments)
	 */
	public String getCode() {

		return code;
	}

	/**
	 * Gets the display name of this Jira object.
	 * 
	 * @return The name of the object
	 */
	public String getName() {

		return name;
	}

	/**
	 * Gets the URL of this Jira object.
	 * 
	 * @return The URL (usually the self-reference URL)
	 */
	public String getUrl() {

		return url;
	}

	/**
	 * Checks if this object represents a resource.
	 * Resources are typically issues, comments, or attachments.
	 * 
	 * @return true if this is a resource, false otherwise
	 */
	@Override
	public boolean isResource() {

		return this.resource;
	}

	/**
	 * Checks if this object represents a folder.
	 * Folders are typically projects or issues that contain other items.
	 * 
	 * @return true if this is a folder, false otherwise
	 */
	@Override
	public boolean isFolder() {

		return this.folder;
	}

	/**
	 * Gets the metadata information map for this resource.
	 * 
	 * @return HashMap containing metadata about this resource
	 */
	@Override
	public HashMap<String, Object> getResourceReferenceMetaInfos() {

		return this.resourceReferenceMetaInfos;
	}

	/**
	 * Gets the content type of this resource.
	 * 
	 * @return The MIME type of the resource
	 */
	@Override
	public String getResourceContentType() {

		return this.resourceContentType;
	}

	/**
	 * Gets the last modification time of this resource.
	 * 
	 * @return Date representing when the resource was last modified
	 */
	@Override
	public Date getResourceModificationTime() {

		return this.resourceModificationTime;
	}

	/**
	 * Converts an ISO-8601 date string to a Date object.
	 * 
	 * @param when String representation of date in ISO-8601 format
	 * @return Date object, or null if conversion fails
	 */
	private Date toDate(String when) {
		try {
			if (when != null)
				return Date.from(Instant.parse(when));
		} catch (Throwable th) {
		}
		return null;
	}

	/**
	 * Gets the file size of this resource.
	 * 
	 * @return The size in bytes, or null if not applicable
	 */
	public Long getResourceFileSize() {
		return resourceFileSize;
	}

	/**
	 * Sets this object to represent a Jira project.
	 * 
	 * @param project The Jira Project to represent
	 */
	public void setProject(Project project) {
		this.project = project;
		this.code = project.getKey();
		this.name = project.getName();
		this.resource = false;
		this.folder = true;
		this.url = this.project.getSelf();
		resourceReferenceMetaInfos.put(JiraNavigationUtil.PROJECT_REFERENCE, project.getKey());
		resourceReferenceMetaInfos.put(GJiraRemoteVirtualFilesystemConsumingServiceImpl.JIRA_OBJECT_TYPE,
				JiraPathNodeType.PROJECT.name());
	}

	/**
	 * Sets this object to represent a Jira issue as a folder.
	 * 
	 * @param issue The Jira IssueBean to represent as a folder
	 */
	public void setIssueAsFolder(IssueBean issue) {
		this.issueAsFolder = issue;
		this.code = issue.getKey();

		this.name = JiraNavigationUtil.getName(issue);
		this.resource = false;
		this.folder = true;
		this.url = this.issueAsFolder.getSelf();
		resourceReferenceMetaInfos.put(GJiraRemoteVirtualFilesystemConsumingServiceImpl.ISSUE_REFERENCE,
				issue.getKey());
		resourceReferenceMetaInfos.put(GJiraRemoteVirtualFilesystemConsumingServiceImpl.JIRA_OBJECT_TYPE,
				JiraPathNodeType.CONTAINER_TICKET.name());
	}

	/**
	 * Gets the project this object is associated with.
	 * 
	 * @return The Project object
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * Checks if this object represents a project.
	 * 
	 * @return true if this is a project, false otherwise
	 */
	public boolean isProject() {
		return project != null;
	}

	/**
	 * Checks if this object represents an issue as a folder.
	 * 
	 * @return true if this is an issue folder, false otherwise
	 */
	public boolean isIssueFolder() {
		return issueAsFolder != null;
	}

	/**
	 * Gets the issue represented as a folder.
	 * 
	 * @return The IssueBean object if this is an issue folder
	 */
	public IssueBean getIssueAsFolder() {
		return issueAsFolder;
	}

	/**
	 * Gets the issue represented as a resource.
	 * 
	 * @return The IssueBean object if this is an issue resource
	 */
	public IssueBean getIssueAsResource() {
		return issueAsResource;
	}

	/**
	 * Sets this object to represent a Jira issue as a resource.
	 * 
	 * @param issueAsResource The Jira IssueBean to represent as a resource
	 */
	public void setIssueAsResource(IssueBean issueAsResource) {
		this.issueAsResource = issueAsResource;
		this.resource = true;
		this.folder = false;
		this.code = issueAsResource.getKey();
		this.url = this.issueAsResource.getSelf();
		Object field = issueAsResource.getFields() != null ? issueAsResource.getFields().get("name") : null;
		this.name = JiraNavigationUtil.getName(issueAsResource);
		resourceReferenceMetaInfos.put(GJiraRemoteVirtualFilesystemConsumingServiceImpl.ISSUE_CONTENT_REFERENCE,
				issueAsResource.getKey());
		resourceReferenceMetaInfos.put(GJiraRemoteVirtualFilesystemConsumingServiceImpl.JIRA_OBJECT_TYPE,
				JiraPathNodeType.TICKET.name());

	}

	/**
	 * Checks if this object represents an issue as a resource.
	 * 
	 * @return true if this is an issue resource, false otherwise
	 */
	public boolean isIssueResource() {
		return issueAsFolder != null;
	}

	/**
	 * Checks if this object represents a comment.
	 * 
	 * @return true if this is a comment, false otherwise
	 */
	public boolean isComment() {
		return comment != null;
	}

	/**
	 * Sets this object to represent a Jira comment.
	 * 
	 * @param comment The Jira Comment to represent
	 */
	public void setComment(Comment comment) {
		this.comment = comment;
		this.url = this.comment.getSelf();
		this.code = this.comment.getId();
		this.resource = true;
		this.folder = false;
		this.name = "comment #" + this.comment.getId();
		this.resourceModificationTime = comment.getUpdated();
		this.resourceContentType = "text/html";
		resourceReferenceMetaInfos.put(GJiraRemoteVirtualFilesystemConsumingServiceImpl.COMMENT_REFERENCE,
				this.comment.getId());
		String content = this.comment.getRenderedBody();
		resourceReferenceMetaInfos.put(GJiraRemoteVirtualFilesystemConsumingServiceImpl.COMMENT_CONTENT, content);
		resourceReferenceMetaInfos.put(GJiraRemoteVirtualFilesystemConsumingServiceImpl.JIRA_OBJECT_TYPE,
				JiraPathNodeType.COMMENT.name());

	}

	/**
	 * Sets this object to represent a Jira attachment.
	 * 
	 * @param jiraAttachment The JiraAttachment to represent
	 */
	public void setAttachment(JiraAttachment jiraAttachment) {
		this.attachment = jiraAttachment;
		this.name = jiraAttachment.getFileName();
		this.code = jiraAttachment.getId();
		this.url = jiraAttachment.getSelf();
		this.resourceContentType = jiraAttachment.getMimeType();
		this.resourceFileSize = jiraAttachment.getSize();
		this.resourceModificationTime = toDate(jiraAttachment.getCreated());
		this.resource = true;
		this.folder = false;
		if (code != null)
			resourceReferenceMetaInfos.put(GJiraRemoteVirtualFilesystemConsumingServiceImpl.ATTACHMENT_ID, code);
		if (attachment.getFileName() != null)
			resourceReferenceMetaInfos.put(GJiraRemoteVirtualFilesystemConsumingServiceImpl.ATTACHMENT_FILENAME,
					attachment.getFileName());
		if (attachment.getContent() != null) {
			resourceReferenceMetaInfos.put(GJiraRemoteVirtualFilesystemConsumingServiceImpl.ATTACHMENT_CONTENT_URL,
					attachment.getContent());
		}
		if (attachment.getMimeType() != null) {
			resourceReferenceMetaInfos.put(GJiraRemoteVirtualFilesystemConsumingServiceImpl.ATTACHMENT_MIME_TYPE,
					attachment.getMimeType());
		}
		resourceReferenceMetaInfos.put(GJiraRemoteVirtualFilesystemConsumingServiceImpl.JIRA_OBJECT_TYPE,
				JiraPathNodeType.ATTACHMENT.name());
	}

	/**
	 * Checks if this object represents an attachment.
	 * 
	 * @return true if this is an attachment, false otherwise
	 */
	public boolean isAttachment() {
		return attachment != null;
	}
}