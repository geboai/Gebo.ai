# GeboAiClient.OpenAiTextToSpeechModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteOpenAITextToSpeechModelConfig**](OpenAiTextToSpeechModelsConfigurationControllerApi.md#deleteOpenAITextToSpeechModelConfig) | **POST** /api/admin/OpenAITextToSpeechModelsConfigurationController/deleteOpenAITextToSpeechModelConfig | 
[**findOpenAITextToSpeechModelConfigByCode**](OpenAiTextToSpeechModelsConfigurationControllerApi.md#findOpenAITextToSpeechModelConfigByCode) | **GET** /api/admin/OpenAITextToSpeechModelsConfigurationController/findOpenAITextToSpeechModelConfigByCode | 
[**getOpenAITextToSpeechModels**](OpenAiTextToSpeechModelsConfigurationControllerApi.md#getOpenAITextToSpeechModels) | **POST** /api/admin/OpenAITextToSpeechModelsConfigurationController/getOpenAITextToSpeechModels | 
[**insertOpenAITextToSpeechModelConfig**](OpenAiTextToSpeechModelsConfigurationControllerApi.md#insertOpenAITextToSpeechModelConfig) | **POST** /api/admin/OpenAITextToSpeechModelsConfigurationController/insertOpenAITextToSpeechModelConfig | 
[**updateOpenAITextToSpeechModelConfig**](OpenAiTextToSpeechModelsConfigurationControllerApi.md#updateOpenAITextToSpeechModelConfig) | **POST** /api/admin/OpenAITextToSpeechModelsConfigurationController/updateOpenAITextToSpeechModelConfig | 

<a name="deleteOpenAITextToSpeechModelConfig"></a>
# **deleteOpenAITextToSpeechModelConfig**
> OperationStatusBoolean deleteOpenAITextToSpeechModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.OpenAiTextToSpeechModelsConfigurationControllerApi();
let body = new GeboAiClient.GOpenAITextToSpeechModelConfig(); // GOpenAITextToSpeechModelConfig | 

apiInstance.deleteOpenAITextToSpeechModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAITextToSpeechModelConfig**](GOpenAITextToSpeechModelConfig.md)|  | 

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findOpenAITextToSpeechModelConfigByCode"></a>
# **findOpenAITextToSpeechModelConfigByCode**
> GOpenAITextToSpeechModelConfig findOpenAITextToSpeechModelConfigByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.OpenAiTextToSpeechModelsConfigurationControllerApi();
let code = "code_example"; // String | 

apiInstance.findOpenAITextToSpeechModelConfigByCode(code).then((data) => {
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

[**GOpenAITextToSpeechModelConfig**](GOpenAITextToSpeechModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getOpenAITextToSpeechModels"></a>
# **getOpenAITextToSpeechModels**
> OperationStatusListGOpenAITextToSpeechModelChoice getOpenAITextToSpeechModels(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.OpenAiTextToSpeechModelsConfigurationControllerApi();
let body = new GeboAiClient.GOpenAITextToSpeechModelConfig(); // GOpenAITextToSpeechModelConfig | 

apiInstance.getOpenAITextToSpeechModels(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAITextToSpeechModelConfig**](GOpenAITextToSpeechModelConfig.md)|  | 

### Return type

[**OperationStatusListGOpenAITextToSpeechModelChoice**](OperationStatusListGOpenAITextToSpeechModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertOpenAITextToSpeechModelConfig"></a>
# **insertOpenAITextToSpeechModelConfig**
> OperationStatusGOpenAITextToSpeechModelConfig insertOpenAITextToSpeechModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.OpenAiTextToSpeechModelsConfigurationControllerApi();
let body = new GeboAiClient.GOpenAITextToSpeechModelConfig(); // GOpenAITextToSpeechModelConfig | 

apiInstance.insertOpenAITextToSpeechModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAITextToSpeechModelConfig**](GOpenAITextToSpeechModelConfig.md)|  | 

### Return type

[**OperationStatusGOpenAITextToSpeechModelConfig**](OperationStatusGOpenAITextToSpeechModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateOpenAITextToSpeechModelConfig"></a>
# **updateOpenAITextToSpeechModelConfig**
> OperationStatusGOpenAITextToSpeechModelConfig updateOpenAITextToSpeechModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.OpenAiTextToSpeechModelsConfigurationControllerApi();
let body = new GeboAiClient.GOpenAITextToSpeechModelConfig(); // GOpenAITextToSpeechModelConfig | 

apiInstance.updateOpenAITextToSpeechModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAITextToSpeechModelConfig**](GOpenAITextToSpeechModelConfig.md)|  | 

### Return type

[**OperationStatusGOpenAITextToSpeechModelConfig**](OperationStatusGOpenAITextToSpeechModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

