/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.onxx_transformers_embedding.services;

import java.util.List;

import org.springframework.stereotype.Service;

import ai.gebo.llms.models.metainfos.ModelMetaInfo;
import ai.gebo.llms.onxx_transformers_embedding.model.GONNXTransformersEmbeddingModelChoice;
import ai.gebo.llms.onxx_transformers_embedding.model.GONNXTransformersEmbeddingModelConfig;
import ai.gebo.model.OperationStatus;

/**
 * The embedding models the ONNX transformers provider runs.
 * <p>
 * The model runs in process: there is no remote models api to ask. The embedding
 * model is built on the model spring-ai-transformers bundles, so that is the one
 * model offered. The list used to ask an ollama server's /api/tags at the base url,
 * offering whatever that server had, or failing when there was none.
 */
@Service
public class ONNXTransformersModelsLookupService {

	/** The model spring-ai-transformers bundles and the embedding model runs */
	public static final String BUNDLED_MODEL = "sentence-transformers/all-MiniLM-L6-v2";

	public ONNXTransformersModelsLookupService() {

	}

	public OperationStatus<List<GONNXTransformersEmbeddingModelChoice>> getEmbeddingModels(
			GONNXTransformersEmbeddingModelConfig config) {
		GONNXTransformersEmbeddingModelChoice choice = new GONNXTransformersEmbeddingModelChoice();
		choice.setCode(BUNDLED_MODEL);
		choice.setDescription("all-MiniLM-L6-v2 (bundled, runs in process, 384 dimensions)");
		choice.setInformativeUrl("https://huggingface.co/" + BUNDLED_MODEL);
		ModelMetaInfo meta = new ModelMetaInfo();
		meta.setProviderId("onnx-transformers");
		meta.setModelId(BUNDLED_MODEL);
		meta.setEmbeddingModel(true);
		meta.setChatModel(false);
		meta.setDescription(choice.getDescription());
		meta.setInformativeUrl(choice.getInformativeUrl());
		choice.setMetaInfos(meta);
		return OperationStatus.of(List.of(choice));
	}
}
