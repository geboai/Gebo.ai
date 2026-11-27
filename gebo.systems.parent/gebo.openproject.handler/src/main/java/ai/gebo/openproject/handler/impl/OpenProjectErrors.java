/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openproject.handler.impl;

import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.http.HttpTimeoutException;

import javax.net.ssl.SSLException;

import org.springframework.web.client.RestClientResponseException;

/**
 * Words a failure to reach OpenProject as something the administrator can act on:
 * the client exceptions only carry the bare cause message (an unknown host reads
 * as the host name alone).
 */
public final class OpenProjectErrors {

	private OpenProjectErrors() {
	}

	/**
	 * What the administrator can do about a failed OpenProject call: a refusal by its
	 * HTTP status, a network failure by its cause.
	 */
	public static String of(Throwable e, String baseUri) {
		return e instanceof RestClientResponseException re ? refused(re) : unreachable(e, baseUri);
	}

	/**
	 * An OpenProject answer that is not a success, by its status.
	 */
	public static String refused(RestClientResponseException e) {
		int status = e.getStatusCode().value();
		switch (status) {
		case 401:
			return "OpenProject refused the credentials (401): check the API key of the OpenProject system, it may have been revoked or its user locked";
		case 403:
			return "The OpenProject account cannot see this content (403)";
		case 404:
			return "Not found on OpenProject (404): it may have been deleted, or the account may not see it";
		default:
			return "OpenProject answered " + status + " " + e.getStatusText();
		}
	}

	/**
	 * What went wrong reaching OpenProject at all (no HTTP answer), from the first
	 * known network cause of the exception.
	 */
	public static String unreachable(Throwable e, String baseUri) {
		String where = baseUri != null ? baseUri : "the OpenProject server";
		for (Throwable t = e; t != null; t = t.getCause() == t ? null : t.getCause()) {
			if (t instanceof UnknownHostException) {
				return "Unknown host " + t.getMessage() + ": check the base URI of the OpenProject system";
			}
			if (t instanceof ConnectException || t instanceof NoRouteToHostException) {
				return "Cannot connect to " + where + ": check the base URI and that OpenProject is running ("
						+ t.getMessage() + ")";
			}
			if (t instanceof SocketTimeoutException || t instanceof HttpTimeoutException) {
				return "No answer from " + where + " in time: check the base URI and the network";
			}
			if (t instanceof SSLException) {
				return "TLS error talking to " + where + ": " + t.getMessage();
			}
		}
		return e.getMessage();
	}
}
