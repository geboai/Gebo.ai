package ai.gebo.monolithic.api.client.api;

import ai.gebo.monolithic.api.client.invoker.ApiClient;

import ai.gebo.monolithic.api.client.model.CreateProviderDealRequest;
import ai.gebo.monolithic.api.client.model.GCurrency;
import ai.gebo.monolithic.api.client.model.GProviderDeal;
import ai.gebo.monolithic.api.client.model.OperationStatusBoolean;
import ai.gebo.monolithic.api.client.model.OperationStatusGProviderCurrency;
import ai.gebo.monolithic.api.client.model.OperationStatusGProviderDeal;
import ai.gebo.monolithic.api.client.model.OperationStatusListGProviderApiKey;
import ai.gebo.monolithic.api.client.model.OperationStatusListGProviderModelPriceInfo;
import ai.gebo.monolithic.api.client.model.ProviderCurrencyRequest;
import ai.gebo.monolithic.api.client.model.ProviderDealDescriptionRequest;
import ai.gebo.monolithic.api.client.model.ProviderDealFlatConditionsRequest;
import ai.gebo.monolithic.api.client.model.ProviderDealKeyRequest;
import ai.gebo.monolithic.api.client.model.ProviderDealModelPricingRequest;
import ai.gebo.monolithic.api.client.model.ProviderDealSpendingLimitsRequest;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2026-10-10T12:17:57.070821934+02:00[Europe/Rome]")

public class ProviderDealsControllerApi {
    private ApiClient apiClient;

