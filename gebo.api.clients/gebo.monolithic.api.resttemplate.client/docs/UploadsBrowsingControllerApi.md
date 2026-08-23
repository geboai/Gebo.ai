# UploadsBrowsingControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**browseUploadsEndpointPath**](UploadsBrowsingControllerApi.md#browseUploadsEndpointPath) | **POST** /api/admin/UploadsBrowsingController/browseUploadsEndpointPath | 
[**getUploadsEndpointNavigationStatus**](UploadsBrowsingControllerApi.md#getUploadsEndpointNavigationStatus) | **POST** /api/admin/UploadsBrowsingController/getUploadsEndpointNavigationStatus | 
[**getUploadsEndpointRoots**](UploadsBrowsingControllerApi.md#getUploadsEndpointRoots) | **GET** /api/admin/UploadsBrowsingController/getUploadsEndpointRoots | 

<a name="browseUploadsEndpointPath"></a>
# **browseUploadsEndpointPath**
> OperationStatusListPathInfo browseUploadsEndpointPath(body, endpointCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UploadsBrowsingControllerApi;


UploadsBrowsingControllerApi apiInstance = new UploadsBrowsingControllerApi();
BrowseParam body = new BrowseParam(); // BrowseParam | 
String endpointCode = "endpointCode_example"; // String | 
try {
    OperationStatusListPathInfo result = apiInstance.browseUploadsEndpointPath(body, endpointCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UploadsBrowsingControllerApi#browseUploadsEndpointPath");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**BrowseParam**](BrowseParam.md)|  |
 **endpointCode** | **String**|  |

### Return type

[**OperationStatusListPathInfo**](OperationStatusListPathInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getUploadsEndpointNavigationStatus"></a>
# **getUploadsEndpointNavigationStatus**
> OperationStatusListVirtualFilesystemNavigationTreeStatus getUploadsEndpointNavigationStatus(body, endpointCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UploadsBrowsingControllerApi;


UploadsBrowsingControllerApi apiInstance = new UploadsBrowsingControllerApi();
List<VFilesystemReference> body = Arrays.asList(new VFilesystemReference()); // List<VFilesystemReference> | 
String endpointCode = "endpointCode_example"; // String | 
try {
    OperationStatusListVirtualFilesystemNavigationTreeStatus result = apiInstance.getUploadsEndpointNavigationStatus(body, endpointCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UploadsBrowsingControllerApi#getUploadsEndpointNavigationStatus");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**List&lt;VFilesystemReference&gt;**](VFilesystemReference.md)|  |
 **endpointCode** | **String**|  |

### Return type

[**OperationStatusListVirtualFilesystemNavigationTreeStatus**](OperationStatusListVirtualFilesystemNavigationTreeStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getUploadsEndpointRoots"></a>
# **getUploadsEndpointRoots**
> OperationStatusListGVirtualFilesystemRoot getUploadsEndpointRoots(endpointCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UploadsBrowsingControllerApi;


UploadsBrowsingControllerApi apiInstance = new UploadsBrowsingControllerApi();
String endpointCode = "endpointCode_example"; // String | 
try {
    OperationStatusListGVirtualFilesystemRoot result = apiInstance.getUploadsEndpointRoots(endpointCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UploadsBrowsingControllerApi#getUploadsEndpointRoots");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **endpointCode** | **String**|  |

### Return type

[**OperationStatusListGVirtualFilesystemRoot**](OperationStatusListGVirtualFilesystemRoot.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

