/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.vectorstores.local;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.ai.embedding.EmbeddingModel;

import ai.gebo.architecture.environment.EnvironmentHolder;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelConfig;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.vectorstores.IGExtendedVectorStore;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactory;
import ai.gebo.ragsystem.vectorstores.local.model.LocalConfig;

/**
 * Creates one {@link GeboLocalVectorStore} per embedding model configuration.
 *
 * Each embedding configuration gets its own data file, named after its code, the
 * same way the Qdrant factory gives each configuration its own collection.
 * Keeping them apart is what lets two embedding models of different width coexist:
 * vectors of mixed dimensions cannot be compared.
 */
public class LocalVectorStoreFactory implements IGVectorStoreFactory {

	/** Directory name used under the work directory when none is configured. */
	public static final String DEFAULT_DIRECTORY_NAME = "vectorstore-local";

	/** Extension of a collection data file. */
	public static final String STORE_FILE_EXTENSION = ".json";

	/** The settings every store built here is opened with. */
	final LocalConfig localConfig;

	/**
	 * @param localConfig settings of the embedded store, may be null for defaults
	 */
	public LocalVectorStoreFactory(LocalConfig localConfig) {
		this.localConfig = localConfig == null ? new LocalConfig() : localConfig;
	}

	/**
	 * Opens the store of one embedding configuration.
	 *
	 * @param embeddingConfiguration configuration whose code names the collection
	 * @param embeddingModel         model used to vectorise text
	 * @return the embedded vector store
	 * @throws LLMConfigException if the store cannot be opened
	 */
	@Override
	public IGExtendedVectorStore create(GBaseEmbeddingModelConfig embeddingConfiguration, EmbeddingModel embeddingModel)
			throws LLMConfigException {
		try {
			Path storeFile = resolveBaseDirectory()
					.resolve(sanitize(embeddingConfiguration.getCode()) + STORE_FILE_EXTENSION);
			return LocalVectorStoreRegistry.acquire(storeFile, localConfig, embeddingModel);
		} catch (Throwable e) {
			throw new LLMConfigException("Cannot open the embedded vector store for embedding configuration '"
					+ (embeddingConfiguration == null ? "null" : embeddingConfiguration.getCode()) + "': "
					+ e.getMessage(), e);
		}
	}

	/**
	 * Resolves the directory that holds one data file per collection.
	 *
	 * The configured path wins; otherwise the data goes under
	 * {@code $GEBO_WORK_DIRECTORY}, which is the volume the deployment already
	 * treats as persistent, and falls back to the JVM working directory when the
	 * environment carries nothing.
	 *
	 * @return the base directory of every embedded store file
	 */
	Path resolveBaseDirectory() {
		String configured = localConfig.getDirectory();
		if (configured != null && !configured.isBlank()) {
			return Paths.get(configured.trim());
		}
		// Read through EnvironmentHolder rather than its cached constant: the tests
		// set GEBO_WORK_DIRECTORY from a static initializer, which may well run
		// after that class has been loaded.
		String work = EnvironmentHolder.getPropertyOrEnvironment(EnvironmentHolder.GEBO_WORK_DIRECTORY);
		if (work != null && !work.isBlank()) {
			return Paths.get(work.trim(), DEFAULT_DIRECTORY_NAME);
		}
		return Paths.get(DEFAULT_DIRECTORY_NAME);
	}

	/**
	 * Makes a collection code safe to use as a file name.
	 *
	 * @param code the embedding configuration code
	 * @return a file name that is valid on Linux and on Windows
	 */
	static String sanitize(String code) {
		if (code == null || code.isBlank()) {
			return "default";
		}
		// Windows rejects \ / : * ? " < > | in a path segment, and the code comes
		// from an administrator typing a free text identifier.
		return code.trim().replaceAll("[^a-zA-Z0-9-_.]", "_");
	}
}
