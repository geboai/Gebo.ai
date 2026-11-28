package gebo.microservices.api.client.zammad.api;

import gebo.microservices.api.client.zammad.invoker.ApiClient;

import gebo.microservices.api.client.zammad.model.AggregateRequestBodyZammadResultsExtractionData;
import gebo.microservices.api.client.zammad.model.CatalogueSample;
import gebo.microservices.api.client.zammad.model.CustomTemplateParamsRequestBody;
import gebo.microservices.api.client.zammad.model.SearchQuery;
import gebo.microservices.api.client.zammad.model.SearchResult;
import gebo.microservices.api.client.zammad.model.SearchResultAnalisysOutcome;
import gebo.microservices.api.client.zammad.model.SearchableSystemMetaData;
import gebo.microservices.api.client.zammad.model.ZammadResultsExtractionData;
import gebo.microservices.api.client.zammad.model.ZammadTicketsSearchFilter;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2026-11-28T07:05:59.134772278+01:00[Europe/Rome]")

public class ZammadSearchServiceControllerApi {
    private ApiClient apiClient;

     public ZammadSearchServiceControllerApi() {
        this(new ApiClient());
    }
    public ZammadSearchServiceControllerApi(ApiClient apiClient) {
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
     * @return ZammadResultsExtractionData
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ZammadResultsExtractionData restAggregateZammad(AggregateRequestBodyZammadResultsExtractionData body) throws RestClientException {
        return restAggregateZammadWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;ZammadResultsExtractionData&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ZammadResultsExtractionData> restAggregateZammadWithHttpInfo(AggregateRequestBodyZammadResultsExtractionData body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling restAggregateZammad");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/ZammadSearchServiceController/aggregate").build().toUriString();
        
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

        ParameterizedTypeReference<ZammadResultsExtractionData> returnType = new ParameterizedTypeReference<ZammadResultsExtractionData>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return Map&lt;String, Object&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Map<String, Object> restCreateCustomTemplateParamsMapZammad(CustomTemplateParamsRequestBody body) throws RestClientException {
        return restCreateCustomTemplateParamsMapZammadWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;Map&lt;String, Object&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Map<String, Object>> restCreateCustomTemplateParamsMapZammadWithHttpInfo(CustomTemplateParamsRequestBody body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling restCreateCustomTemplateParamsMapZammad");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/ZammadSearchServiceController/createCustomTemplateParamsMap").build().toUriString();
        
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

        ParameterizedTypeReference<Map<String, Object>> returnType = new ParameterizedTypeReference<Map<String, Object>>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @param systemId  (required)
     * @return SearchResultAnalisysOutcome
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public SearchResultAnalisysOutcome restExtractRelatedAnalisysReferencesZammad(ZammadResultsExtractionData body, String systemId) throws RestClientException {
        return restExtractRelatedAnalisysReferencesZammadWithHttpInfo(body, systemId).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @param systemId  (required)
     * @return ResponseEntity&lt;SearchResultAnalisysOutcome&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SearchResultAnalisysOutcome> restExtractRelatedAnalisysReferencesZammadWithHttpInfo(ZammadResultsExtractionData body, String systemId) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling restExtractRelatedAnalisysReferencesZammad");
        }
        // verify the required parameter 'systemId' is set
        if (systemId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'systemId' when calling restExtractRelatedAnalisysReferencesZammad");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/ZammadSearchServiceController/extractRelatedAnalisysReferences").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "systemId", systemId));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<SearchResultAnalisysOutcome> returnType = new ParameterizedTypeReference<SearchResultAnalisysOutcome>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param systemId  (required)
     * @return SearchableSystemMetaData
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public SearchableSystemMetaData restFindSystemByIdZammad(String systemId) throws RestClientException {
        return restFindSystemByIdZammadWithHttpInfo(systemId).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param systemId  (required)
     * @return ResponseEntity&lt;SearchableSystemMetaData&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SearchableSystemMetaData> restFindSystemByIdZammadWithHttpInfo(String systemId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'systemId' is set
        if (systemId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'systemId' when calling restFindSystemByIdZammad");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/ZammadSearchServiceController/findSystemById").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "systemId", systemId));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<SearchableSystemMetaData> returnType = new ParameterizedTypeReference<SearchableSystemMetaData>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return SearchableSystemMetaData
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public SearchableSystemMetaData restFindSystemBySearchResultZammad(SearchResult body) throws RestClientException {
        return restFindSystemBySearchResultZammadWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;SearchableSystemMetaData&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SearchableSystemMetaData> restFindSystemBySearchResultZammadWithHttpInfo(SearchResult body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling restFindSystemBySearchResultZammad");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/ZammadSearchServiceController/findSystemBySearchResult").build().toUriString();
        
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

        ParameterizedTypeReference<SearchableSystemMetaData> returnType = new ParameterizedTypeReference<SearchableSystemMetaData>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param systemConfigurationCode  (optional)
     * @return List&lt;CatalogueSample&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<CatalogueSample> restGetCachedCataloguesZammad(String systemConfigurationCode) throws RestClientException {
        return restGetCachedCataloguesZammadWithHttpInfo(systemConfigurationCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param systemConfigurationCode  (optional)
     * @return ResponseEntity&lt;List&lt;CatalogueSample&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<CatalogueSample>> restGetCachedCataloguesZammadWithHttpInfo(String systemConfigurationCode) throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/ZammadSearchServiceController/getCachedCatalogues").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "systemConfigurationCode", systemConfigurationCode));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List<CatalogueSample>> returnType = new ParameterizedTypeReference<List<CatalogueSample>>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param configurationCode  (required)
     * @return List&lt;CatalogueSample&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<CatalogueSample> restGetCataloguesListSampleZammad(String configurationCode) throws RestClientException {
        return restGetCataloguesListSampleZammadWithHttpInfo(configurationCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param configurationCode  (required)
     * @return ResponseEntity&lt;List&lt;CatalogueSample&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<CatalogueSample>> restGetCataloguesListSampleZammadWithHttpInfo(String configurationCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'configurationCode' is set
        if (configurationCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'configurationCode' when calling restGetCataloguesListSampleZammad");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/ZammadSearchServiceController/getCataloguesListSample").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "configurationCode", configurationCode));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List<CatalogueSample>> returnType = new ParameterizedTypeReference<List<CatalogueSample>>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return String
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public String restGetDescriptionZammad() throws RestClientException {
        return restGetDescriptionZammadWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;String&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<String> restGetDescriptionZammadWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/ZammadSearchServiceController/getDescription").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "*/*"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<String> returnType = new ParameterizedTypeReference<String>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return String
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public String restGetIdZammad() throws RestClientException {
        return restGetIdZammadWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;String&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<String> restGetIdZammadWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/ZammadSearchServiceController/getId").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "*/*"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<String> returnType = new ParameterizedTypeReference<String>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return String
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public String restGetMessagingModuleIdZammad() throws RestClientException {
        return restGetMessagingModuleIdZammadWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;String&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<String> restGetMessagingModuleIdZammadWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/ZammadSearchServiceController/getMessagingModuleId").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "*/*"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<String> returnType = new ParameterizedTypeReference<String>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return String
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public String restGetNativePromptTemplateUseCodeZammad() throws RestClientException {
        return restGetNativePromptTemplateUseCodeZammadWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;String&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<String> restGetNativePromptTemplateUseCodeZammadWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/ZammadSearchServiceController/getNativePromptTemplateUseCode").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "*/*"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<String> returnType = new ParameterizedTypeReference<String>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return String
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public String restGetProductIdZammad() throws RestClientException {
        return restGetProductIdZammadWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;String&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<String> restGetProductIdZammadWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/ZammadSearchServiceController/getProductId").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "*/*"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<String> returnType = new ParameterizedTypeReference<String>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return String
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public String restGetQueriesGenerationPromptUseCodeZammad() throws RestClientException {
        return restGetQueriesGenerationPromptUseCodeZammadWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;String&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<String> restGetQueriesGenerationPromptUseCodeZammadWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/ZammadSearchServiceController/getQueriesGenerationPromptUseCode").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "*/*"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<String> returnType = new ParameterizedTypeReference<String>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return List&lt;SearchableSystemMetaData&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<SearchableSystemMetaData> restGetSearchableSystemsZammad() throws RestClientException {
        return restGetSearchableSystemsZammadWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;List&lt;SearchableSystemMetaData&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<SearchableSystemMetaData>> restGetSearchableSystemsZammadWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/ZammadSearchServiceController/getSearchableSystems").build().toUriString();
        
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

        ParameterizedTypeReference<List<SearchableSystemMetaData>> returnType = new ParameterizedTypeReference<List<SearchableSystemMetaData>>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return Boolean
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Boolean restIsEnabledZammad() throws RestClientException {
        return restIsEnabledZammadWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;Boolean&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Boolean> restIsEnabledZammadWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/ZammadSearchServiceController/isEnabled").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "*/*"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Boolean> returnType = new ParameterizedTypeReference<Boolean>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @param systemId  (required)
     * @param nEntryLimit  (required)
     * @param connectTimeoutMillis  (optional)
     * @param readTimeoutMillis  (optional)
     * @param retries  (optional)
     * @return List&lt;SearchResult&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<SearchResult> restNativeSearchZammad(ZammadTicketsSearchFilter body, String systemId, Integer nEntryLimit, Integer connectTimeoutMillis, Integer readTimeoutMillis, Integer retries) throws RestClientException {
        return restNativeSearchZammadWithHttpInfo(body, systemId, nEntryLimit, connectTimeoutMillis, readTimeoutMillis, retries).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @param systemId  (required)
     * @param nEntryLimit  (required)
     * @param connectTimeoutMillis  (optional)
     * @param readTimeoutMillis  (optional)
     * @param retries  (optional)
     * @return ResponseEntity&lt;List&lt;SearchResult&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<SearchResult>> restNativeSearchZammadWithHttpInfo(ZammadTicketsSearchFilter body, String systemId, Integer nEntryLimit, Integer connectTimeoutMillis, Integer readTimeoutMillis, Integer retries) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling restNativeSearchZammad");
        }
        // verify the required parameter 'systemId' is set
        if (systemId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'systemId' when calling restNativeSearchZammad");
        }
        // verify the required parameter 'nEntryLimit' is set
        if (nEntryLimit == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'nEntryLimit' when calling restNativeSearchZammad");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/ZammadSearchServiceController/nativeSearch").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "systemId", systemId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "nEntryLimit", nEntryLimit));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "connectTimeoutMillis", connectTimeoutMillis));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "readTimeoutMillis", readTimeoutMillis));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "retries", retries));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List<SearchResult>> returnType = new ParameterizedTypeReference<List<SearchResult>>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @param systemId  (required)
     * @param nEntryLimit  (required)
     * @param connectTimeoutMillis  (optional)
     * @param readTimeoutMillis  (optional)
     * @param retries  (optional)
     * @return List&lt;SearchResult&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<SearchResult> restSearchZammad(SearchQuery body, String systemId, Integer nEntryLimit, Integer connectTimeoutMillis, Integer readTimeoutMillis, Integer retries) throws RestClientException {
        return restSearchZammadWithHttpInfo(body, systemId, nEntryLimit, connectTimeoutMillis, readTimeoutMillis, retries).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @param systemId  (required)
     * @param nEntryLimit  (required)
     * @param connectTimeoutMillis  (optional)
     * @param readTimeoutMillis  (optional)
     * @param retries  (optional)
     * @return ResponseEntity&lt;List&lt;SearchResult&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<SearchResult>> restSearchZammadWithHttpInfo(SearchQuery body, String systemId, Integer nEntryLimit, Integer connectTimeoutMillis, Integer readTimeoutMillis, Integer retries) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling restSearchZammad");
        }
        // verify the required parameter 'systemId' is set
        if (systemId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'systemId' when calling restSearchZammad");
        }
        // verify the required parameter 'nEntryLimit' is set
        if (nEntryLimit == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'nEntryLimit' when calling restSearchZammad");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/ZammadSearchServiceController/search").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "systemId", systemId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "nEntryLimit", nEntryLimit));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "connectTimeoutMillis", connectTimeoutMillis));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "readTimeoutMillis", readTimeoutMillis));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "retries", retries));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List<SearchResult>> returnType = new ParameterizedTypeReference<List<SearchResult>>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
