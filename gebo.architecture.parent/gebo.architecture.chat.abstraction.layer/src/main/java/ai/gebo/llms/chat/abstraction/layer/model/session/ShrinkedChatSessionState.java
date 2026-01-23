package ai.gebo.llms.chat.abstraction.layer.model.session;

import ai.gebo.llms.chat.abstraction.layer.model.GUserChatInteractionsConsolidationData;
import lombok.Data;

@Data
public class ShrinkedChatSessionState {
	private CSSfRelevantShrinkedDocumentList relevantUploadedDocuments = new CSSfRelevantShrinkedDocumentList();
	private CSSfRelevantShrinkedDocumentList relevantRagRetrievedDocuments = new CSSfRelevantShrinkedDocumentList();
	private CSSfRelevantShrinkedDocumentList relevantLlmGeneratedDocuments = new CSSfRelevantShrinkedDocumentList();
	private GUserChatInteractionsConsolidationData consolidatedInteractions = null;
	

}
