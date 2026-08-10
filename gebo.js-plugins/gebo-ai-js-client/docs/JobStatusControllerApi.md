# GeboAiClient.JobStatusControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getJobStatus**](JobStatusControllerApi.md#getJobStatus) | **GET** /api/admin/JobStatusController/getJobStatus | 
[**getJobSummary**](JobStatusControllerApi.md#getJobSummary) | **GET** /api/admin/JobStatusController/getJobSummary | 
[**getJobsEntriesForProjectEndpoint1**](JobStatusControllerApi.md#getJobsEntriesForProjectEndpoint1) | **POST** /api/admin/JobStatusController/getJobsEntriesForProjectEndpoint | 

<a name="getJobStatus"></a>
# **getJobStatus**
> GJobStatus getJobStatus(jobCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JobStatusControllerApi();
let jobCode = "jobCode_example"; // String | 

apiInstance.getJobStatus(jobCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **jobCode** | **String**|  | 

### Return type

[**GJobStatus**](GJobStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getJobSummary"></a>
# **getJobSummary**
> JobSummary getJobSummary(jobCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JobStatusControllerApi();
let jobCode = "jobCode_example"; // String | 

apiInstance.getJobSummary(jobCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **jobCode** | **String**|  | 

### Return type

[**JobSummary**](JobSummary.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getJobsEntriesForProjectEndpoint1"></a>
# **getJobsEntriesForProjectEndpoint1**
> PagedModelGJobStatusItem getJobsEntriesForProjectEndpoint1(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JobStatusControllerApi();
let body = new GeboAiClient.JobsEntriesForProjectEndpointFilter(); // JobsEntriesForProjectEndpointFilter | 

apiInstance.getJobsEntriesForProjectEndpoint1(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**JobsEntriesForProjectEndpointFilter**](JobsEntriesForProjectEndpointFilter.md)|  | 

### Return type

[**PagedModelGJobStatusItem**](PagedModelGJobStatusItem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

