/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.confluence.onpremise.client;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLEncoder;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import ai.gebo.architecture.utils.NodesSorterUtil;
import ai.gebo.atlassian.confluence.onpremise.model.OnPremiseConfluenceAttachmentsList;
import ai.gebo.atlassian.confluence.onpremise.model.OnPremiseConfluenceCommentList;
import ai.gebo.atlassian.confluence.onpremise.model.OnPremiseConfluenceContent;
import ai.gebo.atlassian.confluence.onpremise.model.OnPremiseConfluenceContentItem;
import ai.gebo.atlassian.confluence.onpremise.model.OnPremiseConfluenceContentsList;
import ai.gebo.atlassian.confluence.onpremise.model.OnPremiseConfluenceSpacesListItem;
import ai.gebo.atlassian.confluence.onpremise.model.OnPremiseFullContent;
import ai.gebo.restintegration.abstraction.layer.GeboNotFoundException;
import ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException;
import lombok.AllArgsConstructor;

/**
 * This class provides methods to interact with the On-Premise Confluence API to manage and retrieve content, comments, and attachments.
 * It serves as a client for performing various operations on Confluence content.
 * AI generated comments
 */
@AllArgsConstructor
public class OnPremiseConfluenceContentApi {
	protected final OnPremiseConfluenceConnection api;

	/**
	 * Retrieves a list of content from a specific space using its list item.
	 *
	 * @param item the space list item
	 * @param start the starting index of the content
	 * @param limit the maximum number of content items to retrieve
	 * @return a list of Confluence contents
	 * @throws GeboRestIntegrationException if an error occurs during content retrieval
	 */
	public OnPremiseConfluenceContentsList getContents(OnPremiseConfluenceSpacesListItem item, int start, int limit)
			throws GeboRestIntegrationException {
		return getContents(item.getKey(), start, limit);
	}

	/**
	 * Retrieves a list of content from a specific space using its space key.
	 *
	 * @param spaceKey the unique key of the space
	 * @param start the starting index of the content
	 * @param limit the maximum number of content items to retrieve
	 * @return a list of Confluence contents
	 * @throws GeboRestIntegrationException if an error occurs during content retrieval
	 */
	public OnPremiseConfluenceContentsList getContents(String spaceKey, int start, int limit)
			throws GeboRestIntegrationException {
		String relative = "/rest/api/content?spaceKey=" + URLEncoder.encode(spaceKey) + "&start=" + start + "&limit="
				+ limit + "&expand=space,body.view,version,container";
		String url = api.getBaseUrl() + relative;
		HttpEntity requestEntity = new HttpEntity<OnPremiseConfluenceContentsList>(api.createHeaders());
		Class responseType = OnPremiseConfluenceContentsList.class;
		ResponseEntity<OnPremiseConfluenceContentsList> response = api.restTemplate.exchange(url, HttpMethod.GET,
				requestEntity, responseType);
		OnPremiseConfluenceContentsList out = response.getBody();
		sort(out);
		return out;
	}

	/**
	 * Retrieves a list of comments for a specific content item.
	 *
	 * @param id the content ID
	 * @param start the starting index of the comments
	 * @param limit the maximum number of comments to retrieve
	 * @return a list of comments for the specified content
	 * @throws GeboRestIntegrationException if an error occurs during comment retrieval
	 */
	public OnPremiseConfluenceCommentList getComments(String id, int start, int limit)
			throws GeboRestIntegrationException {
		String relative = "/rest/api/content/" + id + "/child/comment";
		String url = api.getBaseUrl() + relative;
		HttpEntity requestEntity = new HttpEntity<OnPremiseConfluenceCommentList>(api.createHeaders());
		Class responseType = OnPremiseConfluenceCommentList.class;
		ResponseEntity<OnPremiseConfluenceCommentList> response = api.restTemplate.exchange(url, HttpMethod.GET,
				requestEntity, responseType);
		return response.getBody();
	}