     public ProviderDealsControllerApi() {
        this(new ApiClient());
    }
    public ProviderDealsControllerApi(ApiClient apiClient) {
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
     * @return OperationStatusGProviderDeal
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusGProviderDeal assignApiKey(ProviderDealKeyRequest body) throws RestClientException {
        return assignApiKeyWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusGProviderDeal&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusGProviderDeal> assignApiKeyWithHttpInfo(ProviderDealKeyRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling assignApiKey");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/ProviderDealsController/assignApiKey").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusGProviderDeal> returnType = new ParameterizedTypeReference<OperationStatusGProviderDeal>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusGProviderDeal
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusGProviderDeal createProviderDeal(CreateProviderDealRequest body) throws RestClientException {
        return createProviderDealWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusGProviderDeal&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusGProviderDeal> createProviderDealWithHttpInfo(CreateProviderDealRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling createProviderDeal");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/ProviderDealsController/createProviderDeal").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusGProviderDeal> returnType = new ParameterizedTypeReference<OperationStatusGProviderDeal>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusBoolean
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusBoolean deleteProviderDeal(ProviderDealKeyRequest body) throws RestClientException {
        return deleteProviderDealWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusBoolean&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusBoolean> deleteProviderDealWithHttpInfo(ProviderDealKeyRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling deleteProviderDeal");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/ProviderDealsController/deleteProviderDeal").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusBoolean> returnType = new ParameterizedTypeReference<OperationStatusBoolean>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return List&lt;String&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<String> getConfiguredProviderIds() throws RestClientException {
        return getConfiguredProviderIdsWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;List&lt;String&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<String>> getConfiguredProviderIdsWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/ProviderDealsController/getConfiguredProviderIds").build().toUriString();
        
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

        ParameterizedTypeReference<List<String>> returnType = new ParameterizedTypeReference<List<String>>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return List&lt;GCurrency&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<GCurrency> getCurrencies() throws RestClientException {
        return getCurrenciesWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;List&lt;GCurrency&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<GCurrency>> getCurrenciesWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/ProviderDealsController/getCurrencies").build().toUriString();
        
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

        ParameterizedTypeReference<List<GCurrency>> returnType = new ParameterizedTypeReference<List<GCurrency>>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param providerId  (required)
     * @return OperationStatusListGProviderApiKey
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusListGProviderApiKey getProviderApiKeys(String providerId) throws RestClientException {
        return getProviderApiKeysWithHttpInfo(providerId).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param providerId  (required)
     * @return ResponseEntity&lt;OperationStatusListGProviderApiKey&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusListGProviderApiKey> getProviderApiKeysWithHttpInfo(String providerId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'providerId' is set
        if (providerId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'providerId' when calling getProviderApiKeys");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/ProviderDealsController/getProviderApiKeys").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "providerId", providerId));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<OperationStatusListGProviderApiKey> returnType = new ParameterizedTypeReference<OperationStatusListGProviderApiKey>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param providerId  (required)
     * @return OperationStatusGProviderCurrency
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusGProviderCurrency getProviderCurrency(String providerId) throws RestClientException {
        return getProviderCurrencyWithHttpInfo(providerId).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param providerId  (required)
     * @return ResponseEntity&lt;OperationStatusGProviderCurrency&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusGProviderCurrency> getProviderCurrencyWithHttpInfo(String providerId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'providerId' is set
        if (providerId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'providerId' when calling getProviderCurrency");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/ProviderDealsController/getProviderCurrency").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "providerId", providerId));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<OperationStatusGProviderCurrency> returnType = new ParameterizedTypeReference<OperationStatusGProviderCurrency>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param dealId  (required)
     * @return GProviderDeal
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GProviderDeal getProviderDeal(String dealId) throws RestClientException {
        return getProviderDealWithHttpInfo(dealId).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param dealId  (required)
     * @return ResponseEntity&lt;GProviderDeal&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GProviderDeal> getProviderDealWithHttpInfo(String dealId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'dealId' is set
        if (dealId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'dealId' when calling getProviderDeal");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/ProviderDealsController/getProviderDeal").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "dealId", dealId));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<GProviderDeal> returnType = new ParameterizedTypeReference<GProviderDeal>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return List&lt;String&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<String> getProviderDealProviderIds() throws RestClientException {
        return getProviderDealProviderIdsWithHttpInfo().getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;List&lt;String&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<String>> getProviderDealProviderIdsWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/ProviderDealsController/getProviderDealProviderIds").build().toUriString();
        
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

        ParameterizedTypeReference<List<String>> returnType = new ParameterizedTypeReference<List<String>>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param providerId  (optional)
     * @return List&lt;GProviderDeal&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<GProviderDeal> getProviderDeals(String providerId) throws RestClientException {
        return getProviderDealsWithHttpInfo(providerId).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param providerId  (optional)
     * @return ResponseEntity&lt;List&lt;GProviderDeal&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<GProviderDeal>> getProviderDealsWithHttpInfo(String providerId) throws RestClientException {
        Object postBody = null;
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/ProviderDealsController/getProviderDeals").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "providerId", providerId));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List<GProviderDeal>> returnType = new ParameterizedTypeReference<List<GProviderDeal>>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param providerId  (required)
     * @return OperationStatusListGProviderModelPriceInfo
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusListGProviderModelPriceInfo getProviderModelPrices(String providerId) throws RestClientException {
        return getProviderModelPricesWithHttpInfo(providerId).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param providerId  (required)
     * @return ResponseEntity&lt;OperationStatusListGProviderModelPriceInfo&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusListGProviderModelPriceInfo> getProviderModelPricesWithHttpInfo(String providerId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'providerId' is set
        if (providerId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'providerId' when calling getProviderModelPrices");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/ProviderDealsController/getProviderModelPrices").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "providerId", providerId));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<OperationStatusListGProviderModelPriceInfo> returnType = new ParameterizedTypeReference<OperationStatusListGProviderModelPriceInfo>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusGProviderDeal
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusGProviderDeal refreshImportedLimits(ProviderDealKeyRequest body) throws RestClientException {
        return refreshImportedLimitsWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusGProviderDeal&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusGProviderDeal> refreshImportedLimitsWithHttpInfo(ProviderDealKeyRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling refreshImportedLimits");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/ProviderDealsController/refreshImportedLimits").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusGProviderDeal> returnType = new ParameterizedTypeReference<OperationStatusGProviderDeal>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusGProviderDeal
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusGProviderDeal removeApiKey(ProviderDealKeyRequest body) throws RestClientException {
        return removeApiKeyWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusGProviderDeal&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusGProviderDeal> removeApiKeyWithHttpInfo(ProviderDealKeyRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling removeApiKey");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/ProviderDealsController/removeApiKey").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusGProviderDeal> returnType = new ParameterizedTypeReference<OperationStatusGProviderDeal>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusGProviderDeal
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusGProviderDeal updateDescription(ProviderDealDescriptionRequest body) throws RestClientException {
        return updateDescriptionWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusGProviderDeal&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusGProviderDeal> updateDescriptionWithHttpInfo(ProviderDealDescriptionRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateDescription");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/ProviderDealsController/updateDescription").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusGProviderDeal> returnType = new ParameterizedTypeReference<OperationStatusGProviderDeal>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusGProviderDeal
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusGProviderDeal updateFlatConditions(ProviderDealFlatConditionsRequest body) throws RestClientException {
        return updateFlatConditionsWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusGProviderDeal&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusGProviderDeal> updateFlatConditionsWithHttpInfo(ProviderDealFlatConditionsRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateFlatConditions");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/ProviderDealsController/updateFlatConditions").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusGProviderDeal> returnType = new ParameterizedTypeReference<OperationStatusGProviderDeal>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusGProviderDeal
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusGProviderDeal updateModelPricing(ProviderDealModelPricingRequest body) throws RestClientException {
        return updateModelPricingWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusGProviderDeal&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusGProviderDeal> updateModelPricingWithHttpInfo(ProviderDealModelPricingRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateModelPricing");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/ProviderDealsController/updateModelPricing").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusGProviderDeal> returnType = new ParameterizedTypeReference<OperationStatusGProviderDeal>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusGProviderCurrency
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusGProviderCurrency updateProviderCurrency(ProviderCurrencyRequest body) throws RestClientException {
        return updateProviderCurrencyWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusGProviderCurrency&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusGProviderCurrency> updateProviderCurrencyWithHttpInfo(ProviderCurrencyRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateProviderCurrency");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/ProviderDealsController/updateProviderCurrency").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusGProviderCurrency> returnType = new ParameterizedTypeReference<OperationStatusGProviderCurrency>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return OperationStatusGProviderDeal
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public OperationStatusGProviderDeal updateSpendingLimits(ProviderDealSpendingLimitsRequest body) throws RestClientException {
        return updateSpendingLimitsWithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;OperationStatusGProviderDeal&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<OperationStatusGProviderDeal> updateSpendingLimitsWithHttpInfo(ProviderDealSpendingLimitsRequest body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateSpendingLimits");
        }
        String localVarPath = UriComponentsBuilder.fromPath("/api/admin/ProviderDealsController/updateSpendingLimits").build().toUriString();
        
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

        ParameterizedTypeReference<OperationStatusGProviderDeal> returnType = new ParameterizedTypeReference<OperationStatusGProviderDeal>() {};
        return apiClient.invokeAPI(localVarPath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
