# BrainClient.OnnxTransformersEmbeddingModelsConfigurationControllerApi

All URIs are relative to *http://localhost:13001/brain*

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
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.OnnxTransformersEmbeddingModelsConfigurationControllerApi();
let body = new BrainClient.GONNXTransformersEmbeddingModelConfig(); // GONNXTransformersEmbeddingModelConfig | 

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
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.OnnxTransformersEmbeddingModelsConfigurationControllerApi();
let code = null; // Object | 

apiInstance.findONNXTransformersEmbeddingModelConfigByCode(code).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | [**Object**](.md)|  | 

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
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.OnnxTransformersEmbeddingModelsConfigurationControllerApi();
let body = new BrainClient.GONNXTransformersEmbeddingModelConfig(); // GONNXTransformersEmbeddingModelConfig | 

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
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.OnnxTransformersEmbeddingModelsConfigurationControllerApi();
let body = new BrainClient.GONNXTransformersEmbeddingModelConfig(); // GONNXTransformersEmbeddingModelConfig | 

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
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.OnnxTransformersEmbeddingModelsConfigurationControllerApi();
let body = new BrainClient.GONNXTransformersEmbeddingModelConfig(); // GONNXTransformersEmbeddingModelConfig | 

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

