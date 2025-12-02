# GitSystemsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteGitEndpoint**](GitSystemsControllerApi.md#deleteGitEndpoint) | **POST** /api/admin/GITSystemsController/deleteGitEndpoint | 
[**deleteGitSystem**](GitSystemsControllerApi.md#deleteGitSystem) | **POST** /api/admin/GITSystemsController/deleteGitSystem | 
[**findGitEndpointsByProject**](GitSystemsControllerApi.md#findGitEndpointsByProject) | **GET** /api/admin/GITSystemsController/findGitEndpointsByProject | 
[**findGitEndpointsByQbe**](GitSystemsControllerApi.md#findGitEndpointsByQbe) | **POST** /api/admin/GITSystemsController/findGitEndpointsByQbe | 
[**getBranchesList**](GitSystemsControllerApi.md#getBranchesList) | **POST** /api/admin/GITSystemsController/getBranchesList | 
[**getGitSystemTypes**](GitSystemsControllerApi.md#getGitSystemTypes) | **GET** /api/admin/GITSystemsController/getGitSystemTypes | 
[**getGitSystems**](GitSystemsControllerApi.md#getGitSystems) | **GET** /api/admin/GITSystemsController/getGitSystems | 
[**insertGitEndpoint**](GitSystemsControllerApi.md#insertGitEndpoint) | **POST** /api/admin/GITSystemsController/insertGitEndpoint | 
[**insertGitSystem**](GitSystemsControllerApi.md#insertGitSystem) | **POST** /api/admin/GITSystemsController/insertGitSystem | 
[**updateGitEndpoint**](GitSystemsControllerApi.md#updateGitEndpoint) | **POST** /api/admin/GITSystemsController/updateGitEndpoint | 
[**updateGitSystem**](GitSystemsControllerApi.md#updateGitSystem) | **POST** /api/admin/GITSystemsController/updateGitSystem | 

<a name="deleteGitEndpoint"></a>
# **deleteGitEndpoint**
> deleteGitEndpoint(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GitSystemsControllerApi;


GitSystemsControllerApi apiInstance = new GitSystemsControllerApi();
GGitProjectEndpoint body = new GGitProjectEndpoint(); // GGitProjectEndpoint | 
try {
    apiInstance.deleteGitEndpoint(body);
} catch (ApiException e) {
    System.err.println("Exception when calling GitSystemsControllerApi#deleteGitEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGitProjectEndpoint**](GGitProjectEndpoint.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="deleteGitSystem"></a>
# **deleteGitSystem**
> deleteGitSystem(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GitSystemsControllerApi;


GitSystemsControllerApi apiInstance = new GitSystemsControllerApi();
GGitContentManagementSystem body = new GGitContentManagementSystem(); // GGitContentManagementSystem | 
try {
    apiInstance.deleteGitSystem(body);
} catch (ApiException e) {
    System.err.println("Exception when calling GitSystemsControllerApi#deleteGitSystem");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGitContentManagementSystem**](GGitContentManagementSystem.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="findGitEndpointsByProject"></a>
# **findGitEndpointsByProject**
> List&lt;GGitProjectEndpoint&gt; findGitEndpointsByProject(parentProjectCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GitSystemsControllerApi;


GitSystemsControllerApi apiInstance = new GitSystemsControllerApi();
String parentProjectCode = "parentProjectCode_example"; // String | 
try {
    List<GGitProjectEndpoint> result = apiInstance.findGitEndpointsByProject(parentProjectCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GitSystemsControllerApi#findGitEndpointsByProject");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **parentProjectCode** | **String**|  |

### Return type

[**List&lt;GGitProjectEndpoint&gt;**](GGitProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="findGitEndpointsByQbe"></a>
# **findGitEndpointsByQbe**
> List&lt;GGitProjectEndpoint&gt; findGitEndpointsByQbe(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GitSystemsControllerApi;


GitSystemsControllerApi apiInstance = new GitSystemsControllerApi();
GGitProjectEndpoint body = new GGitProjectEndpoint(); // GGitProjectEndpoint | 
try {
    List<GGitProjectEndpoint> result = apiInstance.findGitEndpointsByQbe(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GitSystemsControllerApi#findGitEndpointsByQbe");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGitProjectEndpoint**](GGitProjectEndpoint.md)|  |

### Return type

[**List&lt;GGitProjectEndpoint&gt;**](GGitProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getBranchesList"></a>
# **getBranchesList**
> OperationStatusListString getBranchesList(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GitSystemsControllerApi;


GitSystemsControllerApi apiInstance = new GitSystemsControllerApi();
GGitProjectEndpoint body = new GGitProjectEndpoint(); // GGitProjectEndpoint | 
try {
    OperationStatusListString result = apiInstance.getBranchesList(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GitSystemsControllerApi#getBranchesList");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGitProjectEndpoint**](GGitProjectEndpoint.md)|  |

### Return type

[**OperationStatusListString**](OperationStatusListString.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getGitSystemTypes"></a>
# **getGitSystemTypes**
> List&lt;GContentManagementSystemType&gt; getGitSystemTypes()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GitSystemsControllerApi;


GitSystemsControllerApi apiInstance = new GitSystemsControllerApi();
try {
    List<GContentManagementSystemType> result = apiInstance.getGitSystemTypes();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GitSystemsControllerApi#getGitSystemTypes");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GContentManagementSystemType&gt;**](GContentManagementSystemType.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getGitSystems"></a>
# **getGitSystems**
> List&lt;GGitContentManagementSystem&gt; getGitSystems(handlerCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GitSystemsControllerApi;


GitSystemsControllerApi apiInstance = new GitSystemsControllerApi();
String handlerCode = "handlerCode_example"; // String | 
try {
    List<GGitContentManagementSystem> result = apiInstance.getGitSystems(handlerCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GitSystemsControllerApi#getGitSystems");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **handlerCode** | **String**|  | [optional]

### Return type

[**List&lt;GGitContentManagementSystem&gt;**](GGitContentManagementSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="insertGitEndpoint"></a>
# **insertGitEndpoint**
> OperationStatusGGitProjectEndpoint insertGitEndpoint(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GitSystemsControllerApi;


GitSystemsControllerApi apiInstance = new GitSystemsControllerApi();
GGitProjectEndpoint body = new GGitProjectEndpoint(); // GGitProjectEndpoint | 
try {
    OperationStatusGGitProjectEndpoint result = apiInstance.insertGitEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GitSystemsControllerApi#insertGitEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGitProjectEndpoint**](GGitProjectEndpoint.md)|  |

### Return type

[**OperationStatusGGitProjectEndpoint**](OperationStatusGGitProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertGitSystem"></a>
# **insertGitSystem**
> GGitContentManagementSystem insertGitSystem(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GitSystemsControllerApi;


GitSystemsControllerApi apiInstance = new GitSystemsControllerApi();
GGitContentManagementSystem body = new GGitContentManagementSystem(); // GGitContentManagementSystem | 
try {
    GGitContentManagementSystem result = apiInstance.insertGitSystem(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GitSystemsControllerApi#insertGitSystem");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGitContentManagementSystem**](GGitContentManagementSystem.md)|  |

### Return type

[**GGitContentManagementSystem**](GGitContentManagementSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateGitEndpoint"></a>
# **updateGitEndpoint**
> OperationStatusGGitProjectEndpoint updateGitEndpoint(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GitSystemsControllerApi;


GitSystemsControllerApi apiInstance = new GitSystemsControllerApi();
GGitProjectEndpoint body = new GGitProjectEndpoint(); // GGitProjectEndpoint | 
try {
    OperationStatusGGitProjectEndpoint result = apiInstance.updateGitEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GitSystemsControllerApi#updateGitEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGitProjectEndpoint**](GGitProjectEndpoint.md)|  |

### Return type

[**OperationStatusGGitProjectEndpoint**](OperationStatusGGitProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateGitSystem"></a>
# **updateGitSystem**
> GGitContentManagementSystem updateGitSystem(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GitSystemsControllerApi;


GitSystemsControllerApi apiInstance = new GitSystemsControllerApi();
GGitContentManagementSystem body = new GGitContentManagementSystem(); // GGitContentManagementSystem | 
try {
    GGitContentManagementSystem result = apiInstance.updateGitSystem(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GitSystemsControllerApi#updateGitSystem");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGitContentManagementSystem**](GGitContentManagementSystem.md)|  |

### Return type

[**GGitContentManagementSystem**](GGitContentManagementSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

