# GeboAgentsNetworkAdminControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteAgentsNetwork**](GeboAgentsNetworkAdminControllerApi.md#deleteAgentsNetwork) | **POST** /api/admin/GeboAgentsNetworkAdminController/deleteAgentsNetwork | 
[**getAgentConfigs**](GeboAgentsNetworkAdminControllerApi.md#getAgentConfigs) | **GET** /api/admin/GeboAgentsNetworkAdminController/getAgentConfigs | 
[**getAgentConfigsByServiceId**](GeboAgentsNetworkAdminControllerApi.md#getAgentConfigsByServiceId) | **GET** /api/admin/GeboAgentsNetworkAdminController/getAgentConfigsByServiceId | 
[**getAgentServices**](GeboAgentsNetworkAdminControllerApi.md#getAgentServices) | **GET** /api/admin/GeboAgentsNetworkAdminController/getAgentServices | 
[**getAgentsNetwork**](GeboAgentsNetworkAdminControllerApi.md#getAgentsNetwork) | **GET** /api/admin/GeboAgentsNetworkAdminController/getAgentsNetwork | 
[**getAgentsNetworkByCode**](GeboAgentsNetworkAdminControllerApi.md#getAgentsNetworkByCode) | **GET** /api/admin/GeboAgentsNetworkAdminController/getAgentsNetworkByCode | 
[**getCompatibleNextServices**](GeboAgentsNetworkAdminControllerApi.md#getCompatibleNextServices) | **GET** /api/admin/GeboAgentsNetworkAdminController/getCompatibleNextServices | 
[**getCompatiblePreviousServices**](GeboAgentsNetworkAdminControllerApi.md#getCompatiblePreviousServices) | **GET** /api/admin/GeboAgentsNetworkAdminController/getCompatiblePreviousServices | 
[**getNetworkAdapterServices**](GeboAgentsNetworkAdminControllerApi.md#getNetworkAdapterServices) | **GET** /api/admin/GeboAgentsNetworkAdminController/getNetworkAdapterServices | 
[**insertAgentsNetwork**](GeboAgentsNetworkAdminControllerApi.md#insertAgentsNetwork) | **POST** /api/admin/GeboAgentsNetworkAdminController/insertAgentsNetwork | 
[**updateAgentsNetwork**](GeboAgentsNetworkAdminControllerApi.md#updateAgentsNetwork) | **POST** /api/admin/GeboAgentsNetworkAdminController/updateAgentsNetwork | 
[**validateAgentsNetwork**](GeboAgentsNetworkAdminControllerApi.md#validateAgentsNetwork) | **POST** /api/admin/GeboAgentsNetworkAdminController/validateAgentsNetwork | 

<a name="deleteAgentsNetwork"></a>
# **deleteAgentsNetwork**
> OperationStatusGAgentsNetwork deleteAgentsNetwork(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAgentsNetworkAdminControllerApi;


GeboAgentsNetworkAdminControllerApi apiInstance = new GeboAgentsNetworkAdminControllerApi();
GAgentsNetwork body = new GAgentsNetwork(); // GAgentsNetwork | 
try {
    OperationStatusGAgentsNetwork result = apiInstance.deleteAgentsNetwork(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAgentsNetworkAdminControllerApi#deleteAgentsNetwork");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAgentsNetwork**](GAgentsNetwork.md)|  |

### Return type

[**OperationStatusGAgentsNetwork**](OperationStatusGAgentsNetwork.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getAgentConfigs"></a>
# **getAgentConfigs**
> List&lt;GBaseObject&gt; getAgentConfigs()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAgentsNetworkAdminControllerApi;


GeboAgentsNetworkAdminControllerApi apiInstance = new GeboAgentsNetworkAdminControllerApi();
try {
    List<GBaseObject> result = apiInstance.getAgentConfigs();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAgentsNetworkAdminControllerApi#getAgentConfigs");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GBaseObject&gt;**](GBaseObject.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAgentConfigsByServiceId"></a>
# **getAgentConfigsByServiceId**
> List&lt;GAgentConfig&gt; getAgentConfigsByServiceId(serviceId)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAgentsNetworkAdminControllerApi;


GeboAgentsNetworkAdminControllerApi apiInstance = new GeboAgentsNetworkAdminControllerApi();
String serviceId = "serviceId_example"; // String | 
try {
    List<GAgentConfig> result = apiInstance.getAgentConfigsByServiceId(serviceId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAgentsNetworkAdminControllerApi#getAgentConfigsByServiceId");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **serviceId** | **String**|  |

### Return type

[**List&lt;GAgentConfig&gt;**](GAgentConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAgentServices"></a>
# **getAgentServices**
> List&lt;AgentServiceDescriptor&gt; getAgentServices()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAgentsNetworkAdminControllerApi;


GeboAgentsNetworkAdminControllerApi apiInstance = new GeboAgentsNetworkAdminControllerApi();
try {
    List<AgentServiceDescriptor> result = apiInstance.getAgentServices();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAgentsNetworkAdminControllerApi#getAgentServices");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;AgentServiceDescriptor&gt;**](AgentServiceDescriptor.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAgentsNetwork"></a>
# **getAgentsNetwork**
> List&lt;GBaseObject&gt; getAgentsNetwork()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAgentsNetworkAdminControllerApi;


GeboAgentsNetworkAdminControllerApi apiInstance = new GeboAgentsNetworkAdminControllerApi();
try {
    List<GBaseObject> result = apiInstance.getAgentsNetwork();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAgentsNetworkAdminControllerApi#getAgentsNetwork");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GBaseObject&gt;**](GBaseObject.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAgentsNetworkByCode"></a>
# **getAgentsNetworkByCode**
> GAgentsNetwork getAgentsNetworkByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAgentsNetworkAdminControllerApi;


GeboAgentsNetworkAdminControllerApi apiInstance = new GeboAgentsNetworkAdminControllerApi();
String code = "code_example"; // String | 
try {
    GAgentsNetwork result = apiInstance.getAgentsNetworkByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAgentsNetworkAdminControllerApi#getAgentsNetworkByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GAgentsNetwork**](GAgentsNetwork.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getCompatibleNextServices"></a>
# **getCompatibleNextServices**
> List&lt;AgentServiceDescriptor&gt; getCompatibleNextServices(serviceId)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAgentsNetworkAdminControllerApi;


GeboAgentsNetworkAdminControllerApi apiInstance = new GeboAgentsNetworkAdminControllerApi();
String serviceId = "serviceId_example"; // String | 
try {
    List<AgentServiceDescriptor> result = apiInstance.getCompatibleNextServices(serviceId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAgentsNetworkAdminControllerApi#getCompatibleNextServices");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **serviceId** | **String**|  |

### Return type

[**List&lt;AgentServiceDescriptor&gt;**](AgentServiceDescriptor.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getCompatiblePreviousServices"></a>
# **getCompatiblePreviousServices**
> List&lt;AgentServiceDescriptor&gt; getCompatiblePreviousServices(serviceId)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAgentsNetworkAdminControllerApi;


GeboAgentsNetworkAdminControllerApi apiInstance = new GeboAgentsNetworkAdminControllerApi();
String serviceId = "serviceId_example"; // String | 
try {
    List<AgentServiceDescriptor> result = apiInstance.getCompatiblePreviousServices(serviceId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAgentsNetworkAdminControllerApi#getCompatiblePreviousServices");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **serviceId** | **String**|  |

### Return type

[**List&lt;AgentServiceDescriptor&gt;**](AgentServiceDescriptor.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getNetworkAdapterServices"></a>
# **getNetworkAdapterServices**
> List&lt;AgentServiceDescriptor&gt; getNetworkAdapterServices()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAgentsNetworkAdminControllerApi;


GeboAgentsNetworkAdminControllerApi apiInstance = new GeboAgentsNetworkAdminControllerApi();
try {
    List<AgentServiceDescriptor> result = apiInstance.getNetworkAdapterServices();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAgentsNetworkAdminControllerApi#getNetworkAdapterServices");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;AgentServiceDescriptor&gt;**](AgentServiceDescriptor.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="insertAgentsNetwork"></a>
# **insertAgentsNetwork**
> OperationStatusGAgentsNetwork insertAgentsNetwork(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAgentsNetworkAdminControllerApi;


GeboAgentsNetworkAdminControllerApi apiInstance = new GeboAgentsNetworkAdminControllerApi();
GAgentsNetwork body = new GAgentsNetwork(); // GAgentsNetwork | 
try {
    OperationStatusGAgentsNetwork result = apiInstance.insertAgentsNetwork(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAgentsNetworkAdminControllerApi#insertAgentsNetwork");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAgentsNetwork**](GAgentsNetwork.md)|  |

### Return type

[**OperationStatusGAgentsNetwork**](OperationStatusGAgentsNetwork.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateAgentsNetwork"></a>
# **updateAgentsNetwork**
> OperationStatusGAgentsNetwork updateAgentsNetwork(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAgentsNetworkAdminControllerApi;


GeboAgentsNetworkAdminControllerApi apiInstance = new GeboAgentsNetworkAdminControllerApi();
GAgentsNetwork body = new GAgentsNetwork(); // GAgentsNetwork | 
try {
    OperationStatusGAgentsNetwork result = apiInstance.updateAgentsNetwork(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAgentsNetworkAdminControllerApi#updateAgentsNetwork");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAgentsNetwork**](GAgentsNetwork.md)|  |

### Return type

[**OperationStatusGAgentsNetwork**](OperationStatusGAgentsNetwork.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="validateAgentsNetwork"></a>
# **validateAgentsNetwork**
> OperationStatusGAgentsNetwork validateAgentsNetwork(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAgentsNetworkAdminControllerApi;


GeboAgentsNetworkAdminControllerApi apiInstance = new GeboAgentsNetworkAdminControllerApi();
GAgentsNetwork body = new GAgentsNetwork(); // GAgentsNetwork | 
try {
    OperationStatusGAgentsNetwork result = apiInstance.validateAgentsNetwork(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAgentsNetworkAdminControllerApi#validateAgentsNetwork");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAgentsNetwork**](GAgentsNetwork.md)|  |

### Return type

[**OperationStatusGAgentsNetwork**](OperationStatusGAgentsNetwork.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

