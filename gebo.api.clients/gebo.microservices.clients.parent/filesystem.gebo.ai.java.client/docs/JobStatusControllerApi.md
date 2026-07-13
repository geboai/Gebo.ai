# JobStatusControllerApi

All URIs are relative to *http://localhost:13006*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getJobStatus**](JobStatusControllerApi.md#getJobStatus) | **GET** /api/admin/JobStatusController/getJobStatus | 
[**getJobSummary**](JobStatusControllerApi.md#getJobSummary) | **GET** /api/admin/JobStatusController/getJobSummary | 

<a name="getJobStatus"></a>
# **getJobStatus**
> GJobStatus getJobStatus(jobCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.filesystem.invoker.ApiException;
//import gebo.microservices.api.client.filesystem.api.JobStatusControllerApi;


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
//import gebo.microservices.api.client.filesystem.invoker.ApiException;
//import gebo.microservices.api.client.filesystem.api.JobStatusControllerApi;


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

