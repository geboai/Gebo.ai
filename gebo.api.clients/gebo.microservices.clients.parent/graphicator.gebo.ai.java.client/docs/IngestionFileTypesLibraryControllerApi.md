# IngestionFileTypesLibraryControllerApi

All URIs are relative to *http://localhost:13003*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAllFileTypes**](IngestionFileTypesLibraryControllerApi.md#getAllFileTypes) | **GET** /api/users/IngestionFileTypesLibraryController/getAllFileTypes | 
[**getIngestionFileTypeByExtension**](IngestionFileTypesLibraryControllerApi.md#getIngestionFileTypeByExtension) | **GET** /api/users/IngestionFileTypesLibraryController/getIngestionFileTypeByExtension | 
[**getIngestionReadingModules**](IngestionFileTypesLibraryControllerApi.md#getIngestionReadingModules) | **GET** /api/users/IngestionFileTypesLibraryController/getIngestionReadingModules | 

<a name="getAllFileTypes"></a>
# **getAllFileTypes**
> Object getAllFileTypes()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.graphicator.invoker.ApiException;
//import gebo.microservices.api.client.graphicator.api.IngestionFileTypesLibraryControllerApi;


IngestionFileTypesLibraryControllerApi apiInstance = new IngestionFileTypesLibraryControllerApi();
try {
    Object result = apiInstance.getAllFileTypes();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IngestionFileTypesLibraryControllerApi#getAllFileTypes");
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

<a name="getIngestionFileTypeByExtension"></a>
# **getIngestionFileTypeByExtension**
> IngestionFileType getIngestionFileTypeByExtension(extension)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.graphicator.invoker.ApiException;
//import gebo.microservices.api.client.graphicator.api.IngestionFileTypesLibraryControllerApi;


IngestionFileTypesLibraryControllerApi apiInstance = new IngestionFileTypesLibraryControllerApi();
Object extension = null; // Object | 
try {
    IngestionFileType result = apiInstance.getIngestionFileTypeByExtension(extension);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IngestionFileTypesLibraryControllerApi#getIngestionFileTypeByExtension");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **extension** | [**Object**](.md)|  |

### Return type

[**IngestionFileType**](IngestionFileType.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getIngestionReadingModules"></a>
# **getIngestionReadingModules**
> Object getIngestionReadingModules()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.graphicator.invoker.ApiException;
//import gebo.microservices.api.client.graphicator.api.IngestionFileTypesLibraryControllerApi;


IngestionFileTypesLibraryControllerApi apiInstance = new IngestionFileTypesLibraryControllerApi();
try {
    Object result = apiInstance.getIngestionReadingModules();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IngestionFileTypesLibraryControllerApi#getIngestionReadingModules");
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

