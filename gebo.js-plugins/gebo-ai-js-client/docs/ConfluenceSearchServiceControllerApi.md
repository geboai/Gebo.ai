# GeboAiClient.ConfluenceSearchServiceControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**restAggregate3**](ConfluenceSearchServiceControllerApi.md#restAggregate3) | **POST** /api/users/ConfluenceSearchServiceController/aggregate | 
[**restCreateCustomTemplateParamsMap2**](ConfluenceSearchServiceControllerApi.md#restCreateCustomTemplateParamsMap2) | **POST** /api/users/ConfluenceSearchServiceController/createCustomTemplateParamsMap | 
[**restExtractRelatedAnalisysReferences3**](ConfluenceSearchServiceControllerApi.md#restExtractRelatedAnalisysReferences3) | **POST** /api/users/ConfluenceSearchServiceController/extractRelatedAnalisysReferences | 
[**restFindSystemById3**](ConfluenceSearchServiceControllerApi.md#restFindSystemById3) | **GET** /api/users/ConfluenceSearchServiceController/findSystemById | 
[**restFindSystemBySearchResult3**](ConfluenceSearchServiceControllerApi.md#restFindSystemBySearchResult3) | **POST** /api/users/ConfluenceSearchServiceController/findSystemBySearchResult | 
[**restGetCachedCatalogues3**](ConfluenceSearchServiceControllerApi.md#restGetCachedCatalogues3) | **GET** /api/users/ConfluenceSearchServiceController/getCachedCatalogues | 
[**restGetCataloguesListSample3**](ConfluenceSearchServiceControllerApi.md#restGetCataloguesListSample3) | **GET** /api/users/ConfluenceSearchServiceController/getCataloguesListSample | 
[**restGetDescription3**](ConfluenceSearchServiceControllerApi.md#restGetDescription3) | **GET** /api/users/ConfluenceSearchServiceController/getDescription | 
[**restGetId3**](ConfluenceSearchServiceControllerApi.md#restGetId3) | **GET** /api/users/ConfluenceSearchServiceController/getId | 
[**restGetMessagingModuleId3**](ConfluenceSearchServiceControllerApi.md#restGetMessagingModuleId3) | **GET** /api/users/ConfluenceSearchServiceController/getMessagingModuleId | 
[**restGetNativePromptTemplateUseCode2**](ConfluenceSearchServiceControllerApi.md#restGetNativePromptTemplateUseCode2) | **GET** /api/users/ConfluenceSearchServiceController/getNativePromptTemplateUseCode | 
[**restGetProductId3**](ConfluenceSearchServiceControllerApi.md#restGetProductId3) | **GET** /api/users/ConfluenceSearchServiceController/getProductId | 
[**restGetQueriesGenerationPromptUseCode3**](ConfluenceSearchServiceControllerApi.md#restGetQueriesGenerationPromptUseCode3) | **GET** /api/users/ConfluenceSearchServiceController/getQueriesGenerationPromptUseCode | 
[**restGetSearchableSystems3**](ConfluenceSearchServiceControllerApi.md#restGetSearchableSystems3) | **GET** /api/users/ConfluenceSearchServiceController/getSearchableSystems | 
[**restIsEnabled3**](ConfluenceSearchServiceControllerApi.md#restIsEnabled3) | **GET** /api/users/ConfluenceSearchServiceController/isEnabled | 
[**restNativeSearch2**](ConfluenceSearchServiceControllerApi.md#restNativeSearch2) | **POST** /api/users/ConfluenceSearchServiceController/nativeSearch | 
[**restSearch3**](ConfluenceSearchServiceControllerApi.md#restSearch3) | **POST** /api/users/ConfluenceSearchServiceController/search | 

<a name="restAggregate3"></a>
# **restAggregate3**
> ConfluenceResultsExtractionData restAggregate3(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ConfluenceSearchServiceControllerApi();
let body = new GeboAiClient.AggregateRequestBodyConfluenceResultsExtractionData(); // AggregateRequestBodyConfluenceResultsExtractionData | 

apiInstance.restAggregate3(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**AggregateRequestBodyConfluenceResultsExtractionData**](AggregateRequestBodyConfluenceResultsExtractionData.md)|  | 

### Return type

[**ConfluenceResultsExtractionData**](ConfluenceResultsExtractionData.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="restCreateCustomTemplateParamsMap2"></a>
# **restCreateCustomTemplateParamsMap2**
> {&#x27;String&#x27;: Object} restCreateCustomTemplateParamsMap2(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ConfluenceSearchServiceControllerApi();
let body = new GeboAiClient.CustomTemplateParamsRequestBody(); // CustomTemplateParamsRequestBody | 

apiInstance.restCreateCustomTemplateParamsMap2(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**CustomTemplateParamsRequestBody**](CustomTemplateParamsRequestBody.md)|  | 

### Return type

**{&#x27;String&#x27;: Object}**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="restExtractRelatedAnalisysReferences3"></a>
# **restExtractRelatedAnalisysReferences3**
> SearchResultAnalisysOutcome restExtractRelatedAnalisysReferences3(body, systemId)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ConfluenceSearchServiceControllerApi();
let body = new GeboAiClient.ConfluenceResultsExtractionData(); // ConfluenceResultsExtractionData | 
let systemId = "systemId_example"; // String | 

apiInstance.restExtractRelatedAnalisysReferences3(body, systemId).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ConfluenceResultsExtractionData**](ConfluenceResultsExtractionData.md)|  | 
 **systemId** | **String**|  | 

### Return type

[**SearchResultAnalisysOutcome**](SearchResultAnalisysOutcome.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="restFindSystemById3"></a>
# **restFindSystemById3**
> SearchableSystemMetaData restFindSystemById3(systemId)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ConfluenceSearchServiceControllerApi();
let systemId = "systemId_example"; // String | 

apiInstance.restFindSystemById3(systemId).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **systemId** | **String**|  | 

### Return type

[**SearchableSystemMetaData**](SearchableSystemMetaData.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="restFindSystemBySearchResult3"></a>
# **restFindSystemBySearchResult3**
> SearchableSystemMetaData restFindSystemBySearchResult3(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ConfluenceSearchServiceControllerApi();
let body = new GeboAiClient.SearchResult(); // SearchResult | 

apiInstance.restFindSystemBySearchResult3(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SearchResult**](SearchResult.md)|  | 

### Return type

[**SearchableSystemMetaData**](SearchableSystemMetaData.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="restGetCachedCatalogues3"></a>
# **restGetCachedCatalogues3**
> [CatalogueSample] restGetCachedCatalogues3(opts)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ConfluenceSearchServiceControllerApi();
let opts = { 
  'systemConfigurationCode': "systemConfigurationCode_example" // String | 
};
apiInstance.restGetCachedCatalogues3(opts).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **systemConfigurationCode** | **String**|  | [optional] 

### Return type

[**[CatalogueSample]**](CatalogueSample.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="restGetCataloguesListSample3"></a>
# **restGetCataloguesListSample3**
> [CatalogueSample] restGetCataloguesListSample3(configurationCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ConfluenceSearchServiceControllerApi();
let configurationCode = "configurationCode_example"; // String | 

apiInstance.restGetCataloguesListSample3(configurationCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **configurationCode** | **String**|  | 

### Return type

[**[CatalogueSample]**](CatalogueSample.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="restGetDescription3"></a>
# **restGetDescription3**
> &#x27;String&#x27; restGetDescription3()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ConfluenceSearchServiceControllerApi();
apiInstance.restGetDescription3().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

**&#x27;String&#x27;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="restGetId3"></a>
# **restGetId3**
> &#x27;String&#x27; restGetId3()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ConfluenceSearchServiceControllerApi();
apiInstance.restGetId3().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

**&#x27;String&#x27;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="restGetMessagingModuleId3"></a>
# **restGetMessagingModuleId3**
> &#x27;String&#x27; restGetMessagingModuleId3()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ConfluenceSearchServiceControllerApi();
apiInstance.restGetMessagingModuleId3().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

**&#x27;String&#x27;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="restGetNativePromptTemplateUseCode2"></a>
# **restGetNativePromptTemplateUseCode2**
> &#x27;String&#x27; restGetNativePromptTemplateUseCode2()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ConfluenceSearchServiceControllerApi();
apiInstance.restGetNativePromptTemplateUseCode2().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

**&#x27;String&#x27;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="restGetProductId3"></a>
# **restGetProductId3**
> &#x27;String&#x27; restGetProductId3()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ConfluenceSearchServiceControllerApi();
apiInstance.restGetProductId3().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

**&#x27;String&#x27;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="restGetQueriesGenerationPromptUseCode3"></a>
# **restGetQueriesGenerationPromptUseCode3**
> &#x27;String&#x27; restGetQueriesGenerationPromptUseCode3()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ConfluenceSearchServiceControllerApi();
apiInstance.restGetQueriesGenerationPromptUseCode3().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

**&#x27;String&#x27;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="restGetSearchableSystems3"></a>
# **restGetSearchableSystems3**
> [SearchableSystemMetaData] restGetSearchableSystems3()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ConfluenceSearchServiceControllerApi();
apiInstance.restGetSearchableSystems3().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[SearchableSystemMetaData]**](SearchableSystemMetaData.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="restIsEnabled3"></a>
# **restIsEnabled3**
> &#x27;Boolean&#x27; restIsEnabled3()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ConfluenceSearchServiceControllerApi();
apiInstance.restIsEnabled3().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

**&#x27;Boolean&#x27;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="restNativeSearch2"></a>
# **restNativeSearch2**
> [SearchResult] restNativeSearch2(body, systemId, nEntryLimit)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ConfluenceSearchServiceControllerApi();
let body = new GeboAiClient.ConfluenceContentSearchFilter(); // ConfluenceContentSearchFilter | 
let systemId = "systemId_example"; // String | 
let nEntryLimit = 56; // Number | 

apiInstance.restNativeSearch2(body, systemId, nEntryLimit).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ConfluenceContentSearchFilter**](ConfluenceContentSearchFilter.md)|  | 
 **systemId** | **String**|  | 
 **nEntryLimit** | **Number**|  | 

### Return type

[**[SearchResult]**](SearchResult.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="restSearch3"></a>
# **restSearch3**
> [SearchResult] restSearch3(body, systemId, nEntryLimit)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ConfluenceSearchServiceControllerApi();
let body = new GeboAiClient.SearchQuery(); // SearchQuery | 
let systemId = "systemId_example"; // String | 
let nEntryLimit = 56; // Number | 

apiInstance.restSearch3(body, systemId, nEntryLimit).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SearchQuery**](SearchQuery.md)|  | 
 **systemId** | **String**|  | 
 **nEntryLimit** | **Number**|  | 

### Return type

[**[SearchResult]**](SearchResult.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

