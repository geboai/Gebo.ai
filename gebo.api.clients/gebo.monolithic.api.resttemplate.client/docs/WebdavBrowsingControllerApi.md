# WebdavBrowsingControllerApi

All URIs are relative to *http://localhost:12999*

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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.WebdavBrowsingControllerApi;


WebdavBrowsingControllerApi apiInstance = new WebdavBrowsingControllerApi();
BrowseParam body = new BrowseParam(); // BrowseParam | 
String systemCode = "systemCode_example"; // String | 
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
 **systemCode** | **String**|  |

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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.WebdavBrowsingControllerApi;


WebdavBrowsingControllerApi apiInstance = new WebdavBrowsingControllerApi();
List<VFilesystemReference> body = Arrays.asList(new VFilesystemReference()); // List<VFilesystemReference> | 
String systemCode = "systemCode_example"; // String | 
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
 **body** | [**List&lt;VFilesystemReference&gt;**](VFilesystemReference.md)|  |
 **systemCode** | **String**|  |

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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.WebdavBrowsingControllerApi;


WebdavBrowsingControllerApi apiInstance = new WebdavBrowsingControllerApi();
String systemCode = "systemCode_example"; // String | 
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
 **systemCode** | **String**|  |

### Return type

[**OperationStatusListGVirtualFilesystemRoot**](OperationStatusListGVirtualFilesystemRoot.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

