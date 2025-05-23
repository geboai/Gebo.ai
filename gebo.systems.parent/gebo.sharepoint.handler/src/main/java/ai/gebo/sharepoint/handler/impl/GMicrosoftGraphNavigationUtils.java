/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.sharepoint.handler.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.microsoft.graph.models.BaseSitePage;
import com.microsoft.graph.models.Drive;
import com.microsoft.graph.models.DriveItem;
import com.microsoft.graph.models.Site;

import ai.gebo.model.virtualfs.BrowseParam;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.PathInfoMetaType;
import ai.gebo.sharepoint.handler.impl.model.MicrosoftGraphPathComponent;
import ai.gebo.sharepoint.handler.impl.model.MicrosoftGraphPathNodeType;

/**
 * AI generated comments
 * 
 * Utility class for Microsoft Graph navigation that provides methods for handling and converting
 * paths, components, and items between Microsoft Graph API objects and Gebo's virtual filesystem
 * representation. This class handles the encoding and decoding of path components and navigation
 * through SharePoint and OneDrive resources.
 */
public class GMicrosoftGraphNavigationUtils {

    /** Prefix for folder items in drives */
	public static final String DRIVE_ITEM_FOLDER_PREFIX = "DRIVE-ITEM-FOLDER:";
	
	/** Prefix for file items in drives */
	public static final String DRIVE_ITEM_PREFIX = "DRIVE-ITEM:";
	
	/** Prefix for OneDrive resources */
	static final String DRIVE_PREFIX = "ONE-DRIVE:";
	
	/** Separator character used in the hierarchy path */
	static final String HIERARCHY_SEPARATOR = "|";
	
	/** Prefix for SharePoint list resources */
	public static final String LIST_PREFIX = "LIST:";
	
	/** Prefix for SharePoint page resources */
	public static final String SHAREPOINT_PAGE_PREFIX = "SHAREPOINT-PAGE:";
	
	/** Prefix for SharePoint site resources */
	static final String SITE_PREFIX = "SHAREPOINT-SITE:";

	/**
	 * Decodes a path component string into a MicrosoftGraphPathComponent object.
	 * 
	 * @param sPathComponent The string representation of a path component
	 * @return The decoded MicrosoftGraphPathComponent
	 * @throws RuntimeException if the component path format is unknown
	 */
	static MicrosoftGraphPathComponent decodePathComponent(String sPathComponent) {
		MicrosoftGraphPathComponent pathComponent = new MicrosoftGraphPathComponent();
		if (sPathComponent.startsWith(DRIVE_ITEM_PREFIX)) {
			pathComponent.type = MicrosoftGraphPathNodeType.DRIVE_ITEM;
			pathComponent.id = removeStarter(sPathComponent, DRIVE_ITEM_PREFIX);
		} else if (sPathComponent.startsWith(DRIVE_ITEM_FOLDER_PREFIX)) {
			pathComponent.type = MicrosoftGraphPathNodeType.DRIVE_ITEM_FOLDER;
			pathComponent.id = removeStarter(sPathComponent, DRIVE_ITEM_FOLDER_PREFIX);
		} else if (sPathComponent.startsWith(SHAREPOINT_PAGE_PREFIX)) {
			pathComponent.type = MicrosoftGraphPathNodeType.SHAREPOINT_PAGE_ITEM;
			pathComponent.id = removeStarter(sPathComponent, SHAREPOINT_PAGE_PREFIX);
		} else if (sPathComponent.startsWith(DRIVE_PREFIX)) {
			pathComponent.type = MicrosoftGraphPathNodeType.DRIVE;
			pathComponent.id = removeStarter(sPathComponent, DRIVE_PREFIX);
		} else
			throw new RuntimeException("Unknown component path:" + sPathComponent);
		return pathComponent;
	}

	/**
	 * Finds the last path component of the specified type in the path list.
	 * 
	 * @param paths List of path components to search
	 * @param type The type of component to find
	 * @return The last path component matching the type
	 * @throws RuntimeException if no component of the specified type is found
	 */
	static MicrosoftGraphPathComponent findLast(List<MicrosoftGraphPathComponent> paths,
			MicrosoftGraphPathNodeType type) {
		for (int i = paths.size() - 1; i >= 0; i--) {
			MicrosoftGraphPathComponent pathItem = paths.get(i);
			if (pathItem.type == type)
				return pathItem;
		}
		throw new RuntimeException("Not found node of type=>" + type.name());
	}

	/**
	 * Extracts the drive ID from a virtual filesystem root.
	 * 
	 * @param root The virtual filesystem root
	 * @return The drive ID
	 */
	static String getDriveId(GVirtualFilesystemRoot root) {
		return root.getCode().substring(DRIVE_PREFIX.length());
	}

	/**
	 * Gets the last path component from a list of path components.
	 * 
	 * @param paths List of path components
	 * @return The last path component
	 */
	static MicrosoftGraphPathComponent getLast(List<MicrosoftGraphPathComponent> paths) {
		return paths.get(paths.size() - 1);
	}

	/**
	 * Extracts the site ID from a virtual filesystem root.
	 * 
	 * @param root The virtual filesystem root
	 * @return The site ID
	 */
	static String getSiteId(GVirtualFilesystemRoot root) {
		return root.getCode().substring(SITE_PREFIX.length());
	}

	/**
	 * Checks if the root represents a drive.
	 * 
	 * @param root The virtual filesystem root to check
	 * @return true if the root is a drive, false otherwise
	 */
	static boolean isDrive(GVirtualFilesystemRoot root) {
		return root != null && root.getCode() != null && root.getCode().startsWith(DRIVE_PREFIX);
	}

