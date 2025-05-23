/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.confluence.cloud.client;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLEncoder;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import ai.gebo.architecture.utils.NodesSorterUtil;
import ai.gebo.atlassian.confluence.cloud.model.CloudConfluenceAttachmentsList;
import ai.gebo.atlassian.confluence.cloud.model.CloudConfluenceCommentList;
import ai.gebo.atlassian.confluence.cloud.model.CloudConfluenceContent;
import ai.gebo.atlassian.confluence.cloud.model.CloudConfluenceContentItem;
import ai.gebo.atlassian.confluence.cloud.model.CloudConfluenceContentsList;
import ai.gebo.atlassian.confluence.cloud.model.CloudConfluenceFullContent;
import ai.gebo.atlassian.confluence.cloud.model.CloudConfluenceSpacesListItem;
import ai.gebo.restintegration.abstraction.layer.GeboNotFoundException;
import ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException;

/**
 * AI generated comments
 * This class handles interactions with the Confluence Cloud API,
 * providing methods to retrieve and interact with content, comments,
 * and attachments.
 */
public class CloudConfluenceContentApi {
    // Connection object for the Confluence Cloud API
	protected final CloudConfluenceConnection api;

    /**
     * Constructor for creating an instance of CloudConfluenceContentApi.
     *
     * @param api The CloudConfluenceConnection to use for API requests.
     */
	public CloudConfluenceContentApi(CloudConfluenceConnection api) {
		this.api = api;
	}

    /**
     * Sorts the contents list based on their title.
     *
     * @param list The list of CloudConfluenceContents to sort.
     */
	private void sort(CloudConfluenceContentsList list) {
		list.setResults(NodesSorterUtil.sort(x -> {
			return x.getTitle();
		}, list.getResults()));
	}

    /**
     * Retrieves a list of contents for a specific space.
     * 
     * @param item  The space item for which contents are requested.
     * @param start The starting index for content retrieval.
     * @param limit The maximum number of contents to retrieve.
     * @return A CloudConfluenceContentsList object containing the contents.
     * @throws GeboRestIntegrationException if an error occurs during API interaction.
     */
	public CloudConfluenceContentsList getContents(CloudConfluenceSpacesListItem item, int start, int limit)
			throws GeboRestIntegrationException {
		return getContents(item.getKey(), start, limit);
	}

    /**
     * Retrieves a list of contents for a specific space by its key.
     * 
     * @param spaceKey The key of the space.
     * @param start    The starting index for content retrieval.
     * @param limit    The maximum number of contents to retrieve.
     * @return A CloudConfluenceContentsList object containing the contents.
     * @throws GeboRestIntegrationException if an error occurs during API interaction.
     */
	public CloudConfluenceContentsList getContents(String spaceKey, int start, int limit)
			throws GeboRestIntegrationException {
		String relative = "/wiki/rest/api/content?spaceKey=" + URLEncoder.encode(spaceKey) + "&start=" + start
				+ "&limit=" + limit + "&expand=space,body.view,version,container";
		String url = api.getBaseUrl() + relative;
		HttpEntity requestEntity = new HttpEntity<CloudConfluenceContentsList>(api.createHeaders());
		Class responseType = CloudConfluenceContentsList.class;
		ResponseEntity<CloudConfluenceContentsList> response = api.restTemplate.exchange(url, HttpMethod.GET,
				requestEntity, responseType);
		CloudConfluenceContentsList out = response.getBody();
		sort(out);
		return out;
	}

    /**
     * Retrieves comments associated with a specific content ID.
     * 
     * @param id    The ID of the content.
     * @param start The starting index for comments retrieval.
     * @param limit The maximum number of comments to retrieve.
     * @return A CloudConfluenceCommentList object containing the comments.
     * @throws GeboRestIntegrationException if an error occurs during API interaction.
     */
	public CloudConfluenceCommentList getComments(String id, int start, int limit) throws GeboRestIntegrationException {
		// 487981100
		String relative = "/wiki/rest/api/content/" + id + "/child/comment";
		String url = api.getBaseUrl() + relative;
		HttpEntity requestEntity = new HttpEntity<CloudConfluenceCommentList>(api.createHeaders());
		Class responseType = CloudConfluenceCommentList.class;
		ResponseEntity<CloudConfluenceCommentList> response = api.restTemplate.exchange(url, HttpMethod.GET,
				requestEntity, responseType);
		return response.getBody();
	}

