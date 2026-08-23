/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.application.messaging.model;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * An engine that changes the form of data - a chunker, an embedding model, a
 * graph extractor, a full-text indexer.
 *
 * <p>
 * This describes the engine's declared capability. The concrete edge it is
 * applied on, between two specific endpoints, is a
 * {@link DataTransformationInfo}.
 * </p>
 */
@Data
public class DataTransformationMetaInfo {
	@NotNull
	private String id = null;
	@NotNull
	private String description = null;

	/**
	 * What the engine consumes. Without it the screen cannot tell that the
	 * tokenizer takes {@link MetaEndpointType#DOCUMENTS} while the vectorizator
	 * takes {@link MetaEndpointType#CHUNK}, which is half of what makes the flow
	 * graph readable.
	 */
	@NotNull @NotEmpty
	List<MetaEndpointType> transformFrom = null;

	/** What the engine produces. */
	@NotNull @NotEmpty
	List<MetaEndpointType> transformInto = null;

	/**
	 * Creates an engine description.
	 *
	 * @param id            the id, unique within the reporting component's report
	 * @param description   what the engine is, e.g. the model actually configured
	 * @param transformFrom what it consumes
	 * @param transformInto what it produces
	 */
	public static DataTransformationMetaInfo of(String id, String description, List<MetaEndpointType> transformFrom,
			List<MetaEndpointType> transformInto) {
		DataTransformationMetaInfo info = new DataTransformationMetaInfo();
		info.setId(id);
		info.setDescription(description);
		info.setTransformFrom(transformFrom);
		info.setTransformInto(transformInto);
		return info;
	}
}
