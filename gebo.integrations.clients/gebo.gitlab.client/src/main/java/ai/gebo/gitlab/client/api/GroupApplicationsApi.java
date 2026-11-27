package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesApplication;
import ai.gebo.gitlab.client.model.APIEntitiesApplicationWithSecret;
import ai.gebo.gitlab.client.model.PostApiV4GroupsIdApplicationsRequest;
import ai.gebo.gitlab.client.model.PutApiV4GroupsIdApplicationsApplicationIdRequest;

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
public class GroupApplicationsApi {
    private ApiClient apiClient;

    public GroupApplicationsApi() {
        this(new ApiClient());
    }

    public GroupApplicationsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a group application
     * Deletes a specified OAuth application owned by a group.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the group
     * @param applicationId ID of the application. Differs from the &#x60;application_id&#x60; in the response.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4GroupsIdApplicationsApplicationIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer applicationId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4GroupsIdApplicationsApplicationId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'applicationId' is set
        if (applicationId == null) {
            throw new RestClientResponseException("Missing the required parameter 'applicationId' when calling deleteApiV4GroupsIdApplicationsApplicationId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("application_id", applicationId);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/applications/{application_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a group application
     * Deletes a specified OAuth application owned by a group.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the group
     * @param applicationId ID of the application. Differs from the &#x60;application_id&#x60; in the response.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4GroupsIdApplicationsApplicationId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer applicationId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4GroupsIdApplicationsApplicationIdRequestCreation(id, applicationId).body(localVarReturnType);
    }

    /**
     * Delete a group application
     * Deletes a specified OAuth application owned by a group.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the group
     * @param applicationId ID of the application. Differs from the &#x60;application_id&#x60; in the response.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4GroupsIdApplicationsApplicationIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer applicationId) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4GroupsIdApplicationsApplicationIdRequestCreation(id, applicationId).toEntity(localVarReturnType);
    }

    /**
     * Delete a group application
     * Deletes a specified OAuth application owned by a group.
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the group
     * @param applicationId ID of the application. Differs from the &#x60;application_id&#x60; in the response.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4GroupsIdApplicationsApplicationIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer applicationId) throws RestClientResponseException {
        return deleteApiV4GroupsIdApplicationsApplicationIdRequestCreation(id, applicationId);
    }

    /**
     * List all group applications
     * Lists all OAuth applications owned by a specified group.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the group
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesApplication&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdApplicationsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdApplications", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesApplication>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/applications", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all group applications
     * Lists all OAuth applications owned by a specified group.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the group
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesApplication&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesApplication> getApiV4GroupsIdApplications(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesApplication>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdApplicationsRequestCreation(id, page, perPage).body(localVarReturnType);
    }

    /**
     * List all group applications
     * Lists all OAuth applications owned by a specified group.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the group
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesApplication&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesApplication>> getApiV4GroupsIdApplicationsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesApplication>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdApplicationsRequestCreation(id, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all group applications
     * Lists all OAuth applications owned by a specified group.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the group
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdApplicationsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4GroupsIdApplicationsRequestCreation(id, page, perPage);
    }

    /**
     * Retrieve a group application
     * Retrieves details of a specified OAuth application owned by a group.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the group
     * @param applicationId ID of the application. Differs from the &#x60;application_id&#x60; in the response.
     * @return APIEntitiesApplication
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdApplicationsApplicationIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer applicationId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdApplicationsApplicationId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'applicationId' is set
        if (applicationId == null) {
            throw new RestClientResponseException("Missing the required parameter 'applicationId' when calling getApiV4GroupsIdApplicationsApplicationId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("application_id", applicationId);

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

        ParameterizedTypeReference<APIEntitiesApplication> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/applications/{application_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a group application
     * Retrieves details of a specified OAuth application owned by a group.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the group
     * @param applicationId ID of the application. Differs from the &#x60;application_id&#x60; in the response.
     * @return APIEntitiesApplication
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesApplication getApiV4GroupsIdApplicationsApplicationId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer applicationId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesApplication> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdApplicationsApplicationIdRequestCreation(id, applicationId).body(localVarReturnType);
    }

    /**
     * Retrieve a group application
     * Retrieves details of a specified OAuth application owned by a group.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the group
     * @param applicationId ID of the application. Differs from the &#x60;application_id&#x60; in the response.
     * @return ResponseEntity&lt;APIEntitiesApplication&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesApplication> getApiV4GroupsIdApplicationsApplicationIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer applicationId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesApplication> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdApplicationsApplicationIdRequestCreation(id, applicationId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a group application
     * Retrieves details of a specified OAuth application owned by a group.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id The ID or URL-encoded path of the group
     * @param applicationId ID of the application. Differs from the &#x60;application_id&#x60; in the response.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdApplicationsApplicationIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer applicationId) throws RestClientResponseException {
        return getApiV4GroupsIdApplicationsApplicationIdRequestCreation(id, applicationId);
    }

    /**
     * Create a group application
     * Creates an OAuth application owned by the specified group. This feature was introduced in GitLab 19.6
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id The ID or URL-encoded path of the group
     * @param postApiV4GroupsIdApplicationsRequest The postApiV4GroupsIdApplicationsRequest parameter
     * @return APIEntitiesApplicationWithSecret
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4GroupsIdApplicationsRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdApplicationsRequest postApiV4GroupsIdApplicationsRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4GroupsIdApplications", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4GroupsIdApplicationsRequest' is set
        if (postApiV4GroupsIdApplicationsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4GroupsIdApplicationsRequest' when calling postApiV4GroupsIdApplications", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesApplicationWithSecret> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/applications", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4GroupsIdApplicationsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a group application
     * Creates an OAuth application owned by the specified group. This feature was introduced in GitLab 19.6
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id The ID or URL-encoded path of the group
     * @param postApiV4GroupsIdApplicationsRequest The postApiV4GroupsIdApplicationsRequest parameter
     * @return APIEntitiesApplicationWithSecret
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesApplicationWithSecret postApiV4GroupsIdApplications(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdApplicationsRequest postApiV4GroupsIdApplicationsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesApplicationWithSecret> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdApplicationsRequestCreation(id, postApiV4GroupsIdApplicationsRequest).body(localVarReturnType);
    }

    /**
     * Create a group application
     * Creates an OAuth application owned by the specified group. This feature was introduced in GitLab 19.6
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id The ID or URL-encoded path of the group
     * @param postApiV4GroupsIdApplicationsRequest The postApiV4GroupsIdApplicationsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesApplicationWithSecret&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesApplicationWithSecret> postApiV4GroupsIdApplicationsWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdApplicationsRequest postApiV4GroupsIdApplicationsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesApplicationWithSecret> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4GroupsIdApplicationsRequestCreation(id, postApiV4GroupsIdApplicationsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a group application
     * Creates an OAuth application owned by the specified group. This feature was introduced in GitLab 19.6
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id The ID or URL-encoded path of the group
     * @param postApiV4GroupsIdApplicationsRequest The postApiV4GroupsIdApplicationsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4GroupsIdApplicationsWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull PostApiV4GroupsIdApplicationsRequest postApiV4GroupsIdApplicationsRequest) throws RestClientResponseException {
        return postApiV4GroupsIdApplicationsRequestCreation(id, postApiV4GroupsIdApplicationsRequest);
    }

    /**
     * Update a group application
     * Updates a specified OAuth application owned by a group. You cannot update the &#x60;redirect_uri&#x60; and &#x60;confidential&#x60; attributes of an existing application. To change them, delete the application and create it again.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id The ID or URL-encoded path of the group
     * @param applicationId ID of the application. Differs from the &#x60;application_id&#x60; in the response.
     * @param putApiV4GroupsIdApplicationsApplicationIdRequest The putApiV4GroupsIdApplicationsApplicationIdRequest parameter
     * @return APIEntitiesApplication
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4GroupsIdApplicationsApplicationIdRequestCreation(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer applicationId, @jakarta.annotation.Nullable PutApiV4GroupsIdApplicationsApplicationIdRequest putApiV4GroupsIdApplicationsApplicationIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4GroupsIdApplicationsApplicationId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'applicationId' is set
        if (applicationId == null) {
            throw new RestClientResponseException("Missing the required parameter 'applicationId' when calling putApiV4GroupsIdApplicationsApplicationId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("application_id", applicationId);

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

        ParameterizedTypeReference<APIEntitiesApplication> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/groups/{id}/applications/{application_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4GroupsIdApplicationsApplicationIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a group application
     * Updates a specified OAuth application owned by a group. You cannot update the &#x60;redirect_uri&#x60; and &#x60;confidential&#x60; attributes of an existing application. To change them, delete the application and create it again.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id The ID or URL-encoded path of the group
     * @param applicationId ID of the application. Differs from the &#x60;application_id&#x60; in the response.
     * @param putApiV4GroupsIdApplicationsApplicationIdRequest The putApiV4GroupsIdApplicationsApplicationIdRequest parameter
     * @return APIEntitiesApplication
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesApplication putApiV4GroupsIdApplicationsApplicationId(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer applicationId, @jakarta.annotation.Nullable PutApiV4GroupsIdApplicationsApplicationIdRequest putApiV4GroupsIdApplicationsApplicationIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesApplication> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdApplicationsApplicationIdRequestCreation(id, applicationId, putApiV4GroupsIdApplicationsApplicationIdRequest).body(localVarReturnType);
    }

    /**
     * Update a group application
     * Updates a specified OAuth application owned by a group. You cannot update the &#x60;redirect_uri&#x60; and &#x60;confidential&#x60; attributes of an existing application. To change them, delete the application and create it again.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id The ID or URL-encoded path of the group
     * @param applicationId ID of the application. Differs from the &#x60;application_id&#x60; in the response.
     * @param putApiV4GroupsIdApplicationsApplicationIdRequest The putApiV4GroupsIdApplicationsApplicationIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesApplication&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesApplication> putApiV4GroupsIdApplicationsApplicationIdWithHttpInfo(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer applicationId, @jakarta.annotation.Nullable PutApiV4GroupsIdApplicationsApplicationIdRequest putApiV4GroupsIdApplicationsApplicationIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesApplication> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4GroupsIdApplicationsApplicationIdRequestCreation(id, applicationId, putApiV4GroupsIdApplicationsApplicationIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a group application
     * Updates a specified OAuth application owned by a group. You cannot update the &#x60;redirect_uri&#x60; and &#x60;confidential&#x60; attributes of an existing application. To change them, delete the application and create it again.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id The ID or URL-encoded path of the group
     * @param applicationId ID of the application. Differs from the &#x60;application_id&#x60; in the response.
     * @param putApiV4GroupsIdApplicationsApplicationIdRequest The putApiV4GroupsIdApplicationsApplicationIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4GroupsIdApplicationsApplicationIdWithResponseSpec(@jakarta.annotation.Nonnull String id, @jakarta.annotation.Nonnull Integer applicationId, @jakarta.annotation.Nullable PutApiV4GroupsIdApplicationsApplicationIdRequest putApiV4GroupsIdApplicationsApplicationIdRequest) throws RestClientResponseException {
        return putApiV4GroupsIdApplicationsApplicationIdRequestCreation(id, applicationId, putApiV4GroupsIdApplicationsApplicationIdRequest);
    }
}
