/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

/**
 * AI generated comments
 * This class represents a native position object in Confluence, extending AbstractNativePositionObject.
 * It encapsulates various Confluence objects such as spaces, pages, and attachments for both Cloud
 * and On-Premise Confluence installations.
 */
package ai.gebo.atlassian.confluence.handler.impl.model;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.HashMap;

import ai.gebo.atlassian.confluence.cloud.model.CloudConfluenceAttachmentItem;
import ai.gebo.atlassian.confluence.cloud.model.CloudConfluenceContent;
import ai.gebo.atlassian.confluence.cloud.model.CloudConfluenceListItem;
import ai.gebo.atlassian.confluence.cloud.model.CloudConfluenceSpacesListItem;
import ai.gebo.atlassian.confluence.handler.ConfluenceVersion;
import ai.gebo.atlassian.confluence.onpremise.model.OnPremiseConfluenceAttachmentItem;
import ai.gebo.atlassian.confluence.onpremise.model.OnPremiseConfluenceContent;
import ai.gebo.atlassian.confluence.onpremise.model.OnPremiseConfluenceListItem;
import ai.gebo.atlassian.confluence.onpremise.model.OnPremiseConfluenceSpacesListItem;
import ai.gebo.systems.abstraction.layer.model.AbstractNativePositionObject;

public class ConfluenceNativePositionObject extends AbstractNativePositionObject {

	// Basic object properties
	private String code = null;
	private String name = null;
	private String url = null;
	private boolean folder = false;
	private HashMap<String, Object> resourceReferenceMetaInfos = new HashMap<String, Object>();
	private String resourceContentType = null;
	private Date resourceModificationTime;
	private boolean resource;
	private Long resourceFileSize = null;
	
	// Cloud Confluence object references
	private CloudConfluenceListItem cloudConfluenceItemChildrens = null;
	private CloudConfluenceListItem cloudConfluencePage = null;
	private CloudConfluenceAttachmentItem cloudConfluenceAttachment = null;
	private CloudConfluenceSpacesListItem cloudConfluenceSpace = null;
	
	// On-Premise Confluence object references
	private OnPremiseConfluenceListItem onPremiseConfluenceListItemChildrens = null;
	private OnPremiseConfluenceListItem onPremiseConfluencePage = null;
	private OnPremiseConfluenceAttachmentItem onPremiseConfluenceAttachment = null;
	private OnPremiseConfluenceSpacesListItem onPremiseConfluenceSpace = null;
	
	// Metadata constants used to store information about Confluence objects
	public static final String CONFLUENCE_ATTACHMENT_TITLE_METAINFO = "CONFLUENCE_ATTACHMENT_TITLE_METAINFO";
	public static final String CONFLUENCE_BROWSER_CACHE_ENTRY = "confluenceBrowser";
	public static final String CONFLUENCE_ATTACHMENT_TYPE_METAINFO = "CONFLUENCE_ATTACHMENT_TYPE_METAINFO";
	public static final String CONFLUENCE_ATTACHMENT_ID_METAINFO = "CONFLUENCE_ATTACHMENT_ID_METAINFO";
	public static final String CONFLUENCE_CONTENT_TYPE_METAINFO = "CONFLUENCE_CONTENT_TYPE_METAINFO";
	public static final String CONFLUENCE_CONTENT_ID_METAINFO = "CONFLUENCE_CONTENT_ID_METAINFO";
	public static final String CONFLUENCE_CONTENT_PARENT_METAINFO = "CONFLUENCE_CONTENT_PARENT_METAINFO";
	public static final String CONFLUENCE_OBJECT_TYPE_METAINFO = "CONFLUENCE_OBJECT_TYPE_METAINFO";
	public static final String CONFLUENCE_VERSION_METAINFO = "CONFLUENCE_VERSION_METAINFO";
	public static final String CONFLUENCE_SPACE_ID_METAINFO = "CONFLUENCE_SPACE_ID_METAINFO";
	public static final String CONFLUENCE_RESOURCE_METAINFO = "CONFLUENCE_RESOURCE_METAINFO";
	public static final String CONFLUENCE_RESOURCE_METAINFO_PAGE = "PAGE";
	public static final String CONFLUENCE_RESOURCE_METAINFO_ATTACHMENT = "ATTACHMENT";

