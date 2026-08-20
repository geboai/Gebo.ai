/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.application.messaging.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * One concrete edge of the flow graph: an engine applied between a source and a
 * destination endpoint.
 *
 * <p>
 * {@link #dataSourceId} and {@link #dataDestinationId} hold <em>qualified</em>
 * endpoint ids - see {@link GDataFlowMetaInfos#qualifiedId(String)}. A
 * {@link DataEndpoint#getId()} on its own is unique only within the reporting
 * component, while the flows this describes routinely span components on
 * different microservices: a document read by {@code git-module}'s content
 * handler is chunked by {@code tokenizer-module}, embedded by
 * {@code vectorizator-module} and indexed by {@code fulltext-module}.
 * </p>
 *
 * <p>
 * {@code @Data} is required here, not decorative: this object crosses the
 * topology REST hop and is serialized by Jackson, which needs the accessors to
 * see the fields at all.
 * </p>
 */
@Data
public class DataTransformationInfo {
	@NotNull
	private String id = null;
	@NotNull
	private String description = null;
	@NotNull
	private DataTransformationMetaInfo transformationInfo = null;

	/** Qualified id of the endpoint the data is read from. */
	@NotNull
	private String dataSourceId = null;

	/** Qualified id of the endpoint the transformed data is written to. */
	@NotNull
	private String dataDestinationId = null;

	/**
	 * Creates one edge of the flow graph.
	 *
	 * @param id                the id, unique within the reporting component's report
	 * @param description       what this particular flow is
	 * @param engine            the engine applied
	 * @param dataSourceId      qualified id of the source endpoint
	 * @param dataDestinationId qualified id of the destination endpoint
	 */
	public static DataTransformationInfo of(String id, String description, DataTransformationMetaInfo engine,
			String dataSourceId, String dataDestinationId) {
		DataTransformationInfo info = new DataTransformationInfo();
		info.setId(id);
		info.setDescription(description);
		info.setTransformationInfo(engine);
		info.setDataSourceId(dataSourceId);
		info.setDataDestinationId(dataDestinationId);
		return info;
	}
}
