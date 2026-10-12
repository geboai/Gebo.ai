/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.aws_bedrock.services;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.llms.models.metainfos.ModelParameterRules;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.core.SdkRequest;
import software.amazon.awssdk.core.document.Document;
import software.amazon.awssdk.core.interceptor.Context;
import software.amazon.awssdk.core.interceptor.ExecutionAttributes;
import software.amazon.awssdk.core.interceptor.ExecutionInterceptor;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.http.nio.netty.NettyNioAsyncHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeAsyncClient;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import software.amazon.awssdk.services.bedrockruntime.model.ConverseRequest;
import software.amazon.awssdk.services.bedrockruntime.model.ConverseStreamRequest;

/**
 * The thinking of Claude on Bedrock, which Spring AI's Bedrock options cannot carry:
 * their request parameters take flat strings, and Claude wants the objects
 * {@code "thinking": {"type": "adaptive" | "disabled"}} and
 * {@code "output_config": {"effort": ...}} in the Converse request's
 * additionalModelRequestFields
 * (docs.aws.amazon.com/bedrock/latest/userguide/claude-messages-adaptive-thinking.html,
 * 2026-10-11). An SDK execution interceptor adds them to every Converse and
 * ConverseStream request of the model's own clients, built as Spring AI builds them.
 */
final class BedrockClaudeThinking {
	private static final Logger LOGGER = LoggerFactory.getLogger(BedrockClaudeThinking.class);

	private BedrockClaudeThinking() {
	}

	/**
	 * The fields an effort asks for: {@code none} turns the thinking off, any other
	 * effort thinks adaptively at it.
	 */
	static Document fields(String effort) {
		if (ModelParameterRules.NO_REASONING_EFFORT.equals(effort)) {
			return Document.mapBuilder()
					.putDocument("thinking", Document.mapBuilder().putString("type", "disabled").build()).build();
		}
		return Document.mapBuilder()
				.putDocument("thinking", Document.mapBuilder().putString("type", "adaptive").build())
				.putDocument("output_config", Document.mapBuilder().putString("effort", effort).build()).build();
	}

	/** The request's own additional fields with the thinking ones over them. */
	static Document merge(Document existing, Document thinking) {
		Map<String, Document> merged = new LinkedHashMap<>();
		if (existing != null && existing.isMap()) {
			merged.putAll(existing.asMap());
		}
		merged.putAll(thinking.asMap());
		return Document.fromMap(merged);
	}

	/** Adds the thinking fields to the Converse and ConverseStream requests. */
	static ExecutionInterceptor interceptor(Document thinking) {
		return new ExecutionInterceptor() {
			@Override
			public SdkRequest modifyRequest(Context.ModifyRequest context, ExecutionAttributes executionAttributes) {
				SdkRequest request = context.request();
				if (request instanceof ConverseRequest converse) {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Converse request to {} with {}", converse.modelId(), thinking);
					}
					return converse.toBuilder()
							.additionalModelRequestFields(merge(converse.additionalModelRequestFields(), thinking)).build();
				}
				if (request instanceof ConverseStreamRequest stream) {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("ConverseStream request to {} with {}", stream.modelId(), thinking);
					}
					return stream.toBuilder()
							.additionalModelRequestFields(merge(stream.additionalModelRequestFields(), thinking)).build();
				}
				return request;
			}
		};
	}

	/** The blocking client, with the timeouts Spring AI's builder gives its own. */
	static BedrockRuntimeClient syncClient(AwsCredentialsProvider credentials, Region region, Duration timeout,
			Duration connect, Document thinking) {
		return BedrockRuntimeClient.builder().region(region).credentialsProvider(credentials)
				.httpClientBuilder(ApacheHttpClient.builder().connectionAcquisitionTimeout(connect)
						.connectionTimeout(connect).socketTimeout(timeout))
				.overrideConfiguration(c -> c.apiCallTimeout(timeout).addExecutionInterceptor(interceptor(thinking)))
				.build();
	}

	/** The streaming client, with the timeouts Spring AI's builder gives its own. */
	static BedrockRuntimeAsyncClient asyncClient(AwsCredentialsProvider credentials, Region region, Duration timeout,
			Duration connect, Document thinking) {
		return BedrockRuntimeAsyncClient.builder().region(region).credentialsProvider(credentials)
				.httpClientBuilder(NettyNioAsyncHttpClient.builder().tcpKeepAlive(true).readTimeout(timeout)
						.connectionTimeout(connect).connectionAcquisitionTimeout(connect).maxConcurrency(200))
				.overrideConfiguration(c -> c.apiCallTimeout(timeout).addExecutionInterceptor(interceptor(thinking)))
				.build();
	}
}
