# McpClientConfigControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteMCPClientConfig**](McpClientConfigControllerApi.md#deleteMCPClientConfig) | **DELETE** /api/admin/McpClientConfigController/deleteMCPClientConfig | 
[**findMCPClientConfigByCode**](McpClientConfigControllerApi.md#findMCPClientConfigByCode) | **GET** /api/admin/McpClientConfigController/findMCPClientConfigByCode | 
[**findMCPClientConfigByQbe**](McpClientConfigControllerApi.md#findMCPClientConfigByQbe) | **POST** /api/admin/McpClientConfigController/findMCPClientConfigByQbe | 
[**insertMCPClientConfig**](McpClientConfigControllerApi.md#insertMCPClientConfig) | **POST** /api/admin/McpClientConfigController/insertMCPClientConfig | 
[**listMCPClientConfig**](McpClientConfigControllerApi.md#listMCPClientConfig) | **POST** /api/admin/McpClientConfigController/listMCPClientConfig | 
[**testAndDiscovery**](McpClientConfigControllerApi.md#testAndDiscovery) | **POST** /api/admin/McpClientConfigController/testAndDiscovery | 
[**updateMCPClientConfig**](McpClientConfigControllerApi.md#updateMCPClientConfig) | **POST** /api/admin/McpClientConfigController/updateMCPClientConfig | 

<a name="deleteMCPClientConfig"></a>
# **deleteMCPClientConfig**
> OperationStatusBoolean deleteMCPClientConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.McpClientConfigControllerApi;


McpClientConfigControllerApi apiInstance = new McpClientConfigControllerApi();
MCPClientConfig body = new MCPClientConfig(); // MCPClientConfig | 
try {
    OperationStatusBoolean result = apiInstance.deleteMCPClientConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling McpClientConfigControllerApi#deleteMCPClientConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**MCPClientConfig**](MCPClientConfig.md)|  |

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findMCPClientConfigByCode"></a>
# **findMCPClientConfigByCode**
> OperationStatusMCPClientConfig findMCPClientConfigByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.McpClientConfigControllerApi;


McpClientConfigControllerApi apiInstance = new McpClientConfigControllerApi();
String code = "code_example"; // String | 
try {
    OperationStatusMCPClientConfig result = apiInstance.findMCPClientConfigByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling McpClientConfigControllerApi#findMCPClientConfigByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**OperationStatusMCPClientConfig**](OperationStatusMCPClientConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findMCPClientConfigByQbe"></a>
# **findMCPClientConfigByQbe**
> PagedModelMCPClientConfig findMCPClientConfigByQbe(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.McpClientConfigControllerApi;


McpClientConfigControllerApi apiInstance = new McpClientConfigControllerApi();
FindByMCPClientConfigQbeParam body = new FindByMCPClientConfigQbeParam(); // FindByMCPClientConfigQbeParam | 
try {
    PagedModelMCPClientConfig result = apiInstance.findMCPClientConfigByQbe(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling McpClientConfigControllerApi#findMCPClientConfigByQbe");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**FindByMCPClientConfigQbeParam**](FindByMCPClientConfigQbeParam.md)|  |

### Return type

[**PagedModelMCPClientConfig**](PagedModelMCPClientConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertMCPClientConfig"></a>
# **insertMCPClientConfig**
> OperationStatusMCPClientConfig insertMCPClientConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.McpClientConfigControllerApi;


McpClientConfigControllerApi apiInstance = new McpClientConfigControllerApi();
MCPClientConfig body = new MCPClientConfig(); // MCPClientConfig | 
try {
    OperationStatusMCPClientConfig result = apiInstance.insertMCPClientConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling McpClientConfigControllerApi#insertMCPClientConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**MCPClientConfig**](MCPClientConfig.md)|  |

### Return type

[**OperationStatusMCPClientConfig**](OperationStatusMCPClientConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="listMCPClientConfig"></a>
# **listMCPClientConfig**
> PagedModelMCPClientConfig listMCPClientConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.McpClientConfigControllerApi;


McpClientConfigControllerApi apiInstance = new McpClientConfigControllerApi();
DataPage body = new DataPage(); // DataPage | 
try {
    PagedModelMCPClientConfig result = apiInstance.listMCPClientConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling McpClientConfigControllerApi#listMCPClientConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**DataPage**](DataPage.md)|  |

### Return type

[**PagedModelMCPClientConfig**](PagedModelMCPClientConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="testAndDiscovery"></a>
# **testAndDiscovery**
> OperationStatusMCPClientConfig testAndDiscovery(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.McpClientConfigControllerApi;


McpClientConfigControllerApi apiInstance = new McpClientConfigControllerApi();
MCPClientConfig body = new MCPClientConfig(); // MCPClientConfig | 
try {
    OperationStatusMCPClientConfig result = apiInstance.testAndDiscovery(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling McpClientConfigControllerApi#testAndDiscovery");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**MCPClientConfig**](MCPClientConfig.md)|  |

### Return type

[**OperationStatusMCPClientConfig**](OperationStatusMCPClientConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateMCPClientConfig"></a>
# **updateMCPClientConfig**
> OperationStatusMCPClientConfig updateMCPClientConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.McpClientConfigControllerApi;


McpClientConfigControllerApi apiInstance = new McpClientConfigControllerApi();
MCPClientConfig body = new MCPClientConfig(); // MCPClientConfig | 
try {
    OperationStatusMCPClientConfig result = apiInstance.updateMCPClientConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling McpClientConfigControllerApi#updateMCPClientConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**MCPClientConfig**](MCPClientConfig.md)|  |

### Return type

[**OperationStatusMCPClientConfig**](OperationStatusMCPClientConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

