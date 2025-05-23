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

import ai.gebo.atlassian.confluence.onpremise.model.OnPremiseConfluenceAttachmentItem;
import ai.gebo.atlassian.confluence.onpremise.model.OnPremiseConfluenceContent;
import ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException;
import lombok.AllArgsConstructor;

/**
 * AI generated comments
 * This class provides methods to interact with Confluence attachments
 * on an on-premise server. It allows retrieving attachment content
 * by constructing the appropriate API endpoint URLs.
 */
@AllArgsConstructor
public class OnPremiseConfluenceAttachmentApi {
    // Connection interface to communicate with Confluence API
    protected final OnPremiseConfluenceConnection api;

    /**
     * Retrieves the content of a Confluence attachment as an InputStream,
     * using a page ID and attachment item.
     *
     * @param pageId          the ID of the Confluence page containing the attachment
     * @param attachementItem the attachment item metadata
     * @return InputStream representing the attachment's content
     * @throws GeboRestIntegrationException if there is an error accessing the attachment
     */
    public InputStream getAttachmentContent(String pageId, OnPremiseConfluenceAttachmentItem attachementItem)
            throws GeboRestIntegrationException {
        String url = getAttachmentUrl(pageId, attachementItem);
        HttpEntity requestEntity = new HttpEntity<OnPremiseConfluenceContent>(api.createHeaders());
        // Define the expected response type
        Class responseType = byte[].class;
        // Exchange the request and get the response
        ResponseEntity<byte[]> response = api.restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType);
        HttpHeaders hdeaders = response.getHeaders();
        return new ByteArrayInputStream(response.getBody());
    }

    /**
     * Retrieves the content of a Confluence attachment as an InputStream,
     * using a content ID and attachment title.
     *
     * @param confluenceContentId the ID of the Confluence content containing the attachment
     * @param attachmentTitle     the title of the attachment
     * @return InputStream representing the attachment's content
     * @throws GeboRestIntegrationException if there is an error accessing the attachment
     */
    public InputStream getAttachmentContent(String confluenceContentId, String attachmentTitle)
            throws GeboRestIntegrationException {
        String url = getAttachmentUrl(confluenceContentId, attachmentTitle);
        HttpEntity requestEntity = new HttpEntity<OnPremiseConfluenceContent>(api.createHeaders());
        // Define the expected response type
        Class responseType = byte[].class;
        // Exchange the request and get the response
        ResponseEntity<byte[]> response = api.restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType);
        HttpHeaders hdeaders = response.getHeaders();
        return new ByteArrayInputStream(response.getBody());
    }

    /**
     * Constructs the URL to access an attachment of a given page using the
     * attachment item metadata.
     *
     * @param pageId          the ID of the Confluence page
     * @param attachementItem the attachment item metadata
     * @return the constructed URL to access the attachment
     */
    public String getAttachmentUrl(String pageId, OnPremiseConfluenceAttachmentItem attachementItem) {
        // Construct relative path for the attachment
        String relative = "/download/attachments/" + pageId + "/" + URLEncoder.encode(attachementItem.getTitle());
        String url = api.getBaseUrl() + relative;
        return url;
    }

    /**
     * Constructs the URL to access an attachment of a given page using its title.
     *
     * @param pageId          the ID of the Confluence page
     * @param attachmentTitle the title of the attachment
     * @return the constructed URL to access the attachment
     */
    public String getAttachmentUrl(String pageId, String attachmentTitle) {
        // Construct relative path for the attachment
        String relative = "/download/attachments/" + pageId + "/" + URLEncoder.encode(attachmentTitle);
        String url = api.getBaseUrl() + relative;
        return url;
    }

}