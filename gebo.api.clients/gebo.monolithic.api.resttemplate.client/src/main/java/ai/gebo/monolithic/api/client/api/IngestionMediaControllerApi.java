package ai.gebo.monolithic.api.client.api;

import ai.gebo.monolithic.api.client.invoker.ApiClient;

import ai.gebo.monolithic.api.client.model.IngestionMediaCapabilities;
import ai.gebo.monolithic.api.client.model.IngestionMediaText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2026-11-20T06:19:25.937202091+01:00[Europe/Rome]")

public class IngestionMediaControllerApi {
    private ApiClient apiClient;

     public IngestionMediaControllerApi() {
        this(new ApiClient());
    }
    public IngestionMediaControllerApi(ApiClient apiClient) {
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
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @param mimeType  (required)
     * @param name  (optional)
     * @return IngestionMediaText
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public IngestionMediaText describePicture(byte[] body, String mimeType, String name) throws RestClientException {
        return describePictureWithHttpInfo(body, mimeType, name).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @param mimeType  (required)
     * @param name  (optional)
     * @return ResponseEntity&lt;IngestionMediaText&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<IngestionMediaText> describePictureWithHttpInfo(byte[] body, String mimeType, String name) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling describePicture");
        }
        // verify the required parameter 'mimeType' is set
        if (mimeType == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'mimeType' when calling describePicture");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/IngestionMediaController/describePicture").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "name", name));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "mimeType", mimeType));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/octet-stream"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<IngestionMediaText> returnType = new ParameterizedTypeReference<IngestionMediaText>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return IngestionMediaCapabilities
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public IngestionMediaCapabilities getIngestionMediaCapabilities() throws RestClientException {
        return getIngestionMediaCapabilitiesWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;IngestionMediaCapabilities&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<IngestionMediaCapabilities> getIngestionMediaCapabilitiesWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/IngestionMediaController/getIngestionMediaCapabilities").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<IngestionMediaCapabilities> returnType = new ParameterizedTypeReference<IngestionMediaCapabilities>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @param name  (optional)
     * @param mimeType  (optional)
     * @return IngestionMediaText
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public IngestionMediaText transcribe(byte[] body, String name, String mimeType) throws RestClientException {
        return transcribeWithHttpInfo(body, name, mimeType).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @param name  (optional)
     * @param mimeType  (optional)
     * @return ResponseEntity&lt;IngestionMediaText&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<IngestionMediaText> transcribeWithHttpInfo(byte[] body, String name, String mimeType) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling transcribe");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/IngestionMediaController/transcribe").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "name", name));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "mimeType", mimeType));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/octet-stream"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<IngestionMediaText> returnType = new ParameterizedTypeReference<IngestionMediaText>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
