# GeboAiClient.GoogleVertexChatModelsConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteGoogleVertexChatModelConfig**](GoogleVertexChatModelsConfigurationControllerApi.md#deleteGoogleVertexChatModelConfig) | **POST** /api/admin/GoogleVertexModelsConfigurationController/deleteGoogleVertexChatModelConfig | 
[**findGoogleVertexChatModelConfigByCode**](GoogleVertexChatModelsConfigurationControllerApi.md#findGoogleVertexChatModelConfigByCode) | **GET** /api/admin/GoogleVertexModelsConfigurationController/findGoogleVertexChatModelConfigByCode | 
[**getGoogleVertexChatModels**](GoogleVertexChatModelsConfigurationControllerApi.md#getGoogleVertexChatModels) | **POST** /api/admin/GoogleVertexModelsConfigurationController/getGoogleVertexChatModels | 
[**insertGoogleVertexChatModelConfig**](GoogleVertexChatModelsConfigurationControllerApi.md#insertGoogleVertexChatModelConfig) | **POST** /api/admin/GoogleVertexModelsConfigurationController/insertGoogleVertexChatModelConfig | 
[**updateGoogleVertexChatModelConfig**](GoogleVertexChatModelsConfigurationControllerApi.md#updateGoogleVertexChatModelConfig) | **POST** /api/admin/GoogleVertexModelsConfigurationController/updateGoogleVertexChatModelConfig | 

<a name="deleteGoogleVertexChatModelConfig"></a>
# **deleteGoogleVertexChatModelConfig**
> OperationStatusBoolean deleteGoogleVertexChatModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleVertexChatModelsConfigurationControllerApi();
let body = new GeboAiClient.GGoogleVertexChatModelConfig(); // GGoogleVertexChatModelConfig | 

apiInstance.deleteGoogleVertexChatModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGoogleVertexChatModelConfig**](GGoogleVertexChatModelConfig.md)|  | 

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findGoogleVertexChatModelConfigByCode"></a>
# **findGoogleVertexChatModelConfigByCode**
> GGoogleVertexChatModelConfig findGoogleVertexChatModelConfigByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleVertexChatModelsConfigurationControllerApi();
let code = "code_example"; // String | 

apiInstance.findGoogleVertexChatModelConfigByCode(code).then((data) => {
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

[**GGoogleVertexChatModelConfig**](GGoogleVertexChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getGoogleVertexChatModels"></a>
# **getGoogleVertexChatModels**
> OperationStatusListGGoogleVertexChatModelChoice getGoogleVertexChatModels(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleVertexChatModelsConfigurationControllerApi();
let body = new GeboAiClient.GGoogleVertexChatModelConfig(); // GGoogleVertexChatModelConfig | 

apiInstance.getGoogleVertexChatModels(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGoogleVertexChatModelConfig**](GGoogleVertexChatModelConfig.md)|  | 

### Return type

[**OperationStatusListGGoogleVertexChatModelChoice**](OperationStatusListGGoogleVertexChatModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertGoogleVertexChatModelConfig"></a>
# **insertGoogleVertexChatModelConfig**
> OperationStatusGGoogleVertexChatModelConfig insertGoogleVertexChatModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleVertexChatModelsConfigurationControllerApi();
let body = new GeboAiClient.GGoogleVertexChatModelConfig(); // GGoogleVertexChatModelConfig | 

apiInstance.insertGoogleVertexChatModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGoogleVertexChatModelConfig**](GGoogleVertexChatModelConfig.md)|  | 

### Return type

[**OperationStatusGGoogleVertexChatModelConfig**](OperationStatusGGoogleVertexChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateGoogleVertexChatModelConfig"></a>
# **updateGoogleVertexChatModelConfig**
> OperationStatusGGoogleVertexChatModelConfig updateGoogleVertexChatModelConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleVertexChatModelsConfigurationControllerApi();
let body = new GeboAiClient.GGoogleVertexChatModelConfig(); // GGoogleVertexChatModelConfig | 

apiInstance.updateGoogleVertexChatModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGoogleVertexChatModelConfig**](GGoogleVertexChatModelConfig.md)|  | 

### Return type

[**OperationStatusGGoogleVertexChatModelConfig**](OperationStatusGGoogleVertexChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

