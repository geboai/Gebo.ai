package ai.gebo.monolithic.api.client.api;

import ai.gebo.monolithic.api.client.invoker.ApiClient;

import ai.gebo.monolithic.api.client.model.WaitingBlockView;
import ai.gebo.monolithic.api.client.model.WaitingConditionsActionAck;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2026-11-09T22:08:53.638937800+01:00[Europe/Rome]")

public class WaitingConditionsControllerApi {
    private ApiClient apiClient;

     public WaitingConditionsControllerApi() {
        this(new ApiClient());
    }
    public WaitingConditionsControllerApi(ApiClient apiClient) {
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
     * @param blockCode  (required)
     * @return WaitingConditionsActionAck
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public WaitingConditionsActionAck abandon(String blockCode) throws RestClientException {
        return abandonWithHttpInfo(blockCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param blockCode  (required)
     * @return ResponseEntity&lt;WaitingConditionsActionAck&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WaitingConditionsActionAck> abandonWithHttpInfo(String blockCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'blockCode' is set
        if (blockCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'blockCode' when calling abandon");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/WaitingConditionsController/abandon").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "blockCode", blockCode));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<WaitingConditionsActionAck> returnType = new ParameterizedTypeReference<WaitingConditionsActionAck>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param blockCode  (required)
     * @param body  (optional)
     * @return WaitingConditionsActionAck
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public WaitingConditionsActionAck goOnWithout(String blockCode, List<String> body) throws RestClientException {
        return goOnWithoutWithHttpInfo(blockCode, body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param blockCode  (required)
     * @param body  (optional)
     * @return ResponseEntity&lt;WaitingConditionsActionAck&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WaitingConditionsActionAck> goOnWithoutWithHttpInfo(String blockCode, List<String> body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'blockCode' is set
        if (blockCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'blockCode' when calling goOnWithout");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/WaitingConditionsController/goOnWithout").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "blockCode", blockCode));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<WaitingConditionsActionAck> returnType = new ParameterizedTypeReference<WaitingConditionsActionAck>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return List&lt;WaitingBlockView&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<WaitingBlockView> waitingConditions() throws RestClientException {
        return waitingConditionsWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;List&lt;WaitingBlockView&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<WaitingBlockView>> waitingConditionsWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/WaitingConditionsController/waitingConditions").build().toUriString();
        
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

        ParameterizedTypeReference<List<WaitingBlockView>> returnType = new ParameterizedTypeReference<List<WaitingBlockView>>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
