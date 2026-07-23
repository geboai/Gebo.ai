package gebo.microservices.api.client.tyr.api;

import gebo.microservices.api.client.tyr.invoker.ApiClient;

import gebo.microservices.api.client.tyr.model.LLMUsageDrillDownLevel;
import gebo.microservices.api.client.tyr.model.LLMUsageDrillDownResult;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2026-07-22T11:47:54.334477201+02:00[Europe/Rome]")

public class LlmsUsageAdminLevelControllerApi {
    private ApiClient apiClient;

     public LlmsUsageAdminLevelControllerApi() {
        this(new ApiClient());
    }
    public LlmsUsageAdminLevelControllerApi(ApiClient apiClient) {
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
     * @return LLMUsageDrillDownResult
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public LLMUsageDrillDownResult adminDrillDown(LLMUsageDrillDownLevel body) throws RestClientException {
        return adminDrillDownWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;LLMUsageDrillDownResult&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<LLMUsageDrillDownResult> adminDrillDownWithHttpInfo(LLMUsageDrillDownLevel body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling adminDrillDown");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/LLMSUsageAdminLevelController/drillDown").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<LLMUsageDrillDownResult> returnType = new ParameterizedTypeReference<LLMUsageDrillDownResult>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