	/**
	 * Converts an OffsetDateTime to a Date object
	 * 
	 * @param lastModifiedDateTime The OffsetDateTime to convert
	 * @return A Date object or null if the input is null
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
	 * Gets the unique identifier of this Confluence object
	 * 
	 * @return The code/ID of the Confluence object
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Gets the display name of this Confluence object
	 * 
	 * @return The name of the Confluence object
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the URL to this Confluence object
	 * 
	 * @return The URL of the Confluence object
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Determines if this object is a resource (file, page) rather than a container
	 * 
	 * @return true if this is a resource, false otherwise
	 */
	@Override
	public boolean isResource() {
		return this.resource;
	}

	/**
	 * Determines if this object is a folder/container that can contain other resources
	 * 
	 * @return true if this is a folder, false otherwise
	 */
	@Override
	public boolean isFolder() {
		return this.folder;
	}

	/**
	 * Gets the metadata map for this Confluence object
	 * 
	 * @return HashMap containing all metadata key-value pairs
	 */
	@Override
	public HashMap<String, Object> getResourceReferenceMetaInfos() {
		return this.resourceReferenceMetaInfos;
	}

	/**
	 * Gets the content type of this resource (e.g., "text/html")
	 * 
	 * @return The content type string
	 */
	@Override
	public String getResourceContentType() {
		return this.resourceContentType;
	}

	/**
	 * Gets the last modification time of this resource
	 * 
	 * @return The modification Date
	 */
	@Override
	public Date getResourceModificationTime() {
		return this.resourceModificationTime;
	}

	/**
	 * Gets the Cloud Confluence children for this object
	 * 
	 * @return CloudConfluenceListItem representing children
	 */
	public CloudConfluenceListItem getCloudConfluenceItemChildrens() {
		return cloudConfluenceItemChildrens;
	}

	/**
	 * Sets this object as a Cloud Confluence container with children
	 * 
	 * @param cloudConfluenceItemChildrens The Cloud Confluence children
	 */
	public void setCloudConfluenceItemChildrens(CloudConfluenceListItem cloudConfluenceItemChildrens) {
		this.cloudConfluenceItemChildrens = cloudConfluenceItemChildrens;
		this.folder = true;
		this.resource = false;
		this.code = this.cloudConfluenceItemChildrens.getId();
		this.name = this.cloudConfluenceItemChildrens.getTitle();
		this.resourceContentType = "text/html";
		this.resourceReferenceMetaInfos.put(CONFLUENCE_VERSION_METAINFO, ConfluenceVersion.CLOUD.name());
		this.resourceReferenceMetaInfos.put(CONFLUENCE_CONTENT_PARENT_METAINFO, cloudConfluenceItemChildrens.getId());
	}

	/**
	 * Gets the On-Premise Confluence children for this object
	 * 
	 * @return OnPremiseConfluenceListItem representing children
	 */
	public OnPremiseConfluenceListItem getOnPremiseConfluenceListItemChildrens() {
		return onPremiseConfluenceListItemChildrens;
	}

