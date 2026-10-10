/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.uploads.content.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A file or a folder physically present in the persistent folder of a
 * {@link GUploadsProjectEndpoint}, with the ones it contains: the tree of the
 * "Uploaded files" tab of the uploads editor.
 *
 * <p>
 * The tree comes from the filesystem, what the next publish ingests; a file is
 * {@link #published} when a not deleted document reference exists for it (for a
 * zip file: for one of the files it holds), i.e. it is already part of the
 * knowledge base. A folder tells how many files it holds and how many of them
 * are published.
 * </p>
 */
public class UploadedFileNode {
	/** Name of the file or folder. */
	public String name = null;
	/**
	 * Path relative to the data source folder, with "/" separators: what the
	 * editor addresses the entry with (upload target, deletion, view).
	 */
	public String relativePath = null;
	/** Lowercase extension including the dot, {@code null} for folders and files without one. */
	public String extension = null;
	/** True for a folder. */
	public boolean folder = false;
	/** Size in bytes of a file; the total of the files it holds for a folder. */
	public long size = 0;
	/** Last modification timestamp. */
	public Date modificationTime = null;
	/** True when a file is already part of the knowledge base. */
	public boolean published = false;
	/** Code of the document reference of a published file, {@code null} otherwise. */
	public String documentCode = null;
	/** Number of files a folder holds, at any depth; 1 for a file. */
	public int filesCount = 0;
	/** Number of those files that are published. */
	public int publishedFilesCount = 0;
	/** The entries of a folder, folders first then by name; empty for a file. */
	public List<UploadedFileNode> children = new ArrayList<UploadedFileNode>();

	public UploadedFileNode() {
	}

	@Override
	public String toString() {
		return "{relativePath:\"" + relativePath + "\", folder:" + folder + ", published:" + published + ", files:"
				+ filesCount + ", publishedFiles:" + publishedFilesCount + "}";
	}
}
