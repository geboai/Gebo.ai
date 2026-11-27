package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesSidekiqConcurrencyLimit;
import ai.gebo.gitlab.client.model.PutApiV4AdminSidekiqConcurrencyLimitsWorkerClassRequest;

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
public class SidekiqApi {
    private ApiClient apiClient;

    public SidekiqApi() {
        this(new ApiClient());
    }

    public SidekiqApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a Sidekiq worker concurrency limit override
     * Clears the temporary override and returns the updated concurrency-limit status. Restores the adaptive limit if present, otherwise the worker default. This feature was introduced in GitLab 19.6.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Administrator access required
     * <p><b>404</b> - Worker not found
     * <p><b>400</b> - Bad Request
     * @param workerClass Registered Sidekiq worker class name.
     * @return APIEntitiesSidekiqConcurrencyLimit
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4AdminSidekiqConcurrencyLimitsWorkerClassRequestCreation(@jakarta.annotation.Nonnull String workerClass) throws RestClientResponseException {
        // verify the required parameter 'workerClass' is set
        if (workerClass == null) {
            throw new RestClientResponseException("Missing the required parameter 'workerClass' when calling deleteApiV4AdminSidekiqConcurrencyLimitsWorkerClass", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("worker_class", workerClass);

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

        ParameterizedTypeReference<APIEntitiesSidekiqConcurrencyLimit> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/sidekiq/concurrency_limits/{worker_class}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a Sidekiq worker concurrency limit override
     * Clears the temporary override and returns the updated concurrency-limit status. Restores the adaptive limit if present, otherwise the worker default. This feature was introduced in GitLab 19.6.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Administrator access required
     * <p><b>404</b> - Worker not found
     * <p><b>400</b> - Bad Request
     * @param workerClass Registered Sidekiq worker class name.
     * @return APIEntitiesSidekiqConcurrencyLimit
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesSidekiqConcurrencyLimit deleteApiV4AdminSidekiqConcurrencyLimitsWorkerClass(@jakarta.annotation.Nonnull String workerClass) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSidekiqConcurrencyLimit> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4AdminSidekiqConcurrencyLimitsWorkerClassRequestCreation(workerClass).body(localVarReturnType);
    }

    /**
     * Delete a Sidekiq worker concurrency limit override
     * Clears the temporary override and returns the updated concurrency-limit status. Restores the adaptive limit if present, otherwise the worker default. This feature was introduced in GitLab 19.6.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Administrator access required
     * <p><b>404</b> - Worker not found
     * <p><b>400</b> - Bad Request
     * @param workerClass Registered Sidekiq worker class name.
     * @return ResponseEntity&lt;APIEntitiesSidekiqConcurrencyLimit&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesSidekiqConcurrencyLimit> deleteApiV4AdminSidekiqConcurrencyLimitsWorkerClassWithHttpInfo(@jakarta.annotation.Nonnull String workerClass) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSidekiqConcurrencyLimit> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4AdminSidekiqConcurrencyLimitsWorkerClassRequestCreation(workerClass).toEntity(localVarReturnType);
    }

    /**
     * Delete a Sidekiq worker concurrency limit override
     * Clears the temporary override and returns the updated concurrency-limit status. Restores the adaptive limit if present, otherwise the worker default. This feature was introduced in GitLab 19.6.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Administrator access required
     * <p><b>404</b> - Worker not found
     * <p><b>400</b> - Bad Request
     * @param workerClass Registered Sidekiq worker class name.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4AdminSidekiqConcurrencyLimitsWorkerClassWithResponseSpec(@jakarta.annotation.Nonnull String workerClass) throws RestClientResponseException {
        return deleteApiV4AdminSidekiqConcurrencyLimitsWorkerClassRequestCreation(workerClass);
    }

    /**
     * Delete jobs from a Sidekiq queue
     * Deletes jobs from a Sidekiq queue that match the specified metadata. The response has three fields: &#x60;deleted_jobs&#x60; is the number of jobs deleted by the request, &#x60;queue_size&#x60; is the remaining size of the queue after the request, and &#x60;completed&#x60; is whether the request processed the entire queue in time. If &#x60;completed&#x60; is &#x60;false&#x60;, retrying with the same parameters can delete more jobs, including jobs added after the first request. Only available to administrators. At least one of 32 related parameters must be provided.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param queueName Name of the queue to delete jobs from.
     * @param organizationId Metadata key to match
     * @param user Username of the user who scheduled the jobs.
     * @param userId Metadata key to match
     * @param glUserId Metadata key to match
     * @param scopedUser Metadata key to match
     * @param scopedUserId Metadata key to match
     * @param project Full path of the project where the jobs were scheduled from.
     * @param rootNamespace Root namespace of the project.
     * @param glRootNamespaceId Metadata key to match
     * @param clientId Metadata key to match
     * @param callerId Endpoint or background job that scheduled the job, for example &#x60;ProjectsController#create&#x60;, &#x60;/api/:version/projects/:id&#x60;, or &#x60;PostReceive&#x60;.
     * @param remoteIp Metadata key to match
     * @param jobId Metadata key to match
     * @param pipelineId Metadata key to match
     * @param relatedClass Metadata key to match
     * @param featureCategory Feature category of the background job, for example &#x60;team_planning&#x60; or &#x60;code_review&#x60;.
     * @param artifactSize Metadata key to match
     * @param artifactUsedCdn Metadata key to match
     * @param artifactsDependenciesSize Metadata key to match
     * @param artifactsDependenciesCount Metadata key to match
     * @param rootCallerId Metadata key to match
     * @param mergeActionStatus Metadata key to match
     * @param bulkImportEntityId Metadata key to match
     * @param sidekiqDestinationShardRedis Metadata key to match
     * @param kubernetesAgentId Metadata key to match
     * @param mvccManifest Metadata key to match
     * @param clientType Kind of client whose request scheduled the jobs, for example &#x60;browser&#x60;, &#x60;ide&#x60;, or &#x60;mobile&#x60;.
     * @param clientName Client whose request scheduled the jobs, for example &#x60;vscode&#x60; or &#x60;gitlab-mobile-ios&#x60;.
     * @param clientVersion Release of the client whose request scheduled the jobs, for example &#x60;6.21.0&#x60; or &#x60;1.2.0&#x60;.
     * @param subscriptionPlan Subscription plan of the root namespace. GitLab.com only.
     * @param aiResource Metadata key to match
     * @param workerClass Class of the background job worker, for example &#x60;PostReceive&#x60; or &#x60;MergeWorker&#x60;.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4AdminSidekiqQueuesQueueNameRequestCreation(@jakarta.annotation.Nonnull String queueName, @jakarta.annotation.Nullable String organizationId, @jakarta.annotation.Nullable String user, @jakarta.annotation.Nullable String userId, @jakarta.annotation.Nullable String glUserId, @jakarta.annotation.Nullable String scopedUser, @jakarta.annotation.Nullable String scopedUserId, @jakarta.annotation.Nullable String project, @jakarta.annotation.Nullable String rootNamespace, @jakarta.annotation.Nullable String glRootNamespaceId, @jakarta.annotation.Nullable String clientId, @jakarta.annotation.Nullable String callerId, @jakarta.annotation.Nullable String remoteIp, @jakarta.annotation.Nullable String jobId, @jakarta.annotation.Nullable String pipelineId, @jakarta.annotation.Nullable String relatedClass, @jakarta.annotation.Nullable String featureCategory, @jakarta.annotation.Nullable String artifactSize, @jakarta.annotation.Nullable String artifactUsedCdn, @jakarta.annotation.Nullable String artifactsDependenciesSize, @jakarta.annotation.Nullable String artifactsDependenciesCount, @jakarta.annotation.Nullable String rootCallerId, @jakarta.annotation.Nullable String mergeActionStatus, @jakarta.annotation.Nullable String bulkImportEntityId, @jakarta.annotation.Nullable String sidekiqDestinationShardRedis, @jakarta.annotation.Nullable String kubernetesAgentId, @jakarta.annotation.Nullable String mvccManifest, @jakarta.annotation.Nullable String clientType, @jakarta.annotation.Nullable String clientName, @jakarta.annotation.Nullable String clientVersion, @jakarta.annotation.Nullable String subscriptionPlan, @jakarta.annotation.Nullable String aiResource, @jakarta.annotation.Nullable String workerClass) throws RestClientResponseException {
        // verify the required parameter 'queueName' is set
        if (queueName == null) {
            throw new RestClientResponseException("Missing the required parameter 'queueName' when calling deleteApiV4AdminSidekiqQueuesQueueName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("queue_name", queueName);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "organization_id", organizationId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "user", user));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "user_id", userId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "gl_user_id", glUserId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "scoped_user", scopedUser));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "scoped_user_id", scopedUserId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "project", project));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "root_namespace", rootNamespace));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "gl_root_namespace_id", glRootNamespaceId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "client_id", clientId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "caller_id", callerId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "remote_ip", remoteIp));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "job_id", jobId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "pipeline_id", pipelineId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "related_class", relatedClass));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "feature_category", featureCategory));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "artifact_size", artifactSize));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "artifact_used_cdn", artifactUsedCdn));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "artifacts_dependencies_size", artifactsDependenciesSize));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "artifacts_dependencies_count", artifactsDependenciesCount));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "root_caller_id", rootCallerId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "merge_action_status", mergeActionStatus));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "bulk_import_entity_id", bulkImportEntityId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sidekiq_destination_shard_redis", sidekiqDestinationShardRedis));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "kubernetes_agent_id", kubernetesAgentId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "mvcc_manifest", mvccManifest));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "client_type", clientType));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "client_name", clientName));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "client_version", clientVersion));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "subscription_plan", subscriptionPlan));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "ai_resource", aiResource));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "worker_class", workerClass));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/sidekiq/queues/{queue_name}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete jobs from a Sidekiq queue
     * Deletes jobs from a Sidekiq queue that match the specified metadata. The response has three fields: &#x60;deleted_jobs&#x60; is the number of jobs deleted by the request, &#x60;queue_size&#x60; is the remaining size of the queue after the request, and &#x60;completed&#x60; is whether the request processed the entire queue in time. If &#x60;completed&#x60; is &#x60;false&#x60;, retrying with the same parameters can delete more jobs, including jobs added after the first request. Only available to administrators. At least one of 32 related parameters must be provided.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param queueName Name of the queue to delete jobs from.
     * @param organizationId Metadata key to match
     * @param user Username of the user who scheduled the jobs.
     * @param userId Metadata key to match
     * @param glUserId Metadata key to match
     * @param scopedUser Metadata key to match
     * @param scopedUserId Metadata key to match
     * @param project Full path of the project where the jobs were scheduled from.
     * @param rootNamespace Root namespace of the project.
     * @param glRootNamespaceId Metadata key to match
     * @param clientId Metadata key to match
     * @param callerId Endpoint or background job that scheduled the job, for example &#x60;ProjectsController#create&#x60;, &#x60;/api/:version/projects/:id&#x60;, or &#x60;PostReceive&#x60;.
     * @param remoteIp Metadata key to match
     * @param jobId Metadata key to match
     * @param pipelineId Metadata key to match
     * @param relatedClass Metadata key to match
     * @param featureCategory Feature category of the background job, for example &#x60;team_planning&#x60; or &#x60;code_review&#x60;.
     * @param artifactSize Metadata key to match
     * @param artifactUsedCdn Metadata key to match
     * @param artifactsDependenciesSize Metadata key to match
     * @param artifactsDependenciesCount Metadata key to match
     * @param rootCallerId Metadata key to match
     * @param mergeActionStatus Metadata key to match
     * @param bulkImportEntityId Metadata key to match
     * @param sidekiqDestinationShardRedis Metadata key to match
     * @param kubernetesAgentId Metadata key to match
     * @param mvccManifest Metadata key to match
     * @param clientType Kind of client whose request scheduled the jobs, for example &#x60;browser&#x60;, &#x60;ide&#x60;, or &#x60;mobile&#x60;.
     * @param clientName Client whose request scheduled the jobs, for example &#x60;vscode&#x60; or &#x60;gitlab-mobile-ios&#x60;.
     * @param clientVersion Release of the client whose request scheduled the jobs, for example &#x60;6.21.0&#x60; or &#x60;1.2.0&#x60;.
     * @param subscriptionPlan Subscription plan of the root namespace. GitLab.com only.
     * @param aiResource Metadata key to match
     * @param workerClass Class of the background job worker, for example &#x60;PostReceive&#x60; or &#x60;MergeWorker&#x60;.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4AdminSidekiqQueuesQueueName(@jakarta.annotation.Nonnull String queueName, @jakarta.annotation.Nullable String organizationId, @jakarta.annotation.Nullable String user, @jakarta.annotation.Nullable String userId, @jakarta.annotation.Nullable String glUserId, @jakarta.annotation.Nullable String scopedUser, @jakarta.annotation.Nullable String scopedUserId, @jakarta.annotation.Nullable String project, @jakarta.annotation.Nullable String rootNamespace, @jakarta.annotation.Nullable String glRootNamespaceId, @jakarta.annotation.Nullable String clientId, @jakarta.annotation.Nullable String callerId, @jakarta.annotation.Nullable String remoteIp, @jakarta.annotation.Nullable String jobId, @jakarta.annotation.Nullable String pipelineId, @jakarta.annotation.Nullable String relatedClass, @jakarta.annotation.Nullable String featureCategory, @jakarta.annotation.Nullable String artifactSize, @jakarta.annotation.Nullable String artifactUsedCdn, @jakarta.annotation.Nullable String artifactsDependenciesSize, @jakarta.annotation.Nullable String artifactsDependenciesCount, @jakarta.annotation.Nullable String rootCallerId, @jakarta.annotation.Nullable String mergeActionStatus, @jakarta.annotation.Nullable String bulkImportEntityId, @jakarta.annotation.Nullable String sidekiqDestinationShardRedis, @jakarta.annotation.Nullable String kubernetesAgentId, @jakarta.annotation.Nullable String mvccManifest, @jakarta.annotation.Nullable String clientType, @jakarta.annotation.Nullable String clientName, @jakarta.annotation.Nullable String clientVersion, @jakarta.annotation.Nullable String subscriptionPlan, @jakarta.annotation.Nullable String aiResource, @jakarta.annotation.Nullable String workerClass) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4AdminSidekiqQueuesQueueNameRequestCreation(queueName, organizationId, user, userId, glUserId, scopedUser, scopedUserId, project, rootNamespace, glRootNamespaceId, clientId, callerId, remoteIp, jobId, pipelineId, relatedClass, featureCategory, artifactSize, artifactUsedCdn, artifactsDependenciesSize, artifactsDependenciesCount, rootCallerId, mergeActionStatus, bulkImportEntityId, sidekiqDestinationShardRedis, kubernetesAgentId, mvccManifest, clientType, clientName, clientVersion, subscriptionPlan, aiResource, workerClass).body(localVarReturnType);
    }

    /**
     * Delete jobs from a Sidekiq queue
     * Deletes jobs from a Sidekiq queue that match the specified metadata. The response has three fields: &#x60;deleted_jobs&#x60; is the number of jobs deleted by the request, &#x60;queue_size&#x60; is the remaining size of the queue after the request, and &#x60;completed&#x60; is whether the request processed the entire queue in time. If &#x60;completed&#x60; is &#x60;false&#x60;, retrying with the same parameters can delete more jobs, including jobs added after the first request. Only available to administrators. At least one of 32 related parameters must be provided.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param queueName Name of the queue to delete jobs from.
     * @param organizationId Metadata key to match
     * @param user Username of the user who scheduled the jobs.
     * @param userId Metadata key to match
     * @param glUserId Metadata key to match
     * @param scopedUser Metadata key to match
     * @param scopedUserId Metadata key to match
     * @param project Full path of the project where the jobs were scheduled from.
     * @param rootNamespace Root namespace of the project.
     * @param glRootNamespaceId Metadata key to match
     * @param clientId Metadata key to match
     * @param callerId Endpoint or background job that scheduled the job, for example &#x60;ProjectsController#create&#x60;, &#x60;/api/:version/projects/:id&#x60;, or &#x60;PostReceive&#x60;.
     * @param remoteIp Metadata key to match
     * @param jobId Metadata key to match
     * @param pipelineId Metadata key to match
     * @param relatedClass Metadata key to match
     * @param featureCategory Feature category of the background job, for example &#x60;team_planning&#x60; or &#x60;code_review&#x60;.
     * @param artifactSize Metadata key to match
     * @param artifactUsedCdn Metadata key to match
     * @param artifactsDependenciesSize Metadata key to match
     * @param artifactsDependenciesCount Metadata key to match
     * @param rootCallerId Metadata key to match
     * @param mergeActionStatus Metadata key to match
     * @param bulkImportEntityId Metadata key to match
     * @param sidekiqDestinationShardRedis Metadata key to match
     * @param kubernetesAgentId Metadata key to match
     * @param mvccManifest Metadata key to match
     * @param clientType Kind of client whose request scheduled the jobs, for example &#x60;browser&#x60;, &#x60;ide&#x60;, or &#x60;mobile&#x60;.
     * @param clientName Client whose request scheduled the jobs, for example &#x60;vscode&#x60; or &#x60;gitlab-mobile-ios&#x60;.
     * @param clientVersion Release of the client whose request scheduled the jobs, for example &#x60;6.21.0&#x60; or &#x60;1.2.0&#x60;.
     * @param subscriptionPlan Subscription plan of the root namespace. GitLab.com only.
     * @param aiResource Metadata key to match
     * @param workerClass Class of the background job worker, for example &#x60;PostReceive&#x60; or &#x60;MergeWorker&#x60;.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4AdminSidekiqQueuesQueueNameWithHttpInfo(@jakarta.annotation.Nonnull String queueName, @jakarta.annotation.Nullable String organizationId, @jakarta.annotation.Nullable String user, @jakarta.annotation.Nullable String userId, @jakarta.annotation.Nullable String glUserId, @jakarta.annotation.Nullable String scopedUser, @jakarta.annotation.Nullable String scopedUserId, @jakarta.annotation.Nullable String project, @jakarta.annotation.Nullable String rootNamespace, @jakarta.annotation.Nullable String glRootNamespaceId, @jakarta.annotation.Nullable String clientId, @jakarta.annotation.Nullable String callerId, @jakarta.annotation.Nullable String remoteIp, @jakarta.annotation.Nullable String jobId, @jakarta.annotation.Nullable String pipelineId, @jakarta.annotation.Nullable String relatedClass, @jakarta.annotation.Nullable String featureCategory, @jakarta.annotation.Nullable String artifactSize, @jakarta.annotation.Nullable String artifactUsedCdn, @jakarta.annotation.Nullable String artifactsDependenciesSize, @jakarta.annotation.Nullable String artifactsDependenciesCount, @jakarta.annotation.Nullable String rootCallerId, @jakarta.annotation.Nullable String mergeActionStatus, @jakarta.annotation.Nullable String bulkImportEntityId, @jakarta.annotation.Nullable String sidekiqDestinationShardRedis, @jakarta.annotation.Nullable String kubernetesAgentId, @jakarta.annotation.Nullable String mvccManifest, @jakarta.annotation.Nullable String clientType, @jakarta.annotation.Nullable String clientName, @jakarta.annotation.Nullable String clientVersion, @jakarta.annotation.Nullable String subscriptionPlan, @jakarta.annotation.Nullable String aiResource, @jakarta.annotation.Nullable String workerClass) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4AdminSidekiqQueuesQueueNameRequestCreation(queueName, organizationId, user, userId, glUserId, scopedUser, scopedUserId, project, rootNamespace, glRootNamespaceId, clientId, callerId, remoteIp, jobId, pipelineId, relatedClass, featureCategory, artifactSize, artifactUsedCdn, artifactsDependenciesSize, artifactsDependenciesCount, rootCallerId, mergeActionStatus, bulkImportEntityId, sidekiqDestinationShardRedis, kubernetesAgentId, mvccManifest, clientType, clientName, clientVersion, subscriptionPlan, aiResource, workerClass).toEntity(localVarReturnType);
    }

    /**
     * Delete jobs from a Sidekiq queue
     * Deletes jobs from a Sidekiq queue that match the specified metadata. The response has three fields: &#x60;deleted_jobs&#x60; is the number of jobs deleted by the request, &#x60;queue_size&#x60; is the remaining size of the queue after the request, and &#x60;completed&#x60; is whether the request processed the entire queue in time. If &#x60;completed&#x60; is &#x60;false&#x60;, retrying with the same parameters can delete more jobs, including jobs added after the first request. Only available to administrators. At least one of 32 related parameters must be provided.
     * <p><b>204</b> - No Content
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param queueName Name of the queue to delete jobs from.
     * @param organizationId Metadata key to match
     * @param user Username of the user who scheduled the jobs.
     * @param userId Metadata key to match
     * @param glUserId Metadata key to match
     * @param scopedUser Metadata key to match
     * @param scopedUserId Metadata key to match
     * @param project Full path of the project where the jobs were scheduled from.
     * @param rootNamespace Root namespace of the project.
     * @param glRootNamespaceId Metadata key to match
     * @param clientId Metadata key to match
     * @param callerId Endpoint or background job that scheduled the job, for example &#x60;ProjectsController#create&#x60;, &#x60;/api/:version/projects/:id&#x60;, or &#x60;PostReceive&#x60;.
     * @param remoteIp Metadata key to match
     * @param jobId Metadata key to match
     * @param pipelineId Metadata key to match
     * @param relatedClass Metadata key to match
     * @param featureCategory Feature category of the background job, for example &#x60;team_planning&#x60; or &#x60;code_review&#x60;.
     * @param artifactSize Metadata key to match
     * @param artifactUsedCdn Metadata key to match
     * @param artifactsDependenciesSize Metadata key to match
     * @param artifactsDependenciesCount Metadata key to match
     * @param rootCallerId Metadata key to match
     * @param mergeActionStatus Metadata key to match
     * @param bulkImportEntityId Metadata key to match
     * @param sidekiqDestinationShardRedis Metadata key to match
     * @param kubernetesAgentId Metadata key to match
     * @param mvccManifest Metadata key to match
     * @param clientType Kind of client whose request scheduled the jobs, for example &#x60;browser&#x60;, &#x60;ide&#x60;, or &#x60;mobile&#x60;.
     * @param clientName Client whose request scheduled the jobs, for example &#x60;vscode&#x60; or &#x60;gitlab-mobile-ios&#x60;.
     * @param clientVersion Release of the client whose request scheduled the jobs, for example &#x60;6.21.0&#x60; or &#x60;1.2.0&#x60;.
     * @param subscriptionPlan Subscription plan of the root namespace. GitLab.com only.
     * @param aiResource Metadata key to match
     * @param workerClass Class of the background job worker, for example &#x60;PostReceive&#x60; or &#x60;MergeWorker&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4AdminSidekiqQueuesQueueNameWithResponseSpec(@jakarta.annotation.Nonnull String queueName, @jakarta.annotation.Nullable String organizationId, @jakarta.annotation.Nullable String user, @jakarta.annotation.Nullable String userId, @jakarta.annotation.Nullable String glUserId, @jakarta.annotation.Nullable String scopedUser, @jakarta.annotation.Nullable String scopedUserId, @jakarta.annotation.Nullable String project, @jakarta.annotation.Nullable String rootNamespace, @jakarta.annotation.Nullable String glRootNamespaceId, @jakarta.annotation.Nullable String clientId, @jakarta.annotation.Nullable String callerId, @jakarta.annotation.Nullable String remoteIp, @jakarta.annotation.Nullable String jobId, @jakarta.annotation.Nullable String pipelineId, @jakarta.annotation.Nullable String relatedClass, @jakarta.annotation.Nullable String featureCategory, @jakarta.annotation.Nullable String artifactSize, @jakarta.annotation.Nullable String artifactUsedCdn, @jakarta.annotation.Nullable String artifactsDependenciesSize, @jakarta.annotation.Nullable String artifactsDependenciesCount, @jakarta.annotation.Nullable String rootCallerId, @jakarta.annotation.Nullable String mergeActionStatus, @jakarta.annotation.Nullable String bulkImportEntityId, @jakarta.annotation.Nullable String sidekiqDestinationShardRedis, @jakarta.annotation.Nullable String kubernetesAgentId, @jakarta.annotation.Nullable String mvccManifest, @jakarta.annotation.Nullable String clientType, @jakarta.annotation.Nullable String clientName, @jakarta.annotation.Nullable String clientVersion, @jakarta.annotation.Nullable String subscriptionPlan, @jakarta.annotation.Nullable String aiResource, @jakarta.annotation.Nullable String workerClass) throws RestClientResponseException {
        return deleteApiV4AdminSidekiqQueuesQueueNameRequestCreation(queueName, organizationId, user, userId, glUserId, scopedUser, scopedUserId, project, rootNamespace, glRootNamespaceId, clientId, callerId, remoteIp, jobId, pipelineId, relatedClass, featureCategory, artifactSize, artifactUsedCdn, artifactsDependenciesSize, artifactsDependenciesCount, rootCallerId, mergeActionStatus, bulkImportEntityId, sidekiqDestinationShardRedis, kubernetesAgentId, mvccManifest, clientType, clientName, clientVersion, subscriptionPlan, aiResource, workerClass);
    }

    /**
     * Get a Sidekiq worker concurrency limit
     * Returns concurrency limits, override expiry and job counts. Namespaced worker class names keep the &#x60;::&#x60; separator in the path. This feature was introduced in GitLab 19.6.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Administrator access required
     * <p><b>404</b> - Worker not found
     * <p><b>400</b> - Bad Request
     * @param workerClass Registered Sidekiq worker class name.
     * @return APIEntitiesSidekiqConcurrencyLimit
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4AdminSidekiqConcurrencyLimitsWorkerClassRequestCreation(@jakarta.annotation.Nonnull String workerClass) throws RestClientResponseException {
        // verify the required parameter 'workerClass' is set
        if (workerClass == null) {
            throw new RestClientResponseException("Missing the required parameter 'workerClass' when calling getApiV4AdminSidekiqConcurrencyLimitsWorkerClass", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("worker_class", workerClass);

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

        ParameterizedTypeReference<APIEntitiesSidekiqConcurrencyLimit> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/sidekiq/concurrency_limits/{worker_class}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get a Sidekiq worker concurrency limit
     * Returns concurrency limits, override expiry and job counts. Namespaced worker class names keep the &#x60;::&#x60; separator in the path. This feature was introduced in GitLab 19.6.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Administrator access required
     * <p><b>404</b> - Worker not found
     * <p><b>400</b> - Bad Request
     * @param workerClass Registered Sidekiq worker class name.
     * @return APIEntitiesSidekiqConcurrencyLimit
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesSidekiqConcurrencyLimit getApiV4AdminSidekiqConcurrencyLimitsWorkerClass(@jakarta.annotation.Nonnull String workerClass) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSidekiqConcurrencyLimit> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminSidekiqConcurrencyLimitsWorkerClassRequestCreation(workerClass).body(localVarReturnType);
    }

    /**
     * Get a Sidekiq worker concurrency limit
     * Returns concurrency limits, override expiry and job counts. Namespaced worker class names keep the &#x60;::&#x60; separator in the path. This feature was introduced in GitLab 19.6.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Administrator access required
     * <p><b>404</b> - Worker not found
     * <p><b>400</b> - Bad Request
     * @param workerClass Registered Sidekiq worker class name.
     * @return ResponseEntity&lt;APIEntitiesSidekiqConcurrencyLimit&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesSidekiqConcurrencyLimit> getApiV4AdminSidekiqConcurrencyLimitsWorkerClassWithHttpInfo(@jakarta.annotation.Nonnull String workerClass) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSidekiqConcurrencyLimit> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4AdminSidekiqConcurrencyLimitsWorkerClassRequestCreation(workerClass).toEntity(localVarReturnType);
    }

    /**
     * Get a Sidekiq worker concurrency limit
     * Returns concurrency limits, override expiry and job counts. Namespaced worker class names keep the &#x60;::&#x60; separator in the path. This feature was introduced in GitLab 19.6.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Administrator access required
     * <p><b>404</b> - Worker not found
     * <p><b>400</b> - Bad Request
     * @param workerClass Registered Sidekiq worker class name.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4AdminSidekiqConcurrencyLimitsWorkerClassWithResponseSpec(@jakarta.annotation.Nonnull String workerClass) throws RestClientResponseException {
        return getApiV4AdminSidekiqConcurrencyLimitsWorkerClassRequestCreation(workerClass);
    }

    /**
     * List all Sidekiq metrics
     * Lists all Sidekiq metrics. Includes queue, process, and job completion metrics.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4SidekiqCompoundMetricsRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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
        return apiClient.invokeAPI("/api/v4/sidekiq/compound_metrics", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all Sidekiq metrics
     * Lists all Sidekiq metrics. Includes queue, process, and job completion metrics.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4SidekiqCompoundMetrics() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4SidekiqCompoundMetricsRequestCreation().body(localVarReturnType);
    }

    /**
     * List all Sidekiq metrics
     * Lists all Sidekiq metrics. Includes queue, process, and job completion metrics.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4SidekiqCompoundMetricsWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4SidekiqCompoundMetricsRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * List all Sidekiq metrics
     * Lists all Sidekiq metrics. Includes queue, process, and job completion metrics.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4SidekiqCompoundMetricsWithResponseSpec() throws RestClientResponseException {
        return getApiV4SidekiqCompoundMetricsRequestCreation();
    }

    /**
     * Retrieve job completion metrics
     * Retrieves statistics on the completion status of all Sidekiq jobs.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4SidekiqJobStatsRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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
        return apiClient.invokeAPI("/api/v4/sidekiq/job_stats", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve job completion metrics
     * Retrieves statistics on the completion status of all Sidekiq jobs.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4SidekiqJobStats() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4SidekiqJobStatsRequestCreation().body(localVarReturnType);
    }

    /**
     * Retrieve job completion metrics
     * Retrieves statistics on the completion status of all Sidekiq jobs.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4SidekiqJobStatsWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4SidekiqJobStatsRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Retrieve job completion metrics
     * Retrieves statistics on the completion status of all Sidekiq jobs.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4SidekiqJobStatsWithResponseSpec() throws RestClientResponseException {
        return getApiV4SidekiqJobStatsRequestCreation();
    }

    /**
     * List all Sidekiq processes
     * Lists all details about all registered Sidekiq worker processes, including hostname, process ID, queues, and concurrency settings.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4SidekiqProcessMetricsRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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
        return apiClient.invokeAPI("/api/v4/sidekiq/process_metrics", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all Sidekiq processes
     * Lists all details about all registered Sidekiq worker processes, including hostname, process ID, queues, and concurrency settings.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4SidekiqProcessMetrics() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4SidekiqProcessMetricsRequestCreation().body(localVarReturnType);
    }

    /**
     * List all Sidekiq processes
     * Lists all details about all registered Sidekiq worker processes, including hostname, process ID, queues, and concurrency settings.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4SidekiqProcessMetricsWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4SidekiqProcessMetricsRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * List all Sidekiq processes
     * Lists all details about all registered Sidekiq worker processes, including hostname, process ID, queues, and concurrency settings.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4SidekiqProcessMetricsWithResponseSpec() throws RestClientResponseException {
        return getApiV4SidekiqProcessMetricsRequestCreation();
    }

    /**
     * List all job queue metrics
     * Lists all details about all Sidekiq job queues, including backlog size and latency.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4SidekiqQueueMetricsRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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
        return apiClient.invokeAPI("/api/v4/sidekiq/queue_metrics", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all job queue metrics
     * Lists all details about all Sidekiq job queues, including backlog size and latency.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4SidekiqQueueMetrics() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4SidekiqQueueMetricsRequestCreation().body(localVarReturnType);
    }

    /**
     * List all job queue metrics
     * Lists all details about all Sidekiq job queues, including backlog size and latency.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4SidekiqQueueMetricsWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4SidekiqQueueMetricsRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * List all job queue metrics
     * Lists all details about all Sidekiq job queues, including backlog size and latency.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4SidekiqQueueMetricsWithResponseSpec() throws RestClientResponseException {
        return getApiV4SidekiqQueueMetricsRequestCreation();
    }

    /**
     * Update a Sidekiq worker concurrency limit override
     * Sets or replaces a temporary concurrency override. Overrides adaptive throttling until the TTL expires or the override is deleted. Replacing an override restarts its TTL. Workers without a limit can receive a temporary cap. A positive override takes precedence over adaptive throttling and the class default. Pausing takes precedence over an override. Disabled concurrency middleware ignores overrides. When an override expires or is cleared, the stored adaptive limit resumes if it remains valid. Otherwise, the worker&#39;s runtime default applies. Lowering a limit does not terminate running jobs. The override is rejected if the worker is paused, the &#x60;sidekiq_concurrency_limit_middleware&#x60; feature flag is disabled, the worker&#39;s &#x60;disable_sidekiq_concurrency_limit_middleware_&lt;WorkerClass&gt;&#x60; bypass flag is enabled, or the worker is &#x60;ConcurrencyLimit::ResumeWorker&#x60;, which resumes deferred jobs and cannot be limited. This feature was introduced in GitLab 19.6.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Invalid override
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Administrator access required
     * <p><b>404</b> - Worker not found
     * @param workerClass Registered Sidekiq worker class name.
     * @param putApiV4AdminSidekiqConcurrencyLimitsWorkerClassRequest The putApiV4AdminSidekiqConcurrencyLimitsWorkerClassRequest parameter
     * @return APIEntitiesSidekiqConcurrencyLimit
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4AdminSidekiqConcurrencyLimitsWorkerClassRequestCreation(@jakarta.annotation.Nonnull String workerClass, @jakarta.annotation.Nonnull PutApiV4AdminSidekiqConcurrencyLimitsWorkerClassRequest putApiV4AdminSidekiqConcurrencyLimitsWorkerClassRequest) throws RestClientResponseException {
        // verify the required parameter 'workerClass' is set
        if (workerClass == null) {
            throw new RestClientResponseException("Missing the required parameter 'workerClass' when calling putApiV4AdminSidekiqConcurrencyLimitsWorkerClass", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'putApiV4AdminSidekiqConcurrencyLimitsWorkerClassRequest' is set
        if (putApiV4AdminSidekiqConcurrencyLimitsWorkerClassRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'putApiV4AdminSidekiqConcurrencyLimitsWorkerClassRequest' when calling putApiV4AdminSidekiqConcurrencyLimitsWorkerClass", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("worker_class", workerClass);

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

        ParameterizedTypeReference<APIEntitiesSidekiqConcurrencyLimit> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/admin/sidekiq/concurrency_limits/{worker_class}", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4AdminSidekiqConcurrencyLimitsWorkerClassRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Update a Sidekiq worker concurrency limit override
     * Sets or replaces a temporary concurrency override. Overrides adaptive throttling until the TTL expires or the override is deleted. Replacing an override restarts its TTL. Workers without a limit can receive a temporary cap. A positive override takes precedence over adaptive throttling and the class default. Pausing takes precedence over an override. Disabled concurrency middleware ignores overrides. When an override expires or is cleared, the stored adaptive limit resumes if it remains valid. Otherwise, the worker&#39;s runtime default applies. Lowering a limit does not terminate running jobs. The override is rejected if the worker is paused, the &#x60;sidekiq_concurrency_limit_middleware&#x60; feature flag is disabled, the worker&#39;s &#x60;disable_sidekiq_concurrency_limit_middleware_&lt;WorkerClass&gt;&#x60; bypass flag is enabled, or the worker is &#x60;ConcurrencyLimit::ResumeWorker&#x60;, which resumes deferred jobs and cannot be limited. This feature was introduced in GitLab 19.6.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Invalid override
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Administrator access required
     * <p><b>404</b> - Worker not found
     * @param workerClass Registered Sidekiq worker class name.
     * @param putApiV4AdminSidekiqConcurrencyLimitsWorkerClassRequest The putApiV4AdminSidekiqConcurrencyLimitsWorkerClassRequest parameter
     * @return APIEntitiesSidekiqConcurrencyLimit
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesSidekiqConcurrencyLimit putApiV4AdminSidekiqConcurrencyLimitsWorkerClass(@jakarta.annotation.Nonnull String workerClass, @jakarta.annotation.Nonnull PutApiV4AdminSidekiqConcurrencyLimitsWorkerClassRequest putApiV4AdminSidekiqConcurrencyLimitsWorkerClassRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSidekiqConcurrencyLimit> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4AdminSidekiqConcurrencyLimitsWorkerClassRequestCreation(workerClass, putApiV4AdminSidekiqConcurrencyLimitsWorkerClassRequest).body(localVarReturnType);
    }

    /**
     * Update a Sidekiq worker concurrency limit override
     * Sets or replaces a temporary concurrency override. Overrides adaptive throttling until the TTL expires or the override is deleted. Replacing an override restarts its TTL. Workers without a limit can receive a temporary cap. A positive override takes precedence over adaptive throttling and the class default. Pausing takes precedence over an override. Disabled concurrency middleware ignores overrides. When an override expires or is cleared, the stored adaptive limit resumes if it remains valid. Otherwise, the worker&#39;s runtime default applies. Lowering a limit does not terminate running jobs. The override is rejected if the worker is paused, the &#x60;sidekiq_concurrency_limit_middleware&#x60; feature flag is disabled, the worker&#39;s &#x60;disable_sidekiq_concurrency_limit_middleware_&lt;WorkerClass&gt;&#x60; bypass flag is enabled, or the worker is &#x60;ConcurrencyLimit::ResumeWorker&#x60;, which resumes deferred jobs and cannot be limited. This feature was introduced in GitLab 19.6.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Invalid override
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Administrator access required
     * <p><b>404</b> - Worker not found
     * @param workerClass Registered Sidekiq worker class name.
     * @param putApiV4AdminSidekiqConcurrencyLimitsWorkerClassRequest The putApiV4AdminSidekiqConcurrencyLimitsWorkerClassRequest parameter
     * @return ResponseEntity&lt;APIEntitiesSidekiqConcurrencyLimit&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesSidekiqConcurrencyLimit> putApiV4AdminSidekiqConcurrencyLimitsWorkerClassWithHttpInfo(@jakarta.annotation.Nonnull String workerClass, @jakarta.annotation.Nonnull PutApiV4AdminSidekiqConcurrencyLimitsWorkerClassRequest putApiV4AdminSidekiqConcurrencyLimitsWorkerClassRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSidekiqConcurrencyLimit> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4AdminSidekiqConcurrencyLimitsWorkerClassRequestCreation(workerClass, putApiV4AdminSidekiqConcurrencyLimitsWorkerClassRequest).toEntity(localVarReturnType);
    }

    /**
     * Update a Sidekiq worker concurrency limit override
     * Sets or replaces a temporary concurrency override. Overrides adaptive throttling until the TTL expires or the override is deleted. Replacing an override restarts its TTL. Workers without a limit can receive a temporary cap. A positive override takes precedence over adaptive throttling and the class default. Pausing takes precedence over an override. Disabled concurrency middleware ignores overrides. When an override expires or is cleared, the stored adaptive limit resumes if it remains valid. Otherwise, the worker&#39;s runtime default applies. Lowering a limit does not terminate running jobs. The override is rejected if the worker is paused, the &#x60;sidekiq_concurrency_limit_middleware&#x60; feature flag is disabled, the worker&#39;s &#x60;disable_sidekiq_concurrency_limit_middleware_&lt;WorkerClass&gt;&#x60; bypass flag is enabled, or the worker is &#x60;ConcurrencyLimit::ResumeWorker&#x60;, which resumes deferred jobs and cannot be limited. This feature was introduced in GitLab 19.6.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Invalid override
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Administrator access required
     * <p><b>404</b> - Worker not found
     * @param workerClass Registered Sidekiq worker class name.
     * @param putApiV4AdminSidekiqConcurrencyLimitsWorkerClassRequest The putApiV4AdminSidekiqConcurrencyLimitsWorkerClassRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4AdminSidekiqConcurrencyLimitsWorkerClassWithResponseSpec(@jakarta.annotation.Nonnull String workerClass, @jakarta.annotation.Nonnull PutApiV4AdminSidekiqConcurrencyLimitsWorkerClassRequest putApiV4AdminSidekiqConcurrencyLimitsWorkerClassRequest) throws RestClientResponseException {
        return putApiV4AdminSidekiqConcurrencyLimitsWorkerClassRequestCreation(workerClass, putApiV4AdminSidekiqConcurrencyLimitsWorkerClassRequest);
    }
}
