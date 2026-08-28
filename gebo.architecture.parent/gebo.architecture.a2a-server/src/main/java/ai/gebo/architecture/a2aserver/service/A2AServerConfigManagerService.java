package ai.gebo.architecture.a2aserver.service;

import ai.gebo.architecture.a2aserver.model.A2AServerConfig;
import ai.gebo.model.OperationStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Administrative CRUD for published {@link A2AServerConfig}s. Every mutation keeps
 * the live {@code A2AServerRegistry} in sync so the {@code /a2a/<url>} endpoints
 * reflect the persisted state immediately, mirroring the MCP server config manager.
 */
public interface A2AServerConfigManagerService {

	OperationStatus<A2AServerConfig> insert(@NotNull @Valid A2AServerConfig config);

	OperationStatus<A2AServerConfig> update(@NotNull @Valid A2AServerConfig config);

	OperationStatus<Boolean> delete(String code);

	OperationStatus<A2AServerConfig> findByCode(String code);

	List<A2AServerConfig> findAll();
}
