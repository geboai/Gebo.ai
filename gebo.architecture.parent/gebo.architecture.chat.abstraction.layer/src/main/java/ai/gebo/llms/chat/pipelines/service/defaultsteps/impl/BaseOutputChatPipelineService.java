package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentReferenceItem;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.rag.support.layer.services.IGAIDocumentsCacheService;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMGeneratedResource;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.UserUploadContentServerSide;
import ai.gebo.llms.chat.abstraction.layer.repository.LLMGeneratedResourceRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.UserUploadContentServerSideRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.model.ExtractedDocumentMetaData;
import ai.gebo.system.ingestion.GeboIngestionException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BaseOutputChatPipelineService {
	final IGAIDocumentsCacheService documentsCacheService;
	final IGChatStorageAreaService chatStorageAreaService;
	final DocumentReferenceRepository docreferenceRepo;
	final UserUploadContentServerSideRepository uploadsRepo;
	final LLMGeneratedResourceRepository generatedRepo;
	static private final Logger LOGGER = LoggerFactory.getLogger(BaseOutputChatPipelineService.class);

	public LLMChatRequestResources integrateWithAISuggestedDocuments(ChatPipelineExecutionRuntimeData runtimeData) {
		List<String> docsList = DefaultPipelineSharedEnvironmentUtil.getAISuggestedSelectedDocuments(runtimeData);
		LLMChatRequestResources rc = runtimeData.getRequestResources();
		if (docsList != null && !docsList.isEmpty()) {
			LOGGER.info("Try loading AI suggested docs:" + docsList);
			AIDocumentsSet out = new AIDocumentsSet();
			for (String docId : docsList) {

				AIDocumentReferenceItem item = runtimeData.getRequestResources().findAIDocumentReferenceByCode(docId);
				if (item != null) {
					runtimeData.getRequestResources().removeAIDocumentReferenceByCode(docId);
				}
				AIDocumentReferenceItem ingested = null;
				try {
					Optional<GDocumentReference> docopt = docreferenceRepo.findById(docId);
					if (docopt.isPresent()) {

						ingested = documentsCacheService.retrieve(docopt.get());

					} else {
						Optional<UserUploadContentServerSide> uploadedopt = uploadsRepo.findById(docId);
						if (uploadedopt.isPresent()) {
							List<Document> documents = chatStorageAreaService.getIngestedContentsOf(uploadedopt.get());
							if (!documents.isEmpty()) {
								AIDocumentsSet set = AIDocumentsSet.from(documents);
								if (set.getDocumentItems().size() > 0) {
									ingested = set.getDocumentItems().get(0);
								}
							}
						} else {
							Optional<LLMGeneratedResource> generatedopt = generatedRepo.findById(docId);
							if (generatedopt.isPresent()) {
								List<Document> documents = chatStorageAreaService
										.getIngestedContentsOf(generatedopt.get());
								if (!documents.isEmpty()) {
									AIDocumentsSet set = AIDocumentsSet.from(documents);
									if (set.getDocumentItems().size() > 0) {
										ingested = set.getDocumentItems().get(0);
									}
								}
							} else {
								LOGGER.error("The code " + docId
										+ " is not a documentref or uploaded or generated document");
							}
						}
					}
					if (ingested != null) {
						out.getDocumentItems().add(ingested);
						out.recalculateSize();
					} else {
						LOGGER.error("The code " + docId
								+ " cannot be retrieved as documentref or uploaded or generated document");
					}
				} catch (GeboPersistenceException | GeboContentHandlerSystemException | IOException
						| GeboIngestionException e) {
					LOGGER.error("Exception ingesting document: " + docId, e);
				}
			}
			AIDocumentsSet docs = rc.getLastRequest().getDocuments();
			if (docs != null) {
				rc.getLastRequest().setDocuments(AIDocumentsSet.join(docs, out));
			}
		}
		return rc;
	}

}
