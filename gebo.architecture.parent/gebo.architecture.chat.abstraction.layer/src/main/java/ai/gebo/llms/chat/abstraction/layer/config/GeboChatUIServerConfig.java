/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.abstraction.layer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * Server-side view of the {@code ai.gebo.chatui} options that the chat runtime
 * has to enforce, independently of the higher-level UI options bean that only
 * the controller layer can see. This lets the chat session lifecycle enforce the
 * "chat with external files" gate itself, so a client that sends an external
 * search result while the feature is disabled is silently ignored rather than
 * trusted.
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.chatui")
@Data
public class GeboChatUIServerConfig {

	/**
	 * {@code ai.gebo.chatui.chatWithExternalFiles}: when true, external-search
	 * results ({@code GResponseDocumentRef} carrying a {@code nestedSearchResult})
	 * can be ingested and "chatted with" like internal documents. When false, the
	 * chat runtime silently skips any external ref it receives. Defaults to true,
	 * matching the UI options default.
	 */
	private boolean chatWithExternalFiles = true;
}
