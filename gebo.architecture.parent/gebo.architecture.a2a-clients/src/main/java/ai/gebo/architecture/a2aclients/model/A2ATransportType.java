package ai.gebo.architecture.a2aclients.model;

/**
 * The A2A client transport used to reach a remote agent. The first cut wires
 * {@link #JSONRPC} (JSON-RPC 2.0 over HTTP(S), with SSE for {@code message/stream}),
 * which is the A2A default and the transport the SDK's
 * {@code a2a-java-sdk-client-transport-jsonrpc} implements. {@link #REST} and
 * {@link #GRPC} are reserved for later and are rejected by the connector until
 * wired.
 */
public enum A2ATransportType {
	JSONRPC, REST, GRPC
}
