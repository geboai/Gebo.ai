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
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMGeneratedResource;
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
	private CSSReferredContentList<UserUploadedContent> latestRequestsUploadedDocuments = new CSSReferredContentList<UserUploadedContent>();
	private CSSReferredContentList<GDocumentReference> latestRequestsChatWithDocuments = new CSSReferredContentList<GDocumentReference>();
	private CSSReferredContentList<GDocumentReference> latestRequestsRetrievedDocuments = new CSSReferredContentList<GDocumentReference>();
	private CSSReferredContentList<LLMGeneratedResource> latestRequestsLlmGeneratedDocuments = new CSSReferredContentList<LLMGeneratedResource>();
	private CSSfRelevantShrinkedDocumentList relevantChatWithDocuments = new CSSfRelevantShrinkedDocumentList();
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
	private boolean toBeShrinked = false;

	@Override
	public int getTokensSize() {
		return ITokensCountable.tokensSize(latestRequestsUploadedDocuments, latestRequestsChatWithDocuments,
				latestRequestsRetrievedDocuments, latestRequestsLlmGeneratedDocuments, relevantChatWithDocuments,
				relevantUploadedDocuments, relevantRetrievedDocuments, relevantLlmGeneratedDocuments,
				consolidatedInteractions, chatHistory, currentRequest);
	}

	@Override
	public LLMChatRequestResources createChatRequestResources() {
		AIDocumentsSet chatWithDocuments = AIDocumentsSet.join(this.latestRequestsChatWithDocuments.toAIDocumentsSet(),
				this.relevantChatWithDocuments.toAIDocumentsSet(0.0f));
		AIDocumentsSet retrievedDocuments = AIDocumentsSet.join(
				this.latestRequestsRetrievedDocuments.toAIDocumentsSet(),
				this.relevantRetrievedDocuments.toAIDocumentsSet(0.0f));
		AIDocumentsSet uploadedDocuments = AIDocumentsSet.join(this.latestRequestsUploadedDocuments.toAIDocumentsSet(),
				this.relevantUploadedDocuments.toAIDocumentsSet(0.0f));
		AIDocumentsSet llmGeneratedDocuments = AIDocumentsSet.join(
				this.latestRequestsLlmGeneratedDocuments.toAIDocumentsSet(),
				this.relevantLlmGeneratedDocuments.toAIDocumentsSet(0.0f));
		return new LLMChatRequestResources(chatWithDocuments, retrievedDocuments, uploadedDocuments,
				llmGeneratedDocuments, consolidatedInteractions.getConsolidationText(), chatHistory.getInteractions(),
				currentRequest);
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
