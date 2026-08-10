# GeboAiClient.GenericOpenAiapiImageModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteGenericOpenAIAPIImageModelConfig**](GenericOpenAiapiImageModelsConfigurationControllerApi.md#deleteGenericOpenAIAPIImageModelConfig) | **POST** /api/admin/GenericOpenAIAPIImageModelsConfigurationController/deleteGenericOpenAIAPIImageModelConfig | 
[**findGenericOpenAIAPIImageModelConfigByCode**](GenericOpenAiapiImageModelsConfigurationControllerApi.md#findGenericOpenAIAPIImageModelConfigByCode) | **GET** /api/admin/GenericOpenAIAPIImageModelsConfigurationController/findGenericOpenAIAPIImageModelConfigByCode | 
[**getGenericOpenAIAPIImageModels**](GenericOpenAiapiImageModelsConfigurationControllerApi.md#getGenericOpenAIAPIImageModels) | **POST** /api/admin/GenericOpenAIAPIImageModelsConfigurationController/getGenericOpenAIAPIImageModels | 
[**getGenericOpenAIImageModelConfigs**](GenericOpenAiapiImageModelsConfigurationControllerApi.md#getGenericOpenAIImageModelConfigs) | **GET** /api/admin/GenericOpenAIAPIImageModelsConfigurationController/getGenericOpenAIImageModelConfigs | 
[**getGenericOpenAIImageModelTypes**](GenericOpenAiapiImageModelsConfigurationControllerApi.md#getGenericOpenAIImageModelTypes) | **GET** /api/admin/GenericOpenAIAPIImageModelsConfigurationController/getGenericOpenAIImageModelTypes | 
[**insertGenericOpenAIAPIImageModelConfig**](GenericOpenAiapiImageModelsConfigurationControllerApi.md#insertGenericOpenAIAPIImageModelConfig) | **POST** /api/admin/GenericOpenAIAPIImageModelsConfigurationController/insertGenericOpenAIAPIImageModelConfig | 
[**updateGenericOpenAIAPIImageModelConfig**](GenericOpenAiapiImageModelsConfigurationControllerApi.md#updateGenericOpenAIAPIImageModelConfig) | **POST** /api/admin/GenericOpenAIAPIImageModelsConfigurationController/updateGenericOpenAIAPIImageModelConfig | 

<a name="deleteGenericOpenAIAPIImageModelConfig"></a>
# **deleteGenericOpenAIAPIImageModelConfig**
> OperationStatusBoolean deleteGenericOpenAIAPIImageModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GenericOpenAiapiImageModelsConfigurationControllerApi();
let body = new GeboAiClient.GenericOpenAIAPIImageModelConfig(); // GenericOpenAIAPIImageModelConfig | 

apiInstance.deleteGenericOpenAIAPIImageModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPIImageModelConfig**](GenericOpenAIAPIImageModelConfig.md)|  | 

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findGenericOpenAIAPIImageModelConfigByCode"></a>
# **findGenericOpenAIAPIImageModelConfigByCode**
> GenericOpenAIAPIImageModelConfig findGenericOpenAIAPIImageModelConfigByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GenericOpenAiapiImageModelsConfigurationControllerApi();
let code = "code_example"; // String | 

apiInstance.findGenericOpenAIAPIImageModelConfigByCode(code).then((data) => {
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

[**GenericOpenAIAPIImageModelConfig**](GenericOpenAIAPIImageModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getGenericOpenAIAPIImageModels"></a>
# **getGenericOpenAIAPIImageModels**
> OperationStatusListGenericOpenAIAPIImageModelChoice getGenericOpenAIAPIImageModels(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GenericOpenAiapiImageModelsConfigurationControllerApi();
let body = new GeboAiClient.GenericOpenAIAPIImageModelConfig(); // GenericOpenAIAPIImageModelConfig | 

apiInstance.getGenericOpenAIAPIImageModels(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPIImageModelConfig**](GenericOpenAIAPIImageModelConfig.md)|  | 

### Return type

[**OperationStatusListGenericOpenAIAPIImageModelChoice**](OperationStatusListGenericOpenAIAPIImageModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getGenericOpenAIImageModelConfigs"></a>
# **getGenericOpenAIImageModelConfigs**
> [GenericOpenAIAPIImageModelConfig] getGenericOpenAIImageModelConfigs()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GenericOpenAiapiImageModelsConfigurationControllerApi();
apiInstance.getGenericOpenAIImageModelConfigs().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GenericOpenAIAPIImageModelConfig]**](GenericOpenAIAPIImageModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getGenericOpenAIImageModelTypes"></a>
# **getGenericOpenAIImageModelTypes**
> [GenericOpenAIImageModelTypeConfig] getGenericOpenAIImageModelTypes()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GenericOpenAiapiImageModelsConfigurationControllerApi();
apiInstance.getGenericOpenAIImageModelTypes().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GenericOpenAIImageModelTypeConfig]**](GenericOpenAIImageModelTypeConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="insertGenericOpenAIAPIImageModelConfig"></a>
# **insertGenericOpenAIAPIImageModelConfig**
> OperationStatusGenericOpenAIAPIImageModelConfig insertGenericOpenAIAPIImageModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GenericOpenAiapiImageModelsConfigurationControllerApi();
let body = new GeboAiClient.GenericOpenAIAPIImageModelConfig(); // GenericOpenAIAPIImageModelConfig | 

apiInstance.insertGenericOpenAIAPIImageModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPIImageModelConfig**](GenericOpenAIAPIImageModelConfig.md)|  | 

### Return type

[**OperationStatusGenericOpenAIAPIImageModelConfig**](OperationStatusGenericOpenAIAPIImageModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateGenericOpenAIAPIImageModelConfig"></a>
# **updateGenericOpenAIAPIImageModelConfig**
> OperationStatusGenericOpenAIAPIImageModelConfig updateGenericOpenAIAPIImageModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GenericOpenAiapiImageModelsConfigurationControllerApi();
let body = new GeboAiClient.GenericOpenAIAPIImageModelConfig(); // GenericOpenAIAPIImageModelConfig | 

apiInstance.updateGenericOpenAIAPIImageModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPIImageModelConfig**](GenericOpenAIAPIImageModelConfig.md)|  | 

### Return type

[**OperationStatusGenericOpenAIAPIImageModelConfig**](OperationStatusGenericOpenAIAPIImageModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

