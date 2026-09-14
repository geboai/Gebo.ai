# FileUploadControllerApi

All URIs are relative to *http://localhost:13007/uploads*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getHandShakeCode**](FileUploadControllerApi.md#getHandShakeCode) | **GET** /api/admin/FileUploadController/getHandShakeCode | 
[**upload**](FileUploadControllerApi.md#upload) | **POST** /api/admin/FileUploadController/upload/{handShakeCode} | 
[**uploadToEndpoint**](FileUploadControllerApi.md#uploadToEndpoint) | **POST** /api/admin/FileUploadController/uploadToEndpoint/{endpointCode} | 

<a name="getHandShakeCode"></a>
# **getHandShakeCode**
> HandShakeToken getHandShakeCode()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.uploads.invoker.ApiException;
//import gebo.microservices.api.client.uploads.api.FileUploadControllerApi;


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

<a name="upload"></a>
# **upload**
> upload(handShakeCode, files)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.uploads.invoker.ApiException;
//import gebo.microservices.api.client.uploads.api.FileUploadControllerApi;


FileUploadControllerApi apiInstance = new FileUploadControllerApi();
String handShakeCode = "handShakeCode_example"; // String | 
List<File> files = Arrays.asList(new File("/path/to/file")); // List<File> | 
try {
    apiInstance.upload(handShakeCode, files);
} catch (ApiException e) {
    System.err.println("Exception when calling FileUploadControllerApi#upload");
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

<a name="uploadToEndpoint"></a>
# **uploadToEndpoint**
> uploadToEndpoint(endpointCode, files)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.uploads.invoker.ApiException;
//import gebo.microservices.api.client.uploads.api.FileUploadControllerApi;


FileUploadControllerApi apiInstance = new FileUploadControllerApi();
String endpointCode = "endpointCode_example"; // String | 
List<File> files = Arrays.asList(new File("/path/to/file")); // List<File> | 
try {
    apiInstance.uploadToEndpoint(endpointCode, files);
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

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: Not defined

