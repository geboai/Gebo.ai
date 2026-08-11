# GeboAiClient.OpenAiChatModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteOpenAIChatModelConfig**](OpenAiChatModelsConfigurationControllerApi.md#deleteOpenAIChatModelConfig) | **POST** /api/admin/OpenAIModelsConfigurationController/deleteOpenAIChatModelConfig | 
[**findOpenAIChatModelConfigByCode**](OpenAiChatModelsConfigurationControllerApi.md#findOpenAIChatModelConfigByCode) | **GET** /api/admin/OpenAIModelsConfigurationController/findOpenAIChatModelConfigByCode | 
[**getOpenAIChatModels**](OpenAiChatModelsConfigurationControllerApi.md#getOpenAIChatModels) | **POST** /api/admin/OpenAIModelsConfigurationController/getOpenAIChatModels | 
[**insertOpenAIChatModelConfig**](OpenAiChatModelsConfigurationControllerApi.md#insertOpenAIChatModelConfig) | **POST** /api/admin/OpenAIModelsConfigurationController/insertOpenAIChatModelConfig | 
[**updateOpenAIChatModelConfig**](OpenAiChatModelsConfigurationControllerApi.md#updateOpenAIChatModelConfig) | **POST** /api/admin/OpenAIModelsConfigurationController/updateOpenAIChatModelConfig | 

<a name="deleteOpenAIChatModelConfig"></a>
# **deleteOpenAIChatModelConfig**
> OperationStatusBoolean deleteOpenAIChatModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.OpenAiChatModelsConfigurationControllerApi();
let body = new GeboAiClient.GOpenAIChatModelConfig(); // GOpenAIChatModelConfig | 

apiInstance.deleteOpenAIChatModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAIChatModelConfig**](GOpenAIChatModelConfig.md)|  | 

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findOpenAIChatModelConfigByCode"></a>
# **findOpenAIChatModelConfigByCode**
> GOpenAIChatModelConfig findOpenAIChatModelConfigByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.OpenAiChatModelsConfigurationControllerApi();
let code = "code_example"; // String | 

apiInstance.findOpenAIChatModelConfigByCode(code).then((data) => {
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

[**GOpenAIChatModelConfig**](GOpenAIChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getOpenAIChatModels"></a>
# **getOpenAIChatModels**
> OperationStatusListGOpenAIChatModelChoice getOpenAIChatModels(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.OpenAiChatModelsConfigurationControllerApi();
let body = new GeboAiClient.GOpenAIChatModelConfig(); // GOpenAIChatModelConfig | 

apiInstance.getOpenAIChatModels(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAIChatModelConfig**](GOpenAIChatModelConfig.md)|  | 

### Return type

[**OperationStatusListGOpenAIChatModelChoice**](OperationStatusListGOpenAIChatModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertOpenAIChatModelConfig"></a>
# **insertOpenAIChatModelConfig**
> OperationStatusGOpenAIChatModelConfig insertOpenAIChatModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.OpenAiChatModelsConfigurationControllerApi();
let body = new GeboAiClient.GOpenAIChatModelConfig(); // GOpenAIChatModelConfig | 

apiInstance.insertOpenAIChatModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAIChatModelConfig**](GOpenAIChatModelConfig.md)|  | 

### Return type

[**OperationStatusGOpenAIChatModelConfig**](OperationStatusGOpenAIChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateOpenAIChatModelConfig"></a>
# **updateOpenAIChatModelConfig**
> OperationStatusGOpenAIChatModelConfig updateOpenAIChatModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.OpenAiChatModelsConfigurationControllerApi();
let body = new GeboAiClient.GOpenAIChatModelConfig(); // GOpenAIChatModelConfig | 

apiInstance.updateOpenAIChatModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenAIChatModelConfig**](GOpenAIChatModelConfig.md)|  | 

### Return type

[**OperationStatusGOpenAIChatModelConfig**](OperationStatusGOpenAIChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

