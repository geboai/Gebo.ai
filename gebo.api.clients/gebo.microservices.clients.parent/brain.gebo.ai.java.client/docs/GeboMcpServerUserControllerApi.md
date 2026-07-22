# GeboMcpServerUserControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**findAccessibleMcpServerByCode**](GeboMcpServerUserControllerApi.md#findAccessibleMcpServerByCode) | **GET** /api/user/GeboMCPServerUserController/findAccessibleMcpServerByCode | 
[**getUsersCanAccessMcpServersList**](GeboMcpServerUserControllerApi.md#getUsersCanAccessMcpServersList) | **GET** /api/user/GeboMCPServerUserController/getUsersCanAccessMcpServersList | 
[**listAccessibleMcpServers**](GeboMcpServerUserControllerApi.md#listAccessibleMcpServers) | **GET** /api/user/GeboMCPServerUserController/listAccessibleMcpServers | 

<a name="findAccessibleMcpServerByCode"></a>
# **findAccessibleMcpServerByCode**
> UserAccessibleMcpServerView findAccessibleMcpServerByCode(code)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboMcpServerUserControllerApi;


GeboMcpServerUserControllerApi apiInstance = new GeboMcpServerUserControllerApi();
Object code = null; // Object | 
try {
    UserAccessibleMcpServerView result = apiInstance.findAccessibleMcpServerByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboMcpServerUserControllerApi#findAccessibleMcpServerByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | [**Object**](.md)|  |

### Return type

[**UserAccessibleMcpServerView**](UserAccessibleMcpServerView.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getUsersCanAccessMcpServersList"></a>
# **getUsersCanAccessMcpServersList**
> Object getUsersCanAccessMcpServersList()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboMcpServerUserControllerApi;


GeboMcpServerUserControllerApi apiInstance = new GeboMcpServerUserControllerApi();
try {
    Object result = apiInstance.getUsersCanAccessMcpServersList();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboMcpServerUserControllerApi#getUsersCanAccessMcpServersList");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="listAccessibleMcpServers"></a>
# **listAccessibleMcpServers**
> Object listAccessibleMcpServers()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboMcpServerUserControllerApi;


GeboMcpServerUserControllerApi apiInstance = new GeboMcpServerUserControllerApi();
try {
    Object result = apiInstance.listAccessibleMcpServers();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboMcpServerUserControllerApi#listAccessibleMcpServers");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

