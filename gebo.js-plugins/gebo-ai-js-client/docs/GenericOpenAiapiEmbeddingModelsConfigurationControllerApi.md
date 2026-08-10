# GeboAiClient.GenericOpenAiapiEmbeddingModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteGenericOpenAIAPIEmbeddingModelConfig**](GenericOpenAiapiEmbeddingModelsConfigurationControllerApi.md#deleteGenericOpenAIAPIEmbeddingModelConfig) | **POST** /api/admin/GenericOpenAIAPIEmbeddingModelsConfigurationController/deleteGenericOpenAIAPIEmbeddingModelConfig | 
[**findGenericOpenAIAPIEmbeddingModelConfigByCode**](GenericOpenAiapiEmbeddingModelsConfigurationControllerApi.md#findGenericOpenAIAPIEmbeddingModelConfigByCode) | **GET** /api/admin/GenericOpenAIAPIEmbeddingModelsConfigurationController/findGenericOpenAIAPIEmbeddingModelConfigByCode | 
[**getGenericOpenAIAPIEmbeddingModels**](GenericOpenAiapiEmbeddingModelsConfigurationControllerApi.md#getGenericOpenAIAPIEmbeddingModels) | **POST** /api/admin/GenericOpenAIAPIEmbeddingModelsConfigurationController/getGenericOpenAIAPIEmbeddingModels | 
[**getGenericOpenAIEmbeddingModelTypes**](GenericOpenAiapiEmbeddingModelsConfigurationControllerApi.md#getGenericOpenAIEmbeddingModelTypes) | **GET** /api/admin/GenericOpenAIAPIEmbeddingModelsConfigurationController/getGenericOpenAIEmbeddingModelTypes | 
[**insertGenericOpenAIAPIEmbeddingModelConfig**](GenericOpenAiapiEmbeddingModelsConfigurationControllerApi.md#insertGenericOpenAIAPIEmbeddingModelConfig) | **POST** /api/admin/GenericOpenAIAPIEmbeddingModelsConfigurationController/insertGenericOpenAIAPIEmbeddingModelConfig | 
[**updateGenericOpenAIAPIEmbeddingModelConfig**](GenericOpenAiapiEmbeddingModelsConfigurationControllerApi.md#updateGenericOpenAIAPIEmbeddingModelConfig) | **POST** /api/admin/GenericOpenAIAPIEmbeddingModelsConfigurationController/updateGenericOpenAIAPIEmbeddingModelConfig | 

<a name="deleteGenericOpenAIAPIEmbeddingModelConfig"></a>
# **deleteGenericOpenAIAPIEmbeddingModelConfig**
> OperationStatusBoolean deleteGenericOpenAIAPIEmbeddingModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GenericOpenAiapiEmbeddingModelsConfigurationControllerApi();
let body = new GeboAiClient.GenericOpenAIAPIEmbeddingModelConfig(); // GenericOpenAIAPIEmbeddingModelConfig | 

apiInstance.deleteGenericOpenAIAPIEmbeddingModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPIEmbeddingModelConfig**](GenericOpenAIAPIEmbeddingModelConfig.md)|  | 

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findGenericOpenAIAPIEmbeddingModelConfigByCode"></a>
# **findGenericOpenAIAPIEmbeddingModelConfigByCode**
> GenericOpenAIAPIEmbeddingModelConfig findGenericOpenAIAPIEmbeddingModelConfigByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GenericOpenAiapiEmbeddingModelsConfigurationControllerApi();
let code = "code_example"; // String | 

apiInstance.findGenericOpenAIAPIEmbeddingModelConfigByCode(code).then((data) => {
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

[**GenericOpenAIAPIEmbeddingModelConfig**](GenericOpenAIAPIEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getGenericOpenAIAPIEmbeddingModels"></a>
# **getGenericOpenAIAPIEmbeddingModels**
> OperationStatusListGenericOpenAIAPIEmbeddingModelChoice getGenericOpenAIAPIEmbeddingModels(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GenericOpenAiapiEmbeddingModelsConfigurationControllerApi();
let body = new GeboAiClient.GenericOpenAIAPIEmbeddingModelConfig(); // GenericOpenAIAPIEmbeddingModelConfig | 

apiInstance.getGenericOpenAIAPIEmbeddingModels(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPIEmbeddingModelConfig**](GenericOpenAIAPIEmbeddingModelConfig.md)|  | 

### Return type

[**OperationStatusListGenericOpenAIAPIEmbeddingModelChoice**](OperationStatusListGenericOpenAIAPIEmbeddingModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getGenericOpenAIEmbeddingModelTypes"></a>
# **getGenericOpenAIEmbeddingModelTypes**
> [GenericOpenAIEmbeddingModelTypeConfig] getGenericOpenAIEmbeddingModelTypes()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GenericOpenAiapiEmbeddingModelsConfigurationControllerApi();
apiInstance.getGenericOpenAIEmbeddingModelTypes().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GenericOpenAIEmbeddingModelTypeConfig]**](GenericOpenAIEmbeddingModelTypeConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="insertGenericOpenAIAPIEmbeddingModelConfig"></a>
# **insertGenericOpenAIAPIEmbeddingModelConfig**
> OperationStatusGenericOpenAIAPIEmbeddingModelConfig insertGenericOpenAIAPIEmbeddingModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GenericOpenAiapiEmbeddingModelsConfigurationControllerApi();
let body = new GeboAiClient.GenericOpenAIAPIEmbeddingModelConfig(); // GenericOpenAIAPIEmbeddingModelConfig | 

apiInstance.insertGenericOpenAIAPIEmbeddingModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPIEmbeddingModelConfig**](GenericOpenAIAPIEmbeddingModelConfig.md)|  | 

### Return type

[**OperationStatusGenericOpenAIAPIEmbeddingModelConfig**](OperationStatusGenericOpenAIAPIEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateGenericOpenAIAPIEmbeddingModelConfig"></a>
# **updateGenericOpenAIAPIEmbeddingModelConfig**
> OperationStatusGenericOpenAIAPIEmbeddingModelConfig updateGenericOpenAIAPIEmbeddingModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GenericOpenAiapiEmbeddingModelsConfigurationControllerApi();
let body = new GeboAiClient.GenericOpenAIAPIEmbeddingModelConfig(); // GenericOpenAIAPIEmbeddingModelConfig | 

apiInstance.updateGenericOpenAIAPIEmbeddingModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPIEmbeddingModelConfig**](GenericOpenAIAPIEmbeddingModelConfig.md)|  | 

### Return type

[**OperationStatusGenericOpenAIAPIEmbeddingModelConfig**](OperationStatusGenericOpenAIAPIEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

