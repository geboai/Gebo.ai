# WorkflowParticipantsEnablementControllerApi

All URIs are relative to *http://localhost:13019/tyr*

Method | HTTP request | Description
------------- | ------------- | -------------
[**enabledSteps**](WorkflowParticipantsEnablementControllerApi.md#enabledSteps) | **GET** /api/users/WorkflowParticipantsEnablementController/enabledSteps | 

<a name="enabledSteps"></a>
# **enabledSteps**
> Object enabledSteps(workflowType, workflowId)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.tyr.invoker.ApiException;
//import gebo.microservices.api.client.tyr.api.WorkflowParticipantsEnablementControllerApi;


WorkflowParticipantsEnablementControllerApi apiInstance = new WorkflowParticipantsEnablementControllerApi();
Object workflowType = null; // Object | 
Object workflowId = null; // Object | 
try {
    Object result = apiInstance.enabledSteps(workflowType, workflowId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WorkflowParticipantsEnablementControllerApi#enabledSteps");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **workflowType** | [**Object**](.md)|  |
 **workflowId** | [**Object**](.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

