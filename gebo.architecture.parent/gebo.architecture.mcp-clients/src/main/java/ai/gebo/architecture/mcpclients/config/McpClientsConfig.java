/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.mcpclients.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * Externally configurable settings for the MCP clients module, bound from
 * {@code application.yml} under the {@code ai.gebo.mcp.clients} prefix.
 * <p>
 * Example:
 *
 * <pre>
 * ai:
 *   gebo:
 *     mcp:
 *       clients:
 *         connection-pool-ttl-seconds: 300
 * </pre>
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.mcp.clients")
@Data
public class McpClientsConfig {

	/**
	 * Maximum lifetime, in seconds, of a pooled MCP client connection before it is
	 * refreshed. This also bounds how long credentials resolved at connect time
	 * (e.g. client-credentials access tokens) are reused. Defaults to 5 minutes. A
	 * value &lt;= 0 disables expiry (connections are kept until the config changes,
	 * a call fails, or the application shuts down).
	 */
	private long connectionPoolTtlSeconds = 300;
}
