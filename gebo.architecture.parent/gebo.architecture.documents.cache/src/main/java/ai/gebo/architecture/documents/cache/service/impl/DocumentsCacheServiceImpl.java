package ai.gebo.architecture.documents.cache.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.documents.cache.repository.DocumentCacheEntryRepository;
import ai.gebo.architecture.documents.cache.service.DocumentCacheAccessException;
import ai.gebo.architecture.documents.cache.service.IDocumentsCacheService;
import ai.gebo.architecture.documents.cache.service.impl.model.DocumentCacheEntry;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.architecture.search.service.ISearchServiceRepositoryPattern;
import ai.gebo.config.service.IGGeboConfigService;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.base.GObjectRef;
import ai.gebo.model.base.GeboComponentInfo;
import ai.gebo.model.base.IGComponentOriginatedDocument;
import ai.gebo.model.base.TypedInputStream;
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemHandler;
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemHandlerRepositoryPattern;
import ai.gebo.systems.abstraction.layer.model.StreamingPurpose;

@Service
public class DocumentsCacheServiceImpl
		extends AbstractCacheEntryCleanupService<DocumentCacheEntry, DocumentCacheEntryRepository>
		implements IDocumentsCacheService {
	private final IGContentManagementSystemHandlerRepositoryPattern contentManagementSystemHandlerRepositoryPattern;
	private final ISearchServiceRepositoryPattern searchServicesRepository;
	private final IGPersistentObjectManager persistentObjectManager;
	private final IGGeboConfigService configService;
	private final static Logger LOGGER = LoggerFactory.getLogger(DocumentsCacheServiceImpl.class);
	private final static String FILESCACHEFOLDER = ".FCACHE";

	public DocumentsCacheServiceImpl(
			IGContentManagementSystemHandlerRepositoryPattern contentManagementSystemHandlerRepositoryPattern,
			ISearchServiceRepositoryPattern searchServicesRepository, IGPersistentObjectManager persistentObjectManager,
			IGGeboConfigService configService, DocumentCacheEntryRepository repository) {
		super(repository, 5 * 60 * 1000);
		this.configService = configService;
		this.persistentObjectManager = persistentObjectManager;
		this.contentManagementSystemHandlerRepositoryPattern = contentManagementSystemHandlerRepositoryPattern;
		this.searchServicesRepository = searchServicesRepository;
	}

	private IGContentManagementSystemHandler retrieveHandler(GDocumentReference reference)
			throws DocumentCacheAccessException {
		GObjectRef<GProjectEndpoint> projectEndpointReference = reference.getProjectEndpointReference();
		GProjectEndpoint endpoint;
		try {
			endpoint = persistentObjectManager.findByReference(projectEndpointReference, GProjectEndpoint.class);
			if (endpoint == null)
				throw new DocumentCacheAccessException("Endpoint is unfound");
			IGContentManagementSystemHandler handler = contentManagementSystemHandlerRepositoryPattern
					.findByHandledEndpoint(endpoint);
			if (handler == null)
				throw new DocumentCacheAccessException("Project endpoint handler not found");
			return handler;
		} catch (GeboPersistenceException e) {
			throw new DocumentCacheAccessException("Exception while accessing project endpoint", e);
		}

	}

	@Override
	public TypedInputStream streamDocument(StreamingPurpose streamingPurpose, IGComponentOriginatedDocument reference)
			throws DocumentCacheAccessException, GeboContentHandlerSystemException, SearchServiceException,
			IOException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin streamDocument(" + reference.getCode() + ");");
		}
		if (reference instanceof GDocumentReference docreference) {
			IGContentManagementSystemHandler handler = retrieveHandler(docreference);
			if (handler.isContentsOnLocalFilesystem()) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("End streamDocument(" + reference.getCode() + ") => streaming from local filesystem");
				}
				return handler.streamContent(streamingPurpose, docreference, new HashMap());
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End streamDocument(" + reference.getCode() + ") => streaming and cache remote system");
			}
			SupplierWithException isSupplier = () -> {

				return handler.streamContent(streamingPurpose, docreference, new HashMap());

			};
			return streamDocumentWithLocalCache(isSupplier, reference);
		}
		if (reference instanceof SearchResult searchResult) {
			GeboComponentInfo originComponent = searchResult.getOriginComponent();
			ISearchService searchService = searchServicesRepository.findByOriginComponent(originComponent);
			SupplierWithException isSupplier = () -> {
				return searchService.loadSearchResult(searchResult);
			};

			return streamDocumentWithLocalCache(isSupplier, reference);
		}
		throw new RuntimeException("Object of type " + reference != null ? reference.getClass().getName() : "Unkown");
	}

	private TypedInputStream streamDocumentWithLocalCache(SupplierWithException typedInputStreamSupplier,
			IGComponentOriginatedDocument reference) throws IOException, DocumentCacheAccessException,
			GeboContentHandlerSystemException, SearchServiceException {
		Optional<DocumentCacheEntry> inCacheCopy = repository.findById(reference.getCode());
		boolean loadAndCache = true;
		if (inCacheCopy.isPresent() && inCacheCopy.get().getBinaryDocumentName() != null) {
			loadAndCache = false;
			Path filePath = Path.of(configService.getGeboWorkDirectory(), FILESCACHEFOLDER,
					inCacheCopy.get().getBinaryDocumentName());
			if (Files.exists(filePath)) {
				Date lastModifiedActual = reference.getModificationDate();
				FileTime localCopyTime = Files.getLastModifiedTime(filePath);
				if (lastModifiedActual != null && localCopyTime != null) {
					loadAndCache = lastModifiedActual.getTime() > localCopyTime.toMillis();
				}
				if (!loadAndCache) {
					// Serve from local filesystem
					DocumentCacheEntry cacheEntry = inCacheCopy.get();
					cacheEntry.setLastAccessed(new Date());
					repository.save(cacheEntry);
					InputStream is = Files.newInputStream(filePath, StandardOpenOption.READ);
					return TypedInputStream.of(is, cacheEntry.getContentType(), cacheEntry.getExtension());
				}
			}
			repository.delete(inCacheCopy.get());
		}
		String newFileName = UUID.randomUUID().toString() + "-" + System.currentTimeMillis();
		DocumentCacheEntry cacheEntry = new DocumentCacheEntry();
		cacheEntry.setBinaryDocumentName(newFileName);
		cacheEntry.setId(reference.getCode());
		cacheEntry.setLastAccessed(new Date());
		Path cacheFolder = Path.of(configService.getGeboWorkDirectory(), FILESCACHEFOLDER);
		Files.createDirectories(cacheFolder);
		Path filePath = Path.of(configService.getGeboWorkDirectory(), FILESCACHEFOLDER,
				cacheEntry.getBinaryDocumentName());
		TypedInputStream tis = typedInputStreamSupplier.get();
		if (tis != null && tis.getInputStream() != null) {
			Files.copy(tis.getInputStream(), filePath);
			try {
				tis.getInputStream().close();
			} catch (Throwable t) {
			}
			cacheEntry.setContentType(tis.getContentType());
			repository.save(cacheEntry);
			return TypedInputStream.of(Files.newInputStream(filePath, StandardOpenOption.READ), tis.getContentType(),
					tis.getExtension());
		}
		return null;
	}

	@Override
	protected void cleanupResources(DocumentCacheEntry data) {
		Path path = Path.of(configService.getGeboWorkDirectory(), FILESCACHEFOLDER, data.getBinaryDocumentName());
		if (Files.exists(path)) {
			try {
				Files.delete(path);
			} catch (IOException e) {
				LOGGER.warn("Cannot delete file" + path, e);
			}
		} else {
			LOGGER.warn("Cannot delete file" + path + " because it does not exist");
		}

	}

}
