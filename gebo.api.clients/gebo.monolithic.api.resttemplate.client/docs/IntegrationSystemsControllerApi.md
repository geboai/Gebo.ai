# IntegrationSystemsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteIntegrationProjectEndpoint**](IntegrationSystemsControllerApi.md#deleteIntegrationProjectEndpoint) | **POST** /api/admin/IntegrationSystemsController/deleteIntegrationProjectEndpoint | 
[**findIntegrationEndpointsByProject**](IntegrationSystemsControllerApi.md#findIntegrationEndpointsByProject) | **GET** /api/admin/IntegrationSystemsController/findIntegrationEndpointsByProject | 
[**insertIntegrationProjectEndpoint**](IntegrationSystemsControllerApi.md#insertIntegrationProjectEndpoint) | **POST** /api/admin/IntegrationSystemsController/insertIntegrationProjectEndpoint | 
[**updateIntegrationProjectEndpoint**](IntegrationSystemsControllerApi.md#updateIntegrationProjectEndpoint) | **POST** /api/admin/IntegrationSystemsController/updateIntegrationProjectEndpoint | 

<a name="deleteIntegrationProjectEndpoint"></a>
# **deleteIntegrationProjectEndpoint**
> deleteIntegrationProjectEndpoint(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.IntegrationSystemsControllerApi;


IntegrationSystemsControllerApi apiInstance = new IntegrationSystemsControllerApi();
GIntegrationProjectEndpoint body = new GIntegrationProjectEndpoint(); // GIntegrationProjectEndpoint | 
try {
    apiInstance.deleteIntegrationProjectEndpoint(body);
} catch (ApiException e) {
    System.err.println("Exception when calling IntegrationSystemsControllerApi#deleteIntegrationProjectEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GIntegrationProjectEndpoint**](GIntegrationProjectEndpoint.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="findIntegrationEndpointsByProject"></a>
# **findIntegrationEndpointsByProject**
> List&lt;GIntegrationProjectEndpoint&gt; findIntegrationEndpointsByProject(parentProjectCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.IntegrationSystemsControllerApi;


IntegrationSystemsControllerApi apiInstance = new IntegrationSystemsControllerApi();
String parentProjectCode = "parentProjectCode_example"; // String | 
try {
    List<GIntegrationProjectEndpoint> result = apiInstance.findIntegrationEndpointsByProject(parentProjectCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IntegrationSystemsControllerApi#findIntegrationEndpointsByProject");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **parentProjectCode** | **String**|  |

### Return type

[**List&lt;GIntegrationProjectEndpoint&gt;**](GIntegrationProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="insertIntegrationProjectEndpoint"></a>
# **insertIntegrationProjectEndpoint**
> GIntegrationProjectEndpoint insertIntegrationProjectEndpoint(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.IntegrationSystemsControllerApi;


IntegrationSystemsControllerApi apiInstance = new IntegrationSystemsControllerApi();
GIntegrationProjectEndpoint body = new GIntegrationProjectEndpoint(); // GIntegrationProjectEndpoint | 
try {
    GIntegrationProjectEndpoint result = apiInstance.insertIntegrationProjectEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IntegrationSystemsControllerApi#insertIntegrationProjectEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GIntegrationProjectEndpoint**](GIntegrationProjectEndpoint.md)|  |

### Return type

[**GIntegrationProjectEndpoint**](GIntegrationProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateIntegrationProjectEndpoint"></a>
# **updateIntegrationProjectEndpoint**
> GIntegrationProjectEndpoint updateIntegrationProjectEndpoint(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.IntegrationSystemsControllerApi;


IntegrationSystemsControllerApi apiInstance = new IntegrationSystemsControllerApi();
GIntegrationProjectEndpoint body = new GIntegrationProjectEndpoint(); // GIntegrationProjectEndpoint | 
try {
    GIntegrationProjectEndpoint result = apiInstance.updateIntegrationProjectEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IntegrationSystemsControllerApi#updateIntegrationProjectEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GIntegrationProjectEndpoint**](GIntegrationProjectEndpoint.md)|  |

### Return type

[**GIntegrationProjectEndpoint**](GIntegrationProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

