package ai.gebo.llms.chat.abstraction.layer.session.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext.ChatRequestContextImpl.ChatRequestContextImplBuilder;
import ai.gebo.llms.abstraction.layer.model.IChatSessionEntry;
import ai.gebo.llms.abstraction.layer.model.IChatSessionEntry.ChatSessionEntryImpl.ChatSessionEntryImplBuilder;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MinimalChatContext implements ITokensCountable {
	@NotNull
	private GeboChatRequest currentRequest = null;
	@NotNull
	private CSSConsolidatedChatHistory chatHistory = new CSSConsolidatedChatHistory();
	/**
	 * The recorder of the tools called while answering the current request (see
	 * {@code LLMChatRequestResources#getToolCallsListener()}), never persisted.
	 */
	@JsonIgnore
	private transient ToolCallsListener toolCallsListener = null;
	/**
	 * The codes of the knowledge bases of the chat (its chat profile's), given to the
	 * tools called through this context; computed per request, never persisted.
	 */
	@JsonIgnore
	private transient List<String> availableKnowledgeBaseCodes = null;

	@Override
	public int getTokensSize() {

		return ITokensCountable.tokensSize(currentRequest, chatHistory);
	}

	public IChatRequestContext createChatRequestContext() {
		ChatRequestContextImplBuilder builder = IChatRequestContext.builder();
		// the tools called through these contexts are recorded for the request, and know
		// which request they are called for
		builder = builder.toolCallListener(toolCallsListener);
		Map<String, Object> toolsContext = new HashMap<>();
		if (this.currentRequest != null) {
			builder = builder.actualUserRequest(GeboChatRequest.actualQuery(this.currentRequest))
					.sessionID(currentRequest.getUserChatContextCode()).requestID(currentRequest.getId());
			if (currentRequest.getId() != null) {
				toolsContext.put(ToolCallbackDeclarationUtil.REQUEST_ID_CONTEXT_KEY, currentRequest.getId());
			}
		}
		// the knowledge bases the tools may search, browse and read: the chat's
		if (availableKnowledgeBaseCodes != null) {
			toolsContext.put(ToolCallbackDeclarationUtil.CHAT_KNOWLEDGE_BASES_CONTEXT_KEY,
					List.copyOf(availableKnowledgeBaseCodes));
		}
		builder = builder.toolsContext(toolsContext);
		if (this.chatHistory != null) {
			String consolidated = this.chatHistory.getConsolidationText();
			if (consolidated != null) {
				builder = builder.consolidatedHistory(consolidated);
			}
			List<IChatSessionEntry> interactions = new ArrayList<>();

			if (chatHistory.getLatestEntries() != null && chatHistory.getLatestEntries().getInteractions() != null) {
				for (CSSSimplefiedInteraction entry : chatHistory.getLatestEntries().getInteractions()) {
					ChatSessionEntryImplBuilder sBuilder = IChatSessionEntry.builder();
					sBuilder.user(entry.getUser() != null ? entry.getUser() : "<<empty text>>");
					sBuilder.assistant(entry.getAssistant() != null ? entry.getAssistant() : "<<empty text>>");
					interactions.add(sBuilder.build());
				}
			}
			builder.interactions(interactions);

		}
		if (currentRequest != null) {
			Map<String, Object> pipelineInfos = new HashMap<>();
			if (currentRequest.getChatPipelineProcessId() != null) {
				pipelineInfos.put("user-choosed-pipelineId", currentRequest.getChatPipelineProcessId());
			}
			if (currentRequest.getUserIntent() != null) {
				pipelineInfos.put("user-intent", currentRequest.getUserIntent().name());
			}
			builder.pipelineInfos(pipelineInfos);
		}

		return builder.build();
	}
}
