# GeboMcpServerAdminControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteMcpServer**](GeboMcpServerAdminControllerApi.md#deleteMcpServer) | **POST** /api/admin/GeboMCPServerAdminController/deleteMcpServer | 
[**findMcpServerByCode**](GeboMcpServerAdminControllerApi.md#findMcpServerByCode) | **GET** /api/admin/GeboMCPServerAdminController/findMcpServerByCode | 
[**getAllMcpServers**](GeboMcpServerAdminControllerApi.md#getAllMcpServers) | **GET** /api/admin/GeboMCPServerAdminController/getAllMcpServers | 
[**getMcpServerPagedList**](GeboMcpServerAdminControllerApi.md#getMcpServerPagedList) | **POST** /api/admin/GeboMCPServerAdminController/getMcpServerPagedList | 
[**insertMcpServer**](GeboMcpServerAdminControllerApi.md#insertMcpServer) | **POST** /api/admin/GeboMCPServerAdminController/insertMcpServer | 
[**setMcpServerAccessAcls**](GeboMcpServerAdminControllerApi.md#setMcpServerAccessAcls) | **POST** /api/admin/GeboMCPServerAdminController/setMcpServerAccessAcls | 
[**updateMcpServer**](GeboMcpServerAdminControllerApi.md#updateMcpServer) | **POST** /api/admin/GeboMCPServerAdminController/updateMcpServer | 

<a name="deleteMcpServer"></a>
# **deleteMcpServer**
> deleteMcpServer(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboMcpServerAdminControllerApi;


GeboMcpServerAdminControllerApi apiInstance = new GeboMcpServerAdminControllerApi();
String code = "code_example"; // String | 
try {
    apiInstance.deleteMcpServer(code);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboMcpServerAdminControllerApi#deleteMcpServer");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="findMcpServerByCode"></a>
# **findMcpServerByCode**
> GeboMCPServerConfig findMcpServerByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboMcpServerAdminControllerApi;


GeboMcpServerAdminControllerApi apiInstance = new GeboMcpServerAdminControllerApi();
String code = "code_example"; // String | 
try {
    GeboMCPServerConfig result = apiInstance.findMcpServerByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboMcpServerAdminControllerApi#findMcpServerByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GeboMCPServerConfig**](GeboMCPServerConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAllMcpServers"></a>
# **getAllMcpServers**
> List&lt;GeboMCPServerConfig&gt; getAllMcpServers()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboMcpServerAdminControllerApi;


GeboMcpServerAdminControllerApi apiInstance = new GeboMcpServerAdminControllerApi();
try {
    List<GeboMCPServerConfig> result = apiInstance.getAllMcpServers();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboMcpServerAdminControllerApi#getAllMcpServers");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GeboMCPServerConfig&gt;**](GeboMCPServerConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getMcpServerPagedList"></a>
# **getMcpServerPagedList**
> PagedModelGeboMCPServerConfig getMcpServerPagedList(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboMcpServerAdminControllerApi;


GeboMcpServerAdminControllerApi apiInstance = new GeboMcpServerAdminControllerApi();
DataPage body = new DataPage(); // DataPage | 
try {
    PagedModelGeboMCPServerConfig result = apiInstance.getMcpServerPagedList(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboMcpServerAdminControllerApi#getMcpServerPagedList");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**DataPage**](DataPage.md)|  |

### Return type

[**PagedModelGeboMCPServerConfig**](PagedModelGeboMCPServerConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertMcpServer"></a>
# **insertMcpServer**
> GeboMCPServerConfig insertMcpServer(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboMcpServerAdminControllerApi;


GeboMcpServerAdminControllerApi apiInstance = new GeboMcpServerAdminControllerApi();
GeboMCPServerConfig body = new GeboMCPServerConfig(); // GeboMCPServerConfig | 
try {
    GeboMCPServerConfig result = apiInstance.insertMcpServer(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboMcpServerAdminControllerApi#insertMcpServer");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GeboMCPServerConfig**](GeboMCPServerConfig.md)|  |

### Return type

[**GeboMCPServerConfig**](GeboMCPServerConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="setMcpServerAccessAcls"></a>
# **setMcpServerAccessAcls**
> GeboMCPServerConfig setMcpServerAccessAcls(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboMcpServerAdminControllerApi;


GeboMcpServerAdminControllerApi apiInstance = new GeboMcpServerAdminControllerApi();
SetMcpServerAclsParam body = new SetMcpServerAclsParam(); // SetMcpServerAclsParam | 
try {
    GeboMCPServerConfig result = apiInstance.setMcpServerAccessAcls(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboMcpServerAdminControllerApi#setMcpServerAccessAcls");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SetMcpServerAclsParam**](SetMcpServerAclsParam.md)|  |

### Return type

[**GeboMCPServerConfig**](GeboMCPServerConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateMcpServer"></a>
# **updateMcpServer**
> GeboMCPServerConfig updateMcpServer(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboMcpServerAdminControllerApi;


GeboMcpServerAdminControllerApi apiInstance = new GeboMcpServerAdminControllerApi();
GeboMCPServerConfig body = new GeboMCPServerConfig(); // GeboMCPServerConfig | 
try {
    GeboMCPServerConfig result = apiInstance.updateMcpServer(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboMcpServerAdminControllerApi#updateMcpServer");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GeboMCPServerConfig**](GeboMCPServerConfig.md)|  |

### Return type

[**GeboMCPServerConfig**](GeboMCPServerConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

