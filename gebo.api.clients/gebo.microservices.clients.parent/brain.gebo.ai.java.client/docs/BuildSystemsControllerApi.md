# BuildSystemsControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getBuildSystemConfigs**](BuildSystemsControllerApi.md#getBuildSystemConfigs) | **GET** /api/admin/BuildSystemsController/getBuildSystemConfigs | 
[**getBuildSystemTypes**](BuildSystemsControllerApi.md#getBuildSystemTypes) | **GET** /api/admin/BuildSystemsController/getBuildSystemTypes | 

<a name="getBuildSystemConfigs"></a>
# **getBuildSystemConfigs**
> Object getBuildSystemConfigs(buildSystemTypeCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.BuildSystemsControllerApi;


BuildSystemsControllerApi apiInstance = new BuildSystemsControllerApi();
Object buildSystemTypeCode = null; // Object | 
try {
    Object result = apiInstance.getBuildSystemConfigs(buildSystemTypeCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BuildSystemsControllerApi#getBuildSystemConfigs");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **buildSystemTypeCode** | [**Object**](.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getBuildSystemTypes"></a>
# **getBuildSystemTypes**
> Object getBuildSystemTypes()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.BuildSystemsControllerApi;


BuildSystemsControllerApi apiInstance = new BuildSystemsControllerApi();
try {
    Object result = apiInstance.getBuildSystemTypes();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BuildSystemsControllerApi#getBuildSystemTypes");
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

