# GeboA2AServerAdminControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**delete**](GeboA2AServerAdminControllerApi.md#delete) | **DELETE** /api/admin/GeboA2AServerAdminController/deleteA2AServer | 
[**findAll1**](GeboA2AServerAdminControllerApi.md#findAll1) | **GET** /api/admin/GeboA2AServerAdminController/findAll | 
[**findByCode1**](GeboA2AServerAdminControllerApi.md#findByCode1) | **GET** /api/admin/GeboA2AServerAdminController/findByCode | 
[**insert**](GeboA2AServerAdminControllerApi.md#insert) | **POST** /api/admin/GeboA2AServerAdminController/insertA2AServer | 
[**update1**](GeboA2AServerAdminControllerApi.md#update1) | **POST** /api/admin/GeboA2AServerAdminController/updateA2AServer | 

<a name="delete"></a>
# **delete**
> OperationStatusBoolean delete(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboA2AServerAdminControllerApi;


GeboA2AServerAdminControllerApi apiInstance = new GeboA2AServerAdminControllerApi();
String code = "code_example"; // String | 
try {
    OperationStatusBoolean result = apiInstance.delete(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboA2AServerAdminControllerApi#delete");
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

<a name="findAll1"></a>
# **findAll1**
> List&lt;A2AServerConfig&gt; findAll1()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboA2AServerAdminControllerApi;


GeboA2AServerAdminControllerApi apiInstance = new GeboA2AServerAdminControllerApi();
try {
    List<A2AServerConfig> result = apiInstance.findAll1();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboA2AServerAdminControllerApi#findAll1");
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

<a name="findByCode1"></a>
# **findByCode1**
> OperationStatusA2AServerConfig findByCode1(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboA2AServerAdminControllerApi;


GeboA2AServerAdminControllerApi apiInstance = new GeboA2AServerAdminControllerApi();
String code = "code_example"; // String | 
try {
    OperationStatusA2AServerConfig result = apiInstance.findByCode1(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboA2AServerAdminControllerApi#findByCode1");
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

<a name="insert"></a>
# **insert**
> OperationStatusA2AServerConfig insert(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboA2AServerAdminControllerApi;


GeboA2AServerAdminControllerApi apiInstance = new GeboA2AServerAdminControllerApi();
A2AServerConfig body = new A2AServerConfig(); // A2AServerConfig | 
try {
    OperationStatusA2AServerConfig result = apiInstance.insert(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboA2AServerAdminControllerApi#insert");
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

<a name="update1"></a>
# **update1**
> OperationStatusA2AServerConfig update1(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboA2AServerAdminControllerApi;


GeboA2AServerAdminControllerApi apiInstance = new GeboA2AServerAdminControllerApi();
A2AServerConfig body = new A2AServerConfig(); // A2AServerConfig | 
try {
    OperationStatusA2AServerConfig result = apiInstance.update1(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboA2AServerAdminControllerApi#update1");
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

