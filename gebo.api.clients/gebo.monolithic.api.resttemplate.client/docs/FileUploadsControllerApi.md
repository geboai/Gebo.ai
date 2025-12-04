# FileUploadsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteUploadsEndpoint**](FileUploadsControllerApi.md#deleteUploadsEndpoint) | **POST** /api/admin/FileUploadsController/deleteUploadsEndpoint | 
[**findUploadsEndpointsByProject**](FileUploadsControllerApi.md#findUploadsEndpointsByProject) | **GET** /api/admin/FileUploadsController/findUploadsEndpointsByProject | 
[**findUploadsEndpointsByQbe**](FileUploadsControllerApi.md#findUploadsEndpointsByQbe) | **POST** /api/admin/FileUploadsController/findUploadsEndpointsByQbe | 
[**getFileSystemSystemTypes**](FileUploadsControllerApi.md#getFileSystemSystemTypes) | **GET** /api/admin/FileUploadsController/getFileSystemSystemTypes | 
[**getUploadableFilesExtensions**](FileUploadsControllerApi.md#getUploadableFilesExtensions) | **GET** /api/admin/FileUploadsController/getUploadableFilesExtensions | 
[**getUploadsSystems**](FileUploadsControllerApi.md#getUploadsSystems) | **GET** /api/admin/FileUploadsController/getUploadsSystems | 
[**insertUploadsEndpoint**](FileUploadsControllerApi.md#insertUploadsEndpoint) | **POST** /api/admin/FileUploadsController/insertUploadsEndpoint | 
[**updateUploadsEndpoint**](FileUploadsControllerApi.md#updateUploadsEndpoint) | **POST** /api/admin/FileUploadsController/updateUploadsEndpoint | 

<a name="deleteUploadsEndpoint"></a>
# **deleteUploadsEndpoint**
> deleteUploadsEndpoint(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.FileUploadsControllerApi;


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
> List&lt;GUploadsProjectEndpoint&gt; findUploadsEndpointsByProject(parentProjectCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.FileUploadsControllerApi;


FileUploadsControllerApi apiInstance = new FileUploadsControllerApi();
String parentProjectCode = "parentProjectCode_example"; // String | 
try {
    List<GUploadsProjectEndpoint> result = apiInstance.findUploadsEndpointsByProject(parentProjectCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FileUploadsControllerApi#findUploadsEndpointsByProject");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **parentProjectCode** | **String**|  |

### Return type

[**List&lt;GUploadsProjectEndpoint&gt;**](GUploadsProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findUploadsEndpointsByQbe"></a>
# **findUploadsEndpointsByQbe**
> List&lt;GUploadsProjectEndpoint&gt; findUploadsEndpointsByQbe(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.FileUploadsControllerApi;


FileUploadsControllerApi apiInstance = new FileUploadsControllerApi();
GUploadsProjectEndpoint body = new GUploadsProjectEndpoint(); // GUploadsProjectEndpoint | 
try {
    List<GUploadsProjectEndpoint> result = apiInstance.findUploadsEndpointsByQbe(body);
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

[**List&lt;GUploadsProjectEndpoint&gt;**](GUploadsProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getFileSystemSystemTypes"></a>
# **getFileSystemSystemTypes**
> List&lt;GContentManagementSystemType&gt; getFileSystemSystemTypes()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.FileUploadsControllerApi;


FileUploadsControllerApi apiInstance = new FileUploadsControllerApi();
try {
    List<GContentManagementSystemType> result = apiInstance.getFileSystemSystemTypes();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FileUploadsControllerApi#getFileSystemSystemTypes");
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

<a name="getUploadableFilesExtensions"></a>
# **getUploadableFilesExtensions**
> List&lt;String&gt; getUploadableFilesExtensions()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.FileUploadsControllerApi;


FileUploadsControllerApi apiInstance = new FileUploadsControllerApi();
try {
    List<String> result = apiInstance.getUploadableFilesExtensions();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FileUploadsControllerApi#getUploadableFilesExtensions");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**List&lt;String&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getUploadsSystems"></a>
# **getUploadsSystems**
> List&lt;GUploadsContentManagementSystem&gt; getUploadsSystems(handlerCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.FileUploadsControllerApi;


FileUploadsControllerApi apiInstance = new FileUploadsControllerApi();
String handlerCode = "handlerCode_example"; // String | 
try {
    List<GUploadsContentManagementSystem> result = apiInstance.getUploadsSystems(handlerCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FileUploadsControllerApi#getUploadsSystems");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **handlerCode** | **String**|  | [optional]

### Return type

[**List&lt;GUploadsContentManagementSystem&gt;**](GUploadsContentManagementSystem.md)

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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.FileUploadsControllerApi;


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

<a name="updateUploadsEndpoint"></a>
# **updateUploadsEndpoint**
> GUploadsProjectEndpoint updateUploadsEndpoint(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.FileUploadsControllerApi;


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

