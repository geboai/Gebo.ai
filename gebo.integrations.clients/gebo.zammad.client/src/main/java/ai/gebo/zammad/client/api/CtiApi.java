package ai.gebo.zammad.client.api;

import ai.gebo.zammad.client.invoker.ApiClient;

import ai.gebo.zammad.client.model.CtiActionResponse;
import ai.gebo.zammad.client.model.CtiEvent;
import ai.gebo.zammad.client.model.ErrorResponse;

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
public class CtiApi {
    private ApiClient apiClient;

    public CtiApi() {
        this(new ApiClient());
    }

    public CtiApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Submit generic CTI event
     * Submit &#x60;newCall&#x60;, &#x60;answer&#x60;, or &#x60;hangup&#x60;. Authentication is the secret path token, not a regular Zammad API credential.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param token Secret Generic CTI integration token from the instance configuration.
     * @param ctiEvent The ctiEvent parameter
     * @return CtiActionResponse
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec submitCtiEventRequestCreation(@jakarta.annotation.Nonnull String token, @jakarta.annotation.Nonnull CtiEvent ctiEvent) throws RestClientResponseException {
        // verify the required parameter 'token' is set
        if (token == null) {
            throw new RestClientResponseException("Missing the required parameter 'token' when calling submitCtiEvent", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'ctiEvent' is set
        if (ctiEvent == null) {
            throw new RestClientResponseException("Missing the required parameter 'ctiEvent' when calling submitCtiEvent", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<>();

        pathParams.put("token", token);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json", "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<CtiActionResponse> localVarReturnType = new ParameterizedTypeReference<>() {};
        return apiClient.invokeAPI("/api/v1/cti/{token}", HttpMethod.POST, pathParams, localVarQueryParams, ctiEvent, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Submit generic CTI event
     * Submit &#x60;newCall&#x60;, &#x60;answer&#x60;, or &#x60;hangup&#x60;. Authentication is the secret path token, not a regular Zammad API credential.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param token Secret Generic CTI integration token from the instance configuration.
     * @param ctiEvent The ctiEvent parameter
     * @return CtiActionResponse
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public CtiActionResponse submitCtiEvent(@jakarta.annotation.Nonnull String token, @jakarta.annotation.Nonnull CtiEvent ctiEvent) throws RestClientResponseException {
        ParameterizedTypeReference<CtiActionResponse> localVarReturnType = new ParameterizedTypeReference<>() {};
        return submitCtiEventRequestCreation(token, ctiEvent).body(localVarReturnType);
    }

    /**
     * Submit generic CTI event
     * Submit &#x60;newCall&#x60;, &#x60;answer&#x60;, or &#x60;hangup&#x60;. Authentication is the secret path token, not a regular Zammad API credential.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param token Secret Generic CTI integration token from the instance configuration.
     * @param ctiEvent The ctiEvent parameter
     * @return ResponseEntity&lt;CtiActionResponse&gt;
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<CtiActionResponse> submitCtiEventWithHttpInfo(@jakarta.annotation.Nonnull String token, @jakarta.annotation.Nonnull CtiEvent ctiEvent) throws RestClientResponseException {
        ParameterizedTypeReference<CtiActionResponse> localVarReturnType = new ParameterizedTypeReference<>() {};
        return submitCtiEventRequestCreation(token, ctiEvent).toEntity(localVarReturnType);
    }

    /**
     * Submit generic CTI event
     * Submit &#x60;newCall&#x60;, &#x60;answer&#x60;, or &#x60;hangup&#x60;. Authentication is the secret path token, not a regular Zammad API credential.
     * <p><b>200</b> - Successful response.
     * <p><b>401</b> - Credentials are invalid or expired.
     * <p><b>403</b> - Authentication is missing or the user lacks permission.
     * <p><b>404</b> - Resource not found.
     * <p><b>422</b> - Validation or request semantics failed.
     * <p><b>0</b> - Unexpected error.
     * @param token Secret Generic CTI integration token from the instance configuration.
     * @param ctiEvent The ctiEvent parameter
     * @return ResponseSpec
     * @throws RestClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec submitCtiEventWithResponseSpec(@jakarta.annotation.Nonnull String token, @jakarta.annotation.Nonnull CtiEvent ctiEvent) throws RestClientResponseException {
        return submitCtiEventRequestCreation(token, ctiEvent);
    }
}
