# VersionApi

All URIs are relative to *https://{your-domain}/wiki/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAttachmentVersionDetails**](VersionApi.md#getAttachmentVersionDetails) | **GET** /attachments/{attachment-id}/versions/{version-number} | Get version details for attachment version
[**getAttachmentVersions**](VersionApi.md#getAttachmentVersions) | **GET** /attachments/{id}/versions | Get attachment versions
[**getBlogPostVersionDetails**](VersionApi.md#getBlogPostVersionDetails) | **GET** /blogposts/{blogpost-id}/versions/{version-number} | Get version details for blog post version
[**getBlogPostVersions**](VersionApi.md#getBlogPostVersions) | **GET** /blogposts/{id}/versions | Get blog post versions
[**getCustomContentVersionDetails**](VersionApi.md#getCustomContentVersionDetails) | **GET** /custom-content/{custom-content-id}/versions/{version-number} | Get version details for custom content version
[**getCustomContentVersions**](VersionApi.md#getCustomContentVersions) | **GET** /custom-content/{custom-content-id}/versions | Get custom content versions
[**getFooterCommentVersionDetails**](VersionApi.md#getFooterCommentVersionDetails) | **GET** /footer-comments/{id}/versions/{version-number} | Get version details for footer comment version
[**getFooterCommentVersions**](VersionApi.md#getFooterCommentVersions) | **GET** /footer-comments/{id}/versions | Get footer comment versions
[**getInlineCommentVersionDetails**](VersionApi.md#getInlineCommentVersionDetails) | **GET** /inline-comments/{id}/versions/{version-number} | Get version details for inline comment version
[**getInlineCommentVersions**](VersionApi.md#getInlineCommentVersions) | **GET** /inline-comments/{id}/versions | Get inline comment versions
[**getPageVersionDetails**](VersionApi.md#getPageVersionDetails) | **GET** /pages/{page-id}/versions/{version-number} | Get version details for page version
[**getPageVersions**](VersionApi.md#getPageVersions) | **GET** /pages/{id}/versions | Get page versions

<a name="getAttachmentVersionDetails"></a>
# **getAttachmentVersionDetails**
> DetailedVersion getAttachmentVersionDetails(attachmentId, versionNumber)

Get version details for attachment version

Retrieves version details for the specified attachment and version number.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the attachment.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.VersionApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

VersionApi apiInstance = new VersionApi();
String attachmentId = "attachmentId_example"; // String | The ID of the attachment for which version details should be returned.
Long versionNumber = 789L; // Long | The version number of the attachment to be returned.
try {
    DetailedVersion result = apiInstance.getAttachmentVersionDetails(attachmentId, versionNumber);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VersionApi#getAttachmentVersionDetails");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **attachmentId** | **String**| The ID of the attachment for which version details should be returned. |
 **versionNumber** | **Long**| The version number of the attachment to be returned. |

### Return type

[**DetailedVersion**](DetailedVersion.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAttachmentVersions"></a>
# **getAttachmentVersions**
> MultiEntityResultVersion getAttachmentVersions(id, cursor, limit, sort)

Get attachment versions

Returns the versions of specific attachment.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the attachment and its corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.VersionApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

VersionApi apiInstance = new VersionApi();
String id = "id_example"; // String | The ID of the attachment to be queried for its versions. If you don't know the attachment ID, use Get attachments and filter the results.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of versions per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
VersionSortOrder sort = new VersionSortOrder(); // VersionSortOrder | Used to sort the result by a particular field.
try {
    MultiEntityResultVersion result = apiInstance.getAttachmentVersions(id, cursor, limit, sort);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VersionApi#getAttachmentVersions");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The ID of the attachment to be queried for its versions. If you don&#x27;t know the attachment ID, use Get attachments and filter the results. |
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of versions per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]
 **sort** | [**VersionSortOrder**](.md)| Used to sort the result by a particular field. | [optional]

### Return type

[**MultiEntityResultVersion**](MultiEntityResultVersion.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getBlogPostVersionDetails"></a>
# **getBlogPostVersionDetails**
> DetailedVersion getBlogPostVersionDetails(blogpostId, versionNumber)

Get version details for blog post version

Retrieves version details for the specified blog post and version number.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the blog post.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.VersionApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

VersionApi apiInstance = new VersionApi();
Long blogpostId = 789L; // Long | The ID of the blog post for which version details should be returned.
Long versionNumber = 789L; // Long | The version number of the blog post to be returned.
try {
    DetailedVersion result = apiInstance.getBlogPostVersionDetails(blogpostId, versionNumber);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VersionApi#getBlogPostVersionDetails");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **blogpostId** | **Long**| The ID of the blog post for which version details should be returned. |
 **versionNumber** | **Long**| The version number of the blog post to be returned. |

### Return type

[**DetailedVersion**](DetailedVersion.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getBlogPostVersions"></a>
# **getBlogPostVersions**
> MultiEntityResultVersion1 getBlogPostVersions(id, bodyFormat, cursor, limit, sort)

Get blog post versions

Returns the versions of specific blog post.   **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the blog post and its corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.VersionApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

VersionApi apiInstance = new VersionApi();
Long id = 789L; // Long | The ID of the blog post to be queried for its versions. If you don't know the blog post ID, use Get blog posts and filter the results.
PrimaryBodyRepresentation bodyFormat = new PrimaryBodyRepresentation(); // PrimaryBodyRepresentation | The content format types to be returned in the `body` field of the response. If available, the representation will be available under a response field of the same name under the `body` field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of versions per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
VersionSortOrder sort = new VersionSortOrder(); // VersionSortOrder | Used to sort the result by a particular field.
try {
    MultiEntityResultVersion1 result = apiInstance.getBlogPostVersions(id, bodyFormat, cursor, limit, sort);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VersionApi#getBlogPostVersions");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the blog post to be queried for its versions. If you don&#x27;t know the blog post ID, use Get blog posts and filter the results. |
 **bodyFormat** | [**PrimaryBodyRepresentation**](.md)| The content format types to be returned in the &#x60;body&#x60; field of the response. If available, the representation will be available under a response field of the same name under the &#x60;body&#x60; field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of versions per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]
 **sort** | [**VersionSortOrder**](.md)| Used to sort the result by a particular field. | [optional]

### Return type

[**MultiEntityResultVersion1**](MultiEntityResultVersion1.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getCustomContentVersionDetails"></a>
# **getCustomContentVersionDetails**
> DetailedVersion getCustomContentVersionDetails(customContentId, versionNumber)

Get version details for custom content version

Retrieves version details for the specified custom content and version number.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the page.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.VersionApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

VersionApi apiInstance = new VersionApi();
Long customContentId = 789L; // Long | The ID of the custom content for which version details should be returned.
Long versionNumber = 789L; // Long | The version number of the custom content to be returned.
try {
    DetailedVersion result = apiInstance.getCustomContentVersionDetails(customContentId, versionNumber);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VersionApi#getCustomContentVersionDetails");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **customContentId** | **Long**| The ID of the custom content for which version details should be returned. |
 **versionNumber** | **Long**| The version number of the custom content to be returned. |

### Return type

[**DetailedVersion**](DetailedVersion.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getCustomContentVersions"></a>
# **getCustomContentVersions**
> MultiEntityResultVersion3 getCustomContentVersions(customContentId, bodyFormat, cursor, limit, sort)

Get custom content versions

Returns the versions of specific custom content.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the custom content and its corresponding page and space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.VersionApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

VersionApi apiInstance = new VersionApi();
Long customContentId = 789L; // Long | The ID of the custom content to be queried for its versions. If you don't know the custom content ID, use Get custom-content by type and filter the results.
CustomContentBodyRepresentation bodyFormat = new CustomContentBodyRepresentation(); // CustomContentBodyRepresentation | The content format types to be returned in the `body` field of the response. If available, the representation will be available under a response field of the same name under the `body` field.  Note: If the custom content body type is `storage`, the `storage` and `atlas_doc_format` body formats are able to be returned. If the custom content body type is `raw`, only the `raw` body format is able to be returned.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of versions per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
VersionSortOrder sort = new VersionSortOrder(); // VersionSortOrder | Used to sort the result by a particular field.
try {
    MultiEntityResultVersion3 result = apiInstance.getCustomContentVersions(customContentId, bodyFormat, cursor, limit, sort);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VersionApi#getCustomContentVersions");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **customContentId** | **Long**| The ID of the custom content to be queried for its versions. If you don&#x27;t know the custom content ID, use Get custom-content by type and filter the results. |
 **bodyFormat** | [**CustomContentBodyRepresentation**](.md)| The content format types to be returned in the &#x60;body&#x60; field of the response. If available, the representation will be available under a response field of the same name under the &#x60;body&#x60; field.  Note: If the custom content body type is &#x60;storage&#x60;, the &#x60;storage&#x60; and &#x60;atlas_doc_format&#x60; body formats are able to be returned. If the custom content body type is &#x60;raw&#x60;, only the &#x60;raw&#x60; body format is able to be returned. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of versions per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]
 **sort** | [**VersionSortOrder**](.md)| Used to sort the result by a particular field. | [optional]

### Return type

[**MultiEntityResultVersion3**](MultiEntityResultVersion3.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getFooterCommentVersionDetails"></a>
# **getFooterCommentVersionDetails**
> DetailedVersion getFooterCommentVersionDetails(id, versionNumber)

Get version details for footer comment version

Retrieves version details for the specified footer comment version.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content of the page or blog post and its corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.VersionApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

VersionApi apiInstance = new VersionApi();
Long id = 789L; // Long | The ID of the footer comment for which version details should be returned.
Long versionNumber = 789L; // Long | The version number of the footer comment to be returned.
try {
    DetailedVersion result = apiInstance.getFooterCommentVersionDetails(id, versionNumber);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VersionApi#getFooterCommentVersionDetails");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the footer comment for which version details should be returned. |
 **versionNumber** | **Long**| The version number of the footer comment to be returned. |

### Return type

[**DetailedVersion**](DetailedVersion.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getFooterCommentVersions"></a>
# **getFooterCommentVersions**
> MultiEntityResultVersion4 getFooterCommentVersions(id, bodyFormat, cursor, limit, sort)

Get footer comment versions

Retrieves the versions of the specified footer comment.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content of the page or blog post and its corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.VersionApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

VersionApi apiInstance = new VersionApi();
Long id = 789L; // Long | The ID of the footer comment for which versions should be returned
PrimaryBodyRepresentation bodyFormat = new PrimaryBodyRepresentation(); // PrimaryBodyRepresentation | The content format types to be returned in the `body` field of the response. If available, the representation will be available under a response field of the same name under the `body` field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of versions per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
VersionSortOrder sort = new VersionSortOrder(); // VersionSortOrder | Used to sort the result by a particular field.
try {
    MultiEntityResultVersion4 result = apiInstance.getFooterCommentVersions(id, bodyFormat, cursor, limit, sort);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VersionApi#getFooterCommentVersions");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the footer comment for which versions should be returned |
 **bodyFormat** | [**PrimaryBodyRepresentation**](.md)| The content format types to be returned in the &#x60;body&#x60; field of the response. If available, the representation will be available under a response field of the same name under the &#x60;body&#x60; field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of versions per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]
 **sort** | [**VersionSortOrder**](.md)| Used to sort the result by a particular field. | [optional]

### Return type

[**MultiEntityResultVersion4**](MultiEntityResultVersion4.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getInlineCommentVersionDetails"></a>
# **getInlineCommentVersionDetails**
> DetailedVersion getInlineCommentVersionDetails(id, versionNumber)

Get version details for inline comment version

Retrieves version details for the specified inline comment version.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content of the page or blog post and its corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.VersionApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

VersionApi apiInstance = new VersionApi();
Long id = 789L; // Long | The ID of the inline comment for which version details should be returned.
Long versionNumber = 789L; // Long | The version number of the inline comment to be returned.
try {
    DetailedVersion result = apiInstance.getInlineCommentVersionDetails(id, versionNumber);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VersionApi#getInlineCommentVersionDetails");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the inline comment for which version details should be returned. |
 **versionNumber** | **Long**| The version number of the inline comment to be returned. |

### Return type

[**DetailedVersion**](DetailedVersion.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getInlineCommentVersions"></a>
# **getInlineCommentVersions**
> MultiEntityResultVersion4 getInlineCommentVersions(id, bodyFormat, cursor, limit, sort)

Get inline comment versions

Retrieves the versions of the specified inline comment.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content of the page or blog post and its corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.VersionApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

VersionApi apiInstance = new VersionApi();
Long id = 789L; // Long | The ID of the inline comment for which versions should be returned
PrimaryBodyRepresentation bodyFormat = new PrimaryBodyRepresentation(); // PrimaryBodyRepresentation | The content format types to be returned in the `body` field of the response. If available, the representation will be available under a response field of the same name under the `body` field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of versions per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
VersionSortOrder sort = new VersionSortOrder(); // VersionSortOrder | Used to sort the result by a particular field.
try {
    MultiEntityResultVersion4 result = apiInstance.getInlineCommentVersions(id, bodyFormat, cursor, limit, sort);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VersionApi#getInlineCommentVersions");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the inline comment for which versions should be returned |
 **bodyFormat** | [**PrimaryBodyRepresentation**](.md)| The content format types to be returned in the &#x60;body&#x60; field of the response. If available, the representation will be available under a response field of the same name under the &#x60;body&#x60; field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of versions per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]
 **sort** | [**VersionSortOrder**](.md)| Used to sort the result by a particular field. | [optional]

### Return type

[**MultiEntityResultVersion4**](MultiEntityResultVersion4.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getPageVersionDetails"></a>
# **getPageVersionDetails**
> DetailedVersion getPageVersionDetails(pageId, versionNumber)

Get version details for page version

Retrieves version details for the specified page and version number.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the page.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.VersionApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

VersionApi apiInstance = new VersionApi();
Long pageId = 789L; // Long | The ID of the page for which version details should be returned.
Long versionNumber = 789L; // Long | The version number of the page to be returned.
try {
    DetailedVersion result = apiInstance.getPageVersionDetails(pageId, versionNumber);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VersionApi#getPageVersionDetails");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **pageId** | **Long**| The ID of the page for which version details should be returned. |
 **versionNumber** | **Long**| The version number of the page to be returned. |

### Return type

[**DetailedVersion**](DetailedVersion.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getPageVersions"></a>
# **getPageVersions**
> MultiEntityResultVersion2 getPageVersions(id, bodyFormat, cursor, limit, sort)

Get page versions

Returns the versions of specific page.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the page and its corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.VersionApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

VersionApi apiInstance = new VersionApi();
Long id = 789L; // Long | The ID of the page to be queried for its versions. If you don't know the page ID, use Get pages and filter the results.
PrimaryBodyRepresentation bodyFormat = new PrimaryBodyRepresentation(); // PrimaryBodyRepresentation | The content format types to be returned in the `body` field of the response. If available, the representation will be available under a response field of the same name under the `body` field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of versions per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
VersionSortOrder sort = new VersionSortOrder(); // VersionSortOrder | Used to sort the result by a particular field.
try {
    MultiEntityResultVersion2 result = apiInstance.getPageVersions(id, bodyFormat, cursor, limit, sort);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VersionApi#getPageVersions");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the page to be queried for its versions. If you don&#x27;t know the page ID, use Get pages and filter the results. |
 **bodyFormat** | [**PrimaryBodyRepresentation**](.md)| The content format types to be returned in the &#x60;body&#x60; field of the response. If available, the representation will be available under a response field of the same name under the &#x60;body&#x60; field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of versions per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]
 **sort** | [**VersionSortOrder**](.md)| Used to sort the result by a particular field. | [optional]

### Return type

[**MultiEntityResultVersion2**](MultiEntityResultVersion2.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

