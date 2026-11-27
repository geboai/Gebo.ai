package ai.gebo.zammad.client.api;

import ai.gebo.zammad.client.invoker.ApiClient;

import ai.gebo.zammad.client.model.ChecklistItem;
import ai.gebo.zammad.client.model.ChecklistItemCreate;
import ai.gebo.zammad.client.model.ChecklistItemUpdate;
import ai.gebo.zammad.client.model.ErrorResponse;

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
public class ChecklistItemsApi {
    private ApiClient apiClient;

    public ChecklistItemsApi() {
        this(new ApiClient());
    }

    public ChecklistItemsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create checklist item
     * 
     * <p><b>201</b> - Checklist item created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistItemCreate The checklistItemCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ChecklistItem
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createChecklistItemRequestCreation(@jakarta.annotation.Nullable ChecklistItemCreate checklistItemCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'checklistItemCreate' is set
        if (checklistItemCreate == null) {
            throw new RestClientResponseException("Missing the required parameter 'checklistItemCreate' when calling createChecklistItem", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<ChecklistItem> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/checklist_items", HttpMethod.POST, pathParams, localVarQueryParams, checklistItemCreate, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create checklist item
     * 
     * <p><b>201</b> - Checklist item created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistItemCreate The checklistItemCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ChecklistItem
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ChecklistItem createChecklistItem(@jakarta.annotation.Nullable ChecklistItemCreate checklistItemCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<ChecklistItem> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createChecklistItemRequestCreation(checklistItemCreate, from).body(localVarReturnType);
    }

    /**
     * Create checklist item
     * 
     * <p><b>201</b> - Checklist item created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistItemCreate The checklistItemCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;ChecklistItem&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ChecklistItem> createChecklistItemWithHttpInfo(@jakarta.annotation.Nullable ChecklistItemCreate checklistItemCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<ChecklistItem> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createChecklistItemRequestCreation(checklistItemCreate, from).toEntity(localVarReturnType);
    }

    /**
     * Create checklist item
     * 
     * <p><b>201</b> - Checklist item created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistItemCreate The checklistItemCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createChecklistItemWithResponseSpec(@jakarta.annotation.Nullable ChecklistItemCreate checklistItemCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return createChecklistItemRequestCreation(checklistItemCreate, from);
    }

    /**
     * Delete checklist item
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistItemId Checklist item ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteChecklistItemRequestCreation(@jakarta.annotation.Nonnull Long checklistItemId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'checklistItemId' is set
        if (checklistItemId == null) {
            throw new RestClientResponseException("Missing the required parameter 'checklistItemId' when calling deleteChecklistItem", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("checklistItemId", checklistItemId);

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
        return apiClient.invokeAPI("/api/v1/checklist_items/{checklistItemId}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete checklist item
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistItemId Checklist item ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object deleteChecklistItem(@jakarta.annotation.Nonnull Long checklistItemId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteChecklistItemRequestCreation(checklistItemId, from).body(localVarReturnType);
    }

    /**
     * Delete checklist item
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistItemId Checklist item ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> deleteChecklistItemWithHttpInfo(@jakarta.annotation.Nonnull Long checklistItemId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteChecklistItemRequestCreation(checklistItemId, from).toEntity(localVarReturnType);
    }

    /**
     * Delete checklist item
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistItemId Checklist item ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteChecklistItemWithResponseSpec(@jakarta.annotation.Nonnull Long checklistItemId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return deleteChecklistItemRequestCreation(checklistItemId, from);
    }

    /**
     * Show checklist item
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistItemId Checklist item ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ChecklistItem
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getChecklistItemRequestCreation(@jakarta.annotation.Nonnull Long checklistItemId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'checklistItemId' is set
        if (checklistItemId == null) {
            throw new RestClientResponseException("Missing the required parameter 'checklistItemId' when calling getChecklistItem", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("checklistItemId", checklistItemId);

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

        ParameterizedTypeReference<ChecklistItem> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/checklist_items/{checklistItemId}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Show checklist item
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistItemId Checklist item ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ChecklistItem
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ChecklistItem getChecklistItem(@jakarta.annotation.Nonnull Long checklistItemId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<ChecklistItem> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getChecklistItemRequestCreation(checklistItemId, from).body(localVarReturnType);
    }

    /**
     * Show checklist item
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistItemId Checklist item ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;ChecklistItem&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ChecklistItem> getChecklistItemWithHttpInfo(@jakarta.annotation.Nonnull Long checklistItemId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<ChecklistItem> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getChecklistItemRequestCreation(checklistItemId, from).toEntity(localVarReturnType);
    }

    /**
     * Show checklist item
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistItemId Checklist item ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getChecklistItemWithResponseSpec(@jakarta.annotation.Nonnull Long checklistItemId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return getChecklistItemRequestCreation(checklistItemId, from);
    }

    /**
     * Update checklist item
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistItemId Checklist item ID.
     * @param checklistItemUpdate The checklistItemUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ChecklistItem
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateChecklistItemRequestCreation(@jakarta.annotation.Nonnull Long checklistItemId, @jakarta.annotation.Nonnull ChecklistItemUpdate checklistItemUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'checklistItemId' is set
        if (checklistItemId == null) {
            throw new RestClientResponseException("Missing the required parameter 'checklistItemId' when calling updateChecklistItem", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'checklistItemUpdate' is set
        if (checklistItemUpdate == null) {
            throw new RestClientResponseException("Missing the required parameter 'checklistItemUpdate' when calling updateChecklistItem", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("checklistItemId", checklistItemId);

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

        ParameterizedTypeReference<ChecklistItem> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/checklist_items/{checklistItemId}", HttpMethod.PATCH, pathParams, localVarQueryParams, checklistItemUpdate, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update checklist item
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistItemId Checklist item ID.
     * @param checklistItemUpdate The checklistItemUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ChecklistItem
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ChecklistItem updateChecklistItem(@jakarta.annotation.Nonnull Long checklistItemId, @jakarta.annotation.Nonnull ChecklistItemUpdate checklistItemUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<ChecklistItem> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateChecklistItemRequestCreation(checklistItemId, checklistItemUpdate, from).body(localVarReturnType);
    }

    /**
     * Update checklist item
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistItemId Checklist item ID.
     * @param checklistItemUpdate The checklistItemUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;ChecklistItem&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ChecklistItem> updateChecklistItemWithHttpInfo(@jakarta.annotation.Nonnull Long checklistItemId, @jakarta.annotation.Nonnull ChecklistItemUpdate checklistItemUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<ChecklistItem> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateChecklistItemRequestCreation(checklistItemId, checklistItemUpdate, from).toEntity(localVarReturnType);
    }

    /**
     * Update checklist item
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistItemId Checklist item ID.
     * @param checklistItemUpdate The checklistItemUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateChecklistItemWithResponseSpec(@jakarta.annotation.Nonnull Long checklistItemId, @jakarta.annotation.Nonnull ChecklistItemUpdate checklistItemUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return updateChecklistItemRequestCreation(checklistItemId, checklistItemUpdate, from);
    }
}
