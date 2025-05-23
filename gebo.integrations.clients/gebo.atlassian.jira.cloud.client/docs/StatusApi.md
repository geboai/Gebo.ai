# StatusApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createStatuses**](StatusApi.md#createStatuses) | **POST** /rest/api/3/statuses | Bulk create statuses
[**deleteStatusesById**](StatusApi.md#deleteStatusesById) | **DELETE** /rest/api/3/statuses | Bulk delete Statuses
[**getProjectIssueTypeUsagesForStatus**](StatusApi.md#getProjectIssueTypeUsagesForStatus) | **GET** /rest/api/3/statuses/{statusId}/project/{projectId}/issueTypeUsages | Get issue type usages by status and project
[**getProjectUsagesForStatus**](StatusApi.md#getProjectUsagesForStatus) | **GET** /rest/api/3/statuses/{statusId}/projectUsages | Get project usages by status
[**getStatusesById**](StatusApi.md#getStatusesById) | **GET** /rest/api/3/statuses | Bulk get statuses
[**getWorkflowUsagesForStatus**](StatusApi.md#getWorkflowUsagesForStatus) | **GET** /rest/api/3/statuses/{statusId}/workflowUsages | Get workflow usages by status
[**search**](StatusApi.md#search) | **GET** /rest/api/3/statuses/search | Search statuses paginated
[**updateStatuses**](StatusApi.md#updateStatuses) | **PUT** /rest/api/3/statuses | Bulk update statuses

<a name="createStatuses"></a>
# **createStatuses**
> List&lt;JiraStatus&gt; createStatuses(body)

Bulk create statuses

Creates statuses for a global or project scope.  **[Permissions](#permissions) required:**   *  *Administer projects* [project permission.](https://confluence.atlassian.com/x/yodKLg)  *  *Administer Jira* [project permission.](https://confluence.atlassian.com/x/yodKLg)

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.StatusApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

StatusApi apiInstance = new StatusApi();
StatusCreateRequest body = new StatusCreateRequest(); // StatusCreateRequest | Details of the statuses being created and their scope.
try {
    List<JiraStatus> result = apiInstance.createStatuses(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling StatusApi#createStatuses");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**StatusCreateRequest**](StatusCreateRequest.md)| Details of the statuses being created and their scope. |

### Return type

[**List&lt;JiraStatus&gt;**](JiraStatus.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deleteStatusesById"></a>
# **deleteStatusesById**
> Object deleteStatusesById(id)

Bulk delete Statuses

Deletes statuses by ID.  **[Permissions](#permissions) required:**   *  *Administer projects* [project permission.](https://confluence.atlassian.com/x/yodKLg)  *  *Administer Jira* [project permission.](https://confluence.atlassian.com/x/yodKLg)

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.StatusApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

StatusApi apiInstance = new StatusApi();
List<String> id = Arrays.asList("id_example"); // List<String> | The list of status IDs. To include multiple IDs, provide an ampersand-separated list. For example, id=10000&id=10001.  Min items `1`, Max items `50`
try {
    Object result = apiInstance.deleteStatusesById(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling StatusApi#deleteStatusesById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | [**List&lt;String&gt;**](String.md)| The list of status IDs. To include multiple IDs, provide an ampersand-separated list. For example, id&#x3D;10000&amp;id&#x3D;10001.  Min items &#x60;1&#x60;, Max items &#x60;50&#x60; |

### Return type

**Object**

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getProjectIssueTypeUsagesForStatus"></a>
# **getProjectIssueTypeUsagesForStatus**
> StatusProjectIssueTypeUsageDTO getProjectIssueTypeUsagesForStatus(statusId, projectId, nextPageToken, maxResults)

Get issue type usages by status and project

Returns a page of issue types in a project using a given status.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.StatusApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

StatusApi apiInstance = new StatusApi();
String statusId = "statusId_example"; // String | The statusId to fetch issue type usages for
String projectId = "projectId_example"; // String | The projectId to fetch issue type usages for
String nextPageToken = "nextPageToken_example"; // String | The cursor for pagination
Integer maxResults = 50; // Integer | The maximum number of results to return. Must be an integer between 1 and 200.
try {
    StatusProjectIssueTypeUsageDTO result = apiInstance.getProjectIssueTypeUsagesForStatus(statusId, projectId, nextPageToken, maxResults);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling StatusApi#getProjectIssueTypeUsagesForStatus");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **statusId** | **String**| The statusId to fetch issue type usages for |
 **projectId** | **String**| The projectId to fetch issue type usages for |
 **nextPageToken** | **String**| The cursor for pagination | [optional]
 **maxResults** | **Integer**| The maximum number of results to return. Must be an integer between 1 and 200. | [optional] [default to 50]

### Return type

[**StatusProjectIssueTypeUsageDTO**](StatusProjectIssueTypeUsageDTO.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getProjectUsagesForStatus"></a>
# **getProjectUsagesForStatus**
> StatusProjectUsageDTO getProjectUsagesForStatus(statusId, nextPageToken, maxResults)

Get project usages by status

Returns a page of projects using a given status.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.StatusApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

StatusApi apiInstance = new StatusApi();
String statusId = "statusId_example"; // String | The statusId to fetch project usages for
String nextPageToken = "nextPageToken_example"; // String | The cursor for pagination
Integer maxResults = 50; // Integer | The maximum number of results to return. Must be an integer between 1 and 200.
try {
    StatusProjectUsageDTO result = apiInstance.getProjectUsagesForStatus(statusId, nextPageToken, maxResults);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling StatusApi#getProjectUsagesForStatus");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **statusId** | **String**| The statusId to fetch project usages for |
 **nextPageToken** | **String**| The cursor for pagination | [optional]
 **maxResults** | **Integer**| The maximum number of results to return. Must be an integer between 1 and 200. | [optional] [default to 50]

### Return type

[**StatusProjectUsageDTO**](StatusProjectUsageDTO.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getStatusesById"></a>
# **getStatusesById**
> List&lt;JiraStatus&gt; getStatusesById(id, expand)

Bulk get statuses

Returns a list of the statuses specified by one or more status IDs.  **[Permissions](#permissions) required:**   *  *Administer projects* [project permission.](https://confluence.atlassian.com/x/yodKLg)  *  *Administer Jira* [project permission.](https://confluence.atlassian.com/x/yodKLg)

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.StatusApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

StatusApi apiInstance = new StatusApi();
List<String> id = Arrays.asList("id_example"); // List<String> | The list of status IDs. To include multiple IDs, provide an ampersand-separated list. For example, id=10000&id=10001.  Min items `1`, Max items `50`
String expand = "expand_example"; // String | Deprecated. See the [deprecation notice](https://developer.atlassian.com/cloud/jira/platform/changelog/#CHANGE-2298) for details.  Use [expand](#expansion) to include additional information in the response. This parameter accepts a comma-separated list. Expand options include:   *  `usages` Returns the project and issue types that use the status in their workflow.  *  `workflowUsages` Returns the workflows that use the status.
try {
    List<JiraStatus> result = apiInstance.getStatusesById(id, expand);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling StatusApi#getStatusesById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | [**List&lt;String&gt;**](String.md)| The list of status IDs. To include multiple IDs, provide an ampersand-separated list. For example, id&#x3D;10000&amp;id&#x3D;10001.  Min items &#x60;1&#x60;, Max items &#x60;50&#x60; |
 **expand** | **String**| Deprecated. See the [deprecation notice](https://developer.atlassian.com/cloud/jira/platform/changelog/#CHANGE-2298) for details.  Use [expand](#expansion) to include additional information in the response. This parameter accepts a comma-separated list. Expand options include:   *  &#x60;usages&#x60; Returns the project and issue types that use the status in their workflow.  *  &#x60;workflowUsages&#x60; Returns the workflows that use the status. | [optional]

### Return type

[**List&lt;JiraStatus&gt;**](JiraStatus.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getWorkflowUsagesForStatus"></a>
# **getWorkflowUsagesForStatus**
> StatusWorkflowUsageDTO getWorkflowUsagesForStatus(statusId, nextPageToken, maxResults)

Get workflow usages by status

Returns a page of workflows using a given status.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.StatusApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

StatusApi apiInstance = new StatusApi();
String statusId = "statusId_example"; // String | The statusId to fetch workflow usages for
String nextPageToken = "nextPageToken_example"; // String | The cursor for pagination
Integer maxResults = 50; // Integer | The maximum number of results to return. Must be an integer between 1 and 200.
try {
    StatusWorkflowUsageDTO result = apiInstance.getWorkflowUsagesForStatus(statusId, nextPageToken, maxResults);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling StatusApi#getWorkflowUsagesForStatus");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **statusId** | **String**| The statusId to fetch workflow usages for |
 **nextPageToken** | **String**| The cursor for pagination | [optional]
 **maxResults** | **Integer**| The maximum number of results to return. Must be an integer between 1 and 200. | [optional] [default to 50]

### Return type

[**StatusWorkflowUsageDTO**](StatusWorkflowUsageDTO.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="search"></a>
# **search**
> PageOfStatuses search(expand, projectId, startAt, maxResults, searchString, statusCategory)

Search statuses paginated

Returns a [paginated](https://developer.atlassian.com/cloud/jira/platform/rest/v3/intro/#pagination) list of statuses that match a search on name or project.  **[Permissions](#permissions) required:**   *  *Administer projects* [project permission.](https://confluence.atlassian.com/x/yodKLg)  *  *Administer Jira* [project permission.](https://confluence.atlassian.com/x/yodKLg)

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.StatusApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

StatusApi apiInstance = new StatusApi();
String expand = "expand_example"; // String | Deprecated. See the [deprecation notice](https://developer.atlassian.com/cloud/jira/platform/changelog/#CHANGE-2298) for details.  Use [expand](#expansion) to include additional information in the response. This parameter accepts a comma-separated list. Expand options include:   *  `usages` Returns the project and issue types that use the status in their workflow.  *  `workflowUsages` Returns the workflows that use the status.
String projectId = "projectId_example"; // String | The project the status is part of or null for global statuses.
Long startAt = 0L; // Long | The index of the first item to return in a page of results (page offset).
Integer maxResults = 200; // Integer | The maximum number of items to return per page.
String searchString = "searchString_example"; // String | Term to match status names against or null to search for all statuses in the search scope.
String statusCategory = "statusCategory_example"; // String | Category of the status to filter by. The supported values are: `TODO`, `IN_PROGRESS`, and `DONE`.
try {
    PageOfStatuses result = apiInstance.search(expand, projectId, startAt, maxResults, searchString, statusCategory);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling StatusApi#search");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **expand** | **String**| Deprecated. See the [deprecation notice](https://developer.atlassian.com/cloud/jira/platform/changelog/#CHANGE-2298) for details.  Use [expand](#expansion) to include additional information in the response. This parameter accepts a comma-separated list. Expand options include:   *  &#x60;usages&#x60; Returns the project and issue types that use the status in their workflow.  *  &#x60;workflowUsages&#x60; Returns the workflows that use the status. | [optional]
 **projectId** | **String**| The project the status is part of or null for global statuses. | [optional]
 **startAt** | **Long**| The index of the first item to return in a page of results (page offset). | [optional] [default to 0]
 **maxResults** | **Integer**| The maximum number of items to return per page. | [optional] [default to 200]
 **searchString** | **String**| Term to match status names against or null to search for all statuses in the search scope. | [optional]
 **statusCategory** | **String**| Category of the status to filter by. The supported values are: &#x60;TODO&#x60;, &#x60;IN_PROGRESS&#x60;, and &#x60;DONE&#x60;. | [optional]

### Return type

[**PageOfStatuses**](PageOfStatuses.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateStatuses"></a>
# **updateStatuses**
> Object updateStatuses(body)

Bulk update statuses

Updates statuses by ID.  **[Permissions](#permissions) required:**   *  *Administer projects* [project permission.](https://confluence.atlassian.com/x/yodKLg)  *  *Administer Jira* [project permission.](https://confluence.atlassian.com/x/yodKLg)

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.StatusApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

StatusApi apiInstance = new StatusApi();
StatusUpdateRequest body = new StatusUpdateRequest(); // StatusUpdateRequest | The list of statuses that will be updated.
try {
    Object result = apiInstance.updateStatuses(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling StatusApi#updateStatuses");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**StatusUpdateRequest**](StatusUpdateRequest.md)| The list of statuses that will be updated. |

### Return type

**Object**

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

