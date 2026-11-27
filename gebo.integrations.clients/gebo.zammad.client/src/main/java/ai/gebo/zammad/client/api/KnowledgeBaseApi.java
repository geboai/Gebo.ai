package ai.gebo.zammad.client.api;

import ai.gebo.zammad.client.invoker.ApiClient;

import ai.gebo.zammad.client.model.ErrorResponse;
import ai.gebo.zammad.client.model.KnowledgeBase;
import ai.gebo.zammad.client.model.KnowledgeBaseInitResult;
import ai.gebo.zammad.client.model.KnowledgeBasePermissions;
import ai.gebo.zammad.client.model.KnowledgeBasePermissionsUpdate;
import ai.gebo.zammad.client.model.KnowledgeBaseSearchRequest;
import ai.gebo.zammad.client.model.KnowledgeBaseSearchResult;
import ai.gebo.zammad.client.model.KnowledgeBaseSettingsUpdate;

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
public class KnowledgeBaseApi {
    private ApiClient apiClient;

    public KnowledgeBaseApi() {
        this(new ApiClient());
    }

    public KnowledgeBaseApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Show knowledge base
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return KnowledgeBase
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getKnowledgeBaseRequestCreation(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'knowledgeBaseId' is set
        if (knowledgeBaseId == null) {
            throw new RestClientResponseException("Missing the required parameter 'knowledgeBaseId' when calling getKnowledgeBase", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("knowledgeBaseId", knowledgeBaseId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (from != null) {
            headerParams.add("From", apiClient.parameterToString(from));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "zammadToken", "basicAuth", "oauth2Bearer" };

        ParameterizedTypeReference<KnowledgeBase> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/knowledge_bases/{knowledgeBaseId}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Show knowledge base
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return KnowledgeBase
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public KnowledgeBase getKnowledgeBase(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<KnowledgeBase> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getKnowledgeBaseRequestCreation(knowledgeBaseId, from).body(localVarReturnType);
    }

    /**
     * Show knowledge base
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;KnowledgeBase&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<KnowledgeBase> getKnowledgeBaseWithHttpInfo(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<KnowledgeBase> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getKnowledgeBaseRequestCreation(knowledgeBaseId, from).toEntity(localVarReturnType);
    }

    /**
     * Show knowledge base
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getKnowledgeBaseWithResponseSpec(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return getKnowledgeBaseRequestCreation(knowledgeBaseId, from);
    }

    /**
     * Show knowledge base permissions
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return KnowledgeBasePermissions
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getKnowledgeBasePermissionsRequestCreation(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'knowledgeBaseId' is set
        if (knowledgeBaseId == null) {
            throw new RestClientResponseException("Missing the required parameter 'knowledgeBaseId' when calling getKnowledgeBasePermissions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("knowledgeBaseId", knowledgeBaseId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (from != null) {
            headerParams.add("From", apiClient.parameterToString(from));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "zammadToken", "basicAuth", "oauth2Bearer" };

        ParameterizedTypeReference<KnowledgeBasePermissions> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/knowledge_bases/{knowledgeBaseId}/permissions", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Show knowledge base permissions
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return KnowledgeBasePermissions
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public KnowledgeBasePermissions getKnowledgeBasePermissions(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<KnowledgeBasePermissions> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getKnowledgeBasePermissionsRequestCreation(knowledgeBaseId, from).body(localVarReturnType);
    }

    /**
     * Show knowledge base permissions
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;KnowledgeBasePermissions&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<KnowledgeBasePermissions> getKnowledgeBasePermissionsWithHttpInfo(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<KnowledgeBasePermissions> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getKnowledgeBasePermissionsRequestCreation(knowledgeBaseId, from).toEntity(localVarReturnType);
    }

    /**
     * Show knowledge base permissions
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getKnowledgeBasePermissionsWithResponseSpec(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return getKnowledgeBasePermissionsRequestCreation(knowledgeBaseId, from);
    }

    /**
     * Load knowledge base overview
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param body The body parameter
     * @return KnowledgeBaseInitResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec initializeKnowledgeBaseOverviewRequestCreation(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (from != null) {
            headerParams.add("From", apiClient.parameterToString(from));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "zammadToken", "basicAuth", "oauth2Bearer" };

        ParameterizedTypeReference<KnowledgeBaseInitResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/knowledge_bases/init", HttpMethod.POST, pathParams, localVarQueryParams, body, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Load knowledge base overview
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param body The body parameter
     * @return KnowledgeBaseInitResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public KnowledgeBaseInitResult initializeKnowledgeBaseOverview(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        ParameterizedTypeReference<KnowledgeBaseInitResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return initializeKnowledgeBaseOverviewRequestCreation(from, body).body(localVarReturnType);
    }

    /**
     * Load knowledge base overview
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param body The body parameter
     * @return ResponseEntity&lt;KnowledgeBaseInitResult&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<KnowledgeBaseInitResult> initializeKnowledgeBaseOverviewWithHttpInfo(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        ParameterizedTypeReference<KnowledgeBaseInitResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return initializeKnowledgeBaseOverviewRequestCreation(from, body).toEntity(localVarReturnType);
    }

    /**
     * Load knowledge base overview
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param body The body parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec initializeKnowledgeBaseOverviewWithResponseSpec(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        return initializeKnowledgeBaseOverviewRequestCreation(from, body);
    }

    /**
     * Search the knowledge base
     * Search visible Knowledge Base translations. Authentication is optional; an authenticated user may see additional content according to their Knowledge Base permissions.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseSearchRequest The knowledgeBaseSearchRequest parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return KnowledgeBaseSearchResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec searchKnowledgeBaseRequestCreation(@jakarta.annotation.Nonnull KnowledgeBaseSearchRequest knowledgeBaseSearchRequest, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'knowledgeBaseSearchRequest' is set
        if (knowledgeBaseSearchRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'knowledgeBaseSearchRequest' when calling searchKnowledgeBase", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (from != null) {
            headerParams.add("From", apiClient.parameterToString(from));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "zammadToken", "basicAuth", "oauth2Bearer" };

        ParameterizedTypeReference<KnowledgeBaseSearchResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/knowledge_bases/search", HttpMethod.POST, pathParams, localVarQueryParams, knowledgeBaseSearchRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Search the knowledge base
     * Search visible Knowledge Base translations. Authentication is optional; an authenticated user may see additional content according to their Knowledge Base permissions.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseSearchRequest The knowledgeBaseSearchRequest parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return KnowledgeBaseSearchResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public KnowledgeBaseSearchResult searchKnowledgeBase(@jakarta.annotation.Nonnull KnowledgeBaseSearchRequest knowledgeBaseSearchRequest, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<KnowledgeBaseSearchResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return searchKnowledgeBaseRequestCreation(knowledgeBaseSearchRequest, from).body(localVarReturnType);
    }

    /**
     * Search the knowledge base
     * Search visible Knowledge Base translations. Authentication is optional; an authenticated user may see additional content according to their Knowledge Base permissions.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseSearchRequest The knowledgeBaseSearchRequest parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;KnowledgeBaseSearchResult&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<KnowledgeBaseSearchResult> searchKnowledgeBaseWithHttpInfo(@jakarta.annotation.Nonnull KnowledgeBaseSearchRequest knowledgeBaseSearchRequest, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<KnowledgeBaseSearchResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return searchKnowledgeBaseRequestCreation(knowledgeBaseSearchRequest, from).toEntity(localVarReturnType);
    }

    /**
     * Search the knowledge base
     * Search visible Knowledge Base translations. Authentication is optional; an authenticated user may see additional content according to their Knowledge Base permissions.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseSearchRequest The knowledgeBaseSearchRequest parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec searchKnowledgeBaseWithResponseSpec(@jakarta.annotation.Nonnull KnowledgeBaseSearchRequest knowledgeBaseSearchRequest, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return searchKnowledgeBaseRequestCreation(knowledgeBaseSearchRequest, from);
    }

    /**
     * Update knowledge base permissions
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param knowledgeBasePermissionsUpdate The knowledgeBasePermissionsUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return KnowledgeBasePermissions
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateKnowledgeBasePermissionsRequestCreation(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull KnowledgeBasePermissionsUpdate knowledgeBasePermissionsUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'knowledgeBaseId' is set
        if (knowledgeBaseId == null) {
            throw new RestClientResponseException("Missing the required parameter 'knowledgeBaseId' when calling updateKnowledgeBasePermissions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'knowledgeBasePermissionsUpdate' is set
        if (knowledgeBasePermissionsUpdate == null) {
            throw new RestClientResponseException("Missing the required parameter 'knowledgeBasePermissionsUpdate' when calling updateKnowledgeBasePermissions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("knowledgeBaseId", knowledgeBaseId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (from != null) {
            headerParams.add("From", apiClient.parameterToString(from));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "zammadToken", "basicAuth", "oauth2Bearer" };

        ParameterizedTypeReference<KnowledgeBasePermissions> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/knowledge_bases/{knowledgeBaseId}/permissions", HttpMethod.PUT, pathParams, localVarQueryParams, knowledgeBasePermissionsUpdate, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update knowledge base permissions
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param knowledgeBasePermissionsUpdate The knowledgeBasePermissionsUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return KnowledgeBasePermissions
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public KnowledgeBasePermissions updateKnowledgeBasePermissions(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull KnowledgeBasePermissionsUpdate knowledgeBasePermissionsUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<KnowledgeBasePermissions> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateKnowledgeBasePermissionsRequestCreation(knowledgeBaseId, knowledgeBasePermissionsUpdate, from).body(localVarReturnType);
    }

    /**
     * Update knowledge base permissions
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param knowledgeBasePermissionsUpdate The knowledgeBasePermissionsUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;KnowledgeBasePermissions&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<KnowledgeBasePermissions> updateKnowledgeBasePermissionsWithHttpInfo(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull KnowledgeBasePermissionsUpdate knowledgeBasePermissionsUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<KnowledgeBasePermissions> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateKnowledgeBasePermissionsRequestCreation(knowledgeBaseId, knowledgeBasePermissionsUpdate, from).toEntity(localVarReturnType);
    }

    /**
     * Update knowledge base permissions
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param knowledgeBasePermissionsUpdate The knowledgeBasePermissionsUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateKnowledgeBasePermissionsWithResponseSpec(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull KnowledgeBasePermissionsUpdate knowledgeBasePermissionsUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return updateKnowledgeBasePermissionsRequestCreation(knowledgeBaseId, knowledgeBasePermissionsUpdate, from);
    }

    /**
     * Update knowledge base settings
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param knowledgeBaseSettingsUpdate The knowledgeBaseSettingsUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return KnowledgeBase
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateKnowledgeBaseSettingsRequestCreation(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull KnowledgeBaseSettingsUpdate knowledgeBaseSettingsUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'knowledgeBaseId' is set
        if (knowledgeBaseId == null) {
            throw new RestClientResponseException("Missing the required parameter 'knowledgeBaseId' when calling updateKnowledgeBaseSettings", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'knowledgeBaseSettingsUpdate' is set
        if (knowledgeBaseSettingsUpdate == null) {
            throw new RestClientResponseException("Missing the required parameter 'knowledgeBaseSettingsUpdate' when calling updateKnowledgeBaseSettings", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("knowledgeBaseId", knowledgeBaseId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (from != null) {
            headerParams.add("From", apiClient.parameterToString(from));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "zammadToken", "basicAuth", "oauth2Bearer" };

        ParameterizedTypeReference<KnowledgeBase> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/knowledge_bases/manage/{knowledgeBaseId}", HttpMethod.PATCH, pathParams, localVarQueryParams, knowledgeBaseSettingsUpdate, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update knowledge base settings
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param knowledgeBaseSettingsUpdate The knowledgeBaseSettingsUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return KnowledgeBase
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public KnowledgeBase updateKnowledgeBaseSettings(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull KnowledgeBaseSettingsUpdate knowledgeBaseSettingsUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<KnowledgeBase> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateKnowledgeBaseSettingsRequestCreation(knowledgeBaseId, knowledgeBaseSettingsUpdate, from).body(localVarReturnType);
    }

    /**
     * Update knowledge base settings
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param knowledgeBaseSettingsUpdate The knowledgeBaseSettingsUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;KnowledgeBase&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<KnowledgeBase> updateKnowledgeBaseSettingsWithHttpInfo(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull KnowledgeBaseSettingsUpdate knowledgeBaseSettingsUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<KnowledgeBase> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateKnowledgeBaseSettingsRequestCreation(knowledgeBaseId, knowledgeBaseSettingsUpdate, from).toEntity(localVarReturnType);
    }

    /**
     * Update knowledge base settings
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param knowledgeBaseSettingsUpdate The knowledgeBaseSettingsUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateKnowledgeBaseSettingsWithResponseSpec(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull KnowledgeBaseSettingsUpdate knowledgeBaseSettingsUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return updateKnowledgeBaseSettingsRequestCreation(knowledgeBaseId, knowledgeBaseSettingsUpdate, from);
    }
}
