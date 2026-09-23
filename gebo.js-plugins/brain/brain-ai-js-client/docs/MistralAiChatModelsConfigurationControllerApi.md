# BrainClient.MistralAiChatModelsConfigurationControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteMistralAIChatModelConfig**](MistralAiChatModelsConfigurationControllerApi.md#deleteMistralAIChatModelConfig) | **POST** /api/admin/MistralAIChatModelsConfigurationController/deleteMistralAIChatModelConfig | 
[**findMistralAIChatModelConfigByCode**](MistralAiChatModelsConfigurationControllerApi.md#findMistralAIChatModelConfigByCode) | **GET** /api/admin/MistralAIChatModelsConfigurationController/findMistralAIChatModelConfigByCode | 
[**getMistralAIChatModels**](MistralAiChatModelsConfigurationControllerApi.md#getMistralAIChatModels) | **POST** /api/admin/MistralAIChatModelsConfigurationController/getMistralAIChatModels | 
[**insertMistralAIChatModelConfig**](MistralAiChatModelsConfigurationControllerApi.md#insertMistralAIChatModelConfig) | **POST** /api/admin/MistralAIChatModelsConfigurationController/insertMistralAIChatModelConfig | 
[**updateMistralAIChatModelConfig**](MistralAiChatModelsConfigurationControllerApi.md#updateMistralAIChatModelConfig) | **POST** /api/admin/MistralAIChatModelsConfigurationController/updateMistralAIChatModelConfig | 

<a name="deleteMistralAIChatModelConfig"></a>
# **deleteMistralAIChatModelConfig**
> OperationStatusBoolean deleteMistralAIChatModelConfig(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.MistralAiChatModelsConfigurationControllerApi();
let body = new BrainClient.GMistralChatModelConfig(); // GMistralChatModelConfig | 

apiInstance.deleteMistralAIChatModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GMistralChatModelConfig**](GMistralChatModelConfig.md)|  | 

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findMistralAIChatModelConfigByCode"></a>
# **findMistralAIChatModelConfigByCode**
> GMistralChatModelConfig findMistralAIChatModelConfigByCode(code)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.MistralAiChatModelsConfigurationControllerApi();
let code = "code_example"; // String | 

apiInstance.findMistralAIChatModelConfigByCode(code).then((data) => {
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

[**GMistralChatModelConfig**](GMistralChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getMistralAIChatModels"></a>
# **getMistralAIChatModels**
> OperationStatusListGMistralChatModelChoice getMistralAIChatModels(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.MistralAiChatModelsConfigurationControllerApi();
let body = new BrainClient.GMistralChatModelConfig(); // GMistralChatModelConfig | 

apiInstance.getMistralAIChatModels(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GMistralChatModelConfig**](GMistralChatModelConfig.md)|  | 

### Return type

[**OperationStatusListGMistralChatModelChoice**](OperationStatusListGMistralChatModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertMistralAIChatModelConfig"></a>
# **insertMistralAIChatModelConfig**
> OperationStatusGMistralChatModelConfig insertMistralAIChatModelConfig(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.MistralAiChatModelsConfigurationControllerApi();
let body = new BrainClient.GMistralChatModelConfig(); // GMistralChatModelConfig | 

apiInstance.insertMistralAIChatModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GMistralChatModelConfig**](GMistralChatModelConfig.md)|  | 

### Return type

[**OperationStatusGMistralChatModelConfig**](OperationStatusGMistralChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateMistralAIChatModelConfig"></a>
# **updateMistralAIChatModelConfig**
> OperationStatusGMistralChatModelConfig updateMistralAIChatModelConfig(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.MistralAiChatModelsConfigurationControllerApi();
let body = new BrainClient.GMistralChatModelConfig(); // GMistralChatModelConfig | 

apiInstance.updateMistralAIChatModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GMistralChatModelConfig**](GMistralChatModelConfig.md)|  | 

### Return type

[**OperationStatusGMistralChatModelConfig**](OperationStatusGMistralChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

