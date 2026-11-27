package ai.gebo.zammad.client.api;

import ai.gebo.zammad.client.invoker.ApiClient;

import ai.gebo.zammad.client.model.Checklist;
import ai.gebo.zammad.client.model.ChecklistCreate;
import ai.gebo.zammad.client.model.ChecklistCreateResult;
import ai.gebo.zammad.client.model.ChecklistUpdate;
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
public class ChecklistsApi {
    private ApiClient apiClient;

    public ChecklistsApi() {
        this(new ApiClient());
    }

    public ChecklistsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create checklist
     * 
     * <p><b>201</b> - Checklist created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistCreate The checklistCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ChecklistCreateResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createChecklistRequestCreation(@jakarta.annotation.Nonnull ChecklistCreate checklistCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'checklistCreate' is set
        if (checklistCreate == null) {
            throw new RestClientResponseException("Missing the required parameter 'checklistCreate' when calling createChecklist", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<ChecklistCreateResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/checklists", HttpMethod.POST, pathParams, localVarQueryParams, checklistCreate, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create checklist
     * 
     * <p><b>201</b> - Checklist created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistCreate The checklistCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ChecklistCreateResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ChecklistCreateResult createChecklist(@jakarta.annotation.Nonnull ChecklistCreate checklistCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<ChecklistCreateResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createChecklistRequestCreation(checklistCreate, from).body(localVarReturnType);
    }

    /**
     * Create checklist
     * 
     * <p><b>201</b> - Checklist created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistCreate The checklistCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;ChecklistCreateResult&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ChecklistCreateResult> createChecklistWithHttpInfo(@jakarta.annotation.Nonnull ChecklistCreate checklistCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<ChecklistCreateResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createChecklistRequestCreation(checklistCreate, from).toEntity(localVarReturnType);
    }

    /**
     * Create checklist
     * 
     * <p><b>201</b> - Checklist created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistCreate The checklistCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createChecklistWithResponseSpec(@jakarta.annotation.Nonnull ChecklistCreate checklistCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return createChecklistRequestCreation(checklistCreate, from);
    }

    /**
     * Delete checklist
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistId Checklist ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteChecklistRequestCreation(@jakarta.annotation.Nonnull Long checklistId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'checklistId' is set
        if (checklistId == null) {
            throw new RestClientResponseException("Missing the required parameter 'checklistId' when calling deleteChecklist", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("checklistId", checklistId);

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
        return apiClient.invokeAPI("/api/v1/checklists/{checklistId}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete checklist
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistId Checklist ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object deleteChecklist(@jakarta.annotation.Nonnull Long checklistId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteChecklistRequestCreation(checklistId, from).body(localVarReturnType);
    }

    /**
     * Delete checklist
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistId Checklist ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> deleteChecklistWithHttpInfo(@jakarta.annotation.Nonnull Long checklistId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteChecklistRequestCreation(checklistId, from).toEntity(localVarReturnType);
    }

    /**
     * Delete checklist
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistId Checklist ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteChecklistWithResponseSpec(@jakarta.annotation.Nonnull Long checklistId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return deleteChecklistRequestCreation(checklistId, from);
    }

    /**
     * Show checklist
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistId Checklist ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Checklist
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getChecklistRequestCreation(@jakarta.annotation.Nonnull Long checklistId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'checklistId' is set
        if (checklistId == null) {
            throw new RestClientResponseException("Missing the required parameter 'checklistId' when calling getChecklist", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("checklistId", checklistId);

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

        ParameterizedTypeReference<Checklist> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/checklists/{checklistId}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Show checklist
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistId Checklist ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Checklist
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Checklist getChecklist(@jakarta.annotation.Nonnull Long checklistId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Checklist> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getChecklistRequestCreation(checklistId, from).body(localVarReturnType);
    }

    /**
     * Show checklist
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistId Checklist ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;Checklist&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Checklist> getChecklistWithHttpInfo(@jakarta.annotation.Nonnull Long checklistId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Checklist> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getChecklistRequestCreation(checklistId, from).toEntity(localVarReturnType);
    }

    /**
     * Show checklist
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistId Checklist ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getChecklistWithResponseSpec(@jakarta.annotation.Nonnull Long checklistId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return getChecklistRequestCreation(checklistId, from);
    }

    /**
     * Update checklist
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistId Checklist ID.
     * @param checklistUpdate The checklistUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Checklist
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateChecklistRequestCreation(@jakarta.annotation.Nonnull Long checklistId, @jakarta.annotation.Nonnull ChecklistUpdate checklistUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'checklistId' is set
        if (checklistId == null) {
            throw new RestClientResponseException("Missing the required parameter 'checklistId' when calling updateChecklist", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'checklistUpdate' is set
        if (checklistUpdate == null) {
            throw new RestClientResponseException("Missing the required parameter 'checklistUpdate' when calling updateChecklist", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("checklistId", checklistId);

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

        ParameterizedTypeReference<Checklist> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/checklists/{checklistId}", HttpMethod.PATCH, pathParams, localVarQueryParams, checklistUpdate, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update checklist
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistId Checklist ID.
     * @param checklistUpdate The checklistUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Checklist
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Checklist updateChecklist(@jakarta.annotation.Nonnull Long checklistId, @jakarta.annotation.Nonnull ChecklistUpdate checklistUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Checklist> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateChecklistRequestCreation(checklistId, checklistUpdate, from).body(localVarReturnType);
    }

    /**
     * Update checklist
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistId Checklist ID.
     * @param checklistUpdate The checklistUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;Checklist&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Checklist> updateChecklistWithHttpInfo(@jakarta.annotation.Nonnull Long checklistId, @jakarta.annotation.Nonnull ChecklistUpdate checklistUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Checklist> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateChecklistRequestCreation(checklistId, checklistUpdate, from).toEntity(localVarReturnType);
    }

    /**
     * Update checklist
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistId Checklist ID.
     * @param checklistUpdate The checklistUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateChecklistWithResponseSpec(@jakarta.annotation.Nonnull Long checklistId, @jakarta.annotation.Nonnull ChecklistUpdate checklistUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return updateChecklistRequestCreation(checklistId, checklistUpdate, from);
    }
}
