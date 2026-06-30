package ai.gebo.architecture.mcpserver.runtime;

import java.util.List;

import org.springframework.stereotype.Service;

import ai.gebo.acl.AclGrantType;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.services.IAgentsNetworkDao;
import ai.gebo.architecture.mcpserver.model.GeboMCPAgentsNetworkTool;
import ai.gebo.architecture.mcpserver.model.GeboMCPServerConfig;
import ai.gebo.security.services.IGSecurityService;
import io.modelcontextprotocol.server.McpServerFeatures.SyncToolSpecification;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GeboMCPAgentsNetworkAsToolsProvider {
	private final IAgentsNetworkDao agentsNetworkDao;
	private final IGSecurityService securityService;

	public List<SyncToolSpecification> buildTools(GeboMCPServerConfig config) {
		List<GeboMCPAgentsNetworkTool> networks = config.getAgentNetworkAsTools();
		if (networks == null)
			networks = List.of();
		List<GAgentsNetwork> agentNetworks = networks.stream().map(x->agentsNetworkDao.findByCode(x.getAgentsNetworkCode())).filter(x->x!=null).toList();
		List<GAgentsNetwork> filtered = securityService.filterCanDoAction(agentNetworks, true, AclGrantType.EXECUTE);
		return List.of();
	}
}
