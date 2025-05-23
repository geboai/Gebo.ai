# ContentPermissionsApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**checkContentPermission**](ContentPermissionsApi.md#checkContentPermission) | **POST** /wiki/rest/api/content/{id}/permission/check | Check content permissions

<a name="checkContentPermission"></a>
# **checkContentPermission**
> PermissionCheckResponse checkContentPermission(body, id)

Check content permissions

Check if a user or a group can perform an operation to the specified content. The &#x60;operation&#x60; to check must be provided. The user’s account ID or the ID of the group can be provided in the &#x60;subject&#x60; to check permissions against a specified user or group. The following permission checks are done to make sure that the user or group has the proper access:  - site permissions - space permissions - content restrictions  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to access the Confluence site (&#x27;Can use&#x27; global permission) if checking permission for self, otherwise &#x27;Confluence Administrator&#x27; global permission is required.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPermissionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPermissionsApi apiInstance = new ContentPermissionsApi();
ContentPermissionRequest body = new ContentPermissionRequest(); // ContentPermissionRequest | The content permission request.
String id = "id_example"; // String | The ID of the content to check permissions against.
try {
    PermissionCheckResponse result = apiInstance.checkContentPermission(body, id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPermissionsApi#checkContentPermission");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ContentPermissionRequest**](ContentPermissionRequest.md)| The content permission request. |
 **id** | **String**| The ID of the content to check permissions against. |

### Return type

[**PermissionCheckResponse**](PermissionCheckResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

