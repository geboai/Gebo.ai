package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesMetricImage;
import java.io.File;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;

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
public class AlertManagementApi {
    private ApiClient apiClient;

    public AlertManagementApi() {
        this(new ApiClient());
    }

    public AlertManagementApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a metric image
     * Deletes a specified metric image for an alert.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Unprocessable entity
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param alertIid Internal ID of the alert.
     * @param metricImageId ID of the metric image.
     * @return APIEntitiesMetricImage
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesMetricImageIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer alertIid, @jakarta.annotation.Nonnull Integer metricImageId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesMetricImageId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'alertIid' is set
        if (alertIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'alertIid' when calling deleteApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesMetricImageId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'metricImageId' is set
        if (metricImageId == null) {
            throw new RestClientResponseException("Missing the required parameter 'metricImageId' when calling deleteApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesMetricImageId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("alert_iid", alertIid);
        pathParams.put("metric_image_id", metricImageId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesMetricImage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/alert_management_alerts/{alert_iid}/metric_images/{metric_image_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a metric image
     * Deletes a specified metric image for an alert.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Unprocessable entity
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param alertIid Internal ID of the alert.
     * @param metricImageId ID of the metric image.
     * @return APIEntitiesMetricImage
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMetricImage deleteApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesMetricImageId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer alertIid, @jakarta.annotation.Nonnull Integer metricImageId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMetricImage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesMetricImageIdRequestCreation(id, alertIid, metricImageId).body(localVarReturnType);
    }

    /**
     * Delete a metric image
     * Deletes a specified metric image for an alert.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Unprocessable entity
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param alertIid Internal ID of the alert.
     * @param metricImageId ID of the metric image.
     * @return ResponseEntity&lt;APIEntitiesMetricImage&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMetricImage> deleteApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesMetricImageIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer alertIid, @jakarta.annotation.Nonnull Integer metricImageId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMetricImage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesMetricImageIdRequestCreation(id, alertIid, metricImageId).toEntity(localVarReturnType);
    }

    /**
     * Delete a metric image
     * Deletes a specified metric image for an alert.
     * <p><b>204</b> - No Content
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Unprocessable entity
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param alertIid Internal ID of the alert.
     * @param metricImageId ID of the metric image.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesMetricImageIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer alertIid, @jakarta.annotation.Nonnull Integer metricImageId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesMetricImageIdRequestCreation(id, alertIid, metricImageId);
    }

    /**
     * List all metric images
     * Lists all metric images for a specified alert.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param alertIid Internal ID of the alert.
     * @return List&lt;APIEntitiesMetricImage&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer alertIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImages", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'alertIid' is set
        if (alertIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'alertIid' when calling getApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImages", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("alert_iid", alertIid);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesMetricImage>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/alert_management_alerts/{alert_iid}/metric_images", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all metric images
     * Lists all metric images for a specified alert.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param alertIid Internal ID of the alert.
     * @return List&lt;APIEntitiesMetricImage&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesMetricImage> getApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImages(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer alertIid) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMetricImage>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesRequestCreation(id, alertIid).body(localVarReturnType);
    }

    /**
     * List all metric images
     * Lists all metric images for a specified alert.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param alertIid Internal ID of the alert.
     * @return ResponseEntity&lt;List&lt;APIEntitiesMetricImage&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesMetricImage>> getApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer alertIid) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMetricImage>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesRequestCreation(id, alertIid).toEntity(localVarReturnType);
    }

    /**
     * List all metric images
     * Lists all metric images for a specified alert.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param alertIid Internal ID of the alert.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer alertIid) throws RestClientResponseException {
        return getApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesRequestCreation(id, alertIid);
    }

    /**
     * Upload a metric image
     * Uploads a metric image for a specified alert.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param alertIid Internal ID of the alert.
     * @param _file The image file to be uploaded
     * @param url The url to view more metric info
     * @param urlText A description of the image or URL
     * @return APIEntitiesMetricImage
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer alertIid, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String url, @jakarta.annotation.Nullable String urlText) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImages", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'alertIid' is set
        if (alertIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'alertIid' when calling postApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImages", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new RestClientResponseException("Missing the required parameter '_file' when calling postApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImages", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("alert_iid", alertIid);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        formParams.add("file", new FileSystemResource(_file));
        if (url != null) {
            formParams.add("url", url);
        }
        if (urlText != null) {
            formParams.add("url_text", urlText);
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesMetricImage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/alert_management_alerts/{alert_iid}/metric_images", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Upload a metric image
     * Uploads a metric image for a specified alert.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param alertIid Internal ID of the alert.
     * @param _file The image file to be uploaded
     * @param url The url to view more metric info
     * @param urlText A description of the image or URL
     * @return APIEntitiesMetricImage
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMetricImage postApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImages(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer alertIid, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String url, @jakarta.annotation.Nullable String urlText) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMetricImage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesRequestCreation(id, alertIid, _file, url, urlText).body(localVarReturnType);
    }

    /**
     * Upload a metric image
     * Uploads a metric image for a specified alert.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param alertIid Internal ID of the alert.
     * @param _file The image file to be uploaded
     * @param url The url to view more metric info
     * @param urlText A description of the image or URL
     * @return ResponseEntity&lt;APIEntitiesMetricImage&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMetricImage> postApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer alertIid, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String url, @jakarta.annotation.Nullable String urlText) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMetricImage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesRequestCreation(id, alertIid, _file, url, urlText).toEntity(localVarReturnType);
    }

    /**
     * Upload a metric image
     * Uploads a metric image for a specified alert.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param alertIid Internal ID of the alert.
     * @param _file The image file to be uploaded
     * @param url The url to view more metric info
     * @param urlText A description of the image or URL
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer alertIid, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String url, @jakarta.annotation.Nullable String urlText) throws RestClientResponseException {
        return postApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesRequestCreation(id, alertIid, _file, url, urlText);
    }

    /**
     * Workhorse authorize metric image file upload
     * Pre-upload authorization endpoint for metric images attached to alerts. Called by GitLab Workhorse.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param alertIid Internal ID of the alert.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesAuthorizeRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer alertIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'alertIid' is set
        if (alertIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'alertIid' when calling postApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("alert_iid", alertIid);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/alert_management_alerts/{alert_iid}/metric_images/authorize", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Workhorse authorize metric image file upload
     * Pre-upload authorization endpoint for metric images attached to alerts. Called by GitLab Workhorse.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param alertIid Internal ID of the alert.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesAuthorize(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer alertIid) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesAuthorizeRequestCreation(id, alertIid).body(localVarReturnType);
    }

    /**
     * Workhorse authorize metric image file upload
     * Pre-upload authorization endpoint for metric images attached to alerts. Called by GitLab Workhorse.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param alertIid Internal ID of the alert.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesAuthorizeWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer alertIid) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesAuthorizeRequestCreation(id, alertIid).toEntity(localVarReturnType);
    }

    /**
     * Workhorse authorize metric image file upload
     * Pre-upload authorization endpoint for metric images attached to alerts. Called by GitLab Workhorse.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param alertIid Internal ID of the alert.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesAuthorizeWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer alertIid) throws RestClientResponseException {
        return postApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesAuthorizeRequestCreation(id, alertIid);
    }

    /**
     * Update a metric image
     * Updates a specified metric image for an alert.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Unprocessable entity
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param alertIid Internal ID of the alert.
     * @param metricImageId ID of the metric image.
     * @param url The url to view more metric info
     * @param urlText A description of the image or URL
     * @return APIEntitiesMetricImage
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesMetricImageIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer alertIid, @jakarta.annotation.Nonnull Integer metricImageId, @jakarta.annotation.Nullable String url, @jakarta.annotation.Nullable String urlText) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesMetricImageId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'alertIid' is set
        if (alertIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'alertIid' when calling putApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesMetricImageId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'metricImageId' is set
        if (metricImageId == null) {
            throw new RestClientResponseException("Missing the required parameter 'metricImageId' when calling putApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesMetricImageId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("alert_iid", alertIid);
        pathParams.put("metric_image_id", metricImageId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (url != null) {
            formParams.add("url", url);
        }
        if (urlText != null) {
            formParams.add("url_text", urlText);
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesMetricImage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/alert_management_alerts/{alert_iid}/metric_images/{metric_image_id}", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a metric image
     * Updates a specified metric image for an alert.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Unprocessable entity
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param alertIid Internal ID of the alert.
     * @param metricImageId ID of the metric image.
     * @param url The url to view more metric info
     * @param urlText A description of the image or URL
     * @return APIEntitiesMetricImage
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMetricImage putApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesMetricImageId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer alertIid, @jakarta.annotation.Nonnull Integer metricImageId, @jakarta.annotation.Nullable String url, @jakarta.annotation.Nullable String urlText) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMetricImage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesMetricImageIdRequestCreation(id, alertIid, metricImageId, url, urlText).body(localVarReturnType);
    }

    /**
     * Update a metric image
     * Updates a specified metric image for an alert.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Unprocessable entity
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param alertIid Internal ID of the alert.
     * @param metricImageId ID of the metric image.
     * @param url The url to view more metric info
     * @param urlText A description of the image or URL
     * @return ResponseEntity&lt;APIEntitiesMetricImage&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMetricImage> putApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesMetricImageIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer alertIid, @jakarta.annotation.Nonnull Integer metricImageId, @jakarta.annotation.Nullable String url, @jakarta.annotation.Nullable String urlText) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMetricImage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesMetricImageIdRequestCreation(id, alertIid, metricImageId, url, urlText).toEntity(localVarReturnType);
    }

    /**
     * Update a metric image
     * Updates a specified metric image for an alert.
     * <p><b>200</b> - OK
     * <p><b>403</b> - Forbidden
     * <p><b>422</b> - Unprocessable entity
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param alertIid Internal ID of the alert.
     * @param metricImageId ID of the metric image.
     * @param url The url to view more metric info
     * @param urlText A description of the image or URL
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesMetricImageIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer alertIid, @jakarta.annotation.Nonnull Integer metricImageId, @jakarta.annotation.Nullable String url, @jakarta.annotation.Nullable String urlText) throws RestClientResponseException {
        return putApiV4ProjectsIdAlertManagementAlertsAlertIidMetricImagesMetricImageIdRequestCreation(id, alertIid, metricImageId, url, urlText);
    }
}
