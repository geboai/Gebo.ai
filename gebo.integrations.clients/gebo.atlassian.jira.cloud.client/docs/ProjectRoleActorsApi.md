# ProjectRoleActorsApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addActorUsers**](ProjectRoleActorsApi.md#addActorUsers) | **POST** /rest/api/3/project/{projectIdOrKey}/role/{id} | Add actors to project role
[**addProjectRoleActorsToRole**](ProjectRoleActorsApi.md#addProjectRoleActorsToRole) | **POST** /rest/api/3/role/{id}/actors | Add default actors to project role
[**deleteActor**](ProjectRoleActorsApi.md#deleteActor) | **DELETE** /rest/api/3/project/{projectIdOrKey}/role/{id} | Delete actors from project role
[**deleteProjectRoleActorsFromRole**](ProjectRoleActorsApi.md#deleteProjectRoleActorsFromRole) | **DELETE** /rest/api/3/role/{id}/actors | Delete default actors from project role
[**getProjectRoleActorsForRole**](ProjectRoleActorsApi.md#getProjectRoleActorsForRole) | **GET** /rest/api/3/role/{id}/actors | Get default actors for project role
[**setActors**](ProjectRoleActorsApi.md#setActors) | **PUT** /rest/api/3/project/{projectIdOrKey}/role/{id} | Set actors for project role

<a name="addActorUsers"></a>
# **addActorUsers**
> ProjectRole addActorUsers(body, projectIdOrKey, id)

Add actors to project role

Adds actors to a project role for the project.  To replace all actors for the project, use [Set actors for project role](#api-rest-api-3-project-projectIdOrKey-role-id-put).  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** *Administer Projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project or *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.ProjectRoleActorsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ProjectRoleActorsApi apiInstance = new ProjectRoleActorsApi();
ActorsMap body = new ActorsMap(); // ActorsMap | The groups or users to associate with the project role for this project. Provide the user account ID, group name, or group ID. As a group's name can change, use of group ID is recommended.
String projectIdOrKey = "projectIdOrKey_example"; // String | The project ID or project key (case sensitive).
Long id = 789L; // Long | The ID of the project role. Use [Get all project roles](#api-rest-api-3-role-get) to get a list of project role IDs.
try {
    ProjectRole result = apiInstance.addActorUsers(body, projectIdOrKey, id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectRoleActorsApi#addActorUsers");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ActorsMap**](ActorsMap.md)| The groups or users to associate with the project role for this project. Provide the user account ID, group name, or group ID. As a group&#x27;s name can change, use of group ID is recommended. |
 **projectIdOrKey** | **String**| The project ID or project key (case sensitive). |
 **id** | **Long**| The ID of the project role. Use [Get all project roles](#api-rest-api-3-role-get) to get a list of project role IDs. |

### Return type

[**ProjectRole**](ProjectRole.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="addProjectRoleActorsToRole"></a>
# **addProjectRoleActorsToRole**
> ProjectRole addProjectRoleActorsToRole(body, id)

Add default actors to project role

Adds [default actors](#api-rest-api-3-resolution-get) to a role. You may add groups or users, but you cannot add groups and users in the same request.  Changing a project role&#x27;s default actors does not affect project role members for projects already created.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.ProjectRoleActorsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ProjectRoleActorsApi apiInstance = new ProjectRoleActorsApi();
ActorInputBean body = new ActorInputBean(); // ActorInputBean | 
Long id = 789L; // Long | The ID of the project role. Use [Get all project roles](#api-rest-api-3-role-get) to get a list of project role IDs.
try {
    ProjectRole result = apiInstance.addProjectRoleActorsToRole(body, id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectRoleActorsApi#addProjectRoleActorsToRole");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ActorInputBean**](ActorInputBean.md)|  |
 **id** | **Long**| The ID of the project role. Use [Get all project roles](#api-rest-api-3-role-get) to get a list of project role IDs. |

### Return type

[**ProjectRole**](ProjectRole.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deleteActor"></a>
# **deleteActor**
> deleteActor(projectIdOrKey, id, user, group, groupId)

Delete actors from project role

Deletes actors from a project role for the project.  To remove default actors from the project role, use [Delete default actors from project role](#api-rest-api-3-role-id-actors-delete).  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** *Administer Projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project or *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.ProjectRoleActorsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ProjectRoleActorsApi apiInstance = new ProjectRoleActorsApi();
String projectIdOrKey = "projectIdOrKey_example"; // String | The project ID or project key (case sensitive).
Long id = 789L; // Long | The ID of the project role. Use [Get all project roles](#api-rest-api-3-role-get) to get a list of project role IDs.
String user = "user_example"; // String | The user account ID of the user to remove from the project role.
String group = "group_example"; // String | The name of the group to remove from the project role. This parameter cannot be used with the `groupId` parameter. As a group's name can change, use of `groupId` is recommended.
String groupId = "groupId_example"; // String | The ID of the group to remove from the project role. This parameter cannot be used with the `group` parameter.
try {
    apiInstance.deleteActor(projectIdOrKey, id, user, group, groupId);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectRoleActorsApi#deleteActor");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectIdOrKey** | **String**| The project ID or project key (case sensitive). |
 **id** | **Long**| The ID of the project role. Use [Get all project roles](#api-rest-api-3-role-get) to get a list of project role IDs. |
 **user** | **String**| The user account ID of the user to remove from the project role. | [optional]
 **group** | **String**| The name of the group to remove from the project role. This parameter cannot be used with the &#x60;groupId&#x60; parameter. As a group&#x27;s name can change, use of &#x60;groupId&#x60; is recommended. | [optional]
 **groupId** | **String**| The ID of the group to remove from the project role. This parameter cannot be used with the &#x60;group&#x60; parameter. | [optional]

### Return type

null (empty response body)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="deleteProjectRoleActorsFromRole"></a>
# **deleteProjectRoleActorsFromRole**
> ProjectRole deleteProjectRoleActorsFromRole(id, user, groupId, group)

Delete default actors from project role

Deletes the [default actors](#api-rest-api-3-resolution-get) from a project role. You may delete a group or user, but you cannot delete a group and a user in the same request.  Changing a project role&#x27;s default actors does not affect project role members for projects already created.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.ProjectRoleActorsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ProjectRoleActorsApi apiInstance = new ProjectRoleActorsApi();
Long id = 789L; // Long | The ID of the project role. Use [Get all project roles](#api-rest-api-3-role-get) to get a list of project role IDs.
String user = "user_example"; // String | The user account ID of the user to remove as a default actor.
String groupId = "groupId_example"; // String | The group ID of the group to be removed as a default actor. This parameter cannot be used with the `group` parameter.
String group = "group_example"; // String | The group name of the group to be removed as a default actor.This parameter cannot be used with the `groupId` parameter. As a group's name can change, use of `groupId` is recommended.
try {
    ProjectRole result = apiInstance.deleteProjectRoleActorsFromRole(id, user, groupId, group);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectRoleActorsApi#deleteProjectRoleActorsFromRole");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the project role. Use [Get all project roles](#api-rest-api-3-role-get) to get a list of project role IDs. |
 **user** | **String**| The user account ID of the user to remove as a default actor. | [optional]
 **groupId** | **String**| The group ID of the group to be removed as a default actor. This parameter cannot be used with the &#x60;group&#x60; parameter. | [optional]
 **group** | **String**| The group name of the group to be removed as a default actor.This parameter cannot be used with the &#x60;groupId&#x60; parameter. As a group&#x27;s name can change, use of &#x60;groupId&#x60; is recommended. | [optional]

### Return type

[**ProjectRole**](ProjectRole.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getProjectRoleActorsForRole"></a>
# **getProjectRoleActorsForRole**
> ProjectRole getProjectRoleActorsForRole(id)

Get default actors for project role

Returns the [default actors](#api-rest-api-3-resolution-get) for the project role.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.ProjectRoleActorsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ProjectRoleActorsApi apiInstance = new ProjectRoleActorsApi();
Long id = 789L; // Long | The ID of the project role. Use [Get all project roles](#api-rest-api-3-role-get) to get a list of project role IDs.
try {
    ProjectRole result = apiInstance.getProjectRoleActorsForRole(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectRoleActorsApi#getProjectRoleActorsForRole");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the project role. Use [Get all project roles](#api-rest-api-3-role-get) to get a list of project role IDs. |

### Return type

[**ProjectRole**](ProjectRole.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="setActors"></a>
# **setActors**
> ProjectRole setActors(body, projectIdOrKey, id)

Set actors for project role

Sets the actors for a project role for a project, replacing all existing actors.  To add actors to the project without overwriting the existing list, use [Add actors to project role](#api-rest-api-3-project-projectIdOrKey-role-id-post).  **[Permissions](#permissions) required:** *Administer Projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project or *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.ProjectRoleActorsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ProjectRoleActorsApi apiInstance = new ProjectRoleActorsApi();
ProjectRoleActorsUpdateBean body = new ProjectRoleActorsUpdateBean(); // ProjectRoleActorsUpdateBean | The groups or users to associate with the project role for this project. Provide the user account ID, group name, or group ID. As a group's name can change, use of group ID is recommended.
String projectIdOrKey = "projectIdOrKey_example"; // String | The project ID or project key (case sensitive).
Long id = 789L; // Long | The ID of the project role. Use [Get all project roles](#api-rest-api-3-role-get) to get a list of project role IDs.
try {
    ProjectRole result = apiInstance.setActors(body, projectIdOrKey, id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectRoleActorsApi#setActors");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ProjectRoleActorsUpdateBean**](ProjectRoleActorsUpdateBean.md)| The groups or users to associate with the project role for this project. Provide the user account ID, group name, or group ID. As a group&#x27;s name can change, use of group ID is recommended. |
 **projectIdOrKey** | **String**| The project ID or project key (case sensitive). |
 **id** | **Long**| The ID of the project role. Use [Get all project roles](#api-rest-api-3-role-get) to get a list of project role IDs. |

### Return type

[**ProjectRole**](ProjectRole.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

