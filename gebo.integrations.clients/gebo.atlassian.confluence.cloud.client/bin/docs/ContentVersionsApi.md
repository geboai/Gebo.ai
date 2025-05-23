# ContentVersionsApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteContentVersion**](ContentVersionsApi.md#deleteContentVersion) | **DELETE** /wiki/rest/api/content/{id}/version/{versionNumber} | Delete content version
[**getContentVersion**](ContentVersionsApi.md#getContentVersion) | **GET** /wiki/rest/api/content/{id}/version/{versionNumber} | Get content version
[**getContentVersions**](ContentVersionsApi.md#getContentVersions) | **GET** /wiki/rest/api/content/{id}/version | Get content versions
[**restoreContentVersion**](ContentVersionsApi.md#restoreContentVersion) | **POST** /wiki/rest/api/content/{id}/version | Restore content version

<a name="deleteContentVersion"></a>
# **deleteContentVersion**
> deleteContentVersion(id, versionNumber)

Delete content version

Delete a historical version. This does not delete the changes made to the content in that version, rather the changes for the deleted version are rolled up into the next version. Note, you cannot delete the current version.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to update the content.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentVersionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentVersionsApi apiInstance = new ContentVersionsApi();
String id = "id_example"; // String | The ID of the content that the version will be deleted from.
Integer versionNumber = 56; // Integer | The number of the version to be deleted. The version number starts from 1 up to current version.
try {
    apiInstance.deleteContentVersion(id, versionNumber);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentVersionsApi#deleteContentVersion");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The ID of the content that the version will be deleted from. |
 **versionNumber** | **Integer**| The number of the version to be deleted. The version number starts from 1 up to current version. |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="getContentVersion"></a>
# **getContentVersion**
> Version getContentVersion(id, versionNumber, expand)

Get content version

Deprecated, use [Confluence&#x27;s v2 API](https://developer.atlassian.com/cloud/confluence/rest/v2/intro/).  Returns a version for a piece of content.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content. If the content is a blog post, &#x27;View&#x27; permission for the space is required.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentVersionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentVersionsApi apiInstance = new ContentVersionsApi();
String id = "id_example"; // String | The ID of the content to be queried for its version.
Integer versionNumber = 56; // Integer | The number of the version to be retrieved.
List<String> expand = Arrays.asList("expand_example"); // List<String> | A multi-value parameter indicating which properties of the content to expand. By default, the `content` object is expanded.  - `collaborators` returns the users that collaborated on the version. - `content` returns the content for the version.
try {
    Version result = apiInstance.getContentVersion(id, versionNumber, expand);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentVersionsApi#getContentVersion");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The ID of the content to be queried for its version. |
 **versionNumber** | **Integer**| The number of the version to be retrieved. |
 **expand** | [**List&lt;String&gt;**](String.md)| A multi-value parameter indicating which properties of the content to expand. By default, the &#x60;content&#x60; object is expanded.  - &#x60;collaborators&#x60; returns the users that collaborated on the version. - &#x60;content&#x60; returns the content for the version. | [optional]

### Return type

[**Version**](Version.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getContentVersions"></a>
# **getContentVersions**
> VersionArray getContentVersions(id, start, limit, expand)

Get content versions

Deprecated, use [Confluence&#x27;s v2 API](https://developer.atlassian.com/cloud/confluence/rest/v2/intro/).  Returns the versions for a piece of content in descending order.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content. If the content is a blog post, &#x27;View&#x27; permission for the space is required.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentVersionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentVersionsApi apiInstance = new ContentVersionsApi();
String id = "id_example"; // String | The ID of the content to be queried for its versions.
Integer start = 0; // Integer | The starting index of the returned versions.
Integer limit = 200; // Integer | The maximum number of versions to return per page. Note, this may be restricted by fixed system limits.
List<String> expand = Arrays.asList("expand_example"); // List<String> | A multi-value parameter indicating which properties of the content to expand. By default, the `content` object is expanded.  - `collaborators` returns the users that collaborated on the version. - `content` returns the content for the version.
try {
    VersionArray result = apiInstance.getContentVersions(id, start, limit, expand);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentVersionsApi#getContentVersions");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The ID of the content to be queried for its versions. |
 **start** | **Integer**| The starting index of the returned versions. | [optional] [default to 0] [enum: 0]
 **limit** | **Integer**| The maximum number of versions to return per page. Note, this may be restricted by fixed system limits. | [optional] [default to 200] [enum: 0]
 **expand** | [**List&lt;String&gt;**](String.md)| A multi-value parameter indicating which properties of the content to expand. By default, the &#x60;content&#x60; object is expanded.  - &#x60;collaborators&#x60; returns the users that collaborated on the version. - &#x60;content&#x60; returns the content for the version. | [optional]

### Return type

[**VersionArray**](VersionArray.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="restoreContentVersion"></a>
# **restoreContentVersion**
> Version restoreContentVersion(body, id, expand)

Restore content version

Restores a historical version to be the latest version. That is, a new version is created with the content of the historical version.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to update the content.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentVersionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentVersionsApi apiInstance = new ContentVersionsApi();
VersionRestore body = new VersionRestore(); // VersionRestore | The content version to be restored.
String id = "id_example"; // String | The ID of the content for which the history will be restored.
List<String> expand = Arrays.asList("expand_example"); // List<String> | A multi-value parameter indicating which properties of the content to expand. By default, the `content` object is expanded.  - `collaborators` returns the users that collaborated on the version. - `content` returns the content for the version.
try {
    Version result = apiInstance.restoreContentVersion(body, id, expand);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentVersionsApi#restoreContentVersion");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**VersionRestore**](VersionRestore.md)| The content version to be restored. |
 **id** | **String**| The ID of the content for which the history will be restored. |
 **expand** | [**List&lt;String&gt;**](String.md)| A multi-value parameter indicating which properties of the content to expand. By default, the &#x60;content&#x60; object is expanded.  - &#x60;collaborators&#x60; returns the users that collaborated on the version. - &#x60;content&#x60; returns the content for the version. | [optional]

### Return type

[**Version**](Version.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

