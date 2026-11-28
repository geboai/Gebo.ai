package gebo.microservices.api.client.redmine.api;

import gebo.microservices.api.client.redmine.invoker.ApiClient;

import gebo.microservices.api.client.redmine.model.AggregateRequestBodyRedmineResultsExtractionData;
import gebo.microservices.api.client.redmine.model.CatalogueSample;
import gebo.microservices.api.client.redmine.model.CustomTemplateParamsRequestBody;
import gebo.microservices.api.client.redmine.model.RedmineIssuesSearchFilter;
import gebo.microservices.api.client.redmine.model.RedmineResultsExtractionData;
import gebo.microservices.api.client.redmine.model.SearchQuery;
import gebo.microservices.api.client.redmine.model.SearchResult;
import gebo.microservices.api.client.redmine.model.SearchResultAnalisysOutcome;
import gebo.microservices.api.client.redmine.model.SearchableSystemMetaData;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2026-11-28T07:05:54.288542252+01:00[Europe/Rome]")

public class RedmineSearchServiceControllerApi {
    private ApiClient apiClient;

     public RedmineSearchServiceControllerApi() {
        this(new ApiClient());
    }
    public RedmineSearchServiceControllerApi(ApiClient apiClient) {
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
     * @return RedmineResultsExtractionData
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public RedmineResultsExtractionData restAggregateRedmine(AggregateRequestBodyRedmineResultsExtractionData body) throws RestClientException {
        return restAggregateRedmineWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;RedmineResultsExtractionData&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<RedmineResultsExtractionData> restAggregateRedmineWithHttpInfo(AggregateRequestBodyRedmineResultsExtractionData body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling restAggregateRedmine");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/RedmineSearchServiceController/aggregate").build().toUriString();
        
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

        ParameterizedTypeReference<RedmineResultsExtractionData> returnType = new ParameterizedTypeReference<RedmineResultsExtractionData>() {};
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
    public Map<String, Object> restCreateCustomTemplateParamsMapRedmine(CustomTemplateParamsRequestBody body) throws RestClientException {
        return restCreateCustomTemplateParamsMapRedmineWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;Map&lt;String, Object&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Map<String, Object>> restCreateCustomTemplateParamsMapRedmineWithHttpInfo(CustomTemplateParamsRequestBody body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling restCreateCustomTemplateParamsMapRedmine");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/RedmineSearchServiceController/createCustomTemplateParamsMap").build().toUriString();
        
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
    public SearchResultAnalisysOutcome restExtractRelatedAnalisysReferencesRedmine(RedmineResultsExtractionData body, String systemId) throws RestClientException {
        return restExtractRelatedAnalisysReferencesRedmineWithHttpInfo(body, systemId).getBody();
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
    public ResponseEntity<SearchResultAnalisysOutcome> restExtractRelatedAnalisysReferencesRedmineWithHttpInfo(RedmineResultsExtractionData body, String systemId) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling restExtractRelatedAnalisysReferencesRedmine");
        }
        // verify the required parameter 'systemId' is set
        if (systemId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'systemId' when calling restExtractRelatedAnalisysReferencesRedmine");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/RedmineSearchServiceController/extractRelatedAnalisysReferences").build().toUriString();
        
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
    public SearchableSystemMetaData restFindSystemByIdRedmine(String systemId) throws RestClientException {
        return restFindSystemByIdRedmineWithHttpInfo(systemId).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param systemId  (required)
     * @return ResponseEntity&lt;SearchableSystemMetaData&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SearchableSystemMetaData> restFindSystemByIdRedmineWithHttpInfo(String systemId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'systemId' is set
        if (systemId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'systemId' when calling restFindSystemByIdRedmine");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/RedmineSearchServiceController/findSystemById").build().toUriString();
        
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
    public SearchableSystemMetaData restFindSystemBySearchResultRedmine(SearchResult body) throws RestClientException {
        return restFindSystemBySearchResultRedmineWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;SearchableSystemMetaData&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SearchableSystemMetaData> restFindSystemBySearchResultRedmineWithHttpInfo(SearchResult body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling restFindSystemBySearchResultRedmine");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/RedmineSearchServiceController/findSystemBySearchResult").build().toUriString();
        
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
    public List<CatalogueSample> restGetCachedCataloguesRedmine(String systemConfigurationCode) throws RestClientException {
        return restGetCachedCataloguesRedmineWithHttpInfo(systemConfigurationCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param systemConfigurationCode  (optional)
     * @return ResponseEntity&lt;List&lt;CatalogueSample&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<CatalogueSample>> restGetCachedCataloguesRedmineWithHttpInfo(String systemConfigurationCode) throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/RedmineSearchServiceController/getCachedCatalogues").build().toUriString();
        
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
    public List<CatalogueSample> restGetCataloguesListSampleRedmine(String configurationCode) throws RestClientException {
        return restGetCataloguesListSampleRedmineWithHttpInfo(configurationCode).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param configurationCode  (required)
     * @return ResponseEntity&lt;List&lt;CatalogueSample&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<CatalogueSample>> restGetCataloguesListSampleRedmineWithHttpInfo(String configurationCode) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'configurationCode' is set
        if (configurationCode == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'configurationCode' when calling restGetCataloguesListSampleRedmine");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/RedmineSearchServiceController/getCataloguesListSample").build().toUriString();
        
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
    public String restGetDescriptionRedmine() throws RestClientException {
        return restGetDescriptionRedmineWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;String&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<String> restGetDescriptionRedmineWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/RedmineSearchServiceController/getDescription").build().toUriString();
        
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
    public String restGetIdRedmine() throws RestClientException {
        return restGetIdRedmineWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;String&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<String> restGetIdRedmineWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/RedmineSearchServiceController/getId").build().toUriString();
        
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
    public String restGetMessagingModuleIdRedmine() throws RestClientException {
        return restGetMessagingModuleIdRedmineWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;String&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<String> restGetMessagingModuleIdRedmineWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/RedmineSearchServiceController/getMessagingModuleId").build().toUriString();
        
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
    public String restGetNativePromptTemplateUseCodeRedmine() throws RestClientException {
        return restGetNativePromptTemplateUseCodeRedmineWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;String&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<String> restGetNativePromptTemplateUseCodeRedmineWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/RedmineSearchServiceController/getNativePromptTemplateUseCode").build().toUriString();
        
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
    public String restGetProductIdRedmine() throws RestClientException {
        return restGetProductIdRedmineWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;String&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<String> restGetProductIdRedmineWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/RedmineSearchServiceController/getProductId").build().toUriString();
        
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
    public String restGetQueriesGenerationPromptUseCodeRedmine() throws RestClientException {
        return restGetQueriesGenerationPromptUseCodeRedmineWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;String&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<String> restGetQueriesGenerationPromptUseCodeRedmineWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/RedmineSearchServiceController/getQueriesGenerationPromptUseCode").build().toUriString();
        
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
    public List<SearchableSystemMetaData> restGetSearchableSystemsRedmine() throws RestClientException {
        return restGetSearchableSystemsRedmineWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;List&lt;SearchableSystemMetaData&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<SearchableSystemMetaData>> restGetSearchableSystemsRedmineWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/RedmineSearchServiceController/getSearchableSystems").build().toUriString();
        
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
    public Boolean restIsEnabledRedmine() throws RestClientException {
        return restIsEnabledRedmineWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;Boolean&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Boolean> restIsEnabledRedmineWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/RedmineSearchServiceController/isEnabled").build().toUriString();
        
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
    public List<SearchResult> restNativeSearchRedmine(RedmineIssuesSearchFilter body, String systemId, Integer nEntryLimit, Integer connectTimeoutMillis, Integer readTimeoutMillis, Integer retries) throws RestClientException {
        return restNativeSearchRedmineWithHttpInfo(body, systemId, nEntryLimit, connectTimeoutMillis, readTimeoutMillis, retries).getBody();
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
    public ResponseEntity<List<SearchResult>> restNativeSearchRedmineWithHttpInfo(RedmineIssuesSearchFilter body, String systemId, Integer nEntryLimit, Integer connectTimeoutMillis, Integer readTimeoutMillis, Integer retries) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling restNativeSearchRedmine");
        }
        // verify the required parameter 'systemId' is set
        if (systemId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'systemId' when calling restNativeSearchRedmine");
        }
        // verify the required parameter 'nEntryLimit' is set
        if (nEntryLimit == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'nEntryLimit' when calling restNativeSearchRedmine");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/RedmineSearchServiceController/nativeSearch").build().toUriString();
        
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
    public List<SearchResult> restSearchRedmine(SearchQuery body, String systemId, Integer nEntryLimit, Integer connectTimeoutMillis, Integer readTimeoutMillis, Integer retries) throws RestClientException {
        return restSearchRedmineWithHttpInfo(body, systemId, nEntryLimit, connectTimeoutMillis, readTimeoutMillis, retries).getBody();
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
    public ResponseEntity<List<SearchResult>> restSearchRedmineWithHttpInfo(SearchQuery body, String systemId, Integer nEntryLimit, Integer connectTimeoutMillis, Integer readTimeoutMillis, Integer retries) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling restSearchRedmine");
        }
        // verify the required parameter 'systemId' is set
        if (systemId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'systemId' when calling restSearchRedmine");
        }
        // verify the required parameter 'nEntryLimit' is set
        if (nEntryLimit == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'nEntryLimit' when calling restSearchRedmine");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/users/RedmineSearchServiceController/search").build().toUriString();
        
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
