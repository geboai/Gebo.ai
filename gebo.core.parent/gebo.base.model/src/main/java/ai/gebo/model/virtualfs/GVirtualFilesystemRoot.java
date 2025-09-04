/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.model.virtualfs;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.ToString;

/***
 * Class that represents a virtual file system root node
 * 
 * AI generated comments
 */
@ToString
public class GVirtualFilesystemRoot {
	
	// Code associated with the virtual file system root
	private String code = null;
	
	// Description of the virtual file system root
	private String description = null;
	
	// Absolute path of the file system root
	private String absolutePath = null;
	
	// URI of the file system root
	private String uri = null;
	
	// Date when the file system root was last modified
	private Date dateModified = null;

	/**
	 * Default constructor for GVirtualFilesystemRoot
	 */
	public GVirtualFilesystemRoot() {
	}

	/**
	 * Gets the absolute path of the virtual file system root.
	 * 
	 * @return the absolute path as a String
	 */
	public String getAbsolutePath() {
		return absolutePath;
	}

	/**
	 * Sets the absolute path of the virtual file system root.
	 * 
	 * @param absolutePath the new absolute path
	 */
	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}

	/**
	 * Gets the URI of the virtual file system root.
	 * 
	 * @return the URI as a String
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * Sets the URI of the virtual file system root.
	 * 
	 * @param uri the new URI
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	/**
	 * Creates a GVirtualFilesystemRoot object from a given Path.
	 * 
	 * @param path the Path from which to create the GVirtualFilesystemRoot
	 * @return an instance of GVirtualFilesystemRoot, or null if the path has no root
	 */
	public static GVirtualFilesystemRoot from(Path path) {
		Path root = path.getRoot();
		if (root == null)
			return null;
		else {
			GVirtualFilesystemRoot rootItem = new GVirtualFilesystemRoot();
			rootItem.setAbsolutePath(root.toAbsolutePath().toString());
			rootItem.setDescription(root.toAbsolutePath().toString());
			File file = root.toFile();
			if (file != null) {
				rootItem.setDateModified(new Date(file.lastModified()));
			}
			return rootItem;
		}
	}

	/**
	 * Lists all available file system roots on the system.
	 * 
	 * @return a list of GVirtualFilesystemRoot objects representing each file system root
	 */
	public static List<GVirtualFilesystemRoot> listRoots() {
		File[] roots = File.listRoots();
		return of(roots);
	}

	/**
	 * Converts an array of File objects representing roots into a list of GVirtualFilesystemRoot objects.
	 * 
	 * @param listRoots an array of File objects representing the system roots
	 * @return a list of GVirtualFilesystemRoot objects
	 */
	public static List<GVirtualFilesystemRoot> of(File[] listRoots) {
		List<GVirtualFilesystemRoot> roots = new ArrayList<GVirtualFilesystemRoot>();
		if (listRoots != null)
			for (File file : listRoots) {
				Path path = Path.of(file.getAbsolutePath());
				GVirtualFilesystemRoot rootItem = GVirtualFilesystemRoot.from(path);
				roots.add(rootItem);
			}
		return roots;
	}

	/**
	 * Gets the date when the virtual file system root was last modified.
	 * 
	 * @return the modification date
	 */
	public Date getDateModified() {
		return dateModified;
	}

	/**
	 * Sets the date when the virtual file system root was last modified.
	 * 
	 * @param dateModified the new modification date
	 */
	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	/**
	 * Gets the code associated with the virtual file system root.
	 * 
	 * @return the code as a String
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code associated with the virtual file system root.
	 * 
	 * @param code the new code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Gets the description of the virtual file system root.
	 * 
	 * @return the description as a String
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the virtual file system root.
	 * 
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}