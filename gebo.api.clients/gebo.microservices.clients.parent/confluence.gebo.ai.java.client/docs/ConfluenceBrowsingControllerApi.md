# ConfluenceBrowsingControllerApi

All URIs are relative to *http://localhost:13010/confluence*

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
//import gebo.microservices.api.client.confluence.invoker.ApiException;
//import gebo.microservices.api.client.confluence.api.ConfluenceBrowsingControllerApi;


ConfluenceBrowsingControllerApi apiInstance = new ConfluenceBrowsingControllerApi();
BrowseParam body = new BrowseParam(); // BrowseParam | 
Object systemCode = null; // Object | 
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
 **systemCode** | [**Object**](.md)|  |

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
//import gebo.microservices.api.client.confluence.invoker.ApiException;
//import gebo.microservices.api.client.confluence.api.ConfluenceBrowsingControllerApi;


ConfluenceBrowsingControllerApi apiInstance = new ConfluenceBrowsingControllerApi();
Object body = null; // Object | 
Object systemCode = null; // Object | 
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
 **body** | [**Object**](Object.md)|  |
 **systemCode** | [**Object**](.md)|  |

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
//import gebo.microservices.api.client.confluence.invoker.ApiException;
//import gebo.microservices.api.client.confluence.api.ConfluenceBrowsingControllerApi;


ConfluenceBrowsingControllerApi apiInstance = new ConfluenceBrowsingControllerApi();
Object systemCode = null; // Object | 
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
 **systemCode** | [**Object**](.md)|  |

### Return type

[**OperationStatusListGVirtualFilesystemRoot**](OperationStatusListGVirtualFilesystemRoot.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

