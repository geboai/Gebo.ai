package gebo.microservices.api.client.factory;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Fetches the clients topology of one Gebo.ai installation and turns it into
 * complete base urls.
 *
 * <p>
 * The topology is read ONCE, lazily, on the first resolution and cached until
 * {@link #refresh()}; a deployment does not re-shape itself between two API
 * calls, and re-asking per client would put a round trip in front of every
 * factory accessor.
 * </p>
 *
 * <h2>When the call fails</h2>
 * <p>
 * A failure is treated as {@link ArchitectureType#MONOLITHIC} with the catch-all
 * entry - i.e. every service resolves to the base url itself - and logged as a
 * warning. That is deliberate: the endpoint was added after the stubs existed,
 * so a Gebo.ai server predating it answers 404, and for a monolith (the only
 * shape those older servers came in) the fallback is exactly the right answer.
 * A microservices gateway always publishes it.
 * </p>
 *
 * <p>
 * Instances are thread-safe.
 * </p>
 */
public class GeboClientsTopologyResolver {

	/** Where the topology is published, in every installation shape alike. */
	public static final String TOPOLOGY_PATH = "/public/ClientsTopologyProviderController";

	private static final Logger LOGGER = LoggerFactory.getLogger(GeboClientsTopologyResolver.class);

	private final String baseUrl;
	private final String topologyUrl;
	private final RestTemplate restTemplate;

	private volatile GeboClientsTopologyInfo topology;

	/**
	 * @param baseUrl the ONE base url of the installation - the gateway public url
	 *            in a microservices deployment, the server url in a monolithic
	 *            one; a trailing slash is stripped
	 * @param restTemplate the template used for the topology call itself; must not
	 *            be null
	 */
	public GeboClientsTopologyResolver(String baseUrl, RestTemplate restTemplate) {
		this.baseUrl = stripTrailingSlash(requireText(baseUrl, "baseUrl"));
		this.topologyUrl = this.baseUrl + TOPOLOGY_PATH;
		this.restTemplate = Objects.requireNonNull(restTemplate, "restTemplate");
	}

	/** @return the common base url this resolver completes, with no trailing slash */
	public String getBaseUrl() {
		return baseUrl;
	}

	/** @return the url the topology is read from */
	public String getTopologyUrl() {
		return topologyUrl;
	}

	/**
	 * The topology of the installation, fetched on first use and cached
	 * afterwards.
	 *
	 * @return the topology; never null - a failed call yields the monolithic
	 *         fallback described in the class javadoc
	 */
	public GeboClientsTopologyInfo getTopology() {
		GeboClientsTopologyInfo current = topology;
		if (current == null) {
			synchronized (this) {
				current = topology;
				if (current == null) {
					current = fetchTopology();
					topology = current;
				}
			}
		}
		return current;
	}

	/**
	 * Drops the cached topology, so the next resolution asks the server again.
	 * Only needed when the installation is re-shaped while the client runs.
	 */
	public void refresh() {
		synchronized (this) {
			topology = null;
		}
	}

	/**
	 * The complete base url of one service: the common base url plus the web
	 * context the installation publishes for it. Ready to hand to
	 * {@code ApiClient.setBasePath(String)}; no trailing slash.
	 *
	 * @param serviceId the service id, in either the dotted or the underscore form
	 *            (see {@link GeboMicroservices})
	 * @return the complete base url
	 * @throws IllegalStateException if a microservices installation publishes no
	 *             entry for that service - it has no address at the edge, and a
	 *             guessed url would only turn into a puzzling 404 later
	 */
	public String baseUrlFor(String serviceId) {
		return baseUrl + relativeContextUrlFor(serviceId);
	}

	/**
	 * The relative web context url published for a service, with the
	 * {@link GeboClientsTopologyInfo#DEFAULT_SERVICE_ID} fallback applied.
	 *
	 * @param serviceId the service id, in either form
	 * @return the relative context url; {@code ""} means served at the base url
	 *         itself, which is every service of a monolith
	 * @throws IllegalStateException if the service is unresolvable, see
	 *             {@link #baseUrlFor(String)}
	 */
	public String relativeContextUrlFor(String serviceId) {
		String canonical = GeboMicroservices.normalizeServiceId(serviceId);
		if (canonical == null) {
			throw new IllegalArgumentException("serviceId must not be null or blank");
		}
		GeboClientsTopologyInfo current = getTopology();

		String own = current.rawRelativeContextUrlFor(canonical);
		if (own != null) {
			return normalizeRelativeContextUrl(own);
		}
		String fallback = current.rawRelativeContextUrlFor(GeboClientsTopologyInfo.DEFAULT_SERVICE_ID);
		if (fallback != null) {
			return normalizeRelativeContextUrl(fallback);
		}
		throw new IllegalStateException("The Gebo.ai installation at " + baseUrl + " publishes no clients-topology"
				+ " entry for service " + canonical + " (architecture " + current.getArchitectureType()
				+ "), and no " + GeboClientsTopologyInfo.DEFAULT_SERVICE_ID + " fallback entry. That service is not"
				+ " reachable through this base url. Published: " + current.getServices());
	}

	private GeboClientsTopologyInfo fetchTopology() {
		try {
			GeboClientsTopologyInfo fetched = restTemplate.getForObject(topologyUrl, GeboClientsTopologyInfo.class);
			if (fetched != null && fetched.getArchitectureType() != null && !fetched.getServices().isEmpty()) {
				LOGGER.info("Gebo.ai clients topology read from {}: {}", topologyUrl, fetched);
				return fetched;
			}
			LOGGER.warn("Gebo.ai clients topology at {} answered an empty payload; falling back to the monolithic"
					+ " shape (every client points at {})", topologyUrl, baseUrl);
		} catch (RestClientException e) {
			LOGGER.warn("Gebo.ai clients topology could not be read from {} ({}); falling back to the monolithic"
					+ " shape (every client points at {}). Expected against a server predating the endpoint, which is"
					+ " always a monolith - and wrong against a microservices gateway, where the per-service web"
					+ " contexts would then be missing.", topologyUrl, e.getMessage(), baseUrl);
		}
		return monolithicFallback();
	}

	private static GeboClientsTopologyInfo monolithicFallback() {
		return new GeboClientsTopologyInfo(ArchitectureType.MONOLITHIC,
				List.of(new GeboServiceWebContextInfo(GeboClientsTopologyInfo.DEFAULT_SERVICE_ID, "")));
	}

	/**
	 * Brings a published value to the form the factory concatenates blindly:
	 * either {@code ""} or a leading-slash path with no trailing slash, so
	 * {@code baseUrl + relative} never grows a double or a dangling slash.
	 *
	 * @param relativeContextUrl the published value; may be null/blank
	 * @return the normalised relative context url, never null
	 */
	private static String normalizeRelativeContextUrl(String relativeContextUrl) {
		if (relativeContextUrl == null) {
			return "";
		}
		String trimmed = relativeContextUrl.trim();
		if (trimmed.isEmpty() || "/".equals(trimmed)) {
			return "";
		}
		String result = trimmed.startsWith("/") ? trimmed : "/" + trimmed;
		return stripTrailingSlash(result);
	}

	private static String stripTrailingSlash(String url) {
		String result = url;
		while (result.length() > 1 && result.endsWith("/")) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	private static String requireText(String value, String name) {
		if (value == null || value.trim().isEmpty()) {
			throw new IllegalArgumentException(name + " must not be null or blank");
		}
		return value.trim();
	}
}
