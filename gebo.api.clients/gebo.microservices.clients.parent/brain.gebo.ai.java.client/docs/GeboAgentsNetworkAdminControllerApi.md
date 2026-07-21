# GeboAgentsNetworkAdminControllerApi

All URIs are relative to *http://localhost:13001/brain*

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
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboAgentsNetworkAdminControllerApi;


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
> Object getAgentConfigs()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboAgentsNetworkAdminControllerApi;


GeboAgentsNetworkAdminControllerApi apiInstance = new GeboAgentsNetworkAdminControllerApi();
try {
    Object result = apiInstance.getAgentConfigs();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAgentsNetworkAdminControllerApi#getAgentConfigs");
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

<a name="getAgentConfigsByServiceId"></a>
# **getAgentConfigsByServiceId**
> Object getAgentConfigsByServiceId(serviceId)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboAgentsNetworkAdminControllerApi;


GeboAgentsNetworkAdminControllerApi apiInstance = new GeboAgentsNetworkAdminControllerApi();
Object serviceId = null; // Object | 
try {
    Object result = apiInstance.getAgentConfigsByServiceId(serviceId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAgentsNetworkAdminControllerApi#getAgentConfigsByServiceId");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **serviceId** | [**Object**](.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAgentServices"></a>
# **getAgentServices**
> Object getAgentServices()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboAgentsNetworkAdminControllerApi;


GeboAgentsNetworkAdminControllerApi apiInstance = new GeboAgentsNetworkAdminControllerApi();
try {
    Object result = apiInstance.getAgentServices();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAgentsNetworkAdminControllerApi#getAgentServices");
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

<a name="getAgentsNetwork"></a>
# **getAgentsNetwork**
> Object getAgentsNetwork()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboAgentsNetworkAdminControllerApi;


GeboAgentsNetworkAdminControllerApi apiInstance = new GeboAgentsNetworkAdminControllerApi();
try {
    Object result = apiInstance.getAgentsNetwork();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAgentsNetworkAdminControllerApi#getAgentsNetwork");
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

<a name="getAgentsNetworkByCode"></a>
# **getAgentsNetworkByCode**
> GAgentsNetwork getAgentsNetworkByCode(code)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboAgentsNetworkAdminControllerApi;


GeboAgentsNetworkAdminControllerApi apiInstance = new GeboAgentsNetworkAdminControllerApi();
Object code = null; // Object | 
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
 **code** | [**Object**](.md)|  |

### Return type

[**GAgentsNetwork**](GAgentsNetwork.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getCompatibleNextServices"></a>
# **getCompatibleNextServices**
> Object getCompatibleNextServices(serviceId)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboAgentsNetworkAdminControllerApi;


GeboAgentsNetworkAdminControllerApi apiInstance = new GeboAgentsNetworkAdminControllerApi();
Object serviceId = null; // Object | 
try {
    Object result = apiInstance.getCompatibleNextServices(serviceId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAgentsNetworkAdminControllerApi#getCompatibleNextServices");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **serviceId** | [**Object**](.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getCompatiblePreviousServices"></a>
# **getCompatiblePreviousServices**
> Object getCompatiblePreviousServices(serviceId)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboAgentsNetworkAdminControllerApi;


GeboAgentsNetworkAdminControllerApi apiInstance = new GeboAgentsNetworkAdminControllerApi();
Object serviceId = null; // Object | 
try {
    Object result = apiInstance.getCompatiblePreviousServices(serviceId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAgentsNetworkAdminControllerApi#getCompatiblePreviousServices");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **serviceId** | [**Object**](.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getNetworkAdapterServices"></a>
# **getNetworkAdapterServices**
> Object getNetworkAdapterServices()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboAgentsNetworkAdminControllerApi;


GeboAgentsNetworkAdminControllerApi apiInstance = new GeboAgentsNetworkAdminControllerApi();
try {
    Object result = apiInstance.getNetworkAdapterServices();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAgentsNetworkAdminControllerApi#getNetworkAdapterServices");
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

<a name="insertAgentsNetwork"></a>
# **insertAgentsNetwork**
> OperationStatusGAgentsNetwork insertAgentsNetwork(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboAgentsNetworkAdminControllerApi;


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
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboAgentsNetworkAdminControllerApi;


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
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboAgentsNetworkAdminControllerApi;


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

