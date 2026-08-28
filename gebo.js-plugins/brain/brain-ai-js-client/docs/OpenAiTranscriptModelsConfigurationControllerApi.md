# BrainClient.OpenAiTranscriptModelsConfigurationControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteOpenAITranscriptModelConfig**](OpenAiTranscriptModelsConfigurationControllerApi.md#deleteOpenAITranscriptModelConfig) | **POST** /api/admin/OpenAITranscriptModelsConfigurationController/deleteOpenAITranscriptModelConfig | 
[**findOpenAITranscriptModelConfigByCode**](OpenAiTranscriptModelsConfigurationControllerApi.md#findOpenAITranscriptModelConfigByCode) | **GET** /api/admin/OpenAITranscriptModelsConfigurationController/findOpenAITranscriptModelConfigByCode | 
[**getOpenAITranscriptModels**](OpenAiTranscriptModelsConfigurationControllerApi.md#getOpenAITranscriptModels) | **POST** /api/admin/OpenAITranscriptModelsConfigurationController/getOpenAITranscriptModels | 
[**insertOpenAITranscriptModelConfig**](OpenAiTranscriptModelsConfigurationControllerApi.md#insertOpenAITranscriptModelConfig) | **POST** /api/admin/OpenAITranscriptModelsConfigurationController/insertOpenAITranscriptModelConfig | 
[**updateOpenAITranscriptModelConfig**](OpenAiTranscriptModelsConfigurationControllerApi.md#updateOpenAITranscriptModelConfig) | **POST** /api/admin/OpenAITranscriptModelsConfigurationController/updateOpenAITranscriptModelConfig | 

<a name="deleteOpenAITranscriptModelConfig"></a>
# **deleteOpenAITranscriptModelConfig**
> OperationStatusBoolean deleteOpenAITranscriptModelConfig(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.OpenAiTranscriptModelsConfigurationControllerApi();
let body = new BrainClient.GOpenAITranscriptModelConfig(); // GOpenAITranscriptModelConfig | 

apiInstance.deleteOpenAITranscriptModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAITranscriptModelConfig**](GOpenAITranscriptModelConfig.md)|  | 

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findOpenAITranscriptModelConfigByCode"></a>
# **findOpenAITranscriptModelConfigByCode**
> GOpenAITranscriptModelConfig findOpenAITranscriptModelConfigByCode(code)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.OpenAiTranscriptModelsConfigurationControllerApi();
let code = null; // Object | 

apiInstance.findOpenAITranscriptModelConfigByCode(code).then((data) => {
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

[**GOpenAITranscriptModelConfig**](GOpenAITranscriptModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getOpenAITranscriptModels"></a>
# **getOpenAITranscriptModels**
> OperationStatusListGOpenAITranscriptModelChoice getOpenAITranscriptModels(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.OpenAiTranscriptModelsConfigurationControllerApi();
let body = new BrainClient.GOpenAITranscriptModelConfig(); // GOpenAITranscriptModelConfig | 

apiInstance.getOpenAITranscriptModels(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAITranscriptModelConfig**](GOpenAITranscriptModelConfig.md)|  | 

### Return type

[**OperationStatusListGOpenAITranscriptModelChoice**](OperationStatusListGOpenAITranscriptModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertOpenAITranscriptModelConfig"></a>
# **insertOpenAITranscriptModelConfig**
> OperationStatusGOpenAITranscriptModelConfig insertOpenAITranscriptModelConfig(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.OpenAiTranscriptModelsConfigurationControllerApi();
let body = new BrainClient.GOpenAITranscriptModelConfig(); // GOpenAITranscriptModelConfig | 

apiInstance.insertOpenAITranscriptModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAITranscriptModelConfig**](GOpenAITranscriptModelConfig.md)|  | 

### Return type

[**OperationStatusGOpenAITranscriptModelConfig**](OperationStatusGOpenAITranscriptModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateOpenAITranscriptModelConfig"></a>
# **updateOpenAITranscriptModelConfig**
> OperationStatusGOpenAITranscriptModelConfig updateOpenAITranscriptModelConfig(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.OpenAiTranscriptModelsConfigurationControllerApi();
let body = new BrainClient.GOpenAITranscriptModelConfig(); // GOpenAITranscriptModelConfig | 

apiInstance.updateOpenAITranscriptModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAITranscriptModelConfig**](GOpenAITranscriptModelConfig.md)|  | 

### Return type

[**OperationStatusGOpenAITranscriptModelConfig**](OperationStatusGOpenAITranscriptModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