    /**
     * Retrieves content using a relative URL.
     * 
     * @param relativeUrl The relative URL of the content.
     * @return A CloudConfluenceContent object containing the content.
     * @throws GeboRestIntegrationException if an error occurs during API interaction.
     */
	public CloudConfluenceContent getContentByRelativeUrl(String relativeUrl) throws GeboRestIntegrationException {
		String relative = relativeUrl + "?expand=space,body.view,version,container";
		String url = api.getBaseUrl() + relative;
		HttpEntity requestEntity = new HttpEntity<CloudConfluenceContent>(api.createHeaders());
		Class responseType = CloudConfluenceContent.class;
		ResponseEntity<CloudConfluenceContent> response = api.restTemplate.exchange(url, HttpMethod.GET, requestEntity,
				responseType);
		return response.getBody();
	}

    /**
     * Retrieves a single content item by its ID.
     * 
     * @param id The ID of the content.
     * @return A CloudConfluenceContent object.
     * @throws GeboRestIntegrationException if an error occurs during API interaction.
     */
	public CloudConfluenceContent getContent(String id) throws GeboRestIntegrationException {
		String relative = "/wiki/rest/api/content/" + id + "?expand=space,body.view,version,container";
		String url = api.getBaseUrl() + relative;
		HttpEntity requestEntity = new HttpEntity<CloudConfluenceContent>(api.createHeaders());
		Class responseType = CloudConfluenceContent.class;
		ResponseEntity<CloudConfluenceContent> response = api.restTemplate.exchange(url, HttpMethod.GET, requestEntity,
				responseType);
		return response.getBody();
	}

    /**
     * Retrieves a single content item using a CloudConfluenceContentItem object.
     * 
     * @param item The CloudConfluenceContentItem object.
     * @return A CloudConfluenceContent object.
     * @throws GeboRestIntegrationException if an error occurs during API interaction.
     */
	public CloudConfluenceContent getContent(CloudConfluenceContentItem item) throws GeboRestIntegrationException {
		return getContent(item.getId());
	}

