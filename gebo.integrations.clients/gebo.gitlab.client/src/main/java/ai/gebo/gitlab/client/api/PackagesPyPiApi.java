package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import java.io.File;
import ai.gebo.gitlab.client.model.GetApiV4DependencyListExportsExportIdExportIdParameter;
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
public class PackagesPyPiApi {
    private ApiClient apiClient;

    public PackagesPyPiApi() {
        this(new ApiClient());
    }

    public PackagesPyPiApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Download a package file from a group
     * Downloads a specified PyPI package file for a group. The package descriptor endpoint usually supplies this URL. This feature was introduced in GitLab 13.12
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param fileIdentifier The PyPi package file identifier
     * @param sha256 The PyPi package sha256 check sum
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdDashPackagesPypiFilesSha256FileIdentifierRequestCreation(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull String fileIdentifier, @jakarta.annotation.Nonnull String sha256) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdDashPackagesPypiFilesSha256FileIdentifier", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileIdentifier' is set
        if (fileIdentifier == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileIdentifier' when calling getApiV4GroupsIdDashPackagesPypiFilesSha256FileIdentifier", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'sha256' is set
        if (sha256 == null) {
            throw new RestClientResponseException("Missing the required parameter 'sha256' when calling getApiV4GroupsIdDashPackagesPypiFilesSha256FileIdentifier", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("file_identifier", fileIdentifier);
        pathParams.put("sha256", sha256);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/-/packages/pypi/files/{sha256}/{file_identifier}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Download a package file from a group
     * Downloads a specified PyPI package file for a group. The package descriptor endpoint usually supplies this URL. This feature was introduced in GitLab 13.12
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param fileIdentifier The PyPi package file identifier
     * @param sha256 The PyPi package sha256 check sum
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4GroupsIdDashPackagesPypiFilesSha256FileIdentifier(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull String fileIdentifier, @jakarta.annotation.Nonnull String sha256) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4GroupsIdDashPackagesPypiFilesSha256FileIdentifierRequestCreation(id, fileIdentifier, sha256).body(localVarReturnType);
    }

    /**
     * Download a package file from a group
     * Downloads a specified PyPI package file for a group. The package descriptor endpoint usually supplies this URL. This feature was introduced in GitLab 13.12
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param fileIdentifier The PyPi package file identifier
     * @param sha256 The PyPi package sha256 check sum
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4GroupsIdDashPackagesPypiFilesSha256FileIdentifierWithHttpInfo(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull String fileIdentifier, @jakarta.annotation.Nonnull String sha256) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashPackagesPypiFilesSha256FileIdentifierRequestCreation(id, fileIdentifier, sha256).toEntity(localVarReturnType);
    }

    /**
     * Download a package file from a group
     * Downloads a specified PyPI package file for a group. The package descriptor endpoint usually supplies this URL. This feature was introduced in GitLab 13.12
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param fileIdentifier The PyPi package file identifier
     * @param sha256 The PyPi package sha256 check sum
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdDashPackagesPypiFilesSha256FileIdentifierWithResponseSpec(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull String fileIdentifier, @jakarta.annotation.Nonnull String sha256) throws RestClientResponseException {
        return getApiV4GroupsIdDashPackagesPypiFilesSha256FileIdentifierRequestCreation(id, fileIdentifier, sha256);
    }

    /**
     * List all packages for a group
     * Lists all packages for a specified group in an HTML file.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdDashPackagesPypiSimpleRequestCreation(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdDashPackagesPypiSimple", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/groups/{id}/-/packages/pypi/simple", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all packages for a group
     * Lists all packages for a specified group in an HTML file.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4GroupsIdDashPackagesPypiSimple(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4GroupsIdDashPackagesPypiSimpleRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List all packages for a group
     * Lists all packages for a specified group in an HTML file.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4GroupsIdDashPackagesPypiSimpleWithHttpInfo(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashPackagesPypiSimpleRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List all packages for a group
     * Lists all packages for a specified group in an HTML file.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdDashPackagesPypiSimpleWithResponseSpec(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id) throws RestClientResponseException {
        return getApiV4GroupsIdDashPackagesPypiSimpleRequestCreation(id);
    }

    /**
     * The PyPi Simple Group Package Endpoint
     * Retrieves the package descriptor as an HTML file for a specified package in a group. The HTML lists a link to each package file, with its SHA256 checksum and its &#x60;data-requires-python&#x60; value. This feature was introduced in GitLab 12.10
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param packageName The PyPi package name
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4GroupsIdDashPackagesPypiSimplePackageNameRequestCreation(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4GroupsIdDashPackagesPypiSimplePackageName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4GroupsIdDashPackagesPypiSimplePackageName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);

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
        return apiClient.invokeAPI("/api/v4/groups/{id}/-/packages/pypi/simple/{package_name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The PyPi Simple Group Package Endpoint
     * Retrieves the package descriptor as an HTML file for a specified package in a group. The HTML lists a link to each package file, with its SHA256 checksum and its &#x60;data-requires-python&#x60; value. This feature was introduced in GitLab 12.10
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param packageName The PyPi package name
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4GroupsIdDashPackagesPypiSimplePackageName(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4GroupsIdDashPackagesPypiSimplePackageNameRequestCreation(id, packageName).body(localVarReturnType);
    }

    /**
     * The PyPi Simple Group Package Endpoint
     * Retrieves the package descriptor as an HTML file for a specified package in a group. The HTML lists a link to each package file, with its SHA256 checksum and its &#x60;data-requires-python&#x60; value. This feature was introduced in GitLab 12.10
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param packageName The PyPi package name
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4GroupsIdDashPackagesPypiSimplePackageNameWithHttpInfo(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4GroupsIdDashPackagesPypiSimplePackageNameRequestCreation(id, packageName).toEntity(localVarReturnType);
    }

    /**
     * The PyPi Simple Group Package Endpoint
     * Retrieves the package descriptor as an HTML file for a specified package in a group. The HTML lists a link to each package file, with its SHA256 checksum and its &#x60;data-requires-python&#x60; value. This feature was introduced in GitLab 12.10
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the group.
     * @param packageName The PyPi package name
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4GroupsIdDashPackagesPypiSimplePackageNameWithResponseSpec(@jakarta.annotation.Nonnull GetApiV4DependencyListExportsExportIdExportIdParameter id, @jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        return getApiV4GroupsIdDashPackagesPypiSimplePackageNameRequestCreation(id, packageName);
    }

    /**
     * The PyPi package download endpoint
     * Downloads a specified PyPI package file for a project. The package descriptor endpoint usually supplies this URL. This feature was introduced in GitLab 12.10
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param fileIdentifier The PyPi package file identifier
     * @param sha256 The PyPi package sha256 check sum
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesPypiFilesSha256FileIdentifierRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileIdentifier, @jakarta.annotation.Nonnull String sha256) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesPypiFilesSha256FileIdentifier", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'fileIdentifier' is set
        if (fileIdentifier == null) {
            throw new RestClientResponseException("Missing the required parameter 'fileIdentifier' when calling getApiV4ProjectsIdPackagesPypiFilesSha256FileIdentifier", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'sha256' is set
        if (sha256 == null) {
            throw new RestClientResponseException("Missing the required parameter 'sha256' when calling getApiV4ProjectsIdPackagesPypiFilesSha256FileIdentifier", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("file_identifier", fileIdentifier);
        pathParams.put("sha256", sha256);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/pypi/files/{sha256}/{file_identifier}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The PyPi package download endpoint
     * Downloads a specified PyPI package file for a project. The package descriptor endpoint usually supplies this URL. This feature was introduced in GitLab 12.10
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param fileIdentifier The PyPi package file identifier
     * @param sha256 The PyPi package sha256 check sum
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesPypiFilesSha256FileIdentifier(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileIdentifier, @jakarta.annotation.Nonnull String sha256) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesPypiFilesSha256FileIdentifierRequestCreation(id, fileIdentifier, sha256).body(localVarReturnType);
    }

    /**
     * The PyPi package download endpoint
     * Downloads a specified PyPI package file for a project. The package descriptor endpoint usually supplies this URL. This feature was introduced in GitLab 12.10
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param fileIdentifier The PyPi package file identifier
     * @param sha256 The PyPi package sha256 check sum
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesPypiFilesSha256FileIdentifierWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileIdentifier, @jakarta.annotation.Nonnull String sha256) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesPypiFilesSha256FileIdentifierRequestCreation(id, fileIdentifier, sha256).toEntity(localVarReturnType);
    }

    /**
     * The PyPi package download endpoint
     * Downloads a specified PyPI package file for a project. The package descriptor endpoint usually supplies this URL. This feature was introduced in GitLab 12.10
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param fileIdentifier The PyPi package file identifier
     * @param sha256 The PyPi package sha256 check sum
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesPypiFilesSha256FileIdentifierWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String fileIdentifier, @jakarta.annotation.Nonnull String sha256) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesPypiFilesSha256FileIdentifierRequestCreation(id, fileIdentifier, sha256);
    }

    /**
     * List all packages for a project
     * Lists all packages for a specified project in an HTML file.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesPypiSimpleRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesPypiSimple", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/pypi/simple", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all packages for a project
     * Lists all packages for a specified project in an HTML file.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesPypiSimple(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesPypiSimpleRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List all packages for a project
     * Lists all packages for a specified project in an HTML file.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesPypiSimpleWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesPypiSimpleRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List all packages for a project
     * Lists all packages for a specified project in an HTML file.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesPypiSimpleWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesPypiSimpleRequestCreation(id);
    }

    /**
     * The PyPi Simple Project Package Endpoint
     * Retrieves the package descriptor as an HTML file for a specified package in a project. The HTML lists a link to each package file, with its SHA256 checksum and its &#x60;data-requires-python&#x60; value. This feature was introduced in GitLab 12.10
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param packageName The PyPi package name
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4ProjectsIdPackagesPypiSimplePackageNameRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getApiV4ProjectsIdPackagesPypiSimplePackageName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'packageName' is set
        if (packageName == null) {
            throw new RestClientResponseException("Missing the required parameter 'packageName' when calling getApiV4ProjectsIdPackagesPypiSimplePackageName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);
        pathParams.put("package_name", packageName);

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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/pypi/simple/{package_name}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * The PyPi Simple Project Package Endpoint
     * Retrieves the package descriptor as an HTML file for a specified package in a project. The HTML lists a link to each package file, with its SHA256 checksum and its &#x60;data-requires-python&#x60; value. This feature was introduced in GitLab 12.10
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param packageName The PyPi package name
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void getApiV4ProjectsIdPackagesPypiSimplePackageName(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        getApiV4ProjectsIdPackagesPypiSimplePackageNameRequestCreation(id, packageName).body(localVarReturnType);
    }

    /**
     * The PyPi Simple Project Package Endpoint
     * Retrieves the package descriptor as an HTML file for a specified package in a project. The HTML lists a link to each package file, with its SHA256 checksum and its &#x60;data-requires-python&#x60; value. This feature was introduced in GitLab 12.10
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param packageName The PyPi package name
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> getApiV4ProjectsIdPackagesPypiSimplePackageNameWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4ProjectsIdPackagesPypiSimplePackageNameRequestCreation(id, packageName).toEntity(localVarReturnType);
    }

    /**
     * The PyPi Simple Project Package Endpoint
     * Retrieves the package descriptor as an HTML file for a specified package in a project. The HTML lists a link to each package file, with its SHA256 checksum and its &#x60;data-requires-python&#x60; value. This feature was introduced in GitLab 12.10
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @param packageName The PyPi package name
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4ProjectsIdPackagesPypiSimplePackageNameWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nonnull String packageName) throws RestClientResponseException {
        return getApiV4ProjectsIdPackagesPypiSimplePackageNameRequestCreation(id, packageName);
    }

    /**
     * Upload a package
     * Uploads a PyPI package for a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>422</b> - Unprocessable Entity
     * @param id ID or URL-encoded path of the project.
     * @param content The package file to be published (generated by Multipart middleware)
     * @param name Name of the package
     * @param requiresPython PyPI version required for the package
     * @param md5Digest MD5 checksum of the package
     * @param sha256Digest SHA256 checksum of the package. Not required for uploads, but without it, &#x60;pip install&#x60; fails because package index URLs lack required checksums.
     * @param metadataVersion Metadata version of the package
     * @param authorEmail Email address for the package author
     * @param description Description of the package
     * @param descriptionContentType Type of content for the package description
     * @param summary Short summary of the package
     * @param keywords Keywords listed for the package
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdPackagesPypiRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable File content, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String requiresPython, @jakarta.annotation.Nullable String md5Digest, @jakarta.annotation.Nullable String sha256Digest, @jakarta.annotation.Nullable String metadataVersion, @jakarta.annotation.Nullable String authorEmail, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable String descriptionContentType, @jakarta.annotation.Nullable String summary, @jakarta.annotation.Nullable String keywords) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdPackagesPypi", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'content' is set
        if (content == null) {
            throw new RestClientResponseException("Missing the required parameter 'content' when calling postApiV4ProjectsIdPackagesPypi", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling postApiV4ProjectsIdPackagesPypi", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        formParams.add("content", new FileSystemResource(content));
        formParams.add("name", name);
        if (requiresPython != null) {
            formParams.add("requires_python", requiresPython);
        }
        if (md5Digest != null) {
            formParams.add("md5_digest", md5Digest);
        }
        if (sha256Digest != null) {
            formParams.add("sha256_digest", sha256Digest);
        }
        if (metadataVersion != null) {
            formParams.add("metadata_version", metadataVersion);
        }
        if (authorEmail != null) {
            formParams.add("author_email", authorEmail);
        }
        if (description != null) {
            formParams.add("description", description);
        }
        if (descriptionContentType != null) {
            formParams.add("description_content_type", descriptionContentType);
        }
        if (summary != null) {
            formParams.add("summary", summary);
        }
        if (keywords != null) {
            formParams.add("keywords", keywords);
        }

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/pypi", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Upload a package
     * Uploads a PyPI package for a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>422</b> - Unprocessable Entity
     * @param id ID or URL-encoded path of the project.
     * @param content The package file to be published (generated by Multipart middleware)
     * @param name Name of the package
     * @param requiresPython PyPI version required for the package
     * @param md5Digest MD5 checksum of the package
     * @param sha256Digest SHA256 checksum of the package. Not required for uploads, but without it, &#x60;pip install&#x60; fails because package index URLs lack required checksums.
     * @param metadataVersion Metadata version of the package
     * @param authorEmail Email address for the package author
     * @param description Description of the package
     * @param descriptionContentType Type of content for the package description
     * @param summary Short summary of the package
     * @param keywords Keywords listed for the package
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ProjectsIdPackagesPypi(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable File content, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String requiresPython, @jakarta.annotation.Nullable String md5Digest, @jakarta.annotation.Nullable String sha256Digest, @jakarta.annotation.Nullable String metadataVersion, @jakarta.annotation.Nullable String authorEmail, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable String descriptionContentType, @jakarta.annotation.Nullable String summary, @jakarta.annotation.Nullable String keywords) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectsIdPackagesPypiRequestCreation(id, content, name, requiresPython, md5Digest, sha256Digest, metadataVersion, authorEmail, description, descriptionContentType, summary, keywords).body(localVarReturnType);
    }

    /**
     * Upload a package
     * Uploads a PyPI package for a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>422</b> - Unprocessable Entity
     * @param id ID or URL-encoded path of the project.
     * @param content The package file to be published (generated by Multipart middleware)
     * @param name Name of the package
     * @param requiresPython PyPI version required for the package
     * @param md5Digest MD5 checksum of the package
     * @param sha256Digest SHA256 checksum of the package. Not required for uploads, but without it, &#x60;pip install&#x60; fails because package index URLs lack required checksums.
     * @param metadataVersion Metadata version of the package
     * @param authorEmail Email address for the package author
     * @param description Description of the package
     * @param descriptionContentType Type of content for the package description
     * @param summary Short summary of the package
     * @param keywords Keywords listed for the package
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ProjectsIdPackagesPypiWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable File content, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String requiresPython, @jakarta.annotation.Nullable String md5Digest, @jakarta.annotation.Nullable String sha256Digest, @jakarta.annotation.Nullable String metadataVersion, @jakarta.annotation.Nullable String authorEmail, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable String descriptionContentType, @jakarta.annotation.Nullable String summary, @jakarta.annotation.Nullable String keywords) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdPackagesPypiRequestCreation(id, content, name, requiresPython, md5Digest, sha256Digest, metadataVersion, authorEmail, description, descriptionContentType, summary, keywords).toEntity(localVarReturnType);
    }

    /**
     * Upload a package
     * Uploads a PyPI package for a specified project.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>422</b> - Unprocessable Entity
     * @param id ID or URL-encoded path of the project.
     * @param content The package file to be published (generated by Multipart middleware)
     * @param name Name of the package
     * @param requiresPython PyPI version required for the package
     * @param md5Digest MD5 checksum of the package
     * @param sha256Digest SHA256 checksum of the package. Not required for uploads, but without it, &#x60;pip install&#x60; fails because package index URLs lack required checksums.
     * @param metadataVersion Metadata version of the package
     * @param authorEmail Email address for the package author
     * @param description Description of the package
     * @param descriptionContentType Type of content for the package description
     * @param summary Short summary of the package
     * @param keywords Keywords listed for the package
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdPackagesPypiWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id, @jakarta.annotation.Nullable File content, @jakarta.annotation.Nullable String name, @jakarta.annotation.Nullable String requiresPython, @jakarta.annotation.Nullable String md5Digest, @jakarta.annotation.Nullable String sha256Digest, @jakarta.annotation.Nullable String metadataVersion, @jakarta.annotation.Nullable String authorEmail, @jakarta.annotation.Nullable String description, @jakarta.annotation.Nullable String descriptionContentType, @jakarta.annotation.Nullable String summary, @jakarta.annotation.Nullable String keywords) throws RestClientResponseException {
        return postApiV4ProjectsIdPackagesPypiRequestCreation(id, content, name, requiresPython, md5Digest, sha256Digest, metadataVersion, authorEmail, description, descriptionContentType, summary, keywords);
    }

    /**
     * Authorize the PyPi package upload from workhorse
     * This feature was introduced in GitLab 12.10
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4ProjectsIdPackagesPypiAuthorizeRequestCreation(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling postApiV4ProjectsIdPackagesPypiAuthorize", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/api/v4/projects/{id}/packages/pypi/authorize", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Authorize the PyPi package upload from workhorse
     * This feature was introduced in GitLab 12.10
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void postApiV4ProjectsIdPackagesPypiAuthorize(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        postApiV4ProjectsIdPackagesPypiAuthorizeRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Authorize the PyPi package upload from workhorse
     * This feature was introduced in GitLab 12.10
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postApiV4ProjectsIdPackagesPypiAuthorizeWithHttpInfo(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4ProjectsIdPackagesPypiAuthorizeRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Authorize the PyPi package upload from workhorse
     * This feature was introduced in GitLab 12.10
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID or URL-encoded path of the project.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4ProjectsIdPackagesPypiAuthorizeWithResponseSpec(@jakarta.annotation.Nonnull PatchApiV4ProjectsIdComplianceExternalControlsControlIdStatusIdParameter id) throws RestClientResponseException {
        return postApiV4ProjectsIdPackagesPypiAuthorizeRequestCreation(id);
    }
}
