package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.FileLinkCollectionReadModel;
import ai.gebo.openproject.client.model.FileLinkCollectionWriteModel;
import ai.gebo.openproject.client.model.FileLinkReadModel;
import ai.gebo.openproject.client.model.OAuthClientCredentialsWriteModel;
import ai.gebo.openproject.client.model.ProjectStorageCollectionModel;
import ai.gebo.openproject.client.model.ProjectStorageModel;
import ai.gebo.openproject.client.model.StorageCollectionModel;
import ai.gebo.openproject.client.model.StorageFileModel;
import ai.gebo.openproject.client.model.StorageFileUploadLinkModel;
import ai.gebo.openproject.client.model.StorageFileUploadPreparationModel;
import ai.gebo.openproject.client.model.StorageFilesModel;
import ai.gebo.openproject.client.model.StorageFolderWriteModel;
import ai.gebo.openproject.client.model.StorageReadModel;
import ai.gebo.openproject.client.model.StorageWriteModel;
import java.net.URI;

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
public class FileLinksApi {
    private ApiClient apiClient;

    public FileLinksApi() {
        this(new ApiClient());
    }

    public FileLinksApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Creates a storage.
     * Creates a storage resource. When creating a storage, a confidential OAuth 2 provider application is created automatically. The oauth client id and secret of the created OAuth application are returned in the response.  **IMPORTANT:** This is the only time, the oauth client secret is visible to the consumer. After that, the secret is hidden.  To update the storage with OAuth client credentials, which enable the storage resource to behave as an OAuth 2 client against an external OAuth 2 provider application, another request must be made to create those, see &#x60;POST /api/v3/storages/{id}/oauth_client_credentials&#x60;.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Depends on the page the grid is defined for.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param storageWriteModel The storageWriteModel parameter
     * @return StorageReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createStorageRequestCreation(@jakarta.annotation.Nullable StorageWriteModel storageWriteModel) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json", "text/plain"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<StorageReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/storages", HttpMethod.POST, pathParams, localVarQueryParams, storageWriteModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Creates a storage.
     * Creates a storage resource. When creating a storage, a confidential OAuth 2 provider application is created automatically. The oauth client id and secret of the created OAuth application are returned in the response.  **IMPORTANT:** This is the only time, the oauth client secret is visible to the consumer. After that, the secret is hidden.  To update the storage with OAuth client credentials, which enable the storage resource to behave as an OAuth 2 client against an external OAuth 2 provider application, another request must be made to create those, see &#x60;POST /api/v3/storages/{id}/oauth_client_credentials&#x60;.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Depends on the page the grid is defined for.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param storageWriteModel The storageWriteModel parameter
     * @return StorageReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public StorageReadModel createStorage(@jakarta.annotation.Nullable StorageWriteModel storageWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<StorageReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createStorageRequestCreation(storageWriteModel).body(localVarReturnType);
    }

