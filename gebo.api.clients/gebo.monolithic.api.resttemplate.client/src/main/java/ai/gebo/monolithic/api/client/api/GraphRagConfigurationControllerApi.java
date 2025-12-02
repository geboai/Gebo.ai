package ai.gebo.monolithic.api.client.api;

import ai.gebo.monolithic.api.client.invoker.ApiClient;

import ai.gebo.monolithic.api.client.model.GObjectRefGProjectEndpoint;
import ai.gebo.monolithic.api.client.model.GraphRagExtractionConfig;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-12-02T06:59:38.467683700+01:00[Europe/Rome]")

public class GraphRagConfigurationControllerApi {
    private ApiClient apiClient;

     public GraphRagConfigurationControllerApi() {
        this(new ApiClient());
    }
    public GraphRagConfigurationControllerApi(ApiClient apiClient) {
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
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void deleteGraphRagExtractionConfig(GraphRagExtractionConfig body) throws RestClientException {
        deleteGraphRagExtractionConfigWithHttpInfo(body);
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteGraphRagExtractionConfigWithHttpInfo(GraphRagExtractionConfig body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling deleteGraphRagExtractionConfig");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/GraphRagConfigurationController/deleteGraphRagExtractionConfig").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.DELETE, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param code  (required)
     * @return GraphRagExtractionConfig
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GraphRagExtractionConfig findGraphRagExtractionConfigByCode(String code) throws RestClientException {
        return findGraphRagExtractionConfigByCodeWithHttpInfo(code).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param code  (required)
     * @return ResponseEntity&lt;GraphRagExtractionConfig&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GraphRagExtractionConfig> findGraphRagExtractionConfigByCodeWithHttpInfo(String code) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'code' is set
        if (code == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'code' when calling findGraphRagExtractionConfigByCode");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/GraphRagConfigurationController/findGraphRagExtractionConfigByCode").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "code", code));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<GraphRagExtractionConfig> returnType = new ParameterizedTypeReference<GraphRagExtractionConfig>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param knowledgeBaseCode  (required)
     * @return List&lt;GraphRagExtractionConfig&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<GraphRagExtractionConfig> findGraphRagExtractionConfigByKnowledgeBase(String knowledgeBaseCode) throws RestClientException {
        return findGraphRagExtractionConfigByKnowledgeBaseWithHttpInfo(knowledgeBaseCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param knowledgeBaseCode  (required)
     * @return ResponseEntity&lt;List&lt;GraphRagExtractionConfig&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<GraphRagExtractionConfig>> findGraphRagExtractionConfigByKnowledgeBaseWithHttpInfo(String knowledgeBaseCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'knowledgeBaseCode' is set
        if (knowledgeBaseCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'knowledgeBaseCode' when calling findGraphRagExtractionConfigByKnowledgeBase");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/GraphRagConfigurationController/findGraphRagExtractionConfigByKnowledgeBase").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "knowledgeBaseCode", knowledgeBaseCode));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List<GraphRagExtractionConfig>> returnType = new ParameterizedTypeReference<List<GraphRagExtractionConfig>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param knowledgeBaseCode  (required)
     * @param projectCode  (required)
     * @return List&lt;GraphRagExtractionConfig&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<GraphRagExtractionConfig> findGraphRagExtractionConfigByKnowledgeBaseAndProjectCode(String knowledgeBaseCode, String projectCode) throws RestClientException {
        return findGraphRagExtractionConfigByKnowledgeBaseAndProjectCodeWithHttpInfo(knowledgeBaseCode, projectCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param knowledgeBaseCode  (required)
     * @param projectCode  (required)
     * @return ResponseEntity&lt;List&lt;GraphRagExtractionConfig&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<GraphRagExtractionConfig>> findGraphRagExtractionConfigByKnowledgeBaseAndProjectCodeWithHttpInfo(String knowledgeBaseCode, String projectCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'knowledgeBaseCode' is set
        if (knowledgeBaseCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'knowledgeBaseCode' when calling findGraphRagExtractionConfigByKnowledgeBaseAndProjectCode");
        }
        // verify the required parameter 'projectCode' is set
        if (projectCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'projectCode' when calling findGraphRagExtractionConfigByKnowledgeBaseAndProjectCode");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/GraphRagConfigurationController/findGraphRagExtractionConfigByKnowledgeBaseAndProjectCode").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "knowledgeBaseCode", knowledgeBaseCode));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "projectCode", projectCode));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List<GraphRagExtractionConfig>> returnType = new ParameterizedTypeReference<List<GraphRagExtractionConfig>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return List&lt;GraphRagExtractionConfig&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<GraphRagExtractionConfig> findGraphRagExtractionConfigByProjectEndpointGObjectRef(GObjectRefGProjectEndpoint body) throws RestClientException {
        return findGraphRagExtractionConfigByProjectEndpointGObjectRefWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;List&lt;GraphRagExtractionConfig&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<GraphRagExtractionConfig>> findGraphRagExtractionConfigByProjectEndpointGObjectRefWithHttpInfo(GObjectRefGProjectEndpoint body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling findGraphRagExtractionConfigByProjectEndpointGObjectRef");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/GraphRagConfigurationController/findGraphRagExtractionConfigByProjectEndpointGObjectRef").build().toUriString();
        
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

        ParameterizedTypeReference<List<GraphRagExtractionConfig>> returnType = new ParameterizedTypeReference<List<GraphRagExtractionConfig>>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return List&lt;GraphRagExtractionConfig&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<GraphRagExtractionConfig> getDefaultGraphRagExtractionConfig() throws RestClientException {
        return getDefaultGraphRagExtractionConfigWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;List&lt;GraphRagExtractionConfig&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<GraphRagExtractionConfig>> getDefaultGraphRagExtractionConfigWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/api/admin/GraphRagConfigurationController/getDefaultGraphRagExtractionConfig").build().toUriString();
        
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

        ParameterizedTypeReference<List<GraphRagExtractionConfig>> returnType = new ParameterizedTypeReference<List<GraphRagExtractionConfig>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param format  (required)
     * @return GraphRagExtractionConfig
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GraphRagExtractionConfig getSystemGraphRagExtractionConfig(String format) throws RestClientException {
        return getSystemGraphRagExtractionConfigWithHttpInfo(format).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param format  (required)
     * @return ResponseEntity&lt;GraphRagExtractionConfig&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GraphRagExtractionConfig> getSystemGraphRagExtractionConfigWithHttpInfo(String format) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'format' when calling getSystemGraphRagExtractionConfig");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/GraphRagConfigurationController/getSystemGraphRagExtractionConfig").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "format", format));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<GraphRagExtractionConfig> returnType = new ParameterizedTypeReference<GraphRagExtractionConfig>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return GraphRagExtractionConfig
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GraphRagExtractionConfig instertGraphRagExtractionConfig(GraphRagExtractionConfig body) throws RestClientException {
        return instertGraphRagExtractionConfigWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;GraphRagExtractionConfig&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GraphRagExtractionConfig> instertGraphRagExtractionConfigWithHttpInfo(GraphRagExtractionConfig body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling instertGraphRagExtractionConfig");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/GraphRagConfigurationController/instertGraphRagExtractionConfig").build().toUriString();
        
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

        ParameterizedTypeReference<GraphRagExtractionConfig> returnType = new ParameterizedTypeReference<GraphRagExtractionConfig>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return GraphRagExtractionConfig
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GraphRagExtractionConfig saveGraphRagExtractionConfig(GraphRagExtractionConfig body) throws RestClientException {
        return saveGraphRagExtractionConfigWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;GraphRagExtractionConfig&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GraphRagExtractionConfig> saveGraphRagExtractionConfigWithHttpInfo(GraphRagExtractionConfig body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling saveGraphRagExtractionConfig");
        }
        String path = UriComponentsBuilder.fromPath("/api/admin/GraphRagConfigurationController/saveGraphRagExtractionConfig").build().toUriString();
        
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

        ParameterizedTypeReference<GraphRagExtractionConfig> returnType = new ParameterizedTypeReference<GraphRagExtractionConfig>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
