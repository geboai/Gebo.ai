# GeboMcpServerUserControllerApi

All URIs are relative to *http://localhost:12999*

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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboMcpServerUserControllerApi;


GeboMcpServerUserControllerApi apiInstance = new GeboMcpServerUserControllerApi();
String code = "code_example"; // String | 
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
 **code** | **String**|  |

### Return type

[**UserAccessibleMcpServerView**](UserAccessibleMcpServerView.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getUsersCanAccessMcpServersList"></a>
# **getUsersCanAccessMcpServersList**
> Boolean getUsersCanAccessMcpServersList()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboMcpServerUserControllerApi;


GeboMcpServerUserControllerApi apiInstance = new GeboMcpServerUserControllerApi();
try {
    Boolean result = apiInstance.getUsersCanAccessMcpServersList();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboMcpServerUserControllerApi#getUsersCanAccessMcpServersList");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**Boolean**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="listAccessibleMcpServers"></a>
# **listAccessibleMcpServers**
> List&lt;UserAccessibleMcpServerView&gt; listAccessibleMcpServers()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboMcpServerUserControllerApi;


GeboMcpServerUserControllerApi apiInstance = new GeboMcpServerUserControllerApi();
try {
    List<UserAccessibleMcpServerView> result = apiInstance.listAccessibleMcpServers();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboMcpServerUserControllerApi#listAccessibleMcpServers");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;UserAccessibleMcpServerView&gt;**](UserAccessibleMcpServerView.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

