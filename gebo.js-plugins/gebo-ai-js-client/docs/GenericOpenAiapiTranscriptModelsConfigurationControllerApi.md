# GeboAiClient.GenericOpenAiapiTranscriptModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteGenericOpenAIAPITranscriptModelConfig**](GenericOpenAiapiTranscriptModelsConfigurationControllerApi.md#deleteGenericOpenAIAPITranscriptModelConfig) | **POST** /api/admin/GenericOpenAIAPITranscriptModelsConfigurationController/deleteGenericOpenAIAPITranscriptModelConfig | 
[**findGenericOpenAIAPITranscriptModelConfigByCode**](GenericOpenAiapiTranscriptModelsConfigurationControllerApi.md#findGenericOpenAIAPITranscriptModelConfigByCode) | **GET** /api/admin/GenericOpenAIAPITranscriptModelsConfigurationController/findGenericOpenAIAPITranscriptModelConfigByCode | 
[**getGenericOpenAIAPITranscriptModels**](GenericOpenAiapiTranscriptModelsConfigurationControllerApi.md#getGenericOpenAIAPITranscriptModels) | **POST** /api/admin/GenericOpenAIAPITranscriptModelsConfigurationController/getGenericOpenAIAPITranscriptModels | 
[**getGenericOpenAITranscriptModelConfigs**](GenericOpenAiapiTranscriptModelsConfigurationControllerApi.md#getGenericOpenAITranscriptModelConfigs) | **GET** /api/admin/GenericOpenAIAPITranscriptModelsConfigurationController/getGenericOpenAITranscriptModelConfigs | 
[**getGenericOpenAITranscriptModelTypes**](GenericOpenAiapiTranscriptModelsConfigurationControllerApi.md#getGenericOpenAITranscriptModelTypes) | **GET** /api/admin/GenericOpenAIAPITranscriptModelsConfigurationController/getGenericOpenAITranscriptModelTypes | 
[**insertGenericOpenAIAPITranscriptModelConfig**](GenericOpenAiapiTranscriptModelsConfigurationControllerApi.md#insertGenericOpenAIAPITranscriptModelConfig) | **POST** /api/admin/GenericOpenAIAPITranscriptModelsConfigurationController/insertGenericOpenAIAPITranscriptModelConfig | 
[**updateGenericOpenAIAPITranscriptModelConfig**](GenericOpenAiapiTranscriptModelsConfigurationControllerApi.md#updateGenericOpenAIAPITranscriptModelConfig) | **POST** /api/admin/GenericOpenAIAPITranscriptModelsConfigurationController/updateGenericOpenAIAPITranscriptModelConfig | 

<a name="deleteGenericOpenAIAPITranscriptModelConfig"></a>
# **deleteGenericOpenAIAPITranscriptModelConfig**
> OperationStatusBoolean deleteGenericOpenAIAPITranscriptModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GenericOpenAiapiTranscriptModelsConfigurationControllerApi();
let body = new GeboAiClient.GenericOpenAIAPITranscriptModelConfig(); // GenericOpenAIAPITranscriptModelConfig | 

apiInstance.deleteGenericOpenAIAPITranscriptModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPITranscriptModelConfig**](GenericOpenAIAPITranscriptModelConfig.md)|  | 

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findGenericOpenAIAPITranscriptModelConfigByCode"></a>
# **findGenericOpenAIAPITranscriptModelConfigByCode**
> GenericOpenAIAPITranscriptModelConfig findGenericOpenAIAPITranscriptModelConfigByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GenericOpenAiapiTranscriptModelsConfigurationControllerApi();
let code = "code_example"; // String | 

apiInstance.findGenericOpenAIAPITranscriptModelConfigByCode(code).then((data) => {
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

[**GenericOpenAIAPITranscriptModelConfig**](GenericOpenAIAPITranscriptModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getGenericOpenAIAPITranscriptModels"></a>
# **getGenericOpenAIAPITranscriptModels**
> OperationStatusListGenericOpenAIAPITranscriptModelChoice getGenericOpenAIAPITranscriptModels(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GenericOpenAiapiTranscriptModelsConfigurationControllerApi();
let body = new GeboAiClient.GenericOpenAIAPITranscriptModelConfig(); // GenericOpenAIAPITranscriptModelConfig | 

apiInstance.getGenericOpenAIAPITranscriptModels(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPITranscriptModelConfig**](GenericOpenAIAPITranscriptModelConfig.md)|  | 

### Return type

[**OperationStatusListGenericOpenAIAPITranscriptModelChoice**](OperationStatusListGenericOpenAIAPITranscriptModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getGenericOpenAITranscriptModelConfigs"></a>
# **getGenericOpenAITranscriptModelConfigs**
> [GenericOpenAIAPITranscriptModelConfig] getGenericOpenAITranscriptModelConfigs()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GenericOpenAiapiTranscriptModelsConfigurationControllerApi();
apiInstance.getGenericOpenAITranscriptModelConfigs().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GenericOpenAIAPITranscriptModelConfig]**](GenericOpenAIAPITranscriptModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getGenericOpenAITranscriptModelTypes"></a>
# **getGenericOpenAITranscriptModelTypes**
> [GenericOpenAITranscriptModelType] getGenericOpenAITranscriptModelTypes()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GenericOpenAiapiTranscriptModelsConfigurationControllerApi();
apiInstance.getGenericOpenAITranscriptModelTypes().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GenericOpenAITranscriptModelType]**](GenericOpenAITranscriptModelType.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="insertGenericOpenAIAPITranscriptModelConfig"></a>
# **insertGenericOpenAIAPITranscriptModelConfig**
> OperationStatusGenericOpenAIAPITranscriptModelConfig insertGenericOpenAIAPITranscriptModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GenericOpenAiapiTranscriptModelsConfigurationControllerApi();
let body = new GeboAiClient.GenericOpenAIAPITranscriptModelConfig(); // GenericOpenAIAPITranscriptModelConfig | 

apiInstance.insertGenericOpenAIAPITranscriptModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPITranscriptModelConfig**](GenericOpenAIAPITranscriptModelConfig.md)|  | 

### Return type

[**OperationStatusGenericOpenAIAPITranscriptModelConfig**](OperationStatusGenericOpenAIAPITranscriptModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateGenericOpenAIAPITranscriptModelConfig"></a>
# **updateGenericOpenAIAPITranscriptModelConfig**
> OperationStatusGenericOpenAIAPITranscriptModelConfig updateGenericOpenAIAPITranscriptModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GenericOpenAiapiTranscriptModelsConfigurationControllerApi();
let body = new GeboAiClient.GenericOpenAIAPITranscriptModelConfig(); // GenericOpenAIAPITranscriptModelConfig | 

apiInstance.updateGenericOpenAIAPITranscriptModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPITranscriptModelConfig**](GenericOpenAIAPITranscriptModelConfig.md)|  | 

### Return type

[**OperationStatusGenericOpenAIAPITranscriptModelConfig**](OperationStatusGenericOpenAIAPITranscriptModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

