# JobLauncherControllerApi

All URIs are relative to *http://localhost:13014/mcpclient*

Method | HTTP request | Description
------------- | ------------- | -------------
[**abortJob**](JobLauncherControllerApi.md#abortJob) | **GET** /api/admin/JobLauncherController/abortJob | 
[**createJob**](JobLauncherControllerApi.md#createJob) | **POST** /api/admin/JobLauncherController/createJob | 
[**getHasRunningJobs**](JobLauncherControllerApi.md#getHasRunningJobs) | **POST** /api/admin/JobLauncherController/getHasRunningJobs | 

<a name="abortJob"></a>
# **abortJob**
> abortJob(jobCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.mcpclient.invoker.ApiException;
//import gebo.microservices.api.client.mcpclient.api.JobLauncherControllerApi;


JobLauncherControllerApi apiInstance = new JobLauncherControllerApi();
Object jobCode = null; // Object | 
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
 **jobCode** | [**Object**](.md)|  |

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
//import gebo.microservices.api.client.mcpclient.invoker.ApiException;
//import gebo.microservices.api.client.mcpclient.api.JobLauncherControllerApi;


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
//import gebo.microservices.api.client.mcpclient.invoker.ApiException;
//import gebo.microservices.api.client.mcpclient.api.JobLauncherControllerApi;


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

