# WebdavSystemsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteWebdavEndpoint**](WebdavSystemsControllerApi.md#deleteWebdavEndpoint) | **POST** /api/admin/WebdavSystemsController/deleteWebdavEndpoint | 
[**deleteWebdavSystem**](WebdavSystemsControllerApi.md#deleteWebdavSystem) | **POST** /api/admin/WebdavSystemsController/deleteWebdavSystem | 
[**fastWebdavConfig**](WebdavSystemsControllerApi.md#fastWebdavConfig) | **POST** /api/admin/WebdavSystemsController/fastWebdavConfig | 
[**findWebdavEndpointsByCode**](WebdavSystemsControllerApi.md#findWebdavEndpointsByCode) | **GET** /api/admin/WebdavSystemsController/findWebdavEndpointsByCode | 
[**findWebdavEndpointsByProject**](WebdavSystemsControllerApi.md#findWebdavEndpointsByProject) | **GET** /api/admin/WebdavSystemsController/findWebdavEndpointsByProject | 
[**findWebdavEndpointsByQbe**](WebdavSystemsControllerApi.md#findWebdavEndpointsByQbe) | **POST** /api/admin/WebdavSystemsController/findWebdavEndpointsByQbe | 
[**findWebdavSystemByCode**](WebdavSystemsControllerApi.md#findWebdavSystemByCode) | **GET** /api/admin/WebdavSystemsController/findWebdavSystemByCode | 
[**getWebdavSystemTypes**](WebdavSystemsControllerApi.md#getWebdavSystemTypes) | **GET** /api/admin/WebdavSystemsController/getWebdavSystemType | 
[**getWebdavSystems**](WebdavSystemsControllerApi.md#getWebdavSystems) | **GET** /api/admin/WebdavSystemsController/getWebdavSystems | 
[**insertWebdavEndpoint**](WebdavSystemsControllerApi.md#insertWebdavEndpoint) | **POST** /api/admin/WebdavSystemsController/insertWebdavEndpoint | 
[**insertWebdavSystem**](WebdavSystemsControllerApi.md#insertWebdavSystem) | **POST** /api/admin/WebdavSystemsController/insertWebdavSystem | 
[**publishWebdavEndpoint**](WebdavSystemsControllerApi.md#publishWebdavEndpoint) | **POST** /api/admin/WebdavSystemsController/publishWebdavEndpoint | 
[**testWebdavSystem**](WebdavSystemsControllerApi.md#testWebdavSystem) | **POST** /api/admin/WebdavSystemsController/testWebdavSystem | 
[**updateWebdavEndpoint**](WebdavSystemsControllerApi.md#updateWebdavEndpoint) | **POST** /api/admin/WebdavSystemsController/updateWebdavEndpoint | 
[**updateWebdavSystem**](WebdavSystemsControllerApi.md#updateWebdavSystem) | **POST** /api/admin/WebdavSystemsController/updateWebdavSystem | 

<a name="deleteWebdavEndpoint"></a>
# **deleteWebdavEndpoint**
> deleteWebdavEndpoint(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.WebdavSystemsControllerApi;


WebdavSystemsControllerApi apiInstance = new WebdavSystemsControllerApi();
GWebdavProjectEndpoint body = new GWebdavProjectEndpoint(); // GWebdavProjectEndpoint | 
try {
    apiInstance.deleteWebdavEndpoint(body);
} catch (ApiException e) {
    System.err.println("Exception when calling WebdavSystemsControllerApi#deleteWebdavEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GWebdavProjectEndpoint**](GWebdavProjectEndpoint.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="deleteWebdavSystem"></a>
# **deleteWebdavSystem**
> deleteWebdavSystem(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.WebdavSystemsControllerApi;


WebdavSystemsControllerApi apiInstance = new WebdavSystemsControllerApi();
GWebdavContentManagementSystem body = new GWebdavContentManagementSystem(); // GWebdavContentManagementSystem | 
try {
    apiInstance.deleteWebdavSystem(body);
} catch (ApiException e) {
    System.err.println("Exception when calling WebdavSystemsControllerApi#deleteWebdavSystem");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GWebdavContentManagementSystem**](GWebdavContentManagementSystem.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="fastWebdavConfig"></a>
# **fastWebdavConfig**
> OperationStatusGWebdavContentManagementSystem fastWebdavConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.WebdavSystemsControllerApi;


WebdavSystemsControllerApi apiInstance = new WebdavSystemsControllerApi();
FastWebdavSystemInsertRequest body = new FastWebdavSystemInsertRequest(); // FastWebdavSystemInsertRequest | 
try {
    OperationStatusGWebdavContentManagementSystem result = apiInstance.fastWebdavConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WebdavSystemsControllerApi#fastWebdavConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**FastWebdavSystemInsertRequest**](FastWebdavSystemInsertRequest.md)|  |

### Return type

[**OperationStatusGWebdavContentManagementSystem**](OperationStatusGWebdavContentManagementSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findWebdavEndpointsByCode"></a>
# **findWebdavEndpointsByCode**
> GWebdavProjectEndpoint findWebdavEndpointsByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.WebdavSystemsControllerApi;


WebdavSystemsControllerApi apiInstance = new WebdavSystemsControllerApi();
String code = "code_example"; // String | 
try {
    GWebdavProjectEndpoint result = apiInstance.findWebdavEndpointsByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WebdavSystemsControllerApi#findWebdavEndpointsByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GWebdavProjectEndpoint**](GWebdavProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="findWebdavEndpointsByProject"></a>
# **findWebdavEndpointsByProject**
> List&lt;GWebdavProjectEndpoint&gt; findWebdavEndpointsByProject(parentProjectCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.WebdavSystemsControllerApi;


WebdavSystemsControllerApi apiInstance = new WebdavSystemsControllerApi();
String parentProjectCode = "parentProjectCode_example"; // String | 
try {
    List<GWebdavProjectEndpoint> result = apiInstance.findWebdavEndpointsByProject(parentProjectCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WebdavSystemsControllerApi#findWebdavEndpointsByProject");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **parentProjectCode** | **String**|  |

### Return type

[**List&lt;GWebdavProjectEndpoint&gt;**](GWebdavProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="findWebdavEndpointsByQbe"></a>
# **findWebdavEndpointsByQbe**
> List&lt;GWebdavProjectEndpoint&gt; findWebdavEndpointsByQbe(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.WebdavSystemsControllerApi;


WebdavSystemsControllerApi apiInstance = new WebdavSystemsControllerApi();
GWebdavProjectEndpoint body = new GWebdavProjectEndpoint(); // GWebdavProjectEndpoint | 
try {
    List<GWebdavProjectEndpoint> result = apiInstance.findWebdavEndpointsByQbe(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WebdavSystemsControllerApi#findWebdavEndpointsByQbe");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GWebdavProjectEndpoint**](GWebdavProjectEndpoint.md)|  |

### Return type

[**List&lt;GWebdavProjectEndpoint&gt;**](GWebdavProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findWebdavSystemByCode"></a>
# **findWebdavSystemByCode**
> GWebdavContentManagementSystem findWebdavSystemByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.WebdavSystemsControllerApi;


WebdavSystemsControllerApi apiInstance = new WebdavSystemsControllerApi();
String code = "code_example"; // String | 
try {
    GWebdavContentManagementSystem result = apiInstance.findWebdavSystemByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WebdavSystemsControllerApi#findWebdavSystemByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GWebdavContentManagementSystem**](GWebdavContentManagementSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="getWebdavSystemTypes"></a>
# **getWebdavSystemTypes**
> GContentManagementSystemType getWebdavSystemTypes()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.WebdavSystemsControllerApi;


WebdavSystemsControllerApi apiInstance = new WebdavSystemsControllerApi();
try {
    GContentManagementSystemType result = apiInstance.getWebdavSystemTypes();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WebdavSystemsControllerApi#getWebdavSystemTypes");
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

<a name="getWebdavSystems"></a>
# **getWebdavSystems**
> List&lt;GWebdavContentManagementSystem&gt; getWebdavSystems()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.WebdavSystemsControllerApi;


WebdavSystemsControllerApi apiInstance = new WebdavSystemsControllerApi();
try {
    List<GWebdavContentManagementSystem> result = apiInstance.getWebdavSystems();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WebdavSystemsControllerApi#getWebdavSystems");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GWebdavContentManagementSystem&gt;**](GWebdavContentManagementSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="insertWebdavEndpoint"></a>
# **insertWebdavEndpoint**
> GWebdavProjectEndpoint insertWebdavEndpoint(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.WebdavSystemsControllerApi;


WebdavSystemsControllerApi apiInstance = new WebdavSystemsControllerApi();
GWebdavProjectEndpoint body = new GWebdavProjectEndpoint(); // GWebdavProjectEndpoint | 
try {
    GWebdavProjectEndpoint result = apiInstance.insertWebdavEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WebdavSystemsControllerApi#insertWebdavEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GWebdavProjectEndpoint**](GWebdavProjectEndpoint.md)|  |

### Return type

[**GWebdavProjectEndpoint**](GWebdavProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertWebdavSystem"></a>
# **insertWebdavSystem**
> OperationStatusGWebdavContentManagementSystem insertWebdavSystem(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.WebdavSystemsControllerApi;


WebdavSystemsControllerApi apiInstance = new WebdavSystemsControllerApi();
GWebdavContentManagementSystem body = new GWebdavContentManagementSystem(); // GWebdavContentManagementSystem | 
try {
    OperationStatusGWebdavContentManagementSystem result = apiInstance.insertWebdavSystem(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WebdavSystemsControllerApi#insertWebdavSystem");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GWebdavContentManagementSystem**](GWebdavContentManagementSystem.md)|  |

### Return type

[**OperationStatusGWebdavContentManagementSystem**](OperationStatusGWebdavContentManagementSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="publishWebdavEndpoint"></a>
# **publishWebdavEndpoint**
> OperationStatusGJobStatus publishWebdavEndpoint(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.WebdavSystemsControllerApi;


WebdavSystemsControllerApi apiInstance = new WebdavSystemsControllerApi();
GWebdavProjectEndpoint body = new GWebdavProjectEndpoint(); // GWebdavProjectEndpoint | 
try {
    OperationStatusGJobStatus result = apiInstance.publishWebdavEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WebdavSystemsControllerApi#publishWebdavEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GWebdavProjectEndpoint**](GWebdavProjectEndpoint.md)|  |

### Return type

[**OperationStatusGJobStatus**](OperationStatusGJobStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="testWebdavSystem"></a>
# **testWebdavSystem**
> OperationStatusGWebdavContentManagementSystem testWebdavSystem(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.WebdavSystemsControllerApi;


WebdavSystemsControllerApi apiInstance = new WebdavSystemsControllerApi();
GWebdavContentManagementSystem body = new GWebdavContentManagementSystem(); // GWebdavContentManagementSystem | 
try {
    OperationStatusGWebdavContentManagementSystem result = apiInstance.testWebdavSystem(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WebdavSystemsControllerApi#testWebdavSystem");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GWebdavContentManagementSystem**](GWebdavContentManagementSystem.md)|  |

### Return type

[**OperationStatusGWebdavContentManagementSystem**](OperationStatusGWebdavContentManagementSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateWebdavEndpoint"></a>
# **updateWebdavEndpoint**
> GWebdavProjectEndpoint updateWebdavEndpoint(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.WebdavSystemsControllerApi;


WebdavSystemsControllerApi apiInstance = new WebdavSystemsControllerApi();
GWebdavProjectEndpoint body = new GWebdavProjectEndpoint(); // GWebdavProjectEndpoint | 
try {
    GWebdavProjectEndpoint result = apiInstance.updateWebdavEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WebdavSystemsControllerApi#updateWebdavEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GWebdavProjectEndpoint**](GWebdavProjectEndpoint.md)|  |

### Return type

[**GWebdavProjectEndpoint**](GWebdavProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateWebdavSystem"></a>
# **updateWebdavSystem**
> OperationStatusGWebdavContentManagementSystem updateWebdavSystem(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.WebdavSystemsControllerApi;


WebdavSystemsControllerApi apiInstance = new WebdavSystemsControllerApi();
GWebdavContentManagementSystem body = new GWebdavContentManagementSystem(); // GWebdavContentManagementSystem | 
try {
    OperationStatusGWebdavContentManagementSystem result = apiInstance.updateWebdavSystem(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WebdavSystemsControllerApi#updateWebdavSystem");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GWebdavContentManagementSystem**](GWebdavContentManagementSystem.md)|  |

### Return type

[**OperationStatusGWebdavContentManagementSystem**](OperationStatusGWebdavContentManagementSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

