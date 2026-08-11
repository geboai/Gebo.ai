# BrainClient.GenericOpenAiRankerModelsConfigurationControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteGenericOpenAIAPIRankerModelConfig**](GenericOpenAiRankerModelsConfigurationControllerApi.md#deleteGenericOpenAIAPIRankerModelConfig) | **POST** /api/admin/GenerigOpenAIRankerModelsConfigurationController/deleteGenericOpenAIAPIRankerModelConfig | 
[**findGenericOpenAIAPIRankerModelConfigByCode**](GenericOpenAiRankerModelsConfigurationControllerApi.md#findGenericOpenAIAPIRankerModelConfigByCode) | **GET** /api/admin/GenerigOpenAIRankerModelsConfigurationController/findGenericOpenAIAPIRankerModelConfigByCode | 
[**getGenericOpenAIAPIRankerModels**](GenericOpenAiRankerModelsConfigurationControllerApi.md#getGenericOpenAIAPIRankerModels) | **POST** /api/admin/GenerigOpenAIRankerModelsConfigurationController/getGenericOpenAIAPIRankerModels | 
[**getGenericOpenAIRankerModelConfigs**](GenericOpenAiRankerModelsConfigurationControllerApi.md#getGenericOpenAIRankerModelConfigs) | **GET** /api/admin/GenerigOpenAIRankerModelsConfigurationController/getGenericOpenAIRankerModelConfigs | 
[**getGenericOpenAIRankerModelTypes**](GenericOpenAiRankerModelsConfigurationControllerApi.md#getGenericOpenAIRankerModelTypes) | **GET** /api/admin/GenerigOpenAIRankerModelsConfigurationController/getGenericOpenAIRankerModelTypes | 
[**insertGenericOpenAIAPIRankerModelConfig**](GenericOpenAiRankerModelsConfigurationControllerApi.md#insertGenericOpenAIAPIRankerModelConfig) | **POST** /api/admin/GenerigOpenAIRankerModelsConfigurationController/insertGenericOpenAIAPIRankerModelConfig | 
[**updateGenericOpenAIAPIRankerModelConfig**](GenericOpenAiRankerModelsConfigurationControllerApi.md#updateGenericOpenAIAPIRankerModelConfig) | **POST** /api/admin/GenerigOpenAIRankerModelsConfigurationController/updateGenericOpenAIAPIRankerModelConfig | 

<a name="deleteGenericOpenAIAPIRankerModelConfig"></a>
# **deleteGenericOpenAIAPIRankerModelConfig**
> OperationStatusBoolean deleteGenericOpenAIAPIRankerModelConfig(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GenericOpenAiRankerModelsConfigurationControllerApi();
let body = new BrainClient.GenericOpenAIAPIRankerModelConfig(); // GenericOpenAIAPIRankerModelConfig | 

apiInstance.deleteGenericOpenAIAPIRankerModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPIRankerModelConfig**](GenericOpenAIAPIRankerModelConfig.md)|  | 

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findGenericOpenAIAPIRankerModelConfigByCode"></a>
# **findGenericOpenAIAPIRankerModelConfigByCode**
> GenericOpenAIAPIRankerModelConfig findGenericOpenAIAPIRankerModelConfigByCode(code)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GenericOpenAiRankerModelsConfigurationControllerApi();
let code = null; // Object | 

apiInstance.findGenericOpenAIAPIRankerModelConfigByCode(code).then((data) => {
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

[**GenericOpenAIAPIRankerModelConfig**](GenericOpenAIAPIRankerModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getGenericOpenAIAPIRankerModels"></a>
# **getGenericOpenAIAPIRankerModels**
> OperationStatusListGenericOpenAIAPIRankerModelChoice getGenericOpenAIAPIRankerModels(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GenericOpenAiRankerModelsConfigurationControllerApi();
let body = new BrainClient.GenericOpenAIAPIRankerModelConfig(); // GenericOpenAIAPIRankerModelConfig | 

apiInstance.getGenericOpenAIAPIRankerModels(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPIRankerModelConfig**](GenericOpenAIAPIRankerModelConfig.md)|  | 

### Return type

[**OperationStatusListGenericOpenAIAPIRankerModelChoice**](OperationStatusListGenericOpenAIAPIRankerModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getGenericOpenAIRankerModelConfigs"></a>
# **getGenericOpenAIRankerModelConfigs**
> Object getGenericOpenAIRankerModelConfigs()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GenericOpenAiRankerModelsConfigurationControllerApi();
apiInstance.getGenericOpenAIRankerModelConfigs().then((data) => {
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

<a name="getGenericOpenAIRankerModelTypes"></a>
# **getGenericOpenAIRankerModelTypes**
> Object getGenericOpenAIRankerModelTypes()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GenericOpenAiRankerModelsConfigurationControllerApi();
apiInstance.getGenericOpenAIRankerModelTypes().then((data) => {
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

<a name="insertGenericOpenAIAPIRankerModelConfig"></a>
# **insertGenericOpenAIAPIRankerModelConfig**
> OperationStatusGenericOpenAIAPIRankerModelConfig insertGenericOpenAIAPIRankerModelConfig(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GenericOpenAiRankerModelsConfigurationControllerApi();
let body = new BrainClient.GenericOpenAIAPIRankerModelConfig(); // GenericOpenAIAPIRankerModelConfig | 

apiInstance.insertGenericOpenAIAPIRankerModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPIRankerModelConfig**](GenericOpenAIAPIRankerModelConfig.md)|  | 

### Return type

[**OperationStatusGenericOpenAIAPIRankerModelConfig**](OperationStatusGenericOpenAIAPIRankerModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateGenericOpenAIAPIRankerModelConfig"></a>
# **updateGenericOpenAIAPIRankerModelConfig**
> OperationStatusGenericOpenAIAPIRankerModelConfig updateGenericOpenAIAPIRankerModelConfig(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GenericOpenAiRankerModelsConfigurationControllerApi();
let body = new BrainClient.GenericOpenAIAPIRankerModelConfig(); // GenericOpenAIAPIRankerModelConfig | 

apiInstance.updateGenericOpenAIAPIRankerModelConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenericOpenAIAPIRankerModelConfig**](GenericOpenAIAPIRankerModelConfig.md)|  | 

### Return type

[**OperationStatusGenericOpenAIAPIRankerModelConfig**](OperationStatusGenericOpenAIAPIRankerModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

