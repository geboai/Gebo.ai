/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.vectorstores.local;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.embedding.EmbeddingModel;

import ai.gebo.ragsystem.vectorstores.local.model.LocalConfig;

/**
 * Hands out ONE {@link GeboLocalVectorStore} per data file, reference counted.
 *
 * The sharing is a correctness requirement, not an optimisation. The embedded
 * store holds its corpus in its own instance and serialises that whole instance
 * when it saves, so two instances over the same file would each see half the
 * writes and the later save would silently discard the other's. A remote store
 * like Qdrant never has to care, but a second instance is easy to reach here -
 * reconfiguring an embedding model, or testing the configuration twice.
 *
 * A JVM shutdown hook flushes whatever is still dirty, so an operator stopping
 * the service does not lose the fragments written since the last debounced save.
 */
public final class LocalVectorStoreRegistry {

	private final static Logger LOGGER = LoggerFactory.getLogger(LocalVectorStoreRegistry.class);

	/** Open stores and their user count, keyed by absolute data file path. */
	private static final Map<String, Entry> OPEN = new HashMap<>();

	static {
		Runtime.getRuntime().addShutdownHook(new Thread(LocalVectorStoreRegistry::flushAll, "gebo-local-vectorstore-flush"));
	}

	/** One open store and how many users hold it. */
	private static final class Entry {

		/** The shared store. */
		private final GeboLocalVectorStore store;

		/** How many holders still use it. */
		private int users;

		/**
		 * @param store the shared store
		 */
		private Entry(GeboLocalVectorStore store) {
			this.store = store;
			this.users = 0;
		}
	}

	/**
	 * Not instantiable.
	 */
	private LocalVectorStoreRegistry() {
	}

	/**
	 * Returns the store for the given file, opening it on first use.
	 *
	 * @param storeFile      the data file of this collection
	 * @param config         settings applied when the store is opened
	 * @param embeddingModel model used to vectorise text
	 * @return a store the caller must eventually close
	 * @throws IOException if the file cannot be created or read
	 */
	static synchronized GeboLocalVectorStore acquire(Path storeFile, LocalConfig config, EmbeddingModel embeddingModel)
			throws IOException {
		final String key = key(storeFile);
		Entry entry = OPEN.get(key);
		if (entry == null) {
			entry = new Entry(new GeboLocalVectorStore(storeFile, config, embeddingModel));
			OPEN.put(key, entry);
		}
		entry.users++;
		return entry.store;
	}

	/**
	 * Lets go of a store, persisting and forgetting it once no user is left.
	 *
	 * Idempotent for an already released store, because the abstraction layer
	 * closes a store before replacing it and may do so more than once.
	 *
	 * @param store the store being closed
	 */
	static synchronized void release(GeboLocalVectorStore store) {
		if (store == null) {
			return;
		}
		final String key = key(store.getStoreFile());
		Entry entry = OPEN.get(key);
		if (entry == null || entry.store != store) {
			// Already gone: still make sure nothing stays unwritten.
			store.flush();
			return;
		}
		entry.users--;
		if (entry.users > 0) {
			return;
		}
		OPEN.remove(key);
		LOGGER.info("Closing the embedded vector store of {}", key);
		store.flush();
	}

	/**
	 * Persists every open store that has unsaved writes.
	 */
	static void flushAll() {
		List<GeboLocalVectorStore> snapshot;
		synchronized (LocalVectorStoreRegistry.class) {
			snapshot = new ArrayList<>();
			for (Entry entry : OPEN.values()) {
				snapshot.add(entry.store);
			}
		}
		// Flushing outside the registry lock: a save can take a while on a large
		// corpus and must not block a store being acquired meanwhile.
		for (GeboLocalVectorStore store : snapshot) {
			try {
				store.flush();
			} catch (Throwable failure) {
				LOGGER.error("Cannot flush the embedded vector store of " + store.getStoreFile(), failure);
			}
		}
	}

	/**
	 * Canonical registry key of a data file.
	 *
	 * @param storeFile the data file
	 * @return its absolute normalised path
	 */
	private static String key(Path storeFile) {
		return storeFile.toAbsolutePath().normalize().toString();
	}
}
