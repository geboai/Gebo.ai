/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.googledrive.handlers.impl;

import java.util.StringTokenizer;

import com.google.api.services.drive.model.Drive;
import com.google.api.services.drive.model.File;

import ai.gebo.googledrive.handlers.impl.model.GoogleDriveCustomPosition;
import ai.gebo.googledrive.handlers.impl.model.GoogleDriveCustomPosition.GoogleDriveType;
import ai.gebo.googledrive.handlers.impl.model.GoogleDriveNavigationCoordinates;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.VFilesystemReference;

/**
 * AI generated comments
 * Utility class for handling navigation and path conversion between Google Drive API objects
 * and the virtual filesystem representation used by the application.
 */
class GoogleDriveNavigationUtil {
	/** Content type identifier for Google Drive folders */
	public final static String GOOGLE_DRIVE_FOLDER_CONTENT_TYPE = "application/vnd.google-apps.folder";
	/** Prefix used to identify folder references in path strings */
	public final static String GOOGLE_DRIVE_FOLDER_PREFIX = "DRIVE_FOLDER:";
	/** Prefix used to identify file/resource references in path strings */
	public final static String GOOGLE_DRIVE_RESOURCE_PREFIX = "DRIVE_ITEM:";
	/** Separator used to delimit path elements in absolute path strings */
	public final static String PATH_SEPARATOR = "|";

	/**
	 * Converts a Google Drive File object to a PathInfo representation.
	 *
	 * @param file The Google Drive File object to convert
	 * @return A PathInfo object representing the file
	 */
	public static PathInfo toPath(File file) {
		String mimeType = file.getMimeType();
		PathInfo pathInfo = new PathInfo();
		pathInfo.folder = mimeType != null && mimeType.equals(GOOGLE_DRIVE_FOLDER_CONTENT_TYPE);
		pathInfo.name = file.getName();
		pathInfo.absolutePath = (pathInfo.folder ? GOOGLE_DRIVE_FOLDER_PREFIX : GOOGLE_DRIVE_RESOURCE_PREFIX)
				+ file.getId();
		return pathInfo;
	}

	/**
	 * Converts a Google Drive File object to a PathInfo representation with a parent path.
	 *
	 * @param parent The parent PathInfo object
	 * @param file The Google Drive File object to convert
	 * @return A PathInfo object representing the file with parent path included
	 */
	public static PathInfo toPath(PathInfo parent, File file) {
		String mimeType = file.getMimeType();
		PathInfo pathInfo = toPath(file);
		pathInfo.absolutePath = parent.absolutePath + PATH_SEPARATOR + pathInfo.absolutePath;
		return pathInfo;
	}

	/**
	 * Extracts the folder ID from a PathInfo object's absolute path.
	 * This method returns the ID of the last folder in the path.
	 *
	 * @param path The PathInfo object containing the absolute path
	 * @return The ID of the last folder in the path, or null if no folder is found
	 */
	public static String getDriveFolderId(PathInfo path) {
		StringTokenizer tokenizer = new StringTokenizer(path.absolutePath, PATH_SEPARATOR);
		String folderId = null;
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			if (token.startsWith(GOOGLE_DRIVE_FOLDER_PREFIX)) {
				folderId = token.substring(GOOGLE_DRIVE_FOLDER_PREFIX.length());
			} else
				folderId = null;
		}
		return folderId;
	}

	/**
	 * Converts a Google Drive object to a virtual filesystem root representation.
	 *
	 * @param drive The Google Drive object to convert
	 * @return A GVirtualFilesystemRoot representing the drive
	 */
	public static GVirtualFilesystemRoot toRoot(Drive drive) {
		GVirtualFilesystemRoot rootItem = new GVirtualFilesystemRoot();
		rootItem.setCode(drive.getId());
		rootItem.setAbsolutePath(drive.getId());
		rootItem.setDescription(drive.getName());
		return rootItem;
	}

	/**
	 * Converts a virtual filesystem reference to Google Drive navigation coordinates.
	 * This separates the path into root and individual browsing steps with type information.
	 *
	 * @param path The virtual filesystem reference to convert
	 * @return GoogleDriveNavigationCoordinates object containing the navigation information
	 */
	public static GoogleDriveNavigationCoordinates toCoordinates(VFilesystemReference path) {
		GoogleDriveNavigationCoordinates coordinates = new GoogleDriveNavigationCoordinates();
		if (path.root != null) {
			GoogleDriveCustomPosition position = new GoogleDriveCustomPosition();
			position.type = GoogleDriveType.DRIVE;
			position.id = path.root.getCode();
			coordinates.setRoot(path.root);
		}
		if (path.path != null && path.path.absolutePath != null) {
			StringTokenizer tokenizer = new StringTokenizer(path.path.absolutePath, PATH_SEPARATOR);
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();
				PathInfo pathInfo = new PathInfo();
				pathInfo.absolutePath = token;
				GoogleDriveCustomPosition position = new GoogleDriveCustomPosition();
				if (token.startsWith(GOOGLE_DRIVE_FOLDER_PREFIX)) {
					position.id = token.substring(GOOGLE_DRIVE_FOLDER_PREFIX.length());
					position.type = GoogleDriveType.FOLDER;
					pathInfo.folder = true;
				} else if (token.startsWith(GOOGLE_DRIVE_RESOURCE_PREFIX)) {
					position.id = token.substring(GOOGLE_DRIVE_RESOURCE_PREFIX.length());
					position.type = GoogleDriveType.RESOURCE;
				}
				coordinates.getBrowsingStepsCustom().add(position);
				coordinates.getBrowsingSteps().add(pathInfo);

			}
		}
		return coordinates;
	}
}