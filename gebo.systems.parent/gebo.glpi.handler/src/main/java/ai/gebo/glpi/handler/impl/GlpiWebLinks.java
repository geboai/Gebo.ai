/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.glpi.handler.impl;

/**
 * The links of the GLPI web interface to the items, shown as the documents
 * and search results urls.
 */
public final class GlpiWebLinks {
	private GlpiWebLinks() {
	}

	private static String form(String rootUrl, String form, Long id) {
		if (rootUrl == null || id == null) {
			return null;
		}
		return rootUrl + "/front/" + form + ".form.php?id=" + id;
	}

	public static String entity(String rootUrl, Long id) {
		return form(rootUrl, "entity", id);
	}

	public static String ticket(String rootUrl, Long id) {
		return form(rootUrl, "ticket", id);
	}

	public static String document(String rootUrl, Long id) {
		return form(rootUrl, "document", id);
	}

	public static String kbArticle(String rootUrl, Long id) {
		return form(rootUrl, "knowbaseitem", id);
	}
}
