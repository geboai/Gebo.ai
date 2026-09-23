# GeboUserChatUploadsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**chatSessionUpload**](GeboUserChatUploadsControllerApi.md#chatSessionUpload) | **POST** /api/users/GeboUserChatUploadsController/chatSessionUpload/{userSessionCode} | 
[**deleteSessionUploads**](GeboUserChatUploadsControllerApi.md#deleteSessionUploads) | **DELETE** /api/users/GeboUserChatUploadsController/deleteSessionUploads | 
[**serveContent**](GeboUserChatUploadsControllerApi.md#serveContent) | **GET** /api/users/GeboUserChatUploadsController/serveContent/{userSessionCode}/{uploadedContentId} | 

<a name="chatSessionUpload"></a>
# **chatSessionUpload**
> OperationStatusListUserUploadedContent chatSessionUpload(userSessionCode, files)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboUserChatUploadsControllerApi;


GeboUserChatUploadsControllerApi apiInstance = new GeboUserChatUploadsControllerApi();
String userSessionCode = "userSessionCode_example"; // String | 
List<File> files = Arrays.asList(new File("/path/to/file")); // List<File> | 
try {
    OperationStatusListUserUploadedContent result = apiInstance.chatSessionUpload(userSessionCode, files);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboUserChatUploadsControllerApi#chatSessionUpload");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userSessionCode** | **String**|  |
 **files** | [**List&lt;File&gt;**](File.md)|  | [optional]

### Return type

[**OperationStatusListUserUploadedContent**](OperationStatusListUserUploadedContent.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: application/json

<a name="deleteSessionUploads"></a>
# **deleteSessionUploads**
> OperationStatusListUserUploadedContent deleteSessionUploads()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboUserChatUploadsControllerApi;


GeboUserChatUploadsControllerApi apiInstance = new GeboUserChatUploadsControllerApi();
try {
    OperationStatusListUserUploadedContent result = apiInstance.deleteSessionUploads();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboUserChatUploadsControllerApi#deleteSessionUploads");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**OperationStatusListUserUploadedContent**](OperationStatusListUserUploadedContent.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: application/json

<a name="serveContent"></a>
# **serveContent**
> serveContent(userSessionCode, uploadedContentId)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboUserChatUploadsControllerApi;


GeboUserChatUploadsControllerApi apiInstance = new GeboUserChatUploadsControllerApi();
String userSessionCode = "userSessionCode_example"; // String | 
String uploadedContentId = "uploadedContentId_example"; // String | 
try {
    apiInstance.serveContent(userSessionCode, uploadedContentId);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboUserChatUploadsControllerApi#serveContent");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userSessionCode** | **String**|  |
 **uploadedContentId** | **String**|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

