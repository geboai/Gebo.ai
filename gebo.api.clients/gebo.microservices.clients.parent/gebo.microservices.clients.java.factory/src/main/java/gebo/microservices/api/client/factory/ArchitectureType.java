package gebo.microservices.api.client.factory;

/**
 * Which Gebo.ai installation shape answered the clients-topology call.
 *
 * <p>
 * The distinction matters to a client only in one place: how an unknown service
 * id is treated. {@link #MONOLITHIC} publishes a single catch-all entry and
 * every service resolves to the base url itself; {@link #MICROSERVICES}
 * publishes one entry per service reachable at the edge, and anything else has
 * no address at all.
 * </p>
 */
public enum ArchitectureType {
	MONOLITHIC, MICROSERVICES
}
