# GeboAiClient.LogViewControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteJobStatus**](LogViewControllerApi.md#deleteJobStatus) | **POST** /api/admin/LogViewController/deleteJobStatus | 
[**getJobMessagesPaged**](LogViewControllerApi.md#getJobMessagesPaged) | **POST** /api/admin/LogViewController/getJobMessagesPaged | 
[**getJobsEntriesForClassName**](LogViewControllerApi.md#getJobsEntriesForClassName) | **POST** /api/admin/LogViewController/getJobsEntriesForClassName | 
[**getJobsEntriesForJobType**](LogViewControllerApi.md#getJobsEntriesForJobType) | **POST** /api/admin/LogViewController/getJobsEntriesForJobType | 
[**getJobsEntriesForProjectEndpoint**](LogViewControllerApi.md#getJobsEntriesForProjectEndpoint) | **POST** /api/admin/LogViewController/getJobsEntriesForProjectEndpoint | 

<a name="deleteJobStatus"></a>
# **deleteJobStatus**
> deleteJobStatus(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.LogViewControllerApi();
let body = ["body_example"]; // [String] | 

apiInstance.deleteJobStatus(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**[String]**](String.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="getJobMessagesPaged"></a>
# **getJobMessagesPaged**
> PagedModelGUserMessage getJobMessagesPaged(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.LogViewControllerApi();
let body = new GeboAiClient.GetJobMessagesParam(); // GetJobMessagesParam | 

apiInstance.getJobMessagesPaged(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GetJobMessagesParam**](GetJobMessagesParam.md)|  | 

### Return type

[**PagedModelGUserMessage**](PagedModelGUserMessage.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getJobsEntriesForClassName"></a>
# **getJobsEntriesForClassName**
> PagedModelGJobStatusItem getJobsEntriesForClassName(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.LogViewControllerApi();
let body = new GeboAiClient.JobsEntriesForClassNameFilter(); // JobsEntriesForClassNameFilter | 

apiInstance.getJobsEntriesForClassName(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**JobsEntriesForClassNameFilter**](JobsEntriesForClassNameFilter.md)|  | 

### Return type

[**PagedModelGJobStatusItem**](PagedModelGJobStatusItem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getJobsEntriesForJobType"></a>
# **getJobsEntriesForJobType**
> PagedModelGJobStatusItem getJobsEntriesForJobType(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.LogViewControllerApi();
let body = new GeboAiClient.JobsEntriesForJobType(); // JobsEntriesForJobType | 

apiInstance.getJobsEntriesForJobType(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**JobsEntriesForJobType**](JobsEntriesForJobType.md)|  | 

### Return type

[**PagedModelGJobStatusItem**](PagedModelGJobStatusItem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getJobsEntriesForProjectEndpoint"></a>
# **getJobsEntriesForProjectEndpoint**
> PagedModelGJobStatusItem getJobsEntriesForProjectEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.LogViewControllerApi();
let body = new GeboAiClient.JobsEntriesForProjectEndpointFilter(); // JobsEntriesForProjectEndpointFilter | 

apiInstance.getJobsEntriesForProjectEndpoint(body).then((data) => {
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