	/**
	 * Sets this object as an On-Premise Confluence container with children
	 * 
	 * @param onPremiseConfluenceListItemChildrens The On-Premise Confluence children
	 */
	public void setOnPremiseConfluenceListItemChildrens(
			OnPremiseConfluenceListItem onPremiseConfluenceListItemChildrens) {
		this.onPremiseConfluenceListItemChildrens = onPremiseConfluenceListItemChildrens;
		this.folder = true;
		this.resource = false;
		this.code = this.onPremiseConfluenceListItemChildrens.getId();
		this.name = this.onPremiseConfluenceListItemChildrens.getTitle();
		this.resourceContentType = null;
		this.resourceReferenceMetaInfos.put(CONFLUENCE_VERSION_METAINFO, ConfluenceVersion.ONPREMISE7X.name());
		this.resourceReferenceMetaInfos.put(CONFLUENCE_CONTENT_PARENT_METAINFO,
				onPremiseConfluenceListItemChildrens.getId());
	}

	/**
	 * Gets the Cloud Confluence page for this object
	 * 
	 * @return CloudConfluenceListItem representing the page
	 */
	public CloudConfluenceListItem getCloudConfluencePage() {
		return cloudConfluencePage;
	}

	/**
	 * Sets this object as a Cloud Confluence page
	 * 
	 * @param cloudConfluencePage The Cloud Confluence page
	 */
	public void setCloudConfluencePage(CloudConfluenceListItem cloudConfluencePage) {
		this.cloudConfluencePage = cloudConfluencePage;
		this.folder = false;
		this.resource = true;
		this.code = this.cloudConfluencePage.getId();
		this.name = this.cloudConfluencePage.getTitle();
		this.resourceContentType = "text/html";
		this.resourceReferenceMetaInfos.put(CONFLUENCE_RESOURCE_METAINFO, CONFLUENCE_RESOURCE_METAINFO_PAGE);
		this.resourceReferenceMetaInfos.put(CONFLUENCE_VERSION_METAINFO, ConfluenceVersion.CLOUD.name());
		this.resourceReferenceMetaInfos.put(CONFLUENCE_CONTENT_ID_METAINFO, cloudConfluencePage.getId());
		if (cloudConfluencePage.getVersion() != null) {
			this.resourceModificationTime = toDate(cloudConfluencePage.getVersion().getWhen());
		}
		if (cloudConfluencePage.get_links() != null) {
			this.url = cloudConfluencePage.get_links().getSelf();
		}
	}

	/**
	 * Gets the On-Premise Confluence page for this object
	 * 
	 * @return OnPremiseConfluenceListItem representing the page
	 */
	public OnPremiseConfluenceListItem getOnPremiseConfluencePage() {
		return onPremiseConfluencePage;
	}

	/**
	 * Sets this object as an On-Premise Confluence page
	 * 
	 * @param onPremiseConfluencePage The On-Premise Confluence page
	 */
	public void setOnPremiseConfluencePage(OnPremiseConfluenceListItem onPremiseConfluencePage) {
		this.onPremiseConfluencePage = onPremiseConfluencePage;
		this.folder = false;
		this.resource = true;
		this.code = this.onPremiseConfluencePage.getId();
		this.name = this.onPremiseConfluencePage.getTitle();
		this.resourceContentType = "text/html";
		this.resourceReferenceMetaInfos.put(CONFLUENCE_RESOURCE_METAINFO, CONFLUENCE_RESOURCE_METAINFO_PAGE);
		this.resourceReferenceMetaInfos.put(CONFLUENCE_VERSION_METAINFO, ConfluenceVersion.ONPREMISE7X.name());
		this.resourceReferenceMetaInfos.put(CONFLUENCE_CONTENT_ID_METAINFO, cloudConfluenceAttachment.getId());
		if (this.onPremiseConfluencePage.getVersion() != null) {
			this.resourceModificationTime = toDate(this.onPremiseConfluencePage.getVersion().getWhen());
		}
		if (onPremiseConfluencePage.get_links() != null) {
			this.url = onPremiseConfluencePage.get_links().getSelf();
		}
	}

	/**
	 * Gets the Cloud Confluence attachment for this object
	 * 
	 * @return CloudConfluenceAttachmentItem
	 */
	public CloudConfluenceAttachmentItem getCloudConfluenceAttachment() {
		return cloudConfluenceAttachment;
	}

