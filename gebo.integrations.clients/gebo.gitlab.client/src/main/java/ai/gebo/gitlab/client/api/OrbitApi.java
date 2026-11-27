package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesOrbitContext;
import ai.gebo.gitlab.client.model.APIEntitiesOrbitSkill;
import ai.gebo.gitlab.client.model.APIEntitiesOrbitSkillCollection;
import ai.gebo.gitlab.client.model.OrbitQueryResponse;
import ai.gebo.gitlab.client.model.PostApiV4OrbitContextRequest;
import ai.gebo.gitlab.client.model.PostApiV4OrbitQueryNameRequest;
import ai.gebo.gitlab.client.model.PostApiV4OrbitQueryRequest;

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
public class OrbitApi {
    private ApiClient apiClient;

    public OrbitApi() {
        this(new ApiClient());
    }

    public OrbitApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * List deployed Orbit skills
     * Lists the Orbit skills deployed with the connected knowledge graph service. The response includes an &#x60;ETag&#x60; header derived from the sorted skill names and versions, and a &#x60;Cache-Control&#x60; header set to &#x60;private, max-age&#x3D;0, must-revalidate&#x60;. Sending the &#x60;ETag&#x60; value in the &#x60;If-None-Match&#x60; request header revalidates the list. A matching value returns &#x60;304 Not Modified&#x60; with no response body. This feature was introduced in GitLab 19.5.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Skills are not available
     * @return APIEntitiesOrbitSkillCollection
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4OrbitSkillsRequestCreation() throws RestClientResponseException {
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

        ParameterizedTypeReference<APIEntitiesOrbitSkillCollection> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/orbit/skills", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List deployed Orbit skills
     * Lists the Orbit skills deployed with the connected knowledge graph service. The response includes an &#x60;ETag&#x60; header derived from the sorted skill names and versions, and a &#x60;Cache-Control&#x60; header set to &#x60;private, max-age&#x3D;0, must-revalidate&#x60;. Sending the &#x60;ETag&#x60; value in the &#x60;If-None-Match&#x60; request header revalidates the list. A matching value returns &#x60;304 Not Modified&#x60; with no response body. This feature was introduced in GitLab 19.5.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Skills are not available
     * @return APIEntitiesOrbitSkillCollection
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesOrbitSkillCollection getApiV4OrbitSkills() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesOrbitSkillCollection> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4OrbitSkillsRequestCreation().body(localVarReturnType);
    }

