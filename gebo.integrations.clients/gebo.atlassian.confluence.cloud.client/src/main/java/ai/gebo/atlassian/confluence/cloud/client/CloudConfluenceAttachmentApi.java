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

import ai.gebo.atlassian.confluence.cloud.model.CloudConfluenceAttachmentItem;
import ai.gebo.atlassian.confluence.cloud.model.CloudConfluenceAttachmentsList;
import ai.gebo.atlassian.confluence.cloud.model.CloudConfluenceContent;
import ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException;
import lombok.AllArgsConstructor;

/**
 * API for interacting with Confluence Cloud attachments.
 * Provides methods to retrieve attachment details and content.
 * AI generated comments
 */
@AllArgsConstructor
public class CloudConfluenceAttachmentApi {
	
    /** Connection handler for Confluence Cloud API */
	protected final CloudConfluenceConnection api;

    /**
     * Retrieve details of an attachment by its ID.
     * @param id the ID of the attachment.
     * @return CloudConfluenceAttachmentItem containing attachment details.
     * @throws GeboRestIntegrationException if an error occurs during retrieval.
     */
	public CloudConfluenceAttachmentItem getAttachment(String id) throws GeboRestIntegrationException {
		String relativeUrl = "/wiki/api/v2/attachments/" + id + "&expand=space,body.view,version,container";
		String url = api.getBaseUrl() + relativeUrl;
		HttpEntity requestEntity = new HttpEntity<CloudConfluenceAttachmentsList>(api.createHeaders());
		Class responseType = CloudConfluenceAttachmentItem.class;
		ResponseEntity<CloudConfluenceAttachmentItem> response = api.restTemplate.exchange(url, HttpMethod.GET,
				requestEntity, responseType);
		return response.getBody();
	}

    /**
     * Retrieve the content of an attachment by its attachment item.
     * @param pageId the ID of the Confluence page.
     * @param attachementItem the attachment item whose content is needed.
     * @return InputStream of the attachment content.
     * @throws GeboRestIntegrationException if an error occurs during retrieval.
     */
	public InputStream getAttachmentContent(String pageId, CloudConfluenceAttachmentItem attachementItem)
			throws GeboRestIntegrationException {
		String url = getAttachmentUrl(pageId, attachementItem);
		HttpEntity requestEntity = new HttpEntity<CloudConfluenceContent>(api.createHeaders());
		Class responseType = byte[].class;
		ResponseEntity<byte[]> response = api.restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType);
		HttpHeaders hdeaders = response.getHeaders(); // Headers are retrieved but not used
		return new ByteArrayInputStream(response.getBody());
	}

    /**
     * Retrieve the content of an attachment by its title.
     * @param confluenceContentId the content ID in Confluence.
     * @param attachmentTitle the title of the attachment.
     * @return InputStream of the attachment content.
     * @throws GeboRestIntegrationException if an error occurs during retrieval.
     */
	public InputStream getAttachmentContent(String confluenceContentId, String attachmentTitle)
			throws GeboRestIntegrationException {
		String url = getAttachmentUrl(confluenceContentId, attachmentTitle);
		HttpEntity requestEntity = new HttpEntity<CloudConfluenceContent>(api.createHeaders());
		Class responseType = byte[].class;
		ResponseEntity<byte[]> response = api.restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType);
		HttpHeaders hdeaders = response.getHeaders(); // Headers are retrieved but not used
		return new ByteArrayInputStream(response.getBody());
	}

    /**
     * Construct the URL for downloading an attachment.
     * @param pageId the ID of the Confluence page.
     * @param attachementItem the attachment item for which URL is needed.
     * @return the constructed URL as String.
     */
	public String getAttachmentUrl(String pageId, CloudConfluenceAttachmentItem attachementItem) {
		String relative = "/wiki/download/attachments/" + pageId + "/" + URLEncoder.encode(attachementItem.getTitle());
		String url = api.getBaseUrl() + relative;
		return url;
	}

    /**
     * Construct the URL for downloading an attachment by its title.
     * @param pageId the ID of the Confluence page.
     * @param attachmentTitle the attachment title for which URL is needed.
     * @return the constructed URL as String.
     */
	public String getAttachmentUrl(String pageId, String attachmentTitle) {
		String relative = "/wiki/download/attachments/" + pageId + "/" + URLEncoder.encode(attachmentTitle);
		String url = api.getBaseUrl() + relative;
		return url;
	}
}