	/**
	 * Retrieves content details for a given relative URL.
	 *
	 * @param relativeUrl the relative URL of the content
	 * @return the content details
	 * @throws GeboRestIntegrationException if an error occurs during content retrieval
	 */
	public OnPremiseConfluenceContent getContentByRelativeUrl(String relativeUrl) throws GeboRestIntegrationException {
		String relative = relativeUrl + "?expand=space,body.view,version,container";
		String url = api.getBaseUrl() + relative;
		HttpEntity requestEntity = new HttpEntity<OnPremiseConfluenceContent>(api.createHeaders());
		Class responseType = OnPremiseConfluenceContent.class;
		ResponseEntity<OnPremiseConfluenceContent> response = api.restTemplate.exchange(url, HttpMethod.GET,
				requestEntity, responseType);
		return response.getBody();
	}

	/**
	 * Retrieves content details by content ID.
	 *
	 * @param id the content ID
	 * @return the content details
	 * @throws GeboRestIntegrationException if an error occurs during content retrieval
	 */
	public OnPremiseConfluenceContent getContent(String id) throws GeboRestIntegrationException {
		String relative = "/rest/api/content/" + id + "?expand=space,body.view,version,container";
		String url = api.getBaseUrl() + relative;
		HttpEntity requestEntity = new HttpEntity<OnPremiseConfluenceContent>(api.createHeaders());
		Class responseType = OnPremiseConfluenceContent.class;
		ResponseEntity<OnPremiseConfluenceContent> response = api.restTemplate.exchange(url, HttpMethod.GET,
				requestEntity, responseType);
		return response.getBody();
	}

	/**
	 * Retrieves content details using a content item.
	 *
	 * @param item the content item
	 * @return the content details
	 * @throws GeboRestIntegrationException if an error occurs during content retrieval
	 */
	public OnPremiseConfluenceContent getContent(OnPremiseConfluenceContentItem item)
			throws GeboRestIntegrationException {
		return getContent(item.getId());
	}

