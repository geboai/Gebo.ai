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
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentFragment;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentReferenceItem;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.rag.support.layer.services.impl.AIDocumentsCacheService;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.llms.chat.abstraction.layer.config.GeboChatConfigs;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMGeneratedResource;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.UserUploadedContent;
import ai.gebo.llms.chat.abstraction.layer.model.ChatInteractions;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.llms.chat.abstraction.layer.model.TokensContainer;
import ai.gebo.llms.chat.abstraction.layer.model.session.CSSInteractionReferredContent;
import ai.gebo.llms.chat.abstraction.layer.model.session.CSSReferredContentList;
import ai.gebo.llms.chat.abstraction.layer.model.session.CSSSimplefiedInteraction;
import ai.gebo.llms.chat.abstraction.layer.model.session.ChatFullSessionState;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatFullSessionStateService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;
import ai.gebo.model.ExtractedDocumentMetaData;
import ai.gebo.system.ingestion.GeboIngestionException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GChatSessionStateServiceImpl implements IGChatFullSessionStateService {
	final IGChatStorageAreaService storageAreaService;
	final DocumentReferenceRepository documentsRepository;
	final AIDocumentsCacheService documentsCacheService;
	final GeboChatConfigs chatConfig;
	final static Logger LOGGER = LoggerFactory.getLogger(GChatSessionStateServiceImpl.class);

	@AllArgsConstructor
	static class PosHigher {
		AIDocumentReferenceItem document = null;
		GDocumentReference original = null;
		int position = 0;
	}

	@Override
	public ChatFullSessionState addRequestToState(GeboChatRequest request, GUserChatContext context, int targetTokenBudget)
			throws IOException, GeboPersistenceException, GeboContentHandlerSystemException, GeboIngestionException {
		ChatFullSessionState outState = new ChatFullSessionState();
		outState.setUserChatContextCode(context.getCode());
		outState.getCurrentRequest().setValue(request);
		final int lastInteractionsOnLatest = this.chatConfig.getLeaveLastInteractionsOnHistoryConsolidation();
		List<ChatInteractions> interactions = context.getInteractions();
		Map<String, Integer> latestChatWithDocuments = new HashMap<String, Integer>();
		long historyTokens = 0;
		// Map used to join fragment of the same document in the higher position it
		// appears and to search Original elements

		if (interactions != null) {
			final int latestContainersThreashold = lastInteractionsOnLatest > interactions.size() ? 0
					: interactions.size() - lastInteractionsOnLatest;
			outState.setHistoricallyRetrievedDocuments(higherPositionShifted(interactions));

			for (int index = 0; index < interactions.size(); index++) {
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
								AIDocumentReferenceItem data = new AIDocumentReferenceItem();
								data.setCode(uploaded.getCode());
								data.setContentType(uploaded.getContentType());
								data.setExtension(uploaded.getExtension());
								data.setName(uploaded.getFileName());
								List<AIDocumentFragment> fragments = new ArrayList<>();
								for (Document document : documents) {
									AIDocumentFragment fragment = new AIDocumentFragment(document,
											ExtractedDocumentMetaData.of(document.getMetadata()));
									fragment.recalculateSize();
								}
								data.setFragments(fragments);
								data.recalculateSize();

								CSSInteractionReferredContent<UserUploadedContent> entry = new CSSInteractionReferredContent<UserUploadedContent>(
										index, data, uploaded);
								if (latestContainersThreashold > index)
									outState.getHistoricallyUploadedDocuments().getValue().add(entry);
								else
									outState.getLatestRequestsUploadedDocuments().getValue().add(entry);
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
								AIDocumentReferenceItem reference = new AIDocumentReferenceItem(
										ExtractedDocumentMetaData.of(ingested.get(0).getMetadata()));
								for (Document document : ingested) {
									reference.getFragments().add(new AIDocumentFragment(document,
											ExtractedDocumentMetaData.of(document.getMetadata())));
								}
								reference.recalculateSize();
								CSSInteractionReferredContent<LLMGeneratedResource> entry = new CSSInteractionReferredContent<LLMGeneratedResource>(
										index, reference, generated);
								outState.getLlmGeneratedDocuments().getValue().add(entry);
								generatedTotalTokenSize += reference.getTokensSize();
							}
						} catch (IOException | GeboContentHandlerSystemException | GeboIngestionException e) {
							LOGGER.error("Exception while ingsting the generated content : " + generated, e);
						}

					}
					outState.getLlmGeneratedDocuments().setTokensSize(generatedTotalTokenSize);
				}
				if (i.getRequest() != null && i.getRequest().getForcedRequestDocuments() != null
						&& index >= lastInteractionsOnLatest) {
					for (String key : i.getRequest().getForcedRequestDocuments()) {
						latestChatWithDocuments.put(key, new Integer(index));
					}
				}
				String decisionCode=i.getResponse()!=null?i.getResponse().getPipelineRouterDecisionCode():null;
				CSSSimplefiedInteraction interaction = new CSSSimplefiedInteraction(user, userTokens, assistant,
						assistantTokens, decisionCode);
				outState.getChatHistory().getValue().getInteractions().add(interaction);
				outState.getChatHistory().setTokensSize((int) historyTokens);
				index++;
			}
		}
		outState.getChatHistory().setTokensSize((int) historyTokens);
		if (request != null && request.getLatestRequestsChatWithDocuments() != null && !request.getLatestRequestsChatWithDocuments().getDocumentItems().isEmpty()) {
			Map<String, AIDocumentReferenceItem> docsMap = new HashMap<String, AIDocumentReferenceItem>();
			for (AIDocumentReferenceItem doc : request.getLatestRequestsChatWithDocuments().getDocumentItems()) {
				docsMap.put(doc.getCode(), doc);
			}
			List<GDocumentReference> docsList = documentsRepository.findAllById(docsMap.keySet());
			for (GDocumentReference doc : docsList) {
				AIDocumentReferenceItem aiDoc = docsMap.get(doc.getCode());
				if (aiDoc != null) {

					CSSInteractionReferredContent<GDocumentReference> entry = new CSSInteractionReferredContent<GDocumentReference>(
							interactions.size(), aiDoc, doc);
					outState.getLatestRequestsRetrievedDocuments().getValue().add(entry);
				}
			}
		}
		if (request != null && request.getUserUploadedContents() != null
				&& !request.getUserUploadedContents().isEmpty()) {
			long tokens = 0l;
			for (UserUploadedContent uploaded : request.getUserUploadedContents()) {
				List<Document> documents = this.storageAreaService.getIngestedContentsOf(uploaded);
				if (documents != null && !documents.isEmpty()) {
					AIDocumentReferenceItem data = new AIDocumentReferenceItem();
					data.setCode(uploaded.getCode());
					data.setContentType(uploaded.getContentType());
					data.setExtension(uploaded.getExtension());
					data.setName(uploaded.getFileName());
					List<AIDocumentFragment> fragments = new ArrayList<>();
					for (Document document : documents) {
						AIDocumentFragment fragment = new AIDocumentFragment(document,
								ExtractedDocumentMetaData.of(document.getMetadata()));
						fragment.recalculateSize();
					}
					data.setFragments(fragments);
					data.recalculateSize();
					tokens += data.getTotalFileNTokens();
					CSSInteractionReferredContent<UserUploadedContent> entry = new CSSInteractionReferredContent<UserUploadedContent>(
							interactions != null ? interactions.size() : 0, data, uploaded);
					outState.getLatestRequestsUploadedDocuments().getValue().add(entry);
					outState.getLatestRequestsUploadedDocuments().setTokensSize((int) tokens);
				}
			}

		}
		if (request.getForcedRequestDocuments() != null) {
			request.getForcedRequestDocuments().stream().forEach(key -> {
				latestChatWithDocuments.put(key, new Integer(interactions.size()));
			});
		}
		if (latestChatWithDocuments != null && !latestChatWithDocuments.isEmpty()) {
			List<GDocumentReference> documents = documentsRepository.findAllById(latestChatWithDocuments.keySet());
			Map<String, AIDocumentReferenceItem> data = new HashMap();
			int ntokens = 0;
			for (GDocumentReference gDocumentReference : documents) {
				AIDocumentReferenceItem ingested = documentsCacheService.retrieve(gDocumentReference);
				ingested.recalculateSize();
				ntokens += ingested.getTokensSize();
				Integer position = latestChatWithDocuments.get(gDocumentReference.getCode());
				data.put(ingested.getCode(), ingested);
				outState.getLatestRequestsChatWithDocuments().getValue()
						.add(new CSSInteractionReferredContent<GDocumentReference>(position.intValue(), ingested,
								gDocumentReference));
			}
			outState.getLatestRequestsChatWithDocuments().setTokensSize(ntokens);
		}

		
		return outState;
	}

	private TokensContainer<CSSReferredContentList<GDocumentReference>> higherPositionShifted(
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
				tokensTotal += entry.getData().getTokensSize();
			}
		}
		TokensContainer<CSSReferredContentList<GDocumentReference>> tl = new TokensContainer<CSSReferredContentList<GDocumentReference>>(
				out, tokensTotal);
		return tl;
	}

	private Map<String, PosHigher> createHigherPositionMap(List<ChatInteractions> interactions) {
		Map<String, PosHigher> out = new HashMap<String, PosHigher>();
		int index = 0;
		for (ChatInteractions inter : interactions) {
			if (inter.getRequest() != null) {
				AIDocumentsSet docs = inter.getRequest().getLatestRequestsChatWithDocuments();
				if (docs != null) {
					for (AIDocumentReferenceItem d : docs.getDocumentItems()) {
						PosHigher pos = out.get(d.getCode());
						if (pos == null) {
							out.put(d.getCode(), pos = new PosHigher(d, null, index));
						} else {
							pos.document = AIDocumentReferenceItem.join(pos.document, d);
							pos.position = index;
						}
					}
				}
			}
			index++;
		}
		return out;
	}

	@Override
	public ChatFullSessionState retrieveState(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteState(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ChatFullSessionState addInteractionToState(GeboChatRequest request, GeboChatResponse response,
			GUserChatContext context, int targetTokenBudget)
			throws IOException, GeboPersistenceException, GeboContentHandlerSystemException, GeboIngestionException {
		// TODO Auto-generated method stub
		return null;
	}

}
