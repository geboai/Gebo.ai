# CompanySystemsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getContentSystem**](CompanySystemsControllerApi.md#getContentSystem) | **GET** /api/admin/CompanySystemsController/getContentSystem | 
[**getContentSystemType**](CompanySystemsControllerApi.md#getContentSystemType) | **GET** /api/admin/CompanySystemsController/getContentSystemType | 
[**getContentSystemTypes**](CompanySystemsControllerApi.md#getContentSystemTypes) | **GET** /api/admin/CompanySystemsController/getContentSystemTypes() | 
[**getContentSystems**](CompanySystemsControllerApi.md#getContentSystems) | **GET** /api/admin/CompanySystemsController/getContentSystems | 
[**getProjectEndpoint**](CompanySystemsControllerApi.md#getProjectEndpoint) | **GET** /api/admin/CompanySystemsController/getProjectEndpoint | 
[**getProjectEndpointByObjectRef**](CompanySystemsControllerApi.md#getProjectEndpointByObjectRef) | **POST** /api/admin/CompanySystemsController/getProjectEndpointByObjectRef | 
[**getProjectEndpointSystemInfos**](CompanySystemsControllerApi.md#getProjectEndpointSystemInfos) | **POST** /api/admin/CompanySystemsController/getProjectEndpointSystemInfos | 

<a name="getContentSystem"></a>
# **getContentSystem**
> GContentManagementSystem getContentSystem(systemTypeCode, systemCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.CompanySystemsControllerApi;


CompanySystemsControllerApi apiInstance = new CompanySystemsControllerApi();
String systemTypeCode = "systemTypeCode_example"; // String | 
String systemCode = "systemCode_example"; // String | 
try {
    GContentManagementSystem result = apiInstance.getContentSystem(systemTypeCode, systemCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CompanySystemsControllerApi#getContentSystem");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **systemTypeCode** | **String**|  |
 **systemCode** | **String**|  |

### Return type

[**GContentManagementSystem**](GContentManagementSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getContentSystemType"></a>
# **getContentSystemType**
> GContentManagementSystemType getContentSystemType(systemTypeCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.CompanySystemsControllerApi;


CompanySystemsControllerApi apiInstance = new CompanySystemsControllerApi();
String systemTypeCode = "systemTypeCode_example"; // String | 
try {
    GContentManagementSystemType result = apiInstance.getContentSystemType(systemTypeCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CompanySystemsControllerApi#getContentSystemType");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **systemTypeCode** | **String**|  |

### Return type

[**GContentManagementSystemType**](GContentManagementSystemType.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getContentSystemTypes"></a>
# **getContentSystemTypes**
> List&lt;GContentManagementSystemType&gt; getContentSystemTypes()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.CompanySystemsControllerApi;


CompanySystemsControllerApi apiInstance = new CompanySystemsControllerApi();
try {
    List<GContentManagementSystemType> result = apiInstance.getContentSystemTypes();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CompanySystemsControllerApi#getContentSystemTypes");
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

<a name="getContentSystems"></a>
# **getContentSystems**
> List&lt;GContentManagementSystem&gt; getContentSystems()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.CompanySystemsControllerApi;


CompanySystemsControllerApi apiInstance = new CompanySystemsControllerApi();
try {
    List<GContentManagementSystem> result = apiInstance.getContentSystems();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CompanySystemsControllerApi#getContentSystems");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GContentManagementSystem&gt;**](GContentManagementSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getProjectEndpoint"></a>
# **getProjectEndpoint**
> GProjectEndpoint getProjectEndpoint(systemTypeCode, systemCode, projectEndpointCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.CompanySystemsControllerApi;


CompanySystemsControllerApi apiInstance = new CompanySystemsControllerApi();
String systemTypeCode = "systemTypeCode_example"; // String | 
String systemCode = "systemCode_example"; // String | 
String projectEndpointCode = "projectEndpointCode_example"; // String | 
try {
    GProjectEndpoint result = apiInstance.getProjectEndpoint(systemTypeCode, systemCode, projectEndpointCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CompanySystemsControllerApi#getProjectEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **systemTypeCode** | **String**|  |
 **systemCode** | **String**|  |
 **projectEndpointCode** | **String**|  |

### Return type

[**GProjectEndpoint**](GProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getProjectEndpointByObjectRef"></a>
# **getProjectEndpointByObjectRef**
> GProjectEndpoint getProjectEndpointByObjectRef(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.CompanySystemsControllerApi;


CompanySystemsControllerApi apiInstance = new CompanySystemsControllerApi();
GObjectRefGProjectEndpoint body = new GObjectRefGProjectEndpoint(); // GObjectRefGProjectEndpoint | 
try {
    GProjectEndpoint result = apiInstance.getProjectEndpointByObjectRef(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CompanySystemsControllerApi#getProjectEndpointByObjectRef");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GObjectRefGProjectEndpoint**](GObjectRefGProjectEndpoint.md)|  |

### Return type

[**GProjectEndpoint**](GProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getProjectEndpointSystemInfos"></a>
# **getProjectEndpointSystemInfos**
> SystemInfos getProjectEndpointSystemInfos(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.CompanySystemsControllerApi;


CompanySystemsControllerApi apiInstance = new CompanySystemsControllerApi();
GObjectRefGProjectEndpoint body = new GObjectRefGProjectEndpoint(); // GObjectRefGProjectEndpoint | 
try {
    SystemInfos result = apiInstance.getProjectEndpointSystemInfos(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CompanySystemsControllerApi#getProjectEndpointSystemInfos");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GObjectRefGProjectEndpoint**](GObjectRefGProjectEndpoint.md)|  |

### Return type

[**SystemInfos**](SystemInfos.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

