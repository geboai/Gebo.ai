# GeboAiClient.GoogleVertexEmbeddingModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteGoogleVertexEmbeddingModelConfig**](GoogleVertexEmbeddingModelsConfigurationControllerApi.md#deleteGoogleVertexEmbeddingModelConfig) | **POST** /api/admin/GoogleVertexEmbeddingModelsConfigurationController/deleteGoogleVertexEmbeddingModelConfig | 
[**findGoogleVertexEmbeddingModelConfigByCode**](GoogleVertexEmbeddingModelsConfigurationControllerApi.md#findGoogleVertexEmbeddingModelConfigByCode) | **GET** /api/admin/GoogleVertexEmbeddingModelsConfigurationController/findGoogleVertexEmbeddingModelConfigByCode | 
[**getGoogleVertexEmbeddingModels**](GoogleVertexEmbeddingModelsConfigurationControllerApi.md#getGoogleVertexEmbeddingModels) | **POST** /api/admin/GoogleVertexEmbeddingModelsConfigurationController/getGoogleVertexEmbeddingModels | 
[**insertGoogleVertexEmbeddingModelConfig**](GoogleVertexEmbeddingModelsConfigurationControllerApi.md#insertGoogleVertexEmbeddingModelConfig) | **POST** /api/admin/GoogleVertexEmbeddingModelsConfigurationController/insertGoogleVertexEmbeddingModelConfig | 
[**updateGoogleVertexEmbeddingModelConfig**](GoogleVertexEmbeddingModelsConfigurationControllerApi.md#updateGoogleVertexEmbeddingModelConfig) | **POST** /api/admin/GoogleVertexEmbeddingModelsConfigurationController/updateGoogleVertexEmbeddingModelConfig | 

<a name="deleteGoogleVertexEmbeddingModelConfig"></a>
# **deleteGoogleVertexEmbeddingModelConfig**
> OperationStatusBoolean deleteGoogleVertexEmbeddingModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleVertexEmbeddingModelsConfigurationControllerApi();
let body = new GeboAiClient.GGoogleVertexEmbeddingModelConfig(); // GGoogleVertexEmbeddingModelConfig | 

apiInstance.deleteGoogleVertexEmbeddingModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGoogleVertexEmbeddingModelConfig**](GGoogleVertexEmbeddingModelConfig.md)|  | 

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findGoogleVertexEmbeddingModelConfigByCode"></a>
# **findGoogleVertexEmbeddingModelConfigByCode**
> GGoogleVertexEmbeddingModelConfig findGoogleVertexEmbeddingModelConfigByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleVertexEmbeddingModelsConfigurationControllerApi();
let code = "code_example"; // String | 

apiInstance.findGoogleVertexEmbeddingModelConfigByCode(code).then((data) => {
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

[**GGoogleVertexEmbeddingModelConfig**](GGoogleVertexEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getGoogleVertexEmbeddingModels"></a>
# **getGoogleVertexEmbeddingModels**
> OperationStatusListGGoogleVertexEmbeddingModelChoice getGoogleVertexEmbeddingModels(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleVertexEmbeddingModelsConfigurationControllerApi();
let body = new GeboAiClient.GGoogleVertexEmbeddingModelConfig(); // GGoogleVertexEmbeddingModelConfig | 

apiInstance.getGoogleVertexEmbeddingModels(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGoogleVertexEmbeddingModelConfig**](GGoogleVertexEmbeddingModelConfig.md)|  | 

### Return type

[**OperationStatusListGGoogleVertexEmbeddingModelChoice**](OperationStatusListGGoogleVertexEmbeddingModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertGoogleVertexEmbeddingModelConfig"></a>
# **insertGoogleVertexEmbeddingModelConfig**
> OperationStatusGGoogleVertexEmbeddingModelConfig insertGoogleVertexEmbeddingModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleVertexEmbeddingModelsConfigurationControllerApi();
let body = new GeboAiClient.GGoogleVertexEmbeddingModelConfig(); // GGoogleVertexEmbeddingModelConfig | 

apiInstance.insertGoogleVertexEmbeddingModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGoogleVertexEmbeddingModelConfig**](GGoogleVertexEmbeddingModelConfig.md)|  | 

### Return type

[**OperationStatusGGoogleVertexEmbeddingModelConfig**](OperationStatusGGoogleVertexEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateGoogleVertexEmbeddingModelConfig"></a>
# **updateGoogleVertexEmbeddingModelConfig**
> OperationStatusGGoogleVertexEmbeddingModelConfig updateGoogleVertexEmbeddingModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleVertexEmbeddingModelsConfigurationControllerApi();
let body = new GeboAiClient.GGoogleVertexEmbeddingModelConfig(); // GGoogleVertexEmbeddingModelConfig | 

apiInstance.updateGoogleVertexEmbeddingModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGoogleVertexEmbeddingModelConfig**](GGoogleVertexEmbeddingModelConfig.md)|  | 

### Return type

[**OperationStatusGGoogleVertexEmbeddingModelConfig**](OperationStatusGGoogleVertexEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

