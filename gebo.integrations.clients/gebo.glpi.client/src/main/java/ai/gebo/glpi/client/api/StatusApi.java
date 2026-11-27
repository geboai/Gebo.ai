package ai.gebo.glpi.client.api;

import ai.gebo.glpi.client.invoker.ApiClient;

import ai.gebo.glpi.client.model.StatusAllGet200ResponseInner;

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
public class StatusApi {
    private ApiClient apiClient;

    public StatusApi() {
        this(new ApiClient());
    }

    public StatusApi(ApiClient apiClient) {
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
     * Get the the status of all GLPI system status checker services
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return List&lt;StatusAllGet200ResponseInner&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec statusAllGetRequestCreation(@jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
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

        ParameterizedTypeReference<List<StatusAllGet200ResponseInner>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/status/all", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Get the the status of all GLPI system status checker services
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return List&lt;StatusAllGet200ResponseInner&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<StatusAllGet200ResponseInner> statusAllGet(@jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<List<StatusAllGet200ResponseInner>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return statusAllGetRequestCreation(acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * Get the the status of all GLPI system status checker services
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseEntity&lt;List&lt;StatusAllGet200ResponseInner&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<StatusAllGet200ResponseInner>> statusAllGetWithHttpInfo(@jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<List<StatusAllGet200ResponseInner>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return statusAllGetRequestCreation(acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * Get the the status of all GLPI system status checker services
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec statusAllGetWithResponseSpec(@jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return statusAllGetRequestCreation(acceptLanguage);
    }

    /**
     * 
     * Get a list of all GLPI system status checker services.
     * <p><b>200</b> - Success
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec statusGetRequestCreation(@jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
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

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/status", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Get a list of all GLPI system status checker services.
     * <p><b>200</b> - Success
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void statusGet(@jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        statusGetRequestCreation(glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * Get a list of all GLPI system status checker services.
     * <p><b>200</b> - Success
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> statusGetWithHttpInfo(@jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return statusGetRequestCreation(glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * Get a list of all GLPI system status checker services.
     * <p><b>200</b> - Success
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec statusGetWithResponseSpec(@jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return statusGetRequestCreation(glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage);
    }

    /**
     * 
     * Get the status of a GLPI system status checker service. Use \&quot;all\&quot; as the service to get the full system status.
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param service 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return StatusAllGet200ResponseInner
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec statusServiceGetRequestCreation(@jakarta.annotation.Nonnull String service, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        // verify the required parameter 'service' is set
        if (service == null) {
            throw new RestClientResponseException("Missing the required parameter 'service' when calling statusServiceGet", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("service", service);

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

        ParameterizedTypeReference<StatusAllGet200ResponseInner> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/status/{service}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Get the status of a GLPI system status checker service. Use \&quot;all\&quot; as the service to get the full system status.
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param service 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return StatusAllGet200ResponseInner
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public StatusAllGet200ResponseInner statusServiceGet(@jakarta.annotation.Nonnull String service, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<StatusAllGet200ResponseInner> localVarReturnType = new ParameterizedTypeReference<>() {};
        return statusServiceGetRequestCreation(service, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * Get the status of a GLPI system status checker service. Use \&quot;all\&quot; as the service to get the full system status.
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param service 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseEntity&lt;StatusAllGet200ResponseInner&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<StatusAllGet200ResponseInner> statusServiceGetWithHttpInfo(@jakarta.annotation.Nonnull String service, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<StatusAllGet200ResponseInner> localVarReturnType = new ParameterizedTypeReference<>() {};
        return statusServiceGetRequestCreation(service, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * Get the status of a GLPI system status checker service. Use \&quot;all\&quot; as the service to get the full system status.
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param service 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec statusServiceGetWithResponseSpec(@jakarta.annotation.Nonnull String service, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return statusServiceGetRequestCreation(service, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage);
    }
}
