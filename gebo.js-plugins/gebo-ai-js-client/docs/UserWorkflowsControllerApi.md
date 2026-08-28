# GeboAiClient.UserWorkflowsControllerApi

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
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UserWorkflowsControllerApi();
apiInstance.getUserWorkflowsConfig().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

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
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UserWorkflowsControllerApi();
let body = new GeboAiClient.StartWorkflowData(); // StartWorkflowData | 

apiInstance.startUserWorkflow(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

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
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UserWorkflowsControllerApi();
let body = new GeboAiClient.UserChangePasswordWithTicket(); // UserChangePasswordWithTicket | 

apiInstance.userChangePasswordWithTicket(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

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