    /**
     * Creates a storage.
     * Creates a storage resource. When creating a storage, a confidential OAuth 2 provider application is created automatically. The oauth client id and secret of the created OAuth application are returned in the response.  **IMPORTANT:** This is the only time, the oauth client secret is visible to the consumer. After that, the secret is hidden.  To update the storage with OAuth client credentials, which enable the storage resource to behave as an OAuth 2 client against an external OAuth 2 provider application, another request must be made to create those, see &#x60;POST /api/v3/storages/{id}/oauth_client_credentials&#x60;.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Depends on the page the grid is defined for.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param storageWriteModel The storageWriteModel parameter
     * @return ResponseEntity&lt;StorageReadModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<StorageReadModel> createStorageWithHttpInfo(@jakarta.annotation.Nullable StorageWriteModel storageWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<StorageReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createStorageRequestCreation(storageWriteModel).toEntity(localVarReturnType);
    }

    /**
     * Creates a storage.
     * Creates a storage resource. When creating a storage, a confidential OAuth 2 provider application is created automatically. The oauth client id and secret of the created OAuth application are returned in the response.  **IMPORTANT:** This is the only time, the oauth client secret is visible to the consumer. After that, the secret is hidden.  To update the storage with OAuth client credentials, which enable the storage resource to behave as an OAuth 2 client against an external OAuth 2 provider application, another request must be made to create those, see &#x60;POST /api/v3/storages/{id}/oauth_client_credentials&#x60;.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** Depends on the page the grid is defined for.
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Returned if:  * a constraint for a property was violated (&#x60;PropertyConstraintViolation&#x60;)
     * @param storageWriteModel The storageWriteModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createStorageWithResponseSpec(@jakarta.annotation.Nullable StorageWriteModel storageWriteModel) throws RestClientResponseException {
        return createStorageRequestCreation(storageWriteModel);
    }

    /**
     * Creation of a new folder
     * Creates a new folder under the given parent
     * <p><b>201</b> - Created
     * <p><b>400</b> - Returned if the request is missing a required parameter.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage file links
     * <p><b>404</b> - Returned if the storage does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view file links
     * @param id Storage id
     * @param storageFolderWriteModel The storageFolderWriteModel parameter
     * @return StorageFileModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createStorageFolderRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable StorageFolderWriteModel storageFolderWriteModel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling createStorageFolder", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<StorageFileModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/storages/{id}/folders", HttpMethod.POST, pathParams, localVarQueryParams, storageFolderWriteModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Creation of a new folder
     * Creates a new folder under the given parent
     * <p><b>201</b> - Created
     * <p><b>400</b> - Returned if the request is missing a required parameter.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage file links
     * <p><b>404</b> - Returned if the storage does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view file links
     * @param id Storage id
     * @param storageFolderWriteModel The storageFolderWriteModel parameter
     * @return StorageFileModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public StorageFileModel createStorageFolder(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable StorageFolderWriteModel storageFolderWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<StorageFileModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createStorageFolderRequestCreation(id, storageFolderWriteModel).body(localVarReturnType);
    }

    /**
     * Creation of a new folder
     * Creates a new folder under the given parent
     * <p><b>201</b> - Created
     * <p><b>400</b> - Returned if the request is missing a required parameter.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage file links
     * <p><b>404</b> - Returned if the storage does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view file links
     * @param id Storage id
     * @param storageFolderWriteModel The storageFolderWriteModel parameter
     * @return ResponseEntity&lt;StorageFileModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<StorageFileModel> createStorageFolderWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable StorageFolderWriteModel storageFolderWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<StorageFileModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createStorageFolderRequestCreation(id, storageFolderWriteModel).toEntity(localVarReturnType);
    }

    /**
     * Creation of a new folder
     * Creates a new folder under the given parent
     * <p><b>201</b> - Created
     * <p><b>400</b> - Returned if the request is missing a required parameter.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage file links
     * <p><b>404</b> - Returned if the storage does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view file links
     * @param id Storage id
     * @param storageFolderWriteModel The storageFolderWriteModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createStorageFolderWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable StorageFolderWriteModel storageFolderWriteModel) throws RestClientResponseException {
        return createStorageFolderRequestCreation(id, storageFolderWriteModel);
    }

    /**
     * Creates an oauth client credentials object for a storage.
     * Inserts the OAuth 2 credentials into the storage, to allow the storage to act as an OAuth 2 client. Calling this endpoint on a storage that already contains OAuth 2 client credentials will replace them.
     * <p><b>201</b> - Created
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** admin
     * <p><b>404</b> - Returned if the storage does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view file links
     * @param id Storage id
     * @param oauthClientCredentialsWriteModel The oauthClientCredentialsWriteModel parameter
     * @return StorageReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createStorageOauthCredentialsRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable OAuthClientCredentialsWriteModel oauthClientCredentialsWriteModel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling createStorageOauthCredentials", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<StorageReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/storages/{id}/oauth_client_credentials", HttpMethod.POST, pathParams, localVarQueryParams, oauthClientCredentialsWriteModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Creates an oauth client credentials object for a storage.
     * Inserts the OAuth 2 credentials into the storage, to allow the storage to act as an OAuth 2 client. Calling this endpoint on a storage that already contains OAuth 2 client credentials will replace them.
     * <p><b>201</b> - Created
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** admin
     * <p><b>404</b> - Returned if the storage does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view file links
     * @param id Storage id
     * @param oauthClientCredentialsWriteModel The oauthClientCredentialsWriteModel parameter
     * @return StorageReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public StorageReadModel createStorageOauthCredentials(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable OAuthClientCredentialsWriteModel oauthClientCredentialsWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<StorageReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createStorageOauthCredentialsRequestCreation(id, oauthClientCredentialsWriteModel).body(localVarReturnType);
    }

    /**
     * Creates an oauth client credentials object for a storage.
     * Inserts the OAuth 2 credentials into the storage, to allow the storage to act as an OAuth 2 client. Calling this endpoint on a storage that already contains OAuth 2 client credentials will replace them.
     * <p><b>201</b> - Created
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** admin
     * <p><b>404</b> - Returned if the storage does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view file links
     * @param id Storage id
     * @param oauthClientCredentialsWriteModel The oauthClientCredentialsWriteModel parameter
     * @return ResponseEntity&lt;StorageReadModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<StorageReadModel> createStorageOauthCredentialsWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable OAuthClientCredentialsWriteModel oauthClientCredentialsWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<StorageReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createStorageOauthCredentialsRequestCreation(id, oauthClientCredentialsWriteModel).toEntity(localVarReturnType);
    }

    /**
     * Creates an oauth client credentials object for a storage.
     * Inserts the OAuth 2 credentials into the storage, to allow the storage to act as an OAuth 2 client. Calling this endpoint on a storage that already contains OAuth 2 client credentials will replace them.
     * <p><b>201</b> - Created
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** admin
     * <p><b>404</b> - Returned if the storage does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view file links
     * @param id Storage id
     * @param oauthClientCredentialsWriteModel The oauthClientCredentialsWriteModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createStorageOauthCredentialsWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable OAuthClientCredentialsWriteModel oauthClientCredentialsWriteModel) throws RestClientResponseException {
        return createStorageOauthCredentialsRequestCreation(id, oauthClientCredentialsWriteModel);
    }

    /**
     * Creates file links.
     * Creates file links on a work package.  The request is interpreted as a bulk insert, where every element of the collection is validated separately. Each element contains the origin meta data and a link to the storage, the file link is about to point to. The storage link can be provided as a resource link with id or as the host url.  The file&#39;s id and name are considered mandatory information. The rest of the origin meta data SHOULD be provided by the client. The _mimeType_ SHOULD be a standard mime type. An empty mime type will be handled as unknown. To link a folder, the custom mime type &#x60;application/x-op-directory&#x60; MUST be used.  Up to 20 file links can be submitted at once.  If any element data is invalid, no file links will be created.  If a file link with matching origin id, work package, and storage already exists, then it will not create an additional file link or update the meta data. Instead the information from the existing file link will be returned.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage file links  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package, view file links
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Occurs if the request body was correctly formatted, but some properties lead to errors in the validation process. This happens e.g. if the provided storage url is not available on the server.
     * @param id Work package id
     * @param fileLinkCollectionWriteModel The fileLinkCollectionWriteModel parameter
     * @return FileLinkCollectionReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createWorkPackageFileLinkRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable FileLinkCollectionWriteModel fileLinkCollectionWriteModel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling createWorkPackageFileLink", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json", "text/plain"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<FileLinkCollectionReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages/{id}/file_links", HttpMethod.POST, pathParams, localVarQueryParams, fileLinkCollectionWriteModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Creates file links.
     * Creates file links on a work package.  The request is interpreted as a bulk insert, where every element of the collection is validated separately. Each element contains the origin meta data and a link to the storage, the file link is about to point to. The storage link can be provided as a resource link with id or as the host url.  The file&#39;s id and name are considered mandatory information. The rest of the origin meta data SHOULD be provided by the client. The _mimeType_ SHOULD be a standard mime type. An empty mime type will be handled as unknown. To link a folder, the custom mime type &#x60;application/x-op-directory&#x60; MUST be used.  Up to 20 file links can be submitted at once.  If any element data is invalid, no file links will be created.  If a file link with matching origin id, work package, and storage already exists, then it will not create an additional file link or update the meta data. Instead the information from the existing file link will be returned.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage file links  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package, view file links
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Occurs if the request body was correctly formatted, but some properties lead to errors in the validation process. This happens e.g. if the provided storage url is not available on the server.
     * @param id Work package id
     * @param fileLinkCollectionWriteModel The fileLinkCollectionWriteModel parameter
     * @return FileLinkCollectionReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public FileLinkCollectionReadModel createWorkPackageFileLink(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable FileLinkCollectionWriteModel fileLinkCollectionWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<FileLinkCollectionReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createWorkPackageFileLinkRequestCreation(id, fileLinkCollectionWriteModel).body(localVarReturnType);
    }

    /**
     * Creates file links.
     * Creates file links on a work package.  The request is interpreted as a bulk insert, where every element of the collection is validated separately. Each element contains the origin meta data and a link to the storage, the file link is about to point to. The storage link can be provided as a resource link with id or as the host url.  The file&#39;s id and name are considered mandatory information. The rest of the origin meta data SHOULD be provided by the client. The _mimeType_ SHOULD be a standard mime type. An empty mime type will be handled as unknown. To link a folder, the custom mime type &#x60;application/x-op-directory&#x60; MUST be used.  Up to 20 file links can be submitted at once.  If any element data is invalid, no file links will be created.  If a file link with matching origin id, work package, and storage already exists, then it will not create an additional file link or update the meta data. Instead the information from the existing file link will be returned.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage file links  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package, view file links
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Occurs if the request body was correctly formatted, but some properties lead to errors in the validation process. This happens e.g. if the provided storage url is not available on the server.
     * @param id Work package id
     * @param fileLinkCollectionWriteModel The fileLinkCollectionWriteModel parameter
     * @return ResponseEntity&lt;FileLinkCollectionReadModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<FileLinkCollectionReadModel> createWorkPackageFileLinkWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable FileLinkCollectionWriteModel fileLinkCollectionWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<FileLinkCollectionReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createWorkPackageFileLinkRequestCreation(id, fileLinkCollectionWriteModel).toEntity(localVarReturnType);
    }

    /**
     * Creates file links.
     * Creates file links on a work package.  The request is interpreted as a bulk insert, where every element of the collection is validated separately. Each element contains the origin meta data and a link to the storage, the file link is about to point to. The storage link can be provided as a resource link with id or as the host url.  The file&#39;s id and name are considered mandatory information. The rest of the origin meta data SHOULD be provided by the client. The _mimeType_ SHOULD be a standard mime type. An empty mime type will be handled as unknown. To link a folder, the custom mime type &#x60;application/x-op-directory&#x60; MUST be used.  Up to 20 file links can be submitted at once.  If any element data is invalid, no file links will be created.  If a file link with matching origin id, work package, and storage already exists, then it will not create an additional file link or update the meta data. Instead the information from the existing file link will be returned.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Occurs when the client did not send a valid JSON object in the request body.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage file links  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package, view file links
     * <p><b>406</b> - Occurs when the client did not send a Content-Type header
     * <p><b>415</b> - Occurs when the client sends an unsupported Content-Type header.
     * <p><b>422</b> - Occurs if the request body was correctly formatted, but some properties lead to errors in the validation process. This happens e.g. if the provided storage url is not available on the server.
     * @param id Work package id
     * @param fileLinkCollectionWriteModel The fileLinkCollectionWriteModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createWorkPackageFileLinkWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable FileLinkCollectionWriteModel fileLinkCollectionWriteModel) throws RestClientResponseException {
        return createWorkPackageFileLinkRequestCreation(id, fileLinkCollectionWriteModel);
    }

    /**
     * Removes a file link.
     * Removes a file link on a work package.  The request contains only the file link identifier as a path parameter. No request body is needed.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage file links  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package or the file link does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package, view file links
     * @param id File link id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteFileLinkRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteFileLink", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/file_links/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Removes a file link.
     * Removes a file link on a work package.  The request contains only the file link identifier as a path parameter. No request body is needed.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage file links  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package or the file link does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package, view file links
     * @param id File link id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteFileLink(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteFileLinkRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Removes a file link.
     * Removes a file link on a work package.  The request contains only the file link identifier as a path parameter. No request body is needed.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage file links  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package or the file link does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package, view file links
     * @param id File link id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteFileLinkWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteFileLinkRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Removes a file link.
     * Removes a file link on a work package.  The request contains only the file link identifier as a path parameter. No request body is needed.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage file links  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package or the file link does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package, view file links
     * @param id File link id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteFileLinkWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return deleteFileLinkRequestCreation(id);
    }

    /**
     * Delete a storage
     * Deletes a storage resource. This also deletes all related records, like the created oauth application, client, and any file links created within this storage.
     * <p><b>204</b> - No content
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** admin
     * <p><b>404</b> - Returned if the storage does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view file links
     * @param id Storage id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteStorageRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteStorage", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/storages/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a storage
     * Deletes a storage resource. This also deletes all related records, like the created oauth application, client, and any file links created within this storage.
     * <p><b>204</b> - No content
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** admin
     * <p><b>404</b> - Returned if the storage does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view file links
     * @param id Storage id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteStorage(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteStorageRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Delete a storage
     * Deletes a storage resource. This also deletes all related records, like the created oauth application, client, and any file links created within this storage.
     * <p><b>204</b> - No content
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** admin
     * <p><b>404</b> - Returned if the storage does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view file links
     * @param id Storage id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteStorageWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteStorageRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Delete a storage
     * Deletes a storage resource. This also deletes all related records, like the created oauth application, client, and any file links created within this storage.
     * <p><b>204</b> - No content
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** admin
     * <p><b>404</b> - Returned if the storage does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view file links
     * @param id Storage id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteStorageWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return deleteStorageRequestCreation(id);
    }

    /**
     * Creates a download uri of the linked file.
     * Creates a uri to download the origin file linked by the given file link. This uri depends on the storage type and is always located on the origin storage itself.
     * <p><b>303</b> - Returned if the request was successful. In the &#x60;Location&#x60; header is the uri where the client can download the origin file from the storage.
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package, view file links
     * @param id File link id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec downloadFileLinkRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling downloadFileLink", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/file_links/{id}/download", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Creates a download uri of the linked file.
     * Creates a uri to download the origin file linked by the given file link. This uri depends on the storage type and is always located on the origin storage itself.
     * <p><b>303</b> - Returned if the request was successful. In the &#x60;Location&#x60; header is the uri where the client can download the origin file from the storage.
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package, view file links
     * @param id File link id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void downloadFileLink(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        downloadFileLinkRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Creates a download uri of the linked file.
     * Creates a uri to download the origin file linked by the given file link. This uri depends on the storage type and is always located on the origin storage itself.
     * <p><b>303</b> - Returned if the request was successful. In the &#x60;Location&#x60; header is the uri where the client can download the origin file from the storage.
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package, view file links
     * @param id File link id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> downloadFileLinkWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return downloadFileLinkRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Creates a download uri of the linked file.
     * Creates a uri to download the origin file linked by the given file link. This uri depends on the storage type and is always located on the origin storage itself.
     * <p><b>303</b> - Returned if the request was successful. In the &#x60;Location&#x60; header is the uri where the client can download the origin file from the storage.
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package, view file links
     * @param id File link id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec downloadFileLinkWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return downloadFileLinkRequestCreation(id);
    }

    /**
     * Gets a file link.
     * Gets a single file link resource of a work package.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package, view file links
     * @param id File link id
     * @return FileLinkReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getFileLinkRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getFileLink", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<FileLinkReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/file_links/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Gets a file link.
     * Gets a single file link resource of a work package.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package, view file links
     * @param id File link id
     * @return FileLinkReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public FileLinkReadModel getFileLink(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<FileLinkReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getFileLinkRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Gets a file link.
     * Gets a single file link resource of a work package.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package, view file links
     * @param id File link id
     * @return ResponseEntity&lt;FileLinkReadModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<FileLinkReadModel> getFileLinkWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<FileLinkReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getFileLinkRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Gets a file link.
     * Gets a single file link resource of a work package.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package, view file links
     * @param id File link id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getFileLinkWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getFileLinkRequestCreation(id);
    }

    /**
     * Gets a project storage
     * Gets a project storage resource. This resource contains all data that is applicable on the relation between a storage and a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the project storage does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view file links
     * @param id Project storage id
     * @return ProjectStorageModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getProjectStorageRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getProjectStorage", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<ProjectStorageModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/project_storages/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Gets a project storage
     * Gets a project storage resource. This resource contains all data that is applicable on the relation between a storage and a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the project storage does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view file links
     * @param id Project storage id
     * @return ProjectStorageModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ProjectStorageModel getProjectStorage(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<ProjectStorageModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getProjectStorageRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Gets a project storage
     * Gets a project storage resource. This resource contains all data that is applicable on the relation between a storage and a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the project storage does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view file links
     * @param id Project storage id
     * @return ResponseEntity&lt;ProjectStorageModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ProjectStorageModel> getProjectStorageWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<ProjectStorageModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getProjectStorageRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Gets a project storage
     * Gets a project storage resource. This resource contains all data that is applicable on the relation between a storage and a project.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the project storage does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view file links
     * @param id Project storage id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getProjectStorageWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getProjectStorageRequestCreation(id);
    }

    /**
     * Get a storage
     * Gets a storage resource. As a side effect, a live connection to the storages origin is established to retrieve connection state data.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the storage does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view file links
     * @param id Storage id
     * @return StorageReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getStorageRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getStorage", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<StorageReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/storages/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get a storage
     * Gets a storage resource. As a side effect, a live connection to the storages origin is established to retrieve connection state data.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the storage does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view file links
     * @param id Storage id
     * @return StorageReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public StorageReadModel getStorage(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<StorageReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getStorageRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Get a storage
     * Gets a storage resource. As a side effect, a live connection to the storages origin is established to retrieve connection state data.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the storage does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view file links
     * @param id Storage id
     * @return ResponseEntity&lt;StorageReadModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<StorageReadModel> getStorageWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<StorageReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getStorageRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Get a storage
     * Gets a storage resource. As a side effect, a live connection to the storages origin is established to retrieve connection state data.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the storage does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view file links
     * @param id Storage id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getStorageWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getStorageRequestCreation(id);
    }

    /**
     * Get a file of a storage.
     * Gets a single file from a storage identified by its remote id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if either the file storage doesn&#39;t exist or there is no file on the identified file storage.
     * @param storageId Storage id
     * @param fileId File id
     * @return StorageFileModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getStorageFileRequestCreation(@jakarta.annotation.Nonnull Integer storageId, @jakarta.annotation.Nonnull Integer fileId) throws RestClientResponseException {
        // verify the required parameter 'storageId' is set
        if (storageId == null) {
            throw new RestClientResponseException("Missing the required parameter 'storageId' when calling getStorageFile", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileId' is set
        if (fileId == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileId' when calling getStorageFile", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("storage_id", storageId);
        pathParams.put("file_id", fileId);

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

        ParameterizedTypeReference<StorageFileModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/storages/{storage_id}/files/{file_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get a file of a storage.
     * Gets a single file from a storage identified by its remote id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if either the file storage doesn&#39;t exist or there is no file on the identified file storage.
     * @param storageId Storage id
     * @param fileId File id
     * @return StorageFileModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public StorageFileModel getStorageFile(@jakarta.annotation.Nonnull Integer storageId, @jakarta.annotation.Nonnull Integer fileId) throws RestClientResponseException {
        ParameterizedTypeReference<StorageFileModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getStorageFileRequestCreation(storageId, fileId).body(localVarReturnType);
    }

    /**
     * Get a file of a storage.
     * Gets a single file from a storage identified by its remote id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if either the file storage doesn&#39;t exist or there is no file on the identified file storage.
     * @param storageId Storage id
     * @param fileId File id
     * @return ResponseEntity&lt;StorageFileModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<StorageFileModel> getStorageFileWithHttpInfo(@jakarta.annotation.Nonnull Integer storageId, @jakarta.annotation.Nonnull Integer fileId) throws RestClientResponseException {
        ParameterizedTypeReference<StorageFileModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getStorageFileRequestCreation(storageId, fileId).toEntity(localVarReturnType);
    }

    /**
     * Get a file of a storage.
     * Gets a single file from a storage identified by its remote id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if either the file storage doesn&#39;t exist or there is no file on the identified file storage.
     * @param storageId Storage id
     * @param fileId File id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getStorageFileWithResponseSpec(@jakarta.annotation.Nonnull Integer storageId, @jakarta.annotation.Nonnull Integer fileId) throws RestClientResponseException {
        return getStorageFileRequestCreation(storageId, fileId);
    }

    /**
     * Gets files of a storage.
     * Gets a collection of files from a storage.  If no &#x60;parent&#x60; context is given, the result is the content of the document root. With &#x60;parent&#x60; context given, the result contains the collections of files/directories from within the given parent file id.  If given &#x60;parent&#x60; context is no directory, &#x60;400 Bad Request&#x60; is returned.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the given parent parameter value does not refer to a directory.
     * <p><b>404</b> - Returned in either of those cases: - if the storage does not exist or the client does not have sufficient permissions to see it    **Required permission:** view file links - if the document root file identification does not exist on the storage
     * @param id Storage id
     * @param parent Parent file identification
     * @return StorageFilesModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getStorageFilesRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String parent) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getStorageFiles", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "parent", parent));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<StorageFilesModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/storages/{id}/files", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Gets files of a storage.
     * Gets a collection of files from a storage.  If no &#x60;parent&#x60; context is given, the result is the content of the document root. With &#x60;parent&#x60; context given, the result contains the collections of files/directories from within the given parent file id.  If given &#x60;parent&#x60; context is no directory, &#x60;400 Bad Request&#x60; is returned.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the given parent parameter value does not refer to a directory.
     * <p><b>404</b> - Returned in either of those cases: - if the storage does not exist or the client does not have sufficient permissions to see it    **Required permission:** view file links - if the document root file identification does not exist on the storage
     * @param id Storage id
     * @param parent Parent file identification
     * @return StorageFilesModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public StorageFilesModel getStorageFiles(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String parent) throws RestClientResponseException {
        ParameterizedTypeReference<StorageFilesModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getStorageFilesRequestCreation(id, parent).body(localVarReturnType);
    }

    /**
     * Gets files of a storage.
     * Gets a collection of files from a storage.  If no &#x60;parent&#x60; context is given, the result is the content of the document root. With &#x60;parent&#x60; context given, the result contains the collections of files/directories from within the given parent file id.  If given &#x60;parent&#x60; context is no directory, &#x60;400 Bad Request&#x60; is returned.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the given parent parameter value does not refer to a directory.
     * <p><b>404</b> - Returned in either of those cases: - if the storage does not exist or the client does not have sufficient permissions to see it    **Required permission:** view file links - if the document root file identification does not exist on the storage
     * @param id Storage id
     * @param parent Parent file identification
     * @return ResponseEntity&lt;StorageFilesModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<StorageFilesModel> getStorageFilesWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String parent) throws RestClientResponseException {
        ParameterizedTypeReference<StorageFilesModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getStorageFilesRequestCreation(id, parent).toEntity(localVarReturnType);
    }

    /**
     * Gets files of a storage.
     * Gets a collection of files from a storage.  If no &#x60;parent&#x60; context is given, the result is the content of the document root. With &#x60;parent&#x60; context given, the result contains the collections of files/directories from within the given parent file id.  If given &#x60;parent&#x60; context is no directory, &#x60;400 Bad Request&#x60; is returned.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the given parent parameter value does not refer to a directory.
     * <p><b>404</b> - Returned in either of those cases: - if the storage does not exist or the client does not have sufficient permissions to see it    **Required permission:** view file links - if the document root file identification does not exist on the storage
     * @param id Storage id
     * @param parent Parent file identification
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getStorageFilesWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String parent) throws RestClientResponseException {
        return getStorageFilesRequestCreation(id, parent);
    }

    /**
     * Gets a list of project storages
     * Gets a collection of all project storages that meet the provided filters and the user has permission to see them.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if any given filter is invalid.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  - project_id - storage_id - storage_url
     * @return ProjectStorageCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listProjectStoragesRequestCreation(@jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filters", filters));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<ProjectStorageCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/project_storages", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Gets a list of project storages
     * Gets a collection of all project storages that meet the provided filters and the user has permission to see them.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if any given filter is invalid.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  - project_id - storage_id - storage_url
     * @return ProjectStorageCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ProjectStorageCollectionModel listProjectStorages(@jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        ParameterizedTypeReference<ProjectStorageCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listProjectStoragesRequestCreation(filters).body(localVarReturnType);
    }

    /**
     * Gets a list of project storages
     * Gets a collection of all project storages that meet the provided filters and the user has permission to see them.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if any given filter is invalid.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  - project_id - storage_id - storage_url
     * @return ResponseEntity&lt;ProjectStorageCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ProjectStorageCollectionModel> listProjectStoragesWithHttpInfo(@jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        ParameterizedTypeReference<ProjectStorageCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listProjectStoragesRequestCreation(filters).toEntity(localVarReturnType);
    }

    /**
     * Gets a list of project storages
     * Gets a collection of all project storages that meet the provided filters and the user has permission to see them.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if any given filter is invalid.
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. Currently supported filters are:  - project_id - storage_id - storage_url
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listProjectStoragesWithResponseSpec(@jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        return listProjectStoragesRequestCreation(filters);
    }

    /**
     * Get Storages
     * Returns a collection of storages.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * @return StorageCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listStoragesRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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

        ParameterizedTypeReference<StorageCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/storages", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get Storages
     * Returns a collection of storages.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * @return StorageCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public StorageCollectionModel listStorages() throws RestClientResponseException {
        ParameterizedTypeReference<StorageCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listStoragesRequestCreation().body(localVarReturnType);
    }

    /**
     * Get Storages
     * Returns a collection of storages.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * @return ResponseEntity&lt;StorageCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<StorageCollectionModel> listStoragesWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<StorageCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listStoragesRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Get Storages
     * Returns a collection of storages.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the client sends invalid request parameters e.g. filters
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listStoragesWithResponseSpec() throws RestClientResponseException {
        return listStoragesRequestCreation();
    }

    /**
     * Gets all file links of a work package
     * Gets all file links of a work package.  As a side effect, for every file link a request is sent to the storage&#39;s origin to fetch live data and patch the file link&#39;s data before returning, as well as retrieving permissions of the user on this origin file.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view file links  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. The following filters are supported:  - storage
     * @return FileLinkCollectionReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listWorkPackageFileLinksRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listWorkPackageFileLinks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filters", filters));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<FileLinkCollectionReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages/{id}/file_links", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Gets all file links of a work package
     * Gets all file links of a work package.  As a side effect, for every file link a request is sent to the storage&#39;s origin to fetch live data and patch the file link&#39;s data before returning, as well as retrieving permissions of the user on this origin file.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view file links  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. The following filters are supported:  - storage
     * @return FileLinkCollectionReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public FileLinkCollectionReadModel listWorkPackageFileLinks(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        ParameterizedTypeReference<FileLinkCollectionReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkPackageFileLinksRequestCreation(id, filters).body(localVarReturnType);
    }

    /**
     * Gets all file links of a work package
     * Gets all file links of a work package.  As a side effect, for every file link a request is sent to the storage&#39;s origin to fetch live data and patch the file link&#39;s data before returning, as well as retrieving permissions of the user on this origin file.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view file links  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. The following filters are supported:  - storage
     * @return ResponseEntity&lt;FileLinkCollectionReadModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<FileLinkCollectionReadModel> listWorkPackageFileLinksWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        ParameterizedTypeReference<FileLinkCollectionReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkPackageFileLinksRequestCreation(id, filters).toEntity(localVarReturnType);
    }

    /**
     * Gets all file links of a work package
     * Gets all file links of a work package.  As a side effect, for every file link a request is sent to the storage&#39;s origin to fetch live data and patch the file link&#39;s data before returning, as well as retrieving permissions of the user on this origin file.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** view file links  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package
     * @param id Work package id
     * @param filters JSON specifying filter conditions. Accepts the same format as returned by the [queries](https://www.openproject.org/docs/api/endpoints/queries/) endpoint. The following filters are supported:  - storage
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listWorkPackageFileLinksWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable String filters) throws RestClientResponseException {
        return listWorkPackageFileLinksRequestCreation(id, filters);
    }

    /**
     * Creates an opening uri of the linked file.
     * Creates a uri to open the origin file linked by the given file link. This uri depends on the storage type and is always located on the origin storage itself.
     * <p><b>303</b> - Returned if the request was successful. In the &#x60;Location&#x60; header is the uri where the client can open the origin file on the storage.
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package, view file links
     * @param id File link id
     * @param location Boolean flag indicating, if the file should be opened directly or rather the directory location.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec openFileLinkRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean location) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling openFileLink", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "location", location));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/file_links/{id}/open", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Creates an opening uri of the linked file.
     * Creates a uri to open the origin file linked by the given file link. This uri depends on the storage type and is always located on the origin storage itself.
     * <p><b>303</b> - Returned if the request was successful. In the &#x60;Location&#x60; header is the uri where the client can open the origin file on the storage.
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package, view file links
     * @param id File link id
     * @param location Boolean flag indicating, if the file should be opened directly or rather the directory location.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void openFileLink(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean location) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        openFileLinkRequestCreation(id, location).body(localVarReturnType);
    }

    /**
     * Creates an opening uri of the linked file.
     * Creates a uri to open the origin file linked by the given file link. This uri depends on the storage type and is always located on the origin storage itself.
     * <p><b>303</b> - Returned if the request was successful. In the &#x60;Location&#x60; header is the uri where the client can open the origin file on the storage.
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package, view file links
     * @param id File link id
     * @param location Boolean flag indicating, if the file should be opened directly or rather the directory location.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> openFileLinkWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean location) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return openFileLinkRequestCreation(id, location).toEntity(localVarReturnType);
    }

    /**
     * Creates an opening uri of the linked file.
     * Creates a uri to open the origin file linked by the given file link. This uri depends on the storage type and is always located on the origin storage itself.
     * <p><b>303</b> - Returned if the request was successful. In the &#x60;Location&#x60; header is the uri where the client can open the origin file on the storage.
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work package, view file links
     * @param id File link id
     * @param location Boolean flag indicating, if the file should be opened directly or rather the directory location.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec openFileLinkWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean location) throws RestClientResponseException {
        return openFileLinkRequestCreation(id, location);
    }

    /**
     * Open the project storage
     * Gets a redirect to the location of the project storage&#39;s remote origin. If the project storage has a project folder, it is opened at this location. If not, the storage root is opened.
     * <p><b>303</b> - Redirect
     * <p><b>403</b> - Returned if the authorization token of the current user grants no permission to access the remote storage.
     * <p><b>404</b> - Returned if the project storage does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view file links
     * @param id Project storage id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec openProjectStorageRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling openProjectStorage", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/project_storages/{id}/open", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Open the project storage
     * Gets a redirect to the location of the project storage&#39;s remote origin. If the project storage has a project folder, it is opened at this location. If not, the storage root is opened.
     * <p><b>303</b> - Redirect
     * <p><b>403</b> - Returned if the authorization token of the current user grants no permission to access the remote storage.
     * <p><b>404</b> - Returned if the project storage does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view file links
     * @param id Project storage id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void openProjectStorage(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        openProjectStorageRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Open the project storage
     * Gets a redirect to the location of the project storage&#39;s remote origin. If the project storage has a project folder, it is opened at this location. If not, the storage root is opened.
     * <p><b>303</b> - Redirect
     * <p><b>403</b> - Returned if the authorization token of the current user grants no permission to access the remote storage.
     * <p><b>404</b> - Returned if the project storage does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view file links
     * @param id Project storage id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> openProjectStorageWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return openProjectStorageRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Open the project storage
     * Gets a redirect to the location of the project storage&#39;s remote origin. If the project storage has a project folder, it is opened at this location. If not, the storage root is opened.
     * <p><b>303</b> - Redirect
     * <p><b>403</b> - Returned if the authorization token of the current user grants no permission to access the remote storage.
     * <p><b>404</b> - Returned if the project storage does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view file links
     * @param id Project storage id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec openProjectStorageWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return openProjectStorageRequestCreation(id);
    }

    /**
     * Open the storage
     * Gets a redirect to the location of the storage&#39;s remote origin. The storage&#39;s files root should be the target location.
     * <p><b>303</b> - Redirect
     * <p><b>404</b> - Returned if the storage does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view file links
     * @param id Storage id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec openStorageRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling openStorage", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/storages/{id}/open", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Open the storage
     * Gets a redirect to the location of the storage&#39;s remote origin. The storage&#39;s files root should be the target location.
     * <p><b>303</b> - Redirect
     * <p><b>404</b> - Returned if the storage does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view file links
     * @param id Storage id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void openStorage(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        openStorageRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Open the storage
     * Gets a redirect to the location of the storage&#39;s remote origin. The storage&#39;s files root should be the target location.
     * <p><b>303</b> - Redirect
     * <p><b>404</b> - Returned if the storage does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view file links
     * @param id Storage id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> openStorageWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return openStorageRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Open the storage
     * Gets a redirect to the location of the storage&#39;s remote origin. The storage&#39;s files root should be the target location.
     * <p><b>303</b> - Redirect
     * <p><b>404</b> - Returned if the storage does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view file links
     * @param id Storage id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec openStorageWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return openStorageRequestCreation(id);
    }

    /**
     * Preparation of a direct upload of a file to the given storage.
     * Executes a request that prepares a link for a direct upload to the storage.  The background here is, that the client needs to make a direct request to the storage instance for file uploading, but should not get access to the credentials, which are stored in the backend. The response contains a link object, that enables the client to execute a file upload without the real credentials.
     * <p><b>201</b> - OK
     * <p><b>400</b> - Returned if the given parent parameter value does not refer to a directory.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage file links
     * <p><b>404</b> - Returned if the storage does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view file links
     * <p><b>500</b> - Returned if the outbound request to the storage has failed with any reason.
     * @param id Storage id
     * @param storageFileUploadPreparationModel The storageFileUploadPreparationModel parameter
     * @return StorageFileUploadLinkModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec prepareStorageFileUploadRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable StorageFileUploadPreparationModel storageFileUploadPreparationModel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling prepareStorageFileUpload", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<StorageFileUploadLinkModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/storages/{id}/files/prepare_upload", HttpMethod.POST, pathParams, localVarQueryParams, storageFileUploadPreparationModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Preparation of a direct upload of a file to the given storage.
     * Executes a request that prepares a link for a direct upload to the storage.  The background here is, that the client needs to make a direct request to the storage instance for file uploading, but should not get access to the credentials, which are stored in the backend. The response contains a link object, that enables the client to execute a file upload without the real credentials.
     * <p><b>201</b> - OK
     * <p><b>400</b> - Returned if the given parent parameter value does not refer to a directory.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage file links
     * <p><b>404</b> - Returned if the storage does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view file links
     * <p><b>500</b> - Returned if the outbound request to the storage has failed with any reason.
     * @param id Storage id
     * @param storageFileUploadPreparationModel The storageFileUploadPreparationModel parameter
     * @return StorageFileUploadLinkModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public StorageFileUploadLinkModel prepareStorageFileUpload(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable StorageFileUploadPreparationModel storageFileUploadPreparationModel) throws RestClientResponseException {
        ParameterizedTypeReference<StorageFileUploadLinkModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return prepareStorageFileUploadRequestCreation(id, storageFileUploadPreparationModel).body(localVarReturnType);
    }

    /**
     * Preparation of a direct upload of a file to the given storage.
     * Executes a request that prepares a link for a direct upload to the storage.  The background here is, that the client needs to make a direct request to the storage instance for file uploading, but should not get access to the credentials, which are stored in the backend. The response contains a link object, that enables the client to execute a file upload without the real credentials.
     * <p><b>201</b> - OK
     * <p><b>400</b> - Returned if the given parent parameter value does not refer to a directory.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage file links
     * <p><b>404</b> - Returned if the storage does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view file links
     * <p><b>500</b> - Returned if the outbound request to the storage has failed with any reason.
     * @param id Storage id
     * @param storageFileUploadPreparationModel The storageFileUploadPreparationModel parameter
     * @return ResponseEntity&lt;StorageFileUploadLinkModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<StorageFileUploadLinkModel> prepareStorageFileUploadWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable StorageFileUploadPreparationModel storageFileUploadPreparationModel) throws RestClientResponseException {
        ParameterizedTypeReference<StorageFileUploadLinkModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return prepareStorageFileUploadRequestCreation(id, storageFileUploadPreparationModel).toEntity(localVarReturnType);
    }

    /**
     * Preparation of a direct upload of a file to the given storage.
     * Executes a request that prepares a link for a direct upload to the storage.  The background here is, that the client needs to make a direct request to the storage instance for file uploading, but should not get access to the credentials, which are stored in the backend. The response contains a link object, that enables the client to execute a file upload without the real credentials.
     * <p><b>201</b> - OK
     * <p><b>400</b> - Returned if the given parent parameter value does not refer to a directory.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** manage file links
     * <p><b>404</b> - Returned if the storage does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view file links
     * <p><b>500</b> - Returned if the outbound request to the storage has failed with any reason.
     * @param id Storage id
     * @param storageFileUploadPreparationModel The storageFileUploadPreparationModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec prepareStorageFileUploadWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable StorageFileUploadPreparationModel storageFileUploadPreparationModel) throws RestClientResponseException {
        return prepareStorageFileUploadRequestCreation(id, storageFileUploadPreparationModel);
    }

    /**
     * Update a storage
     * Updates a storage resource. Only data that is not generated by the server can be updated. This excludes the OAuth 2 application data.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** admin
     * <p><b>404</b> - Returned if the storage does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view file links
     * @param id Storage id
     * @param storageWriteModel The storageWriteModel parameter
     * @return StorageReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateStorageRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable StorageWriteModel storageWriteModel) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling updateStorage", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<StorageReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/storages/{id}", HttpMethod.PATCH, pathParams, localVarQueryParams, storageWriteModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a storage
     * Updates a storage resource. Only data that is not generated by the server can be updated. This excludes the OAuth 2 application data.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** admin
     * <p><b>404</b> - Returned if the storage does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view file links
     * @param id Storage id
     * @param storageWriteModel The storageWriteModel parameter
     * @return StorageReadModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public StorageReadModel updateStorage(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable StorageWriteModel storageWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<StorageReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateStorageRequestCreation(id, storageWriteModel).body(localVarReturnType);
    }

    /**
     * Update a storage
     * Updates a storage resource. Only data that is not generated by the server can be updated. This excludes the OAuth 2 application data.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** admin
     * <p><b>404</b> - Returned if the storage does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view file links
     * @param id Storage id
     * @param storageWriteModel The storageWriteModel parameter
     * @return ResponseEntity&lt;StorageReadModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<StorageReadModel> updateStorageWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable StorageWriteModel storageWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<StorageReadModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateStorageRequestCreation(id, storageWriteModel).toEntity(localVarReturnType);
    }

    /**
     * Update a storage
     * Updates a storage resource. Only data that is not generated by the server can be updated. This excludes the OAuth 2 application data.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** admin
     * <p><b>404</b> - Returned if the storage does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view file links
     * @param id Storage id
     * @param storageWriteModel The storageWriteModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateStorageWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable StorageWriteModel storageWriteModel) throws RestClientResponseException {
        return updateStorageRequestCreation(id, storageWriteModel);
    }
}
