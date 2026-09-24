# A2AClientConfigControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteA2AClientConfig**](A2AClientConfigControllerApi.md#deleteA2AClientConfig) | **DELETE** /api/admin/A2AClientConfigController/deleteA2AAgent | 
[**findByCodeA2AClientConfig**](A2AClientConfigControllerApi.md#findByCodeA2AClientConfig) | **GET** /api/admin/A2AClientConfigController/findByCode | 
[**insertA2AClientConfig**](A2AClientConfigControllerApi.md#insertA2AClientConfig) | **POST** /api/admin/A2AClientConfigController/insertA2AAgent | 
[**listA2AClientConfig**](A2AClientConfigControllerApi.md#listA2AClientConfig) | **GET** /api/admin/A2AClientConfigController/list | 
[**testAndDiscoveryA2AClientConfig**](A2AClientConfigControllerApi.md#testAndDiscoveryA2AClientConfig) | **POST** /api/admin/A2AClientConfigController/testAndDiscovery | 
[**updateA2AClientConfig**](A2AClientConfigControllerApi.md#updateA2AClientConfig) | **POST** /api/admin/A2AClientConfigController/updateA2AAgent | 

<a name="deleteA2AClientConfig"></a>
# **deleteA2AClientConfig**
> OperationStatusBoolean deleteA2AClientConfig(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.A2AClientConfigControllerApi;


A2AClientConfigControllerApi apiInstance = new A2AClientConfigControllerApi();
A2ARemoteAgentConfig body = new A2ARemoteAgentConfig(); // A2ARemoteAgentConfig | 
try {
    OperationStatusBoolean result = apiInstance.deleteA2AClientConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling A2AClientConfigControllerApi#deleteA2AClientConfig");
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

<a name="findByCodeA2AClientConfig"></a>
# **findByCodeA2AClientConfig**
> OperationStatusA2ARemoteAgentConfig findByCodeA2AClientConfig(code)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.A2AClientConfigControllerApi;


A2AClientConfigControllerApi apiInstance = new A2AClientConfigControllerApi();
String code = "code_example"; // String | 
try {
    OperationStatusA2ARemoteAgentConfig result = apiInstance.findByCodeA2AClientConfig(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling A2AClientConfigControllerApi#findByCodeA2AClientConfig");
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

<a name="insertA2AClientConfig"></a>
# **insertA2AClientConfig**
> OperationStatusA2ARemoteAgentConfig insertA2AClientConfig(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.A2AClientConfigControllerApi;


A2AClientConfigControllerApi apiInstance = new A2AClientConfigControllerApi();
A2ARemoteAgentConfig body = new A2ARemoteAgentConfig(); // A2ARemoteAgentConfig | 
try {
    OperationStatusA2ARemoteAgentConfig result = apiInstance.insertA2AClientConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling A2AClientConfigControllerApi#insertA2AClientConfig");
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

<a name="listA2AClientConfig"></a>
# **listA2AClientConfig**
> PageA2ARemoteAgentConfig listA2AClientConfig(page, size)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.A2AClientConfigControllerApi;


A2AClientConfigControllerApi apiInstance = new A2AClientConfigControllerApi();
Integer page = 0; // Integer | 
Integer size = 20; // Integer | 
try {
    PageA2ARemoteAgentConfig result = apiInstance.listA2AClientConfig(page, size);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling A2AClientConfigControllerApi#listA2AClientConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **page** | **Integer**|  | [optional] [default to 0]
 **size** | **Integer**|  | [optional] [default to 20]

### Return type

[**PageA2ARemoteAgentConfig**](PageA2ARemoteAgentConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="testAndDiscoveryA2AClientConfig"></a>
# **testAndDiscoveryA2AClientConfig**
> OperationStatusA2ARemoteAgentConfig testAndDiscoveryA2AClientConfig(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.A2AClientConfigControllerApi;


A2AClientConfigControllerApi apiInstance = new A2AClientConfigControllerApi();
A2ARemoteAgentConfig body = new A2ARemoteAgentConfig(); // A2ARemoteAgentConfig | 
try {
    OperationStatusA2ARemoteAgentConfig result = apiInstance.testAndDiscoveryA2AClientConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling A2AClientConfigControllerApi#testAndDiscoveryA2AClientConfig");
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

<a name="updateA2AClientConfig"></a>
# **updateA2AClientConfig**
> OperationStatusA2ARemoteAgentConfig updateA2AClientConfig(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.A2AClientConfigControllerApi;


A2AClientConfigControllerApi apiInstance = new A2AClientConfigControllerApi();
A2ARemoteAgentConfig body = new A2ARemoteAgentConfig(); // A2ARemoteAgentConfig | 
try {
    OperationStatusA2ARemoteAgentConfig result = apiInstance.updateA2AClientConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling A2AClientConfigControllerApi#updateA2AClientConfig");
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

