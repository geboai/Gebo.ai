# AppMigrationApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**appIssueFieldValueUpdateResourceUpdateIssueFieldsPut**](AppMigrationApi.md#appIssueFieldValueUpdateResourceUpdateIssueFieldsPut) | **PUT** /rest/atlassian-connect/1/migration/field | Bulk update custom field value
[**migrationResourceUpdateEntityPropertiesValuePut**](AppMigrationApi.md#migrationResourceUpdateEntityPropertiesValuePut) | **PUT** /rest/atlassian-connect/1/migration/properties/{entityType} | Bulk update entity properties
[**migrationResourceWorkflowRuleSearchPost**](AppMigrationApi.md#migrationResourceWorkflowRuleSearchPost) | **POST** /rest/atlassian-connect/1/migration/workflow/rule/search | Get workflow transition rule configurations

<a name="appIssueFieldValueUpdateResourceUpdateIssueFieldsPut"></a>
# **appIssueFieldValueUpdateResourceUpdateIssueFieldsPut**
> Object appIssueFieldValueUpdateResourceUpdateIssueFieldsPut(body, atlassianTransferId)

Bulk update custom field value

Updates the value of a custom field added by Connect apps on one or more issues. The values of up to 200 custom fields can be updated.  **[Permissions](#permissions) required:** Only Connect apps can make this request

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.api.AppMigrationApi;


AppMigrationApi apiInstance = new AppMigrationApi();
ConnectCustomFieldValues body = new ConnectCustomFieldValues(); // ConnectCustomFieldValues | 
UUID atlassianTransferId = new UUID(); // UUID | The ID of the transfer.
try {
    Object result = apiInstance.appIssueFieldValueUpdateResourceUpdateIssueFieldsPut(body, atlassianTransferId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AppMigrationApi#appIssueFieldValueUpdateResourceUpdateIssueFieldsPut");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ConnectCustomFieldValues**](ConnectCustomFieldValues.md)|  |
 **atlassianTransferId** | [**UUID**](.md)| The ID of the transfer. |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="migrationResourceUpdateEntityPropertiesValuePut"></a>
# **migrationResourceUpdateEntityPropertiesValuePut**
> migrationResourceUpdateEntityPropertiesValuePut(body, atlassianTransferId, entityType)

Bulk update entity properties

Updates the values of multiple entity properties for an object, up to 50 updates per request. This operation is for use by Connect apps during app migration.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.api.AppMigrationApi;


AppMigrationApi apiInstance = new AppMigrationApi();
List<EntityPropertyDetails> body = Arrays.asList(new EntityPropertyDetails()); // List<EntityPropertyDetails> | 
UUID atlassianTransferId = new UUID(); // UUID | The app migration transfer ID.
String entityType = "entityType_example"; // String | The type indicating the object that contains the entity properties.
try {
    apiInstance.migrationResourceUpdateEntityPropertiesValuePut(body, atlassianTransferId, entityType);
} catch (ApiException e) {
    System.err.println("Exception when calling AppMigrationApi#migrationResourceUpdateEntityPropertiesValuePut");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**List&lt;EntityPropertyDetails&gt;**](EntityPropertyDetails.md)|  |
 **atlassianTransferId** | [**UUID**](.md)| The app migration transfer ID. |
 **entityType** | **String**| The type indicating the object that contains the entity properties. | [enum: IssueProperty, CommentProperty, DashboardItemProperty, IssueTypeProperty, ProjectProperty, UserProperty, WorklogProperty, BoardProperty, SprintProperty]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="migrationResourceWorkflowRuleSearchPost"></a>
# **migrationResourceWorkflowRuleSearchPost**
> WorkflowRulesSearchDetails migrationResourceWorkflowRuleSearchPost(body, atlassianTransferId)

Get workflow transition rule configurations

Returns configurations for workflow transition rules migrated from server to cloud and owned by the calling Connect app.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.api.AppMigrationApi;


AppMigrationApi apiInstance = new AppMigrationApi();
WorkflowRulesSearch body = new WorkflowRulesSearch(); // WorkflowRulesSearch | 
UUID atlassianTransferId = new UUID(); // UUID | The app migration transfer ID.
try {
    WorkflowRulesSearchDetails result = apiInstance.migrationResourceWorkflowRuleSearchPost(body, atlassianTransferId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AppMigrationApi#migrationResourceWorkflowRuleSearchPost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**WorkflowRulesSearch**](WorkflowRulesSearch.md)|  |
 **atlassianTransferId** | [**UUID**](.md)| The app migration transfer ID. |

### Return type

[**WorkflowRulesSearchDetails**](WorkflowRulesSearchDetails.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

