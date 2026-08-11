# GeboAiClient.GenericOpenAiapiChatModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteGenericOpenAIAPIChatModelConfig**](GenericOpenAiapiChatModelsConfigurationControllerApi.md#deleteGenericOpenAIAPIChatModelConfig) | **POST** /api/admin/GenericOpenAIAPIChatModelsConfigurationController/deleteGenericOpenAIAPIChatModelConfig | 
[**findGenericOpenAIAPIChatModelConfigByCode**](GenericOpenAiapiChatModelsConfigurationControllerApi.md#findGenericOpenAIAPIChatModelConfigByCode) | **GET** /api/admin/GenericOpenAIAPIChatModelsConfigurationController/findGenericOpenAIAPIChatModelConfigByCode | 
[**getGenericOpenAIAPIChatModels**](GenericOpenAiapiChatModelsConfigurationControllerApi.md#getGenericOpenAIAPIChatModels) | **POST** /api/admin/GenericOpenAIAPIChatModelsConfigurationController/getGenericOpenAIAPIChatModels | 
[**getGenericOpenAIChatModelTypes**](GenericOpenAiapiChatModelsConfigurationControllerApi.md#getGenericOpenAIChatModelTypes) | **GET** /api/admin/GenericOpenAIAPIChatModelsConfigurationController/getGenericOpenAIChatModelTypes | 
[**insertGenericOpenAIAPIChatModelConfig**](GenericOpenAiapiChatModelsConfigurationControllerApi.md#insertGenericOpenAIAPIChatModelConfig) | **POST** /api/admin/GenericOpenAIAPIChatModelsConfigurationController/insertGenericOpenAIAPIChatModelConfig | 
[**updateGenericOpenAIAPIChatModelConfig**](GenericOpenAiapiChatModelsConfigurationControllerApi.md#updateGenericOpenAIAPIChatModelConfig) | **POST** /api/admin/GenericOpenAIAPIChatModelsConfigurationController/updateGenericOpenAIAPIChatModelConfig | 

<a name="deleteGenericOpenAIAPIChatModelConfig"></a>
# **deleteGenericOpenAIAPIChatModelConfig**
> OperationStatusBoolean deleteGenericOpenAIAPIChatModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GenericOpenAiapiChatModelsConfigurationControllerApi();
let body = new GeboAiClient.GenericOpenAIAPIChatModelConfig(); // GenericOpenAIAPIChatModelConfig | 

apiInstance.deleteGenericOpenAIAPIChatModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPIChatModelConfig**](GenericOpenAIAPIChatModelConfig.md)|  | 

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findGenericOpenAIAPIChatModelConfigByCode"></a>
# **findGenericOpenAIAPIChatModelConfigByCode**
> GenericOpenAIAPIChatModelConfig findGenericOpenAIAPIChatModelConfigByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GenericOpenAiapiChatModelsConfigurationControllerApi();
let code = "code_example"; // String | 

apiInstance.findGenericOpenAIAPIChatModelConfigByCode(code).then((data) => {
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

[**GenericOpenAIAPIChatModelConfig**](GenericOpenAIAPIChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getGenericOpenAIAPIChatModels"></a>
# **getGenericOpenAIAPIChatModels**
> OperationStatusListGenericOpenAIAPIChatModelChoice getGenericOpenAIAPIChatModels(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GenericOpenAiapiChatModelsConfigurationControllerApi();
let body = new GeboAiClient.GenericOpenAIAPIChatModelConfig(); // GenericOpenAIAPIChatModelConfig | 

apiInstance.getGenericOpenAIAPIChatModels(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPIChatModelConfig**](GenericOpenAIAPIChatModelConfig.md)|  | 

### Return type

[**OperationStatusListGenericOpenAIAPIChatModelChoice**](OperationStatusListGenericOpenAIAPIChatModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getGenericOpenAIChatModelTypes"></a>
# **getGenericOpenAIChatModelTypes**
> [GenericOpenAIChatModelTypeConfig] getGenericOpenAIChatModelTypes()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GenericOpenAiapiChatModelsConfigurationControllerApi();
apiInstance.getGenericOpenAIChatModelTypes().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GenericOpenAIChatModelTypeConfig]**](GenericOpenAIChatModelTypeConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="insertGenericOpenAIAPIChatModelConfig"></a>
# **insertGenericOpenAIAPIChatModelConfig**
> OperationStatusGenericOpenAIAPIChatModelConfig insertGenericOpenAIAPIChatModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GenericOpenAiapiChatModelsConfigurationControllerApi();
let body = new GeboAiClient.GenericOpenAIAPIChatModelConfig(); // GenericOpenAIAPIChatModelConfig | 

apiInstance.insertGenericOpenAIAPIChatModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPIChatModelConfig**](GenericOpenAIAPIChatModelConfig.md)|  | 

### Return type

[**OperationStatusGenericOpenAIAPIChatModelConfig**](OperationStatusGenericOpenAIAPIChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateGenericOpenAIAPIChatModelConfig"></a>
# **updateGenericOpenAIAPIChatModelConfig**
> OperationStatusGenericOpenAIAPIChatModelConfig updateGenericOpenAIAPIChatModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GenericOpenAiapiChatModelsConfigurationControllerApi();
let body = new GeboAiClient.GenericOpenAIAPIChatModelConfig(); // GenericOpenAIAPIChatModelConfig | 

apiInstance.updateGenericOpenAIAPIChatModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPIChatModelConfig**](GenericOpenAIAPIChatModelConfig.md)|  | 

### Return type

[**OperationStatusGenericOpenAIAPIChatModelConfig**](OperationStatusGenericOpenAIAPIChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

