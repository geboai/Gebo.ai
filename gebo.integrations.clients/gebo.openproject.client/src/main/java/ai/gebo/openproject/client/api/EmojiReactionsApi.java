package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.EmojiReactionModel;
import ai.gebo.openproject.client.model.EmojiReactionsModel;
import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.ToggleActivityEmojiReactionRequest;

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
public class EmojiReactionsApi {
    private ApiClient apiClient;

    public EmojiReactionsApi() {
        this(new ApiClient());
    }

    public EmojiReactionsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * List emoji reactions by activity
     * List all emoji reactions of a single activity.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the activity does not exist or the client does not have sufficient permissions to see it.  **Required permission:** - &#x60;view_work_packages&#x60; - for internal comments: &#x60;view_internal_comments&#x60;  *Note: A client without sufficient permissions shall not be able to test for the existence of an activity. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the activity whose emoji reactions will be listed
     * @return EmojiReactionsModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listActivityEmojiReactionsRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listActivityEmojiReactions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<EmojiReactionsModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/activities/{id}/emoji_reactions", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List emoji reactions by activity
     * List all emoji reactions of a single activity.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the activity does not exist or the client does not have sufficient permissions to see it.  **Required permission:** - &#x60;view_work_packages&#x60; - for internal comments: &#x60;view_internal_comments&#x60;  *Note: A client without sufficient permissions shall not be able to test for the existence of an activity. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the activity whose emoji reactions will be listed
     * @return EmojiReactionsModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public EmojiReactionsModel listActivityEmojiReactions(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<EmojiReactionsModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listActivityEmojiReactionsRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List emoji reactions by activity
     * List all emoji reactions of a single activity.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the activity does not exist or the client does not have sufficient permissions to see it.  **Required permission:** - &#x60;view_work_packages&#x60; - for internal comments: &#x60;view_internal_comments&#x60;  *Note: A client without sufficient permissions shall not be able to test for the existence of an activity. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the activity whose emoji reactions will be listed
     * @return ResponseEntity&lt;EmojiReactionsModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<EmojiReactionsModel> listActivityEmojiReactionsWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<EmojiReactionsModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listActivityEmojiReactionsRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List emoji reactions by activity
     * List all emoji reactions of a single activity.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the activity does not exist or the client does not have sufficient permissions to see it.  **Required permission:** - &#x60;view_work_packages&#x60; - for internal comments: &#x60;view_internal_comments&#x60;  *Note: A client without sufficient permissions shall not be able to test for the existence of an activity. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the activity whose emoji reactions will be listed
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listActivityEmojiReactionsWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return listActivityEmojiReactionsRequestCreation(id);
    }

