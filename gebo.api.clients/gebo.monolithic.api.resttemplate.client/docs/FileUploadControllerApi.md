# FileUploadControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getHandShakeCode**](FileUploadControllerApi.md#getHandShakeCode) | **GET** /api/admin/FileUploadController/getHandShakeCode | 
[**uploadToEndpoint**](FileUploadControllerApi.md#uploadToEndpoint) | **POST** /api/admin/FileUploadController/uploadToEndpoint/{endpointCode} | 
[**uploadUploadsEndpoint**](FileUploadControllerApi.md#uploadUploadsEndpoint) | **POST** /api/admin/FileUploadController/upload/{handShakeCode} | 

<a name="getHandShakeCode"></a>
# **getHandShakeCode**
> HandShakeToken getHandShakeCode()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.FileUploadControllerApi;


FileUploadControllerApi apiInstance = new FileUploadControllerApi();
try {
    HandShakeToken result = apiInstance.getHandShakeCode();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FileUploadControllerApi#getHandShakeCode");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**HandShakeToken**](HandShakeToken.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="uploadToEndpoint"></a>
# **uploadToEndpoint**
> uploadToEndpoint(endpointCode, files, folder, relativePaths)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.FileUploadControllerApi;


FileUploadControllerApi apiInstance = new FileUploadControllerApi();
String endpointCode = "endpointCode_example"; // String | 
List<File> files = Arrays.asList(new File("/path/to/file")); // List<File> | 
String folder = "folder_example"; // String | 
List<String> relativePaths = Arrays.asList("relativePaths_example"); // List<String> | 
try {
    apiInstance.uploadToEndpoint(endpointCode, files, folder, relativePaths);
} catch (ApiException e) {
    System.err.println("Exception when calling FileUploadControllerApi#uploadToEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **endpointCode** | **String**|  |
 **files** | [**List&lt;File&gt;**](File.md)|  | [optional]
 **folder** | **String**|  | [optional]
 **relativePaths** | [**List&lt;String&gt;**](String.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: Not defined

<a name="uploadUploadsEndpoint"></a>
# **uploadUploadsEndpoint**
> uploadUploadsEndpoint(handShakeCode, files)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.FileUploadControllerApi;


FileUploadControllerApi apiInstance = new FileUploadControllerApi();
String handShakeCode = "handShakeCode_example"; // String | 
List<File> files = Arrays.asList(new File("/path/to/file")); // List<File> | 
try {
    apiInstance.uploadUploadsEndpoint(handShakeCode, files);
} catch (ApiException e) {
    System.err.println("Exception when calling FileUploadControllerApi#uploadUploadsEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **handShakeCode** | **String**|  |
 **files** | [**List&lt;File&gt;**](File.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: Not defined

