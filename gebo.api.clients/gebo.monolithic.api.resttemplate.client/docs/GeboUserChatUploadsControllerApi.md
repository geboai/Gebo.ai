# GeboUserChatUploadsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**chatSessionCreateWithUpload**](GeboUserChatUploadsControllerApi.md#chatSessionCreateWithUpload) | **POST** /api/users/GeboUserChatUploadsController/chatSessionCreateWithUpload/{modelCode} | 
[**chatSessionUpload**](GeboUserChatUploadsControllerApi.md#chatSessionUpload) | **POST** /api/users/GeboUserChatUploadsController/chatSessionUpload/{userSessionCode} | 
[**deleteSessionUploads**](GeboUserChatUploadsControllerApi.md#deleteSessionUploads) | **DELETE** /api/users/GeboUserChatUploadsController/deleteSessionUploads | 
[**ragChatSessionCreateWithUpload**](GeboUserChatUploadsControllerApi.md#ragChatSessionCreateWithUpload) | **POST** /api/users/GeboUserChatUploadsController/ragChatSessionCreateWithUpload/{chatProfileCode} | 
[**serveContent**](GeboUserChatUploadsControllerApi.md#serveContent) | **GET** /api/users/GeboUserChatUploadsController/serveContent/{userSessionCode}/{uploadedContentId} | 

<a name="chatSessionCreateWithUpload"></a>
# **chatSessionCreateWithUpload**
> OperationStatusChatSessionCreationWithUpload chatSessionCreateWithUpload(modelCode, files)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboUserChatUploadsControllerApi;


GeboUserChatUploadsControllerApi apiInstance = new GeboUserChatUploadsControllerApi();
String modelCode = "modelCode_example"; // String | 
List<File> files = Arrays.asList(new File("/path/to/file")); // List<File> | 
try {
    OperationStatusChatSessionCreationWithUpload result = apiInstance.chatSessionCreateWithUpload(modelCode, files);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboUserChatUploadsControllerApi#chatSessionCreateWithUpload");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **modelCode** | **String**|  |
 **files** | [**List&lt;File&gt;**](File.md)|  | [optional]

### Return type

[**OperationStatusChatSessionCreationWithUpload**](OperationStatusChatSessionCreationWithUpload.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: */*

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
 - **Accept**: */*

<a name="deleteSessionUploads"></a>
# **deleteSessionUploads**
> OperationStatusListUserUploadedContent deleteSessionUploads(userSessionCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboUserChatUploadsControllerApi;


GeboUserChatUploadsControllerApi apiInstance = new GeboUserChatUploadsControllerApi();
String userSessionCode = "userSessionCode_example"; // String | 
try {
    OperationStatusListUserUploadedContent result = apiInstance.deleteSessionUploads(userSessionCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboUserChatUploadsControllerApi#deleteSessionUploads");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userSessionCode** | **String**|  |

### Return type

[**OperationStatusListUserUploadedContent**](OperationStatusListUserUploadedContent.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: multipart/form-data

<a name="ragChatSessionCreateWithUpload"></a>
# **ragChatSessionCreateWithUpload**
> OperationStatusChatSessionCreationWithUpload ragChatSessionCreateWithUpload(chatProfileCode, files)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboUserChatUploadsControllerApi;


GeboUserChatUploadsControllerApi apiInstance = new GeboUserChatUploadsControllerApi();
String chatProfileCode = "chatProfileCode_example"; // String | 
List<File> files = Arrays.asList(new File("/path/to/file")); // List<File> | 
try {
    OperationStatusChatSessionCreationWithUpload result = apiInstance.ragChatSessionCreateWithUpload(chatProfileCode, files);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboUserChatUploadsControllerApi#ragChatSessionCreateWithUpload");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **chatProfileCode** | **String**|  |
 **files** | [**List&lt;File&gt;**](File.md)|  | [optional]

### Return type

[**OperationStatusChatSessionCreationWithUpload**](OperationStatusChatSessionCreationWithUpload.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: */*

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

