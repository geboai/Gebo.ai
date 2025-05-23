/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.microsoft.sharepoint.cloud.model;

// NEW: SharePointList and list responses
public class CloudSharepointList {
	/**
	 * The unique GUID of the list. Returned by the REST API as "Id" (e.g.
	 * "cce12c4a-b56b-4c88-b16c-7b178e8f3b7d").
	 */
	private String id;

	/**
	 * Display name of the list (i.e., "Title" column).
	 */
	private String title;

	/**
	 * Optional text describing the list.
	 */
	private String description;

	/**
	 * The numeric template type used to create the list (e.g., 100 = generic list,
	 * 101 = document library).
	 */
	private Integer baseTemplate;

	/**
	 * The base type categorizes the list as a generic list, library, discussion
	 * board, etc. (e.g., 0 = GenericList, 1 = DocumentLibrary).
	 */
	private Integer baseType;

	/**
	 * Whether the list can contain multiple content types (true) or a single one
	 * (false).
	 */
	private Boolean allowContentTypes;

	/**
	 * Whether the list (or library) actually has multiple content types enabled.
	 */
	private Boolean contentTypesEnabled;

	/**
	 * Date/time the list was first created. Often returned as an ISO 8601 string
	 * (e.g., "2025-02-10T10:25:45Z").
	 */
	private String created;

	/**
	 * Date/time the last item in the list was modified (or the list itself).
	 */
	private String lastItemModifiedDate;

	/**
	 * Whether the list is hidden (not visible in the UI).
	 */
	private Boolean hidden;

	/**
	 * Whether items in the list can have attachments (for lists, not typically for
	 * libraries).
	 */
	private Boolean enableAttachments;

	/**
	 * Whether the list is using versioning for items or documents.
	 */
	private Boolean enableVersioning;

	/**
	 * The total number of items currently in the list.
	 */
	private Integer itemCount;

	/**
	 * Internal name used by SharePoint for entity references (e.g.
	 * "Custom_x0020_List").
	 */
	private String entityTypeName;

	/**
	 * The relative URL to the default view (e.g.
	 * "/sites/SiteName/Lists/MyList/AllItems.aspx").
	 */
	private String defaultViewUrl;

	/**
	 * The web-relative URL (or absolute URL) of the parent web (e.g.
	 * "/sites/SiteName").
	 */
	private String parentWebUrl;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getBaseTemplate() {
		return baseTemplate;
	}

	public void setBaseTemplate(Integer baseTemplate) {
		this.baseTemplate = baseTemplate;
	}

	public Integer getBaseType() {
		return baseType;
	}

	public void setBaseType(Integer baseType) {
		this.baseType = baseType;
	}

	public Boolean getAllowContentTypes() {
		return allowContentTypes;
	}

	public void setAllowContentTypes(Boolean allowContentTypes) {
		this.allowContentTypes = allowContentTypes;
	}

	public Boolean getContentTypesEnabled() {
		return contentTypesEnabled;
	}

	public void setContentTypesEnabled(Boolean contentTypesEnabled) {
		this.contentTypesEnabled = contentTypesEnabled;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getLastItemModifiedDate() {
		return lastItemModifiedDate;
	}

	public void setLastItemModifiedDate(String lastItemModifiedDate) {
		this.lastItemModifiedDate = lastItemModifiedDate;
	}

	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	public Boolean getEnableAttachments() {
		return enableAttachments;
	}

	public void setEnableAttachments(Boolean enableAttachments) {
		this.enableAttachments = enableAttachments;
	}

	public Boolean getEnableVersioning() {
		return enableVersioning;
	}

	public void setEnableVersioning(Boolean enableVersioning) {
		this.enableVersioning = enableVersioning;
	}

	public Integer getItemCount() {
		return itemCount;
	}

	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}

	public String getEntityTypeName() {
		return entityTypeName;
	}

	public void setEntityTypeName(String entityTypeName) {
		this.entityTypeName = entityTypeName;
	}

	public String getDefaultViewUrl() {
		return defaultViewUrl;
	}

	public void setDefaultViewUrl(String defaultViewUrl) {
		this.defaultViewUrl = defaultViewUrl;
	}

	public String getParentWebUrl() {
		return parentWebUrl;
	}

	public void setParentWebUrl(String parentWebUrl) {
		this.parentWebUrl = parentWebUrl;
	}
}