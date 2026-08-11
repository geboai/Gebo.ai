# GeboAiClient.MistralAiEmbeddingModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteMistralAIEmbeddingModelConfig**](MistralAiEmbeddingModelsConfigurationControllerApi.md#deleteMistralAIEmbeddingModelConfig) | **POST** /api/admin/MistralAIEmbeddingModelsConfigurationController/deleteMistralAIEmbeddingModelConfig | 
[**findMistralAIEmbeddingModelConfigByCode**](MistralAiEmbeddingModelsConfigurationControllerApi.md#findMistralAIEmbeddingModelConfigByCode) | **GET** /api/admin/MistralAIEmbeddingModelsConfigurationController/findMistralAIEmbeddingModelConfigByCode | 
[**getMistralAIEmbeddingModels**](MistralAiEmbeddingModelsConfigurationControllerApi.md#getMistralAIEmbeddingModels) | **POST** /api/admin/MistralAIEmbeddingModelsConfigurationController/getMistralAIEmbeddingModels | 
[**insertMistralAIEmbeddingModelConfig**](MistralAiEmbeddingModelsConfigurationControllerApi.md#insertMistralAIEmbeddingModelConfig) | **POST** /api/admin/MistralAIEmbeddingModelsConfigurationController/insertMistralAIEmbeddingModelConfig | 
[**updateMistralAIEmbeddingModelConfig**](MistralAiEmbeddingModelsConfigurationControllerApi.md#updateMistralAIEmbeddingModelConfig) | **POST** /api/admin/MistralAIEmbeddingModelsConfigurationController/updateMistralAIEmbeddingModelConfig | 

<a name="deleteMistralAIEmbeddingModelConfig"></a>
# **deleteMistralAIEmbeddingModelConfig**
> OperationStatusBoolean deleteMistralAIEmbeddingModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.MistralAiEmbeddingModelsConfigurationControllerApi();
let body = new GeboAiClient.GMistralEmbeddingModelConfig(); // GMistralEmbeddingModelConfig | 

apiInstance.deleteMistralAIEmbeddingModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GMistralEmbeddingModelConfig**](GMistralEmbeddingModelConfig.md)|  | 

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findMistralAIEmbeddingModelConfigByCode"></a>
# **findMistralAIEmbeddingModelConfigByCode**
> GMistralEmbeddingModelConfig findMistralAIEmbeddingModelConfigByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.MistralAiEmbeddingModelsConfigurationControllerApi();
let code = "code_example"; // String | 

apiInstance.findMistralAIEmbeddingModelConfigByCode(code).then((data) => {
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

[**GMistralEmbeddingModelConfig**](GMistralEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getMistralAIEmbeddingModels"></a>
# **getMistralAIEmbeddingModels**
> OperationStatusListGMistralEmbeddingModelChoice getMistralAIEmbeddingModels(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.MistralAiEmbeddingModelsConfigurationControllerApi();
let body = new GeboAiClient.GMistralEmbeddingModelConfig(); // GMistralEmbeddingModelConfig | 

apiInstance.getMistralAIEmbeddingModels(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GMistralEmbeddingModelConfig**](GMistralEmbeddingModelConfig.md)|  | 

### Return type

[**OperationStatusListGMistralEmbeddingModelChoice**](OperationStatusListGMistralEmbeddingModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertMistralAIEmbeddingModelConfig"></a>
# **insertMistralAIEmbeddingModelConfig**
> OperationStatusGMistralEmbeddingModelConfig insertMistralAIEmbeddingModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.MistralAiEmbeddingModelsConfigurationControllerApi();
let body = new GeboAiClient.GMistralEmbeddingModelConfig(); // GMistralEmbeddingModelConfig | 

apiInstance.insertMistralAIEmbeddingModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GMistralEmbeddingModelConfig**](GMistralEmbeddingModelConfig.md)|  | 

### Return type

[**OperationStatusGMistralEmbeddingModelConfig**](OperationStatusGMistralEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateMistralAIEmbeddingModelConfig"></a>
# **updateMistralAIEmbeddingModelConfig**
> OperationStatusGMistralEmbeddingModelConfig updateMistralAIEmbeddingModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.MistralAiEmbeddingModelsConfigurationControllerApi();
let body = new GeboAiClient.GMistralEmbeddingModelConfig(); // GMistralEmbeddingModelConfig | 

apiInstance.updateMistralAIEmbeddingModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GMistralEmbeddingModelConfig**](GMistralEmbeddingModelConfig.md)|  | 

### Return type

[**OperationStatusGMistralEmbeddingModelConfig**](OperationStatusGMistralEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

