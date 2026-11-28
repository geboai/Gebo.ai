# LlmsUsageUserLevelControllerApi

All URIs are relative to *http://localhost:13019/tyr*

Method | HTTP request | Description
------------- | ------------- | -------------
[**userDrillDown**](LlmsUsageUserLevelControllerApi.md#userDrillDown) | **POST** /api/users/LLMSUsageUserLevelController/drillDown | 
[**userExportExcel**](LlmsUsageUserLevelControllerApi.md#userExportExcel) | **POST** /api/users/LLMSUsageUserLevelController/exportExcel | 

<a name="userDrillDown"></a>
# **userDrillDown**
> LLMUsageDrillDownResult userDrillDown(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.tyr.invoker.ApiException;
//import gebo.microservices.api.client.tyr.api.LlmsUsageUserLevelControllerApi;


LlmsUsageUserLevelControllerApi apiInstance = new LlmsUsageUserLevelControllerApi();
LLMUsageDrillDownLevel body = new LLMUsageDrillDownLevel(); // LLMUsageDrillDownLevel | 
try {
    LLMUsageDrillDownResult result = apiInstance.userDrillDown(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling LlmsUsageUserLevelControllerApi#userDrillDown");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**LLMUsageDrillDownLevel**](LLMUsageDrillDownLevel.md)|  |

### Return type

[**LLMUsageDrillDownResult**](LLMUsageDrillDownResult.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="userExportExcel"></a>
# **userExportExcel**
> byte[] userExportExcel(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.tyr.invoker.ApiException;
//import gebo.microservices.api.client.tyr.api.LlmsUsageUserLevelControllerApi;


LlmsUsageUserLevelControllerApi apiInstance = new LlmsUsageUserLevelControllerApi();
LLMUsageDrillDownLevel body = new LLMUsageDrillDownLevel(); // LLMUsageDrillDownLevel | 
try {
    byte[] result = apiInstance.userExportExcel(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling LlmsUsageUserLevelControllerApi#userExportExcel");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**LLMUsageDrillDownLevel**](LLMUsageDrillDownLevel.md)|  |

### Return type

**byte[]**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/vnd.openxmlformats-officedocument.spreadsheetml.sheet

