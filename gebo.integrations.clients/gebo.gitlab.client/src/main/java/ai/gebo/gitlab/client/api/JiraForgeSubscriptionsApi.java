package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesBasicSuccess;
import ai.gebo.gitlab.client.model.APIEntitiesJiraForgeSubscriptions;
import ai.gebo.gitlab.client.model.PostApiV4IntegrationsJiraForgeSubscriptionsRequest;

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
public class JiraForgeSubscriptionsApi {
    private ApiClient apiClient;

    public JiraForgeSubscriptionsApi() {
        this(new ApiClient());
    }

    public JiraForgeSubscriptionsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete a GitLab for Jira (Forge) namespace subscription
     * Unsubscribes a GitLab namespace from the Forge installation.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID of the subscription.
     * @return APIEntitiesBasicSuccess
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteApiV4IntegrationsJiraForgeSubscriptionsIdRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling deleteApiV4IntegrationsJiraForgeSubscriptionsId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

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

        ParameterizedTypeReference<APIEntitiesBasicSuccess> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/integrations/jira_forge/subscriptions/{id}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete a GitLab for Jira (Forge) namespace subscription
     * Unsubscribes a GitLab namespace from the Forge installation.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID of the subscription.
     * @return APIEntitiesBasicSuccess
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesBasicSuccess deleteApiV4IntegrationsJiraForgeSubscriptionsId(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBasicSuccess> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4IntegrationsJiraForgeSubscriptionsIdRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Delete a GitLab for Jira (Forge) namespace subscription
     * Unsubscribes a GitLab namespace from the Forge installation.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID of the subscription.
     * @return ResponseEntity&lt;APIEntitiesBasicSuccess&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesBasicSuccess> deleteApiV4IntegrationsJiraForgeSubscriptionsIdWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBasicSuccess> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteApiV4IntegrationsJiraForgeSubscriptionsIdRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Delete a GitLab for Jira (Forge) namespace subscription
     * Unsubscribes a GitLab namespace from the Forge installation.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param id ID of the subscription.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteApiV4IntegrationsJiraForgeSubscriptionsIdWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return deleteApiV4IntegrationsJiraForgeSubscriptionsIdRequestCreation(id);
    }

