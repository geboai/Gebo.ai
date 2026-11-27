package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesIssuableMetricImage;
import java.io.File;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PutApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageIdRequest;

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
public class MetricImagesApi {
    private ApiClient apiClient;

    public MetricImagesApi() {
        this(new ApiClient());
    }

    public MetricImagesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a metric image from an incident
     * Deletes a metric image from an incident.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param metricImageId ID of the metric image.
     * @param issueIid The internal ID of an issuable
     * @return APIEntitiesIssuableMetricImage
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer metricImageId, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'metricImageId' is set
        if (metricImageId == null) {
            throw new RestClientResponseException("Missing the required parameter 'metricImageId' when calling deleteApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueIid' is set
        if (issueIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueIid' when calling deleteApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("metric_image_id", metricImageId);
        pathParams.put("issue_iid", issueIid);

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

        ParameterizedTypeReference<APIEntitiesIssuableMetricImage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{issue_iid}/metric_images/{metric_image_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a metric image from an incident
     * Deletes a metric image from an incident.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param metricImageId ID of the metric image.
     * @param issueIid The internal ID of an issuable
     * @return APIEntitiesIssuableMetricImage
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesIssuableMetricImage deleteApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer metricImageId, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIssuableMetricImage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageIdRequestCreation(id, metricImageId, issueIid).body(localVarReturnType);
    }

    /**
     * Delete a metric image from an incident
     * Deletes a metric image from an incident.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param metricImageId ID of the metric image.
     * @param issueIid The internal ID of an issuable
     * @return ResponseEntity&lt;APIEntitiesIssuableMetricImage&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesIssuableMetricImage> deleteApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer metricImageId, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIssuableMetricImage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageIdRequestCreation(id, metricImageId, issueIid).toEntity(localVarReturnType);
    }

    /**
     * Delete a metric image from an incident
     * Deletes a metric image from an incident.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param metricImageId ID of the metric image.
     * @param issueIid The internal ID of an issuable
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer metricImageId, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        return deleteApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageIdRequestCreation(id, metricImageId, issueIid);
    }

    /**
     * List all metric images for an incident
     * Lists all metric images for an incident.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @return List&lt;APIEntitiesIssuableMetricImage&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdIssuesIssueIidMetricImagesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdIssuesIssueIidMetricImages", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueIid' is set
        if (issueIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueIid' when calling getApiV4ProjectsIdIssuesIssueIidMetricImages", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("issue_iid", issueIid);

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

        ParameterizedTypeReference<List<APIEntitiesIssuableMetricImage>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{issue_iid}/metric_images", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all metric images for an incident
     * Lists all metric images for an incident.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @return List&lt;APIEntitiesIssuableMetricImage&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesIssuableMetricImage> getApiV4ProjectsIdIssuesIssueIidMetricImages(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesIssuableMetricImage>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesIssueIidMetricImagesRequestCreation(id, issueIid).body(localVarReturnType);
    }

    /**
     * List all metric images for an incident
     * Lists all metric images for an incident.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @return ResponseEntity&lt;List&lt;APIEntitiesIssuableMetricImage&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesIssuableMetricImage>> getApiV4ProjectsIdIssuesIssueIidMetricImagesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesIssuableMetricImage>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesIssueIidMetricImagesRequestCreation(id, issueIid).toEntity(localVarReturnType);
    }

    /**
     * List all metric images for an incident
     * Lists all metric images for an incident.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdIssuesIssueIidMetricImagesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        return getApiV4ProjectsIdIssuesIssueIidMetricImagesRequestCreation(id, issueIid);
    }

    /**
     * Upload a metric image for an incident
     * Uploads a screenshot of metric charts for an incident. Available only for incidents.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @param _file The image file to be uploaded
     * @param url URL to view more metric information.
     * @param urlText Description of the image or URL.
     * @return APIEntitiesIssuableMetricImage
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdIssuesIssueIidMetricImagesRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String url, @jakarta.annotation.Nullable String urlText) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdIssuesIssueIidMetricImages", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueIid' is set
        if (issueIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueIid' when calling postApiV4ProjectsIdIssuesIssueIidMetricImages", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new RestClientResponseException("Missing the required parameter '_file' when calling postApiV4ProjectsIdIssuesIssueIidMetricImages", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("issue_iid", issueIid);

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

        ParameterizedTypeReference<APIEntitiesIssuableMetricImage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{issue_iid}/metric_images", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Upload a metric image for an incident
     * Uploads a screenshot of metric charts for an incident. Available only for incidents.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @param _file The image file to be uploaded
     * @param url URL to view more metric information.
     * @param urlText Description of the image or URL.
     * @return APIEntitiesIssuableMetricImage
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesIssuableMetricImage postApiV4ProjectsIdIssuesIssueIidMetricImages(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String url, @jakarta.annotation.Nullable String urlText) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIssuableMetricImage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdIssuesIssueIidMetricImagesRequestCreation(id, issueIid, _file, url, urlText).body(localVarReturnType);
    }

    /**
     * Upload a metric image for an incident
     * Uploads a screenshot of metric charts for an incident. Available only for incidents.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @param _file The image file to be uploaded
     * @param url URL to view more metric information.
     * @param urlText Description of the image or URL.
     * @return ResponseEntity&lt;APIEntitiesIssuableMetricImage&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesIssuableMetricImage> postApiV4ProjectsIdIssuesIssueIidMetricImagesWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String url, @jakarta.annotation.Nullable String urlText) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIssuableMetricImage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdIssuesIssueIidMetricImagesRequestCreation(id, issueIid, _file, url, urlText).toEntity(localVarReturnType);
    }

    /**
     * Upload a metric image for an incident
     * Uploads a screenshot of metric charts for an incident. Available only for incidents.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @param _file The image file to be uploaded
     * @param url URL to view more metric information.
     * @param urlText Description of the image or URL.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdIssuesIssueIidMetricImagesWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nullable File _file, @jakarta.annotation.Nullable String url, @jakarta.annotation.Nullable String urlText) throws RestClientResponseException {
        return postApiV4ProjectsIdIssuesIssueIidMetricImagesRequestCreation(id, issueIid, _file, url, urlText);
    }

    /**
     * Authorize metric image upload
     * Authorizes uploading a metric image for a specified incident.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdIssuesIssueIidMetricImagesAuthorizeRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdIssuesIssueIidMetricImagesAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueIid' is set
        if (issueIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueIid' when calling postApiV4ProjectsIdIssuesIssueIidMetricImagesAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("issue_iid", issueIid);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{issue_iid}/metric_images/authorize", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Authorize metric image upload
     * Authorizes uploading a metric image for a specified incident.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ProjectsIdIssuesIssueIidMetricImagesAuthorize(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectsIdIssuesIssueIidMetricImagesAuthorizeRequestCreation(id, issueIid).body(localVarReturnType);
    }

    /**
     * Authorize metric image upload
     * Authorizes uploading a metric image for a specified incident.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ProjectsIdIssuesIssueIidMetricImagesAuthorizeWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdIssuesIssueIidMetricImagesAuthorizeRequestCreation(id, issueIid).toEntity(localVarReturnType);
    }

    /**
     * Authorize metric image upload
     * Authorizes uploading a metric image for a specified incident.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of an issuable
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdIssuesIssueIidMetricImagesAuthorizeWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        return postApiV4ProjectsIdIssuesIssueIidMetricImagesAuthorizeRequestCreation(id, issueIid);
    }

    /**
     * Update a metric image for an incident
     * Updates a metric image for an incident.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param metricImageId ID of the metric image.
     * @param issueIid The internal ID of an issuable
     * @param putApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageIdRequest The putApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageIdRequest parameter
     * @return APIEntitiesIssuableMetricImage
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer metricImageId, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nullable PutApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageIdRequest putApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageIdRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'metricImageId' is set
        if (metricImageId == null) {
            throw new RestClientResponseException("Missing the required parameter 'metricImageId' when calling putApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueIid' is set
        if (issueIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueIid' when calling putApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("metric_image_id", metricImageId);
        pathParams.put("issue_iid", issueIid);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesIssuableMetricImage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{issue_iid}/metric_images/{metric_image_id}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageIdRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a metric image for an incident
     * Updates a metric image for an incident.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param metricImageId ID of the metric image.
     * @param issueIid The internal ID of an issuable
     * @param putApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageIdRequest The putApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageIdRequest parameter
     * @return APIEntitiesIssuableMetricImage
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesIssuableMetricImage putApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer metricImageId, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nullable PutApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageIdRequest putApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIssuableMetricImage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageIdRequestCreation(id, metricImageId, issueIid, putApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageIdRequest).body(localVarReturnType);
    }

    /**
     * Update a metric image for an incident
     * Updates a metric image for an incident.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param metricImageId ID of the metric image.
     * @param issueIid The internal ID of an issuable
     * @param putApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageIdRequest The putApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageIdRequest parameter
     * @return ResponseEntity&lt;APIEntitiesIssuableMetricImage&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesIssuableMetricImage> putApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer metricImageId, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nullable PutApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageIdRequest putApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageIdRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIssuableMetricImage> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageIdRequestCreation(id, metricImageId, issueIid, putApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageIdRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a metric image for an incident
     * Updates a metric image for an incident.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param metricImageId ID of the metric image.
     * @param issueIid The internal ID of an issuable
     * @param putApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageIdRequest The putApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageIdRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer metricImageId, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nullable PutApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageIdRequest putApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageIdRequest) throws RestClientResponseException {
        return putApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageIdRequestCreation(id, metricImageId, issueIid, putApiV4ProjectsIdIssuesIssueIidMetricImagesMetricImageIdRequest);
    }
}
