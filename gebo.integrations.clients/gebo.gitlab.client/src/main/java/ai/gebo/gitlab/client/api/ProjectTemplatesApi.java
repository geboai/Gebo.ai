package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesLicense;
import ai.gebo.gitlab.client.model.APIEntitiesTemplatesList;
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
public class ProjectTemplatesApi {
    private ApiClient apiClient;

    public ProjectTemplatesApi() {
        this(new ApiClient());
    }

    public ProjectTemplatesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * List all templates of a particular type
     * Lists all templates of a specified type for a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param type Type of the template.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesTemplatesList&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdTemplatesTypeRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String type, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdTemplatesType", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'type' is set
        if (type == null) {
            throw new RestClientResponseException("Missing the required parameter 'type' when calling getApiV4ProjectsIdTemplatesType", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("type", type);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesTemplatesList>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/templates/{type}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all templates of a particular type
     * Lists all templates of a specified type for a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param type Type of the template.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesTemplatesList&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesTemplatesList> getApiV4ProjectsIdTemplatesType(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String type, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesTemplatesList>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdTemplatesTypeRequestCreation(id, type, page, perPage).body(localVarReturnType);
    }

    /**
     * List all templates of a particular type
     * Lists all templates of a specified type for a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param type Type of the template.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesTemplatesList&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesTemplatesList>> getApiV4ProjectsIdTemplatesTypeWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String type, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesTemplatesList>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdTemplatesTypeRequestCreation(id, type, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all templates of a particular type
     * Lists all templates of a specified type for a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param type Type of the template.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdTemplatesTypeWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String type, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4ProjectsIdTemplatesTypeRequestCreation(id, type, page, perPage);
    }

    /**
     * Retrieve a template of a particular type
     * Retrieves a template of a specified type for a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param type Type of the template.
     * @param name Key of the template, as obtained from the collection endpoint.
     * @param sourceTemplateProjectId ID of the project where the template is stored. Useful when multiple templates from different projects have the same name. If omitted, the match from the closest ancestor is returned.
     * @param project Project name to use when expanding placeholders in the template. Affects only licenses.
     * @param fullname Full name of the copyright holder to use when expanding placeholders in the template. Affects only licenses.
     * @return APIEntitiesLicense
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdTemplatesTypeNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String type, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable Integer sourceTemplateProjectId, @jakarta.annotation.Nullable String project, @jakarta.annotation.Nullable String fullname) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdTemplatesTypeName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'type' is set
        if (type == null) {
            throw new RestClientResponseException("Missing the required parameter 'type' when calling getApiV4ProjectsIdTemplatesTypeName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling getApiV4ProjectsIdTemplatesTypeName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("type", type);
        pathParams.put("name", name);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "source_template_project_id", sourceTemplateProjectId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "project", project));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "fullname", fullname));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesLicense> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/templates/{type}/{name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a template of a particular type
     * Retrieves a template of a specified type for a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param type Type of the template.
     * @param name Key of the template, as obtained from the collection endpoint.
     * @param sourceTemplateProjectId ID of the project where the template is stored. Useful when multiple templates from different projects have the same name. If omitted, the match from the closest ancestor is returned.
     * @param project Project name to use when expanding placeholders in the template. Affects only licenses.
     * @param fullname Full name of the copyright holder to use when expanding placeholders in the template. Affects only licenses.
     * @return APIEntitiesLicense
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesLicense getApiV4ProjectsIdTemplatesTypeName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String type, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable Integer sourceTemplateProjectId, @jakarta.annotation.Nullable String project, @jakarta.annotation.Nullable String fullname) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesLicense> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdTemplatesTypeNameRequestCreation(id, type, name, sourceTemplateProjectId, project, fullname).body(localVarReturnType);
    }

    /**
     * Retrieve a template of a particular type
     * Retrieves a template of a specified type for a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param type Type of the template.
     * @param name Key of the template, as obtained from the collection endpoint.
     * @param sourceTemplateProjectId ID of the project where the template is stored. Useful when multiple templates from different projects have the same name. If omitted, the match from the closest ancestor is returned.
     * @param project Project name to use when expanding placeholders in the template. Affects only licenses.
     * @param fullname Full name of the copyright holder to use when expanding placeholders in the template. Affects only licenses.
     * @return ResponseEntity&lt;APIEntitiesLicense&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesLicense> getApiV4ProjectsIdTemplatesTypeNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String type, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable Integer sourceTemplateProjectId, @jakarta.annotation.Nullable String project, @jakarta.annotation.Nullable String fullname) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesLicense> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdTemplatesTypeNameRequestCreation(id, type, name, sourceTemplateProjectId, project, fullname).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a template of a particular type
     * Retrieves a template of a specified type for a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Not found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param type Type of the template.
     * @param name Key of the template, as obtained from the collection endpoint.
     * @param sourceTemplateProjectId ID of the project where the template is stored. Useful when multiple templates from different projects have the same name. If omitted, the match from the closest ancestor is returned.
     * @param project Project name to use when expanding placeholders in the template. Affects only licenses.
     * @param fullname Full name of the copyright holder to use when expanding placeholders in the template. Affects only licenses.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdTemplatesTypeNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String type, @jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable Integer sourceTemplateProjectId, @jakarta.annotation.Nullable String project, @jakarta.annotation.Nullable String fullname) throws RestClientResponseException {
        return getApiV4ProjectsIdTemplatesTypeNameRequestCreation(id, type, name, sourceTemplateProjectId, project, fullname);
    }
}
