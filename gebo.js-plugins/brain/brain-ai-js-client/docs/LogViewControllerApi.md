# BrainClient.LogViewControllerApi

All URIs are relative to *http://localhost:13001/brain*

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
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.LogViewControllerApi();
let body = null; // Object | 

apiInstance.deleteJobStatus(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Object**](Object.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="getJobMessagesPaged"></a>
# **getJobMessagesPaged**
> PageGUserMessage getJobMessagesPaged(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.LogViewControllerApi();
let body = new BrainClient.GetJobMessagesParam(); // GetJobMessagesParam | 

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

[**PageGUserMessage**](PageGUserMessage.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getJobsEntriesForClassName"></a>
# **getJobsEntriesForClassName**
> PageGJobStatusItem getJobsEntriesForClassName(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.LogViewControllerApi();
let body = new BrainClient.JobsEntriesForClassNameFilter(); // JobsEntriesForClassNameFilter | 

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

[**PageGJobStatusItem**](PageGJobStatusItem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getJobsEntriesForJobType"></a>
# **getJobsEntriesForJobType**
> PageGJobStatusItem getJobsEntriesForJobType(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.LogViewControllerApi();
let body = new BrainClient.JobsEntriesForJobType(); // JobsEntriesForJobType | 

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

[**PageGJobStatusItem**](PageGJobStatusItem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getJobsEntriesForProjectEndpoint"></a>
# **getJobsEntriesForProjectEndpoint**
> PageGJobStatusItem getJobsEntriesForProjectEndpoint(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.LogViewControllerApi();
let body = new BrainClient.JobsEntriesForProjectEndpointFilter(); // JobsEntriesForProjectEndpointFilter | 

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

[**PageGJobStatusItem**](PageGJobStatusItem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

