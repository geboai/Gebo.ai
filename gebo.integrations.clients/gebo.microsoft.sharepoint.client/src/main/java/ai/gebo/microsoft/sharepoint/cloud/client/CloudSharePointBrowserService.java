/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.microsoft.sharepoint.cloud.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.List;
import java.util.StringTokenizer;

import org.slf4j.Logger; // <--- Logging
import org.slf4j.LoggerFactory; // <--- Logging
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.gebo.microsoft.sharepoint.cloud.model.CloudSharepointApi;
import ai.gebo.microsoft.sharepoint.cloud.model.CloudSharepointAttachment;
import ai.gebo.microsoft.sharepoint.cloud.model.CloudSharepointAttachmentResponse;
import ai.gebo.microsoft.sharepoint.cloud.model.CloudSharepointContentType;
import ai.gebo.microsoft.sharepoint.cloud.model.CloudSharepointContentTypeResponse;
import ai.gebo.microsoft.sharepoint.cloud.model.CloudSharepointDocument;
import ai.gebo.microsoft.sharepoint.cloud.model.CloudSharepointDocumentResponse;
import ai.gebo.microsoft.sharepoint.cloud.model.CloudSharepointDownloadedAttachment;
import ai.gebo.microsoft.sharepoint.cloud.model.CloudSharepointList;
import ai.gebo.microsoft.sharepoint.cloud.model.CloudSharepointListItem;
import ai.gebo.microsoft.sharepoint.cloud.model.CloudSharepointListItemResponse;
import ai.gebo.microsoft.sharepoint.cloud.model.CloudSharepointListResponse;
import ai.gebo.microsoft.sharepoint.cloud.model.CloudSharepointPage;
import ai.gebo.microsoft.sharepoint.cloud.model.CloudSharepointPageResponse;
import ai.gebo.microsoft.sharepoint.cloud.model.CloudSharepointSite;
import ai.gebo.microsoft.sharepoint.cloud.model.CloudSharepointSiteResponse;
import ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException;
import ai.gebo.restintegration.abstraction.layer.RestTemplateWrapperService;

