# GraphRagConfigurationControllerApi

All URIs are relative to *http://localhost:13003/graphicator*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteGraphRagExtractionConfig**](GraphRagConfigurationControllerApi.md#deleteGraphRagExtractionConfig) | **DELETE** /api/admin/GraphRagConfigurationController/deleteGraphRagExtractionConfig | 
[**findGraphRagExtractionConfigByCode**](GraphRagConfigurationControllerApi.md#findGraphRagExtractionConfigByCode) | **GET** /api/admin/GraphRagConfigurationController/findGraphRagExtractionConfigByCode | 
[**findGraphRagExtractionConfigByKnowledgeBase**](GraphRagConfigurationControllerApi.md#findGraphRagExtractionConfigByKnowledgeBase) | **GET** /api/admin/GraphRagConfigurationController/findGraphRagExtractionConfigByKnowledgeBase | 
[**findGraphRagExtractionConfigByKnowledgeBaseAndProjectCode**](GraphRagConfigurationControllerApi.md#findGraphRagExtractionConfigByKnowledgeBaseAndProjectCode) | **GET** /api/admin/GraphRagConfigurationController/findGraphRagExtractionConfigByKnowledgeBaseAndProjectCode | 
[**findGraphRagExtractionConfigByProjectEndpointGObjectRef**](GraphRagConfigurationControllerApi.md#findGraphRagExtractionConfigByProjectEndpointGObjectRef) | **POST** /api/admin/GraphRagConfigurationController/findGraphRagExtractionConfigByProjectEndpointGObjectRef | 
[**getDefaultGraphRagExtractionConfig**](GraphRagConfigurationControllerApi.md#getDefaultGraphRagExtractionConfig) | **GET** /api/admin/GraphRagConfigurationController/getDefaultGraphRagExtractionConfig | 
[**getSystemGraphRagExtractionConfig**](GraphRagConfigurationControllerApi.md#getSystemGraphRagExtractionConfig) | **GET** /api/admin/GraphRagConfigurationController/getSystemGraphRagExtractionConfig | 
[**instertGraphRagExtractionConfig**](GraphRagConfigurationControllerApi.md#instertGraphRagExtractionConfig) | **POST** /api/admin/GraphRagConfigurationController/instertGraphRagExtractionConfig | 
[**saveGraphRagExtractionConfig**](GraphRagConfigurationControllerApi.md#saveGraphRagExtractionConfig) | **POST** /api/admin/GraphRagConfigurationController/saveGraphRagExtractionConfig | 

<a name="deleteGraphRagExtractionConfig"></a>
# **deleteGraphRagExtractionConfig**
> deleteGraphRagExtractionConfig(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.graphicator.invoker.ApiException;
//import gebo.microservices.api.client.graphicator.api.GraphRagConfigurationControllerApi;


GraphRagConfigurationControllerApi apiInstance = new GraphRagConfigurationControllerApi();
GraphRagExtractionConfig body = new GraphRagExtractionConfig(); // GraphRagExtractionConfig | 
try {
    apiInstance.deleteGraphRagExtractionConfig(body);
} catch (ApiException e) {
    System.err.println("Exception when calling GraphRagConfigurationControllerApi#deleteGraphRagExtractionConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GraphRagExtractionConfig**](GraphRagExtractionConfig.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="findGraphRagExtractionConfigByCode"></a>
# **findGraphRagExtractionConfigByCode**
> GraphRagExtractionConfig findGraphRagExtractionConfigByCode(code)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.graphicator.invoker.ApiException;
//import gebo.microservices.api.client.graphicator.api.GraphRagConfigurationControllerApi;


GraphRagConfigurationControllerApi apiInstance = new GraphRagConfigurationControllerApi();
Object code = null; // Object | 
try {
    GraphRagExtractionConfig result = apiInstance.findGraphRagExtractionConfigByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GraphRagConfigurationControllerApi#findGraphRagExtractionConfigByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | [**Object**](.md)|  |

### Return type

[**GraphRagExtractionConfig**](GraphRagExtractionConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findGraphRagExtractionConfigByKnowledgeBase"></a>
# **findGraphRagExtractionConfigByKnowledgeBase**
> Object findGraphRagExtractionConfigByKnowledgeBase(knowledgeBaseCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.graphicator.invoker.ApiException;
//import gebo.microservices.api.client.graphicator.api.GraphRagConfigurationControllerApi;


GraphRagConfigurationControllerApi apiInstance = new GraphRagConfigurationControllerApi();
Object knowledgeBaseCode = null; // Object | 
try {
    Object result = apiInstance.findGraphRagExtractionConfigByKnowledgeBase(knowledgeBaseCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GraphRagConfigurationControllerApi#findGraphRagExtractionConfigByKnowledgeBase");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **knowledgeBaseCode** | [**Object**](.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findGraphRagExtractionConfigByKnowledgeBaseAndProjectCode"></a>
# **findGraphRagExtractionConfigByKnowledgeBaseAndProjectCode**
> Object findGraphRagExtractionConfigByKnowledgeBaseAndProjectCode(knowledgeBaseCode, projectCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.graphicator.invoker.ApiException;
//import gebo.microservices.api.client.graphicator.api.GraphRagConfigurationControllerApi;


GraphRagConfigurationControllerApi apiInstance = new GraphRagConfigurationControllerApi();
Object knowledgeBaseCode = null; // Object | 
Object projectCode = null; // Object | 
try {
    Object result = apiInstance.findGraphRagExtractionConfigByKnowledgeBaseAndProjectCode(knowledgeBaseCode, projectCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GraphRagConfigurationControllerApi#findGraphRagExtractionConfigByKnowledgeBaseAndProjectCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **knowledgeBaseCode** | [**Object**](.md)|  |
 **projectCode** | [**Object**](.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findGraphRagExtractionConfigByProjectEndpointGObjectRef"></a>
# **findGraphRagExtractionConfigByProjectEndpointGObjectRef**
> Object findGraphRagExtractionConfigByProjectEndpointGObjectRef(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.graphicator.invoker.ApiException;
//import gebo.microservices.api.client.graphicator.api.GraphRagConfigurationControllerApi;


GraphRagConfigurationControllerApi apiInstance = new GraphRagConfigurationControllerApi();
GObjectRefGProjectEndpoint body = new GObjectRefGProjectEndpoint(); // GObjectRefGProjectEndpoint | 
try {
    Object result = apiInstance.findGraphRagExtractionConfigByProjectEndpointGObjectRef(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GraphRagConfigurationControllerApi#findGraphRagExtractionConfigByProjectEndpointGObjectRef");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GObjectRefGProjectEndpoint**](GObjectRefGProjectEndpoint.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getDefaultGraphRagExtractionConfig"></a>
# **getDefaultGraphRagExtractionConfig**
> Object getDefaultGraphRagExtractionConfig()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.graphicator.invoker.ApiException;
//import gebo.microservices.api.client.graphicator.api.GraphRagConfigurationControllerApi;


GraphRagConfigurationControllerApi apiInstance = new GraphRagConfigurationControllerApi();
try {
    Object result = apiInstance.getDefaultGraphRagExtractionConfig();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GraphRagConfigurationControllerApi#getDefaultGraphRagExtractionConfig");
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

<a name="getSystemGraphRagExtractionConfig"></a>
# **getSystemGraphRagExtractionConfig**
> GraphRagExtractionConfig getSystemGraphRagExtractionConfig(format)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.graphicator.invoker.ApiException;
//import gebo.microservices.api.client.graphicator.api.GraphRagConfigurationControllerApi;


GraphRagConfigurationControllerApi apiInstance = new GraphRagConfigurationControllerApi();
Object format = null; // Object | 
try {
    GraphRagExtractionConfig result = apiInstance.getSystemGraphRagExtractionConfig(format);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GraphRagConfigurationControllerApi#getSystemGraphRagExtractionConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **format** | [**Object**](.md)|  |

### Return type

[**GraphRagExtractionConfig**](GraphRagExtractionConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="instertGraphRagExtractionConfig"></a>
# **instertGraphRagExtractionConfig**
> GraphRagExtractionConfig instertGraphRagExtractionConfig(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.graphicator.invoker.ApiException;
//import gebo.microservices.api.client.graphicator.api.GraphRagConfigurationControllerApi;


GraphRagConfigurationControllerApi apiInstance = new GraphRagConfigurationControllerApi();
GraphRagExtractionConfig body = new GraphRagExtractionConfig(); // GraphRagExtractionConfig | 
try {
    GraphRagExtractionConfig result = apiInstance.instertGraphRagExtractionConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GraphRagConfigurationControllerApi#instertGraphRagExtractionConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GraphRagExtractionConfig**](GraphRagExtractionConfig.md)|  |

### Return type

[**GraphRagExtractionConfig**](GraphRagExtractionConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="saveGraphRagExtractionConfig"></a>
# **saveGraphRagExtractionConfig**
> GraphRagExtractionConfig saveGraphRagExtractionConfig(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.graphicator.invoker.ApiException;
//import gebo.microservices.api.client.graphicator.api.GraphRagConfigurationControllerApi;


GraphRagConfigurationControllerApi apiInstance = new GraphRagConfigurationControllerApi();
GraphRagExtractionConfig body = new GraphRagExtractionConfig(); // GraphRagExtractionConfig | 
try {
    GraphRagExtractionConfig result = apiInstance.saveGraphRagExtractionConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GraphRagConfigurationControllerApi#saveGraphRagExtractionConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GraphRagExtractionConfig**](GraphRagExtractionConfig.md)|  |

### Return type

[**GraphRagExtractionConfig**](GraphRagExtractionConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

