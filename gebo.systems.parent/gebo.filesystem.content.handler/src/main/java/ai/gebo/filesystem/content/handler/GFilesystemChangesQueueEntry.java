/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.filesystem.content.handler;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Changes of the files of a folder of a filesystem data source that follows them
 * ({@link GFilesystemProjectEndpoint#getSyncOnFilesModifications()}), found by a look
 * at the folder and waiting to be handed to the ingestion: they are stored so that
 * they wait while the data source is being published, and are still there after a
 * restart. A look that finds many changes stores them in several entries, in the
 * order of their {@link #part}.
 */
// the changes of an endpoint, in the order they are read
@CompoundIndex(name = "endpointCode_detectedAt_part", def = "{'endpointCode': 1, 'detectedAt': 1, 'part': 1}")
@Document
public class GFilesystemChangesQueueEntry {
	@Id
	private String id = UUID.randomUUID().toString();
	/** The code of the data source the folder is of. */
	// indexed with detectedAt and part, the order the queue is read in (on the class)
	private String endpointCode = null;
	/** The absolute path of the folder. */
	private String folder = null;
	/** When the look found the changes. */
	private Date detectedAt = null;
	/** The place of this entry among the ones the same look stored. */
	private int part = 0;
	/** The absolute paths of the files found new. */
	private List<String> added = new ArrayList<String>();
	/** The absolute paths of the files found changed. */
	private List<String> modified = new ArrayList<String>();
	/** The absolute paths of the files no longer found. */
	private List<String> deleted = new ArrayList<String>();
	/** How many times handing these changes to the ingestion failed. */
	private int attempts = 0;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEndpointCode() {
		return endpointCode;
	}

	public void setEndpointCode(String endpointCode) {
		this.endpointCode = endpointCode;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public Date getDetectedAt() {
		return detectedAt;
	}

	public void setDetectedAt(Date detectedAt) {
		this.detectedAt = detectedAt;
	}

	public int getPart() {
		return part;
	}

	public void setPart(int part) {
		this.part = part;
	}

	public List<String> getAdded() {
		return added;
	}

	public void setAdded(List<String> added) {
		this.added = added;
	}

	public List<String> getModified() {
		return modified;
	}

	public void setModified(List<String> modified) {
		this.modified = modified;
	}

	public List<String> getDeleted() {
		return deleted;
	}

	public void setDeleted(List<String> deleted) {
		this.deleted = deleted;
	}

	public int getAttempts() {
		return attempts;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}
}
