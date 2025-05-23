# WorkflowSchemeDraftsApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createWorkflowSchemeDraftFromParent**](WorkflowSchemeDraftsApi.md#createWorkflowSchemeDraftFromParent) | **POST** /rest/api/3/workflowscheme/{id}/createdraft | Create draft workflow scheme
[**deleteDraftDefaultWorkflow**](WorkflowSchemeDraftsApi.md#deleteDraftDefaultWorkflow) | **DELETE** /rest/api/3/workflowscheme/{id}/draft/default | Delete draft default workflow
[**deleteDraftWorkflowMapping**](WorkflowSchemeDraftsApi.md#deleteDraftWorkflowMapping) | **DELETE** /rest/api/3/workflowscheme/{id}/draft/workflow | Delete issue types for workflow in draft workflow scheme
[**deleteWorkflowSchemeDraft**](WorkflowSchemeDraftsApi.md#deleteWorkflowSchemeDraft) | **DELETE** /rest/api/3/workflowscheme/{id}/draft | Delete draft workflow scheme
[**deleteWorkflowSchemeDraftIssueType**](WorkflowSchemeDraftsApi.md#deleteWorkflowSchemeDraftIssueType) | **DELETE** /rest/api/3/workflowscheme/{id}/draft/issuetype/{issueType} | Delete workflow for issue type in draft workflow scheme
[**getDraftDefaultWorkflow**](WorkflowSchemeDraftsApi.md#getDraftDefaultWorkflow) | **GET** /rest/api/3/workflowscheme/{id}/draft/default | Get draft default workflow
[**getDraftWorkflow**](WorkflowSchemeDraftsApi.md#getDraftWorkflow) | **GET** /rest/api/3/workflowscheme/{id}/draft/workflow | Get issue types for workflows in draft workflow scheme
[**getWorkflowSchemeDraft**](WorkflowSchemeDraftsApi.md#getWorkflowSchemeDraft) | **GET** /rest/api/3/workflowscheme/{id}/draft | Get draft workflow scheme
[**getWorkflowSchemeDraftIssueType**](WorkflowSchemeDraftsApi.md#getWorkflowSchemeDraftIssueType) | **GET** /rest/api/3/workflowscheme/{id}/draft/issuetype/{issueType} | Get workflow for issue type in draft workflow scheme
[**publishDraftWorkflowScheme**](WorkflowSchemeDraftsApi.md#publishDraftWorkflowScheme) | **POST** /rest/api/3/workflowscheme/{id}/draft/publish | Publish draft workflow scheme
[**setWorkflowSchemeDraftIssueType**](WorkflowSchemeDraftsApi.md#setWorkflowSchemeDraftIssueType) | **PUT** /rest/api/3/workflowscheme/{id}/draft/issuetype/{issueType} | Set workflow for issue type in draft workflow scheme
[**updateDraftDefaultWorkflow**](WorkflowSchemeDraftsApi.md#updateDraftDefaultWorkflow) | **PUT** /rest/api/3/workflowscheme/{id}/draft/default | Update draft default workflow
[**updateDraftWorkflowMapping**](WorkflowSchemeDraftsApi.md#updateDraftWorkflowMapping) | **PUT** /rest/api/3/workflowscheme/{id}/draft/workflow | Set issue types for workflow in workflow scheme
[**updateWorkflowSchemeDraft**](WorkflowSchemeDraftsApi.md#updateWorkflowSchemeDraft) | **PUT** /rest/api/3/workflowscheme/{id}/draft | Update draft workflow scheme

<a name="createWorkflowSchemeDraftFromParent"></a>
# **createWorkflowSchemeDraftFromParent**
> WorkflowScheme createWorkflowSchemeDraftFromParent(id)

Create draft workflow scheme

Create a draft workflow scheme from an active workflow scheme, by copying the active workflow scheme. Note that an active workflow scheme can only have one draft workflow scheme.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.WorkflowSchemeDraftsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

WorkflowSchemeDraftsApi apiInstance = new WorkflowSchemeDraftsApi();
Long id = 789L; // Long | The ID of the active workflow scheme that the draft is created from.
try {
    WorkflowScheme result = apiInstance.createWorkflowSchemeDraftFromParent(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WorkflowSchemeDraftsApi#createWorkflowSchemeDraftFromParent");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the active workflow scheme that the draft is created from. |

### Return type

[**WorkflowScheme**](WorkflowScheme.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="deleteDraftDefaultWorkflow"></a>
# **deleteDraftDefaultWorkflow**
> WorkflowScheme deleteDraftDefaultWorkflow(id)

Delete draft default workflow

Resets the default workflow for a workflow scheme&#x27;s draft. That is, the default workflow is set to Jira&#x27;s system workflow (the *jira* workflow).  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.WorkflowSchemeDraftsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

WorkflowSchemeDraftsApi apiInstance = new WorkflowSchemeDraftsApi();
Long id = 789L; // Long | The ID of the workflow scheme that the draft belongs to.
try {
    WorkflowScheme result = apiInstance.deleteDraftDefaultWorkflow(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WorkflowSchemeDraftsApi#deleteDraftDefaultWorkflow");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the workflow scheme that the draft belongs to. |

### Return type

[**WorkflowScheme**](WorkflowScheme.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="deleteDraftWorkflowMapping"></a>
# **deleteDraftWorkflowMapping**
> deleteDraftWorkflowMapping(id, workflowName)

Delete issue types for workflow in draft workflow scheme

Deletes the workflow-issue type mapping for a workflow in a workflow scheme&#x27;s draft.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.WorkflowSchemeDraftsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

WorkflowSchemeDraftsApi apiInstance = new WorkflowSchemeDraftsApi();
Long id = 789L; // Long | The ID of the workflow scheme that the draft belongs to.
String workflowName = "workflowName_example"; // String | The name of the workflow.
try {
    apiInstance.deleteDraftWorkflowMapping(id, workflowName);
} catch (ApiException e) {
    System.err.println("Exception when calling WorkflowSchemeDraftsApi#deleteDraftWorkflowMapping");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the workflow scheme that the draft belongs to. |
 **workflowName** | **String**| The name of the workflow. |

### Return type

null (empty response body)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="deleteWorkflowSchemeDraft"></a>
# **deleteWorkflowSchemeDraft**
> deleteWorkflowSchemeDraft(id)

Delete draft workflow scheme

Deletes a draft workflow scheme.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.WorkflowSchemeDraftsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

WorkflowSchemeDraftsApi apiInstance = new WorkflowSchemeDraftsApi();
Long id = 789L; // Long | The ID of the active workflow scheme that the draft was created from.
try {
    apiInstance.deleteWorkflowSchemeDraft(id);
} catch (ApiException e) {
    System.err.println("Exception when calling WorkflowSchemeDraftsApi#deleteWorkflowSchemeDraft");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the active workflow scheme that the draft was created from. |

### Return type

null (empty response body)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="deleteWorkflowSchemeDraftIssueType"></a>
# **deleteWorkflowSchemeDraftIssueType**
> WorkflowScheme deleteWorkflowSchemeDraftIssueType(id, issueType)

Delete workflow for issue type in draft workflow scheme

Deletes the issue type-workflow mapping for an issue type in a workflow scheme&#x27;s draft.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.WorkflowSchemeDraftsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

WorkflowSchemeDraftsApi apiInstance = new WorkflowSchemeDraftsApi();
Long id = 789L; // Long | The ID of the workflow scheme that the draft belongs to.
String issueType = "issueType_example"; // String | The ID of the issue type.
try {
    WorkflowScheme result = apiInstance.deleteWorkflowSchemeDraftIssueType(id, issueType);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WorkflowSchemeDraftsApi#deleteWorkflowSchemeDraftIssueType");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the workflow scheme that the draft belongs to. |
 **issueType** | **String**| The ID of the issue type. |

### Return type

[**WorkflowScheme**](WorkflowScheme.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getDraftDefaultWorkflow"></a>
# **getDraftDefaultWorkflow**
> DefaultWorkflow getDraftDefaultWorkflow(id)

Get draft default workflow

Returns the default workflow for a workflow scheme&#x27;s draft. The default workflow is the workflow that is assigned any issue types that have not been mapped to any other workflow. The default workflow has *All Unassigned Issue Types* listed in its issue types for the workflow scheme in Jira.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.WorkflowSchemeDraftsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

WorkflowSchemeDraftsApi apiInstance = new WorkflowSchemeDraftsApi();
Long id = 789L; // Long | The ID of the workflow scheme that the draft belongs to.
try {
    DefaultWorkflow result = apiInstance.getDraftDefaultWorkflow(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WorkflowSchemeDraftsApi#getDraftDefaultWorkflow");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the workflow scheme that the draft belongs to. |

### Return type

[**DefaultWorkflow**](DefaultWorkflow.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getDraftWorkflow"></a>
# **getDraftWorkflow**
> IssueTypesWorkflowMapping getDraftWorkflow(id, workflowName)

Get issue types for workflows in draft workflow scheme

Returns the workflow-issue type mappings for a workflow scheme&#x27;s draft.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.WorkflowSchemeDraftsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

WorkflowSchemeDraftsApi apiInstance = new WorkflowSchemeDraftsApi();
Long id = 789L; // Long | The ID of the workflow scheme that the draft belongs to.
String workflowName = "workflowName_example"; // String | The name of a workflow in the scheme. Limits the results to the workflow-issue type mapping for the specified workflow.
try {
    IssueTypesWorkflowMapping result = apiInstance.getDraftWorkflow(id, workflowName);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WorkflowSchemeDraftsApi#getDraftWorkflow");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the workflow scheme that the draft belongs to. |
 **workflowName** | **String**| The name of a workflow in the scheme. Limits the results to the workflow-issue type mapping for the specified workflow. | [optional]

### Return type

[**IssueTypesWorkflowMapping**](IssueTypesWorkflowMapping.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getWorkflowSchemeDraft"></a>
# **getWorkflowSchemeDraft**
> WorkflowScheme getWorkflowSchemeDraft(id)

Get draft workflow scheme

Returns the draft workflow scheme for an active workflow scheme. Draft workflow schemes allow changes to be made to the active workflow schemes: When an active workflow scheme is updated, a draft copy is created. The draft is modified, then the changes in the draft are copied back to the active workflow scheme. See [Configuring workflow schemes](https://confluence.atlassian.com/x/tohKLg) for more information.   Note that:   *  Only active workflow schemes can have draft workflow schemes.  *  An active workflow scheme can only have one draft workflow scheme.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.WorkflowSchemeDraftsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

WorkflowSchemeDraftsApi apiInstance = new WorkflowSchemeDraftsApi();
Long id = 789L; // Long | The ID of the active workflow scheme that the draft was created from.
try {
    WorkflowScheme result = apiInstance.getWorkflowSchemeDraft(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WorkflowSchemeDraftsApi#getWorkflowSchemeDraft");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the active workflow scheme that the draft was created from. |

### Return type

[**WorkflowScheme**](WorkflowScheme.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getWorkflowSchemeDraftIssueType"></a>
# **getWorkflowSchemeDraftIssueType**
> IssueTypeWorkflowMapping getWorkflowSchemeDraftIssueType(id, issueType)

Get workflow for issue type in draft workflow scheme

Returns the issue type-workflow mapping for an issue type in a workflow scheme&#x27;s draft.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.WorkflowSchemeDraftsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

WorkflowSchemeDraftsApi apiInstance = new WorkflowSchemeDraftsApi();
Long id = 789L; // Long | The ID of the workflow scheme that the draft belongs to.
String issueType = "issueType_example"; // String | The ID of the issue type.
try {
    IssueTypeWorkflowMapping result = apiInstance.getWorkflowSchemeDraftIssueType(id, issueType);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WorkflowSchemeDraftsApi#getWorkflowSchemeDraftIssueType");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the workflow scheme that the draft belongs to. |
 **issueType** | **String**| The ID of the issue type. |

### Return type

[**IssueTypeWorkflowMapping**](IssueTypeWorkflowMapping.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="publishDraftWorkflowScheme"></a>
# **publishDraftWorkflowScheme**
> publishDraftWorkflowScheme(body, id, validateOnly)

Publish draft workflow scheme

Publishes a draft workflow scheme.  Where the draft workflow includes new workflow statuses for an issue type, mappings are provided to update issues with the original workflow status to the new workflow status.  This operation is [asynchronous](#async). Follow the &#x60;location&#x60; link in the response to determine the status of the task and use [Get task](#api-rest-api-3-task-taskId-get) to obtain updates.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.WorkflowSchemeDraftsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

WorkflowSchemeDraftsApi apiInstance = new WorkflowSchemeDraftsApi();
PublishDraftWorkflowScheme body = new PublishDraftWorkflowScheme(); // PublishDraftWorkflowScheme | Details of the status mappings.
Long id = 789L; // Long | The ID of the workflow scheme that the draft belongs to.
Boolean validateOnly = false; // Boolean | Whether the request only performs a validation.
try {
    apiInstance.publishDraftWorkflowScheme(body, id, validateOnly);
} catch (ApiException e) {
    System.err.println("Exception when calling WorkflowSchemeDraftsApi#publishDraftWorkflowScheme");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**PublishDraftWorkflowScheme**](PublishDraftWorkflowScheme.md)| Details of the status mappings. |
 **id** | **Long**| The ID of the workflow scheme that the draft belongs to. |
 **validateOnly** | **Boolean**| Whether the request only performs a validation. | [optional] [default to false]

### Return type

null (empty response body)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="setWorkflowSchemeDraftIssueType"></a>
# **setWorkflowSchemeDraftIssueType**
> WorkflowScheme setWorkflowSchemeDraftIssueType(body, id, issueType)

Set workflow for issue type in draft workflow scheme

Sets the workflow for an issue type in a workflow scheme&#x27;s draft.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.WorkflowSchemeDraftsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

WorkflowSchemeDraftsApi apiInstance = new WorkflowSchemeDraftsApi();
IssueTypeWorkflowMapping body = new IssueTypeWorkflowMapping(); // IssueTypeWorkflowMapping | The issue type-project mapping.
Long id = 789L; // Long | The ID of the workflow scheme that the draft belongs to.
String issueType = "issueType_example"; // String | The ID of the issue type.
try {
    WorkflowScheme result = apiInstance.setWorkflowSchemeDraftIssueType(body, id, issueType);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WorkflowSchemeDraftsApi#setWorkflowSchemeDraftIssueType");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**IssueTypeWorkflowMapping**](IssueTypeWorkflowMapping.md)| The issue type-project mapping. |
 **id** | **Long**| The ID of the workflow scheme that the draft belongs to. |
 **issueType** | **String**| The ID of the issue type. |

### Return type

[**WorkflowScheme**](WorkflowScheme.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateDraftDefaultWorkflow"></a>
# **updateDraftDefaultWorkflow**
> WorkflowScheme updateDraftDefaultWorkflow(body, id)

Update draft default workflow

Sets the default workflow for a workflow scheme&#x27;s draft.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.WorkflowSchemeDraftsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

WorkflowSchemeDraftsApi apiInstance = new WorkflowSchemeDraftsApi();
DefaultWorkflow body = new DefaultWorkflow(); // DefaultWorkflow | The object for the new default workflow.
Long id = 789L; // Long | The ID of the workflow scheme that the draft belongs to.
try {
    WorkflowScheme result = apiInstance.updateDraftDefaultWorkflow(body, id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WorkflowSchemeDraftsApi#updateDraftDefaultWorkflow");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**DefaultWorkflow**](DefaultWorkflow.md)| The object for the new default workflow. |
 **id** | **Long**| The ID of the workflow scheme that the draft belongs to. |

### Return type

[**WorkflowScheme**](WorkflowScheme.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateDraftWorkflowMapping"></a>
# **updateDraftWorkflowMapping**
> WorkflowScheme updateDraftWorkflowMapping(body, workflowName, id)

Set issue types for workflow in workflow scheme

Sets the issue types for a workflow in a workflow scheme&#x27;s draft. The workflow can also be set as the default workflow for the draft workflow scheme. Unmapped issues types are mapped to the default workflow.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.WorkflowSchemeDraftsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

WorkflowSchemeDraftsApi apiInstance = new WorkflowSchemeDraftsApi();
IssueTypesWorkflowMapping body = new IssueTypesWorkflowMapping(); // IssueTypesWorkflowMapping | 
String workflowName = "workflowName_example"; // String | The name of the workflow.
Long id = 789L; // Long | The ID of the workflow scheme that the draft belongs to.
try {
    WorkflowScheme result = apiInstance.updateDraftWorkflowMapping(body, workflowName, id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WorkflowSchemeDraftsApi#updateDraftWorkflowMapping");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**IssueTypesWorkflowMapping**](IssueTypesWorkflowMapping.md)|  |
 **workflowName** | **String**| The name of the workflow. |
 **id** | **Long**| The ID of the workflow scheme that the draft belongs to. |

### Return type

[**WorkflowScheme**](WorkflowScheme.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateWorkflowSchemeDraft"></a>
# **updateWorkflowSchemeDraft**
> WorkflowScheme updateWorkflowSchemeDraft(body, id)

Update draft workflow scheme

Updates a draft workflow scheme. If a draft workflow scheme does not exist for the active workflow scheme, then a draft is created. Note that an active workflow scheme can only have one draft workflow scheme.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.WorkflowSchemeDraftsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

WorkflowSchemeDraftsApi apiInstance = new WorkflowSchemeDraftsApi();
WorkflowScheme body = new WorkflowScheme(); // WorkflowScheme | 
Long id = 789L; // Long | The ID of the active workflow scheme that the draft was created from.
try {
    WorkflowScheme result = apiInstance.updateWorkflowSchemeDraft(body, id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling WorkflowSchemeDraftsApi#updateWorkflowSchemeDraft");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**WorkflowScheme**](WorkflowScheme.md)|  |
 **id** | **Long**| The ID of the active workflow scheme that the draft was created from. |

### Return type

[**WorkflowScheme**](WorkflowScheme.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

