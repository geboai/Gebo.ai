/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.knowledgebase.hierarchy.local.config;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration of the local GProject/GKnowledgeBase lookup cache, bound from
 * {@code ai.gebo.knowledgebase.local.*}:
 *
 * <pre>
 * ai.gebo.knowledgebase.local:
 *   cache-ttl: 60s
 *   cache-max-entries: 2000
 * </pre>
 *
 * Same defaults as {@code gebo.microservices.knowledgebase.client}'s
 * {@code GeboKnowledgeBaseClientProperties}, so a project/knowledge-base's
 * staleness window is the same whether it is read locally or remotely.
 */
@ConfigurationProperties(prefix = "ai.gebo.knowledgebase.local")
public class GeboKnowledgeBaseLocalCacheProperties {

	private Duration cacheTtl = Duration.ofSeconds(60);

	private int cacheMaxEntries = 2000;

	public Duration getCacheTtl() {
		return cacheTtl;
	}

	public void setCacheTtl(Duration cacheTtl) {
		this.cacheTtl = cacheTtl;
	}

	public int getCacheMaxEntries() {
		return cacheMaxEntries;
	}

	public void setCacheMaxEntries(int cacheMaxEntries) {
		this.cacheMaxEntries = cacheMaxEntries;
	}
}
