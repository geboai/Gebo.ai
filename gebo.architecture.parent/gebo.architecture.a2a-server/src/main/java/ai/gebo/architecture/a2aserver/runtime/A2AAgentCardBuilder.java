package ai.gebo.architecture.a2aserver.runtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.a2aserver.model.A2AExportedAgent;
import ai.gebo.architecture.a2aserver.model.A2AServerConfig;
import ai.gebo.architecture.agents.model.AgentCapabilities;
import ai.gebo.architecture.agents.model.AgentCapabilityResource;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.services.IAgentConfigDao;
import ai.gebo.architecture.agents.services.IAgentsNetworkDao;
import ai.gebo.architecture.agents.services.IGAgentServiceRuntimeDao;
import ai.gebo.architecture.agents.services.IGGenericAgentService;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import lombok.AllArgsConstructor;
import org.a2aproject.sdk.spec.AgentCard;
import org.a2aproject.sdk.spec.AgentSkill;

/**
 * Builds the A2A {@link AgentCard} for a published {@link A2AServerConfig}: one
 * A2A {@link AgentSkill} per exported agent/network, projected from the Gebo
 * capability model.
 * <p>
 * For a {@link A2AExportedAgent.Kind#NETWORK} export the skill is derived from the
 * network's {@code scenarioDescription} (opaque by default); for a
 * {@link A2AExportedAgent.Kind#AGENT} export it is derived from the agent service's
 * {@link IGGenericAgentService#getAgentCapabilities(GAgentConfig)}. When
 * {@code exposeMemberCapabilities} is set on a network export, the member agents'
 * capabilities are folded into the skill description/tags for richer discovery.
 */
@Service
@AllArgsConstructor
public class A2AAgentCardBuilder {

	private static final Logger LOGGER = LoggerFactory.getLogger(A2AAgentCardBuilder.class);

	private static final List<String> TEXT_MODES = List.of("text/plain");
	private static final String BEARER_SCHEME = "bearer";

	private final IGRuntimeBinder runtimeBinder;

	/**
	 * Builds the Agent Card served at {@code <cardUrl>/.well-known/agent-card.json}.
	 *
	 * @param config     the published server configuration
	 * @param cardUrl    the absolute URL the card is served from (also the JSON-RPC url)
	 */
	public AgentCard build(A2AServerConfig config, String cardUrl) {
		List<AgentSkill> skills = new ArrayList<>();
		if (config.getExportedAgents() != null) {
			for (A2AExportedAgent exported : config.getExportedAgents()) {
				try {
					AgentSkill skill = buildSkill(exported);
					if (skill != null) {
						skills.add(skill);
					}
				} catch (Throwable t) {
					LOGGER.warn("Skipping A2A export entry (skillName={}) due to error: {}", exported.getSkillName(),
							t.getMessage());
				}
			}
		}
		String name = config.getDescription() != null && !config.getDescription().isBlank() ? config.getDescription()
				: config.getExportedRelativeUrl();
		return AgentCard.builder().name(name)
				.description("Gebo.ai agents exported over A2A: " + config.getExportedRelativeUrl())
				.version("1.0.0").url(cardUrl).preferredTransport("JSONRPC")
				.capabilities(org.a2aproject.sdk.spec.AgentCapabilities.builder().streaming(true)
						.pushNotifications(false).extendedAgentCard(false).extensions(List.of()).build())
				.defaultInputModes(TEXT_MODES).defaultOutputModes(TEXT_MODES).skills(skills)
				// Advertise that callers must present a Bearer token; the platform security
				// chain (self-issued JWT / API key, or OAuth2 resource server) validates it,
				// and the exported network runs impersonating the resolved caller. Both modes
				// present a bearer token, so a single HTTP bearer scheme is accurate.
				.securitySchemes(Map.of(BEARER_SCHEME, org.a2aproject.sdk.spec.HTTPAuthSecurityScheme.builder()
						.scheme("bearer").bearerFormat("JWT")
						.description("Bearer token validated by the platform security chain "
								+ "(self-issued JWT / API key, or OAuth2 resource server)")
						.build()))
				.securityRequirements(List.of(new org.a2aproject.sdk.spec.SecurityRequirement(
						Map.of(BEARER_SCHEME, List.of()))))
				.supportedInterfaces(List.of()).signatures(List.of()).additionalInterfaces(List.of()).build();
	}

