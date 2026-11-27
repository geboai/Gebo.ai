package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.SchemaModel;
import ai.gebo.openproject.client.model.ValuesPropertyModel;

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
public class ValuesPropertyApi {
    private ApiClient apiClient;

    public ValuesPropertyApi() {
        this(new ApiClient());
    }

    public ValuesPropertyApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Get a notification detail
     * Returns an individual detail of a notification identified by the notification id and the id of the detail.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the notification or the detail of it does not exist or if the user does not have permission to view it.  **Required permission** being recipient of the notification
     * @param notificationId notification id
     * @param id detail id
     * @return ValuesPropertyModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getNotificationDetailRequestCreation(@jakarta.annotation.Nonnull Integer notificationId, @jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'notificationId' is set
        if (notificationId == null) {
            throw new RestClientResponseException("Missing the required parameter 'notificationId' when calling getNotificationDetail", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getNotificationDetail", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("notification_id", notificationId);
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

        ParameterizedTypeReference<ValuesPropertyModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/notifications/{notification_id}/details/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get a notification detail
     * Returns an individual detail of a notification identified by the notification id and the id of the detail.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the notification or the detail of it does not exist or if the user does not have permission to view it.  **Required permission** being recipient of the notification
     * @param notificationId notification id
     * @param id detail id
     * @return ValuesPropertyModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ValuesPropertyModel getNotificationDetail(@jakarta.annotation.Nonnull Integer notificationId, @jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<ValuesPropertyModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getNotificationDetailRequestCreation(notificationId, id).body(localVarReturnType);
    }

    /**
     * Get a notification detail
     * Returns an individual detail of a notification identified by the notification id and the id of the detail.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the notification or the detail of it does not exist or if the user does not have permission to view it.  **Required permission** being recipient of the notification
     * @param notificationId notification id
     * @param id detail id
     * @return ResponseEntity&lt;ValuesPropertyModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ValuesPropertyModel> getNotificationDetailWithHttpInfo(@jakarta.annotation.Nonnull Integer notificationId, @jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<ValuesPropertyModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getNotificationDetailRequestCreation(notificationId, id).toEntity(localVarReturnType);
    }

    /**
     * Get a notification detail
     * Returns an individual detail of a notification identified by the notification id and the id of the detail.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the notification or the detail of it does not exist or if the user does not have permission to view it.  **Required permission** being recipient of the notification
     * @param notificationId notification id
     * @param id detail id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getNotificationDetailWithResponseSpec(@jakarta.annotation.Nonnull Integer notificationId, @jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getNotificationDetailRequestCreation(notificationId, id);
    }

    /**
     * View Values schema
     * The schema of a &#x60;Values&#x60; resource.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the schema does not exist.
     * <p><b>400</b> - Returned if the requested property id is not in a lower camel case format.
     * @param id The identifier of the value. This is typically the value of the &#x60;property&#x60; property of the &#x60;Values&#x60; resource. It should be in lower camelcase format.
     * @return SchemaModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getValuesSchemaRequestCreation(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getValuesSchema", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<SchemaModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/values/schemas/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View Values schema
     * The schema of a &#x60;Values&#x60; resource.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the schema does not exist.
     * <p><b>400</b> - Returned if the requested property id is not in a lower camel case format.
     * @param id The identifier of the value. This is typically the value of the &#x60;property&#x60; property of the &#x60;Values&#x60; resource. It should be in lower camelcase format.
     * @return SchemaModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public SchemaModel getValuesSchema(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<SchemaModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getValuesSchemaRequestCreation(id).body(localVarReturnType);
    }

    /**
     * View Values schema
     * The schema of a &#x60;Values&#x60; resource.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the schema does not exist.
     * <p><b>400</b> - Returned if the requested property id is not in a lower camel case format.
     * @param id The identifier of the value. This is typically the value of the &#x60;property&#x60; property of the &#x60;Values&#x60; resource. It should be in lower camelcase format.
     * @return ResponseEntity&lt;SchemaModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SchemaModel> getValuesSchemaWithHttpInfo(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<SchemaModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getValuesSchemaRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * View Values schema
     * The schema of a &#x60;Values&#x60; resource.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the schema does not exist.
     * <p><b>400</b> - Returned if the requested property id is not in a lower camel case format.
     * @param id The identifier of the value. This is typically the value of the &#x60;property&#x60; property of the &#x60;Values&#x60; resource. It should be in lower camelcase format.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getValuesSchemaWithResponseSpec(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return getValuesSchemaRequestCreation(id);
    }
}
