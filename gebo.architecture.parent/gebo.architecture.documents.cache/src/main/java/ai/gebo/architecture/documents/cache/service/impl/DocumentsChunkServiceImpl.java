package ai.gebo.architecture.documents.cache.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.documents.cache.config.GeboDocumentsCacheConfig;
import ai.gebo.architecture.documents.cache.model.AbstractChunkingSpecs;
import ai.gebo.architecture.documents.cache.model.DocumentChunk;
import ai.gebo.architecture.documents.cache.model.DocumentChunkType;
import ai.gebo.architecture.documents.cache.model.DocumentChunkingResponse;
import ai.gebo.architecture.documents.cache.model.DocumentChunksSet;
import ai.gebo.architecture.documents.cache.model.IDocumentChunkWithRef;
import ai.gebo.architecture.documents.cache.model.TextChunkingSpecs;
import ai.gebo.architecture.documents.cache.repository.DocumentChunkOperationRepository;
import ai.gebo.architecture.documents.cache.service.DocumentCacheAccessException;
import ai.gebo.architecture.documents.cache.service.IDocumentsCacheService;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.documents.cache.service.impl.model.DocumentChunkOperation;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.config.service.IGGeboConfigService;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.base.IGComponentOriginatedDocument;
import ai.gebo.model.base.TypedInputStream;
import ai.gebo.system.ingestion.GeboIngestionException;
import ai.gebo.system.ingestion.IGAIDocumentMetaDataEnricher;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler.IngestionHandlerData;
import ai.gebo.system.ingestion.model.MetaDataHeaderInfos;
import jakarta.el.MethodNotFoundException;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Scheduler;

@Service

