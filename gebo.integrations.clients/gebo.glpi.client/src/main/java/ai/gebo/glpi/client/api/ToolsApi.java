package ai.gebo.glpi.client.api;

import ai.gebo.glpi.client.invoker.ApiClient;

import ai.gebo.glpi.client.model.AssetsGet200ResponseInner;
import ai.gebo.glpi.client.model.RSSFeed;
import ai.gebo.glpi.client.model.Reminder;
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
public class ToolsApi {
    private ApiClient apiClient;

    public ToolsApi() {
        this(new ApiClient());
    }

    public ToolsApi(ApiClient apiClient) {
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
     * Get all available tool types
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return List&lt;AssetsGet200ResponseInner&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec toolsGetRequestCreation(@jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
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
            "application/json", "text/csv", "application/xml"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<List<AssetsGet200ResponseInner>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Tools/", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Get all available tool types
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return List&lt;AssetsGet200ResponseInner&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<AssetsGet200ResponseInner> toolsGet(@jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<List<AssetsGet200ResponseInner>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return toolsGetRequestCreation(glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * Get all available tool types
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseEntity&lt;List&lt;AssetsGet200ResponseInner&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<AssetsGet200ResponseInner>> toolsGetWithHttpInfo(@jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<List<AssetsGet200ResponseInner>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return toolsGetRequestCreation(glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * Get all available tool types
     * <p><b>200</b> - 
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
    public ResponseSpec toolsGetWithResponseSpec(@jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return toolsGetRequestCreation(glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage);
    }

    /**
     * 
     * List or search for RSSFeeds
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param filter RSQL query string
     * @param start The first item to return
     * @param limit The maximum number of items to return
     * @param sort One or more properties to sort by in the form of property:direction where property is the full property name in dot notation and direction is either asc or desc. If no direction is provided, asc is assumed. Multiple sorts can be provided by separating them with a comma.
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return List&lt;RSSFeed&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec toolsRSSFeedGetRequestCreation(@jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Long start, @jakarta.annotation.Nullable Long limit, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filter", filter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "start", start));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "limit", limit));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));

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
            "application/json", "text/csv", "application/xml"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<List<RSSFeed>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Tools/RSSFeed", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * List or search for RSSFeeds
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param filter RSQL query string
     * @param start The first item to return
     * @param limit The maximum number of items to return
     * @param sort One or more properties to sort by in the form of property:direction where property is the full property name in dot notation and direction is either asc or desc. If no direction is provided, asc is assumed. Multiple sorts can be provided by separating them with a comma.
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return List&lt;RSSFeed&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<RSSFeed> toolsRSSFeedGet(@jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Long start, @jakarta.annotation.Nullable Long limit, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<List<RSSFeed>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return toolsRSSFeedGetRequestCreation(filter, start, limit, sort, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * List or search for RSSFeeds
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param filter RSQL query string
     * @param start The first item to return
     * @param limit The maximum number of items to return
     * @param sort One or more properties to sort by in the form of property:direction where property is the full property name in dot notation and direction is either asc or desc. If no direction is provided, asc is assumed. Multiple sorts can be provided by separating them with a comma.
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseEntity&lt;List&lt;RSSFeed&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<RSSFeed>> toolsRSSFeedGetWithHttpInfo(@jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Long start, @jakarta.annotation.Nullable Long limit, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<List<RSSFeed>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return toolsRSSFeedGetRequestCreation(filter, start, limit, sort, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * List or search for RSSFeeds
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param filter RSQL query string
     * @param start The first item to return
     * @param limit The maximum number of items to return
     * @param sort One or more properties to sort by in the form of property:direction where property is the full property name in dot notation and direction is either asc or desc. If no direction is provided, asc is assumed. Multiple sorts can be provided by separating them with a comma.
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec toolsRSSFeedGetWithResponseSpec(@jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Long start, @jakarta.annotation.Nullable Long limit, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return toolsRSSFeedGetRequestCreation(filter, start, limit, sort, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage);
    }

    /**
     * 
     * Delete a RSSFeed
     * <p><b>204</b> - Success (no content)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param id 
     * @param force If \&quot;true\&quot;, the item will be permanently deleted instead of being moved to the trash (if the item supported soft-deletion).
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec toolsRSSFeedIdDeleteRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean force, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling toolsRSSFeedIdDelete", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

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

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Tools/RSSFeed/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Delete a RSSFeed
     * <p><b>204</b> - Success (no content)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param id 
     * @param force If \&quot;true\&quot;, the item will be permanently deleted instead of being moved to the trash (if the item supported soft-deletion).
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void toolsRSSFeedIdDelete(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean force, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        toolsRSSFeedIdDeleteRequestCreation(id, force, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * Delete a RSSFeed
     * <p><b>204</b> - Success (no content)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param id 
     * @param force If \&quot;true\&quot;, the item will be permanently deleted instead of being moved to the trash (if the item supported soft-deletion).
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> toolsRSSFeedIdDeleteWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean force, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return toolsRSSFeedIdDeleteRequestCreation(id, force, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * Delete a RSSFeed
     * <p><b>204</b> - Success (no content)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param id 
     * @param force If \&quot;true\&quot;, the item will be permanently deleted instead of being moved to the trash (if the item supported soft-deletion).
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec toolsRSSFeedIdDeleteWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean force, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return toolsRSSFeedIdDeleteRequestCreation(id, force, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage);
    }

    /**
     * 
     * Get an existing RSSFeed
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param id 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return RSSFeed
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec toolsRSSFeedIdGetRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling toolsRSSFeedIdGet", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

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
            "application/json", "text/csv", "application/xml"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<RSSFeed> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Tools/RSSFeed/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Get an existing RSSFeed
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param id 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return RSSFeed
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public RSSFeed toolsRSSFeedIdGet(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<RSSFeed> localVarReturnType = new ParameterizedTypeReference<>() {};
        return toolsRSSFeedIdGetRequestCreation(id, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * Get an existing RSSFeed
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param id 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseEntity&lt;RSSFeed&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<RSSFeed> toolsRSSFeedIdGetWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<RSSFeed> localVarReturnType = new ParameterizedTypeReference<>() {};
        return toolsRSSFeedIdGetRequestCreation(id, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * Get an existing RSSFeed
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param id 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec toolsRSSFeedIdGetWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return toolsRSSFeedIdGetRequestCreation(id, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage);
    }

    /**
     * 
     * Update an existing RSSFeed
     * <p><b>200</b> - The updated {itemtype}
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param id 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @param rsSFeed The rsSFeed parameter
     * @return RSSFeed
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec toolsRSSFeedIdPatchRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable RSSFeed rsSFeed) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling toolsRSSFeedIdPatch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

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

        ParameterizedTypeReference<RSSFeed> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Tools/RSSFeed/{id}", HttpMethod.PATCH, pathParams, localVarQueryParams, rsSFeed, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Update an existing RSSFeed
     * <p><b>200</b> - The updated {itemtype}
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param id 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @param rsSFeed The rsSFeed parameter
     * @return RSSFeed
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public RSSFeed toolsRSSFeedIdPatch(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable RSSFeed rsSFeed) throws RestClientResponseException {
        ParameterizedTypeReference<RSSFeed> localVarReturnType = new ParameterizedTypeReference<>() {};
        return toolsRSSFeedIdPatchRequestCreation(id, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, rsSFeed).body(localVarReturnType);
    }

    /**
     * 
     * Update an existing RSSFeed
     * <p><b>200</b> - The updated {itemtype}
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param id 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @param rsSFeed The rsSFeed parameter
     * @return ResponseEntity&lt;RSSFeed&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<RSSFeed> toolsRSSFeedIdPatchWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable RSSFeed rsSFeed) throws RestClientResponseException {
        ParameterizedTypeReference<RSSFeed> localVarReturnType = new ParameterizedTypeReference<>() {};
        return toolsRSSFeedIdPatchRequestCreation(id, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, rsSFeed).toEntity(localVarReturnType);
    }

    /**
     * 
     * Update an existing RSSFeed
     * <p><b>200</b> - The updated {itemtype}
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param id 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @param rsSFeed The rsSFeed parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec toolsRSSFeedIdPatchWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable RSSFeed rsSFeed) throws RestClientResponseException {
        return toolsRSSFeedIdPatchRequestCreation(id, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, rsSFeed);
    }

    /**
     * 
     * Create a new RSSFeed
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
     * @param rsSFeed The rsSFeed parameter
     * @return ReqPost201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec toolsRSSFeedPostRequestCreation(@jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable RSSFeed rsSFeed) throws RestClientResponseException {
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
        return apiClient.invokeAPI("/Tools/RSSFeed", HttpMethod.POST, pathParams, localVarQueryParams, rsSFeed, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Create a new RSSFeed
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
     * @param rsSFeed The rsSFeed parameter
     * @return ReqPost201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ReqPost201Response toolsRSSFeedPost(@jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable RSSFeed rsSFeed) throws RestClientResponseException {
        ParameterizedTypeReference<ReqPost201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return toolsRSSFeedPostRequestCreation(glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, rsSFeed).body(localVarReturnType);
    }

    /**
     * 
     * Create a new RSSFeed
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
     * @param rsSFeed The rsSFeed parameter
     * @return ResponseEntity&lt;ReqPost201Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ReqPost201Response> toolsRSSFeedPostWithHttpInfo(@jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable RSSFeed rsSFeed) throws RestClientResponseException {
        ParameterizedTypeReference<ReqPost201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return toolsRSSFeedPostRequestCreation(glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, rsSFeed).toEntity(localVarReturnType);
    }

    /**
     * 
     * Create a new RSSFeed
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
     * @param rsSFeed The rsSFeed parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec toolsRSSFeedPostWithResponseSpec(@jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable RSSFeed rsSFeed) throws RestClientResponseException {
        return toolsRSSFeedPostRequestCreation(glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, rsSFeed);
    }

    /**
     * 
     * List or search for Reminders
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param filter RSQL query string
     * @param start The first item to return
     * @param limit The maximum number of items to return
     * @param sort One or more properties to sort by in the form of property:direction where property is the full property name in dot notation and direction is either asc or desc. If no direction is provided, asc is assumed. Multiple sorts can be provided by separating them with a comma.
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return List&lt;Reminder&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec toolsReminderGetRequestCreation(@jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Long start, @jakarta.annotation.Nullable Long limit, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "filter", filter));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "start", start));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "limit", limit));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));

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
            "application/json", "text/csv", "application/xml"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<List<Reminder>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Tools/Reminder", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * List or search for Reminders
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param filter RSQL query string
     * @param start The first item to return
     * @param limit The maximum number of items to return
     * @param sort One or more properties to sort by in the form of property:direction where property is the full property name in dot notation and direction is either asc or desc. If no direction is provided, asc is assumed. Multiple sorts can be provided by separating them with a comma.
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return List&lt;Reminder&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<Reminder> toolsReminderGet(@jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Long start, @jakarta.annotation.Nullable Long limit, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<List<Reminder>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return toolsReminderGetRequestCreation(filter, start, limit, sort, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * List or search for Reminders
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param filter RSQL query string
     * @param start The first item to return
     * @param limit The maximum number of items to return
     * @param sort One or more properties to sort by in the form of property:direction where property is the full property name in dot notation and direction is either asc or desc. If no direction is provided, asc is assumed. Multiple sorts can be provided by separating them with a comma.
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseEntity&lt;List&lt;Reminder&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<Reminder>> toolsReminderGetWithHttpInfo(@jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Long start, @jakarta.annotation.Nullable Long limit, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<List<Reminder>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return toolsReminderGetRequestCreation(filter, start, limit, sort, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * List or search for Reminders
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param filter RSQL query string
     * @param start The first item to return
     * @param limit The maximum number of items to return
     * @param sort One or more properties to sort by in the form of property:direction where property is the full property name in dot notation and direction is either asc or desc. If no direction is provided, asc is assumed. Multiple sorts can be provided by separating them with a comma.
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec toolsReminderGetWithResponseSpec(@jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Long start, @jakarta.annotation.Nullable Long limit, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return toolsReminderGetRequestCreation(filter, start, limit, sort, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage);
    }

    /**
     * 
     * Delete a Reminder
     * <p><b>204</b> - Success (no content)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param id 
     * @param force If \&quot;true\&quot;, the item will be permanently deleted instead of being moved to the trash (if the item supported soft-deletion).
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec toolsReminderIdDeleteRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean force, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling toolsReminderIdDelete", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

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

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Tools/Reminder/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Delete a Reminder
     * <p><b>204</b> - Success (no content)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param id 
     * @param force If \&quot;true\&quot;, the item will be permanently deleted instead of being moved to the trash (if the item supported soft-deletion).
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public void toolsReminderIdDelete(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean force, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        toolsReminderIdDeleteRequestCreation(id, force, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * Delete a Reminder
     * <p><b>204</b> - Success (no content)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param id 
     * @param force If \&quot;true\&quot;, the item will be permanently deleted instead of being moved to the trash (if the item supported soft-deletion).
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> toolsReminderIdDeleteWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean force, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return toolsReminderIdDeleteRequestCreation(id, force, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * Delete a Reminder
     * <p><b>204</b> - Success (no content)
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param id 
     * @param force If \&quot;true\&quot;, the item will be permanently deleted instead of being moved to the trash (if the item supported soft-deletion).
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec toolsReminderIdDeleteWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean force, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return toolsReminderIdDeleteRequestCreation(id, force, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage);
    }

    /**
     * 
     * Get an existing Reminder
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param id 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return Reminder
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec toolsReminderIdGetRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling toolsReminderIdGet", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

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
            "application/json", "text/csv", "application/xml"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<Reminder> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Tools/Reminder/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Get an existing Reminder
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param id 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return Reminder
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Reminder toolsReminderIdGet(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<Reminder> localVarReturnType = new ParameterizedTypeReference<>() {};
        return toolsReminderIdGetRequestCreation(id, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * Get an existing Reminder
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param id 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseEntity&lt;Reminder&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Reminder> toolsReminderIdGetWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<Reminder> localVarReturnType = new ParameterizedTypeReference<>() {};
        return toolsReminderIdGetRequestCreation(id, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * Get an existing Reminder
     * <p><b>200</b> - 
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param id 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec toolsReminderIdGetWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return toolsReminderIdGetRequestCreation(id, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage);
    }

    /**
     * 
     * Update an existing Reminder
     * <p><b>200</b> - The updated {itemtype}
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param id 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @param reminder The reminder parameter
     * @return Reminder
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec toolsReminderIdPatchRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable Reminder reminder) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling toolsReminderIdPatch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

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

        ParameterizedTypeReference<Reminder> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Tools/Reminder/{id}", HttpMethod.PATCH, pathParams, localVarQueryParams, reminder, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Update an existing Reminder
     * <p><b>200</b> - The updated {itemtype}
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param id 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @param reminder The reminder parameter
     * @return Reminder
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Reminder toolsReminderIdPatch(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable Reminder reminder) throws RestClientResponseException {
        ParameterizedTypeReference<Reminder> localVarReturnType = new ParameterizedTypeReference<>() {};
        return toolsReminderIdPatchRequestCreation(id, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, reminder).body(localVarReturnType);
    }

    /**
     * 
     * Update an existing Reminder
     * <p><b>200</b> - The updated {itemtype}
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param id 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @param reminder The reminder parameter
     * @return ResponseEntity&lt;Reminder&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Reminder> toolsReminderIdPatchWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable Reminder reminder) throws RestClientResponseException {
        ParameterizedTypeReference<Reminder> localVarReturnType = new ParameterizedTypeReference<>() {};
        return toolsReminderIdPatchRequestCreation(id, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, reminder).toEntity(localVarReturnType);
    }

    /**
     * 
     * Update an existing Reminder
     * <p><b>200</b> - The updated {itemtype}
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>500</b> - Internal server error
     * @param id 
     * @param glPIEntity The ID of the entity to use. If not specified, the default entity for the user is used.
     * @param glPIProfile The ID of the profile to use. If not specified, the default profile for the user is used.
     * @param glPIEntityRecursive \&quot;true\&quot; if the entity access should include child entities. This is false by default.
     * @param acceptLanguage The language to use for the response. If not specified, the default language for the user is used.
     * @param reminder The reminder parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec toolsReminderIdPatchWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable Reminder reminder) throws RestClientResponseException {
        return toolsReminderIdPatchRequestCreation(id, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, reminder);
    }

    /**
     * 
     * Create a new Reminder
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
     * @param reminder The reminder parameter
     * @return ReqPost201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec toolsReminderPostRequestCreation(@jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable Reminder reminder) throws RestClientResponseException {
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
        return apiClient.invokeAPI("/Tools/Reminder", HttpMethod.POST, pathParams, localVarQueryParams, reminder, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Create a new Reminder
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
     * @param reminder The reminder parameter
     * @return ReqPost201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ReqPost201Response toolsReminderPost(@jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable Reminder reminder) throws RestClientResponseException {
        ParameterizedTypeReference<ReqPost201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return toolsReminderPostRequestCreation(glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, reminder).body(localVarReturnType);
    }

    /**
     * 
     * Create a new Reminder
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
     * @param reminder The reminder parameter
     * @return ResponseEntity&lt;ReqPost201Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ReqPost201Response> toolsReminderPostWithHttpInfo(@jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable Reminder reminder) throws RestClientResponseException {
        ParameterizedTypeReference<ReqPost201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return toolsReminderPostRequestCreation(glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, reminder).toEntity(localVarReturnType);
    }

    /**
     * 
     * Create a new Reminder
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
     * @param reminder The reminder parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec toolsReminderPostWithResponseSpec(@jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable Reminder reminder) throws RestClientResponseException {
        return toolsReminderPostRequestCreation(glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, reminder);
    }
}
