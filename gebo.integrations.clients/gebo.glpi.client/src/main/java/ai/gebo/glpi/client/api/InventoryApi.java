package ai.gebo.glpi.client.api;

import ai.gebo.glpi.client.invoker.ApiClient;

import ai.gebo.glpi.client.model.Agent;
import java.io.File;
import ai.gebo.glpi.client.model.InventoryAgentIdInventoryRequestPost200Response;
import ai.gebo.glpi.client.model.LockedField;
import ai.gebo.glpi.client.model.ReqPost201Response;
import ai.gebo.glpi.client.model.SNMPCredential;

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
public class InventoryApi {
    private ApiClient apiClient;

    public InventoryApi() {
        this(new ApiClient());
    }

    public InventoryApi(ApiClient apiClient) {
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
     * List or search for Agents
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
     * @return List&lt;Agent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec inventoryAgentGetRequestCreation(@jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Long start, @jakarta.annotation.Nullable Long limit, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
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

        ParameterizedTypeReference<List<Agent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Inventory/Agent", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * List or search for Agents
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
     * @return List&lt;Agent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<Agent> inventoryAgentGet(@jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Long start, @jakarta.annotation.Nullable Long limit, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<List<Agent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return inventoryAgentGetRequestCreation(filter, start, limit, sort, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * List or search for Agents
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
     * @return ResponseEntity&lt;List&lt;Agent&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<Agent>> inventoryAgentGetWithHttpInfo(@jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Long start, @jakarta.annotation.Nullable Long limit, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<List<Agent>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return inventoryAgentGetRequestCreation(filter, start, limit, sort, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * List or search for Agents
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
    public ResponseSpec inventoryAgentGetWithResponseSpec(@jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Long start, @jakarta.annotation.Nullable Long limit, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return inventoryAgentGetRequestCreation(filter, start, limit, sort, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage);
    }

    /**
     * 
     * Delete a Agent
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
    private ResponseSpec inventoryAgentIdDeleteRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean force, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling inventoryAgentIdDelete", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/Inventory/Agent/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Delete a Agent
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
    public void inventoryAgentIdDelete(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean force, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        inventoryAgentIdDeleteRequestCreation(id, force, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * Delete a Agent
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
    public ResponseEntity<Void> inventoryAgentIdDeleteWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean force, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return inventoryAgentIdDeleteRequestCreation(id, force, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * Delete a Agent
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
    public ResponseSpec inventoryAgentIdDeleteWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean force, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return inventoryAgentIdDeleteRequestCreation(id, force, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage);
    }

    /**
     * 
     * Get an existing Agent
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
     * @return Agent
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec inventoryAgentIdGetRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling inventoryAgentIdGet", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<Agent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Inventory/Agent/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Get an existing Agent
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
     * @return Agent
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Agent inventoryAgentIdGet(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<Agent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return inventoryAgentIdGetRequestCreation(id, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * Get an existing Agent
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
     * @return ResponseEntity&lt;Agent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Agent> inventoryAgentIdGetWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<Agent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return inventoryAgentIdGetRequestCreation(id, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * Get an existing Agent
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
    public ResponseSpec inventoryAgentIdGetWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return inventoryAgentIdGetRequestCreation(id, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage);
    }

    /**
     * 
     * Get the last inventory file sent by the agent
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
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec inventoryAgentIdInventoryFileGetRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling inventoryAgentIdInventoryFileGet", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
            "application/octet-stream"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Inventory/Agent/{id}/InventoryFile", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Get the last inventory file sent by the agent
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
     * @return File
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public File inventoryAgentIdInventoryFileGet(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return inventoryAgentIdInventoryFileGetRequestCreation(id, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * Get the last inventory file sent by the agent
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
     * @return ResponseEntity&lt;File&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<File> inventoryAgentIdInventoryFileGetWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<>() {};
        return inventoryAgentIdInventoryFileGetRequestCreation(id, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * Get the last inventory file sent by the agent
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
    public ResponseSpec inventoryAgentIdInventoryFileGetWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return inventoryAgentIdInventoryFileGetRequestCreation(id, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage);
    }

    /**
     * 
     * Request inventory from an agent
     * <p><b>200</b> - 
     * <p><b>201</b> - Success (created)
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
     * @return InventoryAgentIdInventoryRequestPost200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec inventoryAgentIdInventoryRequestPostRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling inventoryAgentIdInventoryRequestPost", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<InventoryAgentIdInventoryRequestPost200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Inventory/Agent/{id}/InventoryRequest", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Request inventory from an agent
     * <p><b>200</b> - 
     * <p><b>201</b> - Success (created)
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
     * @return InventoryAgentIdInventoryRequestPost200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public InventoryAgentIdInventoryRequestPost200Response inventoryAgentIdInventoryRequestPost(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<InventoryAgentIdInventoryRequestPost200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return inventoryAgentIdInventoryRequestPostRequestCreation(id, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * Request inventory from an agent
     * <p><b>200</b> - 
     * <p><b>201</b> - Success (created)
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
     * @return ResponseEntity&lt;InventoryAgentIdInventoryRequestPost200Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<InventoryAgentIdInventoryRequestPost200Response> inventoryAgentIdInventoryRequestPostWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<InventoryAgentIdInventoryRequestPost200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return inventoryAgentIdInventoryRequestPostRequestCreation(id, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * Request inventory from an agent
     * <p><b>200</b> - 
     * <p><b>201</b> - Success (created)
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
    public ResponseSpec inventoryAgentIdInventoryRequestPostWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return inventoryAgentIdInventoryRequestPostRequestCreation(id, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage);
    }

    /**
     * 
     * Update an existing Agent
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
     * @param agent The agent parameter
     * @return Agent
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec inventoryAgentIdPatchRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable Agent agent) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling inventoryAgentIdPatch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<Agent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Inventory/Agent/{id}", HttpMethod.PATCH, pathParams, localVarQueryParams, agent, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Update an existing Agent
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
     * @param agent The agent parameter
     * @return Agent
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Agent inventoryAgentIdPatch(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable Agent agent) throws RestClientResponseException {
        ParameterizedTypeReference<Agent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return inventoryAgentIdPatchRequestCreation(id, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, agent).body(localVarReturnType);
    }

    /**
     * 
     * Update an existing Agent
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
     * @param agent The agent parameter
     * @return ResponseEntity&lt;Agent&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Agent> inventoryAgentIdPatchWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable Agent agent) throws RestClientResponseException {
        ParameterizedTypeReference<Agent> localVarReturnType = new ParameterizedTypeReference<>() {};
        return inventoryAgentIdPatchRequestCreation(id, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, agent).toEntity(localVarReturnType);
    }

    /**
     * 
     * Update an existing Agent
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
     * @param agent The agent parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec inventoryAgentIdPatchWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable Agent agent) throws RestClientResponseException {
        return inventoryAgentIdPatchRequestCreation(id, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, agent);
    }

    /**
     * 
     * Request status from an agent
     * <p><b>200</b> - 
     * <p><b>201</b> - Success (created)
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
     * @return InventoryAgentIdInventoryRequestPost200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec inventoryAgentIdStatusRequestPostRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling inventoryAgentIdStatusRequestPost", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "oauth", "oauth" };

        ParameterizedTypeReference<InventoryAgentIdInventoryRequestPost200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Inventory/Agent/{id}/StatusRequest", HttpMethod.POST, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Request status from an agent
     * <p><b>200</b> - 
     * <p><b>201</b> - Success (created)
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
     * @return InventoryAgentIdInventoryRequestPost200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public InventoryAgentIdInventoryRequestPost200Response inventoryAgentIdStatusRequestPost(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<InventoryAgentIdInventoryRequestPost200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return inventoryAgentIdStatusRequestPostRequestCreation(id, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * Request status from an agent
     * <p><b>200</b> - 
     * <p><b>201</b> - Success (created)
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
     * @return ResponseEntity&lt;InventoryAgentIdInventoryRequestPost200Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<InventoryAgentIdInventoryRequestPost200Response> inventoryAgentIdStatusRequestPostWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<InventoryAgentIdInventoryRequestPost200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return inventoryAgentIdStatusRequestPostRequestCreation(id, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * Request status from an agent
     * <p><b>200</b> - 
     * <p><b>201</b> - Success (created)
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
    public ResponseSpec inventoryAgentIdStatusRequestPostWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return inventoryAgentIdStatusRequestPostRequestCreation(id, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage);
    }

    /**
     * 
     * List or search for LockedFields
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
     * @return List&lt;LockedField&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec inventoryLockedFieldGetRequestCreation(@jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Long start, @jakarta.annotation.Nullable Long limit, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
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

        ParameterizedTypeReference<List<LockedField>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Inventory/LockedField", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * List or search for LockedFields
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
     * @return List&lt;LockedField&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<LockedField> inventoryLockedFieldGet(@jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Long start, @jakarta.annotation.Nullable Long limit, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<List<LockedField>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return inventoryLockedFieldGetRequestCreation(filter, start, limit, sort, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * List or search for LockedFields
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
     * @return ResponseEntity&lt;List&lt;LockedField&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<LockedField>> inventoryLockedFieldGetWithHttpInfo(@jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Long start, @jakarta.annotation.Nullable Long limit, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<List<LockedField>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return inventoryLockedFieldGetRequestCreation(filter, start, limit, sort, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * List or search for LockedFields
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
    public ResponseSpec inventoryLockedFieldGetWithResponseSpec(@jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Long start, @jakarta.annotation.Nullable Long limit, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return inventoryLockedFieldGetRequestCreation(filter, start, limit, sort, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage);
    }

    /**
     * 
     * Delete a LockedField
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
    private ResponseSpec inventoryLockedFieldIdDeleteRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean force, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling inventoryLockedFieldIdDelete", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/Inventory/LockedField/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Delete a LockedField
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
    public void inventoryLockedFieldIdDelete(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean force, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        inventoryLockedFieldIdDeleteRequestCreation(id, force, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * Delete a LockedField
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
    public ResponseEntity<Void> inventoryLockedFieldIdDeleteWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean force, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return inventoryLockedFieldIdDeleteRequestCreation(id, force, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * Delete a LockedField
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
    public ResponseSpec inventoryLockedFieldIdDeleteWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean force, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return inventoryLockedFieldIdDeleteRequestCreation(id, force, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage);
    }

    /**
     * 
     * Get an existing LockedField
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
     * @return LockedField
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec inventoryLockedFieldIdGetRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling inventoryLockedFieldIdGet", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<LockedField> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Inventory/LockedField/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Get an existing LockedField
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
     * @return LockedField
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public LockedField inventoryLockedFieldIdGet(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<LockedField> localVarReturnType = new ParameterizedTypeReference<>() {};
        return inventoryLockedFieldIdGetRequestCreation(id, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * Get an existing LockedField
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
     * @return ResponseEntity&lt;LockedField&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<LockedField> inventoryLockedFieldIdGetWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<LockedField> localVarReturnType = new ParameterizedTypeReference<>() {};
        return inventoryLockedFieldIdGetRequestCreation(id, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * Get an existing LockedField
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
    public ResponseSpec inventoryLockedFieldIdGetWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return inventoryLockedFieldIdGetRequestCreation(id, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage);
    }

    /**
     * 
     * Update an existing LockedField
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
     * @param lockedField The lockedField parameter
     * @return LockedField
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec inventoryLockedFieldIdPatchRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable LockedField lockedField) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling inventoryLockedFieldIdPatch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<LockedField> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Inventory/LockedField/{id}", HttpMethod.PATCH, pathParams, localVarQueryParams, lockedField, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Update an existing LockedField
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
     * @param lockedField The lockedField parameter
     * @return LockedField
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public LockedField inventoryLockedFieldIdPatch(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable LockedField lockedField) throws RestClientResponseException {
        ParameterizedTypeReference<LockedField> localVarReturnType = new ParameterizedTypeReference<>() {};
        return inventoryLockedFieldIdPatchRequestCreation(id, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, lockedField).body(localVarReturnType);
    }

    /**
     * 
     * Update an existing LockedField
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
     * @param lockedField The lockedField parameter
     * @return ResponseEntity&lt;LockedField&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<LockedField> inventoryLockedFieldIdPatchWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable LockedField lockedField) throws RestClientResponseException {
        ParameterizedTypeReference<LockedField> localVarReturnType = new ParameterizedTypeReference<>() {};
        return inventoryLockedFieldIdPatchRequestCreation(id, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, lockedField).toEntity(localVarReturnType);
    }

    /**
     * 
     * Update an existing LockedField
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
     * @param lockedField The lockedField parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec inventoryLockedFieldIdPatchWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable LockedField lockedField) throws RestClientResponseException {
        return inventoryLockedFieldIdPatchRequestCreation(id, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, lockedField);
    }

    /**
     * 
     * Create a new LockedField
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
     * @param lockedField The lockedField parameter
     * @return ReqPost201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec inventoryLockedFieldPostRequestCreation(@jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable LockedField lockedField) throws RestClientResponseException {
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
        return apiClient.invokeAPI("/Inventory/LockedField", HttpMethod.POST, pathParams, localVarQueryParams, lockedField, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Create a new LockedField
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
     * @param lockedField The lockedField parameter
     * @return ReqPost201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ReqPost201Response inventoryLockedFieldPost(@jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable LockedField lockedField) throws RestClientResponseException {
        ParameterizedTypeReference<ReqPost201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return inventoryLockedFieldPostRequestCreation(glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, lockedField).body(localVarReturnType);
    }

    /**
     * 
     * Create a new LockedField
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
     * @param lockedField The lockedField parameter
     * @return ResponseEntity&lt;ReqPost201Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ReqPost201Response> inventoryLockedFieldPostWithHttpInfo(@jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable LockedField lockedField) throws RestClientResponseException {
        ParameterizedTypeReference<ReqPost201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return inventoryLockedFieldPostRequestCreation(glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, lockedField).toEntity(localVarReturnType);
    }

    /**
     * 
     * Create a new LockedField
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
     * @param lockedField The lockedField parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec inventoryLockedFieldPostWithResponseSpec(@jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable LockedField lockedField) throws RestClientResponseException {
        return inventoryLockedFieldPostRequestCreation(glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, lockedField);
    }

    /**
     * 
     * List or search for SNMPCredentials
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
     * @return List&lt;SNMPCredential&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec inventorySNMPCredentialGetRequestCreation(@jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Long start, @jakarta.annotation.Nullable Long limit, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
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

        ParameterizedTypeReference<List<SNMPCredential>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Inventory/SNMPCredential", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * List or search for SNMPCredentials
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
     * @return List&lt;SNMPCredential&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<SNMPCredential> inventorySNMPCredentialGet(@jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Long start, @jakarta.annotation.Nullable Long limit, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<List<SNMPCredential>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return inventorySNMPCredentialGetRequestCreation(filter, start, limit, sort, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * List or search for SNMPCredentials
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
     * @return ResponseEntity&lt;List&lt;SNMPCredential&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<SNMPCredential>> inventorySNMPCredentialGetWithHttpInfo(@jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Long start, @jakarta.annotation.Nullable Long limit, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<List<SNMPCredential>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return inventorySNMPCredentialGetRequestCreation(filter, start, limit, sort, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * List or search for SNMPCredentials
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
    public ResponseSpec inventorySNMPCredentialGetWithResponseSpec(@jakarta.annotation.Nullable String filter, @jakarta.annotation.Nullable Long start, @jakarta.annotation.Nullable Long limit, @jakarta.annotation.Nullable String sort, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return inventorySNMPCredentialGetRequestCreation(filter, start, limit, sort, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage);
    }

    /**
     * 
     * Delete a SNMPCredential
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
    private ResponseSpec inventorySNMPCredentialIdDeleteRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean force, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling inventorySNMPCredentialIdDelete", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/Inventory/SNMPCredential/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Delete a SNMPCredential
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
    public void inventorySNMPCredentialIdDelete(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean force, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        inventorySNMPCredentialIdDeleteRequestCreation(id, force, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * Delete a SNMPCredential
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
    public ResponseEntity<Void> inventorySNMPCredentialIdDeleteWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean force, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<>() {};
        return inventorySNMPCredentialIdDeleteRequestCreation(id, force, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * Delete a SNMPCredential
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
    public ResponseSpec inventorySNMPCredentialIdDeleteWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Boolean force, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return inventorySNMPCredentialIdDeleteRequestCreation(id, force, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage);
    }

    /**
     * 
     * Get an existing SNMPCredential
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
     * @return SNMPCredential
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec inventorySNMPCredentialIdGetRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling inventorySNMPCredentialIdGet", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<SNMPCredential> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Inventory/SNMPCredential/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Get an existing SNMPCredential
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
     * @return SNMPCredential
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public SNMPCredential inventorySNMPCredentialIdGet(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<SNMPCredential> localVarReturnType = new ParameterizedTypeReference<>() {};
        return inventorySNMPCredentialIdGetRequestCreation(id, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).body(localVarReturnType);
    }

    /**
     * 
     * Get an existing SNMPCredential
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
     * @return ResponseEntity&lt;SNMPCredential&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SNMPCredential> inventorySNMPCredentialIdGetWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        ParameterizedTypeReference<SNMPCredential> localVarReturnType = new ParameterizedTypeReference<>() {};
        return inventorySNMPCredentialIdGetRequestCreation(id, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage).toEntity(localVarReturnType);
    }

    /**
     * 
     * Get an existing SNMPCredential
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
    public ResponseSpec inventorySNMPCredentialIdGetWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage) throws RestClientResponseException {
        return inventorySNMPCredentialIdGetRequestCreation(id, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage);
    }

    /**
     * 
     * Update an existing SNMPCredential
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
     * @param snMPCredential The snMPCredential parameter
     * @return SNMPCredential
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec inventorySNMPCredentialIdPatchRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable SNMPCredential snMPCredential) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling inventorySNMPCredentialIdPatch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<SNMPCredential> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/Inventory/SNMPCredential/{id}", HttpMethod.PATCH, pathParams, localVarQueryParams, snMPCredential, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Update an existing SNMPCredential
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
     * @param snMPCredential The snMPCredential parameter
     * @return SNMPCredential
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public SNMPCredential inventorySNMPCredentialIdPatch(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable SNMPCredential snMPCredential) throws RestClientResponseException {
        ParameterizedTypeReference<SNMPCredential> localVarReturnType = new ParameterizedTypeReference<>() {};
        return inventorySNMPCredentialIdPatchRequestCreation(id, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, snMPCredential).body(localVarReturnType);
    }

    /**
     * 
     * Update an existing SNMPCredential
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
     * @param snMPCredential The snMPCredential parameter
     * @return ResponseEntity&lt;SNMPCredential&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SNMPCredential> inventorySNMPCredentialIdPatchWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable SNMPCredential snMPCredential) throws RestClientResponseException {
        ParameterizedTypeReference<SNMPCredential> localVarReturnType = new ParameterizedTypeReference<>() {};
        return inventorySNMPCredentialIdPatchRequestCreation(id, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, snMPCredential).toEntity(localVarReturnType);
    }

    /**
     * 
     * Update an existing SNMPCredential
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
     * @param snMPCredential The snMPCredential parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec inventorySNMPCredentialIdPatchWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable SNMPCredential snMPCredential) throws RestClientResponseException {
        return inventorySNMPCredentialIdPatchRequestCreation(id, glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, snMPCredential);
    }

    /**
     * 
     * Create a new SNMPCredential
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
     * @param snMPCredential The snMPCredential parameter
     * @return ReqPost201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec inventorySNMPCredentialPostRequestCreation(@jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable SNMPCredential snMPCredential) throws RestClientResponseException {
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
        return apiClient.invokeAPI("/Inventory/SNMPCredential", HttpMethod.POST, pathParams, localVarQueryParams, snMPCredential, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Create a new SNMPCredential
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
     * @param snMPCredential The snMPCredential parameter
     * @return ReqPost201Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ReqPost201Response inventorySNMPCredentialPost(@jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable SNMPCredential snMPCredential) throws RestClientResponseException {
        ParameterizedTypeReference<ReqPost201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return inventorySNMPCredentialPostRequestCreation(glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, snMPCredential).body(localVarReturnType);
    }

    /**
     * 
     * Create a new SNMPCredential
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
     * @param snMPCredential The snMPCredential parameter
     * @return ResponseEntity&lt;ReqPost201Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ReqPost201Response> inventorySNMPCredentialPostWithHttpInfo(@jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable SNMPCredential snMPCredential) throws RestClientResponseException {
        ParameterizedTypeReference<ReqPost201Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return inventorySNMPCredentialPostRequestCreation(glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, snMPCredential).toEntity(localVarReturnType);
    }

    /**
     * 
     * Create a new SNMPCredential
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
     * @param snMPCredential The snMPCredential parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec inventorySNMPCredentialPostWithResponseSpec(@jakarta.annotation.Nullable Integer glPIEntity, @jakarta.annotation.Nullable Integer glPIProfile, @jakarta.annotation.Nullable String glPIEntityRecursive, @jakarta.annotation.Nullable String acceptLanguage, @jakarta.annotation.Nullable SNMPCredential snMPCredential) throws RestClientResponseException {
        return inventorySNMPCredentialPostRequestCreation(glPIEntity, glPIProfile, glPIEntityRecursive, acceptLanguage, snMPCredential);
    }
}
