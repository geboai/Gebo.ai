# BrainClient.BedrockChatModelsConfigurationControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteBedrockChatModelConfig**](BedrockChatModelsConfigurationControllerApi.md#deleteBedrockChatModelConfig) | **POST** /api/admin/BedrockChatModelsConfigurationController/deleteBedrockChatModelConfig | 
[**findBedrockChatModelConfigByCode**](BedrockChatModelsConfigurationControllerApi.md#findBedrockChatModelConfigByCode) | **GET** /api/admin/BedrockChatModelsConfigurationController/findBedrockChatModelConfigByCode | 
[**getBedrockChatModels**](BedrockChatModelsConfigurationControllerApi.md#getBedrockChatModels) | **POST** /api/admin/BedrockChatModelsConfigurationController/getBedrockChatModels | 
[**insertBedrockChatModelConfig**](BedrockChatModelsConfigurationControllerApi.md#insertBedrockChatModelConfig) | **POST** /api/admin/BedrockChatModelsConfigurationController/insertBedrockChatModelConfig | 
[**updateBedrockChatModelConfig**](BedrockChatModelsConfigurationControllerApi.md#updateBedrockChatModelConfig) | **POST** /api/admin/BedrockChatModelsConfigurationController/updateBedrockChatModelConfig | 

<a name="deleteBedrockChatModelConfig"></a>
# **deleteBedrockChatModelConfig**
> OperationStatusBoolean deleteBedrockChatModelConfig(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.BedrockChatModelsConfigurationControllerApi();
let body = new BrainClient.GBedrockChatModelConfig(); // GBedrockChatModelConfig | 

apiInstance.deleteBedrockChatModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockChatModelConfig**](GBedrockChatModelConfig.md)|  | 

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findBedrockChatModelConfigByCode"></a>
# **findBedrockChatModelConfigByCode**
> GBedrockChatModelConfig findBedrockChatModelConfigByCode(code)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.BedrockChatModelsConfigurationControllerApi();
let code = "code_example"; // String | 

apiInstance.findBedrockChatModelConfigByCode(code).then((data) => {
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

[**GBedrockChatModelConfig**](GBedrockChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getBedrockChatModels"></a>
# **getBedrockChatModels**
> OperationStatusListGBedrockChatModelChoice getBedrockChatModels(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.BedrockChatModelsConfigurationControllerApi();
let body = new BrainClient.GBedrockChatModelConfig(); // GBedrockChatModelConfig | 

apiInstance.getBedrockChatModels(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockChatModelConfig**](GBedrockChatModelConfig.md)|  | 

### Return type

[**OperationStatusListGBedrockChatModelChoice**](OperationStatusListGBedrockChatModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertBedrockChatModelConfig"></a>
# **insertBedrockChatModelConfig**
> OperationStatusGBedrockChatModelConfig insertBedrockChatModelConfig(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.BedrockChatModelsConfigurationControllerApi();
let body = new BrainClient.GBedrockChatModelConfig(); // GBedrockChatModelConfig | 

apiInstance.insertBedrockChatModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockChatModelConfig**](GBedrockChatModelConfig.md)|  | 

### Return type

[**OperationStatusGBedrockChatModelConfig**](OperationStatusGBedrockChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateBedrockChatModelConfig"></a>
# **updateBedrockChatModelConfig**
> OperationStatusGBedrockChatModelConfig updateBedrockChatModelConfig(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.BedrockChatModelsConfigurationControllerApi();
let body = new BrainClient.GBedrockChatModelConfig(); // GBedrockChatModelConfig | 

apiInstance.updateBedrockChatModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockChatModelConfig**](GBedrockChatModelConfig.md)|  | 

### Return type

[**OperationStatusGBedrockChatModelConfig**](OperationStatusGBedrockChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

