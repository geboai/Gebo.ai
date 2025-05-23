/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.confluence.onpremise.client;

import java.net.URLEncoder;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import ai.gebo.architecture.utils.NodesSorterUtil;
import ai.gebo.atlassian.confluence.onpremise.model.OnPremiseConfluenceSpacesList;
import ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException;
import lombok.AllArgsConstructor;

/**
 * AI generated comments
 * Provides API methods to interact with Confluence Spaces on-premise.
 */
@AllArgsConstructor
public class OnPremiseConfluenceSpaceApi {
    // Connection instance for interacting with Confluence's API
    protected final OnPremiseConfluenceConnection api;

    /**
     * Retrieves a list of all Confluence spaces starting at a specific index and with a set limit.
     *
     * @param start the start index for fetching spaces
     * @param limit the maximum number of spaces to retrieve
     * @param type  the type of spaces to retrieve, if specified
     * @return a list of Confluence spaces
     * @throws GeboRestIntegrationException if there is an issue with the API request
     */
    public OnPremiseConfluenceSpacesList getAllSpaces(int start, int limit, String type)
            throws GeboRestIntegrationException {
        String relative = "/rest/api/space";
        String url = api.getBaseUrl() + relative + "?start=" + start + "&limit=" + limit;
        if (type != null) {
            url += "&type=" + URLEncoder.encode(type);
        }
        HttpEntity requestEntity = new HttpEntity<OnPremiseConfluenceSpacesList>(api.createHeaders());
        Class responseType = OnPremiseConfluenceSpacesList.class;
        ResponseEntity<OnPremiseConfluenceSpacesList> response = api.restTemplate.exchange(url, HttpMethod.GET,
                requestEntity, responseType);
        OnPremiseConfluenceSpacesList out = response.getBody();
        sort(out); // Sorts the spaces list before returning
        return out;
    }

    /**
     * Retrieves a specific Confluence space based on the provided key.
     *
     * @param key the unique key of the space
     * @return the Confluence space corresponding to the key
     * @throws GeboRestIntegrationException if there is an issue with the API request
     */
    public OnPremiseConfluenceSpacesList getSpace(String key) throws GeboRestIntegrationException {
        String relative = "/rest/api/space?spaceKey=" + URLEncoder.encode(key) + "&expand=homepage";
        String url = api.getBaseUrl() + relative;
        HttpEntity requestEntity = new HttpEntity<OnPremiseConfluenceSpacesList>(api.createHeaders());
        Class responseType = OnPremiseConfluenceSpacesList.class;
        ResponseEntity<OnPremiseConfluenceSpacesList> response = api.restTemplate.exchange(url, HttpMethod.GET,
                requestEntity, responseType);
        return response.getBody();
    }

    /**
     * Sorts the list of Confluence spaces based on their title.
     *
     * @param list the list of Confluence spaces to be sorted
     */
    private void sort(OnPremiseConfluenceSpacesList list) {
        list.setResults(NodesSorterUtil.sort(x -> {
            return x.getTitle(); 
        }, list.getResults()));

    }
} 