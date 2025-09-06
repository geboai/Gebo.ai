package ai.gebo.architecture.documents.cache.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.xml.crypto.Data;

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
import ai.gebo.config.service.IGGeboConfigService;
import ai.gebo.document.model.GeboDocument;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.system.ingestion.GeboIngestionException;
import jakarta.el.MethodNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DocumentsChunkServiceImpl implements IDocumentsChunkService {
	private static final String CHUNKS_CACHE_DIRECTORY_NAME = ".CHCACHE";
	private final IDocumentsCacheService cacheService;
	private final IGGeboConfigService configService;
	private final DocumentChunkOperationRepository chunkOperationRepository;
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final static long ttlCacheIt = 2 * 60 * 1000;// tokenizing request has 2 minute validity

	@Override
	public DocumentChunkingResponse getChunk(GDocumentReference document, List<AbstractChunkingSpecs> chunkingSpecs)
			throws DocumentCacheAccessException, IOException, GeboContentHandlerSystemException, GeboIngestionException {
		String workDirectory = configService.getGeboWorkDirectory();
		Path rootCachePath = Path.of(workDirectory, CHUNKS_CACHE_DIRECTORY_NAME);
		Files.createDirectories(rootCachePath);

		GeboDocument geboDocument = cacheService.getDocument(document);
		if (chunkingSpecs == null || chunkingSpecs.isEmpty())
			throw new DocumentCacheAccessException("No chunking specs");
		Optional<AbstractChunkingSpecs> imageConfig = chunkingSpecs.stream()
				.filter(x -> x.getChunkType() == DocumentChunkType.IMAGE).findFirst();
		if (imageConfig.isPresent())
			throw new MethodNotFoundException("The images chunking is not yet implemented");
		List<DocumentChunkOperation> matchingOperations = chunkOperationRepository
				.findByOriginalDocumentCode(document.getCode());
		Comparator<? super DocumentChunkOperation> comparator = (o1,
				o2) -> ((int) (o1.getLastAccessed().getTime() - o2.getLastAccessed().getTime()) / 1000);
		Optional<DocumentChunkOperation> matchingOperation = matchingOperations.stream().sorted(comparator)
				.filter(x -> {
					boolean matchingCriterias = true;
					for (AbstractChunkingSpecs spec : chunkingSpecs) {
						Optional<AbstractChunkingSpecs> sameType = x.getChunkingSpecs().stream()
								.filter(y -> y.getClass().equals(spec.getClass())).findFirst();
						matchingCriterias = matchingCriterias && sameType.isPresent();
						if (matchingCriterias) {
							matchingCriterias = matchingCriterias && sameType.get().equals(spec);
						}
					}
					return matchingCriterias;
				}).findFirst();
		if (matchingOperation.isPresent()) {
			// returning the first chunk as 0 index id
			DocumentChunkOperation operationData = matchingOperation.get();
			return getNextChunk(document, operationData.getId(), operationData.getChunksList().get(0));
		}
		Optional<AbstractChunkingSpecs> textConfig = chunkingSpecs.stream()
				.filter(x -> x.getChunkType() == DocumentChunkType.TEXT).findFirst();
		DocumentChunkingResponse response = new DocumentChunkingResponse();
		response.setEmpty(true);
		if (textConfig.isPresent()) {
			AbstractChunkingSpecs spec = textConfig.get();
			if (spec instanceof TextChunkingSpecs textSpecs) {
				DocumentChunkOperation chunkOperation = new DocumentChunkOperation();
				chunkOperation.setChunkingSpecs(chunkingSpecs);
				chunkOperation.setOriginalDocumentCode(document.getCode());
				TokenTextSplitter tokensplitter = new TokenTextSplitter(textSpecs.getDefaultChunkSize(),
						textSpecs.getMinChunkSizeChars(), textSpecs.getMinChunkLengthToEmbed(),
						textSpecs.getMaxNumChunks(), textSpecs.isKeepSeparator());
				List<Document> originalFragments = geboDocument.getTexts().stream().map(x -> {
					Map<String, Object> map = geboDocument.getCustomMetaData();
					map.putAll(x.getCustomMetaData());
					Document _document = new Document(UUID.randomUUID().toString(), x.getContent(), map);
					return _document;
				}).filter(doc -> !doc.getText().isEmpty()).toList();
				List<Document> outContents = tokensplitter.apply(originalFragments);
				JTokkitTokenCountEstimator estimator = new JTokkitTokenCountEstimator();
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
					chunkOperation.getChunksList().add(chunk.getId());
					Path writtenFile = Path.of(workDirectory, CHUNKS_CACHE_DIRECTORY_NAME, chunk.getId());
					objectMapper.writeValue(writtenFile.toFile(), chunk);
					if (response.getCurrentChunk() == null) {
						response.setCurrentChunk(chunk);
					}
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
			} else
				throw new IllegalArgumentException("The text specs have to be of type TextChunkingSpecs");
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
