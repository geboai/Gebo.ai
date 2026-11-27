package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesApplication;
import ai.gebo.gitlab.client.model.APIEntitiesApplicationWithSecret;
import ai.gebo.gitlab.client.model.PostApiV4ApplicationsRequest;
import ai.gebo.gitlab.client.model.PostApiV4UserApplicationsRequest;
import ai.gebo.gitlab.client.model.PutApiV4UserApplicationsIdRequest;

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
public class ApplicationsApi {
    private ApiClient apiClient;

    public ApplicationsApi() {
        this(new ApiClient());
    }

    public ApplicationsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete an application
     * Delete a specific application
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the application. Not the &#x60;application_id&#x60;, which is the OAuth client ID.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ApplicationsIdRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ApplicationsId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/applications/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete an application
     * Delete a specific application
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the application. Not the &#x60;application_id&#x60;, which is the OAuth client ID.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ApplicationsId(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ApplicationsIdRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Delete an application
     * Delete a specific application
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the application. Not the &#x60;application_id&#x60;, which is the OAuth client ID.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ApplicationsIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ApplicationsIdRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Delete an application
     * Delete a specific application
     * <p><b>204</b> - No Content
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the application. Not the &#x60;application_id&#x60;, which is the OAuth client ID.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ApplicationsIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return deleteApiV4ApplicationsIdRequestCreation(id);
    }

    /**
     * Delete an application
     * Deletes a specified application owned by the authenticated user.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the application. Differs from &#x60;application_id&#x60;.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4UserApplicationsIdRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4UserApplicationsId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/user/applications/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete an application
     * Deletes a specified application owned by the authenticated user.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the application. Differs from &#x60;application_id&#x60;.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4UserApplicationsId(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4UserApplicationsIdRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Delete an application
     * Deletes a specified application owned by the authenticated user.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the application. Differs from &#x60;application_id&#x60;.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4UserApplicationsIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4UserApplicationsIdRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Delete an application
     * Deletes a specified application owned by the authenticated user.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the application. Differs from &#x60;application_id&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4UserApplicationsIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return deleteApiV4UserApplicationsIdRequestCreation(id);
    }

    /**
     * Get applications
     * List all registered applications. The &#x60;secret&#x60; value is not returned.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return List&lt;APIEntitiesApplication&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ApplicationsRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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

        ParameterizedTypeReference<List<APIEntitiesApplication>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/applications", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get applications
     * List all registered applications. The &#x60;secret&#x60; value is not returned.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return List&lt;APIEntitiesApplication&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesApplication> getApiV4Applications() throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesApplication>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ApplicationsRequestCreation().body(localVarReturnType);
    }

    /**
     * Get applications
     * List all registered applications. The &#x60;secret&#x60; value is not returned.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return ResponseEntity&lt;List&lt;APIEntitiesApplication&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesApplication>> getApiV4ApplicationsWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesApplication>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ApplicationsRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Get applications
     * List all registered applications. The &#x60;secret&#x60; value is not returned.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ApplicationsWithResponseSpec() throws RestClientResponseException {
        return getApiV4ApplicationsRequestCreation();
    }

    /**
     * List all applications
     * Lists all applications owned by the authenticated user.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return List&lt;APIEntitiesApplication&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4UserApplicationsRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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

        ParameterizedTypeReference<List<APIEntitiesApplication>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/user/applications", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all applications
     * Lists all applications owned by the authenticated user.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return List&lt;APIEntitiesApplication&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesApplication> getApiV4UserApplications() throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesApplication>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UserApplicationsRequestCreation().body(localVarReturnType);
    }

    /**
     * List all applications
     * Lists all applications owned by the authenticated user.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return ResponseEntity&lt;List&lt;APIEntitiesApplication&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesApplication>> getApiV4UserApplicationsWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesApplication>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UserApplicationsRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * List all applications
     * Lists all applications owned by the authenticated user.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4UserApplicationsWithResponseSpec() throws RestClientResponseException {
        return getApiV4UserApplicationsRequestCreation();
    }

    /**
     * Retrieve an application
     * Retrieves details of a specific application owned by the authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the application. Differs from &#x60;application_id&#x60;.
     * @return APIEntitiesApplication
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4UserApplicationsIdRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4UserApplicationsId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesApplication> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/user/applications/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve an application
     * Retrieves details of a specific application owned by the authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the application. Differs from &#x60;application_id&#x60;.
     * @return APIEntitiesApplication
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesApplication getApiV4UserApplicationsId(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesApplication> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UserApplicationsIdRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve an application
     * Retrieves details of a specific application owned by the authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the application. Differs from &#x60;application_id&#x60;.
     * @return ResponseEntity&lt;APIEntitiesApplication&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesApplication> getApiV4UserApplicationsIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesApplication> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4UserApplicationsIdRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve an application
     * Retrieves details of a specific application owned by the authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the application. Differs from &#x60;application_id&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4UserApplicationsIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getApiV4UserApplicationsIdRequestCreation(id);
    }

    /**
     * Create a new application
     * Creates an application. Declaring granular scopes records the fine-grained permissions of the application, as a set of access levels and permissions. GitLab creates the application and stores this set in one step. If GitLab rejects the set, it does not create the application. While an application has granular scopes, GitLab adds the &#x60;granular&#x60; scope to its &#x60;scopes&#x60;. The granular_scopes parameter requires the granular_oauth_tokens feature flag.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param postApiV4ApplicationsRequest The postApiV4ApplicationsRequest parameter
     * @return APIEntitiesApplicationWithSecret
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ApplicationsRequestCreation(@jakarta.annotation.Nonnull PostApiV4ApplicationsRequest postApiV4ApplicationsRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4ApplicationsRequest' is set
        if (postApiV4ApplicationsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ApplicationsRequest' when calling postApiV4Applications", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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
        return apiClient.invokeAPI("/api/v4/applications", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ApplicationsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a new application
     * Creates an application. Declaring granular scopes records the fine-grained permissions of the application, as a set of access levels and permissions. GitLab creates the application and stores this set in one step. If GitLab rejects the set, it does not create the application. While an application has granular scopes, GitLab adds the &#x60;granular&#x60; scope to its &#x60;scopes&#x60;. The granular_scopes parameter requires the granular_oauth_tokens feature flag.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param postApiV4ApplicationsRequest The postApiV4ApplicationsRequest parameter
     * @return APIEntitiesApplicationWithSecret
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesApplicationWithSecret postApiV4Applications(@jakarta.annotation.Nonnull PostApiV4ApplicationsRequest postApiV4ApplicationsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesApplicationWithSecret> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ApplicationsRequestCreation(postApiV4ApplicationsRequest).body(localVarReturnType);
    }

    /**
     * Create a new application
     * Creates an application. Declaring granular scopes records the fine-grained permissions of the application, as a set of access levels and permissions. GitLab creates the application and stores this set in one step. If GitLab rejects the set, it does not create the application. While an application has granular scopes, GitLab adds the &#x60;granular&#x60; scope to its &#x60;scopes&#x60;. The granular_scopes parameter requires the granular_oauth_tokens feature flag.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param postApiV4ApplicationsRequest The postApiV4ApplicationsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesApplicationWithSecret&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesApplicationWithSecret> postApiV4ApplicationsWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4ApplicationsRequest postApiV4ApplicationsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesApplicationWithSecret> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ApplicationsRequestCreation(postApiV4ApplicationsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a new application
     * Creates an application. Declaring granular scopes records the fine-grained permissions of the application, as a set of access levels and permissions. GitLab creates the application and stores this set in one step. If GitLab rejects the set, it does not create the application. While an application has granular scopes, GitLab adds the &#x60;granular&#x60; scope to its &#x60;scopes&#x60;. The granular_scopes parameter requires the granular_oauth_tokens feature flag.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param postApiV4ApplicationsRequest The postApiV4ApplicationsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ApplicationsWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4ApplicationsRequest postApiV4ApplicationsRequest) throws RestClientResponseException {
        return postApiV4ApplicationsRequestCreation(postApiV4ApplicationsRequest);
    }

    /**
     * Renew an application secret
     * Renew the secret of a specific application
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the application. Not the &#x60;application_id&#x60;, which is the OAuth client ID.
     * @return APIEntitiesApplicationWithSecret
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ApplicationsIdRenewSecretRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ApplicationsIdRenewSecret", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesApplicationWithSecret> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/applications/{id}/renew-secret", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Renew an application secret
     * Renew the secret of a specific application
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the application. Not the &#x60;application_id&#x60;, which is the OAuth client ID.
     * @return APIEntitiesApplicationWithSecret
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesApplicationWithSecret postApiV4ApplicationsIdRenewSecret(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesApplicationWithSecret> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ApplicationsIdRenewSecretRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Renew an application secret
     * Renew the secret of a specific application
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the application. Not the &#x60;application_id&#x60;, which is the OAuth client ID.
     * @return ResponseEntity&lt;APIEntitiesApplicationWithSecret&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesApplicationWithSecret> postApiV4ApplicationsIdRenewSecretWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesApplicationWithSecret> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ApplicationsIdRenewSecretRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Renew an application secret
     * Renew the secret of a specific application
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID of the application. Not the &#x60;application_id&#x60;, which is the OAuth client ID.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ApplicationsIdRenewSecretWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return postApiV4ApplicationsIdRenewSecretRequestCreation(id);
    }

    /**
     * Create an application
     * Creates a new OAuth application for the authenticated user. This feature was introduced in GitLab 19.0
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4UserApplicationsRequest The postApiV4UserApplicationsRequest parameter
     * @return APIEntitiesApplicationWithSecret
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4UserApplicationsRequestCreation(@jakarta.annotation.Nonnull PostApiV4UserApplicationsRequest postApiV4UserApplicationsRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4UserApplicationsRequest' is set
        if (postApiV4UserApplicationsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4UserApplicationsRequest' when calling postApiV4UserApplications", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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
        return apiClient.invokeAPI("/api/v4/user/applications", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4UserApplicationsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create an application
     * Creates a new OAuth application for the authenticated user. This feature was introduced in GitLab 19.0
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4UserApplicationsRequest The postApiV4UserApplicationsRequest parameter
     * @return APIEntitiesApplicationWithSecret
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesApplicationWithSecret postApiV4UserApplications(@jakarta.annotation.Nonnull PostApiV4UserApplicationsRequest postApiV4UserApplicationsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesApplicationWithSecret> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UserApplicationsRequestCreation(postApiV4UserApplicationsRequest).body(localVarReturnType);
    }

    /**
     * Create an application
     * Creates a new OAuth application for the authenticated user. This feature was introduced in GitLab 19.0
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4UserApplicationsRequest The postApiV4UserApplicationsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesApplicationWithSecret&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesApplicationWithSecret> postApiV4UserApplicationsWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4UserApplicationsRequest postApiV4UserApplicationsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesApplicationWithSecret> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4UserApplicationsRequestCreation(postApiV4UserApplicationsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create an application
     * Creates a new OAuth application for the authenticated user. This feature was introduced in GitLab 19.0
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * @param postApiV4UserApplicationsRequest The postApiV4UserApplicationsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4UserApplicationsWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4UserApplicationsRequest postApiV4UserApplicationsRequest) throws RestClientResponseException {
        return postApiV4UserApplicationsRequestCreation(postApiV4UserApplicationsRequest);
    }

    /**
     * Update an application
     * Updates an existing application owned by the authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the application. Differs from &#x60;application_id&#x60;.
     * @param putApiV4UserApplicationsIdRequest The putApiV4UserApplicationsIdRequest parameter
     * @return APIEntitiesApplication
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4UserApplicationsIdRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4UserApplicationsIdRequest putApiV4UserApplicationsIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4UserApplicationsId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesApplication> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/user/applications/{id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4UserApplicationsIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update an application
     * Updates an existing application owned by the authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the application. Differs from &#x60;application_id&#x60;.
     * @param putApiV4UserApplicationsIdRequest The putApiV4UserApplicationsIdRequest parameter
     * @return APIEntitiesApplication
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesApplication putApiV4UserApplicationsId(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4UserApplicationsIdRequest putApiV4UserApplicationsIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesApplication> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4UserApplicationsIdRequestCreation(id, putApiV4UserApplicationsIdRequest).body(localVarReturnType);
    }

    /**
     * Update an application
     * Updates an existing application owned by the authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the application. Differs from &#x60;application_id&#x60;.
     * @param putApiV4UserApplicationsIdRequest The putApiV4UserApplicationsIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesApplication&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesApplication> putApiV4UserApplicationsIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4UserApplicationsIdRequest putApiV4UserApplicationsIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesApplication> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4UserApplicationsIdRequestCreation(id, putApiV4UserApplicationsIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update an application
     * Updates an existing application owned by the authenticated user.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the application. Differs from &#x60;application_id&#x60;.
     * @param putApiV4UserApplicationsIdRequest The putApiV4UserApplicationsIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4UserApplicationsIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4UserApplicationsIdRequest putApiV4UserApplicationsIdRequest) throws RestClientResponseException {
        return putApiV4UserApplicationsIdRequestCreation(id, putApiV4UserApplicationsIdRequest);
    }
}
