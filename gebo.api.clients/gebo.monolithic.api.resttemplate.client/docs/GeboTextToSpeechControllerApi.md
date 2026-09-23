# GeboTextToSpeechControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**isEnabled1**](GeboTextToSpeechControllerApi.md#isEnabled1) | **GET** /api/users/GeboTextToSpeechController/isEnabled | 
[**speechText**](GeboTextToSpeechControllerApi.md#speechText) | **POST** /api/users/GeboTextToSpeechController/speechText | 

<a name="isEnabled1"></a>
# **isEnabled1**
> Boolean isEnabled1()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboTextToSpeechControllerApi;


GeboTextToSpeechControllerApi apiInstance = new GeboTextToSpeechControllerApi();
try {
    Boolean result = apiInstance.isEnabled1();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboTextToSpeechControllerApi#isEnabled1");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**Boolean**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="speechText"></a>
# **speechText**
> File speechText(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboTextToSpeechControllerApi;


GeboTextToSpeechControllerApi apiInstance = new GeboTextToSpeechControllerApi();
SpeechRequest body = new SpeechRequest(); // SpeechRequest | 
try {
    File result = apiInstance.speechText(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboTextToSpeechControllerApi#speechText");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SpeechRequest**](SpeechRequest.md)|  |

### Return type

[**File**](File.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/octet-stream

