package ai.gebo.llms.chat.abstraction.layer.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.config.GeboChatPromptsConfigs;
import ai.gebo.llms.chat.abstraction.layer.config.GeboRagConfigs;
import ai.gebo.llms.chat.abstraction.layer.model.ChatInteractions;
import ai.gebo.llms.chat.abstraction.layer.model.ChatProfileRuntimeEnvironment;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;
import ai.gebo.llms.chat.abstraction.layer.model.GPromptConfig;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatConsolidationData;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatProfileManagementService;
import lombok.AllArgsConstructor;

/******************************************************************************************************
 * Consolidates chat history to match the token size budget
 */
@Service
@AllArgsConstructor
public class ChatHistoryConsolidationService {

	private final GeboChatPromptsConfigs chatPromptsConfig;
	private final IGChatProfileManagementService chatProfileManagementService;
	private final IGPersistentObjectManager persistenceManager;
	private final IGChatModelRuntimeConfigurationDao chatModelConfigrationDao;
	public static final String ASSISTANT_MSG = "assistant:";
	public static final String USER_MSG = "user:";
	public static final String NEW_MESSAGES = "new_messages";
	public static final String EXISTING_SUMMARY = "existing_summary";
	public static final String HISTORY_SIZE_TARGET = "historySizeTarget";
	private static final Logger LOGGER = LoggerFactory.getLogger(ChatHistoryConsolidationService.class);
	private static final JTokkitTokenCountEstimator tokenCountEstimator = new JTokkitTokenCountEstimator();

	@Async
	public void consolidateHistory(String userContextCode, int historySizeTarget) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin consolidateHistory(" + userContextCode + "," + historySizeTarget + ")");
		}
		try {
			GUserChatContext context = persistenceManager.transactionalFindById(GUserChatContext.class,
					userContextCode);
			IGConfigurableChatModel chatModel = null;
			if (context.getChatProfileCode() != null) {
				GChatProfileConfiguration chatProfile = persistenceManager.findById(GChatProfileConfiguration.class,
						context.getChatProfileCode());
				if (chatProfile == null) {
					throw new RuntimeException("Unreachable chat profile");
				} else {
					ChatProfileRuntimeEnvironment environment = this.chatProfileManagementService
							.createChatEnvironment(chatProfile);
					chatModel = environment.getChatModel();
				}
			} else if (context.getChatModelCode() != null) {
				chatModel = chatModelConfigrationDao.findByCode(context.getChatModelCode());
			}
			if (chatModel == null) {
				chatModel = chatModelConfigrationDao.defaultHandler();
			}
			if (chatModel != null) {
				GPromptConfig prompt = chatPromptsConfig.getHistoryConsolidationPrompt();
				PromptTemplate promptTemplate = new PromptTemplate(prompt.getPrompt());
				promptTemplate.add(HISTORY_SIZE_TARGET, "" + historySizeTarget);
				StringBuffer existing_summary = new StringBuffer();
				StringBuffer new_messages = new StringBuffer();
				GUserChatConsolidationData data = context.getConsolidation();
				int minimumInteractionIndex = 0;
				if (data != null) {
					existing_summary.append(data.getConsolidationText());
					minimumInteractionIndex = data.getLastInteractionPointer() != null
							? data.getLastInteractionPointer().intValue()
							: 0;
				}
				promptTemplate.add(EXISTING_SUMMARY, existing_summary.toString());
				int leaveLastInteractionsOnHistoryConsolidation = this.chatPromptsConfig
						.getLeaveLastInteractionsOnHistoryConsolidation();
				int lastIndex = context.getInteractions().size() - leaveLastInteractionsOnHistoryConsolidation;
				if (minimumInteractionIndex >= lastIndex) {
					lastIndex = context.getInteractions().size() - leaveLastInteractionsOnHistoryConsolidation / 2;
					if (minimumInteractionIndex >= lastIndex) {
						lastIndex = context.getInteractions().size();
					}
				}
				for (int i = minimumInteractionIndex; i < lastIndex; i++) {
					ChatInteractions interaction = context.getInteractions().get(i);
					if (interaction.getRequest() != null && interaction.getRequest().getQuery() != null) {
						new_messages.append(USER_MSG + interaction.getRequest().getQuery() + "\r\n");
					}
					if (interaction.getResponse() != null && interaction.getResponse().getQueryResponse() != null) {
						new_messages.append(
								ASSISTANT_MSG + interaction.getResponse().getQueryResponse().toString() + "\r\n");
					}
				}

				promptTemplate.add(NEW_MESSAGES, new_messages.toString());
				Generation result = chatModel.getChatModel().call(promptTemplate.create()).getResult();
				if (result != null && result.getOutput() != null) {
					String historyConsolidatinText = result.getOutput().getText();
					if (historyConsolidatinText != null && historyConsolidatinText.trim().length() > 0) {
						GUserChatConsolidationData newConsolidation = new GUserChatConsolidationData();
						newConsolidation.setConsolidationText(historyConsolidatinText);
						newConsolidation.setLastInteractionPointer(lastIndex);
						newConsolidation.setTokensSize(tokenCountEstimator.estimate(historyConsolidatinText));
						context.setConsolidation(newConsolidation);
						persistenceManager.transactionalUpdate(context);
					} else
						throw new RuntimeException("History consolidation is creating ZERO LENGTH text summary");
				}

			}
		} catch (Throwable th) {
			LOGGER.error("History consolidation error", th);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End consolidateHistory(" + userContextCode + "," + historySizeTarget + ")");
		}
	}

}
