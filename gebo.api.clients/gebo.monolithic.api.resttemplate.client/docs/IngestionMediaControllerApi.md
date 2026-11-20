# IngestionMediaControllerApi

All URIs are relative to *http://localhost:13999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**describePicture**](IngestionMediaControllerApi.md#describePicture) | **POST** /api/users/IngestionMediaController/describePicture | 
[**getIngestionMediaCapabilities**](IngestionMediaControllerApi.md#getIngestionMediaCapabilities) | **GET** /api/users/IngestionMediaController/getIngestionMediaCapabilities | 
[**transcribe**](IngestionMediaControllerApi.md#transcribe) | **POST** /api/users/IngestionMediaController/transcribe | 

<a name="describePicture"></a>
# **describePicture**
> IngestionMediaText describePicture(body, mimeType, name)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.IngestionMediaControllerApi;


IngestionMediaControllerApi apiInstance = new IngestionMediaControllerApi();
byte[] body = null; // byte[] | 
String mimeType = "mimeType_example"; // String | 
String name = "name_example"; // String | 
try {
    IngestionMediaText result = apiInstance.describePicture(body, mimeType, name);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IngestionMediaControllerApi#describePicture");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**byte[]**](byte[].md)|  |
 **mimeType** | **String**|  |
 **name** | **String**|  | [optional]

### Return type

[**IngestionMediaText**](IngestionMediaText.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/octet-stream
 - **Accept**: application/json

<a name="getIngestionMediaCapabilities"></a>
# **getIngestionMediaCapabilities**
> IngestionMediaCapabilities getIngestionMediaCapabilities()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.IngestionMediaControllerApi;


IngestionMediaControllerApi apiInstance = new IngestionMediaControllerApi();
try {
    IngestionMediaCapabilities result = apiInstance.getIngestionMediaCapabilities();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IngestionMediaControllerApi#getIngestionMediaCapabilities");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**IngestionMediaCapabilities**](IngestionMediaCapabilities.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="transcribe"></a>
# **transcribe**
> IngestionMediaText transcribe(body, name, mimeType)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.IngestionMediaControllerApi;


IngestionMediaControllerApi apiInstance = new IngestionMediaControllerApi();
byte[] body = null; // byte[] | 
String name = "name_example"; // String | 
String mimeType = "mimeType_example"; // String | 
try {
    IngestionMediaText result = apiInstance.transcribe(body, name, mimeType);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IngestionMediaControllerApi#transcribe");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**byte[]**](byte[].md)|  |
 **name** | **String**|  | [optional]
 **mimeType** | **String**|  | [optional]

### Return type

[**IngestionMediaText**](IngestionMediaText.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/octet-stream
 - **Accept**: application/json

