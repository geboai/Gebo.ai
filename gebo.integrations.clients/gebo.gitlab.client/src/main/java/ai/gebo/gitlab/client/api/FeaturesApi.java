package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesFeature;
import ai.gebo.gitlab.client.model.APIEntitiesFeatureDefinition;
import ai.gebo.gitlab.client.model.PostApiV4FeaturesNameRequest;

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
public class FeaturesApi {
    private ApiClient apiClient;

    public FeaturesApi() {
        this(new ApiClient());
    }

    public FeaturesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a feature
     * Deletes a feature gate. Returns the same response if the feature gate does not exist.
     * <p><b>204</b> - Resource deleted
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param name Name of the feature flag.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4FeaturesNameRequestCreation(@jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling deleteApiV4FeaturesName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("name", name);

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
        return apiClient.invokeAPI("/api/v4/features/{name}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a feature
     * Deletes a feature gate. Returns the same response if the feature gate does not exist.
     * <p><b>204</b> - Resource deleted
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param name Name of the feature flag.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void deleteApiV4FeaturesName(@jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        deleteApiV4FeaturesNameRequestCreation(name).body(localVarReturnType);
    }

    /**
     * Delete a feature
     * Deletes a feature gate. Returns the same response if the feature gate does not exist.
     * <p><b>204</b> - Resource deleted
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param name Name of the feature flag.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteApiV4FeaturesNameWithHttpInfo(@jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4FeaturesNameRequestCreation(name).toEntity(localVarReturnType);
    }

    /**
     * Delete a feature
     * Deletes a feature gate. Returns the same response if the feature gate does not exist.
     * <p><b>204</b> - Resource deleted
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param name Name of the feature flag.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4FeaturesNameWithResponseSpec(@jakarta.annotation.Nonnull String name) throws RestClientResponseException {
        return deleteApiV4FeaturesNameRequestCreation(name);
    }

    /**
     * List all feature flags
     * Lists all persisted feature flags for the instance, with their gate values.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return List&lt;APIEntitiesFeature&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4FeaturesRequestCreation() throws RestClientResponseException {
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

        ParameterizedTypeReference<List<APIEntitiesFeature>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/features", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all feature flags
     * Lists all persisted feature flags for the instance, with their gate values.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return List&lt;APIEntitiesFeature&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesFeature> getApiV4Features() throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesFeature>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4FeaturesRequestCreation().body(localVarReturnType);
    }

    /**
     * List all feature flags
     * Lists all persisted feature flags for the instance, with their gate values.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return ResponseEntity&lt;List&lt;APIEntitiesFeature&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesFeature>> getApiV4FeaturesWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesFeature>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4FeaturesRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * List all feature flags
     * Lists all persisted feature flags for the instance, with their gate values.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4FeaturesWithResponseSpec() throws RestClientResponseException {
        return getApiV4FeaturesRequestCreation();
    }

    /**
     * List all feature flag definitions
     * Lists all feature flag definitions.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return List&lt;APIEntitiesFeatureDefinition&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4FeaturesDefinitionsRequestCreation() throws RestClientResponseException {
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

        ParameterizedTypeReference<List<APIEntitiesFeatureDefinition>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/features/definitions", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List all feature flag definitions
     * Lists all feature flag definitions.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return List&lt;APIEntitiesFeatureDefinition&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesFeatureDefinition> getApiV4FeaturesDefinitions() throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesFeatureDefinition>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4FeaturesDefinitionsRequestCreation().body(localVarReturnType);
    }

    /**
     * List all feature flag definitions
     * Lists all feature flag definitions.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return ResponseEntity&lt;List&lt;APIEntitiesFeatureDefinition&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesFeatureDefinition>> getApiV4FeaturesDefinitionsWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesFeatureDefinition>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4FeaturesDefinitionsRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * List all feature flag definitions
     * Lists all feature flag definitions.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4FeaturesDefinitionsWithResponseSpec() throws RestClientResponseException {
        return getApiV4FeaturesDefinitionsRequestCreation();
    }

    /**
     * Create or update a feature flag
     * Creates or updates a feature flag value. If a feature with the given name doesn&#39;t exist yet, the operation creates one. The value can be a boolean or an integer to indicate percentage of time. A single call can enable or disable a feature for a &#x60;feature_group&#x60;, a &#x60;user&#x60;, a &#x60;group&#x60;, a &#x60;namespace&#x60;, a &#x60;project&#x60;, an &#x60;organization&#x60;, a &#x60;repository&#x60;, and a &#x60;runner&#x60;. Enabling a feature that is still in development has security and stability risks.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param name Name of the feature flag.
     * @param postApiV4FeaturesNameRequest The postApiV4FeaturesNameRequest parameter
     * @return APIEntitiesFeature
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4FeaturesNameRequestCreation(@jakarta.annotation.Nonnull String name, @jakarta.annotation.Nonnull PostApiV4FeaturesNameRequest postApiV4FeaturesNameRequest) throws RestClientResponseException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new RestClientResponseException("Missing the required parameter 'name' when calling postApiV4FeaturesName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'postApiV4FeaturesNameRequest' is set
        if (postApiV4FeaturesNameRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4FeaturesNameRequest' when calling postApiV4FeaturesName", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesFeature> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/features/{name}", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4FeaturesNameRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create or update a feature flag
     * Creates or updates a feature flag value. If a feature with the given name doesn&#39;t exist yet, the operation creates one. The value can be a boolean or an integer to indicate percentage of time. A single call can enable or disable a feature for a &#x60;feature_group&#x60;, a &#x60;user&#x60;, a &#x60;group&#x60;, a &#x60;namespace&#x60;, a &#x60;project&#x60;, an &#x60;organization&#x60;, a &#x60;repository&#x60;, and a &#x60;runner&#x60;. Enabling a feature that is still in development has security and stability risks.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param name Name of the feature flag.
     * @param postApiV4FeaturesNameRequest The postApiV4FeaturesNameRequest parameter
     * @return APIEntitiesFeature
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesFeature postApiV4FeaturesName(@jakarta.annotation.Nonnull String name, @jakarta.annotation.Nonnull PostApiV4FeaturesNameRequest postApiV4FeaturesNameRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesFeature> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4FeaturesNameRequestCreation(name, postApiV4FeaturesNameRequest).body(localVarReturnType);
    }

    /**
     * Create or update a feature flag
     * Creates or updates a feature flag value. If a feature with the given name doesn&#39;t exist yet, the operation creates one. The value can be a boolean or an integer to indicate percentage of time. A single call can enable or disable a feature for a &#x60;feature_group&#x60;, a &#x60;user&#x60;, a &#x60;group&#x60;, a &#x60;namespace&#x60;, a &#x60;project&#x60;, an &#x60;organization&#x60;, a &#x60;repository&#x60;, and a &#x60;runner&#x60;. Enabling a feature that is still in development has security and stability risks.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param name Name of the feature flag.
     * @param postApiV4FeaturesNameRequest The postApiV4FeaturesNameRequest parameter
     * @return ResponseEntity&lt;APIEntitiesFeature&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesFeature> postApiV4FeaturesNameWithHttpInfo(@jakarta.annotation.Nonnull String name, @jakarta.annotation.Nonnull PostApiV4FeaturesNameRequest postApiV4FeaturesNameRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesFeature> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4FeaturesNameRequestCreation(name, postApiV4FeaturesNameRequest).toEntity(localVarReturnType);
    }

    /**
     * Create or update a feature flag
     * Creates or updates a feature flag value. If a feature with the given name doesn&#39;t exist yet, the operation creates one. The value can be a boolean or an integer to indicate percentage of time. A single call can enable or disable a feature for a &#x60;feature_group&#x60;, a &#x60;user&#x60;, a &#x60;group&#x60;, a &#x60;namespace&#x60;, a &#x60;project&#x60;, an &#x60;organization&#x60;, a &#x60;repository&#x60;, and a &#x60;runner&#x60;. Enabling a feature that is still in development has security and stability risks.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param name Name of the feature flag.
     * @param postApiV4FeaturesNameRequest The postApiV4FeaturesNameRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4FeaturesNameWithResponseSpec(@jakarta.annotation.Nonnull String name, @jakarta.annotation.Nonnull PostApiV4FeaturesNameRequest postApiV4FeaturesNameRequest) throws RestClientResponseException {
        return postApiV4FeaturesNameRequestCreation(name, postApiV4FeaturesNameRequest);
    }
}
