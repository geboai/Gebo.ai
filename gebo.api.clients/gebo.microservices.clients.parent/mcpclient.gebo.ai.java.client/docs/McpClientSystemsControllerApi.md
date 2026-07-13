# McpClientSystemsControllerApi

All URIs are relative to *http://localhost:13014*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteMCPClientEndpoint**](McpClientSystemsControllerApi.md#deleteMCPClientEndpoint) | **POST** /api/admin/MCPClientSystemsController/deleteMCPClientEndpoint | 
[**findMCPClientEndpointsByCode**](McpClientSystemsControllerApi.md#findMCPClientEndpointsByCode) | **GET** /api/admin/MCPClientSystemsController/findMCPClientEndpointsByCode | 
[**findMCPClientEndpointsByProject**](McpClientSystemsControllerApi.md#findMCPClientEndpointsByProject) | **GET** /api/admin/MCPClientSystemsController/findMCPClientEndpointsByProject | 
[**findMCPClientEndpointsByQbe**](McpClientSystemsControllerApi.md#findMCPClientEndpointsByQbe) | **POST** /api/admin/MCPClientSystemsController/findMCPClientEndpointsByQbe | 
[**getMCPClientSystemType**](McpClientSystemsControllerApi.md#getMCPClientSystemType) | **GET** /api/admin/MCPClientSystemsController/getMCPClientSystemType | 
[**insertMCPClientEndpoint**](McpClientSystemsControllerApi.md#insertMCPClientEndpoint) | **POST** /api/admin/MCPClientSystemsController/insertMCPClientEndpoint | 
[**publishMCPClientEndpoint**](McpClientSystemsControllerApi.md#publishMCPClientEndpoint) | **POST** /api/admin/MCPClientSystemsController/publishMCPClientEndpoint | 
[**updateMCPClientEndpoint**](McpClientSystemsControllerApi.md#updateMCPClientEndpoint) | **POST** /api/admin/MCPClientSystemsController/updateMCPClientEndpoint | 

<a name="deleteMCPClientEndpoint"></a>
# **deleteMCPClientEndpoint**
> deleteMCPClientEndpoint(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.mcpclient.invoker.ApiException;
//import gebo.microservices.api.client.mcpclient.api.McpClientSystemsControllerApi;


McpClientSystemsControllerApi apiInstance = new McpClientSystemsControllerApi();
MCPClientProjectEndpoint body = new MCPClientProjectEndpoint(); // MCPClientProjectEndpoint | 
try {
    apiInstance.deleteMCPClientEndpoint(body);
} catch (ApiException e) {
    System.err.println("Exception when calling McpClientSystemsControllerApi#deleteMCPClientEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**MCPClientProjectEndpoint**](MCPClientProjectEndpoint.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="findMCPClientEndpointsByCode"></a>
# **findMCPClientEndpointsByCode**
> MCPClientProjectEndpoint findMCPClientEndpointsByCode(code)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.mcpclient.invoker.ApiException;
//import gebo.microservices.api.client.mcpclient.api.McpClientSystemsControllerApi;


McpClientSystemsControllerApi apiInstance = new McpClientSystemsControllerApi();
Object code = null; // Object | 
try {
    MCPClientProjectEndpoint result = apiInstance.findMCPClientEndpointsByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling McpClientSystemsControllerApi#findMCPClientEndpointsByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | [**Object**](.md)|  |

### Return type

[**MCPClientProjectEndpoint**](MCPClientProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="findMCPClientEndpointsByProject"></a>
# **findMCPClientEndpointsByProject**
> Object findMCPClientEndpointsByProject(parentProjectCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.mcpclient.invoker.ApiException;
//import gebo.microservices.api.client.mcpclient.api.McpClientSystemsControllerApi;


McpClientSystemsControllerApi apiInstance = new McpClientSystemsControllerApi();
Object parentProjectCode = null; // Object | 
try {
    Object result = apiInstance.findMCPClientEndpointsByProject(parentProjectCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling McpClientSystemsControllerApi#findMCPClientEndpointsByProject");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **parentProjectCode** | [**Object**](.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="findMCPClientEndpointsByQbe"></a>
# **findMCPClientEndpointsByQbe**
> Object findMCPClientEndpointsByQbe(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.mcpclient.invoker.ApiException;
//import gebo.microservices.api.client.mcpclient.api.McpClientSystemsControllerApi;


McpClientSystemsControllerApi apiInstance = new McpClientSystemsControllerApi();
MCPClientProjectEndpoint body = new MCPClientProjectEndpoint(); // MCPClientProjectEndpoint | 
try {
    Object result = apiInstance.findMCPClientEndpointsByQbe(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling McpClientSystemsControllerApi#findMCPClientEndpointsByQbe");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**MCPClientProjectEndpoint**](MCPClientProjectEndpoint.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMCPClientSystemType"></a>
# **getMCPClientSystemType**
> GContentManagementSystemType getMCPClientSystemType()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.mcpclient.invoker.ApiException;
//import gebo.microservices.api.client.mcpclient.api.McpClientSystemsControllerApi;


McpClientSystemsControllerApi apiInstance = new McpClientSystemsControllerApi();
try {
    GContentManagementSystemType result = apiInstance.getMCPClientSystemType();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling McpClientSystemsControllerApi#getMCPClientSystemType");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**GContentManagementSystemType**](GContentManagementSystemType.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="insertMCPClientEndpoint"></a>
# **insertMCPClientEndpoint**
> MCPClientProjectEndpoint insertMCPClientEndpoint(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.mcpclient.invoker.ApiException;
//import gebo.microservices.api.client.mcpclient.api.McpClientSystemsControllerApi;


McpClientSystemsControllerApi apiInstance = new McpClientSystemsControllerApi();
MCPClientProjectEndpoint body = new MCPClientProjectEndpoint(); // MCPClientProjectEndpoint | 
try {
    MCPClientProjectEndpoint result = apiInstance.insertMCPClientEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling McpClientSystemsControllerApi#insertMCPClientEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**MCPClientProjectEndpoint**](MCPClientProjectEndpoint.md)|  |

### Return type

[**MCPClientProjectEndpoint**](MCPClientProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="publishMCPClientEndpoint"></a>
# **publishMCPClientEndpoint**
> OperationStatusGJobStatus publishMCPClientEndpoint(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.mcpclient.invoker.ApiException;
//import gebo.microservices.api.client.mcpclient.api.McpClientSystemsControllerApi;


McpClientSystemsControllerApi apiInstance = new McpClientSystemsControllerApi();
MCPClientProjectEndpoint body = new MCPClientProjectEndpoint(); // MCPClientProjectEndpoint | 
try {
    OperationStatusGJobStatus result = apiInstance.publishMCPClientEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling McpClientSystemsControllerApi#publishMCPClientEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**MCPClientProjectEndpoint**](MCPClientProjectEndpoint.md)|  |

### Return type

[**OperationStatusGJobStatus**](OperationStatusGJobStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateMCPClientEndpoint"></a>
# **updateMCPClientEndpoint**
> MCPClientProjectEndpoint updateMCPClientEndpoint(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.mcpclient.invoker.ApiException;
//import gebo.microservices.api.client.mcpclient.api.McpClientSystemsControllerApi;


McpClientSystemsControllerApi apiInstance = new McpClientSystemsControllerApi();
MCPClientProjectEndpoint body = new MCPClientProjectEndpoint(); // MCPClientProjectEndpoint | 
try {
    MCPClientProjectEndpoint result = apiInstance.updateMCPClientEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling McpClientSystemsControllerApi#updateMCPClientEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**MCPClientProjectEndpoint**](MCPClientProjectEndpoint.md)|  |

### Return type

[**MCPClientProjectEndpoint**](MCPClientProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

