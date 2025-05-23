# EapApi

All URIs are relative to *https://{your-domain}/wiki/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createSpace**](EapApi.md#createSpace) | **POST** /spaces | Create space
[**getAvailableSpacePermissions**](EapApi.md#getAvailableSpacePermissions) | **GET** /space-permissions | Get available space permissions
[**getAvailableSpaceRoles**](EapApi.md#getAvailableSpaceRoles) | **GET** /space-roles | Get available space roles
[**getSpaceRoleAssignments**](EapApi.md#getSpaceRoleAssignments) | **GET** /spaces/{id}/role-assignments | Get space role assignments
[**getSpaceRolesById**](EapApi.md#getSpaceRolesById) | **GET** /space-roles/{id} | Get space role by ID
[**setSpaceRoleAssignments**](EapApi.md#setSpaceRoleAssignments) | **POST** /spaces/{id}/role-assignments | Set space role assignments

<a name="createSpace"></a>
# **createSpace**
> InlineResponse2011 createSpace(body)

Create space

Creates a Space as specified in the payload.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to create spaces.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.EapApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

EapApi apiInstance = new EapApi();
Object body = null; // Object | 
try {
    InlineResponse2011 result = apiInstance.createSpace(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling EapApi#createSpace");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Object**](Object.md)|  |

### Return type

[**InlineResponse2011**](InlineResponse2011.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

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
//import ai.gebo.atlassian.confluence.api.EapApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

EapApi apiInstance = new EapApi();
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of space permissions to return. If more results exist, use the `Link` response header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultSpacePermission result = apiInstance.getAvailableSpacePermissions(cursor, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling EapApi#getAvailableSpacePermissions");
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

<a name="getAvailableSpaceRoles"></a>
# **getAvailableSpaceRoles**
> MultiEntityResultSpaceRole getAvailableSpaceRoles(spaceId, roleType, principalId, principalType, cursor, limit)

Get available space roles

Retrieves the available space roles.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to access the Confluence site; if requesting a certain space&#x27;s roles, permission to view the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.EapApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

EapApi apiInstance = new EapApi();
String spaceId = "spaceId_example"; // String | The space ID for which to filter available space roles; if empty, return all available space roles for the tenant.
String roleType = "roleType_example"; // String | The space role type to filter results by.
String principalId = "principalId_example"; // String | The principal ID to filter results by. If specified, a principal-type must also be specified. Paired with a `principal-type` of `ACCESS_CLASS`, valid values include [`anonymous-users`, `jsm-project-admins`, `authenticated-users`, `all-licensed-users`, `all-product-admins`]
PrincipalType principalType = new PrincipalType(); // PrincipalType | The principal type to filter results by. If specified, a principal-id must also be specified.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of space roles to return. If more results exist, use the `Link` response header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultSpaceRole result = apiInstance.getAvailableSpaceRoles(spaceId, roleType, principalId, principalType, cursor, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling EapApi#getAvailableSpaceRoles");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **spaceId** | **String**| The space ID for which to filter available space roles; if empty, return all available space roles for the tenant. | [optional]
 **roleType** | **String**| The space role type to filter results by. | [optional]
 **principalId** | **String**| The principal ID to filter results by. If specified, a principal-type must also be specified. Paired with a &#x60;principal-type&#x60; of &#x60;ACCESS_CLASS&#x60;, valid values include [&#x60;anonymous-users&#x60;, &#x60;jsm-project-admins&#x60;, &#x60;authenticated-users&#x60;, &#x60;all-licensed-users&#x60;, &#x60;all-product-admins&#x60;] | [optional]
 **principalType** | [**PrincipalType**](.md)| The principal type to filter results by. If specified, a principal-id must also be specified. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of space roles to return. If more results exist, use the &#x60;Link&#x60; response header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultSpaceRole**](MultiEntityResultSpaceRole.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getSpaceRoleAssignments"></a>
# **getSpaceRoleAssignments**
> MultiEntityResultSpaceRoleAssignment getSpaceRoleAssignments(id, roleId, roleType, principalId, principalType, cursor, limit)

Get space role assignments

Retrieves the space role assignments.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.EapApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

EapApi apiInstance = new EapApi();
Integer id = 56; // Integer | The ID of the space for which to retrieve assignments.
String roleId = "roleId_example"; // String | Filters the returned role assignments to the provided role ID.
String roleType = "roleType_example"; // String | Filters the returned role assignments to the provided role type.
String principalId = "principalId_example"; // String | Filters the returned role assignments to the provided principal id. If specified, a principal-type must also be specified. Paired with a `principal-type` of `ACCESS_CLASS`, valid values include [`anonymous-users`, `jsm-project-admins`, `authenticated-users`, `all-licensed-users`, `all-product-admins`]
PrincipalType principalType = new PrincipalType(); // PrincipalType | Filters the returned role assignments to the provided principal type. If specified, a principal-id must also be specified.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of space roles to return. If more results exist, use the `Link` response header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultSpaceRoleAssignment result = apiInstance.getSpaceRoleAssignments(id, roleId, roleType, principalId, principalType, cursor, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling EapApi#getSpaceRoleAssignments");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Integer**| The ID of the space for which to retrieve assignments. |
 **roleId** | **String**| Filters the returned role assignments to the provided role ID. | [optional]
 **roleType** | **String**| Filters the returned role assignments to the provided role type. | [optional]
 **principalId** | **String**| Filters the returned role assignments to the provided principal id. If specified, a principal-type must also be specified. Paired with a &#x60;principal-type&#x60; of &#x60;ACCESS_CLASS&#x60;, valid values include [&#x60;anonymous-users&#x60;, &#x60;jsm-project-admins&#x60;, &#x60;authenticated-users&#x60;, &#x60;all-licensed-users&#x60;, &#x60;all-product-admins&#x60;] | [optional]
 **principalType** | [**PrincipalType**](.md)| Filters the returned role assignments to the provided principal type. If specified, a principal-id must also be specified. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of space roles to return. If more results exist, use the &#x60;Link&#x60; response header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultSpaceRoleAssignment**](MultiEntityResultSpaceRoleAssignment.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getSpaceRolesById"></a>
# **getSpaceRolesById**
> InlineResponse2008 getSpaceRolesById(id)

Get space role by ID

Retrieves the space role by ID.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to access the Confluence site.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.EapApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

EapApi apiInstance = new EapApi();
Integer id = 56; // Integer | The ID of the space role to retrieve.
try {
    InlineResponse2008 result = apiInstance.getSpaceRolesById(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling EapApi#getSpaceRolesById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Integer**| The ID of the space role to retrieve. |

### Return type

[**InlineResponse2008**](InlineResponse2008.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="setSpaceRoleAssignments"></a>
# **setSpaceRoleAssignments**
> MultiEntityResultSpaceRoleAssignment setSpaceRoleAssignments(body, id)

Set space role assignments

Sets space role assignments as specified in the payload.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to manage roles in the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.EapApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

EapApi apiInstance = new EapApi();
Object body = null; // Object | 
Integer id = 56; // Integer | The ID of the space for which to retrieve assignments.
try {
    MultiEntityResultSpaceRoleAssignment result = apiInstance.setSpaceRoleAssignments(body, id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling EapApi#setSpaceRoleAssignments");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Object**](Object.md)|  |
 **id** | **Integer**| The ID of the space for which to retrieve assignments. |

### Return type

[**MultiEntityResultSpaceRoleAssignment**](MultiEntityResultSpaceRoleAssignment.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

