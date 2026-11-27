package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.CollaborationTokenModel;
import ai.gebo.openproject.client.model.CreateDocumentCollaborationTokenRequest;
import ai.gebo.openproject.client.model.DocumentModel;
import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.UpdateDocumentRequest;

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
public class DocumentsApi {
    private ApiClient apiClient;

    public DocumentsApi() {
        this(new ApiClient());
    }

    public DocumentsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create collaboration token
     * Creates a short-lived token for the real-time collaboration (Hocuspocus) server for the document.  The endpoint is only available when the feature flag &#x60;documents_collaboration_token_api&#x60; is active.  The token is issued for the requesting user. Users who may only view documents receive a read-only token. Requests authenticated with a token issued to the collaboration server are rejected.  To revoke the previous token while creating a new one, send it as &#x60;token&#x60; in the request body. The request is authorized by the client&#39;s own credentials, exactly like creating a token without one. The previous token only identifies which token to revoke, so it may be expired. This allows clients to recover after sleep or offline periods. The previous token must not be revoked (i.e. must not have been sent before), otherwise the request is rejected.  The previous token is revoked 30 seconds after the new token is created. Clients should hand the new token to the collaboration server right away, e.g. by calling &#x60;sendToken()&#x60; on the Hocuspocus provider.
     * <p><b>201</b> - Created. The response must not be cached (&#x60;Cache-Control: no-store&#x60;).
     * <p><b>403</b> - Returned if the request is authenticated with a collaboration token, i.e. the OAuth access token inside it. Such tokens can not request further tokens.
     * <p><b>404</b> - Returned if the document does not exist, if the user does not have permission to view it or if the feature flag &#x60;documents_collaboration_token_api&#x60; is off.  **Required permission** &#x60;view documents&#x60; in the project the document belongs to
     * <p><b>422</b> - Returned if the document is not collaborative, if real-time collaboration is disabled, or if the previous token is invalid, was issued for another document, belongs to another user or is already revoked. Nothing is created or revoked in that case.
     * <p><b>500</b> - Returned if the token could not be issued. Nothing is revoked in that case.
     * @param id Document id
     * @param createDocumentCollaborationTokenRequest The createDocumentCollaborationTokenRequest parameter
     * @return CollaborationTokenModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createDocumentCollaborationTokenRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable CreateDocumentCollaborationTokenRequest createDocumentCollaborationTokenRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling createDocumentCollaborationToken", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<CollaborationTokenModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/documents/{id}/collaboration_token", HttpMethod.POST, pathParams, localVarQueryParams, createDocumentCollaborationTokenRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create collaboration token
     * Creates a short-lived token for the real-time collaboration (Hocuspocus) server for the document.  The endpoint is only available when the feature flag &#x60;documents_collaboration_token_api&#x60; is active.  The token is issued for the requesting user. Users who may only view documents receive a read-only token. Requests authenticated with a token issued to the collaboration server are rejected.  To revoke the previous token while creating a new one, send it as &#x60;token&#x60; in the request body. The request is authorized by the client&#39;s own credentials, exactly like creating a token without one. The previous token only identifies which token to revoke, so it may be expired. This allows clients to recover after sleep or offline periods. The previous token must not be revoked (i.e. must not have been sent before), otherwise the request is rejected.  The previous token is revoked 30 seconds after the new token is created. Clients should hand the new token to the collaboration server right away, e.g. by calling &#x60;sendToken()&#x60; on the Hocuspocus provider.
     * <p><b>201</b> - Created. The response must not be cached (&#x60;Cache-Control: no-store&#x60;).
     * <p><b>403</b> - Returned if the request is authenticated with a collaboration token, i.e. the OAuth access token inside it. Such tokens can not request further tokens.
     * <p><b>404</b> - Returned if the document does not exist, if the user does not have permission to view it or if the feature flag &#x60;documents_collaboration_token_api&#x60; is off.  **Required permission** &#x60;view documents&#x60; in the project the document belongs to
     * <p><b>422</b> - Returned if the document is not collaborative, if real-time collaboration is disabled, or if the previous token is invalid, was issued for another document, belongs to another user or is already revoked. Nothing is created or revoked in that case.
     * <p><b>500</b> - Returned if the token could not be issued. Nothing is revoked in that case.
     * @param id Document id
     * @param createDocumentCollaborationTokenRequest The createDocumentCollaborationTokenRequest parameter
     * @return CollaborationTokenModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public CollaborationTokenModel createDocumentCollaborationToken(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable CreateDocumentCollaborationTokenRequest createDocumentCollaborationTokenRequest) throws RestClientResponseException {
        ParameterizedTypeReference<CollaborationTokenModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createDocumentCollaborationTokenRequestCreation(id, createDocumentCollaborationTokenRequest).body(localVarReturnType);
    }

    /**
     * Create collaboration token
     * Creates a short-lived token for the real-time collaboration (Hocuspocus) server for the document.  The endpoint is only available when the feature flag &#x60;documents_collaboration_token_api&#x60; is active.  The token is issued for the requesting user. Users who may only view documents receive a read-only token. Requests authenticated with a token issued to the collaboration server are rejected.  To revoke the previous token while creating a new one, send it as &#x60;token&#x60; in the request body. The request is authorized by the client&#39;s own credentials, exactly like creating a token without one. The previous token only identifies which token to revoke, so it may be expired. This allows clients to recover after sleep or offline periods. The previous token must not be revoked (i.e. must not have been sent before), otherwise the request is rejected.  The previous token is revoked 30 seconds after the new token is created. Clients should hand the new token to the collaboration server right away, e.g. by calling &#x60;sendToken()&#x60; on the Hocuspocus provider.
     * <p><b>201</b> - Created. The response must not be cached (&#x60;Cache-Control: no-store&#x60;).
     * <p><b>403</b> - Returned if the request is authenticated with a collaboration token, i.e. the OAuth access token inside it. Such tokens can not request further tokens.
     * <p><b>404</b> - Returned if the document does not exist, if the user does not have permission to view it or if the feature flag &#x60;documents_collaboration_token_api&#x60; is off.  **Required permission** &#x60;view documents&#x60; in the project the document belongs to
     * <p><b>422</b> - Returned if the document is not collaborative, if real-time collaboration is disabled, or if the previous token is invalid, was issued for another document, belongs to another user or is already revoked. Nothing is created or revoked in that case.
     * <p><b>500</b> - Returned if the token could not be issued. Nothing is revoked in that case.
     * @param id Document id
     * @param createDocumentCollaborationTokenRequest The createDocumentCollaborationTokenRequest parameter
     * @return ResponseEntity&lt;CollaborationTokenModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<CollaborationTokenModel> createDocumentCollaborationTokenWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable CreateDocumentCollaborationTokenRequest createDocumentCollaborationTokenRequest) throws RestClientResponseException {
        ParameterizedTypeReference<CollaborationTokenModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createDocumentCollaborationTokenRequestCreation(id, createDocumentCollaborationTokenRequest).toEntity(localVarReturnType);
    }

    /**
     * Create collaboration token
     * Creates a short-lived token for the real-time collaboration (Hocuspocus) server for the document.  The endpoint is only available when the feature flag &#x60;documents_collaboration_token_api&#x60; is active.  The token is issued for the requesting user. Users who may only view documents receive a read-only token. Requests authenticated with a token issued to the collaboration server are rejected.  To revoke the previous token while creating a new one, send it as &#x60;token&#x60; in the request body. The request is authorized by the client&#39;s own credentials, exactly like creating a token without one. The previous token only identifies which token to revoke, so it may be expired. This allows clients to recover after sleep or offline periods. The previous token must not be revoked (i.e. must not have been sent before), otherwise the request is rejected.  The previous token is revoked 30 seconds after the new token is created. Clients should hand the new token to the collaboration server right away, e.g. by calling &#x60;sendToken()&#x60; on the Hocuspocus provider.
     * <p><b>201</b> - Created. The response must not be cached (&#x60;Cache-Control: no-store&#x60;).
     * <p><b>403</b> - Returned if the request is authenticated with a collaboration token, i.e. the OAuth access token inside it. Such tokens can not request further tokens.
     * <p><b>404</b> - Returned if the document does not exist, if the user does not have permission to view it or if the feature flag &#x60;documents_collaboration_token_api&#x60; is off.  **Required permission** &#x60;view documents&#x60; in the project the document belongs to
     * <p><b>422</b> - Returned if the document is not collaborative, if real-time collaboration is disabled, or if the previous token is invalid, was issued for another document, belongs to another user or is already revoked. Nothing is created or revoked in that case.
     * <p><b>500</b> - Returned if the token could not be issued. Nothing is revoked in that case.
     * @param id Document id
     * @param createDocumentCollaborationTokenRequest The createDocumentCollaborationTokenRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createDocumentCollaborationTokenWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable CreateDocumentCollaborationTokenRequest createDocumentCollaborationTokenRequest) throws RestClientResponseException {
        return createDocumentCollaborationTokenRequestCreation(id, createDocumentCollaborationTokenRequest);
    }

    /**
     * View document
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the document does not exist or if the user does not have permission to view it.  **Required permission** &#x60;view documents&#x60; in the project the document belongs to
     * @param id Document id
     * @return DocumentModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getDocumentRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getDocument", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<DocumentModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/documents/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View document
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the document does not exist or if the user does not have permission to view it.  **Required permission** &#x60;view documents&#x60; in the project the document belongs to
     * @param id Document id
     * @return DocumentModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public DocumentModel getDocument(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<DocumentModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getDocumentRequestCreation(id).body(localVarReturnType);
    }

    /**
     * View document
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the document does not exist or if the user does not have permission to view it.  **Required permission** &#x60;view documents&#x60; in the project the document belongs to
     * @param id Document id
     * @return ResponseEntity&lt;DocumentModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<DocumentModel> getDocumentWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<DocumentModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getDocumentRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * View document
     * 
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the document does not exist or if the user does not have permission to view it.  **Required permission** &#x60;view documents&#x60; in the project the document belongs to
     * @param id Document id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getDocumentWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getDocumentRequestCreation(id);
    }

    /**
     * List Documents
     * The documents returned depend on the provided parameters and also on the requesting user&#39;s permissions.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * <p><b>403</b> - Returned if the client is not logged in and login is required.
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported sorts are:  + id: Sort by primary key  + created_at: Sort by document creation datetime
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listDocumentsRequestCreation(@jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String sortBy) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "offset", offset));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "pageSize", pageSize));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sortBy", sortBy));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/documents", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List Documents
     * The documents returned depend on the provided parameters and also on the requesting user&#39;s permissions.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * <p><b>403</b> - Returned if the client is not logged in and login is required.
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported sorts are:  + id: Sort by primary key  + created_at: Sort by document creation datetime
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object listDocuments(@jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String sortBy) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listDocumentsRequestCreation(offset, pageSize, sortBy).body(localVarReturnType);
    }

    /**
     * List Documents
     * The documents returned depend on the provided parameters and also on the requesting user&#39;s permissions.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * <p><b>403</b> - Returned if the client is not logged in and login is required.
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported sorts are:  + id: Sort by primary key  + created_at: Sort by document creation datetime
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> listDocumentsWithHttpInfo(@jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String sortBy) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listDocumentsRequestCreation(offset, pageSize, sortBy).toEntity(localVarReturnType);
    }

    /**
     * List Documents
     * The documents returned depend on the provided parameters and also on the requesting user&#39;s permissions.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * <p><b>403</b> - Returned if the client is not logged in and login is required.
     * @param offset Page number inside the requested collection.
     * @param pageSize Number of elements to display per page.
     * @param sortBy JSON specifying sort criteria. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported sorts are:  + id: Sort by primary key  + created_at: Sort by document creation datetime
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listDocumentsWithResponseSpec(@jakarta.annotation.Nullable Integer offset, @jakarta.annotation.Nullable Integer pageSize, @jakarta.annotation.Nullable String sortBy) throws RestClientResponseException {
        return listDocumentsRequestCreation(offset, pageSize, sortBy);
    }

    /**
     * Update document
     * Updates a document&#39;s attributes.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the request body is invalid.
     * <p><b>403</b> - Returned if the user does not have permission to edit the document.  **Required permission** &#x60;manage documents&#x60; in the project the document belongs to
     * <p><b>404</b> - Returned if the document does not exist or if the user does not have permission to view it.  **Required permission** &#x60;view documents&#x60; in the project the document belongs to
     * <p><b>422</b> - Returned if the request body contains validation errors.
     * @param id Document id
     * @param updateDocumentRequest The updateDocumentRequest parameter
     * @return DocumentModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateDocumentRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable UpdateDocumentRequest updateDocumentRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling updateDocument", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<DocumentModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/documents/{id}", HttpMethod.PATCH, pathParams, localVarQueryParams, updateDocumentRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update document
     * Updates a document&#39;s attributes.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the request body is invalid.
     * <p><b>403</b> - Returned if the user does not have permission to edit the document.  **Required permission** &#x60;manage documents&#x60; in the project the document belongs to
     * <p><b>404</b> - Returned if the document does not exist or if the user does not have permission to view it.  **Required permission** &#x60;view documents&#x60; in the project the document belongs to
     * <p><b>422</b> - Returned if the request body contains validation errors.
     * @param id Document id
     * @param updateDocumentRequest The updateDocumentRequest parameter
     * @return DocumentModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public DocumentModel updateDocument(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable UpdateDocumentRequest updateDocumentRequest) throws RestClientResponseException {
        ParameterizedTypeReference<DocumentModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateDocumentRequestCreation(id, updateDocumentRequest).body(localVarReturnType);
    }

    /**
     * Update document
     * Updates a document&#39;s attributes.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the request body is invalid.
     * <p><b>403</b> - Returned if the user does not have permission to edit the document.  **Required permission** &#x60;manage documents&#x60; in the project the document belongs to
     * <p><b>404</b> - Returned if the document does not exist or if the user does not have permission to view it.  **Required permission** &#x60;view documents&#x60; in the project the document belongs to
     * <p><b>422</b> - Returned if the request body contains validation errors.
     * @param id Document id
     * @param updateDocumentRequest The updateDocumentRequest parameter
     * @return ResponseEntity&lt;DocumentModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<DocumentModel> updateDocumentWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable UpdateDocumentRequest updateDocumentRequest) throws RestClientResponseException {
        ParameterizedTypeReference<DocumentModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateDocumentRequestCreation(id, updateDocumentRequest).toEntity(localVarReturnType);
    }

    /**
     * Update document
     * Updates a document&#39;s attributes.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the request body is invalid.
     * <p><b>403</b> - Returned if the user does not have permission to edit the document.  **Required permission** &#x60;manage documents&#x60; in the project the document belongs to
     * <p><b>404</b> - Returned if the document does not exist or if the user does not have permission to view it.  **Required permission** &#x60;view documents&#x60; in the project the document belongs to
     * <p><b>422</b> - Returned if the request body contains validation errors.
     * @param id Document id
     * @param updateDocumentRequest The updateDocumentRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateDocumentWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable UpdateDocumentRequest updateDocumentRequest) throws RestClientResponseException {
        return updateDocumentRequestCreation(id, updateDocumentRequest);
    }
}
