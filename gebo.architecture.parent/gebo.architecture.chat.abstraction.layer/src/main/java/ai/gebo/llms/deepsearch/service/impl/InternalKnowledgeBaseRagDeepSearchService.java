package ai.gebo.llms.deepsearch.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentReferenceItem;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.llms.abstraction.layer.services.BaseLLMSInvokingService;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GInputProcessingEvent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.repository.UserUploadContentServerSideRepository;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.IInternalKnowledgeLLMAssistedRetrieveService;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import ai.gebo.llms.deepsearch.config.DeepSearchDefaultConfig;
import ai.gebo.llms.deepsearch.datasources.model.AbstractPureSearchDocumentResultEntry;
import ai.gebo.llms.deepsearch.datasources.model.PureSearchInternalKnowledgeBaseResultEntry;
import ai.gebo.llms.deepsearch.service.IGInternalKnlowledgeBaseRagDeepSearchService;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.security.services.ReactiveIdentityUtil;
import ai.gebo.system.ingestion.GeboIngestionException;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor
public class InternalKnowledgeBaseRagDeepSearchService extends BaseLLMSInvokingService
		implements IGInternalKnlowledgeBaseRagDeepSearchService {
	private static final Logger LOGGER = LoggerFactory.getLogger(InternalKnowledgeBaseRagDeepSearchService.class);
	private final IGPromptConfigDao promptsDao;
	private final DocumentReferenceRepository documentRepo;
	private final DeepSearchDefaultConfig defaultDeepsearchConfig;
	private final UserUploadContentServerSideRepository userUploadedRepository;
	private final IInternalKnowledgeLLMAssistedRetrieveService llmAssistedRetriveService;
	private final IGeboThreadManager threadManager;

	@Override
	public Flux<AbstractPureSearchDocumentResultEntry> streamPureSearch(MinimalChatContext minimalChatContext,
			ISinkUIEmitter emitter, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel,
			String chunkingSessionId, int topK, int sampleTextTokensSize)
			throws LLMConfigException, GeboChatSessionLifecycleException {

		Flux<AIDocumentsSet> retrievedFlux = this.llmAssistedRetriveService.doDocumentsRetrieve(minimalChatContext,
				serviceModel, LLMRequestGenerationPolicy.ADDING_RESOURCES_DO_NOT_FIT_TOKENS_BUDGET, topK);
		final ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
		Flux<AbstractPureSearchDocumentResultEntry> outFlux = retrievedFlux.concatMap(documentSet -> {
			return runAs.doRunAsWithReturn(() -> {
				if (documentSet.getDocumentItems().isEmpty())
					return Flux.empty();
				List<AIDocumentReferenceItem> documents = documentSet.getDocumentItems();
				Map<String, AIDocumentReferenceItem> docsById = new HashMap<>();
				for (AIDocumentReferenceItem item : documents) {
					docsById.put(item.getCode(), item);
				}
				List<GDocumentReference> docRefs = this.documentRepo.findAllById(docsById.keySet());
				List<AbstractPureSearchDocumentResultEntry> out = new ArrayList<>();
				for (GDocumentReference ref : docRefs) {
					AIDocumentReferenceItem contents = docsById.get(ref.getCode());
					if (contents != null && contents.getFragments() != null && !contents.getFragments().isEmpty()) {
						String extracted = contents.extractTokens(sampleTextTokensSize);
						out.add(new PureSearchInternalKnowledgeBaseResultEntry(ref, extracted));
					}
				}
				return Flux.fromIterable(out);
			});
		});
		return outFlux;
	}

	@Override
	public Flux<Document> streamSearchResults(ChatPipelineExecutionRuntimeData runtimeData,
			ISinkUIEmitter sinkUIEmitter, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel,
			String chunkingSessionId, int topK) throws LLMConfigException, IOException, GeboIngestionException,
			GeboContentHandlerSystemException, SearchServiceException, GeboChatSessionLifecycleException {
		Flux<Document> chatWithDocumentsFlux = runtimeData.getRequestResources().getChatWithDocuments() != null
				? Flux.fromIterable(runtimeData.getRequestResources().getChatWithDocuments().aiDocumentsList())
				: Flux.empty();
		Map<String, GResponseDocumentRef> docrefs = new Hashtable<>();
		Flux<Document> searchFlux = llmAssistedRetriveService.doDocumentsRetrieve(runtimeData.getMinimalChatContext(),
				serviceModel, LLMRequestGenerationPolicy.ADDING_RESOURCES_DO_NOT_FIT_TOKENS_BUDGET, topK).flatMap(x -> {
					List<Document> docsList = x.aiDocumentsList();
					docsList.forEach(doc -> {
						String code = doc.getMetadata() != null
								&& doc.getMetadata().containsKey(DocumentMetaInfos.CONTENT_CODE)
										? doc.getMetadata().get(DocumentMetaInfos.CONTENT_CODE).toString()
										: null;
						if (code != null && !docrefs.containsKey(code)) {
							GResponseDocumentRef ref = new GResponseDocumentRef(doc);
							docrefs.put(code, new GResponseDocumentRef(doc));
							GInputProcessingEvent processingEvent = new GInputProcessingEvent(ref);
							sinkUIEmitter.next(new GeboChatMessageEnvelope(processingEvent));
						}
					});
					return Flux.fromIterable(docsList);
				});
		Flux<Document> documentFlux = Flux.concat(searchFlux, chatWithDocumentsFlux);
		return documentFlux;
	}


}
