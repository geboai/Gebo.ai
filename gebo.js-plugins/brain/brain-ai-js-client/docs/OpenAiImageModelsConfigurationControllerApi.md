# BrainClient.OpenAiImageModelsConfigurationControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteOpenAIImageModelConfig**](OpenAiImageModelsConfigurationControllerApi.md#deleteOpenAIImageModelConfig) | **POST** /api/admin/OpenAIImageModelsConfigurationController/deleteOpenAIImageModelConfig | 
[**findOpenAIImageModelConfigByCode**](OpenAiImageModelsConfigurationControllerApi.md#findOpenAIImageModelConfigByCode) | **GET** /api/admin/OpenAIImageModelsConfigurationController/findOpenAIImageModelConfigByCode | 
[**getOpenAIImageModels**](OpenAiImageModelsConfigurationControllerApi.md#getOpenAIImageModels) | **POST** /api/admin/OpenAIImageModelsConfigurationController/getOpenAIImageModels | 
[**insertOpenAIImageModelConfig**](OpenAiImageModelsConfigurationControllerApi.md#insertOpenAIImageModelConfig) | **POST** /api/admin/OpenAIImageModelsConfigurationController/insertOpenAIImageModelConfig | 
[**updateOpenAIImageModelConfig**](OpenAiImageModelsConfigurationControllerApi.md#updateOpenAIImageModelConfig) | **POST** /api/admin/OpenAIImageModelsConfigurationController/updateOpenAIImageModelConfig | 

<a name="deleteOpenAIImageModelConfig"></a>
# **deleteOpenAIImageModelConfig**
> OperationStatusBoolean deleteOpenAIImageModelConfig(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.OpenAiImageModelsConfigurationControllerApi();
let body = new BrainClient.GOpenAIImageModelConfig(); // GOpenAIImageModelConfig | 

apiInstance.deleteOpenAIImageModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAIImageModelConfig**](GOpenAIImageModelConfig.md)|  | 

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findOpenAIImageModelConfigByCode"></a>
# **findOpenAIImageModelConfigByCode**
> GOpenAIImageModelConfig findOpenAIImageModelConfigByCode(code)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.OpenAiImageModelsConfigurationControllerApi();
let code = null; // Object | 

apiInstance.findOpenAIImageModelConfigByCode(code).then((data) => {
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

[**GOpenAIImageModelConfig**](GOpenAIImageModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getOpenAIImageModels"></a>
# **getOpenAIImageModels**
> OperationStatusListGOpenAIImageModelChoice getOpenAIImageModels(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.OpenAiImageModelsConfigurationControllerApi();
let body = new BrainClient.GOpenAIImageModelConfig(); // GOpenAIImageModelConfig | 

apiInstance.getOpenAIImageModels(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAIImageModelConfig**](GOpenAIImageModelConfig.md)|  | 

### Return type

[**OperationStatusListGOpenAIImageModelChoice**](OperationStatusListGOpenAIImageModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertOpenAIImageModelConfig"></a>
# **insertOpenAIImageModelConfig**
> OperationStatusGOpenAIImageModelConfig insertOpenAIImageModelConfig(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.OpenAiImageModelsConfigurationControllerApi();
let body = new BrainClient.GOpenAIImageModelConfig(); // GOpenAIImageModelConfig | 

apiInstance.insertOpenAIImageModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAIImageModelConfig**](GOpenAIImageModelConfig.md)|  | 

### Return type

[**OperationStatusGOpenAIImageModelConfig**](OperationStatusGOpenAIImageModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateOpenAIImageModelConfig"></a>
# **updateOpenAIImageModelConfig**
> OperationStatusGOpenAIImageModelConfig updateOpenAIImageModelConfig(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.OpenAiImageModelsConfigurationControllerApi();
let body = new BrainClient.GOpenAIImageModelConfig(); // GOpenAIImageModelConfig | 

apiInstance.updateOpenAIImageModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAIImageModelConfig**](GOpenAIImageModelConfig.md)|  | 

### Return type

[**OperationStatusGOpenAIImageModelConfig**](OperationStatusGOpenAIImageModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

