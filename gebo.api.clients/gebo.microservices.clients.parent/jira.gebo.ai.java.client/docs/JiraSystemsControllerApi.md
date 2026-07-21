# JiraSystemsControllerApi

All URIs are relative to *http://localhost:13011/jira*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteJiraEndpoint**](JiraSystemsControllerApi.md#deleteJiraEndpoint) | **POST** /api/admin/JiraSystemsController/deleteJiraEndpoint | 
[**deleteJiraSystem**](JiraSystemsControllerApi.md#deleteJiraSystem) | **POST** /api/admin/JiraSystemsController/deleteJiraSystem | 
[**fastJiraConfig**](JiraSystemsControllerApi.md#fastJiraConfig) | **POST** /api/admin/JiraSystemsController/fastJiraConfig | 
[**findJiraEndpointsByCode**](JiraSystemsControllerApi.md#findJiraEndpointsByCode) | **GET** /api/admin/JiraSystemsController/findJiraEndpointsByCode | 
[**findJiraEndpointsByProject**](JiraSystemsControllerApi.md#findJiraEndpointsByProject) | **GET** /api/admin/JiraSystemsController/findJiraEndpointsByProject | 
[**findJiraEndpointsByQbe**](JiraSystemsControllerApi.md#findJiraEndpointsByQbe) | **POST** /api/admin/JiraSystemsController/findJiraEndpointsByQbe | 
[**findJiraSystemByCode**](JiraSystemsControllerApi.md#findJiraSystemByCode) | **GET** /api/admin/JiraSystemsController/findJiraSystemByCode | 
[**getJiraSystemTypes**](JiraSystemsControllerApi.md#getJiraSystemTypes) | **GET** /api/admin/JiraSystemsController/getJiraSystemType | 
[**getJiraSystems**](JiraSystemsControllerApi.md#getJiraSystems) | **GET** /api/admin/JiraSystemsController/getJiraSystems | 
[**insertJiraEndpoint**](JiraSystemsControllerApi.md#insertJiraEndpoint) | **POST** /api/admin/JiraSystemsController/insertJiraEndpoint | 
[**insertJiraSystem**](JiraSystemsControllerApi.md#insertJiraSystem) | **POST** /api/admin/JiraSystemsController/insertJiraSystem | 
[**publishJiraEndpoint**](JiraSystemsControllerApi.md#publishJiraEndpoint) | **POST** /api/admin/JiraSystemsController/publishJiraEndpoint | 
[**testJiraSystem**](JiraSystemsControllerApi.md#testJiraSystem) | **POST** /api/admin/JiraSystemsController/testJiraSystem | 
[**updateJiraEndpoint**](JiraSystemsControllerApi.md#updateJiraEndpoint) | **POST** /api/admin/JiraSystemsController/updateJiraEndpoint | 
[**updateJiraSystem**](JiraSystemsControllerApi.md#updateJiraSystem) | **POST** /api/admin/JiraSystemsController/updateJiraSystem | 

<a name="deleteJiraEndpoint"></a>
# **deleteJiraEndpoint**
> deleteJiraEndpoint(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.jira.invoker.ApiException;
//import gebo.microservices.api.client.jira.api.JiraSystemsControllerApi;


JiraSystemsControllerApi apiInstance = new JiraSystemsControllerApi();
GJiraProjectEndpoint body = new GJiraProjectEndpoint(); // GJiraProjectEndpoint | 
try {
    apiInstance.deleteJiraEndpoint(body);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSystemsControllerApi#deleteJiraEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GJiraProjectEndpoint**](GJiraProjectEndpoint.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="deleteJiraSystem"></a>
# **deleteJiraSystem**
> deleteJiraSystem(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.jira.invoker.ApiException;
//import gebo.microservices.api.client.jira.api.JiraSystemsControllerApi;


JiraSystemsControllerApi apiInstance = new JiraSystemsControllerApi();
GJiraSystem body = new GJiraSystem(); // GJiraSystem | 
try {
    apiInstance.deleteJiraSystem(body);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSystemsControllerApi#deleteJiraSystem");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GJiraSystem**](GJiraSystem.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="fastJiraConfig"></a>
# **fastJiraConfig**
> OperationStatusGJiraSystem fastJiraConfig(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.jira.invoker.ApiException;
//import gebo.microservices.api.client.jira.api.JiraSystemsControllerApi;


JiraSystemsControllerApi apiInstance = new JiraSystemsControllerApi();
FastJiraSystemInsertRequest body = new FastJiraSystemInsertRequest(); // FastJiraSystemInsertRequest | 
try {
    OperationStatusGJiraSystem result = apiInstance.fastJiraConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSystemsControllerApi#fastJiraConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**FastJiraSystemInsertRequest**](FastJiraSystemInsertRequest.md)|  |

### Return type

[**OperationStatusGJiraSystem**](OperationStatusGJiraSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findJiraEndpointsByCode"></a>
# **findJiraEndpointsByCode**
> GJiraProjectEndpoint findJiraEndpointsByCode(code)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.jira.invoker.ApiException;
//import gebo.microservices.api.client.jira.api.JiraSystemsControllerApi;


JiraSystemsControllerApi apiInstance = new JiraSystemsControllerApi();
Object code = null; // Object | 
try {
    GJiraProjectEndpoint result = apiInstance.findJiraEndpointsByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSystemsControllerApi#findJiraEndpointsByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | [**Object**](.md)|  |

### Return type

[**GJiraProjectEndpoint**](GJiraProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="findJiraEndpointsByProject"></a>
# **findJiraEndpointsByProject**
> Object findJiraEndpointsByProject(parentProjectCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.jira.invoker.ApiException;
//import gebo.microservices.api.client.jira.api.JiraSystemsControllerApi;


JiraSystemsControllerApi apiInstance = new JiraSystemsControllerApi();
Object parentProjectCode = null; // Object | 
try {
    Object result = apiInstance.findJiraEndpointsByProject(parentProjectCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSystemsControllerApi#findJiraEndpointsByProject");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **parentProjectCode** | [**Object**](.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="findJiraEndpointsByQbe"></a>
# **findJiraEndpointsByQbe**
> Object findJiraEndpointsByQbe(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.jira.invoker.ApiException;
//import gebo.microservices.api.client.jira.api.JiraSystemsControllerApi;


JiraSystemsControllerApi apiInstance = new JiraSystemsControllerApi();
GJiraProjectEndpoint body = new GJiraProjectEndpoint(); // GJiraProjectEndpoint | 
try {
    Object result = apiInstance.findJiraEndpointsByQbe(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSystemsControllerApi#findJiraEndpointsByQbe");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GJiraProjectEndpoint**](GJiraProjectEndpoint.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findJiraSystemByCode"></a>
# **findJiraSystemByCode**
> GJiraSystem findJiraSystemByCode(code)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.jira.invoker.ApiException;
//import gebo.microservices.api.client.jira.api.JiraSystemsControllerApi;


JiraSystemsControllerApi apiInstance = new JiraSystemsControllerApi();
Object code = null; // Object | 
try {
    GJiraSystem result = apiInstance.findJiraSystemByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSystemsControllerApi#findJiraSystemByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | [**Object**](.md)|  |

### Return type

[**GJiraSystem**](GJiraSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="getJiraSystemTypes"></a>
# **getJiraSystemTypes**
> GContentManagementSystemType getJiraSystemTypes()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.jira.invoker.ApiException;
//import gebo.microservices.api.client.jira.api.JiraSystemsControllerApi;


JiraSystemsControllerApi apiInstance = new JiraSystemsControllerApi();
try {
    GContentManagementSystemType result = apiInstance.getJiraSystemTypes();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSystemsControllerApi#getJiraSystemTypes");
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

<a name="getJiraSystems"></a>
# **getJiraSystems**
> Object getJiraSystems()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.jira.invoker.ApiException;
//import gebo.microservices.api.client.jira.api.JiraSystemsControllerApi;


JiraSystemsControllerApi apiInstance = new JiraSystemsControllerApi();
try {
    Object result = apiInstance.getJiraSystems();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSystemsControllerApi#getJiraSystems");
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
 - **Accept**: */*

<a name="insertJiraEndpoint"></a>
# **insertJiraEndpoint**
> GJiraProjectEndpoint insertJiraEndpoint(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.jira.invoker.ApiException;
//import gebo.microservices.api.client.jira.api.JiraSystemsControllerApi;


JiraSystemsControllerApi apiInstance = new JiraSystemsControllerApi();
GJiraProjectEndpoint body = new GJiraProjectEndpoint(); // GJiraProjectEndpoint | 
try {
    GJiraProjectEndpoint result = apiInstance.insertJiraEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSystemsControllerApi#insertJiraEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GJiraProjectEndpoint**](GJiraProjectEndpoint.md)|  |

### Return type

[**GJiraProjectEndpoint**](GJiraProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertJiraSystem"></a>
# **insertJiraSystem**
> OperationStatusGJiraSystem insertJiraSystem(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.jira.invoker.ApiException;
//import gebo.microservices.api.client.jira.api.JiraSystemsControllerApi;


JiraSystemsControllerApi apiInstance = new JiraSystemsControllerApi();
GJiraSystem body = new GJiraSystem(); // GJiraSystem | 
try {
    OperationStatusGJiraSystem result = apiInstance.insertJiraSystem(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSystemsControllerApi#insertJiraSystem");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GJiraSystem**](GJiraSystem.md)|  |

### Return type

[**OperationStatusGJiraSystem**](OperationStatusGJiraSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="publishJiraEndpoint"></a>
# **publishJiraEndpoint**
> OperationStatusGJobStatus publishJiraEndpoint(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.jira.invoker.ApiException;
//import gebo.microservices.api.client.jira.api.JiraSystemsControllerApi;


JiraSystemsControllerApi apiInstance = new JiraSystemsControllerApi();
GJiraProjectEndpoint body = new GJiraProjectEndpoint(); // GJiraProjectEndpoint | 
try {
    OperationStatusGJobStatus result = apiInstance.publishJiraEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSystemsControllerApi#publishJiraEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GJiraProjectEndpoint**](GJiraProjectEndpoint.md)|  |

### Return type

[**OperationStatusGJobStatus**](OperationStatusGJobStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="testJiraSystem"></a>
# **testJiraSystem**
> OperationStatusGJiraSystem testJiraSystem(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.jira.invoker.ApiException;
//import gebo.microservices.api.client.jira.api.JiraSystemsControllerApi;


JiraSystemsControllerApi apiInstance = new JiraSystemsControllerApi();
GJiraSystem body = new GJiraSystem(); // GJiraSystem | 
try {
    OperationStatusGJiraSystem result = apiInstance.testJiraSystem(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSystemsControllerApi#testJiraSystem");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GJiraSystem**](GJiraSystem.md)|  |

### Return type

[**OperationStatusGJiraSystem**](OperationStatusGJiraSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateJiraEndpoint"></a>
# **updateJiraEndpoint**
> GJiraProjectEndpoint updateJiraEndpoint(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.jira.invoker.ApiException;
//import gebo.microservices.api.client.jira.api.JiraSystemsControllerApi;


JiraSystemsControllerApi apiInstance = new JiraSystemsControllerApi();
GJiraProjectEndpoint body = new GJiraProjectEndpoint(); // GJiraProjectEndpoint | 
try {
    GJiraProjectEndpoint result = apiInstance.updateJiraEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSystemsControllerApi#updateJiraEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GJiraProjectEndpoint**](GJiraProjectEndpoint.md)|  |

### Return type

[**GJiraProjectEndpoint**](GJiraProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateJiraSystem"></a>
# **updateJiraSystem**
> OperationStatusGJiraSystem updateJiraSystem(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.jira.invoker.ApiException;
//import gebo.microservices.api.client.jira.api.JiraSystemsControllerApi;


JiraSystemsControllerApi apiInstance = new JiraSystemsControllerApi();
GJiraSystem body = new GJiraSystem(); // GJiraSystem | 
try {
    OperationStatusGJiraSystem result = apiInstance.updateJiraSystem(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JiraSystemsControllerApi#updateJiraSystem");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GJiraSystem**](GJiraSystem.md)|  |

### Return type

[**OperationStatusGJiraSystem**](OperationStatusGJiraSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

