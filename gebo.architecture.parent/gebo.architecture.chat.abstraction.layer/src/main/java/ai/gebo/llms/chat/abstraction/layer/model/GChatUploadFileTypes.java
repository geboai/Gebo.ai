/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.chat.abstraction.layer.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The files a chat accepts as uploads, by extension with the dot, lower case.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GChatUploadFileTypes {
	/** The files read into the chat: the ones the content handlers read. */
	private List<String> chatExtensions = new ArrayList<>();
	/**
	 * The files kept only for the agents' tools: the ones the libraries of the chat's
	 * agents work on that no content handler reads. They are not read into the chat,
	 * and the models get no picture or sound of them.
	 */
	private List<String> toolsExtensions = new ArrayList<>();
}
