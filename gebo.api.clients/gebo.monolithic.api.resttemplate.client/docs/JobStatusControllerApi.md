# JobStatusControllerApi

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
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JobStatusControllerApi;


JobStatusControllerApi apiInstance = new JobStatusControllerApi();
String jobCode = "jobCode_example"; // String | 
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
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JobStatusControllerApi;


JobStatusControllerApi apiInstance = new JobStatusControllerApi();
String jobCode = "jobCode_example"; // String | 
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
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JobStatusControllerApi;


JobStatusControllerApi apiInstance = new JobStatusControllerApi();
JobsEntriesForProjectEndpointFilter body = new JobsEntriesForProjectEndpointFilter(); // JobsEntriesForProjectEndpointFilter | 
try {
    PagedModelGJobStatusItem result = apiInstance.getJobsEntriesForProjectEndpoint1(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JobStatusControllerApi#getJobsEntriesForProjectEndpoint1");
    e.printStackTrace();
}
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

