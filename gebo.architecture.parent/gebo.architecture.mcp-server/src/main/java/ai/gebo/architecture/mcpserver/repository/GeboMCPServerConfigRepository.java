package ai.gebo.architecture.mcpserver.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ai.gebo.architecture.mcpserver.model.GeboMCPServerConfig;
import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;

public interface GeboMCPServerConfigRepository extends IGBaseMongoDBRepository<GeboMCPServerConfig> {
	@Override
	default Class<GeboMCPServerConfig> getManagedType() {

		return GeboMCPServerConfig.class;
	}

	/**
	 * Finds the MCP server configuration published at the given unique relative URL,
	 * used to enforce uniqueness of the {@code mcp/<url>} endpoints.
	 *
	 * @param exportedUniqueRelativeUrl the relative URL to look up
	 * @return the matching configuration, if any
	 */
	Optional<GeboMCPServerConfig> findByExportedUniqueRelativeUrl(String exportedUniqueRelativeUrl);

	

	
}
