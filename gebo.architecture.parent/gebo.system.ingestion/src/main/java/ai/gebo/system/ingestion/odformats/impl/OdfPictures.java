/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.system.ingestion.odformats.impl;

import java.util.List;

import org.odftoolkit.odfdom.doc.OdfDocument;
import org.odftoolkit.odfdom.pkg.OdfPackage;

import ai.gebo.system.ingestion.EmbeddedPicture;

/** The pictures an OpenDocument file carries in its package, under Pictures/. */
final class OdfPictures {
	private static final String FOLDER = "Pictures/";

	private OdfPictures() {
	}

	/**
	 * Adds the pictures of the document's package to the list; nothing when the
	 * list is null (nobody is going to describe them). A picture that cannot be read
	 * is passed over: the document's text is read all the same.
	 */
	static void collect(OdfDocument document, List<EmbeddedPicture> pictures) {
		if (pictures == null || document == null) {
			return;
		}
		try {
			final OdfPackage odfPackage = document.getPackage();
			for (String path : new java.util.TreeSet<>(odfPackage.getFilePaths())) {
				if (path == null || !path.startsWith(FOLDER) || path.endsWith("/")) {
					continue;
				}
				final byte[] data = odfPackage.getBytes(path);
				if (data != null) {
					pictures.add(new EmbeddedPicture(path.substring(FOLDER.length()),
							odfPackage.getMediaTypeString(path), data));
				}
			}
		} catch (RuntimeException unreadable) {
			// the text is read all the same
		}
	}
}
