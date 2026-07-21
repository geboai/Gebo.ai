# ReindexingFrequencyOptionsControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**displayTimeValues**](ReindexingFrequencyOptionsControllerApi.md#displayTimeValues) | **POST** /api/users/ReindexingFrequencyOptionsController/displayTimeValues | 
[**getAllTimeStructureMetaInfos**](ReindexingFrequencyOptionsControllerApi.md#getAllTimeStructureMetaInfos) | **GET** /api/users/ReindexingFrequencyOptionsController/getAllTimeStructureMetaInfos | 
[**getTimeStructureMetaInfo**](ReindexingFrequencyOptionsControllerApi.md#getTimeStructureMetaInfo) | **GET** /api/users/ReindexingFrequencyOptionsController/getTimeStructureMetaInfo | 

<a name="displayTimeValues"></a>
# **displayTimeValues**
> Object displayTimeValues(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ReindexingFrequencyOptionsControllerApi;


ReindexingFrequencyOptionsControllerApi apiInstance = new ReindexingFrequencyOptionsControllerApi();
Object body = null; // Object | 
try {
    Object result = apiInstance.displayTimeValues(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ReindexingFrequencyOptionsControllerApi#displayTimeValues");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Object**](Object.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getAllTimeStructureMetaInfos"></a>
# **getAllTimeStructureMetaInfos**
> Object getAllTimeStructureMetaInfos()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ReindexingFrequencyOptionsControllerApi;


ReindexingFrequencyOptionsControllerApi apiInstance = new ReindexingFrequencyOptionsControllerApi();
try {
    Object result = apiInstance.getAllTimeStructureMetaInfos();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ReindexingFrequencyOptionsControllerApi#getAllTimeStructureMetaInfos");
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

<a name="getTimeStructureMetaInfo"></a>
# **getTimeStructureMetaInfo**
> ReindexTimeStructureMetaInfo getTimeStructureMetaInfo(frequency)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ReindexingFrequencyOptionsControllerApi;


ReindexingFrequencyOptionsControllerApi apiInstance = new ReindexingFrequencyOptionsControllerApi();
Object frequency = null; // Object | 
try {
    ReindexTimeStructureMetaInfo result = apiInstance.getTimeStructureMetaInfo(frequency);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ReindexingFrequencyOptionsControllerApi#getTimeStructureMetaInfo");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **frequency** | [**Object**](.md)|  |

### Return type

[**ReindexTimeStructureMetaInfo**](ReindexTimeStructureMetaInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

