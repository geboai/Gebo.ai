/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.zammad.handler;

import ai.gebo.zammad.handler.model.ZammadPathNodeType;
import ai.gebo.systems.abstraction.layer.IGRemoteVirtualFilesystemResourceReference;

/**
 * The minimal handle to stream one Zammad item, rebuilt from the document's
 * custom meta infos.
 */
public class ZammadResourceReference implements IGRemoteVirtualFilesystemResourceReference {
	public ZammadPathNodeType type = null;
	/** The data source root the item was read under: GROUP:<id> or KNOWLEDGE_BASE:<id>. */
	public String rootCode = null;
	public Long ticketId = null;
	public Long articleId = null;
	public Long knowledgeBaseId = null;
	public Long answerId = null;
	public Long attachmentId = null;
	public String contentType = null;

	@Override
	public String toString() {
		return "{type=" + type + ", root=" + rootCode + ", ticket=" + ticketId + ", article=" + articleId
				+ ", knowledgeBase=" + knowledgeBaseId + ", answer=" + answerId + ", attachment=" + attachmentId
				+ ", contentType=" + contentType + "}";
	}
}
