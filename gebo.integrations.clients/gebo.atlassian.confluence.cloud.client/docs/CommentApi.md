# CommentApi

All URIs are relative to *https://{your-domain}/wiki/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createFooterComment**](CommentApi.md#createFooterComment) | **POST** /footer-comments | Create footer comment
[**createInlineComment**](CommentApi.md#createInlineComment) | **POST** /inline-comments | Create inline comment
[**deleteFooterComment**](CommentApi.md#deleteFooterComment) | **DELETE** /footer-comments/{comment-id} | Delete footer comment
[**deleteInlineComment**](CommentApi.md#deleteInlineComment) | **DELETE** /inline-comments/{comment-id} | Delete inline comment
[**getAttachmentComments**](CommentApi.md#getAttachmentComments) | **GET** /attachments/{id}/footer-comments | Get attachment comments
[**getBlogPostFooterComments**](CommentApi.md#getBlogPostFooterComments) | **GET** /blogposts/{id}/footer-comments | Get footer comments for blog post
[**getBlogPostInlineComments**](CommentApi.md#getBlogPostInlineComments) | **GET** /blogposts/{id}/inline-comments | Get inline comments for blog post
[**getCustomContentComments**](CommentApi.md#getCustomContentComments) | **GET** /custom-content/{id}/footer-comments | Get custom content comments
[**getFooterCommentById**](CommentApi.md#getFooterCommentById) | **GET** /footer-comments/{comment-id} | Get footer comment by id
[**getFooterCommentChildren**](CommentApi.md#getFooterCommentChildren) | **GET** /footer-comments/{id}/children | Get children footer comments
[**getFooterComments**](CommentApi.md#getFooterComments) | **GET** /footer-comments | Get footer comments
[**getInlineCommentById**](CommentApi.md#getInlineCommentById) | **GET** /inline-comments/{comment-id} | Get inline comment by id
[**getInlineCommentChildren**](CommentApi.md#getInlineCommentChildren) | **GET** /inline-comments/{id}/children | Get children inline comments
[**getInlineComments**](CommentApi.md#getInlineComments) | **GET** /inline-comments | Get inline comments
[**getPageFooterComments**](CommentApi.md#getPageFooterComments) | **GET** /pages/{id}/footer-comments | Get footer comments for page
[**getPageInlineComments**](CommentApi.md#getPageInlineComments) | **GET** /pages/{id}/inline-comments | Get inline comments for page
[**updateFooterComment**](CommentApi.md#updateFooterComment) | **PUT** /footer-comments/{comment-id} | Update footer comment
[**updateInlineComment**](CommentApi.md#updateInlineComment) | **PUT** /inline-comments/{comment-id} | Update inline comment

<a name="createFooterComment"></a>
# **createFooterComment**
> InlineResponse2012 createFooterComment(body)

Create footer comment

Create a footer comment.  The footer comment can be made against several locations:  - at the top level (specifying pageId or blogPostId in the request body) - as a reply (specifying parentCommentId in the request body) - against an attachment (note: this is different than the comments added via the attachment properties page on the UI, which are referred to as version comments) - against a custom content  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content of the page or blogpost and its corresponding space. Permission to create comments in the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.CommentApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

CommentApi apiInstance = new CommentApi();
CreateFooterCommentModel body = new CreateFooterCommentModel(); // CreateFooterCommentModel | The footer comment to be created
try {
    InlineResponse2012 result = apiInstance.createFooterComment(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CommentApi#createFooterComment");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**CreateFooterCommentModel**](CreateFooterCommentModel.md)| The footer comment to be created |

### Return type

[**InlineResponse2012**](InlineResponse2012.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createInlineComment"></a>
# **createInlineComment**
> InlineResponse2013 createInlineComment(body)

Create inline comment

Create an inline comment. This can be at the top level (specifying pageId or blogPostId in the request body) or as a reply (specifying parentCommentId in the request body). Note the inlineCommentProperties object in the request body is used to select the text the inline comment should be tied to. This is what determines the text  highlighting when viewing a page in Confluence.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content of the page or blogpost and its corresponding space. Permission to create comments in the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.CommentApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

CommentApi apiInstance = new CommentApi();
CreateInlineCommentModel body = new CreateInlineCommentModel(); // CreateInlineCommentModel | The inline comment to be created
try {
    InlineResponse2013 result = apiInstance.createInlineComment(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CommentApi#createInlineComment");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**CreateInlineCommentModel**](CreateInlineCommentModel.md)| The inline comment to be created |

### Return type

[**InlineResponse2013**](InlineResponse2013.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deleteFooterComment"></a>
# **deleteFooterComment**
> deleteFooterComment(commentId)

Delete footer comment

Deletes a footer comment. This is a permanent deletion and cannot be reverted.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content of the page or blogpost and its corresponding space. Permission to delete comments in the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.CommentApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

CommentApi apiInstance = new CommentApi();
Long commentId = 789L; // Long | The ID of the comment to be retrieved.
try {
    apiInstance.deleteFooterComment(commentId);
} catch (ApiException e) {
    System.err.println("Exception when calling CommentApi#deleteFooterComment");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **commentId** | **Long**| The ID of the comment to be retrieved. |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="deleteInlineComment"></a>
# **deleteInlineComment**
> deleteInlineComment(commentId)

Delete inline comment

Deletes an inline comment. This is a permanent deletion and cannot be reverted.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content of the page or blogpost and its corresponding space. Permission to delete comments in the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.CommentApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

CommentApi apiInstance = new CommentApi();
Long commentId = 789L; // Long | The ID of the comment to be deleted.
try {
    apiInstance.deleteInlineComment(commentId);
} catch (ApiException e) {
    System.err.println("Exception when calling CommentApi#deleteInlineComment");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **commentId** | **Long**| The ID of the comment to be deleted. |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="getAttachmentComments"></a>
# **getAttachmentComments**
> MultiEntityResultAttachmentCommentModel getAttachmentComments(id, bodyFormat, cursor, limit, sort, version)

Get attachment comments

Returns the comments of the specific attachment. The number of results is limited by the &#x60;limit&#x60; parameter and additional results (if available) will be available through the &#x60;next&#x60; URL present in the &#x60;Link&#x60; response header.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the attachment and its corresponding containers.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.CommentApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

CommentApi apiInstance = new CommentApi();
String id = "id_example"; // String | The ID of the attachment for which comments should be returned.
PrimaryBodyRepresentation bodyFormat = new PrimaryBodyRepresentation(); // PrimaryBodyRepresentation | The content format type to be returned in the `body` field of the response. If available, the representation will be available under a response field of the same name under the `body` field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of comments per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
CommentSortOrder sort = new CommentSortOrder(); // CommentSortOrder | Used to sort the result by a particular field.
Long version = 789L; // Long | Version number of the attachment to retrieve comments for. If no version provided, retrieves comments for the latest version.
try {
    MultiEntityResultAttachmentCommentModel result = apiInstance.getAttachmentComments(id, bodyFormat, cursor, limit, sort, version);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CommentApi#getAttachmentComments");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The ID of the attachment for which comments should be returned. |
 **bodyFormat** | [**PrimaryBodyRepresentation**](.md)| The content format type to be returned in the &#x60;body&#x60; field of the response. If available, the representation will be available under a response field of the same name under the &#x60;body&#x60; field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of comments per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]
 **sort** | [**CommentSortOrder**](.md)| Used to sort the result by a particular field. | [optional]
 **version** | **Long**| Version number of the attachment to retrieve comments for. If no version provided, retrieves comments for the latest version. | [optional]

### Return type

[**MultiEntityResultAttachmentCommentModel**](MultiEntityResultAttachmentCommentModel.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getBlogPostFooterComments"></a>
# **getBlogPostFooterComments**
> MultiEntityResultBlogPostCommentModel getBlogPostFooterComments(id, bodyFormat, status, sort, cursor, limit)

Get footer comments for blog post

Returns the root footer comments of specific blog post. The number of results is limited by the &#x60;limit&#x60; parameter and additional results (if available) will be available through the &#x60;next&#x60; URL present in the &#x60;Link&#x60; response header.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content of the blog post and its corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.CommentApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

CommentApi apiInstance = new CommentApi();
Long id = 789L; // Long | The ID of the blog post for which footer comments should be returned.
PrimaryBodyRepresentation bodyFormat = new PrimaryBodyRepresentation(); // PrimaryBodyRepresentation | The content format type to be returned in the `body` field of the response. If available, the representation will be available under a response field of the same name under the `body` field.
List<String> status = Arrays.asList("status_example"); // List<String> | Filter the footer comment being retrieved by its status.
CommentSortOrder sort = new CommentSortOrder(); // CommentSortOrder | Used to sort the result by a particular field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of footer comments per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultBlogPostCommentModel result = apiInstance.getBlogPostFooterComments(id, bodyFormat, status, sort, cursor, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CommentApi#getBlogPostFooterComments");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the blog post for which footer comments should be returned. |
 **bodyFormat** | [**PrimaryBodyRepresentation**](.md)| The content format type to be returned in the &#x60;body&#x60; field of the response. If available, the representation will be available under a response field of the same name under the &#x60;body&#x60; field. | [optional]
 **status** | [**List&lt;String&gt;**](String.md)| Filter the footer comment being retrieved by its status. | [optional] [enum: current, deleted, trashed, historical, draft]
 **sort** | [**CommentSortOrder**](.md)| Used to sort the result by a particular field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of footer comments per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultBlogPostCommentModel**](MultiEntityResultBlogPostCommentModel.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getBlogPostInlineComments"></a>
# **getBlogPostInlineComments**
> MultiEntityResultBlogPostInlineCommentModel getBlogPostInlineComments(id, bodyFormat, status, resolutionStatus, sort, cursor, limit)

Get inline comments for blog post

Returns the root inline comments of specific blog post. The number of results is limited by the &#x60;limit&#x60; parameter and additional results (if available) will be available through the &#x60;next&#x60; URL present in the &#x60;Link&#x60; response header.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content of the blog post and its corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.CommentApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

CommentApi apiInstance = new CommentApi();
Long id = 789L; // Long | The ID of the blog post for which inline comments should be returned.
PrimaryBodyRepresentation bodyFormat = new PrimaryBodyRepresentation(); // PrimaryBodyRepresentation | The content format type to be returned in the `body` field of the response. If available, the representation will be available under a response field of the same name under the `body` field.
List<String> status = Arrays.asList("status_example"); // List<String> | Filter the inline comment being retrieved by its status.
List<String> resolutionStatus = Arrays.asList("resolutionStatus_example"); // List<String> | Filter the inline comment being retrieved by its resolution status.
CommentSortOrder sort = new CommentSortOrder(); // CommentSortOrder | Used to sort the result by a particular field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of inline comments per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultBlogPostInlineCommentModel result = apiInstance.getBlogPostInlineComments(id, bodyFormat, status, resolutionStatus, sort, cursor, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CommentApi#getBlogPostInlineComments");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the blog post for which inline comments should be returned. |
 **bodyFormat** | [**PrimaryBodyRepresentation**](.md)| The content format type to be returned in the &#x60;body&#x60; field of the response. If available, the representation will be available under a response field of the same name under the &#x60;body&#x60; field. | [optional]
 **status** | [**List&lt;String&gt;**](String.md)| Filter the inline comment being retrieved by its status. | [optional] [enum: current, deleted, trashed, historical, draft]
 **resolutionStatus** | [**List&lt;String&gt;**](String.md)| Filter the inline comment being retrieved by its resolution status. | [optional] [enum: resolved, open, dangling, reopened]
 **sort** | [**CommentSortOrder**](.md)| Used to sort the result by a particular field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of inline comments per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultBlogPostInlineCommentModel**](MultiEntityResultBlogPostInlineCommentModel.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getCustomContentComments"></a>
# **getCustomContentComments**
> MultiEntityResultCustomContentCommentModel getCustomContentComments(id, bodyFormat, cursor, limit, sort)

Get custom content comments

Returns the comments of the specific custom content. The number of results is limited by the &#x60;limit&#x60; parameter and additional results (if available) will be available through the &#x60;next&#x60; URL present in the &#x60;Link&#x60; response header.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the custom content and its corresponding containers.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.CommentApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

CommentApi apiInstance = new CommentApi();
Long id = 789L; // Long | The ID of the custom content for which comments should be returned.
PrimaryBodyRepresentation bodyFormat = new PrimaryBodyRepresentation(); // PrimaryBodyRepresentation | The content format type to be returned in the `body` field of the response. If available, the representation will be available under a response field of the same name under the `body` field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of comments per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
CommentSortOrder sort = new CommentSortOrder(); // CommentSortOrder | Used to sort the result by a particular field.
try {
    MultiEntityResultCustomContentCommentModel result = apiInstance.getCustomContentComments(id, bodyFormat, cursor, limit, sort);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CommentApi#getCustomContentComments");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the custom content for which comments should be returned. |
 **bodyFormat** | [**PrimaryBodyRepresentation**](.md)| The content format type to be returned in the &#x60;body&#x60; field of the response. If available, the representation will be available under a response field of the same name under the &#x60;body&#x60; field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of comments per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]
 **sort** | [**CommentSortOrder**](.md)| Used to sort the result by a particular field. | [optional]

### Return type

[**MultiEntityResultCustomContentCommentModel**](MultiEntityResultCustomContentCommentModel.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getFooterCommentById"></a>
# **getFooterCommentById**
> InlineResponse2012 getFooterCommentById(commentId, bodyFormat, version, includeProperties, includeOperations, includeLikes, includeVersions, includeVersion)

Get footer comment by id

Retrieves a footer comment by id  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content of the container and its corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.CommentApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

CommentApi apiInstance = new CommentApi();
Long commentId = 789L; // Long | The ID of the comment to be retrieved.
PrimaryBodyRepresentationSingle bodyFormat = new PrimaryBodyRepresentationSingle(); // PrimaryBodyRepresentationSingle | The content format type to be returned in the `body` field of the response. If available, the representation will be available under a response field of the same name under the `body` field.
Integer version = 56; // Integer | Allows you to retrieve a previously published version. Specify the previous version's number to retrieve its details.
Boolean includeProperties = false; // Boolean | Includes content properties associated with this footer comment in the response. The number of results will be limited to 50 and sorted in the default sort order.  A `meta` and `_links` property will be present to indicate if more results are available and a link to retrieve the rest of the results.
Boolean includeOperations = false; // Boolean | Includes operations associated with this footer comment in the response. The number of results will be limited to 50 and sorted in the default sort order.  A `meta` and `_links` property will be present to indicate if more results are available and a link to retrieve the rest of the results.
Boolean includeLikes = false; // Boolean | Includes likes associated with this footer comment in the response. The number of results will be limited to 50 and sorted in the default sort order.  A `meta` and `_links` property will be present to indicate if more results are available and a link to retrieve the rest of the results.
Boolean includeVersions = false; // Boolean | Includes versions associated with this footer comment in the response. The number of results will be limited to 50 and sorted in the default sort order.  A `meta` and `_links` property will be present to indicate if more results are available and a link to retrieve the rest of the results.
Boolean includeVersion = true; // Boolean | Includes the current version associated with this footer comment in the response. By default this is included and can be omitted by setting the value to `false`.
try {
    InlineResponse2012 result = apiInstance.getFooterCommentById(commentId, bodyFormat, version, includeProperties, includeOperations, includeLikes, includeVersions, includeVersion);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CommentApi#getFooterCommentById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **commentId** | **Long**| The ID of the comment to be retrieved. |
 **bodyFormat** | [**PrimaryBodyRepresentationSingle**](.md)| The content format type to be returned in the &#x60;body&#x60; field of the response. If available, the representation will be available under a response field of the same name under the &#x60;body&#x60; field. | [optional]
 **version** | **Integer**| Allows you to retrieve a previously published version. Specify the previous version&#x27;s number to retrieve its details. | [optional]
 **includeProperties** | **Boolean**| Includes content properties associated with this footer comment in the response. The number of results will be limited to 50 and sorted in the default sort order.  A &#x60;meta&#x60; and &#x60;_links&#x60; property will be present to indicate if more results are available and a link to retrieve the rest of the results. | [optional] [default to false]
 **includeOperations** | **Boolean**| Includes operations associated with this footer comment in the response. The number of results will be limited to 50 and sorted in the default sort order.  A &#x60;meta&#x60; and &#x60;_links&#x60; property will be present to indicate if more results are available and a link to retrieve the rest of the results. | [optional] [default to false]
 **includeLikes** | **Boolean**| Includes likes associated with this footer comment in the response. The number of results will be limited to 50 and sorted in the default sort order.  A &#x60;meta&#x60; and &#x60;_links&#x60; property will be present to indicate if more results are available and a link to retrieve the rest of the results. | [optional] [default to false]
 **includeVersions** | **Boolean**| Includes versions associated with this footer comment in the response. The number of results will be limited to 50 and sorted in the default sort order.  A &#x60;meta&#x60; and &#x60;_links&#x60; property will be present to indicate if more results are available and a link to retrieve the rest of the results. | [optional] [default to false]
 **includeVersion** | **Boolean**| Includes the current version associated with this footer comment in the response. By default this is included and can be omitted by setting the value to &#x60;false&#x60;. | [optional] [default to true]

### Return type

[**InlineResponse2012**](InlineResponse2012.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getFooterCommentChildren"></a>
# **getFooterCommentChildren**
> MultiEntityResultChildrenCommentModel getFooterCommentChildren(id, bodyFormat, sort, cursor, limit)

Get children footer comments

Returns the children footer comments of specific comment. The number of results is limited by the &#x60;limit&#x60; parameter and additional results (if available) will be available through the &#x60;next&#x60; URL present in the &#x60;Link&#x60; response header.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content of the page and its corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.CommentApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

CommentApi apiInstance = new CommentApi();
Long id = 789L; // Long | The ID of the parent comment for which footer comment children should be returned.
PrimaryBodyRepresentation bodyFormat = new PrimaryBodyRepresentation(); // PrimaryBodyRepresentation | The content format type to be returned in the `body` field of the response. If available, the representation will be available under a response field of the same name under the `body` field.
CommentSortOrder sort = new CommentSortOrder(); // CommentSortOrder | Used to sort the result by a particular field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of footer comments per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultChildrenCommentModel result = apiInstance.getFooterCommentChildren(id, bodyFormat, sort, cursor, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CommentApi#getFooterCommentChildren");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the parent comment for which footer comment children should be returned. |
 **bodyFormat** | [**PrimaryBodyRepresentation**](.md)| The content format type to be returned in the &#x60;body&#x60; field of the response. If available, the representation will be available under a response field of the same name under the &#x60;body&#x60; field. | [optional]
 **sort** | [**CommentSortOrder**](.md)| Used to sort the result by a particular field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of footer comments per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultChildrenCommentModel**](MultiEntityResultChildrenCommentModel.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getFooterComments"></a>
# **getFooterComments**
> MultiEntityResultFooterCommentModel getFooterComments(bodyFormat, sort, cursor, limit)

Get footer comments

Returns all footer comments. The number of results is limited by the &#x60;limit&#x60; parameter and additional results (if available) will be available through the &#x60;next&#x60; URL present in the &#x60;Link&#x60; response header.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content of the container and its corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.CommentApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

CommentApi apiInstance = new CommentApi();
PrimaryBodyRepresentation bodyFormat = new PrimaryBodyRepresentation(); // PrimaryBodyRepresentation | The content format type to be returned in the `body` field of the response. If available, the representation will be available under a response field of the same name under the `body` field.
CommentSortOrder sort = new CommentSortOrder(); // CommentSortOrder | Used to sort the result by a particular field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of footer comments per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultFooterCommentModel result = apiInstance.getFooterComments(bodyFormat, sort, cursor, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CommentApi#getFooterComments");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **bodyFormat** | [**PrimaryBodyRepresentation**](.md)| The content format type to be returned in the &#x60;body&#x60; field of the response. If available, the representation will be available under a response field of the same name under the &#x60;body&#x60; field. | [optional]
 **sort** | [**CommentSortOrder**](.md)| Used to sort the result by a particular field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of footer comments per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultFooterCommentModel**](MultiEntityResultFooterCommentModel.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getInlineCommentById"></a>
# **getInlineCommentById**
> InlineResponse2013 getInlineCommentById(commentId, bodyFormat, version, includeProperties, includeOperations, includeLikes, includeVersions, includeVersion)

Get inline comment by id

Retrieves an inline comment by id  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content of the page or blogpost and its corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.CommentApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

CommentApi apiInstance = new CommentApi();
Long commentId = 789L; // Long | The ID of the comment to be retrieved.
PrimaryBodyRepresentationSingle bodyFormat = new PrimaryBodyRepresentationSingle(); // PrimaryBodyRepresentationSingle | The content format type to be returned in the `body` field of the response. If available, the representation will be available under a response field of the same name under the `body` field.
Integer version = 56; // Integer | Allows you to retrieve a previously published version. Specify the previous version's number to retrieve its details.
Boolean includeProperties = false; // Boolean | Includes content properties associated with this inline comment in the response. The number of results will be limited to 50 and sorted in the default sort order.  A `meta` and `_links` property will be present to indicate if more results are available and a link to retrieve the rest of the results.
Boolean includeOperations = false; // Boolean | Includes operations associated with this inline comment in the response. The number of results will be limited to 50 and sorted in the default sort order.  A `meta` and `_links` property will be present to indicate if more results are available and a link to retrieve the rest of the results.
Boolean includeLikes = false; // Boolean | Includes likes associated with this inline comment in the response. The number of results will be limited to 50 and sorted in the default sort order.  A `meta` and `_links` property will be present to indicate if more results are available and a link to retrieve the rest of the results.
Boolean includeVersions = false; // Boolean | Includes versions associated with this inline comment in the response. The number of results will be limited to 50 and sorted in the default sort order.  A `meta` and `_links` property will be present to indicate if more results are available and a link to retrieve the rest of the results.
Boolean includeVersion = true; // Boolean | Includes the current version associated with this inline comment in the response. By default this is included and can be omitted by setting the value to `false`.
try {
    InlineResponse2013 result = apiInstance.getInlineCommentById(commentId, bodyFormat, version, includeProperties, includeOperations, includeLikes, includeVersions, includeVersion);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CommentApi#getInlineCommentById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **commentId** | **Long**| The ID of the comment to be retrieved. |
 **bodyFormat** | [**PrimaryBodyRepresentationSingle**](.md)| The content format type to be returned in the &#x60;body&#x60; field of the response. If available, the representation will be available under a response field of the same name under the &#x60;body&#x60; field. | [optional]
 **version** | **Integer**| Allows you to retrieve a previously published version. Specify the previous version&#x27;s number to retrieve its details. | [optional]
 **includeProperties** | **Boolean**| Includes content properties associated with this inline comment in the response. The number of results will be limited to 50 and sorted in the default sort order.  A &#x60;meta&#x60; and &#x60;_links&#x60; property will be present to indicate if more results are available and a link to retrieve the rest of the results. | [optional] [default to false]
 **includeOperations** | **Boolean**| Includes operations associated with this inline comment in the response. The number of results will be limited to 50 and sorted in the default sort order.  A &#x60;meta&#x60; and &#x60;_links&#x60; property will be present to indicate if more results are available and a link to retrieve the rest of the results. | [optional] [default to false]
 **includeLikes** | **Boolean**| Includes likes associated with this inline comment in the response. The number of results will be limited to 50 and sorted in the default sort order.  A &#x60;meta&#x60; and &#x60;_links&#x60; property will be present to indicate if more results are available and a link to retrieve the rest of the results. | [optional] [default to false]
 **includeVersions** | **Boolean**| Includes versions associated with this inline comment in the response. The number of results will be limited to 50 and sorted in the default sort order.  A &#x60;meta&#x60; and &#x60;_links&#x60; property will be present to indicate if more results are available and a link to retrieve the rest of the results. | [optional] [default to false]
 **includeVersion** | **Boolean**| Includes the current version associated with this inline comment in the response. By default this is included and can be omitted by setting the value to &#x60;false&#x60;. | [optional] [default to true]

### Return type

[**InlineResponse2013**](InlineResponse2013.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getInlineCommentChildren"></a>
# **getInlineCommentChildren**
> MultiEntityResultInlineCommentChildrenModel getInlineCommentChildren(id, bodyFormat, sort, cursor, limit)

Get children inline comments

Returns the children inline comments of specific comment. The number of results is limited by the &#x60;limit&#x60; parameter and additional results (if available) will be available through the &#x60;next&#x60; URL present in the &#x60;Link&#x60; response header.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content of the page and its corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.CommentApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

CommentApi apiInstance = new CommentApi();
Long id = 789L; // Long | The ID of the parent comment for which inline comment children should be returned.
PrimaryBodyRepresentation bodyFormat = new PrimaryBodyRepresentation(); // PrimaryBodyRepresentation | The content format type to be returned in the `body` field of the response. If available, the representation will be available under a response field of the same name under the `body` field.
CommentSortOrder sort = new CommentSortOrder(); // CommentSortOrder | Used to sort the result by a particular field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of footer comments per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultInlineCommentChildrenModel result = apiInstance.getInlineCommentChildren(id, bodyFormat, sort, cursor, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CommentApi#getInlineCommentChildren");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the parent comment for which inline comment children should be returned. |
 **bodyFormat** | [**PrimaryBodyRepresentation**](.md)| The content format type to be returned in the &#x60;body&#x60; field of the response. If available, the representation will be available under a response field of the same name under the &#x60;body&#x60; field. | [optional]
 **sort** | [**CommentSortOrder**](.md)| Used to sort the result by a particular field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of footer comments per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultInlineCommentChildrenModel**](MultiEntityResultInlineCommentChildrenModel.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getInlineComments"></a>
# **getInlineComments**
> MultiEntityResultInlineCommentModel getInlineComments(bodyFormat, sort, cursor, limit)

Get inline comments

Returns all inline comments. The number of results is limited by the &#x60;limit&#x60; parameter and additional results (if available) will be available through the &#x60;next&#x60; URL present in the &#x60;Link&#x60; response header.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content of the page and its corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.CommentApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

CommentApi apiInstance = new CommentApi();
PrimaryBodyRepresentation bodyFormat = new PrimaryBodyRepresentation(); // PrimaryBodyRepresentation | The content format type to be returned in the `body` field of the response. If available, the representation will be available under a response field of the same name under the `body` field.
CommentSortOrder sort = new CommentSortOrder(); // CommentSortOrder | Used to sort the result by a particular field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of footer comments per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultInlineCommentModel result = apiInstance.getInlineComments(bodyFormat, sort, cursor, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CommentApi#getInlineComments");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **bodyFormat** | [**PrimaryBodyRepresentation**](.md)| The content format type to be returned in the &#x60;body&#x60; field of the response. If available, the representation will be available under a response field of the same name under the &#x60;body&#x60; field. | [optional]
 **sort** | [**CommentSortOrder**](.md)| Used to sort the result by a particular field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of footer comments per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultInlineCommentModel**](MultiEntityResultInlineCommentModel.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getPageFooterComments"></a>
# **getPageFooterComments**
> MultiEntityResultPageCommentModel getPageFooterComments(id, bodyFormat, status, sort, cursor, limit)

Get footer comments for page

Returns the root footer comments of specific page. The number of results is limited by the &#x60;limit&#x60; parameter and additional results (if available) will be available through the &#x60;next&#x60; URL present in the &#x60;Link&#x60; response header.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content of the page and its corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.CommentApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

CommentApi apiInstance = new CommentApi();
Long id = 789L; // Long | The ID of the page for which footer comments should be returned.
PrimaryBodyRepresentation bodyFormat = new PrimaryBodyRepresentation(); // PrimaryBodyRepresentation | The content format type to be returned in the `body` field of the response. If available, the representation will be available under a response field of the same name under the `body` field.
List<String> status = Arrays.asList("status_example"); // List<String> | Filter the footer comment being retrieved by its status.
CommentSortOrder sort = new CommentSortOrder(); // CommentSortOrder | Used to sort the result by a particular field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of footer comments per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultPageCommentModel result = apiInstance.getPageFooterComments(id, bodyFormat, status, sort, cursor, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CommentApi#getPageFooterComments");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the page for which footer comments should be returned. |
 **bodyFormat** | [**PrimaryBodyRepresentation**](.md)| The content format type to be returned in the &#x60;body&#x60; field of the response. If available, the representation will be available under a response field of the same name under the &#x60;body&#x60; field. | [optional]
 **status** | [**List&lt;String&gt;**](String.md)| Filter the footer comment being retrieved by its status. | [optional] [enum: current, archived, trashed, deleted, historical, draft]
 **sort** | [**CommentSortOrder**](.md)| Used to sort the result by a particular field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of footer comments per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultPageCommentModel**](MultiEntityResultPageCommentModel.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getPageInlineComments"></a>
# **getPageInlineComments**
> MultiEntityResultPageInlineCommentModel getPageInlineComments(id, bodyFormat, status, resolutionStatus, sort, cursor, limit)

Get inline comments for page

Returns the root inline comments of specific page. The number of results is limited by the &#x60;limit&#x60; parameter and additional results (if available) will be available through the &#x60;next&#x60; URL present in the &#x60;Link&#x60; response header.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content of the page and its corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.CommentApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

CommentApi apiInstance = new CommentApi();
Long id = 789L; // Long | The ID of the page for which inline comments should be returned.
PrimaryBodyRepresentation bodyFormat = new PrimaryBodyRepresentation(); // PrimaryBodyRepresentation | The content format type to be returned in the `body` field of the response. If available, the representation will be available under a response field of the same name under the `body` field.
List<String> status = Arrays.asList("status_example"); // List<String> | Filter the inline comment being retrieved by its status.
List<String> resolutionStatus = Arrays.asList("resolutionStatus_example"); // List<String> | Filter the inline comment being retrieved by its resolution status.
CommentSortOrder sort = new CommentSortOrder(); // CommentSortOrder | Used to sort the result by a particular field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of inline comments per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultPageInlineCommentModel result = apiInstance.getPageInlineComments(id, bodyFormat, status, resolutionStatus, sort, cursor, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CommentApi#getPageInlineComments");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the page for which inline comments should be returned. |
 **bodyFormat** | [**PrimaryBodyRepresentation**](.md)| The content format type to be returned in the &#x60;body&#x60; field of the response. If available, the representation will be available under a response field of the same name under the &#x60;body&#x60; field. | [optional]
 **status** | [**List&lt;String&gt;**](String.md)| Filter the inline comment being retrieved by its status. | [optional] [enum: current, archived, trashed, deleted, historical, draft]
 **resolutionStatus** | [**List&lt;String&gt;**](String.md)| Filter the inline comment being retrieved by its resolution status. | [optional] [enum: resolved, open, dangling, reopened]
 **sort** | [**CommentSortOrder**](.md)| Used to sort the result by a particular field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of inline comments per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultPageInlineCommentModel**](MultiEntityResultPageInlineCommentModel.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateFooterComment"></a>
# **updateFooterComment**
> FooterCommentModel updateFooterComment(body, commentId)

Update footer comment

Update a footer comment. This can be used to update the body text of a comment.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content of the page or blogpost and its corresponding space. Permission to create comments in the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.CommentApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

CommentApi apiInstance = new CommentApi();
FootercommentsCommentidBody body = new FootercommentsCommentidBody(); // FootercommentsCommentidBody | The footer comment to be created
Long commentId = 789L; // Long | The ID of the comment to be retrieved.
try {
    FooterCommentModel result = apiInstance.updateFooterComment(body, commentId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CommentApi#updateFooterComment");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**FootercommentsCommentidBody**](FootercommentsCommentidBody.md)| The footer comment to be created |
 **commentId** | **Long**| The ID of the comment to be retrieved. |

### Return type

[**FooterCommentModel**](FooterCommentModel.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateInlineComment"></a>
# **updateInlineComment**
> InlineResponse2013 updateInlineComment(body, commentId)

Update inline comment

Update an inline comment. This can be used to update the body text of a comment and/or to resolve the comment  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content of the page or blogpost and its corresponding space. Permission to create comments in the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.CommentApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

CommentApi apiInstance = new CommentApi();
UpdateInlineCommentModel body = new UpdateInlineCommentModel(); // UpdateInlineCommentModel | The inline comment to be updated
Long commentId = 789L; // Long | The ID of the comment to be retrieved.
try {
    InlineResponse2013 result = apiInstance.updateInlineComment(body, commentId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CommentApi#updateInlineComment");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UpdateInlineCommentModel**](UpdateInlineCommentModel.md)| The inline comment to be updated |
 **commentId** | **Long**| The ID of the comment to be retrieved. |

### Return type

[**InlineResponse2013**](InlineResponse2013.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

