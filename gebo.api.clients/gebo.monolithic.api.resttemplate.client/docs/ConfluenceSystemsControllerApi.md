# ConfluenceSystemsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteConfluenceEndpoint**](ConfluenceSystemsControllerApi.md#deleteConfluenceEndpoint) | **POST** /api/admin/ConfluenceSystemsController/deleteConfluenceEndpoint | 
[**deleteConfluenceSystem**](ConfluenceSystemsControllerApi.md#deleteConfluenceSystem) | **POST** /api/admin/ConfluenceSystemsController/deleteConfluenceSystem | 
[**fastConfluenceConfig**](ConfluenceSystemsControllerApi.md#fastConfluenceConfig) | **POST** /api/admin/ConfluenceSystemsController/fastConfluenceConfig | 
[**findConfluenceEndpointsByCode**](ConfluenceSystemsControllerApi.md#findConfluenceEndpointsByCode) | **GET** /api/admin/ConfluenceSystemsController/findConfluenceEndpointsByCode | 
[**findConfluenceEndpointsByProject**](ConfluenceSystemsControllerApi.md#findConfluenceEndpointsByProject) | **GET** /api/admin/ConfluenceSystemsController/findConfluenceEndpointsByProject | 
[**findConfluenceEndpointsByQbe**](ConfluenceSystemsControllerApi.md#findConfluenceEndpointsByQbe) | **POST** /api/admin/ConfluenceSystemsController/findConfluenceEndpointsByQbe | 
[**findConfluenceSystemByCode**](ConfluenceSystemsControllerApi.md#findConfluenceSystemByCode) | **GET** /api/admin/ConfluenceSystemsController/findConfluenceSystemByCode | 
[**getConfluenceSystemTypes**](ConfluenceSystemsControllerApi.md#getConfluenceSystemTypes) | **GET** /api/admin/ConfluenceSystemsController/getConfluenceSystemType | 
[**getConfluenceSystems**](ConfluenceSystemsControllerApi.md#getConfluenceSystems) | **GET** /api/admin/ConfluenceSystemsController/getConfluenceSystems | 
[**insertConfluenceEndpoint**](ConfluenceSystemsControllerApi.md#insertConfluenceEndpoint) | **POST** /api/admin/ConfluenceSystemsController/insertConfluenceEndpoint | 
[**insertConfluenceSystem**](ConfluenceSystemsControllerApi.md#insertConfluenceSystem) | **POST** /api/admin/ConfluenceSystemsController/insertConfluenceSystem | 
[**testConfluenceSystem**](ConfluenceSystemsControllerApi.md#testConfluenceSystem) | **POST** /api/admin/ConfluenceSystemsController/testConfluenceSystem | 
[**updateConfluenceEndpoint**](ConfluenceSystemsControllerApi.md#updateConfluenceEndpoint) | **POST** /api/admin/ConfluenceSystemsController/updateConfluenceEndpoint | 
[**updateConfluenceSystem**](ConfluenceSystemsControllerApi.md#updateConfluenceSystem) | **POST** /api/admin/ConfluenceSystemsController/updateConfluenceSystem | 

<a name="deleteConfluenceEndpoint"></a>
# **deleteConfluenceEndpoint**
> deleteConfluenceEndpoint(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSystemsControllerApi;


ConfluenceSystemsControllerApi apiInstance = new ConfluenceSystemsControllerApi();
GConfluenceProjectEndpoint body = new GConfluenceProjectEndpoint(); // GConfluenceProjectEndpoint | 
try {
    apiInstance.deleteConfluenceEndpoint(body);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSystemsControllerApi#deleteConfluenceEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GConfluenceProjectEndpoint**](GConfluenceProjectEndpoint.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="deleteConfluenceSystem"></a>
# **deleteConfluenceSystem**
> deleteConfluenceSystem(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSystemsControllerApi;


ConfluenceSystemsControllerApi apiInstance = new ConfluenceSystemsControllerApi();
GConfluenceSystem body = new GConfluenceSystem(); // GConfluenceSystem | 
try {
    apiInstance.deleteConfluenceSystem(body);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSystemsControllerApi#deleteConfluenceSystem");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GConfluenceSystem**](GConfluenceSystem.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="fastConfluenceConfig"></a>
# **fastConfluenceConfig**
> OperationStatusGConfluenceSystem fastConfluenceConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSystemsControllerApi;


ConfluenceSystemsControllerApi apiInstance = new ConfluenceSystemsControllerApi();
FastConfluenceSystemInsertRequest body = new FastConfluenceSystemInsertRequest(); // FastConfluenceSystemInsertRequest | 
try {
    OperationStatusGConfluenceSystem result = apiInstance.fastConfluenceConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSystemsControllerApi#fastConfluenceConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**FastConfluenceSystemInsertRequest**](FastConfluenceSystemInsertRequest.md)|  |

### Return type

[**OperationStatusGConfluenceSystem**](OperationStatusGConfluenceSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findConfluenceEndpointsByCode"></a>
# **findConfluenceEndpointsByCode**
> GConfluenceProjectEndpoint findConfluenceEndpointsByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSystemsControllerApi;


ConfluenceSystemsControllerApi apiInstance = new ConfluenceSystemsControllerApi();
String code = "code_example"; // String | 
try {
    GConfluenceProjectEndpoint result = apiInstance.findConfluenceEndpointsByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSystemsControllerApi#findConfluenceEndpointsByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GConfluenceProjectEndpoint**](GConfluenceProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="findConfluenceEndpointsByProject"></a>
# **findConfluenceEndpointsByProject**
> List&lt;GConfluenceProjectEndpoint&gt; findConfluenceEndpointsByProject(parentProjectCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSystemsControllerApi;


ConfluenceSystemsControllerApi apiInstance = new ConfluenceSystemsControllerApi();
String parentProjectCode = "parentProjectCode_example"; // String | 
try {
    List<GConfluenceProjectEndpoint> result = apiInstance.findConfluenceEndpointsByProject(parentProjectCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSystemsControllerApi#findConfluenceEndpointsByProject");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **parentProjectCode** | **String**|  |

### Return type

[**List&lt;GConfluenceProjectEndpoint&gt;**](GConfluenceProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="findConfluenceEndpointsByQbe"></a>
# **findConfluenceEndpointsByQbe**
> List&lt;GConfluenceProjectEndpoint&gt; findConfluenceEndpointsByQbe(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSystemsControllerApi;


ConfluenceSystemsControllerApi apiInstance = new ConfluenceSystemsControllerApi();
GConfluenceProjectEndpoint body = new GConfluenceProjectEndpoint(); // GConfluenceProjectEndpoint | 
try {
    List<GConfluenceProjectEndpoint> result = apiInstance.findConfluenceEndpointsByQbe(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSystemsControllerApi#findConfluenceEndpointsByQbe");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GConfluenceProjectEndpoint**](GConfluenceProjectEndpoint.md)|  |

### Return type

[**List&lt;GConfluenceProjectEndpoint&gt;**](GConfluenceProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findConfluenceSystemByCode"></a>
# **findConfluenceSystemByCode**
> GConfluenceSystem findConfluenceSystemByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSystemsControllerApi;


ConfluenceSystemsControllerApi apiInstance = new ConfluenceSystemsControllerApi();
String code = "code_example"; // String | 
try {
    GConfluenceSystem result = apiInstance.findConfluenceSystemByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSystemsControllerApi#findConfluenceSystemByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GConfluenceSystem**](GConfluenceSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="getConfluenceSystemTypes"></a>
# **getConfluenceSystemTypes**
> GContentManagementSystemType getConfluenceSystemTypes()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSystemsControllerApi;


ConfluenceSystemsControllerApi apiInstance = new ConfluenceSystemsControllerApi();
try {
    GContentManagementSystemType result = apiInstance.getConfluenceSystemTypes();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSystemsControllerApi#getConfluenceSystemTypes");
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

<a name="getConfluenceSystems"></a>
# **getConfluenceSystems**
> List&lt;GConfluenceSystem&gt; getConfluenceSystems()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSystemsControllerApi;


ConfluenceSystemsControllerApi apiInstance = new ConfluenceSystemsControllerApi();
try {
    List<GConfluenceSystem> result = apiInstance.getConfluenceSystems();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSystemsControllerApi#getConfluenceSystems");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GConfluenceSystem&gt;**](GConfluenceSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="insertConfluenceEndpoint"></a>
# **insertConfluenceEndpoint**
> GConfluenceProjectEndpoint insertConfluenceEndpoint(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSystemsControllerApi;


ConfluenceSystemsControllerApi apiInstance = new ConfluenceSystemsControllerApi();
GConfluenceProjectEndpoint body = new GConfluenceProjectEndpoint(); // GConfluenceProjectEndpoint | 
try {
    GConfluenceProjectEndpoint result = apiInstance.insertConfluenceEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSystemsControllerApi#insertConfluenceEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GConfluenceProjectEndpoint**](GConfluenceProjectEndpoint.md)|  |

### Return type

[**GConfluenceProjectEndpoint**](GConfluenceProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertConfluenceSystem"></a>
# **insertConfluenceSystem**
> OperationStatusGConfluenceSystem insertConfluenceSystem(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSystemsControllerApi;


ConfluenceSystemsControllerApi apiInstance = new ConfluenceSystemsControllerApi();
GConfluenceSystem body = new GConfluenceSystem(); // GConfluenceSystem | 
try {
    OperationStatusGConfluenceSystem result = apiInstance.insertConfluenceSystem(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSystemsControllerApi#insertConfluenceSystem");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GConfluenceSystem**](GConfluenceSystem.md)|  |

### Return type

[**OperationStatusGConfluenceSystem**](OperationStatusGConfluenceSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="testConfluenceSystem"></a>
# **testConfluenceSystem**
> OperationStatusGConfluenceSystem testConfluenceSystem(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSystemsControllerApi;


ConfluenceSystemsControllerApi apiInstance = new ConfluenceSystemsControllerApi();
GConfluenceSystem body = new GConfluenceSystem(); // GConfluenceSystem | 
try {
    OperationStatusGConfluenceSystem result = apiInstance.testConfluenceSystem(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSystemsControllerApi#testConfluenceSystem");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GConfluenceSystem**](GConfluenceSystem.md)|  |

### Return type

[**OperationStatusGConfluenceSystem**](OperationStatusGConfluenceSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateConfluenceEndpoint"></a>
# **updateConfluenceEndpoint**
> GConfluenceProjectEndpoint updateConfluenceEndpoint(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSystemsControllerApi;


ConfluenceSystemsControllerApi apiInstance = new ConfluenceSystemsControllerApi();
GConfluenceProjectEndpoint body = new GConfluenceProjectEndpoint(); // GConfluenceProjectEndpoint | 
try {
    GConfluenceProjectEndpoint result = apiInstance.updateConfluenceEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSystemsControllerApi#updateConfluenceEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GConfluenceProjectEndpoint**](GConfluenceProjectEndpoint.md)|  |

### Return type

[**GConfluenceProjectEndpoint**](GConfluenceProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateConfluenceSystem"></a>
# **updateConfluenceSystem**
> OperationStatusGConfluenceSystem updateConfluenceSystem(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ConfluenceSystemsControllerApi;


ConfluenceSystemsControllerApi apiInstance = new ConfluenceSystemsControllerApi();
GConfluenceSystem body = new GConfluenceSystem(); // GConfluenceSystem | 
try {
    OperationStatusGConfluenceSystem result = apiInstance.updateConfluenceSystem(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfluenceSystemsControllerApi#updateConfluenceSystem");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GConfluenceSystem**](GConfluenceSystem.md)|  |

### Return type

[**OperationStatusGConfluenceSystem**](OperationStatusGConfluenceSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

