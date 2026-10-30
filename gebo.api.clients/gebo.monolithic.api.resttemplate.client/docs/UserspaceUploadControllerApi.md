# UserspaceUploadControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**uploadUserspace**](UserspaceUploadControllerApi.md#uploadUserspace) | **POST** /api/user/UserspaceUploadController/upload/{userspaceFolderCode} | 

<a name="uploadUserspace"></a>
# **uploadUserspace**
> uploadUserspace(userspaceFolderCode, files)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UserspaceUploadControllerApi;


UserspaceUploadControllerApi apiInstance = new UserspaceUploadControllerApi();
String userspaceFolderCode = "userspaceFolderCode_example"; // String | 
List<File> files = Arrays.asList(new File("/path/to/file")); // List<File> | 
try {
    apiInstance.uploadUserspace(userspaceFolderCode, files);
} catch (ApiException e) {
    System.err.println("Exception when calling UserspaceUploadControllerApi#uploadUserspace");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userspaceFolderCode** | **String**|  |
 **files** | [**List&lt;File&gt;**](File.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: Not defined

