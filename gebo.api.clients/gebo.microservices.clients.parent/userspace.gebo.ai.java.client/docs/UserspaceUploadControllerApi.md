# UserspaceUploadControllerApi

All URIs are relative to *http://localhost:13008*

Method | HTTP request | Description
------------- | ------------- | -------------
[**upload**](UserspaceUploadControllerApi.md#upload) | **POST** /api/user/UserspaceUploadController/upload/{userspaceFolderCode} | 

<a name="upload"></a>
# **upload**
> upload(userspaceFolderCode, files)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.userspace.invoker.ApiException;
//import gebo.microservices.api.client.userspace.api.UserspaceUploadControllerApi;


UserspaceUploadControllerApi apiInstance = new UserspaceUploadControllerApi();
Object userspaceFolderCode = null; // Object | 
Object files = null; // Object | 
try {
    apiInstance.upload(userspaceFolderCode, files);
} catch (ApiException e) {
    System.err.println("Exception when calling UserspaceUploadControllerApi#upload");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userspaceFolderCode** | [**Object**](.md)|  |
 **files** | [**Object**](.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: Not defined

