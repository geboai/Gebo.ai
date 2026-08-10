# WebdavBrowsingControllerApi

All URIs are relative to *http://localhost:13020/webdav*

Method | HTTP request | Description
------------- | ------------- | -------------
[**browseWebdavPath**](WebdavBrowsingControllerApi.md#browseWebdavPath) | **POST** /api/admin/WebdavBrowsingController/browseWebdavPath | 
[**getWebdavNavigationStatus**](WebdavBrowsingControllerApi.md#getWebdavNavigationStatus) | **POST** /api/admin/WebdavBrowsingController/getWebdavNavigationStatus | 
[**getWebdavRoots**](WebdavBrowsingControllerApi.md#getWebdavRoots) | **GET** /api/admin/WebdavBrowsingController/getWebdavRoots | 

<a name="browseWebdavPath"></a>
# **browseWebdavPath**
> OperationStatusListPathInfo browseWebdavPath(body, systemCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.webdav.invoker.ApiException;
//import gebo.microservices.api.client.webdav.api.WebdavBrowsingControllerApi;


WebdavBrowsingControllerApi apiInstance = new WebdavBrowsingControllerApi();
BrowseParam body = new BrowseParam(); // BrowseParam | 
Object systemCode = null; // Object | 
try {
    OperationStatusListPathInfo result = apiInstance.browseWebdavPath(body, systemCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WebdavBrowsingControllerApi#browseWebdavPath");
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

<a name="getWebdavNavigationStatus"></a>
# **getWebdavNavigationStatus**
> OperationStatusListVirtualFilesystemNavigationTreeStatus getWebdavNavigationStatus(body, systemCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.webdav.invoker.ApiException;
//import gebo.microservices.api.client.webdav.api.WebdavBrowsingControllerApi;


WebdavBrowsingControllerApi apiInstance = new WebdavBrowsingControllerApi();
Object body = null; // Object | 
Object systemCode = null; // Object | 
try {
    OperationStatusListVirtualFilesystemNavigationTreeStatus result = apiInstance.getWebdavNavigationStatus(body, systemCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WebdavBrowsingControllerApi#getWebdavNavigationStatus");
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

<a name="getWebdavRoots"></a>
# **getWebdavRoots**
> OperationStatusListGVirtualFilesystemRoot getWebdavRoots(systemCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.webdav.invoker.ApiException;
//import gebo.microservices.api.client.webdav.api.WebdavBrowsingControllerApi;


WebdavBrowsingControllerApi apiInstance = new WebdavBrowsingControllerApi();
Object systemCode = null; // Object | 
try {
    OperationStatusListGVirtualFilesystemRoot result = apiInstance.getWebdavRoots(systemCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WebdavBrowsingControllerApi#getWebdavRoots");
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

