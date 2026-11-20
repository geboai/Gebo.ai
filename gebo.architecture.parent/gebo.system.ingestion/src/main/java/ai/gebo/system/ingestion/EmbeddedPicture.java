/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.system.ingestion;

/**
 * A picture found inside a document being read: a figure of a Word document, a
 * picture of a slide, an image of a workbook or of a web page.
 *
 * @param name     its name in the document, or null
 * @param mimeType its media type as the document tells it, or null
 * @param data     its bytes
 * @param page     the page or slide it is on, numbered from 1, or null when the
 *                 document does not tell
 */
public record EmbeddedPicture(String name, String mimeType, byte[] data, Integer page) {

	public EmbeddedPicture(String name, String mimeType, byte[] data) {
		this(name, mimeType, data, null);
	}

	/**
	 * The media type among the ones a model reads as a picture, or null: a vector
	 * drawing (EMF, WMF, SVG) or a type not told is not one. The type is taken from
	 * what the document tells, else from the name's extension.
	 */
	public String readableType() {
		String type = mimeType != null ? mimeType.trim().toLowerCase() : null;
		if (type == null || !type.startsWith("image/")) {
			final String lower = name != null ? name.toLowerCase() : "";
			if (lower.endsWith(".png")) {
				type = "image/png";
			} else if (lower.endsWith(".jpg") || lower.endsWith(".jpeg")) {
				type = "image/jpeg";
			} else if (lower.endsWith(".gif")) {
				type = "image/gif";
			} else if (lower.endsWith(".webp")) {
				type = "image/webp";
			}
		}
		if ("image/jpg".equals(type) || "image/pjpeg".equals(type)) {
			type = "image/jpeg";
		}
		if ("image/x-png".equals(type)) {
			type = "image/png";
		}
		return "image/png".equals(type) || "image/jpeg".equals(type) || "image/gif".equals(type)
				|| "image/webp".equals(type) ? type : null;
	}
}
