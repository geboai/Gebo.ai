package ai.gebo.monolithic.api.client.api;

import ai.gebo.monolithic.api.client.invoker.ApiClient;

import ai.gebo.monolithic.api.client.model.ConditionEvent;
import ai.gebo.monolithic.api.client.model.ConditionEventAck;
import ai.gebo.monolithic.api.client.model.ConditionEventView;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2026-11-09T23:11:35.051684500+01:00[Europe/Rome]")

public class NetworkOfAgentsConditionEventsControllerApi {
    private ApiClient apiClient;

     public NetworkOfAgentsConditionEventsControllerApi() {
        this(new ApiClient());
    }
    public NetworkOfAgentsConditionEventsControllerApi(ApiClient apiClient) {
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
     * @param correlationId  (optional)
     * @param sessionCode  (optional)
     * @param conditionId  (optional)
     * @param externalSystem  (optional)
     * @param externalSessionKey  (optional)
     * @return ConditionEventView
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ConditionEventView conditionEventStatus(String correlationId, String sessionCode, String conditionId, String externalSystem, String externalSessionKey) throws RestClientException {
        return conditionEventStatusWithHttpInfo(correlationId, sessionCode, conditionId, externalSystem, externalSessionKey).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param correlationId  (optional)
     * @param sessionCode  (optional)
     * @param conditionId  (optional)
     * @param externalSystem  (optional)
     * @param externalSessionKey  (optional)
     * @return ResponseEntity&lt;ConditionEventView&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ConditionEventView> conditionEventStatusWithHttpInfo(String correlationId, String sessionCode, String conditionId, String externalSystem, String externalSessionKey) throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/NetworkOfAgentsConditionEventsController/conditionEventStatus").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "correlationId", correlationId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "sessionCode", sessionCode));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "conditionId", conditionId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "externalSystem", externalSystem));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "externalSessionKey", externalSessionKey));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<ConditionEventView> returnType = new ParameterizedTypeReference<ConditionEventView>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ConditionEventAck
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ConditionEventAck deliverConditionEvent(ConditionEvent body) throws RestClientException {
        return deliverConditionEventWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;ConditionEventAck&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ConditionEventAck> deliverConditionEventWithHttpInfo(ConditionEvent body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling deliverConditionEvent");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/NetworkOfAgentsConditionEventsController/deliverConditionEvent").build().toUriString();
        
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

        ParameterizedTypeReference<ConditionEventAck> returnType = new ParameterizedTypeReference<ConditionEventAck>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
