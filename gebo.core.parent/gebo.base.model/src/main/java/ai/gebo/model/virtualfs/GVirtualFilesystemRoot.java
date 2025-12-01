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

import lombok.Data;
import lombok.ToString;

/***
 * Class that represents a virtual file system root node
 * 
 * AI generated comments
 */
@Data
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
	private String iconKey=null;

	/**
	 * Default constructor for GVirtualFilesystemRoot
	 */
	public GVirtualFilesystemRoot() {
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


}