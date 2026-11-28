# RedmineBrowsingControllerApi

All URIs are relative to *http://localhost:13021/redmine*

Method | HTTP request | Description
------------- | ------------- | -------------
[**browseRedminePath**](RedmineBrowsingControllerApi.md#browseRedminePath) | **POST** /api/admin/RedmineBrowsingController/browseRedminePath | 
[**getRedmineNavigationStatus**](RedmineBrowsingControllerApi.md#getRedmineNavigationStatus) | **POST** /api/admin/RedmineBrowsingController/getRedmineNavigationStatus | 
[**getRedmineRoots**](RedmineBrowsingControllerApi.md#getRedmineRoots) | **GET** /api/admin/RedmineBrowsingController/getRedmineRoots | 

<a name="browseRedminePath"></a>
# **browseRedminePath**
> OperationStatusListPathInfo browseRedminePath(body, systemCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.redmine.invoker.ApiException;
//import gebo.microservices.api.client.redmine.api.RedmineBrowsingControllerApi;


RedmineBrowsingControllerApi apiInstance = new RedmineBrowsingControllerApi();
BrowseParam body = new BrowseParam(); // BrowseParam | 
String systemCode = "systemCode_example"; // String | 
try {
    OperationStatusListPathInfo result = apiInstance.browseRedminePath(body, systemCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RedmineBrowsingControllerApi#browseRedminePath");
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

<a name="getRedmineNavigationStatus"></a>
# **getRedmineNavigationStatus**
> OperationStatusListVirtualFilesystemNavigationTreeStatus getRedmineNavigationStatus(body, systemCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.redmine.invoker.ApiException;
//import gebo.microservices.api.client.redmine.api.RedmineBrowsingControllerApi;


RedmineBrowsingControllerApi apiInstance = new RedmineBrowsingControllerApi();
List<VFilesystemReference> body = Arrays.asList(new VFilesystemReference()); // List<VFilesystemReference> | 
String systemCode = "systemCode_example"; // String | 
try {
    OperationStatusListVirtualFilesystemNavigationTreeStatus result = apiInstance.getRedmineNavigationStatus(body, systemCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RedmineBrowsingControllerApi#getRedmineNavigationStatus");
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

<a name="getRedmineRoots"></a>
# **getRedmineRoots**
> OperationStatusListGVirtualFilesystemRoot getRedmineRoots(systemCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.redmine.invoker.ApiException;
//import gebo.microservices.api.client.redmine.api.RedmineBrowsingControllerApi;


RedmineBrowsingControllerApi apiInstance = new RedmineBrowsingControllerApi();
String systemCode = "systemCode_example"; // String | 
try {
    OperationStatusListGVirtualFilesystemRoot result = apiInstance.getRedmineRoots(systemCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RedmineBrowsingControllerApi#getRedmineRoots");
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

