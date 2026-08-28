# GeboAiClient.GeboAdminPromptsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deletePromptConfig**](GeboAdminPromptsControllerApi.md#deletePromptConfig) | **POST** /api/admin/GeboAdminPromptsController/deletePromptConfig | 
[**findPromptConfigByCode**](GeboAdminPromptsControllerApi.md#findPromptConfigByCode) | **GET** /api/admin/GeboAdminPromptsController/findPromptConfigByCode | 
[**getPromptCategories**](GeboAdminPromptsControllerApi.md#getPromptCategories) | **GET** /api/admin/GeboAdminPromptsController/getPromptCategories | 
[**getPromptConfigByFilter**](GeboAdminPromptsControllerApi.md#getPromptConfigByFilter) | **POST** /api/admin/GeboAdminPromptsController/getPromptConfigByFilter | 
[**insertPromptConfig**](GeboAdminPromptsControllerApi.md#insertPromptConfig) | **POST** /api/admin/GeboAdminPromptsController/insertPromptConfig | 
[**updatePromptConfig**](GeboAdminPromptsControllerApi.md#updatePromptConfig) | **POST** /api/admin/GeboAdminPromptsController/updatePromptConfig | 

<a name="deletePromptConfig"></a>
# **deletePromptConfig**
> deletePromptConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboAdminPromptsControllerApi();
let body = new GeboAiClient.GPromptTemplateConfig(); // GPromptTemplateConfig | 

apiInstance.deletePromptConfig(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GPromptTemplateConfig**](GPromptTemplateConfig.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="findPromptConfigByCode"></a>
# **findPromptConfigByCode**
> GPromptTemplateConfig findPromptConfigByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboAdminPromptsControllerApi();
let code = "code_example"; // String | 

apiInstance.findPromptConfigByCode(code).then((data) => {
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

[**GPromptTemplateConfig**](GPromptTemplateConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getPromptCategories"></a>
# **getPromptCategories**
> [&#x27;String&#x27;] getPromptCategories()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboAdminPromptsControllerApi();
apiInstance.getPromptCategories().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

**[&#x27;String&#x27;]**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getPromptConfigByFilter"></a>
# **getPromptConfigByFilter**
> GPromptTemplateConfig getPromptConfigByFilter(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboAdminPromptsControllerApi();
let body = new GeboAiClient.PromptFilter(); // PromptFilter | 

apiInstance.getPromptConfigByFilter(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**PromptFilter**](PromptFilter.md)|  | 

### Return type

[**GPromptTemplateConfig**](GPromptTemplateConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertPromptConfig"></a>
# **insertPromptConfig**
> GPromptTemplateConfig insertPromptConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboAdminPromptsControllerApi();
let body = new GeboAiClient.GPromptTemplateConfig(); // GPromptTemplateConfig | 

apiInstance.insertPromptConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GPromptTemplateConfig**](GPromptTemplateConfig.md)|  | 

### Return type

[**GPromptTemplateConfig**](GPromptTemplateConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updatePromptConfig"></a>
# **updatePromptConfig**
> GPromptTemplateConfig updatePromptConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboAdminPromptsControllerApi();
let body = new GeboAiClient.GPromptTemplateConfig(); // GPromptTemplateConfig | 

apiInstance.updatePromptConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GPromptTemplateConfig**](GPromptTemplateConfig.md)|  | 

### Return type

[**GPromptTemplateConfig**](GPromptTemplateConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

