# GlpiSystemsControllerApi

All URIs are relative to *http://localhost:13999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteGlpiEndpoint**](GlpiSystemsControllerApi.md#deleteGlpiEndpoint) | **POST** /api/admin/GlpiSystemsController/deleteGlpiEndpoint | 
[**deleteGlpiSystem**](GlpiSystemsControllerApi.md#deleteGlpiSystem) | **POST** /api/admin/GlpiSystemsController/deleteGlpiSystem | 
[**fastGlpiConfig**](GlpiSystemsControllerApi.md#fastGlpiConfig) | **POST** /api/admin/GlpiSystemsController/fastGlpiConfig | 
[**findGlpiEndpointsByCode**](GlpiSystemsControllerApi.md#findGlpiEndpointsByCode) | **GET** /api/admin/GlpiSystemsController/findGlpiEndpointsByCode | 
[**findGlpiEndpointsByProject**](GlpiSystemsControllerApi.md#findGlpiEndpointsByProject) | **GET** /api/admin/GlpiSystemsController/findGlpiEndpointsByProject | 
[**findGlpiEndpointsByQbe**](GlpiSystemsControllerApi.md#findGlpiEndpointsByQbe) | **POST** /api/admin/GlpiSystemsController/findGlpiEndpointsByQbe | 
[**findGlpiSystemByCode**](GlpiSystemsControllerApi.md#findGlpiSystemByCode) | **GET** /api/admin/GlpiSystemsController/findGlpiSystemByCode | 
[**getGlpiSystemType**](GlpiSystemsControllerApi.md#getGlpiSystemType) | **GET** /api/admin/GlpiSystemsController/getGlpiSystemType | 
[**getGlpiSystems**](GlpiSystemsControllerApi.md#getGlpiSystems) | **GET** /api/admin/GlpiSystemsController/getGlpiSystems | 
[**insertGlpiEndpoint**](GlpiSystemsControllerApi.md#insertGlpiEndpoint) | **POST** /api/admin/GlpiSystemsController/insertGlpiEndpoint | 
[**insertGlpiSystem**](GlpiSystemsControllerApi.md#insertGlpiSystem) | **POST** /api/admin/GlpiSystemsController/insertGlpiSystem | 
[**publishGlpiEndpoint**](GlpiSystemsControllerApi.md#publishGlpiEndpoint) | **POST** /api/admin/GlpiSystemsController/publishGlpiEndpoint | 
[**testGlpiSystem**](GlpiSystemsControllerApi.md#testGlpiSystem) | **POST** /api/admin/GlpiSystemsController/testGlpiSystem | 
[**updateGlpiEndpoint**](GlpiSystemsControllerApi.md#updateGlpiEndpoint) | **POST** /api/admin/GlpiSystemsController/updateGlpiEndpoint | 
[**updateGlpiSystem**](GlpiSystemsControllerApi.md#updateGlpiSystem) | **POST** /api/admin/GlpiSystemsController/updateGlpiSystem | 

<a name="deleteGlpiEndpoint"></a>
# **deleteGlpiEndpoint**
> deleteGlpiEndpoint(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GlpiSystemsControllerApi;


GlpiSystemsControllerApi apiInstance = new GlpiSystemsControllerApi();
GGlpiProjectEndpoint body = new GGlpiProjectEndpoint(); // GGlpiProjectEndpoint | 
try {
    apiInstance.deleteGlpiEndpoint(body);
} catch (ApiException e) {
    System.err.println("Exception when calling GlpiSystemsControllerApi#deleteGlpiEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGlpiProjectEndpoint**](GGlpiProjectEndpoint.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="deleteGlpiSystem"></a>
# **deleteGlpiSystem**
> deleteGlpiSystem(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GlpiSystemsControllerApi;


GlpiSystemsControllerApi apiInstance = new GlpiSystemsControllerApi();
GGlpiSystem body = new GGlpiSystem(); // GGlpiSystem | 
try {
    apiInstance.deleteGlpiSystem(body);
} catch (ApiException e) {
    System.err.println("Exception when calling GlpiSystemsControllerApi#deleteGlpiSystem");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGlpiSystem**](GGlpiSystem.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="fastGlpiConfig"></a>
# **fastGlpiConfig**
> OperationStatusGGlpiSystem fastGlpiConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GlpiSystemsControllerApi;


GlpiSystemsControllerApi apiInstance = new GlpiSystemsControllerApi();
FastGlpiSystemInsertRequest body = new FastGlpiSystemInsertRequest(); // FastGlpiSystemInsertRequest | 
try {
    OperationStatusGGlpiSystem result = apiInstance.fastGlpiConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GlpiSystemsControllerApi#fastGlpiConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**FastGlpiSystemInsertRequest**](FastGlpiSystemInsertRequest.md)|  |

### Return type

[**OperationStatusGGlpiSystem**](OperationStatusGGlpiSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findGlpiEndpointsByCode"></a>
# **findGlpiEndpointsByCode**
> GGlpiProjectEndpoint findGlpiEndpointsByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GlpiSystemsControllerApi;


GlpiSystemsControllerApi apiInstance = new GlpiSystemsControllerApi();
String code = "code_example"; // String | 
try {
    GGlpiProjectEndpoint result = apiInstance.findGlpiEndpointsByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GlpiSystemsControllerApi#findGlpiEndpointsByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GGlpiProjectEndpoint**](GGlpiProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findGlpiEndpointsByProject"></a>
# **findGlpiEndpointsByProject**
> List&lt;GGlpiProjectEndpoint&gt; findGlpiEndpointsByProject(parentProjectCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GlpiSystemsControllerApi;


GlpiSystemsControllerApi apiInstance = new GlpiSystemsControllerApi();
String parentProjectCode = "parentProjectCode_example"; // String | 
try {
    List<GGlpiProjectEndpoint> result = apiInstance.findGlpiEndpointsByProject(parentProjectCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GlpiSystemsControllerApi#findGlpiEndpointsByProject");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **parentProjectCode** | **String**|  |

### Return type

[**List&lt;GGlpiProjectEndpoint&gt;**](GGlpiProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findGlpiEndpointsByQbe"></a>
# **findGlpiEndpointsByQbe**
> List&lt;GGlpiProjectEndpoint&gt; findGlpiEndpointsByQbe(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GlpiSystemsControllerApi;


GlpiSystemsControllerApi apiInstance = new GlpiSystemsControllerApi();
GGlpiProjectEndpoint body = new GGlpiProjectEndpoint(); // GGlpiProjectEndpoint | 
try {
    List<GGlpiProjectEndpoint> result = apiInstance.findGlpiEndpointsByQbe(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GlpiSystemsControllerApi#findGlpiEndpointsByQbe");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGlpiProjectEndpoint**](GGlpiProjectEndpoint.md)|  |

### Return type

[**List&lt;GGlpiProjectEndpoint&gt;**](GGlpiProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findGlpiSystemByCode"></a>
# **findGlpiSystemByCode**
> GGlpiSystem findGlpiSystemByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GlpiSystemsControllerApi;


GlpiSystemsControllerApi apiInstance = new GlpiSystemsControllerApi();
String code = "code_example"; // String | 
try {
    GGlpiSystem result = apiInstance.findGlpiSystemByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GlpiSystemsControllerApi#findGlpiSystemByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GGlpiSystem**](GGlpiSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getGlpiSystemType"></a>
# **getGlpiSystemType**
> GContentManagementSystemType getGlpiSystemType()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GlpiSystemsControllerApi;


GlpiSystemsControllerApi apiInstance = new GlpiSystemsControllerApi();
try {
    GContentManagementSystemType result = apiInstance.getGlpiSystemType();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GlpiSystemsControllerApi#getGlpiSystemType");
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

<a name="getGlpiSystems"></a>
# **getGlpiSystems**
> List&lt;GGlpiSystem&gt; getGlpiSystems()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GlpiSystemsControllerApi;


GlpiSystemsControllerApi apiInstance = new GlpiSystemsControllerApi();
try {
    List<GGlpiSystem> result = apiInstance.getGlpiSystems();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GlpiSystemsControllerApi#getGlpiSystems");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GGlpiSystem&gt;**](GGlpiSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="insertGlpiEndpoint"></a>
# **insertGlpiEndpoint**
> GGlpiProjectEndpoint insertGlpiEndpoint(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GlpiSystemsControllerApi;


GlpiSystemsControllerApi apiInstance = new GlpiSystemsControllerApi();
GGlpiProjectEndpoint body = new GGlpiProjectEndpoint(); // GGlpiProjectEndpoint | 
try {
    GGlpiProjectEndpoint result = apiInstance.insertGlpiEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GlpiSystemsControllerApi#insertGlpiEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGlpiProjectEndpoint**](GGlpiProjectEndpoint.md)|  |

### Return type

[**GGlpiProjectEndpoint**](GGlpiProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertGlpiSystem"></a>
# **insertGlpiSystem**
> OperationStatusGGlpiSystem insertGlpiSystem(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GlpiSystemsControllerApi;


GlpiSystemsControllerApi apiInstance = new GlpiSystemsControllerApi();
GGlpiSystem body = new GGlpiSystem(); // GGlpiSystem | 
try {
    OperationStatusGGlpiSystem result = apiInstance.insertGlpiSystem(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GlpiSystemsControllerApi#insertGlpiSystem");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGlpiSystem**](GGlpiSystem.md)|  |

### Return type

[**OperationStatusGGlpiSystem**](OperationStatusGGlpiSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="publishGlpiEndpoint"></a>
# **publishGlpiEndpoint**
> OperationStatusGJobStatus publishGlpiEndpoint(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GlpiSystemsControllerApi;


GlpiSystemsControllerApi apiInstance = new GlpiSystemsControllerApi();
GGlpiProjectEndpoint body = new GGlpiProjectEndpoint(); // GGlpiProjectEndpoint | 
try {
    OperationStatusGJobStatus result = apiInstance.publishGlpiEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GlpiSystemsControllerApi#publishGlpiEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGlpiProjectEndpoint**](GGlpiProjectEndpoint.md)|  |

### Return type

[**OperationStatusGJobStatus**](OperationStatusGJobStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="testGlpiSystem"></a>
# **testGlpiSystem**
> OperationStatusGGlpiSystem testGlpiSystem(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GlpiSystemsControllerApi;


GlpiSystemsControllerApi apiInstance = new GlpiSystemsControllerApi();
GGlpiSystem body = new GGlpiSystem(); // GGlpiSystem | 
try {
    OperationStatusGGlpiSystem result = apiInstance.testGlpiSystem(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GlpiSystemsControllerApi#testGlpiSystem");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGlpiSystem**](GGlpiSystem.md)|  |

### Return type

[**OperationStatusGGlpiSystem**](OperationStatusGGlpiSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateGlpiEndpoint"></a>
# **updateGlpiEndpoint**
> GGlpiProjectEndpoint updateGlpiEndpoint(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GlpiSystemsControllerApi;


GlpiSystemsControllerApi apiInstance = new GlpiSystemsControllerApi();
GGlpiProjectEndpoint body = new GGlpiProjectEndpoint(); // GGlpiProjectEndpoint | 
try {
    GGlpiProjectEndpoint result = apiInstance.updateGlpiEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GlpiSystemsControllerApi#updateGlpiEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGlpiProjectEndpoint**](GGlpiProjectEndpoint.md)|  |

### Return type

[**GGlpiProjectEndpoint**](GGlpiProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateGlpiSystem"></a>
# **updateGlpiSystem**
> OperationStatusGGlpiSystem updateGlpiSystem(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GlpiSystemsControllerApi;


GlpiSystemsControllerApi apiInstance = new GlpiSystemsControllerApi();
GGlpiSystem body = new GGlpiSystem(); // GGlpiSystem | 
try {
    OperationStatusGGlpiSystem result = apiInstance.updateGlpiSystem(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GlpiSystemsControllerApi#updateGlpiSystem");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGlpiSystem**](GGlpiSystem.md)|  |

### Return type

[**OperationStatusGGlpiSystem**](OperationStatusGGlpiSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

