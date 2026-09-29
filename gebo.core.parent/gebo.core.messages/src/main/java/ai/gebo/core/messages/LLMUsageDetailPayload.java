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
	/**
	 * Response time of the call in milliseconds: from the request being issued to the
	 * response being complete, a streamed response included, with any tool calling
	 * round trips performed inside the call.
	 */
	private long responseTime;
	/**
	 * Time to first token in milliseconds: from the request being issued to the first
	 * chunk carrying generated content. Measured only for streamed chat calls, null
	 * otherwise.
	 */
	private Long timeToFirstToken;
	/**
	 * Cost of the call in {@link #currencyCode}, from the model's pricing conditions;
	 * null when the model has no pay per use price.
	 */
	private Double cost;
	/** ISO 4217 currency of {@link #cost}. */
	private String currencyCode;
	private long inputToken;
	private long outputToken;
	private long totalToken;
	/**
	 * The API secret code the call went through, the pseudo key "__no-api-key__" for a
	 * model without one: it attributes the traffic to the provider deal covering the
	 * key. Null in the records written before it existed.
	 */
	private String apiSecretCode;
	private long usageTimestamp;
	private LLMCallOutcome outcome;
}
