# WorkflowParticipantsEnablementControllerApi

All URIs are relative to *http://localhost:13019/tyr*

Method | HTTP request | Description
------------- | ------------- | -------------
[**enabledSteps**](WorkflowParticipantsEnablementControllerApi.md#enabledSteps) | **GET** /api/users/WorkflowParticipantsEnablementController/enabledSteps | 

<a name="enabledSteps"></a>
# **enabledSteps**
> List&lt;String&gt; enabledSteps(workflowType, workflowId)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.tyr.invoker.ApiException;
//import gebo.microservices.api.client.tyr.api.WorkflowParticipantsEnablementControllerApi;


WorkflowParticipantsEnablementControllerApi apiInstance = new WorkflowParticipantsEnablementControllerApi();
String workflowType = "workflowType_example"; // String | 
String workflowId = "workflowId_example"; // String | 
try {
    List<String> result = apiInstance.enabledSteps(workflowType, workflowId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WorkflowParticipantsEnablementControllerApi#enabledSteps");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **workflowType** | **String**|  |
 **workflowId** | **String**|  |

### Return type

**List&lt;String&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