	private AgentSkill buildSkill(A2AExportedAgent exported) {
		if (exported.getKind() == A2AExportedAgent.Kind.NETWORK) {
			return buildNetworkSkill(exported);
		}
		return buildAgentSkill(exported);
	}

	private AgentSkill buildNetworkSkill(A2AExportedAgent exported) {
		IAgentsNetworkDao networkDao = runtimeBinder.getImplementationOf(IAgentsNetworkDao.class);
		GAgentsNetwork network = networkDao.findByCode(exported.getNetworkCode());
		if (network == null) {
			LOGGER.warn("Exported network '{}' not found; skill skipped", exported.getNetworkCode());
			return null;
		}
		String id = skillId(exported, network.getCode());
		String name = exported.getSkillName() != null ? exported.getSkillName() : network.getCode();
		String description = network.getScenarioDescription() != null ? network.getScenarioDescription()
				: network.getDescription();
		List<String> tags = new ArrayList<>();
		if (Boolean.TRUE.equals(exported.getExposeMemberCapabilities())) {
			appendMemberCapabilities(network, tags);
		}
		return AgentSkill.builder().id(id).name(name).description(description).tags(tags).examples(List.of())
				.inputModes(TEXT_MODES).outputModes(TEXT_MODES).securityRequirements(List.of()).build();
	}

	private AgentSkill buildAgentSkill(A2AExportedAgent exported) {
		IGAgentServiceRuntimeDao serviceDao = runtimeBinder.getImplementationOf(IGAgentServiceRuntimeDao.class);
		IAgentConfigDao configDao = runtimeBinder.getImplementationOf(IAgentConfigDao.class);
		GAgentConfig agentConfig = configDao.findByCode(exported.getAgentConfigCode());
		String serviceId = agentConfig != null ? agentConfig.getAgentServiceId() : exported.getAgentConfigCode();
		IGGenericAgentService service = serviceId != null ? serviceDao.findByCode(serviceId) : null;
		String id = skillId(exported, exported.getAgentConfigCode());
		String name = exported.getSkillName() != null ? exported.getSkillName() : exported.getAgentConfigCode();
		String description;
		List<String> tags = new ArrayList<>();
		if (service != null) {
			AgentCapabilities caps = service.getAgentCapabilities(agentConfig);
			description = caps.getSummary() != null ? caps.getSummary() : service.getDescription();
			tags.addAll(caps.getCapabilities());
			for (AgentCapabilityResource tool : caps.getTools()) {
				tags.add(tool.getName() != null ? tool.getName() : tool.getCode());
			}
		} else {
			description = "Exported Gebo agent " + exported.getAgentConfigCode();
		}
		return AgentSkill.builder().id(id).name(name).description(description).tags(tags).examples(List.of())
				.inputModes(TEXT_MODES).outputModes(TEXT_MODES).securityRequirements(List.of()).build();
	}

	private void appendMemberCapabilities(GAgentsNetwork network, List<String> tags) {
		IGAgentServiceRuntimeDao serviceDao = runtimeBinder.getImplementationOf(IGAgentServiceRuntimeDao.class);
		IAgentConfigDao configDao = runtimeBinder.getImplementationOf(IAgentConfigDao.class);
		if (network.getAgents() == null) {
			return;
		}
		for (GAgentsNetwork.AgentNetworkParticipant participant : network.getAgents()) {
			GAgentConfig cfg = configDao.findByCode(participant.getAgentConfigCode());
			String serviceId = cfg != null ? cfg.getAgentServiceId() : participant.getAgentConfigCode();
			IGGenericAgentService service = serviceId != null ? serviceDao.findByCode(serviceId) : null;
			if (service != null) {
				AgentCapabilities caps = service.getAgentCapabilities(cfg);
				tags.addAll(caps.getCapabilities());
			}
		}
	}

	private String skillId(A2AExportedAgent exported, String fallback) {
		return exported.getSkillName() != null && !exported.getSkillName().isBlank() ? exported.getSkillName()
				: fallback;
	}
}
