# GeboAiClient.SharePointSearchServiceControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**restAggregate**](SharePointSearchServiceControllerApi.md#restAggregate) | **POST** /api/users/SharePointSearchServiceController/aggregate | 
[**restCreateCustomTemplateParamsMap**](SharePointSearchServiceControllerApi.md#restCreateCustomTemplateParamsMap) | **POST** /api/users/SharePointSearchServiceController/createCustomTemplateParamsMap | 
[**restExtractRelatedAnalisysReferences**](SharePointSearchServiceControllerApi.md#restExtractRelatedAnalisysReferences) | **POST** /api/users/SharePointSearchServiceController/extractRelatedAnalisysReferences | 
[**restFindSystemById**](SharePointSearchServiceControllerApi.md#restFindSystemById) | **GET** /api/users/SharePointSearchServiceController/findSystemById | 
[**restFindSystemBySearchResult**](SharePointSearchServiceControllerApi.md#restFindSystemBySearchResult) | **POST** /api/users/SharePointSearchServiceController/findSystemBySearchResult | 
[**restGetCachedCatalogues**](SharePointSearchServiceControllerApi.md#restGetCachedCatalogues) | **GET** /api/users/SharePointSearchServiceController/getCachedCatalogues | 
[**restGetCataloguesListSample**](SharePointSearchServiceControllerApi.md#restGetCataloguesListSample) | **GET** /api/users/SharePointSearchServiceController/getCataloguesListSample | 
[**restGetDescription**](SharePointSearchServiceControllerApi.md#restGetDescription) | **GET** /api/users/SharePointSearchServiceController/getDescription | 
[**restGetId**](SharePointSearchServiceControllerApi.md#restGetId) | **GET** /api/users/SharePointSearchServiceController/getId | 
[**restGetMessagingModuleId**](SharePointSearchServiceControllerApi.md#restGetMessagingModuleId) | **GET** /api/users/SharePointSearchServiceController/getMessagingModuleId | 
[**restGetNativePromptTemplateUseCode**](SharePointSearchServiceControllerApi.md#restGetNativePromptTemplateUseCode) | **GET** /api/users/SharePointSearchServiceController/getNativePromptTemplateUseCode | 
[**restGetProductId**](SharePointSearchServiceControllerApi.md#restGetProductId) | **GET** /api/users/SharePointSearchServiceController/getProductId | 
[**restGetQueriesGenerationPromptUseCode**](SharePointSearchServiceControllerApi.md#restGetQueriesGenerationPromptUseCode) | **GET** /api/users/SharePointSearchServiceController/getQueriesGenerationPromptUseCode | 
[**restGetSearchableSystems**](SharePointSearchServiceControllerApi.md#restGetSearchableSystems) | **GET** /api/users/SharePointSearchServiceController/getSearchableSystems | 
[**restIsEnabled**](SharePointSearchServiceControllerApi.md#restIsEnabled) | **GET** /api/users/SharePointSearchServiceController/isEnabled | 
[**restNativeSearch**](SharePointSearchServiceControllerApi.md#restNativeSearch) | **POST** /api/users/SharePointSearchServiceController/nativeSearch | 
[**restSearch**](SharePointSearchServiceControllerApi.md#restSearch) | **POST** /api/users/SharePointSearchServiceController/search | 

<a name="restAggregate"></a>
# **restAggregate**
> MicrosoftResultsExtractionData restAggregate(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SharePointSearchServiceControllerApi();
let body = new GeboAiClient.AggregateRequestBodyMicrosoftResultsExtractionData(); // AggregateRequestBodyMicrosoftResultsExtractionData | 

apiInstance.restAggregate(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**AggregateRequestBodyMicrosoftResultsExtractionData**](AggregateRequestBodyMicrosoftResultsExtractionData.md)|  | 

### Return type

[**MicrosoftResultsExtractionData**](MicrosoftResultsExtractionData.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="restCreateCustomTemplateParamsMap"></a>
# **restCreateCustomTemplateParamsMap**
> {&#x27;String&#x27;: Object} restCreateCustomTemplateParamsMap(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SharePointSearchServiceControllerApi();
let body = new GeboAiClient.CustomTemplateParamsRequestBody(); // CustomTemplateParamsRequestBody | 

apiInstance.restCreateCustomTemplateParamsMap(body).then((data) => {
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

<a name="restExtractRelatedAnalisysReferences"></a>
# **restExtractRelatedAnalisysReferences**
> SearchResultAnalisysOutcome restExtractRelatedAnalisysReferences(body, systemId)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SharePointSearchServiceControllerApi();
let body = new GeboAiClient.MicrosoftResultsExtractionData(); // MicrosoftResultsExtractionData | 
let systemId = "systemId_example"; // String | 

apiInstance.restExtractRelatedAnalisysReferences(body, systemId).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**MicrosoftResultsExtractionData**](MicrosoftResultsExtractionData.md)|  | 
 **systemId** | **String**|  | 

### Return type

[**SearchResultAnalisysOutcome**](SearchResultAnalisysOutcome.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="restFindSystemById"></a>
# **restFindSystemById**
> SearchableSystemMetaData restFindSystemById(systemId)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SharePointSearchServiceControllerApi();
let systemId = "systemId_example"; // String | 

apiInstance.restFindSystemById(systemId).then((data) => {
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

<a name="restFindSystemBySearchResult"></a>
# **restFindSystemBySearchResult**
> SearchableSystemMetaData restFindSystemBySearchResult(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SharePointSearchServiceControllerApi();
let body = new GeboAiClient.SearchResult(); // SearchResult | 

apiInstance.restFindSystemBySearchResult(body).then((data) => {
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

<a name="restGetCachedCatalogues"></a>
# **restGetCachedCatalogues**
> [CatalogueSample] restGetCachedCatalogues(opts)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SharePointSearchServiceControllerApi();
let opts = { 
  'systemConfigurationCode': "systemConfigurationCode_example" // String | 
};
apiInstance.restGetCachedCatalogues(opts).then((data) => {
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

<a name="restGetCataloguesListSample"></a>
# **restGetCataloguesListSample**
> [CatalogueSample] restGetCataloguesListSample(configurationCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SharePointSearchServiceControllerApi();
let configurationCode = "configurationCode_example"; // String | 

apiInstance.restGetCataloguesListSample(configurationCode).then((data) => {
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

<a name="restGetDescription"></a>
# **restGetDescription**
> &#x27;String&#x27; restGetDescription()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SharePointSearchServiceControllerApi();
apiInstance.restGetDescription().then((data) => {
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

<a name="restGetId"></a>
# **restGetId**
> &#x27;String&#x27; restGetId()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SharePointSearchServiceControllerApi();
apiInstance.restGetId().then((data) => {
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

<a name="restGetMessagingModuleId"></a>
# **restGetMessagingModuleId**
> &#x27;String&#x27; restGetMessagingModuleId()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SharePointSearchServiceControllerApi();
apiInstance.restGetMessagingModuleId().then((data) => {
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

<a name="restGetNativePromptTemplateUseCode"></a>
# **restGetNativePromptTemplateUseCode**
> &#x27;String&#x27; restGetNativePromptTemplateUseCode()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SharePointSearchServiceControllerApi();
apiInstance.restGetNativePromptTemplateUseCode().then((data) => {
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

<a name="restGetProductId"></a>
# **restGetProductId**
> &#x27;String&#x27; restGetProductId()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SharePointSearchServiceControllerApi();
apiInstance.restGetProductId().then((data) => {
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

<a name="restGetQueriesGenerationPromptUseCode"></a>
# **restGetQueriesGenerationPromptUseCode**
> &#x27;String&#x27; restGetQueriesGenerationPromptUseCode()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SharePointSearchServiceControllerApi();
apiInstance.restGetQueriesGenerationPromptUseCode().then((data) => {
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

<a name="restGetSearchableSystems"></a>
# **restGetSearchableSystems**
> [SearchableSystemMetaData] restGetSearchableSystems()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SharePointSearchServiceControllerApi();
apiInstance.restGetSearchableSystems().then((data) => {
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

<a name="restIsEnabled"></a>
# **restIsEnabled**
> &#x27;Boolean&#x27; restIsEnabled()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SharePointSearchServiceControllerApi();
apiInstance.restIsEnabled().then((data) => {
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

<a name="restNativeSearch"></a>
# **restNativeSearch**
> [SearchResult] restNativeSearch(body, systemId, nEntryLimit)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SharePointSearchServiceControllerApi();
let body = new GeboAiClient.SharePointSearchFilter(); // SharePointSearchFilter | 
let systemId = "systemId_example"; // String | 
let nEntryLimit = 56; // Number | 

apiInstance.restNativeSearch(body, systemId, nEntryLimit).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SharePointSearchFilter**](SharePointSearchFilter.md)|  | 
 **systemId** | **String**|  | 
 **nEntryLimit** | **Number**|  | 

### Return type

[**[SearchResult]**](SearchResult.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="restSearch"></a>
# **restSearch**
> [SearchResult] restSearch(body, systemId, nEntryLimit)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SharePointSearchServiceControllerApi();
let body = new GeboAiClient.SearchQuery(); // SearchQuery | 
let systemId = "systemId_example"; // String | 
let nEntryLimit = 56; // Number | 

apiInstance.restSearch(body, systemId, nEntryLimit).then((data) => {
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

