# BrainClient.BedrockTranscriptModelsConfigurationControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteBedrockTranscriptModelConfig**](BedrockTranscriptModelsConfigurationControllerApi.md#deleteBedrockTranscriptModelConfig) | **POST** /api/admin/BedrockTranscriptModelsConfigurationController/deleteBedrockTranscriptModelConfig | 
[**findBedrockTranscriptModelConfigByCode**](BedrockTranscriptModelsConfigurationControllerApi.md#findBedrockTranscriptModelConfigByCode) | **GET** /api/admin/BedrockTranscriptModelsConfigurationController/findBedrockTranscriptModelConfigByCode | 
[**getBedrockTranscriptModels**](BedrockTranscriptModelsConfigurationControllerApi.md#getBedrockTranscriptModels) | **POST** /api/admin/BedrockTranscriptModelsConfigurationController/getBedrockTranscriptModels | 
[**insertBedrockTranscriptModelConfig**](BedrockTranscriptModelsConfigurationControllerApi.md#insertBedrockTranscriptModelConfig) | **POST** /api/admin/BedrockTranscriptModelsConfigurationController/insertBedrockTranscriptModelConfig | 
[**updateBedrockTranscriptModelConfig**](BedrockTranscriptModelsConfigurationControllerApi.md#updateBedrockTranscriptModelConfig) | **POST** /api/admin/BedrockTranscriptModelsConfigurationController/updateBedrockTranscriptModelConfig | 

<a name="deleteBedrockTranscriptModelConfig"></a>
# **deleteBedrockTranscriptModelConfig**
> OperationStatusBoolean deleteBedrockTranscriptModelConfig(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.BedrockTranscriptModelsConfigurationControllerApi();
let body = new BrainClient.GBedrockTranscriptModelConfig(); // GBedrockTranscriptModelConfig | 

apiInstance.deleteBedrockTranscriptModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockTranscriptModelConfig**](GBedrockTranscriptModelConfig.md)|  | 

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findBedrockTranscriptModelConfigByCode"></a>
# **findBedrockTranscriptModelConfigByCode**
> GBedrockTranscriptModelConfig findBedrockTranscriptModelConfigByCode(code)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.BedrockTranscriptModelsConfigurationControllerApi();
let code = "code_example"; // String | 

apiInstance.findBedrockTranscriptModelConfigByCode(code).then((data) => {
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

[**GBedrockTranscriptModelConfig**](GBedrockTranscriptModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getBedrockTranscriptModels"></a>
# **getBedrockTranscriptModels**
> OperationStatusListGBedrockTranscriptModelChoice getBedrockTranscriptModels(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.BedrockTranscriptModelsConfigurationControllerApi();
let body = new BrainClient.GBedrockTranscriptModelConfig(); // GBedrockTranscriptModelConfig | 

apiInstance.getBedrockTranscriptModels(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockTranscriptModelConfig**](GBedrockTranscriptModelConfig.md)|  | 

### Return type

[**OperationStatusListGBedrockTranscriptModelChoice**](OperationStatusListGBedrockTranscriptModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertBedrockTranscriptModelConfig"></a>
# **insertBedrockTranscriptModelConfig**
> OperationStatusGBedrockTranscriptModelConfig insertBedrockTranscriptModelConfig(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.BedrockTranscriptModelsConfigurationControllerApi();
let body = new BrainClient.GBedrockTranscriptModelConfig(); // GBedrockTranscriptModelConfig | 

apiInstance.insertBedrockTranscriptModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockTranscriptModelConfig**](GBedrockTranscriptModelConfig.md)|  | 

### Return type

[**OperationStatusGBedrockTranscriptModelConfig**](OperationStatusGBedrockTranscriptModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateBedrockTranscriptModelConfig"></a>
# **updateBedrockTranscriptModelConfig**
> OperationStatusGBedrockTranscriptModelConfig updateBedrockTranscriptModelConfig(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.BedrockTranscriptModelsConfigurationControllerApi();
let body = new BrainClient.GBedrockTranscriptModelConfig(); // GBedrockTranscriptModelConfig | 

apiInstance.updateBedrockTranscriptModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockTranscriptModelConfig**](GBedrockTranscriptModelConfig.md)|  | 

### Return type

[**OperationStatusGBedrockTranscriptModelConfig**](OperationStatusGBedrockTranscriptModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

