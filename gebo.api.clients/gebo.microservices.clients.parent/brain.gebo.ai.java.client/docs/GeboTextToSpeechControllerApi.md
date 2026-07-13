# GeboTextToSpeechControllerApi

All URIs are relative to *http://localhost:13001*

Method | HTTP request | Description
------------- | ------------- | -------------
[**isEnabled1**](GeboTextToSpeechControllerApi.md#isEnabled1) | **GET** /api/users/GeboTextToSpeechController/isEnabled | 
[**speechText**](GeboTextToSpeechControllerApi.md#speechText) | **POST** /api/users/GeboTextToSpeechController/speechText | 

<a name="isEnabled1"></a>
# **isEnabled1**
> Object isEnabled1()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboTextToSpeechControllerApi;


GeboTextToSpeechControllerApi apiInstance = new GeboTextToSpeechControllerApi();
try {
    Object result = apiInstance.isEnabled1();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboTextToSpeechControllerApi#isEnabled1");
    e.printStackTrace();
}
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

<a name="speechText"></a>
# **speechText**
> Object speechText(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboTextToSpeechControllerApi;


GeboTextToSpeechControllerApi apiInstance = new GeboTextToSpeechControllerApi();
SpeechRequest body = new SpeechRequest(); // SpeechRequest | 
try {
    Object result = apiInstance.speechText(body);
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

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/octet-stream

