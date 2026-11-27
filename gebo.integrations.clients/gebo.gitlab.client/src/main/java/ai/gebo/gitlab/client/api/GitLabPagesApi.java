package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesPagesDomain;
import ai.gebo.gitlab.client.model.APIEntitiesPagesDomainBasic;
import ai.gebo.gitlab.client.model.APIEntitiesPagesProjectSettings;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdPagesRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdPagesDomainsRequest;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdPagesDomainsDomainRequest;

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
public class GitLabPagesApi {
    private ApiClient apiClient;

    public GitLabPagesApi() {
        this(new ApiClient());
    }

    public GitLabPagesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Unpublish Pages
     * Unpublishes and removes Pages from a specified project. You must have the Maintainer or Owner role for the project.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdPagesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdPages", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/pages", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Unpublish Pages
     * Unpublishes and removes Pages from a specified project. You must have the Maintainer or Owner role for the project.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdPages(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdPagesRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Unpublish Pages
     * Unpublishes and removes Pages from a specified project. You must have the Maintainer or Owner role for the project.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdPagesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdPagesRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Unpublish Pages
     * Unpublishes and removes Pages from a specified project. You must have the Maintainer or Owner role for the project.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdPagesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return deleteApiV4ProjectsIdPagesRequestCreation(id);
    }

    /**
     * Delete Pages domain
     * Deletes a specified Pages domain in a project.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param domain Custom domain.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdPagesDomainsDomainRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String domain) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdPagesDomainsDomain", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'domain' is set
        if (domain == null) {
            throw new RestClientResponseException("Missing the required parameter 'domain' when calling deleteApiV4ProjectsIdPagesDomainsDomain", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("domain", domain);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/pages/domains/{domain}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete Pages domain
     * Deletes a specified Pages domain in a project.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param domain Custom domain.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ProjectsIdPagesDomainsDomain(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String domain) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ProjectsIdPagesDomainsDomainRequestCreation(id, domain).body(localVarReturnType);
    }

    /**
     * Delete Pages domain
     * Deletes a specified Pages domain in a project.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param domain Custom domain.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ProjectsIdPagesDomainsDomainWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String domain) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdPagesDomainsDomainRequestCreation(id, domain).toEntity(localVarReturnType);
    }

    /**
     * Delete Pages domain
     * Deletes a specified Pages domain in a project.
     * <p><b>204</b> - Resource deleted
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param domain Custom domain.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdPagesDomainsDomainWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String domain) throws RestClientResponseException {
        return deleteApiV4ProjectsIdPagesDomainsDomainRequestCreation(id, domain);
    }

    /**
     * List all Pages domains
     * Lists all Pages domains on the instance. You must have administrator access to the instance.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param domain Domain of the GitLab Pages site to filter on.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesPagesDomainBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4PagesDomainsRequestCreation(@jakarta.annotation.Nullable String domain, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "domain", domain));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesPagesDomainBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/pages/domains", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all Pages domains
     * Lists all Pages domains on the instance. You must have administrator access to the instance.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param domain Domain of the GitLab Pages site to filter on.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesPagesDomainBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesPagesDomainBasic> getApiV4PagesDomains(@jakarta.annotation.Nullable String domain, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesPagesDomainBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PagesDomainsRequestCreation(domain, page, perPage).body(localVarReturnType);
    }

    /**
     * List all Pages domains
     * Lists all Pages domains on the instance. You must have administrator access to the instance.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param domain Domain of the GitLab Pages site to filter on.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesPagesDomainBasic&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesPagesDomainBasic>> getApiV4PagesDomainsWithHttpInfo(@jakarta.annotation.Nullable String domain, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesPagesDomainBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4PagesDomainsRequestCreation(domain, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all Pages domains
     * Lists all Pages domains on the instance. You must have administrator access to the instance.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param domain Domain of the GitLab Pages site to filter on.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4PagesDomainsWithResponseSpec(@jakarta.annotation.Nullable String domain, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4PagesDomainsRequestCreation(domain, page, perPage);
    }

    /**
     * Retrieve Pages settings for a project
     * Retrieves Pages settings for a specified project. You must have the Maintainer or Owner role for the project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return APIEntitiesPagesProjectSettings
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPagesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPages", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesPagesProjectSettings> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/pages", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve Pages settings for a project
     * Retrieves Pages settings for a specified project. You must have the Maintainer or Owner role for the project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return APIEntitiesPagesProjectSettings
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPagesProjectSettings getApiV4ProjectsIdPages(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPagesProjectSettings> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPagesRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve Pages settings for a project
     * Retrieves Pages settings for a specified project. You must have the Maintainer or Owner role for the project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseEntity&lt;APIEntitiesPagesProjectSettings&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPagesProjectSettings> getApiV4ProjectsIdPagesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPagesProjectSettings> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPagesRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve Pages settings for a project
     * Retrieves Pages settings for a specified project. You must have the Maintainer or Owner role for the project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPagesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return getApiV4ProjectsIdPagesRequestCreation(id);
    }

    /**
     * List all Pages domains in a project
     * Lists all Pages domains in a specified project. You must have permissions to view Pages domains.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesPagesDomain&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPagesDomainsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPagesDomains", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesPagesDomain>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/pages/domains", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all Pages domains in a project
     * Lists all Pages domains in a specified project. You must have permissions to view Pages domains.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesPagesDomain&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesPagesDomain> getApiV4ProjectsIdPagesDomains(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesPagesDomain>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPagesDomainsRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all Pages domains in a project
     * Lists all Pages domains in a specified project. You must have permissions to view Pages domains.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesPagesDomain&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesPagesDomain>> getApiV4ProjectsIdPagesDomainsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesPagesDomain>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPagesDomainsRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all Pages domains in a project
     * Lists all Pages domains in a specified project. You must have permissions to view Pages domains.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPagesDomainsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdPagesDomainsRequestCreation(id, page, perPage);
    }

    /**
     * Retrieve a Pages domain
     * Retrieves a Pages domain from a specified project. You must have permissions to view Pages domains.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param domain Custom domain.
     * @return APIEntitiesPagesDomain
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPagesDomainsDomainRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String domain) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPagesDomainsDomain", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'domain' is set
        if (domain == null) {
            throw new RestClientResponseException("Missing the required parameter 'domain' when calling getApiV4ProjectsIdPagesDomainsDomain", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("domain", domain);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesPagesDomain> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/pages/domains/{domain}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a Pages domain
     * Retrieves a Pages domain from a specified project. You must have permissions to view Pages domains.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param domain Custom domain.
     * @return APIEntitiesPagesDomain
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPagesDomain getApiV4ProjectsIdPagesDomainsDomain(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String domain) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPagesDomain> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPagesDomainsDomainRequestCreation(id, domain).body(localVarReturnType);
    }

    /**
     * Retrieve a Pages domain
     * Retrieves a Pages domain from a specified project. You must have permissions to view Pages domains.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param domain Custom domain.
     * @return ResponseEntity&lt;APIEntitiesPagesDomain&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPagesDomain> getApiV4ProjectsIdPagesDomainsDomainWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String domain) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPagesDomain> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPagesDomainsDomainRequestCreation(id, domain).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a Pages domain
     * Retrieves a Pages domain from a specified project. You must have permissions to view Pages domains.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param domain Custom domain.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPagesDomainsDomainWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String domain) throws RestClientResponseException {
        return getApiV4ProjectsIdPagesDomainsDomainRequestCreation(id, domain);
    }

    /**
     * Update Pages settings for a project
     * Updates Pages settings for a specified project. You must have the Maintainer or Owner role for the project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param patchApiV4ProjectsIdPagesRequest The patchApiV4ProjectsIdPagesRequest parameter
     * @return APIEntitiesPagesProjectSettings
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec patchApiV4ProjectsIdPagesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PatchApiV4ProjectsIdPagesRequest patchApiV4ProjectsIdPagesRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling patchApiV4ProjectsIdPages", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesPagesProjectSettings> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/pages", HttpMethod.PATCH, pathParams, localVarQueryParams, patchApiV4ProjectsIdPagesRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update Pages settings for a project
     * Updates Pages settings for a specified project. You must have the Maintainer or Owner role for the project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param patchApiV4ProjectsIdPagesRequest The patchApiV4ProjectsIdPagesRequest parameter
     * @return APIEntitiesPagesProjectSettings
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPagesProjectSettings patchApiV4ProjectsIdPages(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PatchApiV4ProjectsIdPagesRequest patchApiV4ProjectsIdPagesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPagesProjectSettings> localVarReturnType = new ParameterizedTypeReference<>() {};
        return patchApiV4ProjectsIdPagesRequestCreation(id, patchApiV4ProjectsIdPagesRequest).body(localVarReturnType);
    }

    /**
     * Update Pages settings for a project
     * Updates Pages settings for a specified project. You must have the Maintainer or Owner role for the project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param patchApiV4ProjectsIdPagesRequest The patchApiV4ProjectsIdPagesRequest parameter
     * @return ResponseEntity&lt;APIEntitiesPagesProjectSettings&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPagesProjectSettings> patchApiV4ProjectsIdPagesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PatchApiV4ProjectsIdPagesRequest patchApiV4ProjectsIdPagesRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPagesProjectSettings> localVarReturnType = new ParameterizedTypeReference<>() {};
        return patchApiV4ProjectsIdPagesRequestCreation(id, patchApiV4ProjectsIdPagesRequest).toEntity(localVarReturnType);
    }

    /**
     * Update Pages settings for a project
     * Updates Pages settings for a specified project. You must have the Maintainer or Owner role for the project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param patchApiV4ProjectsIdPagesRequest The patchApiV4ProjectsIdPagesRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec patchApiV4ProjectsIdPagesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable PatchApiV4ProjectsIdPagesRequest patchApiV4ProjectsIdPagesRequest) throws RestClientResponseException {
        return patchApiV4ProjectsIdPagesRequestCreation(id, patchApiV4ProjectsIdPagesRequest);
    }

    /**
     * Create Pages domain
     * Creates a Pages domain in a specified project. You must have permissions to create Pages domains.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdPagesDomainsRequest The postApiV4ProjectsIdPagesDomainsRequest parameter
     * @return APIEntitiesPagesDomain
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdPagesDomainsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdPagesDomainsRequest postApiV4ProjectsIdPagesDomainsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdPagesDomains", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdPagesDomainsRequest' is set
        if (postApiV4ProjectsIdPagesDomainsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdPagesDomainsRequest' when calling postApiV4ProjectsIdPagesDomains", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesPagesDomain> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/pages/domains", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdPagesDomainsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create Pages domain
     * Creates a Pages domain in a specified project. You must have permissions to create Pages domains.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdPagesDomainsRequest The postApiV4ProjectsIdPagesDomainsRequest parameter
     * @return APIEntitiesPagesDomain
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPagesDomain postApiV4ProjectsIdPagesDomains(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdPagesDomainsRequest postApiV4ProjectsIdPagesDomainsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPagesDomain> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdPagesDomainsRequestCreation(id, postApiV4ProjectsIdPagesDomainsRequest).body(localVarReturnType);
    }

    /**
     * Create Pages domain
     * Creates a Pages domain in a specified project. You must have permissions to create Pages domains.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdPagesDomainsRequest The postApiV4ProjectsIdPagesDomainsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesPagesDomain&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPagesDomain> postApiV4ProjectsIdPagesDomainsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdPagesDomainsRequest postApiV4ProjectsIdPagesDomainsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPagesDomain> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdPagesDomainsRequestCreation(id, postApiV4ProjectsIdPagesDomainsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create Pages domain
     * Creates a Pages domain in a specified project. You must have permissions to create Pages domains.
     * <p><b>201</b> - Created
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param postApiV4ProjectsIdPagesDomainsRequest The postApiV4ProjectsIdPagesDomainsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdPagesDomainsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull PostApiV4ProjectsIdPagesDomainsRequest postApiV4ProjectsIdPagesDomainsRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdPagesDomainsRequestCreation(id, postApiV4ProjectsIdPagesDomainsRequest);
    }

    /**
     * Update Pages domain
     * Updates a specified Pages domain in a project. You must have permissions to change an existing Pages domain.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param domain Custom domain.
     * @param putApiV4ProjectsIdPagesDomainsDomainRequest The putApiV4ProjectsIdPagesDomainsDomainRequest parameter
     * @return APIEntitiesPagesDomain
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdPagesDomainsDomainRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String domain, @jakarta.annotation.Nullable PutApiV4ProjectsIdPagesDomainsDomainRequest putApiV4ProjectsIdPagesDomainsDomainRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdPagesDomainsDomain", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'domain' is set
        if (domain == null) {
            throw new RestClientResponseException("Missing the required parameter 'domain' when calling putApiV4ProjectsIdPagesDomainsDomain", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("domain", domain);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesPagesDomain> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/pages/domains/{domain}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdPagesDomainsDomainRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update Pages domain
     * Updates a specified Pages domain in a project. You must have permissions to change an existing Pages domain.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param domain Custom domain.
     * @param putApiV4ProjectsIdPagesDomainsDomainRequest The putApiV4ProjectsIdPagesDomainsDomainRequest parameter
     * @return APIEntitiesPagesDomain
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPagesDomain putApiV4ProjectsIdPagesDomainsDomain(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String domain, @jakarta.annotation.Nullable PutApiV4ProjectsIdPagesDomainsDomainRequest putApiV4ProjectsIdPagesDomainsDomainRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPagesDomain> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPagesDomainsDomainRequestCreation(id, domain, putApiV4ProjectsIdPagesDomainsDomainRequest).body(localVarReturnType);
    }

    /**
     * Update Pages domain
     * Updates a specified Pages domain in a project. You must have permissions to change an existing Pages domain.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param domain Custom domain.
     * @param putApiV4ProjectsIdPagesDomainsDomainRequest The putApiV4ProjectsIdPagesDomainsDomainRequest parameter
     * @return ResponseEntity&lt;APIEntitiesPagesDomain&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPagesDomain> putApiV4ProjectsIdPagesDomainsDomainWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String domain, @jakarta.annotation.Nullable PutApiV4ProjectsIdPagesDomainsDomainRequest putApiV4ProjectsIdPagesDomainsDomainRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPagesDomain> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPagesDomainsDomainRequestCreation(id, domain, putApiV4ProjectsIdPagesDomainsDomainRequest).toEntity(localVarReturnType);
    }

    /**
     * Update Pages domain
     * Updates a specified Pages domain in a project. You must have permissions to change an existing Pages domain.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param domain Custom domain.
     * @param putApiV4ProjectsIdPagesDomainsDomainRequest The putApiV4ProjectsIdPagesDomainsDomainRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdPagesDomainsDomainWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String domain, @jakarta.annotation.Nullable PutApiV4ProjectsIdPagesDomainsDomainRequest putApiV4ProjectsIdPagesDomainsDomainRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdPagesDomainsDomainRequestCreation(id, domain, putApiV4ProjectsIdPagesDomainsDomainRequest);
    }

    /**
     * Verify Pages domain
     * Verifies a specified Pages domain in a project. You must have permissions to update Pages domains. This feature was introduced in GitLab 17.7.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param domain Custom domain to verify.
     * @return APIEntitiesPagesDomain
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdPagesDomainsDomainVerifyRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String domain) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdPagesDomainsDomainVerify", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'domain' is set
        if (domain == null) {
            throw new RestClientResponseException("Missing the required parameter 'domain' when calling putApiV4ProjectsIdPagesDomainsDomainVerify", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("domain", domain);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesPagesDomain> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/pages/domains/{domain}/verify", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Verify Pages domain
     * Verifies a specified Pages domain in a project. You must have permissions to update Pages domains. This feature was introduced in GitLab 17.7.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param domain Custom domain to verify.
     * @return APIEntitiesPagesDomain
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesPagesDomain putApiV4ProjectsIdPagesDomainsDomainVerify(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String domain) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPagesDomain> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPagesDomainsDomainVerifyRequestCreation(id, domain).body(localVarReturnType);
    }

    /**
     * Verify Pages domain
     * Verifies a specified Pages domain in a project. You must have permissions to update Pages domains. This feature was introduced in GitLab 17.7.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param domain Custom domain to verify.
     * @return ResponseEntity&lt;APIEntitiesPagesDomain&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesPagesDomain> putApiV4ProjectsIdPagesDomainsDomainVerifyWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String domain) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesPagesDomain> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdPagesDomainsDomainVerifyRequestCreation(id, domain).toEntity(localVarReturnType);
    }

    /**
     * Verify Pages domain
     * Verifies a specified Pages domain in a project. You must have permissions to update Pages domains. This feature was introduced in GitLab 17.7.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param domain Custom domain to verify.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdPagesDomainsDomainVerifyWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String domain) throws RestClientResponseException {
        return putApiV4ProjectsIdPagesDomainsDomainVerifyRequestCreation(id, domain);
    }
}
