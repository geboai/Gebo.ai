# GeboAiClient.BedrockRankerModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteBedrockRankerModelConfig**](BedrockRankerModelsConfigurationControllerApi.md#deleteBedrockRankerModelConfig) | **POST** /api/admin/BedrockRankerModelsConfigurationController/deleteBedrockRankerModelConfig | 
[**findBedrockRankerModelConfigByCode**](BedrockRankerModelsConfigurationControllerApi.md#findBedrockRankerModelConfigByCode) | **GET** /api/admin/BedrockRankerModelsConfigurationController/findBedrockRankerModelConfigByCode | 
[**getBedrockRankerModels**](BedrockRankerModelsConfigurationControllerApi.md#getBedrockRankerModels) | **POST** /api/admin/BedrockRankerModelsConfigurationController/getBedrockRankerModels | 
[**insertBedrockRankerModelConfig**](BedrockRankerModelsConfigurationControllerApi.md#insertBedrockRankerModelConfig) | **POST** /api/admin/BedrockRankerModelsConfigurationController/insertBedrockRankerModelConfig | 
[**updateBedrockRankerModelConfig**](BedrockRankerModelsConfigurationControllerApi.md#updateBedrockRankerModelConfig) | **POST** /api/admin/BedrockRankerModelsConfigurationController/updateBedrockRankerModelConfig | 

<a name="deleteBedrockRankerModelConfig"></a>
# **deleteBedrockRankerModelConfig**
> OperationStatusBoolean deleteBedrockRankerModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.BedrockRankerModelsConfigurationControllerApi();
let body = new GeboAiClient.GBedrockRankerModelConfig(); // GBedrockRankerModelConfig | 

apiInstance.deleteBedrockRankerModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockRankerModelConfig**](GBedrockRankerModelConfig.md)|  | 

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findBedrockRankerModelConfigByCode"></a>
# **findBedrockRankerModelConfigByCode**
> GBedrockRankerModelConfig findBedrockRankerModelConfigByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.BedrockRankerModelsConfigurationControllerApi();
let code = "code_example"; // String | 

apiInstance.findBedrockRankerModelConfigByCode(code).then((data) => {
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

[**GBedrockRankerModelConfig**](GBedrockRankerModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getBedrockRankerModels"></a>
# **getBedrockRankerModels**
> OperationStatusListGBedrockRankerModelChoice getBedrockRankerModels(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.BedrockRankerModelsConfigurationControllerApi();
let body = new GeboAiClient.GBedrockRankerModelConfig(); // GBedrockRankerModelConfig | 

apiInstance.getBedrockRankerModels(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockRankerModelConfig**](GBedrockRankerModelConfig.md)|  | 

### Return type

[**OperationStatusListGBedrockRankerModelChoice**](OperationStatusListGBedrockRankerModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertBedrockRankerModelConfig"></a>
# **insertBedrockRankerModelConfig**
> OperationStatusGBedrockRankerModelConfig insertBedrockRankerModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.BedrockRankerModelsConfigurationControllerApi();
let body = new GeboAiClient.GBedrockRankerModelConfig(); // GBedrockRankerModelConfig | 

apiInstance.insertBedrockRankerModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockRankerModelConfig**](GBedrockRankerModelConfig.md)|  | 

### Return type

[**OperationStatusGBedrockRankerModelConfig**](OperationStatusGBedrockRankerModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateBedrockRankerModelConfig"></a>
# **updateBedrockRankerModelConfig**
> OperationStatusGBedrockRankerModelConfig updateBedrockRankerModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.BedrockRankerModelsConfigurationControllerApi();
let body = new GeboAiClient.GBedrockRankerModelConfig(); // GBedrockRankerModelConfig | 

apiInstance.updateBedrockRankerModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GBedrockRankerModelConfig**](GBedrockRankerModelConfig.md)|  | 

### Return type

[**OperationStatusGBedrockRankerModelConfig**](OperationStatusGBedrockRankerModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

