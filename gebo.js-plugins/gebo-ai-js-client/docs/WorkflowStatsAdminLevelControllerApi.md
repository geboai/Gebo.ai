# GeboAiClient.WorkflowStatsAdminLevelControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**workflowDrillDown**](WorkflowStatsAdminLevelControllerApi.md#workflowDrillDown) | **POST** /api/admin/WorkflowStatsAdminLevelController/drillDown | 

<a name="workflowDrillDown"></a>
# **workflowDrillDown**
> WorkflowStatsDrillDownResult workflowDrillDown(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.WorkflowStatsAdminLevelControllerApi();
let body = new GeboAiClient.WorkflowStatsDrillDownLevel(); // WorkflowStatsDrillDownLevel | 

apiInstance.workflowDrillDown(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

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

