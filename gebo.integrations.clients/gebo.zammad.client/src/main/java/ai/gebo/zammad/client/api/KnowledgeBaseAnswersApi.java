package ai.gebo.zammad.client.api;

import ai.gebo.zammad.client.invoker.ApiClient;

import ai.gebo.zammad.client.model.ErrorResponse;
import java.io.File;
import ai.gebo.zammad.client.model.KnowledgeBaseAnswerCreate;
import ai.gebo.zammad.client.model.KnowledgeBaseAnswerUpdate;
import ai.gebo.zammad.client.model.KnowledgeBaseAssetResult;

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
public class KnowledgeBaseAnswersApi {
    private ApiClient apiClient;

    public KnowledgeBaseAnswersApi() {
        this(new ApiClient());
    }

    public KnowledgeBaseAnswersApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Add knowledge base answer attachment
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param answerId Knowledge Base answer ID.
     * @param _file The _file parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Map&lt;String, Map&lt;String, Object&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec addKnowledgeBaseAnswerAttachmentRequestCreation(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long answerId, @jakarta.annotation.Nonnull File _file, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'knowledgeBaseId' is set
        if (knowledgeBaseId == null) {
            throw new RestClientResponseException("Missing the required parameter 'knowledgeBaseId' when calling addKnowledgeBaseAnswerAttachment", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'answerId' is set
        if (answerId == null) {
            throw new RestClientResponseException("Missing the required parameter 'answerId' when calling addKnowledgeBaseAnswerAttachment", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new RestClientResponseException("Missing the required parameter '_file' when calling addKnowledgeBaseAnswerAttachment", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("knowledgeBaseId", knowledgeBaseId);
        pathParams.put("answerId", answerId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (from != null) {
            headerParams.add("From", apiClient.parameterToString(from));
        }

        formParams.add("file", new FileSystemResource(_file));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "zammadToken", "basicAuth", "oauth2Bearer" };

        ParameterizedTypeReference<Map<String, Map<String, Object>>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/knowledge_bases/{knowledgeBaseId}/answers/{answerId}/attachments", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add knowledge base answer attachment
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param answerId Knowledge Base answer ID.
     * @param _file The _file parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Map&lt;String, Map&lt;String, Object&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Map<String, Map<String, Object>> addKnowledgeBaseAnswerAttachment(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long answerId, @jakarta.annotation.Nonnull File _file, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Map<String, Map<String, Object>>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return addKnowledgeBaseAnswerAttachmentRequestCreation(knowledgeBaseId, answerId, _file, from).body(localVarReturnType);
    }

    /**
     * Add knowledge base answer attachment
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param answerId Knowledge Base answer ID.
     * @param _file The _file parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;Map&lt;String, Map&lt;String, Object&gt;&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Map<String, Map<String, Object>>> addKnowledgeBaseAnswerAttachmentWithHttpInfo(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long answerId, @jakarta.annotation.Nonnull File _file, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Map<String, Map<String, Object>>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return addKnowledgeBaseAnswerAttachmentRequestCreation(knowledgeBaseId, answerId, _file, from).toEntity(localVarReturnType);
    }

    /**
     * Add knowledge base answer attachment
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param answerId Knowledge Base answer ID.
     * @param _file The _file parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec addKnowledgeBaseAnswerAttachmentWithResponseSpec(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long answerId, @jakarta.annotation.Nonnull File _file, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return addKnowledgeBaseAnswerAttachmentRequestCreation(knowledgeBaseId, answerId, _file, from);
    }

    /**
     * Archive knowledge base answer
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param answerId Knowledge Base answer ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param body The body parameter
     * @return KnowledgeBaseAssetResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec archiveKnowledgeBaseAnswerRequestCreation(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long answerId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        // verify the required parameter 'knowledgeBaseId' is set
        if (knowledgeBaseId == null) {
            throw new RestClientResponseException("Missing the required parameter 'knowledgeBaseId' when calling archiveKnowledgeBaseAnswer", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'answerId' is set
        if (answerId == null) {
            throw new RestClientResponseException("Missing the required parameter 'answerId' when calling archiveKnowledgeBaseAnswer", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("knowledgeBaseId", knowledgeBaseId);
        pathParams.put("answerId", answerId);

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

        ParameterizedTypeReference<KnowledgeBaseAssetResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/knowledge_bases/{knowledgeBaseId}/answers/{answerId}/archive", HttpMethod.POST, pathParams, localVarQueryParams, body, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Archive knowledge base answer
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param answerId Knowledge Base answer ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param body The body parameter
     * @return KnowledgeBaseAssetResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public KnowledgeBaseAssetResult archiveKnowledgeBaseAnswer(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long answerId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        ParameterizedTypeReference<KnowledgeBaseAssetResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return archiveKnowledgeBaseAnswerRequestCreation(knowledgeBaseId, answerId, from, body).body(localVarReturnType);
    }

    /**
     * Archive knowledge base answer
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param answerId Knowledge Base answer ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param body The body parameter
     * @return ResponseEntity&lt;KnowledgeBaseAssetResult&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<KnowledgeBaseAssetResult> archiveKnowledgeBaseAnswerWithHttpInfo(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long answerId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        ParameterizedTypeReference<KnowledgeBaseAssetResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return archiveKnowledgeBaseAnswerRequestCreation(knowledgeBaseId, answerId, from, body).toEntity(localVarReturnType);
    }

    /**
     * Archive knowledge base answer
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param answerId Knowledge Base answer ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param body The body parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec archiveKnowledgeBaseAnswerWithResponseSpec(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long answerId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        return archiveKnowledgeBaseAnswerRequestCreation(knowledgeBaseId, answerId, from, body);
    }

    /**
     * Create knowledge base answer
     * 
     * <p><b>201</b> - Knowledge Base answer created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param knowledgeBaseAnswerCreate The knowledgeBaseAnswerCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return KnowledgeBaseAssetResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createKnowledgeBaseAnswerRequestCreation(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull KnowledgeBaseAnswerCreate knowledgeBaseAnswerCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'knowledgeBaseId' is set
        if (knowledgeBaseId == null) {
            throw new RestClientResponseException("Missing the required parameter 'knowledgeBaseId' when calling createKnowledgeBaseAnswer", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'knowledgeBaseAnswerCreate' is set
        if (knowledgeBaseAnswerCreate == null) {
            throw new RestClientResponseException("Missing the required parameter 'knowledgeBaseAnswerCreate' when calling createKnowledgeBaseAnswer", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<KnowledgeBaseAssetResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/knowledge_bases/{knowledgeBaseId}/answers", HttpMethod.POST, pathParams, localVarQueryParams, knowledgeBaseAnswerCreate, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create knowledge base answer
     * 
     * <p><b>201</b> - Knowledge Base answer created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param knowledgeBaseAnswerCreate The knowledgeBaseAnswerCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return KnowledgeBaseAssetResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public KnowledgeBaseAssetResult createKnowledgeBaseAnswer(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull KnowledgeBaseAnswerCreate knowledgeBaseAnswerCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<KnowledgeBaseAssetResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createKnowledgeBaseAnswerRequestCreation(knowledgeBaseId, knowledgeBaseAnswerCreate, from).body(localVarReturnType);
    }

    /**
     * Create knowledge base answer
     * 
     * <p><b>201</b> - Knowledge Base answer created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param knowledgeBaseAnswerCreate The knowledgeBaseAnswerCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;KnowledgeBaseAssetResult&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<KnowledgeBaseAssetResult> createKnowledgeBaseAnswerWithHttpInfo(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull KnowledgeBaseAnswerCreate knowledgeBaseAnswerCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<KnowledgeBaseAssetResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createKnowledgeBaseAnswerRequestCreation(knowledgeBaseId, knowledgeBaseAnswerCreate, from).toEntity(localVarReturnType);
    }

    /**
     * Create knowledge base answer
     * 
     * <p><b>201</b> - Knowledge Base answer created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param knowledgeBaseAnswerCreate The knowledgeBaseAnswerCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createKnowledgeBaseAnswerWithResponseSpec(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull KnowledgeBaseAnswerCreate knowledgeBaseAnswerCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return createKnowledgeBaseAnswerRequestCreation(knowledgeBaseId, knowledgeBaseAnswerCreate, from);
    }

    /**
     * Delete knowledge base answer
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param answerId Knowledge Base answer ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteKnowledgeBaseAnswerRequestCreation(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long answerId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'knowledgeBaseId' is set
        if (knowledgeBaseId == null) {
            throw new RestClientResponseException("Missing the required parameter 'knowledgeBaseId' when calling deleteKnowledgeBaseAnswer", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'answerId' is set
        if (answerId == null) {
            throw new RestClientResponseException("Missing the required parameter 'answerId' when calling deleteKnowledgeBaseAnswer", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("knowledgeBaseId", knowledgeBaseId);
        pathParams.put("answerId", answerId);

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
        return apiClient.invokeAPI("/api/v1/knowledge_bases/{knowledgeBaseId}/answers/{answerId}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete knowledge base answer
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param answerId Knowledge Base answer ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object deleteKnowledgeBaseAnswer(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long answerId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteKnowledgeBaseAnswerRequestCreation(knowledgeBaseId, answerId, from).body(localVarReturnType);
    }

    /**
     * Delete knowledge base answer
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param answerId Knowledge Base answer ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> deleteKnowledgeBaseAnswerWithHttpInfo(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long answerId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteKnowledgeBaseAnswerRequestCreation(knowledgeBaseId, answerId, from).toEntity(localVarReturnType);
    }

    /**
     * Delete knowledge base answer
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param answerId Knowledge Base answer ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteKnowledgeBaseAnswerWithResponseSpec(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long answerId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return deleteKnowledgeBaseAnswerRequestCreation(knowledgeBaseId, answerId, from);
    }

    /**
     * Delete knowledge base answer attachment
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param answerId Knowledge Base answer ID.
     * @param attachmentId Attachment ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Map&lt;String, Map&lt;String, Object&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteKnowledgeBaseAnswerAttachmentRequestCreation(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long answerId, @jakarta.annotation.Nonnull Long attachmentId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'knowledgeBaseId' is set
        if (knowledgeBaseId == null) {
            throw new RestClientResponseException("Missing the required parameter 'knowledgeBaseId' when calling deleteKnowledgeBaseAnswerAttachment", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'answerId' is set
        if (answerId == null) {
            throw new RestClientResponseException("Missing the required parameter 'answerId' when calling deleteKnowledgeBaseAnswerAttachment", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'attachmentId' is set
        if (attachmentId == null) {
            throw new RestClientResponseException("Missing the required parameter 'attachmentId' when calling deleteKnowledgeBaseAnswerAttachment", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("knowledgeBaseId", knowledgeBaseId);
        pathParams.put("answerId", answerId);
        pathParams.put("attachmentId", attachmentId);

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

        ParameterizedTypeReference<Map<String, Map<String, Object>>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/knowledge_bases/{knowledgeBaseId}/answers/{answerId}/attachments/{attachmentId}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete knowledge base answer attachment
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param answerId Knowledge Base answer ID.
     * @param attachmentId Attachment ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Map&lt;String, Map&lt;String, Object&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Map<String, Map<String, Object>> deleteKnowledgeBaseAnswerAttachment(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long answerId, @jakarta.annotation.Nonnull Long attachmentId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Map<String, Map<String, Object>>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteKnowledgeBaseAnswerAttachmentRequestCreation(knowledgeBaseId, answerId, attachmentId, from).body(localVarReturnType);
    }

    /**
     * Delete knowledge base answer attachment
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param answerId Knowledge Base answer ID.
     * @param attachmentId Attachment ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;Map&lt;String, Map&lt;String, Object&gt;&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Map<String, Map<String, Object>>> deleteKnowledgeBaseAnswerAttachmentWithHttpInfo(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long answerId, @jakarta.annotation.Nonnull Long attachmentId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Map<String, Map<String, Object>>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteKnowledgeBaseAnswerAttachmentRequestCreation(knowledgeBaseId, answerId, attachmentId, from).toEntity(localVarReturnType);
    }

    /**
     * Delete knowledge base answer attachment
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param answerId Knowledge Base answer ID.
     * @param attachmentId Attachment ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteKnowledgeBaseAnswerAttachmentWithResponseSpec(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long answerId, @jakarta.annotation.Nonnull Long attachmentId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return deleteKnowledgeBaseAnswerAttachmentRequestCreation(knowledgeBaseId, answerId, attachmentId, from);
    }

    /**
     * Show knowledge base answer
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param answerId Knowledge Base answer ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param includeContents Comma-separated translated answer IDs whose content should be included.
     * @return KnowledgeBaseAssetResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getKnowledgeBaseAnswerRequestCreation(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long answerId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable String includeContents) throws RestClientResponseException {
        // verify the required parameter 'knowledgeBaseId' is set
        if (knowledgeBaseId == null) {
            throw new RestClientResponseException("Missing the required parameter 'knowledgeBaseId' when calling getKnowledgeBaseAnswer", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'answerId' is set
        if (answerId == null) {
            throw new RestClientResponseException("Missing the required parameter 'answerId' when calling getKnowledgeBaseAnswer", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("knowledgeBaseId", knowledgeBaseId);
        pathParams.put("answerId", answerId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "include_contents", includeContents));

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

        ParameterizedTypeReference<KnowledgeBaseAssetResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/knowledge_bases/{knowledgeBaseId}/answers/{answerId}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Show knowledge base answer
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param answerId Knowledge Base answer ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param includeContents Comma-separated translated answer IDs whose content should be included.
     * @return KnowledgeBaseAssetResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public KnowledgeBaseAssetResult getKnowledgeBaseAnswer(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long answerId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable String includeContents) throws RestClientResponseException {
        ParameterizedTypeReference<KnowledgeBaseAssetResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getKnowledgeBaseAnswerRequestCreation(knowledgeBaseId, answerId, from, includeContents).body(localVarReturnType);
    }

    /**
     * Show knowledge base answer
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param answerId Knowledge Base answer ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param includeContents Comma-separated translated answer IDs whose content should be included.
     * @return ResponseEntity&lt;KnowledgeBaseAssetResult&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<KnowledgeBaseAssetResult> getKnowledgeBaseAnswerWithHttpInfo(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long answerId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable String includeContents) throws RestClientResponseException {
        ParameterizedTypeReference<KnowledgeBaseAssetResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getKnowledgeBaseAnswerRequestCreation(knowledgeBaseId, answerId, from, includeContents).toEntity(localVarReturnType);
    }

    /**
     * Show knowledge base answer
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param answerId Knowledge Base answer ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param includeContents Comma-separated translated answer IDs whose content should be included.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getKnowledgeBaseAnswerWithResponseSpec(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long answerId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable String includeContents) throws RestClientResponseException {
        return getKnowledgeBaseAnswerRequestCreation(knowledgeBaseId, answerId, from, includeContents);
    }

    /**
     * Publish knowledge base answer internally
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param answerId Knowledge Base answer ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param body The body parameter
     * @return KnowledgeBaseAssetResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec internalKnowledgeBaseAnswerRequestCreation(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long answerId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        // verify the required parameter 'knowledgeBaseId' is set
        if (knowledgeBaseId == null) {
            throw new RestClientResponseException("Missing the required parameter 'knowledgeBaseId' when calling internalKnowledgeBaseAnswer", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'answerId' is set
        if (answerId == null) {
            throw new RestClientResponseException("Missing the required parameter 'answerId' when calling internalKnowledgeBaseAnswer", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("knowledgeBaseId", knowledgeBaseId);
        pathParams.put("answerId", answerId);

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

        ParameterizedTypeReference<KnowledgeBaseAssetResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/knowledge_bases/{knowledgeBaseId}/answers/{answerId}/internal", HttpMethod.POST, pathParams, localVarQueryParams, body, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Publish knowledge base answer internally
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param answerId Knowledge Base answer ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param body The body parameter
     * @return KnowledgeBaseAssetResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public KnowledgeBaseAssetResult internalKnowledgeBaseAnswer(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long answerId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        ParameterizedTypeReference<KnowledgeBaseAssetResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return internalKnowledgeBaseAnswerRequestCreation(knowledgeBaseId, answerId, from, body).body(localVarReturnType);
    }

    /**
     * Publish knowledge base answer internally
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param answerId Knowledge Base answer ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param body The body parameter
     * @return ResponseEntity&lt;KnowledgeBaseAssetResult&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<KnowledgeBaseAssetResult> internalKnowledgeBaseAnswerWithHttpInfo(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long answerId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        ParameterizedTypeReference<KnowledgeBaseAssetResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return internalKnowledgeBaseAnswerRequestCreation(knowledgeBaseId, answerId, from, body).toEntity(localVarReturnType);
    }

    /**
     * Publish knowledge base answer internally
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param answerId Knowledge Base answer ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param body The body parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec internalKnowledgeBaseAnswerWithResponseSpec(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long answerId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        return internalKnowledgeBaseAnswerRequestCreation(knowledgeBaseId, answerId, from, body);
    }

    /**
     * Publish knowledge base answer publicly
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param answerId Knowledge Base answer ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param body The body parameter
     * @return KnowledgeBaseAssetResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec publishKnowledgeBaseAnswerRequestCreation(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long answerId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        // verify the required parameter 'knowledgeBaseId' is set
        if (knowledgeBaseId == null) {
            throw new RestClientResponseException("Missing the required parameter 'knowledgeBaseId' when calling publishKnowledgeBaseAnswer", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'answerId' is set
        if (answerId == null) {
            throw new RestClientResponseException("Missing the required parameter 'answerId' when calling publishKnowledgeBaseAnswer", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("knowledgeBaseId", knowledgeBaseId);
        pathParams.put("answerId", answerId);

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

        ParameterizedTypeReference<KnowledgeBaseAssetResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/knowledge_bases/{knowledgeBaseId}/answers/{answerId}/publish", HttpMethod.POST, pathParams, localVarQueryParams, body, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Publish knowledge base answer publicly
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param answerId Knowledge Base answer ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param body The body parameter
     * @return KnowledgeBaseAssetResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public KnowledgeBaseAssetResult publishKnowledgeBaseAnswer(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long answerId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        ParameterizedTypeReference<KnowledgeBaseAssetResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return publishKnowledgeBaseAnswerRequestCreation(knowledgeBaseId, answerId, from, body).body(localVarReturnType);
    }

    /**
     * Publish knowledge base answer publicly
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param answerId Knowledge Base answer ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param body The body parameter
     * @return ResponseEntity&lt;KnowledgeBaseAssetResult&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<KnowledgeBaseAssetResult> publishKnowledgeBaseAnswerWithHttpInfo(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long answerId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        ParameterizedTypeReference<KnowledgeBaseAssetResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return publishKnowledgeBaseAnswerRequestCreation(knowledgeBaseId, answerId, from, body).toEntity(localVarReturnType);
    }

    /**
     * Publish knowledge base answer publicly
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param answerId Knowledge Base answer ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param body The body parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec publishKnowledgeBaseAnswerWithResponseSpec(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long answerId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        return publishKnowledgeBaseAnswerRequestCreation(knowledgeBaseId, answerId, from, body);
    }

    /**
     * Unarchive knowledge base answer
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param answerId Knowledge Base answer ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param body The body parameter
     * @return KnowledgeBaseAssetResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec unarchiveKnowledgeBaseAnswerRequestCreation(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long answerId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        // verify the required parameter 'knowledgeBaseId' is set
        if (knowledgeBaseId == null) {
            throw new RestClientResponseException("Missing the required parameter 'knowledgeBaseId' when calling unarchiveKnowledgeBaseAnswer", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'answerId' is set
        if (answerId == null) {
            throw new RestClientResponseException("Missing the required parameter 'answerId' when calling unarchiveKnowledgeBaseAnswer", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("knowledgeBaseId", knowledgeBaseId);
        pathParams.put("answerId", answerId);

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

        ParameterizedTypeReference<KnowledgeBaseAssetResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/knowledge_bases/{knowledgeBaseId}/answers/{answerId}/unarchive", HttpMethod.POST, pathParams, localVarQueryParams, body, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Unarchive knowledge base answer
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param answerId Knowledge Base answer ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param body The body parameter
     * @return KnowledgeBaseAssetResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public KnowledgeBaseAssetResult unarchiveKnowledgeBaseAnswer(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long answerId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        ParameterizedTypeReference<KnowledgeBaseAssetResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return unarchiveKnowledgeBaseAnswerRequestCreation(knowledgeBaseId, answerId, from, body).body(localVarReturnType);
    }

    /**
     * Unarchive knowledge base answer
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param answerId Knowledge Base answer ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param body The body parameter
     * @return ResponseEntity&lt;KnowledgeBaseAssetResult&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<KnowledgeBaseAssetResult> unarchiveKnowledgeBaseAnswerWithHttpInfo(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long answerId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        ParameterizedTypeReference<KnowledgeBaseAssetResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return unarchiveKnowledgeBaseAnswerRequestCreation(knowledgeBaseId, answerId, from, body).toEntity(localVarReturnType);
    }

    /**
     * Unarchive knowledge base answer
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param answerId Knowledge Base answer ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param body The body parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec unarchiveKnowledgeBaseAnswerWithResponseSpec(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long answerId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        return unarchiveKnowledgeBaseAnswerRequestCreation(knowledgeBaseId, answerId, from, body);
    }

    /**
     * Update knowledge base answer
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param answerId Knowledge Base answer ID.
     * @param knowledgeBaseAnswerUpdate The knowledgeBaseAnswerUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return KnowledgeBaseAssetResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateKnowledgeBaseAnswerRequestCreation(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long answerId, @jakarta.annotation.Nonnull KnowledgeBaseAnswerUpdate knowledgeBaseAnswerUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'knowledgeBaseId' is set
        if (knowledgeBaseId == null) {
            throw new RestClientResponseException("Missing the required parameter 'knowledgeBaseId' when calling updateKnowledgeBaseAnswer", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'answerId' is set
        if (answerId == null) {
            throw new RestClientResponseException("Missing the required parameter 'answerId' when calling updateKnowledgeBaseAnswer", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'knowledgeBaseAnswerUpdate' is set
        if (knowledgeBaseAnswerUpdate == null) {
            throw new RestClientResponseException("Missing the required parameter 'knowledgeBaseAnswerUpdate' when calling updateKnowledgeBaseAnswer", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("knowledgeBaseId", knowledgeBaseId);
        pathParams.put("answerId", answerId);

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

        ParameterizedTypeReference<KnowledgeBaseAssetResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/knowledge_bases/{knowledgeBaseId}/answers/{answerId}", HttpMethod.PATCH, pathParams, localVarQueryParams, knowledgeBaseAnswerUpdate, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update knowledge base answer
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param answerId Knowledge Base answer ID.
     * @param knowledgeBaseAnswerUpdate The knowledgeBaseAnswerUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return KnowledgeBaseAssetResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public KnowledgeBaseAssetResult updateKnowledgeBaseAnswer(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long answerId, @jakarta.annotation.Nonnull KnowledgeBaseAnswerUpdate knowledgeBaseAnswerUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<KnowledgeBaseAssetResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateKnowledgeBaseAnswerRequestCreation(knowledgeBaseId, answerId, knowledgeBaseAnswerUpdate, from).body(localVarReturnType);
    }

    /**
     * Update knowledge base answer
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param answerId Knowledge Base answer ID.
     * @param knowledgeBaseAnswerUpdate The knowledgeBaseAnswerUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;KnowledgeBaseAssetResult&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<KnowledgeBaseAssetResult> updateKnowledgeBaseAnswerWithHttpInfo(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long answerId, @jakarta.annotation.Nonnull KnowledgeBaseAnswerUpdate knowledgeBaseAnswerUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<KnowledgeBaseAssetResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateKnowledgeBaseAnswerRequestCreation(knowledgeBaseId, answerId, knowledgeBaseAnswerUpdate, from).toEntity(localVarReturnType);
    }

    /**
     * Update knowledge base answer
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param knowledgeBaseId Knowledge Base ID.
     * @param answerId Knowledge Base answer ID.
     * @param knowledgeBaseAnswerUpdate The knowledgeBaseAnswerUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateKnowledgeBaseAnswerWithResponseSpec(@jakarta.annotation.Nonnull Long knowledgeBaseId, @jakarta.annotation.Nonnull Long answerId, @jakarta.annotation.Nonnull KnowledgeBaseAnswerUpdate knowledgeBaseAnswerUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return updateKnowledgeBaseAnswerRequestCreation(knowledgeBaseId, answerId, knowledgeBaseAnswerUpdate, from);
    }
}
