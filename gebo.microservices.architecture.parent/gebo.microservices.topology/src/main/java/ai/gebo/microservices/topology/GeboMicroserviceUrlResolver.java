/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.topology;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Resolves the complete HTTP <b>base url</b> at which a microservice of the
 * {@link GeboMicroservicesTopology} can be contacted, ready to be handed to a
 * {@code RestTemplate}, {@code WebClient} or Feign client. The base url has no
 * trailing slash - callers append a path starting with {@code '/'}.
 *
 * <p>
 * A microservice is addressable in this deployment by three keys, all resolved
 * here to the <b>same</b> owning microservice and therefore the same base url:
 * </p>
 * <ul>
 * <li>its <b>microserviceId</b> (= application name = LoadBalancer service-id),
 * see {@link #baseUrlForMicroserviceId(String)};</li>
 * <li>a <b>messagingModuleId</b> it hosts (unambiguous - a module belongs to
 * exactly one microservice), see {@link #baseUrlForMessagingModuleId(String)};</li>
 * <li>a <b>messagingSystemId</b> it hosts - possibly ambiguous, because shared
 * content-infrastructure systems (e.g. {@code module-ioc-dispatcher-component})
 * live in several microservices, so this returns a set, see
 * {@link #baseUrlsForMessagingSystemId(String)}.</li>
 * </ul>
 *
 * <h2>Strategies</h2>
 * <p>
 * How the base url is built is governed by a {@link Strategy}, matching the
 * three ways the architecture lets one service reach another:
 * </p>
 * <ul>
 * <li>{@link Strategy#LOAD_BALANCER} (default) - client-side load balancing:
 * {@code <scheme>://<microserviceId>} (e.g. {@code http://brain_gebo_ai}). This
 * is what a {@code @LoadBalanced} {@code RestTemplate}/{@code WebClient} resolves
 * and is exactly the Feign service name; the Spring Cloud LoadBalancer picks a
 * live instance. The equivalent {@code lb://} form is
 * {@link #loadBalancerUriForMicroserviceId(String)}.</li>
 * <li>{@link Strategy#GATEWAY} - route every call through the API gateway:
 * {@code <gatewayBaseUrl><gatewayPathTemplate>} with {@code {microserviceId}}
 * expanded (e.g. {@code http://gateway_gebo_ai:8080/brain_gebo_ai}). When no
 * gateway base url is configured it defaults to the load-balanced gateway
 * ({@code <scheme>://gateway_gebo_ai}).</li>
 * <li>{@link Strategy#DIRECT} - a fixed {@code microserviceId -> base url} map
 * (e.g. {@code http://localhost:8081}), for tests, a monolith or a pinned
 * deployment where discovery is not used.</li>
 * </ul>
 *
 * <p>
 * The {@code direct} map is also honoured as a <b>per-service override</b> in the
 * {@code LOAD_BALANCER} and {@code GATEWAY} strategies: an entry present there
 * always wins, so individual services can be pinned to a fixed address without
 * switching the whole deployment to {@code DIRECT}.
 * </p>
 *
 * <p>
 * Resolution only succeeds for a microservice that is a topology member (unknown
 * ids yield {@link Optional#empty()}), so a typo surfaces rather than silently
 * producing a dead url - unless the id has an explicit {@code direct} entry,
 * which may address a non-member such as the gateway or a third-party service.
 * Instances are immutable and thread-safe.
 * </p>
 *
 * <p>
 * In a Spring deployment the effective instance is published as a bean by
 * {@link ai.gebo.microservices.topology.config.GeboMicroservicesTopologyAutoConfiguration}
 * from {@code gebo.microservices.topology.url.*}. Outside Spring, use one of the
 * static factories ({@link #loadBalanced(GeboMicroservicesTopology)} etc.).
 * </p>
 *
 * Gebo.ai comment agent
 */
public final class GeboMicroserviceUrlResolver {

	/** Default scheme when none is configured. */
	public static final String DEFAULT_SCHEME = "http";

	/** Default gateway path template; {@code {microserviceId}} is expanded per service. */
	public static final String DEFAULT_GATEWAY_PATH_TEMPLATE = "/{microserviceId}";

	/** Placeholder replaced by the target microservice id inside a gateway path template. */
	public static final String MICROSERVICE_ID_PLACEHOLDER = "{microserviceId}";

	/**
	 * How a base url is built from a resolved microservice.
	 *
	 * Gebo.ai comment agent
	 */
	public enum Strategy {
		/** {@code <scheme>://<microserviceId>} - client-side Spring Cloud LoadBalancer / Feign. */
		LOAD_BALANCER,
		/** {@code <gatewayBaseUrl><gatewayPathTemplate>} - every call routed through the API gateway. */
		GATEWAY,
		/** A fixed {@code microserviceId -> base url} map - no service discovery. */
		DIRECT
	}

	private final GeboMicroservicesTopology topology;
	private final Strategy strategy;
	private final String scheme;
	private final String gatewayBaseUrl;
	private final String gatewayPathTemplate;
	private final Map<String, String> directBaseUrls;
	/** messagingSystemId -> owning microserviceIds (canonical), built once from the topology. */
	private final Map<String, Set<String>> systemToMicroservices;

	/**
	 * @param topology the resolved topology; required
	 * @param strategy how to build a base url; required
	 * @param scheme url scheme (e.g. {@code http}); {@code null}/blank falls back to
	 *            {@link #DEFAULT_SCHEME}
	 * @param gatewayBaseUrl gateway base url for {@link Strategy#GATEWAY} (e.g.
	 *            {@code http://gateway_gebo_ai:8080}); {@code null}/blank falls back
	 *            to the load-balanced gateway ({@code <scheme>://gateway_gebo_ai})
	 * @param gatewayPathTemplate gateway path template containing
	 *            {@link #MICROSERVICE_ID_PLACEHOLDER}; {@code null}/blank falls back
	 *            to {@link #DEFAULT_GATEWAY_PATH_TEMPLATE}
	 * @param directBaseUrls fixed {@code microserviceId -> base url} entries; used as
	 *            the source in {@link Strategy#DIRECT} and as per-service overrides in
	 *            the other strategies; may be {@code null}/empty. Keys are normalised
	 *            to the canonical (underscore) id form.
	 */
	public GeboMicroserviceUrlResolver(GeboMicroservicesTopology topology, Strategy strategy, String scheme,
			String gatewayBaseUrl, String gatewayPathTemplate, Map<String, String> directBaseUrls) {
		this.topology = Objects.requireNonNull(topology, "topology");
		this.strategy = Objects.requireNonNull(strategy, "strategy");
		this.scheme = isBlank(scheme) ? DEFAULT_SCHEME : scheme.trim();
		this.gatewayBaseUrl = isBlank(gatewayBaseUrl) ? null : stripTrailingSlash(gatewayBaseUrl.trim());
		this.gatewayPathTemplate = isBlank(gatewayPathTemplate) ? DEFAULT_GATEWAY_PATH_TEMPLATE
				: gatewayPathTemplate.trim();

		Map<String, String> direct = new LinkedHashMap<>();
		if (directBaseUrls != null) {
			directBaseUrls.forEach((id, url) -> {
				String canonical = GeboMicroservice.normalizeName(id);
				if (canonical != null && !canonical.isBlank() && !isBlank(url)) {
					direct.put(canonical, stripTrailingSlash(url.trim()));
				}
			});
		}
		this.directBaseUrls = Map.copyOf(direct);
		this.systemToMicroservices = indexSystems(topology);
	}

	private static Map<String, Set<String>> indexSystems(GeboMicroservicesTopology topology) {
		Map<String, Set<String>> index = new LinkedHashMap<>();
		topology.microserviceToMessagingModules().forEach((microserviceId, modules) -> modules
				.forEach((moduleId, systemIds) -> {
					for (String systemId : systemIds) {
						index.computeIfAbsent(systemId, k -> new LinkedHashSet<>()).add(microserviceId);
					}
				}));
		Map<String, Set<String>> immutable = new LinkedHashMap<>();
		index.forEach((systemId, ids) -> immutable.put(systemId, Set.copyOf(ids)));
		return Map.copyOf(immutable);
	}

	// --- Static factories (Spring-free) -------------------------------------

	/**
	 * A resolver using the default {@link Strategy#LOAD_BALANCER} strategy over
	 * {@code http} - i.e. {@code http://<microserviceId>}.
	 *
	 * @param topology the topology
	 * @return a load-balancer resolver
	 */
	public static GeboMicroserviceUrlResolver loadBalanced(GeboMicroservicesTopology topology) {
		return new GeboMicroserviceUrlResolver(topology, Strategy.LOAD_BALANCER, DEFAULT_SCHEME, null, null, Map.of());
	}

	/**
	 * A resolver routing every call through the gateway.
	 *
	 * @param topology the topology
	 * @param gatewayBaseUrl the gateway base url (e.g.
	 *            {@code http://gateway_gebo_ai:8080}); {@code null} uses the
	 *            load-balanced gateway
	 * @return a gateway resolver
	 */
	public static GeboMicroserviceUrlResolver viaGateway(GeboMicroservicesTopology topology, String gatewayBaseUrl) {
		return new GeboMicroserviceUrlResolver(topology, Strategy.GATEWAY, DEFAULT_SCHEME, gatewayBaseUrl, null,
				Map.of());
	}

	/**
	 * A resolver reading base urls from a fixed {@code microserviceId -> base url}
	 * map (no service discovery).
	 *
	 * @param topology the topology
	 * @param directBaseUrls the fixed base-url map
	 * @return a direct resolver
	 */
	public static GeboMicroserviceUrlResolver direct(GeboMicroservicesTopology topology,
			Map<String, String> directBaseUrls) {
		return new GeboMicroserviceUrlResolver(topology, Strategy.DIRECT, DEFAULT_SCHEME, null, null, directBaseUrls);
	}

	// --- Resolution by microservice id --------------------------------------

	/**
	 * The base url for a microservice, addressed by its id / application name.
	 *
	 * @param microserviceId the microservice id (dotted or underscore form, e.g.
	 *            {@code brain.gebo.ai} or {@code brain_gebo_ai})
	 * @return the base url (no trailing slash), or empty if the id is not a topology
	 *         member and has no {@code direct} entry
	 */
	public Optional<String> baseUrlForMicroserviceId(String microserviceId) {
		String canonical = GeboMicroservice.normalizeName(microserviceId);
		if (canonical == null || canonical.isBlank()) {
			return Optional.empty();
		}
		// An explicit direct entry always wins and may address a non-member (gateway/third-party).
		String pinned = directBaseUrls.get(canonical);
		if (pinned != null) {
			return Optional.of(pinned);
		}
		if (topology.forMicroserviceId(canonical).isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(buildBaseUrl(canonical));
	}

	/**
	 * The base url as a {@link URI}.
	 *
	 * @param microserviceId the microservice id (dotted or underscore form)
	 * @return the base uri, or empty if unresolved
	 * @see #baseUrlForMicroserviceId(String)
	 */
	public Optional<URI> baseUriForMicroserviceId(String microserviceId) {
		return baseUrlForMicroserviceId(microserviceId).map(URI::create);
	}

	/**
	 * A complete url for a microservice with a path appended (e.g. build a
	 * {@code RestTemplate} target in one call).
	 *
	 * @param microserviceId the microservice id (dotted or underscore form)
	 * @param path the path to append; a leading {@code '/'} is added if missing,
	 *            {@code null}/blank yields the bare base url
	 * @return {@code baseUrl + path}, or empty if the microservice is unresolved
	 */
	public Optional<String> urlForMicroserviceId(String microserviceId, String path) {
		return baseUrlForMicroserviceId(microserviceId).map(base -> base + normalizePath(path));
	}

	/**
	 * The Spring Cloud LoadBalancer uri ({@code lb://<microserviceId>}) for a
	 * topology member - handy to configure a gateway route or a
	 * {@code @LoadBalanced WebClient} target regardless of the resolver strategy.
	 *
	 * @param microserviceId the microservice id (dotted or underscore form)
	 * @return the {@code lb://} uri, or empty if the id is not a topology member
	 */
	public Optional<String> loadBalancerUriForMicroserviceId(String microserviceId) {
		return topology.forMicroserviceId(GeboMicroservice.normalizeName(microserviceId))
				.map(GeboMicroservice::getLoadBalancerUri);
	}

	// --- Resolution by messaging module / system ----------------------------

	/**
	 * The base url of the microservice hosting a given messaging module (a module
	 * belongs to exactly one microservice, so this is unambiguous).
	 *
	 * @param messagingModuleId a module id (e.g. {@code vectorizator-module})
	 * @return the owning microservice's base url, or empty if the module is unmapped
	 */
	public Optional<String> baseUrlForMessagingModuleId(String messagingModuleId) {
		return topology.forMessagingModuleId(messagingModuleId).map(GeboMicroservice::getApplicationName)
				.flatMap(this::baseUrlForMicroserviceId);
	}

	/**
	 * The base url(s) of every microservice hosting a given messaging system. A
	 * system id shared by several microservices (e.g. the content-infrastructure
	 * components {@code module-ioc-dispatcher-component},
	 * {@code resources-dispose-component}, {@code system-settings-controller-component})
	 * resolves to more than one base url; a code-unique system id resolves to a
	 * single-element set.
	 *
	 * @param messagingSystemId a system id (e.g. {@code vectorization-component})
	 * @return the base urls of the hosting microservices, in topology order; empty
	 *         if the system id is unknown
	 */
	public Set<String> baseUrlsForMessagingSystemId(String messagingSystemId) {
		Set<String> owners = microserviceIdsForMessagingSystemId(messagingSystemId);
		Set<String> urls = new LinkedHashSet<>();
		for (String microserviceId : owners) {
			baseUrlForMicroserviceId(microserviceId).ifPresent(urls::add);
		}
		return urls;
	}

	/**
	 * The microservice id(s) hosting a given messaging system - the reverse of the
	 * topology's {@code microserviceId -> module -> [system]} map.
	 *
	 * @param messagingSystemId a system id
	 * @return the owning microservice ids (canonical, topology order); empty if the
	 *         system id is unknown
	 */
	public Set<String> microserviceIdsForMessagingSystemId(String messagingSystemId) {
		return systemToMicroservices.getOrDefault(messagingSystemId, Set.of());
	}

	// --- Accessors ----------------------------------------------------------

	/** @return the active strategy */
	public Strategy getStrategy() {
		return strategy;
	}

	/** @return the url scheme (e.g. {@code http}) */
	public String getScheme() {
		return scheme;
	}

	/** @return the effective gateway base url used in {@link Strategy#GATEWAY} */
	public String getGatewayBaseUrl() {
		return gatewayBaseUrl != null ? gatewayBaseUrl : scheme + "://" + GeboStandardMicroservices.GATEWAY_MICROSERVICE_ID;
	}

	/** @return the topology this resolver addresses */
	public GeboMicroservicesTopology topology() {
		return topology;
	}

	// --- Internals ----------------------------------------------------------

	private String buildBaseUrl(String canonicalMicroserviceId) {
		return switch (strategy) {
			case LOAD_BALANCER -> scheme + "://" + canonicalMicroserviceId;
			case GATEWAY -> getGatewayBaseUrl()
					+ normalizePath(gatewayPathTemplate.replace(MICROSERVICE_ID_PLACEHOLDER, canonicalMicroserviceId));
			// A DIRECT-strategy member with no entry has no address: surface it rather than guess.
			case DIRECT -> throw new IllegalStateException("No 'direct' base url configured for microservice '"
					+ canonicalMicroserviceId + "' (gebo.microservices.topology.url.direct). Known DIRECT entries: "
					+ directBaseUrls.keySet());
		};
	}

	private static String normalizePath(String path) {
		if (isBlank(path)) {
			return "";
		}
		String trimmed = path.trim();
		return trimmed.startsWith("/") ? trimmed : "/" + trimmed;
	}

	private static String stripTrailingSlash(String url) {
		String result = url;
		while (result.endsWith("/")) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	private static boolean isBlank(String value) {
		return value == null || value.isBlank();
	}

	@Override
	public String toString() {
		List<String> parts = new java.util.ArrayList<>();
		parts.add("strategy=" + strategy);
		parts.add("scheme=" + scheme);
		if (strategy == Strategy.GATEWAY) {
			parts.add("gatewayBaseUrl=" + getGatewayBaseUrl());
			parts.add("gatewayPathTemplate=" + gatewayPathTemplate);
		}
		if (!directBaseUrls.isEmpty()) {
			parts.add("direct=" + directBaseUrls.keySet());
		}
		return "GeboMicroserviceUrlResolver[" + String.join(", ", parts) + "]";
	}
}
