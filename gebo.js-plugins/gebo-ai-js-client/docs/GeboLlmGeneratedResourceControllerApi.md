# GeboAiClient.GeboLlmGeneratedResourceControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**serveLLMGeneratedContent**](GeboLlmGeneratedResourceControllerApi.md#serveLLMGeneratedContent) | **GET** /api/users/GeboLLMGeneratedResourceController/serveLLMGeneratedContent/{userSessionCode}/{generatedResourceCode} | 

<a name="serveLLMGeneratedContent"></a>
# **serveLLMGeneratedContent**
> serveLLMGeneratedContent(userSessionCode, generatedResourceCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboLlmGeneratedResourceControllerApi();
let userSessionCode = "userSessionCode_example"; // String | 
let generatedResourceCode = "generatedResourceCode_example"; // String | 

apiInstance.serveLLMGeneratedContent(userSessionCode, generatedResourceCode).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userSessionCode** | **String**|  | 
 **generatedResourceCode** | **String**|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