    /**
     * List GitLab for Jira (Forge) namespace subscriptions
     * Lists the GitLab namespaces subscribed to the Forge installation.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * @return APIEntitiesJiraForgeSubscriptions
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getApiV4IntegrationsJiraForgeSubscriptionsRequestCreation() throws RestClientResponseException {
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

        ParameterizedTypeReference<APIEntitiesJiraForgeSubscriptions> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/integrations/jira_forge/subscriptions", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List GitLab for Jira (Forge) namespace subscriptions
     * Lists the GitLab namespaces subscribed to the Forge installation.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * @return APIEntitiesJiraForgeSubscriptions
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesJiraForgeSubscriptions getApiV4IntegrationsJiraForgeSubscriptions() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesJiraForgeSubscriptions> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4IntegrationsJiraForgeSubscriptionsRequestCreation().body(localVarReturnType);
    }

    /**
     * List GitLab for Jira (Forge) namespace subscriptions
     * Lists the GitLab namespaces subscribed to the Forge installation.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * @return ResponseEntity&lt;APIEntitiesJiraForgeSubscriptions&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesJiraForgeSubscriptions> getApiV4IntegrationsJiraForgeSubscriptionsWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesJiraForgeSubscriptions> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getApiV4IntegrationsJiraForgeSubscriptionsRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * List GitLab for Jira (Forge) namespace subscriptions
     * Lists the GitLab namespaces subscribed to the Forge installation.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getApiV4IntegrationsJiraForgeSubscriptionsWithResponseSpec() throws RestClientResponseException {
        return getApiV4IntegrationsJiraForgeSubscriptionsRequestCreation();
    }

    /**
     * Create a GitLab for Jira (Forge) namespace subscription
     * Subscribes a GitLab namespace to the Forge installation so its development data syncs to Jira. Authenticated by the Forge Invocation Token (app-context call); the GitLab user is bound via the signed delegation token from the user_delegation endpoint, and the Jira site-admin check runs against Jira with the app system token riding the call.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param postApiV4IntegrationsJiraForgeSubscriptionsRequest The postApiV4IntegrationsJiraForgeSubscriptionsRequest parameter
     * @return APIEntitiesBasicSuccess
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4IntegrationsJiraForgeSubscriptionsRequestCreation(@jakarta.annotation.Nonnull PostApiV4IntegrationsJiraForgeSubscriptionsRequest postApiV4IntegrationsJiraForgeSubscriptionsRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4IntegrationsJiraForgeSubscriptionsRequest' is set
        if (postApiV4IntegrationsJiraForgeSubscriptionsRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4IntegrationsJiraForgeSubscriptionsRequest' when calling postApiV4IntegrationsJiraForgeSubscriptions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesBasicSuccess> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/integrations/jira_forge/subscriptions", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4IntegrationsJiraForgeSubscriptionsRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create a GitLab for Jira (Forge) namespace subscription
     * Subscribes a GitLab namespace to the Forge installation so its development data syncs to Jira. Authenticated by the Forge Invocation Token (app-context call); the GitLab user is bound via the signed delegation token from the user_delegation endpoint, and the Jira site-admin check runs against Jira with the app system token riding the call.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param postApiV4IntegrationsJiraForgeSubscriptionsRequest The postApiV4IntegrationsJiraForgeSubscriptionsRequest parameter
     * @return APIEntitiesBasicSuccess
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesBasicSuccess postApiV4IntegrationsJiraForgeSubscriptions(@jakarta.annotation.Nonnull PostApiV4IntegrationsJiraForgeSubscriptionsRequest postApiV4IntegrationsJiraForgeSubscriptionsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBasicSuccess> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4IntegrationsJiraForgeSubscriptionsRequestCreation(postApiV4IntegrationsJiraForgeSubscriptionsRequest).body(localVarReturnType);
    }

    /**
     * Create a GitLab for Jira (Forge) namespace subscription
     * Subscribes a GitLab namespace to the Forge installation so its development data syncs to Jira. Authenticated by the Forge Invocation Token (app-context call); the GitLab user is bound via the signed delegation token from the user_delegation endpoint, and the Jira site-admin check runs against Jira with the app system token riding the call.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param postApiV4IntegrationsJiraForgeSubscriptionsRequest The postApiV4IntegrationsJiraForgeSubscriptionsRequest parameter
     * @return ResponseEntity&lt;APIEntitiesBasicSuccess&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesBasicSuccess> postApiV4IntegrationsJiraForgeSubscriptionsWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4IntegrationsJiraForgeSubscriptionsRequest postApiV4IntegrationsJiraForgeSubscriptionsRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesBasicSuccess> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4IntegrationsJiraForgeSubscriptionsRequestCreation(postApiV4IntegrationsJiraForgeSubscriptionsRequest).toEntity(localVarReturnType);
    }

    /**
     * Create a GitLab for Jira (Forge) namespace subscription
     * Subscribes a GitLab namespace to the Forge installation so its development data syncs to Jira. Authenticated by the Forge Invocation Token (app-context call); the GitLab user is bound via the signed delegation token from the user_delegation endpoint, and the Jira site-admin check runs against Jira with the app system token riding the call.
     * <p><b>201</b> - Created
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not found
     * <p><b>422</b> - Unprocessable entity
     * <p><b>400</b> - Bad Request
     * @param postApiV4IntegrationsJiraForgeSubscriptionsRequest The postApiV4IntegrationsJiraForgeSubscriptionsRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4IntegrationsJiraForgeSubscriptionsWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4IntegrationsJiraForgeSubscriptionsRequest postApiV4IntegrationsJiraForgeSubscriptionsRequest) throws RestClientResponseException {
        return postApiV4IntegrationsJiraForgeSubscriptionsRequestCreation(postApiV4IntegrationsJiraForgeSubscriptionsRequest);
    }
}
