/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.system.ingestion.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import ai.gebo.system.ingestion.ContentHash;
import ai.gebo.system.ingestion.IGDocumentsHashingService;

/**
 * Implementation of the IGDocumentsHashingService interface that provides
 * functionality for hashing document content.
 * 
 * AI generated comments
 */
@Service
public class GDocumentsHashingServiceImpl implements IGDocumentsHashingService {

	/**
	 * Default constructor for GDocumentsHashingServiceImpl.
	 */
	public GDocumentsHashingServiceImpl() {

	}

	/**
	 * Character array used for converting bytes to hexadecimal representation.
	 */
	private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII);

	/**
	 * Converts a byte array to its hexadecimal string representation.
	 * 
	 * @param bytes The byte array to convert
	 * @return A string containing the hexadecimal representation of the byte array
	 */
	public static String bytesToHex(byte[] bytes) {
		byte[] hexChars = new byte[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = HEX_ARRAY[v >>> 4];
			hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
		}
		return new String(hexChars, StandardCharsets.UTF_8);
	}

	/**
	 * The hash of the documents' texts: the same as the one computed while the text is
	 * read (see {@link ContentHash}), the documents with no text left out.
	 */
	@Override
	public String recalculateHash(List<Document> content) throws IOException, NoSuchAlgorithmException {
		final ContentHash hash = new ContentHash();
		for (Document document : content) {
			if (document.isText()) {
				hash.add(document.getText());
			}
		}
		return hash.hex();
	}

}