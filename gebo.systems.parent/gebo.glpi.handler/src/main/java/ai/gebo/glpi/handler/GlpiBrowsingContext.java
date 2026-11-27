/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.glpi.handler;

import ai.gebo.systems.abstraction.layer.IGVirtualFileSystemContext;

/**
 * Browsing context of the GLPI data source tree selector: the code of the
 * GLPI system browsed.
 */
public class GlpiBrowsingContext implements IGVirtualFileSystemContext {
	private String systemCode = null;

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public static GlpiBrowsingContext of(String systemCode) {
		GlpiBrowsingContext context = new GlpiBrowsingContext();
		context.systemCode = systemCode;
		return context;
	}
}
