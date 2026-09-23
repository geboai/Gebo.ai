# GeboModulesConfigControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAllModules**](GeboModulesConfigControllerApi.md#getAllModules) | **GET** /api/users/GeboModulesConfigController/getAllModules | 
[**getModuleInfo**](GeboModulesConfigControllerApi.md#getModuleInfo) | **GET** /api/users/GeboModulesConfigController/getModuleInfo | 

<a name="getAllModules"></a>
# **getAllModules**
> List&lt;GeboModuleInfo&gt; getAllModules()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboModulesConfigControllerApi;


GeboModulesConfigControllerApi apiInstance = new GeboModulesConfigControllerApi();
try {
    List<GeboModuleInfo> result = apiInstance.getAllModules();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboModulesConfigControllerApi#getAllModules");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GeboModuleInfo&gt;**](GeboModuleInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getModuleInfo"></a>
# **getModuleInfo**
> GeboModuleInfo getModuleInfo(moduleId)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboModulesConfigControllerApi;


GeboModulesConfigControllerApi apiInstance = new GeboModulesConfigControllerApi();
String moduleId = "moduleId_example"; // String | 
try {
    GeboModuleInfo result = apiInstance.getModuleInfo(moduleId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboModulesConfigControllerApi#getModuleInfo");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **moduleId** | **String**|  |

### Return type

[**GeboModuleInfo**](GeboModuleInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

