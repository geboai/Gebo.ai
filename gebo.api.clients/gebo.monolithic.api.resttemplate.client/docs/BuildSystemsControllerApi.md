# BuildSystemsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getBuildSystemConfigs**](BuildSystemsControllerApi.md#getBuildSystemConfigs) | **GET** /api/admin/BuildSystemsController/getBuildSystemConfigs | 
[**getBuildSystemTypes**](BuildSystemsControllerApi.md#getBuildSystemTypes) | **GET** /api/admin/BuildSystemsController/getBuildSystemTypes | 

<a name="getBuildSystemConfigs"></a>
# **getBuildSystemConfigs**
> List&lt;GBuildSystem&gt; getBuildSystemConfigs(buildSystemTypeCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.BuildSystemsControllerApi;


BuildSystemsControllerApi apiInstance = new BuildSystemsControllerApi();
String buildSystemTypeCode = "buildSystemTypeCode_example"; // String | 
try {
    List<GBuildSystem> result = apiInstance.getBuildSystemConfigs(buildSystemTypeCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BuildSystemsControllerApi#getBuildSystemConfigs");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **buildSystemTypeCode** | **String**|  |

### Return type

[**List&lt;GBuildSystem&gt;**](GBuildSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getBuildSystemTypes"></a>
# **getBuildSystemTypes**
> List&lt;GBuildSystemType&gt; getBuildSystemTypes()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.BuildSystemsControllerApi;


BuildSystemsControllerApi apiInstance = new BuildSystemsControllerApi();
try {
    List<GBuildSystemType> result = apiInstance.getBuildSystemTypes();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BuildSystemsControllerApi#getBuildSystemTypes");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GBuildSystemType&gt;**](GBuildSystemType.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

