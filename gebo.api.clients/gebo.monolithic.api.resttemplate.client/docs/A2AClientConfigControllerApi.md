# A2AClientConfigControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**delete1**](A2AClientConfigControllerApi.md#delete1) | **DELETE** /api/admin/A2AClientConfigController/deleteA2AAgent | 
[**findByCode2**](A2AClientConfigControllerApi.md#findByCode2) | **GET** /api/admin/A2AClientConfigController/findByCode | 
[**insert1**](A2AClientConfigControllerApi.md#insert1) | **POST** /api/admin/A2AClientConfigController/insertA2AAgent | 
[**list**](A2AClientConfigControllerApi.md#list) | **GET** /api/admin/A2AClientConfigController/list | 
[**testAndDiscovery1**](A2AClientConfigControllerApi.md#testAndDiscovery1) | **POST** /api/admin/A2AClientConfigController/testAndDiscovery | 
[**update2**](A2AClientConfigControllerApi.md#update2) | **POST** /api/admin/A2AClientConfigController/updateA2AAgent | 

<a name="delete1"></a>
# **delete1**
> OperationStatusBoolean delete1(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.A2AClientConfigControllerApi;


A2AClientConfigControllerApi apiInstance = new A2AClientConfigControllerApi();
A2ARemoteAgentConfig body = new A2ARemoteAgentConfig(); // A2ARemoteAgentConfig | 
try {
    OperationStatusBoolean result = apiInstance.delete1(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling A2AClientConfigControllerApi#delete1");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**A2ARemoteAgentConfig**](A2ARemoteAgentConfig.md)|  |

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findByCode2"></a>
# **findByCode2**
> OperationStatusA2ARemoteAgentConfig findByCode2(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.A2AClientConfigControllerApi;


A2AClientConfigControllerApi apiInstance = new A2AClientConfigControllerApi();
String code = "code_example"; // String | 
try {
    OperationStatusA2ARemoteAgentConfig result = apiInstance.findByCode2(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling A2AClientConfigControllerApi#findByCode2");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**OperationStatusA2ARemoteAgentConfig**](OperationStatusA2ARemoteAgentConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="insert1"></a>
# **insert1**
> OperationStatusA2ARemoteAgentConfig insert1(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.A2AClientConfigControllerApi;


A2AClientConfigControllerApi apiInstance = new A2AClientConfigControllerApi();
A2ARemoteAgentConfig body = new A2ARemoteAgentConfig(); // A2ARemoteAgentConfig | 
try {
    OperationStatusA2ARemoteAgentConfig result = apiInstance.insert1(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling A2AClientConfigControllerApi#insert1");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**A2ARemoteAgentConfig**](A2ARemoteAgentConfig.md)|  |

### Return type

[**OperationStatusA2ARemoteAgentConfig**](OperationStatusA2ARemoteAgentConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="list"></a>
# **list**
> PagedModelA2ARemoteAgentConfig list(page, size)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.A2AClientConfigControllerApi;


A2AClientConfigControllerApi apiInstance = new A2AClientConfigControllerApi();
Integer page = 0; // Integer | 
Integer size = 20; // Integer | 
try {
    PagedModelA2ARemoteAgentConfig result = apiInstance.list(page, size);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling A2AClientConfigControllerApi#list");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **page** | **Integer**|  | [optional] [default to 0]
 **size** | **Integer**|  | [optional] [default to 20]

### Return type

[**PagedModelA2ARemoteAgentConfig**](PagedModelA2ARemoteAgentConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="testAndDiscovery1"></a>
# **testAndDiscovery1**
> OperationStatusA2ARemoteAgentConfig testAndDiscovery1(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.A2AClientConfigControllerApi;


A2AClientConfigControllerApi apiInstance = new A2AClientConfigControllerApi();
A2ARemoteAgentConfig body = new A2ARemoteAgentConfig(); // A2ARemoteAgentConfig | 
try {
    OperationStatusA2ARemoteAgentConfig result = apiInstance.testAndDiscovery1(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling A2AClientConfigControllerApi#testAndDiscovery1");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**A2ARemoteAgentConfig**](A2ARemoteAgentConfig.md)|  |

### Return type

[**OperationStatusA2ARemoteAgentConfig**](OperationStatusA2ARemoteAgentConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="update2"></a>
# **update2**
> OperationStatusA2ARemoteAgentConfig update2(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.A2AClientConfigControllerApi;


A2AClientConfigControllerApi apiInstance = new A2AClientConfigControllerApi();
A2ARemoteAgentConfig body = new A2ARemoteAgentConfig(); // A2ARemoteAgentConfig | 
try {
    OperationStatusA2ARemoteAgentConfig result = apiInstance.update2(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling A2AClientConfigControllerApi#update2");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**A2ARemoteAgentConfig**](A2ARemoteAgentConfig.md)|  |

### Return type

[**OperationStatusA2ARemoteAgentConfig**](OperationStatusA2ARemoteAgentConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

