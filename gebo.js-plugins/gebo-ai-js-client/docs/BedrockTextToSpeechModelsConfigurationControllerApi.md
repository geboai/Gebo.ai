# GeboAiClient.BedrockTextToSpeechModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteBedrockTextToSpeechModelConfig**](BedrockTextToSpeechModelsConfigurationControllerApi.md#deleteBedrockTextToSpeechModelConfig) | **POST** /api/admin/BedrockTextToSpeechModelsConfigurationController/deleteBedrockTextToSpeechModelConfig | 
[**findBedrockTextToSpeechModelConfigByCode**](BedrockTextToSpeechModelsConfigurationControllerApi.md#findBedrockTextToSpeechModelConfigByCode) | **GET** /api/admin/BedrockTextToSpeechModelsConfigurationController/findBedrockTextToSpeechModelConfigByCode | 
[**getBedrockTextToSpeechModels**](BedrockTextToSpeechModelsConfigurationControllerApi.md#getBedrockTextToSpeechModels) | **POST** /api/admin/BedrockTextToSpeechModelsConfigurationController/getBedrockTextToSpeechModels | 
[**insertBedrockTextToSpeechModelConfig**](BedrockTextToSpeechModelsConfigurationControllerApi.md#insertBedrockTextToSpeechModelConfig) | **POST** /api/admin/BedrockTextToSpeechModelsConfigurationController/insertBedrockTextToSpeechModelConfig | 
[**updateBedrockTextToSpeechModelConfig**](BedrockTextToSpeechModelsConfigurationControllerApi.md#updateBedrockTextToSpeechModelConfig) | **POST** /api/admin/BedrockTextToSpeechModelsConfigurationController/updateBedrockTextToSpeechModelConfig | 

<a name="deleteBedrockTextToSpeechModelConfig"></a>
# **deleteBedrockTextToSpeechModelConfig**
> OperationStatusBoolean deleteBedrockTextToSpeechModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.BedrockTextToSpeechModelsConfigurationControllerApi();
let body = new GeboAiClient.GBedrockTextToSpeechModelConfig(); // GBedrockTextToSpeechModelConfig | 

apiInstance.deleteBedrockTextToSpeechModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockTextToSpeechModelConfig**](GBedrockTextToSpeechModelConfig.md)|  | 

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findBedrockTextToSpeechModelConfigByCode"></a>
# **findBedrockTextToSpeechModelConfigByCode**
> GBedrockTextToSpeechModelConfig findBedrockTextToSpeechModelConfigByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.BedrockTextToSpeechModelsConfigurationControllerApi();
let code = "code_example"; // String | 

apiInstance.findBedrockTextToSpeechModelConfigByCode(code).then((data) => {
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

[**GBedrockTextToSpeechModelConfig**](GBedrockTextToSpeechModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getBedrockTextToSpeechModels"></a>
# **getBedrockTextToSpeechModels**
> OperationStatusListGBedrockTextToSpeechModelChoice getBedrockTextToSpeechModels(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.BedrockTextToSpeechModelsConfigurationControllerApi();
let body = new GeboAiClient.GBedrockTextToSpeechModelConfig(); // GBedrockTextToSpeechModelConfig | 

apiInstance.getBedrockTextToSpeechModels(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockTextToSpeechModelConfig**](GBedrockTextToSpeechModelConfig.md)|  | 

### Return type

[**OperationStatusListGBedrockTextToSpeechModelChoice**](OperationStatusListGBedrockTextToSpeechModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertBedrockTextToSpeechModelConfig"></a>
# **insertBedrockTextToSpeechModelConfig**
> OperationStatusGBedrockTextToSpeechModelConfig insertBedrockTextToSpeechModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.BedrockTextToSpeechModelsConfigurationControllerApi();
let body = new GeboAiClient.GBedrockTextToSpeechModelConfig(); // GBedrockTextToSpeechModelConfig | 

apiInstance.insertBedrockTextToSpeechModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockTextToSpeechModelConfig**](GBedrockTextToSpeechModelConfig.md)|  | 

### Return type

[**OperationStatusGBedrockTextToSpeechModelConfig**](OperationStatusGBedrockTextToSpeechModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateBedrockTextToSpeechModelConfig"></a>
# **updateBedrockTextToSpeechModelConfig**
> OperationStatusGBedrockTextToSpeechModelConfig updateBedrockTextToSpeechModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.BedrockTextToSpeechModelsConfigurationControllerApi();
let body = new GeboAiClient.GBedrockTextToSpeechModelConfig(); // GBedrockTextToSpeechModelConfig | 

apiInstance.updateBedrockTextToSpeechModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockTextToSpeechModelConfig**](GBedrockTextToSpeechModelConfig.md)|  | 

### Return type

[**OperationStatusGBedrockTextToSpeechModelConfig**](OperationStatusGBedrockTextToSpeechModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

