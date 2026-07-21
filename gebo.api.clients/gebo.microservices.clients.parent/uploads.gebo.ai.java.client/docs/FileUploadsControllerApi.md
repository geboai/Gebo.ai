# FileUploadsControllerApi

All URIs are relative to *http://localhost:13007/uploads*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteUploadsEndpoint**](FileUploadsControllerApi.md#deleteUploadsEndpoint) | **POST** /api/admin/FileUploadsController/deleteUploadsEndpoint | 
[**findUploadsEndpointsByProject**](FileUploadsControllerApi.md#findUploadsEndpointsByProject) | **GET** /api/admin/FileUploadsController/findUploadsEndpointsByProject | 
[**findUploadsEndpointsByQbe**](FileUploadsControllerApi.md#findUploadsEndpointsByQbe) | **POST** /api/admin/FileUploadsController/findUploadsEndpointsByQbe | 
[**getFileSystemSystemTypes**](FileUploadsControllerApi.md#getFileSystemSystemTypes) | **GET** /api/admin/FileUploadsController/getFileSystemSystemTypes | 
[**getUploadableFilesExtensions**](FileUploadsControllerApi.md#getUploadableFilesExtensions) | **GET** /api/admin/FileUploadsController/getUploadableFilesExtensions | 
[**getUploadsSystems**](FileUploadsControllerApi.md#getUploadsSystems) | **GET** /api/admin/FileUploadsController/getUploadsSystems | 
[**insertUploadsEndpoint**](FileUploadsControllerApi.md#insertUploadsEndpoint) | **POST** /api/admin/FileUploadsController/insertUploadsEndpoint | 
[**publishUploadsEndpoint**](FileUploadsControllerApi.md#publishUploadsEndpoint) | **POST** /api/admin/FileUploadsController/publishUploadsEndpoint | 
[**updateUploadsEndpoint**](FileUploadsControllerApi.md#updateUploadsEndpoint) | **POST** /api/admin/FileUploadsController/updateUploadsEndpoint | 

<a name="deleteUploadsEndpoint"></a>
# **deleteUploadsEndpoint**
> deleteUploadsEndpoint(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.uploads.invoker.ApiException;
//import gebo.microservices.api.client.uploads.api.FileUploadsControllerApi;


FileUploadsControllerApi apiInstance = new FileUploadsControllerApi();
GUploadsProjectEndpoint body = new GUploadsProjectEndpoint(); // GUploadsProjectEndpoint | 
try {
    apiInstance.deleteUploadsEndpoint(body);
} catch (ApiException e) {
    System.err.println("Exception when calling FileUploadsControllerApi#deleteUploadsEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GUploadsProjectEndpoint**](GUploadsProjectEndpoint.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="findUploadsEndpointsByProject"></a>
# **findUploadsEndpointsByProject**
> Object findUploadsEndpointsByProject(parentProjectCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.uploads.invoker.ApiException;
//import gebo.microservices.api.client.uploads.api.FileUploadsControllerApi;


FileUploadsControllerApi apiInstance = new FileUploadsControllerApi();
Object parentProjectCode = null; // Object | 
try {
    Object result = apiInstance.findUploadsEndpointsByProject(parentProjectCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FileUploadsControllerApi#findUploadsEndpointsByProject");
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
 - **Accept**: application/json

<a name="findUploadsEndpointsByQbe"></a>
# **findUploadsEndpointsByQbe**
> Object findUploadsEndpointsByQbe(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.uploads.invoker.ApiException;
//import gebo.microservices.api.client.uploads.api.FileUploadsControllerApi;


FileUploadsControllerApi apiInstance = new FileUploadsControllerApi();
GUploadsProjectEndpoint body = new GUploadsProjectEndpoint(); // GUploadsProjectEndpoint | 
try {
    Object result = apiInstance.findUploadsEndpointsByQbe(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FileUploadsControllerApi#findUploadsEndpointsByQbe");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GUploadsProjectEndpoint**](GUploadsProjectEndpoint.md)|  |

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
//import gebo.microservices.api.client.uploads.invoker.ApiException;
//import gebo.microservices.api.client.uploads.api.FileUploadsControllerApi;


FileUploadsControllerApi apiInstance = new FileUploadsControllerApi();
try {
    Object result = apiInstance.getFileSystemSystemTypes();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FileUploadsControllerApi#getFileSystemSystemTypes");
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

<a name="getUploadableFilesExtensions"></a>
# **getUploadableFilesExtensions**
> Object getUploadableFilesExtensions()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.uploads.invoker.ApiException;
//import gebo.microservices.api.client.uploads.api.FileUploadsControllerApi;


FileUploadsControllerApi apiInstance = new FileUploadsControllerApi();
try {
    Object result = apiInstance.getUploadableFilesExtensions();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FileUploadsControllerApi#getUploadableFilesExtensions");
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

<a name="getUploadsSystems"></a>
# **getUploadsSystems**
> Object getUploadsSystems(handlerCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.uploads.invoker.ApiException;
//import gebo.microservices.api.client.uploads.api.FileUploadsControllerApi;


FileUploadsControllerApi apiInstance = new FileUploadsControllerApi();
Object handlerCode = null; // Object | 
try {
    Object result = apiInstance.getUploadsSystems(handlerCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FileUploadsControllerApi#getUploadsSystems");
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

<a name="insertUploadsEndpoint"></a>
# **insertUploadsEndpoint**
> GUploadsProjectEndpoint insertUploadsEndpoint(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.uploads.invoker.ApiException;
//import gebo.microservices.api.client.uploads.api.FileUploadsControllerApi;


FileUploadsControllerApi apiInstance = new FileUploadsControllerApi();
GUploadsProjectEndpoint body = new GUploadsProjectEndpoint(); // GUploadsProjectEndpoint | 
try {
    GUploadsProjectEndpoint result = apiInstance.insertUploadsEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FileUploadsControllerApi#insertUploadsEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GUploadsProjectEndpoint**](GUploadsProjectEndpoint.md)|  |

### Return type

[**GUploadsProjectEndpoint**](GUploadsProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="publishUploadsEndpoint"></a>
# **publishUploadsEndpoint**
> OperationStatusGJobStatus publishUploadsEndpoint(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.uploads.invoker.ApiException;
//import gebo.microservices.api.client.uploads.api.FileUploadsControllerApi;


FileUploadsControllerApi apiInstance = new FileUploadsControllerApi();
GUploadsProjectEndpoint body = new GUploadsProjectEndpoint(); // GUploadsProjectEndpoint | 
try {
    OperationStatusGJobStatus result = apiInstance.publishUploadsEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FileUploadsControllerApi#publishUploadsEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GUploadsProjectEndpoint**](GUploadsProjectEndpoint.md)|  |

### Return type

[**OperationStatusGJobStatus**](OperationStatusGJobStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateUploadsEndpoint"></a>
# **updateUploadsEndpoint**
> GUploadsProjectEndpoint updateUploadsEndpoint(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.uploads.invoker.ApiException;
//import gebo.microservices.api.client.uploads.api.FileUploadsControllerApi;


FileUploadsControllerApi apiInstance = new FileUploadsControllerApi();
GUploadsProjectEndpoint body = new GUploadsProjectEndpoint(); // GUploadsProjectEndpoint | 
try {
    GUploadsProjectEndpoint result = apiInstance.updateUploadsEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FileUploadsControllerApi#updateUploadsEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GUploadsProjectEndpoint**](GUploadsProjectEndpoint.md)|  |

### Return type

[**GUploadsProjectEndpoint**](GUploadsProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

