/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.microsoft.sharepoint.cloud.model;

// NEW: Items in a SharePoint list
public class CloudSharepointListItem {
	/**
	 * The numeric identifier (primary key) of the list item. Typically returned as
	 * "Id" in the JSON.
	 */
	private Integer id;

	/**
	 * The Title field (commonly used by default lists). If your list doesn’t have a
	 * Title column, you can omit this.
	 */
	private String title;

	/**
	 * The date/time this item was created. Often returned as an ISO 8601 string or
	 * an OData date string. For a more robust approach, consider mapping it to
	 * OffsetDateTime or ZonedDateTime.
	 */
	private String created;

	/**
	 * The date/time this item was last modified.
	 */
	private String modified;

	/**
	 * The numeric ID (UserInfo table) of the user who created the item. You’d need
	 * a separate query to resolve the user’s actual name or email.
	 */
	private Integer authorId;

	/**
	 * The numeric ID (UserInfo table) of the user who last modified the item.
	 */
	private Integer editorId;

	/**
	 * The globally unique identifier (GUID) of this list item.
	 */
	private String guid;

	/**
	 * Whether this list item has attachments. In classic SharePoint REST, it can be
	 * returned as a boolean field "Attachments".
	 */
	private Boolean attachments;

	/**
	 * The content type ID of this item, e.g., "0x0100D84EAB...".
	 */
	private String contentTypeId;

	/**
	 * The ETag for concurrency checks (if you need to do updates).
	 */
	private String eTag;

	// Adjust fields to match your real data (e.g. custom columns, etc.)

	public String getTitle() {
		return title;
	}

	public void setTitle(String t) {
		this.title = t;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public Integer getEditorId() {
		return editorId;
	}

	public void setEditorId(Integer editorId) {
		this.editorId = editorId;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public Boolean getAttachments() {
		return attachments;
	}

	public void setAttachments(Boolean attachments) {
		this.attachments = attachments;
	}

	public String getContentTypeId() {
		return contentTypeId;
	}

	public void setContentTypeId(String contentTypeId) {
		this.contentTypeId = contentTypeId;
	}

	public String geteTag() {
		return eTag;
	}

	public void seteTag(String eTag) {
		this.eTag = eTag;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
}