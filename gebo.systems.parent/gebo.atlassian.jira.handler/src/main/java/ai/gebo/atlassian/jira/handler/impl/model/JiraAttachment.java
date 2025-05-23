/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.jira.handler.impl.model;

import java.util.LinkedHashMap;

import lombok.Getter;
import lombok.ToString;

/**
 * AI generated comments
 * 
 * Represents a Jira attachment with its metadata.
 * This class encapsulates information about files attached to Jira issues,
 * including the file properties and content reference.
 */
@Getter
@ToString
public class JiraAttachment {

	/** URL reference to the attachment */
	private String self;
	/** Unique identifier of the attachment */
	private String id;
	/** Name of the attached file */
	private String fileName;
	/** Timestamp when the attachment was created */
	private String created;
	/** Size of the attachment in bytes */
	private Long size;
	/** MIME type of the attachment */
	private String mimeType;
	/** URL or content reference of the attachment */
	private String content;

	/**
	 * Constructs a JiraAttachment object from a map of attachment data.
	 * Extracts and converts attachment properties from the provided map.
	 *
	 * @param value A LinkedHashMap containing the attachment data from Jira API
	 */
	public JiraAttachment(LinkedHashMap<String, Object> value) {
		Object self = value.get("self");
		Object id = value.get("id");
		Object fileName = value.get("filename");
		Object created = value.get("created");
		Object size = value.get("size");
		Object mimeType = value.get("mimeType");
		Object content = value.get("content");
		
		// Safe assignment with null checks
		if (self != null) {
			this.self = self.toString();
		}
		if (id != null) {
			this.id = id.toString();
		}
		if (fileName != null) {
			this.fileName = fileName.toString();
		}
		if (created != null) {
			this.created = created.toString();
		}
		if (size != null && size instanceof Number) {
			this.size = ((Number) size).longValue();
		}
		if (mimeType != null) {
			this.mimeType = mimeType.toString();
		}
		if (content != null) {
			this.content = content.toString();
		}
	}
}