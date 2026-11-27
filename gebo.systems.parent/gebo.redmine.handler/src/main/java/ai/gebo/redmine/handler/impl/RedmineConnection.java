/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.redmine.handler.impl;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClient;

import ai.gebo.redmine.client.api.AttachmentsApi;
import ai.gebo.redmine.client.api.EnumerationsApi;
import ai.gebo.redmine.client.api.FilesApi;
import ai.gebo.redmine.client.api.IssueStatusesApi;
import ai.gebo.redmine.client.api.IssuesApi;
import ai.gebo.redmine.client.api.MyAccountApi;
import ai.gebo.redmine.client.api.ProjectsApi;
import ai.gebo.redmine.client.api.SearchApi;
import ai.gebo.redmine.client.api.TrackersApi;
import ai.gebo.redmine.client.api.WikiPagesApi;
import ai.gebo.redmine.client.invoker.ApiClient;

/**
 * An authenticated access to one Redmine server: the generated API client and
 * the RestClient underneath it, which carries the credentials as default
 * headers and also streams the attachments.
 */
public class RedmineConnection {
	private static final Logger LOGGER = LoggerFactory.getLogger(RedmineConnection.class);
	/** The format path parameter of every Redmine REST call. */
	public static final String JSON = "json";
	private final ApiClient apiClient;
	private final RestClient restClient;
	private final String baseUri;

	public RedmineConnection(ApiClient apiClient, RestClient restClient, String baseUri) {
		this.apiClient = apiClient;
		this.restClient = restClient;
		this.baseUri = baseUri;
	}

	public String getBaseUri() {
		return baseUri;
	}

	public ProjectsApi projects() {
		return new ProjectsApi(apiClient);
	}

	public IssuesApi issues() {
		return new IssuesApi(apiClient);
	}

	public WikiPagesApi wiki() {
		return new WikiPagesApi(apiClient);
	}

	public FilesApi files() {
		return new FilesApi(apiClient);
	}

	public AttachmentsApi attachments() {
		return new AttachmentsApi(apiClient);
	}

	public SearchApi search() {
		return new SearchApi(apiClient);
	}

	public MyAccountApi myAccount() {
		return new MyAccountApi(apiClient);
	}

	public TrackersApi trackers() {
		return new TrackersApi(apiClient);
	}

	public IssueStatusesApi issueStatuses() {
		return new IssueStatusesApi(apiClient);
	}

	public EnumerationsApi enumerations() {
		return new EnumerationsApi(apiClient);
	}

	/**
	 * Streams an attachment (issue, wiki page or project file) from the
	 * configured server. The URL is built from the system base URI, not taken
	 * from the content_url Redmine returns, so the credentials only go to the
	 * configured server. The response stays open until the stream is closed.
	 */
	public InputStream downloadAttachment(Integer attachmentId) throws IOException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Downloading the Redmine attachment {} from {}", attachmentId, baseUri);
		}
		return restClient.get().uri(baseUri + "/attachments/download/{id}", attachmentId)
				.exchange((request, response) -> {
					if (response.getStatusCode().isError()) {
						int status = response.getStatusCode().value();
						response.close();
						throw new IOException(
								"Redmine answered " + status + " downloading the attachment " + attachmentId);
					}
					InputStream body = response.getBody();
					return new FilterInputStream(body) {
						@Override
						public void close() throws IOException {
							try {
								super.close();
							} finally {
								response.close();
							}
						}
					};
				}, false);
	}
}
