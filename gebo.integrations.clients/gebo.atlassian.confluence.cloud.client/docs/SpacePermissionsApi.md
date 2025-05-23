# SpacePermissionsApi

All URIs are relative to *https://{your-domain}/wiki/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAvailableSpacePermissions**](SpacePermissionsApi.md#getAvailableSpacePermissions) | **GET** /space-permissions | Get available space permissions
[**getSpacePermissionsAssignments**](SpacePermissionsApi.md#getSpacePermissionsAssignments) | **GET** /spaces/{id}/permissions | Get space permissions assignments

<a name="getAvailableSpacePermissions"></a>
# **getAvailableSpacePermissions**
> MultiEntityResultSpacePermission getAvailableSpacePermissions(cursor, limit)

Get available space permissions

Retrieves the available space permissions.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to access the Confluence site.

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
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of space permissions to return. If more results exist, use the `Link` response header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultSpacePermission result = apiInstance.getAvailableSpacePermissions(cursor, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SpacePermissionsApi#getAvailableSpacePermissions");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of space permissions to return. If more results exist, use the &#x60;Link&#x60; response header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultSpacePermission**](MultiEntityResultSpacePermission.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getSpacePermissionsAssignments"></a>
# **getSpacePermissionsAssignments**
> MultiEntityResultSpacePermissionAssignment getSpacePermissionsAssignments(id, cursor, limit)

Get space permissions assignments

Returns space permission assignments for a specific space.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the space.

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
Long id = 789L; // Long | The ID of the space to be returned.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of assignments to return. If more results exist, use the `Link` response header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultSpacePermissionAssignment result = apiInstance.getSpacePermissionsAssignments(id, cursor, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SpacePermissionsApi#getSpacePermissionsAssignments");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the space to be returned. |
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of assignments to return. If more results exist, use the &#x60;Link&#x60; response header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultSpacePermissionAssignment**](MultiEntityResultSpacePermissionAssignment.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

