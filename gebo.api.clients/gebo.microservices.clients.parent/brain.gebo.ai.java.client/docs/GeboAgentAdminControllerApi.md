# GeboAgentAdminControllerApi

All URIs are relative to *http://localhost:13001*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteAgent**](GeboAgentAdminControllerApi.md#deleteAgent) | **DELETE** /api/admin/GeboAgentAdminController/deleteAgent | 
[**getAgentByCode**](GeboAgentAdminControllerApi.md#getAgentByCode) | **GET** /api/admin/GeboAgentAdminController/getAgentByCode | 
[**getAgents**](GeboAgentAdminControllerApi.md#getAgents) | **GET** /api/admin/GeboAgentAdminController/getAgents | 
[**getAgentsChoices**](GeboAgentAdminControllerApi.md#getAgentsChoices) | **GET** /api/admin/GeboAgentAdminController/getAgentsChoices | 
[**getPromptTemplatesByAgentId**](GeboAgentAdminControllerApi.md#getPromptTemplatesByAgentId) | **GET** /api/admin/GeboAgentAdminController/getPromptTemplateByAgentId | 
[**insertAgent**](GeboAgentAdminControllerApi.md#insertAgent) | **POST** /api/admin/GeboAgentAdminController/insertAgent | 
[**updateAgent**](GeboAgentAdminControllerApi.md#updateAgent) | **POST** /api/admin/GeboAgentAdminController/updateAgent | 

<a name="deleteAgent"></a>
# **deleteAgent**
> deleteAgent(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboAgentAdminControllerApi;


GeboAgentAdminControllerApi apiInstance = new GeboAgentAdminControllerApi();
GAgentConfig body = new GAgentConfig(); // GAgentConfig | 
try {
    apiInstance.deleteAgent(body);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAgentAdminControllerApi#deleteAgent");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAgentConfig**](GAgentConfig.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="getAgentByCode"></a>
# **getAgentByCode**
> GAgentConfig getAgentByCode(code)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboAgentAdminControllerApi;


GeboAgentAdminControllerApi apiInstance = new GeboAgentAdminControllerApi();
Object code = null; // Object | 
try {
    GAgentConfig result = apiInstance.getAgentByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAgentAdminControllerApi#getAgentByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | [**Object**](.md)|  |

### Return type

[**GAgentConfig**](GAgentConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAgents"></a>
# **getAgents**
> Object getAgents()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboAgentAdminControllerApi;


GeboAgentAdminControllerApi apiInstance = new GeboAgentAdminControllerApi();
try {
    Object result = apiInstance.getAgents();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAgentAdminControllerApi#getAgents");
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

<a name="getAgentsChoices"></a>
# **getAgentsChoices**
> Object getAgentsChoices()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboAgentAdminControllerApi;


GeboAgentAdminControllerApi apiInstance = new GeboAgentAdminControllerApi();
try {
    Object result = apiInstance.getAgentsChoices();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAgentAdminControllerApi#getAgentsChoices");
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

<a name="getPromptTemplatesByAgentId"></a>
# **getPromptTemplatesByAgentId**
> Object getPromptTemplatesByAgentId(agentId)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboAgentAdminControllerApi;


GeboAgentAdminControllerApi apiInstance = new GeboAgentAdminControllerApi();
Object agentId = null; // Object | 
try {
    Object result = apiInstance.getPromptTemplatesByAgentId(agentId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAgentAdminControllerApi#getPromptTemplatesByAgentId");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **agentId** | [**Object**](.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="insertAgent"></a>
# **insertAgent**
> GAgentConfig insertAgent(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboAgentAdminControllerApi;


GeboAgentAdminControllerApi apiInstance = new GeboAgentAdminControllerApi();
GAgentConfig body = new GAgentConfig(); // GAgentConfig | 
try {
    GAgentConfig result = apiInstance.insertAgent(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAgentAdminControllerApi#insertAgent");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAgentConfig**](GAgentConfig.md)|  |

### Return type

[**GAgentConfig**](GAgentConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateAgent"></a>
# **updateAgent**
> GAgentConfig updateAgent(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.GeboAgentAdminControllerApi;


GeboAgentAdminControllerApi apiInstance = new GeboAgentAdminControllerApi();
GAgentConfig body = new GAgentConfig(); // GAgentConfig | 
try {
    GAgentConfig result = apiInstance.updateAgent(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAgentAdminControllerApi#updateAgent");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAgentConfig**](GAgentConfig.md)|  |

### Return type

[**GAgentConfig**](GAgentConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

