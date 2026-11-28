# ZammadSystemsControllerApi

All URIs are relative to *http://localhost:13024/zammad*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteZammadEndpoint**](ZammadSystemsControllerApi.md#deleteZammadEndpoint) | **POST** /api/admin/ZammadSystemsController/deleteZammadEndpoint | 
[**deleteZammadSystem**](ZammadSystemsControllerApi.md#deleteZammadSystem) | **POST** /api/admin/ZammadSystemsController/deleteZammadSystem | 
[**fastZammadConfig**](ZammadSystemsControllerApi.md#fastZammadConfig) | **POST** /api/admin/ZammadSystemsController/fastZammadConfig | 
[**findZammadEndpointByCode**](ZammadSystemsControllerApi.md#findZammadEndpointByCode) | **GET** /api/admin/ZammadSystemsController/findZammadEndpointByCode | 
[**findZammadEndpointsByProject**](ZammadSystemsControllerApi.md#findZammadEndpointsByProject) | **GET** /api/admin/ZammadSystemsController/findZammadEndpointsByProject | 
[**findZammadEndpointsByQbe**](ZammadSystemsControllerApi.md#findZammadEndpointsByQbe) | **POST** /api/admin/ZammadSystemsController/findZammadEndpointsByQbe | 
[**findZammadSystemByCode**](ZammadSystemsControllerApi.md#findZammadSystemByCode) | **GET** /api/admin/ZammadSystemsController/findZammadSystemByCode | 
[**getZammadSystemType**](ZammadSystemsControllerApi.md#getZammadSystemType) | **GET** /api/admin/ZammadSystemsController/getZammadSystemType | 
[**getZammadSystems**](ZammadSystemsControllerApi.md#getZammadSystems) | **GET** /api/admin/ZammadSystemsController/getZammadSystems | 
[**insertZammadEndpoint**](ZammadSystemsControllerApi.md#insertZammadEndpoint) | **POST** /api/admin/ZammadSystemsController/insertZammadEndpoint | 
[**insertZammadSystem**](ZammadSystemsControllerApi.md#insertZammadSystem) | **POST** /api/admin/ZammadSystemsController/insertZammadSystem | 
[**publishZammadEndpoint**](ZammadSystemsControllerApi.md#publishZammadEndpoint) | **POST** /api/admin/ZammadSystemsController/publishZammadEndpoint | 
[**testZammadSystem**](ZammadSystemsControllerApi.md#testZammadSystem) | **POST** /api/admin/ZammadSystemsController/testZammadSystem | 
[**updateZammadEndpoint**](ZammadSystemsControllerApi.md#updateZammadEndpoint) | **POST** /api/admin/ZammadSystemsController/updateZammadEndpoint | 
[**updateZammadSystem**](ZammadSystemsControllerApi.md#updateZammadSystem) | **POST** /api/admin/ZammadSystemsController/updateZammadSystem | 

<a name="deleteZammadEndpoint"></a>
# **deleteZammadEndpoint**
> deleteZammadEndpoint(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.zammad.invoker.ApiException;
//import gebo.microservices.api.client.zammad.api.ZammadSystemsControllerApi;


ZammadSystemsControllerApi apiInstance = new ZammadSystemsControllerApi();
GZammadProjectEndpoint body = new GZammadProjectEndpoint(); // GZammadProjectEndpoint | 
try {
    apiInstance.deleteZammadEndpoint(body);
} catch (ApiException e) {
    System.err.println("Exception when calling ZammadSystemsControllerApi#deleteZammadEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GZammadProjectEndpoint**](GZammadProjectEndpoint.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="deleteZammadSystem"></a>
# **deleteZammadSystem**
> deleteZammadSystem(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.zammad.invoker.ApiException;
//import gebo.microservices.api.client.zammad.api.ZammadSystemsControllerApi;


ZammadSystemsControllerApi apiInstance = new ZammadSystemsControllerApi();
GZammadSystem body = new GZammadSystem(); // GZammadSystem | 
try {
    apiInstance.deleteZammadSystem(body);
} catch (ApiException e) {
    System.err.println("Exception when calling ZammadSystemsControllerApi#deleteZammadSystem");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GZammadSystem**](GZammadSystem.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="fastZammadConfig"></a>
# **fastZammadConfig**
> OperationStatusGZammadSystem fastZammadConfig(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.zammad.invoker.ApiException;
//import gebo.microservices.api.client.zammad.api.ZammadSystemsControllerApi;


ZammadSystemsControllerApi apiInstance = new ZammadSystemsControllerApi();
FastZammadSystemInsertRequest body = new FastZammadSystemInsertRequest(); // FastZammadSystemInsertRequest | 
try {
    OperationStatusGZammadSystem result = apiInstance.fastZammadConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ZammadSystemsControllerApi#fastZammadConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**FastZammadSystemInsertRequest**](FastZammadSystemInsertRequest.md)|  |

### Return type

[**OperationStatusGZammadSystem**](OperationStatusGZammadSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findZammadEndpointByCode"></a>
# **findZammadEndpointByCode**
> GZammadProjectEndpoint findZammadEndpointByCode(code)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.zammad.invoker.ApiException;
//import gebo.microservices.api.client.zammad.api.ZammadSystemsControllerApi;


ZammadSystemsControllerApi apiInstance = new ZammadSystemsControllerApi();
String code = "code_example"; // String | 
try {
    GZammadProjectEndpoint result = apiInstance.findZammadEndpointByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ZammadSystemsControllerApi#findZammadEndpointByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GZammadProjectEndpoint**](GZammadProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findZammadEndpointsByProject"></a>
# **findZammadEndpointsByProject**
> List&lt;GZammadProjectEndpoint&gt; findZammadEndpointsByProject(parentProjectCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.zammad.invoker.ApiException;
//import gebo.microservices.api.client.zammad.api.ZammadSystemsControllerApi;


ZammadSystemsControllerApi apiInstance = new ZammadSystemsControllerApi();
String parentProjectCode = "parentProjectCode_example"; // String | 
try {
    List<GZammadProjectEndpoint> result = apiInstance.findZammadEndpointsByProject(parentProjectCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ZammadSystemsControllerApi#findZammadEndpointsByProject");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **parentProjectCode** | **String**|  |

### Return type

[**List&lt;GZammadProjectEndpoint&gt;**](GZammadProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findZammadEndpointsByQbe"></a>
# **findZammadEndpointsByQbe**
> List&lt;GZammadProjectEndpoint&gt; findZammadEndpointsByQbe(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.zammad.invoker.ApiException;
//import gebo.microservices.api.client.zammad.api.ZammadSystemsControllerApi;


ZammadSystemsControllerApi apiInstance = new ZammadSystemsControllerApi();
GZammadProjectEndpoint body = new GZammadProjectEndpoint(); // GZammadProjectEndpoint | 
try {
    List<GZammadProjectEndpoint> result = apiInstance.findZammadEndpointsByQbe(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ZammadSystemsControllerApi#findZammadEndpointsByQbe");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GZammadProjectEndpoint**](GZammadProjectEndpoint.md)|  |

### Return type

[**List&lt;GZammadProjectEndpoint&gt;**](GZammadProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findZammadSystemByCode"></a>
# **findZammadSystemByCode**
> GZammadSystem findZammadSystemByCode(code)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.zammad.invoker.ApiException;
//import gebo.microservices.api.client.zammad.api.ZammadSystemsControllerApi;


ZammadSystemsControllerApi apiInstance = new ZammadSystemsControllerApi();
String code = "code_example"; // String | 
try {
    GZammadSystem result = apiInstance.findZammadSystemByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ZammadSystemsControllerApi#findZammadSystemByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GZammadSystem**](GZammadSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getZammadSystemType"></a>
# **getZammadSystemType**
> GContentManagementSystemType getZammadSystemType()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.zammad.invoker.ApiException;
//import gebo.microservices.api.client.zammad.api.ZammadSystemsControllerApi;


ZammadSystemsControllerApi apiInstance = new ZammadSystemsControllerApi();
try {
    GContentManagementSystemType result = apiInstance.getZammadSystemType();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ZammadSystemsControllerApi#getZammadSystemType");
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

<a name="getZammadSystems"></a>
# **getZammadSystems**
> List&lt;GZammadSystem&gt; getZammadSystems()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.zammad.invoker.ApiException;
//import gebo.microservices.api.client.zammad.api.ZammadSystemsControllerApi;


ZammadSystemsControllerApi apiInstance = new ZammadSystemsControllerApi();
try {
    List<GZammadSystem> result = apiInstance.getZammadSystems();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ZammadSystemsControllerApi#getZammadSystems");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GZammadSystem&gt;**](GZammadSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="insertZammadEndpoint"></a>
# **insertZammadEndpoint**
> GZammadProjectEndpoint insertZammadEndpoint(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.zammad.invoker.ApiException;
//import gebo.microservices.api.client.zammad.api.ZammadSystemsControllerApi;


ZammadSystemsControllerApi apiInstance = new ZammadSystemsControllerApi();
GZammadProjectEndpoint body = new GZammadProjectEndpoint(); // GZammadProjectEndpoint | 
try {
    GZammadProjectEndpoint result = apiInstance.insertZammadEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ZammadSystemsControllerApi#insertZammadEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GZammadProjectEndpoint**](GZammadProjectEndpoint.md)|  |

### Return type

[**GZammadProjectEndpoint**](GZammadProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertZammadSystem"></a>
# **insertZammadSystem**
> OperationStatusGZammadSystem insertZammadSystem(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.zammad.invoker.ApiException;
//import gebo.microservices.api.client.zammad.api.ZammadSystemsControllerApi;


ZammadSystemsControllerApi apiInstance = new ZammadSystemsControllerApi();
GZammadSystem body = new GZammadSystem(); // GZammadSystem | 
try {
    OperationStatusGZammadSystem result = apiInstance.insertZammadSystem(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ZammadSystemsControllerApi#insertZammadSystem");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GZammadSystem**](GZammadSystem.md)|  |

### Return type

[**OperationStatusGZammadSystem**](OperationStatusGZammadSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="publishZammadEndpoint"></a>
# **publishZammadEndpoint**
> OperationStatusGJobStatus publishZammadEndpoint(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.zammad.invoker.ApiException;
//import gebo.microservices.api.client.zammad.api.ZammadSystemsControllerApi;


ZammadSystemsControllerApi apiInstance = new ZammadSystemsControllerApi();
GZammadProjectEndpoint body = new GZammadProjectEndpoint(); // GZammadProjectEndpoint | 
try {
    OperationStatusGJobStatus result = apiInstance.publishZammadEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ZammadSystemsControllerApi#publishZammadEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GZammadProjectEndpoint**](GZammadProjectEndpoint.md)|  |

### Return type

[**OperationStatusGJobStatus**](OperationStatusGJobStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="testZammadSystem"></a>
# **testZammadSystem**
> OperationStatusGZammadSystem testZammadSystem(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.zammad.invoker.ApiException;
//import gebo.microservices.api.client.zammad.api.ZammadSystemsControllerApi;


ZammadSystemsControllerApi apiInstance = new ZammadSystemsControllerApi();
GZammadSystem body = new GZammadSystem(); // GZammadSystem | 
try {
    OperationStatusGZammadSystem result = apiInstance.testZammadSystem(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ZammadSystemsControllerApi#testZammadSystem");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GZammadSystem**](GZammadSystem.md)|  |

### Return type

[**OperationStatusGZammadSystem**](OperationStatusGZammadSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateZammadEndpoint"></a>
# **updateZammadEndpoint**
> GZammadProjectEndpoint updateZammadEndpoint(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.zammad.invoker.ApiException;
//import gebo.microservices.api.client.zammad.api.ZammadSystemsControllerApi;


ZammadSystemsControllerApi apiInstance = new ZammadSystemsControllerApi();
GZammadProjectEndpoint body = new GZammadProjectEndpoint(); // GZammadProjectEndpoint | 
try {
    GZammadProjectEndpoint result = apiInstance.updateZammadEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ZammadSystemsControllerApi#updateZammadEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GZammadProjectEndpoint**](GZammadProjectEndpoint.md)|  |

### Return type

[**GZammadProjectEndpoint**](GZammadProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateZammadSystem"></a>
# **updateZammadSystem**
> OperationStatusGZammadSystem updateZammadSystem(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.zammad.invoker.ApiException;
//import gebo.microservices.api.client.zammad.api.ZammadSystemsControllerApi;


ZammadSystemsControllerApi apiInstance = new ZammadSystemsControllerApi();
GZammadSystem body = new GZammadSystem(); // GZammadSystem | 
try {
    OperationStatusGZammadSystem result = apiInstance.updateZammadSystem(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ZammadSystemsControllerApi#updateZammadSystem");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GZammadSystem**](GZammadSystem.md)|  |

### Return type

[**OperationStatusGZammadSystem**](OperationStatusGZammadSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

