# GeboAiClient.DeepseekChatModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteDeepseekChatModelConfig**](DeepseekChatModelsConfigurationControllerApi.md#deleteDeepseekChatModelConfig) | **POST** /api/admin/DeepseekChatModelsConfigurationController/deleteDeepseekChatModelConfig | 
[**findDeepseekChatModelConfigByCode**](DeepseekChatModelsConfigurationControllerApi.md#findDeepseekChatModelConfigByCode) | **GET** /api/admin/DeepseekChatModelsConfigurationController/findDeepseekChatModelConfigByCode | 
[**getDeepseekChatModels**](DeepseekChatModelsConfigurationControllerApi.md#getDeepseekChatModels) | **POST** /api/admin/DeepseekChatModelsConfigurationController/getDeepseekModels | 
[**insertDeepseekChatModelConfig**](DeepseekChatModelsConfigurationControllerApi.md#insertDeepseekChatModelConfig) | **POST** /api/admin/DeepseekChatModelsConfigurationController/insertDeepseekChatModelConfig | 
[**updateDeepseekChatModelConfig**](DeepseekChatModelsConfigurationControllerApi.md#updateDeepseekChatModelConfig) | **POST** /api/admin/DeepseekChatModelsConfigurationController/updateDeepseekChatModelConfig | 

<a name="deleteDeepseekChatModelConfig"></a>
# **deleteDeepseekChatModelConfig**
> OperationStatusBoolean deleteDeepseekChatModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.DeepseekChatModelsConfigurationControllerApi();
let body = new GeboAiClient.GDeepseekChatModelConfig(); // GDeepseekChatModelConfig | 

apiInstance.deleteDeepseekChatModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GDeepseekChatModelConfig**](GDeepseekChatModelConfig.md)|  | 

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findDeepseekChatModelConfigByCode"></a>
# **findDeepseekChatModelConfigByCode**
> GDeepseekChatModelConfig findDeepseekChatModelConfigByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.DeepseekChatModelsConfigurationControllerApi();
let code = "code_example"; // String | 

apiInstance.findDeepseekChatModelConfigByCode(code).then((data) => {
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

[**GDeepseekChatModelConfig**](GDeepseekChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getDeepseekChatModels"></a>
# **getDeepseekChatModels**
> OperationStatusListGDeepseekChatModelChoice getDeepseekChatModels(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.DeepseekChatModelsConfigurationControllerApi();
let body = new GeboAiClient.GDeepseekChatModelConfig(); // GDeepseekChatModelConfig | 

apiInstance.getDeepseekChatModels(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GDeepseekChatModelConfig**](GDeepseekChatModelConfig.md)|  | 

### Return type

[**OperationStatusListGDeepseekChatModelChoice**](OperationStatusListGDeepseekChatModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertDeepseekChatModelConfig"></a>
# **insertDeepseekChatModelConfig**
> OperationStatusGDeepseekChatModelConfig insertDeepseekChatModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.DeepseekChatModelsConfigurationControllerApi();
let body = new GeboAiClient.GDeepseekChatModelConfig(); // GDeepseekChatModelConfig | 

apiInstance.insertDeepseekChatModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GDeepseekChatModelConfig**](GDeepseekChatModelConfig.md)|  | 

### Return type

[**OperationStatusGDeepseekChatModelConfig**](OperationStatusGDeepseekChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateDeepseekChatModelConfig"></a>
# **updateDeepseekChatModelConfig**
> OperationStatusGDeepseekChatModelConfig updateDeepseekChatModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.DeepseekChatModelsConfigurationControllerApi();
let body = new GeboAiClient.GDeepseekChatModelConfig(); // GDeepseekChatModelConfig | 

apiInstance.updateDeepseekChatModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GDeepseekChatModelConfig**](GDeepseekChatModelConfig.md)|  | 

### Return type

[**OperationStatusGDeepseekChatModelConfig**](OperationStatusGDeepseekChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

