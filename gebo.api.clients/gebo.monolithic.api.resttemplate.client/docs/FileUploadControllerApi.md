# FileUploadControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getHandShakeCode**](FileUploadControllerApi.md#getHandShakeCode) | **GET** /api/admin/FileUploadController/getHandShakeCode | 
[**upload1**](FileUploadControllerApi.md#upload1) | **POST** /api/admin/FileUploadController/upload/{handShakeCode} | 

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

<a name="upload1"></a>
# **upload1**
> upload1(handShakeCode, files)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.FileUploadControllerApi;


FileUploadControllerApi apiInstance = new FileUploadControllerApi();
String handShakeCode = "handShakeCode_example"; // String | 
List<File> files = Arrays.asList(new File("/path/to/file")); // List<File> | 
try {
    apiInstance.upload1(handShakeCode, files);
} catch (ApiException e) {
    System.err.println("Exception when calling FileUploadControllerApi#upload1");
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

