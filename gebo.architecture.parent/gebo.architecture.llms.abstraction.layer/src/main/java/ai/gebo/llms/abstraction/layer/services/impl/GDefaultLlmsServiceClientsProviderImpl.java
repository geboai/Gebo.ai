/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services.impl;

import java.io.IOException;
import java.time.Duration;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ReactorClientHttpRequestFactory;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.retry.support.RetryTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.ClientHttpObservationDocumentation;
import org.springframework.web.reactive.function.client.ClientRequestObservationContext;
import org.springframework.web.reactive.function.client.ClientRequestObservationConvention;
import org.springframework.web.reactive.function.client.DefaultClientRequestObservationConvention;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProvider;
import ai.gebo.restintegration.abstraction.layer.GeboHttpClientObservations;
import io.micrometer.common.KeyValue;
import io.micrometer.observation.ObservationRegistry;
import ai.gebo.llms.abstraction.layer.services.config.GeboDefaultLlmsServiceClientsProviderConfig;
import ai.gebo.llms.abstraction.layer.services.config.GeboLlmsClientConfig;
import okhttp3.Interceptor;
import okhttp3.Response;

@Service
public class GDefaultLlmsServiceClientsProviderImpl implements IGLlmsServiceClientsProvider {

	@Autowired
	GeboDefaultLlmsServiceClientsProviderConfig defaultConfig;

	/**
	 * The registry the LLM providers' HTTP calls are observed in: a client span under the
	 * model's own observation, the trace sent on, {@code http.client.requests} by provider
	 * host and path (see GeboHttpClientObservations).
	 */
	@Autowired
	ObjectProvider<ObservationRegistry> observationRegistry;

	public GDefaultLlmsServiceClientsProviderImpl() {
	}

	private reactor.netty.http.client.HttpClient getReactorClient() {
		return reactor.netty.http.client.HttpClient.create()
				.responseTimeout(Duration.ofMillis(defaultConfig.getWebClientConfig().getResponseTimeout()));
	}

	/**
	 * Spring's WebClient convention, the url it records stripped of its query and user
	 * info as for the RestClient (GeboHttpClientObservations).
	 */
	static final ClientRequestObservationConvention WITHOUT_QUERY = new DefaultClientRequestObservationConvention() {
		@Override
		protected KeyValue httpUrl(ClientRequestObservationContext context) {
			if (context.getRequest() != null) {
				return KeyValue.of(ClientHttpObservationDocumentation.HighCardinalityKeyNames.HTTP_URL,
						GeboHttpClientObservations.recordable(context.getRequest().url()));
			}
			return super.httpUrl(context);
		}
	};

	@Override
	public Builder getWebClientBuilder() {
		Builder builder = WebClient.builder();
		builder.clientConnector(new ReactorClientHttpConnector(getReactorClient()));
		final ObservationRegistry registry = GeboHttpClientObservations.registryOf(observationRegistry);
		if (!registry.isNoop()) {
			builder.observationRegistry(registry).observationConvention(WITHOUT_QUERY);
		}
		return builder;
	}

	@Override
	public org.springframework.web.client.RestClient.Builder getRestClientBuilder() {
		org.springframework.web.client.RestClient.Builder builder = RestClient.builder();
		builder.requestFactory(new ReactorClientHttpRequestFactory(getReactorClient()));
		// the providers call paths of their own (/v1/chat/completions): the uri tag is theirs
		return GeboHttpClientObservations.observed(builder,
				GeboHttpClientObservations.registryOf(observationRegistry), GeboHttpClientObservations.PATH_TEMPLATES);
	}

	@Override
	public RetryTemplate getRetryTemplate() {
		RetryTemplateBuilder builder = RetryTemplate.builder();
		FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
		backOffPolicy.setBackOffPeriod(defaultConfig.getRetryConfig().getBackoffInterval());
		SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
		retryPolicy.setMaxAttempts(defaultConfig.getRetryConfig().getMaxAttempts());
		return builder.customPolicy(retryPolicy).customBackoff(backOffPolicy).build();
	}

	@Override
	public org.springframework.core.retry.RetryTemplate getCoreRetryTemplate() {
		org.springframework.core.retry.RetryPolicy policy = org.springframework.core.retry.RetryPolicy.builder()
				.maxRetries(defaultConfig.getRetryConfig().getMaxAttempts())
				.delay(Duration.ofMillis(defaultConfig.getRetryConfig().getBackoffInterval()))
				.multiplier(1.0)
				.build();
		return new org.springframework.core.retry.RetryTemplate(policy);
	}

	@Override
	public GeboLlmsClientConfig getClientConfig() {
		return new GeboLlmsClientConfig(
				defaultConfig.getWebClientConfig().getConnectTimeout(),
				defaultConfig.getWebClientConfig().getResponseTimeout(),
				defaultConfig.getWebClientConfig().getResponseTimeout(),
				defaultConfig.getRetryConfig().getBackoffInterval(),
				defaultConfig.getRetryConfig().getMaxAttempts());
	}

	@Override
	public Interceptor getOkHttpRetryInterceptor() {
		return new OkHttpRetryInterceptor(
				defaultConfig.getRetryConfig().getMaxAttempts(),
				defaultConfig.getRetryConfig().getBackoffInterval());
	}

	private static final class OkHttpRetryInterceptor implements Interceptor {
		private final int maxAttempts;
		private final long backoffMs;

		OkHttpRetryInterceptor(int maxAttempts, long backoffMs) {
			this.maxAttempts = maxAttempts;
			this.backoffMs = backoffMs;
		}

		@Override
		public Response intercept(Chain chain) throws IOException {
			Response response = null;
			for (int attempt = 1; attempt <= maxAttempts; attempt++) {
				if (response != null) response.close();
				response = chain.proceed(chain.request());
				if (attempt == maxAttempts || !isRetryable(response.code())) {
					return response;
				}
				try {
					Thread.sleep(backoffMs);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					throw new IOException("Interrupted during retry backoff", e);
				}
			}
			return response;
		}

		private boolean isRetryable(int code) {
			return code == 429 || code == 502 || code == 503 || code == 504;
		}
	}
}
