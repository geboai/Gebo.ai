# GroupAndUserPickerApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**findUsersAndGroups**](GroupAndUserPickerApi.md#findUsersAndGroups) | **GET** /rest/api/3/groupuserpicker | Find users and groups

<a name="findUsersAndGroups"></a>
# **findUsersAndGroups**
> FoundUsersAndGroups findUsersAndGroups(query, maxResults, showAvatar, fieldId, projectId, issueTypeId, avatarSize, caseInsensitive, excludeConnectAddons)

Find users and groups

Returns a list of users and groups matching a string. The string is used:   *  for users, to find a case-insensitive match with display name and e-mail address. Note that if a user has hidden their email address in their user profile, partial matches of the email address will not find the user. An exact match is required.  *  for groups, to find a case-sensitive match with group name.  For example, if the string *tin* is used, records with the display name *Tina*, email address *sarah@tinplatetraining.com*, and the group *accounting* would be returned.  Optionally, the search can be refined to:   *  the projects and issue types associated with a custom field, such as a user picker. The search can then be further refined to return only users and groups that have permission to view specific:           *  projects.      *  issue types.          If multiple projects or issue types are specified, they must be a subset of those enabled for the custom field or no results are returned. For example, if a field is enabled for projects A, B, and C then the search could be limited to projects B and C. However, if the search is limited to projects B and D, nothing is returned.  *  not return Connect app users and groups.  *  return groups that have a case-insensitive match with the query.  The primary use case for this resource is to populate a picker field suggestion list with users or groups. To this end, the returned object includes an &#x60;html&#x60; field for each list. This field highlights the matched query term in the item name with the HTML strong tag. Also, each list is wrapped in a response object that contains a header for use in a picker, specifically *Showing X of Y matching groups*.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** *Browse users and groups* [global permission](https://confluence.atlassian.com/x/yodKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.GroupAndUserPickerApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

GroupAndUserPickerApi apiInstance = new GroupAndUserPickerApi();
String query = "query_example"; // String | The search string.
Integer maxResults = 50; // Integer | The maximum number of items to return in each list.
Boolean showAvatar = false; // Boolean | Whether the user avatar should be returned. If an invalid value is provided, the default value is used.
String fieldId = "fieldId_example"; // String | The custom field ID of the field this request is for.
List<String> projectId = Arrays.asList("projectId_example"); // List<String> | The ID of a project that returned users and groups must have permission to view. To include multiple projects, provide an ampersand-separated list. For example, `projectId=10000&projectId=10001`. This parameter is only used when `fieldId` is present.
List<String> issueTypeId = Arrays.asList("issueTypeId_example"); // List<String> | The ID of an issue type that returned users and groups must have permission to view. To include multiple issue types, provide an ampersand-separated list. For example, `issueTypeId=10000&issueTypeId=10001`. Special values, such as `-1` (all standard issue types) and `-2` (all subtask issue types), are supported. This parameter is only used when `fieldId` is present.
String avatarSize = "xsmall"; // String | The size of the avatar to return. If an invalid value is provided, the default value is used.
Boolean caseInsensitive = false; // Boolean | Whether the search for groups should be case insensitive.
Boolean excludeConnectAddons = false; // Boolean | Whether Connect app users and groups should be excluded from the search results. If an invalid value is provided, the default value is used.
try {
    FoundUsersAndGroups result = apiInstance.findUsersAndGroups(query, maxResults, showAvatar, fieldId, projectId, issueTypeId, avatarSize, caseInsensitive, excludeConnectAddons);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GroupAndUserPickerApi#findUsersAndGroups");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **query** | **String**| The search string. |
 **maxResults** | **Integer**| The maximum number of items to return in each list. | [optional] [default to 50]
 **showAvatar** | **Boolean**| Whether the user avatar should be returned. If an invalid value is provided, the default value is used. | [optional] [default to false]
 **fieldId** | **String**| The custom field ID of the field this request is for. | [optional]
 **projectId** | [**List&lt;String&gt;**](String.md)| The ID of a project that returned users and groups must have permission to view. To include multiple projects, provide an ampersand-separated list. For example, &#x60;projectId&#x3D;10000&amp;projectId&#x3D;10001&#x60;. This parameter is only used when &#x60;fieldId&#x60; is present. | [optional]
 **issueTypeId** | [**List&lt;String&gt;**](String.md)| The ID of an issue type that returned users and groups must have permission to view. To include multiple issue types, provide an ampersand-separated list. For example, &#x60;issueTypeId&#x3D;10000&amp;issueTypeId&#x3D;10001&#x60;. Special values, such as &#x60;-1&#x60; (all standard issue types) and &#x60;-2&#x60; (all subtask issue types), are supported. This parameter is only used when &#x60;fieldId&#x60; is present. | [optional]
 **avatarSize** | **String**| The size of the avatar to return. If an invalid value is provided, the default value is used. | [optional] [default to xsmall] [enum: xsmall, xsmall@2x, xsmall@3x, small, small@2x, small@3x, medium, medium@2x, medium@3x, large, large@2x, large@3x, xlarge, xlarge@2x, xlarge@3x, xxlarge, xxlarge@2x, xxlarge@3x, xxxlarge, xxxlarge@2x, xxxlarge@3x]
 **caseInsensitive** | **Boolean**| Whether the search for groups should be case insensitive. | [optional] [default to false]
 **excludeConnectAddons** | **Boolean**| Whether Connect app users and groups should be excluded from the search results. If an invalid value is provided, the default value is used. | [optional] [default to false]

### Return type

[**FoundUsersAndGroups**](FoundUsersAndGroups.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

