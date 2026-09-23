# GraphRagConfigurationControllerApi

All URIs are relative to *http://localhost:12999*

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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GraphRagConfigurationControllerApi;


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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GraphRagConfigurationControllerApi;


GraphRagConfigurationControllerApi apiInstance = new GraphRagConfigurationControllerApi();
String code = "code_example"; // String | 
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
 **code** | **String**|  |

### Return type

[**GraphRagExtractionConfig**](GraphRagExtractionConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findGraphRagExtractionConfigByKnowledgeBase"></a>
# **findGraphRagExtractionConfigByKnowledgeBase**
> List&lt;GraphRagExtractionConfig&gt; findGraphRagExtractionConfigByKnowledgeBase(knowledgeBaseCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GraphRagConfigurationControllerApi;


GraphRagConfigurationControllerApi apiInstance = new GraphRagConfigurationControllerApi();
String knowledgeBaseCode = "knowledgeBaseCode_example"; // String | 
try {
    List<GraphRagExtractionConfig> result = apiInstance.findGraphRagExtractionConfigByKnowledgeBase(knowledgeBaseCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GraphRagConfigurationControllerApi#findGraphRagExtractionConfigByKnowledgeBase");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **knowledgeBaseCode** | **String**|  |

### Return type

[**List&lt;GraphRagExtractionConfig&gt;**](GraphRagExtractionConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findGraphRagExtractionConfigByKnowledgeBaseAndProjectCode"></a>
# **findGraphRagExtractionConfigByKnowledgeBaseAndProjectCode**
> List&lt;GraphRagExtractionConfig&gt; findGraphRagExtractionConfigByKnowledgeBaseAndProjectCode(knowledgeBaseCode, projectCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GraphRagConfigurationControllerApi;


GraphRagConfigurationControllerApi apiInstance = new GraphRagConfigurationControllerApi();
String knowledgeBaseCode = "knowledgeBaseCode_example"; // String | 
String projectCode = "projectCode_example"; // String | 
try {
    List<GraphRagExtractionConfig> result = apiInstance.findGraphRagExtractionConfigByKnowledgeBaseAndProjectCode(knowledgeBaseCode, projectCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GraphRagConfigurationControllerApi#findGraphRagExtractionConfigByKnowledgeBaseAndProjectCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **knowledgeBaseCode** | **String**|  |
 **projectCode** | **String**|  |

### Return type

[**List&lt;GraphRagExtractionConfig&gt;**](GraphRagExtractionConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findGraphRagExtractionConfigByProjectEndpointGObjectRef"></a>
# **findGraphRagExtractionConfigByProjectEndpointGObjectRef**
> List&lt;GraphRagExtractionConfig&gt; findGraphRagExtractionConfigByProjectEndpointGObjectRef(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GraphRagConfigurationControllerApi;


GraphRagConfigurationControllerApi apiInstance = new GraphRagConfigurationControllerApi();
GObjectRefGProjectEndpoint body = new GObjectRefGProjectEndpoint(); // GObjectRefGProjectEndpoint | 
try {
    List<GraphRagExtractionConfig> result = apiInstance.findGraphRagExtractionConfigByProjectEndpointGObjectRef(body);
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

[**List&lt;GraphRagExtractionConfig&gt;**](GraphRagExtractionConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getDefaultGraphRagExtractionConfig"></a>
# **getDefaultGraphRagExtractionConfig**
> List&lt;GraphRagExtractionConfig&gt; getDefaultGraphRagExtractionConfig()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GraphRagConfigurationControllerApi;


GraphRagConfigurationControllerApi apiInstance = new GraphRagConfigurationControllerApi();
try {
    List<GraphRagExtractionConfig> result = apiInstance.getDefaultGraphRagExtractionConfig();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GraphRagConfigurationControllerApi#getDefaultGraphRagExtractionConfig");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GraphRagExtractionConfig&gt;**](GraphRagExtractionConfig.md)

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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GraphRagConfigurationControllerApi;


GraphRagConfigurationControllerApi apiInstance = new GraphRagConfigurationControllerApi();
String format = "format_example"; // String | 
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
 **format** | **String**|  | [enum: JSON, CSV]

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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GraphRagConfigurationControllerApi;


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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GraphRagConfigurationControllerApi;


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

