# BrainClient.AnthropicChatModelsConfigurationControllerApi

All URIs are relative to *http://localhost:13001/brain*

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
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.AnthropicChatModelsConfigurationControllerApi();
let body = new BrainClient.GAnthropicChatModelConfig(); // GAnthropicChatModelConfig | 

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
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.AnthropicChatModelsConfigurationControllerApi();
let code = null; // Object | 

apiInstance.findAnthropicChatModelConfigByCode(code).then((data) => {
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
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.AnthropicChatModelsConfigurationControllerApi();
let body = new BrainClient.GAnthropicChatModelConfig(); // GAnthropicChatModelConfig | 

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
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.AnthropicChatModelsConfigurationControllerApi();
let body = new BrainClient.GAnthropicChatModelConfig(); // GAnthropicChatModelConfig | 

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
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.AnthropicChatModelsConfigurationControllerApi();
let body = new BrainClient.GAnthropicChatModelConfig(); // GAnthropicChatModelConfig | 

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

