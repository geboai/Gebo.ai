# TaskApi

All URIs are relative to *https://{your-domain}/wiki/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getTaskById**](TaskApi.md#getTaskById) | **GET** /tasks/{id} | Get task by id
[**getTasks**](TaskApi.md#getTasks) | **GET** /tasks | Get tasks
[**updateTask**](TaskApi.md#updateTask) | **PUT** /tasks/{id} | Update task

<a name="getTaskById"></a>
# **getTaskById**
> Task getTaskById(id, bodyFormat)

Get task by id

Returns a specific task.   **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the containing page or blog post and its corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.TaskApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

TaskApi apiInstance = new TaskApi();
Long id = 789L; // Long | The ID of the task to be returned. If you don't know the task ID, use Get tasks and filter the results.
PrimaryBodyRepresentation bodyFormat = new PrimaryBodyRepresentation(); // PrimaryBodyRepresentation | The content format types to be returned in the `body` field of the response. If available, the representation will be available under a response field of the same name under the `body` field.
try {
    Task result = apiInstance.getTaskById(id, bodyFormat);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TaskApi#getTaskById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the task to be returned. If you don&#x27;t know the task ID, use Get tasks and filter the results. |
 **bodyFormat** | [**PrimaryBodyRepresentation**](.md)| The content format types to be returned in the &#x60;body&#x60; field of the response. If available, the representation will be available under a response field of the same name under the &#x60;body&#x60; field. | [optional]

### Return type

[**Task**](Task.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getTasks"></a>
# **getTasks**
> MultiEntityResultTask getTasks(bodyFormat, includeBlankTasks, status, taskId, spaceId, pageId, blogpostId, createdBy, assignedTo, completedBy, createdAtFrom, createdAtTo, dueAtFrom, dueAtTo, completedAtFrom, completedAtTo, cursor, limit)

Get tasks

Returns all tasks. The number of results is limited by the &#x60;limit&#x60; parameter and additional results (if available) will be available through the &#x60;next&#x60; URL present in the &#x60;Link&#x60; response header.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to access the Confluence site (&#x27;Can use&#x27; global permission). Only tasks that the user has permission to view will be returned.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.TaskApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

TaskApi apiInstance = new TaskApi();
PrimaryBodyRepresentation bodyFormat = new PrimaryBodyRepresentation(); // PrimaryBodyRepresentation | The content format types to be returned in the `body` field of the response. If available, the representation will be available under a response field of the same name under the `body` field.
Boolean includeBlankTasks = true; // Boolean | Specifies whether to include blank tasks in the response. Defaults to `true`.
String status = "status_example"; // String | Filters on the status of the task.
List<Long> taskId = Arrays.asList(56L); // List<Long> | Filters on task ID. Multiple IDs can be specified.
List<Long> spaceId = Arrays.asList(56L); // List<Long> | Filters on the space ID of the task. Multiple IDs can be specified.
List<Long> pageId = Arrays.asList(56L); // List<Long> | Filters on the page ID of the task. Multiple IDs can be specified. Note - page and blog post filters can be used in conjunction.
List<Long> blogpostId = Arrays.asList(56L); // List<Long> | Filters on the blog post ID of the task. Multiple IDs can be specified. Note - page and blog post filters can be used in conjunction.
List<String> createdBy = Arrays.asList("createdBy_example"); // List<String> | Filters on the Account ID of the user who created this task. Multiple IDs can be specified.
List<String> assignedTo = Arrays.asList("assignedTo_example"); // List<String> | Filters on the Account ID of the user to whom this task is assigned. Multiple IDs can be specified.
List<String> completedBy = Arrays.asList("completedBy_example"); // List<String> | Filters on the Account ID of the user who completed this task. Multiple IDs can be specified.
Long createdAtFrom = 789L; // Long | Filters on start of date-time range of task based on creation date (inclusive). Input is epoch time in milliseconds.
Long createdAtTo = 789L; // Long | Filters on end of date-time range of task based on creation date (inclusive). Input is epoch time in milliseconds.
Long dueAtFrom = 789L; // Long | Filters on start of date-time range of task based on due date (inclusive). Input is epoch time in milliseconds.
Long dueAtTo = 789L; // Long | Filters on end of date-time range of task based on due date (inclusive). Input is epoch time in milliseconds.
Long completedAtFrom = 789L; // Long | Filters on start of date-time range of task based on completion date (inclusive). Input is epoch time in milliseconds.
Long completedAtTo = 789L; // Long | Filters on end of date-time range of task based on completion date (inclusive). Input is epoch time in milliseconds.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of tasks per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultTask result = apiInstance.getTasks(bodyFormat, includeBlankTasks, status, taskId, spaceId, pageId, blogpostId, createdBy, assignedTo, completedBy, createdAtFrom, createdAtTo, dueAtFrom, dueAtTo, completedAtFrom, completedAtTo, cursor, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TaskApi#getTasks");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **bodyFormat** | [**PrimaryBodyRepresentation**](.md)| The content format types to be returned in the &#x60;body&#x60; field of the response. If available, the representation will be available under a response field of the same name under the &#x60;body&#x60; field. | [optional]
 **includeBlankTasks** | **Boolean**| Specifies whether to include blank tasks in the response. Defaults to &#x60;true&#x60;. | [optional]
 **status** | **String**| Filters on the status of the task. | [optional] [enum: complete, incomplete]
 **taskId** | [**List&lt;Long&gt;**](Long.md)| Filters on task ID. Multiple IDs can be specified. | [optional]
 **spaceId** | [**List&lt;Long&gt;**](Long.md)| Filters on the space ID of the task. Multiple IDs can be specified. | [optional]
 **pageId** | [**List&lt;Long&gt;**](Long.md)| Filters on the page ID of the task. Multiple IDs can be specified. Note - page and blog post filters can be used in conjunction. | [optional]
 **blogpostId** | [**List&lt;Long&gt;**](Long.md)| Filters on the blog post ID of the task. Multiple IDs can be specified. Note - page and blog post filters can be used in conjunction. | [optional]
 **createdBy** | [**List&lt;String&gt;**](String.md)| Filters on the Account ID of the user who created this task. Multiple IDs can be specified. | [optional]
 **assignedTo** | [**List&lt;String&gt;**](String.md)| Filters on the Account ID of the user to whom this task is assigned. Multiple IDs can be specified. | [optional]
 **completedBy** | [**List&lt;String&gt;**](String.md)| Filters on the Account ID of the user who completed this task. Multiple IDs can be specified. | [optional]
 **createdAtFrom** | **Long**| Filters on start of date-time range of task based on creation date (inclusive). Input is epoch time in milliseconds. | [optional]
 **createdAtTo** | **Long**| Filters on end of date-time range of task based on creation date (inclusive). Input is epoch time in milliseconds. | [optional]
 **dueAtFrom** | **Long**| Filters on start of date-time range of task based on due date (inclusive). Input is epoch time in milliseconds. | [optional]
 **dueAtTo** | **Long**| Filters on end of date-time range of task based on due date (inclusive). Input is epoch time in milliseconds. | [optional]
 **completedAtFrom** | **Long**| Filters on start of date-time range of task based on completion date (inclusive). Input is epoch time in milliseconds. | [optional]
 **completedAtTo** | **Long**| Filters on end of date-time range of task based on completion date (inclusive). Input is epoch time in milliseconds. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of tasks per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultTask**](MultiEntityResultTask.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateTask"></a>
# **updateTask**
> Task updateTask(body, id, bodyFormat)

Update task

Update a task by id. This endpoint currently only supports updating task status.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to edit the containing page or blog post and view its corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.TaskApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

TaskApi apiInstance = new TaskApi();
Object body = null; // Object | 
Long id = 789L; // Long | The ID of the task to be updated. If you don't know the task ID, use Get tasks and filter the results.
PrimaryBodyRepresentation bodyFormat = new PrimaryBodyRepresentation(); // PrimaryBodyRepresentation | The content format types to be returned in the `body` field of the response. If available, the representation will be available under a response field of the same name under the `body` field.
try {
    Task result = apiInstance.updateTask(body, id, bodyFormat);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TaskApi#updateTask");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Object**](Object.md)|  |
 **id** | **Long**| The ID of the task to be updated. If you don&#x27;t know the task ID, use Get tasks and filter the results. |
 **bodyFormat** | [**PrimaryBodyRepresentation**](.md)| The content format types to be returned in the &#x60;body&#x60; field of the response. If available, the representation will be available under a response field of the same name under the &#x60;body&#x60; field. | [optional]

### Return type

[**Task**](Task.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

