package ai.gebo.glpi.client.api;

import ai.gebo.glpi.client.invoker.ApiClient;

import ai.gebo.glpi.client.model.APIInformation;
import ai.gebo.glpi.client.model.EntityTransferRecord;
import ai.gebo.glpi.client.model.ReqPost201Response;

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
public class DefaultApi {
    private ApiClient apiClient;

    public DefaultApi() {
        this(new ApiClient());
    }

    public DefaultApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * 
     * Displays the API documentation as a Swagger UI HTML page.
     * <p><b>200</b> - Success
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec docGetRequestCreation(@jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (acceptLanguage != null) {
            headerParams.add("Accept-Language", apiClient.parameterToString(acceptLanguage));
        }

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/doc", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Displays the API documentation as a Swagger UI HTML page.
     * <p><b>200</b> - Success
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void docGet(@jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        docGetRequestCreation(acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * Displays the API documentation as a Swagger UI HTML page.
     * <p><b>200</b> - Success
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> docGetWithHttpInfo(@jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return docGetRequestCreation(acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * Displays the API documentation as a Swagger UI HTML page.
     * <p><b>200</b> - Success
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec docGetWithResponseSpec(@jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return docGetRequestCreation(acceptLanguage);
    }

    /**
     * 
     * Get the OpenAPI JSON schema.
     * <p><b>200</b> - Success
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec docJsonGetRequestCreation(@jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (acceptLanguage != null) {
            headerParams.add("Accept-Language", apiClient.parameterToString(acceptLanguage));
        }

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/doc.json", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Get the OpenAPI JSON schema.
     * <p><b>200</b> - Success
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void docJsonGet(@jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        docJsonGetRequestCreation(acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * Get the OpenAPI JSON schema.
     * <p><b>200</b> - Success
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> docJsonGetWithHttpInfo(@jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return docJsonGetRequestCreation(acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * Get the OpenAPI JSON schema.
     * <p><b>200</b> - Success
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec docJsonGetWithResponseSpec(@jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return docJsonGetRequestCreation(acceptLanguage);
    }

    /**
     * 
     * Displays the general API documentation to get started.
     * <p><b>200</b> - Success
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec gettingStartedGetRequestCreation(@jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (acceptLanguage != null) {
            headerParams.add("Accept-Language", apiClient.parameterToString(acceptLanguage));
        }

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/getting-started", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Displays the general API documentation to get started.
     * <p><b>200</b> - Success
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void gettingStartedGet(@jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        gettingStartedGetRequestCreation(acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * Displays the general API documentation to get started.
     * <p><b>200</b> - Success
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> gettingStartedGetWithHttpInfo(@jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return gettingStartedGetRequestCreation(acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * Displays the general API documentation to get started.
     * <p><b>200</b> - Success
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec gettingStartedGetWithResponseSpec(@jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return gettingStartedGetRequestCreation(acceptLanguage);
    }

    /**
     * 
     * A fallback for when no other endpoint matches the request. A 404 error will be shown.
     * <p><b>200</b> - Never returned
     * <p><b>404</b> - No route found for the requested path
     * <p><b>204</b> - Success (no content)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>500</b> - Internal server error
     * @param req 
     * @param force If \&quot;true\&quot;, the item will be permanently deleted instead of being moved to the trash (if the item supported soft-deletion).
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec reqDeleteRequestCreation(@jakarta.annotation.Nonnull String req, @jakarta.annotation.Nullable Boolean force, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        // verify the required parameter 'req' is set
        if (req == null) {
            throw new RestClientResponseException("Missing the required parameter 'req' when calling reqDelete", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("req", req);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "force", force));

        if (glPIEntity != null) {
            headerParams.add("GLPI-Entity", apiClient.parameterToString(glPIEntity));
        }
        if (glPIProfile != null) {
            headerParams.add("GLPI-Profile", apiClient.parameterToString(glPIProfile));
        }
        if (glPIEntityRecursive != null) {
            headerParams.add("GLPI-Entity-Recursive", apiClient.parameterToString(glPIEntityRecursive));
        }
        if (acceptLanguage != null) {
            headerParams.add("Accept-Language", apiClient.parameterToString(acceptLanguage));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/{req}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * A fallback for when no other endpoint matches the request. A 404 error will be shown.
     * <p><b>200</b> - Never returned
     * <p><b>404</b> - No route found for the requested path
     * <p><b>204</b> - Success (no content)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>500</b> - Internal server error
     * @param req 
     * @param force If \&quot;true\&quot;, the item will be permanently deleted instead of being moved to the trash (if the item supported soft-deletion).
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void reqDelete(@jakarta.annotation.Nonnull String req, @jakarta.annotation.Nullable Boolean force, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        reqDeleteRequestCreation(req, force, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * A fallback for when no other endpoint matches the request. A 404 error will be shown.
     * <p><b>200</b> - Never returned
     * <p><b>404</b> - No route found for the requested path
     * <p><b>204</b> - Success (no content)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>500</b> - Internal server error
     * @param req 
     * @param force If \&quot;true\&quot;, the item will be permanently deleted instead of being moved to the trash (if the item supported soft-deletion).
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> reqDeleteWithHttpInfo(@jakarta.annotation.Nonnull String req, @jakarta.annotation.Nullable Boolean force, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return reqDeleteRequestCreation(req, force, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * A fallback for when no other endpoint matches the request. A 404 error will be shown.
     * <p><b>200</b> - Never returned
     * <p><b>404</b> - No route found for the requested path
     * <p><b>204</b> - Success (no content)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>500</b> - Internal server error
     * @param req 
     * @param force If \&quot;true\&quot;, the item will be permanently deleted instead of being moved to the trash (if the item supported soft-deletion).
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec reqDeleteWithResponseSpec(@jakarta.annotation.Nonnull String req, @jakarta.annotation.Nullable Boolean force, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return reqDeleteRequestCreation(req, force, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage);
    }

    /**
     * 
     * A fallback for when no other endpoint matches the request. A 404 error will be shown.
     * <p><b>200</b> - Never returned
     * <p><b>404</b> - No route found for the requested path
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>500</b> - Internal server error
     * @param req 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec reqGetRequestCreation(@jakarta.annotation.Nonnull String req, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        // verify the required parameter 'req' is set
        if (req == null) {
            throw new RestClientResponseException("Missing the required parameter 'req' when calling reqGet", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("req", req);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (glPIEntity != null) {
            headerParams.add("GLPI-Entity", apiClient.parameterToString(glPIEntity));
        }
        if (glPIProfile != null) {
            headerParams.add("GLPI-Profile", apiClient.parameterToString(glPIProfile));
        }
        if (glPIEntityRecursive != null) {
            headerParams.add("GLPI-Entity-Recursive", apiClient.parameterToString(glPIEntityRecursive));
        }
        if (acceptLanguage != null) {
            headerParams.add("Accept-Language", apiClient.parameterToString(acceptLanguage));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/{req}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * A fallback for when no other endpoint matches the request. A 404 error will be shown.
     * <p><b>200</b> - Never returned
     * <p><b>404</b> - No route found for the requested path
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>500</b> - Internal server error
     * @param req 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void reqGet(@jakarta.annotation.Nonnull String req, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        reqGetRequestCreation(req, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * A fallback for when no other endpoint matches the request. A 404 error will be shown.
     * <p><b>200</b> - Never returned
     * <p><b>404</b> - No route found for the requested path
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>500</b> - Internal server error
     * @param req 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> reqGetWithHttpInfo(@jakarta.annotation.Nonnull String req, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return reqGetRequestCreation(req, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * A fallback for when no other endpoint matches the request. A 404 error will be shown.
     * <p><b>200</b> - Never returned
     * <p><b>404</b> - No route found for the requested path
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>500</b> - Internal server error
     * @param req 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec reqGetWithResponseSpec(@jakarta.annotation.Nonnull String req, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return reqGetRequestCreation(req, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage);
    }

    /**
     * 
     * A global route that enables the OPTIONS method on all endpoints. This responds with an Accept header indicating which methods are allowed.
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param req 
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec reqOptionsRequestCreation(@jakarta.annotation.Nonnull String req, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        // verify the required parameter 'req' is set
        if (req == null) {
            throw new RestClientResponseException("Missing the required parameter 'req' when calling reqOptions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("req", req);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (acceptLanguage != null) {
            headerParams.add("Accept-Language", apiClient.parameterToString(acceptLanguage));
        }

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/{req}", HttpMethod.OPTIONS, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * A global route that enables the OPTIONS method on all endpoints. This responds with an Accept header indicating which methods are allowed.
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param req 
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void reqOptions(@jakarta.annotation.Nonnull String req, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        reqOptionsRequestCreation(req, acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * A global route that enables the OPTIONS method on all endpoints. This responds with an Accept header indicating which methods are allowed.
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param req 
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> reqOptionsWithHttpInfo(@jakarta.annotation.Nonnull String req, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return reqOptionsRequestCreation(req, acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * A global route that enables the OPTIONS method on all endpoints. This responds with an Accept header indicating which methods are allowed.
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param req 
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec reqOptionsWithResponseSpec(@jakarta.annotation.Nonnull String req, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return reqOptionsRequestCreation(req, acceptLanguage);
    }

    /**
     * 
     * A fallback for when no other endpoint matches the request. A 404 error will be shown.
     * <p><b>200</b> - Never returned
     * <p><b>404</b> - No route found for the requested path
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>500</b> - Internal server error
     * @param req 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec reqPatchRequestCreation(@jakarta.annotation.Nonnull String req, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        // verify the required parameter 'req' is set
        if (req == null) {
            throw new RestClientResponseException("Missing the required parameter 'req' when calling reqPatch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("req", req);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (glPIEntity != null) {
            headerParams.add("GLPI-Entity", apiClient.parameterToString(glPIEntity));
        }
        if (glPIProfile != null) {
            headerParams.add("GLPI-Profile", apiClient.parameterToString(glPIProfile));
        }
        if (glPIEntityRecursive != null) {
            headerParams.add("GLPI-Entity-Recursive", apiClient.parameterToString(glPIEntityRecursive));
        }
        if (acceptLanguage != null) {
            headerParams.add("Accept-Language", apiClient.parameterToString(acceptLanguage));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/{req}", HttpMethod.PATCH, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * A fallback for when no other endpoint matches the request. A 404 error will be shown.
     * <p><b>200</b> - Never returned
     * <p><b>404</b> - No route found for the requested path
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>500</b> - Internal server error
     * @param req 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void reqPatch(@jakarta.annotation.Nonnull String req, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        reqPatchRequestCreation(req, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * A fallback for when no other endpoint matches the request. A 404 error will be shown.
     * <p><b>200</b> - Never returned
     * <p><b>404</b> - No route found for the requested path
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>500</b> - Internal server error
     * @param req 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> reqPatchWithHttpInfo(@jakarta.annotation.Nonnull String req, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return reqPatchRequestCreation(req, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * A fallback for when no other endpoint matches the request. A 404 error will be shown.
     * <p><b>200</b> - Never returned
     * <p><b>404</b> - No route found for the requested path
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>500</b> - Internal server error
     * @param req 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec reqPatchWithResponseSpec(@jakarta.annotation.Nonnull String req, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return reqPatchRequestCreation(req, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage);
    }

    /**
     * 
     * A fallback for when no other endpoint matches the request. A 404 error will be shown.
     * <p><b>200</b> - Never returned
     * <p><b>404</b> - No route found for the requested path
     * <p><b>201</b> - Success (created)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>500</b> - Internal server error
     * @param req 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec reqPostRequestCreation(@jakarta.annotation.Nonnull String req, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        // verify the required parameter 'req' is set
        if (req == null) {
            throw new RestClientResponseException("Missing the required parameter 'req' when calling reqPost", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("req", req);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (glPIEntity != null) {
            headerParams.add("GLPI-Entity", apiClient.parameterToString(glPIEntity));
        }
        if (glPIProfile != null) {
            headerParams.add("GLPI-Profile", apiClient.parameterToString(glPIProfile));
        }
        if (glPIEntityRecursive != null) {
            headerParams.add("GLPI-Entity-Recursive", apiClient.parameterToString(glPIEntityRecursive));
        }
        if (acceptLanguage != null) {
            headerParams.add("Accept-Language", apiClient.parameterToString(acceptLanguage));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/{req}", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * A fallback for when no other endpoint matches the request. A 404 error will be shown.
     * <p><b>200</b> - Never returned
     * <p><b>404</b> - No route found for the requested path
     * <p><b>201</b> - Success (created)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>500</b> - Internal server error
     * @param req 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void reqPost(@jakarta.annotation.Nonnull String req, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        reqPostRequestCreation(req, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * A fallback for when no other endpoint matches the request. A 404 error will be shown.
     * <p><b>200</b> - Never returned
     * <p><b>404</b> - No route found for the requested path
     * <p><b>201</b> - Success (created)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>500</b> - Internal server error
     * @param req 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> reqPostWithHttpInfo(@jakarta.annotation.Nonnull String req, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return reqPostRequestCreation(req, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * A fallback for when no other endpoint matches the request. A 404 error will be shown.
     * <p><b>200</b> - Never returned
     * <p><b>404</b> - No route found for the requested path
     * <p><b>201</b> - Success (created)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>500</b> - Internal server error
     * @param req 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec reqPostWithResponseSpec(@jakarta.annotation.Nonnull String req, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return reqPostRequestCreation(req, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage);
    }

    /**
     * 
     * A fallback for when no other endpoint matches the request. A 404 error will be shown.
     * <p><b>200</b> - Never returned
     * <p><b>404</b> - No route found for the requested path
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>500</b> - Internal server error
     * @param req 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec reqPutRequestCreation(@jakarta.annotation.Nonnull String req, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        // verify the required parameter 'req' is set
        if (req == null) {
            throw new RestClientResponseException("Missing the required parameter 'req' when calling reqPut", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("req", req);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (glPIEntity != null) {
            headerParams.add("GLPI-Entity", apiClient.parameterToString(glPIEntity));
        }
        if (glPIProfile != null) {
            headerParams.add("GLPI-Profile", apiClient.parameterToString(glPIProfile));
        }
        if (glPIEntityRecursive != null) {
            headerParams.add("GLPI-Entity-Recursive", apiClient.parameterToString(glPIEntityRecursive));
        }
        if (acceptLanguage != null) {
            headerParams.add("Accept-Language", apiClient.parameterToString(acceptLanguage));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/{req}", HttpMethod.PUT, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * A fallback for when no other endpoint matches the request. A 404 error will be shown.
     * <p><b>200</b> - Never returned
     * <p><b>404</b> - No route found for the requested path
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>500</b> - Internal server error
     * @param req 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void reqPut(@jakarta.annotation.Nonnull String req, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        reqPutRequestCreation(req, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * A fallback for when no other endpoint matches the request. A 404 error will be shown.
     * <p><b>200</b> - Never returned
     * <p><b>404</b> - No route found for the requested path
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>500</b> - Internal server error
     * @param req 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> reqPutWithHttpInfo(@jakarta.annotation.Nonnull String req, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return reqPutRequestCreation(req, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * A fallback for when no other endpoint matches the request. A 404 error will be shown.
     * <p><b>200</b> - Never returned
     * <p><b>404</b> - No route found for the requested path
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>500</b> - Internal server error
     * @param req 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec reqPutWithResponseSpec(@jakarta.annotation.Nonnull String req, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return reqPutRequestCreation(req, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage);
    }

    /**
     * 
     * API Homepage. Displays the available API versions and a list of available routes. When logged in, more routes are displayed.
     * <p><b>200</b> - API information
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return APIInformation
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec rootGetRequestCreation(@jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (acceptLanguage != null) {
            headerParams.add("Accept-Language", apiClient.parameterToString(acceptLanguage));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<APIInformation> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * API Homepage. Displays the available API versions and a list of available routes. When logged in, more routes are displayed.
     * <p><b>200</b> - API information
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return APIInformation
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIInformation rootGet(@jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<APIInformation> localVarReturnType = new ParameterizedTypeReference<>() {};
        return rootGetRequestCreation(acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * API Homepage. Displays the available API versions and a list of available routes. When logged in, more routes are displayed.
     * <p><b>200</b> - API information
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseEntity&lt;APIInformation&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIInformation> rootGetWithHttpInfo(@jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<APIInformation> localVarReturnType = new ParameterizedTypeReference<>() {};
        return rootGetRequestCreation(acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * API Homepage. Displays the available API versions and a list of available routes. When logged in, more routes are displayed.
     * <p><b>200</b> - API information
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec rootGetWithResponseSpec(@jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return rootGetRequestCreation(acceptLanguage);
    }

    /**
     * 
     * Transfer one or more items to another entity
     * <p><b>201</b> - Success (created)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @param entityTransferRecord The entityTransferRecord parameter
     * @return ReqPost201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec transferPostRequestCreation(@jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable List<EntityTransferRecord> entityTransferRecord) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (glPIEntity != null) {
            headerParams.add("GLPI-Entity", apiClient.parameterToString(glPIEntity));
        }
        if (glPIProfile != null) {
            headerParams.add("GLPI-Profile", apiClient.parameterToString(glPIProfile));
        }
        if (glPIEntityRecursive != null) {
            headerParams.add("GLPI-Entity-Recursive", apiClient.parameterToString(glPIEntityRecursive));
        }
        if (acceptLanguage != null) {
            headerParams.add("Accept-Language", apiClient.parameterToString(acceptLanguage));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<ReqPost201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Transfer", HttpMethod.POST, pathParams, localVarQueryParams, entityTransferRecord, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Transfer one or more items to another entity
     * <p><b>201</b> - Success (created)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @param entityTransferRecord The entityTransferRecord parameter
     * @return ReqPost201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ReqPost201Response transferPost(@jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable List<EntityTransferRecord> entityTransferRecord) throws RestClientResponseException {
        ParameterizedTypeReference<ReqPost201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return transferPostRequestCreation(glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, entityTransferRecord).body(localVarReturnType);
    }

    /**
     * 
     * Transfer one or more items to another entity
     * <p><b>201</b> - Success (created)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @param entityTransferRecord The entityTransferRecord parameter
     * @return ResponseEntity&lt;ReqPost201Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ReqPost201Response> transferPostWithHttpInfo(@jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable List<EntityTransferRecord> entityTransferRecord) throws RestClientResponseException {
        ParameterizedTypeReference<ReqPost201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return transferPostRequestCreation(glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, entityTransferRecord).toEntity(localVarReturnType);
    }

    /**
     * 
     * Transfer one or more items to another entity
     * <p><b>201</b> - Success (created)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @param entityTransferRecord The entityTransferRecord parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec transferPostWithResponseSpec(@jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable List<EntityTransferRecord> entityTransferRecord) throws RestClientResponseException {
        return transferPostRequestCreation(glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, entityTransferRecord);
    }
}
