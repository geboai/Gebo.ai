package ai.gebo.architecture.mcpclients.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ai.gebo.architecture.mcpclients.model.MCPClientConfig;
import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;

public interface McpClientConfigRepository extends IGBaseMongoDBRepository<MCPClientConfig> {
	@Override
	default Class<MCPClientConfig> getManagedType() {
		return MCPClientConfig.class;
	}

	/**
	 * Paged Query-by-Example search: returns the page of MCP client configurations
	 * whose non-null fields match the given example.
	 *
	 * @param example  the probe whose populated fields are used as match criteria
	 * @param pageable the pagination/sorting information
	 * @return the matching page of configurations
	 */
	default Page<MCPClientConfig> findByQbe(MCPClientConfig example, Pageable pageable) {
		return findAll(Example.of(example), pageable);
	}
}
