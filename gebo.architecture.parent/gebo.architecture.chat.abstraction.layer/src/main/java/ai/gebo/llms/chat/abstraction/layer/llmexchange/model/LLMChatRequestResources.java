package ai.gebo.llms.chat.abstraction.layer.llmexchange.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;

import ai.gebo.llms.abstraction.layer.model.AIDocumentsSet;
import ai.gebo.llms.abstraction.layer.model.ITokensCountable;
import ai.gebo.llms.chat.abstraction.layer.model.ChatInteractions;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LLMChatRequestResources implements ITokensCountable {
	private static final JTokkitTokenCountEstimator tokensEstimator = new JTokkitTokenCountEstimator();
	private final AIDocumentsSet ragRetrivedDocuments;
	private final AIDocumentsSet uploadedDocuments;
	private final AIDocumentsSet llmGeneratedDocuments;
	private final List<ChatInteractions> lastInteractions;
	private final GeboChatRequest lastRequest;

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

}
