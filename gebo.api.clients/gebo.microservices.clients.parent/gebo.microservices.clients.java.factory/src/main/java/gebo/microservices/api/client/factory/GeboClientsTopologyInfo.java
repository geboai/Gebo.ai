package gebo.microservices.api.client.factory;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The answer of {@code GET <baseUrl>/public/ClientsTopologyProviderController}:
 * how a client completes the one base url it was configured with, per service.
 *
 * @see GeboMicroservicesClientsFactory
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeboClientsTopologyInfo {

	/**
	 * The catch-all service id. It is the only entry a monolithic installation
	 * publishes, and it carries the empty relative url - so every client of a
	 * monolith resolves to the base url itself.
	 */
	public static final String DEFAULT_SERVICE_ID = "default";

	private ArchitectureType architectureType;
	private List<GeboServiceWebContextInfo> services = new ArrayList<>();

	public GeboClientsTopologyInfo() {
	}

	public GeboClientsTopologyInfo(ArchitectureType architectureType, List<GeboServiceWebContextInfo> services) {
		this.architectureType = architectureType;
		setServices(services);
	}

	public ArchitectureType getArchitectureType() {
		return architectureType;
	}

	public void setArchitectureType(ArchitectureType architectureType) {
		this.architectureType = architectureType;
	}

	public List<GeboServiceWebContextInfo> getServices() {
		return services;
	}

	public void setServices(List<GeboServiceWebContextInfo> services) {
		this.services = services == null ? new ArrayList<>() : new ArrayList<>(services);
	}

	/**
	 * The relative web context url published for a service, WITHOUT the
	 * {@link #DEFAULT_SERVICE_ID} fallback - see
	 * {@link GeboClientsTopologyResolver#relativeContextUrlFor(String)} for the
	 * resolution a caller actually wants.
	 *
	 * @param serviceId a service id in either the dotted or the underscore form
	 * @return the relative context url, or {@code null} when the service has no
	 *         entry of its own
	 */
	public String rawRelativeContextUrlFor(String serviceId) {
		String canonical = GeboMicroservices.normalizeServiceId(serviceId);
		if (canonical == null) {
			return null;
		}
		for (GeboServiceWebContextInfo service : services) {
			if (canonical.equals(GeboMicroservices.normalizeServiceId(service.getServiceId()))) {
				return service.getRelativeContextUrl();
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "GeboClientsTopologyInfo[" + architectureType + ", " + services + "]";
	}
}
