# GeboLlmGeneratedResourceControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**serveLLMGeneratedContent**](GeboLlmGeneratedResourceControllerApi.md#serveLLMGeneratedContent) | **GET** /api/users/GeboLLMGeneratedResourceController/serveLLMGeneratedContent/{userSessionCode}/{generatedResourceCode} | 

<a name="serveLLMGeneratedContent"></a>
# **serveLLMGeneratedContent**
> serveLLMGeneratedContent(userSessionCode, generatedResourceCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboLlmGeneratedResourceControllerApi;


GeboLlmGeneratedResourceControllerApi apiInstance = new GeboLlmGeneratedResourceControllerApi();
String userSessionCode = "userSessionCode_example"; // String | 
String generatedResourceCode = "generatedResourceCode_example"; // String | 
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
 **userSessionCode** | **String**|  |
 **generatedResourceCode** | **String**|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

