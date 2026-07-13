# SharepointBrowsingControllerApi

All URIs are relative to *http://localhost:13009*

Method | HTTP request | Description
------------- | ------------- | -------------
[**browseSharepointPath**](SharepointBrowsingControllerApi.md#browseSharepointPath) | **POST** /api/admin/SharepointBrowsingController/browseSharepointPath | 
[**getSharepointNavigationStatus**](SharepointBrowsingControllerApi.md#getSharepointNavigationStatus) | **POST** /api/admin/SharepointBrowsingController/getSharepointNavigationStatus | 
[**getSharepointRoots**](SharepointBrowsingControllerApi.md#getSharepointRoots) | **GET** /api/admin/SharepointBrowsingController/getSharepointRoots | 

<a name="browseSharepointPath"></a>
# **browseSharepointPath**
> OperationStatusListPathInfo browseSharepointPath(body, systemCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.sharepoint.invoker.ApiException;
//import gebo.microservices.api.client.sharepoint.api.SharepointBrowsingControllerApi;


SharepointBrowsingControllerApi apiInstance = new SharepointBrowsingControllerApi();
BrowseParam body = new BrowseParam(); // BrowseParam | 
Object systemCode = null; // Object | 
try {
    OperationStatusListPathInfo result = apiInstance.browseSharepointPath(body, systemCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharepointBrowsingControllerApi#browseSharepointPath");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**BrowseParam**](BrowseParam.md)|  |
 **systemCode** | [**Object**](.md)|  |

### Return type

[**OperationStatusListPathInfo**](OperationStatusListPathInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getSharepointNavigationStatus"></a>
# **getSharepointNavigationStatus**
> OperationStatusListVirtualFilesystemNavigationTreeStatus getSharepointNavigationStatus(body, systemCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.sharepoint.invoker.ApiException;
//import gebo.microservices.api.client.sharepoint.api.SharepointBrowsingControllerApi;


SharepointBrowsingControllerApi apiInstance = new SharepointBrowsingControllerApi();
Object body = null; // Object | 
Object systemCode = null; // Object | 
try {
    OperationStatusListVirtualFilesystemNavigationTreeStatus result = apiInstance.getSharepointNavigationStatus(body, systemCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharepointBrowsingControllerApi#getSharepointNavigationStatus");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Object**](Object.md)|  |
 **systemCode** | [**Object**](.md)|  |

### Return type

[**OperationStatusListVirtualFilesystemNavigationTreeStatus**](OperationStatusListVirtualFilesystemNavigationTreeStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getSharepointRoots"></a>
# **getSharepointRoots**
> OperationStatusListGVirtualFilesystemRoot getSharepointRoots(systemCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.sharepoint.invoker.ApiException;
//import gebo.microservices.api.client.sharepoint.api.SharepointBrowsingControllerApi;


SharepointBrowsingControllerApi apiInstance = new SharepointBrowsingControllerApi();
Object systemCode = null; // Object | 
try {
    OperationStatusListGVirtualFilesystemRoot result = apiInstance.getSharepointRoots(systemCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharepointBrowsingControllerApi#getSharepointRoots");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **systemCode** | [**Object**](.md)|  |

### Return type

[**OperationStatusListGVirtualFilesystemRoot**](OperationStatusListGVirtualFilesystemRoot.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

