package ai.gebo.llms.agent.standard.config;

import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.architecture.agents.model.PipelineType;
import ai.gebo.architecture.agents.services.IGConfiguredDefaultChatNetworksOfAgents;
import lombok.Data;

@Configuration
@ConfigurationProperties(value = "ai.gebo.agents.standard")
@Data
public class StandardAgentsConfig implements IGConfiguredDefaultChatNetworksOfAgents {
	private static final Logger LOGGER = LoggerFactory.getLogger(StandardAgentsConfig.class);

	private boolean enabled = false;

	/**
	 * Hard cap on the number of chunks kept per source document by the standard
	 * document-search agents, bounding the candidate pool fed to the ranker. Set via
	 * {@code ai.gebo.agents.standard.max-chunks-per-document} in application.yml.
	 */
	private int maxChunksPerDocument = 10;

	/**
	 * The network of agents the chats with a chat profile (RAG pipeline) are handed to
	 * when neither the chat profile nor an administrator chose one. Set via
	 * {@code ai.gebo.agents.standard.default-chat-network-of-agents}.
	 */
	private String defaultChatNetworkOfAgents = AgenticLoopAgentsInitialization.AGENTIC_LOOP_AGENTS_NETWORK;

	/**
	 * The network of agents the free chats (pure chat pipeline) are handed to when no
	 * administrator chose one. Set via
	 * {@code ai.gebo.agents.standard.default-pure-chat-network-of-agents}.
	 */
	private String defaultPureChatNetworkOfAgents = AgenticLoopAgentsInitialization.AGENTIC_LOOP_PURE_CHAT_AGENTS_NETWORK;

	@Override
	public String getConfiguredDefaultChatNetworkOfAgents(PipelineType pipelineType) {
		String configured = pipelineType == PipelineType.PURE_CHAT_PIPELINE ? defaultPureChatNetworkOfAgents
				: defaultChatNetworkOfAgents;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getConfiguredDefaultChatNetworkOfAgents(" + pipelineType + ") configured:" + configured);
		}
		return configured;
	}

	@PostConstruct
	public void logResolvedConfiguration() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Standard agents configuration resolved: enabled:" + enabled + " maxChunksPerDocument:"
					+ maxChunksPerDocument + " defaultChatNetworkOfAgents:" + defaultChatNetworkOfAgents
					+ " defaultPureChatNetworkOfAgents:" + defaultPureChatNetworkOfAgents);
		}
	}
}
