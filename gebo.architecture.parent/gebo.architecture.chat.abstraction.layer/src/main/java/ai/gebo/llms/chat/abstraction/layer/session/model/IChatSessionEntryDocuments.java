/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.abstraction.layer.session.model;

import java.util.List;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef;

/**
 * An earlier interaction of the chat that knows the documents its answer rested on:
 * they were read then and stay valid for the chat, the answer being one of what the
 * next requests are given.
 */
public interface IChatSessionEntryDocuments {

	/** The documents the answer rested on, none when unknown. */
	List<GResponseDocumentRef> getDocumentsRef();

	/**
	 * The names of the documents the tools only listed for the answer (not read) that it
	 * names, none when unknown.
	 */
	default List<String> getListedDocumentNames() {
		return List.of();
	}
}
