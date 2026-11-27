package ai.gebo.zammad.client.api;

import ai.gebo.zammad.client.invoker.ApiClient;

import ai.gebo.zammad.client.model.ErrorResponse;
import ai.gebo.zammad.client.model.ObjectManagerAttribute;
import ai.gebo.zammad.client.model.ObjectManagerAttributeCreate;
import ai.gebo.zammad.client.model.ObjectManagerAttributeUpdate;
import ai.gebo.zammad.client.model.ObjectManagerMigrationResult;

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
public class ObjectManagerApi {
    private ApiClient apiClient;

    public ObjectManagerApi() {
        this(new ApiClient());
    }

    public ObjectManagerApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create object attribute
     * 
     * <p><b>201</b> - Attribute created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param objectManagerAttributeCreate The objectManagerAttributeCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ObjectManagerAttribute
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createObjectManagerAttributeRequestCreation(@jakarta.annotation.Nonnull ObjectManagerAttributeCreate objectManagerAttributeCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'objectManagerAttributeCreate' is set
        if (objectManagerAttributeCreate == null) {
            throw new RestClientResponseException("Missing the required parameter 'objectManagerAttributeCreate' when calling createObjectManagerAttribute", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<ObjectManagerAttribute> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/object_manager_attributes", HttpMethod.POST, pathParams, localVarQueryParams, objectManagerAttributeCreate, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create object attribute
     * 
     * <p><b>201</b> - Attribute created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param objectManagerAttributeCreate The objectManagerAttributeCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ObjectManagerAttribute
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ObjectManagerAttribute createObjectManagerAttribute(@jakarta.annotation.Nonnull ObjectManagerAttributeCreate objectManagerAttributeCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<ObjectManagerAttribute> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createObjectManagerAttributeRequestCreation(objectManagerAttributeCreate, from).body(localVarReturnType);
    }

    /**
     * Create object attribute
     * 
     * <p><b>201</b> - Attribute created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param objectManagerAttributeCreate The objectManagerAttributeCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;ObjectManagerAttribute&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ObjectManagerAttribute> createObjectManagerAttributeWithHttpInfo(@jakarta.annotation.Nonnull ObjectManagerAttributeCreate objectManagerAttributeCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<ObjectManagerAttribute> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createObjectManagerAttributeRequestCreation(objectManagerAttributeCreate, from).toEntity(localVarReturnType);
    }

    /**
     * Create object attribute
     * 
     * <p><b>201</b> - Attribute created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param objectManagerAttributeCreate The objectManagerAttributeCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createObjectManagerAttributeWithResponseSpec(@jakarta.annotation.Nonnull ObjectManagerAttributeCreate objectManagerAttributeCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return createObjectManagerAttributeRequestCreation(objectManagerAttributeCreate, from);
    }

    /**
     * Delete object attribute
     * Marks a deletable custom attribute for removal. Execute the migration endpoint to apply database changes.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param objectManagerAttributeId Object Manager attribute ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteObjectManagerAttributeRequestCreation(@jakarta.annotation.Nonnull Long objectManagerAttributeId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'objectManagerAttributeId' is set
        if (objectManagerAttributeId == null) {
            throw new RestClientResponseException("Missing the required parameter 'objectManagerAttributeId' when calling deleteObjectManagerAttribute", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("objectManagerAttributeId", objectManagerAttributeId);

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
        return apiClient.invokeAPI("/api/v1/object_manager_attributes/{objectManagerAttributeId}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete object attribute
     * Marks a deletable custom attribute for removal. Execute the migration endpoint to apply database changes.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param objectManagerAttributeId Object Manager attribute ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Object
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Object deleteObjectManagerAttribute(@jakarta.annotation.Nonnull Long objectManagerAttributeId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteObjectManagerAttributeRequestCreation(objectManagerAttributeId, from).body(localVarReturnType);
    }

    /**
     * Delete object attribute
     * Marks a deletable custom attribute for removal. Execute the migration endpoint to apply database changes.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param objectManagerAttributeId Object Manager attribute ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> deleteObjectManagerAttributeWithHttpInfo(@jakarta.annotation.Nonnull Long objectManagerAttributeId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteObjectManagerAttributeRequestCreation(objectManagerAttributeId, from).toEntity(localVarReturnType);
    }

    /**
     * Delete object attribute
     * Marks a deletable custom attribute for removal. Execute the migration endpoint to apply database changes.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param objectManagerAttributeId Object Manager attribute ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteObjectManagerAttributeWithResponseSpec(@jakarta.annotation.Nonnull Long objectManagerAttributeId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return deleteObjectManagerAttributeRequestCreation(objectManagerAttributeId, from);
    }

    /**
     * Execute object attribute database migrations
     * Applies pending Object Manager schema changes. This can lock database tables and should be planned operationally.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param body The body parameter
     * @return ObjectManagerMigrationResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec executeObjectManagerAttributeMigrationsRequestCreation(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
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

        ParameterizedTypeReference<ObjectManagerMigrationResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/object_manager_attributes_execute_migrations", HttpMethod.POST, pathParams, localVarQueryParams, body, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Execute object attribute database migrations
     * Applies pending Object Manager schema changes. This can lock database tables and should be planned operationally.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param body The body parameter
     * @return ObjectManagerMigrationResult
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ObjectManagerMigrationResult executeObjectManagerAttributeMigrations(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        ParameterizedTypeReference<ObjectManagerMigrationResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return executeObjectManagerAttributeMigrationsRequestCreation(from, body).body(localVarReturnType);
    }

    /**
     * Execute object attribute database migrations
     * Applies pending Object Manager schema changes. This can lock database tables and should be planned operationally.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param body The body parameter
     * @return ResponseEntity&lt;ObjectManagerMigrationResult&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ObjectManagerMigrationResult> executeObjectManagerAttributeMigrationsWithHttpInfo(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        ParameterizedTypeReference<ObjectManagerMigrationResult> localVarReturnType = new ParameterizedTypeReference<>() {};
        return executeObjectManagerAttributeMigrationsRequestCreation(from, body).toEntity(localVarReturnType);
    }

    /**
     * Execute object attribute database migrations
     * Applies pending Object Manager schema changes. This can lock database tables and should be planned operationally.
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
    public ResponseSpec executeObjectManagerAttributeMigrationsWithResponseSpec(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Object body) throws RestClientResponseException {
        return executeObjectManagerAttributeMigrationsRequestCreation(from, body);
    }

    /**
     * Show object attribute
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param objectManagerAttributeId Object Manager attribute ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ObjectManagerAttribute
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getObjectManagerAttributeRequestCreation(@jakarta.annotation.Nonnull Long objectManagerAttributeId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'objectManagerAttributeId' is set
        if (objectManagerAttributeId == null) {
            throw new RestClientResponseException("Missing the required parameter 'objectManagerAttributeId' when calling getObjectManagerAttribute", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("objectManagerAttributeId", objectManagerAttributeId);

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

        ParameterizedTypeReference<ObjectManagerAttribute> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/object_manager_attributes/{objectManagerAttributeId}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Show object attribute
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param objectManagerAttributeId Object Manager attribute ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ObjectManagerAttribute
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ObjectManagerAttribute getObjectManagerAttribute(@jakarta.annotation.Nonnull Long objectManagerAttributeId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<ObjectManagerAttribute> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getObjectManagerAttributeRequestCreation(objectManagerAttributeId, from).body(localVarReturnType);
    }

    /**
     * Show object attribute
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param objectManagerAttributeId Object Manager attribute ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;ObjectManagerAttribute&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ObjectManagerAttribute> getObjectManagerAttributeWithHttpInfo(@jakarta.annotation.Nonnull Long objectManagerAttributeId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<ObjectManagerAttribute> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getObjectManagerAttributeRequestCreation(objectManagerAttributeId, from).toEntity(localVarReturnType);
    }

    /**
     * Show object attribute
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param objectManagerAttributeId Object Manager attribute ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getObjectManagerAttributeWithResponseSpec(@jakarta.annotation.Nonnull Long objectManagerAttributeId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return getObjectManagerAttributeRequestCreation(objectManagerAttributeId, from);
    }

    /**
     * List object attributes
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
     * @return List&lt;ObjectManagerAttribute&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listObjectManagerAttributesRequestCreation(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
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

        ParameterizedTypeReference<List<ObjectManagerAttribute>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/object_manager_attributes", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List object attributes
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
     * @return List&lt;ObjectManagerAttribute&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<ObjectManagerAttribute> listObjectManagerAttributes(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<ObjectManagerAttribute>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listObjectManagerAttributesRequestCreation(from, page, perPage).body(localVarReturnType);
    }

    /**
     * List object attributes
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
     * @return ResponseEntity&lt;List&lt;ObjectManagerAttribute&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<ObjectManagerAttribute>> listObjectManagerAttributesWithHttpInfo(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<ObjectManagerAttribute>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listObjectManagerAttributesRequestCreation(from, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List object attributes
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
    public ResponseSpec listObjectManagerAttributesWithResponseSpec(@jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return listObjectManagerAttributesRequestCreation(from, page, perPage);
    }

    /**
     * Update object attribute
     * Changes are staged in &#x60;data_option_new&#x60; when a database migration is required.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param objectManagerAttributeId Object Manager attribute ID.
     * @param objectManagerAttributeUpdate The objectManagerAttributeUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ObjectManagerAttribute
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateObjectManagerAttributeRequestCreation(@jakarta.annotation.Nonnull Long objectManagerAttributeId, @jakarta.annotation.Nonnull ObjectManagerAttributeUpdate objectManagerAttributeUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'objectManagerAttributeId' is set
        if (objectManagerAttributeId == null) {
            throw new RestClientResponseException("Missing the required parameter 'objectManagerAttributeId' when calling updateObjectManagerAttribute", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'objectManagerAttributeUpdate' is set
        if (objectManagerAttributeUpdate == null) {
            throw new RestClientResponseException("Missing the required parameter 'objectManagerAttributeUpdate' when calling updateObjectManagerAttribute", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("objectManagerAttributeId", objectManagerAttributeId);

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

        ParameterizedTypeReference<ObjectManagerAttribute> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/object_manager_attributes/{objectManagerAttributeId}", HttpMethod.PUT, pathParams, localVarQueryParams, objectManagerAttributeUpdate, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update object attribute
     * Changes are staged in &#x60;data_option_new&#x60; when a database migration is required.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param objectManagerAttributeId Object Manager attribute ID.
     * @param objectManagerAttributeUpdate The objectManagerAttributeUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ObjectManagerAttribute
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ObjectManagerAttribute updateObjectManagerAttribute(@jakarta.annotation.Nonnull Long objectManagerAttributeId, @jakarta.annotation.Nonnull ObjectManagerAttributeUpdate objectManagerAttributeUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<ObjectManagerAttribute> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateObjectManagerAttributeRequestCreation(objectManagerAttributeId, objectManagerAttributeUpdate, from).body(localVarReturnType);
    }

    /**
     * Update object attribute
     * Changes are staged in &#x60;data_option_new&#x60; when a database migration is required.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param objectManagerAttributeId Object Manager attribute ID.
     * @param objectManagerAttributeUpdate The objectManagerAttributeUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;ObjectManagerAttribute&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ObjectManagerAttribute> updateObjectManagerAttributeWithHttpInfo(@jakarta.annotation.Nonnull Long objectManagerAttributeId, @jakarta.annotation.Nonnull ObjectManagerAttributeUpdate objectManagerAttributeUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<ObjectManagerAttribute> localVarReturnType = new ParameterizedTypeReference<>() {};
        return updateObjectManagerAttributeRequestCreation(objectManagerAttributeId, objectManagerAttributeUpdate, from).toEntity(localVarReturnType);
    }

    /**
     * Update object attribute
     * Changes are staged in &#x60;data_option_new&#x60; when a database migration is required.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param objectManagerAttributeId Object Manager attribute ID.
     * @param objectManagerAttributeUpdate The objectManagerAttributeUpdate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateObjectManagerAttributeWithResponseSpec(@jakarta.annotation.Nonnull Long objectManagerAttributeId, @jakarta.annotation.Nonnull ObjectManagerAttributeUpdate objectManagerAttributeUpdate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return updateObjectManagerAttributeRequestCreation(objectManagerAttributeId, objectManagerAttributeUpdate, from);
    }
}
