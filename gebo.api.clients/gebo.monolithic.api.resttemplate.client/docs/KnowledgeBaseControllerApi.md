# KnowledgeBaseControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteKnowledgeBase**](KnowledgeBaseControllerApi.md#deleteKnowledgeBase) | **POST** /api/admin/KnowledgeBaseController/deleteKnowledgeBase | 
[**findKnowledgeBaseByCode**](KnowledgeBaseControllerApi.md#findKnowledgeBaseByCode) | **GET** /api/admin/KnowledgeBaseController/findKnowledgeBaseByCode | 
[**findKnowledgeBasesByQbe**](KnowledgeBaseControllerApi.md#findKnowledgeBasesByQbe) | **POST** /api/admin/KnowledgeBaseController/findKnowledgeBasesByQbe | 
[**getChildKnowledgeBases**](KnowledgeBaseControllerApi.md#getChildKnowledgeBases) | **GET** /api/admin/KnowledgeBaseController/getChildKnowledgeBases | 
[**getKnowledgeBases**](KnowledgeBaseControllerApi.md#getKnowledgeBases) | **GET** /api/admin/KnowledgeBaseController/getKnowledgeBases | 
[**insertKnowledgeBase**](KnowledgeBaseControllerApi.md#insertKnowledgeBase) | **POST** /api/admin/KnowledgeBaseController/insertKnowledgeBase | 
[**updateKnowledgeBase**](KnowledgeBaseControllerApi.md#updateKnowledgeBase) | **POST** /api/admin/KnowledgeBaseController/updateKnowledgeBase | 

<a name="deleteKnowledgeBase"></a>
# **deleteKnowledgeBase**
> deleteKnowledgeBase(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.KnowledgeBaseControllerApi;


KnowledgeBaseControllerApi apiInstance = new KnowledgeBaseControllerApi();
GKnowledgeBase body = new GKnowledgeBase(); // GKnowledgeBase | 
try {
    apiInstance.deleteKnowledgeBase(body);
} catch (ApiException e) {
    System.err.println("Exception when calling KnowledgeBaseControllerApi#deleteKnowledgeBase");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GKnowledgeBase**](GKnowledgeBase.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="findKnowledgeBaseByCode"></a>
# **findKnowledgeBaseByCode**
> GKnowledgeBase findKnowledgeBaseByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.KnowledgeBaseControllerApi;


KnowledgeBaseControllerApi apiInstance = new KnowledgeBaseControllerApi();
String code = "code_example"; // String | 
try {
    GKnowledgeBase result = apiInstance.findKnowledgeBaseByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling KnowledgeBaseControllerApi#findKnowledgeBaseByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GKnowledgeBase**](GKnowledgeBase.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findKnowledgeBasesByQbe"></a>
# **findKnowledgeBasesByQbe**
> List&lt;GKnowledgeBase&gt; findKnowledgeBasesByQbe(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.KnowledgeBaseControllerApi;


KnowledgeBaseControllerApi apiInstance = new KnowledgeBaseControllerApi();
GKnowledgeBase body = new GKnowledgeBase(); // GKnowledgeBase | 
try {
    List<GKnowledgeBase> result = apiInstance.findKnowledgeBasesByQbe(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling KnowledgeBaseControllerApi#findKnowledgeBasesByQbe");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GKnowledgeBase**](GKnowledgeBase.md)|  |

### Return type

[**List&lt;GKnowledgeBase&gt;**](GKnowledgeBase.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getChildKnowledgeBases"></a>
# **getChildKnowledgeBases**
> List&lt;GKnowledgeBase&gt; getChildKnowledgeBases(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.KnowledgeBaseControllerApi;


KnowledgeBaseControllerApi apiInstance = new KnowledgeBaseControllerApi();
String code = "code_example"; // String | 
try {
    List<GKnowledgeBase> result = apiInstance.getChildKnowledgeBases(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling KnowledgeBaseControllerApi#getChildKnowledgeBases");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**List&lt;GKnowledgeBase&gt;**](GKnowledgeBase.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getKnowledgeBases"></a>
# **getKnowledgeBases**
> List&lt;GKnowledgeBase&gt; getKnowledgeBases()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.KnowledgeBaseControllerApi;


KnowledgeBaseControllerApi apiInstance = new KnowledgeBaseControllerApi();
try {
    List<GKnowledgeBase> result = apiInstance.getKnowledgeBases();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling KnowledgeBaseControllerApi#getKnowledgeBases");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GKnowledgeBase&gt;**](GKnowledgeBase.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="insertKnowledgeBase"></a>
# **insertKnowledgeBase**
> GKnowledgeBase insertKnowledgeBase(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.KnowledgeBaseControllerApi;


KnowledgeBaseControllerApi apiInstance = new KnowledgeBaseControllerApi();
GKnowledgeBase body = new GKnowledgeBase(); // GKnowledgeBase | 
try {
    GKnowledgeBase result = apiInstance.insertKnowledgeBase(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling KnowledgeBaseControllerApi#insertKnowledgeBase");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GKnowledgeBase**](GKnowledgeBase.md)|  |

### Return type

[**GKnowledgeBase**](GKnowledgeBase.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateKnowledgeBase"></a>
# **updateKnowledgeBase**
> GKnowledgeBase updateKnowledgeBase(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.KnowledgeBaseControllerApi;


KnowledgeBaseControllerApi apiInstance = new KnowledgeBaseControllerApi();
GKnowledgeBase body = new GKnowledgeBase(); // GKnowledgeBase | 
try {
    GKnowledgeBase result = apiInstance.updateKnowledgeBase(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling KnowledgeBaseControllerApi#updateKnowledgeBase");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GKnowledgeBase**](GKnowledgeBase.md)|  |

### Return type

[**GKnowledgeBase**](GKnowledgeBase.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

