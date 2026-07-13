# WorkflowStatsAdminLevelControllerApi

All URIs are relative to *http://localhost:13013*

Method | HTTP request | Description
------------- | ------------- | -------------
[**workflowDrillDown**](WorkflowStatsAdminLevelControllerApi.md#workflowDrillDown) | **POST** /api/admin/WorkflowStatsAdminLevelController/drillDown | 

<a name="workflowDrillDown"></a>
# **workflowDrillDown**
> WorkflowStatsDrillDownResult workflowDrillDown(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.googledrive.invoker.ApiException;
//import gebo.microservices.api.client.googledrive.api.WorkflowStatsAdminLevelControllerApi;


WorkflowStatsAdminLevelControllerApi apiInstance = new WorkflowStatsAdminLevelControllerApi();
WorkflowStatsDrillDownLevel body = new WorkflowStatsDrillDownLevel(); // WorkflowStatsDrillDownLevel | 
try {
    WorkflowStatsDrillDownResult result = apiInstance.workflowDrillDown(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WorkflowStatsAdminLevelControllerApi#workflowDrillDown");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**WorkflowStatsDrillDownLevel**](WorkflowStatsDrillDownLevel.md)|  |

### Return type

[**WorkflowStatsDrillDownResult**](WorkflowStatsDrillDownResult.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

