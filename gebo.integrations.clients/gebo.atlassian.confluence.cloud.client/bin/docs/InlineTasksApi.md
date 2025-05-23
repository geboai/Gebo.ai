# InlineTasksApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getTaskById**](InlineTasksApi.md#getTaskById) | **GET** /wiki/rest/api/inlinetasks/{inlineTaskId} | Get inline task based on global ID
[**searchTasks**](InlineTasksApi.md#searchTasks) | **GET** /wiki/rest/api/inlinetasks/search | Get inline tasks based on search parameters
[**updateTaskById**](InlineTasksApi.md#updateTaskById) | **PUT** /wiki/rest/api/inlinetasks/{inlineTaskId} | Update inline task given global ID

<a name="getTaskById"></a>
# **getTaskById**
> Task getTaskById(inlineTaskId)

Get inline task based on global ID

Deprecated, use [Confluence&#x27;s v2 API](https://developer.atlassian.com/cloud/confluence/rest/v2/intro/).  Returns inline task based on the global ID.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content associated with the task.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.InlineTasksApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

InlineTasksApi apiInstance = new InlineTasksApi();
String inlineTaskId = "inlineTaskId_example"; // String | Global ID of the inline task
try {
    Task result = apiInstance.getTaskById(inlineTaskId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling InlineTasksApi#getTaskById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **inlineTaskId** | **String**| Global ID of the inline task |

### Return type

[**Task**](Task.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="searchTasks"></a>
# **searchTasks**
> TaskPageResponse searchTasks(start, limit, spaceKey, pageId, assignee, creator, completedUser, duedateFrom, duedateTo, createdateFrom, createdateTo, completedateFrom, completedateTo, status)

Get inline tasks based on search parameters

Deprecated, use [Confluence&#x27;s v2 API](https://developer.atlassian.com/cloud/confluence/rest/v2/intro/).  Returns inline tasks based on the search query.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to access the Confluence site (&#x27;Can use&#x27; global permission). Only tasks in contents that the user has permission to view are returned.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.InlineTasksApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

InlineTasksApi apiInstance = new InlineTasksApi();
Integer start = 0; // Integer | The starting offset for the results.
Integer limit = 20; // Integer | The number of results to be returned.
String spaceKey = "spaceKey_example"; // String | The space key of a space. Multiple space keys can be specified.
String pageId = "pageId_example"; // String | The page id of a page. Multiple page ids can be specified.
String assignee = "assignee_example"; // String | Account ID of a user to whom a task is assigned. Multiple users can be specified.
String creator = "creator_example"; // String | Account ID of a user to who created a task. Multiple users can be specified.
String completedUser = "completedUser_example"; // String | Account ID of a user who completed a task. Multiple users can be specified.
Long duedateFrom = 789L; // Long | Start of date range based on due dates (inclusive).
Long duedateTo = 789L; // Long | End of date range based on due dates (inclusive).
Long createdateFrom = 789L; // Long | Start of date range based on create dates (inclusive).
Long createdateTo = 789L; // Long | End of date range based on create dates (inclusive).
Long completedateFrom = 789L; // Long | Start of date range based on complete dates (inclusive).
Long completedateTo = 789L; // Long | End of date range based on complete dates (inclusive).
String status = "status_example"; // String | The status of the task. (checked/unchecked)
try {
    TaskPageResponse result = apiInstance.searchTasks(start, limit, spaceKey, pageId, assignee, creator, completedUser, duedateFrom, duedateTo, createdateFrom, createdateTo, completedateFrom, completedateTo, status);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling InlineTasksApi#searchTasks");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **start** | **Integer**| The starting offset for the results. | [optional] [default to 0]
 **limit** | **Integer**| The number of results to be returned. | [optional] [default to 20]
 **spaceKey** | **String**| The space key of a space. Multiple space keys can be specified. | [optional]
 **pageId** | **String**| The page id of a page. Multiple page ids can be specified. | [optional]
 **assignee** | **String**| Account ID of a user to whom a task is assigned. Multiple users can be specified. | [optional]
 **creator** | **String**| Account ID of a user to who created a task. Multiple users can be specified. | [optional]
 **completedUser** | **String**| Account ID of a user who completed a task. Multiple users can be specified. | [optional]
 **duedateFrom** | **Long**| Start of date range based on due dates (inclusive). | [optional]
 **duedateTo** | **Long**| End of date range based on due dates (inclusive). | [optional]
 **createdateFrom** | **Long**| Start of date range based on create dates (inclusive). | [optional]
 **createdateTo** | **Long**| End of date range based on create dates (inclusive). | [optional]
 **completedateFrom** | **Long**| Start of date range based on complete dates (inclusive). | [optional]
 **completedateTo** | **Long**| End of date range based on complete dates (inclusive). | [optional]
 **status** | **String**| The status of the task. (checked/unchecked) | [optional] [enum: complete, incomplete]

### Return type

[**TaskPageResponse**](TaskPageResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateTaskById"></a>
# **updateTaskById**
> Task updateTaskById(body, inlineTaskId)

Update inline task given global ID

Updates an inline tasks status given its global ID  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to update the content associated with the task.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.InlineTasksApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

InlineTasksApi apiInstance = new InlineTasksApi();
TaskStatusUpdate body = new TaskStatusUpdate(); // TaskStatusUpdate | The updated task status.
String inlineTaskId = "inlineTaskId_example"; // String | Global ID of the inline task to update
try {
    Task result = apiInstance.updateTaskById(body, inlineTaskId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling InlineTasksApi#updateTaskById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**TaskStatusUpdate**](TaskStatusUpdate.md)| The updated task status. |
 **inlineTaskId** | **String**| Global ID of the inline task to update |

### Return type

[**Task**](Task.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

