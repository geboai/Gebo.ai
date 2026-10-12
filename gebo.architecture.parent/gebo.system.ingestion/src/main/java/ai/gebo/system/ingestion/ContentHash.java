/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.system.ingestion;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The hash of a document's extracted text, computed while the text is read: the
 * MD5 of its texts, in reading order, as UTF-8. The same text gives the same
 * hash whatever pieces it is read in, so an ingestion can tell a document whose
 * text changed from one only touched (a new date, the same text).
 */
public class ContentHash {
	private final MessageDigest digest;
	private long texts = 0l;

	public ContentHash() {
		try {
			this.digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("MD5 not available", e);
		}
	}

	/**
	 * Adds a piece of the text, the next in reading order (null adds nothing).
	 */
	public ContentHash add(String text) {
		if (text != null) {
			digest.update(text.getBytes(StandardCharsets.UTF_8));
			texts++;
		}
		return this;
	}

	/**
	 * How many pieces of text were added.
	 */
	public long getTexts() {
		return texts;
	}

	/**
	 * The hash of the text added, as upper case hex: ends the computation.
	 */
	public String hex() {
		return toHex(digest.digest());
	}

	private static final char[] HEX = "0123456789ABCDEF".toCharArray();

	static String toHex(byte[] bytes) {
		final char[] chars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			chars[j * 2] = HEX[v >>> 4];
			chars[j * 2 + 1] = HEX[v & 0x0F];
		}
		return new String(chars);
	}
}
