package ai.gebo.architecture.environment.microservices;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.environment.ArchitectureType;
import ai.gebo.architecture.environment.GeboClientsTopologyInfo;
import ai.gebo.architecture.environment.GeboClientsTopologyInfo.GeboServiceWebContextInfo;
import ai.gebo.architecture.environment.IGClientsTopologyProvider;
import ai.gebo.microservices.topology.GeboMicroservice;
import ai.gebo.microservices.topology.GeboMicroservicesTopology;
import ai.gebo.microservices.topology.GeboStandardMicroservices;

/**
 * The microservices-architecture counterpart of
 * {@code ai.gebo.architecture.environment.monolithic.GClientsTopologyProviderImpl}:
 * it answers the SAME question - "what must a client append to the one base url
 * it is configured with?" - from the live microservices topology instead of the
 * monolith's fixed {@code ""}.
 *
 * <h2>Where the relative urls come from</h2>
 * <p>
 * Nothing is hardcoded here. Every published {@code relativeContextUrl} is the
 * target service's {@link GeboMicroservice#getContextPath()} - the single value
 * three things already share by construction (see that method's javadoc): the
 * backend's own {@code server.servlet.context-path}, the gateway's
 * topology-derived route ({@code /<contextName>/**  ->  lb://<discoveryServiceId>},
 * NO {@code StripPrefix}), and the segment
 * {@code GeboMicroserviceUrlResolver}'s {@code GATEWAY} strategy appends to
 * every base url it resolves. So a client that concatenates the gateway's
 * public url with what this provider hands it lands on exactly the path the
 * backend serves, and stays in lockstep with the routing table: declaring a
 * service in {@code gebo.microservices.topology.services} gives it a route and
 * a clients-topology entry in one edit, with no rebuild.
 * </p>
 *
 * <h2>Which services are published</h2>
 * <ol>
 * <li>every member of the running {@link GeboMicroservicesTopology} - the
 * deployment's own, yml-bound map;</li>
 * <li>then the built-in {@link GeboStandardMicroservices#DEFAULTS} catalogue,
 * for the services that are routable at the edge without hosting a messaging
 * module and therefore have no entry in that map (heimdall is the standing
 * example - hand-routed under {@code /heimdall/api/admin/**}); switch off with
 * {@code gebo.microservices.clients-topology.include-standard-services};</li>
 * <li>then the gateway itself, under the empty relative url: the gateway IS the
 * common base url, so its own client appends nothing;</li>
 * <li>then the explicit
 * {@code gebo.microservices.clients-topology.services} entries, which add a
 * service or override a derived one;</li>
 * <li>and finally {@code ...clients-topology.excluded-services} is subtracted.</li>
 * </ol>
 *
 * <p>
 * Deliberately NO {@code default} entry
 * ({@link GeboClientsTopologyInfo#DEFAULT_SERVICE_ID}) is published: in a
 * microservices installation a client whose service is not in the topology has
 * no address at the edge, and must surface that rather than silently fall back
 * to the gateway root and 404 on every call. The monolithic provider publishes
 * that entry - and only that entry - precisely because there the fallback IS
 * the whole answer.
 * </p>
 *
 * <p>
 * The topology and the properties are both immutable once bound, so the answer
 * is computed once at construction and shared; the instance is thread-safe.
 * </p>
 *
 * Gebo.ai comment agent
 */
@Service
public class GClientsTopologyProviderImpl implements IGClientsTopologyProvider {

	private static final Logger LOGGER = LoggerFactory.getLogger(GClientsTopologyProviderImpl.class);

	/** The gateway is the common base url itself - its client appends nothing. */
	private static final String GATEWAY_RELATIVE_CONTEXT_URL = "";

	private final GeboClientsTopologyInfo topologyInfo;

	public GClientsTopologyProviderImpl(GeboMicroservicesTopology topology,
			GeboClientsTopologyProperties properties) {
		this.topologyInfo = buildTopologyInfo(topology, properties);
		LOGGER.info("Microservices clients topology published for {} service(s): {}",
				topologyInfo.getServices().size(), describe(topologyInfo));
	}

	@Override
	public GeboClientsTopologyInfo getTopology() {
		return topologyInfo;
	}

	private static GeboClientsTopologyInfo buildTopologyInfo(GeboMicroservicesTopology topology,
			GeboClientsTopologyProperties properties) {
		// Insertion-ordered: topology order first, then the catalogue additions,
		// then the gateway, then the deployment's own entries - and a later put on
		// the same key overrides the value while KEEPING the original position, so
		// an override never reshuffles the published list.
		Map<String, String> byServiceId = new LinkedHashMap<>();

		for (GeboMicroservice microservice : topology.microservices()) {
			byServiceId.put(microservice.getMicroserviceId(), microservice.getContextPath());
		}
		if (properties.isIncludeStandardServices()) {
			for (GeboMicroservice microservice : GeboStandardMicroservices.DEFAULTS) {
				byServiceId.putIfAbsent(microservice.getMicroserviceId(), microservice.getContextPath());
			}
		}
		byServiceId.put(GeboStandardMicroservices.GATEWAY_MICROSERVICE_ID, GATEWAY_RELATIVE_CONTEXT_URL);

		properties.getServices().forEach((serviceId, relativeContextUrl) -> {
			String canonical = GeboMicroservice.normalizeName(trimToNull(serviceId));
			if (canonical != null) {
				byServiceId.put(canonical, relativeContextUrl);
			}
		});

		for (String excluded : properties.getExcludedServices()) {
			String canonical = GeboMicroservice.normalizeName(trimToNull(excluded));
			if (canonical != null) {
				byServiceId.remove(canonical);
			}
		}

		List<GeboServiceWebContextInfo> services = new ArrayList<>();
		byServiceId.forEach((serviceId, relativeContextUrl) -> services
				.add(new GeboServiceWebContextInfo(serviceId, normalizeRelativeContextUrl(relativeContextUrl))));
		return GeboClientsTopologyInfo.of(ArchitectureType.MICROSERVICES, List.copyOf(services));
	}

	/**
	 * Brings a configured value to the form clients concatenate blindly: either
	 * {@code ""} or a leading-slash path with no trailing slash. Without this a
	 * hand-written {@code brain/} or {@code /brain/} in yml would produce
	 * {@code <base>brain//api/...} once appended.
	 *
	 * @param relativeContextUrl the raw value; may be null/blank
	 * @return the normalised relative context url, never null
	 */
	private static String normalizeRelativeContextUrl(String relativeContextUrl) {
		String trimmed = trimToNull(relativeContextUrl);
		if (trimmed == null || "/".equals(trimmed)) {
			return "";
		}
		String result = trimmed.startsWith("/") ? trimmed : "/" + trimmed;
		while (result.endsWith("/")) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	private static String trimToNull(String value) {
		if (value == null) {
			return null;
		}
		String trimmed = value.trim();
		return trimmed.isEmpty() ? null : trimmed;
	}

	private static String describe(GeboClientsTopologyInfo info) {
		Set<String> descriptions = new LinkedHashSet<>();
		info.getServices().forEach(service -> descriptions
				.add(service.getServiceId() + "->'" + service.getRelativeContextUrl() + "'"));
		return String.join(", ", descriptions);
	}
}
