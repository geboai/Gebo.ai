package ai.gebo.zammad.client.api;

import ai.gebo.zammad.client.invoker.ApiClient;

import ai.gebo.zammad.client.model.ErrorResponse;
import ai.gebo.zammad.client.model.KnowledgeBaseCategory;
import ai.gebo.zammad.client.model.KnowledgeBaseCategoryCreate;
import ai.gebo.zammad.client.model.KnowledgeBaseCategoryUpdate;
import ai.gebo.zammad.client.model.KnowledgeBasePermissions;
import ai.gebo.zammad.client.model.KnowledgeBasePermissionsUpdate;
import ai.gebo.zammad.client.model.KnowledgeBaseReorder;

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
public class KnowledgeBaseCategoriesApi {
    private ApiClient apiClient;

    public KnowledgeBaseCategoriesApi() {
        this(new ApiClient());
    }

    public KnowledgeBaseCategoriesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create knowledge base category
     * 
     * <p><b>201</b> - Knowledge Base category created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param knowledgeBaseCategoryCreate The knowledgeBaseCategoryCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return KnowledgeBaseCategory
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createKnowledgeBaseCategoryRequestCreation(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull KnowledgeBaseCategoryCreate knowledgeBaseCategoryCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'knowledgeBaseId' is set
        if (knowledgeBaseId == null) {
            throw new RestClientResponseException("Missing the required parameter 'knowledgeBaseId' when calling createKnowledgeBaseCategory", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'knowledgeBaseCategoryCreate' is set
        if (knowledgeBaseCategoryCreate == null) {
            throw new RestClientResponseException("Missing the required parameter 'knowledgeBaseCategoryCreate' when calling createKnowledgeBaseCategory", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<KnowledgeBaseCategory> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/knowledge_bases/{knowledgeBaseId}/categories", HttpMethod.POST, pathParams, localVarQueryParams, knowledgeBaseCategoryCreate, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create knowledge base category
     * 
     * <p><b>201</b> - Knowledge Base category created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param knowledgeBaseCategoryCreate The knowledgeBaseCategoryCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return KnowledgeBaseCategory
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public KnowledgeBaseCategory createKnowledgeBaseCategory(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull KnowledgeBaseCategoryCreate knowledgeBaseCategoryCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<KnowledgeBaseCategory> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createKnowledgeBaseCategoryRequestCreation(knowledgeBaseId, knowledgeBaseCategoryCreate, from).body(localVarReturnType);
    }

    /**
     * Create knowledge base category
     * 
     * <p><b>201</b> - Knowledge Base category created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param knowledgeBaseCategoryCreate The knowledgeBaseCategoryCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;KnowledgeBaseCategory&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<KnowledgeBaseCategory> createKnowledgeBaseCategoryWithHttpInfo(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull KnowledgeBaseCategoryCreate knowledgeBaseCategoryCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<KnowledgeBaseCategory> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createKnowledgeBaseCategoryRequestCreation(knowledgeBaseId, knowledgeBaseCategoryCreate, from).toEntity(localVarReturnType);
    }

    /**
     * Create knowledge base category
     * 
     * <p><b>201</b> - Knowledge Base category created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param knowledgeBaseCategoryCreate The knowledgeBaseCategoryCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createKnowledgeBaseCategoryWithResponseSpec(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull KnowledgeBaseCategoryCreate knowledgeBaseCategoryCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return createKnowledgeBaseCategoryRequestCreation(knowledgeBaseId, knowledgeBaseCategoryCreate, from);
    }

    /**
     * Delete knowledge base category
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param categoryId Knowledge Base category ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteKnowledgeBaseCategoryRequestCreation(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long categoryId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'knowledgeBaseId' is set
        if (knowledgeBaseId == null) {
            throw new RestClientResponseException("Missing the required parameter 'knowledgeBaseId' when calling deleteKnowledgeBaseCategory", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'categoryId' is set
        if (categoryId == null) {
            throw new RestClientResponseException("Missing the required parameter 'categoryId' when calling deleteKnowledgeBaseCategory", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("knowledgeBaseId", knowledgeBaseId);
        pathParams.put("categoryId", categoryId);

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

        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/knowledge_bases/{knowledgeBaseId}/categories/{categoryId}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete knowledge base category
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param categoryId Knowledge Base category ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object deleteKnowledgeBaseCategory(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long categoryId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteKnowledgeBaseCategoryRequestCreation(knowledgeBaseId, categoryId, from).body(localVarReturnType);
    }

    /**
     * Delete knowledge base category
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param categoryId Knowledge Base category ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> deleteKnowledgeBaseCategoryWithHttpInfo(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long categoryId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteKnowledgeBaseCategoryRequestCreation(knowledgeBaseId, categoryId, from).toEntity(localVarReturnType);
    }

    /**
     * Delete knowledge base category
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param categoryId Knowledge Base category ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteKnowledgeBaseCategoryWithResponseSpec(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long categoryId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return deleteKnowledgeBaseCategoryRequestCreation(knowledgeBaseId, categoryId, from);
    }

    /**
     * Show knowledge base category
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param categoryId Knowledge Base category ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return KnowledgeBaseCategory
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getKnowledgeBaseCategoryRequestCreation(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long categoryId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'knowledgeBaseId' is set
        if (knowledgeBaseId == null) {
            throw new RestClientResponseException("Missing the required parameter 'knowledgeBaseId' when calling getKnowledgeBaseCategory", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'categoryId' is set
        if (categoryId == null) {
            throw new RestClientResponseException("Missing the required parameter 'categoryId' when calling getKnowledgeBaseCategory", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("knowledgeBaseId", knowledgeBaseId);
        pathParams.put("categoryId", categoryId);

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

        ParameterizedTypeReference<KnowledgeBaseCategory> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/knowledge_bases/{knowledgeBaseId}/categories/{categoryId}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Show knowledge base category
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param categoryId Knowledge Base category ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return KnowledgeBaseCategory
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public KnowledgeBaseCategory getKnowledgeBaseCategory(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long categoryId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<KnowledgeBaseCategory> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getKnowledgeBaseCategoryRequestCreation(knowledgeBaseId, categoryId, from).body(localVarReturnType);
    }

    /**
     * Show knowledge base category
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param categoryId Knowledge Base category ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;KnowledgeBaseCategory&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<KnowledgeBaseCategory> getKnowledgeBaseCategoryWithHttpInfo(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long categoryId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<KnowledgeBaseCategory> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getKnowledgeBaseCategoryRequestCreation(knowledgeBaseId, categoryId, from).toEntity(localVarReturnType);
    }

    /**
     * Show knowledge base category
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param categoryId Knowledge Base category ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getKnowledgeBaseCategoryWithResponseSpec(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long categoryId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return getKnowledgeBaseCategoryRequestCreation(knowledgeBaseId, categoryId, from);
    }

    /**
     * Show category permissions
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param categoryId Knowledge Base category ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return KnowledgeBasePermissions
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getKnowledgeBaseCategoryPermissionsRequestCreation(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long categoryId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'knowledgeBaseId' is set
        if (knowledgeBaseId == null) {
            throw new RestClientResponseException("Missing the required parameter 'knowledgeBaseId' when calling getKnowledgeBaseCategoryPermissions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'categoryId' is set
        if (categoryId == null) {
            throw new RestClientResponseException("Missing the required parameter 'categoryId' when calling getKnowledgeBaseCategoryPermissions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("knowledgeBaseId", knowledgeBaseId);
        pathParams.put("categoryId", categoryId);

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
        return apiClient.invokeAPI("/api/v1/knowledge_bases/{knowledgeBaseId}/categories/{categoryId}/permissions", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Show category permissions
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param categoryId Knowledge Base category ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return KnowledgeBasePermissions
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public KnowledgeBasePermissions getKnowledgeBaseCategoryPermissions(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long categoryId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<KnowledgeBasePermissions> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getKnowledgeBaseCategoryPermissionsRequestCreation(knowledgeBaseId, categoryId, from).body(localVarReturnType);
    }

    /**
     * Show category permissions
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param categoryId Knowledge Base category ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;KnowledgeBasePermissions&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<KnowledgeBasePermissions> getKnowledgeBaseCategoryPermissionsWithHttpInfo(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long categoryId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<KnowledgeBasePermissions> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getKnowledgeBaseCategoryPermissionsRequestCreation(knowledgeBaseId, categoryId, from).toEntity(localVarReturnType);
    }

    /**
     * Show category permissions
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param categoryId Knowledge Base category ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getKnowledgeBaseCategoryPermissionsWithResponseSpec(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long categoryId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return getKnowledgeBaseCategoryPermissionsRequestCreation(knowledgeBaseId, categoryId, from);
    }

    /**
     * Reorder category answers
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param categoryId Knowledge Base category ID.
     * @param knowledgeBaseReorder The knowledgeBaseReorder parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Map&lt;String, Map&lt;String, Object&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec reorderKnowledgeBaseAnswersRequestCreation(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long categoryId, @jakarta.annotation.Nonnull KnowledgeBaseReorder knowledgeBaseReorder, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'knowledgeBaseId' is set
        if (knowledgeBaseId == null) {
            throw new RestClientResponseException("Missing the required parameter 'knowledgeBaseId' when calling reorderKnowledgeBaseAnswers", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'categoryId' is set
        if (categoryId == null) {
            throw new RestClientResponseException("Missing the required parameter 'categoryId' when calling reorderKnowledgeBaseAnswers", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'knowledgeBaseReorder' is set
        if (knowledgeBaseReorder == null) {
            throw new RestClientResponseException("Missing the required parameter 'knowledgeBaseReorder' when calling reorderKnowledgeBaseAnswers", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("knowledgeBaseId", knowledgeBaseId);
        pathParams.put("categoryId", categoryId);

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

        ParameterizedTypeReference<Map<String, Map<String, Object>>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/knowledge_bases/{knowledgeBaseId}/categories/{categoryId}/reorder_answers", HttpMethod.PATCH, pathParams, localVarQueryParams, knowledgeBaseReorder, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Reorder category answers
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param categoryId Knowledge Base category ID.
     * @param knowledgeBaseReorder The knowledgeBaseReorder parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Map&lt;String, Map&lt;String, Object&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Map<String, Map<String, Object>> reorderKnowledgeBaseAnswers(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long categoryId, @jakarta.annotation.Nonnull KnowledgeBaseReorder knowledgeBaseReorder, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Map<String, Map<String, Object>>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return reorderKnowledgeBaseAnswersRequestCreation(knowledgeBaseId, categoryId, knowledgeBaseReorder, from).body(localVarReturnType);
    }

    /**
     * Reorder category answers
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param categoryId Knowledge Base category ID.
     * @param knowledgeBaseReorder The knowledgeBaseReorder parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;Map&lt;String, Map&lt;String, Object&gt;&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Map<String, Map<String, Object>>> reorderKnowledgeBaseAnswersWithHttpInfo(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long categoryId, @jakarta.annotation.Nonnull KnowledgeBaseReorder knowledgeBaseReorder, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Map<String, Map<String, Object>>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return reorderKnowledgeBaseAnswersRequestCreation(knowledgeBaseId, categoryId, knowledgeBaseReorder, from).toEntity(localVarReturnType);
    }

    /**
     * Reorder category answers
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param categoryId Knowledge Base category ID.
     * @param knowledgeBaseReorder The knowledgeBaseReorder parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec reorderKnowledgeBaseAnswersWithResponseSpec(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long categoryId, @jakarta.annotation.Nonnull KnowledgeBaseReorder knowledgeBaseReorder, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return reorderKnowledgeBaseAnswersRequestCreation(knowledgeBaseId, categoryId, knowledgeBaseReorder, from);
    }

    /**
     * Reorder root categories
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param knowledgeBaseReorder The knowledgeBaseReorder parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Map&lt;String, Map&lt;String, Object&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec reorderKnowledgeBaseRootCategoriesRequestCreation(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull KnowledgeBaseReorder knowledgeBaseReorder, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'knowledgeBaseId' is set
        if (knowledgeBaseId == null) {
            throw new RestClientResponseException("Missing the required parameter 'knowledgeBaseId' when calling reorderKnowledgeBaseRootCategories", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'knowledgeBaseReorder' is set
        if (knowledgeBaseReorder == null) {
            throw new RestClientResponseException("Missing the required parameter 'knowledgeBaseReorder' when calling reorderKnowledgeBaseRootCategories", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<Map<String, Map<String, Object>>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/knowledge_bases/{knowledgeBaseId}/categories/reorder_root_categories", HttpMethod.PATCH, pathParams, localVarQueryParams, knowledgeBaseReorder, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Reorder root categories
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param knowledgeBaseReorder The knowledgeBaseReorder parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Map&lt;String, Map&lt;String, Object&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Map<String, Map<String, Object>> reorderKnowledgeBaseRootCategories(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull KnowledgeBaseReorder knowledgeBaseReorder, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Map<String, Map<String, Object>>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return reorderKnowledgeBaseRootCategoriesRequestCreation(knowledgeBaseId, knowledgeBaseReorder, from).body(localVarReturnType);
    }

    /**
     * Reorder root categories
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param knowledgeBaseReorder The knowledgeBaseReorder parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;Map&lt;String, Map&lt;String, Object&gt;&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Map<String, Map<String, Object>>> reorderKnowledgeBaseRootCategoriesWithHttpInfo(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull KnowledgeBaseReorder knowledgeBaseReorder, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Map<String, Map<String, Object>>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return reorderKnowledgeBaseRootCategoriesRequestCreation(knowledgeBaseId, knowledgeBaseReorder, from).toEntity(localVarReturnType);
    }

    /**
     * Reorder root categories
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param knowledgeBaseReorder The knowledgeBaseReorder parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec reorderKnowledgeBaseRootCategoriesWithResponseSpec(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull KnowledgeBaseReorder knowledgeBaseReorder, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return reorderKnowledgeBaseRootCategoriesRequestCreation(knowledgeBaseId, knowledgeBaseReorder, from);
    }

    /**
     * Reorder subcategories
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param categoryId Knowledge Base category ID.
     * @param knowledgeBaseReorder The knowledgeBaseReorder parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Map&lt;String, Map&lt;String, Object&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec reorderKnowledgeBaseSubcategoriesRequestCreation(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long categoryId, @jakarta.annotation.Nonnull KnowledgeBaseReorder knowledgeBaseReorder, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'knowledgeBaseId' is set
        if (knowledgeBaseId == null) {
            throw new RestClientResponseException("Missing the required parameter 'knowledgeBaseId' when calling reorderKnowledgeBaseSubcategories", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'categoryId' is set
        if (categoryId == null) {
            throw new RestClientResponseException("Missing the required parameter 'categoryId' when calling reorderKnowledgeBaseSubcategories", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'knowledgeBaseReorder' is set
        if (knowledgeBaseReorder == null) {
            throw new RestClientResponseException("Missing the required parameter 'knowledgeBaseReorder' when calling reorderKnowledgeBaseSubcategories", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("knowledgeBaseId", knowledgeBaseId);
        pathParams.put("categoryId", categoryId);

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

        ParameterizedTypeReference<Map<String, Map<String, Object>>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/knowledge_bases/{knowledgeBaseId}/categories/{categoryId}/reorder_categories", HttpMethod.PATCH, pathParams, localVarQueryParams, knowledgeBaseReorder, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Reorder subcategories
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param categoryId Knowledge Base category ID.
     * @param knowledgeBaseReorder The knowledgeBaseReorder parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Map&lt;String, Map&lt;String, Object&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Map<String, Map<String, Object>> reorderKnowledgeBaseSubcategories(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long categoryId, @jakarta.annotation.Nonnull KnowledgeBaseReorder knowledgeBaseReorder, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Map<String, Map<String, Object>>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return reorderKnowledgeBaseSubcategoriesRequestCreation(knowledgeBaseId, categoryId, knowledgeBaseReorder, from).body(localVarReturnType);
    }

    /**
     * Reorder subcategories
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param categoryId Knowledge Base category ID.
     * @param knowledgeBaseReorder The knowledgeBaseReorder parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;Map&lt;String, Map&lt;String, Object&gt;&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Map<String, Map<String, Object>>> reorderKnowledgeBaseSubcategoriesWithHttpInfo(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long categoryId, @jakarta.annotation.Nonnull KnowledgeBaseReorder knowledgeBaseReorder, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Map<String, Map<String, Object>>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return reorderKnowledgeBaseSubcategoriesRequestCreation(knowledgeBaseId, categoryId, knowledgeBaseReorder, from).toEntity(localVarReturnType);
    }

    /**
     * Reorder subcategories
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param categoryId Knowledge Base category ID.
     * @param knowledgeBaseReorder The knowledgeBaseReorder parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec reorderKnowledgeBaseSubcategoriesWithResponseSpec(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long categoryId, @jakarta.annotation.Nonnull KnowledgeBaseReorder knowledgeBaseReorder, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return reorderKnowledgeBaseSubcategoriesRequestCreation(knowledgeBaseId, categoryId, knowledgeBaseReorder, from);
    }

    /**
     * Update knowledge base category
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param categoryId Knowledge Base category ID.
     * @param knowledgeBaseCategoryUpdate The knowledgeBaseCategoryUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return KnowledgeBaseCategory
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateKnowledgeBaseCategoryRequestCreation(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long categoryId, @jakarta.annotation.Nonnull KnowledgeBaseCategoryUpdate knowledgeBaseCategoryUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'knowledgeBaseId' is set
        if (knowledgeBaseId == null) {
            throw new RestClientResponseException("Missing the required parameter 'knowledgeBaseId' when calling updateKnowledgeBaseCategory", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'categoryId' is set
        if (categoryId == null) {
            throw new RestClientResponseException("Missing the required parameter 'categoryId' when calling updateKnowledgeBaseCategory", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'knowledgeBaseCategoryUpdate' is set
        if (knowledgeBaseCategoryUpdate == null) {
            throw new RestClientResponseException("Missing the required parameter 'knowledgeBaseCategoryUpdate' when calling updateKnowledgeBaseCategory", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("knowledgeBaseId", knowledgeBaseId);
        pathParams.put("categoryId", categoryId);

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

        ParameterizedTypeReference<KnowledgeBaseCategory> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/knowledge_bases/{knowledgeBaseId}/categories/{categoryId}", HttpMethod.PATCH, pathParams, localVarQueryParams, knowledgeBaseCategoryUpdate, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update knowledge base category
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param categoryId Knowledge Base category ID.
     * @param knowledgeBaseCategoryUpdate The knowledgeBaseCategoryUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return KnowledgeBaseCategory
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public KnowledgeBaseCategory updateKnowledgeBaseCategory(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long categoryId, @jakarta.annotation.Nonnull KnowledgeBaseCategoryUpdate knowledgeBaseCategoryUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<KnowledgeBaseCategory> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateKnowledgeBaseCategoryRequestCreation(knowledgeBaseId, categoryId, knowledgeBaseCategoryUpdate, from).body(localVarReturnType);
    }

    /**
     * Update knowledge base category
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param categoryId Knowledge Base category ID.
     * @param knowledgeBaseCategoryUpdate The knowledgeBaseCategoryUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;KnowledgeBaseCategory&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<KnowledgeBaseCategory> updateKnowledgeBaseCategoryWithHttpInfo(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long categoryId, @jakarta.annotation.Nonnull KnowledgeBaseCategoryUpdate knowledgeBaseCategoryUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<KnowledgeBaseCategory> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateKnowledgeBaseCategoryRequestCreation(knowledgeBaseId, categoryId, knowledgeBaseCategoryUpdate, from).toEntity(localVarReturnType);
    }

    /**
     * Update knowledge base category
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param categoryId Knowledge Base category ID.
     * @param knowledgeBaseCategoryUpdate The knowledgeBaseCategoryUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateKnowledgeBaseCategoryWithResponseSpec(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long categoryId, @jakarta.annotation.Nonnull KnowledgeBaseCategoryUpdate knowledgeBaseCategoryUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return updateKnowledgeBaseCategoryRequestCreation(knowledgeBaseId, categoryId, knowledgeBaseCategoryUpdate, from);
    }

    /**
     * Update category permissions
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param categoryId Knowledge Base category ID.
     * @param knowledgeBasePermissionsUpdate The knowledgeBasePermissionsUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return KnowledgeBasePermissions
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateKnowledgeBaseCategoryPermissionsRequestCreation(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long categoryId, @jakarta.annotation.Nonnull KnowledgeBasePermissionsUpdate knowledgeBasePermissionsUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'knowledgeBaseId' is set
        if (knowledgeBaseId == null) {
            throw new RestClientResponseException("Missing the required parameter 'knowledgeBaseId' when calling updateKnowledgeBaseCategoryPermissions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'categoryId' is set
        if (categoryId == null) {
            throw new RestClientResponseException("Missing the required parameter 'categoryId' when calling updateKnowledgeBaseCategoryPermissions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'knowledgeBasePermissionsUpdate' is set
        if (knowledgeBasePermissionsUpdate == null) {
            throw new RestClientResponseException("Missing the required parameter 'knowledgeBasePermissionsUpdate' when calling updateKnowledgeBaseCategoryPermissions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("knowledgeBaseId", knowledgeBaseId);
        pathParams.put("categoryId", categoryId);

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
        return apiClient.invokeAPI("/api/v1/knowledge_bases/{knowledgeBaseId}/categories/{categoryId}/permissions", HttpMethod.PUT, pathParams, localVarQueryParams, knowledgeBasePermissionsUpdate, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update category permissions
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param categoryId Knowledge Base category ID.
     * @param knowledgeBasePermissionsUpdate The knowledgeBasePermissionsUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return KnowledgeBasePermissions
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public KnowledgeBasePermissions updateKnowledgeBaseCategoryPermissions(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long categoryId, @jakarta.annotation.Nonnull KnowledgeBasePermissionsUpdate knowledgeBasePermissionsUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<KnowledgeBasePermissions> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateKnowledgeBaseCategoryPermissionsRequestCreation(knowledgeBaseId, categoryId, knowledgeBasePermissionsUpdate, from).body(localVarReturnType);
    }

    /**
     * Update category permissions
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param categoryId Knowledge Base category ID.
     * @param knowledgeBasePermissionsUpdate The knowledgeBasePermissionsUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;KnowledgeBasePermissions&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<KnowledgeBasePermissions> updateKnowledgeBaseCategoryPermissionsWithHttpInfo(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long categoryId, @jakarta.annotation.Nonnull KnowledgeBasePermissionsUpdate knowledgeBasePermissionsUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<KnowledgeBasePermissions> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateKnowledgeBaseCategoryPermissionsRequestCreation(knowledgeBaseId, categoryId, knowledgeBasePermissionsUpdate, from).toEntity(localVarReturnType);
    }

    /**
     * Update category permissions
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param categoryId Knowledge Base category ID.
     * @param knowledgeBasePermissionsUpdate The knowledgeBasePermissionsUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateKnowledgeBaseCategoryPermissionsWithResponseSpec(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long categoryId, @jakarta.annotation.Nonnull KnowledgeBasePermissionsUpdate knowledgeBasePermissionsUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return updateKnowledgeBaseCategoryPermissionsRequestCreation(knowledgeBaseId, categoryId, knowledgeBasePermissionsUpdate, from);
    }
}
