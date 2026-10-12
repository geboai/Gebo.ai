# BrainClient.BedrockEmbeddingModelsConfigurationControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteBedrockEmbeddingModelConfig**](BedrockEmbeddingModelsConfigurationControllerApi.md#deleteBedrockEmbeddingModelConfig) | **POST** /api/admin/BedrockEmbeddingModelsConfigurationController/deleteBedrockEmbeddingModelConfig | 
[**findBedrockEmbeddingModelConfigByCode**](BedrockEmbeddingModelsConfigurationControllerApi.md#findBedrockEmbeddingModelConfigByCode) | **GET** /api/admin/BedrockEmbeddingModelsConfigurationController/findBedrockEmbeddingModelConfigByCode | 
[**getBedrockEmbeddingModels**](BedrockEmbeddingModelsConfigurationControllerApi.md#getBedrockEmbeddingModels) | **POST** /api/admin/BedrockEmbeddingModelsConfigurationController/getBedrockEmbeddingModels | 
[**insertBedrockEmbeddingModelConfig**](BedrockEmbeddingModelsConfigurationControllerApi.md#insertBedrockEmbeddingModelConfig) | **POST** /api/admin/BedrockEmbeddingModelsConfigurationController/insertBedrockEmbeddingModelConfig | 
[**updateBedrockEmbeddingModelConfig**](BedrockEmbeddingModelsConfigurationControllerApi.md#updateBedrockEmbeddingModelConfig) | **POST** /api/admin/BedrockEmbeddingModelsConfigurationController/updateBedrockEmbeddingModelConfig | 

<a name="deleteBedrockEmbeddingModelConfig"></a>
# **deleteBedrockEmbeddingModelConfig**
> OperationStatusBoolean deleteBedrockEmbeddingModelConfig(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.BedrockEmbeddingModelsConfigurationControllerApi();
let body = new BrainClient.GBedrockEmbeddingModelConfig(); // GBedrockEmbeddingModelConfig | 

apiInstance.deleteBedrockEmbeddingModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockEmbeddingModelConfig**](GBedrockEmbeddingModelConfig.md)|  | 

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findBedrockEmbeddingModelConfigByCode"></a>
# **findBedrockEmbeddingModelConfigByCode**
> GBedrockEmbeddingModelConfig findBedrockEmbeddingModelConfigByCode(code)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.BedrockEmbeddingModelsConfigurationControllerApi();
let code = "code_example"; // String | 

apiInstance.findBedrockEmbeddingModelConfigByCode(code).then((data) => {
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

[**GBedrockEmbeddingModelConfig**](GBedrockEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getBedrockEmbeddingModels"></a>
# **getBedrockEmbeddingModels**
> OperationStatusListGBedrockEmbeddingModelChoice getBedrockEmbeddingModels(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.BedrockEmbeddingModelsConfigurationControllerApi();
let body = new BrainClient.GBedrockEmbeddingModelConfig(); // GBedrockEmbeddingModelConfig | 

apiInstance.getBedrockEmbeddingModels(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockEmbeddingModelConfig**](GBedrockEmbeddingModelConfig.md)|  | 

### Return type

[**OperationStatusListGBedrockEmbeddingModelChoice**](OperationStatusListGBedrockEmbeddingModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertBedrockEmbeddingModelConfig"></a>
# **insertBedrockEmbeddingModelConfig**
> OperationStatusGBedrockEmbeddingModelConfig insertBedrockEmbeddingModelConfig(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.BedrockEmbeddingModelsConfigurationControllerApi();
let body = new BrainClient.GBedrockEmbeddingModelConfig(); // GBedrockEmbeddingModelConfig | 

apiInstance.insertBedrockEmbeddingModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockEmbeddingModelConfig**](GBedrockEmbeddingModelConfig.md)|  | 

### Return type

[**OperationStatusGBedrockEmbeddingModelConfig**](OperationStatusGBedrockEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateBedrockEmbeddingModelConfig"></a>
# **updateBedrockEmbeddingModelConfig**
> OperationStatusGBedrockEmbeddingModelConfig updateBedrockEmbeddingModelConfig(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.BedrockEmbeddingModelsConfigurationControllerApi();
let body = new BrainClient.GBedrockEmbeddingModelConfig(); // GBedrockEmbeddingModelConfig | 

apiInstance.updateBedrockEmbeddingModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockEmbeddingModelConfig**](GBedrockEmbeddingModelConfig.md)|  | 

### Return type

[**OperationStatusGBedrockEmbeddingModelConfig**](OperationStatusGBedrockEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

