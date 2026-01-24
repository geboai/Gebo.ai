package ai.gebo.llms.chat.abstraction.layer.model.session;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.llms.abstraction.layer.model.ITokensCountable;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatInteractionsConsolidationData;
import lombok.Data;

@Document
@Data
public class ShrinkedChatSessionState implements ITokensCountable {
	/** Code representing the user's chat context */
	@Id
	protected String userChatContextCode = null;
	private int tokensSize = 0;
	private CSSfRelevantShrinkedDocumentList relevantUploadedDocuments = new CSSfRelevantShrinkedDocumentList();
	private CSSfRelevantShrinkedDocumentList relevantRagRetrievedDocuments = new CSSfRelevantShrinkedDocumentList();
	private CSSfRelevantShrinkedDocumentList relevantLlmGeneratedDocuments = new CSSfRelevantShrinkedDocumentList();
	private GUserChatInteractionsConsolidationData consolidatedInteractions = null;

	@Override
	public int getTokensSize() {
		return tokensSize(relevantLlmGeneratedDocuments, relevantRagRetrievedDocuments, relevantUploadedDocuments,
				consolidatedInteractions);
	}

}
