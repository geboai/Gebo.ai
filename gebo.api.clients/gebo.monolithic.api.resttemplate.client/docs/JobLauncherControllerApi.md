# JobLauncherControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**abortJob**](JobLauncherControllerApi.md#abortJob) | **GET** /api/admin/JobLauncherController/abortJob | 
[**createJob**](JobLauncherControllerApi.md#createJob) | **POST** /api/admin/JobLauncherController/createJob | 
[**getHasRunningJobs**](JobLauncherControllerApi.md#getHasRunningJobs) | **POST** /api/admin/JobLauncherController/getHasRunningJobs | 
[**getJobStatus**](JobLauncherControllerApi.md#getJobStatus) | **GET** /api/admin/JobLauncherController/getJobStatus | 
[**getJobSummary**](JobLauncherControllerApi.md#getJobSummary) | **GET** /api/admin/JobLauncherController/getJobSummary | 

<a name="abortJob"></a>
# **abortJob**
> abortJob(jobCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JobLauncherControllerApi;


JobLauncherControllerApi apiInstance = new JobLauncherControllerApi();
String jobCode = "jobCode_example"; // String | 
try {
    apiInstance.abortJob(jobCode);
} catch (ApiException e) {
    System.err.println("Exception when calling JobLauncherControllerApi#abortJob");
    e.printStackTrace();
}
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
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JobLauncherControllerApi;


JobLauncherControllerApi apiInstance = new JobLauncherControllerApi();
GObjectRefGProjectEndpoint body = new GObjectRefGProjectEndpoint(); // GObjectRefGProjectEndpoint | 
try {
    OperationStatusGJobStatus result = apiInstance.createJob(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JobLauncherControllerApi#createJob");
    e.printStackTrace();
}
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
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JobLauncherControllerApi;


JobLauncherControllerApi apiInstance = new JobLauncherControllerApi();
GObjectRefGProjectEndpoint body = new GObjectRefGProjectEndpoint(); // GObjectRefGProjectEndpoint | 
try {
    HasRunningJobs result = apiInstance.getHasRunningJobs(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JobLauncherControllerApi#getHasRunningJobs");
    e.printStackTrace();
}
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

<a name="getJobStatus"></a>
# **getJobStatus**
> GJobStatus getJobStatus(jobCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.JobLauncherControllerApi;


JobLauncherControllerApi apiInstance = new JobLauncherControllerApi();
String jobCode = "jobCode_example"; // String | 
try {
    GJobStatus result = apiInstance.getJobStatus(jobCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JobLauncherControllerApi#getJobStatus");
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
//import ai.gebo.monolithic.api.client.api.JobLauncherControllerApi;


JobLauncherControllerApi apiInstance = new JobLauncherControllerApi();
String jobCode = "jobCode_example"; // String | 
try {
    JobSummary result = apiInstance.getJobSummary(jobCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling JobLauncherControllerApi#getJobSummary");
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

