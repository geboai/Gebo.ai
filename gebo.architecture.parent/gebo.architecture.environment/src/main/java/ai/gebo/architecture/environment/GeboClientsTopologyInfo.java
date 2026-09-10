package ai.gebo.architecture.environment;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/*****************************************************************
 * This class gives to a Gebo.ai monolithic/microservices client the information
 * on the actual topology of the rest/reactive web mappings in term of
 * microservices clients web context relativeUrl(s) to be added to the main base
 * url. With this being received an application having all microservices clients
 * can configure pointing to all microservices endpoints (that are accessed via
 * a gateway with a common base url) or to a monolithic server.
 * 
 */
@AllArgsConstructor
@Getter
public class GeboClientsTopologyInfo {
	@AllArgsConstructor
	public static class GeboServiceWebContextInfo {
		private final String serviceId;
		private final String relativeContextUrl;
	}

	@NotNull
	private final ArchitectureType architectureType;
	@NotNull
	@NotEmpty
	private final List<GeboServiceWebContextInfo> services;

	public static GeboClientsTopologyInfo of(ArchitectureType architectureType,
			List<GeboServiceWebContextInfo> services) {
		return new GeboClientsTopologyInfo(architectureType, services);
	}

	public static GeboClientsTopologyInfo ofMonolithic() {
		return of(ArchitectureType.MONOLITHIC, List.of(new GeboServiceWebContextInfo("default", "")));
	}
}
