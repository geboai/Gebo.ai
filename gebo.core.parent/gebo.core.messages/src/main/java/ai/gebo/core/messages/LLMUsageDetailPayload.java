/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.core.messages;

import ai.gebo.application.messaging.model.GBaseMessagePayload;
import ai.gebo.model.ModelType;
import lombok.Data;

/**
 * Carries one LLM call's usage detail from the emitting side
 * ({@code LLMSUsageCrudServiceImpl}, {@code gebo.architecture.llms.abstraction.layer})
 * to the {@code LLMS-USAGE-MONITOR}/{@code USAGE-CONCENTRATOR} receiver
 * ({@code gebo.architecture.compute.workflow}). {@code usageTimestamp} is named
 * distinctly from the inherited {@link #getTimestamp()} (envelope creation time)
 * since it carries the actual epoch-millis of the LLM call, used by the receiver
 * for the same range queries the old {@code LLMUsageDetail.timestamp} field served.
 */
@Data
public class LLMUsageDetailPayload extends GBaseMessagePayload {
	private String providerId;
	private String username;
	private String model;
	private String callerStack;
	private ModelType modelType;
	private long latency;
	private long inputToken;
	private long outputToken;
	private long totalToken;
	private long usageTimestamp;
}
