package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.llms.abstraction.layer.model.RagDocumentFragment;
import ai.gebo.llms.abstraction.layer.model.RagDocumentReferenceItem;
import ai.gebo.llms.abstraction.layer.model.RagDocumentsCachedDaoResult;
import ai.gebo.llms.chat.abstraction.layer.model.ChatInteractions;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatInteractionsConsolidationData;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.model.LLMGeneratedResource;
import ai.gebo.llms.chat.abstraction.layer.model.TokenLimitedContent;
import ai.gebo.llms.chat.abstraction.layer.model.UserUploadedContent;
import ai.gebo.llms.chat.abstraction.layer.model.session.ChatSessionState;
import ai.gebo.llms.chat.abstraction.layer.model.session.CSSInteractionReferredContent;
import ai.gebo.llms.chat.abstraction.layer.model.session.CSSReferredContentList;
import ai.gebo.llms.chat.abstraction.layer.model.session.CSSSimplefiedInteraction;
import ai.gebo.llms.chat.abstraction.layer.model.session.CSSSimplifiedChatHistory;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionStateService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;
import ai.gebo.model.ExtractedDocumentMetaData;
import ai.gebo.system.ingestion.GeboIngestionException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GChatSessionStateServiceImpl implements IGChatSessionStateService {
	final IGChatStorageAreaService storageAreaService;
	final DocumentReferenceRepository documentsRepository;
	final static Logger LOGGER = LoggerFactory.getLogger(GChatSessionStateServiceImpl.class);

	@AllArgsConstructor
	static class PosHigher {
		RagDocumentReferenceItem document = null;
		GDocumentReference original = null;
		int position = 0;
	}

	@Override
	public ChatSessionState extractState(GeboChatRequest request, GUserChatContext context) throws IOException {
		ChatSessionState outState = new ChatSessionState();
		GUserChatInteractionsConsolidationData consolidation = context.getConsolidation();
		List<ChatInteractions> interactions = context.getInteractions();
		long historyTokens = consolidation != null ? consolidation.getTokensSize() : 0;
		outState.getChatHistory().getValue().setConsolidation(consolidation);

		// Map used to join fragment of the same document in the higher position it
		// appears and to search Original elements

		if (interactions != null) {

			outState.setRagResultsHistory(higherPositionShifted(interactions));
			final int startIndex = consolidation != null ? consolidation.getLastInteractionPointer() : 0;

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

					GeboChatRequest crequest = i.getRequest();
					if (crequest.getUserUploadedContents() != null && !crequest.getUserUploadedContents().isEmpty()) {
						Map<String, UserUploadedContent> byCode = new HashMap<String, UserUploadedContent>();
						for (UserUploadedContent uploaded : crequest.getUserUploadedContents()) {
							byCode.put(uploaded.getCode(), uploaded);
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
								CSSInteractionReferredContent<UserUploadedContent> entry = new CSSInteractionReferredContent<UserUploadedContent>(
										index, data, uploaded);
								outState.getUploadsHistory().getValue().add(entry);
							}

						}

					}
				}
				if (i.getResponse() != null && i.getResponse().getQueryResponse() != null) {
					assistant = i.getResponse().getQueryResponse().toString();
					assistantTokens = i.getResponseNTokens();
					if (assistantTokens != null) {
						historyTokens += assistantTokens.longValue();
					}
				}
				if (i.getResponse() != null && i.getResponse().getGeneratedResources() != null
						&& !i.getResponse().getGeneratedResources().isEmpty()) {
					int generatedTotalTokenSize = 0;
					for (int j = 0; j < i.getResponse().getGeneratedResources().size(); j++) {
						LLMGeneratedResource generated = (LLMGeneratedResource) i.getResponse().getGeneratedResources()
								.get(j);
						try {
							List<Document> ingested = storageAreaService.getIngestedContentsOf(generated);
							if (!ingested.isEmpty()) {
								RagDocumentReferenceItem reference = new RagDocumentReferenceItem(
										ExtractedDocumentMetaData.of(ingested.get(0).getMetadata()));
								for (Document document : ingested) {
									reference.getFragments().add(new RagDocumentFragment(document,
											ExtractedDocumentMetaData.of(document.getMetadata())));
								}
								reference.recalculateSize();
								CSSInteractionReferredContent<LLMGeneratedResource> entry = new CSSInteractionReferredContent<LLMGeneratedResource>(
										index, reference, generated);
								outState.getGeneratedArtifacts().getValue().add(entry);
								generatedTotalTokenSize += reference.getNTokens();
							}
						} catch (IOException | GeboContentHandlerSystemException | GeboIngestionException e) {
							LOGGER.error("Exception while ingsting the generated content : " + generated, e);
						}

					}
					outState.getGeneratedArtifacts().setNToken(generatedTotalTokenSize);
				}
				CSSSimplefiedInteraction interaction = new CSSSimplefiedInteraction(user, userTokens, assistant,
						assistantTokens);
				outState.getChatHistory().getValue().getInteractions().add(interaction);
				outState.getChatHistory().setNToken((int) historyTokens);
				index++;
			}
		}
		outState.getChatHistory().setNToken((int) historyTokens);
		if (request.getUserUploadedContents() != null && !request.getUserUploadedContents().isEmpty()) {
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
					CSSInteractionReferredContent<UserUploadedContent> entry = new CSSInteractionReferredContent<UserUploadedContent>(
							interactions != null ? interactions.size() : 0, data, uploaded);
					outState.getCurrentRequestUploads().getValue().add(entry);
					outState.getCurrentRequestUploads().setNToken((int) tokens);
				}
			}

		}
		int totalTokens = outState.getChatHistory().getNToken() + outState.getCurrentRequestUploads().getNToken()
				+ outState.getRagResultsHistory().getNToken() + outState.getGeneratedArtifacts().getNToken()
				+ outState.getUploadsHistory().getNToken();
		outState.setTotalTokensSize(totalTokens);
		return outState;
	}

	private TokenLimitedContent<CSSReferredContentList<GDocumentReference>> higherPositionShifted(
			List<ChatInteractions> interactions) {
		Map<String, PosHigher> higherPositionMap = createHigherPositionMap(interactions);
		TreeMap<Integer, List<PosHigher>> byLine = new TreeMap<Integer, List<PosHigher>>();
		higherPositionMap.values().forEach(x -> {
			Integer position = x.position;
			byLine.computeIfAbsent(position, ArrayList::new);
			byLine.get(position).add(x);
		});
		List<GDocumentReference> originalDocuments = documentsRepository.findAllById(higherPositionMap.keySet());
		for (GDocumentReference gDocumentReference : originalDocuments) {
			higherPositionMap.get(gDocumentReference.getCode()).original = gDocumentReference;
		}
		CSSReferredContentList<GDocumentReference> out = new CSSReferredContentList<GDocumentReference>();
		int tokensTotal = 0;
		for (List<PosHigher> x : byLine.values()) {
			for (PosHigher posh : x) {
				CSSInteractionReferredContent<GDocumentReference> entry = new CSSInteractionReferredContent<GDocumentReference>(
						posh.position, posh.document, posh.original);
				out.add(entry);
				tokensTotal += entry.getData().getNTokens();
			}
		}
		TokenLimitedContent<CSSReferredContentList<GDocumentReference>> tl = new TokenLimitedContent<CSSReferredContentList<GDocumentReference>>(
				out, tokensTotal);
		return tl;
	}

	private Map<String, PosHigher> createHigherPositionMap(List<ChatInteractions> interactions) {
		Map<String, PosHigher> out = new HashMap<String, PosHigher>();
		int index = 0;
		for (ChatInteractions inter : interactions) {
			if (inter.getRequest() != null) {
				RagDocumentsCachedDaoResult docs = inter.getRequest().getDocuments();
				if (docs != null) {
					for (RagDocumentReferenceItem d : docs.getDocumentItems()) {
						PosHigher pos = out.get(d.getCode());
						if (pos == null) {
							out.put(d.getCode(), pos = new PosHigher(d, null, index));
						} else {
							pos.document = RagDocumentReferenceItem.join(pos.document, d);
							pos.position = index;
						}
					}
				}
			}
			index++;
		}
		return out;
	}

}