	/**
	 * Sets this object as a Cloud Confluence attachment
	 * 
	 * @param cloudConfluenceAttachment The Cloud Confluence attachment
	 */
	public void setCloudConfluenceAttachment(CloudConfluenceAttachmentItem cloudConfluenceAttachment) {
		this.cloudConfluenceAttachment = cloudConfluenceAttachment;
		this.resourceReferenceMetaInfos.put(CONFLUENCE_RESOURCE_METAINFO, CONFLUENCE_RESOURCE_METAINFO_ATTACHMENT);
		this.resourceReferenceMetaInfos.put(CONFLUENCE_VERSION_METAINFO, ConfluenceVersion.CLOUD.name());
		this.resourceReferenceMetaInfos.put(CONFLUENCE_ATTACHMENT_ID_METAINFO, cloudConfluenceAttachment.getId());
		this.resourceReferenceMetaInfos.put(CONFLUENCE_ATTACHMENT_TITLE_METAINFO, cloudConfluenceAttachment.getTitle());
		this.folder = false;
		this.resource = true;
		this.code = this.cloudConfluenceAttachment.getId();
		this.name = this.cloudConfluenceAttachment.getTitle();
		if (this.cloudConfluenceAttachment.getExtensions() != null)
			this.resourceFileSize = this.cloudConfluenceAttachment.getExtensions().getFileSize();
		this.resourceContentType = this.cloudConfluenceAttachment != null
				? this.cloudConfluenceAttachment.getMetadata() != null
						? this.cloudConfluenceAttachment.getMetadata().getMediaType()
						: null
				: null;
		if (this.cloudConfluenceAttachment.getVersion() != null) {
			this.resourceModificationTime = toDate(this.cloudConfluenceAttachment.getVersion().getWhen());
		}
		if (cloudConfluenceAttachment.get_links() != null) {
			this.url = cloudConfluenceAttachment.get_links().getSelf();
		}
	}

	/**
	 * Gets the On-Premise Confluence attachment for this object
	 * 
	 * @return OnPremiseConfluenceAttachmentItem
	 */
	public OnPremiseConfluenceAttachmentItem getOnPremiseConfluenceAttachment() {
		return onPremiseConfluenceAttachment;
	}

	/**
	 * Sets this object as an On-Premise Confluence attachment
	 * 
	 * @param onPremiseConfluenceAttachment The On-Premise Confluence attachment
	 */
	public void setOnPremiseConfluenceAttachment(OnPremiseConfluenceAttachmentItem onPremiseConfluenceAttachment) {
		this.onPremiseConfluenceAttachment = onPremiseConfluenceAttachment;
		this.resourceReferenceMetaInfos.put(CONFLUENCE_RESOURCE_METAINFO, CONFLUENCE_RESOURCE_METAINFO_ATTACHMENT);
		this.resourceReferenceMetaInfos.put(CONFLUENCE_ATTACHMENT_ID_METAINFO, onPremiseConfluenceAttachment.getId());
		this.resourceReferenceMetaInfos.put(CONFLUENCE_ATTACHMENT_TITLE_METAINFO,
				onPremiseConfluenceAttachment.getTitle());
		this.resourceReferenceMetaInfos.put(CONFLUENCE_VERSION_METAINFO, ConfluenceVersion.ONPREMISE7X.name());
		this.folder = false;
		this.resource = true;
		this.code = this.onPremiseConfluenceAttachment.getId();
		this.name = this.onPremiseConfluenceAttachment.getTitle();
		if (onPremiseConfluenceAttachment.getVersion() != null
				&& onPremiseConfluenceAttachment.getVersion().getWhen() != null) {
			this.resourceModificationTime = toDate(onPremiseConfluenceAttachment.getVersion().getWhen());
		}
		if (onPremiseConfluenceAttachment.get_links() != null) {
			this.url = onPremiseConfluenceAttachment.get_links().getSelf();
		}
		if (onPremiseConfluenceAttachment.getExtensions() != null)
			this.resourceFileSize = onPremiseConfluenceAttachment.getExtensions().getFileSize();
		this.resourceContentType = this.onPremiseConfluenceAttachment != null
				? this.onPremiseConfluenceAttachment.getMetadata() != null
						? this.onPremiseConfluenceAttachment.getMetadata().getMediaType()
						: null
				: null;
		if (this.onPremiseConfluenceAttachment.getVersion() != null) {
			this.resourceModificationTime = toDate(this.onPremiseConfluenceAttachment.getVersion().getWhen());
		}

	}

