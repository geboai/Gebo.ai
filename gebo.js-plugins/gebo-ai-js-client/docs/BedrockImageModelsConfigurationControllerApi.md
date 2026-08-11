# GeboAiClient.BedrockImageModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteBedrockImageModelConfig**](BedrockImageModelsConfigurationControllerApi.md#deleteBedrockImageModelConfig) | **POST** /api/admin/BedrockImageModelsConfigurationController/deleteBedrockImageModelConfig | 
[**findBedrockImageModelConfigByCode**](BedrockImageModelsConfigurationControllerApi.md#findBedrockImageModelConfigByCode) | **GET** /api/admin/BedrockImageModelsConfigurationController/findBedrockImageModelConfigByCode | 
[**getBedrockImageModels**](BedrockImageModelsConfigurationControllerApi.md#getBedrockImageModels) | **POST** /api/admin/BedrockImageModelsConfigurationController/getBedrockImageModels | 
[**insertBedrockImageModelConfig**](BedrockImageModelsConfigurationControllerApi.md#insertBedrockImageModelConfig) | **POST** /api/admin/BedrockImageModelsConfigurationController/insertBedrockImageModelConfig | 
[**updateBedrockImageModelConfig**](BedrockImageModelsConfigurationControllerApi.md#updateBedrockImageModelConfig) | **POST** /api/admin/BedrockImageModelsConfigurationController/updateBedrockImageModelConfig | 

<a name="deleteBedrockImageModelConfig"></a>
# **deleteBedrockImageModelConfig**
> OperationStatusBoolean deleteBedrockImageModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.BedrockImageModelsConfigurationControllerApi();
let body = new GeboAiClient.GBedrockImageModelConfig(); // GBedrockImageModelConfig | 

apiInstance.deleteBedrockImageModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockImageModelConfig**](GBedrockImageModelConfig.md)|  | 

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findBedrockImageModelConfigByCode"></a>
# **findBedrockImageModelConfigByCode**
> GBedrockImageModelConfig findBedrockImageModelConfigByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.BedrockImageModelsConfigurationControllerApi();
let code = "code_example"; // String | 

apiInstance.findBedrockImageModelConfigByCode(code).then((data) => {
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

[**GBedrockImageModelConfig**](GBedrockImageModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getBedrockImageModels"></a>
# **getBedrockImageModels**
> OperationStatusListGBedrockImageModelChoice getBedrockImageModels(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.BedrockImageModelsConfigurationControllerApi();
let body = new GeboAiClient.GBedrockImageModelConfig(); // GBedrockImageModelConfig | 

apiInstance.getBedrockImageModels(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockImageModelConfig**](GBedrockImageModelConfig.md)|  | 

### Return type

[**OperationStatusListGBedrockImageModelChoice**](OperationStatusListGBedrockImageModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertBedrockImageModelConfig"></a>
# **insertBedrockImageModelConfig**
> OperationStatusGBedrockImageModelConfig insertBedrockImageModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.BedrockImageModelsConfigurationControllerApi();
let body = new GeboAiClient.GBedrockImageModelConfig(); // GBedrockImageModelConfig | 

apiInstance.insertBedrockImageModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockImageModelConfig**](GBedrockImageModelConfig.md)|  | 

### Return type

[**OperationStatusGBedrockImageModelConfig**](OperationStatusGBedrockImageModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateBedrockImageModelConfig"></a>
# **updateBedrockImageModelConfig**
> OperationStatusGBedrockImageModelConfig updateBedrockImageModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.BedrockImageModelsConfigurationControllerApi();
let body = new GeboAiClient.GBedrockImageModelConfig(); // GBedrockImageModelConfig | 

apiInstance.updateBedrockImageModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockImageModelConfig**](GBedrockImageModelConfig.md)|  | 

### Return type

[**OperationStatusGBedrockImageModelConfig**](OperationStatusGBedrockImageModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

