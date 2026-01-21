package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import ai.gebo.llms.abstraction.layer.model.RagDocumentFragment;
import ai.gebo.llms.abstraction.layer.model.RagDocumentReferenceItem;
import ai.gebo.llms.abstraction.layer.model.RagDocumentsCachedDaoResult;
import ai.gebo.llms.chat.abstraction.layer.model.ChatInteractions;
import ai.gebo.llms.chat.abstraction.layer.model.ChatSessionState;
import ai.gebo.llms.chat.abstraction.layer.model.ChatSessionState.InteractionReferredInfo;
import ai.gebo.llms.chat.abstraction.layer.model.ChatSessionState.SimplefiedInteraction;
import ai.gebo.llms.chat.abstraction.layer.model.ChatSessionState.SimplifiedChatHistory;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatConsolidationData;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.model.UserUploadedContent;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionStateService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;
import ai.gebo.model.ExtractedDocumentMetaData;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GChatSessionStateServiceImpl implements IGChatSessionStateService {
	final IGChatStorageAreaService storageAreaService;

	@Override
	public ChatSessionState extractState(GeboChatRequest request, GUserChatContext context) throws IOException {
		ChatSessionState outState = new ChatSessionState();
		outState.getChatHistory().setValue(new SimplifiedChatHistory());
		outState.getRagResultsHistory().setValue(new ArrayList<InteractionReferredInfo<RagDocumentsCachedDaoResult>>());
		outState.getUploadsHistory().setValue(new ArrayList<InteractionReferredInfo<RagDocumentsCachedDaoResult>>());
		GUserChatConsolidationData consolidation = context.getConsolidation();
		List<ChatInteractions> interactions = context.getInteractions();
		long historyTokens = consolidation != null ? consolidation.getTokensSize() : 0;
		outState.getChatHistory().getValue().setConsolidation(consolidation);
		if (interactions != null) {
			final int startIndex = consolidation != null ? consolidation.getLastInteractionPointer() : 0;
			for (int index = 0; index < startIndex; index++) {
				ChatInteractions i = interactions.get(index);
				if (i.getRequest() != null) {
					RagDocumentsCachedDaoResult ragDocs = i.getRequest().getDocuments();
					if (ragDocs != null && ragDocs.getDocumentItems().size() > 0) {
						InteractionReferredInfo<RagDocumentsCachedDaoResult> interactionRefInfo = new InteractionReferredInfo<RagDocumentsCachedDaoResult>(
								index, ragDocs);
						outState.getRagResultsHistory().getValue().add(interactionRefInfo);
					}
				}
			}
			for (int index = startIndex; index < interactions.size(); index++) {
				ChatInteractions i = interactions.get(index);
				String user = null;
				Integer userTokens = null;
				String assistant = null;
				Integer assistantTokens = null;
				if (i.getRequest() != null) {
					user = i.getRequest().getQuery();
					userTokens = i.getRequestNTokens();
					if (userTokens != null) {
						historyTokens += userTokens.longValue();
					}
					RagDocumentsCachedDaoResult ragDocs = i.getRequest().getDocuments();
					if (ragDocs != null && ragDocs.getDocumentItems().size() > 0) {
						InteractionReferredInfo<RagDocumentsCachedDaoResult> interactionRefInfo = new InteractionReferredInfo<RagDocumentsCachedDaoResult>(
								index, ragDocs);
						outState.getRagResultsHistory().getValue().add(interactionRefInfo);
						outState.getRagResultsHistory()
								.setNToken((int) (outState.getRagResultsHistory().getNToken() + ragDocs.getNTokens()));
					}
					GeboChatRequest crequest = i.getRequest();
					if (crequest.getUserUploadedContents() != null && !crequest.getUserUploadedContents().isEmpty()) {
						outState.getUploadsHistory()
								.setValue(new ArrayList<InteractionReferredInfo<RagDocumentsCachedDaoResult>>());
						Map<String, RagDocumentReferenceItem> docs = new HashMap<String, RagDocumentReferenceItem>();
						for (UserUploadedContent uploaded : crequest.getUserUploadedContents()) {
							List<Document> documents = this.storageAreaService.getIngestedContentsOf(uploaded);
							if (documents != null && !documents.isEmpty()) {
								RagDocumentReferenceItem data = new RagDocumentReferenceItem();
								data.setCode(uploaded.getCode());
								data.setContentType(uploaded.getContentType());
								data.setExtension(uploaded.getExtension());
								data.setName(uploaded.getFileName());
								List<RagDocumentFragment> fragments = new ArrayList<>();
								for (Document document : documents) {
									RagDocumentFragment fragment = new RagDocumentFragment(document,
											ExtractedDocumentMetaData.of(document.getMetadata()));
									fragment.recalculateSize();
								}
								data.setFragments(fragments);
								data.recalculateSize();
								docs.put(data.getCode(), data);
							}
						}
						InteractionReferredInfo<RagDocumentsCachedDaoResult> entry = new InteractionReferredInfo<RagDocumentsCachedDaoResult>(
								index, RagDocumentsCachedDaoResult.createDocumentsDaoResultFromMap(docs));
						entry.getData().recalculateSize();
						outState.getUploadsHistory().getValue().add(entry);
						outState.getUploadsHistory().setNToken((int) entry.getData().getNTokens());
					}
				}
				if (i.getResponse() != null && i.getResponse().getQueryResponse() != null) {
					assistant = i.getResponse().getQueryResponse().toString();
					assistantTokens = i.getResponseNTokens();
					if (assistantTokens != null) {
						historyTokens += assistantTokens.longValue();
					}
				}
				SimplefiedInteraction interaction = new SimplefiedInteraction(user, userTokens, assistant,
						assistantTokens);
				outState.getChatHistory().getValue().getInteractions().add(interaction);
				outState.getChatHistory().setNToken((int) historyTokens);
				index++;
			}
		}
		if (request.getUserUploadedContents() != null && !request.getUserUploadedContents().isEmpty()) {
			outState.getCurrentRequestUploads().getValue().setDocumentItems(new ArrayList<RagDocumentReferenceItem>());
			long tokens = 0l;
			for (UserUploadedContent uploaded : request.getUserUploadedContents()) {
				List<Document> documents = this.storageAreaService.getIngestedContentsOf(uploaded);
				if (documents != null && !documents.isEmpty()) {
					RagDocumentReferenceItem data = new RagDocumentReferenceItem();
					data.setCode(uploaded.getCode());
					data.setContentType(uploaded.getContentType());
					data.setExtension(uploaded.getExtension());
					data.setName(uploaded.getFileName());
					List<RagDocumentFragment> fragments = new ArrayList<>();
					for (Document document : documents) {
						RagDocumentFragment fragment = new RagDocumentFragment(document,
								ExtractedDocumentMetaData.of(document.getMetadata()));
						fragment.recalculateSize();
					}
					data.setFragments(fragments);
					data.recalculateSize();
					tokens += data.getTotalFileNTokens();
					outState.getCurrentRequestUploads().getValue().getDocumentItems().add(data);
					outState.getCurrentRequestUploads().setNToken((int) tokens);
				}
			}

		}
		return outState;
	}

}
