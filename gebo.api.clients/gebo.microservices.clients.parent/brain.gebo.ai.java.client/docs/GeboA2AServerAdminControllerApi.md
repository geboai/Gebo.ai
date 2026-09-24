# GeboA2AServerAdminControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteA2AServer**](GeboA2AServerAdminControllerApi.md#deleteA2AServer) | **DELETE** /api/admin/GeboA2AServerAdminController/deleteA2AServer | 
[**findAllA2AServer**](GeboA2AServerAdminControllerApi.md#findAllA2AServer) | **GET** /api/admin/GeboA2AServerAdminController/findAll | 
[**findByCodeA2AServer**](GeboA2AServerAdminControllerApi.md#findByCodeA2AServer) | **GET** /api/admin/GeboA2AServerAdminController/findByCode | 
[**insertA2AServer**](GeboA2AServerAdminControllerApi.md#insertA2AServer) | **POST** /api/admin/GeboA2AServerAdminController/insertA2AServer | 
[**updateA2AServer**](GeboA2AServerAdminControllerApi.md#updateA2AServer) | **POST** /api/admin/GeboA2AServerAdminController/updateA2AServer | 

<a name="deleteA2AServer"></a>
# **deleteA2AServer**
> OperationStatusBoolean deleteA2AServer(code)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboA2AServerAdminControllerApi;


GeboA2AServerAdminControllerApi apiInstance = new GeboA2AServerAdminControllerApi();
String code = "code_example"; // String | 
try {
    OperationStatusBoolean result = apiInstance.deleteA2AServer(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboA2AServerAdminControllerApi#deleteA2AServer");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findAllA2AServer"></a>
# **findAllA2AServer**
> List&lt;A2AServerConfig&gt; findAllA2AServer()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboA2AServerAdminControllerApi;


GeboA2AServerAdminControllerApi apiInstance = new GeboA2AServerAdminControllerApi();
try {
    List<A2AServerConfig> result = apiInstance.findAllA2AServer();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboA2AServerAdminControllerApi#findAllA2AServer");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;A2AServerConfig&gt;**](A2AServerConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findByCodeA2AServer"></a>
# **findByCodeA2AServer**
> OperationStatusA2AServerConfig findByCodeA2AServer(code)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboA2AServerAdminControllerApi;


GeboA2AServerAdminControllerApi apiInstance = new GeboA2AServerAdminControllerApi();
String code = "code_example"; // String | 
try {
    OperationStatusA2AServerConfig result = apiInstance.findByCodeA2AServer(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboA2AServerAdminControllerApi#findByCodeA2AServer");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**OperationStatusA2AServerConfig**](OperationStatusA2AServerConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="insertA2AServer"></a>
# **insertA2AServer**
> OperationStatusA2AServerConfig insertA2AServer(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboA2AServerAdminControllerApi;


GeboA2AServerAdminControllerApi apiInstance = new GeboA2AServerAdminControllerApi();
A2AServerConfig body = new A2AServerConfig(); // A2AServerConfig | 
try {
    OperationStatusA2AServerConfig result = apiInstance.insertA2AServer(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboA2AServerAdminControllerApi#insertA2AServer");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**A2AServerConfig**](A2AServerConfig.md)|  |

### Return type

[**OperationStatusA2AServerConfig**](OperationStatusA2AServerConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateA2AServer"></a>
# **updateA2AServer**
> OperationStatusA2AServerConfig updateA2AServer(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboA2AServerAdminControllerApi;


GeboA2AServerAdminControllerApi apiInstance = new GeboA2AServerAdminControllerApi();
A2AServerConfig body = new A2AServerConfig(); // A2AServerConfig | 
try {
    OperationStatusA2AServerConfig result = apiInstance.updateA2AServer(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboA2AServerAdminControllerApi#updateA2AServer");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**A2AServerConfig**](A2AServerConfig.md)|  |

### Return type

[**OperationStatusA2AServerConfig**](OperationStatusA2AServerConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

