package ai.gebo.architecture.documents.cache.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.ai.document.Document;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.documents.cache.model.AbstractChunkingSpecs;
import ai.gebo.architecture.documents.cache.model.DocumentChunk;
import ai.gebo.architecture.documents.cache.model.DocumentChunkType;
import ai.gebo.architecture.documents.cache.model.DocumentChunkingResponse;
import ai.gebo.architecture.documents.cache.model.TextChunkingSpecs;
import ai.gebo.architecture.documents.cache.service.DocumentCacheAccessException;
import ai.gebo.architecture.documents.cache.service.IDocumentsCacheService;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.config.service.IGGeboConfigService;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.system.ingestion.GeboIngestionException;
import ai.gebo.system.ingestion.IGAIDocumentMetaDataEnricher;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler.IngestionHandlerData;
import ai.gebo.system.ingestion.model.MetaDataHeaderInfos;
import jakarta.el.MethodNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DocumentsChunkServiceImpl implements IDocumentsChunkService {
	private static final String CHUNKS_CACHE_DIRECTORY_NAME = ".CHCACHE";
	private final IDocumentsCacheService cacheService;
	private final IGGeboConfigService configService;
	private final DocumentChunkOperationRepository chunkOperationRepository;
	private final IGAIDocumentMetaDataEnricher metaDataEnricher;
	private final IGDocumentReferenceIngestionHandler ingestionHandler;
	private final IGPersistentObjectManager persistentObjectManager;
	private final static ObjectMapper objectMapper = new ObjectMapper();
	private final static long ttlCacheIt = 5 * 60 * 1000;// tokenizing request has 5 minute validity

	@Override
	public DocumentChunkingResponse getChunk(GDocumentReference document, List<AbstractChunkingSpecs> chunkingSpecs,
			boolean enrichWithMetaData) throws DocumentCacheAccessException, IOException,
			GeboContentHandlerSystemException, GeboIngestionException {
		// Take work directory
		String workDirectory = configService.getGeboWorkDirectory();
		// If caching folder do not exist create it
		Path rootCachePath = Path.of(workDirectory, CHUNKS_CACHE_DIRECTORY_NAME);
		Files.createDirectories(rootCachePath);
		// if no chunking specification is added than is an invalid call
		if (chunkingSpecs == null || chunkingSpecs.isEmpty())
			throw new DocumentCacheAccessException("No chunking specs");
		// if there is an imaging chunking request we do not handle it actually (but we
		// will in the future)
		Optional<AbstractChunkingSpecs> imageConfig = chunkingSpecs.stream()
				.filter(x -> x.getChunkType() == DocumentChunkType.IMAGE).findFirst();
		if (imageConfig.isPresent())
			throw new MethodNotFoundException("The images chunking is not yet implemented");
		// Check if there is a matching request in the last ttlCacheIt period of time
		// already done to be reused
		List<DocumentChunkOperation> matchingOperations = chunkOperationRepository
				.findByOriginalDocumentCode(document.getCode());

		final long actualTime = System.currentTimeMillis();
		Comparator<? super DocumentChunkOperation> comparator = (o1,
				o2) -> ((int) (o1.getLastAccessed().getTime() - o2.getLastAccessed().getTime()) / 1000);
		Optional<DocumentChunkOperation> matchingOperation = matchingOperations.stream().sorted(comparator)
				.filter(x -> {
					// Check if the request whas to enrich with metadata and all matching parameters
					boolean matchingCriterias = x.isEnrichWithMetaData() == enrichWithMetaData;
					if (matchingCriterias) {
						// checking that the request is in the last ttlCacheIt milliseconds
						matchingCriterias = (actualTime - x.getLastAccessed().getTime()) < ttlCacheIt;
						if (matchingCriterias) {
							for (AbstractChunkingSpecs spec : chunkingSpecs) {
								Optional<AbstractChunkingSpecs> sameType = x.getChunkingSpecs().stream()
										.filter(y -> y.getClass().equals(spec.getClass())).findFirst();
								matchingCriterias = matchingCriterias && sameType.isPresent();
								if (matchingCriterias) {
									matchingCriterias = matchingCriterias && sameType.get().equals(spec);
								}
							}
						}
					}
					return matchingCriterias;
				}).findFirst();
		// if there is a matching operation than we return those chunks
		if (matchingOperation.isPresent()) {
			// returning the first chunk as 0 index id
			DocumentChunkOperation operationData = matchingOperation.get();
			return getNextChunk(document, operationData.getId(), operationData.getChunksList().get(0));
		}
		// We have to calculate chunking from scratch
		Optional<AbstractChunkingSpecs> textConfig = chunkingSpecs.stream()
				.filter(x -> x.getChunkType() == DocumentChunkType.TEXT).findFirst();
		final DocumentChunkingResponse response = new DocumentChunkingResponse();
		final List<Throwable> exceptions = new ArrayList<Throwable>();
		response.setEmpty(true);
		// We ask the cacheService to stream the document
		try (InputStream is = this.cacheService.streamDocument(document)) {
			if (is != null) {

				IngestionHandlerData content = ingestionHandler.handleContent(document, is);
				if (!content.isUnmanagedContent()) {
					// if we have an handled content from the ingestion layer we proceed
					Stream<Document> docsStream = content.getStream();
					// We use streaming so huge files or huge datasets are streamed directly to the
					// disk in chunks without heavy memory use
					// Start filling the chunkOperation to remain saved in mongodb for later use and
					// next chunk calls
					DocumentChunkOperation chunkOperation = new DocumentChunkOperation();
					chunkOperation.setEnrichWithMetaData(enrichWithMetaData);
					chunkOperation.setChunkingSpecs(chunkingSpecs);
					chunkOperation.setOriginalDocumentCode(document.getCode());
					// Start streaming ingested AI documents
					docsStream.forEach(doc -> {

						try {
							MetaDataHeaderInfos metaDataHeader = null;
							// We calculate metaDataHeader if enrichWithMetaData is true (it will be the
							// same metadata for all other chunks)
							if (enrichWithMetaData && doc.isText()) {
								try {
									GProject project = document.getParentProjectCode() != null
											? persistentObjectManager.findById(GProject.class,
													document.getParentProjectCode())
											: null;
									GKnowledgeBase knowledgeBase = document.getRootKnowledgebaseCode() != null
											? persistentObjectManager.findById(GKnowledgeBase.class,
													document.getRootKnowledgebaseCode())
											: null;
									GProjectEndpoint endpoint = document.getProjectEndpointReference() != null
											? persistentObjectManager.findByReference(
													document.getProjectEndpointReference(), GProjectEndpoint.class)
											: null;

									metaDataHeader = this.metaDataEnricher.createMetaDataHeader(List.of(doc), document,
											knowledgeBase, project, endpoint);
								} catch (GeboPersistenceException e) {
									exceptions.add(e);
									return;
								}
							}
							// discard zero length contents
							if (textConfig.isPresent() && doc.isText() && doc.getText() != null
									&& doc.getText().trim().length() > 0) {
								AbstractChunkingSpecs spec = textConfig.get();
								if (spec instanceof TextChunkingSpecs textSpecs) {

									TokenTextSplitter tokensplitter = new TokenTextSplitter(
											textSpecs.getDefaultChunkSize(), textSpecs.getMinChunkSizeChars(),
											textSpecs.getMinChunkLengthToEmbed(), textSpecs.getMaxNumChunks(),
											textSpecs.isKeepSeparator());

									List<Document> outContents = tokensplitter.split(doc);
									if (enrichWithMetaData && metaDataHeader != null) {
										List<Document> withMetaData = new ArrayList<Document>();
										for (Document thisDoc : outContents) {
											withMetaData.add(this.metaDataEnricher.enrich(thisDoc, metaDataHeader));
										}
										outContents = withMetaData;
									}
									JTokkitTokenCountEstimator estimator = new JTokkitTokenCountEstimator();
									response.setEmpty(outContents.isEmpty());
									for (Document _document : outContents) {

										int bytesSize = _document.getText() != null ? _document.getText().length() * 2
												: 0;
										int tokensSize = _document.getText() != null && _document.isText()
												? estimator.estimate(_document.getText())
												: 0;
										_document.getMetadata().put(DocumentMetaInfos.GEBO_BYTES_LENGTH, bytesSize);
										_document.getMetadata().put(DocumentMetaInfos.GEBO_TOKEN_LENGTH, tokensSize);
										response.setEmpty(false);
										DocumentChunk chunk = DocumentChunk.ofText(document.getCode(),
												_document.getText(), _document.getMetadata());
										chunkOperation.getChunksList().add(chunk.getId());
										Path writtenFile = Path.of(workDirectory, CHUNKS_CACHE_DIRECTORY_NAME,
												chunk.getId());
										objectMapper.writeValue(writtenFile.toFile(), chunk);
										if (response.getCurrentChunk() == null) {
											response.setCurrentChunk(chunk);
										}
									}

								} else
									throw new IllegalArgumentException(
											"The text specs have to be of type TextChunkingSpecs");
							}
						} catch (Throwable th) {
							exceptions.add(th);
						}

					});
					if (!exceptions.isEmpty()) {
						throw new DocumentCacheAccessException("Cannot split in chunk because of an exception",
								exceptions);
					}
					if (!chunkOperation.getChunksList().isEmpty()) {
						response.setId(chunkOperation.getId());
						chunkOperation.setLastAccessed(new Date());
						chunkOperationRepository.insert(chunkOperation);
						int index = chunkOperation.getChunksList().indexOf(response.getCurrentChunk().getId());
						if (chunkOperation.getChunksList().size() > index + 1) {
							response.setNextChunkId(chunkOperation.getChunksList().get(index + 1));
						}
					}

				}
			}
		}
		return response;

	}

	@Override
	public DocumentChunkingResponse getNextChunk(GDocumentReference document, String chunkRequestId, String chunkId)
			throws DocumentCacheAccessException, IOException {
		Optional<DocumentChunkOperation> optionalChunkOperation = chunkOperationRepository.findById(chunkRequestId);
		if (optionalChunkOperation.isEmpty())
			throw new DocumentCacheAccessException("Chunk operation invalid id or expired id =>" + chunkRequestId);
		DocumentChunkOperation operation = optionalChunkOperation.get();
		int index = operation.getChunksList().indexOf(chunkId);
		if (index >= 0) {
			DocumentChunkingResponse response = new DocumentChunkingResponse();
			response.setEmpty(false);
			String workDirectory = configService.getGeboWorkDirectory();
			Path fileToRead = Path.of(workDirectory, CHUNKS_CACHE_DIRECTORY_NAME, chunkId);
			DocumentChunk chunk = objectMapper.readValue(fileToRead.toFile(), DocumentChunk.class);
			response.setCurrentChunk(chunk);
			response.setId(chunkRequestId);
			if (index < operation.getChunksList().size() - 1) {
				String nextChunk = operation.getChunksList().get(index + 1);
				response.setNextChunkId(nextChunk);
			}
			operation.setLastAccessed(new Date());
			this.chunkOperationRepository.save(operation);
			return response;
		} else
			throw new DocumentCacheAccessException(chunkId + " not part of the chunking operation " + chunkRequestId);

	}

}
