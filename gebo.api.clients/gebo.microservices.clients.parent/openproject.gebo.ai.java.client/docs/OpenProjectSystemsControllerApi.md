# OpenProjectSystemsControllerApi

All URIs are relative to *http://localhost:13023/openproject*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteOpenProjectEndpoint**](OpenProjectSystemsControllerApi.md#deleteOpenProjectEndpoint) | **POST** /api/admin/OpenProjectSystemsController/deleteOpenProjectEndpoint | 
[**deleteOpenProjectSystem**](OpenProjectSystemsControllerApi.md#deleteOpenProjectSystem) | **POST** /api/admin/OpenProjectSystemsController/deleteOpenProjectSystem | 
[**fastOpenProjectConfig**](OpenProjectSystemsControllerApi.md#fastOpenProjectConfig) | **POST** /api/admin/OpenProjectSystemsController/fastOpenProjectConfig | 
[**findOpenProjectEndpointByCode**](OpenProjectSystemsControllerApi.md#findOpenProjectEndpointByCode) | **GET** /api/admin/OpenProjectSystemsController/findOpenProjectEndpointByCode | 
[**findOpenProjectEndpointsByProject**](OpenProjectSystemsControllerApi.md#findOpenProjectEndpointsByProject) | **GET** /api/admin/OpenProjectSystemsController/findOpenProjectEndpointsByProject | 
[**findOpenProjectEndpointsByQbe**](OpenProjectSystemsControllerApi.md#findOpenProjectEndpointsByQbe) | **POST** /api/admin/OpenProjectSystemsController/findOpenProjectEndpointsByQbe | 
[**findOpenProjectSystemByCode**](OpenProjectSystemsControllerApi.md#findOpenProjectSystemByCode) | **GET** /api/admin/OpenProjectSystemsController/findOpenProjectSystemByCode | 
[**getOpenProjectSystemType**](OpenProjectSystemsControllerApi.md#getOpenProjectSystemType) | **GET** /api/admin/OpenProjectSystemsController/getOpenProjectSystemType | 
[**getOpenProjectSystems**](OpenProjectSystemsControllerApi.md#getOpenProjectSystems) | **GET** /api/admin/OpenProjectSystemsController/getOpenProjectSystems | 
[**insertOpenProjectEndpoint**](OpenProjectSystemsControllerApi.md#insertOpenProjectEndpoint) | **POST** /api/admin/OpenProjectSystemsController/insertOpenProjectEndpoint | 
[**insertOpenProjectSystem**](OpenProjectSystemsControllerApi.md#insertOpenProjectSystem) | **POST** /api/admin/OpenProjectSystemsController/insertOpenProjectSystem | 
[**publishOpenProjectEndpoint**](OpenProjectSystemsControllerApi.md#publishOpenProjectEndpoint) | **POST** /api/admin/OpenProjectSystemsController/publishOpenProjectEndpoint | 
[**testOpenProjectSystem**](OpenProjectSystemsControllerApi.md#testOpenProjectSystem) | **POST** /api/admin/OpenProjectSystemsController/testOpenProjectSystem | 
[**updateOpenProjectEndpoint**](OpenProjectSystemsControllerApi.md#updateOpenProjectEndpoint) | **POST** /api/admin/OpenProjectSystemsController/updateOpenProjectEndpoint | 
[**updateOpenProjectSystem**](OpenProjectSystemsControllerApi.md#updateOpenProjectSystem) | **POST** /api/admin/OpenProjectSystemsController/updateOpenProjectSystem | 

<a name="deleteOpenProjectEndpoint"></a>
# **deleteOpenProjectEndpoint**
> deleteOpenProjectEndpoint(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.openproject.invoker.ApiException;
//import gebo.microservices.api.client.openproject.api.OpenProjectSystemsControllerApi;


OpenProjectSystemsControllerApi apiInstance = new OpenProjectSystemsControllerApi();
GOpenProjectProjectEndpoint body = new GOpenProjectProjectEndpoint(); // GOpenProjectProjectEndpoint | 
try {
    apiInstance.deleteOpenProjectEndpoint(body);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenProjectSystemsControllerApi#deleteOpenProjectEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenProjectProjectEndpoint**](GOpenProjectProjectEndpoint.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="deleteOpenProjectSystem"></a>
# **deleteOpenProjectSystem**
> deleteOpenProjectSystem(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.openproject.invoker.ApiException;
//import gebo.microservices.api.client.openproject.api.OpenProjectSystemsControllerApi;


OpenProjectSystemsControllerApi apiInstance = new OpenProjectSystemsControllerApi();
GOpenProjectSystem body = new GOpenProjectSystem(); // GOpenProjectSystem | 
try {
    apiInstance.deleteOpenProjectSystem(body);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenProjectSystemsControllerApi#deleteOpenProjectSystem");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenProjectSystem**](GOpenProjectSystem.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="fastOpenProjectConfig"></a>
# **fastOpenProjectConfig**
> OperationStatusGOpenProjectSystem fastOpenProjectConfig(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.openproject.invoker.ApiException;
//import gebo.microservices.api.client.openproject.api.OpenProjectSystemsControllerApi;


OpenProjectSystemsControllerApi apiInstance = new OpenProjectSystemsControllerApi();
FastOpenProjectSystemInsertRequest body = new FastOpenProjectSystemInsertRequest(); // FastOpenProjectSystemInsertRequest | 
try {
    OperationStatusGOpenProjectSystem result = apiInstance.fastOpenProjectConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenProjectSystemsControllerApi#fastOpenProjectConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**FastOpenProjectSystemInsertRequest**](FastOpenProjectSystemInsertRequest.md)|  |

### Return type

[**OperationStatusGOpenProjectSystem**](OperationStatusGOpenProjectSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findOpenProjectEndpointByCode"></a>
# **findOpenProjectEndpointByCode**
> GOpenProjectProjectEndpoint findOpenProjectEndpointByCode(code)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.openproject.invoker.ApiException;
//import gebo.microservices.api.client.openproject.api.OpenProjectSystemsControllerApi;


OpenProjectSystemsControllerApi apiInstance = new OpenProjectSystemsControllerApi();
String code = "code_example"; // String | 
try {
    GOpenProjectProjectEndpoint result = apiInstance.findOpenProjectEndpointByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenProjectSystemsControllerApi#findOpenProjectEndpointByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GOpenProjectProjectEndpoint**](GOpenProjectProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findOpenProjectEndpointsByProject"></a>
# **findOpenProjectEndpointsByProject**
> List&lt;GOpenProjectProjectEndpoint&gt; findOpenProjectEndpointsByProject(parentProjectCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.openproject.invoker.ApiException;
//import gebo.microservices.api.client.openproject.api.OpenProjectSystemsControllerApi;


OpenProjectSystemsControllerApi apiInstance = new OpenProjectSystemsControllerApi();
String parentProjectCode = "parentProjectCode_example"; // String | 
try {
    List<GOpenProjectProjectEndpoint> result = apiInstance.findOpenProjectEndpointsByProject(parentProjectCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenProjectSystemsControllerApi#findOpenProjectEndpointsByProject");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **parentProjectCode** | **String**|  |

### Return type

[**List&lt;GOpenProjectProjectEndpoint&gt;**](GOpenProjectProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findOpenProjectEndpointsByQbe"></a>
# **findOpenProjectEndpointsByQbe**
> List&lt;GOpenProjectProjectEndpoint&gt; findOpenProjectEndpointsByQbe(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.openproject.invoker.ApiException;
//import gebo.microservices.api.client.openproject.api.OpenProjectSystemsControllerApi;


OpenProjectSystemsControllerApi apiInstance = new OpenProjectSystemsControllerApi();
GOpenProjectProjectEndpoint body = new GOpenProjectProjectEndpoint(); // GOpenProjectProjectEndpoint | 
try {
    List<GOpenProjectProjectEndpoint> result = apiInstance.findOpenProjectEndpointsByQbe(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenProjectSystemsControllerApi#findOpenProjectEndpointsByQbe");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenProjectProjectEndpoint**](GOpenProjectProjectEndpoint.md)|  |

### Return type

[**List&lt;GOpenProjectProjectEndpoint&gt;**](GOpenProjectProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findOpenProjectSystemByCode"></a>
# **findOpenProjectSystemByCode**
> GOpenProjectSystem findOpenProjectSystemByCode(code)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.openproject.invoker.ApiException;
//import gebo.microservices.api.client.openproject.api.OpenProjectSystemsControllerApi;


OpenProjectSystemsControllerApi apiInstance = new OpenProjectSystemsControllerApi();
String code = "code_example"; // String | 
try {
    GOpenProjectSystem result = apiInstance.findOpenProjectSystemByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenProjectSystemsControllerApi#findOpenProjectSystemByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GOpenProjectSystem**](GOpenProjectSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getOpenProjectSystemType"></a>
# **getOpenProjectSystemType**
> GContentManagementSystemType getOpenProjectSystemType()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.openproject.invoker.ApiException;
//import gebo.microservices.api.client.openproject.api.OpenProjectSystemsControllerApi;


OpenProjectSystemsControllerApi apiInstance = new OpenProjectSystemsControllerApi();
try {
    GContentManagementSystemType result = apiInstance.getOpenProjectSystemType();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenProjectSystemsControllerApi#getOpenProjectSystemType");
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

<a name="getOpenProjectSystems"></a>
# **getOpenProjectSystems**
> List&lt;GOpenProjectSystem&gt; getOpenProjectSystems()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.openproject.invoker.ApiException;
//import gebo.microservices.api.client.openproject.api.OpenProjectSystemsControllerApi;


OpenProjectSystemsControllerApi apiInstance = new OpenProjectSystemsControllerApi();
try {
    List<GOpenProjectSystem> result = apiInstance.getOpenProjectSystems();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenProjectSystemsControllerApi#getOpenProjectSystems");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GOpenProjectSystem&gt;**](GOpenProjectSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="insertOpenProjectEndpoint"></a>
# **insertOpenProjectEndpoint**
> GOpenProjectProjectEndpoint insertOpenProjectEndpoint(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.openproject.invoker.ApiException;
//import gebo.microservices.api.client.openproject.api.OpenProjectSystemsControllerApi;


OpenProjectSystemsControllerApi apiInstance = new OpenProjectSystemsControllerApi();
GOpenProjectProjectEndpoint body = new GOpenProjectProjectEndpoint(); // GOpenProjectProjectEndpoint | 
try {
    GOpenProjectProjectEndpoint result = apiInstance.insertOpenProjectEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenProjectSystemsControllerApi#insertOpenProjectEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenProjectProjectEndpoint**](GOpenProjectProjectEndpoint.md)|  |

### Return type

[**GOpenProjectProjectEndpoint**](GOpenProjectProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertOpenProjectSystem"></a>
# **insertOpenProjectSystem**
> OperationStatusGOpenProjectSystem insertOpenProjectSystem(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.openproject.invoker.ApiException;
//import gebo.microservices.api.client.openproject.api.OpenProjectSystemsControllerApi;


OpenProjectSystemsControllerApi apiInstance = new OpenProjectSystemsControllerApi();
GOpenProjectSystem body = new GOpenProjectSystem(); // GOpenProjectSystem | 
try {
    OperationStatusGOpenProjectSystem result = apiInstance.insertOpenProjectSystem(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenProjectSystemsControllerApi#insertOpenProjectSystem");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenProjectSystem**](GOpenProjectSystem.md)|  |

### Return type

[**OperationStatusGOpenProjectSystem**](OperationStatusGOpenProjectSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="publishOpenProjectEndpoint"></a>
# **publishOpenProjectEndpoint**
> OperationStatusGJobStatus publishOpenProjectEndpoint(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.openproject.invoker.ApiException;
//import gebo.microservices.api.client.openproject.api.OpenProjectSystemsControllerApi;


OpenProjectSystemsControllerApi apiInstance = new OpenProjectSystemsControllerApi();
GOpenProjectProjectEndpoint body = new GOpenProjectProjectEndpoint(); // GOpenProjectProjectEndpoint | 
try {
    OperationStatusGJobStatus result = apiInstance.publishOpenProjectEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenProjectSystemsControllerApi#publishOpenProjectEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenProjectProjectEndpoint**](GOpenProjectProjectEndpoint.md)|  |

### Return type

[**OperationStatusGJobStatus**](OperationStatusGJobStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="testOpenProjectSystem"></a>
# **testOpenProjectSystem**
> OperationStatusGOpenProjectSystem testOpenProjectSystem(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.openproject.invoker.ApiException;
//import gebo.microservices.api.client.openproject.api.OpenProjectSystemsControllerApi;


OpenProjectSystemsControllerApi apiInstance = new OpenProjectSystemsControllerApi();
GOpenProjectSystem body = new GOpenProjectSystem(); // GOpenProjectSystem | 
try {
    OperationStatusGOpenProjectSystem result = apiInstance.testOpenProjectSystem(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenProjectSystemsControllerApi#testOpenProjectSystem");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenProjectSystem**](GOpenProjectSystem.md)|  |

### Return type

[**OperationStatusGOpenProjectSystem**](OperationStatusGOpenProjectSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateOpenProjectEndpoint"></a>
# **updateOpenProjectEndpoint**
> GOpenProjectProjectEndpoint updateOpenProjectEndpoint(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.openproject.invoker.ApiException;
//import gebo.microservices.api.client.openproject.api.OpenProjectSystemsControllerApi;


OpenProjectSystemsControllerApi apiInstance = new OpenProjectSystemsControllerApi();
GOpenProjectProjectEndpoint body = new GOpenProjectProjectEndpoint(); // GOpenProjectProjectEndpoint | 
try {
    GOpenProjectProjectEndpoint result = apiInstance.updateOpenProjectEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenProjectSystemsControllerApi#updateOpenProjectEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenProjectProjectEndpoint**](GOpenProjectProjectEndpoint.md)|  |

### Return type

[**GOpenProjectProjectEndpoint**](GOpenProjectProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateOpenProjectSystem"></a>
# **updateOpenProjectSystem**
> OperationStatusGOpenProjectSystem updateOpenProjectSystem(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.openproject.invoker.ApiException;
//import gebo.microservices.api.client.openproject.api.OpenProjectSystemsControllerApi;


OpenProjectSystemsControllerApi apiInstance = new OpenProjectSystemsControllerApi();
GOpenProjectSystem body = new GOpenProjectSystem(); // GOpenProjectSystem | 
try {
    OperationStatusGOpenProjectSystem result = apiInstance.updateOpenProjectSystem(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OpenProjectSystemsControllerApi#updateOpenProjectSystem");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GOpenProjectSystem**](GOpenProjectSystem.md)|  |

### Return type

[**OperationStatusGOpenProjectSystem**](OperationStatusGOpenProjectSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

