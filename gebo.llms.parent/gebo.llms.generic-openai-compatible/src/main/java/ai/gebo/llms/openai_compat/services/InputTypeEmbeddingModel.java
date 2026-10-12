/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.openai_compat.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingOptions;

import ai.gebo.llms.abstraction.layer.services.IGRoleAwareEmbeddingModel;

/**
 * An OpenAI compatible embedding model sending an {@code input_type} with every
 * request: the asymmetric retrieval models (NVIDIA's nemotron embed and embedqa,
 * "passage" when indexing, "query" when querying) refuse a request without one. The
 * input types come from the model's parameter rules in the models library.
 * <p>
 * A request is made from the provider model's own configured options, with the input
 * type added: options built from nothing would reset settings the provider model
 * applies on every call, such as its timeout. A request that did not say what it
 * embeds is embedded as documents.
 */
public class InputTypeEmbeddingModel implements IGRoleAwareEmbeddingModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(InputTypeEmbeddingModel.class);
	/** The request field the asymmetric models read. */
	static final String INPUT_TYPE = "input_type";
	/** The text embedded once to learn the dimensions, as Spring AI's own probe does. */
	static final String DIMENSIONS_PROBE = "Test String";

	private final OpenAiEmbeddingModel delegate;
	private final String queryInputType;
	private final String documentInputType;
	private final MetadataMode metadataMode;
	private volatile Integer dimensions = null;

	public InputTypeEmbeddingModel(OpenAiEmbeddingModel delegate, String queryInputType, String documentInputType,
			MetadataMode metadataMode) {
		this.delegate = delegate;
		this.queryInputType = queryInputType;
		this.documentInputType = documentInputType;
		this.metadataMode = metadataMode;
	}

	@Override
	public EmbeddingRequest forQuery(EmbeddingRequest request) {
		return withInputType(request, queryInputType);
	}

	@Override
	public EmbeddingRequest forDocuments(EmbeddingRequest request) {
		return withInputType(request, documentInputType);
	}

	private EmbeddingRequest withInputType(EmbeddingRequest request, String inputType) {
		final OpenAiEmbeddingOptions configured = delegate.getOptions();
		final Map<String, Object> extraBody = new HashMap<>();
		if (configured != null && configured.getExtraBody() != null) {
			extraBody.putAll(configured.getExtraBody());
		}
		extraBody.put(INPUT_TYPE, inputType);
		final OpenAiEmbeddingOptions.Builder options = OpenAiEmbeddingOptions.builder();
		if (configured != null) {
			options.from(configured);
		}
		options.merge(request.getOptions()).extraBody(extraBody);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Embedding " + request.getInstructions().size() + " text(s) with " + INPUT_TYPE + "="
					+ inputType);
		}
		return new EmbeddingRequest(request.getInstructions(), options.build());
	}

	/** Whether the request already says what it embeds. */
	private static boolean hasInputType(EmbeddingRequest request) {
		return request.getOptions() instanceof OpenAiEmbeddingOptions options && options.getExtraBody() != null
				&& options.getExtraBody().containsKey(INPUT_TYPE);
	}

	@Override
	public EmbeddingResponse call(EmbeddingRequest request) {
		return delegate.call(hasInputType(request) ? request : forDocuments(request));
	}

	@Override
	public float[] embed(String text) {
		return call(forQuery(new EmbeddingRequest(List.of(text), null))).getResults().get(0).getOutput();
	}

	@Override
	public float[] embed(Document document) {
		final EmbeddingResponse response = call(
				forDocuments(new EmbeddingRequest(List.of(document.getFormattedContent(metadataMode)), null)));
		return response.getResults().isEmpty() ? new float[0] : response.getResults().get(0).getOutput();
	}

	/**
	 * Learnt once by embedding a probe text as a query: the provider model's own probe
	 * would send no input type and be refused.
	 */
	@Override
	public int dimensions() {
		Integer known = dimensions;
		if (known == null) {
			known = embed(DIMENSIONS_PROBE).length;
			dimensions = known;
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Embedding model " + (delegate.getOptions() != null ? delegate.getOptions().getModel() : null)
						+ " has " + known + " dimensions");
			}
		}
		return known;
	}

	@Override
	public String getEmbeddingContent(Document document) {
		return delegate.getEmbeddingContent(document);
	}

	/** The provider model this one sends its requests through. */
	public OpenAiEmbeddingModel getDelegate() {
		return delegate;
	}
}
