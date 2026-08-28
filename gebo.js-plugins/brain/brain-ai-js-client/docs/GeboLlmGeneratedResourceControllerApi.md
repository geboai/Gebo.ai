# BrainClient.GeboLlmGeneratedResourceControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**serveLLMGeneratedContent**](GeboLlmGeneratedResourceControllerApi.md#serveLLMGeneratedContent) | **GET** /api/users/GeboLLMGeneratedResourceController/serveLLMGeneratedContent/{userSessionCode}/{generatedResourceCode} | 

<a name="serveLLMGeneratedContent"></a>
# **serveLLMGeneratedContent**
> serveLLMGeneratedContent(userSessionCode, generatedResourceCode)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboLlmGeneratedResourceControllerApi();
let userSessionCode = null; // Object | 
let generatedResourceCode = null; // Object | 

apiInstance.serveLLMGeneratedContent(userSessionCode, generatedResourceCode).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userSessionCode** | [**Object**](.md)|  | 
 **generatedResourceCode** | [**Object**](.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

