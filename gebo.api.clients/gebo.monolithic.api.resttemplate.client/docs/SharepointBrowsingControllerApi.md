# SharepointBrowsingControllerApi

All URIs are relative to *http://localhost:12999*

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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharepointBrowsingControllerApi;


SharepointBrowsingControllerApi apiInstance = new SharepointBrowsingControllerApi();
BrowseParam body = new BrowseParam(); // BrowseParam | 
String systemCode = "systemCode_example"; // String | 
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
 **systemCode** | **String**|  |

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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharepointBrowsingControllerApi;


SharepointBrowsingControllerApi apiInstance = new SharepointBrowsingControllerApi();
List<VFilesystemReference> body = Arrays.asList(new VFilesystemReference()); // List<VFilesystemReference> | 
String systemCode = "systemCode_example"; // String | 
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
 **body** | [**List&lt;VFilesystemReference&gt;**](VFilesystemReference.md)|  |
 **systemCode** | **String**|  |

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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharepointBrowsingControllerApi;


SharepointBrowsingControllerApi apiInstance = new SharepointBrowsingControllerApi();
String systemCode = "systemCode_example"; // String | 
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
 **systemCode** | **String**|  |

### Return type

[**OperationStatusListGVirtualFilesystemRoot**](OperationStatusListGVirtualFilesystemRoot.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

