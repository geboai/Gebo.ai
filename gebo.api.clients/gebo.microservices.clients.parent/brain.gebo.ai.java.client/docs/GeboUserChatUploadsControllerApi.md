# GeboUserChatUploadsControllerApi

All URIs are relative to *http://localhost:13001*

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
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboUserChatUploadsControllerApi;


GeboUserChatUploadsControllerApi apiInstance = new GeboUserChatUploadsControllerApi();
Object userSessionCode = null; // Object | 
Object files = null; // Object | 
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
 **userSessionCode** | [**Object**](.md)|  |
 **files** | [**Object**](.md)|  | [optional]

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
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboUserChatUploadsControllerApi;


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
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboUserChatUploadsControllerApi;


GeboUserChatUploadsControllerApi apiInstance = new GeboUserChatUploadsControllerApi();
Object userSessionCode = null; // Object | 
Object uploadedContentId = null; // Object | 
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
 **userSessionCode** | [**Object**](.md)|  |
 **uploadedContentId** | [**Object**](.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

