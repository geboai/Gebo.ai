package ai.gebo.zammad.client.api;

import ai.gebo.zammad.client.invoker.ApiClient;

import ai.gebo.zammad.client.model.ErrorResponse;
import ai.gebo.zammad.client.model.ListMentions200Response;
import ai.gebo.zammad.client.model.ListMentionsMentionableTypeParameter;
import ai.gebo.zammad.client.model.MentionCreate;

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
public class MentionsApi {
    private ApiClient apiClient;

    public MentionsApi() {
        this(new ApiClient());
    }

    public MentionsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create mention
     * 
     * <p><b>201</b> - Mention created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param mentionCreate The mentionCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Boolean
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createMentionRequestCreation(@jakarta.annotation.Nonnull MentionCreate mentionCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'mentionCreate' is set
        if (mentionCreate == null) {
            throw new RestClientResponseException("Missing the required parameter 'mentionCreate' when calling createMention", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (from != null) {
            headerParams.add("From", apiClient.parameterToString(from));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "zammadToken", "basicAuth", "oauth2Bearer" };

        ParameterizedTypeReference<Boolean> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/mentions", HttpMethod.POST, pathParams, localVarQueryParams, mentionCreate, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Create mention
     * 
     * <p><b>201</b> - Mention created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param mentionCreate The mentionCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Boolean
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Boolean createMention(@jakarta.annotation.Nonnull MentionCreate mentionCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Boolean> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createMentionRequestCreation(mentionCreate, from).body(localVarReturnType);
    }

    /**
     * Create mention
     * 
     * <p><b>201</b> - Mention created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param mentionCreate The mentionCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;Boolean&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Boolean> createMentionWithHttpInfo(@jakarta.annotation.Nonnull MentionCreate mentionCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Boolean> localVarReturnType = new ParameterizedTypeReference<>() {};
        return createMentionRequestCreation(mentionCreate, from).toEntity(localVarReturnType);
    }

    /**
     * Create mention
     * 
     * <p><b>201</b> - Mention created.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param mentionCreate The mentionCreate parameter
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createMentionWithResponseSpec(@jakarta.annotation.Nonnull MentionCreate mentionCreate, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return createMentionRequestCreation(mentionCreate, from);
    }

    /**
     * Delete mention
     * 
     * <p><b>200</b> - Mention subscription removed.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param mentionId Mention ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Boolean
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteMentionRequestCreation(@jakarta.annotation.Nonnull Long mentionId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        // verify the required parameter 'mentionId' is set
        if (mentionId == null) {
            throw new RestClientResponseException("Missing the required parameter 'mentionId' when calling deleteMention", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("mentionId", mentionId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        if (from != null) {
            headerParams.add("From", apiClient.parameterToString(from));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "zammadToken", "basicAuth", "oauth2Bearer" };

        ParameterizedTypeReference<Boolean> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/mentions/{mentionId}", HttpMethod.DELETE, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Delete mention
     * 
     * <p><b>200</b> - Mention subscription removed.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param mentionId Mention ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return Boolean
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public Boolean deleteMention(@jakarta.annotation.Nonnull Long mentionId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Boolean> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteMentionRequestCreation(mentionId, from).body(localVarReturnType);
    }

    /**
     * Delete mention
     * 
     * <p><b>200</b> - Mention subscription removed.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param mentionId Mention ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseEntity&lt;Boolean&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Boolean> deleteMentionWithHttpInfo(@jakarta.annotation.Nonnull Long mentionId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        ParameterizedTypeReference<Boolean> localVarReturnType = new ParameterizedTypeReference<>() {};
        return deleteMentionRequestCreation(mentionId, from).toEntity(localVarReturnType);
    }

    /**
     * Delete mention
     * 
     * <p><b>200</b> - Mention subscription removed.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param mentionId Mention ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteMentionWithResponseSpec(@jakarta.annotation.Nonnull Long mentionId, @jakarta.annotation.Nullable String from) throws RestClientResponseException {
        return deleteMentionRequestCreation(mentionId, from);
    }

    /**
     * List mentions
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param mentionableType The mentionableType parameter
     * @param mentionableId Ticket ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param full Return record IDs and related assets.
     * @return ListMentions200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listMentionsRequestCreation(@jakarta.annotation.Nonnull ListMentionsMentionableTypeParameter mentionableType, @jakarta.annotation.Nonnull Long mentionableId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean full) throws RestClientResponseException {
        // verify the required parameter 'mentionableType' is set
        if (mentionableType == null) {
            throw new RestClientResponseException("Missing the required parameter 'mentionableType' when calling listMentions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mentionableId' is set
        if (mentionableId == null) {
            throw new RestClientResponseException("Missing the required parameter 'mentionableId' when calling listMentions", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "mentionable_type", mentionableType));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "mentionable_id", mentionableId));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "full", full));

        if (from != null) {
            headerParams.add("From", apiClient.parameterToString(from));
        }

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "zammadToken", "basicAuth", "oauth2Bearer" };

        ParameterizedTypeReference<ListMentions200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/mentions", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List mentions
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param mentionableType The mentionableType parameter
     * @param mentionableId Ticket ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param full Return record IDs and related assets.
     * @return ListMentions200Response
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ListMentions200Response listMentions(@jakarta.annotation.Nonnull ListMentionsMentionableTypeParameter mentionableType, @jakarta.annotation.Nonnull Long mentionableId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean full) throws RestClientResponseException {
        ParameterizedTypeReference<ListMentions200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listMentionsRequestCreation(mentionableType, mentionableId, from, full).body(localVarReturnType);
    }

    /**
     * List mentions
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param mentionableType The mentionableType parameter
     * @param mentionableId Ticket ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param full Return record IDs and related assets.
     * @return ResponseEntity&lt;ListMentions200Response&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ListMentions200Response> listMentionsWithHttpInfo(@jakarta.annotation.Nonnull ListMentionsMentionableTypeParameter mentionableType, @jakarta.annotation.Nonnull Long mentionableId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean full) throws RestClientResponseException {
        ParameterizedTypeReference<ListMentions200Response> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listMentionsRequestCreation(mentionableType, mentionableId, from, full).toEntity(localVarReturnType);
    }

    /**
     * List mentions
     * 
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param mentionableType The mentionableType parameter
     * @param mentionableId Ticket ID.
     * @param from Act on behalf of another user. Accepts user ID, login, or email and requires &#x60;admin.user&#x60;.
     * @param full Return record IDs and related assets.
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listMentionsWithResponseSpec(@jakarta.annotation.Nonnull ListMentionsMentionableTypeParameter mentionableType, @jakarta.annotation.Nonnull Long mentionableId, @jakarta.annotation.Nullable String from, @jakarta.annotation.Nullable Boolean full) throws RestClientResponseException {
        return listMentionsRequestCreation(mentionableType, mentionableId, from, full);
    }
}