    /**
     * List deployed Orbit skills
     * Lists the Orbit skills deployed with the connected knowledge graph service. The response includes an &#x60;ETag&#x60; header derived from the sorted skill names and versions, and a &#x60;Cache-Control&#x60; header set to &#x60;private, max-age&#x3D;0, must-revalidate&#x60;. Sending the &#x60;ETag&#x60; value in the &#x60;If-None-Match&#x60; request header revalidates the list. A matching value returns &#x60;304 Not Modified&#x60; with no response body. This feature was introduced in GitLab 19.5.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Skills are not available
     * @return ResponseEntity&lt;APIEntitiesOrbitSkillCollection&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesOrbitSkillCollection> getApiV4OrbitSkillsWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesOrbitSkillCollection> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4OrbitSkillsRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * List deployed Orbit skills
     * Lists the Orbit skills deployed with the connected knowledge graph service. The response includes an &#x60;ETag&#x60; header derived from the sorted skill names and versions, and a &#x60;Cache-Control&#x60; header set to &#x60;private, max-age&#x3D;0, must-revalidate&#x60;. Sending the &#x60;ETag&#x60; value in the &#x60;If-None-Match&#x60; request header revalidates the list. A matching value returns &#x60;304 Not Modified&#x60; with no response body. This feature was introduced in GitLab 19.5.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Skills are not available
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4OrbitSkillsWithResponseSpec() throws RestClientResponseException {
        return getApiV4OrbitSkillsRequestCreation();
    }

    /**
     * Retrieve a deployed Orbit skill
     * Retrieves a complete deployed Orbit skill tree. HEAD requests retrieve only skill metadata. The response includes a &#x60;Cache-Control&#x60; header set to &#x60;private, max-age&#x3D;0, must-revalidate&#x60;, and an &#x60;ETag&#x60; header set to &#x60;\&quot;&lt;version&gt;\&quot;&#x60;. Sending the current &#x60;ETag&#x60; value in the &#x60;If-None-Match&#x60; request header revalidates a cached skill tree. A matching value returns &#x60;304 Not Modified&#x60; with no response body. This feature was introduced in GitLab 19.5.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Skill not found or skills are not available
     * <p><b>400</b> - Bad Request
     * @param name Deployed skill name
     * @return APIEntitiesOrbitSkill
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4OrbitSkillsNameRequestCreation(@jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling getApiV4OrbitSkillsName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("name", name);

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

        ParameterizedTypeReference<APIEntitiesOrbitSkill> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/orbit/skills/{name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a deployed Orbit skill
     * Retrieves a complete deployed Orbit skill tree. HEAD requests retrieve only skill metadata. The response includes a &#x60;Cache-Control&#x60; header set to &#x60;private, max-age&#x3D;0, must-revalidate&#x60;, and an &#x60;ETag&#x60; header set to &#x60;\&quot;&lt;version&gt;\&quot;&#x60;. Sending the current &#x60;ETag&#x60; value in the &#x60;If-None-Match&#x60; request header revalidates a cached skill tree. A matching value returns &#x60;304 Not Modified&#x60; with no response body. This feature was introduced in GitLab 19.5.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Skill not found or skills are not available
     * <p><b>400</b> - Bad Request
     * @param name Deployed skill name
     * @return APIEntitiesOrbitSkill
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesOrbitSkill getApiV4OrbitSkillsName(@jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesOrbitSkill> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4OrbitSkillsNameRequestCreation(name).body(localVarReturnType);
    }

    /**
     * Retrieve a deployed Orbit skill
     * Retrieves a complete deployed Orbit skill tree. HEAD requests retrieve only skill metadata. The response includes a &#x60;Cache-Control&#x60; header set to &#x60;private, max-age&#x3D;0, must-revalidate&#x60;, and an &#x60;ETag&#x60; header set to &#x60;\&quot;&lt;version&gt;\&quot;&#x60;. Sending the current &#x60;ETag&#x60; value in the &#x60;If-None-Match&#x60; request header revalidates a cached skill tree. A matching value returns &#x60;304 Not Modified&#x60; with no response body. This feature was introduced in GitLab 19.5.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Skill not found or skills are not available
     * <p><b>400</b> - Bad Request
     * @param name Deployed skill name
     * @return ResponseEntity&lt;APIEntitiesOrbitSkill&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesOrbitSkill> getApiV4OrbitSkillsNameWithHttpInfo(@jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesOrbitSkill> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4OrbitSkillsNameRequestCreation(name).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a deployed Orbit skill
     * Retrieves a complete deployed Orbit skill tree. HEAD requests retrieve only skill metadata. The response includes a &#x60;Cache-Control&#x60; header set to &#x60;private, max-age&#x3D;0, must-revalidate&#x60;, and an &#x60;ETag&#x60; header set to &#x60;\&quot;&lt;version&gt;\&quot;&#x60;. Sending the current &#x60;ETag&#x60; value in the &#x60;If-None-Match&#x60; request header revalidates a cached skill tree. A matching value returns &#x60;304 Not Modified&#x60; with no response body. This feature was introduced in GitLab 19.5.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Skill not found or skills are not available
     * <p><b>400</b> - Bad Request
     * @param name Deployed skill name
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4OrbitSkillsNameWithResponseSpec(@jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        return getApiV4OrbitSkillsNameRequestCreation(name);
    }

    /**
     * Retrieve a deployed Orbit skill file
     * Retrieves a UTF-8 file from a deployed Orbit skill as raw text. It fetches &#x60;SKILL.md&#x60; directly or follows relative links to files in the same skill tree. The response is the file content, not a JSON object. Markdown files (&#x60;.md&#x60;) have the content type &#x60;text/markdown; charset&#x3D;utf-8&#x60;. Other files have the content type &#x60;text/plain; charset&#x3D;utf-8&#x60;. The endpoint never returns a skill file as HTML. Only exact paths listed in the skill manifest are available. Unknown paths return &#x60;404 Not Found&#x60;. A path with a &#x60;.&#x60; segment returns &#x60;404 Not Found&#x60;. A path with a &#x60;..&#x60; segment returns &#x60;400 Bad Request&#x60;. Malformed paths can return &#x60;400 Bad Request&#x60;. API error responses are JSON, rather than raw file content. Requests blocked by path traversal middleware can return a plain-text error. The response includes a strong &#x60;ETag&#x60; from the SHA-256 of the file and a &#x60;Cache-Control&#x60; header set to &#x60;private, max-age&#x3D;0, must-revalidate&#x60;. Sending the &#x60;ETag&#x60; in &#x60;If-None-Match&#x60; returns &#x60;304 Not Modified&#x60; when the file is unchanged. &#x60;HEAD&#x60; returns the same headers without a body. This feature was introduced in GitLab 19.5.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Skill file not found or skills are not available
     * <p><b>400</b> - Bad Request
     * @param name Deployed skill name
     * @param path File path relative to the skill root
     * @return String
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4OrbitSkillsNamePathRequestCreation(@jakarta.annotation.Nonnull String name, @jakarta.annotation.Nonnull String path) throws RestClientResponseException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling getApiV4OrbitSkillsNamePath", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'path' is set
        if (path == null) {
            throw new RestClientResponseException("Missing the required parameter 'path' when calling getApiV4OrbitSkillsNamePath", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("name", name);
        pathParams.put("path", path);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "text/markdown", "text/plain"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<String> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/orbit/skills/{name}/{path}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a deployed Orbit skill file
     * Retrieves a UTF-8 file from a deployed Orbit skill as raw text. It fetches &#x60;SKILL.md&#x60; directly or follows relative links to files in the same skill tree. The response is the file content, not a JSON object. Markdown files (&#x60;.md&#x60;) have the content type &#x60;text/markdown; charset&#x3D;utf-8&#x60;. Other files have the content type &#x60;text/plain; charset&#x3D;utf-8&#x60;. The endpoint never returns a skill file as HTML. Only exact paths listed in the skill manifest are available. Unknown paths return &#x60;404 Not Found&#x60;. A path with a &#x60;.&#x60; segment returns &#x60;404 Not Found&#x60;. A path with a &#x60;..&#x60; segment returns &#x60;400 Bad Request&#x60;. Malformed paths can return &#x60;400 Bad Request&#x60;. API error responses are JSON, rather than raw file content. Requests blocked by path traversal middleware can return a plain-text error. The response includes a strong &#x60;ETag&#x60; from the SHA-256 of the file and a &#x60;Cache-Control&#x60; header set to &#x60;private, max-age&#x3D;0, must-revalidate&#x60;. Sending the &#x60;ETag&#x60; in &#x60;If-None-Match&#x60; returns &#x60;304 Not Modified&#x60; when the file is unchanged. &#x60;HEAD&#x60; returns the same headers without a body. This feature was introduced in GitLab 19.5.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Skill file not found or skills are not available
     * <p><b>400</b> - Bad Request
     * @param name Deployed skill name
     * @param path File path relative to the skill root
     * @return String
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public String getApiV4OrbitSkillsNamePath(@jakarta.annotation.Nonnull String name, @jakarta.annotation.Nonnull String path) throws RestClientResponseException {
        ParameterizedTypeReference<String> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4OrbitSkillsNamePathRequestCreation(name, path).body(localVarReturnType);
    }

    /**
     * Retrieve a deployed Orbit skill file
     * Retrieves a UTF-8 file from a deployed Orbit skill as raw text. It fetches &#x60;SKILL.md&#x60; directly or follows relative links to files in the same skill tree. The response is the file content, not a JSON object. Markdown files (&#x60;.md&#x60;) have the content type &#x60;text/markdown; charset&#x3D;utf-8&#x60;. Other files have the content type &#x60;text/plain; charset&#x3D;utf-8&#x60;. The endpoint never returns a skill file as HTML. Only exact paths listed in the skill manifest are available. Unknown paths return &#x60;404 Not Found&#x60;. A path with a &#x60;.&#x60; segment returns &#x60;404 Not Found&#x60;. A path with a &#x60;..&#x60; segment returns &#x60;400 Bad Request&#x60;. Malformed paths can return &#x60;400 Bad Request&#x60;. API error responses are JSON, rather than raw file content. Requests blocked by path traversal middleware can return a plain-text error. The response includes a strong &#x60;ETag&#x60; from the SHA-256 of the file and a &#x60;Cache-Control&#x60; header set to &#x60;private, max-age&#x3D;0, must-revalidate&#x60;. Sending the &#x60;ETag&#x60; in &#x60;If-None-Match&#x60; returns &#x60;304 Not Modified&#x60; when the file is unchanged. &#x60;HEAD&#x60; returns the same headers without a body. This feature was introduced in GitLab 19.5.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Skill file not found or skills are not available
     * <p><b>400</b> - Bad Request
     * @param name Deployed skill name
     * @param path File path relative to the skill root
     * @return ResponseEntity&lt;String&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<String> getApiV4OrbitSkillsNamePathWithHttpInfo(@jakarta.annotation.Nonnull String name, @jakarta.annotation.Nonnull String path) throws RestClientResponseException {
        ParameterizedTypeReference<String> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4OrbitSkillsNamePathRequestCreation(name, path).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a deployed Orbit skill file
     * Retrieves a UTF-8 file from a deployed Orbit skill as raw text. It fetches &#x60;SKILL.md&#x60; directly or follows relative links to files in the same skill tree. The response is the file content, not a JSON object. Markdown files (&#x60;.md&#x60;) have the content type &#x60;text/markdown; charset&#x3D;utf-8&#x60;. Other files have the content type &#x60;text/plain; charset&#x3D;utf-8&#x60;. The endpoint never returns a skill file as HTML. Only exact paths listed in the skill manifest are available. Unknown paths return &#x60;404 Not Found&#x60;. A path with a &#x60;.&#x60; segment returns &#x60;404 Not Found&#x60;. A path with a &#x60;..&#x60; segment returns &#x60;400 Bad Request&#x60;. Malformed paths can return &#x60;400 Bad Request&#x60;. API error responses are JSON, rather than raw file content. Requests blocked by path traversal middleware can return a plain-text error. The response includes a strong &#x60;ETag&#x60; from the SHA-256 of the file and a &#x60;Cache-Control&#x60; header set to &#x60;private, max-age&#x3D;0, must-revalidate&#x60;. Sending the &#x60;ETag&#x60; in &#x60;If-None-Match&#x60; returns &#x60;304 Not Modified&#x60; when the file is unchanged. &#x60;HEAD&#x60; returns the same headers without a body. This feature was introduced in GitLab 19.5.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Skill file not found or skills are not available
     * <p><b>400</b> - Bad Request
     * @param name Deployed skill name
     * @param path File path relative to the skill root
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4OrbitSkillsNamePathWithResponseSpec(@jakarta.annotation.Nonnull String name, @jakarta.annotation.Nonnull String path) throws RestClientResponseException {
        return getApiV4OrbitSkillsNamePathRequestCreation(name, path);
    }

    /**
     * Get entity context
     * Resolves one Orbit token to a PostgreSQL summary and, optionally, its graph neighborhood. The entity can be a merge request, work item, CI/CD job, CI/CD pipeline, branch, or commit. Requests that reach token resolution count against the orbit_query rate limit; with expand, they run at most one billable graph query. Requires a JSON request body. The request and response shape can change without notice. A &#x60;tokens&#x60; array that does not hold exactly one string, or an &#x60;expand&#x60; value that is not the JSON boolean &#x60;true&#x60; or &#x60;false&#x60;, returns &#x60;400 Bad Request&#x60;. With a fine-grained personal access token, the &#x60;read_orbit&#x60; permission is not enough. The token also needs &#x60;read_merge_request&#x60; or &#x60;read_work_item&#x60; on the project or group of the entity, &#x60;read_job&#x60; on the project for a job and for job logs, &#x60;read_pipeline&#x60; for pipelines, &#x60;read_pipeline_test_report&#x60; for failed tests, &#x60;read_pipeline_job&#x60; for pipeline jobs, &#x60;read_branch&#x60; or &#x60;read_commit&#x60; for a branch or commit, &#x60;read_commit_diff&#x60; and &#x60;read_commit_merge_request&#x60; for the changed files and merge requests of a commit, and &#x60;download_code&#x60; for the changed files of a merge request. Without them, the entity is returned as &#x60;not_found&#x60;. The pipeline status is &#x60;null&#x60;, and linked items the token cannot read are left out of the summary. Discussions need &#x60;read_merge_request_discussion&#x60; for a merge request, &#x60;read_epic_discussion&#x60; for an epic, and &#x60;read_issue_discussion&#x60; for other work items. Without them, &#x60;discussions&#x60; is &#x60;null&#x60;. The project scope of the token applies to the entity summary only. The graph expansion is filtered by the access of the user, the same as for named queries. Token forms are: &#x60;Type[id]&#x60;, for example &#x60;MergeRequest[123]&#x60;, where &#x60;id&#x60; is the global database ID; &#x60;gid://gitlab/&lt;Type&gt;/&lt;id&gt;&#x60;; a merge request, issue, work item, job, pipeline, or commit URL on this instance, including group work item URLs under &#x60;/groups/&#x60;; &#x60;path!iid&#x60; for a merge request, for example &#x60;gitlab-org/gitlab!42&#x60;; &#x60;path#iid&#x60; for a work item, for example &#x60;gitlab-org/gitlab#7&#x60;, where the path can also be a group; &#x60;Epic[id]&#x60;, &#x60;gid://gitlab/Epic/&lt;id&gt;&#x60;, an epic URL such as &#x60;/groups/gitlab-org/-/epics/5&#x60;, or &#x60;group&amp;iid&#x60;, for example &#x60;gitlab-org&amp;5&#x60;, where &#x60;id&#x60; is the epic ID; and &#x60;path@ref&#x60; for a branch of the project at &#x60;path&#x60;, or for a commit SHA when no branch has that name, for example &#x60;gitlab-org/gitlab@main&#x60;. The supported types are &#x60;MergeRequest&#x60;, &#x60;WorkItem&#x60;, &#x60;Job&#x60;, &#x60;Pipeline&#x60;, &#x60;Branch&#x60;, and &#x60;Commit&#x60;. &#x60;Issue&#x60; is an alias for &#x60;WorkItem&#x60;, and the response reports &#x60;WorkItem&#x60;. An epic resolves to its work item in the same way: the response reports &#x60;WorkItem&#x60;, the ID of the work item, and &#x60;work_item_type&#x60; &#x60;epic&#x60;, and the expansion runs for the work item. Other types return &#x60;unsupported_type&#x60;. Summaries include these fields. For a merge request: title, state, author, branches, head pipeline status, merge commit, reviewers, linked issues, description, changed files with their diffs, failed jobs, and discussions. For a work item: title, state, labels, assignees, milestone, linked merge requests, description, and discussions. For a job: name, status, stage, failure reason, ref, commit, pipeline, merge request, failed tests, and log. For a pipeline: status, ref, commit, source, merge request, jobs, and failed jobs. Jobs come from the pipeline itself, not from its child pipelines. For a branch: head commit, its open or latest merge request, and its latest pipeline with failed jobs. For a commit: title, description, author, its latest pipeline, the merge requests that contain it, and changed files. Failed tests come from the unit test report of the job. The log of a job is its job script output, followed by its &#x60;after_script&#x60; output, with lines longer than 500 characters cut. The log has a size limit of 40 KB for a requested job and 6 KB for each failed job of a merge request, pipeline, or branch, and &#x60;after_script&#x60; output gets at most a quarter of it. When a part does not fit, runs of five or more lines that differ only in numbers or hashes, such as progress output, are folded into their first and last line, and only the end of that part that fits is kept. &#x60;...&#x60; marks where lines were left out, and for a requested job &#x60;summary.truncated&#x60; then includes &#x60;log&#x60;. Changed line ranges use new-file line numbers, so a deletion is reported at the line after it, which can be one past the end of the file. A changed file has no &#x60;lines&#x60; when it was deleted or its diff could not be read, and empty &#x60;lines&#x60; when its diff has no text lines, such as for a binary or very large file. The &#x60;diff&#x60; of a changed file has its first 40 diff lines, with 200 lines shared across the merge request in file order. It is absent for deleted, generated, binary, and very large files, and for files after the budget ran out. A cut diff ends with &#x60;... N more diff lines&#x60;, and &#x60;summary.truncated&#x60; then includes &#x60;diffs&#x60;. Lists are limited and contain only records the user can read. &#x60;summary.truncated&#x60; is an array with the names of the lists that were cut, for example &#x60;[\&quot;reviewers\&quot;]&#x60;. It is empty when no list was cut. A failed job of a merge request whose failed tests were cut has &#x60;truncated: [\&quot;failed_tests\&quot;]&#x60;. Entries in &#x60;linked_issues&#x60; and &#x60;linked_merge_requests&#x60; are ordered by ID and have &#x60;type&#x60;, &#x60;id&#x60; (a string), &#x60;token&#x60;, &#x60;relation&#x60; (&#x60;CLOSES&#x60;), &#x60;iid&#x60;, &#x60;project_path&#x60;, and &#x60;title&#x60;. Every failure after a lookup starts returns the same &#x60;not_found&#x60; error, whether the record is missing or the user cannot read it. With &#x60;expand&#x60;, the request runs at most one graph query, using the &#x60;context_for_review&#x60;, &#x60;context_for_issue&#x60;, or &#x60;context_for_job&#x60; named query. Other types return &#x60;expansion_unsupported_type&#x60;. A request that runs one query is billed as one query. A failure of the expansion never fails the summary. &#x60;expansion.query&#x60; has the &#x60;name&#x60; and &#x60;parameters&#x60; of the query. It is present on a result and on every error except &#x60;expansion_unsupported_type&#x60;, including when no query ran. After &#x60;outside_graph_scope&#x60;, a rerun is billed and returns nothing. &#x60;expansion.error.code&#x60; is one of: &#x60;expansion_unsupported_type&#x60;, when the type has no expansion query; &#x60;outside_graph_scope&#x60;, when the entity is outside the part of the graph the user can query, which is a group where the user is not at least a Reporter or a group where Orbit is not turned on, and no query runs and nothing is billed; &#x60;billing_namespace_required&#x60;, with &#x60;message&#x60;, &#x60;deep_link&#x60; to the preferences of the user, and &#x60;candidates&#x60;, the groups the user can select, matching the &#x60;422&#x60; response of &#x60;POST /api/v4/orbit/query/:name&#x60;; or &#x60;quota_exhausted&#x60;, &#x60;timeout&#x60;, or &#x60;execution_error&#x60;. If &#x60;pagination.truncated&#x60; in the graph result is &#x60;false&#x60;, a missing center node means the entity has no readable neighbors. Otherwise, the result is unknown. With &#x60;response_format&#x3D;llm&#x60;, the response is &#x60;text/plain&#x60;. Each entity starts with its scalar fields, then its graph in TOON format under an indented &#x60;graph:&#x60; label, before longer blocks such as the description. A graph with no nodes is left out, and a failed expansion is a single &#x60;graph_error&#x3D;&lt;code&gt;&#x60; line. When a list in an entity is cut, the entity includes a &#x60;truncated&#x3D;&#x60; line right after its scalar fields that names the incomplete lists, for example &#x60;truncated&#x3D;reviewers,changed_files&#x60;. A failed job whose failed tests were cut has its own &#x60;truncated&#x3D;failed_tests&#x60; line. This feature was introduced in GitLab 19.6.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param postApiV4OrbitContextRequest The postApiV4OrbitContextRequest parameter
     * @return APIEntitiesOrbitContext
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4OrbitContextRequestCreation(@jakarta.annotation.Nonnull PostApiV4OrbitContextRequest postApiV4OrbitContextRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4OrbitContextRequest' is set
        if (postApiV4OrbitContextRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4OrbitContextRequest' when calling postApiV4OrbitContext", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
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
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesOrbitContext> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/orbit/context", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4OrbitContextRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get entity context
     * Resolves one Orbit token to a PostgreSQL summary and, optionally, its graph neighborhood. The entity can be a merge request, work item, CI/CD job, CI/CD pipeline, branch, or commit. Requests that reach token resolution count against the orbit_query rate limit; with expand, they run at most one billable graph query. Requires a JSON request body. The request and response shape can change without notice. A &#x60;tokens&#x60; array that does not hold exactly one string, or an &#x60;expand&#x60; value that is not the JSON boolean &#x60;true&#x60; or &#x60;false&#x60;, returns &#x60;400 Bad Request&#x60;. With a fine-grained personal access token, the &#x60;read_orbit&#x60; permission is not enough. The token also needs &#x60;read_merge_request&#x60; or &#x60;read_work_item&#x60; on the project or group of the entity, &#x60;read_job&#x60; on the project for a job and for job logs, &#x60;read_pipeline&#x60; for pipelines, &#x60;read_pipeline_test_report&#x60; for failed tests, &#x60;read_pipeline_job&#x60; for pipeline jobs, &#x60;read_branch&#x60; or &#x60;read_commit&#x60; for a branch or commit, &#x60;read_commit_diff&#x60; and &#x60;read_commit_merge_request&#x60; for the changed files and merge requests of a commit, and &#x60;download_code&#x60; for the changed files of a merge request. Without them, the entity is returned as &#x60;not_found&#x60;. The pipeline status is &#x60;null&#x60;, and linked items the token cannot read are left out of the summary. Discussions need &#x60;read_merge_request_discussion&#x60; for a merge request, &#x60;read_epic_discussion&#x60; for an epic, and &#x60;read_issue_discussion&#x60; for other work items. Without them, &#x60;discussions&#x60; is &#x60;null&#x60;. The project scope of the token applies to the entity summary only. The graph expansion is filtered by the access of the user, the same as for named queries. Token forms are: &#x60;Type[id]&#x60;, for example &#x60;MergeRequest[123]&#x60;, where &#x60;id&#x60; is the global database ID; &#x60;gid://gitlab/&lt;Type&gt;/&lt;id&gt;&#x60;; a merge request, issue, work item, job, pipeline, or commit URL on this instance, including group work item URLs under &#x60;/groups/&#x60;; &#x60;path!iid&#x60; for a merge request, for example &#x60;gitlab-org/gitlab!42&#x60;; &#x60;path#iid&#x60; for a work item, for example &#x60;gitlab-org/gitlab#7&#x60;, where the path can also be a group; &#x60;Epic[id]&#x60;, &#x60;gid://gitlab/Epic/&lt;id&gt;&#x60;, an epic URL such as &#x60;/groups/gitlab-org/-/epics/5&#x60;, or &#x60;group&amp;iid&#x60;, for example &#x60;gitlab-org&amp;5&#x60;, where &#x60;id&#x60; is the epic ID; and &#x60;path@ref&#x60; for a branch of the project at &#x60;path&#x60;, or for a commit SHA when no branch has that name, for example &#x60;gitlab-org/gitlab@main&#x60;. The supported types are &#x60;MergeRequest&#x60;, &#x60;WorkItem&#x60;, &#x60;Job&#x60;, &#x60;Pipeline&#x60;, &#x60;Branch&#x60;, and &#x60;Commit&#x60;. &#x60;Issue&#x60; is an alias for &#x60;WorkItem&#x60;, and the response reports &#x60;WorkItem&#x60;. An epic resolves to its work item in the same way: the response reports &#x60;WorkItem&#x60;, the ID of the work item, and &#x60;work_item_type&#x60; &#x60;epic&#x60;, and the expansion runs for the work item. Other types return &#x60;unsupported_type&#x60;. Summaries include these fields. For a merge request: title, state, author, branches, head pipeline status, merge commit, reviewers, linked issues, description, changed files with their diffs, failed jobs, and discussions. For a work item: title, state, labels, assignees, milestone, linked merge requests, description, and discussions. For a job: name, status, stage, failure reason, ref, commit, pipeline, merge request, failed tests, and log. For a pipeline: status, ref, commit, source, merge request, jobs, and failed jobs. Jobs come from the pipeline itself, not from its child pipelines. For a branch: head commit, its open or latest merge request, and its latest pipeline with failed jobs. For a commit: title, description, author, its latest pipeline, the merge requests that contain it, and changed files. Failed tests come from the unit test report of the job. The log of a job is its job script output, followed by its &#x60;after_script&#x60; output, with lines longer than 500 characters cut. The log has a size limit of 40 KB for a requested job and 6 KB for each failed job of a merge request, pipeline, or branch, and &#x60;after_script&#x60; output gets at most a quarter of it. When a part does not fit, runs of five or more lines that differ only in numbers or hashes, such as progress output, are folded into their first and last line, and only the end of that part that fits is kept. &#x60;...&#x60; marks where lines were left out, and for a requested job &#x60;summary.truncated&#x60; then includes &#x60;log&#x60;. Changed line ranges use new-file line numbers, so a deletion is reported at the line after it, which can be one past the end of the file. A changed file has no &#x60;lines&#x60; when it was deleted or its diff could not be read, and empty &#x60;lines&#x60; when its diff has no text lines, such as for a binary or very large file. The &#x60;diff&#x60; of a changed file has its first 40 diff lines, with 200 lines shared across the merge request in file order. It is absent for deleted, generated, binary, and very large files, and for files after the budget ran out. A cut diff ends with &#x60;... N more diff lines&#x60;, and &#x60;summary.truncated&#x60; then includes &#x60;diffs&#x60;. Lists are limited and contain only records the user can read. &#x60;summary.truncated&#x60; is an array with the names of the lists that were cut, for example &#x60;[\&quot;reviewers\&quot;]&#x60;. It is empty when no list was cut. A failed job of a merge request whose failed tests were cut has &#x60;truncated: [\&quot;failed_tests\&quot;]&#x60;. Entries in &#x60;linked_issues&#x60; and &#x60;linked_merge_requests&#x60; are ordered by ID and have &#x60;type&#x60;, &#x60;id&#x60; (a string), &#x60;token&#x60;, &#x60;relation&#x60; (&#x60;CLOSES&#x60;), &#x60;iid&#x60;, &#x60;project_path&#x60;, and &#x60;title&#x60;. Every failure after a lookup starts returns the same &#x60;not_found&#x60; error, whether the record is missing or the user cannot read it. With &#x60;expand&#x60;, the request runs at most one graph query, using the &#x60;context_for_review&#x60;, &#x60;context_for_issue&#x60;, or &#x60;context_for_job&#x60; named query. Other types return &#x60;expansion_unsupported_type&#x60;. A request that runs one query is billed as one query. A failure of the expansion never fails the summary. &#x60;expansion.query&#x60; has the &#x60;name&#x60; and &#x60;parameters&#x60; of the query. It is present on a result and on every error except &#x60;expansion_unsupported_type&#x60;, including when no query ran. After &#x60;outside_graph_scope&#x60;, a rerun is billed and returns nothing. &#x60;expansion.error.code&#x60; is one of: &#x60;expansion_unsupported_type&#x60;, when the type has no expansion query; &#x60;outside_graph_scope&#x60;, when the entity is outside the part of the graph the user can query, which is a group where the user is not at least a Reporter or a group where Orbit is not turned on, and no query runs and nothing is billed; &#x60;billing_namespace_required&#x60;, with &#x60;message&#x60;, &#x60;deep_link&#x60; to the preferences of the user, and &#x60;candidates&#x60;, the groups the user can select, matching the &#x60;422&#x60; response of &#x60;POST /api/v4/orbit/query/:name&#x60;; or &#x60;quota_exhausted&#x60;, &#x60;timeout&#x60;, or &#x60;execution_error&#x60;. If &#x60;pagination.truncated&#x60; in the graph result is &#x60;false&#x60;, a missing center node means the entity has no readable neighbors. Otherwise, the result is unknown. With &#x60;response_format&#x3D;llm&#x60;, the response is &#x60;text/plain&#x60;. Each entity starts with its scalar fields, then its graph in TOON format under an indented &#x60;graph:&#x60; label, before longer blocks such as the description. A graph with no nodes is left out, and a failed expansion is a single &#x60;graph_error&#x3D;&lt;code&gt;&#x60; line. When a list in an entity is cut, the entity includes a &#x60;truncated&#x3D;&#x60; line right after its scalar fields that names the incomplete lists, for example &#x60;truncated&#x3D;reviewers,changed_files&#x60;. A failed job whose failed tests were cut has its own &#x60;truncated&#x3D;failed_tests&#x60; line. This feature was introduced in GitLab 19.6.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param postApiV4OrbitContextRequest The postApiV4OrbitContextRequest parameter
     * @return APIEntitiesOrbitContext
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesOrbitContext postApiV4OrbitContext(@jakarta.annotation.Nonnull PostApiV4OrbitContextRequest postApiV4OrbitContextRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesOrbitContext> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4OrbitContextRequestCreation(postApiV4OrbitContextRequest).body(localVarReturnType);
    }

    /**
     * Get entity context
     * Resolves one Orbit token to a PostgreSQL summary and, optionally, its graph neighborhood. The entity can be a merge request, work item, CI/CD job, CI/CD pipeline, branch, or commit. Requests that reach token resolution count against the orbit_query rate limit; with expand, they run at most one billable graph query. Requires a JSON request body. The request and response shape can change without notice. A &#x60;tokens&#x60; array that does not hold exactly one string, or an &#x60;expand&#x60; value that is not the JSON boolean &#x60;true&#x60; or &#x60;false&#x60;, returns &#x60;400 Bad Request&#x60;. With a fine-grained personal access token, the &#x60;read_orbit&#x60; permission is not enough. The token also needs &#x60;read_merge_request&#x60; or &#x60;read_work_item&#x60; on the project or group of the entity, &#x60;read_job&#x60; on the project for a job and for job logs, &#x60;read_pipeline&#x60; for pipelines, &#x60;read_pipeline_test_report&#x60; for failed tests, &#x60;read_pipeline_job&#x60; for pipeline jobs, &#x60;read_branch&#x60; or &#x60;read_commit&#x60; for a branch or commit, &#x60;read_commit_diff&#x60; and &#x60;read_commit_merge_request&#x60; for the changed files and merge requests of a commit, and &#x60;download_code&#x60; for the changed files of a merge request. Without them, the entity is returned as &#x60;not_found&#x60;. The pipeline status is &#x60;null&#x60;, and linked items the token cannot read are left out of the summary. Discussions need &#x60;read_merge_request_discussion&#x60; for a merge request, &#x60;read_epic_discussion&#x60; for an epic, and &#x60;read_issue_discussion&#x60; for other work items. Without them, &#x60;discussions&#x60; is &#x60;null&#x60;. The project scope of the token applies to the entity summary only. The graph expansion is filtered by the access of the user, the same as for named queries. Token forms are: &#x60;Type[id]&#x60;, for example &#x60;MergeRequest[123]&#x60;, where &#x60;id&#x60; is the global database ID; &#x60;gid://gitlab/&lt;Type&gt;/&lt;id&gt;&#x60;; a merge request, issue, work item, job, pipeline, or commit URL on this instance, including group work item URLs under &#x60;/groups/&#x60;; &#x60;path!iid&#x60; for a merge request, for example &#x60;gitlab-org/gitlab!42&#x60;; &#x60;path#iid&#x60; for a work item, for example &#x60;gitlab-org/gitlab#7&#x60;, where the path can also be a group; &#x60;Epic[id]&#x60;, &#x60;gid://gitlab/Epic/&lt;id&gt;&#x60;, an epic URL such as &#x60;/groups/gitlab-org/-/epics/5&#x60;, or &#x60;group&amp;iid&#x60;, for example &#x60;gitlab-org&amp;5&#x60;, where &#x60;id&#x60; is the epic ID; and &#x60;path@ref&#x60; for a branch of the project at &#x60;path&#x60;, or for a commit SHA when no branch has that name, for example &#x60;gitlab-org/gitlab@main&#x60;. The supported types are &#x60;MergeRequest&#x60;, &#x60;WorkItem&#x60;, &#x60;Job&#x60;, &#x60;Pipeline&#x60;, &#x60;Branch&#x60;, and &#x60;Commit&#x60;. &#x60;Issue&#x60; is an alias for &#x60;WorkItem&#x60;, and the response reports &#x60;WorkItem&#x60;. An epic resolves to its work item in the same way: the response reports &#x60;WorkItem&#x60;, the ID of the work item, and &#x60;work_item_type&#x60; &#x60;epic&#x60;, and the expansion runs for the work item. Other types return &#x60;unsupported_type&#x60;. Summaries include these fields. For a merge request: title, state, author, branches, head pipeline status, merge commit, reviewers, linked issues, description, changed files with their diffs, failed jobs, and discussions. For a work item: title, state, labels, assignees, milestone, linked merge requests, description, and discussions. For a job: name, status, stage, failure reason, ref, commit, pipeline, merge request, failed tests, and log. For a pipeline: status, ref, commit, source, merge request, jobs, and failed jobs. Jobs come from the pipeline itself, not from its child pipelines. For a branch: head commit, its open or latest merge request, and its latest pipeline with failed jobs. For a commit: title, description, author, its latest pipeline, the merge requests that contain it, and changed files. Failed tests come from the unit test report of the job. The log of a job is its job script output, followed by its &#x60;after_script&#x60; output, with lines longer than 500 characters cut. The log has a size limit of 40 KB for a requested job and 6 KB for each failed job of a merge request, pipeline, or branch, and &#x60;after_script&#x60; output gets at most a quarter of it. When a part does not fit, runs of five or more lines that differ only in numbers or hashes, such as progress output, are folded into their first and last line, and only the end of that part that fits is kept. &#x60;...&#x60; marks where lines were left out, and for a requested job &#x60;summary.truncated&#x60; then includes &#x60;log&#x60;. Changed line ranges use new-file line numbers, so a deletion is reported at the line after it, which can be one past the end of the file. A changed file has no &#x60;lines&#x60; when it was deleted or its diff could not be read, and empty &#x60;lines&#x60; when its diff has no text lines, such as for a binary or very large file. The &#x60;diff&#x60; of a changed file has its first 40 diff lines, with 200 lines shared across the merge request in file order. It is absent for deleted, generated, binary, and very large files, and for files after the budget ran out. A cut diff ends with &#x60;... N more diff lines&#x60;, and &#x60;summary.truncated&#x60; then includes &#x60;diffs&#x60;. Lists are limited and contain only records the user can read. &#x60;summary.truncated&#x60; is an array with the names of the lists that were cut, for example &#x60;[\&quot;reviewers\&quot;]&#x60;. It is empty when no list was cut. A failed job of a merge request whose failed tests were cut has &#x60;truncated: [\&quot;failed_tests\&quot;]&#x60;. Entries in &#x60;linked_issues&#x60; and &#x60;linked_merge_requests&#x60; are ordered by ID and have &#x60;type&#x60;, &#x60;id&#x60; (a string), &#x60;token&#x60;, &#x60;relation&#x60; (&#x60;CLOSES&#x60;), &#x60;iid&#x60;, &#x60;project_path&#x60;, and &#x60;title&#x60;. Every failure after a lookup starts returns the same &#x60;not_found&#x60; error, whether the record is missing or the user cannot read it. With &#x60;expand&#x60;, the request runs at most one graph query, using the &#x60;context_for_review&#x60;, &#x60;context_for_issue&#x60;, or &#x60;context_for_job&#x60; named query. Other types return &#x60;expansion_unsupported_type&#x60;. A request that runs one query is billed as one query. A failure of the expansion never fails the summary. &#x60;expansion.query&#x60; has the &#x60;name&#x60; and &#x60;parameters&#x60; of the query. It is present on a result and on every error except &#x60;expansion_unsupported_type&#x60;, including when no query ran. After &#x60;outside_graph_scope&#x60;, a rerun is billed and returns nothing. &#x60;expansion.error.code&#x60; is one of: &#x60;expansion_unsupported_type&#x60;, when the type has no expansion query; &#x60;outside_graph_scope&#x60;, when the entity is outside the part of the graph the user can query, which is a group where the user is not at least a Reporter or a group where Orbit is not turned on, and no query runs and nothing is billed; &#x60;billing_namespace_required&#x60;, with &#x60;message&#x60;, &#x60;deep_link&#x60; to the preferences of the user, and &#x60;candidates&#x60;, the groups the user can select, matching the &#x60;422&#x60; response of &#x60;POST /api/v4/orbit/query/:name&#x60;; or &#x60;quota_exhausted&#x60;, &#x60;timeout&#x60;, or &#x60;execution_error&#x60;. If &#x60;pagination.truncated&#x60; in the graph result is &#x60;false&#x60;, a missing center node means the entity has no readable neighbors. Otherwise, the result is unknown. With &#x60;response_format&#x3D;llm&#x60;, the response is &#x60;text/plain&#x60;. Each entity starts with its scalar fields, then its graph in TOON format under an indented &#x60;graph:&#x60; label, before longer blocks such as the description. A graph with no nodes is left out, and a failed expansion is a single &#x60;graph_error&#x3D;&lt;code&gt;&#x60; line. When a list in an entity is cut, the entity includes a &#x60;truncated&#x3D;&#x60; line right after its scalar fields that names the incomplete lists, for example &#x60;truncated&#x3D;reviewers,changed_files&#x60;. A failed job whose failed tests were cut has its own &#x60;truncated&#x3D;failed_tests&#x60; line. This feature was introduced in GitLab 19.6.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param postApiV4OrbitContextRequest The postApiV4OrbitContextRequest parameter
     * @return ResponseEntity&lt;APIEntitiesOrbitContext&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesOrbitContext> postApiV4OrbitContextWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4OrbitContextRequest postApiV4OrbitContextRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesOrbitContext> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4OrbitContextRequestCreation(postApiV4OrbitContextRequest).toEntity(localVarReturnType);
    }

    /**
     * Get entity context
     * Resolves one Orbit token to a PostgreSQL summary and, optionally, its graph neighborhood. The entity can be a merge request, work item, CI/CD job, CI/CD pipeline, branch, or commit. Requests that reach token resolution count against the orbit_query rate limit; with expand, they run at most one billable graph query. Requires a JSON request body. The request and response shape can change without notice. A &#x60;tokens&#x60; array that does not hold exactly one string, or an &#x60;expand&#x60; value that is not the JSON boolean &#x60;true&#x60; or &#x60;false&#x60;, returns &#x60;400 Bad Request&#x60;. With a fine-grained personal access token, the &#x60;read_orbit&#x60; permission is not enough. The token also needs &#x60;read_merge_request&#x60; or &#x60;read_work_item&#x60; on the project or group of the entity, &#x60;read_job&#x60; on the project for a job and for job logs, &#x60;read_pipeline&#x60; for pipelines, &#x60;read_pipeline_test_report&#x60; for failed tests, &#x60;read_pipeline_job&#x60; for pipeline jobs, &#x60;read_branch&#x60; or &#x60;read_commit&#x60; for a branch or commit, &#x60;read_commit_diff&#x60; and &#x60;read_commit_merge_request&#x60; for the changed files and merge requests of a commit, and &#x60;download_code&#x60; for the changed files of a merge request. Without them, the entity is returned as &#x60;not_found&#x60;. The pipeline status is &#x60;null&#x60;, and linked items the token cannot read are left out of the summary. Discussions need &#x60;read_merge_request_discussion&#x60; for a merge request, &#x60;read_epic_discussion&#x60; for an epic, and &#x60;read_issue_discussion&#x60; for other work items. Without them, &#x60;discussions&#x60; is &#x60;null&#x60;. The project scope of the token applies to the entity summary only. The graph expansion is filtered by the access of the user, the same as for named queries. Token forms are: &#x60;Type[id]&#x60;, for example &#x60;MergeRequest[123]&#x60;, where &#x60;id&#x60; is the global database ID; &#x60;gid://gitlab/&lt;Type&gt;/&lt;id&gt;&#x60;; a merge request, issue, work item, job, pipeline, or commit URL on this instance, including group work item URLs under &#x60;/groups/&#x60;; &#x60;path!iid&#x60; for a merge request, for example &#x60;gitlab-org/gitlab!42&#x60;; &#x60;path#iid&#x60; for a work item, for example &#x60;gitlab-org/gitlab#7&#x60;, where the path can also be a group; &#x60;Epic[id]&#x60;, &#x60;gid://gitlab/Epic/&lt;id&gt;&#x60;, an epic URL such as &#x60;/groups/gitlab-org/-/epics/5&#x60;, or &#x60;group&amp;iid&#x60;, for example &#x60;gitlab-org&amp;5&#x60;, where &#x60;id&#x60; is the epic ID; and &#x60;path@ref&#x60; for a branch of the project at &#x60;path&#x60;, or for a commit SHA when no branch has that name, for example &#x60;gitlab-org/gitlab@main&#x60;. The supported types are &#x60;MergeRequest&#x60;, &#x60;WorkItem&#x60;, &#x60;Job&#x60;, &#x60;Pipeline&#x60;, &#x60;Branch&#x60;, and &#x60;Commit&#x60;. &#x60;Issue&#x60; is an alias for &#x60;WorkItem&#x60;, and the response reports &#x60;WorkItem&#x60;. An epic resolves to its work item in the same way: the response reports &#x60;WorkItem&#x60;, the ID of the work item, and &#x60;work_item_type&#x60; &#x60;epic&#x60;, and the expansion runs for the work item. Other types return &#x60;unsupported_type&#x60;. Summaries include these fields. For a merge request: title, state, author, branches, head pipeline status, merge commit, reviewers, linked issues, description, changed files with their diffs, failed jobs, and discussions. For a work item: title, state, labels, assignees, milestone, linked merge requests, description, and discussions. For a job: name, status, stage, failure reason, ref, commit, pipeline, merge request, failed tests, and log. For a pipeline: status, ref, commit, source, merge request, jobs, and failed jobs. Jobs come from the pipeline itself, not from its child pipelines. For a branch: head commit, its open or latest merge request, and its latest pipeline with failed jobs. For a commit: title, description, author, its latest pipeline, the merge requests that contain it, and changed files. Failed tests come from the unit test report of the job. The log of a job is its job script output, followed by its &#x60;after_script&#x60; output, with lines longer than 500 characters cut. The log has a size limit of 40 KB for a requested job and 6 KB for each failed job of a merge request, pipeline, or branch, and &#x60;after_script&#x60; output gets at most a quarter of it. When a part does not fit, runs of five or more lines that differ only in numbers or hashes, such as progress output, are folded into their first and last line, and only the end of that part that fits is kept. &#x60;...&#x60; marks where lines were left out, and for a requested job &#x60;summary.truncated&#x60; then includes &#x60;log&#x60;. Changed line ranges use new-file line numbers, so a deletion is reported at the line after it, which can be one past the end of the file. A changed file has no &#x60;lines&#x60; when it was deleted or its diff could not be read, and empty &#x60;lines&#x60; when its diff has no text lines, such as for a binary or very large file. The &#x60;diff&#x60; of a changed file has its first 40 diff lines, with 200 lines shared across the merge request in file order. It is absent for deleted, generated, binary, and very large files, and for files after the budget ran out. A cut diff ends with &#x60;... N more diff lines&#x60;, and &#x60;summary.truncated&#x60; then includes &#x60;diffs&#x60;. Lists are limited and contain only records the user can read. &#x60;summary.truncated&#x60; is an array with the names of the lists that were cut, for example &#x60;[\&quot;reviewers\&quot;]&#x60;. It is empty when no list was cut. A failed job of a merge request whose failed tests were cut has &#x60;truncated: [\&quot;failed_tests\&quot;]&#x60;. Entries in &#x60;linked_issues&#x60; and &#x60;linked_merge_requests&#x60; are ordered by ID and have &#x60;type&#x60;, &#x60;id&#x60; (a string), &#x60;token&#x60;, &#x60;relation&#x60; (&#x60;CLOSES&#x60;), &#x60;iid&#x60;, &#x60;project_path&#x60;, and &#x60;title&#x60;. Every failure after a lookup starts returns the same &#x60;not_found&#x60; error, whether the record is missing or the user cannot read it. With &#x60;expand&#x60;, the request runs at most one graph query, using the &#x60;context_for_review&#x60;, &#x60;context_for_issue&#x60;, or &#x60;context_for_job&#x60; named query. Other types return &#x60;expansion_unsupported_type&#x60;. A request that runs one query is billed as one query. A failure of the expansion never fails the summary. &#x60;expansion.query&#x60; has the &#x60;name&#x60; and &#x60;parameters&#x60; of the query. It is present on a result and on every error except &#x60;expansion_unsupported_type&#x60;, including when no query ran. After &#x60;outside_graph_scope&#x60;, a rerun is billed and returns nothing. &#x60;expansion.error.code&#x60; is one of: &#x60;expansion_unsupported_type&#x60;, when the type has no expansion query; &#x60;outside_graph_scope&#x60;, when the entity is outside the part of the graph the user can query, which is a group where the user is not at least a Reporter or a group where Orbit is not turned on, and no query runs and nothing is billed; &#x60;billing_namespace_required&#x60;, with &#x60;message&#x60;, &#x60;deep_link&#x60; to the preferences of the user, and &#x60;candidates&#x60;, the groups the user can select, matching the &#x60;422&#x60; response of &#x60;POST /api/v4/orbit/query/:name&#x60;; or &#x60;quota_exhausted&#x60;, &#x60;timeout&#x60;, or &#x60;execution_error&#x60;. If &#x60;pagination.truncated&#x60; in the graph result is &#x60;false&#x60;, a missing center node means the entity has no readable neighbors. Otherwise, the result is unknown. With &#x60;response_format&#x3D;llm&#x60;, the response is &#x60;text/plain&#x60;. Each entity starts with its scalar fields, then its graph in TOON format under an indented &#x60;graph:&#x60; label, before longer blocks such as the description. A graph with no nodes is left out, and a failed expansion is a single &#x60;graph_error&#x3D;&lt;code&gt;&#x60; line. When a list in an entity is cut, the entity includes a &#x60;truncated&#x3D;&#x60; line right after its scalar fields that names the incomplete lists, for example &#x60;truncated&#x3D;reviewers,changed_files&#x60;. A failed job whose failed tests were cut has its own &#x60;truncated&#x3D;failed_tests&#x60; line. This feature was introduced in GitLab 19.6.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>429</b> - Too many requests
     * @param postApiV4OrbitContextRequest The postApiV4OrbitContextRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4OrbitContextWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4OrbitContextRequest postApiV4OrbitContextRequest) throws RestClientResponseException {
        return postApiV4OrbitContextRequestCreation(postApiV4OrbitContextRequest);
    }

    /**
     * Execute a query
     * Executes a query against the Orbit gRPC service. For &#x60;response_format&#x60; &#x60;llm&#x60; and &#x60;gql&#x60;, the response is &#x60;text/plain&#x60;.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param postApiV4OrbitQueryRequest The postApiV4OrbitQueryRequest parameter
     * @return OrbitQueryResponse
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4OrbitQueryRequestCreation(@jakarta.annotation.Nonnull PostApiV4OrbitQueryRequest postApiV4OrbitQueryRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4OrbitQueryRequest' is set
        if (postApiV4OrbitQueryRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4OrbitQueryRequest' when calling postApiV4OrbitQuery", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
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
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<OrbitQueryResponse> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/orbit/query", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4OrbitQueryRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Execute a query
     * Executes a query against the Orbit gRPC service. For &#x60;response_format&#x60; &#x60;llm&#x60; and &#x60;gql&#x60;, the response is &#x60;text/plain&#x60;.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param postApiV4OrbitQueryRequest The postApiV4OrbitQueryRequest parameter
     * @return OrbitQueryResponse
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public OrbitQueryResponse postApiV4OrbitQuery(@jakarta.annotation.Nonnull PostApiV4OrbitQueryRequest postApiV4OrbitQueryRequest) throws RestClientResponseException {
        ParameterizedTypeReference<OrbitQueryResponse> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4OrbitQueryRequestCreation(postApiV4OrbitQueryRequest).body(localVarReturnType);
    }

    /**
     * Execute a query
     * Executes a query against the Orbit gRPC service. For &#x60;response_format&#x60; &#x60;llm&#x60; and &#x60;gql&#x60;, the response is &#x60;text/plain&#x60;.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param postApiV4OrbitQueryRequest The postApiV4OrbitQueryRequest parameter
     * @return ResponseEntity&lt;OrbitQueryResponse&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OrbitQueryResponse> postApiV4OrbitQueryWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4OrbitQueryRequest postApiV4OrbitQueryRequest) throws RestClientResponseException {
        ParameterizedTypeReference<OrbitQueryResponse> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4OrbitQueryRequestCreation(postApiV4OrbitQueryRequest).toEntity(localVarReturnType);
    }

    /**
     * Execute a query
     * Executes a query against the Orbit gRPC service. For &#x60;response_format&#x60; &#x60;llm&#x60; and &#x60;gql&#x60;, the response is &#x60;text/plain&#x60;.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param postApiV4OrbitQueryRequest The postApiV4OrbitQueryRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4OrbitQueryWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4OrbitQueryRequest postApiV4OrbitQueryRequest) throws RestClientResponseException {
        return postApiV4OrbitQueryRequestCreation(postApiV4OrbitQueryRequest);
    }

    /**
     * Execute a named query
     * Executes a server-defined named query against the Orbit gRPC service. Orbit defines its named queries on the server, so they stay in step with the query language and the schema. For programmatic use, this endpoint is preferred over &#x60;POST /orbit/query&#x60;. The GraphQL field &#x60;Query.orbit.queryTemplates&#x60; lists the named queries. Each query template has a &#x60;name&#x60;, a &#x60;description&#x60;, and the GQL &#x60;query&#x60; it runs. The &#x60;name&#x60; of a query template is passed as &#x60;:name&#x60; in the path. Requires a JSON request body: form encoding stringifies nested parameter values, which the per-query parameter schemas reject. This endpoint can return the same errors as &#x60;POST /orbit/query&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param name Named query identifier
     * @param postApiV4OrbitQueryNameRequest The postApiV4OrbitQueryNameRequest parameter
     * @return OrbitQueryResponse
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4OrbitQueryNameRequestCreation(@jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable PostApiV4OrbitQueryNameRequest postApiV4OrbitQueryNameRequest) throws RestClientResponseException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling postApiV4OrbitQueryName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("name", name);

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

        ParameterizedTypeReference<OrbitQueryResponse> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/orbit/query/{name}", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4OrbitQueryNameRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Execute a named query
     * Executes a server-defined named query against the Orbit gRPC service. Orbit defines its named queries on the server, so they stay in step with the query language and the schema. For programmatic use, this endpoint is preferred over &#x60;POST /orbit/query&#x60;. The GraphQL field &#x60;Query.orbit.queryTemplates&#x60; lists the named queries. Each query template has a &#x60;name&#x60;, a &#x60;description&#x60;, and the GQL &#x60;query&#x60; it runs. The &#x60;name&#x60; of a query template is passed as &#x60;:name&#x60; in the path. Requires a JSON request body: form encoding stringifies nested parameter values, which the per-query parameter schemas reject. This endpoint can return the same errors as &#x60;POST /orbit/query&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param name Named query identifier
     * @param postApiV4OrbitQueryNameRequest The postApiV4OrbitQueryNameRequest parameter
     * @return OrbitQueryResponse
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public OrbitQueryResponse postApiV4OrbitQueryName(@jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable PostApiV4OrbitQueryNameRequest postApiV4OrbitQueryNameRequest) throws RestClientResponseException {
        ParameterizedTypeReference<OrbitQueryResponse> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4OrbitQueryNameRequestCreation(name, postApiV4OrbitQueryNameRequest).body(localVarReturnType);
    }

    /**
     * Execute a named query
     * Executes a server-defined named query against the Orbit gRPC service. Orbit defines its named queries on the server, so they stay in step with the query language and the schema. For programmatic use, this endpoint is preferred over &#x60;POST /orbit/query&#x60;. The GraphQL field &#x60;Query.orbit.queryTemplates&#x60; lists the named queries. Each query template has a &#x60;name&#x60;, a &#x60;description&#x60;, and the GQL &#x60;query&#x60; it runs. The &#x60;name&#x60; of a query template is passed as &#x60;:name&#x60; in the path. Requires a JSON request body: form encoding stringifies nested parameter values, which the per-query parameter schemas reject. This endpoint can return the same errors as &#x60;POST /orbit/query&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param name Named query identifier
     * @param postApiV4OrbitQueryNameRequest The postApiV4OrbitQueryNameRequest parameter
     * @return ResponseEntity&lt;OrbitQueryResponse&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OrbitQueryResponse> postApiV4OrbitQueryNameWithHttpInfo(@jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable PostApiV4OrbitQueryNameRequest postApiV4OrbitQueryNameRequest) throws RestClientResponseException {
        ParameterizedTypeReference<OrbitQueryResponse> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4OrbitQueryNameRequestCreation(name, postApiV4OrbitQueryNameRequest).toEntity(localVarReturnType);
    }

    /**
     * Execute a named query
     * Executes a server-defined named query against the Orbit gRPC service. Orbit defines its named queries on the server, so they stay in step with the query language and the schema. For programmatic use, this endpoint is preferred over &#x60;POST /orbit/query&#x60;. The GraphQL field &#x60;Query.orbit.queryTemplates&#x60; lists the named queries. Each query template has a &#x60;name&#x60;, a &#x60;description&#x60;, and the GQL &#x60;query&#x60; it runs. The &#x60;name&#x60; of a query template is passed as &#x60;:name&#x60; in the path. Requires a JSON request body: form encoding stringifies nested parameter values, which the per-query parameter schemas reject. This endpoint can return the same errors as &#x60;POST /orbit/query&#x60;.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param name Named query identifier
     * @param postApiV4OrbitQueryNameRequest The postApiV4OrbitQueryNameRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4OrbitQueryNameWithResponseSpec(@jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable PostApiV4OrbitQueryNameRequest postApiV4OrbitQueryNameRequest) throws RestClientResponseException {
        return postApiV4OrbitQueryNameRequestCreation(name, postApiV4OrbitQueryNameRequest);
    }
}