    /**
     * Retrieves Word format for a given content ID.
     * 
     * @param id The ID of the content.
     * @return An InputStream of the Word document.
     * @throws GeboRestIntegrationException if an error occurs during API interaction.
     */
	public InputStream getContentAsWord(String id) throws GeboRestIntegrationException {
		String relative = "/wiki/exportword?pageId=" + id;
		String url = api.getBaseUrl() + relative;
		HttpEntity requestEntity = new HttpEntity<CloudConfluenceContent>(api.createHeaders());
		Class responseType = byte[].class;
		ResponseEntity<byte[]> response = api.restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType);
		HttpHeaders hdeaders = response.getHeaders();
		return new ByteArrayInputStream(response.getBody());
	}

    /**
     * Retrieves PDF format for a given content ID.
     * 
     * @param id The ID of the content.
     * @return An InputStream of the PDF document.
     * @throws GeboRestIntegrationException if an error occurs during API interaction.
     */
	public InputStream getContentAsPdf(String id) throws GeboRestIntegrationException {
		String relative = "/wiki/spaces/flyingpdf/pdfpageexport.action?pageId=" + id;
		String url = api.getBaseUrl() + relative;
		HttpEntity requestEntity = new HttpEntity<CloudConfluenceContent>(api.createHeaders());
		Class responseType = byte[].class;
		ResponseEntity<byte[]> response = api.restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType);
		HttpHeaders hdeaders = response.getHeaders();
		return new ByteArrayInputStream(response.getBody());
	}

    /**
     * Retrieves child contents of a specific content ID.
     * 
     * @param id    The ID of the parent content.
     * @param start The starting index for child content retrieval.
     * @param limit The maximum number of child contents to retrieve.
     * @return A CloudConfluenceContentsList object containing the child contents.
     * @throws GeboRestIntegrationException if an error occurs during API interaction.
     */
	public CloudConfluenceContentsList getChildContents(String id, int start, int limit)
			throws GeboRestIntegrationException {
		String relative = "/wiki/rest/api/content/" + id + "/child/page?start=" + start + "&limit=" + limit;
		String url = api.getBaseUrl() + relative;
		HttpEntity requestEntity = new HttpEntity<CloudConfluenceContentsList>(api.createHeaders());
		Class responseType = CloudConfluenceContentsList.class;
		try {
			ResponseEntity<CloudConfluenceContentsList> response = api.restTemplate.exchange(url, HttpMethod.GET,
					requestEntity, responseType);
			CloudConfluenceContentsList out = response.getBody();
			sort(out);
			return out;
		} catch (GeboNotFoundException exc) {
			return new CloudConfluenceContentsList();
		}
	}

    /**
     * Retrieves full content details, including child pages, comments, and attachments.
     * 
     * @param id The ID of the content.
     * @return A CloudConfluenceFullContent object containing comprehensive content details.
     * @throws GeboRestIntegrationException if an error occurs during API interaction.
     */
	public CloudConfluenceFullContent getFullContent(String id) throws GeboRestIntegrationException {
		CloudConfluenceFullContent full = new CloudConfluenceFullContent();
		full.setRootContent(getContent(id));
		int start = 1;
		int limit = 500;
		full.setChildPagesList(new CloudConfluenceContentsList());
		CloudConfluenceContentsList items = null;
		do {
			items = getChildContents(id, start, limit);
			full.getChildPagesList().getResults().addAll(items.getResults());
			start += limit;
		} while (items != null && items.getResults().size() == limit);
		start = 1;
		full.setCommentsList(new CloudConfluenceCommentList());
		CloudConfluenceCommentList commentlist = null;
		do {
			commentlist = getComments(id, start, limit);
			full.getCommentsList().getResults().addAll(commentlist.getResults());
			start += limit;
		} while (commentlist != null && commentlist.getResults().size() == limit);
		for (CloudConfluenceContentItem c : full.getCommentsList().getResults()) {
			full.getCommentContents().add(getContent(c.getId()));
		}
		full.setAttachmentsList(new CloudConfluenceAttachmentsList());
		CloudConfluenceAttachmentsList attachlist = null;
		start = 1;
		do {
			attachlist = getContentAttachments(id, start, limit);
			full.getAttachmentsList().getResults().addAll(attachlist.getResults());
			start += limit;
		} while (attachlist != null && attachlist.getResults().size() == limit);
		return full;
	}

    /**
     * Retrieves attachments for a specific content ID.
     * 
     * @param id    The ID of the content.
     * @param start The starting index for attachment retrieval.
     * @param limit The maximum number of attachments to retrieve.
     * @return A CloudConfluenceAttachmentsList object containing the attachments.
     * @throws GeboRestIntegrationException if an error occurs during API interaction.
     */
	public CloudConfluenceAttachmentsList getContentAttachments(String id, int start, int limit)
			throws GeboRestIntegrationException {
		String relative = "/wiki/rest/api/content/" + id + "/child/attachment?start=" + start + "&limit=" + limit;
		String url = api.getBaseUrl() + relative;
		HttpEntity requestEntity = new HttpEntity<CloudConfluenceAttachmentsList>(api.createHeaders());
		Class responseType = CloudConfluenceAttachmentsList.class;
		ResponseEntity<CloudConfluenceAttachmentsList> response = api.restTemplate.exchange(url, HttpMethod.GET,
				requestEntity, responseType);
		return response.getBody();
	}
}