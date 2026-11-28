# ZammadBrowsingControllerApi

All URIs are relative to *http://localhost:13024/zammad*

Method | HTTP request | Description
------------- | ------------- | -------------
[**browseZammadPath**](ZammadBrowsingControllerApi.md#browseZammadPath) | **POST** /api/admin/ZammadBrowsingController/browseZammadPath | 
[**getZammadNavigationStatus**](ZammadBrowsingControllerApi.md#getZammadNavigationStatus) | **POST** /api/admin/ZammadBrowsingController/getZammadNavigationStatus | 
[**getZammadRoots**](ZammadBrowsingControllerApi.md#getZammadRoots) | **GET** /api/admin/ZammadBrowsingController/getZammadRoots | 

<a name="browseZammadPath"></a>
# **browseZammadPath**
> OperationStatusListPathInfo browseZammadPath(body, systemCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.zammad.invoker.ApiException;
//import gebo.microservices.api.client.zammad.api.ZammadBrowsingControllerApi;


ZammadBrowsingControllerApi apiInstance = new ZammadBrowsingControllerApi();
BrowseParam body = new BrowseParam(); // BrowseParam | 
String systemCode = "systemCode_example"; // String | 
try {
    OperationStatusListPathInfo result = apiInstance.browseZammadPath(body, systemCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ZammadBrowsingControllerApi#browseZammadPath");
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

<a name="getZammadNavigationStatus"></a>
# **getZammadNavigationStatus**
> OperationStatusListVirtualFilesystemNavigationTreeStatus getZammadNavigationStatus(body, systemCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.zammad.invoker.ApiException;
//import gebo.microservices.api.client.zammad.api.ZammadBrowsingControllerApi;


ZammadBrowsingControllerApi apiInstance = new ZammadBrowsingControllerApi();
List<VFilesystemReference> body = Arrays.asList(new VFilesystemReference()); // List<VFilesystemReference> | 
String systemCode = "systemCode_example"; // String | 
try {
    OperationStatusListVirtualFilesystemNavigationTreeStatus result = apiInstance.getZammadNavigationStatus(body, systemCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ZammadBrowsingControllerApi#getZammadNavigationStatus");
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

<a name="getZammadRoots"></a>
# **getZammadRoots**
> OperationStatusListGVirtualFilesystemRoot getZammadRoots(systemCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.zammad.invoker.ApiException;
//import gebo.microservices.api.client.zammad.api.ZammadBrowsingControllerApi;


ZammadBrowsingControllerApi apiInstance = new ZammadBrowsingControllerApi();
String systemCode = "systemCode_example"; // String | 
try {
    OperationStatusListGVirtualFilesystemRoot result = apiInstance.getZammadRoots(systemCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ZammadBrowsingControllerApi#getZammadRoots");
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

