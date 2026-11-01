# ConfluenceBrowsingControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**browseConfluencePath**](ConfluenceBrowsingControllerApi.md#browseConfluencePath) | **POST** /api/admin/ConfluenceBrowsingController/browseConfluencePath | 
[**getConfluenceNavigationStatus**](ConfluenceBrowsingControllerApi.md#getConfluenceNavigationStatus) | **POST** /api/admin/ConfluenceBrowsingController/getConfluenceNavigationStatus | 
[**getConfluenceRoots**](ConfluenceBrowsingControllerApi.md#getConfluenceRoots) | **GET** /api/admin/ConfluenceBrowsingController/getConfluenceRoots | 

<a name="browseConfluencePath"></a>
# **browseConfluencePath**
> OperationStatusListPathInfo browseConfluencePath(body, systemCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceBrowsingControllerApi;


ConfluenceBrowsingControllerApi apiInstance = new ConfluenceBrowsingControllerApi();
BrowseParam body = new BrowseParam(); // BrowseParam | 
String systemCode = "systemCode_example"; // String | 
try {
    OperationStatusListPathInfo result = apiInstance.browseConfluencePath(body, systemCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceBrowsingControllerApi#browseConfluencePath");
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

<a name="getConfluenceNavigationStatus"></a>
# **getConfluenceNavigationStatus**
> OperationStatusListVirtualFilesystemNavigationTreeStatus getConfluenceNavigationStatus(body, systemCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceBrowsingControllerApi;


ConfluenceBrowsingControllerApi apiInstance = new ConfluenceBrowsingControllerApi();
List<VFilesystemReference> body = Arrays.asList(new VFilesystemReference()); // List<VFilesystemReference> | 
String systemCode = "systemCode_example"; // String | 
try {
    OperationStatusListVirtualFilesystemNavigationTreeStatus result = apiInstance.getConfluenceNavigationStatus(body, systemCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceBrowsingControllerApi#getConfluenceNavigationStatus");
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

<a name="getConfluenceRoots"></a>
# **getConfluenceRoots**
> OperationStatusListGVirtualFilesystemRoot getConfluenceRoots(systemCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceBrowsingControllerApi;


ConfluenceBrowsingControllerApi apiInstance = new ConfluenceBrowsingControllerApi();
String systemCode = "systemCode_example"; // String | 
try {
    OperationStatusListGVirtualFilesystemRoot result = apiInstance.getConfluenceRoots(systemCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceBrowsingControllerApi#getConfluenceRoots");
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

