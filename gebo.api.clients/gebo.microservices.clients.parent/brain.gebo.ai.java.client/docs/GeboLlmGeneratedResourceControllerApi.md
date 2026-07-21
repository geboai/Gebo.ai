# GeboLlmGeneratedResourceControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**serveLLMGeneratedContent**](GeboLlmGeneratedResourceControllerApi.md#serveLLMGeneratedContent) | **GET** /api/users/GeboLLMGeneratedResourceController/serveLLMGeneratedContent/{userSessionCode}/{generatedResourceCode} | 

<a name="serveLLMGeneratedContent"></a>
# **serveLLMGeneratedContent**
> serveLLMGeneratedContent(userSessionCode, generatedResourceCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboLlmGeneratedResourceControllerApi;


GeboLlmGeneratedResourceControllerApi apiInstance = new GeboLlmGeneratedResourceControllerApi();
Object userSessionCode = null; // Object | 
Object generatedResourceCode = null; // Object | 
try {
    apiInstance.serveLLMGeneratedContent(userSessionCode, generatedResourceCode);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboLlmGeneratedResourceControllerApi#serveLLMGeneratedContent");
    e.printStackTrace();
}
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

