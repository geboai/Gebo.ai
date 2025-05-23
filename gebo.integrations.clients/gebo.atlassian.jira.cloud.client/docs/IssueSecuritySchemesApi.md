# IssueSecuritySchemesApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addSecurityLevel**](IssueSecuritySchemesApi.md#addSecurityLevel) | **PUT** /rest/api/3/issuesecurityschemes/{schemeId}/level | Add issue security levels
[**addSecurityLevelMembers**](IssueSecuritySchemesApi.md#addSecurityLevelMembers) | **PUT** /rest/api/3/issuesecurityschemes/{schemeId}/level/{levelId}/member | Add issue security level members
[**associateSchemesToProjects**](IssueSecuritySchemesApi.md#associateSchemesToProjects) | **PUT** /rest/api/3/issuesecurityschemes/project | Associate security scheme to project
[**createIssueSecurityScheme**](IssueSecuritySchemesApi.md#createIssueSecurityScheme) | **POST** /rest/api/3/issuesecurityschemes | Create issue security scheme
[**deleteSecurityScheme**](IssueSecuritySchemesApi.md#deleteSecurityScheme) | **DELETE** /rest/api/3/issuesecurityschemes/{schemeId} | Delete issue security scheme
[**getIssueSecurityScheme**](IssueSecuritySchemesApi.md#getIssueSecurityScheme) | **GET** /rest/api/3/issuesecurityschemes/{id} | Get issue security scheme
[**getIssueSecuritySchemes**](IssueSecuritySchemesApi.md#getIssueSecuritySchemes) | **GET** /rest/api/3/issuesecurityschemes | Get issue security schemes
[**getSecurityLevelMembers**](IssueSecuritySchemesApi.md#getSecurityLevelMembers) | **GET** /rest/api/3/issuesecurityschemes/level/member | Get issue security level members
[**getSecurityLevels**](IssueSecuritySchemesApi.md#getSecurityLevels) | **GET** /rest/api/3/issuesecurityschemes/level | Get issue security levels
[**removeLevel**](IssueSecuritySchemesApi.md#removeLevel) | **DELETE** /rest/api/3/issuesecurityschemes/{schemeId}/level/{levelId} | Remove issue security level
[**removeMemberFromSecurityLevel**](IssueSecuritySchemesApi.md#removeMemberFromSecurityLevel) | **DELETE** /rest/api/3/issuesecurityschemes/{schemeId}/level/{levelId}/member/{memberId} | Remove member from issue security level
[**searchProjectsUsingSecuritySchemes**](IssueSecuritySchemesApi.md#searchProjectsUsingSecuritySchemes) | **GET** /rest/api/3/issuesecurityschemes/project | Get projects using issue security schemes
[**searchSecuritySchemes**](IssueSecuritySchemesApi.md#searchSecuritySchemes) | **GET** /rest/api/3/issuesecurityschemes/search | Search issue security schemes
[**setDefaultLevels**](IssueSecuritySchemesApi.md#setDefaultLevels) | **PUT** /rest/api/3/issuesecurityschemes/level/default | Set default issue security levels
[**updateIssueSecurityScheme**](IssueSecuritySchemesApi.md#updateIssueSecurityScheme) | **PUT** /rest/api/3/issuesecurityschemes/{id} | Update issue security scheme
[**updateSecurityLevel**](IssueSecuritySchemesApi.md#updateSecurityLevel) | **PUT** /rest/api/3/issuesecurityschemes/{schemeId}/level/{levelId} | Update issue security level

<a name="addSecurityLevel"></a>
# **addSecurityLevel**
> Object addSecurityLevel(body, schemeId)

Add issue security levels

Adds levels and levels&#x27; members to the issue security scheme. You can add up to 100 levels per request.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.IssueSecuritySchemesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

IssueSecuritySchemesApi apiInstance = new IssueSecuritySchemesApi();
AddSecuritySchemeLevelsRequestBean body = new AddSecuritySchemeLevelsRequestBean(); // AddSecuritySchemeLevelsRequestBean | 
String schemeId = "schemeId_example"; // String | The ID of the issue security scheme.
try {
    Object result = apiInstance.addSecurityLevel(body, schemeId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IssueSecuritySchemesApi#addSecurityLevel");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**AddSecuritySchemeLevelsRequestBean**](AddSecuritySchemeLevelsRequestBean.md)|  |
 **schemeId** | **String**| The ID of the issue security scheme. |

### Return type

**Object**

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="addSecurityLevelMembers"></a>
# **addSecurityLevelMembers**
> Object addSecurityLevelMembers(body, schemeId, levelId)

Add issue security level members

Adds members to the issue security level. You can add up to 100 members per request.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.IssueSecuritySchemesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

IssueSecuritySchemesApi apiInstance = new IssueSecuritySchemesApi();
SecuritySchemeMembersRequest body = new SecuritySchemeMembersRequest(); // SecuritySchemeMembersRequest | 
String schemeId = "schemeId_example"; // String | The ID of the issue security scheme.
String levelId = "levelId_example"; // String | The ID of the issue security level.
try {
    Object result = apiInstance.addSecurityLevelMembers(body, schemeId, levelId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IssueSecuritySchemesApi#addSecurityLevelMembers");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SecuritySchemeMembersRequest**](SecuritySchemeMembersRequest.md)|  |
 **schemeId** | **String**| The ID of the issue security scheme. |
 **levelId** | **String**| The ID of the issue security level. |

### Return type

**Object**

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="associateSchemesToProjects"></a>
# **associateSchemesToProjects**
> associateSchemesToProjects(body)

Associate security scheme to project

Associates an issue security scheme with a project and remaps security levels of issues to the new levels, if provided.  This operation is [asynchronous](#async). Follow the &#x60;location&#x60; link in the response to determine the status of the task and use [Get task](#api-rest-api-3-task-taskId-get) to obtain subsequent updates.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.IssueSecuritySchemesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

IssueSecuritySchemesApi apiInstance = new IssueSecuritySchemesApi();
AssociateSecuritySchemeWithProjectDetails body = new AssociateSecuritySchemeWithProjectDetails(); // AssociateSecuritySchemeWithProjectDetails | 
try {
    apiInstance.associateSchemesToProjects(body);
} catch (ApiException e) {
    System.err.println("Exception when calling IssueSecuritySchemesApi#associateSchemesToProjects");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**AssociateSecuritySchemeWithProjectDetails**](AssociateSecuritySchemeWithProjectDetails.md)|  |

### Return type

null (empty response body)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createIssueSecurityScheme"></a>
# **createIssueSecurityScheme**
> SecuritySchemeId createIssueSecurityScheme(body)

Create issue security scheme

Creates a security scheme with security scheme levels and levels&#x27; members. You can create up to 100 security scheme levels and security scheme levels&#x27; members per request.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.IssueSecuritySchemesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

IssueSecuritySchemesApi apiInstance = new IssueSecuritySchemesApi();
Map<String, Object> body = new Map(); // Map<String, Object> | 
try {
    SecuritySchemeId result = apiInstance.createIssueSecurityScheme(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IssueSecuritySchemesApi#createIssueSecurityScheme");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Map&lt;String, Object&gt;**](Map.md)|  |

### Return type

[**SecuritySchemeId**](SecuritySchemeId.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deleteSecurityScheme"></a>
# **deleteSecurityScheme**
> Object deleteSecurityScheme(schemeId)

Delete issue security scheme

Deletes an issue security scheme.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.IssueSecuritySchemesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

IssueSecuritySchemesApi apiInstance = new IssueSecuritySchemesApi();
String schemeId = "schemeId_example"; // String | The ID of the issue security scheme.
try {
    Object result = apiInstance.deleteSecurityScheme(schemeId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IssueSecuritySchemesApi#deleteSecurityScheme");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **schemeId** | **String**| The ID of the issue security scheme. |

### Return type

**Object**

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getIssueSecurityScheme"></a>
# **getIssueSecurityScheme**
> SecurityScheme getIssueSecurityScheme(id)

Get issue security scheme

Returns an issue security scheme along with its security levels.  **[Permissions](#permissions) required:**   *  *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).  *  *Administer Projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for a project that uses the requested issue security scheme.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.IssueSecuritySchemesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

IssueSecuritySchemesApi apiInstance = new IssueSecuritySchemesApi();
Long id = 789L; // Long | The ID of the issue security scheme. Use the [Get issue security schemes](#api-rest-api-3-issuesecurityschemes-get) operation to get a list of issue security scheme IDs.
try {
    SecurityScheme result = apiInstance.getIssueSecurityScheme(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IssueSecuritySchemesApi#getIssueSecurityScheme");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the issue security scheme. Use the [Get issue security schemes](#api-rest-api-3-issuesecurityschemes-get) operation to get a list of issue security scheme IDs. |

### Return type

[**SecurityScheme**](SecurityScheme.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getIssueSecuritySchemes"></a>
# **getIssueSecuritySchemes**
> SecuritySchemes getIssueSecuritySchemes()

Get issue security schemes

Returns all [issue security schemes](https://confluence.atlassian.com/x/J4lKLg).  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.IssueSecuritySchemesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

IssueSecuritySchemesApi apiInstance = new IssueSecuritySchemesApi();
try {
    SecuritySchemes result = apiInstance.getIssueSecuritySchemes();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IssueSecuritySchemesApi#getIssueSecuritySchemes");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**SecuritySchemes**](SecuritySchemes.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getSecurityLevelMembers"></a>
# **getSecurityLevelMembers**
> PageBeanSecurityLevelMember getSecurityLevelMembers(startAt, maxResults, id, schemeId, levelId, expand)

Get issue security level members

Returns a [paginated](#pagination) list of issue security level members.  Only issue security level members in the context of classic projects are returned.  Filtering using parameters is inclusive: if you specify both security scheme IDs and level IDs, the result will include all issue security level members from the specified schemes and levels.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.IssueSecuritySchemesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

IssueSecuritySchemesApi apiInstance = new IssueSecuritySchemesApi();
String startAt = "0"; // String | The index of the first item to return in a page of results (page offset).
String maxResults = "50"; // String | The maximum number of items to return per page.
List<String> id = Arrays.asList("id_example"); // List<String> | The list of issue security level member IDs. To include multiple issue security level members separate IDs with an ampersand: `id=10000&id=10001`.
List<String> schemeId = Arrays.asList("schemeId_example"); // List<String> | The list of issue security scheme IDs. To include multiple issue security schemes separate IDs with an ampersand: `schemeId=10000&schemeId=10001`.
List<String> levelId = Arrays.asList("levelId_example"); // List<String> | The list of issue security level IDs. To include multiple issue security levels separate IDs with an ampersand: `levelId=10000&levelId=10001`.
String expand = "expand_example"; // String | Use expand to include additional information in the response. This parameter accepts a comma-separated list. Expand options include:   *  `all` Returns all expandable information  *  `field` Returns information about the custom field granted the permission  *  `group` Returns information about the group that is granted the permission  *  `projectRole` Returns information about the project role granted the permission  *  `user` Returns information about the user who is granted the permission
try {
    PageBeanSecurityLevelMember result = apiInstance.getSecurityLevelMembers(startAt, maxResults, id, schemeId, levelId, expand);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IssueSecuritySchemesApi#getSecurityLevelMembers");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **startAt** | **String**| The index of the first item to return in a page of results (page offset). | [optional] [default to 0]
 **maxResults** | **String**| The maximum number of items to return per page. | [optional] [default to 50]
 **id** | [**List&lt;String&gt;**](String.md)| The list of issue security level member IDs. To include multiple issue security level members separate IDs with an ampersand: &#x60;id&#x3D;10000&amp;id&#x3D;10001&#x60;. | [optional]
 **schemeId** | [**List&lt;String&gt;**](String.md)| The list of issue security scheme IDs. To include multiple issue security schemes separate IDs with an ampersand: &#x60;schemeId&#x3D;10000&amp;schemeId&#x3D;10001&#x60;. | [optional]
 **levelId** | [**List&lt;String&gt;**](String.md)| The list of issue security level IDs. To include multiple issue security levels separate IDs with an ampersand: &#x60;levelId&#x3D;10000&amp;levelId&#x3D;10001&#x60;. | [optional]
 **expand** | **String**| Use expand to include additional information in the response. This parameter accepts a comma-separated list. Expand options include:   *  &#x60;all&#x60; Returns all expandable information  *  &#x60;field&#x60; Returns information about the custom field granted the permission  *  &#x60;group&#x60; Returns information about the group that is granted the permission  *  &#x60;projectRole&#x60; Returns information about the project role granted the permission  *  &#x60;user&#x60; Returns information about the user who is granted the permission | [optional]

### Return type

[**PageBeanSecurityLevelMember**](PageBeanSecurityLevelMember.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getSecurityLevels"></a>
# **getSecurityLevels**
> PageBeanSecurityLevel getSecurityLevels(startAt, maxResults, id, schemeId, onlyDefault)

Get issue security levels

Returns a [paginated](#pagination) list of issue security levels.  Only issue security levels in the context of classic projects are returned.  Filtering using IDs is inclusive: if you specify both security scheme IDs and level IDs, the result will include both specified issue security levels and all issue security levels from the specified schemes.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.IssueSecuritySchemesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

IssueSecuritySchemesApi apiInstance = new IssueSecuritySchemesApi();
String startAt = "0"; // String | The index of the first item to return in a page of results (page offset).
String maxResults = "50"; // String | The maximum number of items to return per page.
List<String> id = Arrays.asList("id_example"); // List<String> | The list of issue security scheme level IDs. To include multiple issue security levels, separate IDs with an ampersand: `id=10000&id=10001`.
List<String> schemeId = Arrays.asList("schemeId_example"); // List<String> | The list of issue security scheme IDs. To include multiple issue security schemes, separate IDs with an ampersand: `schemeId=10000&schemeId=10001`.
Boolean onlyDefault = false; // Boolean | When set to true, returns multiple default levels for each security scheme containing a default. If you provide scheme and level IDs not associated with the default, returns an empty page. The default value is false.
try {
    PageBeanSecurityLevel result = apiInstance.getSecurityLevels(startAt, maxResults, id, schemeId, onlyDefault);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IssueSecuritySchemesApi#getSecurityLevels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **startAt** | **String**| The index of the first item to return in a page of results (page offset). | [optional] [default to 0]
 **maxResults** | **String**| The maximum number of items to return per page. | [optional] [default to 50]
 **id** | [**List&lt;String&gt;**](String.md)| The list of issue security scheme level IDs. To include multiple issue security levels, separate IDs with an ampersand: &#x60;id&#x3D;10000&amp;id&#x3D;10001&#x60;. | [optional]
 **schemeId** | [**List&lt;String&gt;**](String.md)| The list of issue security scheme IDs. To include multiple issue security schemes, separate IDs with an ampersand: &#x60;schemeId&#x3D;10000&amp;schemeId&#x3D;10001&#x60;. | [optional]
 **onlyDefault** | **Boolean**| When set to true, returns multiple default levels for each security scheme containing a default. If you provide scheme and level IDs not associated with the default, returns an empty page. The default value is false. | [optional] [default to false]

### Return type

[**PageBeanSecurityLevel**](PageBeanSecurityLevel.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="removeLevel"></a>
# **removeLevel**
> removeLevel(schemeId, levelId, replaceWith)

Remove issue security level

Deletes an issue security level.  This operation is [asynchronous](#async). Follow the &#x60;location&#x60; link in the response to determine the status of the task and use [Get task](#api-rest-api-3-task-taskId-get) to obtain subsequent updates.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.IssueSecuritySchemesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

IssueSecuritySchemesApi apiInstance = new IssueSecuritySchemesApi();
String schemeId = "schemeId_example"; // String | The ID of the issue security scheme.
String levelId = "levelId_example"; // String | The ID of the issue security level to remove.
String replaceWith = "replaceWith_example"; // String | The ID of the issue security level that will replace the currently selected level.
try {
    apiInstance.removeLevel(schemeId, levelId, replaceWith);
} catch (ApiException e) {
    System.err.println("Exception when calling IssueSecuritySchemesApi#removeLevel");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **schemeId** | **String**| The ID of the issue security scheme. |
 **levelId** | **String**| The ID of the issue security level to remove. |
 **replaceWith** | **String**| The ID of the issue security level that will replace the currently selected level. | [optional]

### Return type

null (empty response body)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="removeMemberFromSecurityLevel"></a>
# **removeMemberFromSecurityLevel**
> Object removeMemberFromSecurityLevel(schemeId, levelId, memberId)

Remove member from issue security level

Removes an issue security level member from an issue security scheme.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.IssueSecuritySchemesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

IssueSecuritySchemesApi apiInstance = new IssueSecuritySchemesApi();
String schemeId = "schemeId_example"; // String | The ID of the issue security scheme.
String levelId = "levelId_example"; // String | The ID of the issue security level.
String memberId = "memberId_example"; // String | The ID of the issue security level member to be removed.
try {
    Object result = apiInstance.removeMemberFromSecurityLevel(schemeId, levelId, memberId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IssueSecuritySchemesApi#removeMemberFromSecurityLevel");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **schemeId** | **String**| The ID of the issue security scheme. |
 **levelId** | **String**| The ID of the issue security level. |
 **memberId** | **String**| The ID of the issue security level member to be removed. |

### Return type

**Object**

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="searchProjectsUsingSecuritySchemes"></a>
# **searchProjectsUsingSecuritySchemes**
> PageBeanIssueSecuritySchemeToProjectMapping searchProjectsUsingSecuritySchemes(startAt, maxResults, issueSecuritySchemeId, projectId)

Get projects using issue security schemes

Returns a [paginated](#pagination) mapping of projects that are using security schemes. You can provide either one or multiple security scheme IDs or project IDs to filter by. If you don&#x27;t provide any, this will return a list of all mappings. Only issue security schemes in the context of classic projects are supported. **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.IssueSecuritySchemesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

IssueSecuritySchemesApi apiInstance = new IssueSecuritySchemesApi();
String startAt = "0"; // String | The index of the first item to return in a page of results (page offset).
String maxResults = "50"; // String | The maximum number of items to return per page.
List<String> issueSecuritySchemeId = Arrays.asList("issueSecuritySchemeId_example"); // List<String> | The list of security scheme IDs to be filtered out.
List<String> projectId = Arrays.asList("projectId_example"); // List<String> | The list of project IDs to be filtered out.
try {
    PageBeanIssueSecuritySchemeToProjectMapping result = apiInstance.searchProjectsUsingSecuritySchemes(startAt, maxResults, issueSecuritySchemeId, projectId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IssueSecuritySchemesApi#searchProjectsUsingSecuritySchemes");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **startAt** | **String**| The index of the first item to return in a page of results (page offset). | [optional] [default to 0]
 **maxResults** | **String**| The maximum number of items to return per page. | [optional] [default to 50]
 **issueSecuritySchemeId** | [**List&lt;String&gt;**](String.md)| The list of security scheme IDs to be filtered out. | [optional]
 **projectId** | [**List&lt;String&gt;**](String.md)| The list of project IDs to be filtered out. | [optional]

### Return type

[**PageBeanIssueSecuritySchemeToProjectMapping**](PageBeanIssueSecuritySchemeToProjectMapping.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="searchSecuritySchemes"></a>
# **searchSecuritySchemes**
> PageBeanSecuritySchemeWithProjects searchSecuritySchemes(startAt, maxResults, id, projectId)

Search issue security schemes

Returns a [paginated](#pagination) list of issue security schemes.   If you specify the project ID parameter, the result will contain issue security schemes and related project IDs you filter by. Use \\{@link IssueSecuritySchemeResource\\#searchProjectsUsingSecuritySchemes(String, String, Set, Set)\\} to obtain all projects related to scheme.  Only issue security schemes in the context of classic projects are returned.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.IssueSecuritySchemesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

IssueSecuritySchemesApi apiInstance = new IssueSecuritySchemesApi();
String startAt = "0"; // String | The index of the first item to return in a page of results (page offset).
String maxResults = "50"; // String | The maximum number of items to return per page.
List<String> id = Arrays.asList("id_example"); // List<String> | The list of issue security scheme IDs. To include multiple issue security scheme IDs, separate IDs with an ampersand: `id=10000&id=10001`.
List<String> projectId = Arrays.asList("projectId_example"); // List<String> | The list of project IDs. To include multiple project IDs, separate IDs with an ampersand: `projectId=10000&projectId=10001`.
try {
    PageBeanSecuritySchemeWithProjects result = apiInstance.searchSecuritySchemes(startAt, maxResults, id, projectId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IssueSecuritySchemesApi#searchSecuritySchemes");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **startAt** | **String**| The index of the first item to return in a page of results (page offset). | [optional] [default to 0]
 **maxResults** | **String**| The maximum number of items to return per page. | [optional] [default to 50]
 **id** | [**List&lt;String&gt;**](String.md)| The list of issue security scheme IDs. To include multiple issue security scheme IDs, separate IDs with an ampersand: &#x60;id&#x3D;10000&amp;id&#x3D;10001&#x60;. | [optional]
 **projectId** | [**List&lt;String&gt;**](String.md)| The list of project IDs. To include multiple project IDs, separate IDs with an ampersand: &#x60;projectId&#x3D;10000&amp;projectId&#x3D;10001&#x60;. | [optional]

### Return type

[**PageBeanSecuritySchemeWithProjects**](PageBeanSecuritySchemeWithProjects.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="setDefaultLevels"></a>
# **setDefaultLevels**
> Object setDefaultLevels(body)

Set default issue security levels

Sets default issue security levels for schemes.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.IssueSecuritySchemesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

IssueSecuritySchemesApi apiInstance = new IssueSecuritySchemesApi();
Map<String, Object> body = new Map(); // Map<String, Object> | 
try {
    Object result = apiInstance.setDefaultLevels(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IssueSecuritySchemesApi#setDefaultLevels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Map&lt;String, Object&gt;**](Map.md)|  |

### Return type

**Object**

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateIssueSecurityScheme"></a>
# **updateIssueSecurityScheme**
> Object updateIssueSecurityScheme(body, id)

Update issue security scheme

Updates the issue security scheme.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.IssueSecuritySchemesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

IssueSecuritySchemesApi apiInstance = new IssueSecuritySchemesApi();
UpdateIssueSecuritySchemeRequestBean body = new UpdateIssueSecuritySchemeRequestBean(); // UpdateIssueSecuritySchemeRequestBean | 
String id = "id_example"; // String | The ID of the issue security scheme.
try {
    Object result = apiInstance.updateIssueSecurityScheme(body, id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IssueSecuritySchemesApi#updateIssueSecurityScheme");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UpdateIssueSecuritySchemeRequestBean**](UpdateIssueSecuritySchemeRequestBean.md)|  |
 **id** | **String**| The ID of the issue security scheme. |

### Return type

**Object**

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateSecurityLevel"></a>
# **updateSecurityLevel**
> Object updateSecurityLevel(body, schemeId, levelId)

Update issue security level

Updates the issue security level.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.IssueSecuritySchemesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

IssueSecuritySchemesApi apiInstance = new IssueSecuritySchemesApi();
Map<String, Object> body = new Map(); // Map<String, Object> | 
String schemeId = "schemeId_example"; // String | The ID of the issue security scheme level belongs to.
String levelId = "levelId_example"; // String | The ID of the issue security level to update.
try {
    Object result = apiInstance.updateSecurityLevel(body, schemeId, levelId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IssueSecuritySchemesApi#updateSecurityLevel");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Map&lt;String, Object&gt;**](Map.md)|  |
 **schemeId** | **String**| The ID of the issue security scheme level belongs to. |
 **levelId** | **String**| The ID of the issue security level to update. |

### Return type

**Object**

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

