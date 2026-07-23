# JiraBrowsingControllerApi

All URIs are relative to *http://localhost:13011/jira*

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
//import gebo.microservices.api.client.jira.invoker.ApiException;
//import gebo.microservices.api.client.jira.api.JiraBrowsingControllerApi;


JiraBrowsingControllerApi apiInstance = new JiraBrowsingControllerApi();
BrowseParam body = new BrowseParam(); // BrowseParam | 
Object systemCode = null; // Object | 
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
 **systemCode** | [**Object**](.md)|  |

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
//import gebo.microservices.api.client.jira.invoker.ApiException;
//import gebo.microservices.api.client.jira.api.JiraBrowsingControllerApi;


JiraBrowsingControllerApi apiInstance = new JiraBrowsingControllerApi();
Object body = null; // Object | 
Object systemCode = null; // Object | 
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
 **body** | [**Object**](Object.md)|  |
 **systemCode** | [**Object**](.md)|  |

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
//import gebo.microservices.api.client.jira.invoker.ApiException;
//import gebo.microservices.api.client.jira.api.JiraBrowsingControllerApi;


JiraBrowsingControllerApi apiInstance = new JiraBrowsingControllerApi();
Object systemCode = null; // Object | 
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
 **systemCode** | [**Object**](.md)|  |

### Return type

[**OperationStatusListGVirtualFilesystemRoot**](OperationStatusListGVirtualFilesystemRoot.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

