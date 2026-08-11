# GeboAiClient.OpenAiImageModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

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
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.OpenAiImageModelsConfigurationControllerApi();
let body = new GeboAiClient.GOpenAIImageModelConfig(); // GOpenAIImageModelConfig | 

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
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.OpenAiImageModelsConfigurationControllerApi();
let code = "code_example"; // String | 

apiInstance.findOpenAIImageModelConfigByCode(code).then((data) => {
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
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.OpenAiImageModelsConfigurationControllerApi();
let body = new GeboAiClient.GOpenAIImageModelConfig(); // GOpenAIImageModelConfig | 

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
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.OpenAiImageModelsConfigurationControllerApi();
let body = new GeboAiClient.GOpenAIImageModelConfig(); // GOpenAIImageModelConfig | 

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
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.OpenAiImageModelsConfigurationControllerApi();
let body = new GeboAiClient.GOpenAIImageModelConfig(); // GOpenAIImageModelConfig | 

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

