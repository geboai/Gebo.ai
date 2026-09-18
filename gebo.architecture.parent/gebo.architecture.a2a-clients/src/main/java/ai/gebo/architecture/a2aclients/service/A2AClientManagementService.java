package ai.gebo.architecture.a2aclients.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ai.gebo.architecture.a2aclients.model.A2ARemoteAgentConfig;
import ai.gebo.model.OperationStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * Administrative CRUD + connectivity/discovery for registered remote A2A agents,
 * mirroring {@code McpClientManagementService}.
 */
public interface A2AClientManagementService {

	/**
	 * Fetches the remote Agent Card and reconciles its skills against the ones
	 * stored on the config: on first discovery (config not yet persisted) every
	 * skill is flagged {@code addedOnRemote}; otherwise skills newly present are
	 * flagged added, skills gone from the remote are flagged deleted, and unchanged
	 * skills keep their stored state.
	 *
	 * @param config the connection config to probe
	 * @return the diffed config, or the diagnostic errors if the agent is unreachable
	 */
	OperationStatus<A2ARemoteAgentConfig> testAndDiscovery(@NotNull @Valid A2ARemoteAgentConfig config);

	OperationStatus<A2ARemoteAgentConfig> insert(@NotNull @Valid A2ARemoteAgentConfig config);

	OperationStatus<A2ARemoteAgentConfig> update(@NotNull @Valid A2ARemoteAgentConfig config);

	OperationStatus<Boolean> delete(@NotNull @Valid A2ARemoteAgentConfig config);

	OperationStatus<A2ARemoteAgentConfig> findByCode(String code);

	Page<A2ARemoteAgentConfig> list(Pageable pageable);
}