	/**
	 * Checks if the root represents a site.
	 * 
	 * @param root The virtual filesystem root to check
	 * @return true if the root is a site, false otherwise
	 */
	static boolean isSite(GVirtualFilesystemRoot root) {
		return root != null && root.getCode() != null && root.getCode().startsWith(SITE_PREFIX);
	}

	/**
	 * Converts a PathInfo object into a list of MicrosoftGraphPathComponent objects.
	 * 
	 * @param pathInfo The PathInfo to convert
	 * @return List of path components
	 */
	static List<MicrosoftGraphPathComponent> pathComponents(PathInfo pathInfo) {
		List<MicrosoftGraphPathComponent> out = new ArrayList<MicrosoftGraphPathComponent>();
		List<String> sComponents = pathStrings(pathInfo);
		for (String sPathComponent : sComponents) {
			MicrosoftGraphPathComponent pathComponent = decodePathComponent(sPathComponent);
			out.add(pathComponent);
		}
		return out;
	}

	/**
	 * Extracts the string components from a PathInfo object.
	 * 
	 * @param pathInfo The PathInfo to extract from
	 * @return List of path component strings
	 */
	static List<String> pathStrings(PathInfo pathInfo) {
		StringTokenizer tokenizer = new StringTokenizer(pathInfo.absolutePath, HIERARCHY_SEPARATOR);
		String lastToken = null;
		List<String> list = new ArrayList<String>();
		while (tokenizer.hasMoreTokens()) {
			lastToken = tokenizer.nextToken();
			list.add(lastToken);
		}
		return list;
	}

	/**
	 * Removes a prefix from a string.
	 * 
	 * @param sPathComponent The string to modify
	 * @param prefix The prefix to remove
	 * @return The string without the prefix
	 */
	static String removeStarter(String sPathComponent, String prefix) {
		return sPathComponent.substring(prefix.length());
	}

	/**
	 * Converts a BaseSitePage to a PathInfo object.
	 * 
	 * @param item The BaseSitePage to convert
	 * @return The resulting PathInfo
	 */
	static PathInfo toPathInfo(BaseSitePage item) {
		PathInfo path = new PathInfo();
		path.absolutePath = SHAREPOINT_PAGE_PREFIX + item.getId();
		path.folder = false;
		path.metaType = PathInfoMetaType.WEB_PAGE;
		path.name = (item.getTitle() != null ? item.getTitle()
				: item.getName() != null ? item.getName() : item.getDescription());
		return path;
	}

	/**
	 * Converts a DriveItem to a PathInfo object with browse context.
	 * 
	 * @param param The BrowseParam containing current path context
	 * @param driveItem The DriveItem to convert
	 * @return The resulting PathInfo
	 */
	static PathInfo toPathInfo(BrowseParam param, DriveItem driveItem) {
		PathInfo pathSegment = toPathInfo(driveItem);
		PathInfo previusPath = param.path;
		pathSegment.absolutePath = previusPath.absolutePath + HIERARCHY_SEPARATOR + pathSegment.absolutePath;
		return pathSegment;
	}

	/**
	 * Converts a DriveItem to a PathInfo object.
	 * 
	 * @param driveItem The DriveItem to convert
	 * @return The resulting PathInfo
	 */
	static PathInfo toPathInfo(DriveItem driveItem) {
		PathInfo path = new PathInfo();
		path.folder = driveItem.getFolder() != null;
		if (path.folder) {
			path.absolutePath = DRIVE_ITEM_FOLDER_PREFIX + driveItem.getId();
			path.metaType = PathInfoMetaType.FOLDER;
		} else {
			path.absolutePath = DRIVE_ITEM_PREFIX + driveItem.getId();
			path.metaType = PathInfoMetaType.FILE;
		}
		path.name = driveItem.getName() != null ? driveItem.getName() : driveItem.getDescription();
		return path;
	}

	/**
	 * Converts a Drive to a GVirtualFilesystemRoot object.
	 * 
	 * @param item The Drive to convert
	 * @return The resulting GVirtualFilesystemRoot
	 */
	static GVirtualFilesystemRoot toRoot(Drive item) {
		GVirtualFilesystemRoot root = new GVirtualFilesystemRoot();
		root.setCode(DRIVE_PREFIX + item.getId());
		String description = item.getName() != null ? item.getName() : item.getDescription();
		root.setDescription("OneDrive: " + (description != null ? description : "Drive"));
		root.setAbsolutePath(item.getWebUrl());
		return root;
	}

	/**
	 * Converts a Drive to a PathInfo object with root context.
	 * 
	 * @param root The virtual filesystem root
	 * @param drive The Drive to convert
	 * @return The resulting PathInfo
	 */
	static PathInfo toPathInfo(GVirtualFilesystemRoot root, Drive drive) {
		PathInfo path = new PathInfo();
		path.absolutePath = DRIVE_PREFIX + drive.getId();
		path.metaType = PathInfoMetaType.DEVICE;
		path.name = drive.getName();
		path.folder = true;
		return path;
	}

	/**
	 * Converts a Site to a GVirtualFilesystemRoot object.
	 * 
	 * @param item The Site to convert
	 * @return The resulting GVirtualFilesystemRoot
	 */
	static GVirtualFilesystemRoot toRoot(Site item) {
		GVirtualFilesystemRoot root = new GVirtualFilesystemRoot();
		root.setCode(SITE_PREFIX + item.getId());
		String description = item.getDisplayName() != null ? item.getDisplayName()
				: item.getName() != null ? item.getName() : item.getDescription();
		root.setDescription("Sharepoint site: " + (description != null ? description : "Site") + " "
				+ (item.getWebUrl() != null ? item.getWebUrl() : ""));
		root.setAbsolutePath(item.getWebUrl());
		return root;
	}
}