	/**
	 * Retrieves content as a Word document.
	 *
	 * @param id the content ID
	 * @return an InputStream of the Word document
	 * @throws GeboRestIntegrationException if an error occurs during content retrieval
	 */
	public InputStream getContentAsWord(String id) throws GeboRestIntegrationException {
		String relative = "/exportword?pageId=" + id;
		String url = api.getBaseUrl() + relative;
		HttpEntity requestEntity = new HttpEntity<OnPremiseConfluenceContent>(api.createHeaders());
		Class responseType = byte[].class;
		ResponseEntity<byte[]> response = api.restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType);
		HttpHeaders hdeaders = response.getHeaders();
		return new ByteArrayInputStream(response.getBody());
	}

	/**
	 * Retrieves content as a PDF document.
	 *
	 * @param id the content ID
	 * @return an InputStream of the PDF document
	 * @throws GeboRestIntegrationException if an error occurs during content retrieval
	 */
	public InputStream getContentAsPdf(String id) throws GeboRestIntegrationException {
		String relative = "/spaces/flyingpdf/pdfpageexport.action?pageId=" + id;
		String url = api.getBaseUrl() + relative;
		HttpEntity requestEntity = new HttpEntity<OnPremiseConfluenceContent>(api.createHeaders());
		Class responseType = byte[].class;
		ResponseEntity<byte[]> response = api.restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType);
		HttpHeaders hdeaders = response.getHeaders();
		return new ByteArrayInputStream(response.getBody());
	}

	/**
	 * Retrieves a list of child contents for a specific content ID.
	 *
	 * @param id the content ID
	 * @param start the starting index of the child contents
	 * @param limit the maximum number of child content items to retrieve
	 * @return a list of child contents
	 * @throws GeboRestIntegrationException if an error occurs during content retrieval
	 */
	public OnPremiseConfluenceContentsList getChildContents(String id, int start, int limit)
			throws GeboRestIntegrationException {
		String relative = "/rest/api/content/" + id + "/child/page?start=" + start + "&limit=" + limit;
		String url = api.getBaseUrl() + relative;
		HttpEntity requestEntity = new HttpEntity<OnPremiseConfluenceContentsList>(api.createHeaders());
		Class responseType = OnPremiseConfluenceContentsList.class;
		try {
			ResponseEntity<OnPremiseConfluenceContentsList> response = api.restTemplate.exchange(url, HttpMethod.GET,
					requestEntity, responseType);
			OnPremiseConfluenceContentsList out = response.getBody();
			sort(out);
			return out;
		} catch (GeboNotFoundException exc) {
			return new OnPremiseConfluenceContentsList();
		}
	}

	/**
	 * Retrieves the full content, including root content, child pages, comments, and attachments, for a specific content ID.
	 *
	 * @param id the content ID
	 * @return the full content details
	 * @throws GeboRestIntegrationException if an error occurs during content retrieval
	 */
	public OnPremiseFullContent getFullContent(String id) throws GeboRestIntegrationException {
		OnPremiseFullContent full = new OnPremiseFullContent();
		full.setRootContent(getContent(id));
		int start = 1;
		int limit = 500;
		full.setChildPagesList(new OnPremiseConfluenceContentsList());
		OnPremiseConfluenceContentsList items = null;
		do {
			items = getChildContents(id, start, limit);
			full.getChildPagesList().getResults().addAll(items.getResults());
			start += limit;
		} while (items != null && items.getResults().size() == limit);
		start = 1;
		full.setCommentsList(new OnPremiseConfluenceCommentList());
		OnPremiseConfluenceCommentList commentlist = null;
		do {
			commentlist = getComments(id, start, limit);
			full.getCommentsList().getResults().addAll(commentlist.getResults());
			start += limit;
		} while (commentlist != null && commentlist.getResults().size() == limit);
		for (OnPremiseConfluenceContentItem c : full.getCommentsList().getResults()) {
			full.getCommentContents().add(getContent(c.getId()));
		}
		full.setAttachmentsList(new OnPremiseConfluenceAttachmentsList());
		OnPremiseConfluenceAttachmentsList attachlist = null;
		start = 1;
		do {
			attachlist = getContentAttachments(id, start, limit);
			full.getAttachmentsList().getResults().addAll(attachlist.getResults());
			start += limit;
		} while (attachlist != null && attachlist.getResults().size() == limit);
		return full;
	}

	/**
	 * Retrieves a list of attachments for a specific content ID.
	 *
	 * @param id the content ID
	 * @param start the starting index of the attachments
	 * @param limit the maximum number of attachments to retrieve
	 * @return a list of attachments for the specified content
	 * @throws GeboRestIntegrationException if an error occurs during attachment retrieval
	 */
	public OnPremiseConfluenceAttachmentsList getContentAttachments(String id, int start, int limit)
			throws GeboRestIntegrationException {
		String relative = "/rest/api/content/" + id + "/child/attachment?start=" + start + "&limit=" + limit;
		String url = api.getBaseUrl() + relative;
		HttpEntity requestEntity = new HttpEntity<OnPremiseConfluenceAttachmentsList>(api.createHeaders());
		Class responseType = OnPremiseConfluenceAttachmentsList.class;
		ResponseEntity<OnPremiseConfluenceAttachmentsList> response = api.restTemplate.exchange(url, HttpMethod.GET,
				requestEntity, responseType);
		return response.getBody();
	}

	/**
	 * Sorts the provided list of Confluence contents based on their titles.
	 *
	 * @param list the list of Confluence contents to be sorted
	 */
	private void sort(OnPremiseConfluenceContentsList list) {
		list.setResults(NodesSorterUtil.sort(x -> {
			return x.getTitle();
		}, list.getResults()));
	}

}