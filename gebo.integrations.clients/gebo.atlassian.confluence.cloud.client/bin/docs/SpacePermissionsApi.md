# SpacePermissionsApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addCustomContentPermissions**](SpacePermissionsApi.md#addCustomContentPermissions) | **POST** /wiki/rest/api/space/{spaceKey}/permission/custom-content | Add new custom content permission to space
[**addPermissionToSpace**](SpacePermissionsApi.md#addPermissionToSpace) | **POST** /wiki/rest/api/space/{spaceKey}/permission | Add new permission to space
[**removePermission**](SpacePermissionsApi.md#removePermission) | **DELETE** /wiki/rest/api/space/{spaceKey}/permission/{id} | Remove a space permission

<a name="addCustomContentPermissions"></a>
# **addCustomContentPermissions**
> addCustomContentPermissions(body, spaceKey)

Add new custom content permission to space

Adds new custom content permission to space.  If the permission to be added is a group permission, the group can be identified by its group name or group id.  Note: Only apps can access this REST resource and only make changes to the respective app permissions.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Admin&#x27; permission for the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.SpacePermissionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

SpacePermissionsApi apiInstance = new SpacePermissionsApi();
SpacePermissionCustomContent body = new SpacePermissionCustomContent(); // SpacePermissionCustomContent | The permissions to be created.
String spaceKey = "spaceKey_example"; // String | The key of the space to be queried for its content.
try {
    apiInstance.addCustomContentPermissions(body, spaceKey);
} catch (ApiException e) {
    System.err.println("Exception when calling SpacePermissionsApi#addCustomContentPermissions");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SpacePermissionCustomContent**](SpacePermissionCustomContent.md)| The permissions to be created. |
 **spaceKey** | **String**| The key of the space to be queried for its content. |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="addPermissionToSpace"></a>
# **addPermissionToSpace**
> SpacePermissionV2 addPermissionToSpace(body, spaceKey)

Add new permission to space

Adds new permission to space.  If the permission to be added is a group permission, the group can be identified by its group name or group id.  Note: Apps cannot access this REST resource - including when utilizing user impersonation.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Admin&#x27; permission for the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.SpacePermissionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

SpacePermissionsApi apiInstance = new SpacePermissionsApi();
Map<String, Object> body = new Map(); // Map<String, Object> | The permission to be created.
String spaceKey = "spaceKey_example"; // String | The key of the space to be queried for its content.
try {
    SpacePermissionV2 result = apiInstance.addPermissionToSpace(body, spaceKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SpacePermissionsApi#addPermissionToSpace");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Map&lt;String, Object&gt;**](Map.md)| The permission to be created. |
 **spaceKey** | **String**| The key of the space to be queried for its content. |

### Return type

[**SpacePermissionV2**](SpacePermissionV2.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="removePermission"></a>
# **removePermission**
> removePermission(spaceKey, id)

Remove a space permission

Removes a space permission. Note that removing Read Space permission for a user or group will remove all the space permissions for that user or group.  Note: Apps cannot access this REST resource - including when utilizing user impersonation.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Admin&#x27; permission for the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.SpacePermissionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

SpacePermissionsApi apiInstance = new SpacePermissionsApi();
String spaceKey = "spaceKey_example"; // String | The key of the space to be queried for its content.
Integer id = 56; // Integer | Id of the permission to be deleted.
try {
    apiInstance.removePermission(spaceKey, id);
} catch (ApiException e) {
    System.err.println("Exception when calling SpacePermissionsApi#removePermission");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **spaceKey** | **String**| The key of the space to be queried for its content. |
 **id** | **Integer**| Id of the permission to be deleted. |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

