/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.mcpserver.runtime;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

/**
 * Registers the single Spring MVC functional endpoint that serves every erogated
 * MCP server.
 * <p>
 * Spring collects {@link RouterFunction} beans once at startup, so instead of
 * registering one router per configuration (which would require a restart to change)
 * a single delegating router is published here. On every request it consults the
 * {@link GeboMcpServerRegistry}'s live composite router, which the registry rebuilds
 * whenever a configuration is added, changed or removed — giving runtime updates of
 * the {@code mcp/<url>} endpoints with no restart.
 */
@Configuration
public class GeboMcpDispatcherConfig {

	@Bean
	public RouterFunction<ServerResponse> geboMcpRouterFunction(GeboMcpServerRegistry registry) {
		return request -> registry.currentComposite().route(request);
	}
}