@Service
public class CloudSharePointBrowserService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CloudSharePointBrowserService.class);

	private final RestTemplateWrapperService restTemplate;

	public CloudSharePointBrowserService(RestTemplateWrapperService restTemplate) {
		this.restTemplate = restTemplate;

	}

	/**
	 * Creates HttpHeaders with a Bearer token, plus Accept header.
	 */
	private HttpHeaders createHeaders(CloudSharepointApi system) {

		HttpHeaders headers = new HttpHeaders();

		headers.add("Authorization", "Bearer " + system.getToken());

		// Using minimalmetadata to reduce payload size
		headers.add("Accept", "application/json");
		return headers;
	}

	/**
	 * Fetch basic list of pages from a SharePoint site.
	 */
	public List<CloudSharepointPage> getPages(CloudSharepointApi system, String siteId)
			throws GeboRestIntegrationException {

		String url = composeUrl(system, "/sites/" + siteId + "/pages");
		LOGGER.info("Fetching pages from URL: {}", url);

		HttpHeaders headers = createHeaders(system);
		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		ResponseEntity<CloudSharepointPageResponse> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
				CloudSharepointPageResponse.class);

		if (response.getBody() == null) {
			LOGGER.warn("No response body received when fetching pages from SharePoint.");
			return List.of();
		}

		return response.getBody().getPages();
	}

	protected String composeUrl(CloudSharepointApi system, String relative) {
		String baseUrl = system.getBaseUri();
		return composeUrl(baseUrl, relative);
	}

	protected String composeUrl(String baseUrl, String relative) {
		String completeUrl = null;
		if (baseUrl.endsWith("/") && relative != null && relative.startsWith("/")) {
			completeUrl = baseUrl + relative.substring(1);
		} else if ((!baseUrl.endsWith("/")) && (!relative.startsWith("/"))) {
			completeUrl = baseUrl + "/" + relative;
		} else {
			completeUrl = baseUrl + relative;
		}
		return completeUrl;
	}

	/**
	 * Fetch documents (files) from a SharePoint drive (root).
	 */
	public List<CloudSharepointDocument> getDocuments(CloudSharepointApi system, String siteId)
			throws GeboRestIntegrationException {

		String url = composeUrl(system, "/sites/" + siteId + "/drive/root/children");
		LOGGER.info("Fetching documents from URL: {}", url);

		HttpHeaders headers = createHeaders(system);
		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		ResponseEntity<CloudSharepointDocumentResponse> response = restTemplate.exchange(url, HttpMethod.GET,
				requestEntity, CloudSharepointDocumentResponse.class);

		if (response.getBody() == null) {
			LOGGER.warn("No response body received when fetching documents from SharePoint.");
			return List.of();
		}

		return response.getBody().getDocuments();
	}

	public List<CloudSharepointContentType> getSiteContentTypes(CloudSharepointApi system)
			throws GeboRestIntegrationException {
		String url = composeUrl(system, "/ContentTypes?$select=Id,Name,Description,Group");
		HttpHeaders headers = createHeaders(system);
		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		ResponseEntity<CloudSharepointContentTypeResponse> response = restTemplate.exchange(url, HttpMethod.GET,
				requestEntity, CloudSharepointContentTypeResponse.class);

		if (response.getBody() == null || response.getBody().getD() == null) {
			return List.of();
		}
		return response.getBody().getD().getResults();
	}

	/**
	 * Fetch attachments from a SharePoint list item.
	 */
	public List<CloudSharepointAttachment> getAttachments(CloudSharepointApi system, String listId, String itemId)
			throws GeboRestIntegrationException {
		String url = composeUrl(system, "/lists/" + listId + "/items/" + itemId + "/attachmentFiles");
		LOGGER.info("Fetching attachments from URL: {}", url);

		HttpHeaders headers = createHeaders(system);
		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		ResponseEntity<CloudSharepointAttachmentResponse> response = restTemplate.exchange(url, HttpMethod.GET,
				requestEntity, CloudSharepointAttachmentResponse.class);

		if (response.getBody() == null) {
			LOGGER.warn("No response body received when fetching attachments from SharePoint.");
			return List.of();
		}

		return response.getBody().getAttachments();
	}

	// ----------------------------------------------------------------
	// New Method: Download the binary stream of a single attachment
	// ----------------------------------------------------------------
	/**
	 * Downloads the binary content of a specific attachment from a list item,
	 * returning an InputStream plus content type info. For classic SharePoint REST,
	 * appending '/$value' gets the file's raw bytes.
	 *
	 * @param system         The SharePoint system configuration.
	 * @param listId         The target list ID.
	 * @param itemId         The target list item ID.
	 * @param attachmentName The file name of the attachment (e.g. "mydoc.docx").
	 * @return a {@link CloudSharepointDownloadedAttachment} object containing the
	 *         InputStream, content type, and whether the document is a recognized
	 *         Office file.
	 * @throws GeboRestIntegrationException on REST-related errors.
	 * @throws GeboCryptSecretException     if there's an issue retrieving the
	 *                                      token.
	 * @throws URISyntaxException
	 */
	public CloudSharepointDownloadedAttachment downloadAttachmentBinary(CloudSharepointApi system, String listId,
			String itemId, String attachmentName) throws GeboRestIntegrationException, URISyntaxException {

		// Example endpoint (classic SharePoint):
		// /lists/{listId}/items/{itemId}/attachmentFiles('<ATTACHMENT_NAME>')/$value
		String url = String.format("%s/lists/%s/items/%s/attachmentFiles('%s')/$value", system.getBaseUri(), listId,
				itemId, attachmentName);

		LOGGER.info("Downloading attachment from URL: {}", url);

		// Prepare headers
		HttpHeaders headers = createHeaders(system);

		// restTemplate.execute allows us to handle the raw stream

		return restTemplate.execute(new URI(url), HttpMethod.GET, request -> request.getHeaders().addAll(headers),
				clientHttpResponse -> {
					// Grab the content type (may be null if server doesn't send one)
					MediaType mediaType = clientHttpResponse.getHeaders().getContentType();
					String contentType = (mediaType != null) ? mediaType.toString() : null;

					// Return a custom wrapper with InputStream + metadata
					return new CloudSharepointDownloadedAttachment(clientHttpResponse.getBody(), contentType);
				});

	}

	/**
	 * Retrieves the lists in a given SharePoint site.
	 *
	 * - Classic (SharePoint REST): /sites/{siteId}/lists or possibly
	 * /_api/web/lists (if your baseUri points to the site root). - Microsoft Graph:
	 * /v1.0/sites/{siteId}/lists
	 *
	 * @param system The SharePoint system configuration
	 * @param siteId The site identifier
	 * @return A list of SharePoint lists
	 */
	public List<CloudSharepointList> getLists(CloudSharepointApi system, String siteId)
			throws GeboRestIntegrationException {

		// Classic approach (depending on your environment):
		// String url = system.getBaseUri() + "/sites/" + siteId + "/_api/web/lists";
		// OR you might do:
		// String url = system.getBaseUri() + "/sites/" + siteId + "/lists";
		// This can vary based on your exact endpoint.
		// For example, if system.getBaseUri() is already pointing to "/_api/web",
		// you'd adjust accordingly.

		String url = composeUrl(system, "/sites/" + siteId + "/lists");
		LOGGER.info("Fetching lists from URL: {}", url);

		HttpHeaders headers = createHeaders(system);
		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		ResponseEntity<CloudSharepointListResponse> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
				CloudSharepointListResponse.class);

		if (response.getBody() == null) {
			LOGGER.warn("No response body received when fetching lists from SharePoint.");
			return List.of();
		}
		return response.getBody().getLists();
	}

	// ----------------------------------------------------------------
	// 5) NEW: Fetch items from a specific list
	// ----------------------------------------------------------------
	/**
	 * Retrieves the items (rows) within a given list.
	 *
	 * - Classic (SharePoint REST): /lists/{listId}/items - Microsoft Graph:
	 * /v1.0/sites/{siteId}/lists/{listId}/items
	 *
	 * @param system The SharePoint system configuration
	 * @param siteId The site identifier
	 * @param listId The list identifier
	 * @return A list of list items
	 */
	public List<CloudSharepointListItem> getListItems(CloudSharepointApi system, String siteId, String listId)
			throws GeboRestIntegrationException {

		// Classic approach:
		// String url = system.getBaseUri() + "/sites/" + siteId + "/lists/" + listId +
		// "/items"
		// Graph approach:
		// String url = "https://graph.microsoft.com/v1.0/sites/" + siteId + "/lists/" +
		// listId + "/items"

		String url = composeUrl(system,
				"/sites/" + siteId + "/lists/" +listId + "/items");
		LOGGER.info("Fetching items from list '{}' via URL: {}", listId, url);

		HttpHeaders headers = createHeaders(system);
		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		ResponseEntity<CloudSharepointListItemResponse> response = restTemplate.exchange(url, HttpMethod.GET,
				requestEntity, CloudSharepointListItemResponse.class);

		if (response.getBody() == null) {
			LOGGER.warn("No response body received when fetching list items from SharePoint.");
			return List.of();
		}
		return response.getBody().getItems();
	}

	/**
	 * Example method to list all sites in a tenant using Microsoft Graph. -
	 * Endpoint: GET https://graph.microsoft.com/v1.0/sites?search=* - You need the
	 * Sites.Read.All permission.
	 *
	 * @param system the SharePoint system configuration
	 * @return list of sites
	 */
	public List<CloudSharepointSite> getAllSites(CloudSharepointApi system) throws GeboRestIntegrationException {

		// Graph endpoint to search for sites
		// Some tenants prefer ?search=keyword or a different filter approach
		String url = "https://graph.microsoft.com/v1.0/sites?search=*";
		// String url = system.getBaseUri()+"v1.0/sites?search=*";
		LOGGER.info("Fetching all sites via Graph from URL: {}", url);

		HttpHeaders headers = createHeaders(system);
		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		ResponseEntity<CloudSharepointSiteResponse> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
				CloudSharepointSiteResponse.class);

		if (response.getBody() == null) {
			LOGGER.warn("No response body received when fetching all sites.");
			return List.of();
		}

		return response.getBody().getValue(); // Adjust if the field is named differently
	}

	public String getSiteId(CloudSharepointSite site) {
		String id = site.getId();
		/*if (id != null) {
			StringTokenizer tokenizer = new StringTokenizer(id, ",");
			String tok1 = tokenizer.nextToken();
			if (tokenizer.hasMoreTokens()) {
				String tok2 = tokenizer.nextToken();
				//return tok2;
				if (tokenizer.hasMoreTokens()) {
					tok2 = tokenizer.nextToken();
					return tok2;
				}
			}
		}*/
		return id;
	}

	/*
	 * ... (Other existing methods, e.g. getPages, getLists, etc.)
	 */

	// -----------------------------------------
	// Additional POJOs
	// -----------------------------------------

}