public class DocumentsChunkServiceImpl
		extends AbstractCacheEntryCleanupService<DocumentChunkOperation, DocumentChunkOperationRepository>
		implements IDocumentsChunkService {
	private static final String CHUNKS_CACHE_DIRECTORY_NAME = ".CHCACHE";
	private final GeboDocumentsCacheConfig cacheConfig;
	private final IDocumentsCacheService cacheService;
	private final IGGeboConfigService configService;
	private final IGAIDocumentMetaDataEnricher metaDataEnricher;
	private final IGDocumentReferenceIngestionHandler ingestionHandler;
	private final IGDocumentReferenceFactory docReferenceFactory;
	private final IGPersistentObjectManager persistentObjectManager;
	private final IGeboThreadManager geboThreadManager;
	private final static JTokkitTokenCountEstimator estimator = new JTokkitTokenCountEstimator();
	private final static ObjectMapper objectMapper = new ObjectMapper();
	private final static Logger LOGGER = LoggerFactory.getLogger(DocumentsChunkServiceImpl.class);
	private final static long ttlCacheIt = 10 * 60 * 1000;// tokenizing request has 5 minute validity
	private final Scheduler chunkingScheduler;

	public DocumentsChunkServiceImpl(IDocumentsCacheService cacheService, IGGeboConfigService configService,
			DocumentChunkOperationRepository chunkOperationRepository, IGAIDocumentMetaDataEnricher metaDataEnricher,
			IGDocumentReferenceIngestionHandler ingestionHandler, IGDocumentReferenceFactory docReferenceFactory,
			IGeboThreadManager geboThreadManager, IGPersistentObjectManager persistentObjectManager,
			GeboDocumentsCacheConfig cacheConfig) {
		super(chunkOperationRepository, ttlCacheIt);
		this.cacheService = cacheService;
		this.configService = configService;
		this.ingestionHandler = ingestionHandler;
		this.persistentObjectManager = persistentObjectManager;
		this.metaDataEnricher = metaDataEnricher;
		this.docReferenceFactory = docReferenceFactory;
		this.geboThreadManager = geboThreadManager;
		this.chunkingScheduler = geboThreadManager.getScheduler();
		this.cacheConfig = cacheConfig;
	}

	@Override
	public DocumentChunkingResponse getChunkSet(IGComponentOriginatedDocument document,
			List<AbstractChunkingSpecs> chunkingSpecs, boolean enrichWithMetaData, long tokensPerChunkSet)
			throws DocumentCacheAccessException, IOException, GeboContentHandlerSystemException, GeboIngestionException,
			SearchServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin getChunk(" + document.getCode() + ",...)");
		}
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
		List<DocumentChunkOperation> matchingOperations = repository.findByOriginalDocumentCode(document.getCode());

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
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("operation getChunk(" + document.getCode() + ",...) has found cached data");
			}
			// returning the first chunk as 0 index id
			DocumentChunkOperation operationData = matchingOperation.get();
			return getNextChunkSet(document, operationData.getId(), operationData.getChunkSetsList().get(0));
		}
		// We have to calculate chunking from scratch
		Optional<AbstractChunkingSpecs> textConfig = chunkingSpecs.stream()
				.filter(x -> x.getChunkType() == DocumentChunkType.TEXT).findFirst();
		final DocumentChunkingResponse response = new DocumentChunkingResponse();
		final List<Throwable> exceptions = new ArrayList<Throwable>();
		TokenTextSplitter tokensplitter = null;
		if (textConfig.get() instanceof TextChunkingSpecs textSpecs) {

			tokensplitter = new TokenTextSplitter(textSpecs.getDefaultChunkSize(), textSpecs.getMinChunkSizeChars(),
					textSpecs.getMinChunkLengthToEmbed(), textSpecs.getMaxNumChunks(), textSpecs.isKeepSeparator());
		}
		final TokenTextSplitter usedSplitter = tokensplitter;

		response.setEmpty(true);
		// We ask the cacheService to stream the document
		TypedInputStream is = null;
		try {
			is = this.cacheService.streamDocument(document);

			if (is != null && is.getInputStream() != null) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Doing raw ingestion");
				}

				IngestionHandlerData content = null;
				if (document instanceof GDocumentReference dr) {
					content = ingestionHandler.handleContent(dr, is.getInputStream());
				} else if (document instanceof SearchResult searchResult) {

					GDocumentReference fakeDr = docReferenceFactory.createReference(searchResult.getCode(),
							searchResult.getCode(), is.getContentType(), is.getExtension(), null,
							document.getOriginComponent().getMessagingModuleId(),
							document.getOriginComponent().getMessagingComponentId());
					content = ingestionHandler.handleContent(fakeDr, is.getInputStream());
				} else
					throw new RuntimeException("Unknown reference object type");

				if (!content.isUnmanagedContent()) {
					// if we have an handled content from the ingestion layer we proceed
					Stream<Document> docsStream = content.getStream();
					// We use streaming so huge files or huge datasets are streamed directly to the
					// disk in chunks without heavy memory use
					// Start filling the chunkOperation to remain saved in mongodb for later use and
					// next chunk calls
					final DocumentChunkOperation chunkOperation = new DocumentChunkOperation();
					final List<DocumentChunksSet> chunkSets = new ArrayList<DocumentChunksSet>();
					chunkOperation.setEnrichWithMetaData(enrichWithMetaData);
					chunkOperation.setChunkingSpecs(chunkingSpecs);
					chunkOperation.setOriginalDocumentCode(document.getCode());
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Start looping contents stream");
					}

					final AtomicLong atomicLong = new AtomicLong(0l);
					docsStream.forEach(doc -> {

						try {
							MetaDataHeaderInfos metaDataHeader = null;
							// We calculate metaDataHeader if enrichWithMetaData is true (it will be the
							// same metadata for all other chunks)
							if (enrichWithMetaData && metaDataHeader == null && doc.isText()
									&& document instanceof GDocumentReference dr) {
								try {
									GProject project = dr.getParentProjectCode() != null
											? persistentObjectManager.findById(GProject.class,
													dr.getParentProjectCode())
											: null;
									GKnowledgeBase knowledgeBase = dr.getRootKnowledgebaseCode() != null
											? persistentObjectManager.findById(GKnowledgeBase.class,
													dr.getRootKnowledgebaseCode())
											: null;
									GProjectEndpoint endpoint = dr.getProjectEndpointReference() != null
											? persistentObjectManager.findByReference(dr.getProjectEndpointReference(),
													GProjectEndpoint.class)
											: null;

									metaDataHeader = this.metaDataEnricher.createMetaDataHeader(List.of(doc), dr,
											knowledgeBase, project, endpoint);
								} catch (GeboPersistenceException e) {
									exceptions.add(e);
									return;
								}
							}
							// discard zero length contents
							if (usedSplitter != null && doc.isText() && doc.getText() != null
									&& doc.getText().trim().length() > 0) {

								List<Document> outContents = usedSplitter.split(doc);
								if (enrichWithMetaData && metaDataHeader != null) {
									List<Document> withMetaData = new ArrayList<Document>();
									for (Document thisDoc : outContents) {
										withMetaData.add(this.metaDataEnricher.enrich(thisDoc, metaDataHeader));
									}
									outContents = withMetaData;
								}

								response.setEmpty(outContents.isEmpty());

								for (Document _document : outContents) {

									int bytesSize = _document.getText() != null ? _document.getText().length() * 2 : 0;
									int tokensSize = _document.getText() != null && _document.isText()
											? estimator.estimate(_document.getText())
											: 0;
									_document.getMetadata().put(DocumentMetaInfos.GEBO_BYTES_LENGTH, bytesSize);
									_document.getMetadata().put(DocumentMetaInfos.GEBO_TOKEN_LENGTH, tokensSize);
									response.setEmpty(false);
									DocumentChunk chunk = DocumentChunk.ofText(document.getCode(), _document.getText(),
											_document.getMetadata());
									chunk.setChunkPosition(atomicLong.incrementAndGet());
									;
									chunk.setBytesSize((long) bytesSize);
									chunk.setTokensSize((long) tokensSize);
									chunkOperation
											.setTotalBytesSize(chunkOperation.getTotalBytesSize() + ((long) bytesSize));
									chunkOperation.setTotalTokensSize(
											chunkOperation.getTotalTokensSize() + ((long) tokensSize));
									chunkOperation.setTotalChunks(chunkOperation.getTotalChunks() + 1);
									DocumentChunksSet currentChunkSet = null;
									if (chunkSets.isEmpty()) {
										currentChunkSet = new DocumentChunksSet();
										chunkSets.add(currentChunkSet);
										chunkOperation.getChunkSetsList().add(currentChunkSet.getId());
									} else {
										currentChunkSet = chunkSets.get(0);
									}
									currentChunkSet.getChunks().add(chunk);
									currentChunkSet
											.setTotalBytes(currentChunkSet.getTotalBytes() + chunk.getBytesSize());
									currentChunkSet
											.setTotalTokens(currentChunkSet.getTotalTokens() + chunk.getTokensSize());
									if (currentChunkSet.getTotalTokens() > tokensPerChunkSet) {
										Path writtenFile = Path.of(workDirectory, CHUNKS_CACHE_DIRECTORY_NAME,
												currentChunkSet.getId());
										if (LOGGER.isDebugEnabled()) {
											LOGGER.debug("document " + document.getCode() + " Writing chunk=>"
													+ currentChunkSet.getId() + " tokens=>"
													+ currentChunkSet.getTotalTokens());
										}
										objectMapper.writeValue(writtenFile.toFile(), currentChunkSet);
										chunkSets.clear();
									}
									if (response.getCurrentChunkSet() == null) {
										response.setCurrentChunkSet(currentChunkSet);
									}
								}

							}
						} catch (Throwable th) {
							LOGGER.error("Exception in reading file " + document.getCode(), th);
							exceptions.add(th);
						}

					});
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("End looping contents stream");
					}
					if (!exceptions.isEmpty()) {
						throw new DocumentCacheAccessException("Cannot split in chunk because of an exception",
								exceptions);
					}
					if (!chunkSets.isEmpty()) {
						DocumentChunksSet currentChunkSet = chunkSets.get(0);
						Path writtenFile = Path.of(workDirectory, CHUNKS_CACHE_DIRECTORY_NAME, currentChunkSet.getId());
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("document " + document.getCode() + " Writing chunk=>" + currentChunkSet.getId()
									+ " tokens=>" + currentChunkSet.getTotalTokens());
						}
						objectMapper.writeValue(writtenFile.toFile(), currentChunkSet);
						chunkSets.clear();
					}
					if (!chunkOperation.getChunkSetsList().isEmpty()) {
						response.setId(chunkOperation.getId());
						chunkOperation.setLastAccessed(new Date());
						response.setTotalBytesSize(chunkOperation.getTotalBytesSize());
						response.setTotalTokensSize(chunkOperation.getTotalTokensSize());
						response.setTotalChunksNumber(chunkOperation.getTotalChunks());
						repository.insert(chunkOperation);
						int index = chunkOperation.getChunkSetsList().indexOf(response.getCurrentChunkSet().getId());
						if (chunkOperation.getChunkSetsList().size() > index + 1) {
							response.setNextChunkSetId(chunkOperation.getChunkSetsList().get(index + 1));
						}
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("Saved chunking operation for (" + document.getCode() + ",...) nchunks="
									+ chunkOperation.getChunkSetsList().size());
						}
					}

				}
			}
		} finally {
			try {
				is.getInputStream().close();
			} catch (Throwable t) {
			}
		}
		if (LOGGER.isDebugEnabled())

		{
			LOGGER.debug("End getChunk(" + document.getCode() + ",...)");
		}
		return response;

	}

	@Override
	public DocumentChunkingResponse getNextChunkSet(IGComponentOriginatedDocument document, String chunkRequestId,
			String chunkId) throws DocumentCacheAccessException, IOException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin getNextChunk(" + document.getCode() + ",'" + chunkRequestId + "','" + chunkId + "')");
		}
		Optional<DocumentChunkOperation> optionalChunkOperation = repository.findById(chunkRequestId);
		if (optionalChunkOperation.isEmpty())
			throw new DocumentCacheAccessException("Chunk operation invalid id or expired id =>" + chunkRequestId);
		DocumentChunkOperation operation = optionalChunkOperation.get();
		int index = operation.getChunkSetsList().indexOf(chunkId);
		if (index >= 0) {
			DocumentChunkingResponse response = new DocumentChunkingResponse();
			response.setEmpty(false);
			String workDirectory = configService.getGeboWorkDirectory();
			Path fileToRead = Path.of(workDirectory, CHUNKS_CACHE_DIRECTORY_NAME, chunkId);
			DocumentChunksSet chunkSet = objectMapper.readValue(fileToRead.toFile(), DocumentChunksSet.class);
			response.setCurrentChunkSet(chunkSet);
			response.setId(chunkRequestId);
			response.setTotalChunksNumber(operation.getTotalChunks());
			response.setTotalBytesSize(operation.getTotalBytesSize());
			response.setTotalTokensSize(operation.getTotalTokensSize());
			if (index < operation.getChunkSetsList().size() - 1) {
				String nextChunk = operation.getChunkSetsList().get(index + 1);
				response.setNextChunkSetId(nextChunk);
			}
			operation.setLastAccessed(new Date());
			this.repository.save(operation);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End getNextChunk(" + document.getCode() + ",'" + chunkRequestId + "','" + chunkId + "')");
			}
			return response;
		} else
			throw new DocumentCacheAccessException(chunkId + " not part of the chunking operation " + chunkRequestId);

	}

	@Override
	protected void cleanupResources(DocumentChunkOperation data) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin cleanupResources(..)");
		}
		String workDirectory = configService.getGeboWorkDirectory();
		data.getChunkSetsList().stream().forEach(fileName -> {
			try {
				Path path = Path.of(workDirectory, CHUNKS_CACHE_DIRECTORY_NAME, fileName);
				if (Files.exists(path)) {
					Files.delete(path);
				} else {
					LOGGER.warn("File " + path.toString() + " not found");
				}
			} catch (IOException exc) {
				LOGGER.warn("Exception while deleting " + fileName, exc);
			}
		});
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End cleanupResources(..)");
		}
	}

	@Override
	public DocumentChunkingResponse prepareChunks(IGComponentOriginatedDocument document,
			List<AbstractChunkingSpecs> chunkingSpecs, boolean enrichWithMetaData, long tokensPerChunkSet)
			throws DocumentCacheAccessException, IOException, GeboContentHandlerSystemException, GeboIngestionException,
			SearchServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin prepareChunks(" + document.getCode() + "..)");
		}
		DocumentChunkingResponse firstChunk = getChunkSet(document, chunkingSpecs, enrichWithMetaData,
				tokensPerChunkSet);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End prepareChunks(" + document.getCode() + "..)");
		}
		return firstChunk;
	}

	@Override
	public DocumentChunkingResponse getCachedChunkSet(IGComponentOriginatedDocument document)
			throws DocumentCacheAccessException, IOException, GeboContentHandlerSystemException,
			GeboIngestionException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin getCachedChunk(" + document.getCode() + "..)");
		}
		List<DocumentChunkOperation> data = repository.findByOriginalDocumentCode(document.getCode());
		if (!data.isEmpty()) {
			DocumentChunkOperation entry = data.get(0);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End getCachedChunk(" + document.getCode() + "..) getting next chunk");
			}
			return getNextChunkSet(document, entry.getId(), entry.getChunkSetsList().get(0));
		}

		LOGGER.error("Chunks for document " + document.getCode() + " have not been found");

		throw new DocumentCacheAccessException("No existing cached chunks");
	}

	public Flux<IDocumentChunkWithRef> streamChunks(IGComponentOriginatedDocument document,
			List<AbstractChunkingSpecs> chunkingSpecs, boolean enrichWithMetaData, long tokensPerChunkSet) {
		return this.streamChunksSingle(document, chunkingSpecs, enrichWithMetaData, tokensPerChunkSet);
	}

	private Flux<IDocumentChunkWithRef> streamChunksSingle(IGComponentOriginatedDocument document,
			List<AbstractChunkingSpecs> chunkingSpecs, boolean enrichWithMetaData, long tokensPerChunkSet) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Running streamChunksSingle(" + document.getCode() + ",.....)");
		}
		return Mono.fromCallable(() -> unchecked(() -> {
			try {
				DocumentChunkingResponse response = prepareChunks(document, chunkingSpecs, enrichWithMetaData,
						tokensPerChunkSet);
				return response;
			} catch (Throwable th) {
				LOGGER.error("Error in call to prepareChunks(...)", th);
				return null;
			}
		})).subscribeOn(chunkingScheduler).flatMapMany(firstResponse -> Flux.just(firstResponse).expand(resp -> {
			String nextId = resp.getNextChunkSetId();
			if (nextId == null)
				return Mono.empty();

			return Mono.fromCallable(() -> unchecked(() -> getNextChunkSet(document, resp.getId(), nextId)))
					.subscribeOn(chunkingScheduler);
		}).concatMap(resp -> {
			var set = resp.getCurrentChunkSet();
			var chunks = (set != null && set.getChunks() != null) ? set.getChunks() : List.<DocumentChunk>of();
			var mapped = chunks.stream().map(x -> IDocumentChunkWithRef.of(x, document)).toList();
			return Flux.fromIterable(mapped);
		}));
	}

	public ParallelFlux<IDocumentChunkWithRef> streamChunks(List<? extends IGComponentOriginatedDocument> documents,
			List<AbstractChunkingSpecs> chunkingSpecs, boolean enrichWithMetaData, long tokensPerChunkSet) {
		final int docConcurrency = cacheConfig.getReactiveDocumentsConcurrency();
		return Flux.fromIterable(documents).parallel(docConcurrency).runOn(chunkingScheduler)
				.flatMap(doc -> streamChunksSingle(doc, chunkingSpecs, enrichWithMetaData, tokensPerChunkSet));

	}

	@FunctionalInterface
	private interface CheckedSupplier<T> {
		T get() throws Exception;
	}

	private static <T> T unchecked(CheckedSupplier<T> s) {
		try {
			return s.get();
		} catch (Exception e) {
			throw Exceptions.propagate(e);
		}
	}

	@Override
	public ParallelFlux<IDocumentChunkWithRef> streamChunks(
			org.reactivestreams.Publisher<List<IGComponentOriginatedDocument>> documentsPublisher,
			List<AbstractChunkingSpecs> chunkingSpecs, boolean enrichWithMetaData, long tokensPerChunkSet) {
		final int docConcurrency = cacheConfig.getReactiveDocumentsConcurrency();
		ParallelFlux<IDocumentChunkWithRef> out = Flux.from(documentsPublisher).flatMapIterable(list -> list)
				.parallel(docConcurrency).runOn(chunkingScheduler).flatMap(doc -> Flux
						.defer(() -> streamChunksSingle(doc, chunkingSpecs, enrichWithMetaData, tokensPerChunkSet)));
		return out;
	}

}
