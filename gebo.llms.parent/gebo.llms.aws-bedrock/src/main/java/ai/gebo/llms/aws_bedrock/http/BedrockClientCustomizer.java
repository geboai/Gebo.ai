/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.aws_bedrock.http;

import java.time.Duration;

import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;

import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProvider;
import ai.gebo.llms.abstraction.layer.services.config.GeboLlmsClientConfig;

/**
 * Thin adapter mapping the transport-agnostic timeouts and retry budget provided by
 * {@link IGLlmsServiceClientsProvider} onto the AWS SDK v2 client builders used by the
 * Bedrock services.
 * <p>
 * The AWS SDK has its own defaults, and until this adapter existed none of the Bedrock
 * services took an {@link IGLlmsServiceClientsProvider} at all, so
 * {@code ai.gebo.llms.default.clients.config} never reached them. The defaults are
 * tighter than the configured values and bite hardest on streaming:
 * {@code BedrockProxyChatModel.Builder} defaults {@code asyncReadTimeout} and
 * {@code socketTimeout} to 30 seconds, so a long generation was cut well before the
 * configured response timeout.
 * <p>
 * All AWS SDK types are confined to this class and to the builder call sites.
 */
public final class BedrockClientCustomizer {

	private BedrockClientCustomizer() {
	}

	/**
	 * Per-attempt timeout, the analogue of the OpenAI request timeout. Applied as
	 * {@code apiCallAttemptTimeout} and as the read/socket timeouts of the chat builder.
	 */
	public static Duration requestTimeout(IGLlmsServiceClientsProvider provider) {
		return Duration.ofMillis(provider.getClientConfig().getReadTimeoutMs());
	}

	/**
	 * Time allowed to establish the connection.
	 */
	public static Duration connectTimeout(IGLlmsServiceClientsProvider provider) {
		return Duration.ofMillis(provider.getClientConfig().getConnectTimeoutMs());
	}

	/**
	 * Override configuration for the plain AWS SDK clients (Bedrock runtime, Bedrock
	 * agent runtime, Polly, Transcribe streaming).
	 * <p>
	 * Only {@code apiCallAttemptTimeout} is set, deliberately: it bounds a single
	 * attempt, which is the value the configuration describes. Setting
	 * {@code apiCallTimeout} to the same duration would make the overall budget equal to
	 * one attempt and silently defeat the retry strategy below.
	 */
	public static ClientOverrideConfiguration overrideConfiguration(IGLlmsServiceClientsProvider provider) {
		GeboLlmsClientConfig cfg = provider.getClientConfig();
		return ClientOverrideConfiguration.builder()
				.apiCallAttemptTimeout(Duration.ofMillis(cfg.getReadTimeoutMs()))
				.retryStrategy(b -> b.maxAttempts(cfg.getMaxRetryAttempts()))
				.build();
	}
}
