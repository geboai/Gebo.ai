# KnowledgeBaseControllerApi

All URIs are relative to *http://localhost:13001/brain*

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
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.KnowledgeBaseControllerApi;


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
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.KnowledgeBaseControllerApi;


KnowledgeBaseControllerApi apiInstance = new KnowledgeBaseControllerApi();
Object code = null; // Object | 
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
 **code** | [**Object**](.md)|  |

### Return type

[**GKnowledgeBase**](GKnowledgeBase.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findKnowledgeBasesByQbe"></a>
# **findKnowledgeBasesByQbe**
> Object findKnowledgeBasesByQbe(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.KnowledgeBaseControllerApi;


KnowledgeBaseControllerApi apiInstance = new KnowledgeBaseControllerApi();
GKnowledgeBase body = new GKnowledgeBase(); // GKnowledgeBase | 
try {
    Object result = apiInstance.findKnowledgeBasesByQbe(body);
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

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getChildKnowledgeBases"></a>
# **getChildKnowledgeBases**
> Object getChildKnowledgeBases(code)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.KnowledgeBaseControllerApi;


KnowledgeBaseControllerApi apiInstance = new KnowledgeBaseControllerApi();
Object code = null; // Object | 
try {
    Object result = apiInstance.getChildKnowledgeBases(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling KnowledgeBaseControllerApi#getChildKnowledgeBases");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | [**Object**](.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getKnowledgeBases"></a>
# **getKnowledgeBases**
> Object getKnowledgeBases()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.KnowledgeBaseControllerApi;


KnowledgeBaseControllerApi apiInstance = new KnowledgeBaseControllerApi();
try {
    Object result = apiInstance.getKnowledgeBases();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling KnowledgeBaseControllerApi#getKnowledgeBases");
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

<a name="insertKnowledgeBase"></a>
# **insertKnowledgeBase**
> GKnowledgeBase insertKnowledgeBase(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.KnowledgeBaseControllerApi;


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
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.KnowledgeBaseControllerApi;


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

