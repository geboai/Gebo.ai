# OpenProjectBrowsingControllerApi

All URIs are relative to *http://localhost:13023/openproject*

Method | HTTP request | Description
------------- | ------------- | -------------
[**browseOpenProjectPath**](OpenProjectBrowsingControllerApi.md#browseOpenProjectPath) | **POST** /api/admin/OpenProjectBrowsingController/browseOpenProjectPath | 
[**getOpenProjectNavigationStatus**](OpenProjectBrowsingControllerApi.md#getOpenProjectNavigationStatus) | **POST** /api/admin/OpenProjectBrowsingController/getOpenProjectNavigationStatus | 
[**getOpenProjectRoots**](OpenProjectBrowsingControllerApi.md#getOpenProjectRoots) | **GET** /api/admin/OpenProjectBrowsingController/getOpenProjectRoots | 

<a name="browseOpenProjectPath"></a>
# **browseOpenProjectPath**
> OperationStatusListPathInfo browseOpenProjectPath(body, systemCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.openproject.invoker.ApiException;
//import gebo.microservices.api.client.openproject.api.OpenProjectBrowsingControllerApi;


OpenProjectBrowsingControllerApi apiInstance = new OpenProjectBrowsingControllerApi();
BrowseParam body = new BrowseParam(); // BrowseParam | 
String systemCode = "systemCode_example"; // String | 
try {
    OperationStatusListPathInfo result = apiInstance.browseOpenProjectPath(body, systemCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenProjectBrowsingControllerApi#browseOpenProjectPath");
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

<a name="getOpenProjectNavigationStatus"></a>
# **getOpenProjectNavigationStatus**
> OperationStatusListVirtualFilesystemNavigationTreeStatus getOpenProjectNavigationStatus(body, systemCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.openproject.invoker.ApiException;
//import gebo.microservices.api.client.openproject.api.OpenProjectBrowsingControllerApi;


OpenProjectBrowsingControllerApi apiInstance = new OpenProjectBrowsingControllerApi();
List<VFilesystemReference> body = Arrays.asList(new VFilesystemReference()); // List<VFilesystemReference> | 
String systemCode = "systemCode_example"; // String | 
try {
    OperationStatusListVirtualFilesystemNavigationTreeStatus result = apiInstance.getOpenProjectNavigationStatus(body, systemCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenProjectBrowsingControllerApi#getOpenProjectNavigationStatus");
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

<a name="getOpenProjectRoots"></a>
# **getOpenProjectRoots**
> OperationStatusListGVirtualFilesystemRoot getOpenProjectRoots(systemCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.openproject.invoker.ApiException;
//import gebo.microservices.api.client.openproject.api.OpenProjectBrowsingControllerApi;


OpenProjectBrowsingControllerApi apiInstance = new OpenProjectBrowsingControllerApi();
String systemCode = "systemCode_example"; // String | 
try {
    OperationStatusListGVirtualFilesystemRoot result = apiInstance.getOpenProjectRoots(systemCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenProjectBrowsingControllerApi#getOpenProjectRoots");
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

