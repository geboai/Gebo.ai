package ai.gebo.openproject.client.api;

import ai.gebo.openproject.client.invoker.ApiClient;

import ai.gebo.openproject.client.model.ErrorResponse;
import ai.gebo.openproject.client.model.HelpTextCollectionModel;
import ai.gebo.openproject.client.model.HelpTextModel;

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
public class HelpTextsApi {
    private ApiClient apiClient;

    public HelpTextsApi() {
        this(new ApiClient());
    }

    public HelpTextsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Get help text
     * Fetches the help text from the given id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the help text does not exist.
     * @param id Help text id
     * @return HelpTextModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getHelpTextRequestCreation(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new RestClientResponseException("Missing the required parameter 'id' when calling getHelpText", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<HelpTextModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/help_texts/{id}", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Get help text
     * Fetches the help text from the given id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the help text does not exist.
     * @param id Help text id
     * @return HelpTextModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public HelpTextModel getHelpText(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<HelpTextModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getHelpTextRequestCreation(id).body(localVarReturnType);
    }

    /**
     * Get help text
     * Fetches the help text from the given id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the help text does not exist.
     * @param id Help text id
     * @return ResponseEntity&lt;HelpTextModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<HelpTextModel> getHelpTextWithHttpInfo(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        ParameterizedTypeReference<HelpTextModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return getHelpTextRequestCreation(id).toEntity(localVarReturnType);
    }

    /**
     * Get help text
     * Fetches the help text from the given id.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Returned if the help text does not exist.
     * @param id Help text id
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getHelpTextWithResponseSpec(@jakarta.annotation.Nonnull Integer id) throws RestClientResponseException {
        return getHelpTextRequestCreation(id);
    }

    /**
     * List help texts
     * List the complete collection of help texts.
     * <p><b>200</b> - OK
     * @return HelpTextCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec listHelpTextsRequestCreation() throws RestClientResponseException {
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

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

        ParameterizedTypeReference<HelpTextCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v3/help_texts", HttpMethod.GET, pathParams, localVarQueryParams, null, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * List help texts
     * List the complete collection of help texts.
     * <p><b>200</b> - OK
     * @return HelpTextCollectionModel
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public HelpTextCollectionModel listHelpTexts() throws RestClientResponseException {
        ParameterizedTypeReference<HelpTextCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listHelpTextsRequestCreation().body(localVarReturnType);
    }

    /**
     * List help texts
     * List the complete collection of help texts.
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;HelpTextCollectionModel&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<HelpTextCollectionModel> listHelpTextsWithHttpInfo() throws RestClientResponseException {
        ParameterizedTypeReference<HelpTextCollectionModel> localVarReturnType = new ParameterizedTypeReference<>() {};
        return listHelpTextsRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * List help texts
     * List the complete collection of help texts.
     * <p><b>200</b> - OK
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec listHelpTextsWithResponseSpec() throws RestClientResponseException {
        return listHelpTextsRequestCreation();
    }
}
