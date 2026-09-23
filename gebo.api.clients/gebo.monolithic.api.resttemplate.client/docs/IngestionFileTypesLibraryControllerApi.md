# IngestionFileTypesLibraryControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAllFileTypes**](IngestionFileTypesLibraryControllerApi.md#getAllFileTypes) | **GET** /api/users/IngestionFileTypesLibraryController/getAllFileTypes | 
[**getIngestionFileTypeByExtension**](IngestionFileTypesLibraryControllerApi.md#getIngestionFileTypeByExtension) | **GET** /api/users/IngestionFileTypesLibraryController/getIngestionFileTypeByExtension | 
[**getIngestionReadingModules**](IngestionFileTypesLibraryControllerApi.md#getIngestionReadingModules) | **GET** /api/users/IngestionFileTypesLibraryController/getIngestionReadingModules | 

<a name="getAllFileTypes"></a>
# **getAllFileTypes**
> List&lt;IngestionFileType&gt; getAllFileTypes()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.IngestionFileTypesLibraryControllerApi;


IngestionFileTypesLibraryControllerApi apiInstance = new IngestionFileTypesLibraryControllerApi();
try {
    List<IngestionFileType> result = apiInstance.getAllFileTypes();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IngestionFileTypesLibraryControllerApi#getAllFileTypes");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;IngestionFileType&gt;**](IngestionFileType.md)

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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.IngestionFileTypesLibraryControllerApi;


IngestionFileTypesLibraryControllerApi apiInstance = new IngestionFileTypesLibraryControllerApi();
String extension = "extension_example"; // String | 
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
 **extension** | **String**|  |

### Return type

[**IngestionFileType**](IngestionFileType.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getIngestionReadingModules"></a>
# **getIngestionReadingModules**
> List&lt;IngestionHandlerConfig&gt; getIngestionReadingModules()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.IngestionFileTypesLibraryControllerApi;


IngestionFileTypesLibraryControllerApi apiInstance = new IngestionFileTypesLibraryControllerApi();
try {
    List<IngestionHandlerConfig> result = apiInstance.getIngestionReadingModules();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IngestionFileTypesLibraryControllerApi#getIngestionReadingModules");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;IngestionHandlerConfig&gt;**](IngestionHandlerConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

