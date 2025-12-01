# SharepointSystemsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteSharepointEndpoint**](SharepointSystemsControllerApi.md#deleteSharepointEndpoint) | **POST** /api/admin/SharepointSystemsController/deleteSharepointEndpoint | 
[**deleteSharepointSystem**](SharepointSystemsControllerApi.md#deleteSharepointSystem) | **POST** /api/admin/SharepointSystemsController/deleteSharepointSystem | 
[**fastSharepointConfig**](SharepointSystemsControllerApi.md#fastSharepointConfig) | **POST** /api/admin/SharepointSystemsController/fastSharepointConfig | 
[**findSharepointEndpointsByCode**](SharepointSystemsControllerApi.md#findSharepointEndpointsByCode) | **GET** /api/admin/SharepointSystemsController/findSharepointEndpointsByCode | 
[**findSharepointEndpointsByProject**](SharepointSystemsControllerApi.md#findSharepointEndpointsByProject) | **GET** /api/admin/SharepointSystemsController/findSharepointEndpointsByProject | 
[**findSharepointEndpointsByQbe**](SharepointSystemsControllerApi.md#findSharepointEndpointsByQbe) | **POST** /api/admin/SharepointSystemsController/findSharepointEndpointsByQbe | 
[**findSharepointSystemByCode**](SharepointSystemsControllerApi.md#findSharepointSystemByCode) | **GET** /api/admin/SharepointSystemsController/findSharepointSystemByCode | 
[**getSharepointSystemTypes**](SharepointSystemsControllerApi.md#getSharepointSystemTypes) | **GET** /api/admin/SharepointSystemsController/getSharepointSystemType | 
[**getSharepointSystems**](SharepointSystemsControllerApi.md#getSharepointSystems) | **GET** /api/admin/SharepointSystemsController/getSharepointSystems | 
[**insertSharepointEndpoint**](SharepointSystemsControllerApi.md#insertSharepointEndpoint) | **POST** /api/admin/SharepointSystemsController/insertSharepointEndpoint | 
[**insertSharepointSystem**](SharepointSystemsControllerApi.md#insertSharepointSystem) | **POST** /api/admin/SharepointSystemsController/insertSharepointSystem | 
[**testSharepointSystem**](SharepointSystemsControllerApi.md#testSharepointSystem) | **POST** /api/admin/SharepointSystemsController/testSharepointSystem | 
[**updateSharepointEndpoint**](SharepointSystemsControllerApi.md#updateSharepointEndpoint) | **POST** /api/admin/SharepointSystemsController/updateSharepointEndpoint | 
[**updateSharepointSystem**](SharepointSystemsControllerApi.md#updateSharepointSystem) | **POST** /api/admin/SharepointSystemsController/updateSharepointSystem | 

<a name="deleteSharepointEndpoint"></a>
# **deleteSharepointEndpoint**
> deleteSharepointEndpoint(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharepointSystemsControllerApi;


SharepointSystemsControllerApi apiInstance = new SharepointSystemsControllerApi();
GSharepointProjectEndpoint body = new GSharepointProjectEndpoint(); // GSharepointProjectEndpoint | 
try {
    apiInstance.deleteSharepointEndpoint(body);
} catch (ApiException e) {
    System.err.println("Exception when calling SharepointSystemsControllerApi#deleteSharepointEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GSharepointProjectEndpoint**](GSharepointProjectEndpoint.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="deleteSharepointSystem"></a>
# **deleteSharepointSystem**
> deleteSharepointSystem(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharepointSystemsControllerApi;


SharepointSystemsControllerApi apiInstance = new SharepointSystemsControllerApi();
GSharepointContentManagementSystem body = new GSharepointContentManagementSystem(); // GSharepointContentManagementSystem | 
try {
    apiInstance.deleteSharepointSystem(body);
} catch (ApiException e) {
    System.err.println("Exception when calling SharepointSystemsControllerApi#deleteSharepointSystem");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GSharepointContentManagementSystem**](GSharepointContentManagementSystem.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="fastSharepointConfig"></a>
# **fastSharepointConfig**
> OperationStatusGSharepointContentManagementSystem fastSharepointConfig(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharepointSystemsControllerApi;


SharepointSystemsControllerApi apiInstance = new SharepointSystemsControllerApi();
FastSharepointSystemInsertRequest body = new FastSharepointSystemInsertRequest(); // FastSharepointSystemInsertRequest | 
try {
    OperationStatusGSharepointContentManagementSystem result = apiInstance.fastSharepointConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharepointSystemsControllerApi#fastSharepointConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**FastSharepointSystemInsertRequest**](FastSharepointSystemInsertRequest.md)|  |

### Return type

[**OperationStatusGSharepointContentManagementSystem**](OperationStatusGSharepointContentManagementSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findSharepointEndpointsByCode"></a>
# **findSharepointEndpointsByCode**
> GSharepointProjectEndpoint findSharepointEndpointsByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharepointSystemsControllerApi;


SharepointSystemsControllerApi apiInstance = new SharepointSystemsControllerApi();
String code = "code_example"; // String | 
try {
    GSharepointProjectEndpoint result = apiInstance.findSharepointEndpointsByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharepointSystemsControllerApi#findSharepointEndpointsByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GSharepointProjectEndpoint**](GSharepointProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="findSharepointEndpointsByProject"></a>
# **findSharepointEndpointsByProject**
> List&lt;GSharepointProjectEndpoint&gt; findSharepointEndpointsByProject(parentProjectCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharepointSystemsControllerApi;


SharepointSystemsControllerApi apiInstance = new SharepointSystemsControllerApi();
String parentProjectCode = "parentProjectCode_example"; // String | 
try {
    List<GSharepointProjectEndpoint> result = apiInstance.findSharepointEndpointsByProject(parentProjectCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharepointSystemsControllerApi#findSharepointEndpointsByProject");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **parentProjectCode** | **String**|  |

### Return type

[**List&lt;GSharepointProjectEndpoint&gt;**](GSharepointProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="findSharepointEndpointsByQbe"></a>
# **findSharepointEndpointsByQbe**
> List&lt;GSharepointProjectEndpoint&gt; findSharepointEndpointsByQbe(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharepointSystemsControllerApi;


SharepointSystemsControllerApi apiInstance = new SharepointSystemsControllerApi();
GSharepointProjectEndpoint body = new GSharepointProjectEndpoint(); // GSharepointProjectEndpoint | 
try {
    List<GSharepointProjectEndpoint> result = apiInstance.findSharepointEndpointsByQbe(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharepointSystemsControllerApi#findSharepointEndpointsByQbe");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GSharepointProjectEndpoint**](GSharepointProjectEndpoint.md)|  |

### Return type

[**List&lt;GSharepointProjectEndpoint&gt;**](GSharepointProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findSharepointSystemByCode"></a>
# **findSharepointSystemByCode**
> GSharepointContentManagementSystem findSharepointSystemByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharepointSystemsControllerApi;


SharepointSystemsControllerApi apiInstance = new SharepointSystemsControllerApi();
String code = "code_example"; // String | 
try {
    GSharepointContentManagementSystem result = apiInstance.findSharepointSystemByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharepointSystemsControllerApi#findSharepointSystemByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**GSharepointContentManagementSystem**](GSharepointContentManagementSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="getSharepointSystemTypes"></a>
# **getSharepointSystemTypes**
> GContentManagementSystemType getSharepointSystemTypes()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharepointSystemsControllerApi;


SharepointSystemsControllerApi apiInstance = new SharepointSystemsControllerApi();
try {
    GContentManagementSystemType result = apiInstance.getSharepointSystemTypes();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharepointSystemsControllerApi#getSharepointSystemTypes");
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

<a name="getSharepointSystems"></a>
# **getSharepointSystems**
> List&lt;GSharepointContentManagementSystem&gt; getSharepointSystems()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharepointSystemsControllerApi;


SharepointSystemsControllerApi apiInstance = new SharepointSystemsControllerApi();
try {
    List<GSharepointContentManagementSystem> result = apiInstance.getSharepointSystems();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharepointSystemsControllerApi#getSharepointSystems");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GSharepointContentManagementSystem&gt;**](GSharepointContentManagementSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="insertSharepointEndpoint"></a>
# **insertSharepointEndpoint**
> GSharepointProjectEndpoint insertSharepointEndpoint(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharepointSystemsControllerApi;


SharepointSystemsControllerApi apiInstance = new SharepointSystemsControllerApi();
GSharepointProjectEndpoint body = new GSharepointProjectEndpoint(); // GSharepointProjectEndpoint | 
try {
    GSharepointProjectEndpoint result = apiInstance.insertSharepointEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharepointSystemsControllerApi#insertSharepointEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GSharepointProjectEndpoint**](GSharepointProjectEndpoint.md)|  |

### Return type

[**GSharepointProjectEndpoint**](GSharepointProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertSharepointSystem"></a>
# **insertSharepointSystem**
> OperationStatusGSharepointContentManagementSystem insertSharepointSystem(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharepointSystemsControllerApi;


SharepointSystemsControllerApi apiInstance = new SharepointSystemsControllerApi();
GSharepointContentManagementSystem body = new GSharepointContentManagementSystem(); // GSharepointContentManagementSystem | 
try {
    OperationStatusGSharepointContentManagementSystem result = apiInstance.insertSharepointSystem(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharepointSystemsControllerApi#insertSharepointSystem");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GSharepointContentManagementSystem**](GSharepointContentManagementSystem.md)|  |

### Return type

[**OperationStatusGSharepointContentManagementSystem**](OperationStatusGSharepointContentManagementSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="testSharepointSystem"></a>
# **testSharepointSystem**
> OperationStatusGSharepointContentManagementSystem testSharepointSystem(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharepointSystemsControllerApi;


SharepointSystemsControllerApi apiInstance = new SharepointSystemsControllerApi();
GSharepointContentManagementSystem body = new GSharepointContentManagementSystem(); // GSharepointContentManagementSystem | 
try {
    OperationStatusGSharepointContentManagementSystem result = apiInstance.testSharepointSystem(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharepointSystemsControllerApi#testSharepointSystem");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GSharepointContentManagementSystem**](GSharepointContentManagementSystem.md)|  |

### Return type

[**OperationStatusGSharepointContentManagementSystem**](OperationStatusGSharepointContentManagementSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateSharepointEndpoint"></a>
# **updateSharepointEndpoint**
> GSharepointProjectEndpoint updateSharepointEndpoint(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharepointSystemsControllerApi;


SharepointSystemsControllerApi apiInstance = new SharepointSystemsControllerApi();
GSharepointProjectEndpoint body = new GSharepointProjectEndpoint(); // GSharepointProjectEndpoint | 
try {
    GSharepointProjectEndpoint result = apiInstance.updateSharepointEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharepointSystemsControllerApi#updateSharepointEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GSharepointProjectEndpoint**](GSharepointProjectEndpoint.md)|  |

### Return type

[**GSharepointProjectEndpoint**](GSharepointProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateSharepointSystem"></a>
# **updateSharepointSystem**
> OperationStatusGSharepointContentManagementSystem updateSharepointSystem(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SharepointSystemsControllerApi;


SharepointSystemsControllerApi apiInstance = new SharepointSystemsControllerApi();
GSharepointContentManagementSystem body = new GSharepointContentManagementSystem(); // GSharepointContentManagementSystem | 
try {
    OperationStatusGSharepointContentManagementSystem result = apiInstance.updateSharepointSystem(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SharepointSystemsControllerApi#updateSharepointSystem");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GSharepointContentManagementSystem**](GSharepointContentManagementSystem.md)|  |

### Return type

[**OperationStatusGSharepointContentManagementSystem**](OperationStatusGSharepointContentManagementSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

