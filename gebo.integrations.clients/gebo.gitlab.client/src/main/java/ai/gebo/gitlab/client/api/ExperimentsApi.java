package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesExperiment;
import ai.gebo.gitlab.client.model.APIEntitiesExperimentsAssignment;
import ai.gebo.gitlab.client.model.PostApiV4ExperimentsExperimentNameAssignmentsRequest;

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
public class ExperimentsApi {
    private ApiClient apiClient;

    public ExperimentsApi() {
        this(new ApiClient());
    }

    public ExperimentsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Clear a variant assignment for an experiment context
     * Removes the cached variant assignment for the given experiment and context, returning the actor to normal rollout assignment. Defaults to current user if no context is provided.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param experimentName The experiment name
     * @param context Context parameters (user, namespace, project). An actor context is resolved from the user. Defaults to current user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ExperimentsExperimentNameAssignmentsRequestCreation(@jakarta.annotation.Nonnull String experimentName, @jakarta.annotation.Nullable Object context) throws RestClientResponseException {
        // verify the required parameter 'experimentName' is set
        if (experimentName == null) {
            throw new RestClientResponseException("Missing the required parameter 'experimentName' when calling deleteApiV4ExperimentsExperimentNameAssignments", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("experiment_name", experimentName);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "context", context));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/experiments/{experiment_name}/assignments", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Clear a variant assignment for an experiment context
     * Removes the cached variant assignment for the given experiment and context, returning the actor to normal rollout assignment. Defaults to current user if no context is provided.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param experimentName The experiment name
     * @param context Context parameters (user, namespace, project). An actor context is resolved from the user. Defaults to current user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ExperimentsExperimentNameAssignments(@jakarta.annotation.Nonnull String experimentName, @jakarta.annotation.Nullable Object context) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ExperimentsExperimentNameAssignmentsRequestCreation(experimentName, context).body(localVarReturnType);
    }

