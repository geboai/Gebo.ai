package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesIssuableTimeStats;
import ai.gebo.gitlab.client.model.APIEntitiesIssue;
import ai.gebo.gitlab.client.model.APIEntitiesIssueLink;
import ai.gebo.gitlab.client.model.APIEntitiesMergeRequestBasic;
import ai.gebo.gitlab.client.model.APIEntitiesRelatedIssue;
import ai.gebo.gitlab.client.model.APIEntitiesUserAgentDetail;
import ai.gebo.gitlab.client.model.APIEntitiesUserBasic;
import ai.gebo.gitlab.client.model.GetApiV4MergeRequestsAssigneeIdParameter;
import java.time.OffsetDateTime;
import ai.gebo.gitlab.client.model.PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdIssuesIssueIidAddSpentTimeRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdIssuesIssueIidLinksRequest;
import ai.gebo.gitlab.client.model.PostApiV4ProjectsIdIssuesIssueIidTimeEstimateRequest;

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
public class IssuesApi {
    private ApiClient apiClient;

    public IssuesApi() {
        this(new ApiClient());
    }

    public IssuesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete an issue link
     * Deletes a specified issue link, removing the two-way relationship.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid Internal ID of the issue.
     * @param issueLinkId ID of the issue relationship.
     * @return APIEntitiesIssueLink
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4ProjectsIdIssuesIssueIidLinksIssueLinkIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter issueLinkId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4ProjectsIdIssuesIssueIidLinksIssueLinkId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueIid' is set
        if (issueIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueIid' when calling deleteApiV4ProjectsIdIssuesIssueIidLinksIssueLinkId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueLinkId' is set
        if (issueLinkId == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueLinkId' when calling deleteApiV4ProjectsIdIssuesIssueIidLinksIssueLinkId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("issue_iid", issueIid);
        pathParams.put("issue_link_id", issueLinkId);

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

        ParameterizedTypeReference<APIEntitiesIssueLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{issue_iid}/links/{issue_link_id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete an issue link
     * Deletes a specified issue link, removing the two-way relationship.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid Internal ID of the issue.
     * @param issueLinkId ID of the issue relationship.
     * @return APIEntitiesIssueLink
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesIssueLink deleteApiV4ProjectsIdIssuesIssueIidLinksIssueLinkId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter issueLinkId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIssueLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdIssuesIssueIidLinksIssueLinkIdRequestCreation(id, issueIid, issueLinkId).body(localVarReturnType);
    }

    /**
     * Delete an issue link
     * Deletes a specified issue link, removing the two-way relationship.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid Internal ID of the issue.
     * @param issueLinkId ID of the issue relationship.
     * @return ResponseEntity&lt;APIEntitiesIssueLink&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesIssueLink> deleteApiV4ProjectsIdIssuesIssueIidLinksIssueLinkIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter issueLinkId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIssueLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4ProjectsIdIssuesIssueIidLinksIssueLinkIdRequestCreation(id, issueIid, issueLinkId).toEntity(localVarReturnType);
    }

    /**
     * Delete an issue link
     * Deletes a specified issue link, removing the two-way relationship.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid Internal ID of the issue.
     * @param issueLinkId ID of the issue relationship.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4ProjectsIdIssuesIssueIidLinksIssueLinkIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter issueLinkId) throws RestClientResponseException {
        return deleteApiV4ProjectsIdIssuesIssueIidLinksIssueLinkIdRequestCreation(id, issueIid, issueLinkId);
    }

    /**
     * List all issues for the currently authenticated user
     * Lists all issues accessible by the currently authenticated user. By default, returns only issues created by the current user. To list all issues, use parameter &#x60;scope&#x3D;all&#x60;.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param withLabelsDetails If &#x60;true&#x60;, the response returns more details for each label in the labels field: &#x60;name&#x60;, &#x60;color&#x60;, &#x60;description&#x60;, &#x60;description_html&#x60;, &#x60;text_color&#x60;.
     * @param state Filter issues by state.
     * @param closedById Return issues closed by the user with the given ID.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param dueDate Return issues that have no due date, are overdue, or whose due date is this week, this month, or between two weeks ago and next month.
     * @param issueType The type of the issue. Accepts: issue, incident, test_case, requirement, task, ticket
     * @param labels Comma-separated list of label names. &#x60;None&#x60; means no labels are assigned. &#x60;Any&#x60; means at least one label is assigned. &#x60;No+Label&#x60; (deprecated) means no labels are assigned. Set to an empty string to unassign all labels. If a label does not already exist, this creates a new project label and assigns it to the issue. Predefined names are case-insensitive.
     * @param milestone Milestone title. &#x60;None&#x60; lists all issues with no milestone. &#x60;Any&#x60; lists all issues that have an assigned milestone. Support for &#x60;None&#x60; and &#x60;Any&#x60; is [planned for removal](https://gitlab.com/gitlab-org/gitlab/-/issues/336044). Use the &#x60;milestone_id&#x60; attribute instead. Mutually exclusive with &#x60;milestone_id&#x60;.
     * @param milestoneId Return issues assigned to milestones with a given timebox value. &#x60;None&#x60; lists all issues with no milestone. &#x60;Any&#x60; lists all issues that have an assigned milestone. &#x60;Upcoming&#x60; lists all issues assigned to milestones due in the future. &#x60;Started&#x60; lists all issues assigned to open, started milestones. The logic for &#x60;Upcoming&#x60; and &#x60;Started&#x60; differs from the logic used in the [GraphQL API](https://docs.gitlab.com/user/project/milestones/#special-milestone-filters). Mutually exclusive with &#x60;milestone&#x60;.
     * @param iids Return only the issues having the given &#x60;iid&#x60;.
     * @param search Search issues against their &#x60;title&#x60; and &#x60;description&#x60;.
     * @param in Modify the scope of the &#x60;search&#x60; attribute to &#x60;title&#x60;, &#x60;description&#x60;, or &#x60;title,description&#x60;. If omitted, defaults to &#x60;title,description&#x60;.
     * @param authorId Return issues created by the given user ID. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;author_username&#x60;.
     * @param authorUsername Return issues created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;author_id&#x60;.
     * @param assigneeId Return issues assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned issues and &#x60;Any&#x60; returns issues with an assignee. Mutually exclusive with &#x60;assignee_username&#x60;.
     * @param assigneeUsername Return issues assigned to the given username. In GitLab Community Edition, only a single value is accepted. Otherwise, an invalid parameter error is returned. When multiple usernames are given, only issues assigned to all of them are returned. Mutually exclusive with &#x60;assignee_id&#x60;.
     * @param createdAfter Return issues created on or after the specified time.
     * @param createdBefore Return issues created on or before the specified time.
     * @param updatedAfter Return issues updated on or after the specified time.
     * @param updatedBefore Return issues updated on or before the specified time.
     * @param not Return issues that do not match the specified parameters.
     * @param notLabels Comma-separated list of label names.
     * @param notMilestone Milestone title. Mutually exclusive with &#x60;not[milestone_id]&#x60;.
     * @param notMilestoneId Return issues assigned to milestones without the specified timebox value. Mutually exclusive with &#x60;not[milestone]&#x60;.
     * @param notIids Internal IDs of issues to exclude.
     * @param notAuthorId Return issues not authored by the user with the given ID. Mutually exclusive with &#x60;not[author_username]&#x60;.
     * @param notAuthorUsername Return issues not authored by the user with the given username. Mutually exclusive with &#x60;not[author_id]&#x60;.
     * @param notAssigneeId Return issues not assigned to the user with the given ID. Mutually exclusive with &#x60;not[assignee_username]&#x60;.
     * @param notAssigneeUsername Return issues not assigned to the user with the given username. Mutually exclusive with &#x60;not[assignee_id]&#x60;.
     * @param notWeight Return issues that do not have the specified weight.
     * @param notIterationId Return issues not assigned to the iteration with the given ID. Mutually exclusive with &#x60;not[iteration_title]&#x60;.
     * @param notIterationTitle Return issues not assigned to the iteration with the given title. Mutually exclusive with &#x60;not[iteration_id]&#x60;.
     * @param scope Return issues for the given scope.
     * @param myReactionEmoji Return issues reacted to by the authenticated user with the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues with no reaction and &#x60;Any&#x60; returns issues with at least one reaction.
     * @param confidential If &#x60;true&#x60;, returns only confidential issues. If &#x60;false&#x60;, returns only public issues.
     * @param weight The weight of the issue
     * @param epicId Return issues associated with the epic with the given ID. &#x60;None&#x60; returns issues that are not associated with an epic. &#x60;Any&#x60; returns issues that are associated with an epic. Premium and Ultimate only.
     * @param healthStatus The health status of the issue. Must be one of: on_track, needs_attention, at_risk, none, any
     * @param iterationId Return issues assigned to the iteration with the given ID. &#x60;None&#x60; returns issues that do not belong to an iteration. &#x60;Any&#x60; returns issues that belong to an iteration. Premium and Ultimate only. Mutually exclusive with &#x60;iteration_title&#x60;.
     * @param iterationTitle Return issues assigned to the iteration with the given title. Premium and Ultimate only. Mutually exclusive with &#x60;iteration_id&#x60;.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param nonArchived If &#x60;true&#x60;, returns only issues from non-archived projects. If &#x60;false&#x60;, returns issues from both archived and non-archived projects.
     * @return List&lt;APIEntitiesIssue&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4IssuesRequestCreation(@jakarta.annotation.Nullable Boolean withLabelsDetails, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable Integer closedById, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String dueDate, @jakarta.annotation.Nullable String issueType, @jakarta.annotation.Nullable List<String> labels, @jakarta.annotation.Nullable String milestone, @jakarta.annotation.Nullable String milestoneId, @jakarta.annotation.Nullable List<Integer> iids, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String in, @jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter assigneeId, @jakarta.annotation.Nullable List<String> assigneeUsername, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable List<String> notLabels, @jakarta.annotation.Nullable String notMilestone, @jakarta.annotation.Nullable String notMilestoneId, @jakarta.annotation.Nullable List<Integer> notIids, @jakarta.annotation.Nullable Integer notAuthorId, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable Integer notAssigneeId, @jakarta.annotation.Nullable List<String> notAssigneeUsername, @jakarta.annotation.Nullable Integer notWeight, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter notIterationId, @jakarta.annotation.Nullable String notIterationTitle, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String myReactionEmoji, @jakarta.annotation.Nullable Boolean confidential, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter weight, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter epicId, @jakarta.annotation.Nullable String healthStatus, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter iterationId, @jakarta.annotation.Nullable String iterationTitle, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean nonArchived) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "with_labels_details", withLabelsDetails));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "state", state));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "closed_by_id", closedById));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "order_by", orderBy));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "due_date", dueDate));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "issue_type", issueType));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "labels", labels));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "milestone", milestone));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "milestone_id", milestoneId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "iids", iids));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "in", in));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "author_id", authorId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "author_username", authorUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "assignee_id", assigneeId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "assignee_username", assigneeUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_after", createdAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_before", createdBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_after", updatedAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_before", updatedBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not", not));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "not[labels]", notLabels));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[milestone]", notMilestone));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[milestone_id]", notMilestoneId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "not[iids]", notIids));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[author_id]", notAuthorId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[author_username]", notAuthorUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[assignee_id]", notAssigneeId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "not[assignee_username]", notAssigneeUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[weight]", notWeight));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[iteration_id]", notIterationId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[iteration_title]", notIterationTitle));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "scope", scope));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "my_reaction_emoji", myReactionEmoji));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "confidential", confidential));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "weight", weight));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "epic_id", epicId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "health_status", healthStatus));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "iteration_id", iterationId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "iteration_title", iterationTitle));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "non_archived", nonArchived));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesIssue>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/issues", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all issues for the currently authenticated user
     * Lists all issues accessible by the currently authenticated user. By default, returns only issues created by the current user. To list all issues, use parameter &#x60;scope&#x3D;all&#x60;.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param withLabelsDetails If &#x60;true&#x60;, the response returns more details for each label in the labels field: &#x60;name&#x60;, &#x60;color&#x60;, &#x60;description&#x60;, &#x60;description_html&#x60;, &#x60;text_color&#x60;.
     * @param state Filter issues by state.
     * @param closedById Return issues closed by the user with the given ID.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param dueDate Return issues that have no due date, are overdue, or whose due date is this week, this month, or between two weeks ago and next month.
     * @param issueType The type of the issue. Accepts: issue, incident, test_case, requirement, task, ticket
     * @param labels Comma-separated list of label names. &#x60;None&#x60; means no labels are assigned. &#x60;Any&#x60; means at least one label is assigned. &#x60;No+Label&#x60; (deprecated) means no labels are assigned. Set to an empty string to unassign all labels. If a label does not already exist, this creates a new project label and assigns it to the issue. Predefined names are case-insensitive.
     * @param milestone Milestone title. &#x60;None&#x60; lists all issues with no milestone. &#x60;Any&#x60; lists all issues that have an assigned milestone. Support for &#x60;None&#x60; and &#x60;Any&#x60; is [planned for removal](https://gitlab.com/gitlab-org/gitlab/-/issues/336044). Use the &#x60;milestone_id&#x60; attribute instead. Mutually exclusive with &#x60;milestone_id&#x60;.
     * @param milestoneId Return issues assigned to milestones with a given timebox value. &#x60;None&#x60; lists all issues with no milestone. &#x60;Any&#x60; lists all issues that have an assigned milestone. &#x60;Upcoming&#x60; lists all issues assigned to milestones due in the future. &#x60;Started&#x60; lists all issues assigned to open, started milestones. The logic for &#x60;Upcoming&#x60; and &#x60;Started&#x60; differs from the logic used in the [GraphQL API](https://docs.gitlab.com/user/project/milestones/#special-milestone-filters). Mutually exclusive with &#x60;milestone&#x60;.
     * @param iids Return only the issues having the given &#x60;iid&#x60;.
     * @param search Search issues against their &#x60;title&#x60; and &#x60;description&#x60;.
     * @param in Modify the scope of the &#x60;search&#x60; attribute to &#x60;title&#x60;, &#x60;description&#x60;, or &#x60;title,description&#x60;. If omitted, defaults to &#x60;title,description&#x60;.
     * @param authorId Return issues created by the given user ID. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;author_username&#x60;.
     * @param authorUsername Return issues created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;author_id&#x60;.
     * @param assigneeId Return issues assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned issues and &#x60;Any&#x60; returns issues with an assignee. Mutually exclusive with &#x60;assignee_username&#x60;.
     * @param assigneeUsername Return issues assigned to the given username. In GitLab Community Edition, only a single value is accepted. Otherwise, an invalid parameter error is returned. When multiple usernames are given, only issues assigned to all of them are returned. Mutually exclusive with &#x60;assignee_id&#x60;.
     * @param createdAfter Return issues created on or after the specified time.
     * @param createdBefore Return issues created on or before the specified time.
     * @param updatedAfter Return issues updated on or after the specified time.
     * @param updatedBefore Return issues updated on or before the specified time.
     * @param not Return issues that do not match the specified parameters.
     * @param notLabels Comma-separated list of label names.
     * @param notMilestone Milestone title. Mutually exclusive with &#x60;not[milestone_id]&#x60;.
     * @param notMilestoneId Return issues assigned to milestones without the specified timebox value. Mutually exclusive with &#x60;not[milestone]&#x60;.
     * @param notIids Internal IDs of issues to exclude.
     * @param notAuthorId Return issues not authored by the user with the given ID. Mutually exclusive with &#x60;not[author_username]&#x60;.
     * @param notAuthorUsername Return issues not authored by the user with the given username. Mutually exclusive with &#x60;not[author_id]&#x60;.
     * @param notAssigneeId Return issues not assigned to the user with the given ID. Mutually exclusive with &#x60;not[assignee_username]&#x60;.
     * @param notAssigneeUsername Return issues not assigned to the user with the given username. Mutually exclusive with &#x60;not[assignee_id]&#x60;.
     * @param notWeight Return issues that do not have the specified weight.
     * @param notIterationId Return issues not assigned to the iteration with the given ID. Mutually exclusive with &#x60;not[iteration_title]&#x60;.
     * @param notIterationTitle Return issues not assigned to the iteration with the given title. Mutually exclusive with &#x60;not[iteration_id]&#x60;.
     * @param scope Return issues for the given scope.
     * @param myReactionEmoji Return issues reacted to by the authenticated user with the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues with no reaction and &#x60;Any&#x60; returns issues with at least one reaction.
     * @param confidential If &#x60;true&#x60;, returns only confidential issues. If &#x60;false&#x60;, returns only public issues.
     * @param weight The weight of the issue
     * @param epicId Return issues associated with the epic with the given ID. &#x60;None&#x60; returns issues that are not associated with an epic. &#x60;Any&#x60; returns issues that are associated with an epic. Premium and Ultimate only.
     * @param healthStatus The health status of the issue. Must be one of: on_track, needs_attention, at_risk, none, any
     * @param iterationId Return issues assigned to the iteration with the given ID. &#x60;None&#x60; returns issues that do not belong to an iteration. &#x60;Any&#x60; returns issues that belong to an iteration. Premium and Ultimate only. Mutually exclusive with &#x60;iteration_title&#x60;.
     * @param iterationTitle Return issues assigned to the iteration with the given title. Premium and Ultimate only. Mutually exclusive with &#x60;iteration_id&#x60;.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param nonArchived If &#x60;true&#x60;, returns only issues from non-archived projects. If &#x60;false&#x60;, returns issues from both archived and non-archived projects.
     * @return List&lt;APIEntitiesIssue&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesIssue> getApiV4Issues(@jakarta.annotation.Nullable Boolean withLabelsDetails, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable Integer closedById, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String dueDate, @jakarta.annotation.Nullable String issueType, @jakarta.annotation.Nullable List<String> labels, @jakarta.annotation.Nullable String milestone, @jakarta.annotation.Nullable String milestoneId, @jakarta.annotation.Nullable List<Integer> iids, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String in, @jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter assigneeId, @jakarta.annotation.Nullable List<String> assigneeUsername, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable List<String> notLabels, @jakarta.annotation.Nullable String notMilestone, @jakarta.annotation.Nullable String notMilestoneId, @jakarta.annotation.Nullable List<Integer> notIids, @jakarta.annotation.Nullable Integer notAuthorId, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable Integer notAssigneeId, @jakarta.annotation.Nullable List<String> notAssigneeUsername, @jakarta.annotation.Nullable Integer notWeight, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter notIterationId, @jakarta.annotation.Nullable String notIterationTitle, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String myReactionEmoji, @jakarta.annotation.Nullable Boolean confidential, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter weight, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter epicId, @jakarta.annotation.Nullable String healthStatus, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter iterationId, @jakarta.annotation.Nullable String iterationTitle, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean nonArchived) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesIssue>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4IssuesRequestCreation(withLabelsDetails, state, closedById, orderBy, sort, dueDate, issueType, labels, milestone, milestoneId, iids, search, in, authorId, authorUsername, assigneeId, assigneeUsername, createdAfter, createdBefore, updatedAfter, updatedBefore, not, notLabels, notMilestone, notMilestoneId, notIids, notAuthorId, notAuthorUsername, notAssigneeId, notAssigneeUsername, notWeight, notIterationId, notIterationTitle, scope, myReactionEmoji, confidential, weight, epicId, healthStatus, iterationId, iterationTitle, page, perPage, nonArchived).body(localVarReturnType);
    }

    /**
     * List all issues for the currently authenticated user
     * Lists all issues accessible by the currently authenticated user. By default, returns only issues created by the current user. To list all issues, use parameter &#x60;scope&#x3D;all&#x60;.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param withLabelsDetails If &#x60;true&#x60;, the response returns more details for each label in the labels field: &#x60;name&#x60;, &#x60;color&#x60;, &#x60;description&#x60;, &#x60;description_html&#x60;, &#x60;text_color&#x60;.
     * @param state Filter issues by state.
     * @param closedById Return issues closed by the user with the given ID.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param dueDate Return issues that have no due date, are overdue, or whose due date is this week, this month, or between two weeks ago and next month.
     * @param issueType The type of the issue. Accepts: issue, incident, test_case, requirement, task, ticket
     * @param labels Comma-separated list of label names. &#x60;None&#x60; means no labels are assigned. &#x60;Any&#x60; means at least one label is assigned. &#x60;No+Label&#x60; (deprecated) means no labels are assigned. Set to an empty string to unassign all labels. If a label does not already exist, this creates a new project label and assigns it to the issue. Predefined names are case-insensitive.
     * @param milestone Milestone title. &#x60;None&#x60; lists all issues with no milestone. &#x60;Any&#x60; lists all issues that have an assigned milestone. Support for &#x60;None&#x60; and &#x60;Any&#x60; is [planned for removal](https://gitlab.com/gitlab-org/gitlab/-/issues/336044). Use the &#x60;milestone_id&#x60; attribute instead. Mutually exclusive with &#x60;milestone_id&#x60;.
     * @param milestoneId Return issues assigned to milestones with a given timebox value. &#x60;None&#x60; lists all issues with no milestone. &#x60;Any&#x60; lists all issues that have an assigned milestone. &#x60;Upcoming&#x60; lists all issues assigned to milestones due in the future. &#x60;Started&#x60; lists all issues assigned to open, started milestones. The logic for &#x60;Upcoming&#x60; and &#x60;Started&#x60; differs from the logic used in the [GraphQL API](https://docs.gitlab.com/user/project/milestones/#special-milestone-filters). Mutually exclusive with &#x60;milestone&#x60;.
     * @param iids Return only the issues having the given &#x60;iid&#x60;.
     * @param search Search issues against their &#x60;title&#x60; and &#x60;description&#x60;.
     * @param in Modify the scope of the &#x60;search&#x60; attribute to &#x60;title&#x60;, &#x60;description&#x60;, or &#x60;title,description&#x60;. If omitted, defaults to &#x60;title,description&#x60;.
     * @param authorId Return issues created by the given user ID. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;author_username&#x60;.
     * @param authorUsername Return issues created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;author_id&#x60;.
     * @param assigneeId Return issues assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned issues and &#x60;Any&#x60; returns issues with an assignee. Mutually exclusive with &#x60;assignee_username&#x60;.
     * @param assigneeUsername Return issues assigned to the given username. In GitLab Community Edition, only a single value is accepted. Otherwise, an invalid parameter error is returned. When multiple usernames are given, only issues assigned to all of them are returned. Mutually exclusive with &#x60;assignee_id&#x60;.
     * @param createdAfter Return issues created on or after the specified time.
     * @param createdBefore Return issues created on or before the specified time.
     * @param updatedAfter Return issues updated on or after the specified time.
     * @param updatedBefore Return issues updated on or before the specified time.
     * @param not Return issues that do not match the specified parameters.
     * @param notLabels Comma-separated list of label names.
     * @param notMilestone Milestone title. Mutually exclusive with &#x60;not[milestone_id]&#x60;.
     * @param notMilestoneId Return issues assigned to milestones without the specified timebox value. Mutually exclusive with &#x60;not[milestone]&#x60;.
     * @param notIids Internal IDs of issues to exclude.
     * @param notAuthorId Return issues not authored by the user with the given ID. Mutually exclusive with &#x60;not[author_username]&#x60;.
     * @param notAuthorUsername Return issues not authored by the user with the given username. Mutually exclusive with &#x60;not[author_id]&#x60;.
     * @param notAssigneeId Return issues not assigned to the user with the given ID. Mutually exclusive with &#x60;not[assignee_username]&#x60;.
     * @param notAssigneeUsername Return issues not assigned to the user with the given username. Mutually exclusive with &#x60;not[assignee_id]&#x60;.
     * @param notWeight Return issues that do not have the specified weight.
     * @param notIterationId Return issues not assigned to the iteration with the given ID. Mutually exclusive with &#x60;not[iteration_title]&#x60;.
     * @param notIterationTitle Return issues not assigned to the iteration with the given title. Mutually exclusive with &#x60;not[iteration_id]&#x60;.
     * @param scope Return issues for the given scope.
     * @param myReactionEmoji Return issues reacted to by the authenticated user with the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues with no reaction and &#x60;Any&#x60; returns issues with at least one reaction.
     * @param confidential If &#x60;true&#x60;, returns only confidential issues. If &#x60;false&#x60;, returns only public issues.
     * @param weight The weight of the issue
     * @param epicId Return issues associated with the epic with the given ID. &#x60;None&#x60; returns issues that are not associated with an epic. &#x60;Any&#x60; returns issues that are associated with an epic. Premium and Ultimate only.
     * @param healthStatus The health status of the issue. Must be one of: on_track, needs_attention, at_risk, none, any
     * @param iterationId Return issues assigned to the iteration with the given ID. &#x60;None&#x60; returns issues that do not belong to an iteration. &#x60;Any&#x60; returns issues that belong to an iteration. Premium and Ultimate only. Mutually exclusive with &#x60;iteration_title&#x60;.
     * @param iterationTitle Return issues assigned to the iteration with the given title. Premium and Ultimate only. Mutually exclusive with &#x60;iteration_id&#x60;.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param nonArchived If &#x60;true&#x60;, returns only issues from non-archived projects. If &#x60;false&#x60;, returns issues from both archived and non-archived projects.
     * @return ResponseEntity&lt;List&lt;APIEntitiesIssue&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesIssue>> getApiV4IssuesWithHttpInfo(@jakarta.annotation.Nullable Boolean withLabelsDetails, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable Integer closedById, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String dueDate, @jakarta.annotation.Nullable String issueType, @jakarta.annotation.Nullable List<String> labels, @jakarta.annotation.Nullable String milestone, @jakarta.annotation.Nullable String milestoneId, @jakarta.annotation.Nullable List<Integer> iids, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String in, @jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter assigneeId, @jakarta.annotation.Nullable List<String> assigneeUsername, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable List<String> notLabels, @jakarta.annotation.Nullable String notMilestone, @jakarta.annotation.Nullable String notMilestoneId, @jakarta.annotation.Nullable List<Integer> notIids, @jakarta.annotation.Nullable Integer notAuthorId, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable Integer notAssigneeId, @jakarta.annotation.Nullable List<String> notAssigneeUsername, @jakarta.annotation.Nullable Integer notWeight, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter notIterationId, @jakarta.annotation.Nullable String notIterationTitle, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String myReactionEmoji, @jakarta.annotation.Nullable Boolean confidential, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter weight, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter epicId, @jakarta.annotation.Nullable String healthStatus, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter iterationId, @jakarta.annotation.Nullable String iterationTitle, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean nonArchived) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesIssue>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4IssuesRequestCreation(withLabelsDetails, state, closedById, orderBy, sort, dueDate, issueType, labels, milestone, milestoneId, iids, search, in, authorId, authorUsername, assigneeId, assigneeUsername, createdAfter, createdBefore, updatedAfter, updatedBefore, not, notLabels, notMilestone, notMilestoneId, notIids, notAuthorId, notAuthorUsername, notAssigneeId, notAssigneeUsername, notWeight, notIterationId, notIterationTitle, scope, myReactionEmoji, confidential, weight, epicId, healthStatus, iterationId, iterationTitle, page, perPage, nonArchived).toEntity(localVarReturnType);
    }

    /**
     * List all issues for the currently authenticated user
     * Lists all issues accessible by the currently authenticated user. By default, returns only issues created by the current user. To list all issues, use parameter &#x60;scope&#x3D;all&#x60;.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param withLabelsDetails If &#x60;true&#x60;, the response returns more details for each label in the labels field: &#x60;name&#x60;, &#x60;color&#x60;, &#x60;description&#x60;, &#x60;description_html&#x60;, &#x60;text_color&#x60;.
     * @param state Filter issues by state.
     * @param closedById Return issues closed by the user with the given ID.
     * @param orderBy Sort results by the specified field.
     * @param sort Sort results in ascending or descending order.
     * @param dueDate Return issues that have no due date, are overdue, or whose due date is this week, this month, or between two weeks ago and next month.
     * @param issueType The type of the issue. Accepts: issue, incident, test_case, requirement, task, ticket
     * @param labels Comma-separated list of label names. &#x60;None&#x60; means no labels are assigned. &#x60;Any&#x60; means at least one label is assigned. &#x60;No+Label&#x60; (deprecated) means no labels are assigned. Set to an empty string to unassign all labels. If a label does not already exist, this creates a new project label and assigns it to the issue. Predefined names are case-insensitive.
     * @param milestone Milestone title. &#x60;None&#x60; lists all issues with no milestone. &#x60;Any&#x60; lists all issues that have an assigned milestone. Support for &#x60;None&#x60; and &#x60;Any&#x60; is [planned for removal](https://gitlab.com/gitlab-org/gitlab/-/issues/336044). Use the &#x60;milestone_id&#x60; attribute instead. Mutually exclusive with &#x60;milestone_id&#x60;.
     * @param milestoneId Return issues assigned to milestones with a given timebox value. &#x60;None&#x60; lists all issues with no milestone. &#x60;Any&#x60; lists all issues that have an assigned milestone. &#x60;Upcoming&#x60; lists all issues assigned to milestones due in the future. &#x60;Started&#x60; lists all issues assigned to open, started milestones. The logic for &#x60;Upcoming&#x60; and &#x60;Started&#x60; differs from the logic used in the [GraphQL API](https://docs.gitlab.com/user/project/milestones/#special-milestone-filters). Mutually exclusive with &#x60;milestone&#x60;.
     * @param iids Return only the issues having the given &#x60;iid&#x60;.
     * @param search Search issues against their &#x60;title&#x60; and &#x60;description&#x60;.
     * @param in Modify the scope of the &#x60;search&#x60; attribute to &#x60;title&#x60;, &#x60;description&#x60;, or &#x60;title,description&#x60;. If omitted, defaults to &#x60;title,description&#x60;.
     * @param authorId Return issues created by the given user ID. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;author_username&#x60;.
     * @param authorUsername Return issues created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;author_id&#x60;.
     * @param assigneeId Return issues assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned issues and &#x60;Any&#x60; returns issues with an assignee. Mutually exclusive with &#x60;assignee_username&#x60;.
     * @param assigneeUsername Return issues assigned to the given username. In GitLab Community Edition, only a single value is accepted. Otherwise, an invalid parameter error is returned. When multiple usernames are given, only issues assigned to all of them are returned. Mutually exclusive with &#x60;assignee_id&#x60;.
     * @param createdAfter Return issues created on or after the specified time.
     * @param createdBefore Return issues created on or before the specified time.
     * @param updatedAfter Return issues updated on or after the specified time.
     * @param updatedBefore Return issues updated on or before the specified time.
     * @param not Return issues that do not match the specified parameters.
     * @param notLabels Comma-separated list of label names.
     * @param notMilestone Milestone title. Mutually exclusive with &#x60;not[milestone_id]&#x60;.
     * @param notMilestoneId Return issues assigned to milestones without the specified timebox value. Mutually exclusive with &#x60;not[milestone]&#x60;.
     * @param notIids Internal IDs of issues to exclude.
     * @param notAuthorId Return issues not authored by the user with the given ID. Mutually exclusive with &#x60;not[author_username]&#x60;.
     * @param notAuthorUsername Return issues not authored by the user with the given username. Mutually exclusive with &#x60;not[author_id]&#x60;.
     * @param notAssigneeId Return issues not assigned to the user with the given ID. Mutually exclusive with &#x60;not[assignee_username]&#x60;.
     * @param notAssigneeUsername Return issues not assigned to the user with the given username. Mutually exclusive with &#x60;not[assignee_id]&#x60;.
     * @param notWeight Return issues that do not have the specified weight.
     * @param notIterationId Return issues not assigned to the iteration with the given ID. Mutually exclusive with &#x60;not[iteration_title]&#x60;.
     * @param notIterationTitle Return issues not assigned to the iteration with the given title. Mutually exclusive with &#x60;not[iteration_id]&#x60;.
     * @param scope Return issues for the given scope.
     * @param myReactionEmoji Return issues reacted to by the authenticated user with the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues with no reaction and &#x60;Any&#x60; returns issues with at least one reaction.
     * @param confidential If &#x60;true&#x60;, returns only confidential issues. If &#x60;false&#x60;, returns only public issues.
     * @param weight The weight of the issue
     * @param epicId Return issues associated with the epic with the given ID. &#x60;None&#x60; returns issues that are not associated with an epic. &#x60;Any&#x60; returns issues that are associated with an epic. Premium and Ultimate only.
     * @param healthStatus The health status of the issue. Must be one of: on_track, needs_attention, at_risk, none, any
     * @param iterationId Return issues assigned to the iteration with the given ID. &#x60;None&#x60; returns issues that do not belong to an iteration. &#x60;Any&#x60; returns issues that belong to an iteration. Premium and Ultimate only. Mutually exclusive with &#x60;iteration_title&#x60;.
     * @param iterationTitle Return issues assigned to the iteration with the given title. Premium and Ultimate only. Mutually exclusive with &#x60;iteration_id&#x60;.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @param nonArchived If &#x60;true&#x60;, returns only issues from non-archived projects. If &#x60;false&#x60;, returns issues from both archived and non-archived projects.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4IssuesWithResponseSpec(@jakarta.annotation.Nullable Boolean withLabelsDetails, @jakarta.annotation.Nullable String state, @jakarta.annotation.Nullable Integer closedById, @jakarta.annotation.Nullable String orderBy, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable String dueDate, @jakarta.annotation.Nullable String issueType, @jakarta.annotation.Nullable List<String> labels, @jakarta.annotation.Nullable String milestone, @jakarta.annotation.Nullable String milestoneId, @jakarta.annotation.Nullable List<Integer> iids, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String in, @jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter assigneeId, @jakarta.annotation.Nullable List<String> assigneeUsername, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable List<String> notLabels, @jakarta.annotation.Nullable String notMilestone, @jakarta.annotation.Nullable String notMilestoneId, @jakarta.annotation.Nullable List<Integer> notIids, @jakarta.annotation.Nullable Integer notAuthorId, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable Integer notAssigneeId, @jakarta.annotation.Nullable List<String> notAssigneeUsername, @jakarta.annotation.Nullable Integer notWeight, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter notIterationId, @jakarta.annotation.Nullable String notIterationTitle, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String myReactionEmoji, @jakarta.annotation.Nullable Boolean confidential, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter weight, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter epicId, @jakarta.annotation.Nullable String healthStatus, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter iterationId, @jakarta.annotation.Nullable String iterationTitle, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage, @jakarta.annotation.Nullable Boolean nonArchived) throws RestClientResponseException {
        return getApiV4IssuesRequestCreation(withLabelsDetails, state, closedById, orderBy, sort, dueDate, issueType, labels, milestone, milestoneId, iids, search, in, authorId, authorUsername, assigneeId, assigneeUsername, createdAfter, createdBefore, updatedAfter, updatedBefore, not, notLabels, notMilestone, notMilestoneId, notIids, notAuthorId, notAuthorUsername, notAssigneeId, notAssigneeUsername, notWeight, notIterationId, notIterationTitle, scope, myReactionEmoji, confidential, weight, epicId, healthStatus, iterationId, iterationTitle, page, perPage, nonArchived);
    }

    /**
     * Retrieve an issue
     * Retrieves a specified issue. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the issue.
     * @return APIEntitiesIssue
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4IssuesIdRequestCreation(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4IssuesId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

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

        ParameterizedTypeReference<APIEntitiesIssue> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/issues/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve an issue
     * Retrieves a specified issue. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the issue.
     * @return APIEntitiesIssue
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesIssue getApiV4IssuesId(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIssue> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4IssuesIdRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Retrieve an issue
     * Retrieves a specified issue. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the issue.
     * @return ResponseEntity&lt;APIEntitiesIssue&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesIssue> getApiV4IssuesIdWithHttpInfo(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIssue> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4IssuesIdRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Retrieve an issue
     * Retrieves a specified issue. Administrators only.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the issue.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4IssuesIdWithResponseSpec(@jakarta.annotation.Nonnull String id) throws RestClientResponseException {
        return getApiV4IssuesIdRequestCreation(id);
    }

    /**
     * Retrieve issues statistics for the currently authenticated user
     * Retrieves statistics for issues accessible by the currently authenticated user. By default, returns only issues created by the current user. To get all issues, set the &#x60;scope&#x60; attribute to &#x60;all&#x60;.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param labels Comma-separated list of label names. &#x60;None&#x60; means no labels are assigned. &#x60;Any&#x60; means at least one label is assigned. &#x60;No+Label&#x60; (deprecated) means no labels are assigned. Set to an empty string to unassign all labels. If a label does not already exist, this creates a new project label and assigns it to the issue. Predefined names are case-insensitive.
     * @param milestone Milestone title. &#x60;None&#x60; lists all issues with no milestone. &#x60;Any&#x60; lists all issues that have an assigned milestone. Support for &#x60;None&#x60; and &#x60;Any&#x60; is [planned for removal](https://gitlab.com/gitlab-org/gitlab/-/issues/336044). Use the &#x60;milestone_id&#x60; attribute instead. Mutually exclusive with &#x60;milestone_id&#x60;.
     * @param milestoneId Return issues assigned to milestones with a given timebox value. &#x60;None&#x60; lists all issues with no milestone. &#x60;Any&#x60; lists all issues that have an assigned milestone. &#x60;Upcoming&#x60; lists all issues assigned to milestones due in the future. &#x60;Started&#x60; lists all issues assigned to open, started milestones. The logic for &#x60;Upcoming&#x60; and &#x60;Started&#x60; differs from the logic used in the [GraphQL API](https://docs.gitlab.com/user/project/milestones/#special-milestone-filters). Mutually exclusive with &#x60;milestone&#x60;.
     * @param iids Return only the issues having the given &#x60;iid&#x60;.
     * @param search Search issues against their &#x60;title&#x60; and &#x60;description&#x60;.
     * @param in Modify the scope of the &#x60;search&#x60; attribute to &#x60;title&#x60;, &#x60;description&#x60;, or &#x60;title,description&#x60;. If omitted, defaults to &#x60;title,description&#x60;.
     * @param authorId Return issues created by the given user ID. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;author_username&#x60;.
     * @param authorUsername Return issues created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;author_id&#x60;.
     * @param assigneeId Return issues assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned issues and &#x60;Any&#x60; returns issues with an assignee. Mutually exclusive with &#x60;assignee_username&#x60;.
     * @param assigneeUsername Return issues assigned to the given username. In GitLab Community Edition, only a single value is accepted. Otherwise, an invalid parameter error is returned. When multiple usernames are given, only issues assigned to all of them are returned. Mutually exclusive with &#x60;assignee_id&#x60;.
     * @param createdAfter Return issues created on or after the specified time.
     * @param createdBefore Return issues created on or before the specified time.
     * @param updatedAfter Return issues updated on or after the specified time.
     * @param updatedBefore Return issues updated on or before the specified time.
     * @param not Return issues that do not match the specified parameters.
     * @param notLabels Comma-separated list of label names.
     * @param notMilestone Milestone title. Mutually exclusive with &#x60;not[milestone_id]&#x60;.
     * @param notMilestoneId Return issues assigned to milestones without the specified timebox value. Mutually exclusive with &#x60;not[milestone]&#x60;.
     * @param notIids Internal IDs of issues to exclude.
     * @param notAuthorId Return issues not authored by the user with the given ID. Mutually exclusive with &#x60;not[author_username]&#x60;.
     * @param notAuthorUsername Return issues not authored by the user with the given username. Mutually exclusive with &#x60;not[author_id]&#x60;.
     * @param notAssigneeId Return issues not assigned to the user with the given ID. Mutually exclusive with &#x60;not[assignee_username]&#x60;.
     * @param notAssigneeUsername Return issues not assigned to the user with the given username. Mutually exclusive with &#x60;not[assignee_id]&#x60;.
     * @param notWeight Return issues that do not have the specified weight.
     * @param notIterationId Return issues not assigned to the iteration with the given ID. Mutually exclusive with &#x60;not[iteration_title]&#x60;.
     * @param notIterationTitle Return issues not assigned to the iteration with the given title. Mutually exclusive with &#x60;not[iteration_id]&#x60;.
     * @param scope Return issues for the given scope.
     * @param myReactionEmoji Return issues reacted to by the authenticated user with the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues with no reaction and &#x60;Any&#x60; returns issues with at least one reaction.
     * @param confidential If &#x60;true&#x60;, returns only confidential issues. If &#x60;false&#x60;, returns only public issues.
     * @param weight The weight of the issue
     * @param epicId Return issues associated with the epic with the given ID. &#x60;None&#x60; returns issues that are not associated with an epic. &#x60;Any&#x60; returns issues that are associated with an epic. Premium and Ultimate only.
     * @param healthStatus The health status of the issue. Must be one of: on_track, needs_attention, at_risk, none, any
     * @param iterationId Return issues assigned to the iteration with the given ID. &#x60;None&#x60; returns issues that do not belong to an iteration. &#x60;Any&#x60; returns issues that belong to an iteration. Premium and Ultimate only. Mutually exclusive with &#x60;iteration_title&#x60;.
     * @param iterationTitle Return issues assigned to the iteration with the given title. Premium and Ultimate only. Mutually exclusive with &#x60;iteration_id&#x60;.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4IssuesStatisticsRequestCreation(@jakarta.annotation.Nullable List<String> labels, @jakarta.annotation.Nullable String milestone, @jakarta.annotation.Nullable String milestoneId, @jakarta.annotation.Nullable List<Integer> iids, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String in, @jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter assigneeId, @jakarta.annotation.Nullable List<String> assigneeUsername, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable List<String> notLabels, @jakarta.annotation.Nullable String notMilestone, @jakarta.annotation.Nullable String notMilestoneId, @jakarta.annotation.Nullable List<Integer> notIids, @jakarta.annotation.Nullable Integer notAuthorId, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable Integer notAssigneeId, @jakarta.annotation.Nullable List<String> notAssigneeUsername, @jakarta.annotation.Nullable Integer notWeight, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter notIterationId, @jakarta.annotation.Nullable String notIterationTitle, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String myReactionEmoji, @jakarta.annotation.Nullable Boolean confidential, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter weight, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter epicId, @jakarta.annotation.Nullable String healthStatus, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter iterationId, @jakarta.annotation.Nullable String iterationTitle) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "labels", labels));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "milestone", milestone));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "milestone_id", milestoneId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "iids", iids));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "in", in));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "author_id", authorId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "author_username", authorUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "assignee_id", assigneeId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "assignee_username", assigneeUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_after", createdAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "created_before", createdBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_after", updatedAfter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "updated_before", updatedBefore));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not", not));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "not[labels]", notLabels));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[milestone]", notMilestone));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[milestone_id]", notMilestoneId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "not[iids]", notIids));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[author_id]", notAuthorId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[author_username]", notAuthorUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[assignee_id]", notAssigneeId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "not[assignee_username]", notAssigneeUsername));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[weight]", notWeight));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[iteration_id]", notIterationId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "not[iteration_title]", notIterationTitle));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "scope", scope));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "my_reaction_emoji", myReactionEmoji));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "confidential", confidential));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "weight", weight));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "epic_id", epicId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "health_status", healthStatus));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "iteration_id", iterationId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "iteration_title", iterationTitle));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/issues_statistics", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve issues statistics for the currently authenticated user
     * Retrieves statistics for issues accessible by the currently authenticated user. By default, returns only issues created by the current user. To get all issues, set the &#x60;scope&#x60; attribute to &#x60;all&#x60;.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param labels Comma-separated list of label names. &#x60;None&#x60; means no labels are assigned. &#x60;Any&#x60; means at least one label is assigned. &#x60;No+Label&#x60; (deprecated) means no labels are assigned. Set to an empty string to unassign all labels. If a label does not already exist, this creates a new project label and assigns it to the issue. Predefined names are case-insensitive.
     * @param milestone Milestone title. &#x60;None&#x60; lists all issues with no milestone. &#x60;Any&#x60; lists all issues that have an assigned milestone. Support for &#x60;None&#x60; and &#x60;Any&#x60; is [planned for removal](https://gitlab.com/gitlab-org/gitlab/-/issues/336044). Use the &#x60;milestone_id&#x60; attribute instead. Mutually exclusive with &#x60;milestone_id&#x60;.
     * @param milestoneId Return issues assigned to milestones with a given timebox value. &#x60;None&#x60; lists all issues with no milestone. &#x60;Any&#x60; lists all issues that have an assigned milestone. &#x60;Upcoming&#x60; lists all issues assigned to milestones due in the future. &#x60;Started&#x60; lists all issues assigned to open, started milestones. The logic for &#x60;Upcoming&#x60; and &#x60;Started&#x60; differs from the logic used in the [GraphQL API](https://docs.gitlab.com/user/project/milestones/#special-milestone-filters). Mutually exclusive with &#x60;milestone&#x60;.
     * @param iids Return only the issues having the given &#x60;iid&#x60;.
     * @param search Search issues against their &#x60;title&#x60; and &#x60;description&#x60;.
     * @param in Modify the scope of the &#x60;search&#x60; attribute to &#x60;title&#x60;, &#x60;description&#x60;, or &#x60;title,description&#x60;. If omitted, defaults to &#x60;title,description&#x60;.
     * @param authorId Return issues created by the given user ID. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;author_username&#x60;.
     * @param authorUsername Return issues created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;author_id&#x60;.
     * @param assigneeId Return issues assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned issues and &#x60;Any&#x60; returns issues with an assignee. Mutually exclusive with &#x60;assignee_username&#x60;.
     * @param assigneeUsername Return issues assigned to the given username. In GitLab Community Edition, only a single value is accepted. Otherwise, an invalid parameter error is returned. When multiple usernames are given, only issues assigned to all of them are returned. Mutually exclusive with &#x60;assignee_id&#x60;.
     * @param createdAfter Return issues created on or after the specified time.
     * @param createdBefore Return issues created on or before the specified time.
     * @param updatedAfter Return issues updated on or after the specified time.
     * @param updatedBefore Return issues updated on or before the specified time.
     * @param not Return issues that do not match the specified parameters.
     * @param notLabels Comma-separated list of label names.
     * @param notMilestone Milestone title. Mutually exclusive with &#x60;not[milestone_id]&#x60;.
     * @param notMilestoneId Return issues assigned to milestones without the specified timebox value. Mutually exclusive with &#x60;not[milestone]&#x60;.
     * @param notIids Internal IDs of issues to exclude.
     * @param notAuthorId Return issues not authored by the user with the given ID. Mutually exclusive with &#x60;not[author_username]&#x60;.
     * @param notAuthorUsername Return issues not authored by the user with the given username. Mutually exclusive with &#x60;not[author_id]&#x60;.
     * @param notAssigneeId Return issues not assigned to the user with the given ID. Mutually exclusive with &#x60;not[assignee_username]&#x60;.
     * @param notAssigneeUsername Return issues not assigned to the user with the given username. Mutually exclusive with &#x60;not[assignee_id]&#x60;.
     * @param notWeight Return issues that do not have the specified weight.
     * @param notIterationId Return issues not assigned to the iteration with the given ID. Mutually exclusive with &#x60;not[iteration_title]&#x60;.
     * @param notIterationTitle Return issues not assigned to the iteration with the given title. Mutually exclusive with &#x60;not[iteration_id]&#x60;.
     * @param scope Return issues for the given scope.
     * @param myReactionEmoji Return issues reacted to by the authenticated user with the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues with no reaction and &#x60;Any&#x60; returns issues with at least one reaction.
     * @param confidential If &#x60;true&#x60;, returns only confidential issues. If &#x60;false&#x60;, returns only public issues.
     * @param weight The weight of the issue
     * @param epicId Return issues associated with the epic with the given ID. &#x60;None&#x60; returns issues that are not associated with an epic. &#x60;Any&#x60; returns issues that are associated with an epic. Premium and Ultimate only.
     * @param healthStatus The health status of the issue. Must be one of: on_track, needs_attention, at_risk, none, any
     * @param iterationId Return issues assigned to the iteration with the given ID. &#x60;None&#x60; returns issues that do not belong to an iteration. &#x60;Any&#x60; returns issues that belong to an iteration. Premium and Ultimate only. Mutually exclusive with &#x60;iteration_title&#x60;.
     * @param iterationTitle Return issues assigned to the iteration with the given title. Premium and Ultimate only. Mutually exclusive with &#x60;iteration_id&#x60;.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4IssuesStatistics(@jakarta.annotation.Nullable List<String> labels, @jakarta.annotation.Nullable String milestone, @jakarta.annotation.Nullable String milestoneId, @jakarta.annotation.Nullable List<Integer> iids, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String in, @jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter assigneeId, @jakarta.annotation.Nullable List<String> assigneeUsername, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable List<String> notLabels, @jakarta.annotation.Nullable String notMilestone, @jakarta.annotation.Nullable String notMilestoneId, @jakarta.annotation.Nullable List<Integer> notIids, @jakarta.annotation.Nullable Integer notAuthorId, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable Integer notAssigneeId, @jakarta.annotation.Nullable List<String> notAssigneeUsername, @jakarta.annotation.Nullable Integer notWeight, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter notIterationId, @jakarta.annotation.Nullable String notIterationTitle, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String myReactionEmoji, @jakarta.annotation.Nullable Boolean confidential, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter weight, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter epicId, @jakarta.annotation.Nullable String healthStatus, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter iterationId, @jakarta.annotation.Nullable String iterationTitle) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4IssuesStatisticsRequestCreation(labels, milestone, milestoneId, iids, search, in, authorId, authorUsername, assigneeId, assigneeUsername, createdAfter, createdBefore, updatedAfter, updatedBefore, not, notLabels, notMilestone, notMilestoneId, notIids, notAuthorId, notAuthorUsername, notAssigneeId, notAssigneeUsername, notWeight, notIterationId, notIterationTitle, scope, myReactionEmoji, confidential, weight, epicId, healthStatus, iterationId, iterationTitle).body(localVarReturnType);
    }

    /**
     * Retrieve issues statistics for the currently authenticated user
     * Retrieves statistics for issues accessible by the currently authenticated user. By default, returns only issues created by the current user. To get all issues, set the &#x60;scope&#x60; attribute to &#x60;all&#x60;.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param labels Comma-separated list of label names. &#x60;None&#x60; means no labels are assigned. &#x60;Any&#x60; means at least one label is assigned. &#x60;No+Label&#x60; (deprecated) means no labels are assigned. Set to an empty string to unassign all labels. If a label does not already exist, this creates a new project label and assigns it to the issue. Predefined names are case-insensitive.
     * @param milestone Milestone title. &#x60;None&#x60; lists all issues with no milestone. &#x60;Any&#x60; lists all issues that have an assigned milestone. Support for &#x60;None&#x60; and &#x60;Any&#x60; is [planned for removal](https://gitlab.com/gitlab-org/gitlab/-/issues/336044). Use the &#x60;milestone_id&#x60; attribute instead. Mutually exclusive with &#x60;milestone_id&#x60;.
     * @param milestoneId Return issues assigned to milestones with a given timebox value. &#x60;None&#x60; lists all issues with no milestone. &#x60;Any&#x60; lists all issues that have an assigned milestone. &#x60;Upcoming&#x60; lists all issues assigned to milestones due in the future. &#x60;Started&#x60; lists all issues assigned to open, started milestones. The logic for &#x60;Upcoming&#x60; and &#x60;Started&#x60; differs from the logic used in the [GraphQL API](https://docs.gitlab.com/user/project/milestones/#special-milestone-filters). Mutually exclusive with &#x60;milestone&#x60;.
     * @param iids Return only the issues having the given &#x60;iid&#x60;.
     * @param search Search issues against their &#x60;title&#x60; and &#x60;description&#x60;.
     * @param in Modify the scope of the &#x60;search&#x60; attribute to &#x60;title&#x60;, &#x60;description&#x60;, or &#x60;title,description&#x60;. If omitted, defaults to &#x60;title,description&#x60;.
     * @param authorId Return issues created by the given user ID. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;author_username&#x60;.
     * @param authorUsername Return issues created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;author_id&#x60;.
     * @param assigneeId Return issues assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned issues and &#x60;Any&#x60; returns issues with an assignee. Mutually exclusive with &#x60;assignee_username&#x60;.
     * @param assigneeUsername Return issues assigned to the given username. In GitLab Community Edition, only a single value is accepted. Otherwise, an invalid parameter error is returned. When multiple usernames are given, only issues assigned to all of them are returned. Mutually exclusive with &#x60;assignee_id&#x60;.
     * @param createdAfter Return issues created on or after the specified time.
     * @param createdBefore Return issues created on or before the specified time.
     * @param updatedAfter Return issues updated on or after the specified time.
     * @param updatedBefore Return issues updated on or before the specified time.
     * @param not Return issues that do not match the specified parameters.
     * @param notLabels Comma-separated list of label names.
     * @param notMilestone Milestone title. Mutually exclusive with &#x60;not[milestone_id]&#x60;.
     * @param notMilestoneId Return issues assigned to milestones without the specified timebox value. Mutually exclusive with &#x60;not[milestone]&#x60;.
     * @param notIids Internal IDs of issues to exclude.
     * @param notAuthorId Return issues not authored by the user with the given ID. Mutually exclusive with &#x60;not[author_username]&#x60;.
     * @param notAuthorUsername Return issues not authored by the user with the given username. Mutually exclusive with &#x60;not[author_id]&#x60;.
     * @param notAssigneeId Return issues not assigned to the user with the given ID. Mutually exclusive with &#x60;not[assignee_username]&#x60;.
     * @param notAssigneeUsername Return issues not assigned to the user with the given username. Mutually exclusive with &#x60;not[assignee_id]&#x60;.
     * @param notWeight Return issues that do not have the specified weight.
     * @param notIterationId Return issues not assigned to the iteration with the given ID. Mutually exclusive with &#x60;not[iteration_title]&#x60;.
     * @param notIterationTitle Return issues not assigned to the iteration with the given title. Mutually exclusive with &#x60;not[iteration_id]&#x60;.
     * @param scope Return issues for the given scope.
     * @param myReactionEmoji Return issues reacted to by the authenticated user with the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues with no reaction and &#x60;Any&#x60; returns issues with at least one reaction.
     * @param confidential If &#x60;true&#x60;, returns only confidential issues. If &#x60;false&#x60;, returns only public issues.
     * @param weight The weight of the issue
     * @param epicId Return issues associated with the epic with the given ID. &#x60;None&#x60; returns issues that are not associated with an epic. &#x60;Any&#x60; returns issues that are associated with an epic. Premium and Ultimate only.
     * @param healthStatus The health status of the issue. Must be one of: on_track, needs_attention, at_risk, none, any
     * @param iterationId Return issues assigned to the iteration with the given ID. &#x60;None&#x60; returns issues that do not belong to an iteration. &#x60;Any&#x60; returns issues that belong to an iteration. Premium and Ultimate only. Mutually exclusive with &#x60;iteration_title&#x60;.
     * @param iterationTitle Return issues assigned to the iteration with the given title. Premium and Ultimate only. Mutually exclusive with &#x60;iteration_id&#x60;.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4IssuesStatisticsWithHttpInfo(@jakarta.annotation.Nullable List<String> labels, @jakarta.annotation.Nullable String milestone, @jakarta.annotation.Nullable String milestoneId, @jakarta.annotation.Nullable List<Integer> iids, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String in, @jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter assigneeId, @jakarta.annotation.Nullable List<String> assigneeUsername, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable List<String> notLabels, @jakarta.annotation.Nullable String notMilestone, @jakarta.annotation.Nullable String notMilestoneId, @jakarta.annotation.Nullable List<Integer> notIids, @jakarta.annotation.Nullable Integer notAuthorId, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable Integer notAssigneeId, @jakarta.annotation.Nullable List<String> notAssigneeUsername, @jakarta.annotation.Nullable Integer notWeight, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter notIterationId, @jakarta.annotation.Nullable String notIterationTitle, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String myReactionEmoji, @jakarta.annotation.Nullable Boolean confidential, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter weight, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter epicId, @jakarta.annotation.Nullable String healthStatus, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter iterationId, @jakarta.annotation.Nullable String iterationTitle) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4IssuesStatisticsRequestCreation(labels, milestone, milestoneId, iids, search, in, authorId, authorUsername, assigneeId, assigneeUsername, createdAfter, createdBefore, updatedAfter, updatedBefore, not, notLabels, notMilestone, notMilestoneId, notIids, notAuthorId, notAuthorUsername, notAssigneeId, notAssigneeUsername, notWeight, notIterationId, notIterationTitle, scope, myReactionEmoji, confidential, weight, epicId, healthStatus, iterationId, iterationTitle).toEntity(localVarReturnType);
    }

    /**
     * Retrieve issues statistics for the currently authenticated user
     * Retrieves statistics for issues accessible by the currently authenticated user. By default, returns only issues created by the current user. To get all issues, set the &#x60;scope&#x60; attribute to &#x60;all&#x60;.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param labels Comma-separated list of label names. &#x60;None&#x60; means no labels are assigned. &#x60;Any&#x60; means at least one label is assigned. &#x60;No+Label&#x60; (deprecated) means no labels are assigned. Set to an empty string to unassign all labels. If a label does not already exist, this creates a new project label and assigns it to the issue. Predefined names are case-insensitive.
     * @param milestone Milestone title. &#x60;None&#x60; lists all issues with no milestone. &#x60;Any&#x60; lists all issues that have an assigned milestone. Support for &#x60;None&#x60; and &#x60;Any&#x60; is [planned for removal](https://gitlab.com/gitlab-org/gitlab/-/issues/336044). Use the &#x60;milestone_id&#x60; attribute instead. Mutually exclusive with &#x60;milestone_id&#x60;.
     * @param milestoneId Return issues assigned to milestones with a given timebox value. &#x60;None&#x60; lists all issues with no milestone. &#x60;Any&#x60; lists all issues that have an assigned milestone. &#x60;Upcoming&#x60; lists all issues assigned to milestones due in the future. &#x60;Started&#x60; lists all issues assigned to open, started milestones. The logic for &#x60;Upcoming&#x60; and &#x60;Started&#x60; differs from the logic used in the [GraphQL API](https://docs.gitlab.com/user/project/milestones/#special-milestone-filters). Mutually exclusive with &#x60;milestone&#x60;.
     * @param iids Return only the issues having the given &#x60;iid&#x60;.
     * @param search Search issues against their &#x60;title&#x60; and &#x60;description&#x60;.
     * @param in Modify the scope of the &#x60;search&#x60; attribute to &#x60;title&#x60;, &#x60;description&#x60;, or &#x60;title,description&#x60;. If omitted, defaults to &#x60;title,description&#x60;.
     * @param authorId Return issues created by the given user ID. Combine with &#x60;scope&#x3D;all&#x60; or &#x60;scope&#x3D;assigned_to_me&#x60;. Mutually exclusive with &#x60;author_username&#x60;.
     * @param authorUsername Return issues created by the given &#x60;username&#x60;. Mutually exclusive with &#x60;author_id&#x60;.
     * @param assigneeId Return issues assigned to the given user &#x60;id&#x60;. &#x60;None&#x60; returns unassigned issues and &#x60;Any&#x60; returns issues with an assignee. Mutually exclusive with &#x60;assignee_username&#x60;.
     * @param assigneeUsername Return issues assigned to the given username. In GitLab Community Edition, only a single value is accepted. Otherwise, an invalid parameter error is returned. When multiple usernames are given, only issues assigned to all of them are returned. Mutually exclusive with &#x60;assignee_id&#x60;.
     * @param createdAfter Return issues created on or after the specified time.
     * @param createdBefore Return issues created on or before the specified time.
     * @param updatedAfter Return issues updated on or after the specified time.
     * @param updatedBefore Return issues updated on or before the specified time.
     * @param not Return issues that do not match the specified parameters.
     * @param notLabels Comma-separated list of label names.
     * @param notMilestone Milestone title. Mutually exclusive with &#x60;not[milestone_id]&#x60;.
     * @param notMilestoneId Return issues assigned to milestones without the specified timebox value. Mutually exclusive with &#x60;not[milestone]&#x60;.
     * @param notIids Internal IDs of issues to exclude.
     * @param notAuthorId Return issues not authored by the user with the given ID. Mutually exclusive with &#x60;not[author_username]&#x60;.
     * @param notAuthorUsername Return issues not authored by the user with the given username. Mutually exclusive with &#x60;not[author_id]&#x60;.
     * @param notAssigneeId Return issues not assigned to the user with the given ID. Mutually exclusive with &#x60;not[assignee_username]&#x60;.
     * @param notAssigneeUsername Return issues not assigned to the user with the given username. Mutually exclusive with &#x60;not[assignee_id]&#x60;.
     * @param notWeight Return issues that do not have the specified weight.
     * @param notIterationId Return issues not assigned to the iteration with the given ID. Mutually exclusive with &#x60;not[iteration_title]&#x60;.
     * @param notIterationTitle Return issues not assigned to the iteration with the given title. Mutually exclusive with &#x60;not[iteration_id]&#x60;.
     * @param scope Return issues for the given scope.
     * @param myReactionEmoji Return issues reacted to by the authenticated user with the given &#x60;emoji&#x60;. &#x60;None&#x60; returns issues with no reaction and &#x60;Any&#x60; returns issues with at least one reaction.
     * @param confidential If &#x60;true&#x60;, returns only confidential issues. If &#x60;false&#x60;, returns only public issues.
     * @param weight The weight of the issue
     * @param epicId Return issues associated with the epic with the given ID. &#x60;None&#x60; returns issues that are not associated with an epic. &#x60;Any&#x60; returns issues that are associated with an epic. Premium and Ultimate only.
     * @param healthStatus The health status of the issue. Must be one of: on_track, needs_attention, at_risk, none, any
     * @param iterationId Return issues assigned to the iteration with the given ID. &#x60;None&#x60; returns issues that do not belong to an iteration. &#x60;Any&#x60; returns issues that belong to an iteration. Premium and Ultimate only. Mutually exclusive with &#x60;iteration_title&#x60;.
     * @param iterationTitle Return issues assigned to the iteration with the given title. Premium and Ultimate only. Mutually exclusive with &#x60;iteration_id&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4IssuesStatisticsWithResponseSpec(@jakarta.annotation.Nullable List<String> labels, @jakarta.annotation.Nullable String milestone, @jakarta.annotation.Nullable String milestoneId, @jakarta.annotation.Nullable List<Integer> iids, @jakarta.annotation.Nullable String search, @jakarta.annotation.Nullable String in, @jakarta.annotation.Nullable Integer authorId, @jakarta.annotation.Nullable String authorUsername, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter assigneeId, @jakarta.annotation.Nullable List<String> assigneeUsername, @jakarta.annotation.Nullable OffsetDateTime createdAfter, @jakarta.annotation.Nullable OffsetDateTime createdBefore, @jakarta.annotation.Nullable OffsetDateTime updatedAfter, @jakarta.annotation.Nullable OffsetDateTime updatedBefore, @jakarta.annotation.Nullable Object not, @jakarta.annotation.Nullable List<String> notLabels, @jakarta.annotation.Nullable String notMilestone, @jakarta.annotation.Nullable String notMilestoneId, @jakarta.annotation.Nullable List<Integer> notIids, @jakarta.annotation.Nullable Integer notAuthorId, @jakarta.annotation.Nullable String notAuthorUsername, @jakarta.annotation.Nullable Integer notAssigneeId, @jakarta.annotation.Nullable List<String> notAssigneeUsername, @jakarta.annotation.Nullable Integer notWeight, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter notIterationId, @jakarta.annotation.Nullable String notIterationTitle, @jakarta.annotation.Nullable String scope, @jakarta.annotation.Nullable String myReactionEmoji, @jakarta.annotation.Nullable Boolean confidential, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter weight, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter epicId, @jakarta.annotation.Nullable String healthStatus, @jakarta.annotation.Nullable GetApiV4MergeRequestsAssigneeIdParameter iterationId, @jakarta.annotation.Nullable String iterationTitle) throws RestClientResponseException {
        return getApiV4IssuesStatisticsRequestCreation(labels, milestone, milestoneId, iids, search, in, authorId, authorUsername, assigneeId, assigneeUsername, createdAfter, createdBefore, updatedAfter, updatedBefore, not, notLabels, notMilestone, notMilestoneId, notIids, notAuthorId, notAuthorUsername, notAssigneeId, notAssigneeUsername, notWeight, notIterationId, notIterationTitle, scope, myReactionEmoji, confidential, weight, epicId, healthStatus, iterationId, iterationTitle);
    }

    /**
     * List all issue links
     * Lists all linked issues for a specified issue, sorted by the relationship creation datetime (ascending). Issues are filtered according to the user authorizations.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid Internal ID of the issue.
     * @return List&lt;APIEntitiesRelatedIssue&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdIssuesIssueIidLinksRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdIssuesIssueIidLinks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueIid' is set
        if (issueIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueIid' when calling getApiV4ProjectsIdIssuesIssueIidLinks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesRelatedIssue>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{issue_iid}/links", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all issue links
     * Lists all linked issues for a specified issue, sorted by the relationship creation datetime (ascending). Issues are filtered according to the user authorizations.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid Internal ID of the issue.
     * @return List&lt;APIEntitiesRelatedIssue&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesRelatedIssue> getApiV4ProjectsIdIssuesIssueIidLinks(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesRelatedIssue>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesIssueIidLinksRequestCreation(id, issueIid).body(localVarReturnType);
    }

    /**
     * List all issue links
     * Lists all linked issues for a specified issue, sorted by the relationship creation datetime (ascending). Issues are filtered according to the user authorizations.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid Internal ID of the issue.
     * @return ResponseEntity&lt;List&lt;APIEntitiesRelatedIssue&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesRelatedIssue>> getApiV4ProjectsIdIssuesIssueIidLinksWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesRelatedIssue>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesIssueIidLinksRequestCreation(id, issueIid).toEntity(localVarReturnType);
    }

    /**
     * List all issue links
     * Lists all linked issues for a specified issue, sorted by the relationship creation datetime (ascending). Issues are filtered according to the user authorizations.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid Internal ID of the issue.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdIssuesIssueIidLinksWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        return getApiV4ProjectsIdIssuesIssueIidLinksRequestCreation(id, issueIid);
    }

    /**
     * Retrieve an issue link
     * Retrieves a specified issue link.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid Internal ID of the issue.
     * @param issueLinkId ID of the issue relationship.
     * @return APIEntitiesIssueLink
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdIssuesIssueIidLinksIssueLinkIdRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter issueLinkId) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdIssuesIssueIidLinksIssueLinkId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueIid' is set
        if (issueIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueIid' when calling getApiV4ProjectsIdIssuesIssueIidLinksIssueLinkId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueLinkId' is set
        if (issueLinkId == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueLinkId' when calling getApiV4ProjectsIdIssuesIssueIidLinksIssueLinkId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("issue_iid", issueIid);
        pathParams.put("issue_link_id", issueLinkId);

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

        ParameterizedTypeReference<APIEntitiesIssueLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{issue_iid}/links/{issue_link_id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve an issue link
     * Retrieves a specified issue link.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid Internal ID of the issue.
     * @param issueLinkId ID of the issue relationship.
     * @return APIEntitiesIssueLink
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesIssueLink getApiV4ProjectsIdIssuesIssueIidLinksIssueLinkId(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter issueLinkId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIssueLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesIssueIidLinksIssueLinkIdRequestCreation(id, issueIid, issueLinkId).body(localVarReturnType);
    }

    /**
     * Retrieve an issue link
     * Retrieves a specified issue link.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid Internal ID of the issue.
     * @param issueLinkId ID of the issue relationship.
     * @return ResponseEntity&lt;APIEntitiesIssueLink&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesIssueLink> getApiV4ProjectsIdIssuesIssueIidLinksIssueLinkIdWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter issueLinkId) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIssueLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesIssueIidLinksIssueLinkIdRequestCreation(id, issueIid, issueLinkId).toEntity(localVarReturnType);
    }

    /**
     * Retrieve an issue link
     * Retrieves a specified issue link.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid Internal ID of the issue.
     * @param issueLinkId ID of the issue relationship.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdIssuesIssueIidLinksIssueLinkIdWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter issueLinkId) throws RestClientResponseException {
        return getApiV4ProjectsIdIssuesIssueIidLinksIssueLinkIdRequestCreation(id, issueIid, issueLinkId);
    }

    /**
     * List all participants in an issue
     * Lists all users that are participants in a specified issue. If the project is private or the issue is confidential, you need to provide credentials to authorize. In most cases, you should authenticate with a personal access token.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid Internal ID of the issue.
     * @return List&lt;APIEntitiesUserBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdIssuesIssueIidParticipantsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdIssuesIssueIidParticipants", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueIid' is set
        if (issueIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueIid' when calling getApiV4ProjectsIdIssuesIssueIidParticipants", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesUserBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{issue_iid}/participants", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all participants in an issue
     * Lists all users that are participants in a specified issue. If the project is private or the issue is confidential, you need to provide credentials to authorize. In most cases, you should authenticate with a personal access token.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid Internal ID of the issue.
     * @return List&lt;APIEntitiesUserBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesUserBasic> getApiV4ProjectsIdIssuesIssueIidParticipants(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesUserBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesIssueIidParticipantsRequestCreation(id, issueIid).body(localVarReturnType);
    }

    /**
     * List all participants in an issue
     * Lists all users that are participants in a specified issue. If the project is private or the issue is confidential, you need to provide credentials to authorize. In most cases, you should authenticate with a personal access token.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid Internal ID of the issue.
     * @return ResponseEntity&lt;List&lt;APIEntitiesUserBasic&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesUserBasic>> getApiV4ProjectsIdIssuesIssueIidParticipantsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesUserBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesIssueIidParticipantsRequestCreation(id, issueIid).toEntity(localVarReturnType);
    }

    /**
     * List all participants in an issue
     * Lists all users that are participants in a specified issue. If the project is private or the issue is confidential, you need to provide credentials to authorize. In most cases, you should authenticate with a personal access token.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid Internal ID of the issue.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdIssuesIssueIidParticipantsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        return getApiV4ProjectsIdIssuesIssueIidParticipantsRequestCreation(id, issueIid);
    }

    /**
     * List all merge requests related to an issue
     * Lists all merge requests that are related to a specified issue. If the project is private or the issue is confidential, you need to provide credentials to authorize. In most cases, you should authenticate with a personal access token.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid Internal ID of the issue.
     * @return List&lt;APIEntitiesMergeRequestBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdIssuesIssueIidRelatedMergeRequestsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdIssuesIssueIidRelatedMergeRequests", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueIid' is set
        if (issueIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueIid' when calling getApiV4ProjectsIdIssuesIssueIidRelatedMergeRequests", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesMergeRequestBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{issue_iid}/related_merge_requests", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all merge requests related to an issue
     * Lists all merge requests that are related to a specified issue. If the project is private or the issue is confidential, you need to provide credentials to authorize. In most cases, you should authenticate with a personal access token.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid Internal ID of the issue.
     * @return List&lt;APIEntitiesMergeRequestBasic&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesMergeRequestBasic> getApiV4ProjectsIdIssuesIssueIidRelatedMergeRequests(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMergeRequestBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesIssueIidRelatedMergeRequestsRequestCreation(id, issueIid).body(localVarReturnType);
    }

    /**
     * List all merge requests related to an issue
     * Lists all merge requests that are related to a specified issue. If the project is private or the issue is confidential, you need to provide credentials to authorize. In most cases, you should authenticate with a personal access token.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid Internal ID of the issue.
     * @return ResponseEntity&lt;List&lt;APIEntitiesMergeRequestBasic&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesMergeRequestBasic>> getApiV4ProjectsIdIssuesIssueIidRelatedMergeRequestsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesMergeRequestBasic>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesIssueIidRelatedMergeRequestsRequestCreation(id, issueIid).toEntity(localVarReturnType);
    }

    /**
     * List all merge requests related to an issue
     * Lists all merge requests that are related to a specified issue. If the project is private or the issue is confidential, you need to provide credentials to authorize. In most cases, you should authenticate with a personal access token.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid Internal ID of the issue.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdIssuesIssueIidRelatedMergeRequestsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        return getApiV4ProjectsIdIssuesIssueIidRelatedMergeRequestsRequestCreation(id, issueIid);
    }

    /**
     * Retrieve time tracking stats for an issue
     * Retrieves time tracking stats for a specified issue, including time estimate and time spent in seconds and human-readable format (for example, &#x60;1h 30m&#x60;).
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of the issue
     * @return APIEntitiesIssuableTimeStats
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdIssuesIssueIidTimeStatsRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdIssuesIssueIidTimeStats", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueIid' is set
        if (issueIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueIid' when calling getApiV4ProjectsIdIssuesIssueIidTimeStats", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesIssuableTimeStats> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{issue_iid}/time_stats", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve time tracking stats for an issue
     * Retrieves time tracking stats for a specified issue, including time estimate and time spent in seconds and human-readable format (for example, &#x60;1h 30m&#x60;).
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of the issue
     * @return APIEntitiesIssuableTimeStats
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesIssuableTimeStats getApiV4ProjectsIdIssuesIssueIidTimeStats(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIssuableTimeStats> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesIssueIidTimeStatsRequestCreation(id, issueIid).body(localVarReturnType);
    }

    /**
     * Retrieve time tracking stats for an issue
     * Retrieves time tracking stats for a specified issue, including time estimate and time spent in seconds and human-readable format (for example, &#x60;1h 30m&#x60;).
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of the issue
     * @return ResponseEntity&lt;APIEntitiesIssuableTimeStats&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesIssuableTimeStats> getApiV4ProjectsIdIssuesIssueIidTimeStatsWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIssuableTimeStats> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesIssueIidTimeStatsRequestCreation(id, issueIid).toEntity(localVarReturnType);
    }

    /**
     * Retrieve time tracking stats for an issue
     * Retrieves time tracking stats for a specified issue, including time estimate and time spent in seconds and human-readable format (for example, &#x60;1h 30m&#x60;).
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of the issue
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdIssuesIssueIidTimeStatsWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        return getApiV4ProjectsIdIssuesIssueIidTimeStatsRequestCreation(id, issueIid);
    }

    /**
     * Retrieve user agent details for an issue
     * Retrieves user agent details for an issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid Internal ID of the issue.
     * @return APIEntitiesUserAgentDetail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdIssuesIssueIidUserAgentDetailRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdIssuesIssueIidUserAgentDetail", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueIid' is set
        if (issueIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueIid' when calling getApiV4ProjectsIdIssuesIssueIidUserAgentDetail", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesUserAgentDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{issue_iid}/user_agent_detail", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve user agent details for an issue
     * Retrieves user agent details for an issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid Internal ID of the issue.
     * @return APIEntitiesUserAgentDetail
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesUserAgentDetail getApiV4ProjectsIdIssuesIssueIidUserAgentDetail(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserAgentDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesIssueIidUserAgentDetailRequestCreation(id, issueIid).body(localVarReturnType);
    }

    /**
     * Retrieve user agent details for an issue
     * Retrieves user agent details for an issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid Internal ID of the issue.
     * @return ResponseEntity&lt;APIEntitiesUserAgentDetail&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesUserAgentDetail> getApiV4ProjectsIdIssuesIssueIidUserAgentDetailWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesUserAgentDetail> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdIssuesIssueIidUserAgentDetailRequestCreation(id, issueIid).toEntity(localVarReturnType);
    }

    /**
     * Retrieve user agent details for an issue
     * Retrieves user agent details for an issue.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid Internal ID of the issue.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdIssuesIssueIidUserAgentDetailWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        return getApiV4ProjectsIdIssuesIssueIidUserAgentDetailRequestCreation(id, issueIid);
    }

    /**
     * Add spent time for an issue
     * Adds spent time for a specified issue.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of the issue.
     * @param postApiV4ProjectsIdIssuesIssueIidAddSpentTimeRequest The postApiV4ProjectsIdIssuesIssueIidAddSpentTimeRequest parameter
     * @return APIEntitiesIssuableTimeStats
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdIssuesIssueIidAddSpentTimeRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdIssuesIssueIidAddSpentTimeRequest postApiV4ProjectsIdIssuesIssueIidAddSpentTimeRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdIssuesIssueIidAddSpentTime", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueIid' is set
        if (issueIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueIid' when calling postApiV4ProjectsIdIssuesIssueIidAddSpentTime", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdIssuesIssueIidAddSpentTimeRequest' is set
        if (postApiV4ProjectsIdIssuesIssueIidAddSpentTimeRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdIssuesIssueIidAddSpentTimeRequest' when calling postApiV4ProjectsIdIssuesIssueIidAddSpentTime", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesIssuableTimeStats> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{issue_iid}/add_spent_time", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdIssuesIssueIidAddSpentTimeRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Add spent time for an issue
     * Adds spent time for a specified issue.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of the issue.
     * @param postApiV4ProjectsIdIssuesIssueIidAddSpentTimeRequest The postApiV4ProjectsIdIssuesIssueIidAddSpentTimeRequest parameter
     * @return APIEntitiesIssuableTimeStats
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesIssuableTimeStats postApiV4ProjectsIdIssuesIssueIidAddSpentTime(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdIssuesIssueIidAddSpentTimeRequest postApiV4ProjectsIdIssuesIssueIidAddSpentTimeRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIssuableTimeStats> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdIssuesIssueIidAddSpentTimeRequestCreation(id, issueIid, postApiV4ProjectsIdIssuesIssueIidAddSpentTimeRequest).body(localVarReturnType);
    }

    /**
     * Add spent time for an issue
     * Adds spent time for a specified issue.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of the issue.
     * @param postApiV4ProjectsIdIssuesIssueIidAddSpentTimeRequest The postApiV4ProjectsIdIssuesIssueIidAddSpentTimeRequest parameter
     * @return ResponseEntity&lt;APIEntitiesIssuableTimeStats&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesIssuableTimeStats> postApiV4ProjectsIdIssuesIssueIidAddSpentTimeWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdIssuesIssueIidAddSpentTimeRequest postApiV4ProjectsIdIssuesIssueIidAddSpentTimeRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIssuableTimeStats> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdIssuesIssueIidAddSpentTimeRequestCreation(id, issueIid, postApiV4ProjectsIdIssuesIssueIidAddSpentTimeRequest).toEntity(localVarReturnType);
    }

    /**
     * Add spent time for an issue
     * Adds spent time for a specified issue.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of the issue.
     * @param postApiV4ProjectsIdIssuesIssueIidAddSpentTimeRequest The postApiV4ProjectsIdIssuesIssueIidAddSpentTimeRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdIssuesIssueIidAddSpentTimeWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdIssuesIssueIidAddSpentTimeRequest postApiV4ProjectsIdIssuesIssueIidAddSpentTimeRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdIssuesIssueIidAddSpentTimeRequestCreation(id, issueIid, postApiV4ProjectsIdIssuesIssueIidAddSpentTimeRequest);
    }

    /**
     * Create an issue link
     * Creates a two-way relationship between two issues. The user must be allowed to update both issues to succeed.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param issueIid Internal ID of the issue.
     * @param postApiV4ProjectsIdIssuesIssueIidLinksRequest The postApiV4ProjectsIdIssuesIssueIidLinksRequest parameter
     * @return APIEntitiesIssueLink
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdIssuesIssueIidLinksRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdIssuesIssueIidLinksRequest postApiV4ProjectsIdIssuesIssueIidLinksRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdIssuesIssueIidLinks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueIid' is set
        if (issueIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueIid' when calling postApiV4ProjectsIdIssuesIssueIidLinks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdIssuesIssueIidLinksRequest' is set
        if (postApiV4ProjectsIdIssuesIssueIidLinksRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdIssuesIssueIidLinksRequest' when calling postApiV4ProjectsIdIssuesIssueIidLinks", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesIssueLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{issue_iid}/links", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdIssuesIssueIidLinksRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create an issue link
     * Creates a two-way relationship between two issues. The user must be allowed to update both issues to succeed.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param issueIid Internal ID of the issue.
     * @param postApiV4ProjectsIdIssuesIssueIidLinksRequest The postApiV4ProjectsIdIssuesIssueIidLinksRequest parameter
     * @return APIEntitiesIssueLink
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesIssueLink postApiV4ProjectsIdIssuesIssueIidLinks(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdIssuesIssueIidLinksRequest postApiV4ProjectsIdIssuesIssueIidLinksRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIssueLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdIssuesIssueIidLinksRequestCreation(id, issueIid, postApiV4ProjectsIdIssuesIssueIidLinksRequest).body(localVarReturnType);
    }

    /**
     * Create an issue link
     * Creates a two-way relationship between two issues. The user must be allowed to update both issues to succeed.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param issueIid Internal ID of the issue.
     * @param postApiV4ProjectsIdIssuesIssueIidLinksRequest The postApiV4ProjectsIdIssuesIssueIidLinksRequest parameter
     * @return ResponseEntity&lt;APIEntitiesIssueLink&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesIssueLink> postApiV4ProjectsIdIssuesIssueIidLinksWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdIssuesIssueIidLinksRequest postApiV4ProjectsIdIssuesIssueIidLinksRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIssueLink> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdIssuesIssueIidLinksRequestCreation(id, issueIid, postApiV4ProjectsIdIssuesIssueIidLinksRequest).toEntity(localVarReturnType);
    }

    /**
     * Create an issue link
     * Creates a two-way relationship between two issues. The user must be allowed to update both issues to succeed.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param issueIid Internal ID of the issue.
     * @param postApiV4ProjectsIdIssuesIssueIidLinksRequest The postApiV4ProjectsIdIssuesIssueIidLinksRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdIssuesIssueIidLinksWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdIssuesIssueIidLinksRequest postApiV4ProjectsIdIssuesIssueIidLinksRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdIssuesIssueIidLinksRequestCreation(id, issueIid, postApiV4ProjectsIdIssuesIssueIidLinksRequest);
    }

    /**
     * Reset spent time for an issue
     * Resets the total spent time for a specified issue to &#x60;0&#x60; seconds.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of the issue
     * @return APIEntitiesIssuableTimeStats
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdIssuesIssueIidResetSpentTimeRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdIssuesIssueIidResetSpentTime", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueIid' is set
        if (issueIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueIid' when calling postApiV4ProjectsIdIssuesIssueIidResetSpentTime", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesIssuableTimeStats> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{issue_iid}/reset_spent_time", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Reset spent time for an issue
     * Resets the total spent time for a specified issue to &#x60;0&#x60; seconds.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of the issue
     * @return APIEntitiesIssuableTimeStats
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesIssuableTimeStats postApiV4ProjectsIdIssuesIssueIidResetSpentTime(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIssuableTimeStats> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdIssuesIssueIidResetSpentTimeRequestCreation(id, issueIid).body(localVarReturnType);
    }

    /**
     * Reset spent time for an issue
     * Resets the total spent time for a specified issue to &#x60;0&#x60; seconds.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of the issue
     * @return ResponseEntity&lt;APIEntitiesIssuableTimeStats&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesIssuableTimeStats> postApiV4ProjectsIdIssuesIssueIidResetSpentTimeWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIssuableTimeStats> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdIssuesIssueIidResetSpentTimeRequestCreation(id, issueIid).toEntity(localVarReturnType);
    }

    /**
     * Reset spent time for an issue
     * Resets the total spent time for a specified issue to &#x60;0&#x60; seconds.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of the issue
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdIssuesIssueIidResetSpentTimeWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        return postApiV4ProjectsIdIssuesIssueIidResetSpentTimeRequestCreation(id, issueIid);
    }

    /**
     * Reset the estimated time for an issue
     * Resets the estimated time for a specified issue to &#x60;0&#x60; seconds.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of the issue.
     * @return APIEntitiesIssuableTimeStats
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdIssuesIssueIidResetTimeEstimateRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdIssuesIssueIidResetTimeEstimate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueIid' is set
        if (issueIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueIid' when calling postApiV4ProjectsIdIssuesIssueIidResetTimeEstimate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesIssuableTimeStats> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{issue_iid}/reset_time_estimate", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Reset the estimated time for an issue
     * Resets the estimated time for a specified issue to &#x60;0&#x60; seconds.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of the issue.
     * @return APIEntitiesIssuableTimeStats
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesIssuableTimeStats postApiV4ProjectsIdIssuesIssueIidResetTimeEstimate(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIssuableTimeStats> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdIssuesIssueIidResetTimeEstimateRequestCreation(id, issueIid).body(localVarReturnType);
    }

    /**
     * Reset the estimated time for an issue
     * Resets the estimated time for a specified issue to &#x60;0&#x60; seconds.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of the issue.
     * @return ResponseEntity&lt;APIEntitiesIssuableTimeStats&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesIssuableTimeStats> postApiV4ProjectsIdIssuesIssueIidResetTimeEstimateWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIssuableTimeStats> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdIssuesIssueIidResetTimeEstimateRequestCreation(id, issueIid).toEntity(localVarReturnType);
    }

    /**
     * Reset the estimated time for an issue
     * Resets the estimated time for a specified issue to &#x60;0&#x60; seconds.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of the issue.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdIssuesIssueIidResetTimeEstimateWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid) throws RestClientResponseException {
        return postApiV4ProjectsIdIssuesIssueIidResetTimeEstimateRequestCreation(id, issueIid);
    }

    /**
     * Set the estimated time for an issue
     * Sets an estimated time of work for a specified issue.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of the issue.
     * @param postApiV4ProjectsIdIssuesIssueIidTimeEstimateRequest The postApiV4ProjectsIdIssuesIssueIidTimeEstimateRequest parameter
     * @return APIEntitiesIssuableTimeStats
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdIssuesIssueIidTimeEstimateRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdIssuesIssueIidTimeEstimateRequest postApiV4ProjectsIdIssuesIssueIidTimeEstimateRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdIssuesIssueIidTimeEstimate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'issueIid' is set
        if (issueIid == null) {
            throw new RestClientResponseException("Missing the required parameter 'issueIid' when calling postApiV4ProjectsIdIssuesIssueIidTimeEstimate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4ProjectsIdIssuesIssueIidTimeEstimateRequest' is set
        if (postApiV4ProjectsIdIssuesIssueIidTimeEstimateRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4ProjectsIdIssuesIssueIidTimeEstimateRequest' when calling postApiV4ProjectsIdIssuesIssueIidTimeEstimate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesIssuableTimeStats> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/issues/{issue_iid}/time_estimate", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4ProjectsIdIssuesIssueIidTimeEstimateRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Set the estimated time for an issue
     * Sets an estimated time of work for a specified issue.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of the issue.
     * @param postApiV4ProjectsIdIssuesIssueIidTimeEstimateRequest The postApiV4ProjectsIdIssuesIssueIidTimeEstimateRequest parameter
     * @return APIEntitiesIssuableTimeStats
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesIssuableTimeStats postApiV4ProjectsIdIssuesIssueIidTimeEstimate(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdIssuesIssueIidTimeEstimateRequest postApiV4ProjectsIdIssuesIssueIidTimeEstimateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIssuableTimeStats> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdIssuesIssueIidTimeEstimateRequestCreation(id, issueIid, postApiV4ProjectsIdIssuesIssueIidTimeEstimateRequest).body(localVarReturnType);
    }

    /**
     * Set the estimated time for an issue
     * Sets an estimated time of work for a specified issue.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of the issue.
     * @param postApiV4ProjectsIdIssuesIssueIidTimeEstimateRequest The postApiV4ProjectsIdIssuesIssueIidTimeEstimateRequest parameter
     * @return ResponseEntity&lt;APIEntitiesIssuableTimeStats&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesIssuableTimeStats> postApiV4ProjectsIdIssuesIssueIidTimeEstimateWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdIssuesIssueIidTimeEstimateRequest postApiV4ProjectsIdIssuesIssueIidTimeEstimateRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesIssuableTimeStats> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdIssuesIssueIidTimeEstimateRequestCreation(id, issueIid, postApiV4ProjectsIdIssuesIssueIidTimeEstimateRequest).toEntity(localVarReturnType);
    }

    /**
     * Set the estimated time for an issue
     * Sets an estimated time of work for a specified issue.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>400</b> - Bad request
     * <p><b>404</b> - Not found
     * @param id ID or URL-encoded path of the project.
     * @param issueIid The internal ID of the issue.
     * @param postApiV4ProjectsIdIssuesIssueIidTimeEstimateRequest The postApiV4ProjectsIdIssuesIssueIidTimeEstimateRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdIssuesIssueIidTimeEstimateWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull Integer issueIid, @jakarta.annotation.Nonnull PostApiV4ProjectsIdIssuesIssueIidTimeEstimateRequest postApiV4ProjectsIdIssuesIssueIidTimeEstimateRequest) throws RestClientResponseException {
        return postApiV4ProjectsIdIssuesIssueIidTimeEstimateRequestCreation(id, issueIid, postApiV4ProjectsIdIssuesIssueIidTimeEstimateRequest);
    }
}
