# UploadsBrowsingControllerApi

All URIs are relative to *http://localhost:13007/uploads*

Method | HTTP request | Description
------------- | ------------- | -------------
[**browseUploadsEndpointPath**](UploadsBrowsingControllerApi.md#browseUploadsEndpointPath) | **POST** /api/admin/UploadsBrowsingController/browseUploadsEndpointPath | 
[**getUploadsEndpointNavigationStatus**](UploadsBrowsingControllerApi.md#getUploadsEndpointNavigationStatus) | **POST** /api/admin/UploadsBrowsingController/getUploadsEndpointNavigationStatus | 
[**getUploadsEndpointRoots**](UploadsBrowsingControllerApi.md#getUploadsEndpointRoots) | **GET** /api/admin/UploadsBrowsingController/getUploadsEndpointRoots | 
[**serveUploadsEndpointFile**](UploadsBrowsingControllerApi.md#serveUploadsEndpointFile) | **GET** /api/admin/UploadsBrowsingController/serveUploadsEndpointFile | 

<a name="browseUploadsEndpointPath"></a>
# **browseUploadsEndpointPath**
> OperationStatusListPathInfo browseUploadsEndpointPath(body, endpointCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.uploads.invoker.ApiException;
//import gebo.microservices.api.client.uploads.api.UploadsBrowsingControllerApi;


UploadsBrowsingControllerApi apiInstance = new UploadsBrowsingControllerApi();
BrowseParam body = new BrowseParam(); // BrowseParam | 
Object endpointCode = null; // Object | 
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
 **endpointCode** | [**Object**](.md)|  |

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
//import gebo.microservices.api.client.uploads.invoker.ApiException;
//import gebo.microservices.api.client.uploads.api.UploadsBrowsingControllerApi;


UploadsBrowsingControllerApi apiInstance = new UploadsBrowsingControllerApi();
Object body = null; // Object | 
Object endpointCode = null; // Object | 
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
 **body** | [**Object**](Object.md)|  |
 **endpointCode** | [**Object**](.md)|  |

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
//import gebo.microservices.api.client.uploads.invoker.ApiException;
//import gebo.microservices.api.client.uploads.api.UploadsBrowsingControllerApi;


UploadsBrowsingControllerApi apiInstance = new UploadsBrowsingControllerApi();
Object endpointCode = null; // Object | 
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
 **endpointCode** | [**Object**](.md)|  |

### Return type

[**OperationStatusListGVirtualFilesystemRoot**](OperationStatusListGVirtualFilesystemRoot.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="serveUploadsEndpointFile"></a>
# **serveUploadsEndpointFile**
> Object serveUploadsEndpointFile(endpointCode, path)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.uploads.invoker.ApiException;
//import gebo.microservices.api.client.uploads.api.UploadsBrowsingControllerApi;


UploadsBrowsingControllerApi apiInstance = new UploadsBrowsingControllerApi();
Object endpointCode = null; // Object | 
Object path = null; // Object | 
try {
    Object result = apiInstance.serveUploadsEndpointFile(endpointCode, path);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UploadsBrowsingControllerApi#serveUploadsEndpointFile");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **endpointCode** | [**Object**](.md)|  |
 **path** | [**Object**](.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

