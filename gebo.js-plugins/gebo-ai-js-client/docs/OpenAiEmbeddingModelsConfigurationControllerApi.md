# GeboAiClient.OpenAiEmbeddingModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteOpenAIEmbeddingModelConfig**](OpenAiEmbeddingModelsConfigurationControllerApi.md#deleteOpenAIEmbeddingModelConfig) | **POST** /api/admin/OpenAIEmbeddingModelsConfigurationController/deleteOpenAIEmbeddingModelConfig | 
[**findOpenAIEmbeddingModelConfigByCode**](OpenAiEmbeddingModelsConfigurationControllerApi.md#findOpenAIEmbeddingModelConfigByCode) | **GET** /api/admin/OpenAIEmbeddingModelsConfigurationController/findOpenAIEmbeddingModelConfigByCode | 
[**getOpenAIEmbeddingModels**](OpenAiEmbeddingModelsConfigurationControllerApi.md#getOpenAIEmbeddingModels) | **POST** /api/admin/OpenAIEmbeddingModelsConfigurationController/getOpenAIEmbeddingModels | 
[**insertOpenAIEmbeddingModelConfig**](OpenAiEmbeddingModelsConfigurationControllerApi.md#insertOpenAIEmbeddingModelConfig) | **POST** /api/admin/OpenAIEmbeddingModelsConfigurationController/insertOpenAIEmbeddingModelConfig | 
[**updateOpenAIEmbeddingModelConfig**](OpenAiEmbeddingModelsConfigurationControllerApi.md#updateOpenAIEmbeddingModelConfig) | **POST** /api/admin/OpenAIEmbeddingModelsConfigurationController/updateOpenAIEmbeddingModelConfig | 

<a name="deleteOpenAIEmbeddingModelConfig"></a>
# **deleteOpenAIEmbeddingModelConfig**
> OperationStatusBoolean deleteOpenAIEmbeddingModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.OpenAiEmbeddingModelsConfigurationControllerApi();
let body = new GeboAiClient.GOpenAIEmbeddingModelConfig(); // GOpenAIEmbeddingModelConfig | 

apiInstance.deleteOpenAIEmbeddingModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAIEmbeddingModelConfig**](GOpenAIEmbeddingModelConfig.md)|  | 

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findOpenAIEmbeddingModelConfigByCode"></a>
# **findOpenAIEmbeddingModelConfigByCode**
> GOpenAIEmbeddingModelConfig findOpenAIEmbeddingModelConfigByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.OpenAiEmbeddingModelsConfigurationControllerApi();
let code = "code_example"; // String | 

apiInstance.findOpenAIEmbeddingModelConfigByCode(code).then((data) => {
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

[**GOpenAIEmbeddingModelConfig**](GOpenAIEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getOpenAIEmbeddingModels"></a>
# **getOpenAIEmbeddingModels**
> OperationStatusListGOpenAIEmbeddingModelChoice getOpenAIEmbeddingModels(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.OpenAiEmbeddingModelsConfigurationControllerApi();
let body = new GeboAiClient.GOpenAIEmbeddingModelConfig(); // GOpenAIEmbeddingModelConfig | 

apiInstance.getOpenAIEmbeddingModels(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAIEmbeddingModelConfig**](GOpenAIEmbeddingModelConfig.md)|  | 

### Return type

[**OperationStatusListGOpenAIEmbeddingModelChoice**](OperationStatusListGOpenAIEmbeddingModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertOpenAIEmbeddingModelConfig"></a>
# **insertOpenAIEmbeddingModelConfig**
> OperationStatusGOpenAIEmbeddingModelConfig insertOpenAIEmbeddingModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.OpenAiEmbeddingModelsConfigurationControllerApi();
let body = new GeboAiClient.GOpenAIEmbeddingModelConfig(); // GOpenAIEmbeddingModelConfig | 

apiInstance.insertOpenAIEmbeddingModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAIEmbeddingModelConfig**](GOpenAIEmbeddingModelConfig.md)|  | 

### Return type

[**OperationStatusGOpenAIEmbeddingModelConfig**](OperationStatusGOpenAIEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateOpenAIEmbeddingModelConfig"></a>
# **updateOpenAIEmbeddingModelConfig**
> OperationStatusGOpenAIEmbeddingModelConfig updateOpenAIEmbeddingModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.OpenAiEmbeddingModelsConfigurationControllerApi();
let body = new GeboAiClient.GOpenAIEmbeddingModelConfig(); // GOpenAIEmbeddingModelConfig | 

apiInstance.updateOpenAIEmbeddingModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAIEmbeddingModelConfig**](GOpenAIEmbeddingModelConfig.md)|  | 

### Return type

[**OperationStatusGOpenAIEmbeddingModelConfig**](OperationStatusGOpenAIEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

