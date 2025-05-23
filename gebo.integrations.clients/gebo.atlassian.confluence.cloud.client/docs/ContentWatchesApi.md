# ContentWatchesApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addContentWatcher**](ContentWatchesApi.md#addContentWatcher) | **POST** /wiki/rest/api/user/watch/content/{contentId} | Add content watcher
[**addLabelWatcher**](ContentWatchesApi.md#addLabelWatcher) | **POST** /wiki/rest/api/user/watch/label/{labelName} | Add label watcher
[**addSpaceWatcher**](ContentWatchesApi.md#addSpaceWatcher) | **POST** /wiki/rest/api/user/watch/space/{spaceKey} | Add space watcher
[**getContentWatchStatus**](ContentWatchesApi.md#getContentWatchStatus) | **GET** /wiki/rest/api/user/watch/content/{contentId} | Get content watch status
[**getWatchersForSpace**](ContentWatchesApi.md#getWatchersForSpace) | **GET** /wiki/rest/api/space/{spaceKey}/watch | Get space watchers
[**getWatchesForPage**](ContentWatchesApi.md#getWatchesForPage) | **GET** /wiki/rest/api/content/{id}/notification/child-created | Get watches for page
[**getWatchesForSpace**](ContentWatchesApi.md#getWatchesForSpace) | **GET** /wiki/rest/api/content/{id}/notification/created | Get watches for space
[**isWatchingLabel**](ContentWatchesApi.md#isWatchingLabel) | **GET** /wiki/rest/api/user/watch/label/{labelName} | Get label watch status
[**isWatchingSpace**](ContentWatchesApi.md#isWatchingSpace) | **GET** /wiki/rest/api/user/watch/space/{spaceKey} | Get space watch status
[**removeContentWatcher**](ContentWatchesApi.md#removeContentWatcher) | **DELETE** /wiki/rest/api/user/watch/content/{contentId} | Remove content watcher
[**removeLabelWatcher**](ContentWatchesApi.md#removeLabelWatcher) | **DELETE** /wiki/rest/api/user/watch/label/{labelName} | Remove label watcher
[**removeSpaceWatch**](ContentWatchesApi.md#removeSpaceWatch) | **DELETE** /wiki/rest/api/user/watch/space/{spaceKey} | Remove space watch

<a name="addContentWatcher"></a>
# **addContentWatcher**
> addContentWatcher(contentId, key, username, accountId)

Add content watcher

Adds a user as a watcher to a piece of content. Choose the user by doing one of the following:  - Specify a user via a query parameter: Use the &#x60;accountId&#x60; to identify the user. - Do not specify a user: The currently logged-in user will be used.  Note, you must add the &#x60;X-Atlassian-Token: no-check&#x60; header when making a request, as this operation has XSRF protection.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Confluence Administrator&#x27; global permission if specifying a user, otherwise permission to access the Confluence site (&#x27;Can use&#x27; global permission).

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentWatchesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentWatchesApi apiInstance = new ContentWatchesApi();
String contentId = "contentId_example"; // String | The ID of the content to add the watcher to.
String key = "key_example"; // String | This parameter is no longer available and will be removed from the documentation soon. Use `accountId` instead. See the [deprecation notice](/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details.
String username = "username_example"; // String | This parameter is no longer available and will be removed from the documentation soon. Use `accountId` instead. See the [deprecation notice](/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details.
String accountId = "accountId_example"; // String | The account ID of the user. The accountId uniquely identifies the user across all Atlassian products. For example, `384093:32b4d9w0-f6a5-3535-11a3-9c8c88d10192`.
try {
    apiInstance.addContentWatcher(contentId, key, username, accountId);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentWatchesApi#addContentWatcher");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **contentId** | **String**| The ID of the content to add the watcher to. |
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

<a name="addLabelWatcher"></a>
# **addLabelWatcher**
> addLabelWatcher(xAtlassianToken, labelName, key, username, accountId)

Add label watcher

Adds a user as a watcher to a label. Choose the user by doing one of the following:  - Specify a user via a query parameter: Use the &#x60;accountId&#x60; to identify the user. - Do not specify a user: The currently logged-in user will be used.  Note, you must add the &#x60;X-Atlassian-Token: no-check&#x60; header when making a request, as this operation has XSRF protection.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Confluence Administrator&#x27; global permission if specifying a user, otherwise permission to access the Confluence site (&#x27;Can use&#x27; global permission).

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentWatchesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentWatchesApi apiInstance = new ContentWatchesApi();
String xAtlassianToken = "no-check"; // String | Note, you must add header when making a request, as this operation has XSRF protection.
String labelName = "labelName_example"; // String | The name of the label to add the watcher to.
String key = "key_example"; // String | This parameter is no longer available and will be removed from the documentation soon. Use `accountId` instead. See the [deprecation notice](/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details.
String username = "username_example"; // String | This parameter is no longer available and will be removed from the documentation soon. Use `accountId` instead. See the [deprecation notice](/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details.
String accountId = "accountId_example"; // String | The account ID of the user. The accountId uniquely identifies the user across all Atlassian products. For example, `384093:32b4d9w0-f6a5-3535-11a3-9c8c88d10192`.
try {
    apiInstance.addLabelWatcher(xAtlassianToken, labelName, key, username, accountId);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentWatchesApi#addLabelWatcher");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **xAtlassianToken** | **String**| Note, you must add header when making a request, as this operation has XSRF protection. | [default to no-check]
 **labelName** | **String**| The name of the label to add the watcher to. |
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

<a name="addSpaceWatcher"></a>
# **addSpaceWatcher**
> addSpaceWatcher(xAtlassianToken, spaceKey, key, username, accountId)

Add space watcher

Adds a user as a watcher to a space. Choose the user by doing one of the following:  - Specify a user via a query parameter: Use the &#x60;accountId&#x60; to identify the user. - Do not specify a user: The currently logged-in user will be used.  Note, you must add the &#x60;X-Atlassian-Token: no-check&#x60; header when making a request, as this operation has XSRF protection.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Confluence Administrator&#x27; global permission if specifying a user, otherwise permission to access the Confluence site (&#x27;Can use&#x27; global permission).

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentWatchesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentWatchesApi apiInstance = new ContentWatchesApi();
String xAtlassianToken = "no-check"; // String | Note, you must add header when making a request, as this operation has XSRF protection.
String spaceKey = "spaceKey_example"; // String | The key of the space to add the watcher to.
String key = "key_example"; // String | This parameter is no longer available and will be removed from the documentation soon. Use `accountId` instead. See the [deprecation notice](/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details.
String username = "username_example"; // String | This parameter is no longer available and will be removed from the documentation soon. Use `accountId` instead. See the [deprecation notice](/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details.
String accountId = "accountId_example"; // String | The account ID of the user. The accountId uniquely identifies the user across all Atlassian products. For example, `384093:32b4d9w0-f6a5-3535-11a3-9c8c88d10192`.
try {
    apiInstance.addSpaceWatcher(xAtlassianToken, spaceKey, key, username, accountId);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentWatchesApi#addSpaceWatcher");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **xAtlassianToken** | **String**| Note, you must add header when making a request, as this operation has XSRF protection. | [default to no-check]
 **spaceKey** | **String**| The key of the space to add the watcher to. |
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

<a name="getContentWatchStatus"></a>
# **getContentWatchStatus**
> UserWatch getContentWatchStatus(contentId, key, username, accountId)

Get content watch status

Returns whether a user is watching a piece of content. Choose the user by doing one of the following:  - Specify a user via a query parameter: Use the &#x60;accountId&#x60; to identify the user. - Do not specify a user: The currently logged-in user will be used.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Confluence Administrator&#x27; global permission if specifying a user, otherwise permission to access the Confluence site (&#x27;Can use&#x27; global permission).

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentWatchesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentWatchesApi apiInstance = new ContentWatchesApi();
String contentId = "contentId_example"; // String | The ID of the content to be queried for whether the specified user is watching it.
String key = "key_example"; // String | This parameter is no longer available and will be removed from the documentation soon. Use `accountId` instead. See the [deprecation notice](/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details.
String username = "username_example"; // String | This parameter is no longer available and will be removed from the documentation soon. Use `accountId` instead. See the [deprecation notice](/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details.
String accountId = "accountId_example"; // String | The account ID of the user. The accountId uniquely identifies the user across all Atlassian products. For example, `384093:32b4d9w0-f6a5-3535-11a3-9c8c88d10192`.
try {
    UserWatch result = apiInstance.getContentWatchStatus(contentId, key, username, accountId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentWatchesApi#getContentWatchStatus");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **contentId** | **String**| The ID of the content to be queried for whether the specified user is watching it. |
 **key** | **String**| This parameter is no longer available and will be removed from the documentation soon. Use &#x60;accountId&#x60; instead. See the [deprecation notice](/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details. | [optional]
 **username** | **String**| This parameter is no longer available and will be removed from the documentation soon. Use &#x60;accountId&#x60; instead. See the [deprecation notice](/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details. | [optional]
 **accountId** | **String**| The account ID of the user. The accountId uniquely identifies the user across all Atlassian products. For example, &#x60;384093:32b4d9w0-f6a5-3535-11a3-9c8c88d10192&#x60;. | [optional]

### Return type

[**UserWatch**](UserWatch.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getWatchersForSpace"></a>
# **getWatchersForSpace**
> SpaceWatchArray getWatchersForSpace(spaceKey, start, limit)

Get space watchers

Returns a list of watchers of a space

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentWatchesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentWatchesApi apiInstance = new ContentWatchesApi();
String spaceKey = "spaceKey_example"; // String | The key of the space to get watchers.
String start = "start_example"; // String | The start point of the collection to return.
String limit = "limit_example"; // String | The limit of the number of items to return, this may be restricted by fixed system limits.
try {
    SpaceWatchArray result = apiInstance.getWatchersForSpace(spaceKey, start, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentWatchesApi#getWatchersForSpace");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **spaceKey** | **String**| The key of the space to get watchers. |
 **start** | **String**| The start point of the collection to return. | [optional]
 **limit** | **String**| The limit of the number of items to return, this may be restricted by fixed system limits. | [optional]

### Return type

[**SpaceWatchArray**](SpaceWatchArray.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getWatchesForPage"></a>
# **getWatchesForPage**
> WatchArray getWatchesForPage(id, start, limit)

Get watches for page

Returns the watches for a page. A user that watches a page will receive receive notifications when the page is updated.  If you want to manage watches for a page, use the following &#x60;user&#x60; methods:  - [Get content watch status for user](#api-user-watch-content-contentId-get) - [Add content watch](#api-user-watch-content-contentId-post) - [Remove content watch](#api-user-watch-content-contentId-delete)  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to access the Confluence site (&#x27;Can use&#x27; global permission).

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentWatchesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentWatchesApi apiInstance = new ContentWatchesApi();
String id = "id_example"; // String | The ID of the content to be queried for its watches.
Integer start = 0; // Integer | The starting index of the returned watches.
Integer limit = 200; // Integer | The maximum number of watches to return per page. Note, this may be restricted by fixed system limits.
try {
    WatchArray result = apiInstance.getWatchesForPage(id, start, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentWatchesApi#getWatchesForPage");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The ID of the content to be queried for its watches. |
 **start** | **Integer**| The starting index of the returned watches. | [optional] [default to 0] [enum: 0]
 **limit** | **Integer**| The maximum number of watches to return per page. Note, this may be restricted by fixed system limits. | [optional] [default to 200] [enum: 0]

### Return type

[**WatchArray**](WatchArray.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getWatchesForSpace"></a>
# **getWatchesForSpace**
> SpaceWatchArray getWatchesForSpace(id, start, limit)

Get watches for space

Returns all space watches for the space that the content is in. A user that watches a space will receive receive notifications when any content in the space is updated.  If you want to manage watches for a space, use the following &#x60;user&#x60; methods:  - [Get space watch status for user](#api-user-watch-space-spaceKey-get) - [Add space watch](#api-user-watch-space-spaceKey-post) - [Remove space watch](#api-user-watch-space-spaceKey-delete)  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to access the Confluence site (&#x27;Can use&#x27; global permission).

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentWatchesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentWatchesApi apiInstance = new ContentWatchesApi();
String id = "id_example"; // String | The ID of the content to be queried for its watches.
Integer start = 0; // Integer | The starting index of the returned watches.
Integer limit = 200; // Integer | The maximum number of watches to return per page. Note, this may be restricted by fixed system limits.
try {
    SpaceWatchArray result = apiInstance.getWatchesForSpace(id, start, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentWatchesApi#getWatchesForSpace");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The ID of the content to be queried for its watches. |
 **start** | **Integer**| The starting index of the returned watches. | [optional] [default to 0] [enum: 0]
 **limit** | **Integer**| The maximum number of watches to return per page. Note, this may be restricted by fixed system limits. | [optional] [default to 200] [enum: 0]

### Return type

[**SpaceWatchArray**](SpaceWatchArray.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="isWatchingLabel"></a>
# **isWatchingLabel**
> UserWatch isWatchingLabel(labelName, key, username, accountId)

Get label watch status

Returns whether a user is watching a label. Choose the user by doing one of the following:  - Specify a user via a query parameter: Use the &#x60;accountId&#x60; to identify the user. - Do not specify a user: The currently logged-in user will be used.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Confluence Administrator&#x27; global permission if specifying a user, otherwise permission to access the Confluence site (&#x27;Can use&#x27; global permission).

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentWatchesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentWatchesApi apiInstance = new ContentWatchesApi();
String labelName = "labelName_example"; // String | The name of the label to be queried for whether the specified user is watching it.
String key = "key_example"; // String | This parameter is no longer available and will be removed from the documentation soon. Use `accountId` instead. See the [deprecation notice](/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details.
String username = "username_example"; // String | This parameter is no longer available and will be removed from the documentation soon. Use `accountId` instead. See the [deprecation notice](/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details.
String accountId = "accountId_example"; // String | The account ID of the user. The accountId uniquely identifies the user across all Atlassian products. For example, `384093:32b4d9w0-f6a5-3535-11a3-9c8c88d10192`.
try {
    UserWatch result = apiInstance.isWatchingLabel(labelName, key, username, accountId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentWatchesApi#isWatchingLabel");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **labelName** | **String**| The name of the label to be queried for whether the specified user is watching it. |
 **key** | **String**| This parameter is no longer available and will be removed from the documentation soon. Use &#x60;accountId&#x60; instead. See the [deprecation notice](/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details. | [optional]
 **username** | **String**| This parameter is no longer available and will be removed from the documentation soon. Use &#x60;accountId&#x60; instead. See the [deprecation notice](/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details. | [optional]
 **accountId** | **String**| The account ID of the user. The accountId uniquely identifies the user across all Atlassian products. For example, &#x60;384093:32b4d9w0-f6a5-3535-11a3-9c8c88d10192&#x60;. | [optional]

### Return type

[**UserWatch**](UserWatch.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="isWatchingSpace"></a>
# **isWatchingSpace**
> UserWatch isWatchingSpace(spaceKey, key, username, accountId)

Get space watch status

Returns whether a user is watching a space. Choose the user by doing one of the following:  - Specify a user via a query parameter: Use the &#x60;accountId&#x60; to identify the user. - Do not specify a user: The currently logged-in user will be used.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Confluence Administrator&#x27; global permission if specifying a user, otherwise permission to access the Confluence site (&#x27;Can use&#x27; global permission).

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentWatchesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentWatchesApi apiInstance = new ContentWatchesApi();
String spaceKey = "spaceKey_example"; // String | The key of the space to be queried for whether the specified user is watching it.
String key = "key_example"; // String | This parameter is no longer available and will be removed from the documentation soon. Use `accountId` instead. See the [deprecation notice](/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details.
String username = "username_example"; // String | This parameter is no longer available and will be removed from the documentation soon. Use `accountId` instead. See the [deprecation notice](/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details.
String accountId = "accountId_example"; // String | The account ID of the user. The accountId uniquely identifies the user across all Atlassian products. For example, `384093:32b4d9w0-f6a5-3535-11a3-9c8c88d10192`.
try {
    UserWatch result = apiInstance.isWatchingSpace(spaceKey, key, username, accountId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentWatchesApi#isWatchingSpace");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **spaceKey** | **String**| The key of the space to be queried for whether the specified user is watching it. |
 **key** | **String**| This parameter is no longer available and will be removed from the documentation soon. Use &#x60;accountId&#x60; instead. See the [deprecation notice](/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details. | [optional]
 **username** | **String**| This parameter is no longer available and will be removed from the documentation soon. Use &#x60;accountId&#x60; instead. See the [deprecation notice](/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details. | [optional]
 **accountId** | **String**| The account ID of the user. The accountId uniquely identifies the user across all Atlassian products. For example, &#x60;384093:32b4d9w0-f6a5-3535-11a3-9c8c88d10192&#x60;. | [optional]

### Return type

[**UserWatch**](UserWatch.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="removeContentWatcher"></a>
# **removeContentWatcher**
> removeContentWatcher(xAtlassianToken, contentId, key, username, accountId)

Remove content watcher

Removes a user as a watcher from a piece of content. Choose the user by doing one of the following:  - Specify a user via a query parameter: Use the &#x60;accountId&#x60; to identify the user. - Do not specify a user: The currently logged-in user will be used.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Confluence Administrator&#x27; global permission if specifying a user, otherwise permission to access the Confluence site (&#x27;Can use&#x27; global permission).

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentWatchesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentWatchesApi apiInstance = new ContentWatchesApi();
String xAtlassianToken = "no-check"; // String | Note, you must add header when making a request, as this operation has XSRF protection.
String contentId = "contentId_example"; // String | The ID of the content to remove the watcher from.
String key = "key_example"; // String | This parameter is no longer available and will be removed from the documentation soon. Use `accountId` instead. See the [deprecation notice](/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details.
String username = "username_example"; // String | This parameter is no longer available and will be removed from the documentation soon. Use `accountId` instead. See the [deprecation notice](/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details.
String accountId = "accountId_example"; // String | The account ID of the user. The accountId uniquely identifies the user across all Atlassian products. For example, `384093:32b4d9w0-f6a5-3535-11a3-9c8c88d10192`.
try {
    apiInstance.removeContentWatcher(xAtlassianToken, contentId, key, username, accountId);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentWatchesApi#removeContentWatcher");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **xAtlassianToken** | **String**| Note, you must add header when making a request, as this operation has XSRF protection. | [default to no-check]
 **contentId** | **String**| The ID of the content to remove the watcher from. |
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

<a name="removeLabelWatcher"></a>
# **removeLabelWatcher**
> removeLabelWatcher(labelName, key, username, accountId)

Remove label watcher

Removes a user as a watcher from a label. Choose the user by doing one of the following:  - Specify a user via a query parameter: Use the &#x60;accountId&#x60; to identify the user. - Do not specify a user: The currently logged-in user will be used.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Confluence Administrator&#x27; global permission if specifying a user, otherwise permission to access the Confluence site (&#x27;Can use&#x27; global permission).

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentWatchesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentWatchesApi apiInstance = new ContentWatchesApi();
String labelName = "labelName_example"; // String | The name of the label to remove the watcher from.
String key = "key_example"; // String | This parameter is no longer available and will be removed from the documentation soon. Use `accountId` instead. See the [deprecation notice](/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details.
String username = "username_example"; // String | This parameter is no longer available and will be removed from the documentation soon. Use `accountId` instead. See the [deprecation notice](/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details.
String accountId = "accountId_example"; // String | The account ID of the user. The accountId uniquely identifies the user across all Atlassian products. For example, `384093:32b4d9w0-f6a5-3535-11a3-9c8c88d10192`.
try {
    apiInstance.removeLabelWatcher(labelName, key, username, accountId);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentWatchesApi#removeLabelWatcher");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **labelName** | **String**| The name of the label to remove the watcher from. |
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

<a name="removeSpaceWatch"></a>
# **removeSpaceWatch**
> removeSpaceWatch(spaceKey, key, username, accountId)

Remove space watch

Removes a user as a watcher from a space. Choose the user by doing one of the following:  - Specify a user via a query parameter: Use the &#x60;accountId&#x60; to identify the user. - Do not specify a user: The currently logged-in user will be used.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Confluence Administrator&#x27; global permission if specifying a user, otherwise permission to access the Confluence site (&#x27;Can use&#x27; global permission).

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentWatchesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentWatchesApi apiInstance = new ContentWatchesApi();
String spaceKey = "spaceKey_example"; // String | The key of the space to remove the watcher from.
String key = "key_example"; // String | This parameter is no longer available and will be removed from the documentation soon. Use `accountId` instead. See the [deprecation notice](/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details.
String username = "username_example"; // String | This parameter is no longer available and will be removed from the documentation soon. Use `accountId` instead. See the [deprecation notice](/cloud/confluence/deprecation-notice-user-privacy-api-migration-guide/) for details.
String accountId = "accountId_example"; // String | The account ID of the user. The accountId uniquely identifies the user across all Atlassian products. For example, `384093:32b4d9w0-f6a5-3535-11a3-9c8c88d10192`.
try {
    apiInstance.removeSpaceWatch(spaceKey, key, username, accountId);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentWatchesApi#removeSpaceWatch");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **spaceKey** | **String**| The key of the space to remove the watcher from. |
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

