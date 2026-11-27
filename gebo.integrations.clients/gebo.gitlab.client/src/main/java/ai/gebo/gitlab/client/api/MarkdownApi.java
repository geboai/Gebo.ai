package ai.gebo.gitlab.client.api;

import ai.gebo.gitlab.client.invoker.ApiClient;

import ai.gebo.gitlab.client.model.APIEntitiesMarkdown;
import ai.gebo.gitlab.client.model.PostApiV4MarkdownRequest;

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
public class MarkdownApi {
    private ApiClient apiClient;

    public MarkdownApi() {
        this(new ApiClient());
    }

    public MarkdownApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Render Markdown content
     * Renders Markdown content as HTML.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * @param postApiV4MarkdownRequest The postApiV4MarkdownRequest parameter
     * @return APIEntitiesMarkdown
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec postApiV4MarkdownRequestCreation(@jakarta.annotation.Nonnull PostApiV4MarkdownRequest postApiV4MarkdownRequest) throws RestClientResponseException {
        // verify the required parameter 'postApiV4MarkdownRequest' is set
        if (postApiV4MarkdownRequest == null) {
            throw new RestClientResponseException("Missing the required parameter 'postApiV4MarkdownRequest' when calling postApiV4Markdown", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<APIEntitiesMarkdown> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v4/markdown", HttpMethod.POST, pathParams, localVarQueryParams, postApiV4MarkdownRequest, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Render Markdown content
     * Renders Markdown content as HTML.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * @param postApiV4MarkdownRequest The postApiV4MarkdownRequest parameter
     * @return APIEntitiesMarkdown
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public APIEntitiesMarkdown postApiV4Markdown(@jakarta.annotation.Nonnull PostApiV4MarkdownRequest postApiV4MarkdownRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMarkdown> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4MarkdownRequestCreation(postApiV4MarkdownRequest).body(localVarReturnType);
    }

    /**
     * Render Markdown content
     * Renders Markdown content as HTML.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * @param postApiV4MarkdownRequest The postApiV4MarkdownRequest parameter
     * @return ResponseEntity&lt;APIEntitiesMarkdown&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<APIEntitiesMarkdown> postApiV4MarkdownWithHttpInfo(@jakarta.annotation.Nonnull PostApiV4MarkdownRequest postApiV4MarkdownRequest) throws RestClientResponseException {
        ParameterizedTypeReference<APIEntitiesMarkdown> localVarReturnType = new ParameterizedTypeReference<>() {};
        return postApiV4MarkdownRequestCreation(postApiV4MarkdownRequest).toEntity(localVarReturnType);
    }

    /**
     * Render Markdown content
     * Renders Markdown content as HTML.
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad request
     * <p><b>401</b> - Unauthorized
     * @param postApiV4MarkdownRequest The postApiV4MarkdownRequest parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec postApiV4MarkdownWithResponseSpec(@jakarta.annotation.Nonnull PostApiV4MarkdownRequest postApiV4MarkdownRequest) throws RestClientResponseException {
        return postApiV4MarkdownRequestCreation(postApiV4MarkdownRequest);
    }
}
