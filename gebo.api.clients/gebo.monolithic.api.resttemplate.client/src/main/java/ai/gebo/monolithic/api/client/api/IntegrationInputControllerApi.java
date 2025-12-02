package ai.gebo.monolithic.api.client.api;

import ai.gebo.monolithic.api.client.invoker.ApiClient;

import java.io.File;
import ai.gebo.monolithic.api.client.model.IntegrationDocumentEnvelop;
import ai.gebo.monolithic.api.client.model.JobTicket;

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

public class IntegrationInputControllerApi {
    private ApiClient apiClient;

     public IntegrationInputControllerApi() {
        this(new ApiClient());
    }
    public IntegrationInputControllerApi(ApiClient apiClient) {
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
     * @param endpointCode  (required)
     * @return JobTicket
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public JobTicket publishContents(List<JobTicket> body, String endpointCode) throws RestClientException {
        return publishContentsWithHttpInfo(body, endpointCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @param endpointCode  (required)
     * @return ResponseEntity&lt;JobTicket&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<JobTicket> publishContentsWithHttpInfo(List<JobTicket> body, String endpointCode) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling publishContents");
        }
        // verify the required parameter 'endpointCode' is set
        if (endpointCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'endpointCode' when calling publishContents");
        }
        String path = UriComponentsBuilder.fromPath("/api/application/IntegrationInputController/publishContents").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "endpointCode", endpointCode));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<JobTicket> returnType = new ParameterizedTypeReference<JobTicket>() {};
        return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param endpointCode  (required)
     * @return JobTicket
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public JobTicket publishSync(String endpointCode) throws RestClientException {
        return publishSyncWithHttpInfo(endpointCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param endpointCode  (required)
     * @return ResponseEntity&lt;JobTicket&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<JobTicket> publishSyncWithHttpInfo(String endpointCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'endpointCode' is set
        if (endpointCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'endpointCode' when calling publishSync");
        }
        String path = UriComponentsBuilder.fromPath("/api/application/IntegrationInputController/publishSync").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "endpointCode", endpointCode));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<JobTicket> returnType = new ParameterizedTypeReference<JobTicket>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param file  (required)
     * @param endpointCode  (required)
     * @param relativePath  (required)
     * @return JobTicket
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public JobTicket spoolDocument(File file, String endpointCode, String relativePath) throws RestClientException {
        return spoolDocumentWithHttpInfo(file, endpointCode, relativePath).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param file  (required)
     * @param endpointCode  (required)
     * @param relativePath  (required)
     * @return ResponseEntity&lt;JobTicket&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<JobTicket> spoolDocumentWithHttpInfo(File file, String endpointCode, String relativePath) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'file' is set
        if (file == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'file' when calling spoolDocument");
        }
        // verify the required parameter 'endpointCode' is set
        if (endpointCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'endpointCode' when calling spoolDocument");
        }
        // verify the required parameter 'relativePath' is set
        if (relativePath == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'relativePath' when calling spoolDocument");
        }
        String path = UriComponentsBuilder.fromPath("/api/application/IntegrationInputController/spoolDocument").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "endpointCode", endpointCode));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "relativePath", relativePath));
        if (file != null)
            formParams.add("file", new FileSystemResource(file));

        final String[] accepts = { 
            "*/*"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "multipart/form-data"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<JobTicket> returnType = new ParameterizedTypeReference<JobTicket>() {};
        return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @param endpointCode  (required)
     * @param relativePath  (required)
     * @return JobTicket
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public JobTicket spoolDocument1(IntegrationDocumentEnvelop body, String endpointCode, String relativePath) throws RestClientException {
        return spoolDocument1WithHttpInfo(body, endpointCode, relativePath).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @param endpointCode  (required)
     * @param relativePath  (required)
     * @return ResponseEntity&lt;JobTicket&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<JobTicket> spoolDocument1WithHttpInfo(IntegrationDocumentEnvelop body, String endpointCode, String relativePath) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling spoolDocument1");
        }
        // verify the required parameter 'endpointCode' is set
        if (endpointCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'endpointCode' when calling spoolDocument1");
        }
        // verify the required parameter 'relativePath' is set
        if (relativePath == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'relativePath' when calling spoolDocument1");
        }
        String path = UriComponentsBuilder.fromPath("/api/application/IntegrationInputController/spoolDocument").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "endpointCode", endpointCode));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "relativePath", relativePath));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<JobTicket> returnType = new ParameterizedTypeReference<JobTicket>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
