# ContentRestrictionsApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addGroupToContentRestrictionByGroupId**](ContentRestrictionsApi.md#addGroupToContentRestrictionByGroupId) | **PUT** /wiki/rest/api/content/{id}/restriction/byOperation/{operationKey}/byGroupId/{groupId} | Add group to content restriction
[**addRestrictions**](ContentRestrictionsApi.md#addRestrictions) | **POST** /wiki/rest/api/content/{id}/restriction | Add restrictions
[**addUserToContentRestriction**](ContentRestrictionsApi.md#addUserToContentRestriction) | **PUT** /wiki/rest/api/content/{id}/restriction/byOperation/{operationKey}/user | Add user to content restriction
[**deleteRestrictions**](ContentRestrictionsApi.md#deleteRestrictions) | **DELETE** /wiki/rest/api/content/{id}/restriction | Delete restrictions
[**getContentRestrictionStatusForUser**](ContentRestrictionsApi.md#getContentRestrictionStatusForUser) | **GET** /wiki/rest/api/content/{id}/restriction/byOperation/{operationKey}/user | Get content restriction status for user
[**getIndividualGroupRestrictionStatusByGroupId**](ContentRestrictionsApi.md#getIndividualGroupRestrictionStatusByGroupId) | **GET** /wiki/rest/api/content/{id}/restriction/byOperation/{operationKey}/byGroupId/{groupId} | Get content restriction status for group
[**getRestrictions**](ContentRestrictionsApi.md#getRestrictions) | **GET** /wiki/rest/api/content/{id}/restriction | Get restrictions
[**getRestrictionsByOperation**](ContentRestrictionsApi.md#getRestrictionsByOperation) | **GET** /wiki/rest/api/content/{id}/restriction/byOperation | Get restrictions by operation
[**getRestrictionsForOperation**](ContentRestrictionsApi.md#getRestrictionsForOperation) | **GET** /wiki/rest/api/content/{id}/restriction/byOperation/{operationKey} | Get restrictions for operation
[**removeGroupFromContentRestriction**](ContentRestrictionsApi.md#removeGroupFromContentRestriction) | **DELETE** /wiki/rest/api/content/{id}/restriction/byOperation/{operationKey}/byGroupId/{groupId} | Remove group from content restriction
[**removeUserFromContentRestriction**](ContentRestrictionsApi.md#removeUserFromContentRestriction) | **DELETE** /wiki/rest/api/content/{id}/restriction/byOperation/{operationKey}/user | Remove user from content restriction
[**updateRestrictions**](ContentRestrictionsApi.md#updateRestrictions) | **PUT** /wiki/rest/api/content/{id}/restriction | Update restrictions

<a name="addGroupToContentRestrictionByGroupId"></a>
# **addGroupToContentRestrictionByGroupId**
> addGroupToContentRestrictionByGroupId(id, operationKey, groupId)

Add group to content restriction

Adds a group to a content restriction by Group Id. That is, grant read or update permission to the group for a piece of content.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to edit the content.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentRestrictionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentRestrictionsApi apiInstance = new ContentRestrictionsApi();
String id = "id_example"; // String | The ID of the content that the restriction applies to.
String operationKey = "operationKey_example"; // String | The operation that the restriction applies to.
String groupId = "groupId_example"; // String | The groupId of the group to add to the content restriction.
try {
    apiInstance.addGroupToContentRestrictionByGroupId(id, operationKey, groupId);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentRestrictionsApi#addGroupToContentRestrictionByGroupId");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The ID of the content that the restriction applies to. |
 **operationKey** | **String**| The operation that the restriction applies to. | [enum: read, update]
 **groupId** | **String**| The groupId of the group to add to the content restriction. |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="addRestrictions"></a>
# **addRestrictions**
> ContentRestrictionArray addRestrictions(body, id, expand)

Add restrictions

Adds restrictions to a piece of content. Note, this does not change any existing restrictions on the content.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to edit the content.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentRestrictionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentRestrictionsApi apiInstance = new ContentRestrictionsApi();
ContentRestrictionAddOrUpdateArray body = new ContentRestrictionAddOrUpdateArray(); // ContentRestrictionAddOrUpdateArray | The restrictions to be added to the content.
String id = "id_example"; // String | The ID of the content to add restrictions to.
List<String> expand = Arrays.asList("expand_example"); // List<String> | A multi-value parameter indicating which properties of the content restrictions (returned in response) to expand.  - `restrictions.user` returns the piece of content that the restrictions are applied to. Expanded by default. - `restrictions.group` returns the piece of content that the restrictions are applied to. Expanded by default. - `content` returns the piece of content that the restrictions are applied to.
try {
    ContentRestrictionArray result = apiInstance.addRestrictions(body, id, expand);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentRestrictionsApi#addRestrictions");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ContentRestrictionAddOrUpdateArray**](ContentRestrictionAddOrUpdateArray.md)| The restrictions to be added to the content. |
 **id** | **String**| The ID of the content to add restrictions to. |
 **expand** | [**List&lt;String&gt;**](String.md)| A multi-value parameter indicating which properties of the content restrictions (returned in response) to expand.  - &#x60;restrictions.user&#x60; returns the piece of content that the restrictions are applied to. Expanded by default. - &#x60;restrictions.group&#x60; returns the piece of content that the restrictions are applied to. Expanded by default. - &#x60;content&#x60; returns the piece of content that the restrictions are applied to. | [optional] [enum: restrictions.user, read.restrictions.user, update.restrictions.user, restrictions.group, read.restrictions.group, update.restrictions.group, content]

### Return type

[**ContentRestrictionArray**](ContentRestrictionArray.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="addUserToContentRestriction"></a>
# **addUserToContentRestriction**
> addUserToContentRestriction(id, operationKey, key, username, accountId)

Add user to content restriction

Adds a user to a content restriction. That is, grant read or update permission to the user for a piece of content.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to edit the content.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentRestrictionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentRestrictionsApi apiInstance = new ContentRestrictionsApi();
String id = "id_example"; // String | The ID of the content that the restriction applies to.
String operationKey = "operationKey_example"; // String | The operation that the restriction applies to.
String key = "key_example"; // String | This parameter is no longer available and will be removed from the documentation soon. Use `accountId` instead. See the [deprecation notice](/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details.
String username = "username_example"; // String | This parameter is no longer available and will be removed from the documentation soon. Use `accountId` instead. See the [deprecation notice](/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details.
String accountId = "accountId_example"; // String | The account ID of the user. The accountId uniquely identifies the user across all Atlassian products. For example, `384093:32b4d9w0-f6a5-3535-11a3-9c8c88d10192`.
try {
    apiInstance.addUserToContentRestriction(id, operationKey, key, username, accountId);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentRestrictionsApi#addUserToContentRestriction");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The ID of the content that the restriction applies to. |
 **operationKey** | **String**| The operation that the restriction applies to. |
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

<a name="deleteRestrictions"></a>
# **deleteRestrictions**
> ContentRestrictionArray deleteRestrictions(id, expand)

Delete restrictions

Removes all restrictions (read and update) on a piece of content.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to edit the content.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentRestrictionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentRestrictionsApi apiInstance = new ContentRestrictionsApi();
String id = "id_example"; // String | The ID of the content to remove restrictions from.
List<String> expand = Arrays.asList("expand_example"); // List<String> | A multi-value parameter indicating which properties of the content restrictions (returned in response) to expand.  - `restrictions.user` returns the piece of content that the restrictions are applied to. Expanded by default. - `restrictions.group` returns the piece of content that the restrictions are applied to. Expanded by default. - `content` returns the piece of content that the restrictions are applied to.
try {
    ContentRestrictionArray result = apiInstance.deleteRestrictions(id, expand);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentRestrictionsApi#deleteRestrictions");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The ID of the content to remove restrictions from. |
 **expand** | [**List&lt;String&gt;**](String.md)| A multi-value parameter indicating which properties of the content restrictions (returned in response) to expand.  - &#x60;restrictions.user&#x60; returns the piece of content that the restrictions are applied to. Expanded by default. - &#x60;restrictions.group&#x60; returns the piece of content that the restrictions are applied to. Expanded by default. - &#x60;content&#x60; returns the piece of content that the restrictions are applied to. | [optional] [enum: restrictions.user, read.restrictions.user, update.restrictions.user, restrictions.group, read.restrictions.group, update.restrictions.group, content]

### Return type

[**ContentRestrictionArray**](ContentRestrictionArray.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getContentRestrictionStatusForUser"></a>
# **getContentRestrictionStatusForUser**
> getContentRestrictionStatusForUser(id, operationKey, key, username, accountId)

Get content restriction status for user

Returns whether the specified content restriction applies to a user. For example, if a page with &#x60;id&#x3D;123&#x60; has a &#x60;read&#x60; restriction for a user with an account ID of &#x60;384093:32b4d9w0-f6a5-3535-11a3-9c8c88d10192&#x60;, the following request will return &#x60;true&#x60;:  &#x60;/wiki/rest/api/content/123/restriction/byOperation/read/user?accountId&#x3D;384093:32b4d9w0-f6a5-3535-11a3-9c8c88d10192&#x60;  Note that a response of &#x60;true&#x60; does not guarantee that the user can view the page, as it does not account for account-inherited restrictions, space permissions, or even product access. For more information, see [Confluence permissions](https://confluence.atlassian.com/x/_AozKw).  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentRestrictionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentRestrictionsApi apiInstance = new ContentRestrictionsApi();
String id = "id_example"; // String | The ID of the content that the restriction applies to.
String operationKey = "operationKey_example"; // String | The operation that is restricted.
String key = "key_example"; // String | This parameter is no longer available and will be removed from the documentation soon. Use `accountId` instead. See the [deprecation notice](/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details.
String username = "username_example"; // String | This parameter is no longer available and will be removed from the documentation soon. Use `accountId` instead. See the [deprecation notice](/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details.
String accountId = "accountId_example"; // String | The account ID of the user. The accountId uniquely identifies the user across all Atlassian products. For example, `384093:32b4d9w0-f6a5-3535-11a3-9c8c88d10192`.
try {
    apiInstance.getContentRestrictionStatusForUser(id, operationKey, key, username, accountId);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentRestrictionsApi#getContentRestrictionStatusForUser");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The ID of the content that the restriction applies to. |
 **operationKey** | **String**| The operation that is restricted. |
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

<a name="getIndividualGroupRestrictionStatusByGroupId"></a>
# **getIndividualGroupRestrictionStatusByGroupId**
> getIndividualGroupRestrictionStatusByGroupId(id, operationKey, groupId)

Get content restriction status for group

Returns whether the specified content restriction applies to a group. For example, if a page with &#x60;id&#x3D;123&#x60; has a &#x60;read&#x60; restriction for the &#x60;123456&#x60; group id, the following request will return &#x60;true&#x60;:  &#x60;/wiki/rest/api/content/123/restriction/byOperation/read/byGroupId/123456&#x60;  Note that a response of &#x60;true&#x60; does not guarantee that the group can view the page, as it does not account for account-inherited restrictions, space permissions, or even product access. For more information, see [Confluence permissions](https://confluence.atlassian.com/x/_AozKw).  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentRestrictionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentRestrictionsApi apiInstance = new ContentRestrictionsApi();
String id = "id_example"; // String | The ID of the content that the restriction applies to.
String operationKey = "operationKey_example"; // String | The operation that the restriction applies to.
String groupId = "groupId_example"; // String | The id of the group to be queried for whether the content restriction applies to it.
try {
    apiInstance.getIndividualGroupRestrictionStatusByGroupId(id, operationKey, groupId);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentRestrictionsApi#getIndividualGroupRestrictionStatusByGroupId");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The ID of the content that the restriction applies to. |
 **operationKey** | **String**| The operation that the restriction applies to. | [enum: read, update]
 **groupId** | **String**| The id of the group to be queried for whether the content restriction applies to it. |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="getRestrictions"></a>
# **getRestrictions**
> ContentRestrictionArray getRestrictions(id, expand, start, limit)

Get restrictions

Returns the restrictions on a piece of content.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentRestrictionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentRestrictionsApi apiInstance = new ContentRestrictionsApi();
String id = "id_example"; // String | The ID of the content to be queried for its restrictions.
List<String> expand = Arrays.asList("expand_example"); // List<String> | A multi-value parameter indicating which properties of the content restrictions to expand. By default, the following objects are expanded: `restrictions.user`, `restrictions.group`.  - `restrictions.user` returns the piece of content that the restrictions are applied to. - `restrictions.group` returns the piece of content that the restrictions are applied to. - `content` returns the piece of content that the restrictions are applied to.
Integer start = 0; // Integer | The starting index of the users and groups in the returned restrictions.
Integer limit = 100; // Integer | The maximum number of users and the maximum number of groups, in the returned restrictions, to return per page. Note, this may be restricted by fixed system limits.
try {
    ContentRestrictionArray result = apiInstance.getRestrictions(id, expand, start, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentRestrictionsApi#getRestrictions");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The ID of the content to be queried for its restrictions. |
 **expand** | [**List&lt;String&gt;**](String.md)| A multi-value parameter indicating which properties of the content restrictions to expand. By default, the following objects are expanded: &#x60;restrictions.user&#x60;, &#x60;restrictions.group&#x60;.  - &#x60;restrictions.user&#x60; returns the piece of content that the restrictions are applied to. - &#x60;restrictions.group&#x60; returns the piece of content that the restrictions are applied to. - &#x60;content&#x60; returns the piece of content that the restrictions are applied to. | [optional] [enum: restrictions.user, read.restrictions.user, update.restrictions.user, restrictions.group, read.restrictions.group, update.restrictions.group, content]
 **start** | **Integer**| The starting index of the users and groups in the returned restrictions. | [optional] [default to 0] [enum: 0]
 **limit** | **Integer**| The maximum number of users and the maximum number of groups, in the returned restrictions, to return per page. Note, this may be restricted by fixed system limits. | [optional] [default to 100] [enum: 0]

### Return type

[**ContentRestrictionArray**](ContentRestrictionArray.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getRestrictionsByOperation"></a>
# **getRestrictionsByOperation**
> Map&lt;String, InlineResponseMap200&gt; getRestrictionsByOperation(id, expand)

Get restrictions by operation

Returns restrictions on a piece of content by operation. This method is similar to [Get restrictions](#api-content-id-restriction-get) except that the operations are properties of the return object, rather than items in a results array.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentRestrictionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentRestrictionsApi apiInstance = new ContentRestrictionsApi();
String id = "id_example"; // String | The ID of the content to be queried for its restrictions.
List<String> expand = Arrays.asList("expand_example"); // List<String> | A multi-value parameter indicating which properties of the content restrictions to expand.  - `restrictions.user` returns the piece of content that the restrictions are applied to. Expanded by default. - `restrictions.group` returns the piece of content that the restrictions are applied to. Expanded by default. - `content` returns the piece of content that the restrictions are applied to.
try {
    Map<String, InlineResponseMap200> result = apiInstance.getRestrictionsByOperation(id, expand);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentRestrictionsApi#getRestrictionsByOperation");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The ID of the content to be queried for its restrictions. |
 **expand** | [**List&lt;String&gt;**](String.md)| A multi-value parameter indicating which properties of the content restrictions to expand.  - &#x60;restrictions.user&#x60; returns the piece of content that the restrictions are applied to. Expanded by default. - &#x60;restrictions.group&#x60; returns the piece of content that the restrictions are applied to. Expanded by default. - &#x60;content&#x60; returns the piece of content that the restrictions are applied to. | [optional] [enum: restrictions.user, restrictions.group, content]

### Return type

[**Map&lt;String, InlineResponseMap200&gt;**](InlineResponseMap200.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getRestrictionsForOperation"></a>
# **getRestrictionsForOperation**
> ContentRestriction getRestrictionsForOperation(id, operationKey, expand, start, limit)

Get restrictions for operation

Returns the restictions on a piece of content for a given operation (read or update).  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentRestrictionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentRestrictionsApi apiInstance = new ContentRestrictionsApi();
String id = "id_example"; // String | The ID of the content to be queried for its restrictions.
String operationKey = "operationKey_example"; // String | The operation type of the restrictions to be returned.
List<String> expand = Arrays.asList("expand_example"); // List<String> | A multi-value parameter indicating which properties of the content restrictions to expand.  - `restrictions.user` returns the piece of content that the restrictions are applied to. Expanded by default. - `restrictions.group` returns the piece of content that the restrictions are applied to. Expanded by default. - `content` returns the piece of content that the restrictions are applied to.
Integer start = 0; // Integer | The starting index of the users and groups in the returned restrictions.
Integer limit = 100; // Integer | The maximum number of users and the maximum number of groups, in the returned restrictions, to return per page. Note, this may be restricted by fixed system limits.
try {
    ContentRestriction result = apiInstance.getRestrictionsForOperation(id, operationKey, expand, start, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentRestrictionsApi#getRestrictionsForOperation");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The ID of the content to be queried for its restrictions. |
 **operationKey** | **String**| The operation type of the restrictions to be returned. | [enum: read, update]
 **expand** | [**List&lt;String&gt;**](String.md)| A multi-value parameter indicating which properties of the content restrictions to expand.  - &#x60;restrictions.user&#x60; returns the piece of content that the restrictions are applied to. Expanded by default. - &#x60;restrictions.group&#x60; returns the piece of content that the restrictions are applied to. Expanded by default. - &#x60;content&#x60; returns the piece of content that the restrictions are applied to. | [optional] [enum: restrictions.user, restrictions.group, content]
 **start** | **Integer**| The starting index of the users and groups in the returned restrictions. | [optional] [default to 0] [enum: 0]
 **limit** | **Integer**| The maximum number of users and the maximum number of groups, in the returned restrictions, to return per page. Note, this may be restricted by fixed system limits. | [optional] [default to 100] [enum: 0]

### Return type

[**ContentRestriction**](ContentRestriction.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="removeGroupFromContentRestriction"></a>
# **removeGroupFromContentRestriction**
> removeGroupFromContentRestriction(id, operationKey, groupId)

Remove group from content restriction

Removes a group from a content restriction. That is, remove read or update permission for the group for a piece of content.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to edit the content.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentRestrictionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentRestrictionsApi apiInstance = new ContentRestrictionsApi();
String id = "id_example"; // String | The ID of the content that the restriction applies to.
String operationKey = "operationKey_example"; // String | The operation that the restriction applies to.
String groupId = "groupId_example"; // String | The id of the group to remove from the content restriction.
try {
    apiInstance.removeGroupFromContentRestriction(id, operationKey, groupId);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentRestrictionsApi#removeGroupFromContentRestriction");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The ID of the content that the restriction applies to. |
 **operationKey** | **String**| The operation that the restriction applies to. | [enum: read, update]
 **groupId** | **String**| The id of the group to remove from the content restriction. |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="removeUserFromContentRestriction"></a>
# **removeUserFromContentRestriction**
> removeUserFromContentRestriction(id, operationKey, key, username, accountId)

Remove user from content restriction

Removes a group from a content restriction. That is, remove read or update permission for the group for a piece of content.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to edit the content.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentRestrictionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentRestrictionsApi apiInstance = new ContentRestrictionsApi();
String id = "id_example"; // String | The ID of the content that the restriction applies to.
String operationKey = "operationKey_example"; // String | The operation that the restriction applies to.
String key = "key_example"; // String | This parameter is no longer available and will be removed from the documentation soon. Use `accountId` instead. See the [deprecation notice](/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details.
String username = "username_example"; // String | This parameter is no longer available and will be removed from the documentation soon. Use `accountId` instead. See the [deprecation notice](/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details.
String accountId = "accountId_example"; // String | The account ID of the user. The accountId uniquely identifies the user across all Atlassian products. For example, `384093:32b4d9w0-f6a5-3535-11a3-9c8c88d10192`.
try {
    apiInstance.removeUserFromContentRestriction(id, operationKey, key, username, accountId);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentRestrictionsApi#removeUserFromContentRestriction");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The ID of the content that the restriction applies to. |
 **operationKey** | **String**| The operation that the restriction applies to. | [enum: read, update]
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

<a name="updateRestrictions"></a>
# **updateRestrictions**
> ContentRestrictionArray updateRestrictions(body, id, expand)

Update restrictions

Updates restrictions for a piece of content. This removes the existing restrictions and replaces them with the restrictions in the request.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to edit the content.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentRestrictionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentRestrictionsApi apiInstance = new ContentRestrictionsApi();
ContentRestrictionAddOrUpdateArray body = new ContentRestrictionAddOrUpdateArray(); // ContentRestrictionAddOrUpdateArray | The updated restrictions for the content.
String id = "id_example"; // String | The ID of the content to update restrictions for.
List<String> expand = Arrays.asList("expand_example"); // List<String> | A multi-value parameter indicating which properties of the content restrictions (returned in response) to expand.  - `restrictions.user` returns the piece of content that the restrictions are applied to. Expanded by default. - `restrictions.group` returns the piece of content that the restrictions are applied to. Expanded by default. - `content` returns the piece of content that the restrictions are applied to.
try {
    ContentRestrictionArray result = apiInstance.updateRestrictions(body, id, expand);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentRestrictionsApi#updateRestrictions");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ContentRestrictionAddOrUpdateArray**](ContentRestrictionAddOrUpdateArray.md)| The updated restrictions for the content. |
 **id** | **String**| The ID of the content to update restrictions for. |
 **expand** | [**List&lt;String&gt;**](String.md)| A multi-value parameter indicating which properties of the content restrictions (returned in response) to expand.  - &#x60;restrictions.user&#x60; returns the piece of content that the restrictions are applied to. Expanded by default. - &#x60;restrictions.group&#x60; returns the piece of content that the restrictions are applied to. Expanded by default. - &#x60;content&#x60; returns the piece of content that the restrictions are applied to. | [optional] [enum: restrictions.user, read.restrictions.user, update.restrictions.user, restrictions.group, read.restrictions.group, update.restrictions.group, content]

### Return type

[**ContentRestrictionArray**](ContentRestrictionArray.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

