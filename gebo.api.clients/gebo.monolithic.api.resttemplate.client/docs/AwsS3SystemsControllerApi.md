# AwsS3SystemsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteAwsS3ProjectEndpoint**](AwsS3SystemsControllerApi.md#deleteAwsS3ProjectEndpoint) | **POST** /api/admin/AwsS3SystemsController/deleteAwsS3ProjectEndpoint | 
[**deleteAwsS3System**](AwsS3SystemsControllerApi.md#deleteAwsS3System) | **POST** /api/admin/AwsS3SystemsController/deleteAwsS3System | 
[**fastAwsS3Config**](AwsS3SystemsControllerApi.md#fastAwsS3Config) | **POST** /api/admin/AwsS3SystemsController/fastAwsS3Config | 
[**findAwsS3EndpointsByProject**](AwsS3SystemsControllerApi.md#findAwsS3EndpointsByProject) | **GET** /api/admin/AwsS3SystemsController/findAwsS3EndpointsByProject | 
[**findAwsS3EndpointsByQbe**](AwsS3SystemsControllerApi.md#findAwsS3EndpointsByQbe) | **POST** /api/admin/AwsS3SystemsController/findAwsS3EndpointsByQbe | 
[**findAwsS3ProjectEndpointByCode**](AwsS3SystemsControllerApi.md#findAwsS3ProjectEndpointByCode) | **GET** /api/admin/AwsS3SystemsController/findAwsS3ProjectEndpointByCode | 
[**findAwsS3SystemByCode**](AwsS3SystemsControllerApi.md#findAwsS3SystemByCode) | **GET** /api/admin/AwsS3SystemsController/findAwsS3SystemByCode | 
[**getAwsS3SystemType**](AwsS3SystemsControllerApi.md#getAwsS3SystemType) | **GET** /api/admin/AwsS3SystemsController/getAwsS3SystemType | 
[**getAwsS3Systems**](AwsS3SystemsControllerApi.md#getAwsS3Systems) | **GET** /api/admin/AwsS3SystemsController/getAwsS3Systems | 
[**insertAwsS3ProjectEndpoint**](AwsS3SystemsControllerApi.md#insertAwsS3ProjectEndpoint) | **POST** /api/admin/AwsS3SystemsController/insertAwsS3ProjectEndpoint | 
[**insertAwsS3System**](AwsS3SystemsControllerApi.md#insertAwsS3System) | **POST** /api/admin/AwsS3SystemsController/insertAwsS3System | 
[**updateAwsS3ProjectEndpoint**](AwsS3SystemsControllerApi.md#updateAwsS3ProjectEndpoint) | **POST** /api/admin/AwsS3SystemsController/updateAwsS3ProjectEndpoint | 
[**updateAwsS3System**](AwsS3SystemsControllerApi.md#updateAwsS3System) | **POST** /api/admin/AwsS3SystemsController/updateAwsS3System | 

<a name="deleteAwsS3ProjectEndpoint"></a>
# **deleteAwsS3ProjectEndpoint**
> deleteAwsS3ProjectEndpoint(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.AwsS3SystemsControllerApi;


AwsS3SystemsControllerApi apiInstance = new AwsS3SystemsControllerApi();
GAwsS3ProjectEndpoint body = new GAwsS3ProjectEndpoint(); // GAwsS3ProjectEndpoint | 
try {
    apiInstance.deleteAwsS3ProjectEndpoint(body);
} catch (ApiException e) {
    System.err.println("Exception when calling AwsS3SystemsControllerApi#deleteAwsS3ProjectEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAwsS3ProjectEndpoint**](GAwsS3ProjectEndpoint.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="deleteAwsS3System"></a>
# **deleteAwsS3System**
> deleteAwsS3System(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.AwsS3SystemsControllerApi;


AwsS3SystemsControllerApi apiInstance = new AwsS3SystemsControllerApi();
GAwsS3System body = new GAwsS3System(); // GAwsS3System | 
try {
    apiInstance.deleteAwsS3System(body);
} catch (ApiException e) {
    System.err.println("Exception when calling AwsS3SystemsControllerApi#deleteAwsS3System");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAwsS3System**](GAwsS3System.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="fastAwsS3Config"></a>
# **fastAwsS3Config**
> OperationStatusGAwsS3System fastAwsS3Config(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.AwsS3SystemsControllerApi;


AwsS3SystemsControllerApi apiInstance = new AwsS3SystemsControllerApi();
FastAwsS3SystemInsertRequest body = new FastAwsS3SystemInsertRequest(); // FastAwsS3SystemInsertRequest | 
try {
    OperationStatusGAwsS3System result = apiInstance.fastAwsS3Config(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AwsS3SystemsControllerApi#fastAwsS3Config");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**FastAwsS3SystemInsertRequest**](FastAwsS3SystemInsertRequest.md)|  |

### Return type

[**OperationStatusGAwsS3System**](OperationStatusGAwsS3System.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findAwsS3EndpointsByProject"></a>
# **findAwsS3EndpointsByProject**
> List&lt;GAwsS3ProjectEndpoint&gt; findAwsS3EndpointsByProject(parentProjectCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.AwsS3SystemsControllerApi;


AwsS3SystemsControllerApi apiInstance = new AwsS3SystemsControllerApi();
String parentProjectCode = "parentProjectCode_example"; // String | 
try {
    List<GAwsS3ProjectEndpoint> result = apiInstance.findAwsS3EndpointsByProject(parentProjectCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AwsS3SystemsControllerApi#findAwsS3EndpointsByProject");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **parentProjectCode** | **String**|  |

### Return type

[**List&lt;GAwsS3ProjectEndpoint&gt;**](GAwsS3ProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="findAwsS3EndpointsByQbe"></a>
# **findAwsS3EndpointsByQbe**
> List&lt;GAwsS3ProjectEndpoint&gt; findAwsS3EndpointsByQbe(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.AwsS3SystemsControllerApi;


AwsS3SystemsControllerApi apiInstance = new AwsS3SystemsControllerApi();
GAwsS3ProjectEndpoint body = new GAwsS3ProjectEndpoint(); // GAwsS3ProjectEndpoint | 
try {
    List<GAwsS3ProjectEndpoint> result = apiInstance.findAwsS3EndpointsByQbe(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AwsS3SystemsControllerApi#findAwsS3EndpointsByQbe");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAwsS3ProjectEndpoint**](GAwsS3ProjectEndpoint.md)|  |

### Return type

[**List&lt;GAwsS3ProjectEndpoint&gt;**](GAwsS3ProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findAwsS3ProjectEndpointByCode"></a>
# **findAwsS3ProjectEndpointByCode**
> GAwsS3ProjectEndpoint findAwsS3ProjectEndpointByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.AwsS3SystemsControllerApi;


AwsS3SystemsControllerApi apiInstance = new AwsS3SystemsControllerApi();
String code = "code_example"; // String | 
try {
    GAwsS3ProjectEndpoint result = apiInstance.findAwsS3ProjectEndpointByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AwsS3SystemsControllerApi#findAwsS3ProjectEndpointByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GAwsS3ProjectEndpoint**](GAwsS3ProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findAwsS3SystemByCode"></a>
# **findAwsS3SystemByCode**
> GAwsS3System findAwsS3SystemByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.AwsS3SystemsControllerApi;


AwsS3SystemsControllerApi apiInstance = new AwsS3SystemsControllerApi();
String code = "code_example"; // String | 
try {
    GAwsS3System result = apiInstance.findAwsS3SystemByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AwsS3SystemsControllerApi#findAwsS3SystemByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GAwsS3System**](GAwsS3System.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAwsS3SystemType"></a>
# **getAwsS3SystemType**
> GContentManagementSystemType getAwsS3SystemType()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.AwsS3SystemsControllerApi;


AwsS3SystemsControllerApi apiInstance = new AwsS3SystemsControllerApi();
try {
    GContentManagementSystemType result = apiInstance.getAwsS3SystemType();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AwsS3SystemsControllerApi#getAwsS3SystemType");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**GContentManagementSystemType**](GContentManagementSystemType.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAwsS3Systems"></a>
# **getAwsS3Systems**
> List&lt;GAwsS3System&gt; getAwsS3Systems()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.AwsS3SystemsControllerApi;


AwsS3SystemsControllerApi apiInstance = new AwsS3SystemsControllerApi();
try {
    List<GAwsS3System> result = apiInstance.getAwsS3Systems();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AwsS3SystemsControllerApi#getAwsS3Systems");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GAwsS3System&gt;**](GAwsS3System.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="insertAwsS3ProjectEndpoint"></a>
# **insertAwsS3ProjectEndpoint**
> GAwsS3ProjectEndpoint insertAwsS3ProjectEndpoint(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.AwsS3SystemsControllerApi;


AwsS3SystemsControllerApi apiInstance = new AwsS3SystemsControllerApi();
GAwsS3ProjectEndpoint body = new GAwsS3ProjectEndpoint(); // GAwsS3ProjectEndpoint | 
try {
    GAwsS3ProjectEndpoint result = apiInstance.insertAwsS3ProjectEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AwsS3SystemsControllerApi#insertAwsS3ProjectEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAwsS3ProjectEndpoint**](GAwsS3ProjectEndpoint.md)|  |

### Return type

[**GAwsS3ProjectEndpoint**](GAwsS3ProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertAwsS3System"></a>
# **insertAwsS3System**
> OperationStatusGAwsS3System insertAwsS3System(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.AwsS3SystemsControllerApi;


AwsS3SystemsControllerApi apiInstance = new AwsS3SystemsControllerApi();
GAwsS3System body = new GAwsS3System(); // GAwsS3System | 
try {
    OperationStatusGAwsS3System result = apiInstance.insertAwsS3System(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AwsS3SystemsControllerApi#insertAwsS3System");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAwsS3System**](GAwsS3System.md)|  |

### Return type

[**OperationStatusGAwsS3System**](OperationStatusGAwsS3System.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateAwsS3ProjectEndpoint"></a>
# **updateAwsS3ProjectEndpoint**
> GAwsS3ProjectEndpoint updateAwsS3ProjectEndpoint(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.AwsS3SystemsControllerApi;


AwsS3SystemsControllerApi apiInstance = new AwsS3SystemsControllerApi();
GAwsS3ProjectEndpoint body = new GAwsS3ProjectEndpoint(); // GAwsS3ProjectEndpoint | 
try {
    GAwsS3ProjectEndpoint result = apiInstance.updateAwsS3ProjectEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AwsS3SystemsControllerApi#updateAwsS3ProjectEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAwsS3ProjectEndpoint**](GAwsS3ProjectEndpoint.md)|  |

### Return type

[**GAwsS3ProjectEndpoint**](GAwsS3ProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateAwsS3System"></a>
# **updateAwsS3System**
> OperationStatusGAwsS3System updateAwsS3System(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.AwsS3SystemsControllerApi;


AwsS3SystemsControllerApi apiInstance = new AwsS3SystemsControllerApi();
GAwsS3System body = new GAwsS3System(); // GAwsS3System | 
try {
    OperationStatusGAwsS3System result = apiInstance.updateAwsS3System(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AwsS3SystemsControllerApi#updateAwsS3System");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAwsS3System**](GAwsS3System.md)|  |

### Return type

[**OperationStatusGAwsS3System**](OperationStatusGAwsS3System.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

