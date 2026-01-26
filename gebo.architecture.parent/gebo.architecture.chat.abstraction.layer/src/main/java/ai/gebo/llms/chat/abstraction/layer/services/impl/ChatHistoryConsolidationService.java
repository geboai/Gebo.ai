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
import ai.gebo.llms.abstraction.layer.services.BaseLlmsInvokingService;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.config.GeboChatPromptsConfigs;
import ai.gebo.llms.chat.abstraction.layer.config.GeboChatConfigs;
import ai.gebo.llms.chat.abstraction.layer.model.ChatInteractions;
import ai.gebo.llms.chat.abstraction.layer.model.ChatProfileRuntimeEnvironment;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;
import ai.gebo.llms.chat.abstraction.layer.model.GPromptConfig;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatInteractionsConsolidationData;
import ai.gebo.llms.chat.abstraction.layer.model.session.ChatFullSessionState;
import ai.gebo.llms.chat.abstraction.layer.model.session.ShrinkedChatSessionState;
import ai.gebo.llms.chat.abstraction.layer.repository.ShrinkedChatSessionStateRepository;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatProfileManagementService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionStateService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionStateShrinkerService;
import lombok.AllArgsConstructor;

/******************************************************************************************************
 * Consolidates chat history to match the token size budget
 */
@Service
@AllArgsConstructor
public class ChatHistoryConsolidationService {
	private final IGChatSessionStateService sessionStateService;
	private final IGChatSessionStateShrinkerService shrinkerService;
	private final IGPersistentObjectManager persistenceManager;
	private final ShrinkedChatSessionStateRepository shrinkedRepository;
	private static final Logger LOGGER = LoggerFactory.getLogger(ChatHistoryConsolidationService.class);

	@Async
	public void consolidateHistory(String userContextCode, int historySizeTarget) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin consolidateHistory(" + userContextCode + "," + historySizeTarget + ")");
		}
		try {
			GUserChatContext context = persistenceManager.transactionalFindById(GUserChatContext.class,
					userContextCode);
			ChatFullSessionState extractedFullState = sessionStateService.extractState(null, context);
			ShrinkedChatSessionState shrinked = shrinkerService.shrink(extractedFullState, historySizeTarget);
			shrinkedRepository.save(shrinked);
		} catch (Throwable th) {
			LOGGER.error("History consolidation error", th);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End consolidateHistory(" + userContextCode + "," + historySizeTarget + ")");
		}
	}

}
