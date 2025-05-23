/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.confluence.cloud.client;

import java.net.URLEncoder;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import ai.gebo.architecture.utils.NodesSorterUtil;
import ai.gebo.atlassian.confluence.cloud.model.CloudConfluenceSpacesList;
import ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException;
import lombok.AllArgsConstructor;

/**
 * Provides an API client to interact with the Confluence Cloud spaces.
 * AI generated comments
 */
@AllArgsConstructor
public class CloudConfluenceSpaceApi {
    // Connection to Confluence Cloud API
    protected final CloudConfluenceConnection api;

    /**
     * Retrieves a paginated list of all spaces from Confluence.
     *
     * @param start The starting index of the results.
     * @param limit The maximum number of spaces to retrieve.
     * @param type The type of the spaces to filter by, can be null.
     * @return A list containing the Confluence spaces.
     * @throws GeboRestIntegrationException if there is a REST integration failure.
     */
    public CloudConfluenceSpacesList getAllSpaces(int start, int limit, String type)
            throws GeboRestIntegrationException {
        String relative = "/wiki/rest/api/space";
        String url = api.getBaseUrl() + relative + "?start=" + start + "&limit=" + limit;
        // Encode the type parameter if it is provided
        if (type != null) {
            url = url + "&type=" + URLEncoder.encode(type);
        }
        // Create an HTTP entity with the necessary headers
        HttpEntity requestEntity = new HttpEntity<CloudConfluenceSpacesList>(api.createHeaders());
        // Specify the response type
        Class responseType = CloudConfluenceSpacesList.class;
        // Execute the REST API call and receive the response
        ResponseEntity<CloudConfluenceSpacesList> response = api.restTemplate.exchange(url, HttpMethod.GET,
                requestEntity, responseType);
        CloudConfluenceSpacesList out = response.getBody();
        // Sort the received spaces list
        sort(out);
        return out;
    }

    /**
     * Sorts the provided list of Confluence spaces by title.
     *
     * @param list The list to be sorted.
     */
    private void sort(CloudConfluenceSpacesList list) {
        // Sort the spaces based on their titles
        list.setResults(NodesSorterUtil.sort(x -> {
            return x.getTitle();
        }, list.getResults()));
    
    }

    /**
     * Retrieves the space information for a specific space key.
     *
     * @param key The key of the space to retrieve.
     * @return The space details, including its homepage and metadata.
     * @throws GeboRestIntegrationException if there is a REST integration failure.
     */
    public CloudConfluenceSpacesList getSpace(String key) throws GeboRestIntegrationException {
        String relative = "/wiki/rest/api/space?spaceKey=" + URLEncoder.encode(key)
                + "&expand=homepage&expand=metadata";
        String url = api.getBaseUrl() + relative;
        // Create an HTTP entity with the necessary headers
        HttpEntity requestEntity = new HttpEntity<CloudConfluenceSpacesList>(api.createHeaders());
        // Specify the response type
        Class responseType = CloudConfluenceSpacesList.class;
        // Execute the REST API call and receive the response
        ResponseEntity<CloudConfluenceSpacesList> response = api.restTemplate.exchange(url, HttpMethod.GET,
                requestEntity, responseType);
        return response.getBody();
    }

}