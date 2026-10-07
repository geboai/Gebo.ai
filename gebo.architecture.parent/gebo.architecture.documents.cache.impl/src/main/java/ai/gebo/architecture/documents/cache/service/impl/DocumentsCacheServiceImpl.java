package ai.gebo.architecture.documents.cache.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.documents.access.DocumentContentStreamerException;
import ai.gebo.architecture.documents.access.IGDocumentContentStreamer;
import ai.gebo.architecture.documents.access.StreamingPurpose;
import ai.gebo.architecture.documents.cache.config.DocumentsCacheTtlConfig;
import ai.gebo.architecture.documents.cache.repository.DocumentCacheEntryRepository;
import ai.gebo.architecture.documents.cache.service.DocumentCacheAccessException;
import ai.gebo.architecture.documents.cache.service.IDocumentsCacheService;
import ai.gebo.architecture.documents.cache.service.impl.model.DocumentCacheEntry;
import ai.gebo.config.service.IGGeboConfigService;
import ai.gebo.model.base.IGComponentOriginatedDocument;
import ai.gebo.model.base.TypedInputStream;

@Service
public class DocumentsCacheServiceImpl
		extends AbstractCacheEntryCleanupService<DocumentCacheEntry, DocumentCacheEntryRepository>
		implements IDocumentsCacheService {

	private static final String ERROR_ACCESSING_DOCUMENT = "Error accessing document";
	private final IGDocumentContentStreamer documentContentStreamer;
	private final IGGeboConfigService configService;
	private final static Logger LOGGER = LoggerFactory.getLogger(DocumentsCacheServiceImpl.class);
	private final static String FILESCACHEFOLDER = ".FCACHE";

	public DocumentsCacheServiceImpl(

			IGGeboConfigService configService, DocumentCacheEntryRepository repository,
			IGDocumentContentStreamer documentContentStreamer, DocumentsCacheTtlConfig ttlConfig) {
		// a copy lives this long after its last access (ai.gebo.documents-cache.ttl-seconds)
		super(repository, ttlConfig.ttlMillis());
		this.documentContentStreamer = documentContentStreamer;
		this.configService = configService;

	}

	/**
	 * The copies not accessed for their time to live deleted, records and files: the
	 * expiry of this cache only (the chunk cache's chunks are read later, by the steps of
	 * their session, and are released with it).
	 */
	@Scheduled(initialDelay = 10000, fixedRate = 120000)
	public void expireCopies() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("expireCopies() the copies not accessed for " + cacheLastUsedMillisecondAgoExpiration / 1000
					+ " s");
		}
		checkExpirationTick();
	}

	@Override
	public TypedInputStream streamDocument(StreamingPurpose streamingPurpose, IGComponentOriginatedDocument reference)
			throws DocumentCacheAccessException, IOException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin streamDocument(" + reference.getCode() + ");");
		}
		SupplierWithException isSupplier = () -> {
			return documentContentStreamer.streamContent(streamingPurpose, reference);
		};
		return streamDocumentWithLocalCache(isSupplier, reference);

	}

	private TypedInputStream streamDocumentWithLocalCache(SupplierWithException typedInputStreamSupplier,
			IGComponentOriginatedDocument reference) throws IOException, DocumentCacheAccessException {
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
			// a stale copy, or a record whose file is gone: its file too goes
			if (Files.exists(filePath)) {
				cleanupResources(inCacheCopy.get());
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
		TypedInputStream tis;
		try {
			tis = typedInputStreamSupplier.get();
			if (tis != null && tis.getInputStream() != null) {
				Files.copy(tis.getInputStream(), filePath);
				try {
					tis.getInputStream().close();
				} catch (Throwable t) {
				}
				cacheEntry.setContentType(tis.getContentType());
				repository.save(cacheEntry);
				return TypedInputStream.of(Files.newInputStream(filePath, StandardOpenOption.READ),
						tis.getContentType(), tis.getExtension());
			}
			return null;
		} catch (DocumentCacheAccessException | DocumentContentStreamerException | IOException e) {
			LOGGER.error(ERROR_ACCESSING_DOCUMENT, e);
			throw new DocumentCacheAccessException(ERROR_ACCESSING_DOCUMENT, e);
		}

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
