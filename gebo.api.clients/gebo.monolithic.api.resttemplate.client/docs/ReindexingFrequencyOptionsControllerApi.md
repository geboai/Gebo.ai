# ReindexingFrequencyOptionsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**displayTimeValues**](ReindexingFrequencyOptionsControllerApi.md#displayTimeValues) | **POST** /api/users/ReindexingFrequencyOptionsController/displayTimeValues | 
[**getAllTimeStructureMetaInfos**](ReindexingFrequencyOptionsControllerApi.md#getAllTimeStructureMetaInfos) | **GET** /api/users/ReindexingFrequencyOptionsController/getAllTimeStructureMetaInfos | 
[**getTimeStructureMetaInfo**](ReindexingFrequencyOptionsControllerApi.md#getTimeStructureMetaInfo) | **GET** /api/users/ReindexingFrequencyOptionsController/getTimeStructureMetaInfo | 

<a name="displayTimeValues"></a>
# **displayTimeValues**
> List&lt;String&gt; displayTimeValues(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ReindexingFrequencyOptionsControllerApi;


ReindexingFrequencyOptionsControllerApi apiInstance = new ReindexingFrequencyOptionsControllerApi();
List<ReindexingProgrammedTable> body = Arrays.asList(new ReindexingProgrammedTable()); // List<ReindexingProgrammedTable> | 
try {
    List<String> result = apiInstance.displayTimeValues(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ReindexingFrequencyOptionsControllerApi#displayTimeValues");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**List&lt;ReindexingProgrammedTable&gt;**](ReindexingProgrammedTable.md)|  |

### Return type

**List&lt;String&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getAllTimeStructureMetaInfos"></a>
# **getAllTimeStructureMetaInfos**
> List&lt;ReindexTimeStructureMetaInfo&gt; getAllTimeStructureMetaInfos()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ReindexingFrequencyOptionsControllerApi;


ReindexingFrequencyOptionsControllerApi apiInstance = new ReindexingFrequencyOptionsControllerApi();
try {
    List<ReindexTimeStructureMetaInfo> result = apiInstance.getAllTimeStructureMetaInfos();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ReindexingFrequencyOptionsControllerApi#getAllTimeStructureMetaInfos");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;ReindexTimeStructureMetaInfo&gt;**](ReindexTimeStructureMetaInfo.md)

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
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ReindexingFrequencyOptionsControllerApi;


ReindexingFrequencyOptionsControllerApi apiInstance = new ReindexingFrequencyOptionsControllerApi();
String frequency = "frequency_example"; // String | 
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
 **frequency** | **String**|  | [enum: DAILY, MONTHLY, WEEKLY, HOURLY, YEARLY, ON_CHANGES, DATES]

### Return type

[**ReindexTimeStructureMetaInfo**](ReindexTimeStructureMetaInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

