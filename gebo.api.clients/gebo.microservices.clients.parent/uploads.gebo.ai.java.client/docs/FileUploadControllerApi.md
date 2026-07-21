# FileUploadControllerApi

All URIs are relative to *http://localhost:13007/uploads*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getHandShakeCode**](FileUploadControllerApi.md#getHandShakeCode) | **GET** /api/admin/FileUploadController/getHandShakeCode | 
[**upload**](FileUploadControllerApi.md#upload) | **POST** /api/admin/FileUploadController/upload/{handShakeCode} | 

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
Object handShakeCode = null; // Object | 
Object files = null; // Object | 
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
 **handShakeCode** | [**Object**](.md)|  |
 **files** | [**Object**](.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: Not defined

