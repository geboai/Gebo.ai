# IssueRemoteLinksApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createOrUpdateRemoteIssueLink**](IssueRemoteLinksApi.md#createOrUpdateRemoteIssueLink) | **POST** /rest/api/3/issue/{issueIdOrKey}/remotelink | Create or update remote issue link
[**deleteRemoteIssueLinkByGlobalId**](IssueRemoteLinksApi.md#deleteRemoteIssueLinkByGlobalId) | **DELETE** /rest/api/3/issue/{issueIdOrKey}/remotelink | Delete remote issue link by global ID
[**deleteRemoteIssueLinkById**](IssueRemoteLinksApi.md#deleteRemoteIssueLinkById) | **DELETE** /rest/api/3/issue/{issueIdOrKey}/remotelink/{linkId} | Delete remote issue link by ID
[**getRemoteIssueLinkById**](IssueRemoteLinksApi.md#getRemoteIssueLinkById) | **GET** /rest/api/3/issue/{issueIdOrKey}/remotelink/{linkId} | Get remote issue link by ID
[**getRemoteIssueLinks**](IssueRemoteLinksApi.md#getRemoteIssueLinks) | **GET** /rest/api/3/issue/{issueIdOrKey}/remotelink | Get remote issue links
[**updateRemoteIssueLink**](IssueRemoteLinksApi.md#updateRemoteIssueLink) | **PUT** /rest/api/3/issue/{issueIdOrKey}/remotelink/{linkId} | Update remote issue link by ID

<a name="createOrUpdateRemoteIssueLink"></a>
# **createOrUpdateRemoteIssueLink**
> RemoteIssueLinkIdentifies createOrUpdateRemoteIssueLink(body, issueIdOrKey)

Create or update remote issue link

Creates or updates a remote issue link for an issue.  If a &#x60;globalId&#x60; is provided and a remote issue link with that global ID is found it is updated. Any fields without values in the request are set to null. Otherwise, the remote issue link is created.  This operation requires [issue linking to be active](https://confluence.atlassian.com/x/yoXKM).  This operation can be accessed anonymously.  **[Permissions](#permissions) required:**   *  *Browse projects* and *Link issues* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project that the issue is in.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.IssueRemoteLinksApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

IssueRemoteLinksApi apiInstance = new IssueRemoteLinksApi();
Map<String, Object> body = new Map(); // Map<String, Object> | 
String issueIdOrKey = "issueIdOrKey_example"; // String | The ID or key of the issue.
try {
    RemoteIssueLinkIdentifies result = apiInstance.createOrUpdateRemoteIssueLink(body, issueIdOrKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IssueRemoteLinksApi#createOrUpdateRemoteIssueLink");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Map&lt;String, Object&gt;**](Map.md)|  |
 **issueIdOrKey** | **String**| The ID or key of the issue. |

### Return type

[**RemoteIssueLinkIdentifies**](RemoteIssueLinkIdentifies.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deleteRemoteIssueLinkByGlobalId"></a>
# **deleteRemoteIssueLinkByGlobalId**
> deleteRemoteIssueLinkByGlobalId(issueIdOrKey, globalId)

Delete remote issue link by global ID

Deletes the remote issue link from the issue using the link&#x27;s global ID. Where the global ID includes reserved URL characters these must be escaped in the request. For example, pass &#x60;system&#x3D;http://www.mycompany.com/support&amp;id&#x3D;1&#x60; as &#x60;system%3Dhttp%3A%2F%2Fwww.mycompany.com%2Fsupport%26id%3D1&#x60;.  This operation requires [issue linking to be active](https://confluence.atlassian.com/x/yoXKM).  This operation can be accessed anonymously.  **[Permissions](#permissions) required:**   *  *Browse projects* and *Link issues* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project that the issue is in.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is implemented, issue-level security permission to view the issue.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.IssueRemoteLinksApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

IssueRemoteLinksApi apiInstance = new IssueRemoteLinksApi();
String issueIdOrKey = "issueIdOrKey_example"; // String | The ID or key of the issue.
String globalId = "globalId_example"; // String | The global ID of a remote issue link.
try {
    apiInstance.deleteRemoteIssueLinkByGlobalId(issueIdOrKey, globalId);
} catch (ApiException e) {
    System.err.println("Exception when calling IssueRemoteLinksApi#deleteRemoteIssueLinkByGlobalId");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **issueIdOrKey** | **String**| The ID or key of the issue. |
 **globalId** | **String**| The global ID of a remote issue link. |

### Return type

null (empty response body)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="deleteRemoteIssueLinkById"></a>
# **deleteRemoteIssueLinkById**
> deleteRemoteIssueLinkById(issueIdOrKey, linkId)

Delete remote issue link by ID

Deletes a remote issue link from an issue.  This operation requires [issue linking to be active](https://confluence.atlassian.com/x/yoXKM).  This operation can be accessed anonymously.  **[Permissions](#permissions) required:**   *  *Browse projects*, *Edit issues*, and *Link issues* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project that the issue is in.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.IssueRemoteLinksApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

IssueRemoteLinksApi apiInstance = new IssueRemoteLinksApi();
String issueIdOrKey = "issueIdOrKey_example"; // String | The ID or key of the issue.
String linkId = "linkId_example"; // String | The ID of a remote issue link.
try {
    apiInstance.deleteRemoteIssueLinkById(issueIdOrKey, linkId);
} catch (ApiException e) {
    System.err.println("Exception when calling IssueRemoteLinksApi#deleteRemoteIssueLinkById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **issueIdOrKey** | **String**| The ID or key of the issue. |
 **linkId** | **String**| The ID of a remote issue link. |

### Return type

null (empty response body)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="getRemoteIssueLinkById"></a>
# **getRemoteIssueLinkById**
> RemoteIssueLink getRemoteIssueLinkById(issueIdOrKey, linkId)

Get remote issue link by ID

Returns a remote issue link for an issue.  This operation requires [issue linking to be active](https://confluence.atlassian.com/x/yoXKM).  This operation can be accessed anonymously.  **[Permissions](#permissions) required:**   *  *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project that the issue is in.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.IssueRemoteLinksApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

IssueRemoteLinksApi apiInstance = new IssueRemoteLinksApi();
String issueIdOrKey = "issueIdOrKey_example"; // String | The ID or key of the issue.
String linkId = "linkId_example"; // String | The ID of the remote issue link.
try {
    RemoteIssueLink result = apiInstance.getRemoteIssueLinkById(issueIdOrKey, linkId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IssueRemoteLinksApi#getRemoteIssueLinkById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **issueIdOrKey** | **String**| The ID or key of the issue. |
 **linkId** | **String**| The ID of the remote issue link. |

### Return type

[**RemoteIssueLink**](RemoteIssueLink.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getRemoteIssueLinks"></a>
# **getRemoteIssueLinks**
> RemoteIssueLink getRemoteIssueLinks(issueIdOrKey, globalId)

Get remote issue links

Returns the remote issue links for an issue. When a remote issue link global ID is provided the record with that global ID is returned, otherwise all remote issue links are returned. Where a global ID includes reserved URL characters these must be escaped in the request. For example, pass &#x60;system&#x3D;http://www.mycompany.com/support&amp;id&#x3D;1&#x60; as &#x60;system%3Dhttp%3A%2F%2Fwww.mycompany.com%2Fsupport%26id%3D1&#x60;.  This operation requires [issue linking to be active](https://confluence.atlassian.com/x/yoXKM).  This operation can be accessed anonymously.  **[Permissions](#permissions) required:**   *  *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project that the issue is in.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.IssueRemoteLinksApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

IssueRemoteLinksApi apiInstance = new IssueRemoteLinksApi();
String issueIdOrKey = "issueIdOrKey_example"; // String | The ID or key of the issue.
String globalId = "globalId_example"; // String | The global ID of the remote issue link.
try {
    RemoteIssueLink result = apiInstance.getRemoteIssueLinks(issueIdOrKey, globalId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IssueRemoteLinksApi#getRemoteIssueLinks");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **issueIdOrKey** | **String**| The ID or key of the issue. |
 **globalId** | **String**| The global ID of the remote issue link. | [optional]

### Return type

[**RemoteIssueLink**](RemoteIssueLink.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateRemoteIssueLink"></a>
# **updateRemoteIssueLink**
> Object updateRemoteIssueLink(body, issueIdOrKey, linkId)

Update remote issue link by ID

Updates a remote issue link for an issue.  Note: Fields without values in the request are set to null.  This operation requires [issue linking to be active](https://confluence.atlassian.com/x/yoXKM).  This operation can be accessed anonymously.  **[Permissions](#permissions) required:**   *  *Browse projects* and *Link issues* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project that the issue is in.  *  If [issue-level security](https://confluence.atlassian.com/x/J4lKLg) is configured, issue-level security permission to view the issue.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.IssueRemoteLinksApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

IssueRemoteLinksApi apiInstance = new IssueRemoteLinksApi();
Map<String, Object> body = new Map(); // Map<String, Object> | 
String issueIdOrKey = "issueIdOrKey_example"; // String | The ID or key of the issue.
String linkId = "linkId_example"; // String | The ID of the remote issue link.
try {
    Object result = apiInstance.updateRemoteIssueLink(body, issueIdOrKey, linkId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IssueRemoteLinksApi#updateRemoteIssueLink");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Map&lt;String, Object&gt;**](Map.md)|  |
 **issueIdOrKey** | **String**| The ID or key of the issue. |
 **linkId** | **String**| The ID of the remote issue link. |

### Return type

**Object**

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