	/**
	 * Converts a timestamp string to a Date object
	 * 
	 * @param when ISO 8601 timestamp string
	 * @return Date object or null if conversion fails
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
	 * Sets whether this object is a folder
	 * 
	 * @param folder true if this is a folder, false otherwise
	 */
	public void setFolder(boolean folder) {
		this.folder = folder;
	}

	/**
	 * Sets the metadata map for this object
	 * 
	 * @param resourceReferenceMetaInfos The metadata map
	 */
	public void setResourceReferenceMetaInfos(HashMap<String, Object> resourceReferenceMetaInfos) {
		this.resourceReferenceMetaInfos = resourceReferenceMetaInfos;
	}

	/**
	 * Sets the content type of this resource
	 * 
	 * @param resourceContentType The content type string
	 */
	public void setResourceContentType(String resourceContentType) {
		this.resourceContentType = resourceContentType;
	}

	/**
	 * Sets the modification time of this resource
	 * 
	 * @param resourceModificationTime The modification Date
	 */
	public void setResourceModificationTime(Date resourceModificationTime) {
		this.resourceModificationTime = resourceModificationTime;
	}

	/**
	 * Sets whether this object is a resource
	 * 
	 * @param resource true if this is a resource, false otherwise
	 */
	public void setResource(boolean resource) {
		this.resource = resource;
	}

	/**
	 * Gets the Cloud Confluence space for this object
	 * 
	 * @return CloudConfluenceSpacesListItem
	 */
	public CloudConfluenceSpacesListItem getCloudConfluenceSpace() {
		return cloudConfluenceSpace;
	}

	/**
	 * Sets this object as a Cloud Confluence space
	 * 
	 * @param cloudConfluenceSpace The Cloud Confluence space
	 */
	public void setCloudConfluenceSpace(CloudConfluenceSpacesListItem cloudConfluenceSpace) {
		this.cloudConfluenceSpace = cloudConfluenceSpace;
		this.folder = true;
		this.resource = false;
		this.code = this.cloudConfluenceSpace.getKey() != null ? this.cloudConfluenceSpace.getKey()
				: this.cloudConfluenceSpace.getId();
		this.name = this.cloudConfluenceSpace.getName() != null ? this.cloudConfluenceSpace.getName()
				: this.cloudConfluenceSpace.getTitle();
		if (this.cloudConfluenceSpace != null && this.cloudConfluenceSpace.get_links() != null)
			this.url = this.cloudConfluenceSpace.get_links().getWebui();
		this.resourceReferenceMetaInfos.put(CONFLUENCE_VERSION_METAINFO, ConfluenceVersion.CLOUD.name());
		this.resourceReferenceMetaInfos.put(CONFLUENCE_SPACE_ID_METAINFO, this.code);
	}

	/**
	 * Determines if this object is a Confluence space
	 * 
	 * @return true if this is a Confluence space, false otherwise
	 */
	public boolean isConfluenceSpace() {
		return this.cloudConfluenceSpace != null || this.onPremiseConfluenceSpace != null;
	}

	/**
	 * Determines if this object is a Confluence page
	 * 
	 * @return true if this is a Confluence page, false otherwise
	 */
	public boolean isConfluencePage() {
		return this.cloudConfluencePage != null || this.onPremiseConfluencePage != null;
	}

