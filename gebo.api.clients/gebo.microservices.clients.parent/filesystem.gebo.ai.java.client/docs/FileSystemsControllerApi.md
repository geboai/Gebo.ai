# FileSystemsControllerApi

All URIs are relative to *http://localhost:13006*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteFilesystemEndpoint**](FileSystemsControllerApi.md#deleteFilesystemEndpoint) | **POST** /api/admin/FileSystemsController/deleteFilesystemEndpoint | 
[**findFileSystemEndpointsByProject**](FileSystemsControllerApi.md#findFileSystemEndpointsByProject) | **GET** /api/admin/FileSystemsController/findFileSystemEndpointsByProject | 
[**findFileSystemEndpointsByQbe**](FileSystemsControllerApi.md#findFileSystemEndpointsByQbe) | **POST** /api/admin/FileSystemsController/findFileSystemEndpointsByQbe | 
[**getFileSystemSystemTypes**](FileSystemsControllerApi.md#getFileSystemSystemTypes) | **GET** /api/admin/FileSystemsController/getFileSystemSystemTypes | 
[**getFileSystemSystems**](FileSystemsControllerApi.md#getFileSystemSystems) | **GET** /api/admin/FileSystemsController/getFileSystemSystems | 
[**insertFilesystemEndpoint**](FileSystemsControllerApi.md#insertFilesystemEndpoint) | **POST** /api/admin/FileSystemsController/insertFilesystemEndpoint | 
[**publishFilesystemEndpoint**](FileSystemsControllerApi.md#publishFilesystemEndpoint) | **POST** /api/admin/FileSystemsController/publishFilesystemEndpoint | 
[**updateFilesystemEndpoint**](FileSystemsControllerApi.md#updateFilesystemEndpoint) | **POST** /api/admin/FileSystemsController/updateFilesystemEndpoint | 

<a name="deleteFilesystemEndpoint"></a>
# **deleteFilesystemEndpoint**
> deleteFilesystemEndpoint(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.filesystem.invoker.ApiException;
//import gebo.microservices.api.client.filesystem.api.FileSystemsControllerApi;


FileSystemsControllerApi apiInstance = new FileSystemsControllerApi();
GFilesystemProjectEndpoint body = new GFilesystemProjectEndpoint(); // GFilesystemProjectEndpoint | 
try {
    apiInstance.deleteFilesystemEndpoint(body);
} catch (ApiException e) {
    System.err.println("Exception when calling FileSystemsControllerApi#deleteFilesystemEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GFilesystemProjectEndpoint**](GFilesystemProjectEndpoint.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="findFileSystemEndpointsByProject"></a>
# **findFileSystemEndpointsByProject**
> Object findFileSystemEndpointsByProject(parentProjectCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.filesystem.invoker.ApiException;
//import gebo.microservices.api.client.filesystem.api.FileSystemsControllerApi;


FileSystemsControllerApi apiInstance = new FileSystemsControllerApi();
Object parentProjectCode = null; // Object | 
try {
    Object result = apiInstance.findFileSystemEndpointsByProject(parentProjectCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FileSystemsControllerApi#findFileSystemEndpointsByProject");
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

<a name="findFileSystemEndpointsByQbe"></a>
# **findFileSystemEndpointsByQbe**
> Object findFileSystemEndpointsByQbe(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.filesystem.invoker.ApiException;
//import gebo.microservices.api.client.filesystem.api.FileSystemsControllerApi;


FileSystemsControllerApi apiInstance = new FileSystemsControllerApi();
GFilesystemProjectEndpoint body = new GFilesystemProjectEndpoint(); // GFilesystemProjectEndpoint | 
try {
    Object result = apiInstance.findFileSystemEndpointsByQbe(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FileSystemsControllerApi#findFileSystemEndpointsByQbe");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GFilesystemProjectEndpoint**](GFilesystemProjectEndpoint.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getFileSystemSystemTypes"></a>
# **getFileSystemSystemTypes**
> Object getFileSystemSystemTypes()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.filesystem.invoker.ApiException;
//import gebo.microservices.api.client.filesystem.api.FileSystemsControllerApi;


FileSystemsControllerApi apiInstance = new FileSystemsControllerApi();
try {
    Object result = apiInstance.getFileSystemSystemTypes();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FileSystemsControllerApi#getFileSystemSystemTypes");
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
 - **Accept**: application/json

<a name="getFileSystemSystems"></a>
# **getFileSystemSystems**
> Object getFileSystemSystems(handlerCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.filesystem.invoker.ApiException;
//import gebo.microservices.api.client.filesystem.api.FileSystemsControllerApi;


FileSystemsControllerApi apiInstance = new FileSystemsControllerApi();
Object handlerCode = null; // Object | 
try {
    Object result = apiInstance.getFileSystemSystems(handlerCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FileSystemsControllerApi#getFileSystemSystems");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **handlerCode** | [**Object**](.md)|  | [optional]

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="insertFilesystemEndpoint"></a>
# **insertFilesystemEndpoint**
> GFilesystemProjectEndpoint insertFilesystemEndpoint(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.filesystem.invoker.ApiException;
//import gebo.microservices.api.client.filesystem.api.FileSystemsControllerApi;


FileSystemsControllerApi apiInstance = new FileSystemsControllerApi();
GFilesystemProjectEndpoint body = new GFilesystemProjectEndpoint(); // GFilesystemProjectEndpoint | 
try {
    GFilesystemProjectEndpoint result = apiInstance.insertFilesystemEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FileSystemsControllerApi#insertFilesystemEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GFilesystemProjectEndpoint**](GFilesystemProjectEndpoint.md)|  |

### Return type

[**GFilesystemProjectEndpoint**](GFilesystemProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="publishFilesystemEndpoint"></a>
# **publishFilesystemEndpoint**
> OperationStatusGJobStatus publishFilesystemEndpoint(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.filesystem.invoker.ApiException;
//import gebo.microservices.api.client.filesystem.api.FileSystemsControllerApi;


FileSystemsControllerApi apiInstance = new FileSystemsControllerApi();
GFilesystemProjectEndpoint body = new GFilesystemProjectEndpoint(); // GFilesystemProjectEndpoint | 
try {
    OperationStatusGJobStatus result = apiInstance.publishFilesystemEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FileSystemsControllerApi#publishFilesystemEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GFilesystemProjectEndpoint**](GFilesystemProjectEndpoint.md)|  |

### Return type

[**OperationStatusGJobStatus**](OperationStatusGJobStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateFilesystemEndpoint"></a>
# **updateFilesystemEndpoint**
> GFilesystemProjectEndpoint updateFilesystemEndpoint(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.filesystem.invoker.ApiException;
//import gebo.microservices.api.client.filesystem.api.FileSystemsControllerApi;


FileSystemsControllerApi apiInstance = new FileSystemsControllerApi();
GFilesystemProjectEndpoint body = new GFilesystemProjectEndpoint(); // GFilesystemProjectEndpoint | 
try {
    GFilesystemProjectEndpoint result = apiInstance.updateFilesystemEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FileSystemsControllerApi#updateFilesystemEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GFilesystemProjectEndpoint**](GFilesystemProjectEndpoint.md)|  |

### Return type

[**GFilesystemProjectEndpoint**](GFilesystemProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

