/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.googledrive.handlers.impl.model;

import java.util.Date;
import java.util.HashMap;

import com.google.api.services.drive.model.Drive;
import com.google.api.services.drive.model.File;

import ai.gebo.systems.abstraction.layer.model.AbstractNativePositionObject;

/**
 * AI generated comments
 * 
 * GoogleDriveNativePositionObject represents an object in Google Drive, 
 * which can be a drive, folder, or file resource. It extends AbstractNativePositionObject
 * to provide specific implementation for Google Drive objects.
 */
public class GoogleDriveNativePositionObject extends AbstractNativePositionObject {
	/** Constant for Google resource ID key in meta infos */
	public static final String GOOGLE_RESOURCE_ID = "GOOGLE_RESOURCE_ID";
	/** Constant for Google folder ID key in meta infos */
	public static final String GOOGLE_FOLDER_ID = "GOOGLE_FOLDER_ID";
	/** Constant for Google drive ID key in meta infos */
	public static final String GOOGLE_DRIVE_ID = "GOOGLE_DRIVE_ID";
	/** Reference to the Drive object */
	private Drive drive = null;
	/** Reference to the file resource */
	private File resourceFile = null;
	/** Reference to the folder file */
	private File folderFile = null;
	/** Flag indicating if this object is a resource */
	private boolean resource = false;
	/** Flag indicating if this object is a folder */
	private boolean folder = false;
	/** The ID code of the object */
	private String code = null;
	/** The name of the object */
	private String name = null;
	/** The URL of the object */
	private String url = null;
	/** The content type of the resource */
	private String resourceContentType = null;
	/** Map of metadata information for the resource reference */
	private HashMap<String, Object> resourceReferenceMetaInfos = new HashMap<>();
	/** Time when the resource was last modified */
	private Date modificationTime = null;
	/** Size of the resource file in bytes */
	private Long resourceFileSize = null;

	/**
	 * Returns the ID code of this object.
	 * @return The object's code
	 */
	@Override
	public String getCode() {
		return code;
	}

	/**
	 * Returns the name of this object.
	 * @return The object's name
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Returns the URL of this object.
	 * @return The object's URL
	 */
	@Override
	public String getUrl() {
		return url;
	}

	/**
	 * Checks if this object is a resource.
	 * @return true if the object is a resource, false otherwise
	 */
	@Override
	public boolean isResource() {
		return resource;
	}

	/**
	 * Checks if this object is a folder.
	 * @return true if the object is a folder, false otherwise
	 */
	@Override
	public boolean isFolder() {
		return folder;
	}

	/**
	 * Returns the metadata information for the resource reference.
	 * @return HashMap containing resource reference metadata
	 */
	@Override
	public HashMap<String, Object> getResourceReferenceMetaInfos() {
		return resourceReferenceMetaInfos;
	}

	/**
	 * Returns the content type of the resource.
	 * @return The resource's content type
	 */
	@Override
	public String getResourceContentType() {
		return resourceContentType;
	}

	/**
	 * Returns the last modification time of the resource.
	 * @return Date when the resource was last modified
	 */
	@Override
	public Date getResourceModificationTime() {
		return modificationTime;
	}

	/**
	 * Returns the file size of the resource.
	 * @return The resource's file size in bytes
	 */
	@Override
	public Long getResourceFileSize() {
		return resourceFileSize;
	}

	/**
	 * Sets this object as a Drive and initializes its properties.
	 * @param drive The Drive object to set
	 */
	public void setDrive(Drive drive) {
		this.drive = drive;
		this.code = drive.getId();
		this.name = drive.getName();
		this.resourceReferenceMetaInfos.put(GOOGLE_DRIVE_ID, code);
		this.folder = true;
	}

	/**
	 * Sets this object as a resource file and initializes its properties.
	 * @param resourceFile The File object representing a resource
	 */
	public void setResourceFile(File resourceFile) {
		this.resourceFile = resourceFile;
		this.code = resourceFile.getId();
		this.name = resourceFile.getName();
		if (resourceFile.getModifiedTime() != null) {
			this.modificationTime = new Date(resourceFile.getModifiedTime().getValue());
		}
		this.resourceContentType = resourceFile.getMimeType();
		this.resourceFileSize = resourceFile.getSize();
		this.resourceReferenceMetaInfos.put(GOOGLE_RESOURCE_ID, code);
		this.resource = true;
	}

	/**
	 * Sets this object as a folder file and initializes its properties.
	 * @param folderFile The File object representing a folder
	 */
	public void setFolderFile(File folderFile) {
		this.folderFile = folderFile;
		this.code = folderFile.getId();
		this.name = folderFile.getName();
		this.resourceReferenceMetaInfos.put(GOOGLE_FOLDER_ID, code);
		this.folder = true;
	}

	/**
	 * Checks if this object is a Drive.
	 * @return true if this object is a Drive, false otherwise
	 */
	public boolean isDrive() {
		return drive != null;
	}

	/**
	 * Checks if this object is a file resource.
	 * @return true if this object is a file resource, false otherwise
	 */
	public boolean isFileResource() {
		return resourceFile != null;
	}

	/**
	 * Checks if this object is a folder file.
	 * @return true if this object is a folder file, false otherwise
	 */
	public boolean isFolderFile() {
		return folderFile != null;
	}

	/**
	 * Returns the Drive object.
	 * @return The Drive object or null if not set
	 */
	public Drive getDrive() {
		return drive;
	}

	/**
	 * Returns the resource file.
	 * @return The File object representing the resource or null if not set
	 */
	public File getResourceFile() {
		return resourceFile;
	}

	/**
	 * Returns the folder file.
	 * @return The File object representing the folder or null if not set
	 */
	public File getFolderFile() {
		return folderFile;
	}
}