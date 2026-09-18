package ai.gebo.architecture.environment.microservices;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Deployment overrides for the clients topology published by
 * {@link GClientsTopologyProviderImpl} under
 * {@code gebo.microservices.clients-topology}.
 *
 * <p>
 * The published set is DERIVED - from the running
 * {@code GeboMicroservicesTopology} plus the built-in
 * {@code GeboStandardMicroservices} catalogue - so a deployment normally needs
 * nothing here: declaring a service in {@code gebo.microservices.topology.*}
 * already gives it both a gateway route and a clients-topology entry, in
 * lockstep. These keys exist for the cases the derivation cannot know about: a
 * gateway that rewrites a service's path away from the naming convention, or a
 * service that must not be advertised at the edge at all.
 * </p>
 *
 * Gebo.ai comment agent
 */
@ConfigurationProperties(prefix = "gebo.microservices.clients-topology")
public class GeboClientsTopologyProperties {

	/**
	 * Whether the built-in {@code GeboStandardMicroservices} catalogue
	 * contributes its members on top of the running topology map. Kept ON by
	 * default and INDEPENDENT of
	 * {@code gebo.microservices.topology.include-defaults}: that flag governs the
	 * MESSAGING topology (and the routes derived from it), and a service can be
	 * absent from it while still being reachable at the edge - heimdall is
	 * exactly that case, hand-routed under {@code /heimdall/api/admin/**} with no
	 * messaging module of its own. Its clients still need to be told
	 * {@code /heimdall}.
	 */
	private boolean includeStandardServices = true;

	/**
	 * Explicit {@code serviceId -> relativeContextUrl} entries, added on top of
	 * the derived ones and overriding an entry derived for the same service. Keys
	 * accept either the dotted or the underscore id form; values are normalised
	 * to a leading-slash, no-trailing-slash path ({@code ""} means "served at the
	 * base url itself").
	 */
	private Map<String, String> services = new LinkedHashMap<>();

	/**
	 * Service ids to drop from the published topology, applied last - so a
	 * service the derivation picked up but that is deliberately NOT published at
	 * the edge can be withheld from the clients.
	 */
	private List<String> excludedServices = new ArrayList<>();

	public boolean isIncludeStandardServices() {
		return includeStandardServices;
	}

	public void setIncludeStandardServices(boolean includeStandardServices) {
		this.includeStandardServices = includeStandardServices;
	}

	public Map<String, String> getServices() {
		return services;
	}

	public void setServices(Map<String, String> services) {
		this.services = services == null ? new LinkedHashMap<>() : services;
	}

	public List<String> getExcludedServices() {
		return excludedServices;
	}

	public void setExcludedServices(List<String> excludedServices) {
		this.excludedServices = excludedServices == null ? new ArrayList<>() : excludedServices;
	}
}
