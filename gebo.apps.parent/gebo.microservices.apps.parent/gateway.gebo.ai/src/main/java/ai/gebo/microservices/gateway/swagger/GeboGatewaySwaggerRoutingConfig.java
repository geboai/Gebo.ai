/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.gateway.swagger;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ai.gebo.microservices.topology.GeboMicroservice;

/**
 * Exposes the backend microservices' OpenAPI specs through the gateway, so the
 * Swagger UI served here can document the whole deployment.
 *
 * <p>
 * The gateway is a pure proxy - it owns no {@code @RestController} - so its own
 * spec is empty and a Swagger UI on it would be useless on its own. What makes
 * it useful is aggregation: springdoc renders a "Select a definition" dropdown
 * from {@code springdoc.swagger-ui.urls}, one entry per backend, and this class
 * registers the matching proxy route for each entry so the browser can actually
 * fetch those specs from the gateway's own origin (no CORS, no direct access to
 * the backends). Each {@code url} is routed to {@code lb://<name>/v3/api-docs},
 * i.e. through Spring Cloud LoadBalancer / Eureka to a live instance.
 * </p>
 *
 * <p>
 * That single {@code springdoc.swagger-ui.urls} list in application.yml is the
 * one source of truth: the entries drive both the dropdown (read by springdoc)
 * and the routes (read here). Adding a service to the console is therefore one
 * yaml entry - provided that service is itself built with its {@code swagger-on}
 * profile, otherwise it publishes no spec to proxy.
 * </p>
 *
 * <p>
 * <b>Security.</b> The whole class is conditional on springdoc being present,
 * which only happens when the gateway is built with {@code -P swagger-on}. A
 * production build therefore registers none of these routes, and the backends'
 * specs are unreachable through the gateway - matching the rule that production
 * builds ship no API console. Nothing here is switchable at runtime; it is a
 * build-time decision, as intended.
 * </p>
 */
@Configuration
// The springdoc marker class, named as a literal STRING rather than referenced as
// a type: that is what lets this class compile in the default (swagger-less)
// build, where springdoc is not on the classpath at all.
@ConditionalOnClass(name = "org.springdoc.core.configuration.SpringDocConfiguration")
@EnableConfigurationProperties(GeboGatewaySwaggerRoutingConfig.SwaggerUiUrlsProperties.class)
public class GeboGatewaySwaggerRoutingConfig {

	/** The path every springdoc-enabled Gebo service publishes its spec on. */
	private static final String BACKEND_API_DOCS_PATH = "/v3/api-docs";

	private static final Logger LOGGER = LoggerFactory.getLogger(GeboGatewaySwaggerRoutingConfig.class);

	/**
	 * One proxy route per configured definition: the URL the Swagger UI fetches is
	 * rewritten onto the backend's {@code /v3/api-docs} and load-balanced to that
	 * service.
	 *
	 * @param builder    the gateway's route builder
	 * @param properties the {@code springdoc.swagger-ui.urls} definitions
	 * @return the api-docs proxy routes, empty when no definition is configured
	 */
	@Bean
	public RouteLocator geboSwaggerApiDocsRoutes(RouteLocatorBuilder builder, SwaggerUiUrlsProperties properties) {
		RouteLocatorBuilder.Builder routes = builder.routes();
		for (SwaggerUrl definition : properties.getUrls()) {
			// The definition is named with the canonical (underscore) microservice id, as
			// everywhere else in the topology; the lb:// target must be its DNS-safe form,
			// because a URI host cannot carry an underscore.
			String serviceId = GeboMicroservice.toDiscoveryServiceId(definition.getName());
			String exposedPath = definition.getUrl();
			LOGGER.info("Swagger UI enabled: proxying {} -> lb://{}{}", exposedPath, serviceId, BACKEND_API_DOCS_PATH);
			routes = routes.route("api-docs-" + serviceId, r -> r.path(exposedPath)
					.filters(f -> f.setPath(BACKEND_API_DOCS_PATH)).uri("lb://" + serviceId));
		}
		return routes.build();
	}

	/**
	 * Binds the {@code springdoc.swagger-ui.urls} definitions with a local type, so
	 * that no springdoc class is referenced at compile time and the gateway still
	 * builds without the {@code swagger-on} profile. springdoc binds the very same
	 * keys for the dropdown; both bindings coexist.
	 */
	@ConfigurationProperties(prefix = "springdoc.swagger-ui")
	public static class SwaggerUiUrlsProperties {

		private List<SwaggerUrl> urls = new ArrayList<>();

		public List<SwaggerUrl> getUrls() {
			return urls;
		}

		public void setUrls(List<SwaggerUrl> urls) {
			this.urls = urls;
		}
	}

	/**
	 * A single Swagger UI definition: {@code name} is the canonical (underscore)
	 * microservice id - it labels the dropdown entry and, hyphenated, names the
	 * {@code lb://} target - and {@code url} is the gateway path the browser
	 * fetches that spec from.
	 */
	public static class SwaggerUrl {

		private String name;

		private String url;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
	}
}
