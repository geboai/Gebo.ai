/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.restintegration.abstraction.layer;

import java.net.URI;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.client.observation.ClientHttpObservationDocumentation.HighCardinalityKeyNames;
import org.springframework.http.client.observation.ClientHttpObservationDocumentation.LowCardinalityKeyNames;
import org.springframework.http.client.observation.ClientRequestObservationContext;
import org.springframework.http.client.observation.ClientRequestObservationConvention;
import org.springframework.http.client.observation.DefaultClientRequestObservationConvention;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import io.micrometer.common.KeyValue;
import io.micrometer.observation.ObservationRegistry;

/**
 * The application's {@link ObservationRegistry} on the HTTP clients Gebo builds itself
 * (the integrations' {@link RestTemplateWrapperService}, the generated clients'
 * {@code RestTemplate}, the LLM providers' clients). Spring Boot observes only the
 * builders it makes, and none of these comes from one. Each call, observed, is a client
 * span carrying the trace to the server called (the {@code traceparent} header) and an
 * {@code http.client.requests} measure.
 * <p>
 * What the observations record of a url never holds its query string nor its user
 * info: integrations put credentials there (the Google Custom Search {@code key}, the
 * SerpApi {@code api_key}), and a span is no place for them.
 */
public final class GeboHttpClientObservations {

	/** The value of the {@code uri} tag when it cannot be told without risk. */
	static final String NO_URI = "none";

	/**
	 * For the clients whose callers write whole urls, an id or a query in them: the
	 * {@code uri} tag is a url template's path only when the template has variables
	 * ({@code /rest/api/content/{id}}), {@value #NO_URI} otherwise, so that a url built by
	 * concatenation does not make a measure per id. The traces keep the whole path.
	 */
	public static final ClientRequestObservationConvention URL_CALLS = new SanitizedConvention(true);

	/**
	 * For the clients called with path templates of their own (the LLM providers'
	 * {@code /v1/chat/completions}): the {@code uri} tag is the template's path.
	 */
	public static final ClientRequestObservationConvention PATH_TEMPLATES = new SanitizedConvention(false);

	private GeboHttpClientObservations() {
	}

	/** The application's registry, or the no-op one when there is none (no telemetry). */
	public static ObservationRegistry registryOf(ObjectProvider<ObservationRegistry> provider) {
		final ObservationRegistry registry = provider != null ? provider.getIfAvailable() : null;
		return registry != null ? registry : ObservationRegistry.NOOP;
	}

	/** The builder observing its client's calls in the registry, the uri tag as {@link #URL_CALLS} says. */
	public static RestClient.Builder observed(RestClient.Builder builder, ObservationRegistry registry) {
		return observed(builder, registry, URL_CALLS);
	}

	/** The builder observing its client's calls in the registry, with the convention given. */
	public static RestClient.Builder observed(RestClient.Builder builder, ObservationRegistry registry,
			ClientRequestObservationConvention convention) {
		if (builder == null || registry == null || registry.isNoop()) {
			return builder;
		}
		return builder.observationRegistry(registry).observationConvention(convention);
	}

	/** The template observing its calls in the registry, the uri tag as {@link #URL_CALLS} says. */
	public static RestTemplate observed(RestTemplate restTemplate, ObservationRegistry registry) {
		if (restTemplate != null && registry != null && !registry.isNoop()) {
			restTemplate.setObservationRegistry(registry);
			restTemplate.setObservationConvention(URL_CALLS);
		}
		return restTemplate;
	}

	/** The url as an observation may record it: scheme, host, port and path, nothing else. */
	public static String recordable(URI uri) {
		if (uri == null) {
			return NO_URI;
		}
		final String path = uri.getRawPath() != null ? uri.getRawPath() : "";
		if (uri.getHost() == null) {
			return path.isEmpty() ? NO_URI : path;
		}
		return uri.getScheme() + "://" + uri.getHost() + (uri.getPort() >= 0 ? ":" + uri.getPort() : "") + path;
	}

	/** A url template's path as an observation may record it: without its query or fragment. */
	static String withoutQuery(String path) {
		if (path == null) {
			return null;
		}
		int end = path.length();
		final int query = path.indexOf('?');
		if (query >= 0) {
			end = query;
		}
		final int fragment = path.indexOf('#');
		if (fragment >= 0 && fragment < end) {
			end = fragment;
		}
		final String cut = path.substring(0, end);
		return cut.isEmpty() ? "/" : cut;
	}

	/**
	 * Spring's convention, the urls it records stripped of their query and user info,
	 * and, for {@link #URL_CALLS}, the uri tag only from a template with variables.
	 */
	static final class SanitizedConvention extends DefaultClientRequestObservationConvention {
		private final boolean templatesWithVariablesOnly;

		SanitizedConvention(boolean templatesWithVariablesOnly) {
			this.templatesWithVariablesOnly = templatesWithVariablesOnly;
		}

		@Override
		protected KeyValue uri(ClientRequestObservationContext context) {
			final String template = context.getUriTemplate();
			if (template == null || (templatesWithVariablesOnly && !template.contains("{"))) {
				return KeyValue.of(LowCardinalityKeyNames.URI, NO_URI);
			}
			// the template's path, as Spring extracts it, cut before any query
			return KeyValue.of(LowCardinalityKeyNames.URI, withoutQuery(super.uri(context).getValue()));
		}

		@Override
		protected KeyValue requestUri(ClientRequestObservationContext context) {
			if (context.getCarrier() != null) {
				return KeyValue.of(HighCardinalityKeyNames.HTTP_URL, recordable(context.getCarrier().getURI()));
			}
			return super.requestUri(context);
		}
	}
}
