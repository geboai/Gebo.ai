# JobStatusControllerApi

All URIs are relative to *http://localhost:13019/tyr*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getJobStatus**](JobStatusControllerApi.md#getJobStatus) | **GET** /api/admin/JobStatusController/getJobStatus | 
[**getJobSummary**](JobStatusControllerApi.md#getJobSummary) | **GET** /api/admin/JobStatusController/getJobSummary | 
[**getJobsEntriesForProjectEndpoint**](JobStatusControllerApi.md#getJobsEntriesForProjectEndpoint) | **POST** /api/admin/JobStatusController/getJobsEntriesForProjectEndpoint | 

<a name="getJobStatus"></a>
# **getJobStatus**
> GJobStatus getJobStatus(jobCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.tyr.invoker.ApiException;
//import gebo.microservices.api.client.tyr.api.JobStatusControllerApi;


JobStatusControllerApi apiInstance = new JobStatusControllerApi();
Object jobCode = null; // Object | 
try {
    GJobStatus result = apiInstance.getJobStatus(jobCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JobStatusControllerApi#getJobStatus");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **jobCode** | [**Object**](.md)|  |

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
```java
// Import classes:
//import gebo.microservices.api.client.tyr.invoker.ApiException;
//import gebo.microservices.api.client.tyr.api.JobStatusControllerApi;


JobStatusControllerApi apiInstance = new JobStatusControllerApi();
Object jobCode = null; // Object | 
try {
    JobSummary result = apiInstance.getJobSummary(jobCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JobStatusControllerApi#getJobSummary");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **jobCode** | [**Object**](.md)|  |

### Return type

[**JobSummary**](JobSummary.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getJobsEntriesForProjectEndpoint"></a>
# **getJobsEntriesForProjectEndpoint**
> PageGJobStatusItem getJobsEntriesForProjectEndpoint(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.tyr.invoker.ApiException;
//import gebo.microservices.api.client.tyr.api.JobStatusControllerApi;


JobStatusControllerApi apiInstance = new JobStatusControllerApi();
JobsEntriesForProjectEndpointFilter body = new JobsEntriesForProjectEndpointFilter(); // JobsEntriesForProjectEndpointFilter | 
try {
    PageGJobStatusItem result = apiInstance.getJobsEntriesForProjectEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JobStatusControllerApi#getJobsEntriesForProjectEndpoint");
    e.printStackTrace();
}
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

