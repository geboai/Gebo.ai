package gebo.microservices.api.client.brain.api;

import gebo.microservices.api.client.brain.invoker.ApiClient;


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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2026-07-21T14:57:36.399842475+02:00[Europe/Rome]")

public class GeboLlmGeneratedResourceControllerApi {
    private ApiClient apiClient;

     public GeboLlmGeneratedResourceControllerApi() {
        this(new ApiClient());
    }
    public GeboLlmGeneratedResourceControllerApi(ApiClient apiClient) {
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
     * @param userSessionCode  (required)
     * @param generatedResourceCode  (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void serveLLMGeneratedContent(Object userSessionCode, Object generatedResourceCode) throws RestClientException {
        serveLLMGeneratedContentWithHttpInfo(userSessionCode, generatedResourceCode);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param userSessionCode  (required)
     * @param generatedResourceCode  (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> serveLLMGeneratedContentWithHttpInfo(Object userSessionCode, Object generatedResourceCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'userSessionCode' is set
        if (userSessionCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'userSessionCode' when calling serveLLMGeneratedContent");
        }
        // verify the required parameter 'generatedResourceCode' is set
        if (generatedResourceCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'generatedResourceCode' when calling serveLLMGeneratedContent");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("userSessionCode", userSessionCode);
        uriVariables.put("generatedResourceCode", generatedResourceCode);
        String path = UriComponentsBuilder.fromPath("/api/users/GeboLLMGeneratedResourceController/serveLLMGeneratedContent/{userSessionCode}/{generatedResourceCode}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
