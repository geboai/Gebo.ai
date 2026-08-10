# GeboAiClient.GoogleDriveSearchServiceControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**restAggregate2**](GoogleDriveSearchServiceControllerApi.md#restAggregate2) | **POST** /api/users/GoogleDriveSearchServiceController/aggregate | 
[**restExtractRelatedAnalisysReferences2**](GoogleDriveSearchServiceControllerApi.md#restExtractRelatedAnalisysReferences2) | **POST** /api/users/GoogleDriveSearchServiceController/extractRelatedAnalisysReferences | 
[**restFindSystemById2**](GoogleDriveSearchServiceControllerApi.md#restFindSystemById2) | **GET** /api/users/GoogleDriveSearchServiceController/findSystemById | 
[**restFindSystemBySearchResult2**](GoogleDriveSearchServiceControllerApi.md#restFindSystemBySearchResult2) | **POST** /api/users/GoogleDriveSearchServiceController/findSystemBySearchResult | 
[**restGetCachedCatalogues2**](GoogleDriveSearchServiceControllerApi.md#restGetCachedCatalogues2) | **GET** /api/users/GoogleDriveSearchServiceController/getCachedCatalogues | 
[**restGetCataloguesListSample2**](GoogleDriveSearchServiceControllerApi.md#restGetCataloguesListSample2) | **GET** /api/users/GoogleDriveSearchServiceController/getCataloguesListSample | 
[**restGetDescription2**](GoogleDriveSearchServiceControllerApi.md#restGetDescription2) | **GET** /api/users/GoogleDriveSearchServiceController/getDescription | 
[**restGetId2**](GoogleDriveSearchServiceControllerApi.md#restGetId2) | **GET** /api/users/GoogleDriveSearchServiceController/getId | 
[**restGetMessagingModuleId2**](GoogleDriveSearchServiceControllerApi.md#restGetMessagingModuleId2) | **GET** /api/users/GoogleDriveSearchServiceController/getMessagingModuleId | 
[**restGetProductId2**](GoogleDriveSearchServiceControllerApi.md#restGetProductId2) | **GET** /api/users/GoogleDriveSearchServiceController/getProductId | 
[**restGetQueriesGenerationPromptUseCode2**](GoogleDriveSearchServiceControllerApi.md#restGetQueriesGenerationPromptUseCode2) | **GET** /api/users/GoogleDriveSearchServiceController/getQueriesGenerationPromptUseCode | 
[**restGetSearchableSystems2**](GoogleDriveSearchServiceControllerApi.md#restGetSearchableSystems2) | **GET** /api/users/GoogleDriveSearchServiceController/getSearchableSystems | 
[**restIsEnabled2**](GoogleDriveSearchServiceControllerApi.md#restIsEnabled2) | **GET** /api/users/GoogleDriveSearchServiceController/isEnabled | 
[**restSearch2**](GoogleDriveSearchServiceControllerApi.md#restSearch2) | **POST** /api/users/GoogleDriveSearchServiceController/search | 

<a name="restAggregate2"></a>
# **restAggregate2**
> GoogleDriveResultsExtractionData restAggregate2(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleDriveSearchServiceControllerApi();
let body = new GeboAiClient.AggregateRequestBodyGoogleDriveResultsExtractionData(); // AggregateRequestBodyGoogleDriveResultsExtractionData | 

apiInstance.restAggregate2(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**AggregateRequestBodyGoogleDriveResultsExtractionData**](AggregateRequestBodyGoogleDriveResultsExtractionData.md)|  | 

### Return type

[**GoogleDriveResultsExtractionData**](GoogleDriveResultsExtractionData.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="restExtractRelatedAnalisysReferences2"></a>
# **restExtractRelatedAnalisysReferences2**
> SearchResultAnalisysOutcome restExtractRelatedAnalisysReferences2(body, systemId)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleDriveSearchServiceControllerApi();
let body = new GeboAiClient.GoogleDriveResultsExtractionData(); // GoogleDriveResultsExtractionData | 
let systemId = "systemId_example"; // String | 

apiInstance.restExtractRelatedAnalisysReferences2(body, systemId).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GoogleDriveResultsExtractionData**](GoogleDriveResultsExtractionData.md)|  | 
 **systemId** | **String**|  | 

### Return type

[**SearchResultAnalisysOutcome**](SearchResultAnalisysOutcome.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="restFindSystemById2"></a>
# **restFindSystemById2**
> SearchableSystemMetaData restFindSystemById2(systemId)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleDriveSearchServiceControllerApi();
let systemId = "systemId_example"; // String | 

apiInstance.restFindSystemById2(systemId).then((data) => {
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

<a name="restFindSystemBySearchResult2"></a>
# **restFindSystemBySearchResult2**
> SearchableSystemMetaData restFindSystemBySearchResult2(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleDriveSearchServiceControllerApi();
let body = new GeboAiClient.SearchResult(); // SearchResult | 

apiInstance.restFindSystemBySearchResult2(body).then((data) => {
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

<a name="restGetCachedCatalogues2"></a>
# **restGetCachedCatalogues2**
> [CatalogueSample] restGetCachedCatalogues2(opts)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleDriveSearchServiceControllerApi();
let opts = { 
  'systemConfigurationCode': "systemConfigurationCode_example" // String | 
};
apiInstance.restGetCachedCatalogues2(opts).then((data) => {
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

<a name="restGetCataloguesListSample2"></a>
# **restGetCataloguesListSample2**
> [CatalogueSample] restGetCataloguesListSample2(configurationCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleDriveSearchServiceControllerApi();
let configurationCode = "configurationCode_example"; // String | 

apiInstance.restGetCataloguesListSample2(configurationCode).then((data) => {
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

<a name="restGetDescription2"></a>
# **restGetDescription2**
> &#x27;String&#x27; restGetDescription2()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleDriveSearchServiceControllerApi();
apiInstance.restGetDescription2().then((data) => {
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

<a name="restGetId2"></a>
# **restGetId2**
> &#x27;String&#x27; restGetId2()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleDriveSearchServiceControllerApi();
apiInstance.restGetId2().then((data) => {
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

<a name="restGetMessagingModuleId2"></a>
# **restGetMessagingModuleId2**
> &#x27;String&#x27; restGetMessagingModuleId2()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleDriveSearchServiceControllerApi();
apiInstance.restGetMessagingModuleId2().then((data) => {
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

<a name="restGetProductId2"></a>
# **restGetProductId2**
> &#x27;String&#x27; restGetProductId2()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleDriveSearchServiceControllerApi();
apiInstance.restGetProductId2().then((data) => {
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

<a name="restGetQueriesGenerationPromptUseCode2"></a>
# **restGetQueriesGenerationPromptUseCode2**
> &#x27;String&#x27; restGetQueriesGenerationPromptUseCode2()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleDriveSearchServiceControllerApi();
apiInstance.restGetQueriesGenerationPromptUseCode2().then((data) => {
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

<a name="restGetSearchableSystems2"></a>
# **restGetSearchableSystems2**
> [SearchableSystemMetaData] restGetSearchableSystems2()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleDriveSearchServiceControllerApi();
apiInstance.restGetSearchableSystems2().then((data) => {
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

<a name="restIsEnabled2"></a>
# **restIsEnabled2**
> &#x27;Boolean&#x27; restIsEnabled2()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleDriveSearchServiceControllerApi();
apiInstance.restIsEnabled2().then((data) => {
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

<a name="restSearch2"></a>
# **restSearch2**
> [SearchResult] restSearch2(body, systemId, nEntryLimit)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleDriveSearchServiceControllerApi();
let body = new GeboAiClient.SearchQuery(); // SearchQuery | 
let systemId = "systemId_example"; // String | 
let nEntryLimit = 56; // Number | 

apiInstance.restSearch2(body, systemId, nEntryLimit).then((data) => {
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

