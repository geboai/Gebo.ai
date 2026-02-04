package ai.gebo.llms.chat.abstraction.layer.model.session;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.architecture.rag.support.layer.model.AIDocumentFragment;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentReferenceItem;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.rag.support.layer.model.ITokensCountable;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.UserUploadedContent;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatInteractionsConsolidationData;
import ai.gebo.llms.chat.abstraction.layer.model.TokensContainer;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Document
@Data
public class ShrinkedChatSessionState implements ITokensCountable, IChatRequestFactory {
	/** Code representing the user's chat context */
	@Id
	@NotNull
	private String userChatContextCode = null;
	private AIDocumentsSet latestRequestsUploadedDocuments = new AIDocumentsSet();
	private AIDocumentsSet latestRequestsChatWithDocuments = new AIDocumentsSet();
	private AIDocumentsSet latestRequestsRetrievedDocuments = new AIDocumentsSet();;

	private CSSfRelevantShrinkedDocumentList relevantUploadedDocuments = new CSSfRelevantShrinkedDocumentList();
	private CSSfRelevantShrinkedDocumentList relevantRetrievedDocuments = new CSSfRelevantShrinkedDocumentList();
	private CSSfRelevantShrinkedDocumentList relevantLlmGeneratedDocuments = new CSSfRelevantShrinkedDocumentList();
	@NotNull
	private GUserChatInteractionsConsolidationData consolidatedInteractions = null;
	@NotNull
	private CSSSimplifiedChatHistory chatHistory = new CSSSimplifiedChatHistory();
	@NotNull
	private GeboChatRequest currentRequest = null;
	private int targetTokenBudget = 0;

	@Override
	public int getTokensSize() {
		return ITokensCountable.tokensSize(latestRequestsChatWithDocuments, relevantLlmGeneratedDocuments,
				relevantRetrievedDocuments, relevantUploadedDocuments, consolidatedInteractions);
	}

	@Override
	public LLMChatRequestResources createChatRequestResources() {
		return new LLMChatRequestResources((latestRequestsChatWithDocuments), (latestRequestsRetrievedDocuments),
				(latestRequestsUploadedDocuments), toDocsSet(relevantRetrievedDocuments),
				toDocsSet(relevantUploadedDocuments), toDocsSet(relevantLlmGeneratedDocuments),
				consolidatedInteractions.getConsolidationText(), chatHistory.getInteractions(), currentRequest);
	}

	private AIDocumentsSet toDocsSet(CSSfRelevantShrinkedDocumentList relevantUploadedDocuments2) {
		AIDocumentsSet set = new AIDocumentsSet();
		for (CSSRelevantShrinkedDocument item : relevantUploadedDocuments2) {
			AIDocumentFragment singleFragment = new AIDocumentFragment();
			singleFragment.setDocumentId(item.getId());
			singleFragment.setCode(item.getDocumentReference());
			singleFragment.setMetaData(item.getMetaData());
			singleFragment.setDocumentContent(item.getSummarizedContent());
			singleFragment.setTokensSize(item.getTokensSize());
			AIDocumentReferenceItem document = new AIDocumentReferenceItem();
			document.setCode(item.getDocumentReference());
			document.setName(item.getDocumentName());
			document.setOriginalUrl(item.getDocumentUrl());
			document.getFragments().add(singleFragment);
			set.getDocumentItems().add(document);
		}
		return set;
	}

}
