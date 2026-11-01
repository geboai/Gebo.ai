# FileSystemsBrowsingControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**browseSharedFilesystemRootsPath**](FileSystemsBrowsingControllerApi.md#browseSharedFilesystemRootsPath) | **POST** /api/admin/FileSystemsBrowsingController/browseSharedFilesystemRootsPath | 
[**getSharedFilesystemNavigationStatus**](FileSystemsBrowsingControllerApi.md#getSharedFilesystemNavigationStatus) | **POST** /api/admin/FileSystemsBrowsingController/getSharedFilesystemNavigationStatus | 
[**getSharedFilesystemRoots**](FileSystemsBrowsingControllerApi.md#getSharedFilesystemRoots) | **GET** /api/admin/FileSystemsBrowsingController/getSharedFilesystemRoots | 

<a name="browseSharedFilesystemRootsPath"></a>
# **browseSharedFilesystemRootsPath**
> OperationStatusListPathInfo browseSharedFilesystemRootsPath(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.FileSystemsBrowsingControllerApi;


FileSystemsBrowsingControllerApi apiInstance = new FileSystemsBrowsingControllerApi();
BrowseParam body = new BrowseParam(); // BrowseParam | 
try {
    OperationStatusListPathInfo result = apiInstance.browseSharedFilesystemRootsPath(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FileSystemsBrowsingControllerApi#browseSharedFilesystemRootsPath");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**BrowseParam**](BrowseParam.md)|  |

### Return type

[**OperationStatusListPathInfo**](OperationStatusListPathInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getSharedFilesystemNavigationStatus"></a>
# **getSharedFilesystemNavigationStatus**
> OperationStatusListVirtualFilesystemNavigationTreeStatus getSharedFilesystemNavigationStatus(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.FileSystemsBrowsingControllerApi;


FileSystemsBrowsingControllerApi apiInstance = new FileSystemsBrowsingControllerApi();
List<VFilesystemReference> body = Arrays.asList(new VFilesystemReference()); // List<VFilesystemReference> | 
try {
    OperationStatusListVirtualFilesystemNavigationTreeStatus result = apiInstance.getSharedFilesystemNavigationStatus(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FileSystemsBrowsingControllerApi#getSharedFilesystemNavigationStatus");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**List&lt;VFilesystemReference&gt;**](VFilesystemReference.md)|  |

### Return type

[**OperationStatusListVirtualFilesystemNavigationTreeStatus**](OperationStatusListVirtualFilesystemNavigationTreeStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getSharedFilesystemRoots"></a>
# **getSharedFilesystemRoots**
> OperationStatusListGVirtualFilesystemRoot getSharedFilesystemRoots()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.FileSystemsBrowsingControllerApi;


FileSystemsBrowsingControllerApi apiInstance = new FileSystemsBrowsingControllerApi();
try {
    OperationStatusListGVirtualFilesystemRoot result = apiInstance.getSharedFilesystemRoots();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FileSystemsBrowsingControllerApi#getSharedFilesystemRoots");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**OperationStatusListGVirtualFilesystemRoot**](OperationStatusListGVirtualFilesystemRoot.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

