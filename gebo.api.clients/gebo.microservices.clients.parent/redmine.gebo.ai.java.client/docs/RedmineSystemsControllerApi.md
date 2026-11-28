# RedmineSystemsControllerApi

All URIs are relative to *http://localhost:13021/redmine*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteRedmineEndpoint**](RedmineSystemsControllerApi.md#deleteRedmineEndpoint) | **POST** /api/admin/RedmineSystemsController/deleteRedmineEndpoint | 
[**deleteRedmineSystem**](RedmineSystemsControllerApi.md#deleteRedmineSystem) | **POST** /api/admin/RedmineSystemsController/deleteRedmineSystem | 
[**fastRedmineConfig**](RedmineSystemsControllerApi.md#fastRedmineConfig) | **POST** /api/admin/RedmineSystemsController/fastRedmineConfig | 
[**findRedmineEndpointByCode**](RedmineSystemsControllerApi.md#findRedmineEndpointByCode) | **GET** /api/admin/RedmineSystemsController/findRedmineEndpointByCode | 
[**findRedmineEndpointsByProject**](RedmineSystemsControllerApi.md#findRedmineEndpointsByProject) | **GET** /api/admin/RedmineSystemsController/findRedmineEndpointsByProject | 
[**findRedmineEndpointsByQbe**](RedmineSystemsControllerApi.md#findRedmineEndpointsByQbe) | **POST** /api/admin/RedmineSystemsController/findRedmineEndpointsByQbe | 
[**findRedmineSystemByCode**](RedmineSystemsControllerApi.md#findRedmineSystemByCode) | **GET** /api/admin/RedmineSystemsController/findRedmineSystemByCode | 
[**getRedmineSystemType**](RedmineSystemsControllerApi.md#getRedmineSystemType) | **GET** /api/admin/RedmineSystemsController/getRedmineSystemType | 
[**getRedmineSystems**](RedmineSystemsControllerApi.md#getRedmineSystems) | **GET** /api/admin/RedmineSystemsController/getRedmineSystems | 
[**insertRedmineEndpoint**](RedmineSystemsControllerApi.md#insertRedmineEndpoint) | **POST** /api/admin/RedmineSystemsController/insertRedmineEndpoint | 
[**insertRedmineSystem**](RedmineSystemsControllerApi.md#insertRedmineSystem) | **POST** /api/admin/RedmineSystemsController/insertRedmineSystem | 
[**publishRedmineEndpoint**](RedmineSystemsControllerApi.md#publishRedmineEndpoint) | **POST** /api/admin/RedmineSystemsController/publishRedmineEndpoint | 
[**testRedmineSystem**](RedmineSystemsControllerApi.md#testRedmineSystem) | **POST** /api/admin/RedmineSystemsController/testRedmineSystem | 
[**updateRedmineEndpoint**](RedmineSystemsControllerApi.md#updateRedmineEndpoint) | **POST** /api/admin/RedmineSystemsController/updateRedmineEndpoint | 
[**updateRedmineSystem**](RedmineSystemsControllerApi.md#updateRedmineSystem) | **POST** /api/admin/RedmineSystemsController/updateRedmineSystem | 

<a name="deleteRedmineEndpoint"></a>
# **deleteRedmineEndpoint**
> deleteRedmineEndpoint(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.redmine.invoker.ApiException;
//import gebo.microservices.api.client.redmine.api.RedmineSystemsControllerApi;


RedmineSystemsControllerApi apiInstance = new RedmineSystemsControllerApi();
GRedmineProjectEndpoint body = new GRedmineProjectEndpoint(); // GRedmineProjectEndpoint | 
try {
    apiInstance.deleteRedmineEndpoint(body);
} catch (ApiException e) {
    System.err.println("Exception when calling RedmineSystemsControllerApi#deleteRedmineEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GRedmineProjectEndpoint**](GRedmineProjectEndpoint.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="deleteRedmineSystem"></a>
# **deleteRedmineSystem**
> deleteRedmineSystem(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.redmine.invoker.ApiException;
//import gebo.microservices.api.client.redmine.api.RedmineSystemsControllerApi;


RedmineSystemsControllerApi apiInstance = new RedmineSystemsControllerApi();
GRedmineSystem body = new GRedmineSystem(); // GRedmineSystem | 
try {
    apiInstance.deleteRedmineSystem(body);
} catch (ApiException e) {
    System.err.println("Exception when calling RedmineSystemsControllerApi#deleteRedmineSystem");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GRedmineSystem**](GRedmineSystem.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="fastRedmineConfig"></a>
# **fastRedmineConfig**
> OperationStatusGRedmineSystem fastRedmineConfig(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.redmine.invoker.ApiException;
//import gebo.microservices.api.client.redmine.api.RedmineSystemsControllerApi;


RedmineSystemsControllerApi apiInstance = new RedmineSystemsControllerApi();
FastRedmineSystemInsertRequest body = new FastRedmineSystemInsertRequest(); // FastRedmineSystemInsertRequest | 
try {
    OperationStatusGRedmineSystem result = apiInstance.fastRedmineConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RedmineSystemsControllerApi#fastRedmineConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**FastRedmineSystemInsertRequest**](FastRedmineSystemInsertRequest.md)|  |

### Return type

[**OperationStatusGRedmineSystem**](OperationStatusGRedmineSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findRedmineEndpointByCode"></a>
# **findRedmineEndpointByCode**
> GRedmineProjectEndpoint findRedmineEndpointByCode(code)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.redmine.invoker.ApiException;
//import gebo.microservices.api.client.redmine.api.RedmineSystemsControllerApi;


RedmineSystemsControllerApi apiInstance = new RedmineSystemsControllerApi();
String code = "code_example"; // String | 
try {
    GRedmineProjectEndpoint result = apiInstance.findRedmineEndpointByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RedmineSystemsControllerApi#findRedmineEndpointByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GRedmineProjectEndpoint**](GRedmineProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findRedmineEndpointsByProject"></a>
# **findRedmineEndpointsByProject**
> List&lt;GRedmineProjectEndpoint&gt; findRedmineEndpointsByProject(parentProjectCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.redmine.invoker.ApiException;
//import gebo.microservices.api.client.redmine.api.RedmineSystemsControllerApi;


RedmineSystemsControllerApi apiInstance = new RedmineSystemsControllerApi();
String parentProjectCode = "parentProjectCode_example"; // String | 
try {
    List<GRedmineProjectEndpoint> result = apiInstance.findRedmineEndpointsByProject(parentProjectCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RedmineSystemsControllerApi#findRedmineEndpointsByProject");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **parentProjectCode** | **String**|  |

### Return type

[**List&lt;GRedmineProjectEndpoint&gt;**](GRedmineProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findRedmineEndpointsByQbe"></a>
# **findRedmineEndpointsByQbe**
> List&lt;GRedmineProjectEndpoint&gt; findRedmineEndpointsByQbe(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.redmine.invoker.ApiException;
//import gebo.microservices.api.client.redmine.api.RedmineSystemsControllerApi;


RedmineSystemsControllerApi apiInstance = new RedmineSystemsControllerApi();
GRedmineProjectEndpoint body = new GRedmineProjectEndpoint(); // GRedmineProjectEndpoint | 
try {
    List<GRedmineProjectEndpoint> result = apiInstance.findRedmineEndpointsByQbe(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RedmineSystemsControllerApi#findRedmineEndpointsByQbe");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GRedmineProjectEndpoint**](GRedmineProjectEndpoint.md)|  |

### Return type

[**List&lt;GRedmineProjectEndpoint&gt;**](GRedmineProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findRedmineSystemByCode"></a>
# **findRedmineSystemByCode**
> GRedmineSystem findRedmineSystemByCode(code)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.redmine.invoker.ApiException;
//import gebo.microservices.api.client.redmine.api.RedmineSystemsControllerApi;


RedmineSystemsControllerApi apiInstance = new RedmineSystemsControllerApi();
String code = "code_example"; // String | 
try {
    GRedmineSystem result = apiInstance.findRedmineSystemByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RedmineSystemsControllerApi#findRedmineSystemByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GRedmineSystem**](GRedmineSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getRedmineSystemType"></a>
# **getRedmineSystemType**
> GContentManagementSystemType getRedmineSystemType()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.redmine.invoker.ApiException;
//import gebo.microservices.api.client.redmine.api.RedmineSystemsControllerApi;


RedmineSystemsControllerApi apiInstance = new RedmineSystemsControllerApi();
try {
    GContentManagementSystemType result = apiInstance.getRedmineSystemType();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RedmineSystemsControllerApi#getRedmineSystemType");
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

<a name="getRedmineSystems"></a>
# **getRedmineSystems**
> List&lt;GRedmineSystem&gt; getRedmineSystems()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.redmine.invoker.ApiException;
//import gebo.microservices.api.client.redmine.api.RedmineSystemsControllerApi;


RedmineSystemsControllerApi apiInstance = new RedmineSystemsControllerApi();
try {
    List<GRedmineSystem> result = apiInstance.getRedmineSystems();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RedmineSystemsControllerApi#getRedmineSystems");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GRedmineSystem&gt;**](GRedmineSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="insertRedmineEndpoint"></a>
# **insertRedmineEndpoint**
> GRedmineProjectEndpoint insertRedmineEndpoint(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.redmine.invoker.ApiException;
//import gebo.microservices.api.client.redmine.api.RedmineSystemsControllerApi;


RedmineSystemsControllerApi apiInstance = new RedmineSystemsControllerApi();
GRedmineProjectEndpoint body = new GRedmineProjectEndpoint(); // GRedmineProjectEndpoint | 
try {
    GRedmineProjectEndpoint result = apiInstance.insertRedmineEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RedmineSystemsControllerApi#insertRedmineEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GRedmineProjectEndpoint**](GRedmineProjectEndpoint.md)|  |

### Return type

[**GRedmineProjectEndpoint**](GRedmineProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertRedmineSystem"></a>
# **insertRedmineSystem**
> OperationStatusGRedmineSystem insertRedmineSystem(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.redmine.invoker.ApiException;
//import gebo.microservices.api.client.redmine.api.RedmineSystemsControllerApi;


RedmineSystemsControllerApi apiInstance = new RedmineSystemsControllerApi();
GRedmineSystem body = new GRedmineSystem(); // GRedmineSystem | 
try {
    OperationStatusGRedmineSystem result = apiInstance.insertRedmineSystem(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RedmineSystemsControllerApi#insertRedmineSystem");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GRedmineSystem**](GRedmineSystem.md)|  |

### Return type

[**OperationStatusGRedmineSystem**](OperationStatusGRedmineSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="publishRedmineEndpoint"></a>
# **publishRedmineEndpoint**
> OperationStatusGJobStatus publishRedmineEndpoint(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.redmine.invoker.ApiException;
//import gebo.microservices.api.client.redmine.api.RedmineSystemsControllerApi;


RedmineSystemsControllerApi apiInstance = new RedmineSystemsControllerApi();
GRedmineProjectEndpoint body = new GRedmineProjectEndpoint(); // GRedmineProjectEndpoint | 
try {
    OperationStatusGJobStatus result = apiInstance.publishRedmineEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RedmineSystemsControllerApi#publishRedmineEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GRedmineProjectEndpoint**](GRedmineProjectEndpoint.md)|  |

### Return type

[**OperationStatusGJobStatus**](OperationStatusGJobStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="testRedmineSystem"></a>
# **testRedmineSystem**
> OperationStatusGRedmineSystem testRedmineSystem(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.redmine.invoker.ApiException;
//import gebo.microservices.api.client.redmine.api.RedmineSystemsControllerApi;


RedmineSystemsControllerApi apiInstance = new RedmineSystemsControllerApi();
GRedmineSystem body = new GRedmineSystem(); // GRedmineSystem | 
try {
    OperationStatusGRedmineSystem result = apiInstance.testRedmineSystem(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RedmineSystemsControllerApi#testRedmineSystem");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GRedmineSystem**](GRedmineSystem.md)|  |

### Return type

[**OperationStatusGRedmineSystem**](OperationStatusGRedmineSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateRedmineEndpoint"></a>
# **updateRedmineEndpoint**
> GRedmineProjectEndpoint updateRedmineEndpoint(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.redmine.invoker.ApiException;
//import gebo.microservices.api.client.redmine.api.RedmineSystemsControllerApi;


RedmineSystemsControllerApi apiInstance = new RedmineSystemsControllerApi();
GRedmineProjectEndpoint body = new GRedmineProjectEndpoint(); // GRedmineProjectEndpoint | 
try {
    GRedmineProjectEndpoint result = apiInstance.updateRedmineEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RedmineSystemsControllerApi#updateRedmineEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GRedmineProjectEndpoint**](GRedmineProjectEndpoint.md)|  |

### Return type

[**GRedmineProjectEndpoint**](GRedmineProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateRedmineSystem"></a>
# **updateRedmineSystem**
> OperationStatusGRedmineSystem updateRedmineSystem(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.redmine.invoker.ApiException;
//import gebo.microservices.api.client.redmine.api.RedmineSystemsControllerApi;


RedmineSystemsControllerApi apiInstance = new RedmineSystemsControllerApi();
GRedmineSystem body = new GRedmineSystem(); // GRedmineSystem | 
try {
    OperationStatusGRedmineSystem result = apiInstance.updateRedmineSystem(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RedmineSystemsControllerApi#updateRedmineSystem");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GRedmineSystem**](GRedmineSystem.md)|  |

### Return type

[**OperationStatusGRedmineSystem**](OperationStatusGRedmineSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

