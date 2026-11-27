package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesSuggestion;
import ai.gebo.gitlab.client.model.PutApiV4SuggestionsBatchApplyRequest;
import ai.gebo.gitlab.client.model.PutApiV4SuggestionsIdApplyRequest;

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
public class SuggestionsApi {
    private ApiClient apiClient;

    public SuggestionsApi() {
        this(new ApiClient());
    }

    public SuggestionsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Apply multiple suggestions to a merge request
     * Applies multiple suggested patches in a merge request. You must have the Developer, Maintainer, or Owner role.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param putApiV4SuggestionsBatchApplyRequest The putApiV4SuggestionsBatchApplyRequest parameter
     * @return List&lt;APIEntitiesSuggestion&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4SuggestionsBatchApplyRequestCreation(@jakarta.annotation.Nonnull PutApiV4SuggestionsBatchApplyRequest putApiV4SuggestionsBatchApplyRequest) throws RestClientResponseException {
        // verify the required parameter 'putApiV4SuggestionsBatchApplyRequest' is set
        if (putApiV4SuggestionsBatchApplyRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'putApiV4SuggestionsBatchApplyRequest' when calling putApiV4SuggestionsBatchApply", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<List<APIEntitiesSuggestion>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/suggestions/batch_apply", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4SuggestionsBatchApplyRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Apply multiple suggestions to a merge request
     * Applies multiple suggested patches in a merge request. You must have the Developer, Maintainer, or Owner role.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param putApiV4SuggestionsBatchApplyRequest The putApiV4SuggestionsBatchApplyRequest parameter
     * @return List&lt;APIEntitiesSuggestion&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public List<APIEntitiesSuggestion> putApiV4SuggestionsBatchApply(@jakarta.annotation.Nonnull PutApiV4SuggestionsBatchApplyRequest putApiV4SuggestionsBatchApplyRequest) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesSuggestion>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4SuggestionsBatchApplyRequestCreation(putApiV4SuggestionsBatchApplyRequest).body(localVarReturnType);
    }

    /**
     * Apply multiple suggestions to a merge request
     * Applies multiple suggested patches in a merge request. You must have the Developer, Maintainer, or Owner role.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param putApiV4SuggestionsBatchApplyRequest The putApiV4SuggestionsBatchApplyRequest parameter
     * @return ResponseEntity&lt;List&lt;APIEntitiesSuggestion&gt;&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<APIEntitiesSuggestion>> putApiV4SuggestionsBatchApplyWithHttpInfo(@jakarta.annotation.Nonnull PutApiV4SuggestionsBatchApplyRequest putApiV4SuggestionsBatchApplyRequest) throws RestClientResponseException {
        ParameterizedTypeReference<List<APIEntitiesSuggestion>> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4SuggestionsBatchApplyRequestCreation(putApiV4SuggestionsBatchApplyRequest).toEntity(localVarReturnType);
    }

    /**
     * Apply multiple suggestions to a merge request
     * Applies multiple suggested patches in a merge request. You must have the Developer, Maintainer, or Owner role.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * @param putApiV4SuggestionsBatchApplyRequest The putApiV4SuggestionsBatchApplyRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4SuggestionsBatchApplyWithResponseSpec(@jakarta.annotation.Nonnull PutApiV4SuggestionsBatchApplyRequest putApiV4SuggestionsBatchApplyRequest) throws RestClientResponseException {
        return putApiV4SuggestionsBatchApplyRequestCreation(putApiV4SuggestionsBatchApplyRequest);
    }

    /**
     * Apply a suggestion to a merge request
     * Applies a suggested patch in a merge request. You must have the Developer, Maintainer, or Owner role.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the suggestion.
     * @param putApiV4SuggestionsIdApplyRequest The putApiV4SuggestionsIdApplyRequest parameter
     * @return APIEntitiesSuggestion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec putApiV4SuggestionsIdApplyRequestCreation(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4SuggestionsIdApplyRequest putApiV4SuggestionsIdApplyRequest) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling putApiV4SuggestionsIdApply", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "apiKey", "http", "oauth2" };

        ParameterizedTypeReference<APIEntitiesSuggestion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/suggestions/{id}/apply", HttpMethod.PUT, pathParams, localVarQueryParams, putApiV4SuggestionsIdApplyRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Apply a suggestion to a merge request
     * Applies a suggested patch in a merge request. You must have the Developer, Maintainer, or Owner role.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the suggestion.
     * @param putApiV4SuggestionsIdApplyRequest The putApiV4SuggestionsIdApplyRequest parameter
     * @return APIEntitiesSuggestion
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesSuggestion putApiV4SuggestionsIdApply(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4SuggestionsIdApplyRequest putApiV4SuggestionsIdApplyRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSuggestion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4SuggestionsIdApplyRequestCreation(id, putApiV4SuggestionsIdApplyRequest).body(localVarReturnType);
    }

    /**
     * Apply a suggestion to a merge request
     * Applies a suggested patch in a merge request. You must have the Developer, Maintainer, or Owner role.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the suggestion.
     * @param putApiV4SuggestionsIdApplyRequest The putApiV4SuggestionsIdApplyRequest parameter
     * @return ResponseEntity&lt;APIEntitiesSuggestion&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesSuggestion> putApiV4SuggestionsIdApplyWithHttpInfo(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4SuggestionsIdApplyRequest putApiV4SuggestionsIdApplyRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesSuggestion> localVarReturnType = new ParameterizedTypeReference<>() {};
        return putApiV4SuggestionsIdApplyRequestCreation(id, putApiV4SuggestionsIdApplyRequest).toEntity(localVarReturnType);
    }

    /**
     * Apply a suggestion to a merge request
     * Applies a suggested patch in a merge request. You must have the Developer, Maintainer, or Owner role.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * <p><b>400</b> - Bad Request
     * @param id ID of the suggestion.
     * @param putApiV4SuggestionsIdApplyRequest The putApiV4SuggestionsIdApplyRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec putApiV4SuggestionsIdApplyWithResponseSpec(@jakarta.annotation.Nonnull Integer id, @jakarta.annotation.Nullable PutApiV4SuggestionsIdApplyRequest putApiV4SuggestionsIdApplyRequest) throws RestClientResponseException {
        return putApiV4SuggestionsIdApplyRequestCreation(id, putApiV4SuggestionsIdApplyRequest);
    }
}
