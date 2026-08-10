# GeboAiClient.OllamaEmbeddingModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteOllamaEmbeddingModelConfig**](OllamaEmbeddingModelsConfigurationControllerApi.md#deleteOllamaEmbeddingModelConfig) | **POST** /api/admin/OllamaEmbeddingModelsConfigurationController/deleteOllamaEmbeddingModelConfig | 
[**findOllamaEmbeddingModelConfigByCode**](OllamaEmbeddingModelsConfigurationControllerApi.md#findOllamaEmbeddingModelConfigByCode) | **GET** /api/admin/OllamaEmbeddingModelsConfigurationController/findOllamaEmbeddingModelConfigByCode | 
[**getOllamaEmbeddingModels**](OllamaEmbeddingModelsConfigurationControllerApi.md#getOllamaEmbeddingModels) | **POST** /api/admin/OllamaEmbeddingModelsConfigurationController/getOllamaEmbeddingModels | 
[**insertOllamaEmbeddingModelConfig**](OllamaEmbeddingModelsConfigurationControllerApi.md#insertOllamaEmbeddingModelConfig) | **POST** /api/admin/OllamaEmbeddingModelsConfigurationController/insertOllamaEmbeddingModelConfig | 
[**updateOllamaEmbeddingModelConfig**](OllamaEmbeddingModelsConfigurationControllerApi.md#updateOllamaEmbeddingModelConfig) | **POST** /api/admin/OllamaEmbeddingModelsConfigurationController/updateOllamaEmbeddingModelConfig | 

<a name="deleteOllamaEmbeddingModelConfig"></a>
# **deleteOllamaEmbeddingModelConfig**
> OperationStatusBoolean deleteOllamaEmbeddingModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.OllamaEmbeddingModelsConfigurationControllerApi();
let body = new GeboAiClient.GOllamaEmbeddingModelConfig(); // GOllamaEmbeddingModelConfig | 

apiInstance.deleteOllamaEmbeddingModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOllamaEmbeddingModelConfig**](GOllamaEmbeddingModelConfig.md)|  | 

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findOllamaEmbeddingModelConfigByCode"></a>
# **findOllamaEmbeddingModelConfigByCode**
> GOllamaEmbeddingModelConfig findOllamaEmbeddingModelConfigByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.OllamaEmbeddingModelsConfigurationControllerApi();
let code = "code_example"; // String | 

apiInstance.findOllamaEmbeddingModelConfigByCode(code).then((data) => {
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

[**GOllamaEmbeddingModelConfig**](GOllamaEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getOllamaEmbeddingModels"></a>
# **getOllamaEmbeddingModels**
> OperationStatusListGOllamaEmbeddingModelChoice getOllamaEmbeddingModels(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.OllamaEmbeddingModelsConfigurationControllerApi();
let body = new GeboAiClient.GOllamaEmbeddingModelConfig(); // GOllamaEmbeddingModelConfig | 

apiInstance.getOllamaEmbeddingModels(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOllamaEmbeddingModelConfig**](GOllamaEmbeddingModelConfig.md)|  | 

### Return type

[**OperationStatusListGOllamaEmbeddingModelChoice**](OperationStatusListGOllamaEmbeddingModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertOllamaEmbeddingModelConfig"></a>
# **insertOllamaEmbeddingModelConfig**
> OperationStatusGOllamaEmbeddingModelConfig insertOllamaEmbeddingModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.OllamaEmbeddingModelsConfigurationControllerApi();
let body = new GeboAiClient.GOllamaEmbeddingModelConfig(); // GOllamaEmbeddingModelConfig | 

apiInstance.insertOllamaEmbeddingModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOllamaEmbeddingModelConfig**](GOllamaEmbeddingModelConfig.md)|  | 

### Return type

[**OperationStatusGOllamaEmbeddingModelConfig**](OperationStatusGOllamaEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateOllamaEmbeddingModelConfig"></a>
# **updateOllamaEmbeddingModelConfig**
> OperationStatusGOllamaEmbeddingModelConfig updateOllamaEmbeddingModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.OllamaEmbeddingModelsConfigurationControllerApi();
let body = new GeboAiClient.GOllamaEmbeddingModelConfig(); // GOllamaEmbeddingModelConfig | 

apiInstance.updateOllamaEmbeddingModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOllamaEmbeddingModelConfig**](GOllamaEmbeddingModelConfig.md)|  | 

### Return type

[**OperationStatusGOllamaEmbeddingModelConfig**](OperationStatusGOllamaEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

