/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.documents.cache.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * The life of a cached document copy: deleted, record and file, once not accessed for
 * this many seconds.
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.documents-cache")
@Data
public class DocumentsCacheTtlConfig {
	public static final long DEFAULT_TTL_SECONDS = 300;
	/** The seconds a cached document copy lives after its last access. */
	private long ttlSeconds = DEFAULT_TTL_SECONDS;

	/** The life in milliseconds, never less than one second. */
	public long ttlMillis() {
		return Math.max(1, ttlSeconds) * 1000L;
	}
}
