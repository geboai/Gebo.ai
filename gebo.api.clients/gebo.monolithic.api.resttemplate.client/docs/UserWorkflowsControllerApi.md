# UserWorkflowsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getUserWorkflowsConfig**](UserWorkflowsControllerApi.md#getUserWorkflowsConfig) | **GET** /public/UserWorkflowsController/getUserWorkflowsConfig | 
[**startUserWorkflow**](UserWorkflowsControllerApi.md#startUserWorkflow) | **POST** /public/UserWorkflowsController/startUserWorkflow | 
[**userChangePasswordWithTicket**](UserWorkflowsControllerApi.md#userChangePasswordWithTicket) | **POST** /public/UserWorkflowsController/userChangePasswordWithTicket | 

<a name="getUserWorkflowsConfig"></a>
# **getUserWorkflowsConfig**
> UserWorkflows getUserWorkflowsConfig()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UserWorkflowsControllerApi;


UserWorkflowsControllerApi apiInstance = new UserWorkflowsControllerApi();
try {
    UserWorkflows result = apiInstance.getUserWorkflowsConfig();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserWorkflowsControllerApi#getUserWorkflowsConfig");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**UserWorkflows**](UserWorkflows.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="startUserWorkflow"></a>
# **startUserWorkflow**
> UserWorkFlowStartResponse startUserWorkflow(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UserWorkflowsControllerApi;


UserWorkflowsControllerApi apiInstance = new UserWorkflowsControllerApi();
StartWorkflowData body = new StartWorkflowData(); // StartWorkflowData | 
try {
    UserWorkFlowStartResponse result = apiInstance.startUserWorkflow(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserWorkflowsControllerApi#startUserWorkflow");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**StartWorkflowData**](StartWorkflowData.md)|  |

### Return type

[**UserWorkFlowStartResponse**](UserWorkFlowStartResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="userChangePasswordWithTicket"></a>
# **userChangePasswordWithTicket**
> UserWorkFlowChangePasswordResponse userChangePasswordWithTicket(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UserWorkflowsControllerApi;


UserWorkflowsControllerApi apiInstance = new UserWorkflowsControllerApi();
UserChangePasswordWithTicket body = new UserChangePasswordWithTicket(); // UserChangePasswordWithTicket | 
try {
    UserWorkFlowChangePasswordResponse result = apiInstance.userChangePasswordWithTicket(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserWorkflowsControllerApi#userChangePasswordWithTicket");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UserChangePasswordWithTicket**](UserChangePasswordWithTicket.md)|  |

### Return type

[**UserWorkFlowChangePasswordResponse**](UserWorkFlowChangePasswordResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

