# IntegrationSystemsControllerApi

All URIs are relative to *http://localhost:13015*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteIntegrationProjectEndpoint**](IntegrationSystemsControllerApi.md#deleteIntegrationProjectEndpoint) | **POST** /api/admin/IntegrationSystemsController/deleteIntegrationProjectEndpoint | 
[**findIntegrationEndpointsByProject**](IntegrationSystemsControllerApi.md#findIntegrationEndpointsByProject) | **GET** /api/admin/IntegrationSystemsController/findIntegrationEndpointsByProject | 
[**insertIntegrationProjectEndpoint**](IntegrationSystemsControllerApi.md#insertIntegrationProjectEndpoint) | **POST** /api/admin/IntegrationSystemsController/insertIntegrationProjectEndpoint | 
[**publishIntegrationProjectEndpoint**](IntegrationSystemsControllerApi.md#publishIntegrationProjectEndpoint) | **POST** /api/admin/IntegrationSystemsController/publishIntegrationProjectEndpoint | 
[**updateIntegrationProjectEndpoint**](IntegrationSystemsControllerApi.md#updateIntegrationProjectEndpoint) | **POST** /api/admin/IntegrationSystemsController/updateIntegrationProjectEndpoint | 

<a name="deleteIntegrationProjectEndpoint"></a>
# **deleteIntegrationProjectEndpoint**
> deleteIntegrationProjectEndpoint(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.integration.invoker.ApiException;
//import gebo.microservices.api.client.integration.api.IntegrationSystemsControllerApi;


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
> Object findIntegrationEndpointsByProject(parentProjectCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.integration.invoker.ApiException;
//import gebo.microservices.api.client.integration.api.IntegrationSystemsControllerApi;


IntegrationSystemsControllerApi apiInstance = new IntegrationSystemsControllerApi();
Object parentProjectCode = null; // Object | 
try {
    Object result = apiInstance.findIntegrationEndpointsByProject(parentProjectCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IntegrationSystemsControllerApi#findIntegrationEndpointsByProject");
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

<a name="insertIntegrationProjectEndpoint"></a>
# **insertIntegrationProjectEndpoint**
> GIntegrationProjectEndpoint insertIntegrationProjectEndpoint(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.integration.invoker.ApiException;
//import gebo.microservices.api.client.integration.api.IntegrationSystemsControllerApi;


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

<a name="publishIntegrationProjectEndpoint"></a>
# **publishIntegrationProjectEndpoint**
> OperationStatusGJobStatus publishIntegrationProjectEndpoint(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.integration.invoker.ApiException;
//import gebo.microservices.api.client.integration.api.IntegrationSystemsControllerApi;


IntegrationSystemsControllerApi apiInstance = new IntegrationSystemsControllerApi();
GIntegrationProjectEndpoint body = new GIntegrationProjectEndpoint(); // GIntegrationProjectEndpoint | 
try {
    OperationStatusGJobStatus result = apiInstance.publishIntegrationProjectEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IntegrationSystemsControllerApi#publishIntegrationProjectEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GIntegrationProjectEndpoint**](GIntegrationProjectEndpoint.md)|  |

### Return type

[**OperationStatusGJobStatus**](OperationStatusGJobStatus.md)

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
//import gebo.microservices.api.client.integration.invoker.ApiException;
//import gebo.microservices.api.client.integration.api.IntegrationSystemsControllerApi;


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

