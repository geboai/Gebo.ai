package ai.gebo.architecture.a2aclients.service.impl;

import ai.gebo.architecture.a2aclients.model.A2ARemoteAgentConfig;

/**
 * Single source of truth for the runtime agent-service id a remote A2A agent is
 * surfaced under. The {@code IGDynamicAgentServiceSupplier} (which creates the
 * {@link RemoteA2ANetworkAgentService}) and the {@code IGDynamicAgentConfigDataSource}
 * (which creates the matching {@code GAgentConfig}) must agree on this id, since
 * the runtime pairs a config to its service by {@code agentServiceId}.
 */
public final class A2ARemoteServiceIds {

	private A2ARemoteServiceIds() {
	}

	/** {@code a2a:<exportingPrefix|code>} — stable per registered remote agent. */
	public static String serviceId(A2ARemoteAgentConfig config) {
		String key = config.getExportingPrefix();
		if (key == null || key.isBlank()) {
			key = config.getCode();
		}
		return "a2a:" + key;
	}
}
