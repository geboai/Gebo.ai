# BrainClient.GenericOpenAiapiChatModelsConfigurationControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteGenericOpenAIAPIChatModelConfig**](GenericOpenAiapiChatModelsConfigurationControllerApi.md#deleteGenericOpenAIAPIChatModelConfig) | **POST** /api/admin/GenericOpenAIAPIChatModelsConfigurationController/deleteGenericOpenAIAPIChatModelConfig | 
[**findGenericOpenAIAPIChatModelConfigByCode**](GenericOpenAiapiChatModelsConfigurationControllerApi.md#findGenericOpenAIAPIChatModelConfigByCode) | **GET** /api/admin/GenericOpenAIAPIChatModelsConfigurationController/findGenericOpenAIAPIChatModelConfigByCode | 
[**getGenericOpenAIAPIChatModels**](GenericOpenAiapiChatModelsConfigurationControllerApi.md#getGenericOpenAIAPIChatModels) | **POST** /api/admin/GenericOpenAIAPIChatModelsConfigurationController/getGenericOpenAIAPIChatModels | 
[**getGenericOpenAIChatModelTypes**](GenericOpenAiapiChatModelsConfigurationControllerApi.md#getGenericOpenAIChatModelTypes) | **GET** /api/admin/GenericOpenAIAPIChatModelsConfigurationController/getGenericOpenAIChatModelTypes | 
[**insertGenericOpenAIAPIChatModelConfig**](GenericOpenAiapiChatModelsConfigurationControllerApi.md#insertGenericOpenAIAPIChatModelConfig) | **POST** /api/admin/GenericOpenAIAPIChatModelsConfigurationController/insertGenericOpenAIAPIChatModelConfig | 
[**updateGenericOpenAIAPIChatModelConfig**](GenericOpenAiapiChatModelsConfigurationControllerApi.md#updateGenericOpenAIAPIChatModelConfig) | **POST** /api/admin/GenericOpenAIAPIChatModelsConfigurationController/updateGenericOpenAIAPIChatModelConfig | 

<a name="deleteGenericOpenAIAPIChatModelConfig"></a>
# **deleteGenericOpenAIAPIChatModelConfig**
> OperationStatusBoolean deleteGenericOpenAIAPIChatModelConfig(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GenericOpenAiapiChatModelsConfigurationControllerApi();
let body = new BrainClient.GenericOpenAIAPIChatModelConfig(); // GenericOpenAIAPIChatModelConfig | 

apiInstance.deleteGenericOpenAIAPIChatModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPIChatModelConfig**](GenericOpenAIAPIChatModelConfig.md)|  | 

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findGenericOpenAIAPIChatModelConfigByCode"></a>
# **findGenericOpenAIAPIChatModelConfigByCode**
> GenericOpenAIAPIChatModelConfig findGenericOpenAIAPIChatModelConfigByCode(code)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GenericOpenAiapiChatModelsConfigurationControllerApi();
let code = null; // Object | 

apiInstance.findGenericOpenAIAPIChatModelConfigByCode(code).then((data) => {
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

[**GenericOpenAIAPIChatModelConfig**](GenericOpenAIAPIChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getGenericOpenAIAPIChatModels"></a>
# **getGenericOpenAIAPIChatModels**
> OperationStatusListGenericOpenAIAPIChatModelChoice getGenericOpenAIAPIChatModels(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GenericOpenAiapiChatModelsConfigurationControllerApi();
let body = new BrainClient.GenericOpenAIAPIChatModelConfig(); // GenericOpenAIAPIChatModelConfig | 

apiInstance.getGenericOpenAIAPIChatModels(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPIChatModelConfig**](GenericOpenAIAPIChatModelConfig.md)|  | 

### Return type

[**OperationStatusListGenericOpenAIAPIChatModelChoice**](OperationStatusListGenericOpenAIAPIChatModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getGenericOpenAIChatModelTypes"></a>
# **getGenericOpenAIChatModelTypes**
> Object getGenericOpenAIChatModelTypes()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GenericOpenAiapiChatModelsConfigurationControllerApi();
apiInstance.getGenericOpenAIChatModelTypes().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="insertGenericOpenAIAPIChatModelConfig"></a>
# **insertGenericOpenAIAPIChatModelConfig**
> OperationStatusGenericOpenAIAPIChatModelConfig insertGenericOpenAIAPIChatModelConfig(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GenericOpenAiapiChatModelsConfigurationControllerApi();
let body = new BrainClient.GenericOpenAIAPIChatModelConfig(); // GenericOpenAIAPIChatModelConfig | 

apiInstance.insertGenericOpenAIAPIChatModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPIChatModelConfig**](GenericOpenAIAPIChatModelConfig.md)|  | 

### Return type

[**OperationStatusGenericOpenAIAPIChatModelConfig**](OperationStatusGenericOpenAIAPIChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateGenericOpenAIAPIChatModelConfig"></a>
# **updateGenericOpenAIAPIChatModelConfig**
> OperationStatusGenericOpenAIAPIChatModelConfig updateGenericOpenAIAPIChatModelConfig(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GenericOpenAiapiChatModelsConfigurationControllerApi();
let body = new BrainClient.GenericOpenAIAPIChatModelConfig(); // GenericOpenAIAPIChatModelConfig | 

apiInstance.updateGenericOpenAIAPIChatModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPIChatModelConfig**](GenericOpenAIAPIChatModelConfig.md)|  | 

### Return type

[**OperationStatusGenericOpenAIAPIChatModelConfig**](OperationStatusGenericOpenAIAPIChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

