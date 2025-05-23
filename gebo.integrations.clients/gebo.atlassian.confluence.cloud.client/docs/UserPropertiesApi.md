# UserPropertiesApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createUserProperty**](UserPropertiesApi.md#createUserProperty) | **POST** /wiki/rest/api/user/{userId}/property/{key} | Create user property by key
[**deleteUserProperty**](UserPropertiesApi.md#deleteUserProperty) | **DELETE** /wiki/rest/api/user/{userId}/property/{key} | Delete user property
[**getUserProperties**](UserPropertiesApi.md#getUserProperties) | **GET** /wiki/rest/api/user/{userId}/property | Get user properties
[**getUserProperty**](UserPropertiesApi.md#getUserProperty) | **GET** /wiki/rest/api/user/{userId}/property/{key} | Get user property
[**updateUserProperty**](UserPropertiesApi.md#updateUserProperty) | **PUT** /wiki/rest/api/user/{userId}/property/{key} | Update user property

<a name="createUserProperty"></a>
# **createUserProperty**
> createUserProperty(body, userId, key)

Create user property by key

Creates a property for a user. For more information  about user properties, see [Confluence entity properties] (https://developer.atlassian.com/cloud/confluence/confluence-entity-properties/). &#x60;Note&#x60;, these properties stored against a user are on a Confluence site level and not space/content level.  &#x60;Note:&#x60; the number of properties which could be created per app in a tenant for each user might be restricted by fixed system limits. **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to access the Confluence site (&#x27;Can use&#x27; global permission).

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.UserPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

UserPropertiesApi apiInstance = new UserPropertiesApi();
UserPropertyCreate body = new UserPropertyCreate(); // UserPropertyCreate | The user property to be created.
String userId = "userId_example"; // String | The account ID of the user. The accountId uniquely identifies the user across all Atlassian products. For example, 384093:32b4d9w0-f6a5-3535-11a3-9c8c88d10192
String key = "key_example"; // String | The key of the user property.
try {
    apiInstance.createUserProperty(body, userId, key);
} catch (ApiException e) {
    System.err.println("Exception when calling UserPropertiesApi#createUserProperty");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UserPropertyCreate**](UserPropertyCreate.md)| The user property to be created. |
 **userId** | **String**| The account ID of the user. The accountId uniquely identifies the user across all Atlassian products. For example, 384093:32b4d9w0-f6a5-3535-11a3-9c8c88d10192 |
 **key** | **String**| The key of the user property. |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="deleteUserProperty"></a>
# **deleteUserProperty**
> deleteUserProperty(userId, key)

Delete user property

Deletes a property for the given user. For more information about user properties, see [Confluence entity properties](https://developer.atlassian.com/cloud/confluence/confluence-entity-properties/). &#x60;Note&#x60;, these properties stored against a user are on a Confluence site level and not space/content level.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to access the Confluence site (&#x27;Can use&#x27; global permission).

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.UserPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

UserPropertiesApi apiInstance = new UserPropertiesApi();
String userId = "userId_example"; // String | The account ID of the user. The accountId uniquely identifies the user across all Atlassian products. For example, 384093:32b4d9w0-f6a5-3535-11a3-9c8c88d10192
String key = "key_example"; // String | The key of the user property.
try {
    apiInstance.deleteUserProperty(userId, key);
} catch (ApiException e) {
    System.err.println("Exception when calling UserPropertiesApi#deleteUserProperty");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userId** | **String**| The account ID of the user. The accountId uniquely identifies the user across all Atlassian products. For example, 384093:32b4d9w0-f6a5-3535-11a3-9c8c88d10192 |
 **key** | **String**| The key of the user property. |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="getUserProperties"></a>
# **getUserProperties**
> UserPropertyKeyArray getUserProperties(userId, start, limit)

Get user properties

Returns the properties for a user as list of property keys. For more information about user properties, see [Confluence entity properties](https://developer.atlassian.com/cloud/confluence/confluence-entity-properties/). &#x60;Note&#x60;, these properties stored against a user are on a Confluence site level and not space/content level.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to access the Confluence site (&#x27;Can use&#x27; global permission).

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.UserPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

UserPropertiesApi apiInstance = new UserPropertiesApi();
String userId = "userId_example"; // String | The account ID of the user to be queried for its properties.
Integer start = 0; // Integer | The starting index of the returned properties.
Integer limit = 5; // Integer | The maximum number of properties to return per page. Note, this may be restricted by fixed system limits.
try {
    UserPropertyKeyArray result = apiInstance.getUserProperties(userId, start, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserPropertiesApi#getUserProperties");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userId** | **String**| The account ID of the user to be queried for its properties. |
 **start** | **Integer**| The starting index of the returned properties. | [optional] [default to 0] [enum: 0]
 **limit** | **Integer**| The maximum number of properties to return per page. Note, this may be restricted by fixed system limits. | [optional] [default to 5] [enum: 0, 25]

### Return type

[**UserPropertyKeyArray**](UserPropertyKeyArray.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getUserProperty"></a>
# **getUserProperty**
> UserProperty getUserProperty(userId, key)

Get user property

Returns the property corresponding to &#x60;key&#x60; for a user. For more information about user properties, see [Confluence entity properties](https://developer.atlassian.com/cloud/confluence/confluence-entity-properties/). &#x60;Note&#x60;, these properties stored against a user are on a Confluence site level and not space/content level.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to access the Confluence site (&#x27;Can use&#x27; global permission).

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.UserPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

UserPropertiesApi apiInstance = new UserPropertiesApi();
String userId = "userId_example"; // String | The account ID of the user to be queried for its properties.
String key = "key_example"; // String | The key of the user property.
try {
    UserProperty result = apiInstance.getUserProperty(userId, key);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserPropertiesApi#getUserProperty");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userId** | **String**| The account ID of the user to be queried for its properties. |
 **key** | **String**| The key of the user property. |

### Return type

[**UserProperty**](UserProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateUserProperty"></a>
# **updateUserProperty**
> updateUserProperty(body, userId, key)

Update user property

Updates a property for the given user. Note, you cannot update the key of a user property, only the value. For more information about user properties, see [Confluence entity properties](https://developer.atlassian.com/cloud/confluence/confluence-entity-properties/). &#x60;Note&#x60;, these properties stored against a user are on a Confluence site level and not space/content level.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to access the Confluence site (&#x27;Can use&#x27; global permission).

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.UserPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

UserPropertiesApi apiInstance = new UserPropertiesApi();
UserPropertyUpdate body = new UserPropertyUpdate(); // UserPropertyUpdate | The user property to be updated.
String userId = "userId_example"; // String | The account ID of the user. The accountId uniquely identifies the user across all Atlassian products. For example, 384093:32b4d9w0-f6a5-3535-11a3-9c8c88d10192
String key = "key_example"; // String | The key of the user property.
try {
    apiInstance.updateUserProperty(body, userId, key);
} catch (ApiException e) {
    System.err.println("Exception when calling UserPropertiesApi#updateUserProperty");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UserPropertyUpdate**](UserPropertyUpdate.md)| The user property to be updated. |
 **userId** | **String**| The account ID of the user. The accountId uniquely identifies the user across all Atlassian products. For example, 384093:32b4d9w0-f6a5-3535-11a3-9c8c88d10192 |
 **key** | **String**| The key of the user property. |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