    /**
     * Clear a variant assignment for an experiment context
     * Removes the cached variant assignment for the given experiment and context, returning the actor to normal rollout assignment. Defaults to current user if no context is provided.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param experimentName The experiment name
     * @param context Context parameters (user, namespace, project). An actor context is resolved from the user. Defaults to current user.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ExperimentsExperimentNameAssignmentsWithHttpInfo(@jakarta.annotation.Nonnull String experimentName, @jakarta.annotation.Nullable Object context) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ExperimentsExperimentNameAssignmentsRequestCreation(experimentName, context).toEntity(localVarReturnType);
    }

    /**
     * Clear a variant assignment for an experiment context
     * Removes the cached variant assignment for the given experiment and context, returning the actor to normal rollout assignment. Defaults to current user if no context is provided.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param experimentName The experiment name
     * @param context Context parameters (user, namespace, project). An actor context is resolved from the user. Defaults to current user.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ExperimentsExperimentNameAssignmentsWithResponseSpec(@jakarta.annotation.Nonnull String experimentName, @jakarta.annotation.Nullable Object context) throws RestClientResponseException {
        return deleteApiV4ExperimentsExperimentNameAssignmentsRequestCreation(experimentName, context);
    }

    /**
     * Delete cached assignments for an experiment
     * Removes all cached variant assignments for the experiment with the given cache key. Useful for cleaning up completed experiments whose code has been removed from the codebase. The &#x60;name&#x60; value is used directly as the cache key, so this endpoint clears any matching cache entry, even one that does not belong to a currently defined experiment.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param name Cache key of the experiment to clear.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ExperimentsNameCacheRequestCreation(@jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling deleteApiV4ExperimentsNameCache", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("name", name);

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
        return apiClient.invokeAPI("/api/v4/experiments/{name}/cache", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete cached assignments for an experiment
     * Removes all cached variant assignments for the experiment with the given cache key. Useful for cleaning up completed experiments whose code has been removed from the codebase. The &#x60;name&#x60; value is used directly as the cache key, so this endpoint clears any matching cache entry, even one that does not belong to a currently defined experiment.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param name Cache key of the experiment to clear.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4ExperimentsNameCache(@jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4ExperimentsNameCacheRequestCreation(name).body(localVarReturnType);
    }

    /**
     * Delete cached assignments for an experiment
     * Removes all cached variant assignments for the experiment with the given cache key. Useful for cleaning up completed experiments whose code has been removed from the codebase. The &#x60;name&#x60; value is used directly as the cache key, so this endpoint clears any matching cache entry, even one that does not belong to a currently defined experiment.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param name Cache key of the experiment to clear.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4ExperimentsNameCacheWithHttpInfo(@jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ExperimentsNameCacheRequestCreation(name).toEntity(localVarReturnType);
    }

    /**
     * Delete cached assignments for an experiment
     * Removes all cached variant assignments for the experiment with the given cache key. Useful for cleaning up completed experiments whose code has been removed from the codebase. The &#x60;name&#x60; value is used directly as the cache key, so this endpoint clears any matching cache entry, even one that does not belong to a currently defined experiment.
     * <p><b>204</b> - No Content
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param name Cache key of the experiment to clear.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ExperimentsNameCacheWithResponseSpec(@jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        return deleteApiV4ExperimentsNameCacheRequestCreation(name);
    }

    /**
     * List all experiments
     * Lists all experiments on the GitLab instance. Each experiment has an &#x60;enabled&#x60; status that indicates whether the experiment is enabled globally, or only in specific contexts. Each experiment also has a &#x60;context&#x60; array with the context keys the experiment declares: &#x60;user&#x60;, &#x60;namespace&#x60;, &#x60;project&#x60;, or &#x60;actor&#x60;. The array is empty for experiments that declare no context keys. Pass these keys when you force, read, or clear a variant assignment. For an &#x60;actor&#x60; key, pass the &#x60;context[user]&#x60; parameter, because the actor is resolved from the user.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return List&lt;APIEntitiesExperiment&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ExperimentsRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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

        ParameterizedTypeReference<List<APIEntitiesExperiment>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/experiments", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all experiments
     * Lists all experiments on the GitLab instance. Each experiment has an &#x60;enabled&#x60; status that indicates whether the experiment is enabled globally, or only in specific contexts. Each experiment also has a &#x60;context&#x60; array with the context keys the experiment declares: &#x60;user&#x60;, &#x60;namespace&#x60;, &#x60;project&#x60;, or &#x60;actor&#x60;. The array is empty for experiments that declare no context keys. Pass these keys when you force, read, or clear a variant assignment. For an &#x60;actor&#x60; key, pass the &#x60;context[user]&#x60; parameter, because the actor is resolved from the user.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return List&lt;APIEntitiesExperiment&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesExperiment> getApiV4Experiments() throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesExperiment>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ExperimentsRequestCreation().body(localVarReturnType);
    }

    /**
     * List all experiments
     * Lists all experiments on the GitLab instance. Each experiment has an &#x60;enabled&#x60; status that indicates whether the experiment is enabled globally, or only in specific contexts. Each experiment also has a &#x60;context&#x60; array with the context keys the experiment declares: &#x60;user&#x60;, &#x60;namespace&#x60;, &#x60;project&#x60;, or &#x60;actor&#x60;. The array is empty for experiments that declare no context keys. Pass these keys when you force, read, or clear a variant assignment. For an &#x60;actor&#x60; key, pass the &#x60;context[user]&#x60; parameter, because the actor is resolved from the user.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return ResponseEntity&lt;List&lt;APIEntitiesExperiment&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesExperiment>> getApiV4ExperimentsWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesExperiment>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ExperimentsRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * List all experiments
     * Lists all experiments on the GitLab instance. Each experiment has an &#x60;enabled&#x60; status that indicates whether the experiment is enabled globally, or only in specific contexts. Each experiment also has a &#x60;context&#x60; array with the context keys the experiment declares: &#x60;user&#x60;, &#x60;namespace&#x60;, &#x60;project&#x60;, or &#x60;actor&#x60;. The array is empty for experiments that declare no context keys. Pass these keys when you force, read, or clear a variant assignment. For an &#x60;actor&#x60; key, pass the &#x60;context[user]&#x60; parameter, because the actor is resolved from the user.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ExperimentsWithResponseSpec() throws RestClientResponseException {
        return getApiV4ExperimentsRequestCreation();
    }

    /**
     * Get current variant assignment for an experiment context
     * Returns the currently cached variant assignment for the given experiment and context. Defaults to current user if no context is provided. If the experiment declares multiple context keys, pass every declared key. The fallback to the authenticated user applies only when you omit the &#x60;context&#x60; parameter, and only for the &#x60;user&#x60; and &#x60;actor&#x60; keys. The &#x60;namespace&#x60; and &#x60;project&#x60; keys must always be passed.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param experimentName The experiment name
     * @param context Context parameters (user, namespace, project). Pass every key the experiment declares in &#x60;context_keys&#x60;. Experiments can declare multiple keys. An actor context is resolved from the user. The user defaults to the current user.
     * @return APIEntitiesExperimentsAssignment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ExperimentsExperimentNameAssignmentsRequestCreation(@jakarta.annotation.Nonnull String experimentName, @jakarta.annotation.Nullable Object context) throws RestClientResponseException {
        // verify the required parameter 'experimentName' is set
        if (experimentName == null) {
            throw new RestClientResponseException("Missing the required parameter 'experimentName' when calling getApiV4ExperimentsExperimentNameAssignments", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("experiment_name", experimentName);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "context", context));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesExperimentsAssignment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/experiments/{experiment_name}/assignments", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get current variant assignment for an experiment context
     * Returns the currently cached variant assignment for the given experiment and context. Defaults to current user if no context is provided. If the experiment declares multiple context keys, pass every declared key. The fallback to the authenticated user applies only when you omit the &#x60;context&#x60; parameter, and only for the &#x60;user&#x60; and &#x60;actor&#x60; keys. The &#x60;namespace&#x60; and &#x60;project&#x60; keys must always be passed.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param experimentName The experiment name
     * @param context Context parameters (user, namespace, project). Pass every key the experiment declares in &#x60;context_keys&#x60;. Experiments can declare multiple keys. An actor context is resolved from the user. The user defaults to the current user.
     * @return APIEntitiesExperimentsAssignment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesExperimentsAssignment getApiV4ExperimentsExperimentNameAssignments(@jakarta.annotation.Nonnull String experimentName, @jakarta.annotation.Nullable Object context) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesExperimentsAssignment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ExperimentsExperimentNameAssignmentsRequestCreation(experimentName, context).body(localVarReturnType);
    }

    /**
     * Get current variant assignment for an experiment context
     * Returns the currently cached variant assignment for the given experiment and context. Defaults to current user if no context is provided. If the experiment declares multiple context keys, pass every declared key. The fallback to the authenticated user applies only when you omit the &#x60;context&#x60; parameter, and only for the &#x60;user&#x60; and &#x60;actor&#x60; keys. The &#x60;namespace&#x60; and &#x60;project&#x60; keys must always be passed.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param experimentName The experiment name
     * @param context Context parameters (user, namespace, project). Pass every key the experiment declares in &#x60;context_keys&#x60;. Experiments can declare multiple keys. An actor context is resolved from the user. The user defaults to the current user.
     * @return ResponseEntity&lt;APIEntitiesExperimentsAssignment&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesExperimentsAssignment> getApiV4ExperimentsExperimentNameAssignmentsWithHttpInfo(@jakarta.annotation.Nonnull String experimentName, @jakarta.annotation.Nullable Object context) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesExperimentsAssignment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ExperimentsExperimentNameAssignmentsRequestCreation(experimentName, context).toEntity(localVarReturnType);
    }

    /**
     * Get current variant assignment for an experiment context
     * Returns the currently cached variant assignment for the given experiment and context. Defaults to current user if no context is provided. If the experiment declares multiple context keys, pass every declared key. The fallback to the authenticated user applies only when you omit the &#x60;context&#x60; parameter, and only for the &#x60;user&#x60; and &#x60;actor&#x60; keys. The &#x60;namespace&#x60; and &#x60;project&#x60; keys must always be passed.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param experimentName The experiment name
     * @param context Context parameters (user, namespace, project). Pass every key the experiment declares in &#x60;context_keys&#x60;. Experiments can declare multiple keys. An actor context is resolved from the user. The user defaults to the current user.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ExperimentsExperimentNameAssignmentsWithResponseSpec(@jakarta.annotation.Nonnull String experimentName, @jakarta.annotation.Nullable Object context) throws RestClientResponseException {
        return getApiV4ExperimentsExperimentNameAssignmentsRequestCreation(experimentName, context);
    }

    /**
     * Force a variant assignment for an experiment context
     * Forces a specific variant assignment for the given experiment and context. The assignment is stored in Redis cache and will persist until overwritten. Defaults to current user if no context is provided.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param experimentName The experiment name
     * @param postApiV4ExperimentsExperimentNameAssignmentsRequest The postApiV4ExperimentsExperimentNameAssignmentsRequest parameter
     * @return APIEntitiesExperimentsAssignment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ExperimentsExperimentNameAssignmentsRequestCreation(@jakarta.annotation.Nonnull String experimentName, @jakarta.annotation.Nonnull PostApiV4ExperimentsExperimentNameAssignmentsRequest postApiV4ExperimentsExperimentNameAssignmentsRequest) throws RestClientResponseException {
        // verify the required parameter 'experimentName' is set
        if (experimentName == null) {
            throw new RestClientResponseException("Missing the required parameter 'experimentName' when calling postApiV4ExperimentsExperimentNameAssignments", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ExperimentsExperimentNameAssignmentsRequest' is set
        if (postApiV4ExperimentsExperimentNameAssignmentsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ExperimentsExperimentNameAssignmentsRequest' when calling postApiV4ExperimentsExperimentNameAssignments", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("experiment_name", experimentName);

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

        ParameterizedTypeReference<APIEntitiesExperimentsAssignment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/experiments/{experiment_name}/assignments", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ExperimentsExperimentNameAssignmentsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Force a variant assignment for an experiment context
     * Forces a specific variant assignment for the given experiment and context. The assignment is stored in Redis cache and will persist until overwritten. Defaults to current user if no context is provided.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param experimentName The experiment name
     * @param postApiV4ExperimentsExperimentNameAssignmentsRequest The postApiV4ExperimentsExperimentNameAssignmentsRequest parameter
     * @return APIEntitiesExperimentsAssignment
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesExperimentsAssignment postApiV4ExperimentsExperimentNameAssignments(@jakarta.annotation.Nonnull String experimentName, @jakarta.annotation.Nonnull PostApiV4ExperimentsExperimentNameAssignmentsRequest postApiV4ExperimentsExperimentNameAssignmentsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesExperimentsAssignment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ExperimentsExperimentNameAssignmentsRequestCreation(experimentName, postApiV4ExperimentsExperimentNameAssignmentsRequest).body(localVarReturnType);
    }

    /**
     * Force a variant assignment for an experiment context
     * Forces a specific variant assignment for the given experiment and context. The assignment is stored in Redis cache and will persist until overwritten. Defaults to current user if no context is provided.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param experimentName The experiment name
     * @param postApiV4ExperimentsExperimentNameAssignmentsRequest The postApiV4ExperimentsExperimentNameAssignmentsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesExperimentsAssignment&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesExperimentsAssignment> postApiV4ExperimentsExperimentNameAssignmentsWithHttpInfo(@jakarta.annotation.Nonnull String experimentName, @jakarta.annotation.Nonnull PostApiV4ExperimentsExperimentNameAssignmentsRequest postApiV4ExperimentsExperimentNameAssignmentsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesExperimentsAssignment> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ExperimentsExperimentNameAssignmentsRequestCreation(experimentName, postApiV4ExperimentsExperimentNameAssignmentsRequest).toEntity(localVarReturnType);
    }

    /**
     * Force a variant assignment for an experiment context
     * Forces a specific variant assignment for the given experiment and context. The assignment is stored in Redis cache and will persist until overwritten. Defaults to current user if no context is provided.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param experimentName The experiment name
     * @param postApiV4ExperimentsExperimentNameAssignmentsRequest The postApiV4ExperimentsExperimentNameAssignmentsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ExperimentsExperimentNameAssignmentsWithResponseSpec(@jakarta.annotation.Nonnull String experimentName, @jakarta.annotation.Nonnull PostApiV4ExperimentsExperimentNameAssignmentsRequest postApiV4ExperimentsExperimentNameAssignmentsRequest) throws RestClientResponseException {
        return postApiV4ExperimentsExperimentNameAssignmentsRequestCreation(experimentName, postApiV4ExperimentsExperimentNameAssignmentsRequest);
    }
}
