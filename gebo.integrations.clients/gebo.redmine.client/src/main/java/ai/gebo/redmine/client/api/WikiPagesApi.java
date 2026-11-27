package ai.gebo.redmine.client.api;

import ai.gebo.redmine.client.invoker.ApiClient;

import ai.gebo.redmine.client.model.CreateIssueRequestIssueProjectId;
import ai.gebo.redmine.client.model.Errors;
import ai.gebo.redmine.client.model.GetWikiPage200Response;
import ai.gebo.redmine.client.model.GetWikiPages200Response;
import ai.gebo.redmine.client.model.UpdateWikiPageRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient.ResponseSpec;
import org.springframework.web.client.RestClientResponseException;

@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.28.0")
public class WikiPagesApi {
    private ApiClient apiClient;

    public WikiPagesApi() {
        this(new ApiClient());
    }

    public WikiPagesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete wiki page
     * When the requested title refers to a redirect, Redmine returns 302 without deleting the page. Send the DELETE request to the current title to delete the page.
     * <p><b>204</b> - 
     * <p><b>302</b> - Redmine redirects requests for an old wiki page title to the page&#39;s current title after a rename or move. This also applies when the configured start-page title points to that redirect.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param wikiPageTitle The wikiPageTitle parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_WikiPages#Deleting-a-wiki-page">Delete wiki page Documentation</a>
     */
    private ResponseSpec deleteWikiPageRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequestIssueProjectId projectId, @jakarta.annotation.Nonnull String wikiPageTitle, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling deleteWikiPage", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling deleteWikiPage", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'wikiPageTitle' is set
        if (wikiPageTitle == null) {
            throw new RestClientResponseException("Missing the required parameter 'wikiPageTitle' when calling deleteWikiPage", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("project_id", projectId);
        pathParams.put("wiki_page_title", wikiPageTitle);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (xRedmineSwitchUser != null) {
            headerParams.add("X-Redmine-Switch-User", apiClient.parameterToString(xRedmineSwitchUser));
        }

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/projects/{project_id}/wiki/{wiki_page_title}.{format}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete wiki page
     * When the requested title refers to a redirect, Redmine returns 302 without deleting the page. Send the DELETE request to the current title to delete the page.
     * <p><b>204</b> - 
     * <p><b>302</b> - Redmine redirects requests for an old wiki page title to the page&#39;s current title after a rename or move. This also applies when the configured start-page title points to that redirect.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param wikiPageTitle The wikiPageTitle parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_WikiPages#Deleting-a-wiki-page">Delete wiki page Documentation</a>
     */
    public void deleteWikiPage(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequestIssueProjectId projectId, @jakarta.annotation.Nonnull String wikiPageTitle, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteWikiPageRequestCreation(format, projectId, wikiPageTitle, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * Delete wiki page
     * When the requested title refers to a redirect, Redmine returns 302 without deleting the page. Send the DELETE request to the current title to delete the page.
     * <p><b>204</b> - 
     * <p><b>302</b> - Redmine redirects requests for an old wiki page title to the page&#39;s current title after a rename or move. This also applies when the configured start-page title points to that redirect.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param wikiPageTitle The wikiPageTitle parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_WikiPages#Deleting-a-wiki-page">Delete wiki page Documentation</a>
     */
    public ResponseEntity<Void> deleteWikiPageWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequestIssueProjectId projectId, @jakarta.annotation.Nonnull String wikiPageTitle, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteWikiPageRequestCreation(format, projectId, wikiPageTitle, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * Delete wiki page
     * When the requested title refers to a redirect, Redmine returns 302 without deleting the page. Send the DELETE request to the current title to delete the page.
     * <p><b>204</b> - 
     * <p><b>302</b> - Redmine redirects requests for an old wiki page title to the page&#39;s current title after a rename or move. This also applies when the configured start-page title points to that redirect.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param wikiPageTitle The wikiPageTitle parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_WikiPages#Deleting-a-wiki-page">Delete wiki page Documentation</a>
     */
    public ResponseSpec deleteWikiPageWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequestIssueProjectId projectId, @jakarta.annotation.Nonnull String wikiPageTitle, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return deleteWikiPageRequestCreation(format, projectId, wikiPageTitle, xRedmineSwitchUser);
    }

    /**
     * Show wiki page
     * 
     * <p><b>200</b> - 
     * <p><b>302</b> - Redmine redirects requests for an old wiki page title to the page&#39;s current title after a rename or move. This also applies when the configured start-page title points to that redirect.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param wikiPageTitle The wikiPageTitle parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param include Comma-separated list of associated data to include in the response. Values: &#x60;attachments&#x60; (file attachments)
     * @param version Historical content version; an alternative to the versioned page path.
     * @return GetWikiPage200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_WikiPages#Getting-a-wiki-page">Show wiki page Documentation</a>
     */
    private ResponseSpec getWikiPageRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequestIssueProjectId projectId, @jakarta.annotation.Nonnull String wikiPageTitle, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable List<String> include, @jakarta.annotation.Nullable Integer version) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling getWikiPage", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling getWikiPage", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'wikiPageTitle' is set
        if (wikiPageTitle == null) {
            throw new RestClientResponseException("Missing the required parameter 'wikiPageTitle' when calling getWikiPage", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("project_id", projectId);
        pathParams.put("wiki_page_title", wikiPageTitle);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "include", include));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "version", version));

        if (xRedmineSwitchUser != null) {
            headerParams.add("X-Redmine-Switch-User", apiClient.parameterToString(xRedmineSwitchUser));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<GetWikiPage200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/projects/{project_id}/wiki/{wiki_page_title}.{format}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Show wiki page
     * 
     * <p><b>200</b> - 
     * <p><b>302</b> - Redmine redirects requests for an old wiki page title to the page&#39;s current title after a rename or move. This also applies when the configured start-page title points to that redirect.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param wikiPageTitle The wikiPageTitle parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param include Comma-separated list of associated data to include in the response. Values: &#x60;attachments&#x60; (file attachments)
     * @param version Historical content version; an alternative to the versioned page path.
     * @return GetWikiPage200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_WikiPages#Getting-a-wiki-page">Show wiki page Documentation</a>
     */
    public GetWikiPage200Response getWikiPage(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequestIssueProjectId projectId, @jakarta.annotation.Nonnull String wikiPageTitle, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable List<String> include, @jakarta.annotation.Nullable Integer version) throws RestClientResponseException {
        ParameterizedTypeReference<GetWikiPage200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getWikiPageRequestCreation(format, projectId, wikiPageTitle, xRedmineSwitchUser, include, version).body(localVarReturnType);
    }

    /**
     * Show wiki page
     * 
     * <p><b>200</b> - 
     * <p><b>302</b> - Redmine redirects requests for an old wiki page title to the page&#39;s current title after a rename or move. This also applies when the configured start-page title points to that redirect.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param wikiPageTitle The wikiPageTitle parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param include Comma-separated list of associated data to include in the response. Values: &#x60;attachments&#x60; (file attachments)
     * @param version Historical content version; an alternative to the versioned page path.
     * @return ResponseEntity&lt;GetWikiPage200Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_WikiPages#Getting-a-wiki-page">Show wiki page Documentation</a>
     */
    public ResponseEntity<GetWikiPage200Response> getWikiPageWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequestIssueProjectId projectId, @jakarta.annotation.Nonnull String wikiPageTitle, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable List<String> include, @jakarta.annotation.Nullable Integer version) throws RestClientResponseException {
        ParameterizedTypeReference<GetWikiPage200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getWikiPageRequestCreation(format, projectId, wikiPageTitle, xRedmineSwitchUser, include, version).toEntity(localVarReturnType);
    }

    /**
     * Show wiki page
     * 
     * <p><b>200</b> - 
     * <p><b>302</b> - Redmine redirects requests for an old wiki page title to the page&#39;s current title after a rename or move. This also applies when the configured start-page title points to that redirect.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param wikiPageTitle The wikiPageTitle parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param include Comma-separated list of associated data to include in the response. Values: &#x60;attachments&#x60; (file attachments)
     * @param version Historical content version; an alternative to the versioned page path.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_WikiPages#Getting-a-wiki-page">Show wiki page Documentation</a>
     */
    public ResponseSpec getWikiPageWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequestIssueProjectId projectId, @jakarta.annotation.Nonnull String wikiPageTitle, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable List<String> include, @jakarta.annotation.Nullable Integer version) throws RestClientResponseException {
        return getWikiPageRequestCreation(format, projectId, wikiPageTitle, xRedmineSwitchUser, include, version);
    }

