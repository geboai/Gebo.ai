# McpClientBrowsingControllerApi

All URIs are relative to *http://localhost:12999*

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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.McpClientBrowsingControllerApi;


McpClientBrowsingControllerApi apiInstance = new McpClientBrowsingControllerApi();
BrowseParam body = new BrowseParam(); // BrowseParam | 
String mcpClientConfigCode = "mcpClientConfigCode_example"; // String | 
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
 **mcpClientConfigCode** | **String**|  |

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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.McpClientBrowsingControllerApi;


McpClientBrowsingControllerApi apiInstance = new McpClientBrowsingControllerApi();
List<VFilesystemReference> body = Arrays.asList(new VFilesystemReference()); // List<VFilesystemReference> | 
String mcpClientConfigCode = "mcpClientConfigCode_example"; // String | 
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
 **body** | [**List&lt;VFilesystemReference&gt;**](VFilesystemReference.md)|  |
 **mcpClientConfigCode** | **String**|  |

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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.McpClientBrowsingControllerApi;


McpClientBrowsingControllerApi apiInstance = new McpClientBrowsingControllerApi();
String mcpClientConfigCode = "mcpClientConfigCode_example"; // String | 
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
 **mcpClientConfigCode** | **String**|  |

### Return type

[**OperationStatusListGVirtualFilesystemRoot**](OperationStatusListGVirtualFilesystemRoot.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

