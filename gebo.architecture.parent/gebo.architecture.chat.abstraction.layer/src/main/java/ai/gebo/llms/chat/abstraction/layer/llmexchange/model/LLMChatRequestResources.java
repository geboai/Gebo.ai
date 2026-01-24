package ai.gebo.llms.chat.abstraction.layer.llmexchange.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;

import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.rag.support.layer.model.ITokensCountable;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.model.IQuestionAnswerEntry;
import ai.gebo.llms.chat.abstraction.layer.model.ChatInteractions;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LLMChatRequestResources implements ITokensCountable, IChatRequestContext {
	private static final JTokkitTokenCountEstimator tokensEstimator = new JTokkitTokenCountEstimator();
	private final AIDocumentsSet ragRetrivedDocuments;
	private final AIDocumentsSet uploadedDocuments;
	private final AIDocumentsSet llmGeneratedDocuments;
	private final String chatConsolidation;
	private final List<ChatInteractions> lastInteractions;
	private final GeboChatRequest lastRequest;

	@AllArgsConstructor
	static final class InteractionWrapper implements IQuestionAnswerEntry {
		ChatInteractions interaction = null;

		@Override
		public String getUser() {

			return interaction.getRequest() != null ? interaction.getRequest().getQuery() : "";
		}

		@Override
		public String getAssistant() {

			return interaction.getResponse() != null ? interaction.getResponse().getQueryResponse().toString() : "";
		}
	}

	@Override
	public int getTokensSize() {
		int size = 0;
		if (lastRequest != null && lastRequest.getQuery() != null) {
			size += tokensEstimator.estimate(lastRequest.getQuery());
		}
		size += tokensSize(ragRetrivedDocuments, uploadedDocuments, llmGeneratedDocuments);
		size += tokensSize(lastInteractions);
		return size;
	}

	@Override
	public String getConsolidatedHistory() {

		return chatConsolidation != null ? chatConsolidation : "";
	}

	@Override
	public List<IQuestionAnswerEntry> getInteractions() {
		List<IQuestionAnswerEntry> entries = new ArrayList<IQuestionAnswerEntry>();
		if (lastInteractions != null) {
			for (ChatInteractions i : lastInteractions) {
				entries.add(new InteractionWrapper(i));
			}
		}
		return entries;
	}

	@Override
	public List<Document> getDocuments() {
		List<Document> data = new ArrayList<Document>();
		if (ragRetrivedDocuments != null) {
			data.addAll(ragRetrivedDocuments.aiDocumentsList());
		}
		if (uploadedDocuments != null) {
			data.addAll(uploadedDocuments.aiDocumentsList());
		}
		if (llmGeneratedDocuments != null) {
			data.addAll(llmGeneratedDocuments.aiDocumentsList());
		}
		return data;
	}

	@Override
	public String getActualUserRequest() {

		return lastRequest.getQuery();
	}

	@Override
	public Map<String, Object> getToolsContext() {

		return new HashMap<String, Object>();
	}

}
