package ai.gebo.topology_provider.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.environment.GeboClientsTopologyInfo;
import ai.gebo.architecture.environment.IGClientsTopologyProvider;
import lombok.AllArgsConstructor;

/**
 * The single endpoint every Gebo.ai client bootstraps its base urls from.
 *
 * <p>
 * A client application is configured with ONE url - the common base url - and
 * asks this controller, at the same relative path in every installation, how to
 * complete it for each generated microservice client. What comes back is
 * architecture-specific but the client code is not: a monolithic installation
 * answers with the single {@code default -> ""} entry (every client points
 * straight at the base url), a microservices installation answers with the web
 * context the gateway maps each service under (e.g. {@code brain_gebo_ai ->
 * /brain}), derived live from the deployment's topology.
 * </p>
 *
 * <p>
 * The answer is produced by whichever {@link IGClientsTopologyProvider} the
 * deployable's architecture module contributes - exactly one is ever on a given
 * classpath - so this module is deployed unchanged on the monolith and on
 * gateway.gebo.ai, and both publish the same relative url.
 * </p>
 *
 * <p>
 * Mapped under {@code /public/} on purpose: a client must be able to resolve
 * the topology BEFORE it can authenticate anywhere, and the payload is routing
 * metadata the deployment already publishes as its gateway routes.
 * </p>
 *
 * Gebo.ai comment agent
 */
@RestController
@RequestMapping("/public/ClientsTopologyProviderController")
@AllArgsConstructor
public class ClientsTopologyProviderController {
	private final IGClientsTopologyProvider topologyProvider;

	/**
	 * @return the clients topology of this installation: the architecture type
	 *         and, per service id, the relative web context url to append to the
	 *         common base url
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public GeboClientsTopologyInfo getClientsTopology() {
		return topologyProvider.getTopology();
	}

}
