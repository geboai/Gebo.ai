# GeboAiClient.GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteGenericOpenAIAPITextToSpeechModelConfig**](GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi.md#deleteGenericOpenAIAPITextToSpeechModelConfig) | **POST** /api/admin/GenericOpenAIAPITextToSpeechModelsConfigurationController/deleteGenericOpenAIAPITextToSpeechModelConfig | 
[**findGenericOpenAIAPITextToSpeechModelConfigByCode**](GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi.md#findGenericOpenAIAPITextToSpeechModelConfigByCode) | **GET** /api/admin/GenericOpenAIAPITextToSpeechModelsConfigurationController/findGenericOpenAIAPITextToSpeechModelConfigByCode | 
[**getGenericOpenAIAPITextToSpeechModels**](GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi.md#getGenericOpenAIAPITextToSpeechModels) | **POST** /api/admin/GenericOpenAIAPITextToSpeechModelsConfigurationController/getGenericOpenAIAPITextToSpeechModels | 
[**getGenericOpenAITextToSpeechModelConfigs**](GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi.md#getGenericOpenAITextToSpeechModelConfigs) | **GET** /api/admin/GenericOpenAIAPITextToSpeechModelsConfigurationController/getGenericOpenAITextToSpeechModelConfigs | 
[**getGenericOpenAITextToSpeechModelTypes**](GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi.md#getGenericOpenAITextToSpeechModelTypes) | **GET** /api/admin/GenericOpenAIAPITextToSpeechModelsConfigurationController/getGenericOpenAITextToSpeechModelTypes | 
[**insertGenericOpenAIAPITextToSpeechModelConfig**](GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi.md#insertGenericOpenAIAPITextToSpeechModelConfig) | **POST** /api/admin/GenericOpenAIAPITextToSpeechModelsConfigurationController/insertGenericOpenAIAPITextToSpeechModelConfig | 
[**updateGenericOpenAIAPITextToSpeechModelConfig**](GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi.md#updateGenericOpenAIAPITextToSpeechModelConfig) | **POST** /api/admin/GenericOpenAIAPITextToSpeechModelsConfigurationController/updateGenericOpenAIAPITextToSpeechModelConfig | 

<a name="deleteGenericOpenAIAPITextToSpeechModelConfig"></a>
# **deleteGenericOpenAIAPITextToSpeechModelConfig**
> OperationStatusBoolean deleteGenericOpenAIAPITextToSpeechModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi();
let body = new GeboAiClient.GenericOpenAIAPITextToSpeechModelConfig(); // GenericOpenAIAPITextToSpeechModelConfig | 

apiInstance.deleteGenericOpenAIAPITextToSpeechModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPITextToSpeechModelConfig**](GenericOpenAIAPITextToSpeechModelConfig.md)|  | 

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findGenericOpenAIAPITextToSpeechModelConfigByCode"></a>
# **findGenericOpenAIAPITextToSpeechModelConfigByCode**
> GenericOpenAIAPITextToSpeechModelConfig findGenericOpenAIAPITextToSpeechModelConfigByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi();
let code = "code_example"; // String | 

apiInstance.findGenericOpenAIAPITextToSpeechModelConfigByCode(code).then((data) => {
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

[**GenericOpenAIAPITextToSpeechModelConfig**](GenericOpenAIAPITextToSpeechModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getGenericOpenAIAPITextToSpeechModels"></a>
# **getGenericOpenAIAPITextToSpeechModels**
> OperationStatusListGenericOpenAIAPITextToSpeechModelChoice getGenericOpenAIAPITextToSpeechModels(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi();
let body = new GeboAiClient.GenericOpenAIAPITextToSpeechModelConfig(); // GenericOpenAIAPITextToSpeechModelConfig | 

apiInstance.getGenericOpenAIAPITextToSpeechModels(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPITextToSpeechModelConfig**](GenericOpenAIAPITextToSpeechModelConfig.md)|  | 

### Return type

[**OperationStatusListGenericOpenAIAPITextToSpeechModelChoice**](OperationStatusListGenericOpenAIAPITextToSpeechModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getGenericOpenAITextToSpeechModelConfigs"></a>
# **getGenericOpenAITextToSpeechModelConfigs**
> [GenericOpenAIAPITextToSpeechModelConfig] getGenericOpenAITextToSpeechModelConfigs()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi();
apiInstance.getGenericOpenAITextToSpeechModelConfigs().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GenericOpenAIAPITextToSpeechModelConfig]**](GenericOpenAIAPITextToSpeechModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getGenericOpenAITextToSpeechModelTypes"></a>
# **getGenericOpenAITextToSpeechModelTypes**
> [GenericOpenAITextToSpeechModelType] getGenericOpenAITextToSpeechModelTypes()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi();
apiInstance.getGenericOpenAITextToSpeechModelTypes().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GenericOpenAITextToSpeechModelType]**](GenericOpenAITextToSpeechModelType.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="insertGenericOpenAIAPITextToSpeechModelConfig"></a>
# **insertGenericOpenAIAPITextToSpeechModelConfig**
> OperationStatusGenericOpenAIAPITextToSpeechModelConfig insertGenericOpenAIAPITextToSpeechModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi();
let body = new GeboAiClient.GenericOpenAIAPITextToSpeechModelConfig(); // GenericOpenAIAPITextToSpeechModelConfig | 

apiInstance.insertGenericOpenAIAPITextToSpeechModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPITextToSpeechModelConfig**](GenericOpenAIAPITextToSpeechModelConfig.md)|  | 

### Return type

[**OperationStatusGenericOpenAIAPITextToSpeechModelConfig**](OperationStatusGenericOpenAIAPITextToSpeechModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateGenericOpenAIAPITextToSpeechModelConfig"></a>
# **updateGenericOpenAIAPITextToSpeechModelConfig**
> OperationStatusGenericOpenAIAPITextToSpeechModelConfig updateGenericOpenAIAPITextToSpeechModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GenericOpenAiapiTextToSpeechModelsConfigurationControllerApi();
let body = new GeboAiClient.GenericOpenAIAPITextToSpeechModelConfig(); // GenericOpenAIAPITextToSpeechModelConfig | 

apiInstance.updateGenericOpenAIAPITextToSpeechModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPITextToSpeechModelConfig**](GenericOpenAIAPITextToSpeechModelConfig.md)|  | 

### Return type

[**OperationStatusGenericOpenAIAPITextToSpeechModelConfig**](OperationStatusGenericOpenAIAPITextToSpeechModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

