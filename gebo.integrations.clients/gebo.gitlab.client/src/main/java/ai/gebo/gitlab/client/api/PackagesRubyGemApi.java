package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import java.io.File;
import ai.gebo.gitlab.client.model.GetApiV4DependencyListExportsExportIdExportIdParameter;

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
public class PackagesRubyGemApi {
    private ApiClient apiClient;

    public PackagesRubyGemApi() {
        this(new ApiClient());
    }

    public PackagesRubyGemApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Retrieve dependencies
     * Retrieves a list of dependencies for specified gems. The response is a marshalled array of hashes for all versions of the requested gems. Because the response is marshalled, you can store it in a file. Each hash contains &#x60;name&#x60;, &#x60;number&#x60;, &#x60;platform&#x60;, and &#x60;dependencies&#x60;, where &#x60;dependencies&#x60; is an array of &#x60;[name, requirement]&#x60; pairs. To read the response with Ruby, authenticate the request with an access token or with credentials set in &#x60;~/.gem/credentials&#x60;.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param gems Comma-separated list of gems to fetch dependencies for.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesRubygemsApiV1DependenciesRequestCreation(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nullable List<String> gems) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesRubygemsApiV1Dependencies", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "gems", gems));

        final String[] localVarAccepts = { 
            "application/octet-stream"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/rubygems/api/v1/dependencies", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Retrieve dependencies
     * Retrieves a list of dependencies for specified gems. The response is a marshalled array of hashes for all versions of the requested gems. Because the response is marshalled, you can store it in a file. Each hash contains &#x60;name&#x60;, &#x60;number&#x60;, &#x60;platform&#x60;, and &#x60;dependencies&#x60;, where &#x60;dependencies&#x60; is an array of &#x60;[name, requirement]&#x60; pairs. To read the response with Ruby, authenticate the request with an access token or with credentials set in &#x60;~/.gem/credentials&#x60;.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param gems Comma-separated list of gems to fetch dependencies for.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public File getApiV4ProjectsIdPackagesRubygemsApiV1Dependencies(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nullable List<String> gems) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesRubygemsApiV1DependenciesRequestCreation(id, gems).body(localVarReturnType);
    }