    /**
     * List emoji reactions by work package activities
     * List all emoji reactions of all activities of a single work package.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** - &#x60;view_work_packages&#x60; - for internal comments: &#x60;view_internal_comments&#x60;  *Note: A client without sufficient permissions shall not be able to test for the existence of a work package. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the work package whose activities&#39; emoji reactions will be listed
     * @return EmojiReactionsModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listWorkPackageActivitiesEmojiReactionsRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling listWorkPackageActivitiesEmojiReactions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<EmojiReactionsModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/work_packages/{id}/activities_emoji_reactions", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List emoji reactions by work package activities
     * List all emoji reactions of all activities of a single work package.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** - &#x60;view_work_packages&#x60; - for internal comments: &#x60;view_internal_comments&#x60;  *Note: A client without sufficient permissions shall not be able to test for the existence of a work package. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the work package whose activities&#39; emoji reactions will be listed
     * @return EmojiReactionsModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public EmojiReactionsModel listWorkPackageActivitiesEmojiReactions(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<EmojiReactionsModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkPackageActivitiesEmojiReactionsRequestCreation(id).body(localVarReturnType);
    }

    /**
     * List emoji reactions by work package activities
     * List all emoji reactions of all activities of a single work package.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** - &#x60;view_work_packages&#x60; - for internal comments: &#x60;view_internal_comments&#x60;  *Note: A client without sufficient permissions shall not be able to test for the existence of a work package. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the work package whose activities&#39; emoji reactions will be listed
     * @return ResponseEntity&lt;EmojiReactionsModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<EmojiReactionsModel> listWorkPackageActivitiesEmojiReactionsWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<EmojiReactionsModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listWorkPackageActivitiesEmojiReactionsRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * List emoji reactions by work package activities
     * List all emoji reactions of all activities of a single work package.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the work package does not exist or the client does not have sufficient permissions to see it.  **Required permission:** - &#x60;view_work_packages&#x60; - for internal comments: &#x60;view_internal_comments&#x60;  *Note: A client without sufficient permissions shall not be able to test for the existence of a work package. That&#39;s why a 404 is returned here, even if a 403 might be more appropriate.*
     * @param id ID of the work package whose activities&#39; emoji reactions will be listed
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listWorkPackageActivitiesEmojiReactionsWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return listWorkPackageActivitiesEmojiReactionsRequestCreation(id);
    }

    /**
     * Toggle emoji reaction for an activity
     * Toggle an emoji reaction for a given activity. If the user has already reacted with the given emoji, the reaction will be removed. Otherwise, a new reaction will be created.  **Note:** The response contains the complete collection of all emoji reactions for this activity.  **Required permission:** - &#x60;add_work_package_comments&#x60; - for internal comments: &#x60;add_internal_comments&#x60;
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the request is invalid. For example, if the reaction is not valid.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to toggle the emoji reaction for the activity.
     * <p><b>404</b> - Returned if the activity does not exist or the client does not have sufficient permissions to see it.
     * @param id ID of the activity to toggle emoji reaction for
     * @param toggleActivityEmojiReactionRequest The toggleActivityEmojiReactionRequest parameter
     * @return EmojiReactionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec toggleActivityEmojiReactionRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull ToggleActivityEmojiReactionRequest toggleActivityEmojiReactionRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling toggleActivityEmojiReaction", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'toggleActivityEmojiReactionRequest' is set
        if (toggleActivityEmojiReactionRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'toggleActivityEmojiReactionRequest' when calling toggleActivityEmojiReaction", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("id", id);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/hal+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/hal+json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "BasicAuth" };

        ParameterizedTypeReference<EmojiReactionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/activities/{id}/emoji_reactions", HttpMethod.PATCH, pathParams, localVarQueryParams, toggleActivityEmojiReactionRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Toggle emoji reaction for an activity
     * Toggle an emoji reaction for a given activity. If the user has already reacted with the given emoji, the reaction will be removed. Otherwise, a new reaction will be created.  **Note:** The response contains the complete collection of all emoji reactions for this activity.  **Required permission:** - &#x60;add_work_package_comments&#x60; - for internal comments: &#x60;add_internal_comments&#x60;
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the request is invalid. For example, if the reaction is not valid.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to toggle the emoji reaction for the activity.
     * <p><b>404</b> - Returned if the activity does not exist or the client does not have sufficient permissions to see it.
     * @param id ID of the activity to toggle emoji reaction for
     * @param toggleActivityEmojiReactionRequest The toggleActivityEmojiReactionRequest parameter
     * @return EmojiReactionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public EmojiReactionModel toggleActivityEmojiReaction(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull ToggleActivityEmojiReactionRequest toggleActivityEmojiReactionRequest) throws RestClientResponseException {
        ParameterizedTypeReference<EmojiReactionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return toggleActivityEmojiReactionRequestCreation(id, toggleActivityEmojiReactionRequest).body(localVarReturnType);
    }

    /**
     * Toggle emoji reaction for an activity
     * Toggle an emoji reaction for a given activity. If the user has already reacted with the given emoji, the reaction will be removed. Otherwise, a new reaction will be created.  **Note:** The response contains the complete collection of all emoji reactions for this activity.  **Required permission:** - &#x60;add_work_package_comments&#x60; - for internal comments: &#x60;add_internal_comments&#x60;
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the request is invalid. For example, if the reaction is not valid.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to toggle the emoji reaction for the activity.
     * <p><b>404</b> - Returned if the activity does not exist or the client does not have sufficient permissions to see it.
     * @param id ID of the activity to toggle emoji reaction for
     * @param toggleActivityEmojiReactionRequest The toggleActivityEmojiReactionRequest parameter
     * @return ResponseEntity&lt;EmojiReactionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<EmojiReactionModel> toggleActivityEmojiReactionWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull ToggleActivityEmojiReactionRequest toggleActivityEmojiReactionRequest) throws RestClientResponseException {
        ParameterizedTypeReference<EmojiReactionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return toggleActivityEmojiReactionRequestCreation(id, toggleActivityEmojiReactionRequest).toEntity(localVarReturnType);
    }

    /**
     * Toggle emoji reaction for an activity
     * Toggle an emoji reaction for a given activity. If the user has already reacted with the given emoji, the reaction will be removed. Otherwise, a new reaction will be created.  **Note:** The response contains the complete collection of all emoji reactions for this activity.  **Required permission:** - &#x60;add_work_package_comments&#x60; - for internal comments: &#x60;add_internal_comments&#x60;
     * <p><b>200</b> - OK
     * <p><b>400</b> - Returned if the request is invalid. For example, if the reaction is not valid.
     * <p><b>403</b> - Returned if the client does not have sufficient permissions to toggle the emoji reaction for the activity.
     * <p><b>404</b> - Returned if the activity does not exist or the client does not have sufficient permissions to see it.
     * @param id ID of the activity to toggle emoji reaction for
     * @param toggleActivityEmojiReactionRequest The toggleActivityEmojiReactionRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec toggleActivityEmojiReactionWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nonnull ToggleActivityEmojiReactionRequest toggleActivityEmojiReactionRequest) throws RestClientResponseException {
        return toggleActivityEmojiReactionRequestCreation(id, toggleActivityEmojiReactionRequest);
    }
}
