package ai.gebo.monolithic.api.client.api;

import ai.gebo.monolithic.api.client.invoker.ApiClient;

import ai.gebo.monolithic.api.client.model.ChatRuleConflict;
import ai.gebo.monolithic.api.client.model.GChatRule;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2026-10-09T13:37:14.667236589+02:00[Europe/Rome]")

public class GeboAdminChatRulesControllerApi {
    private ApiClient apiClient;

     public GeboAdminChatRulesControllerApi() {
        this(new ApiClient());
    }
    public GeboAdminChatRulesControllerApi(ApiClient apiClient) {
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
     * @return List&lt;ChatRuleConflict&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<ChatRuleConflict> checkSharedRuleConflicts(GChatRule body) throws RestClientException {
        return checkSharedRuleConflictsWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;List&lt;ChatRuleConflict&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<ChatRuleConflict>> checkSharedRuleConflictsWithHttpInfo(GChatRule body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling checkSharedRuleConflicts");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/GeboAdminChatRulesController/checkSharedRuleConflicts").build().toUriString();
        
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

        ParameterizedTypeReference<List<ChatRuleConflict>> returnType = new ParameterizedTypeReference<List<ChatRuleConflict>>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return GChatRule
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GChatRule createSharedRule(GChatRule body) throws RestClientException {
        return createSharedRuleWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;GChatRule&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GChatRule> createSharedRuleWithHttpInfo(GChatRule body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling createSharedRule");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/GeboAdminChatRulesController/createSharedRule").build().toUriString();
        
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

        ParameterizedTypeReference<GChatRule> returnType = new ParameterizedTypeReference<GChatRule>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param id  (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void deleteSharedRule(String id) throws RestClientException {
        deleteSharedRuleWithHttpInfo(id);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param id  (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteSharedRuleWithHttpInfo(String id) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling deleteSharedRule");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/GeboAdminChatRulesController/deleteSharedRule").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "id", id));

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.DELETE, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return List&lt;GChatRule&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<GChatRule> getSharedRules() throws RestClientException {
        return getSharedRulesWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;List&lt;GChatRule&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<GChatRule>> getSharedRulesWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/GeboAdminChatRulesController/getSharedRules").build().toUriString();
        
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

        ParameterizedTypeReference<List<GChatRule>> returnType = new ParameterizedTypeReference<List<GChatRule>>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return GChatRule
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GChatRule updateSharedRule(GChatRule body) throws RestClientException {
        return updateSharedRuleWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;GChatRule&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GChatRule> updateSharedRuleWithHttpInfo(GChatRule body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateSharedRule");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/GeboAdminChatRulesController/updateSharedRule").build().toUriString();
        
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

        ParameterizedTypeReference<GChatRule> returnType = new ParameterizedTypeReference<GChatRule>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
