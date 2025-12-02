# LogViewControllerApi

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
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.LogViewControllerApi;


LogViewControllerApi apiInstance = new LogViewControllerApi();
List<String> body = Arrays.asList("body_example"); // List<String> | 
try {
    apiInstance.deleteJobStatus(body);
} catch (ApiException e) {
    System.err.println("Exception when calling LogViewControllerApi#deleteJobStatus");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**List&lt;String&gt;**](String.md)|  |

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
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.LogViewControllerApi;


LogViewControllerApi apiInstance = new LogViewControllerApi();
GetJobMessagesParam body = new GetJobMessagesParam(); // GetJobMessagesParam | 
try {
    PageGUserMessage result = apiInstance.getJobMessagesPaged(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling LogViewControllerApi#getJobMessagesPaged");
    e.printStackTrace();
}
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
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.LogViewControllerApi;


LogViewControllerApi apiInstance = new LogViewControllerApi();
JobsEntriesForClassNameFilter body = new JobsEntriesForClassNameFilter(); // JobsEntriesForClassNameFilter | 
try {
    PageGJobStatusItem result = apiInstance.getJobsEntriesForClassName(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling LogViewControllerApi#getJobsEntriesForClassName");
    e.printStackTrace();
}
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
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.LogViewControllerApi;


LogViewControllerApi apiInstance = new LogViewControllerApi();
JobsEntriesForJobType body = new JobsEntriesForJobType(); // JobsEntriesForJobType | 
try {
    PageGJobStatusItem result = apiInstance.getJobsEntriesForJobType(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling LogViewControllerApi#getJobsEntriesForJobType");
    e.printStackTrace();
}
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
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.LogViewControllerApi;


LogViewControllerApi apiInstance = new LogViewControllerApi();
JobsEntriesForProjectEndpointFilter body = new JobsEntriesForProjectEndpointFilter(); // JobsEntriesForProjectEndpointFilter | 
try {
    PageGJobStatusItem result = apiInstance.getJobsEntriesForProjectEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling LogViewControllerApi#getJobsEntriesForProjectEndpoint");
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

