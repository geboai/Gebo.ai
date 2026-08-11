# GeboAiClient.OllamaChatModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteOllamaChatModelConfig**](OllamaChatModelsConfigurationControllerApi.md#deleteOllamaChatModelConfig) | **POST** /api/admin/OllamaChatModelsConfigurationController/deleteOllamaChatModelConfig | 
[**findOllamaChatModelConfigByCode**](OllamaChatModelsConfigurationControllerApi.md#findOllamaChatModelConfigByCode) | **GET** /api/admin/OllamaChatModelsConfigurationController/findOllamaChatModelConfigByCode | 
[**getOllamaChatModels**](OllamaChatModelsConfigurationControllerApi.md#getOllamaChatModels) | **POST** /api/admin/OllamaChatModelsConfigurationController/getOllamaModels | 
[**insertOllamaChatModelConfig**](OllamaChatModelsConfigurationControllerApi.md#insertOllamaChatModelConfig) | **POST** /api/admin/OllamaChatModelsConfigurationController/insertOllamaChatModelConfig | 
[**updateOllamaChatModelConfig**](OllamaChatModelsConfigurationControllerApi.md#updateOllamaChatModelConfig) | **POST** /api/admin/OllamaChatModelsConfigurationController/updateOllamaChatModelConfig | 

<a name="deleteOllamaChatModelConfig"></a>
# **deleteOllamaChatModelConfig**
> OperationStatusBoolean deleteOllamaChatModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.OllamaChatModelsConfigurationControllerApi();
let body = new GeboAiClient.GOllamaChatModelConfig(); // GOllamaChatModelConfig | 

apiInstance.deleteOllamaChatModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOllamaChatModelConfig**](GOllamaChatModelConfig.md)|  | 

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findOllamaChatModelConfigByCode"></a>
# **findOllamaChatModelConfigByCode**
> GOllamaChatModelConfig findOllamaChatModelConfigByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.OllamaChatModelsConfigurationControllerApi();
let code = "code_example"; // String | 

apiInstance.findOllamaChatModelConfigByCode(code).then((data) => {
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

[**GOllamaChatModelConfig**](GOllamaChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getOllamaChatModels"></a>
# **getOllamaChatModels**
> OperationStatusListGOllamaChatModelChoice getOllamaChatModels(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.OllamaChatModelsConfigurationControllerApi();
let body = new GeboAiClient.GOllamaChatModelConfig(); // GOllamaChatModelConfig | 

apiInstance.getOllamaChatModels(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOllamaChatModelConfig**](GOllamaChatModelConfig.md)|  | 

### Return type

[**OperationStatusListGOllamaChatModelChoice**](OperationStatusListGOllamaChatModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertOllamaChatModelConfig"></a>
# **insertOllamaChatModelConfig**
> OperationStatusGOllamaChatModelConfig insertOllamaChatModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.OllamaChatModelsConfigurationControllerApi();
let body = new GeboAiClient.GOllamaChatModelConfig(); // GOllamaChatModelConfig | 

apiInstance.insertOllamaChatModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOllamaChatModelConfig**](GOllamaChatModelConfig.md)|  | 

### Return type

[**OperationStatusGOllamaChatModelConfig**](OperationStatusGOllamaChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateOllamaChatModelConfig"></a>
# **updateOllamaChatModelConfig**
> OperationStatusGOllamaChatModelConfig updateOllamaChatModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.OllamaChatModelsConfigurationControllerApi();
let body = new GeboAiClient.GOllamaChatModelConfig(); // GOllamaChatModelConfig | 

apiInstance.updateOllamaChatModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOllamaChatModelConfig**](GOllamaChatModelConfig.md)|  | 

### Return type

[**OperationStatusGOllamaChatModelConfig**](OperationStatusGOllamaChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

