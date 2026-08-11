# GeboAiClient.OnnxTransformersEmbeddingModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteONNXTransformersEmbeddingModelConfig**](OnnxTransformersEmbeddingModelsConfigurationControllerApi.md#deleteONNXTransformersEmbeddingModelConfig) | **POST** /api/admin/ONNXTransformersEmbeddingModelsConfigurationController/deleteONNXTransformersEmbeddingModelConfig | 
[**findONNXTransformersEmbeddingModelConfigByCode**](OnnxTransformersEmbeddingModelsConfigurationControllerApi.md#findONNXTransformersEmbeddingModelConfigByCode) | **GET** /api/admin/ONNXTransformersEmbeddingModelsConfigurationController/findONNXTransformersEmbeddingModelConfigByCode | 
[**getONNXTransformersEmbeddingModels**](OnnxTransformersEmbeddingModelsConfigurationControllerApi.md#getONNXTransformersEmbeddingModels) | **POST** /api/admin/ONNXTransformersEmbeddingModelsConfigurationController/getONNXTransformersEmbeddingModels | 
[**insertONNXTransformersEmbeddingModelConfig**](OnnxTransformersEmbeddingModelsConfigurationControllerApi.md#insertONNXTransformersEmbeddingModelConfig) | **POST** /api/admin/ONNXTransformersEmbeddingModelsConfigurationController/insertONNXTransformersEmbeddingModelConfig | 
[**updateONNXTransformersEmbeddingModelConfig**](OnnxTransformersEmbeddingModelsConfigurationControllerApi.md#updateONNXTransformersEmbeddingModelConfig) | **POST** /api/admin/ONNXTransformersEmbeddingModelsConfigurationController/updateONNXTransformersEmbeddingModelConfig | 

<a name="deleteONNXTransformersEmbeddingModelConfig"></a>
# **deleteONNXTransformersEmbeddingModelConfig**
> OperationStatusBoolean deleteONNXTransformersEmbeddingModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.OnnxTransformersEmbeddingModelsConfigurationControllerApi();
let body = new GeboAiClient.GONNXTransformersEmbeddingModelConfig(); // GONNXTransformersEmbeddingModelConfig | 

apiInstance.deleteONNXTransformersEmbeddingModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GONNXTransformersEmbeddingModelConfig**](GONNXTransformersEmbeddingModelConfig.md)|  | 

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findONNXTransformersEmbeddingModelConfigByCode"></a>
# **findONNXTransformersEmbeddingModelConfigByCode**
> GONNXTransformersEmbeddingModelConfig findONNXTransformersEmbeddingModelConfigByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.OnnxTransformersEmbeddingModelsConfigurationControllerApi();
let code = "code_example"; // String | 

apiInstance.findONNXTransformersEmbeddingModelConfigByCode(code).then((data) => {
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

[**GONNXTransformersEmbeddingModelConfig**](GONNXTransformersEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getONNXTransformersEmbeddingModels"></a>
# **getONNXTransformersEmbeddingModels**
> OperationStatusListGONNXTransformersEmbeddingModelChoice getONNXTransformersEmbeddingModels(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.OnnxTransformersEmbeddingModelsConfigurationControllerApi();
let body = new GeboAiClient.GONNXTransformersEmbeddingModelConfig(); // GONNXTransformersEmbeddingModelConfig | 

apiInstance.getONNXTransformersEmbeddingModels(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GONNXTransformersEmbeddingModelConfig**](GONNXTransformersEmbeddingModelConfig.md)|  | 

### Return type

[**OperationStatusListGONNXTransformersEmbeddingModelChoice**](OperationStatusListGONNXTransformersEmbeddingModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertONNXTransformersEmbeddingModelConfig"></a>
# **insertONNXTransformersEmbeddingModelConfig**
> OperationStatusGONNXTransformersEmbeddingModelConfig insertONNXTransformersEmbeddingModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.OnnxTransformersEmbeddingModelsConfigurationControllerApi();
let body = new GeboAiClient.GONNXTransformersEmbeddingModelConfig(); // GONNXTransformersEmbeddingModelConfig | 

apiInstance.insertONNXTransformersEmbeddingModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GONNXTransformersEmbeddingModelConfig**](GONNXTransformersEmbeddingModelConfig.md)|  | 

### Return type

[**OperationStatusGONNXTransformersEmbeddingModelConfig**](OperationStatusGONNXTransformersEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateONNXTransformersEmbeddingModelConfig"></a>
# **updateONNXTransformersEmbeddingModelConfig**
> OperationStatusGONNXTransformersEmbeddingModelConfig updateONNXTransformersEmbeddingModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.OnnxTransformersEmbeddingModelsConfigurationControllerApi();
let body = new GeboAiClient.GONNXTransformersEmbeddingModelConfig(); // GONNXTransformersEmbeddingModelConfig | 

apiInstance.updateONNXTransformersEmbeddingModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GONNXTransformersEmbeddingModelConfig**](GONNXTransformersEmbeddingModelConfig.md)|  | 

### Return type

[**OperationStatusGONNXTransformersEmbeddingModelConfig**](OperationStatusGONNXTransformersEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

