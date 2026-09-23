# GeboAgentAdminControllerApi

All URIs are relative to *http://localhost:12999*

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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAgentAdminControllerApi;


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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAgentAdminControllerApi;


GeboAgentAdminControllerApi apiInstance = new GeboAgentAdminControllerApi();
String code = "code_example"; // String | 
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
 **code** | **String**|  |

### Return type

[**GAgentConfig**](GAgentConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAgents"></a>
# **getAgents**
> List&lt;GBaseObject&gt; getAgents()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAgentAdminControllerApi;


GeboAgentAdminControllerApi apiInstance = new GeboAgentAdminControllerApi();
try {
    List<GBaseObject> result = apiInstance.getAgents();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAgentAdminControllerApi#getAgents");
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

<a name="getAgentsChoices"></a>
# **getAgentsChoices**
> List&lt;GBaseObject&gt; getAgentsChoices()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAgentAdminControllerApi;


GeboAgentAdminControllerApi apiInstance = new GeboAgentAdminControllerApi();
try {
    List<GBaseObject> result = apiInstance.getAgentsChoices();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAgentAdminControllerApi#getAgentsChoices");
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

<a name="getPromptTemplatesByAgentId"></a>
# **getPromptTemplatesByAgentId**
> List&lt;GPromptTemplateConfig&gt; getPromptTemplatesByAgentId(agentId)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAgentAdminControllerApi;


GeboAgentAdminControllerApi apiInstance = new GeboAgentAdminControllerApi();
String agentId = "agentId_example"; // String | 
try {
    List<GPromptTemplateConfig> result = apiInstance.getPromptTemplatesByAgentId(agentId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAgentAdminControllerApi#getPromptTemplatesByAgentId");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **agentId** | **String**|  |

### Return type

[**List&lt;GPromptTemplateConfig&gt;**](GPromptTemplateConfig.md)

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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAgentAdminControllerApi;


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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAgentAdminControllerApi;


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

