# GeboAiClient.GraphRagConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteGraphRagExtractionConfig**](GraphRagConfigurationControllerApi.md#deleteGraphRagExtractionConfig) | **DELETE** /api/admin/GraphRagConfigurationController/deleteGraphRagExtractionConfig | 
[**findGraphRagExtractionConfigByCode**](GraphRagConfigurationControllerApi.md#findGraphRagExtractionConfigByCode) | **GET** /api/admin/GraphRagConfigurationController/findGraphRagExtractionConfigByCode | 
[**findGraphRagExtractionConfigByKnowledgeBase**](GraphRagConfigurationControllerApi.md#findGraphRagExtractionConfigByKnowledgeBase) | **GET** /api/admin/GraphRagConfigurationController/findGraphRagExtractionConfigByKnowledgeBase | 
[**findGraphRagExtractionConfigByKnowledgeBaseAndProjectCode**](GraphRagConfigurationControllerApi.md#findGraphRagExtractionConfigByKnowledgeBaseAndProjectCode) | **GET** /api/admin/GraphRagConfigurationController/findGraphRagExtractionConfigByKnowledgeBaseAndProjectCode | 
[**findGraphRagExtractionConfigByProjectEndpointGObjectRef**](GraphRagConfigurationControllerApi.md#findGraphRagExtractionConfigByProjectEndpointGObjectRef) | **POST** /api/admin/GraphRagConfigurationController/findGraphRagExtractionConfigByProjectEndpointGObjectRef | 
[**getDefaultGraphRagExtractionConfig**](GraphRagConfigurationControllerApi.md#getDefaultGraphRagExtractionConfig) | **GET** /api/admin/GraphRagConfigurationController/getDefaultGraphRagExtractionConfig | 
[**getSystemGraphRagExtractionConfig**](GraphRagConfigurationControllerApi.md#getSystemGraphRagExtractionConfig) | **GET** /api/admin/GraphRagConfigurationController/getSystemGraphRagExtractionConfig | 
[**instertGraphRagExtractionConfig**](GraphRagConfigurationControllerApi.md#instertGraphRagExtractionConfig) | **POST** /api/admin/GraphRagConfigurationController/instertGraphRagExtractionConfig | 
[**saveGraphRagExtractionConfig**](GraphRagConfigurationControllerApi.md#saveGraphRagExtractionConfig) | **POST** /api/admin/GraphRagConfigurationController/saveGraphRagExtractionConfig | 

<a name="deleteGraphRagExtractionConfig"></a>
# **deleteGraphRagExtractionConfig**
> deleteGraphRagExtractionConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GraphRagConfigurationControllerApi();
let body = new GeboAiClient.GraphRagExtractionConfig(); // GraphRagExtractionConfig | 

apiInstance.deleteGraphRagExtractionConfig(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GraphRagExtractionConfig**](GraphRagExtractionConfig.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="findGraphRagExtractionConfigByCode"></a>
# **findGraphRagExtractionConfigByCode**
> GraphRagExtractionConfig findGraphRagExtractionConfigByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GraphRagConfigurationControllerApi();
let code = "code_example"; // String | 

apiInstance.findGraphRagExtractionConfigByCode(code).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  | 

### Return type

[**GraphRagExtractionConfig**](GraphRagExtractionConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findGraphRagExtractionConfigByKnowledgeBase"></a>
# **findGraphRagExtractionConfigByKnowledgeBase**
> [GraphRagExtractionConfig] findGraphRagExtractionConfigByKnowledgeBase(knowledgeBaseCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GraphRagConfigurationControllerApi();
let knowledgeBaseCode = "knowledgeBaseCode_example"; // String | 

apiInstance.findGraphRagExtractionConfigByKnowledgeBase(knowledgeBaseCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **knowledgeBaseCode** | **String**|  | 

### Return type

[**[GraphRagExtractionConfig]**](GraphRagExtractionConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findGraphRagExtractionConfigByKnowledgeBaseAndProjectCode"></a>
# **findGraphRagExtractionConfigByKnowledgeBaseAndProjectCode**
> [GraphRagExtractionConfig] findGraphRagExtractionConfigByKnowledgeBaseAndProjectCode(knowledgeBaseCode, projectCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GraphRagConfigurationControllerApi();
let knowledgeBaseCode = "knowledgeBaseCode_example"; // String | 
let projectCode = "projectCode_example"; // String | 

apiInstance.findGraphRagExtractionConfigByKnowledgeBaseAndProjectCode(knowledgeBaseCode, projectCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **knowledgeBaseCode** | **String**|  | 
 **projectCode** | **String**|  | 

### Return type

[**[GraphRagExtractionConfig]**](GraphRagExtractionConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findGraphRagExtractionConfigByProjectEndpointGObjectRef"></a>
# **findGraphRagExtractionConfigByProjectEndpointGObjectRef**
> [GraphRagExtractionConfig] findGraphRagExtractionConfigByProjectEndpointGObjectRef(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GraphRagConfigurationControllerApi();
let body = new GeboAiClient.GObjectRefGProjectEndpoint(); // GObjectRefGProjectEndpoint | 

apiInstance.findGraphRagExtractionConfigByProjectEndpointGObjectRef(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GObjectRefGProjectEndpoint**](GObjectRefGProjectEndpoint.md)|  | 

### Return type

[**[GraphRagExtractionConfig]**](GraphRagExtractionConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getDefaultGraphRagExtractionConfig"></a>
# **getDefaultGraphRagExtractionConfig**
> [GraphRagExtractionConfig] getDefaultGraphRagExtractionConfig()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GraphRagConfigurationControllerApi();
apiInstance.getDefaultGraphRagExtractionConfig().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GraphRagExtractionConfig]**](GraphRagExtractionConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getSystemGraphRagExtractionConfig"></a>
# **getSystemGraphRagExtractionConfig**
> GraphRagExtractionConfig getSystemGraphRagExtractionConfig(format)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GraphRagConfigurationControllerApi();
let format = "format_example"; // String | 

apiInstance.getSystemGraphRagExtractionConfig(format).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **format** | **String**|  | 

### Return type

[**GraphRagExtractionConfig**](GraphRagExtractionConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="instertGraphRagExtractionConfig"></a>
# **instertGraphRagExtractionConfig**
> GraphRagExtractionConfig instertGraphRagExtractionConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GraphRagConfigurationControllerApi();
let body = new GeboAiClient.GraphRagExtractionConfig(); // GraphRagExtractionConfig | 

apiInstance.instertGraphRagExtractionConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GraphRagExtractionConfig**](GraphRagExtractionConfig.md)|  | 

### Return type

[**GraphRagExtractionConfig**](GraphRagExtractionConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="saveGraphRagExtractionConfig"></a>
# **saveGraphRagExtractionConfig**
> GraphRagExtractionConfig saveGraphRagExtractionConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GraphRagConfigurationControllerApi();
let body = new GeboAiClient.GraphRagExtractionConfig(); // GraphRagExtractionConfig | 

apiInstance.saveGraphRagExtractionConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GraphRagExtractionConfig**](GraphRagExtractionConfig.md)|  | 

### Return type

[**GraphRagExtractionConfig**](GraphRagExtractionConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

