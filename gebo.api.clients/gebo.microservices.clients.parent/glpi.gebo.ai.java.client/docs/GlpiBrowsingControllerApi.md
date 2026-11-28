# GlpiBrowsingControllerApi

All URIs are relative to *http://localhost:13022/glpi*

Method | HTTP request | Description
------------- | ------------- | -------------
[**browseGlpiPath**](GlpiBrowsingControllerApi.md#browseGlpiPath) | **POST** /api/admin/GlpiBrowsingController/browseGlpiPath | 
[**getGlpiNavigationStatus**](GlpiBrowsingControllerApi.md#getGlpiNavigationStatus) | **POST** /api/admin/GlpiBrowsingController/getGlpiNavigationStatus | 
[**getGlpiRoots**](GlpiBrowsingControllerApi.md#getGlpiRoots) | **GET** /api/admin/GlpiBrowsingController/getGlpiRoots | 

<a name="browseGlpiPath"></a>
# **browseGlpiPath**
> OperationStatusListPathInfo browseGlpiPath(body, systemCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.glpi.invoker.ApiException;
//import gebo.microservices.api.client.glpi.api.GlpiBrowsingControllerApi;


GlpiBrowsingControllerApi apiInstance = new GlpiBrowsingControllerApi();
BrowseParam body = new BrowseParam(); // BrowseParam | 
String systemCode = "systemCode_example"; // String | 
try {
    OperationStatusListPathInfo result = apiInstance.browseGlpiPath(body, systemCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GlpiBrowsingControllerApi#browseGlpiPath");
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

<a name="getGlpiNavigationStatus"></a>
# **getGlpiNavigationStatus**
> OperationStatusListVirtualFilesystemNavigationTreeStatus getGlpiNavigationStatus(body, systemCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.glpi.invoker.ApiException;
//import gebo.microservices.api.client.glpi.api.GlpiBrowsingControllerApi;


GlpiBrowsingControllerApi apiInstance = new GlpiBrowsingControllerApi();
List<VFilesystemReference> body = Arrays.asList(new VFilesystemReference()); // List<VFilesystemReference> | 
String systemCode = "systemCode_example"; // String | 
try {
    OperationStatusListVirtualFilesystemNavigationTreeStatus result = apiInstance.getGlpiNavigationStatus(body, systemCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GlpiBrowsingControllerApi#getGlpiNavigationStatus");
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

<a name="getGlpiRoots"></a>
# **getGlpiRoots**
> OperationStatusListGVirtualFilesystemRoot getGlpiRoots(systemCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.glpi.invoker.ApiException;
//import gebo.microservices.api.client.glpi.api.GlpiBrowsingControllerApi;


GlpiBrowsingControllerApi apiInstance = new GlpiBrowsingControllerApi();
String systemCode = "systemCode_example"; // String | 
try {
    OperationStatusListGVirtualFilesystemRoot result = apiInstance.getGlpiRoots(systemCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GlpiBrowsingControllerApi#getGlpiRoots");
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

