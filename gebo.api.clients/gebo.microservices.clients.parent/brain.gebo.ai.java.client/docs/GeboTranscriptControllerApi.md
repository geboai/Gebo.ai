# GeboTranscriptControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**isEnabled**](GeboTranscriptControllerApi.md#isEnabled) | **GET** /api/users/GeboTranscriptController/isEnabled | 
[**transcriptText**](GeboTranscriptControllerApi.md#transcriptText) | **POST** /api/users/GeboTranscriptController/transcriptText | 

<a name="isEnabled"></a>
# **isEnabled**
> Object isEnabled()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboTranscriptControllerApi;


GeboTranscriptControllerApi apiInstance = new GeboTranscriptControllerApi();
try {
    Object result = apiInstance.isEnabled();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboTranscriptControllerApi#isEnabled");
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

<a name="transcriptText"></a>
# **transcriptText**
> TranscriptResponse transcriptText()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboTranscriptControllerApi;


GeboTranscriptControllerApi apiInstance = new GeboTranscriptControllerApi();
try {
    TranscriptResponse result = apiInstance.transcriptText();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboTranscriptControllerApi#transcriptText");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**TranscriptResponse**](TranscriptResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

