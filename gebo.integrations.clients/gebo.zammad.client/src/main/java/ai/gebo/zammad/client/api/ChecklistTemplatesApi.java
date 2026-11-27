package ai.gebo.zammad.client.api;

import ai.gebo.zammad.client.invoker.ApiClient;

import ai.gebo.zammad.client.model.ChecklistTemplate;
import ai.gebo.zammad.client.model.ChecklistTemplateCreate;
import ai.gebo.zammad.client.model.ChecklistTemplateUpdate;
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
public class ChecklistTemplatesApi {
    private ApiClient apiClient;

    public ChecklistTemplatesApi() {
        this(new ApiClient());
    }

    public ChecklistTemplatesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create checklist template
     * 
     * <p><b>201</b> - Checklist template created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistTemplateCreate The checklistTemplateCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ChecklistTemplate
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createChecklistTemplateRequestCreation(@jakarta.annotation.Nonnull ChecklistTemplateCreate checklistTemplateCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'checklistTemplateCreate' is set
        if (checklistTemplateCreate == null) {
            throw new RestClientResponseException("Missing the required parameter 'checklistTemplateCreate' when calling createChecklistTemplate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<ChecklistTemplate> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/checklist_templates", HttpMethod.POST, pathParams, localVarQueryParams, checklistTemplateCreate, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create checklist template
     * 
     * <p><b>201</b> - Checklist template created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistTemplateCreate The checklistTemplateCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ChecklistTemplate
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ChecklistTemplate createChecklistTemplate(@jakarta.annotation.Nonnull ChecklistTemplateCreate checklistTemplateCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<ChecklistTemplate> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createChecklistTemplateRequestCreation(checklistTemplateCreate, from).body(localVarReturnType);
    }

    /**
     * Create checklist template
     * 
     * <p><b>201</b> - Checklist template created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistTemplateCreate The checklistTemplateCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;ChecklistTemplate&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ChecklistTemplate> createChecklistTemplateWithHttpInfo(@jakarta.annotation.Nonnull ChecklistTemplateCreate checklistTemplateCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<ChecklistTemplate> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createChecklistTemplateRequestCreation(checklistTemplateCreate, from).toEntity(localVarReturnType);
    }

    /**
     * Create checklist template
     * 
     * <p><b>201</b> - Checklist template created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistTemplateCreate The checklistTemplateCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createChecklistTemplateWithResponseSpec(@jakarta.annotation.Nonnull ChecklistTemplateCreate checklistTemplateCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return createChecklistTemplateRequestCreation(checklistTemplateCreate, from);
    }

    /**
     * Delete checklist template
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistTemplateId Checklist template ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteChecklistTemplateRequestCreation(@jakarta.annotation.Nonnull Long checklistTemplateId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'checklistTemplateId' is set
        if (checklistTemplateId == null) {
            throw new RestClientResponseException("Missing the required parameter 'checklistTemplateId' when calling deleteChecklistTemplate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("checklistTemplateId", checklistTemplateId);

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
        return apiClient.invokeAPI("/api/v1/checklist_templates/{checklistTemplateId}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete checklist template
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistTemplateId Checklist template ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object deleteChecklistTemplate(@jakarta.annotation.Nonnull Long checklistTemplateId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteChecklistTemplateRequestCreation(checklistTemplateId, from).body(localVarReturnType);
    }

    /**
     * Delete checklist template
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistTemplateId Checklist template ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> deleteChecklistTemplateWithHttpInfo(@jakarta.annotation.Nonnull Long checklistTemplateId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteChecklistTemplateRequestCreation(checklistTemplateId, from).toEntity(localVarReturnType);
    }

    /**
     * Delete checklist template
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistTemplateId Checklist template ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteChecklistTemplateWithResponseSpec(@jakarta.annotation.Nonnull Long checklistTemplateId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return deleteChecklistTemplateRequestCreation(checklistTemplateId, from);
    }

    /**
     * Show checklist template
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistTemplateId Checklist template ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ChecklistTemplate
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getChecklistTemplateRequestCreation(@jakarta.annotation.Nonnull Long checklistTemplateId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'checklistTemplateId' is set
        if (checklistTemplateId == null) {
            throw new RestClientResponseException("Missing the required parameter 'checklistTemplateId' when calling getChecklistTemplate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("checklistTemplateId", checklistTemplateId);

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

        ParameterizedTypeReference<ChecklistTemplate> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/checklist_templates/{checklistTemplateId}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Show checklist template
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistTemplateId Checklist template ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ChecklistTemplate
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ChecklistTemplate getChecklistTemplate(@jakarta.annotation.Nonnull Long checklistTemplateId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<ChecklistTemplate> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getChecklistTemplateRequestCreation(checklistTemplateId, from).body(localVarReturnType);
    }

    /**
     * Show checklist template
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistTemplateId Checklist template ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;ChecklistTemplate&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ChecklistTemplate> getChecklistTemplateWithHttpInfo(@jakarta.annotation.Nonnull Long checklistTemplateId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<ChecklistTemplate> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getChecklistTemplateRequestCreation(checklistTemplateId, from).toEntity(localVarReturnType);
    }

    /**
     * Show checklist template
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistTemplateId Checklist template ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getChecklistTemplateWithResponseSpec(@jakarta.annotation.Nonnull Long checklistTemplateId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return getChecklistTemplateRequestCreation(checklistTemplateId, from);
    }

    /**
     * List checklist templates
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return List&lt;ChecklistTemplate&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listChecklistTemplatesRequestCreation(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

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

        ParameterizedTypeReference<List<ChecklistTemplate>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/checklist_templates", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List checklist templates
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return List&lt;ChecklistTemplate&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<ChecklistTemplate> listChecklistTemplates(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<ChecklistTemplate>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listChecklistTemplatesRequestCreation(from, page, perPage).body(localVarReturnType);
    }

    /**
     * List checklist templates
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return ResponseEntity&lt;List&lt;ChecklistTemplate&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<ChecklistTemplate>> listChecklistTemplatesWithHttpInfo(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<ChecklistTemplate>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listChecklistTemplatesRequestCreation(from, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List checklist templates
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param page One-based page number.
     * @param perPage Requested page size. Zammad silently caps values at a controller-specific hard limit.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listChecklistTemplatesWithResponseSpec(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return listChecklistTemplatesRequestCreation(from, page, perPage);
    }

    /**
     * Update checklist template
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistTemplateId Checklist template ID.
     * @param checklistTemplateUpdate The checklistTemplateUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ChecklistTemplate
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateChecklistTemplateRequestCreation(@jakarta.annotation.Nonnull Long checklistTemplateId, @jakarta.annotation.Nonnull ChecklistTemplateUpdate checklistTemplateUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'checklistTemplateId' is set
        if (checklistTemplateId == null) {
            throw new RestClientResponseException("Missing the required parameter 'checklistTemplateId' when calling updateChecklistTemplate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'checklistTemplateUpdate' is set
        if (checklistTemplateUpdate == null) {
            throw new RestClientResponseException("Missing the required parameter 'checklistTemplateUpdate' when calling updateChecklistTemplate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("checklistTemplateId", checklistTemplateId);

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

        ParameterizedTypeReference<ChecklistTemplate> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/checklist_templates/{checklistTemplateId}", HttpMethod.PATCH, pathParams, localVarQueryParams, checklistTemplateUpdate, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update checklist template
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistTemplateId Checklist template ID.
     * @param checklistTemplateUpdate The checklistTemplateUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ChecklistTemplate
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ChecklistTemplate updateChecklistTemplate(@jakarta.annotation.Nonnull Long checklistTemplateId, @jakarta.annotation.Nonnull ChecklistTemplateUpdate checklistTemplateUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<ChecklistTemplate> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateChecklistTemplateRequestCreation(checklistTemplateId, checklistTemplateUpdate, from).body(localVarReturnType);
    }

    /**
     * Update checklist template
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistTemplateId Checklist template ID.
     * @param checklistTemplateUpdate The checklistTemplateUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;ChecklistTemplate&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ChecklistTemplate> updateChecklistTemplateWithHttpInfo(@jakarta.annotation.Nonnull Long checklistTemplateId, @jakarta.annotation.Nonnull ChecklistTemplateUpdate checklistTemplateUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<ChecklistTemplate> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateChecklistTemplateRequestCreation(checklistTemplateId, checklistTemplateUpdate, from).toEntity(localVarReturnType);
    }

    /**
     * Update checklist template
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param checklistTemplateId Checklist template ID.
     * @param checklistTemplateUpdate The checklistTemplateUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateChecklistTemplateWithResponseSpec(@jakarta.annotation.Nonnull Long checklistTemplateId, @jakarta.annotation.Nonnull ChecklistTemplateUpdate checklistTemplateUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return updateChecklistTemplateRequestCreation(checklistTemplateId, checklistTemplateUpdate, from);
    }
}
