package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.PutApiV4ElasticsearchIndexedNamespacesRollbackRequest;
import ai.gebo.gitlab.client.model.PutApiV4ElasticsearchIndexedNamespacesRolloutRequest;

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
public class AdvancedSearchRolloutApi {
    private ApiClient apiClient;

    public AdvancedSearchRolloutApi() {
        this(new ApiClient());
    }

    public AdvancedSearchRolloutApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Rollback namespaces to be indexed down to n%
     * This feature was introduced in GitLab 12.7.  **DEPRECATED**: This endpoint is deprecated and for GitLab.com use only.  This will only ever decrease the number of indexed namespaces. Providing a value higher than the current rolled out percentage will have no effect.  This percentage is never persisted but is used to calculate the number of namespaces to rollback. 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param putApiV4ElasticsearchIndexedNamespacesRollbackRequest The putApiV4ElasticsearchIndexedNamespacesRollbackRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ElasticsearchIndexedNamespacesRollbackRequestCreation(@jakarta.annotation.Nonnull PutApiV4ElasticsearchIndexedNamespacesRollbackRequest putApiV4ElasticsearchIndexedNamespacesRollbackRequest) throws RestClientResponseException {
        // verify the required parameter 'putApiV4ElasticsearchIndexedNamespacesRollbackRequest' is set
        if (putApiV4ElasticsearchIndexedNamespacesRollbackRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'putApiV4ElasticsearchIndexedNamespacesRollbackRequest' when calling putApiV4ElasticsearchIndexedNamespacesRollback", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/elasticsearch_indexed_namespaces/rollback", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ElasticsearchIndexedNamespacesRollbackRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Rollback namespaces to be indexed down to n%
     * This feature was introduced in GitLab 12.7.  **DEPRECATED**: This endpoint is deprecated and for GitLab.com use only.  This will only ever decrease the number of indexed namespaces. Providing a value higher than the current rolled out percentage will have no effect.  This percentage is never persisted but is used to calculate the number of namespaces to rollback. 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param putApiV4ElasticsearchIndexedNamespacesRollbackRequest The putApiV4ElasticsearchIndexedNamespacesRollbackRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4ElasticsearchIndexedNamespacesRollback(@jakarta.annotation.Nonnull PutApiV4ElasticsearchIndexedNamespacesRollbackRequest putApiV4ElasticsearchIndexedNamespacesRollbackRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4ElasticsearchIndexedNamespacesRollbackRequestCreation(putApiV4ElasticsearchIndexedNamespacesRollbackRequest).body(localVarReturnType);
    }

    /**
     * Rollback namespaces to be indexed down to n%
     * This feature was introduced in GitLab 12.7.  **DEPRECATED**: This endpoint is deprecated and for GitLab.com use only.  This will only ever decrease the number of indexed namespaces. Providing a value higher than the current rolled out percentage will have no effect.  This percentage is never persisted but is used to calculate the number of namespaces to rollback. 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param putApiV4ElasticsearchIndexedNamespacesRollbackRequest The putApiV4ElasticsearchIndexedNamespacesRollbackRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4ElasticsearchIndexedNamespacesRollbackWithHttpInfo(@jakarta.annotation.Nonnull PutApiV4ElasticsearchIndexedNamespacesRollbackRequest putApiV4ElasticsearchIndexedNamespacesRollbackRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ElasticsearchIndexedNamespacesRollbackRequestCreation(putApiV4ElasticsearchIndexedNamespacesRollbackRequest).toEntity(localVarReturnType);
    }

    /**
     * Rollback namespaces to be indexed down to n%
     * This feature was introduced in GitLab 12.7.  **DEPRECATED**: This endpoint is deprecated and for GitLab.com use only.  This will only ever decrease the number of indexed namespaces. Providing a value higher than the current rolled out percentage will have no effect.  This percentage is never persisted but is used to calculate the number of namespaces to rollback. 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param putApiV4ElasticsearchIndexedNamespacesRollbackRequest The putApiV4ElasticsearchIndexedNamespacesRollbackRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ElasticsearchIndexedNamespacesRollbackWithResponseSpec(@jakarta.annotation.Nonnull PutApiV4ElasticsearchIndexedNamespacesRollbackRequest putApiV4ElasticsearchIndexedNamespacesRollbackRequest) throws RestClientResponseException {
        return putApiV4ElasticsearchIndexedNamespacesRollbackRequestCreation(putApiV4ElasticsearchIndexedNamespacesRollbackRequest);
    }

    /**
     * Rollout namespaces to be indexed up to n%
     * This feature was introduced in GitLab 12.7.  **DEPRECATED**: This endpoint is deprecated and for GitLab.com use only.  This will only ever increase the number of indexed namespaces. Providing a value lower than the current rolled out percentage will have no effect.  This percentage is never persisted but is used to calculate the number of new namespaces to rollout.  If the same percentage is applied again at a later time, due to possible new namespaces being created during the period, some of them will also be indexed. Therefore you may expect that setting this to 10%, then waiting a month and setting to 10% again will trigger new namespaces to be added (i.e. 10% of the number of newly created namespaces in the last month within the given plan). 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param putApiV4ElasticsearchIndexedNamespacesRolloutRequest The putApiV4ElasticsearchIndexedNamespacesRolloutRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4ElasticsearchIndexedNamespacesRolloutRequestCreation(@jakarta.annotation.Nonnull PutApiV4ElasticsearchIndexedNamespacesRolloutRequest putApiV4ElasticsearchIndexedNamespacesRolloutRequest) throws RestClientResponseException {
        // verify the required parameter 'putApiV4ElasticsearchIndexedNamespacesRolloutRequest' is set
        if (putApiV4ElasticsearchIndexedNamespacesRolloutRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'putApiV4ElasticsearchIndexedNamespacesRolloutRequest' when calling putApiV4ElasticsearchIndexedNamespacesRollout", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/elasticsearch_indexed_namespaces/rollout", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4ElasticsearchIndexedNamespacesRolloutRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Rollout namespaces to be indexed up to n%
     * This feature was introduced in GitLab 12.7.  **DEPRECATED**: This endpoint is deprecated and for GitLab.com use only.  This will only ever increase the number of indexed namespaces. Providing a value lower than the current rolled out percentage will have no effect.  This percentage is never persisted but is used to calculate the number of new namespaces to rollout.  If the same percentage is applied again at a later time, due to possible new namespaces being created during the period, some of them will also be indexed. Therefore you may expect that setting this to 10%, then waiting a month and setting to 10% again will trigger new namespaces to be added (i.e. 10% of the number of newly created namespaces in the last month within the given plan). 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param putApiV4ElasticsearchIndexedNamespacesRolloutRequest The putApiV4ElasticsearchIndexedNamespacesRolloutRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void putApiV4ElasticsearchIndexedNamespacesRollout(@jakarta.annotation.Nonnull PutApiV4ElasticsearchIndexedNamespacesRolloutRequest putApiV4ElasticsearchIndexedNamespacesRolloutRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        putApiV4ElasticsearchIndexedNamespacesRolloutRequestCreation(putApiV4ElasticsearchIndexedNamespacesRolloutRequest).body(localVarReturnType);
    }

    /**
     * Rollout namespaces to be indexed up to n%
     * This feature was introduced in GitLab 12.7.  **DEPRECATED**: This endpoint is deprecated and for GitLab.com use only.  This will only ever increase the number of indexed namespaces. Providing a value lower than the current rolled out percentage will have no effect.  This percentage is never persisted but is used to calculate the number of new namespaces to rollout.  If the same percentage is applied again at a later time, due to possible new namespaces being created during the period, some of them will also be indexed. Therefore you may expect that setting this to 10%, then waiting a month and setting to 10% again will trigger new namespaces to be added (i.e. 10% of the number of newly created namespaces in the last month within the given plan). 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param putApiV4ElasticsearchIndexedNamespacesRolloutRequest The putApiV4ElasticsearchIndexedNamespacesRolloutRequest parameter
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putApiV4ElasticsearchIndexedNamespacesRolloutWithHttpInfo(@jakarta.annotation.Nonnull PutApiV4ElasticsearchIndexedNamespacesRolloutRequest putApiV4ElasticsearchIndexedNamespacesRolloutRequest) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4ElasticsearchIndexedNamespacesRolloutRequestCreation(putApiV4ElasticsearchIndexedNamespacesRolloutRequest).toEntity(localVarReturnType);
    }

    /**
     * Rollout namespaces to be indexed up to n%
     * This feature was introduced in GitLab 12.7.  **DEPRECATED**: This endpoint is deprecated and for GitLab.com use only.  This will only ever increase the number of indexed namespaces. Providing a value lower than the current rolled out percentage will have no effect.  This percentage is never persisted but is used to calculate the number of new namespaces to rollout.  If the same percentage is applied again at a later time, due to possible new namespaces being created during the period, some of them will also be indexed. Therefore you may expect that setting this to 10%, then waiting a month and setting to 10% again will trigger new namespaces to be added (i.e. 10% of the number of newly created namespaces in the last month within the given plan). 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param putApiV4ElasticsearchIndexedNamespacesRolloutRequest The putApiV4ElasticsearchIndexedNamespacesRolloutRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4ElasticsearchIndexedNamespacesRolloutWithResponseSpec(@jakarta.annotation.Nonnull PutApiV4ElasticsearchIndexedNamespacesRolloutRequest putApiV4ElasticsearchIndexedNamespacesRolloutRequest) throws RestClientResponseException {
        return putApiV4ElasticsearchIndexedNamespacesRolloutRequestCreation(putApiV4ElasticsearchIndexedNamespacesRolloutRequest);
    }
}
