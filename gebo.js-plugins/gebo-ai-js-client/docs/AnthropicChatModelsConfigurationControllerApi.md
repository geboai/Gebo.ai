# GeboAiClient.AnthropicChatModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteAnthropicChatModelConfig**](AnthropicChatModelsConfigurationControllerApi.md#deleteAnthropicChatModelConfig) | **POST** /api/admin/AnthropicChatModelsConfigurationController/deleteAnthropicChatModelConfig | 
[**findAnthropicChatModelConfigByCode**](AnthropicChatModelsConfigurationControllerApi.md#findAnthropicChatModelConfigByCode) | **GET** /api/admin/AnthropicChatModelsConfigurationController/findAnthropicChatModelConfigByCode | 
[**getAnthropicChatModels**](AnthropicChatModelsConfigurationControllerApi.md#getAnthropicChatModels) | **POST** /api/admin/AnthropicChatModelsConfigurationController/getAnthropicModels | 
[**insertAnthropicChatModelConfig**](AnthropicChatModelsConfigurationControllerApi.md#insertAnthropicChatModelConfig) | **POST** /api/admin/AnthropicChatModelsConfigurationController/insertAnthropicChatModelConfig | 
[**updateAnthropicChatModelConfig**](AnthropicChatModelsConfigurationControllerApi.md#updateAnthropicChatModelConfig) | **POST** /api/admin/AnthropicChatModelsConfigurationController/updateAnthropicChatModelConfig | 

<a name="deleteAnthropicChatModelConfig"></a>
# **deleteAnthropicChatModelConfig**
> OperationStatusBoolean deleteAnthropicChatModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.AnthropicChatModelsConfigurationControllerApi();
let body = new GeboAiClient.GAnthropicChatModelConfig(); // GAnthropicChatModelConfig | 

apiInstance.deleteAnthropicChatModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAnthropicChatModelConfig**](GAnthropicChatModelConfig.md)|  | 

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findAnthropicChatModelConfigByCode"></a>
# **findAnthropicChatModelConfigByCode**
> GAnthropicChatModelConfig findAnthropicChatModelConfigByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.AnthropicChatModelsConfigurationControllerApi();
let code = "code_example"; // String | 

apiInstance.findAnthropicChatModelConfigByCode(code).then((data) => {
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

[**GAnthropicChatModelConfig**](GAnthropicChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAnthropicChatModels"></a>
# **getAnthropicChatModels**
> OperationStatusListGAnthropicChatModelChoice getAnthropicChatModels(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.AnthropicChatModelsConfigurationControllerApi();
let body = new GeboAiClient.GAnthropicChatModelConfig(); // GAnthropicChatModelConfig | 

apiInstance.getAnthropicChatModels(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAnthropicChatModelConfig**](GAnthropicChatModelConfig.md)|  | 

### Return type

[**OperationStatusListGAnthropicChatModelChoice**](OperationStatusListGAnthropicChatModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertAnthropicChatModelConfig"></a>
# **insertAnthropicChatModelConfig**
> OperationStatusGAnthropicChatModelConfig insertAnthropicChatModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.AnthropicChatModelsConfigurationControllerApi();
let body = new GeboAiClient.GAnthropicChatModelConfig(); // GAnthropicChatModelConfig | 

apiInstance.insertAnthropicChatModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAnthropicChatModelConfig**](GAnthropicChatModelConfig.md)|  | 

### Return type

[**OperationStatusGAnthropicChatModelConfig**](OperationStatusGAnthropicChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateAnthropicChatModelConfig"></a>
# **updateAnthropicChatModelConfig**
> OperationStatusGAnthropicChatModelConfig updateAnthropicChatModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.AnthropicChatModelsConfigurationControllerApi();
let body = new GeboAiClient.GAnthropicChatModelConfig(); // GAnthropicChatModelConfig | 

apiInstance.updateAnthropicChatModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAnthropicChatModelConfig**](GAnthropicChatModelConfig.md)|  | 

### Return type

[**OperationStatusGAnthropicChatModelConfig**](OperationStatusGAnthropicChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