    /**
     * Show wiki page by specific version
     * Returns the requested content version. If the requested title is a redirect, the 302 response has a &#x60;Location&#x60; header that points to the current title and retains the requested version number.
     * <p><b>200</b> - 
     * <p><b>302</b> - Redmine redirects requests for an old wiki page title to the page&#39;s current title after a rename or move. This also applies when the configured start-page title points to that redirect.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param wikiPageTitle The wikiPageTitle parameter
     * @param versionId The versionId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param include Comma-separated list of associated data to include in the response. Values: &#x60;attachments&#x60; (file attachments)
     * @return GetWikiPage200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_WikiPages#Getting-an-old-version-of-a-wiki-page">Show wiki page by specific version Documentation</a>
     */
    private ResponseSpec getWikiPageByVersionRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequestIssueProjectId projectId, @jakarta.annotation.Nonnull String wikiPageTitle, @jakarta.annotation.Nonnull Integer versionId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable List<String> include) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling getWikiPageByVersion", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling getWikiPageByVersion", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'wikiPageTitle' is set
        if (wikiPageTitle == null) {
            throw new RestClientResponseException("Missing the required parameter 'wikiPageTitle' when calling getWikiPageByVersion", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'versionId' is set
        if (versionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'versionId' when calling getWikiPageByVersion", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("project_id", projectId);
        pathParams.put("wiki_page_title", wikiPageTitle);
        pathParams.put("version_id", versionId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "include", include));

        if (xRedmineSwitchUser != null) {
            headerParams.add("X-Redmine-Switch-User", apiClient.parameterToString(xRedmineSwitchUser));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<GetWikiPage200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/projects/{project_id}/wiki/{wiki_page_title}/{version_id}.{format}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Show wiki page by specific version
     * Returns the requested content version. If the requested title is a redirect, the 302 response has a &#x60;Location&#x60; header that points to the current title and retains the requested version number.
     * <p><b>200</b> - 
     * <p><b>302</b> - Redmine redirects requests for an old wiki page title to the page&#39;s current title after a rename or move. This also applies when the configured start-page title points to that redirect.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param wikiPageTitle The wikiPageTitle parameter
     * @param versionId The versionId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param include Comma-separated list of associated data to include in the response. Values: &#x60;attachments&#x60; (file attachments)
     * @return GetWikiPage200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_WikiPages#Getting-an-old-version-of-a-wiki-page">Show wiki page by specific version Documentation</a>
     */
    public GetWikiPage200Response getWikiPageByVersion(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequestIssueProjectId projectId, @jakarta.annotation.Nonnull String wikiPageTitle, @jakarta.annotation.Nonnull Integer versionId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable List<String> include) throws RestClientResponseException {
        ParameterizedTypeReference<GetWikiPage200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getWikiPageByVersionRequestCreation(format, projectId, wikiPageTitle, versionId, xRedmineSwitchUser, include).body(localVarReturnType);
    }

    /**
     * Show wiki page by specific version
     * Returns the requested content version. If the requested title is a redirect, the 302 response has a &#x60;Location&#x60; header that points to the current title and retains the requested version number.
     * <p><b>200</b> - 
     * <p><b>302</b> - Redmine redirects requests for an old wiki page title to the page&#39;s current title after a rename or move. This also applies when the configured start-page title points to that redirect.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param wikiPageTitle The wikiPageTitle parameter
     * @param versionId The versionId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param include Comma-separated list of associated data to include in the response. Values: &#x60;attachments&#x60; (file attachments)
     * @return ResponseEntity&lt;GetWikiPage200Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_WikiPages#Getting-an-old-version-of-a-wiki-page">Show wiki page by specific version Documentation</a>
     */
    public ResponseEntity<GetWikiPage200Response> getWikiPageByVersionWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequestIssueProjectId projectId, @jakarta.annotation.Nonnull String wikiPageTitle, @jakarta.annotation.Nonnull Integer versionId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable List<String> include) throws RestClientResponseException {
        ParameterizedTypeReference<GetWikiPage200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getWikiPageByVersionRequestCreation(format, projectId, wikiPageTitle, versionId, xRedmineSwitchUser, include).toEntity(localVarReturnType);
    }

    /**
     * Show wiki page by specific version
     * Returns the requested content version. If the requested title is a redirect, the 302 response has a &#x60;Location&#x60; header that points to the current title and retains the requested version number.
     * <p><b>200</b> - 
     * <p><b>302</b> - Redmine redirects requests for an old wiki page title to the page&#39;s current title after a rename or move. This also applies when the configured start-page title points to that redirect.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param wikiPageTitle The wikiPageTitle parameter
     * @param versionId The versionId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param include Comma-separated list of associated data to include in the response. Values: &#x60;attachments&#x60; (file attachments)
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_WikiPages#Getting-an-old-version-of-a-wiki-page">Show wiki page by specific version Documentation</a>
     */
    public ResponseSpec getWikiPageByVersionWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequestIssueProjectId projectId, @jakarta.annotation.Nonnull String wikiPageTitle, @jakarta.annotation.Nonnull Integer versionId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable List<String> include) throws RestClientResponseException {
        return getWikiPageByVersionRequestCreation(format, projectId, wikiPageTitle, versionId, xRedmineSwitchUser, include);
    }

    /**
     * List wiki pages
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return GetWikiPages200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_WikiPages#Getting-the-pages-list-of-a-wiki">List wiki pages Documentation</a>
     */
    private ResponseSpec getWikiPagesRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequestIssueProjectId projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling getWikiPages", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling getWikiPages", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("project_id", projectId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (xRedmineSwitchUser != null) {
            headerParams.add("X-Redmine-Switch-User", apiClient.parameterToString(xRedmineSwitchUser));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<GetWikiPages200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/projects/{project_id}/wiki/index.{format}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List wiki pages
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return GetWikiPages200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_WikiPages#Getting-the-pages-list-of-a-wiki">List wiki pages Documentation</a>
     */
    public GetWikiPages200Response getWikiPages(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequestIssueProjectId projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<GetWikiPages200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getWikiPagesRequestCreation(format, projectId, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * List wiki pages
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseEntity&lt;GetWikiPages200Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_WikiPages#Getting-the-pages-list-of-a-wiki">List wiki pages Documentation</a>
     */
    public ResponseEntity<GetWikiPages200Response> getWikiPagesWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequestIssueProjectId projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<GetWikiPages200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getWikiPagesRequestCreation(format, projectId, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * List wiki pages
     * 
     * <p><b>200</b> - 
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_WikiPages#Getting-the-pages-list-of-a-wiki">List wiki pages Documentation</a>
     */
    public ResponseSpec getWikiPagesWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequestIssueProjectId projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return getWikiPagesRequestCreation(format, projectId, xRedmineSwitchUser);
    }

    /**
     * Show wiki start page
     * Returns the configured start page of the project wiki.
     * <p><b>200</b> - 
     * <p><b>302</b> - Redmine redirects requests for an old wiki page title to the page&#39;s current title after a rename or move. This also applies when the configured start-page title points to that redirect.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param include Comma-separated list of associated data to include in the response. Values: &#x60;attachments&#x60; (file attachments)
     * @param version Historical content version; an alternative to the versioned page path.
     * @return GetWikiPage200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_WikiPages#Getting-a-wiki-page">Show wiki start page Documentation</a>
     */
    private ResponseSpec getWikiStartPageRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequestIssueProjectId projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable List<String> include, @jakarta.annotation.Nullable Integer version) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling getWikiStartPage", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling getWikiStartPage", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("project_id", projectId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "include", include));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "version", version));

        if (xRedmineSwitchUser != null) {
            headerParams.add("X-Redmine-Switch-User", apiClient.parameterToString(xRedmineSwitchUser));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<GetWikiPage200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/projects/{project_id}/wiki.{format}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Show wiki start page
     * Returns the configured start page of the project wiki.
     * <p><b>200</b> - 
     * <p><b>302</b> - Redmine redirects requests for an old wiki page title to the page&#39;s current title after a rename or move. This also applies when the configured start-page title points to that redirect.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param include Comma-separated list of associated data to include in the response. Values: &#x60;attachments&#x60; (file attachments)
     * @param version Historical content version; an alternative to the versioned page path.
     * @return GetWikiPage200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_WikiPages#Getting-a-wiki-page">Show wiki start page Documentation</a>
     */
    public GetWikiPage200Response getWikiStartPage(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequestIssueProjectId projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable List<String> include, @jakarta.annotation.Nullable Integer version) throws RestClientResponseException {
        ParameterizedTypeReference<GetWikiPage200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getWikiStartPageRequestCreation(format, projectId, xRedmineSwitchUser, include, version).body(localVarReturnType);
    }

    /**
     * Show wiki start page
     * Returns the configured start page of the project wiki.
     * <p><b>200</b> - 
     * <p><b>302</b> - Redmine redirects requests for an old wiki page title to the page&#39;s current title after a rename or move. This also applies when the configured start-page title points to that redirect.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param include Comma-separated list of associated data to include in the response. Values: &#x60;attachments&#x60; (file attachments)
     * @param version Historical content version; an alternative to the versioned page path.
     * @return ResponseEntity&lt;GetWikiPage200Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_WikiPages#Getting-a-wiki-page">Show wiki start page Documentation</a>
     */
    public ResponseEntity<GetWikiPage200Response> getWikiStartPageWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequestIssueProjectId projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable List<String> include, @jakarta.annotation.Nullable Integer version) throws RestClientResponseException {
        ParameterizedTypeReference<GetWikiPage200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getWikiStartPageRequestCreation(format, projectId, xRedmineSwitchUser, include, version).toEntity(localVarReturnType);
    }

    /**
     * Show wiki start page
     * Returns the configured start page of the project wiki.
     * <p><b>200</b> - 
     * <p><b>302</b> - Redmine redirects requests for an old wiki page title to the page&#39;s current title after a rename or move. This also applies when the configured start-page title points to that redirect.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - 
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @param include Comma-separated list of associated data to include in the response. Values: &#x60;attachments&#x60; (file attachments)
     * @param version Historical content version; an alternative to the versioned page path.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_WikiPages#Getting-a-wiki-page">Show wiki start page Documentation</a>
     */
    public ResponseSpec getWikiStartPageWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequestIssueProjectId projectId, @jakarta.annotation.Nullable String xRedmineSwitchUser, @jakarta.annotation.Nullable List<String> include, @jakarta.annotation.Nullable Integer version) throws RestClientResponseException {
        return getWikiStartPageRequestCreation(format, projectId, xRedmineSwitchUser, include, version);
    }

    /**
     * Create or update wiki page
     * 
     * <p><b>201</b> - The wiki page was created.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>409</b> - The supplied content version conflicts with the current wiki content version.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param wikiPageTitle The wikiPageTitle parameter
     * @param updateWikiPageRequest The updateWikiPageRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return GetWikiPage200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_WikiPages#Creating-or-updating-a-wiki-page">Create or update wiki page Documentation</a>
     */
    private ResponseSpec updateWikiPageRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequestIssueProjectId projectId, @jakarta.annotation.Nonnull String wikiPageTitle, @jakarta.annotation.Nonnull UpdateWikiPageRequest updateWikiPageRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling updateWikiPage", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling updateWikiPage", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'wikiPageTitle' is set
        if (wikiPageTitle == null) {
            throw new RestClientResponseException("Missing the required parameter 'wikiPageTitle' when calling updateWikiPage", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'updateWikiPageRequest' is set
        if (updateWikiPageRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'updateWikiPageRequest' when calling updateWikiPage", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("project_id", projectId);
        pathParams.put("wiki_page_title", wikiPageTitle);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (xRedmineSwitchUser != null) {
            headerParams.add("X-Redmine-Switch-User", apiClient.parameterToString(xRedmineSwitchUser));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<GetWikiPage200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/projects/{project_id}/wiki/{wiki_page_title}.{format}", HttpMethod.PUT, pathParams, localVarQueryParams, updateWikiPageRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create or update wiki page
     * 
     * <p><b>201</b> - The wiki page was created.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>409</b> - The supplied content version conflicts with the current wiki content version.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param wikiPageTitle The wikiPageTitle parameter
     * @param updateWikiPageRequest The updateWikiPageRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return GetWikiPage200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_WikiPages#Creating-or-updating-a-wiki-page">Create or update wiki page Documentation</a>
     */
    public GetWikiPage200Response updateWikiPage(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequestIssueProjectId projectId, @jakarta.annotation.Nonnull String wikiPageTitle, @jakarta.annotation.Nonnull UpdateWikiPageRequest updateWikiPageRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<GetWikiPage200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateWikiPageRequestCreation(format, projectId, wikiPageTitle, updateWikiPageRequest, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * Create or update wiki page
     * 
     * <p><b>201</b> - The wiki page was created.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>409</b> - The supplied content version conflicts with the current wiki content version.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param wikiPageTitle The wikiPageTitle parameter
     * @param updateWikiPageRequest The updateWikiPageRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseEntity&lt;GetWikiPage200Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_WikiPages#Creating-or-updating-a-wiki-page">Create or update wiki page Documentation</a>
     */
    public ResponseEntity<GetWikiPage200Response> updateWikiPageWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequestIssueProjectId projectId, @jakarta.annotation.Nonnull String wikiPageTitle, @jakarta.annotation.Nonnull UpdateWikiPageRequest updateWikiPageRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<GetWikiPage200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateWikiPageRequestCreation(format, projectId, wikiPageTitle, updateWikiPageRequest, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * Create or update wiki page
     * 
     * <p><b>201</b> - The wiki page was created.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>409</b> - The supplied content version conflicts with the current wiki content version.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param wikiPageTitle The wikiPageTitle parameter
     * @param updateWikiPageRequest The updateWikiPageRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_WikiPages#Creating-or-updating-a-wiki-page">Create or update wiki page Documentation</a>
     */
    public ResponseSpec updateWikiPageWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequestIssueProjectId projectId, @jakarta.annotation.Nonnull String wikiPageTitle, @jakarta.annotation.Nonnull UpdateWikiPageRequest updateWikiPageRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return updateWikiPageRequestCreation(format, projectId, wikiPageTitle, updateWikiPageRequest, xRedmineSwitchUser);
    }

    /**
     * Create or update wiki page (PATCH)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior.
     * <p><b>201</b> - The wiki page was created.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>409</b> - The supplied content version conflicts with the current wiki content version.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param wikiPageTitle The wikiPageTitle parameter
     * @param updateWikiPageRequest The updateWikiPageRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return GetWikiPage200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_WikiPages#Creating-or-updating-a-wiki-page">Create or update wiki page (PATCH) Documentation</a>
     */
    private ResponseSpec updateWikiPageViaPatchRequestCreation(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequestIssueProjectId projectId, @jakarta.annotation.Nonnull String wikiPageTitle, @jakarta.annotation.Nonnull UpdateWikiPageRequest updateWikiPageRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new RestClientResponseException("Missing the required parameter 'format' when calling updateWikiPageViaPatch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'projectId' is set
        if (projectId == null) {
            throw new RestClientResponseException("Missing the required parameter 'projectId' when calling updateWikiPageViaPatch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'wikiPageTitle' is set
        if (wikiPageTitle == null) {
            throw new RestClientResponseException("Missing the required parameter 'wikiPageTitle' when calling updateWikiPageViaPatch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'updateWikiPageRequest' is set
        if (updateWikiPageRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'updateWikiPageRequest' when calling updateWikiPageViaPatch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("format", format);
        pathParams.put("project_id", projectId);
        pathParams.put("wiki_page_title", wikiPageTitle);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (xRedmineSwitchUser != null) {
            headerParams.add("X-Redmine-Switch-User", apiClient.parameterToString(xRedmineSwitchUser));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "Basic", "OAuth2", "ApiKey", "ApiKeyInQuery" };

        ParameterizedTypeReference<GetWikiPage200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/projects/{project_id}/wiki/{wiki_page_title}.{format}", HttpMethod.PATCH, pathParams, localVarQueryParams, updateWikiPageRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create or update wiki page (PATCH)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior.
     * <p><b>201</b> - The wiki page was created.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>409</b> - The supplied content version conflicts with the current wiki content version.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param wikiPageTitle The wikiPageTitle parameter
     * @param updateWikiPageRequest The updateWikiPageRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return GetWikiPage200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
    * 
    * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_WikiPages#Creating-or-updating-a-wiki-page">Create or update wiki page (PATCH) Documentation</a>
     */
    public GetWikiPage200Response updateWikiPageViaPatch(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequestIssueProjectId projectId, @jakarta.annotation.Nonnull String wikiPageTitle, @jakarta.annotation.Nonnull UpdateWikiPageRequest updateWikiPageRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<GetWikiPage200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateWikiPageViaPatchRequestCreation(format, projectId, wikiPageTitle, updateWikiPageRequest, xRedmineSwitchUser).body(localVarReturnType);
    }

    /**
     * Create or update wiki page (PATCH)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior.
     * <p><b>201</b> - The wiki page was created.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>409</b> - The supplied content version conflicts with the current wiki content version.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param wikiPageTitle The wikiPageTitle parameter
     * @param updateWikiPageRequest The updateWikiPageRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseEntity&lt;GetWikiPage200Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_WikiPages#Creating-or-updating-a-wiki-page">Create or update wiki page (PATCH) Documentation</a>
     */
    public ResponseEntity<GetWikiPage200Response> updateWikiPageViaPatchWithHttpInfo(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequestIssueProjectId projectId, @jakarta.annotation.Nonnull String wikiPageTitle, @jakarta.annotation.Nonnull UpdateWikiPageRequest updateWikiPageRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        ParameterizedTypeReference<GetWikiPage200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateWikiPageViaPatchRequestCreation(format, projectId, wikiPageTitle, updateWikiPageRequest, xRedmineSwitchUser).toEntity(localVarReturnType);
    }

    /**
     * Create or update wiki page (PATCH)
     * Equivalent to PUT on this path, including its permission requirements and request/response behavior.
     * <p><b>201</b> - The wiki page was created.
     * <p><b>204</b> - The request was completed successfully; no response body is returned.
     * <p><b>401</b> - Authentication is required, or Redmine rejected the supplied credentials or authentication method. Operations that allow anonymous access may still succeed without valid credentials.
     * <p><b>403</b> - Access is forbidden by permissions, installation settings, or account restrictions.
     * <p><b>404</b> - The requested resource was not found or is not visible.
     * <p><b>409</b> - The supplied content version conflicts with the current wiki content version.
     * <p><b>412</b> - Invalid X-Redmine-Switch-User header. Returns 412 when the specified user does not exist or is not active. Only applicable to admin users using the X-Redmine-Switch-User header.
     * <p><b>422</b> - The request could not be completed. The response lists the reasons.
     * @param format The format parameter
     * @param projectId The projectId parameter
     * @param wikiPageTitle The wikiPageTitle parameter
     * @param updateWikiPageRequest The updateWikiPageRequest parameter
     * @param xRedmineSwitchUser The xRedmineSwitchUser parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     * 
     * @see <a href="https://www.redmine.org/projects/redmine/wiki/Rest_WikiPages#Creating-or-updating-a-wiki-page">Create or update wiki page (PATCH) Documentation</a>
     */
    public ResponseSpec updateWikiPageViaPatchWithResponseSpec(@jakarta.annotation.Nonnull String format, @jakarta.annotation.Nonnull CreateIssueRequestIssueProjectId projectId, @jakarta.annotation.Nonnull String wikiPageTitle, @jakarta.annotation.Nonnull UpdateWikiPageRequest updateWikiPageRequest, @jakarta.annotation.Nullable String xRedmineSwitchUser) throws RestClientResponseException {
        return updateWikiPageViaPatchRequestCreation(format, projectId, wikiPageTitle, updateWikiPageRequest, xRedmineSwitchUser);
    }
}
