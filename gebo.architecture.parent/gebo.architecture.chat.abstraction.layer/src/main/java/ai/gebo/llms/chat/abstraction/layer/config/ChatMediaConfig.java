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
 * How much of a conversation's files goes to the model as it is, on each request:
 * pictures, PDF files and recordings travel as bytes and are paid by the provider's
 * own count. The newest files come first; the ones beyond a limit stay with the
 * tools.
 */
@Configuration
@ConfigurationProperties(prefix = "ai.gebo.chat.media")
@Data
public class ChatMediaConfig {
	/** The files a request carries to the model at most. */
	private int maxFiles = 10;
	/** The size in bytes a file may have to be carried. */
	private long maxFileBytes = 20L * 1024 * 1024;
	/** The bytes a request carries to the model at most, all files together. */
	private long maxTotalBytes = 40L * 1024 * 1024;
}
