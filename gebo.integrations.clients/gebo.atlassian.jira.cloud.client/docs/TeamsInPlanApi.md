# TeamsInPlanApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addAtlassianTeam**](TeamsInPlanApi.md#addAtlassianTeam) | **POST** /rest/api/3/plans/plan/{planId}/team/atlassian | Add Atlassian team to plan
[**createPlanOnlyTeam**](TeamsInPlanApi.md#createPlanOnlyTeam) | **POST** /rest/api/3/plans/plan/{planId}/team/planonly | Create plan-only team
[**deletePlanOnlyTeam**](TeamsInPlanApi.md#deletePlanOnlyTeam) | **DELETE** /rest/api/3/plans/plan/{planId}/team/planonly/{planOnlyTeamId} | Delete plan-only team
[**getAtlassianTeam**](TeamsInPlanApi.md#getAtlassianTeam) | **GET** /rest/api/3/plans/plan/{planId}/team/atlassian/{atlassianTeamId} | Get Atlassian team in plan
[**getPlanOnlyTeam**](TeamsInPlanApi.md#getPlanOnlyTeam) | **GET** /rest/api/3/plans/plan/{planId}/team/planonly/{planOnlyTeamId} | Get plan-only team
[**getTeams**](TeamsInPlanApi.md#getTeams) | **GET** /rest/api/3/plans/plan/{planId}/team | Get teams in plan paginated
[**removeAtlassianTeam**](TeamsInPlanApi.md#removeAtlassianTeam) | **DELETE** /rest/api/3/plans/plan/{planId}/team/atlassian/{atlassianTeamId} | Remove Atlassian team from plan
[**updateAtlassianTeam**](TeamsInPlanApi.md#updateAtlassianTeam) | **PUT** /rest/api/3/plans/plan/{planId}/team/atlassian/{atlassianTeamId} | Update Atlassian team in plan
[**updatePlanOnlyTeam**](TeamsInPlanApi.md#updatePlanOnlyTeam) | **PUT** /rest/api/3/plans/plan/{planId}/team/planonly/{planOnlyTeamId} | Update plan-only team

<a name="addAtlassianTeam"></a>
# **addAtlassianTeam**
> Object addAtlassianTeam(body, planId)

Add Atlassian team to plan

Adds an existing Atlassian team to a plan and configures their plannning settings.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.TeamsInPlanApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

TeamsInPlanApi apiInstance = new TeamsInPlanApi();
AddAtlassianTeamRequest body = new AddAtlassianTeamRequest(); // AddAtlassianTeamRequest | 
Long planId = 789L; // Long | The ID of the plan.
try {
    Object result = apiInstance.addAtlassianTeam(body, planId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TeamsInPlanApi#addAtlassianTeam");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**AddAtlassianTeamRequest**](AddAtlassianTeamRequest.md)|  |
 **planId** | **Long**| The ID of the plan. |

### Return type

**Object**

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createPlanOnlyTeam"></a>
# **createPlanOnlyTeam**
> Long createPlanOnlyTeam(body, planId)

Create plan-only team

Creates a plan-only team and configures their planning settings.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.TeamsInPlanApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

TeamsInPlanApi apiInstance = new TeamsInPlanApi();
CreatePlanOnlyTeamRequest body = new CreatePlanOnlyTeamRequest(); // CreatePlanOnlyTeamRequest | 
Long planId = 789L; // Long | The ID of the plan.
try {
    Long result = apiInstance.createPlanOnlyTeam(body, planId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TeamsInPlanApi#createPlanOnlyTeam");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**CreatePlanOnlyTeamRequest**](CreatePlanOnlyTeamRequest.md)|  |
 **planId** | **Long**| The ID of the plan. |

### Return type

**Long**

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deletePlanOnlyTeam"></a>
# **deletePlanOnlyTeam**
> Object deletePlanOnlyTeam(planId, planOnlyTeamId)

Delete plan-only team

Deletes a plan-only team and their planning settings.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.TeamsInPlanApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

TeamsInPlanApi apiInstance = new TeamsInPlanApi();
Long planId = 789L; // Long | The ID of the plan.
Long planOnlyTeamId = 789L; // Long | The ID of the plan-only team.
try {
    Object result = apiInstance.deletePlanOnlyTeam(planId, planOnlyTeamId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TeamsInPlanApi#deletePlanOnlyTeam");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **planId** | **Long**| The ID of the plan. |
 **planOnlyTeamId** | **Long**| The ID of the plan-only team. |

### Return type

**Object**

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAtlassianTeam"></a>
# **getAtlassianTeam**
> GetAtlassianTeamResponse getAtlassianTeam(planId, atlassianTeamId)

Get Atlassian team in plan

Returns planning settings for an Atlassian team in a plan.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.TeamsInPlanApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

TeamsInPlanApi apiInstance = new TeamsInPlanApi();
Long planId = 789L; // Long | The ID of the plan.
String atlassianTeamId = "atlassianTeamId_example"; // String | The ID of the Atlassian team.
try {
    GetAtlassianTeamResponse result = apiInstance.getAtlassianTeam(planId, atlassianTeamId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TeamsInPlanApi#getAtlassianTeam");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **planId** | **Long**| The ID of the plan. |
 **atlassianTeamId** | **String**| The ID of the Atlassian team. |

### Return type

[**GetAtlassianTeamResponse**](GetAtlassianTeamResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getPlanOnlyTeam"></a>
# **getPlanOnlyTeam**
> GetPlanOnlyTeamResponse getPlanOnlyTeam(planId, planOnlyTeamId)

Get plan-only team

Returns planning settings for a plan-only team.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.TeamsInPlanApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

TeamsInPlanApi apiInstance = new TeamsInPlanApi();
Long planId = 789L; // Long | The ID of the plan.
Long planOnlyTeamId = 789L; // Long | The ID of the plan-only team.
try {
    GetPlanOnlyTeamResponse result = apiInstance.getPlanOnlyTeam(planId, planOnlyTeamId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TeamsInPlanApi#getPlanOnlyTeam");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **planId** | **Long**| The ID of the plan. |
 **planOnlyTeamId** | **Long**| The ID of the plan-only team. |

### Return type

[**GetPlanOnlyTeamResponse**](GetPlanOnlyTeamResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getTeams"></a>
# **getTeams**
> PageWithCursorGetTeamResponseForPage getTeams(planId, cursor, maxResults)

Get teams in plan paginated

Returns a [paginated](#pagination) list of plan-only and Atlassian teams in a plan.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.TeamsInPlanApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

TeamsInPlanApi apiInstance = new TeamsInPlanApi();
Long planId = 789L; // Long | The ID of the plan.
String cursor = ""; // String | The cursor to start from. If not provided, the first page will be returned.
Integer maxResults = 50; // Integer | The maximum number of plan teams to return per page. The maximum value is 50. The default value is 50.
try {
    PageWithCursorGetTeamResponseForPage result = apiInstance.getTeams(planId, cursor, maxResults);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TeamsInPlanApi#getTeams");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **planId** | **Long**| The ID of the plan. |
 **cursor** | **String**| The cursor to start from. If not provided, the first page will be returned. | [optional]
 **maxResults** | **Integer**| The maximum number of plan teams to return per page. The maximum value is 50. The default value is 50. | [optional] [default to 50]

### Return type

[**PageWithCursorGetTeamResponseForPage**](PageWithCursorGetTeamResponseForPage.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="removeAtlassianTeam"></a>
# **removeAtlassianTeam**
> Object removeAtlassianTeam(planId, atlassianTeamId)

Remove Atlassian team from plan

Removes an Atlassian team from a plan and deletes their planning settings.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.TeamsInPlanApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

TeamsInPlanApi apiInstance = new TeamsInPlanApi();
Long planId = 789L; // Long | The ID of the plan.
String atlassianTeamId = "atlassianTeamId_example"; // String | The ID of the Atlassian team.
try {
    Object result = apiInstance.removeAtlassianTeam(planId, atlassianTeamId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TeamsInPlanApi#removeAtlassianTeam");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **planId** | **Long**| The ID of the plan. |
 **atlassianTeamId** | **String**| The ID of the Atlassian team. |

### Return type

**Object**

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateAtlassianTeam"></a>
# **updateAtlassianTeam**
> Object updateAtlassianTeam(body, planId, atlassianTeamId)

Update Atlassian team in plan

Updates any of the following planning settings of an Atlassian team in a plan using [JSON Patch](https://datatracker.ietf.org/doc/html/rfc6902).   *  planningStyle  *  issueSourceId  *  sprintLength  *  capacity  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).  *Note that \&quot;add\&quot; operations do not respect array indexes in target locations. Call the \&quot;Get Atlassian team in plan\&quot; endpoint to find out the order of array elements.*

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.TeamsInPlanApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

TeamsInPlanApi apiInstance = new TeamsInPlanApi();
Object body = "[{\"op\": \"replace\", \"path\": \"/planningStyle\", \"value\": \"Kanban\"}]\n"; // Object | 
Long planId = 789L; // Long | The ID of the plan.
String atlassianTeamId = "atlassianTeamId_example"; // String | The ID of the Atlassian team.
try {
    Object result = apiInstance.updateAtlassianTeam(body, planId, atlassianTeamId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TeamsInPlanApi#updateAtlassianTeam");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Object**](Object.md)|  |
 **planId** | **Long**| The ID of the plan. |
 **atlassianTeamId** | **String**| The ID of the Atlassian team. |

### Return type

**Object**

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json-patch+json
 - **Accept**: application/json

<a name="updatePlanOnlyTeam"></a>
# **updatePlanOnlyTeam**
> Object updatePlanOnlyTeam(body, planId, planOnlyTeamId)

Update plan-only team

Updates any of the following planning settings of a plan-only team using [JSON Patch](https://datatracker.ietf.org/doc/html/rfc6902).   *  name  *  planningStyle  *  issueSourceId  *  sprintLength  *  capacity  *  memberAccountIds  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).  *Note that \&quot;add\&quot; operations do not respect array indexes in target locations. Call the \&quot;Get plan-only team\&quot; endpoint to find out the order of array elements.*

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.TeamsInPlanApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

TeamsInPlanApi apiInstance = new TeamsInPlanApi();
Object body = "[{\"op\": \"replace\", \"path\": \"/planningStyle\", \"value\": \"Kanban\"}]\n"; // Object | 
Long planId = 789L; // Long | The ID of the plan.
Long planOnlyTeamId = 789L; // Long | The ID of the plan-only team.
try {
    Object result = apiInstance.updatePlanOnlyTeam(body, planId, planOnlyTeamId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TeamsInPlanApi#updatePlanOnlyTeam");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Object**](Object.md)|  |
 **planId** | **Long**| The ID of the plan. |
 **planOnlyTeamId** | **Long**| The ID of the plan-only team. |

### Return type

**Object**

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json-patch+json
 - **Accept**: application/json

