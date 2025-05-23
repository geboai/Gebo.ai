/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.services.impl;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ReactorClientHttpRequestFactory;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.retry.support.RetryTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProvider;
import ai.gebo.llms.abstraction.layer.services.config.GeboDefaultLlmsServiceClientsProviderConfig;

/**
 * Service implementation for providing configured clients and retry templates
 * for making HTTP requests.
 * 
 * This class implements the IGLlmsServiceClientsProvider interface.
 * 
 * AI generated comments
 */
@Service
public class GDefaultLlmsServiceClientsProviderImpl implements IGLlmsServiceClientsProvider {

	// Injecting the default configuration for client settings and retry policies
	@Autowired
	GeboDefaultLlmsServiceClientsProviderConfig defaultConfig;

	/**
	 * Default constructor for GDefaultLlmsServiceClientsProviderImpl.
	 */
	public GDefaultLlmsServiceClientsProviderImpl() {

	}

	/**
	 * Creates and configures a reactor.netty.http.client.HttpClient with a response
	 * timeout.
	 * 
	 * @return Configured HttpClient instance
	 */
	private reactor.netty.http.client.HttpClient getClient() {
		reactor.netty.http.client.HttpClient client = reactor.netty.http.client.HttpClient.create()
				.responseTimeout(Duration.ofMillis(defaultConfig.getWebClientConfig().getResponseTimeout()));

		return client;
	}

	/**
	 * Provides a configured WebClient builder.
	 * 
	 * @return WebClient.Builder instance with the configured HttpClient connector
	 */
	@Override
	public Builder getWebClientBuilder() {
		Builder builder = WebClient.builder();
		reactor.netty.http.client.HttpClient client = getClient();
		ReactorClientHttpConnector connector = new ReactorClientHttpConnector(client);
		builder.clientConnector(connector);
		return builder;
	}

	/**
	 * Provides a configured RestClient builder.
	 * 
	 * @return RestClient.Builder instance with configured request factory using
	 *         HttpClient
	 */
	@Override
	public org.springframework.web.client.RestClient.Builder getRestClientBuilder() {
		reactor.netty.http.client.HttpClient client = getClient();
		ReactorClientHttpConnector connector = new ReactorClientHttpConnector(client);
		org.springframework.web.client.RestClient.Builder builder = RestClient.builder();
		builder.requestFactory(new ReactorClientHttpRequestFactory(client));
		return builder;
	}

	/**
	 * Provides a RetryTemplate configured with backoff policy and retry policy.
	 * 
	 * @return Configured RetryTemplate instance
	 */
	@Override
	public RetryTemplate getRetryTemplate() {
		RetryTemplateBuilder builder = RetryTemplate.builder();
		// Customize backoff policy
		FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
		backOffPolicy.setBackOffPeriod(defaultConfig.getRetryConfig().getBackoffInterval()); // example: 1 second

		// Customize retry policy
		SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
		retryPolicy.setMaxAttempts(defaultConfig.getRetryConfig().getMaxAttempts());

		RetryTemplate out = builder.customPolicy(retryPolicy).customBackoff(backOffPolicy).build();

		return out;
	}

}