/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.ai.files;

import java.io.IOException;

/**
 * Keeps in a conversation a file that reached it from outside its tools' own files
 * locations - the picture an MCP tool returned, the file another agent sent back -
 * so that it is not lost: the user finds it among the conversation's files, the
 * tools working on files open it in the {@value #LOCATION_ID} location, and a model
 * that takes such files sees it on the next requests.
 */
public interface IGConversationGeneratedFilesStore {
	/** The read-only files location holding the files kept this way. */
	String LOCATION_ID = "generated";

	/**
	 * Keeps a file in a conversation.
	 *
	 * @param chatSessionCode the conversation
	 * @param name            the file's name, with its extension
	 * @param mimeType        the file's MIME type
	 * @param data            the file's bytes
	 * @return the file as the tools find it in the {@value #LOCATION_ID} location
	 */
	GLibraryFile store(String chatSessionCode, String name, String mimeType, byte[] data) throws IOException;

	/** An extension for a MIME type, by its subtype: {@code .png} for image/png; .bin when unknown. */
	static String extensionOf(String mimeType) {
		if (mimeType == null || !mimeType.contains("/")) {
			return ".bin";
		}
		String subtype = mimeType.substring(mimeType.indexOf('/') + 1).trim().toLowerCase(java.util.Locale.ROOT);
		final int parameters = subtype.indexOf(';');
		if (parameters >= 0) {
			subtype = subtype.substring(0, parameters).trim();
		}
		subtype = switch (subtype) {
		case "jpeg" -> "jpg";
		case "mpeg" -> mimeType.startsWith("audio") ? "mp3" : "mpeg";
		case "svg+xml" -> "svg";
		case "plain" -> "txt";
		case "x-wav", "wave" -> "wav";
		default -> subtype;
		};
		return subtype.matches("[a-z0-9]{1,8}") ? "." + subtype : ".bin";
	}
}