    /**
     * Retrieve dependencies
     * Retrieves a list of dependencies for specified gems. The response is a marshalled array of hashes for all versions of the requested gems. Because the response is marshalled, you can store it in a file. Each hash contains &#x60;name&#x60;, &#x60;number&#x60;, &#x60;platform&#x60;, and &#x60;dependencies&#x60;, where &#x60;dependencies&#x60; is an array of &#x60;[name, requirement]&#x60; pairs. To read the response with Ruby, authenticate the request with an access token or with credentials set in &#x60;~/.gem/credentials&#x60;.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param gems Comma-separated list of gems to fetch dependencies for.
     * @return ResponseEntity&lt;File&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<File> getApiV4ProjectsIdPackagesRubygemsApiV1DependenciesWithHttpInfo(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nullable List<String> gems) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesRubygemsApiV1DependenciesRequestCreation(id, gems).toEntity(localVarReturnType);
    }

    /**
     * Retrieve dependencies
     * Retrieves a list of dependencies for specified gems. The response is a marshalled array of hashes for all versions of the requested gems. Because the response is marshalled, you can store it in a file. Each hash contains &#x60;name&#x60;, &#x60;number&#x60;, &#x60;platform&#x60;, and &#x60;dependencies&#x60;, where &#x60;dependencies&#x60; is an array of &#x60;[name, requirement]&#x60; pairs. To read the response with Ruby, authenticate the request with an access token or with credentials set in &#x60;~/.gem/credentials&#x60;.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param gems Comma-separated list of gems to fetch dependencies for.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesRubygemsApiV1DependenciesWithResponseSpec(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nullable List<String> gems) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesRubygemsApiV1DependenciesRequestCreation(id, gems);
    }

    /**
     * Download the spec index file
     * Downloads a RubyGems spec index file (specs.4.8.gz, latest_specs.4.8.gz, or prerelease_specs.4.8.gz) for a project. The index lists the gems available in the project registry. &#x60;specs.4.8.gz&#x60; lists released versions, &#x60;latest_specs.4.8.gz&#x60; lists the latest released version of each gem, and &#x60;prerelease_specs.4.8.gz&#x60; lists prerelease versions. The response is a Gzip-compressed, marshalled array of &#x60;[name, version, platform]&#x60; entries.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param fileName Name of the spec file.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesRubygemsFileNameRequestCreation(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesRubygemsFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling getApiV4ProjectsIdPackagesRubygemsFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("file_name", fileName);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/octet-stream"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/rubygems/{file_name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download the spec index file
     * Downloads a RubyGems spec index file (specs.4.8.gz, latest_specs.4.8.gz, or prerelease_specs.4.8.gz) for a project. The index lists the gems available in the project registry. &#x60;specs.4.8.gz&#x60; lists released versions, &#x60;latest_specs.4.8.gz&#x60; lists the latest released version of each gem, and &#x60;prerelease_specs.4.8.gz&#x60; lists prerelease versions. The response is a Gzip-compressed, marshalled array of &#x60;[name, version, platform]&#x60; entries.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param fileName Name of the spec file.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public File getApiV4ProjectsIdPackagesRubygemsFileName(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesRubygemsFileNameRequestCreation(id, fileName).body(localVarReturnType);
    }

    /**
     * Download the spec index file
     * Downloads a RubyGems spec index file (specs.4.8.gz, latest_specs.4.8.gz, or prerelease_specs.4.8.gz) for a project. The index lists the gems available in the project registry. &#x60;specs.4.8.gz&#x60; lists released versions, &#x60;latest_specs.4.8.gz&#x60; lists the latest released version of each gem, and &#x60;prerelease_specs.4.8.gz&#x60; lists prerelease versions. The response is a Gzip-compressed, marshalled array of &#x60;[name, version, platform]&#x60; entries.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param fileName Name of the spec file.
     * @return ResponseEntity&lt;File&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<File> getApiV4ProjectsIdPackagesRubygemsFileNameWithHttpInfo(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesRubygemsFileNameRequestCreation(id, fileName).toEntity(localVarReturnType);
    }

    /**
     * Download the spec index file
     * Downloads a RubyGems spec index file (specs.4.8.gz, latest_specs.4.8.gz, or prerelease_specs.4.8.gz) for a project. The index lists the gems available in the project registry. &#x60;specs.4.8.gz&#x60; lists released versions, &#x60;latest_specs.4.8.gz&#x60; lists the latest released version of each gem, and &#x60;prerelease_specs.4.8.gz&#x60; lists prerelease versions. The response is a Gzip-compressed, marshalled array of &#x60;[name, version, platform]&#x60; entries.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param id ID or URL-encoded path of the project.
     * @param fileName Name of the spec file.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesRubygemsFileNameWithResponseSpec(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesRubygemsFileNameRequestCreation(id, fileName);
    }

    /**
     * Download a gem file
     * Downloads a specified gem file for a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param fileName Name of the &#x60;.gem&#x60; file.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesRubygemsGemsFileNameRequestCreation(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesRubygemsGemsFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling getApiV4ProjectsIdPackagesRubygemsGemsFileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("file_name", fileName);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/octet-stream"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/rubygems/gems/{file_name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download a gem file
     * Downloads a specified gem file for a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param fileName Name of the &#x60;.gem&#x60; file.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public File getApiV4ProjectsIdPackagesRubygemsGemsFileName(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesRubygemsGemsFileNameRequestCreation(id, fileName).body(localVarReturnType);
    }

    /**
     * Download a gem file
     * Downloads a specified gem file for a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param fileName Name of the &#x60;.gem&#x60; file.
     * @return ResponseEntity&lt;File&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<File> getApiV4ProjectsIdPackagesRubygemsGemsFileNameWithHttpInfo(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesRubygemsGemsFileNameRequestCreation(id, fileName).toEntity(localVarReturnType);
    }

    /**
     * Download a gem file
     * Downloads a specified gem file for a project.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param fileName Name of the &#x60;.gem&#x60; file.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesRubygemsGemsFileNameWithResponseSpec(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesRubygemsGemsFileNameRequestCreation(id, fileName);
    }

    /**
     * Download a gemspec file
     * Downloads a gemspec file in Marshal format for a specified gem version. The response is a deflate-compressed, marshalled &#x60;Gem::Specification&#x60; object.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param fileName Name of the gemspec file, in the format &#x60;&lt;gem_name&gt;-&lt;version&gt;.gemspec.rz&#x60;.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesRubygemsQuickMarshal48FileNameRequestCreation(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesRubygemsQuickMarshal48FileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileName' when calling getApiV4ProjectsIdPackagesRubygemsQuickMarshal48FileName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("file_name", fileName);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/octet-stream"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/rubygems/quick/Marshal.4.8/{file_name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download a gemspec file
     * Downloads a gemspec file in Marshal format for a specified gem version. The response is a deflate-compressed, marshalled &#x60;Gem::Specification&#x60; object.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param fileName Name of the gemspec file, in the format &#x60;&lt;gem_name&gt;-&lt;version&gt;.gemspec.rz&#x60;.
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public File getApiV4ProjectsIdPackagesRubygemsQuickMarshal48FileName(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesRubygemsQuickMarshal48FileNameRequestCreation(id, fileName).body(localVarReturnType);
    }

    /**
     * Download a gemspec file
     * Downloads a gemspec file in Marshal format for a specified gem version. The response is a deflate-compressed, marshalled &#x60;Gem::Specification&#x60; object.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param fileName Name of the gemspec file, in the format &#x60;&lt;gem_name&gt;-&lt;version&gt;.gemspec.rz&#x60;.
     * @return ResponseEntity&lt;File&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<File> getApiV4ProjectsIdPackagesRubygemsQuickMarshal48FileNameWithHttpInfo(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesRubygemsQuickMarshal48FileNameRequestCreation(id, fileName).toEntity(localVarReturnType);
    }

    /**
     * Download a gemspec file
     * Downloads a gemspec file in Marshal format for a specified gem version. The response is a deflate-compressed, marshalled &#x60;Gem::Specification&#x60; object.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param fileName Name of the gemspec file, in the format &#x60;&lt;gem_name&gt;-&lt;version&gt;.gemspec.rz&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesRubygemsQuickMarshal48FileNameWithResponseSpec(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull String fileName) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesRubygemsQuickMarshal48FileNameRequestCreation(id, fileName);
    }

    /**
     * Upload a gem
     * Uploads a gem for a specified project.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param _file The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdPackagesRubygemsApiV1GemsRequestCreation(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdPackagesRubygemsApiV1Gems", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new RestClientResponseException("Missing the required parameter '_file' when calling postApiV4ProjectsIdPackagesRubygemsApiV1Gems", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        formParams.add("file", new FileSystemResource(_file));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/rubygems/api/v1/gems", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Upload a gem
     * Uploads a gem for a specified project.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param _file The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ProjectsIdPackagesRubygemsApiV1Gems(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectsIdPackagesRubygemsApiV1GemsRequestCreation(id, _file).body(localVarReturnType);
    }

    /**
     * Upload a gem
     * Uploads a gem for a specified project.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param _file The package file to be published (generated by Multipart middleware)
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ProjectsIdPackagesRubygemsApiV1GemsWithHttpInfo(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdPackagesRubygemsApiV1GemsRequestCreation(id, _file).toEntity(localVarReturnType);
    }

    /**
     * Upload a gem
     * Uploads a gem for a specified project.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param _file The package file to be published (generated by Multipart middleware)
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdPackagesRubygemsApiV1GemsWithResponseSpec(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nullable File _file) throws RestClientResponseException {
        return postApiV4ProjectsIdPackagesRubygemsApiV1GemsRequestCreation(id, _file);
    }

    /**
     * Authorize a gem upload from workhorse
     * This feature was introduced in GitLab 13.9
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdPackagesRubygemsApiV1GemsAuthorizeRequestCreation(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdPackagesRubygemsApiV1GemsAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/rubygems/api/v1/gems/authorize", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Authorize a gem upload from workhorse
     * This feature was introduced in GitLab 13.9
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ProjectsIdPackagesRubygemsApiV1GemsAuthorize(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectsIdPackagesRubygemsApiV1GemsAuthorizeRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Authorize a gem upload from workhorse
     * This feature was introduced in GitLab 13.9
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ProjectsIdPackagesRubygemsApiV1GemsAuthorizeWithHttpInfo(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdPackagesRubygemsApiV1GemsAuthorizeRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Authorize a gem upload from workhorse
     * This feature was introduced in GitLab 13.9
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdPackagesRubygemsApiV1GemsAuthorizeWithResponseSpec(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id) throws RestClientResponseException {
        return postApiV4ProjectsIdPackagesRubygemsApiV1GemsAuthorizeRequestCreation(id);
    }
}
