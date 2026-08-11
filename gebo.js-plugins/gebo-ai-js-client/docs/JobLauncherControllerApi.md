# GeboAiClient.JobLauncherControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**abortJob**](JobLauncherControllerApi.md#abortJob) | **GET** /api/admin/JobLauncherController/abortJob | 
[**createJob**](JobLauncherControllerApi.md#createJob) | **POST** /api/admin/JobLauncherController/createJob | 
[**getHasRunningJobs**](JobLauncherControllerApi.md#getHasRunningJobs) | **POST** /api/admin/JobLauncherController/getHasRunningJobs | 

<a name="abortJob"></a>
# **abortJob**
> abortJob(jobCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JobLauncherControllerApi();
let jobCode = "jobCode_example"; // String | 

apiInstance.abortJob(jobCode).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **jobCode** | **String**|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="createJob"></a>
# **createJob**
> OperationStatusGJobStatus createJob(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JobLauncherControllerApi();
let body = new GeboAiClient.GObjectRefGProjectEndpoint(); // GObjectRefGProjectEndpoint | 

apiInstance.createJob(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GObjectRefGProjectEndpoint**](GObjectRefGProjectEndpoint.md)|  | 

### Return type

[**OperationStatusGJobStatus**](OperationStatusGJobStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getHasRunningJobs"></a>
# **getHasRunningJobs**
> HasRunningJobs getHasRunningJobs(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JobLauncherControllerApi();
let body = new GeboAiClient.GObjectRefGProjectEndpoint(); // GObjectRefGProjectEndpoint | 

apiInstance.getHasRunningJobs(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GObjectRefGProjectEndpoint**](GObjectRefGProjectEndpoint.md)|  | 

### Return type

[**HasRunningJobs**](HasRunningJobs.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

