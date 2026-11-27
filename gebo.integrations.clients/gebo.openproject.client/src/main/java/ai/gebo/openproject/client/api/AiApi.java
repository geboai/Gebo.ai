package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.AITextActionCollectionModel;
import ai.gebo.openproject.client.model.AITextTransformRunModel;
import ai.gebo.openproject.client.model.AITextTransformRunWriteModel;
import ai.gebo.openproject.client.model.ErrorResponse;
import java.util.UUID;

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
public class AiApi {
    private ApiClient apiClient;

    public AiApi() {
        this(new ApiClient());
    }

    public AiApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Cancel AI text transform run
     * Asks the background job to stop the run. The job checks the request whenever it flushes generated text and then finishes the run with status &#x60;cancelled&#x60;; a client keeps polling until it sees that status. The request is idempotent: repeating it or sending it for a run that has already finished answers 204 and changes nothing.  The request has no body, but a &#x60;Content-Type: application/json&#x60; header is still required.  **Required permission:** logged in, owner of the run
     * <p><b>204</b> - Returned if the cancellation was recorded or the run had already finished.
     * <p><b>404</b> - Returned if the run does not exist, has been deleted after its retention period, or belongs to another user.  **Required permission:** owner of the run
     * @param id Run id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec cancelAiTextTransformRunRequestCreation(@jakarta.annotation.Nonnull UUID id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling cancelAiTextTransformRun", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v3/ai_text_transform_runs/{id}/cancel", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Cancel AI text transform run
     * Asks the background job to stop the run. The job checks the request whenever it flushes generated text and then finishes the run with status &#x60;cancelled&#x60;; a client keeps polling until it sees that status. The request is idempotent: repeating it or sending it for a run that has already finished answers 204 and changes nothing.  The request has no body, but a &#x60;Content-Type: application/json&#x60; header is still required.  **Required permission:** logged in, owner of the run
     * <p><b>204</b> - Returned if the cancellation was recorded or the run had already finished.
     * <p><b>404</b> - Returned if the run does not exist, has been deleted after its retention period, or belongs to another user.  **Required permission:** owner of the run
     * @param id Run id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void cancelAiTextTransformRun(@jakarta.annotation.Nonnull UUID id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        cancelAiTextTransformRunRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Cancel AI text transform run
     * Asks the background job to stop the run. The job checks the request whenever it flushes generated text and then finishes the run with status &#x60;cancelled&#x60;; a client keeps polling until it sees that status. The request is idempotent: repeating it or sending it for a run that has already finished answers 204 and changes nothing.  The request has no body, but a &#x60;Content-Type: application/json&#x60; header is still required.  **Required permission:** logged in, owner of the run
     * <p><b>204</b> - Returned if the cancellation was recorded or the run had already finished.
     * <p><b>404</b> - Returned if the run does not exist, has been deleted after its retention period, or belongs to another user.  **Required permission:** owner of the run
     * @param id Run id
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> cancelAiTextTransformRunWithHttpInfo(@jakarta.annotation.Nonnull UUID id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return cancelAiTextTransformRunRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Cancel AI text transform run
     * Asks the background job to stop the run. The job checks the request whenever it flushes generated text and then finishes the run with status &#x60;cancelled&#x60;; a client keeps polling until it sees that status. The request is idempotent: repeating it or sending it for a run that has already finished answers 204 and changes nothing.  The request has no body, but a &#x60;Content-Type: application/json&#x60; header is still required.  **Required permission:** logged in, owner of the run
     * <p><b>204</b> - Returned if the cancellation was recorded or the run had already finished.
     * <p><b>404</b> - Returned if the run does not exist, has been deleted after its retention period, or belongs to another user.  **Required permission:** owner of the run
     * @param id Run id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec cancelAiTextTransformRunWithResponseSpec(@jakarta.annotation.Nonnull UUID id) throws RestClientResponseException {
        return cancelAiTextTransformRunRequestCreation(id);
    }

    /**
     * Create AI text transform run
     * Starts an AI text action on the given content. The server assembles the prompt from the action and, when the action injects it, the work package type&#39;s description template, and hands the run to a background job. The response is the run in status &#x60;queued&#x60;; poll it with [&#x60;GET /api/v3/ai_text_transform_runs/{id}&#x60;](#operation/view_ai_text_transform_run) to receive the generated text.  The context is optional. Pass &#x60;workPackageId&#x60; for the description of an existing work package, or &#x60;projectId&#x60; and &#x60;typeId&#x60; for a work package about to be created. Without a context, only actions usable everywhere can run. The context decides which actions are available and which template is injected; it is not stored on the run.  Runs are private to the user who created them and are deleted after a retention period.  **Required permission:** logged in. With &#x60;workPackageId&#x60;: edit work packages on that work package. With &#x60;projectId&#x60;: add work packages in that project.
     * <p><b>202</b> - Returned if the run was created and queued.
     * <p><b>400</b> - Returned if the request is malformed: a required property is missing, &#x60;workPackageId&#x60; and &#x60;projectId&#x60; are both given, or &#x60;projectId&#x60; and &#x60;typeId&#x60; are not given together.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** logged in; edit work packages on the work package; add work packages in the project
     * <p><b>404</b> - Returned if the work package or project does not exist or is not visible to the client, or if the type is not enabled in the project.
     * <p><b>422</b> - Returned if the content is blank or too long, or if the action cannot run in this context. In the latter case &#x60;_embedded.details.reason&#x60; carries a stable, untranslated code:  * &#x60;feature_disabled&#x60;: the feature flag &#x60;ai_text_actions&#x60; is off * &#x60;llm_unavailable&#x60;: no LLM connection is ready * &#x60;unknown_action&#x60;: no action with the given id exists * &#x60;action_inactive&#x60;: the action is deactivated * &#x60;context_required&#x60;: the action is scoped to work package types but no context was given * &#x60;type_mismatch&#x60;: the action is not enabled for the work package&#39;s type * &#x60;template_missing&#x60;: the action injects the type template but the type has none
     * @param aiTextTransformRunWriteModel The aiTextTransformRunWriteModel parameter
     * @return AITextTransformRunModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createAiTextTransformRunRequestCreation(@jakarta.annotation.Nullable AITextTransformRunWriteModel aiTextTransformRunWriteModel) throws RestClientResponseException {
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
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<AITextTransformRunModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/ai_text_transform_runs", HttpMethod.POST, pathParams, localVarQueryParams, aiTextTransformRunWriteModel, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create AI text transform run
     * Starts an AI text action on the given content. The server assembles the prompt from the action and, when the action injects it, the work package type&#39;s description template, and hands the run to a background job. The response is the run in status &#x60;queued&#x60;; poll it with [&#x60;GET /api/v3/ai_text_transform_runs/{id}&#x60;](#operation/view_ai_text_transform_run) to receive the generated text.  The context is optional. Pass &#x60;workPackageId&#x60; for the description of an existing work package, or &#x60;projectId&#x60; and &#x60;typeId&#x60; for a work package about to be created. Without a context, only actions usable everywhere can run. The context decides which actions are available and which template is injected; it is not stored on the run.  Runs are private to the user who created them and are deleted after a retention period.  **Required permission:** logged in. With &#x60;workPackageId&#x60;: edit work packages on that work package. With &#x60;projectId&#x60;: add work packages in that project.
     * <p><b>202</b> - Returned if the run was created and queued.
     * <p><b>400</b> - Returned if the request is malformed: a required property is missing, &#x60;workPackageId&#x60; and &#x60;projectId&#x60; are both given, or &#x60;projectId&#x60; and &#x60;typeId&#x60; are not given together.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** logged in; edit work packages on the work package; add work packages in the project
     * <p><b>404</b> - Returned if the work package or project does not exist or is not visible to the client, or if the type is not enabled in the project.
     * <p><b>422</b> - Returned if the content is blank or too long, or if the action cannot run in this context. In the latter case &#x60;_embedded.details.reason&#x60; carries a stable, untranslated code:  * &#x60;feature_disabled&#x60;: the feature flag &#x60;ai_text_actions&#x60; is off * &#x60;llm_unavailable&#x60;: no LLM connection is ready * &#x60;unknown_action&#x60;: no action with the given id exists * &#x60;action_inactive&#x60;: the action is deactivated * &#x60;context_required&#x60;: the action is scoped to work package types but no context was given * &#x60;type_mismatch&#x60;: the action is not enabled for the work package&#39;s type * &#x60;template_missing&#x60;: the action injects the type template but the type has none
     * @param aiTextTransformRunWriteModel The aiTextTransformRunWriteModel parameter
     * @return AITextTransformRunModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public AITextTransformRunModel createAiTextTransformRun(@jakarta.annotation.Nullable AITextTransformRunWriteModel aiTextTransformRunWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<AITextTransformRunModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createAiTextTransformRunRequestCreation(aiTextTransformRunWriteModel).body(localVarReturnType);
    }

    /**
     * Create AI text transform run
     * Starts an AI text action on the given content. The server assembles the prompt from the action and, when the action injects it, the work package type&#39;s description template, and hands the run to a background job. The response is the run in status &#x60;queued&#x60;; poll it with [&#x60;GET /api/v3/ai_text_transform_runs/{id}&#x60;](#operation/view_ai_text_transform_run) to receive the generated text.  The context is optional. Pass &#x60;workPackageId&#x60; for the description of an existing work package, or &#x60;projectId&#x60; and &#x60;typeId&#x60; for a work package about to be created. Without a context, only actions usable everywhere can run. The context decides which actions are available and which template is injected; it is not stored on the run.  Runs are private to the user who created them and are deleted after a retention period.  **Required permission:** logged in. With &#x60;workPackageId&#x60;: edit work packages on that work package. With &#x60;projectId&#x60;: add work packages in that project.
     * <p><b>202</b> - Returned if the run was created and queued.
     * <p><b>400</b> - Returned if the request is malformed: a required property is missing, &#x60;workPackageId&#x60; and &#x60;projectId&#x60; are both given, or &#x60;projectId&#x60; and &#x60;typeId&#x60; are not given together.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** logged in; edit work packages on the work package; add work packages in the project
     * <p><b>404</b> - Returned if the work package or project does not exist or is not visible to the client, or if the type is not enabled in the project.
     * <p><b>422</b> - Returned if the content is blank or too long, or if the action cannot run in this context. In the latter case &#x60;_embedded.details.reason&#x60; carries a stable, untranslated code:  * &#x60;feature_disabled&#x60;: the feature flag &#x60;ai_text_actions&#x60; is off * &#x60;llm_unavailable&#x60;: no LLM connection is ready * &#x60;unknown_action&#x60;: no action with the given id exists * &#x60;action_inactive&#x60;: the action is deactivated * &#x60;context_required&#x60;: the action is scoped to work package types but no context was given * &#x60;type_mismatch&#x60;: the action is not enabled for the work package&#39;s type * &#x60;template_missing&#x60;: the action injects the type template but the type has none
     * @param aiTextTransformRunWriteModel The aiTextTransformRunWriteModel parameter
     * @return ResponseEntity&lt;AITextTransformRunModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<AITextTransformRunModel> createAiTextTransformRunWithHttpInfo(@jakarta.annotation.Nullable AITextTransformRunWriteModel aiTextTransformRunWriteModel) throws RestClientResponseException {
        ParameterizedTypeReference<AITextTransformRunModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createAiTextTransformRunRequestCreation(aiTextTransformRunWriteModel).toEntity(localVarReturnType);
    }

    /**
     * Create AI text transform run
     * Starts an AI text action on the given content. The server assembles the prompt from the action and, when the action injects it, the work package type&#39;s description template, and hands the run to a background job. The response is the run in status &#x60;queued&#x60;; poll it with [&#x60;GET /api/v3/ai_text_transform_runs/{id}&#x60;](#operation/view_ai_text_transform_run) to receive the generated text.  The context is optional. Pass &#x60;workPackageId&#x60; for the description of an existing work package, or &#x60;projectId&#x60; and &#x60;typeId&#x60; for a work package about to be created. Without a context, only actions usable everywhere can run. The context decides which actions are available and which template is injected; it is not stored on the run.  Runs are private to the user who created them and are deleted after a retention period.  **Required permission:** logged in. With &#x60;workPackageId&#x60;: edit work packages on that work package. With &#x60;projectId&#x60;: add work packages in that project.
     * <p><b>202</b> - Returned if the run was created and queued.
     * <p><b>400</b> - Returned if the request is malformed: a required property is missing, &#x60;workPackageId&#x60; and &#x60;projectId&#x60; are both given, or &#x60;projectId&#x60; and &#x60;typeId&#x60; are not given together.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** logged in; edit work packages on the work package; add work packages in the project
     * <p><b>404</b> - Returned if the work package or project does not exist or is not visible to the client, or if the type is not enabled in the project.
     * <p><b>422</b> - Returned if the content is blank or too long, or if the action cannot run in this context. In the latter case &#x60;_embedded.details.reason&#x60; carries a stable, untranslated code:  * &#x60;feature_disabled&#x60;: the feature flag &#x60;ai_text_actions&#x60; is off * &#x60;llm_unavailable&#x60;: no LLM connection is ready * &#x60;unknown_action&#x60;: no action with the given id exists * &#x60;action_inactive&#x60;: the action is deactivated * &#x60;context_required&#x60;: the action is scoped to work package types but no context was given * &#x60;type_mismatch&#x60;: the action is not enabled for the work package&#39;s type * &#x60;template_missing&#x60;: the action injects the type template but the type has none
     * @param aiTextTransformRunWriteModel The aiTextTransformRunWriteModel parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createAiTextTransformRunWithResponseSpec(@jakarta.annotation.Nullable AITextTransformRunWriteModel aiTextTransformRunWriteModel) throws RestClientResponseException {
        return createAiTextTransformRunRequestCreation(aiTextTransformRunWriteModel);
    }

    /**
     * View AI text transform run
     * Returns the run with its status and the events recorded after the &#x60;after&#x60; cursor. The events are an append-only log; a client reads the generated text by polling this endpoint until the status is terminal.  ## Polling  * Send the highest &#x60;seq&#x60; seen so far as &#x60;after&#x60;. The first request uses &#x60;after&#x3D;0&#x60; or omits   the parameter. The response then contains only events the client has not seen. * Keep one request in flight at a time. Start polling after about 400 ms and increase the   interval by 150 ms per poll up to 1000 ms. * Ignore a response whose requested cursor is no longer the highest seen &#x60;seq&#x60;. * Stop when &#x60;status&#x60; is &#x60;succeeded&#x60;, &#x60;failed&#x60; or &#x60;cancelled&#x60;. The terminal status can arrive   in the same response as the last events. * Pause polling while the document is hidden and resume when it becomes visible. * Render &#x60;text_delta&#x60; payloads progressively. Once &#x60;completed&#x60; arrives, replace the rendered   text with its &#x60;text&#x60; and build any diff from it, never from the concatenated deltas. * Show the &#x60;message&#x60; of an &#x60;error&#x60; event to the user.  Only the user who created the run can read it. Everybody else, administrators included, receives a 404.  **Required permission:** logged in, owner of the run
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if &#x60;after&#x60; is not a non-negative integer.
     * <p><b>404</b> - Returned if the run does not exist, has been deleted after its retention period, or belongs to another user.  **Required permission:** owner of the run
     * @param id Run id
     * @param after Cursor: only events with a &#x60;seq&#x60; greater than this value are returned. Defaults to 0, which returns every event.
     * @return AITextTransformRunModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getAiTextTransformRunRequestCreation(@jakarta.annotation.Nonnull UUID id, @jakarta.annotation.Nullable Integer after) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getAiTextTransformRun", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "after", after));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<AITextTransformRunModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/ai_text_transform_runs/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * View AI text transform run
     * Returns the run with its status and the events recorded after the &#x60;after&#x60; cursor. The events are an append-only log; a client reads the generated text by polling this endpoint until the status is terminal.  ## Polling  * Send the highest &#x60;seq&#x60; seen so far as &#x60;after&#x60;. The first request uses &#x60;after&#x3D;0&#x60; or omits   the parameter. The response then contains only events the client has not seen. * Keep one request in flight at a time. Start polling after about 400 ms and increase the   interval by 150 ms per poll up to 1000 ms. * Ignore a response whose requested cursor is no longer the highest seen &#x60;seq&#x60;. * Stop when &#x60;status&#x60; is &#x60;succeeded&#x60;, &#x60;failed&#x60; or &#x60;cancelled&#x60;. The terminal status can arrive   in the same response as the last events. * Pause polling while the document is hidden and resume when it becomes visible. * Render &#x60;text_delta&#x60; payloads progressively. Once &#x60;completed&#x60; arrives, replace the rendered   text with its &#x60;text&#x60; and build any diff from it, never from the concatenated deltas. * Show the &#x60;message&#x60; of an &#x60;error&#x60; event to the user.  Only the user who created the run can read it. Everybody else, administrators included, receives a 404.  **Required permission:** logged in, owner of the run
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if &#x60;after&#x60; is not a non-negative integer.
     * <p><b>404</b> - Returned if the run does not exist, has been deleted after its retention period, or belongs to another user.  **Required permission:** owner of the run
     * @param id Run id
     * @param after Cursor: only events with a &#x60;seq&#x60; greater than this value are returned. Defaults to 0, which returns every event.
     * @return AITextTransformRunModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public AITextTransformRunModel getAiTextTransformRun(@jakarta.annotation.Nonnull UUID id, @jakarta.annotation.Nullable Integer after) throws RestClientResponseException {
        ParameterizedTypeReference<AITextTransformRunModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getAiTextTransformRunRequestCreation(id, after).body(localVarReturnType);
    }

    /**
     * View AI text transform run
     * Returns the run with its status and the events recorded after the &#x60;after&#x60; cursor. The events are an append-only log; a client reads the generated text by polling this endpoint until the status is terminal.  ## Polling  * Send the highest &#x60;seq&#x60; seen so far as &#x60;after&#x60;. The first request uses &#x60;after&#x3D;0&#x60; or omits   the parameter. The response then contains only events the client has not seen. * Keep one request in flight at a time. Start polling after about 400 ms and increase the   interval by 150 ms per poll up to 1000 ms. * Ignore a response whose requested cursor is no longer the highest seen &#x60;seq&#x60;. * Stop when &#x60;status&#x60; is &#x60;succeeded&#x60;, &#x60;failed&#x60; or &#x60;cancelled&#x60;. The terminal status can arrive   in the same response as the last events. * Pause polling while the document is hidden and resume when it becomes visible. * Render &#x60;text_delta&#x60; payloads progressively. Once &#x60;completed&#x60; arrives, replace the rendered   text with its &#x60;text&#x60; and build any diff from it, never from the concatenated deltas. * Show the &#x60;message&#x60; of an &#x60;error&#x60; event to the user.  Only the user who created the run can read it. Everybody else, administrators included, receives a 404.  **Required permission:** logged in, owner of the run
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if &#x60;after&#x60; is not a non-negative integer.
     * <p><b>404</b> - Returned if the run does not exist, has been deleted after its retention period, or belongs to another user.  **Required permission:** owner of the run
     * @param id Run id
     * @param after Cursor: only events with a &#x60;seq&#x60; greater than this value are returned. Defaults to 0, which returns every event.
     * @return ResponseEntity&lt;AITextTransformRunModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<AITextTransformRunModel> getAiTextTransformRunWithHttpInfo(@jakarta.annotation.Nonnull UUID id, @jakarta.annotation.Nullable Integer after) throws RestClientResponseException {
        ParameterizedTypeReference<AITextTransformRunModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getAiTextTransformRunRequestCreation(id, after).toEntity(localVarReturnType);
    }

    /**
     * View AI text transform run
     * Returns the run with its status and the events recorded after the &#x60;after&#x60; cursor. The events are an append-only log; a client reads the generated text by polling this endpoint until the status is terminal.  ## Polling  * Send the highest &#x60;seq&#x60; seen so far as &#x60;after&#x60;. The first request uses &#x60;after&#x3D;0&#x60; or omits   the parameter. The response then contains only events the client has not seen. * Keep one request in flight at a time. Start polling after about 400 ms and increase the   interval by 150 ms per poll up to 1000 ms. * Ignore a response whose requested cursor is no longer the highest seen &#x60;seq&#x60;. * Stop when &#x60;status&#x60; is &#x60;succeeded&#x60;, &#x60;failed&#x60; or &#x60;cancelled&#x60;. The terminal status can arrive   in the same response as the last events. * Pause polling while the document is hidden and resume when it becomes visible. * Render &#x60;text_delta&#x60; payloads progressively. Once &#x60;completed&#x60; arrives, replace the rendered   text with its &#x60;text&#x60; and build any diff from it, never from the concatenated deltas. * Show the &#x60;message&#x60; of an &#x60;error&#x60; event to the user.  Only the user who created the run can read it. Everybody else, administrators included, receives a 404.  **Required permission:** logged in, owner of the run
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if &#x60;after&#x60; is not a non-negative integer.
     * <p><b>404</b> - Returned if the run does not exist, has been deleted after its retention period, or belongs to another user.  **Required permission:** owner of the run
     * @param id Run id
     * @param after Cursor: only events with a &#x60;seq&#x60; greater than this value are returned. Defaults to 0, which returns every event.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getAiTextTransformRunWithResponseSpec(@jakarta.annotation.Nonnull UUID id, @jakarta.annotation.Nullable Integer after) throws RestClientResponseException {
        return getAiTextTransformRunRequestCreation(id, after);
    }

    /**
     * List AI text actions
     * Lists the active AI text actions that can be run without a context, that is actions configured to be usable everywhere. Actions scoped to work package types are omitted; use the work package or project variant of this endpoint to list them.  The collection is empty when the feature flag &#x60;ai_text_actions&#x60; is off, when the administrative setting is off or when no LLM connection is ready. An empty collection means \&quot;assistant not available\&quot;.  **Required permission:** logged in
     * <p><b>200</b> - OK
     * <p><b>401</b> - Returned if no user is currently authenticated.  **Required permission:** logged in
     * @return AITextActionCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listAiTextActionsRequestCreation() throws RestClientResponseException {
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

        ParameterizedTypeReference<AITextActionCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/ai_text_actions", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List AI text actions
     * Lists the active AI text actions that can be run without a context, that is actions configured to be usable everywhere. Actions scoped to work package types are omitted; use the work package or project variant of this endpoint to list them.  The collection is empty when the feature flag &#x60;ai_text_actions&#x60; is off, when the administrative setting is off or when no LLM connection is ready. An empty collection means \&quot;assistant not available\&quot;.  **Required permission:** logged in
     * <p><b>200</b> - OK
     * <p><b>401</b> - Returned if no user is currently authenticated.  **Required permission:** logged in
     * @return AITextActionCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public AITextActionCollectionModel listAiTextActions() throws RestClientResponseException {
        ParameterizedTypeReference<AITextActionCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAiTextActionsRequestCreation().body(localVarReturnType);
    }

    /**
     * List AI text actions
     * Lists the active AI text actions that can be run without a context, that is actions configured to be usable everywhere. Actions scoped to work package types are omitted; use the work package or project variant of this endpoint to list them.  The collection is empty when the feature flag &#x60;ai_text_actions&#x60; is off, when the administrative setting is off or when no LLM connection is ready. An empty collection means \&quot;assistant not available\&quot;.  **Required permission:** logged in
     * <p><b>200</b> - OK
     * <p><b>401</b> - Returned if no user is currently authenticated.  **Required permission:** logged in
     * @return ResponseEntity&lt;AITextActionCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<AITextActionCollectionModel> listAiTextActionsWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<AITextActionCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAiTextActionsRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * List AI text actions
     * Lists the active AI text actions that can be run without a context, that is actions configured to be usable everywhere. Actions scoped to work package types are omitted; use the work package or project variant of this endpoint to list them.  The collection is empty when the feature flag &#x60;ai_text_actions&#x60; is off, when the administrative setting is off or when no LLM connection is ready. An empty collection means \&quot;assistant not available\&quot;.  **Required permission:** logged in
     * <p><b>200</b> - OK
     * <p><b>401</b> - Returned if no user is currently authenticated.  **Required permission:** logged in
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listAiTextActionsWithResponseSpec() throws RestClientResponseException {
        return listAiTextActionsRequestCreation();
    }

    /**
     * List AI text actions for a new work package
     * Lists the active AI text actions that can be run on the description of a work package about to be created in the given project with the given type: actions usable everywhere, actions scoped to all work package types and actions scoped to that type. Actions that inject the type template are listed only when the type, as configured in the project, has a default description.  The collection is empty when the feature flag &#x60;ai_text_actions&#x60; is off, when the administrative setting is off or when no LLM connection is ready. An empty collection means \&quot;assistant not available\&quot;.  **Required permission:** add work packages
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the &#x60;typeId&#x60; query parameter is missing or not an integer.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding project.*
     * <p><b>404</b> - Returned if the project does not exist, the client does not have sufficient permissions to see it, or the type is not enabled in the project.  **Required permission:** view project
     * @param id Project id
     * @param typeId Id of the type the work package is about to be created with. The type has to be enabled in the project.
     * @return AITextActionCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listAiTextActionsByProjectRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer typeId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listAiTextActionsByProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'typeId' is set
        if (typeId == null) {
            throw new RestClientResponseException("Missing the required parameter 'typeId' when calling listAiTextActionsByProject", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "typeId", typeId));

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<AITextActionCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/projects/{id}/ai_text_actions", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List AI text actions for a new work package
     * Lists the active AI text actions that can be run on the description of a work package about to be created in the given project with the given type: actions usable everywhere, actions scoped to all work package types and actions scoped to that type. Actions that inject the type template are listed only when the type, as configured in the project, has a default description.  The collection is empty when the feature flag &#x60;ai_text_actions&#x60; is off, when the administrative setting is off or when no LLM connection is ready. An empty collection means \&quot;assistant not available\&quot;.  **Required permission:** add work packages
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the &#x60;typeId&#x60; query parameter is missing or not an integer.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding project.*
     * <p><b>404</b> - Returned if the project does not exist, the client does not have sufficient permissions to see it, or the type is not enabled in the project.  **Required permission:** view project
     * @param id Project id
     * @param typeId Id of the type the work package is about to be created with. The type has to be enabled in the project.
     * @return AITextActionCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public AITextActionCollectionModel listAiTextActionsByProject(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer typeId) throws RestClientResponseException {
        ParameterizedTypeReference<AITextActionCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAiTextActionsByProjectRequestCreation(id, typeId).body(localVarReturnType);
    }

    /**
     * List AI text actions for a new work package
     * Lists the active AI text actions that can be run on the description of a work package about to be created in the given project with the given type: actions usable everywhere, actions scoped to all work package types and actions scoped to that type. Actions that inject the type template are listed only when the type, as configured in the project, has a default description.  The collection is empty when the feature flag &#x60;ai_text_actions&#x60; is off, when the administrative setting is off or when no LLM connection is ready. An empty collection means \&quot;assistant not available\&quot;.  **Required permission:** add work packages
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the &#x60;typeId&#x60; query parameter is missing or not an integer.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding project.*
     * <p><b>404</b> - Returned if the project does not exist, the client does not have sufficient permissions to see it, or the type is not enabled in the project.  **Required permission:** view project
     * @param id Project id
     * @param typeId Id of the type the work package is about to be created with. The type has to be enabled in the project.
     * @return ResponseEntity&lt;AITextActionCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<AITextActionCollectionModel> listAiTextActionsByProjectWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer typeId) throws RestClientResponseException {
        ParameterizedTypeReference<AITextActionCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAiTextActionsByProjectRequestCreation(id, typeId).toEntity(localVarReturnType);
    }

    /**
     * List AI text actions for a new work package
     * Lists the active AI text actions that can be run on the description of a work package about to be created in the given project with the given type: actions usable everywhere, actions scoped to all work package types and actions scoped to that type. Actions that inject the type template are listed only when the type, as configured in the project, has a default description.  The collection is empty when the feature flag &#x60;ai_text_actions&#x60; is off, when the administrative setting is off or when no LLM connection is ready. An empty collection means \&quot;assistant not available\&quot;.  **Required permission:** add work packages
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the &#x60;typeId&#x60; query parameter is missing or not an integer.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** add work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding project.*
     * <p><b>404</b> - Returned if the project does not exist, the client does not have sufficient permissions to see it, or the type is not enabled in the project.  **Required permission:** view project
     * @param id Project id
     * @param typeId Id of the type the work package is about to be created with. The type has to be enabled in the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listAiTextActionsByProjectWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull Integer typeId) throws RestClientResponseException {
        return listAiTextActionsByProjectRequestCreation(id, typeId);
    }

    /**
     * List AI text actions for a work package
     * Lists the active AI text actions that can be run on the description of the given work package: actions usable everywhere, actions scoped to all work package types and actions scoped to the work package&#39;s type. Actions that inject the type template are listed only when the work package&#39;s type, as configured in its project, has a default description.  The collection is empty when the feature flag &#x60;ai_text_actions&#x60; is off, when the administrative setting is off or when no LLM connection is ready. An empty collection means \&quot;assistant not available\&quot;.  **Required permission:** edit work packages
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages
     * @param id Work package id
     * @return AITextActionCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listAiTextActionsByWorkPackageRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listAiTextActionsByWorkPackage", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<AITextActionCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages/{id}/ai_text_actions", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List AI text actions for a work package
     * Lists the active AI text actions that can be run on the description of the given work package: actions usable everywhere, actions scoped to all work package types and actions scoped to the work package&#39;s type. Actions that inject the type template are listed only when the work package&#39;s type, as configured in its project, has a default description.  The collection is empty when the feature flag &#x60;ai_text_actions&#x60; is off, when the administrative setting is off or when no LLM connection is ready. An empty collection means \&quot;assistant not available\&quot;.  **Required permission:** edit work packages
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages
     * @param id Work package id
     * @return AITextActionCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public AITextActionCollectionModel listAiTextActionsByWorkPackage(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<AITextActionCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAiTextActionsByWorkPackageRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List AI text actions for a work package
     * Lists the active AI text actions that can be run on the description of the given work package: actions usable everywhere, actions scoped to all work package types and actions scoped to the work package&#39;s type. Actions that inject the type template are listed only when the work package&#39;s type, as configured in its project, has a default description.  The collection is empty when the feature flag &#x60;ai_text_actions&#x60; is off, when the administrative setting is off or when no LLM connection is ready. An empty collection means \&quot;assistant not available\&quot;.  **Required permission:** edit work packages
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages
     * @param id Work package id
     * @return ResponseEntity&lt;AITextActionCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<AITextActionCollectionModel> listAiTextActionsByWorkPackageWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<AITextActionCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listAiTextActionsByWorkPackageRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List AI text actions for a work package
     * Lists the active AI text actions that can be run on the description of the given work package: actions usable everywhere, actions scoped to all work package types and actions scoped to the work package&#39;s type. Actions that inject the type template are listed only when the work package&#39;s type, as configured in its project, has a default description.  The collection is empty when the feature flag &#x60;ai_text_actions&#x60; is off, when the administrative setting is off or when no LLM connection is ready. An empty collection means \&quot;assistant not available\&quot;.  **Required permission:** edit work packages
     * <p><b>200</b> - OK
     * <p><b>403</b> - Returned if the client does not have sufficient permissions.  **Required permission:** edit work packages  *Note that you will only receive this error, if you are at least allowed to see the corresponding work package.*
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** view work packages
     * @param id Work package id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listAiTextActionsByWorkPackageWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return listAiTextActionsByWorkPackageRequestCreation(id);
    }
}
