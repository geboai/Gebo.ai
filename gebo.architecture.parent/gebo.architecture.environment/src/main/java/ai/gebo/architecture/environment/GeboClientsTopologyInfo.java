package ai.gebo.architecture.environment;

import java.util.List;
import java.util.Optional;

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
 * <p>
 * The contract a client applies is always the same, whatever the architecture
 * is: take the ONE base url it was configured with (the gateway's public url in
 * a microservices installation, the server's url in a monolithic one) and
 * append the {@code relativeContextUrl} published here for its own service id.
 * In a monolithic installation every client resolves to the single
 * {@link #DEFAULT_SERVICE_ID} entry, whose relative url is {@code ""} - i.e.
 * every generated client points straight at the base url; in a microservices
 * installation each client gets the web context the gateway maps its service
 * under (e.g. {@code /brain}). Use
 * {@link #relativeContextUrlFor(String)} rather than reading {@link #services}
 * by hand: it encodes both the id normalisation and the
 * {@link #DEFAULT_SERVICE_ID} fallback.
 * </p>
 */
@AllArgsConstructor
@Getter
public class GeboClientsTopologyInfo {
	/**
	 * The catch-all service id: the entry a client falls back to when the
	 * topology publishes no entry for its own service id. It is the ONLY entry a
	 * monolithic installation publishes, and it carries the empty relative url.
	 */
	public static final String DEFAULT_SERVICE_ID = "default";

	@AllArgsConstructor
	@Getter
	public static class GeboServiceWebContextInfo {
		/**
		 * The service this entry addresses, in the canonical dot-free form (e.g.
		 * {@code brain_gebo_ai}), or {@link #DEFAULT_SERVICE_ID}.
		 */
		private final String serviceId;
		/**
		 * The web context to append to the common base url to reach that service:
		 * a path starting with {@code '/'} and with no trailing slash (e.g.
		 * {@code /brain}), or {@code ""} when the service is served at the base url
		 * itself (always the case in a monolithic installation).
		 */
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
		return of(ArchitectureType.MONOLITHIC, List.of(new GeboServiceWebContextInfo(DEFAULT_SERVICE_ID, "")));
	}

	/**
	 * The relative web context url a client of {@code serviceId} must append to
	 * the common base url, falling back to the {@link #DEFAULT_SERVICE_ID} entry
	 * when the service has no entry of its own.
	 *
	 * @param serviceId the caller's service id, in either the dotted
	 *            ({@code brain.gebo.ai}) or the canonical underscore
	 *            ({@code brain_gebo_ai}) form; may be null
	 * @return the relative context url (possibly {@code ""}), or empty when
	 *         neither the service id nor a {@link #DEFAULT_SERVICE_ID} entry is
	 *         published
	 */
	public Optional<String> relativeContextUrlFor(String serviceId) {
		Optional<String> own = findByServiceId(normalizeServiceId(serviceId));
		return own.isPresent() ? own : findByServiceId(DEFAULT_SERVICE_ID);
	}

	private Optional<String> findByServiceId(String canonicalServiceId) {
		if (canonicalServiceId == null || services == null) {
			return Optional.empty();
		}
		return services.stream().filter(service -> canonicalServiceId.equals(normalizeServiceId(service.getServiceId())))
				.map(GeboServiceWebContextInfo::getRelativeContextUrl).findFirst();
	}

	/**
	 * The '.' -&gt; '_' canonicalisation of a service name. Deliberately restated
	 * here instead of reused from {@code GeboMicroservice.normalizeName(String)}:
	 * this module is the architecture-neutral one, shared by the monolith, and
	 * must not drag the microservices topology onto a monolithic classpath.
	 *
	 * @param serviceId a service name in either form; may be null
	 * @return the canonical id, or null
	 */
	private static String normalizeServiceId(String serviceId) {
		return serviceId == null ? null : serviceId.trim().replace('.', '_');
	}
}
