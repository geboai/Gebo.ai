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

import org.springframework.stereotype.Service;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.documents.cache.service.DocumentCacheAccessException;
import ai.gebo.architecture.documents.cache.service.IDocumentsCacheService;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.config.service.IGGeboConfigService;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.base.GObjectRef;
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemHandler;
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemHandlerRepositoryPattern;

@Service
public class DocumentsCacheServiceImpl
		extends AbstractCacheEntryCleanupService<DocumentCacheEntry, DocumentCacheEntryRepository>
		implements IDocumentsCacheService {
	private final IGContentManagementSystemHandlerRepositoryPattern contentManagementSystemHandlerRepositoryPattern;
	private final IGPersistentObjectManager persistentObjectManager;
	private final IGGeboConfigService configService;

	private final static String FILESCACHEFOLDER = ".FCACHE";

	public DocumentsCacheServiceImpl(
			IGContentManagementSystemHandlerRepositoryPattern contentManagementSystemHandlerRepositoryPattern,
			IGPersistentObjectManager persistentObjectManager, IGGeboConfigService configService,
			DocumentCacheEntryRepository repository) {
		super(repository, 5 * 60 * 1000);
		this.configService = configService;
		this.persistentObjectManager = persistentObjectManager;
		this.contentManagementSystemHandlerRepositoryPattern = contentManagementSystemHandlerRepositoryPattern;
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
	public InputStream streamDocument(GDocumentReference reference)
			throws DocumentCacheAccessException, GeboContentHandlerSystemException, IOException {
		IGContentManagementSystemHandler handler = retrieveHandler(reference);
		if (handler.isContentsOnLocalFilesystem())
			return handler.streamContent(reference, new HashMap());
		return streamDocumentWithLocalCache(reference, handler);
	}

	private InputStream streamDocumentWithLocalCache(GDocumentReference reference,
			IGContentManagementSystemHandler handler)
			throws DocumentCacheAccessException, GeboContentHandlerSystemException, IOException {
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
					return Files.newInputStream(filePath, StandardOpenOption.READ);
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
		Files.copy(handler.streamContent(reference, new HashMap()), filePath);
		repository.save(cacheEntry);
		return Files.newInputStream(filePath, StandardOpenOption.READ);
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
