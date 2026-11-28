/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.glpi.handler;

import ai.gebo.glpi.handler.model.GlpiPathNodeType;
import ai.gebo.systems.abstraction.layer.IGRemoteVirtualFilesystemResourceReference;

/**
 * The minimal handle to read one GLPI item again: rebuilt from the custom meta
 * infos of the stored document reference, or from the native coordinates of a
 * search result.
 */
public class GlpiResourceReference implements IGRemoteVirtualFilesystemResourceReference {
	public GlpiPathNodeType objectType = null;
	public Long entityId = null;
	public Long ticketId = null;
	public Long itemId = null;
	public String ticketName = null;
	public String documentFileName = null;
	public String documentMimeType = null;

	@Override
	public String toString() {
		return "{objectType=" + objectType + ", entityId=" + entityId + ", ticketId=" + ticketId + ", itemId="
				+ itemId + ", ticketName=" + ticketName + ", documentFileName=" + documentFileName + ", documentMimeType=" + documentMimeType + "}";
	}
}
