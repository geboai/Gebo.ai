/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.microsoft.sharepoint.cloud.model;

import java.io.InputStream;

// ----------------------------------------------------------------
// Custom object to hold the binary InputStream + metadata
// ----------------------------------------------------------------
public class CloudSharepointDownloadedAttachment {
	private final InputStream inputStream;
	private final String contentType;
	private final boolean officeDocument;

	/**
	 * Basic constructor. We also do a small check to see if it's a recognized
	 * Microsoft Office MIME type.
	 */
	public CloudSharepointDownloadedAttachment(InputStream inputStream, String contentType) {
		this.inputStream = inputStream;
		this.contentType = contentType;
		this.officeDocument = isOfficeMimeType(contentType);
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public String getContentType() {
		return contentType;
	}

	public boolean isOfficeDocument() {
		return officeDocument;
	}

	/**
	 * A simplistic check for some known MS Office MIME types. Adjust or extend as
	 * needed for your scenario.
	 */
	private boolean isOfficeMimeType(String contentType) {
		if (contentType == null)
			return false;
		// Common modern Office formats: docx, xlsx, pptx
		// "application/vnd.openxmlformats-officedocument.*"
		// or older "application/msword", "application/vnd.ms-excel", etc.
		return contentType.startsWith("application/vnd.openxmlformats-officedocument")
				|| contentType.equals("application/msword") || contentType.equals("application/vnd.ms-excel")
				|| contentType.equals("application/vnd.ms-powerpoint");
	}
}