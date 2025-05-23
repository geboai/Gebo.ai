# GroupApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addUserToGroupByGroupId**](GroupApi.md#addUserToGroupByGroupId) | **POST** /wiki/rest/api/group/userByGroupId | Add member to group by groupId
[**createGroup**](GroupApi.md#createGroup) | **POST** /wiki/rest/api/group | Create new user group
[**getGroupByGroupId**](GroupApi.md#getGroupByGroupId) | **GET** /wiki/rest/api/group/by-id | Get group
[**getGroupMembersByGroupId**](GroupApi.md#getGroupMembersByGroupId) | **GET** /wiki/rest/api/group/{groupId}/membersByGroupId | Get group members
[**getGroups**](GroupApi.md#getGroups) | **GET** /wiki/rest/api/group | Get groups
[**removeGroupById**](GroupApi.md#removeGroupById) | **DELETE** /wiki/rest/api/group/by-id | Delete user group
[**removeMemberFromGroupByGroupId**](GroupApi.md#removeMemberFromGroupByGroupId) | **DELETE** /wiki/rest/api/group/userByGroupId | Remove member from group using group id
[**searchGroups**](GroupApi.md#searchGroups) | **GET** /wiki/rest/api/group/picker | Search groups by partial query

<a name="addUserToGroupByGroupId"></a>
# **addUserToGroupByGroupId**
> addUserToGroupByGroupId(body, groupId)

Add member to group by groupId

Adds a user as a member in a group represented by its groupId  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: User must be a site admin.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.GroupApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

GroupApi apiInstance = new GroupApi();
AccountId body = new AccountId(); // AccountId | AccountId of the user who needs to be added as member.
String groupId = "groupId_example"; // String | GroupId of the group whose membership is updated
try {
    apiInstance.addUserToGroupByGroupId(body, groupId);
} catch (ApiException e) {
    System.err.println("Exception when calling GroupApi#addUserToGroupByGroupId");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**AccountId**](AccountId.md)| AccountId of the user who needs to be added as member. |
 **groupId** | **String**| GroupId of the group whose membership is updated |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="createGroup"></a>
# **createGroup**
> Group createGroup(body)

Create new user group

Creates a new user group.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: User must be a site admin.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.GroupApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

GroupApi apiInstance = new GroupApi();
GroupName body = new GroupName(); // GroupName | Name of the group that is to be created.
try {
    Group result = apiInstance.createGroup(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GroupApi#createGroup");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GroupName**](GroupName.md)| Name of the group that is to be created. |

### Return type

[**Group**](Group.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getGroupByGroupId"></a>
# **getGroupByGroupId**
> Group getGroupByGroupId(id)

Get group

Returns a user group for a given group id.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to access the Confluence site (&#x27;Can use&#x27; global permission).

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.GroupApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

GroupApi apiInstance = new GroupApi();
String id = "id_example"; // String | The id of the group.
try {
    Group result = apiInstance.getGroupByGroupId(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GroupApi#getGroupByGroupId");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The id of the group. |

### Return type

[**Group**](Group.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getGroupMembersByGroupId"></a>
# **getGroupMembersByGroupId**
> UserArray getGroupMembersByGroupId(groupId, start, limit, shouldReturnTotalSize, expand)

Get group members

Returns the users that are members of a group.  Use updated Get group API  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to access the Confluence site (&#x27;Can use&#x27; global permission).

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.GroupApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

GroupApi apiInstance = new GroupApi();
String groupId = "groupId_example"; // String | The id of the group to be queried for its members.
Integer start = 0; // Integer | The starting index of the returned users.
Integer limit = 200; // Integer | The maximum number of users to return per page. Note, this may be restricted by fixed system limits.
Boolean shouldReturnTotalSize = false; // Boolean | Whether to include total size parameter in the results. Note, fetching total size property is an expensive operation; use it if your use case needs this value.
List<String> expand = Arrays.asList("expand_example"); // List<String> | A multi-value parameter indicating which properties of the user to expand.    - `operations` returns the operations that the user is allowed to do.   - `personalSpace` returns the user's personal space, if it exists.   - `isExternalCollaborator`(@deprecated) see `isGuest` in response to find out whether the user is a guest.
try {
    UserArray result = apiInstance.getGroupMembersByGroupId(groupId, start, limit, shouldReturnTotalSize, expand);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GroupApi#getGroupMembersByGroupId");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **groupId** | **String**| The id of the group to be queried for its members. |
 **start** | **Integer**| The starting index of the returned users. | [optional] [default to 0] [enum: 0]
 **limit** | **Integer**| The maximum number of users to return per page. Note, this may be restricted by fixed system limits. | [optional] [default to 200] [enum: 0]
 **shouldReturnTotalSize** | **Boolean**| Whether to include total size parameter in the results. Note, fetching total size property is an expensive operation; use it if your use case needs this value. | [optional] [default to false]
 **expand** | [**List&lt;String&gt;**](String.md)| A multi-value parameter indicating which properties of the user to expand.    - &#x60;operations&#x60; returns the operations that the user is allowed to do.   - &#x60;personalSpace&#x60; returns the user&#x27;s personal space, if it exists.   - &#x60;isExternalCollaborator&#x60;(@deprecated) see &#x60;isGuest&#x60; in response to find out whether the user is a guest. | [optional] [enum: operations, personalSpace, isExternalCollaborator]

### Return type

[**UserArray**](UserArray.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getGroups"></a>
# **getGroups**
> GroupArrayWithLinks getGroups(start, limit, accessType)

Get groups

Returns all user groups. The returned groups are ordered alphabetically in ascending order by group name.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to access the Confluence site (&#x27;Can use&#x27; global permission).

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.GroupApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

GroupApi apiInstance = new GroupApi();
Integer start = 0; // Integer | The starting index of the returned groups.
Integer limit = 200; // Integer | The maximum number of groups to return per page. Note, this may be restricted by fixed system limits.
String accessType = "accessType_example"; // String | The group permission level for which to filter results.
try {
    GroupArrayWithLinks result = apiInstance.getGroups(start, limit, accessType);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GroupApi#getGroups");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **start** | **Integer**| The starting index of the returned groups. | [optional] [default to 0] [enum: 0]
 **limit** | **Integer**| The maximum number of groups to return per page. Note, this may be restricted by fixed system limits. | [optional] [default to 200] [enum: 0]
 **accessType** | **String**| The group permission level for which to filter results. | [optional] [enum: user, admin, site-admin]

### Return type

[**GroupArrayWithLinks**](GroupArrayWithLinks.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="removeGroupById"></a>
# **removeGroupById**
> removeGroupById(id)

Delete user group

Delete user group.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: User must be a site admin.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.GroupApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

GroupApi apiInstance = new GroupApi();
String id = "id_example"; // String | Id of the group to delete.
try {
    apiInstance.removeGroupById(id);
} catch (ApiException e) {
    System.err.println("Exception when calling GroupApi#removeGroupById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| Id of the group to delete. |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="removeMemberFromGroupByGroupId"></a>
# **removeMemberFromGroupByGroupId**
> removeMemberFromGroupByGroupId(groupId, key, username, accountId)

Remove member from group using group id

Remove user as a member from a group.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: User must be a site admin.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.GroupApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

GroupApi apiInstance = new GroupApi();
String groupId = "groupId_example"; // String | Id of the group whose membership is updated.
String key = "key_example"; // String | This parameter is no longer available and will be removed from the documentation soon. Use `accountId` instead. See the [deprecation notice](/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details.
String username = "username_example"; // String | This parameter is no longer available and will be removed from the documentation soon. Use `accountId` instead. See the [deprecation notice](/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details.
String accountId = "accountId_example"; // String | The account ID of the user. The accountId uniquely identifies the user across all Atlassian products. For example, `384093:32b4d9w0-f6a5-3535-11a3-9c8c88d10192`.
try {
    apiInstance.removeMemberFromGroupByGroupId(groupId, key, username, accountId);
} catch (ApiException e) {
    System.err.println("Exception when calling GroupApi#removeMemberFromGroupByGroupId");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **groupId** | **String**| Id of the group whose membership is updated. |
 **key** | **String**| This parameter is no longer available and will be removed from the documentation soon. Use &#x60;accountId&#x60; instead. See the [deprecation notice](/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details. | [optional]
 **username** | **String**| This parameter is no longer available and will be removed from the documentation soon. Use &#x60;accountId&#x60; instead. See the [deprecation notice](/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details. | [optional]
 **accountId** | **String**| The account ID of the user. The accountId uniquely identifies the user across all Atlassian products. For example, &#x60;384093:32b4d9w0-f6a5-3535-11a3-9c8c88d10192&#x60;. | [optional]

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="searchGroups"></a>
# **searchGroups**
> GroupArrayWithLinks searchGroups(query, start, limit, shouldReturnTotalSize)

Search groups by partial query

Get search results of groups by partial query provided.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.GroupApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

GroupApi apiInstance = new GroupApi();
String query = "query_example"; // String | the search term used to query results.
Integer start = 0; // Integer | The starting index of the returned groups.
Integer limit = 200; // Integer | The maximum number of groups to return per page. Note, this is restricted to a maximum limit of 200 groups.
Boolean shouldReturnTotalSize = false; // Boolean | Whether to include total size parameter in the results. Note, fetching total size property is an expensive operation; use it if your use case needs this value.
try {
    GroupArrayWithLinks result = apiInstance.searchGroups(query, start, limit, shouldReturnTotalSize);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GroupApi#searchGroups");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **query** | **String**| the search term used to query results. |
 **start** | **Integer**| The starting index of the returned groups. | [optional] [default to 0] [enum: 0]
 **limit** | **Integer**| The maximum number of groups to return per page. Note, this is restricted to a maximum limit of 200 groups. | [optional] [default to 200] [enum: 0]
 **shouldReturnTotalSize** | **Boolean**| Whether to include total size parameter in the results. Note, fetching total size property is an expensive operation; use it if your use case needs this value. | [optional] [default to false]

### Return type

[**GroupArrayWithLinks**](GroupArrayWithLinks.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

