# FileSystemsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteFilesystemEndpoint**](FileSystemsControllerApi.md#deleteFilesystemEndpoint) | **POST** /api/admin/FileSystemsController/deleteFilesystemEndpoint | 
[**findFileSystemEndpointsByProject**](FileSystemsControllerApi.md#findFileSystemEndpointsByProject) | **GET** /api/admin/FileSystemsController/findFileSystemEndpointsByProject | 
[**findFileSystemEndpointsByQbe**](FileSystemsControllerApi.md#findFileSystemEndpointsByQbe) | **POST** /api/admin/FileSystemsController/findFileSystemEndpointsByQbe | 
[**getFileSystemSystemTypes1**](FileSystemsControllerApi.md#getFileSystemSystemTypes1) | **GET** /api/admin/FileSystemsController/getFileSystemSystemTypes | 
[**getFileSystemSystems**](FileSystemsControllerApi.md#getFileSystemSystems) | **GET** /api/admin/FileSystemsController/getFileSystemSystems | 
[**insertFilesystemEndpoint**](FileSystemsControllerApi.md#insertFilesystemEndpoint) | **POST** /api/admin/FileSystemsController/insertFilesystemEndpoint | 
[**updateFilesystemEndpoint**](FileSystemsControllerApi.md#updateFilesystemEndpoint) | **POST** /api/admin/FileSystemsController/updateFilesystemEndpoint | 

<a name="deleteFilesystemEndpoint"></a>
# **deleteFilesystemEndpoint**
> deleteFilesystemEndpoint(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.FileSystemsControllerApi;


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
> List&lt;GFilesystemProjectEndpoint&gt; findFileSystemEndpointsByProject(parentProjectCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.FileSystemsControllerApi;


FileSystemsControllerApi apiInstance = new FileSystemsControllerApi();
String parentProjectCode = "parentProjectCode_example"; // String | 
try {
    List<GFilesystemProjectEndpoint> result = apiInstance.findFileSystemEndpointsByProject(parentProjectCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FileSystemsControllerApi#findFileSystemEndpointsByProject");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **parentProjectCode** | **String**|  |

### Return type

[**List&lt;GFilesystemProjectEndpoint&gt;**](GFilesystemProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="findFileSystemEndpointsByQbe"></a>
# **findFileSystemEndpointsByQbe**
> List&lt;GFilesystemProjectEndpoint&gt; findFileSystemEndpointsByQbe(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.FileSystemsControllerApi;


FileSystemsControllerApi apiInstance = new FileSystemsControllerApi();
GFilesystemProjectEndpoint body = new GFilesystemProjectEndpoint(); // GFilesystemProjectEndpoint | 
try {
    List<GFilesystemProjectEndpoint> result = apiInstance.findFileSystemEndpointsByQbe(body);
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

[**List&lt;GFilesystemProjectEndpoint&gt;**](GFilesystemProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getFileSystemSystemTypes1"></a>
# **getFileSystemSystemTypes1**
> List&lt;GContentManagementSystemType&gt; getFileSystemSystemTypes1()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.FileSystemsControllerApi;


FileSystemsControllerApi apiInstance = new FileSystemsControllerApi();
try {
    List<GContentManagementSystemType> result = apiInstance.getFileSystemSystemTypes1();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FileSystemsControllerApi#getFileSystemSystemTypes1");
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

<a name="getFileSystemSystems"></a>
# **getFileSystemSystems**
> List&lt;GFilesystemContentManagementSystem&gt; getFileSystemSystems(handlerCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.FileSystemsControllerApi;


FileSystemsControllerApi apiInstance = new FileSystemsControllerApi();
String handlerCode = "handlerCode_example"; // String | 
try {
    List<GFilesystemContentManagementSystem> result = apiInstance.getFileSystemSystems(handlerCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FileSystemsControllerApi#getFileSystemSystems");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **handlerCode** | **String**|  | [optional]

### Return type

[**List&lt;GFilesystemContentManagementSystem&gt;**](GFilesystemContentManagementSystem.md)

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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.FileSystemsControllerApi;


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

<a name="updateFilesystemEndpoint"></a>
# **updateFilesystemEndpoint**
> GFilesystemProjectEndpoint updateFilesystemEndpoint(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.FileSystemsControllerApi;


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

