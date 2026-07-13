# McpClientBrowsingControllerApi

All URIs are relative to *http://localhost:13014*

Method | HTTP request | Description
------------- | ------------- | -------------
[**browseMCPClientPath**](McpClientBrowsingControllerApi.md#browseMCPClientPath) | **POST** /api/admin/MCPClientBrowsingController/browseMCPClientPath | 
[**getMCPClientNavigationStatus**](McpClientBrowsingControllerApi.md#getMCPClientNavigationStatus) | **POST** /api/admin/MCPClientBrowsingController/getMCPClientNavigationStatus | 
[**getMCPClientRoots**](McpClientBrowsingControllerApi.md#getMCPClientRoots) | **GET** /api/admin/MCPClientBrowsingController/getMCPClientRoots | 

<a name="browseMCPClientPath"></a>
# **browseMCPClientPath**
> OperationStatusListPathInfo browseMCPClientPath(body, mcpClientConfigCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.mcpclient.invoker.ApiException;
//import gebo.microservices.api.client.mcpclient.api.McpClientBrowsingControllerApi;


McpClientBrowsingControllerApi apiInstance = new McpClientBrowsingControllerApi();
BrowseParam body = new BrowseParam(); // BrowseParam | 
Object mcpClientConfigCode = null; // Object | 
try {
    OperationStatusListPathInfo result = apiInstance.browseMCPClientPath(body, mcpClientConfigCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling McpClientBrowsingControllerApi#browseMCPClientPath");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**BrowseParam**](BrowseParam.md)|  |
 **mcpClientConfigCode** | [**Object**](.md)|  |

### Return type

[**OperationStatusListPathInfo**](OperationStatusListPathInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMCPClientNavigationStatus"></a>
# **getMCPClientNavigationStatus**
> OperationStatusListVirtualFilesystemNavigationTreeStatus getMCPClientNavigationStatus(body, mcpClientConfigCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.mcpclient.invoker.ApiException;
//import gebo.microservices.api.client.mcpclient.api.McpClientBrowsingControllerApi;


McpClientBrowsingControllerApi apiInstance = new McpClientBrowsingControllerApi();
Object body = null; // Object | 
Object mcpClientConfigCode = null; // Object | 
try {
    OperationStatusListVirtualFilesystemNavigationTreeStatus result = apiInstance.getMCPClientNavigationStatus(body, mcpClientConfigCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling McpClientBrowsingControllerApi#getMCPClientNavigationStatus");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Object**](Object.md)|  |
 **mcpClientConfigCode** | [**Object**](.md)|  |

### Return type

[**OperationStatusListVirtualFilesystemNavigationTreeStatus**](OperationStatusListVirtualFilesystemNavigationTreeStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMCPClientRoots"></a>
# **getMCPClientRoots**
> OperationStatusListGVirtualFilesystemRoot getMCPClientRoots(mcpClientConfigCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.mcpclient.invoker.ApiException;
//import gebo.microservices.api.client.mcpclient.api.McpClientBrowsingControllerApi;


McpClientBrowsingControllerApi apiInstance = new McpClientBrowsingControllerApi();
Object mcpClientConfigCode = null; // Object | 
try {
    OperationStatusListGVirtualFilesystemRoot result = apiInstance.getMCPClientRoots(mcpClientConfigCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling McpClientBrowsingControllerApi#getMCPClientRoots");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **mcpClientConfigCode** | [**Object**](.md)|  |

### Return type

[**OperationStatusListGVirtualFilesystemRoot**](OperationStatusListGVirtualFilesystemRoot.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

