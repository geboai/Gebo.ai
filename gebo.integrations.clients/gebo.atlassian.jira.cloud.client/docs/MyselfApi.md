# MyselfApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteLocale**](MyselfApi.md#deleteLocale) | **DELETE** /rest/api/3/mypreferences/locale | Delete locale
[**getCurrentUser**](MyselfApi.md#getCurrentUser) | **GET** /rest/api/3/myself | Get current user
[**getLocale**](MyselfApi.md#getLocale) | **GET** /rest/api/3/mypreferences/locale | Get locale
[**getPreference**](MyselfApi.md#getPreference) | **GET** /rest/api/3/mypreferences | Get preference
[**removePreference**](MyselfApi.md#removePreference) | **DELETE** /rest/api/3/mypreferences | Delete preference
[**setLocale**](MyselfApi.md#setLocale) | **PUT** /rest/api/3/mypreferences/locale | Set locale
[**setPreference**](MyselfApi.md#setPreference) | **PUT** /rest/api/3/mypreferences | Set preference

<a name="deleteLocale"></a>
# **deleteLocale**
> Object deleteLocale()

Delete locale

Deprecated, use [ Update a user profile](https://developer.atlassian.com/cloud/admin/user-management/rest/#api-users-account-id-manage-profile-patch) from the user management REST API instead.  Deletes the locale of the user, which restores the default setting.  **[Permissions](#permissions) required:** Permission to access Jira.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.MyselfApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

MyselfApi apiInstance = new MyselfApi();
try {
    Object result = apiInstance.deleteLocale();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MyselfApi#deleteLocale");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**Object**

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getCurrentUser"></a>
# **getCurrentUser**
> User getCurrentUser(expand)

Get current user

Returns details for the current user.  **[Permissions](#permissions) required:** Permission to access Jira.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.MyselfApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

MyselfApi apiInstance = new MyselfApi();
String expand = "expand_example"; // String | Use [expand](#expansion) to include additional information about user in the response. This parameter accepts a comma-separated list. Expand options include:   *  `groups` Returns all groups, including nested groups, the user belongs to.  *  `applicationRoles` Returns the application roles the user is assigned to.
try {
    User result = apiInstance.getCurrentUser(expand);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MyselfApi#getCurrentUser");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **expand** | **String**| Use [expand](#expansion) to include additional information about user in the response. This parameter accepts a comma-separated list. Expand options include:   *  &#x60;groups&#x60; Returns all groups, including nested groups, the user belongs to.  *  &#x60;applicationRoles&#x60; Returns the application roles the user is assigned to. | [optional]

### Return type

[**User**](User.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getLocale"></a>
# **getLocale**
> Locale getLocale()

Get locale

Returns the locale for the user.  If the user has no language preference set (which is the default setting) or this resource is accessed anonymous, the browser locale detected by Jira is returned. Jira detects the browser locale using the *Accept-Language* header in the request. However, if this doesn&#x27;t match a locale available Jira, the site default locale is returned.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** None.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.MyselfApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

MyselfApi apiInstance = new MyselfApi();
try {
    Locale result = apiInstance.getLocale();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MyselfApi#getLocale");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**Locale**](Locale.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getPreference"></a>
# **getPreference**
> String getPreference(key)

Get preference

Returns the value of a preference of the current user.  Note that these keys are deprecated:   *  *jira.user.locale* The locale of the user. By default this is not set and the user takes the locale of the instance.  *  *jira.user.timezone* The time zone of the user. By default this is not set and the user takes the timezone of the instance.  These system preferences keys will be deprecated by 15/07/2024. You can still retrieve these keys, but it will not have any impact on Notification behaviour.   *  *user.notifications.watcher* Whether the user gets notified when they are watcher.  *  *user.notifications.assignee* Whether the user gets notified when they are assignee.  *  *user.notifications.reporter* Whether the user gets notified when they are reporter.  *  *user.notifications.mentions* Whether the user gets notified when they are mentions.  Use [ Update a user profile](https://developer.atlassian.com/cloud/admin/user-management/rest/#api-users-account-id-manage-profile-patch) from the user management REST API to manage timezone and locale instead.  **[Permissions](#permissions) required:** Permission to access Jira.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.MyselfApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

MyselfApi apiInstance = new MyselfApi();
String key = "key_example"; // String | The key of the preference.
try {
    String result = apiInstance.getPreference(key);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MyselfApi#getPreference");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **key** | **String**| The key of the preference. |

### Return type

**String**

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="removePreference"></a>
# **removePreference**
> removePreference(key)

Delete preference

Deletes a preference of the user, which restores the default value of system defined settings.  Note that these keys are deprecated:   *  *jira.user.locale* The locale of the user. By default, not set. The user takes the instance locale.  *  *jira.user.timezone* The time zone of the user. By default, not set. The user takes the instance timezone.  Use [ Update a user profile](https://developer.atlassian.com/cloud/admin/user-management/rest/#api-users-account-id-manage-profile-patch) from the user management REST API to manage timezone and locale instead.  **[Permissions](#permissions) required:** Permission to access Jira.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.MyselfApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

MyselfApi apiInstance = new MyselfApi();
String key = "key_example"; // String | The key of the preference.
try {
    apiInstance.removePreference(key);
} catch (ApiException e) {
    System.err.println("Exception when calling MyselfApi#removePreference");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **key** | **String**| The key of the preference. |

### Return type

null (empty response body)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="setLocale"></a>
# **setLocale**
> Object setLocale(body)

Set locale

Deprecated, use [ Update a user profile](https://developer.atlassian.com/cloud/admin/user-management/rest/#api-users-account-id-manage-profile-patch) from the user management REST API instead.  Sets the locale of the user. The locale must be one supported by the instance of Jira.  **[Permissions](#permissions) required:** Permission to access Jira.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.MyselfApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

MyselfApi apiInstance = new MyselfApi();
Locale body = new Locale(); // Locale | The locale defined in a LocaleBean.
try {
    Object result = apiInstance.setLocale(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MyselfApi#setLocale");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Locale**](Locale.md)| The locale defined in a LocaleBean. |

### Return type

**Object**

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="setPreference"></a>
# **setPreference**
> Object setPreference(body, key)

Set preference

Creates a preference for the user or updates a preference&#x27;s value by sending a plain text string. For example, &#x60;false&#x60;. An arbitrary preference can be created with the value containing up to 255 characters. In addition, the following keys define system preferences that can be set or created:   *  *user.notifications.mimetype* The mime type used in notifications sent to the user. Defaults to &#x60;html&#x60;.  *  *user.default.share.private* Whether new [ filters](https://confluence.atlassian.com/x/eQiiLQ) are set to private. Defaults to &#x60;true&#x60;.  *  *user.keyboard.shortcuts.disabled* Whether keyboard shortcuts are disabled. Defaults to &#x60;false&#x60;.  *  *user.autowatch.disabled* Whether the user automatically watches issues they create or add a comment to. By default, not set: the user takes the instance autowatch setting.  *  *user.notifiy.own.changes* Whether the user gets notified of their own changes.  Note that these keys are deprecated:   *  *jira.user.locale* The locale of the user. By default, not set. The user takes the instance locale.  *  *jira.user.timezone* The time zone of the user. By default, not set. The user takes the instance timezone.  These system preferences keys will be deprecated by 15/07/2024. You can still use these keys to create arbitrary preferences, but it will not have any impact on Notification behaviour.   *  *user.notifications.watcher* Whether the user gets notified when they are watcher.  *  *user.notifications.assignee* Whether the user gets notified when they are assignee.  *  *user.notifications.reporter* Whether the user gets notified when they are reporter.  *  *user.notifications.mentions* Whether the user gets notified when they are mentions.  Use [ Update a user profile](https://developer.atlassian.com/cloud/admin/user-management/rest/#api-users-account-id-manage-profile-patch) from the user management REST API to manage timezone and locale instead.  **[Permissions](#permissions) required:** Permission to access Jira.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.MyselfApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

MyselfApi apiInstance = new MyselfApi();
String body = "body_example"; // String | The value of the preference as a plain text string. The maximum length is 255 characters.
String key = "key_example"; // String | The key of the preference. The maximum length is 255 characters.
try {
    Object result = apiInstance.setPreference(body, key);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MyselfApi#setPreference");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**String**](String.md)| The value of the preference as a plain text string. The maximum length is 255 characters. |
 **key** | **String**| The key of the preference. The maximum length is 255 characters. |

### Return type

**Object**

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json, text/plain
 - **Accept**: application/json

