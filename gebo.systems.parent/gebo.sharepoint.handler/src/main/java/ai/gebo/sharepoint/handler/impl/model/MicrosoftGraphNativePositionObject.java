/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.sharepoint.handler.impl.model;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.HashMap;

import com.microsoft.graph.models.BaseItem;
import com.microsoft.graph.models.BaseSitePage;
import com.microsoft.graph.models.Drive;
import com.microsoft.graph.models.DriveItem;
import com.microsoft.graph.models.Site;

import ai.gebo.systems.abstraction.layer.model.AbstractNativePositionObject;

/**
 * AI generated comments
 * 
 * A class that represents a Microsoft Graph native position object.
 * This class extends AbstractNativePositionObject and provides functionality
 * for handling various Microsoft Graph resources like Drive, DriveItem, Site, and BaseSitePage.
 */
public class MicrosoftGraphNativePositionObject extends AbstractNativePositionObject {
	/** The base item reference */
	private BaseItem _item = null;
	/** The drive item reference */
	private DriveItem driveItem = null;
	/** The drive reference */
	private Drive drive = null;
	/** The site reference */
	private Site site = null;
	/** The SharePoint page reference */
	private BaseSitePage page = null;
	/** Flag indicating if the item is a folder */
	private boolean folder = false;
	/** Map storing metadata information about the resource */
	private HashMap<String, Object> resourceReferenceMetaInfos = new HashMap<String, Object>();
	/** The content type of the resource */
	private String resourceContentType = null;
	/** The modification time of the resource */
	private Date resourceModificationTime;
	/** Flag indicating if the item is a resource (file) */
	private boolean resource;
	/** The file size of the resource */
	private Long resourceFileSize = null;
	/** Constant for drive item reference key */
	public static final String DRIVE_ITEM_REFERENCE = "DRIVE_ITEM_REFERENCE";
	/** Constant for drive reference key */
	public static final String DRIVE_REFERENCE = "DRIVE_ID_REFERENCE";
	/** Constant for SharePoint page reference key */
	public static final String SHAREPOINT_PAGE_REFERENCE = "SHAREPOINT_PAGE_REFERENCE";
	/** Constant for SharePoint site reference key */
	public static final String SHAREPOINT_SITE_REFERENCE = "SHAREPOINT_SITE_REFERENCE";

	/**
	 * Gets the drive item.
	 * 
	 * @return The drive item
	 */
	public DriveItem getDriveItem() {
		return driveItem;
	}

	/**
	 * Sets the drive item and updates related properties.
	 * 
	 * @param driveItem The drive item to set
	 */
	public void setDriveItem(DriveItem driveItem) {
		this.driveItem = driveItem;
		this._item = driveItem;
		this.folder = driveItem.getFolder() != null;
		this.resource = driveItem.getFile() != null;
		this.resourceReferenceMetaInfos.put(DRIVE_ITEM_REFERENCE, driveItem.getId());
		if (this.resource) {
			this.resourceContentType = driveItem.getFile().getMimeType();
			this.resourceModificationTime = toDate(driveItem.getLastModifiedDateTime());
			this.resourceFileSize = driveItem.getSize();
		}
	}

	/**
	 * Converts OffsetDateTime to Date.
	 * 
	 * @param lastModifiedDateTime The OffsetDateTime to convert
	 * @return The converted Date or null if input is null
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
	 * Gets the drive.
	 * 
	 * @return The drive
	 */
	public Drive getDrive() {
		return drive;
	}

	/**
	 * Sets the drive and updates related properties.
	 * 
	 * @param drive The drive to set
	 */
	public void setDrive(Drive drive) {
		this.drive = drive;
		this._item = drive;
		this.folder = true;
		this.resourceReferenceMetaInfos.put(DRIVE_REFERENCE, drive.getId());
	}

	/**
	 * Gets the site.
	 * 
	 * @return The site
	 */
	public Site getSite() {
		return site;
	}

	/**
	 * Sets the site and updates related properties.
	 * 
	 * @param site The site to set
	 */
	public void setSite(Site site) {
		this.site = site;
		this._item = site;
		this.folder = true;
		this.resourceReferenceMetaInfos.put(SHAREPOINT_SITE_REFERENCE, site.getId());
	}

	/**
	 * Gets the SharePoint page.
	 * 
	 * @return The SharePoint page
	 */
	public BaseSitePage getPage() {
		return page;
	}

	/**
	 * Sets the SharePoint page and updates related properties.
	 * 
	 * @param page The page to set
	 */
	public void setPage(BaseSitePage page) {
		this.page = page;
		this._item = page;
		this.resource = true;
		this.resourceContentType = "text/html";
		this.resourceModificationTime = toDate(page.getLastModifiedDateTime());
		this.resourceReferenceMetaInfos.put(SHAREPOINT_PAGE_REFERENCE, page.getId());
	}

	/**
	 * Checks if the current item is a drive.
	 * 
	 * @return true if it's a drive, false otherwise
	 */
	public boolean isDrive() {
		return drive != null;
	}

	/**
	 * Checks if the current item is a drive item.
	 * 
	 * @return true if it's a drive item, false otherwise
	 */
	public boolean isDriveItem() {
		return driveItem != null;
	}

	/**
	 * Checks if the current item is a site.
	 * 
	 * @return true if it's a site, false otherwise
	 */
	public boolean isSite() {
		return site != null;
	}

	/**
	 * Checks if the current item is a SharePoint page.
	 * 
	 * @return true if it's a SharePoint page, false otherwise
	 */
	public boolean isSharepointPage() {
		return page != null;
	}

	/**
	 * Gets the code (ID) of the item.
	 * 
	 * @return The item ID or null if no item is set
	 */
	public String getCode() {
		return _item != null ? _item.getId() : null;
	}

	/**
	 * Gets the name of the item.
	 * 
	 * @return The item name, page title, or null if no item is set
	 */
	public String getName() {
		return isSharepointPage() ? page.getTitle() : _item != null ? _item.getName() : null;
	}

	/**
	 * Gets the URL of the item.
	 * 
	 * @return The web URL or null if no item is set
	 */
	public String getUrl() {
		return _item != null ? _item.getWebUrl() : null;
	}

	/**
	 * Checks if the current item is a resource (file).
	 * 
	 * @return true if it's a resource, false otherwise
	 */
	@Override
	public boolean isResource() {
		return this.resource;
	}

	/**
	 * Checks if the current item is a folder.
	 * 
	 * @return true if it's a folder, false otherwise
	 */
	@Override
	public boolean isFolder() {
		return this.folder;
	}

	/**
	 * Gets the resource reference metadata.
	 * 
	 * @return HashMap containing resource reference metadata
	 */
	@Override
	public HashMap<String, Object> getResourceReferenceMetaInfos() {
		return this.resourceReferenceMetaInfos;
	}

	/**
	 * Gets the resource content type.
	 * 
	 * @return The content type (MIME type) of the resource
	 */
	@Override
	public String getResourceContentType() {
		return this.resourceContentType;
	}

	/**
	 * Gets the resource modification time.
	 * 
	 * @return The last modification time of the resource
	 */
	@Override
	public Date getResourceModificationTime() {
		return this.resourceModificationTime;
	}

	/**
	 * Gets the resource file size.
	 * 
	 * @return The file size of the resource
	 */
	public Long getResourceFileSize() {
		return resourceFileSize;
	}
}