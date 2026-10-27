/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import java.util.Map;

import org.junit.jupiter.api.Test;

class MimeTypesTest {

	@Test
	void anXlsxContentTypeHasTheXlsxExtension() {
		assertThat(MimeTypes.getDefaultExt(MimeTypes.MIME_APPLICATION_VND_MSEXCEL_2007)).isEqualTo("xlsx");
	}

	/**
	 * The class keeps two maps, extension to type and type back to extension, filled
	 * by hand one beside the other. A type registered in the first and forgotten in
	 * the second comes back from getDefaultExt as "unknown": a crawled xlsx used to
	 * be saved as file.xlsx.unknown that way.
	 */
	@Test
	@SuppressWarnings("unchecked")
	void everyRegisteredContentTypeMapsBackToAnExtension() throws Exception {
		Field forward = MimeTypes.class.getDeclaredField("mimeTypeMapping");
		forward.setAccessible(true);
		Map<String, String> extensionToType = (Map<String, String>) forward.get(null);
		assertThat(extensionToType).isNotEmpty();
		for (Map.Entry<String, String> entry : extensionToType.entrySet()) {
			assertThat(MimeTypes.lookupExt(entry.getValue()))
					.as("extension for %s, registered for .%s", entry.getValue(), entry.getKey()).isNotNull();
		}
	}
}