	/**
	 * Determines if this object is a Confluence super page (container for other pages)
	 * 
	 * @return true if this is a Confluence super page, false otherwise
	 */
	public boolean isConfluenceSuperPage() {
		return this.cloudConfluenceItemChildrens != null || this.onPremiseConfluenceListItemChildrens != null;
	}

	/**
	 * Determines if this object is a Confluence attachment
	 * 
	 * @return true if this is a Confluence attachment, false otherwise
	 */
	public boolean isConfluenceAttachment() {
		return this.cloudConfluenceAttachment != null || this.onPremiseConfluenceAttachment != null;
	}

	/**
	 * Gets the On-Premise Confluence space for this object
	 * 
	 * @return OnPremiseConfluenceSpacesListItem
	 */
	public OnPremiseConfluenceSpacesListItem getOnPremiseConfluenceSpace() {
		return onPremiseConfluenceSpace;
	}

	/**
	 * Sets this object as an On-Premise Confluence space
	 * 
	 * @param onPremiseConfluenceSpace The On-Premise Confluence space
	 */
	public void setOnPremiseConfluenceSpace(OnPremiseConfluenceSpacesListItem onPremiseConfluenceSpace) {
		this.onPremiseConfluenceSpace = onPremiseConfluenceSpace;
		this.folder = true;
		this.resource = false;
		this.code = this.onPremiseConfluenceSpace.getKey() != null ? this.onPremiseConfluenceSpace.getKey()
				: this.onPremiseConfluenceSpace.getId();
		this.name = this.onPremiseConfluenceSpace.getName() != null ? this.onPremiseConfluenceSpace.getName()
				: this.onPremiseConfluenceSpace.getTitle();
		if (this.onPremiseConfluenceSpace != null && this.onPremiseConfluenceSpace.get_links() != null)
			this.url = this.onPremiseConfluenceSpace.get_links().getWebui();
		this.resourceReferenceMetaInfos.put(CONFLUENCE_VERSION_METAINFO, ConfluenceVersion.ONPREMISE7X.name());
		this.resourceReferenceMetaInfos.put(CONFLUENCE_SPACE_ID_METAINFO, this.code);
	}

	/**
	 * Sets this object from a Cloud Confluence content page
	 * 
	 * @param page The Cloud Confluence content page
	 */
	public void setCloudConfluenceContent(CloudConfluenceContent page) {
		this.cloudConfluencePage = new CloudConfluenceListItem();
		this.cloudConfluencePage.setId(page.getId());
		this.cloudConfluencePage.setTitle(page.getTitle());
		this.cloudConfluencePage.setType(page.getType());
		this.cloudConfluencePage.setStatus(page.getStatus());
		this.code = page.getId();
		this.name = page.getTitle();
		this.resource = true;
		this.folder = false;
		this.resourceReferenceMetaInfos.put(CONFLUENCE_RESOURCE_METAINFO, CONFLUENCE_RESOURCE_METAINFO_PAGE);
		this.resourceReferenceMetaInfos.put(CONFLUENCE_VERSION_METAINFO, ConfluenceVersion.CLOUD.name());
		this.resourceReferenceMetaInfos.put(CONFLUENCE_CONTENT_ID_METAINFO, cloudConfluencePage.getId());
		this.setCloudConfluencePage(this.cloudConfluencePage);
		if (page.getVersion() != null && page.getVersion().getWhen() != null) {
			this.resourceModificationTime = toDate(page.getVersion().getWhen());
		}
		if (page.get_links() != null) {
			this.url = page.get_links().getSelf();
		}
	}

