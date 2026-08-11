# GeboAiClient.JiraSearchServiceControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**restAggregate1**](JiraSearchServiceControllerApi.md#restAggregate1) | **POST** /api/users/JiraSearchServiceController/aggregate | 
[**restCreateCustomTemplateParamsMap1**](JiraSearchServiceControllerApi.md#restCreateCustomTemplateParamsMap1) | **POST** /api/users/JiraSearchServiceController/createCustomTemplateParamsMap | 
[**restExtractRelatedAnalisysReferences1**](JiraSearchServiceControllerApi.md#restExtractRelatedAnalisysReferences1) | **POST** /api/users/JiraSearchServiceController/extractRelatedAnalisysReferences | 
[**restFindSystemById1**](JiraSearchServiceControllerApi.md#restFindSystemById1) | **GET** /api/users/JiraSearchServiceController/findSystemById | 
[**restFindSystemBySearchResult1**](JiraSearchServiceControllerApi.md#restFindSystemBySearchResult1) | **POST** /api/users/JiraSearchServiceController/findSystemBySearchResult | 
[**restGetCachedCatalogues1**](JiraSearchServiceControllerApi.md#restGetCachedCatalogues1) | **GET** /api/users/JiraSearchServiceController/getCachedCatalogues | 
[**restGetCataloguesListSample1**](JiraSearchServiceControllerApi.md#restGetCataloguesListSample1) | **GET** /api/users/JiraSearchServiceController/getCataloguesListSample | 
[**restGetDescription1**](JiraSearchServiceControllerApi.md#restGetDescription1) | **GET** /api/users/JiraSearchServiceController/getDescription | 
[**restGetId1**](JiraSearchServiceControllerApi.md#restGetId1) | **GET** /api/users/JiraSearchServiceController/getId | 
[**restGetMessagingModuleId1**](JiraSearchServiceControllerApi.md#restGetMessagingModuleId1) | **GET** /api/users/JiraSearchServiceController/getMessagingModuleId | 
[**restGetNativePromptTemplateUseCode1**](JiraSearchServiceControllerApi.md#restGetNativePromptTemplateUseCode1) | **GET** /api/users/JiraSearchServiceController/getNativePromptTemplateUseCode | 
[**restGetProductId1**](JiraSearchServiceControllerApi.md#restGetProductId1) | **GET** /api/users/JiraSearchServiceController/getProductId | 
[**restGetQueriesGenerationPromptUseCode1**](JiraSearchServiceControllerApi.md#restGetQueriesGenerationPromptUseCode1) | **GET** /api/users/JiraSearchServiceController/getQueriesGenerationPromptUseCode | 
[**restGetSearchableSystems1**](JiraSearchServiceControllerApi.md#restGetSearchableSystems1) | **GET** /api/users/JiraSearchServiceController/getSearchableSystems | 
[**restIsEnabled1**](JiraSearchServiceControllerApi.md#restIsEnabled1) | **GET** /api/users/JiraSearchServiceController/isEnabled | 
[**restNativeSearch1**](JiraSearchServiceControllerApi.md#restNativeSearch1) | **POST** /api/users/JiraSearchServiceController/nativeSearch | 
[**restSearch1**](JiraSearchServiceControllerApi.md#restSearch1) | **POST** /api/users/JiraSearchServiceController/search | 

<a name="restAggregate1"></a>
# **restAggregate1**
> JiraResultsExtractionData restAggregate1(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JiraSearchServiceControllerApi();
let body = new GeboAiClient.AggregateRequestBodyJiraResultsExtractionData(); // AggregateRequestBodyJiraResultsExtractionData | 

apiInstance.restAggregate1(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**AggregateRequestBodyJiraResultsExtractionData**](AggregateRequestBodyJiraResultsExtractionData.md)|  | 

### Return type

[**JiraResultsExtractionData**](JiraResultsExtractionData.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="restCreateCustomTemplateParamsMap1"></a>
# **restCreateCustomTemplateParamsMap1**
> {&#x27;String&#x27;: Object} restCreateCustomTemplateParamsMap1(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JiraSearchServiceControllerApi();
let body = new GeboAiClient.CustomTemplateParamsRequestBody(); // CustomTemplateParamsRequestBody | 

apiInstance.restCreateCustomTemplateParamsMap1(body).then((data) => {
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

<a name="restExtractRelatedAnalisysReferences1"></a>
# **restExtractRelatedAnalisysReferences1**
> SearchResultAnalisysOutcome restExtractRelatedAnalisysReferences1(body, systemId)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JiraSearchServiceControllerApi();
let body = new GeboAiClient.JiraResultsExtractionData(); // JiraResultsExtractionData | 
let systemId = "systemId_example"; // String | 

apiInstance.restExtractRelatedAnalisysReferences1(body, systemId).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**JiraResultsExtractionData**](JiraResultsExtractionData.md)|  | 
 **systemId** | **String**|  | 

### Return type

[**SearchResultAnalisysOutcome**](SearchResultAnalisysOutcome.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="restFindSystemById1"></a>
# **restFindSystemById1**
> SearchableSystemMetaData restFindSystemById1(systemId)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JiraSearchServiceControllerApi();
let systemId = "systemId_example"; // String | 

apiInstance.restFindSystemById1(systemId).then((data) => {
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

<a name="restFindSystemBySearchResult1"></a>
# **restFindSystemBySearchResult1**
> SearchableSystemMetaData restFindSystemBySearchResult1(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JiraSearchServiceControllerApi();
let body = new GeboAiClient.SearchResult(); // SearchResult | 

apiInstance.restFindSystemBySearchResult1(body).then((data) => {
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

<a name="restGetCachedCatalogues1"></a>
# **restGetCachedCatalogues1**
> [CatalogueSample] restGetCachedCatalogues1(opts)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JiraSearchServiceControllerApi();
let opts = { 
  'systemConfigurationCode': "systemConfigurationCode_example" // String | 
};
apiInstance.restGetCachedCatalogues1(opts).then((data) => {
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

<a name="restGetCataloguesListSample1"></a>
# **restGetCataloguesListSample1**
> [CatalogueSample] restGetCataloguesListSample1(configurationCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JiraSearchServiceControllerApi();
let configurationCode = "configurationCode_example"; // String | 

apiInstance.restGetCataloguesListSample1(configurationCode).then((data) => {
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

<a name="restGetDescription1"></a>
# **restGetDescription1**
> &#x27;String&#x27; restGetDescription1()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JiraSearchServiceControllerApi();
apiInstance.restGetDescription1().then((data) => {
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

<a name="restGetId1"></a>
# **restGetId1**
> &#x27;String&#x27; restGetId1()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JiraSearchServiceControllerApi();
apiInstance.restGetId1().then((data) => {
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

<a name="restGetMessagingModuleId1"></a>
# **restGetMessagingModuleId1**
> &#x27;String&#x27; restGetMessagingModuleId1()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JiraSearchServiceControllerApi();
apiInstance.restGetMessagingModuleId1().then((data) => {
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

<a name="restGetNativePromptTemplateUseCode1"></a>
# **restGetNativePromptTemplateUseCode1**
> &#x27;String&#x27; restGetNativePromptTemplateUseCode1()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JiraSearchServiceControllerApi();
apiInstance.restGetNativePromptTemplateUseCode1().then((data) => {
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

<a name="restGetProductId1"></a>
# **restGetProductId1**
> &#x27;String&#x27; restGetProductId1()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JiraSearchServiceControllerApi();
apiInstance.restGetProductId1().then((data) => {
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

<a name="restGetQueriesGenerationPromptUseCode1"></a>
# **restGetQueriesGenerationPromptUseCode1**
> &#x27;String&#x27; restGetQueriesGenerationPromptUseCode1()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JiraSearchServiceControllerApi();
apiInstance.restGetQueriesGenerationPromptUseCode1().then((data) => {
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

<a name="restGetSearchableSystems1"></a>
# **restGetSearchableSystems1**
> [SearchableSystemMetaData] restGetSearchableSystems1()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JiraSearchServiceControllerApi();
apiInstance.restGetSearchableSystems1().then((data) => {
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

<a name="restIsEnabled1"></a>
# **restIsEnabled1**
> &#x27;Boolean&#x27; restIsEnabled1()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JiraSearchServiceControllerApi();
apiInstance.restIsEnabled1().then((data) => {
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

<a name="restNativeSearch1"></a>
# **restNativeSearch1**
> [SearchResult] restNativeSearch1(body, systemId, nEntryLimit)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JiraSearchServiceControllerApi();
let body = new GeboAiClient.JiraIssuesSearchFilter(); // JiraIssuesSearchFilter | 
let systemId = "systemId_example"; // String | 
let nEntryLimit = 56; // Number | 

apiInstance.restNativeSearch1(body, systemId, nEntryLimit).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**JiraIssuesSearchFilter**](JiraIssuesSearchFilter.md)|  | 
 **systemId** | **String**|  | 
 **nEntryLimit** | **Number**|  | 

### Return type

[**[SearchResult]**](SearchResult.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="restSearch1"></a>
# **restSearch1**
> [SearchResult] restSearch1(body, systemId, nEntryLimit)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JiraSearchServiceControllerApi();
let body = new GeboAiClient.SearchQuery(); // SearchQuery | 
let systemId = "systemId_example"; // String | 
let nEntryLimit = 56; // Number | 

apiInstance.restSearch1(body, systemId, nEntryLimit).then((data) => {
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

