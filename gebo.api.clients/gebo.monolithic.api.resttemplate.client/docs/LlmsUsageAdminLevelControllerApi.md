# LlmsUsageAdminLevelControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**adminDrillDown**](LlmsUsageAdminLevelControllerApi.md#adminDrillDown) | **POST** /api/admin/LLMSUsageAdminLevelController/drillDown | 

<a name="adminDrillDown"></a>
# **adminDrillDown**
> LLMUsageDrillDownResult adminDrillDown(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.LlmsUsageAdminLevelControllerApi;


LlmsUsageAdminLevelControllerApi apiInstance = new LlmsUsageAdminLevelControllerApi();
LLMUsageDrillDownLevel body = new LLMUsageDrillDownLevel(); // LLMUsageDrillDownLevel | 
try {
    LLMUsageDrillDownResult result = apiInstance.adminDrillDown(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling LlmsUsageAdminLevelControllerApi#adminDrillDown");
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