	/**
	 * Sets this object from an On-Premise Confluence content page
	 * 
	 * @param page The On-Premise Confluence content page
	 */
	public void setOnPremiseConfluenceContent(OnPremiseConfluenceContent page) {
		this.code = page.getId();
		this.name = page.getTitle();
		this.resource = true;
		this.folder = false;
		this.onPremiseConfluencePage = new OnPremiseConfluenceListItem();
		this.onPremiseConfluencePage.setId(page.getId());
		this.onPremiseConfluencePage.setTitle(page.getTitle());
		this.onPremiseConfluencePage.setType(page.getType());
		this.onPremiseConfluencePage.setStatus(page.getStatus());
		this.resourceReferenceMetaInfos.put(CONFLUENCE_RESOURCE_METAINFO, CONFLUENCE_RESOURCE_METAINFO_PAGE);
		this.resourceReferenceMetaInfos.put(CONFLUENCE_VERSION_METAINFO, ConfluenceVersion.ONPREMISE7X.name());
		this.resourceReferenceMetaInfos.put(CONFLUENCE_CONTENT_ID_METAINFO, onPremiseConfluencePage.getId());
		this.setOnPremiseConfluencePage(this.onPremiseConfluencePage);
		if (page.getVersion() != null && page.getVersion().getWhen() != null) {
			this.resourceModificationTime = toDate(page.getVersion().getWhen());
		}
		if (page.get_links() != null) {
			this.url = page.get_links().getSelf();
		}
	}

	/**
	 * Sets this object as a container from a Cloud Confluence content page
	 * 
	 * @param page The Cloud Confluence content page
	 */
	public void setCloudConfluenceContentAsContainer(CloudConfluenceContent page) {
		this.code = page.getId();
		this.name = page.getTitle();
		this.resource = false;
		this.folder = true;
		this.cloudConfluenceItemChildrens = new CloudConfluenceListItem();
		this.cloudConfluenceItemChildrens.setId(page.getId());
		this.cloudConfluenceItemChildrens.setTitle(page.getTitle());
		this.cloudConfluenceItemChildrens.setType(page.getType());
		this.cloudConfluenceItemChildrens.setStatus(page.getStatus());
		if (page.getVersion() != null && page.getVersion().getWhen() != null) {
			this.resourceModificationTime = toDate(page.getVersion().getWhen());
		}
		if (page.get_links() != null) {
			this.url = page.get_links().getSelf();
		}
		this.resourceReferenceMetaInfos.put(CONFLUENCE_VERSION_METAINFO, ConfluenceVersion.CLOUD.name());
		this.resourceReferenceMetaInfos.put(CONFLUENCE_CONTENT_PARENT_METAINFO, cloudConfluenceItemChildrens.getId());
	}

	/**
	 * Sets this object as a container from an On-Premise Confluence content page
	 * 
	 * @param page The On-Premise Confluence content page
	 */
	public void setOnPremiseConfluenceContentAsContainer(OnPremiseConfluenceContent page) {
		this.code = page.getId();
		this.name = page.getTitle();
		this.resource = false;
		this.folder = true;
		this.onPremiseConfluenceListItemChildrens = new OnPremiseConfluenceListItem();
		this.onPremiseConfluenceListItemChildrens.setId(page.getId());
		this.onPremiseConfluenceListItemChildrens.setTitle(page.getTitle());
		this.onPremiseConfluenceListItemChildrens.setType(page.getType());
		this.onPremiseConfluenceListItemChildrens.setStatus(page.getStatus());

		if (page.getVersion() != null && page.getVersion().getWhen() != null) {
			this.resourceModificationTime = toDate(page.getVersion().getWhen());
		}
		this.resourceReferenceMetaInfos.put(CONFLUENCE_VERSION_METAINFO, ConfluenceVersion.ONPREMISE7X.name());
		this.resourceReferenceMetaInfos.put(CONFLUENCE_CONTENT_PARENT_METAINFO,
				onPremiseConfluenceListItemChildrens.getId());
	}

	/**
	 * Gets the file size of the resource in bytes
	 * 
	 * @return The file size or null if not available
	 */
	public Long getResourceFileSize() {
		return resourceFileSize;
	}
}