package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesLicense;
import ai.gebo.gitlab.client.model.APIEntitiesTemplate;
import ai.gebo.gitlab.client.model.APIEntitiesTemplatesList;

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
public class TemplatesApi {
    private ApiClient apiClient;

    public TemplatesApi() {
        this(new ApiClient());
    }

    public TemplatesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * List all Dockerfile templates
     * Lists all Dockerfile templates.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesTemplatesList&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4TemplatesDockerfilesRequestCreation(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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
        return apiClient.invokeAPI("/api/v4/templates/dockerfiles", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all Dockerfile templates
     * Lists all Dockerfile templates.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesTemplatesList&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesTemplatesList> getApiV4TemplatesDockerfiles(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesTemplatesList>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4TemplatesDockerfilesRequestCreation(page, perPage).body(localVarReturnType);
    }

    /**
     * List all Dockerfile templates
     * Lists all Dockerfile templates.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesTemplatesList&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesTemplatesList>> getApiV4TemplatesDockerfilesWithHttpInfo(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesTemplatesList>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4TemplatesDockerfilesRequestCreation(page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all Dockerfile templates
     * Lists all Dockerfile templates.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4TemplatesDockerfilesWithResponseSpec(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4TemplatesDockerfilesRequestCreation(page, perPage);
    }

    /**
     * Retrieve a Dockerfile template
     * Retrieves a specified Dockerfile template.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param name The name of the Dockerfile template
     * @return APIEntitiesTemplate
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4TemplatesDockerfilesNameRequestCreation(@jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling getApiV4TemplatesDockerfilesName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesTemplate> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/templates/dockerfiles/{name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a Dockerfile template
     * Retrieves a specified Dockerfile template.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param name The name of the Dockerfile template
     * @return APIEntitiesTemplate
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesTemplate getApiV4TemplatesDockerfilesName(@jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesTemplate> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4TemplatesDockerfilesNameRequestCreation(name).body(localVarReturnType);
    }

    /**
     * Retrieve a Dockerfile template
     * Retrieves a specified Dockerfile template.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param name The name of the Dockerfile template
     * @return ResponseEntity&lt;APIEntitiesTemplate&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesTemplate> getApiV4TemplatesDockerfilesNameWithHttpInfo(@jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesTemplate> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4TemplatesDockerfilesNameRequestCreation(name).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a Dockerfile template
     * Retrieves a specified Dockerfile template.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param name The name of the Dockerfile template
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4TemplatesDockerfilesNameWithResponseSpec(@jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        return getApiV4TemplatesDockerfilesNameRequestCreation(name);
    }

    /**
     * List all .gitignore templates
     * Lists all .gitignore templates.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesTemplatesList&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4TemplatesGitignoresRequestCreation(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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
        return apiClient.invokeAPI("/api/v4/templates/gitignores", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all .gitignore templates
     * Lists all .gitignore templates.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesTemplatesList&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesTemplatesList> getApiV4TemplatesGitignores(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesTemplatesList>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4TemplatesGitignoresRequestCreation(page, perPage).body(localVarReturnType);
    }

    /**
     * List all .gitignore templates
     * Lists all .gitignore templates.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesTemplatesList&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesTemplatesList>> getApiV4TemplatesGitignoresWithHttpInfo(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesTemplatesList>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4TemplatesGitignoresRequestCreation(page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all .gitignore templates
     * Lists all .gitignore templates.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4TemplatesGitignoresWithResponseSpec(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4TemplatesGitignoresRequestCreation(page, perPage);
    }

    /**
     * Retrieve a .gitignore template
     * Retrieves a specified .gitignore template.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param name The name of the .gitignore template
     * @return APIEntitiesTemplate
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4TemplatesGitignoresNameRequestCreation(@jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling getApiV4TemplatesGitignoresName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesTemplate> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/templates/gitignores/{name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a .gitignore template
     * Retrieves a specified .gitignore template.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param name The name of the .gitignore template
     * @return APIEntitiesTemplate
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesTemplate getApiV4TemplatesGitignoresName(@jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesTemplate> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4TemplatesGitignoresNameRequestCreation(name).body(localVarReturnType);
    }

    /**
     * Retrieve a .gitignore template
     * Retrieves a specified .gitignore template.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param name The name of the .gitignore template
     * @return ResponseEntity&lt;APIEntitiesTemplate&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesTemplate> getApiV4TemplatesGitignoresNameWithHttpInfo(@jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesTemplate> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4TemplatesGitignoresNameRequestCreation(name).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a .gitignore template
     * Retrieves a specified .gitignore template.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param name The name of the .gitignore template
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4TemplatesGitignoresNameWithResponseSpec(@jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        return getApiV4TemplatesGitignoresNameRequestCreation(name);
    }

    /**
     * List all GitLab CI/CD YAML templates
     * Lists all GitLab CI/CD YAML templates.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesTemplatesList&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4TemplatesGitlabCiYmlsRequestCreation(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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
        return apiClient.invokeAPI("/api/v4/templates/gitlab_ci_ymls", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all GitLab CI/CD YAML templates
     * Lists all GitLab CI/CD YAML templates.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesTemplatesList&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesTemplatesList> getApiV4TemplatesGitlabCiYmls(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesTemplatesList>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4TemplatesGitlabCiYmlsRequestCreation(page, perPage).body(localVarReturnType);
    }

    /**
     * List all GitLab CI/CD YAML templates
     * Lists all GitLab CI/CD YAML templates.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesTemplatesList&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesTemplatesList>> getApiV4TemplatesGitlabCiYmlsWithHttpInfo(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesTemplatesList>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4TemplatesGitlabCiYmlsRequestCreation(page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all GitLab CI/CD YAML templates
     * Lists all GitLab CI/CD YAML templates.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4TemplatesGitlabCiYmlsWithResponseSpec(@jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4TemplatesGitlabCiYmlsRequestCreation(page, perPage);
    }

    /**
     * Retrieve a GitLab CI/CD YAML template
     * Retrieves a specified GitLab CI/CD YAML template.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param name The name of the GitLab CI/CD YAML template
     * @return APIEntitiesTemplate
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4TemplatesGitlabCiYmlsNameRequestCreation(@jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling getApiV4TemplatesGitlabCiYmlsName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesTemplate> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/templates/gitlab_ci_ymls/{name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a GitLab CI/CD YAML template
     * Retrieves a specified GitLab CI/CD YAML template.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param name The name of the GitLab CI/CD YAML template
     * @return APIEntitiesTemplate
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesTemplate getApiV4TemplatesGitlabCiYmlsName(@jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesTemplate> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4TemplatesGitlabCiYmlsNameRequestCreation(name).body(localVarReturnType);
    }

    /**
     * Retrieve a GitLab CI/CD YAML template
     * Retrieves a specified GitLab CI/CD YAML template.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param name The name of the GitLab CI/CD YAML template
     * @return ResponseEntity&lt;APIEntitiesTemplate&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesTemplate> getApiV4TemplatesGitlabCiYmlsNameWithHttpInfo(@jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesTemplate> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4TemplatesGitlabCiYmlsNameRequestCreation(name).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a GitLab CI/CD YAML template
     * Retrieves a specified GitLab CI/CD YAML template.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param name The name of the GitLab CI/CD YAML template
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4TemplatesGitlabCiYmlsNameWithResponseSpec(@jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        return getApiV4TemplatesGitlabCiYmlsNameRequestCreation(name);
    }

    /**
     * List all license templates
     * Lists all available license templates.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param popular If &#x60;true&#x60;, returns only popular licenses. If &#x60;false&#x60;, returns only licenses that aren&#39;t popular.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesLicense&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4TemplatesLicensesRequestCreation(@jakarta.annotation.Nullable Boolean popular, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "popular", popular));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "per_page", perPage));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<List<APIEntitiesLicense>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/templates/licenses", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all license templates
     * Lists all available license templates.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param popular If &#x60;true&#x60;, returns only popular licenses. If &#x60;false&#x60;, returns only licenses that aren&#39;t popular.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return List&lt;APIEntitiesLicense&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesLicense> getApiV4TemplatesLicenses(@jakarta.annotation.Nullable Boolean popular, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesLicense>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4TemplatesLicensesRequestCreation(popular, page, perPage).body(localVarReturnType);
    }

    /**
     * List all license templates
     * Lists all available license templates.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param popular If &#x60;true&#x60;, returns only popular licenses. If &#x60;false&#x60;, returns only licenses that aren&#39;t popular.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseEntity&lt;List&lt;APIEntitiesLicense&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesLicense>> getApiV4TemplatesLicensesWithHttpInfo(@jakarta.annotation.Nullable Boolean popular, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesLicense>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4TemplatesLicensesRequestCreation(popular, page, perPage).toEntity(localVarReturnType);
    }

    /**
     * List all license templates
     * Lists all available license templates.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param popular If &#x60;true&#x60;, returns only popular licenses. If &#x60;false&#x60;, returns only licenses that aren&#39;t popular.
     * @param page Page number of results to retrieve.
     * @param perPage Number of items to list per page.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4TemplatesLicensesWithResponseSpec(@jakarta.annotation.Nullable Boolean popular, @jakarta.annotation.Nullable Integer page, @jakarta.annotation.Nullable Integer perPage) throws RestClientResponseException {
        return getApiV4TemplatesLicensesRequestCreation(popular, page, perPage);
    }

    /**
     * Retrieve a license template
     * Retrieves a specified license template. You can pass parameters to replace the license placeholder. If &#x60;fullname&#x60; is omitted and the request is authenticated, the name of the authenticated user replaces the copyright holder placeholder.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param name Name of the license template.
     * @param project Name of the copyrighted project.
     * @param fullname Full name of the copyright holder.
     * @return APIEntitiesLicense
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4TemplatesLicensesNameRequestCreation(@jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable String project, @jakarta.annotation.Nullable String fullname) throws RestClientResponseException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling getApiV4TemplatesLicensesName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("name", name);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

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
        return apiClient.invokeAPI("/api/v4/templates/licenses/{name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve a license template
     * Retrieves a specified license template. You can pass parameters to replace the license placeholder. If &#x60;fullname&#x60; is omitted and the request is authenticated, the name of the authenticated user replaces the copyright holder placeholder.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param name Name of the license template.
     * @param project Name of the copyrighted project.
     * @param fullname Full name of the copyright holder.
     * @return APIEntitiesLicense
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesLicense getApiV4TemplatesLicensesName(@jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable String project, @jakarta.annotation.Nullable String fullname) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesLicense> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4TemplatesLicensesNameRequestCreation(name, project, fullname).body(localVarReturnType);
    }

    /**
     * Retrieve a license template
     * Retrieves a specified license template. You can pass parameters to replace the license placeholder. If &#x60;fullname&#x60; is omitted and the request is authenticated, the name of the authenticated user replaces the copyright holder placeholder.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param name Name of the license template.
     * @param project Name of the copyrighted project.
     * @param fullname Full name of the copyright holder.
     * @return ResponseEntity&lt;APIEntitiesLicense&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesLicense> getApiV4TemplatesLicensesNameWithHttpInfo(@jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable String project, @jakarta.annotation.Nullable String fullname) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesLicense> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4TemplatesLicensesNameRequestCreation(name, project, fullname).toEntity(localVarReturnType);
    }

    /**
     * Retrieve a license template
     * Retrieves a specified license template. You can pass parameters to replace the license placeholder. If &#x60;fullname&#x60; is omitted and the request is authenticated, the name of the authenticated user replaces the copyright holder placeholder.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param name Name of the license template.
     * @param project Name of the copyrighted project.
     * @param fullname Full name of the copyright holder.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4TemplatesLicensesNameWithResponseSpec(@jakarta.annotation.Nonnull String name, @jakarta.annotation.Nullable String project, @jakarta.annotation.Nullable String fullname) throws RestClientResponseException {
        return getApiV4TemplatesLicensesNameRequestCreation(name, project, fullname);
    }
}
