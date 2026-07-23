/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.gateway.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

/**
 * Serves the Gebo.ai Angular single-page application from the gateway.
 *
 * <p>
 * The gebo.ui artifact (added by the {@code angular-ui} profile) packages the
 * Angular production build under {@code static/}, so Spring Boot's WebFlux
 * resource handling already serves every real file - {@code index.html}, the
 * js/css bundles and lazy chunks, {@code favicon.ico}, {@code assets/} and
 * {@code media/} - straight off {@code classpath:/static/}.
 * </p>
 *
 * <p>
 * What it does NOT do is serve the SPA's <em>client-side</em> routes. Every UI
 * page lives under {@code /ui/<page>} ({@code /ui/chat}, {@code /ui/admin},
 * ...), and those paths have no corresponding file on the classpath: a deep
 * link, a browser refresh or a bookmark on {@code /ui/chat} would be a plain
 * 404. This router therefore returns the {@code index.html} shell for
 * {@code /}, {@code /ui} and anything under {@code /ui/**}, letting the Angular
 * router take over on the client (the app is built with {@code <base href="/">},
 * so its assets resolve from the root regardless of the deep link's depth).
 * </p>
 *
 * <p>
 * Precedence: Boot's {@code RouterFunctionMapping} (order -1) is consulted
 * before Spring Cloud Gateway's route mapping (order 1), so these paths are
 * always answered locally by the UI and never proxied to a backend
 * microservice. Only {@code /} and {@code /ui/**} are claimed here, so proxied
 * API routes are untouched.
 * </p>
 */
@Configuration
public class GeboUiRoutingConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(GeboUiRoutingConfig.class);

	/**
	 * The SPA shell, as packaged by gebo.ui. Absent when the gateway is built
	 * without the {@code angular-ui} profile.
	 */
	private final Resource indexHtml = new ClassPathResource("static/index.html");

	public GeboUiRoutingConfig() {
		if (indexHtml.exists()) {
			LOGGER.info("Gebo.ai UI found on the classpath: serving the SPA at / and /ui/**");
		} else {
			LOGGER.warn("No Gebo.ai UI on the classpath (built without the 'angular-ui' profile): "
					+ "/ and /ui/** will answer 404. The gateway's proxying routes are unaffected.");
		}
	}

	/**
	 * Routes the SPA entry points to the {@code index.html} shell.
	 *
	 * @return the router function backing {@code /}, {@code /ui} and {@code /ui/**}
	 */
	@Bean
	public RouterFunction<ServerResponse> geboUiRouterFunction() {
		return RouterFunctions.route().GET("/", this::serveIndexHtml).GET("/ui", this::serveIndexHtml)
				.GET("/ui/**", this::serveIndexHtml).build();
	}

	/**
	 * Returns the SPA shell, uncached so a redeployed UI is picked up on the next
	 * navigation (the hashed js/css bundles it references stay cacheable).
	 *
	 * @param request the incoming request, unused - every SPA route yields the same
	 *                shell
	 * @return the {@code index.html} response, or 404 when the UI was not bundled
	 */
	private Mono<ServerResponse> serveIndexHtml(ServerRequest request) {
		if (!indexHtml.exists()) {
			return ServerResponse.notFound().build();
		}
		return ServerResponse.ok().contentType(MediaType.TEXT_HTML).cacheControl(CacheControl.noStore())
				.bodyValue(indexHtml);
	}
}
