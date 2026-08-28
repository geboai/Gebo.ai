# GeboAiClient.McpClientBrowsingControllerApi

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
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.McpClientBrowsingControllerApi();
let body = new GeboAiClient.BrowseParam(); // BrowseParam | 
let mcpClientConfigCode = "mcpClientConfigCode_example"; // String | 

apiInstance.browseMCPClientPath(body, mcpClientConfigCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

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
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.McpClientBrowsingControllerApi();
let body = [new GeboAiClient.VFilesystemReference()]; // [VFilesystemReference] | 
let mcpClientConfigCode = "mcpClientConfigCode_example"; // String | 

apiInstance.getMCPClientNavigationStatus(body, mcpClientConfigCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**[VFilesystemReference]**](VFilesystemReference.md)|  | 
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
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.McpClientBrowsingControllerApi();
let mcpClientConfigCode = "mcpClientConfigCode_example"; // String | 

apiInstance.getMCPClientRoots(mcpClientConfigCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

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

