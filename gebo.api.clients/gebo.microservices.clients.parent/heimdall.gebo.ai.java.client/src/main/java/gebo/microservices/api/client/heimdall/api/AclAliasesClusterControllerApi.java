package gebo.microservices.api.client.heimdall.api;

import gebo.microservices.api.client.heimdall.invoker.ApiClient;

import gebo.microservices.api.client.heimdall.model.GAclEntry;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2026-09-23T15:45:03.167494770+02:00[Europe/Rome]")

public class AclAliasesClusterControllerApi {
    private ApiClient apiClient;

     public AclAliasesClusterControllerApi() {
        this(new ApiClient());
    }
    public AclAliasesClusterControllerApi(ApiClient apiClient) {
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
     * @return Integer
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Integer infrastructureAddAcl(GAclEntry body) throws RestClientException {
        return infrastructureAddAclWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;Integer&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Integer> infrastructureAddAclWithHttpInfo(GAclEntry body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling infrastructureAddAcl");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/cluster/AclController/addAcl").build().toUriString();
        
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

        ParameterizedTypeReference<Integer> returnType = new ParameterizedTypeReference<Integer>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param alias  (required)
     * @return GAclEntry
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GAclEntry infrastructureFindAcl(Integer alias) throws RestClientException {
        return infrastructureFindAclWithHttpInfo(alias).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param alias  (required)
     * @return ResponseEntity&lt;GAclEntry&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GAclEntry> infrastructureFindAclWithHttpInfo(Integer alias) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'alias' is set
        if (alias == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'alias' when calling infrastructureFindAcl");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/cluster/AclController/findAcl").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "alias", alias));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<GAclEntry> returnType = new ParameterizedTypeReference<GAclEntry>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return Integer
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Integer infrastructureFindAlias(GAclEntry body) throws RestClientException {
        return infrastructureFindAliasWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;Integer&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Integer> infrastructureFindAliasWithHttpInfo(GAclEntry body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling infrastructureFindAlias");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/cluster/AclController/findAlias").build().toUriString();
        
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

        ParameterizedTypeReference<Integer> returnType = new ParameterizedTypeReference<Integer>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param uniqueId  (required)
     * @return List&lt;Integer&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<Integer> infrastructureFindAliasesByAclGrantedUniqueId(String uniqueId) throws RestClientException {
        return infrastructureFindAliasesByAclGrantedUniqueIdWithHttpInfo(uniqueId).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param uniqueId  (required)
     * @return ResponseEntity&lt;List&lt;Integer&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<Integer>> infrastructureFindAliasesByAclGrantedUniqueIdWithHttpInfo(String uniqueId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'uniqueId' is set
        if (uniqueId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'uniqueId' when calling infrastructureFindAliasesByAclGrantedUniqueId");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/cluster/AclController/findAliasesByAclGrantedUniqueId").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "uniqueId", uniqueId));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List<Integer>> returnType = new ParameterizedTypeReference<List<Integer>>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param uniqueId  (required)
     * @param grantType  (required)
     * @return List&lt;Integer&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<Integer> infrastructureFindAliasesByAclGrantedUniqueIdAndAclGrantType(String uniqueId, String grantType) throws RestClientException {
        return infrastructureFindAliasesByAclGrantedUniqueIdAndAclGrantTypeWithHttpInfo(uniqueId, grantType).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param uniqueId  (required)
     * @param grantType  (required)
     * @return ResponseEntity&lt;List&lt;Integer&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<Integer>> infrastructureFindAliasesByAclGrantedUniqueIdAndAclGrantTypeWithHttpInfo(String uniqueId, String grantType) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'uniqueId' is set
        if (uniqueId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'uniqueId' when calling infrastructureFindAliasesByAclGrantedUniqueIdAndAclGrantType");
        }
        // verify the required parameter 'grantType' is set
        if (grantType == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'grantType' when calling infrastructureFindAliasesByAclGrantedUniqueIdAndAclGrantType");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/cluster/AclController/findAliasesByAclGrantedUniqueIdAndAclGrantType").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "uniqueId", uniqueId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "grantType", grantType));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List<Integer>> returnType = new ParameterizedTypeReference<List<Integer>>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return List&lt;Integer&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<Integer> infrastructureFindAliasesByAclGrantedUniqueIdIn(List<String> body) throws RestClientException {
        return infrastructureFindAliasesByAclGrantedUniqueIdInWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;List&lt;Integer&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<Integer>> infrastructureFindAliasesByAclGrantedUniqueIdInWithHttpInfo(List<String> body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling infrastructureFindAliasesByAclGrantedUniqueIdIn");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/cluster/AclController/findAliasesByAclGrantedUniqueIdIn").build().toUriString();
        
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

        ParameterizedTypeReference<List<Integer>> returnType = new ParameterizedTypeReference<List<Integer>>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @param grantType  (required)
     * @return List&lt;Integer&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<Integer> infrastructureFindAliasesByAclGrantedUniqueIdInAndAclGrantType(List<String> body, String grantType) throws RestClientException {
        return infrastructureFindAliasesByAclGrantedUniqueIdInAndAclGrantTypeWithHttpInfo(body, grantType).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @param grantType  (required)
     * @return ResponseEntity&lt;List&lt;Integer&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<Integer>> infrastructureFindAliasesByAclGrantedUniqueIdInAndAclGrantTypeWithHttpInfo(List<String> body, String grantType) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling infrastructureFindAliasesByAclGrantedUniqueIdInAndAclGrantType");
        }
        // verify the required parameter 'grantType' is set
        if (grantType == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'grantType' when calling infrastructureFindAliasesByAclGrantedUniqueIdInAndAclGrantType");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/cluster/AclController/findAliasesByAclGrantedUniqueIdInAndAclGrantType").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "grantType", grantType));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List<Integer>> returnType = new ParameterizedTypeReference<List<Integer>>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param alias  (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void infrastructureRemoveAcl(Integer alias) throws RestClientException {
        infrastructureRemoveAclWithHttpInfo(alias);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param alias  (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> infrastructureRemoveAclWithHttpInfo(Integer alias) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'alias' is set
        if (alias == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'alias' when calling infrastructureRemoveAcl");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/cluster/AclController/removeAcl").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "alias", alias));

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.DELETE, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
