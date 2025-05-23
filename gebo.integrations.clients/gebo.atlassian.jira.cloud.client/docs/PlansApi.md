# PlansApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**archivePlan**](PlansApi.md#archivePlan) | **PUT** /rest/api/3/plans/plan/{planId}/archive | Archive plan
[**createPlan**](PlansApi.md#createPlan) | **POST** /rest/api/3/plans/plan | Create plan
[**duplicatePlan**](PlansApi.md#duplicatePlan) | **POST** /rest/api/3/plans/plan/{planId}/duplicate | Duplicate plan
[**getPlan**](PlansApi.md#getPlan) | **GET** /rest/api/3/plans/plan/{planId} | Get plan
[**getPlans**](PlansApi.md#getPlans) | **GET** /rest/api/3/plans/plan | Get plans paginated
[**trashPlan**](PlansApi.md#trashPlan) | **PUT** /rest/api/3/plans/plan/{planId}/trash | Trash plan
[**updatePlan**](PlansApi.md#updatePlan) | **PUT** /rest/api/3/plans/plan/{planId} | Update plan

<a name="archivePlan"></a>
# **archivePlan**
> Object archivePlan(planId)

Archive plan

Archives a plan.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.PlansApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

PlansApi apiInstance = new PlansApi();
Long planId = 789L; // Long | The ID of the plan.
try {
    Object result = apiInstance.archivePlan(planId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PlansApi#archivePlan");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **planId** | **Long**| The ID of the plan. |

### Return type

**Object**

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="createPlan"></a>
# **createPlan**
> Long createPlan(body, useGroupId)

Create plan

Creates a plan.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.PlansApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

PlansApi apiInstance = new PlansApi();
CreatePlanRequest body = new CreatePlanRequest(); // CreatePlanRequest | 
Boolean useGroupId = false; // Boolean | Whether to accept group IDs instead of group names. Group names are deprecated.
try {
    Long result = apiInstance.createPlan(body, useGroupId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PlansApi#createPlan");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**CreatePlanRequest**](CreatePlanRequest.md)|  |
 **useGroupId** | **Boolean**| Whether to accept group IDs instead of group names. Group names are deprecated. | [optional] [default to false]

### Return type

**Long**

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="duplicatePlan"></a>
# **duplicatePlan**
> Long duplicatePlan(body, planId)

Duplicate plan

Duplicates a plan.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.PlansApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

PlansApi apiInstance = new PlansApi();
DuplicatePlanRequest body = new DuplicatePlanRequest(); // DuplicatePlanRequest | 
Long planId = 789L; // Long | The ID of the plan.
try {
    Long result = apiInstance.duplicatePlan(body, planId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PlansApi#duplicatePlan");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**DuplicatePlanRequest**](DuplicatePlanRequest.md)|  |
 **planId** | **Long**| The ID of the plan. |

### Return type

**Long**

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getPlan"></a>
# **getPlan**
> GetPlanResponse getPlan(planId, useGroupId)

Get plan

Returns a plan.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.PlansApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

PlansApi apiInstance = new PlansApi();
Long planId = 789L; // Long | The ID of the plan.
Boolean useGroupId = false; // Boolean | Whether to return group IDs instead of group names. Group names are deprecated.
try {
    GetPlanResponse result = apiInstance.getPlan(planId, useGroupId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PlansApi#getPlan");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **planId** | **Long**| The ID of the plan. |
 **useGroupId** | **Boolean**| Whether to return group IDs instead of group names. Group names are deprecated. | [optional] [default to false]

### Return type

[**GetPlanResponse**](GetPlanResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getPlans"></a>
# **getPlans**
> PageWithCursorGetPlanResponseForPage getPlans(includeTrashed, includeArchived, cursor, maxResults)

Get plans paginated

Returns a [paginated](#pagination) list of plans.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.PlansApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

PlansApi apiInstance = new PlansApi();
Boolean includeTrashed = false; // Boolean | Whether to include trashed plans in the results.
Boolean includeArchived = false; // Boolean | Whether to include archived plans in the results.
String cursor = ""; // String | The cursor to start from. If not provided, the first page will be returned.
Integer maxResults = 50; // Integer | The maximum number of plans to return per page. The maximum value is 50. The default value is 50.
try {
    PageWithCursorGetPlanResponseForPage result = apiInstance.getPlans(includeTrashed, includeArchived, cursor, maxResults);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PlansApi#getPlans");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **includeTrashed** | **Boolean**| Whether to include trashed plans in the results. | [optional] [default to false]
 **includeArchived** | **Boolean**| Whether to include archived plans in the results. | [optional] [default to false]
 **cursor** | **String**| The cursor to start from. If not provided, the first page will be returned. | [optional]
 **maxResults** | **Integer**| The maximum number of plans to return per page. The maximum value is 50. The default value is 50. | [optional] [default to 50]

### Return type

[**PageWithCursorGetPlanResponseForPage**](PageWithCursorGetPlanResponseForPage.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="trashPlan"></a>
# **trashPlan**
> Object trashPlan(planId)

Trash plan

Moves a plan to trash.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.PlansApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

PlansApi apiInstance = new PlansApi();
Long planId = 789L; // Long | The ID of the plan.
try {
    Object result = apiInstance.trashPlan(planId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PlansApi#trashPlan");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **planId** | **Long**| The ID of the plan. |

### Return type

**Object**

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updatePlan"></a>
# **updatePlan**
> Object updatePlan(body, planId, useGroupId)

Update plan

Updates any of the following details of a plan using [JSON Patch](https://datatracker.ietf.org/doc/html/rfc6902).   *  name  *  leadAccountId  *  scheduling           *  estimation with StoryPoints, Days or Hours as possible values      *  startDate                   *  type with DueDate, TargetStartDate, TargetEndDate or DateCustomField as possible values          *  dateCustomFieldId      *  endDate                   *  type with DueDate, TargetStartDate, TargetEndDate or DateCustomField as possible values          *  dateCustomFieldId      *  inferredDates with None, SprintDates or ReleaseDates as possible values      *  dependencies with Sequential or Concurrent as possible values  *  issueSources           *  type with Board, Project or Filter as possible values      *  value  *  exclusionRules           *  numberOfDaysToShowCompletedIssues      *  issueIds      *  workStatusIds      *  workStatusCategoryIds      *  issueTypeIds      *  releaseIds  *  crossProjectReleases           *  name      *  releaseIds  *  customFields           *  customFieldId      *  filter  *  permissions           *  type with View or Edit as possible values      *  holder                   *  type with Group or AccountId as possible values          *  value  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).  *Note that \&quot;add\&quot; operations do not respect array indexes in target locations. Call the \&quot;Get plan\&quot; endpoint to find out the order of array elements.*

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.PlansApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

PlansApi apiInstance = new PlansApi();
Object body = "[{\"op\": \"replace\", \"path\": \"/scheduling/estimation\", \"value\": \"Days\"}]\n"; // Object | 
Long planId = 789L; // Long | The ID of the plan.
Boolean useGroupId = false; // Boolean | Whether to accept group IDs instead of group names. Group names are deprecated.
try {
    Object result = apiInstance.updatePlan(body, planId, useGroupId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PlansApi#updatePlan");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Object**](Object.md)|  |
 **planId** | **Long**| The ID of the plan. |
 **useGroupId** | **Boolean**| Whether to accept group IDs instead of group names. Group names are deprecated. | [optional] [default to false]

### Return type

**Object**

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json-patch+json
 - **Accept**: application/json

