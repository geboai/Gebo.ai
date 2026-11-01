# JiraBrowsingControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**browseJiraPath**](JiraBrowsingControllerApi.md#browseJiraPath) | **POST** /api/admin/JiraBrowsingController/browseJiraPath | 
[**getJiraNavigationStatus**](JiraBrowsingControllerApi.md#getJiraNavigationStatus) | **POST** /api/admin/JiraBrowsingController/getJiraNavigationStatus | 
[**getJiraRoots**](JiraBrowsingControllerApi.md#getJiraRoots) | **GET** /api/admin/JiraBrowsingController/getJiraRoots | 

<a name="browseJiraPath"></a>
# **browseJiraPath**
> OperationStatusListPathInfo browseJiraPath(body, systemCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JiraBrowsingControllerApi;


JiraBrowsingControllerApi apiInstance = new JiraBrowsingControllerApi();
BrowseParam body = new BrowseParam(); // BrowseParam | 
String systemCode = "systemCode_example"; // String | 
try {
    OperationStatusListPathInfo result = apiInstance.browseJiraPath(body, systemCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraBrowsingControllerApi#browseJiraPath");
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

<a name="getJiraNavigationStatus"></a>
# **getJiraNavigationStatus**
> OperationStatusListVirtualFilesystemNavigationTreeStatus getJiraNavigationStatus(body, systemCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JiraBrowsingControllerApi;


JiraBrowsingControllerApi apiInstance = new JiraBrowsingControllerApi();
List<VFilesystemReference> body = Arrays.asList(new VFilesystemReference()); // List<VFilesystemReference> | 
String systemCode = "systemCode_example"; // String | 
try {
    OperationStatusListVirtualFilesystemNavigationTreeStatus result = apiInstance.getJiraNavigationStatus(body, systemCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraBrowsingControllerApi#getJiraNavigationStatus");
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

<a name="getJiraRoots"></a>
# **getJiraRoots**
> OperationStatusListGVirtualFilesystemRoot getJiraRoots(systemCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JiraBrowsingControllerApi;


JiraBrowsingControllerApi apiInstance = new JiraBrowsingControllerApi();
String systemCode = "systemCode_example"; // String | 
try {
    OperationStatusListGVirtualFilesystemRoot result = apiInstance.getJiraRoots(systemCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraBrowsingControllerApi#getJiraRoots");
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

