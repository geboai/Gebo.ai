package ai.gebo.llms.chat.abstraction.layer.model.session;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.architecture.rag.support.layer.model.ITokensCountable;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatInteractionsConsolidationData;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Document
@Data
public class ShrinkedChatSessionState implements ITokensCountable {
	/** Code representing the user's chat context */
	@Id
	@NotNull
	private String userChatContextCode = null;
	private CSSfRelevantShrinkedDocumentList relevantUploadedDocuments = new CSSfRelevantShrinkedDocumentList();
	private CSSfRelevantShrinkedDocumentList relevantRetrievedDocuments = new CSSfRelevantShrinkedDocumentList();
	private CSSfRelevantShrinkedDocumentList relevantLlmGeneratedDocuments = new CSSfRelevantShrinkedDocumentList();
	@NotNull
	private GUserChatInteractionsConsolidationData consolidatedInteractions = null;

	@Override
	public int getTokensSize() {
		return ITokensCountable.tokensSize(relevantLlmGeneratedDocuments, relevantRetrievedDocuments, relevantUploadedDocuments,
				consolidatedInteractions);
	}

}
