# BrainClient.PromptTemplatesControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getDefaultPrompt**](PromptTemplatesControllerApi.md#getDefaultPrompt) | **GET** /api/admin/PromptTemplatesController/getDefaultPrompt | 
[**getDefaultPromptForChatModel**](PromptTemplatesControllerApi.md#getDefaultPromptForChatModel) | **POST** /api/admin/PromptTemplatesController/getDefaultPromptForChatModel | 
[**getDefaultPromptForChatModelReference**](PromptTemplatesControllerApi.md#getDefaultPromptForChatModelReference) | **POST** /api/admin/PromptTemplatesController/getDefaultPromptForChatModelReference | 

<a name="getDefaultPrompt"></a>
# **getDefaultPrompt**
> GPromptTemplateConfig getDefaultPrompt(ragPrompt)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.PromptTemplatesControllerApi();
let ragPrompt = null; // Object | 

apiInstance.getDefaultPrompt(ragPrompt).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ragPrompt** | [**Object**](.md)|  | 

### Return type

[**GPromptTemplateConfig**](GPromptTemplateConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getDefaultPromptForChatModel"></a>
# **getDefaultPromptForChatModel**
> GPromptTemplateConfig getDefaultPromptForChatModel(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.PromptTemplatesControllerApi();
let body = new BrainClient.DefaultPromptForChatModelParam(); // DefaultPromptForChatModelParam | 

apiInstance.getDefaultPromptForChatModel(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**DefaultPromptForChatModelParam**](DefaultPromptForChatModelParam.md)|  | 

### Return type

[**GPromptTemplateConfig**](GPromptTemplateConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getDefaultPromptForChatModelReference"></a>
# **getDefaultPromptForChatModelReference**
> GPromptTemplateConfig getDefaultPromptForChatModelReference(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.PromptTemplatesControllerApi();
let body = new BrainClient.DefaultPromptForChatModelReferenceParam(); // DefaultPromptForChatModelReferenceParam | 

apiInstance.getDefaultPromptForChatModelReference(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**DefaultPromptForChatModelReferenceParam**](DefaultPromptForChatModelReferenceParam.md)|  | 

### Return type

[**GPromptTemplateConfig**](GPromptTemplateConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

