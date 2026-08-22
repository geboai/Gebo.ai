/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */




package ai.gebo.uploads.content.handler;

import java.util.Date;

/**
 * View of a single file physically present in the persistent folder of a
 * {@link GUploadsProjectEndpoint}, enriched with the ingestion state of the
 * corresponding knowledge base document when one exists.
 *
 * <p>
 * It is the read model behind the "files of this data source" panel of the
 * uploads editor: the file listing comes from the filesystem (the source of
 * truth for what will be ingested at the next publish), while {@link #ingested}
 * and {@link #documentCode} come from the already ingested document references,
 * so the UI can tell apart a file that is only uploaded from one that is
 * already part of the knowledge base and can therefore be opened in the
 * contents viewer.
 * </p>
 */
public class UploadedFileInfo {

	/** File name, relative to the endpoint persistent folder root. */
	public String name = null;

	/** Absolute path of the file on the node hosting the uploads module. */
	public String absolutePath = null;

	/** Lowercase extension including the dot, {@code null} when there is none. */
	public String extension = null;

	/** Size in bytes. */
	public long size = 0;

	/** Last modification timestamp of the file. */
	public Date modificationTime = null;

	/** True when the entry is a folder (zip contents are expanded on publish). */
	public boolean folder = false;

	/** True when a not deleted document reference exists for this file. */
	public boolean ingested = false;

	/**
	 * Code of the ingested document reference, usable to stream/open the content;
	 * {@code null} while the file has not been published yet.
	 */
	public String documentCode = null;

	/**
	 * True when the file is listed in
	 * {@link GUploadsProjectEndpoint#getUploadedContents()}, i.e. it was uploaded
	 * through Gebo.ai rather than appearing in the folder by other means.
	 */
	public boolean tracked = false;

	public UploadedFileInfo() {

	}

	@Override
	public String toString() {
		return "{name:\"" + name + "\", size:" + size + ", ingested:" + ingested + "}";
	}
}
