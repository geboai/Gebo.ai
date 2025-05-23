# BlogPostApi

All URIs are relative to *https://{your-domain}/wiki/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createBlogPost**](BlogPostApi.md#createBlogPost) | **POST** /blogposts | Create blog post
[**deleteBlogPost**](BlogPostApi.md#deleteBlogPost) | **DELETE** /blogposts/{id} | Delete blog post
[**getBlogPostById**](BlogPostApi.md#getBlogPostById) | **GET** /blogposts/{id} | Get blog post by id
[**getBlogPosts**](BlogPostApi.md#getBlogPosts) | **GET** /blogposts | Get blog posts
[**getBlogPostsInSpace**](BlogPostApi.md#getBlogPostsInSpace) | **GET** /spaces/{id}/blogposts | Get blog posts in space
[**getLabelBlogPosts**](BlogPostApi.md#getLabelBlogPosts) | **GET** /labels/{id}/blogposts | Get blog posts for label
[**updateBlogPost**](BlogPostApi.md#updateBlogPost) | **PUT** /blogposts/{id} | Update blog post

<a name="createBlogPost"></a>
# **createBlogPost**
> InlineResponse2001 createBlogPost(body, _private)

Create blog post

Creates a new blog post in the space specified by the spaceId.  By default this will create the blog post as a non-draft, unless the status is specified as draft. If creating a non-draft, the title must not be empty.  Currently only supports the storage representation specified in the body.representation enums below

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.BlogPostApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

BlogPostApi apiInstance = new BlogPostApi();
Object body = null; // Object | 
Boolean _private = false; // Boolean | The blog post will be private. Only the user who creates this blog post will have permission to view and edit one.
try {
    InlineResponse2001 result = apiInstance.createBlogPost(body, _private);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BlogPostApi#createBlogPost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Object**](Object.md)|  |
 **_private** | **Boolean**| The blog post will be private. Only the user who creates this blog post will have permission to view and edit one. | [optional] [default to false]

### Return type

[**InlineResponse2001**](InlineResponse2001.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deleteBlogPost"></a>
# **deleteBlogPost**
> deleteBlogPost(id, purge, draft)

Delete blog post

Delete a blog post by id.  By default this will delete blog posts that are non-drafts. To delete a blog post that is a draft, the endpoint must be called on a  draft with the following param &#x60;draft&#x3D;true&#x60;. Discarded drafts are not sent to the trash and are permanently deleted.  Deleting a blog post that is not a draft moves the blog post to the trash, where it can be restored later. To permanently delete a blog post (or \&quot;purge\&quot; it), the endpoint must be called on a **trashed** blog post with the following param &#x60;purge&#x3D;true&#x60;.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the blog post and its corresponding space. Permission to delete blog posts in the space. Permission to administer the space (if attempting to purge).

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.BlogPostApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

BlogPostApi apiInstance = new BlogPostApi();
Long id = 789L; // Long | The ID of the blog post to be deleted.
Boolean purge = false; // Boolean | If attempting to purge the blog post.
Boolean draft = false; // Boolean | If attempting to delete a blog post that is a draft.
try {
    apiInstance.deleteBlogPost(id, purge, draft);
} catch (ApiException e) {
    System.err.println("Exception when calling BlogPostApi#deleteBlogPost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the blog post to be deleted. |
 **purge** | **Boolean**| If attempting to purge the blog post. | [optional] [default to false]
 **draft** | **Boolean**| If attempting to delete a blog post that is a draft. | [optional] [default to false]

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="getBlogPostById"></a>
# **getBlogPostById**
> InlineResponse2001 getBlogPostById(id, bodyFormat, getDraft, status, version, includeLabels, includeProperties, includeOperations, includeLikes, includeVersions, includeVersion, includeFavoritedByCurrentUserStatus)

Get blog post by id

Returns a specific blog post.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the blog post and its corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.BlogPostApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

BlogPostApi apiInstance = new BlogPostApi();
Long id = 789L; // Long | The ID of the blog post to be returned. If you don't know the blog post ID, use Get blog posts and filter the results.
PrimaryBodyRepresentationSingle bodyFormat = new PrimaryBodyRepresentationSingle(); // PrimaryBodyRepresentationSingle | The content format types to be returned in the `body` field of the response. If available, the representation will be available under a response field of the same name under the `body` field.
Boolean getDraft = false; // Boolean | Retrieve the draft version of this blog post.
List<String> status = Arrays.asList("status_example"); // List<String> | Filter the blog post being retrieved by its status.
Integer version = 56; // Integer | Allows you to retrieve a previously published version. Specify the previous version's number to retrieve its details.
Boolean includeLabels = false; // Boolean | Includes labels associated with this blog post in the response. The number of results will be limited to 50 and sorted in the default sort order.  A `meta` and `_links` property will be present to indicate if more results are available and a link to retrieve the rest of the results.
Boolean includeProperties = false; // Boolean | Includes content properties associated with this blog post in the response. The number of results will be limited to 50 and sorted in the default sort order.  A `meta` and `_links` property will be present to indicate if more results are available and a link to retrieve the rest of the results.
Boolean includeOperations = false; // Boolean | Includes operations associated with this blog post in the response. The number of results will be limited to 50 and sorted in the default sort order.  A `meta` and `_links` property will be present to indicate if more results are available and a link to retrieve the rest of the results.
Boolean includeLikes = false; // Boolean | Includes likes associated with this blog post in the response. The number of results will be limited to 50 and sorted in the default sort order.  A `meta` and `_links` property will be present to indicate if more results are available and a link to retrieve the rest of the results.
Boolean includeVersions = false; // Boolean | Includes versions associated with this blog post in the response. The number of results will be limited to 50 and sorted in the default sort order.  A `meta` and `_links` property will be present to indicate if more results are available and a link to retrieve the rest of the results.
Boolean includeVersion = true; // Boolean | Includes the current version associated with this blog post in the response. By default this is included and can be omitted by setting the value to `false`.
Boolean includeFavoritedByCurrentUserStatus = false; // Boolean | Includes whether this blog post has been favorited by the current user.
try {
    InlineResponse2001 result = apiInstance.getBlogPostById(id, bodyFormat, getDraft, status, version, includeLabels, includeProperties, includeOperations, includeLikes, includeVersions, includeVersion, includeFavoritedByCurrentUserStatus);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BlogPostApi#getBlogPostById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the blog post to be returned. If you don&#x27;t know the blog post ID, use Get blog posts and filter the results. |
 **bodyFormat** | [**PrimaryBodyRepresentationSingle**](.md)| The content format types to be returned in the &#x60;body&#x60; field of the response. If available, the representation will be available under a response field of the same name under the &#x60;body&#x60; field. | [optional]
 **getDraft** | **Boolean**| Retrieve the draft version of this blog post. | [optional] [default to false]
 **status** | [**List&lt;String&gt;**](String.md)| Filter the blog post being retrieved by its status. | [optional] [enum: current, trashed, deleted, historical, draft]
 **version** | **Integer**| Allows you to retrieve a previously published version. Specify the previous version&#x27;s number to retrieve its details. | [optional]
 **includeLabels** | **Boolean**| Includes labels associated with this blog post in the response. The number of results will be limited to 50 and sorted in the default sort order.  A &#x60;meta&#x60; and &#x60;_links&#x60; property will be present to indicate if more results are available and a link to retrieve the rest of the results. | [optional] [default to false]
 **includeProperties** | **Boolean**| Includes content properties associated with this blog post in the response. The number of results will be limited to 50 and sorted in the default sort order.  A &#x60;meta&#x60; and &#x60;_links&#x60; property will be present to indicate if more results are available and a link to retrieve the rest of the results. | [optional] [default to false]
 **includeOperations** | **Boolean**| Includes operations associated with this blog post in the response. The number of results will be limited to 50 and sorted in the default sort order.  A &#x60;meta&#x60; and &#x60;_links&#x60; property will be present to indicate if more results are available and a link to retrieve the rest of the results. | [optional] [default to false]
 **includeLikes** | **Boolean**| Includes likes associated with this blog post in the response. The number of results will be limited to 50 and sorted in the default sort order.  A &#x60;meta&#x60; and &#x60;_links&#x60; property will be present to indicate if more results are available and a link to retrieve the rest of the results. | [optional] [default to false]
 **includeVersions** | **Boolean**| Includes versions associated with this blog post in the response. The number of results will be limited to 50 and sorted in the default sort order.  A &#x60;meta&#x60; and &#x60;_links&#x60; property will be present to indicate if more results are available and a link to retrieve the rest of the results. | [optional] [default to false]
 **includeVersion** | **Boolean**| Includes the current version associated with this blog post in the response. By default this is included and can be omitted by setting the value to &#x60;false&#x60;. | [optional] [default to true]
 **includeFavoritedByCurrentUserStatus** | **Boolean**| Includes whether this blog post has been favorited by the current user. | [optional] [default to false]

### Return type

[**InlineResponse2001**](InlineResponse2001.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getBlogPosts"></a>
# **getBlogPosts**
> MultiEntityResultBlogPost getBlogPosts(id, spaceId, sort, status, title, bodyFormat, cursor, limit)

Get blog posts

Returns all blog posts. The number of results is limited by the &#x60;limit&#x60; parameter and additional results (if available) will be available through the &#x60;next&#x60; URL present in the &#x60;Link&#x60; response header.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to access the Confluence site (&#x27;Can use&#x27; global permission). Only blog posts that the user has permission to view will be returned.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.BlogPostApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

BlogPostApi apiInstance = new BlogPostApi();
List<Long> id = Arrays.asList(56L); // List<Long> | Filter the results based on blog post ids. Multiple blog post ids can be specified as a comma-separated list.
List<Long> spaceId = Arrays.asList(56L); // List<Long> | Filter the results based on space ids. Multiple space ids can be specified as a comma-separated list.
BlogPostSortOrder sort = new BlogPostSortOrder(); // BlogPostSortOrder | Used to sort the result by a particular field.
List<String> status = Arrays.asList("status_example"); // List<String> | Filter the results to blog posts based on their status. By default, `current` is used.
String title = "title_example"; // String | Filter the results to blog posts based on their title.
PrimaryBodyRepresentation bodyFormat = new PrimaryBodyRepresentation(); // PrimaryBodyRepresentation | The content format types to be returned in the `body` field of the response. If available, the representation will be available under a response field of the same name under the `body` field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of blog posts per result to return. If more results exist, use the `Link` response header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultBlogPost result = apiInstance.getBlogPosts(id, spaceId, sort, status, title, bodyFormat, cursor, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BlogPostApi#getBlogPosts");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | [**List&lt;Long&gt;**](Long.md)| Filter the results based on blog post ids. Multiple blog post ids can be specified as a comma-separated list. | [optional]
 **spaceId** | [**List&lt;Long&gt;**](Long.md)| Filter the results based on space ids. Multiple space ids can be specified as a comma-separated list. | [optional]
 **sort** | [**BlogPostSortOrder**](.md)| Used to sort the result by a particular field. | [optional]
 **status** | [**List&lt;String&gt;**](String.md)| Filter the results to blog posts based on their status. By default, &#x60;current&#x60; is used. | [optional] [enum: current, deleted, trashed]
 **title** | **String**| Filter the results to blog posts based on their title. | [optional]
 **bodyFormat** | [**PrimaryBodyRepresentation**](.md)| The content format types to be returned in the &#x60;body&#x60; field of the response. If available, the representation will be available under a response field of the same name under the &#x60;body&#x60; field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of blog posts per result to return. If more results exist, use the &#x60;Link&#x60; response header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultBlogPost**](MultiEntityResultBlogPost.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getBlogPostsInSpace"></a>
# **getBlogPostsInSpace**
> MultiEntityResultBlogPost getBlogPostsInSpace(id, sort, status, title, bodyFormat, cursor, limit)

Get blog posts in space

Returns all blog posts in a space. The number of results is limited by the &#x60;limit&#x60; parameter and additional results (if available) will be available through the &#x60;next&#x60; URL present in the &#x60;Link&#x60; response header.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to access the Confluence site (&#x27;Can use&#x27; global permission) and view the space. Only blog posts that the user has permission to view will be returned.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.BlogPostApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

BlogPostApi apiInstance = new BlogPostApi();
Long id = 789L; // Long | The ID of the space for which blog posts should be returned.
BlogPostSortOrder sort = new BlogPostSortOrder(); // BlogPostSortOrder | Used to sort the result by a particular field.
List<String> status = Arrays.asList("status_example"); // List<String> | Filter the results to blog posts based on their status. By default, `current` is used.
String title = "title_example"; // String | Filter the results to blog posts based on their title.
PrimaryBodyRepresentation bodyFormat = new PrimaryBodyRepresentation(); // PrimaryBodyRepresentation | The content format types to be returned in the `body` field of the response. If available, the representation will be available under a response field of the same name under the `body` field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of blog posts per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultBlogPost result = apiInstance.getBlogPostsInSpace(id, sort, status, title, bodyFormat, cursor, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BlogPostApi#getBlogPostsInSpace");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the space for which blog posts should be returned. |
 **sort** | [**BlogPostSortOrder**](.md)| Used to sort the result by a particular field. | [optional]
 **status** | [**List&lt;String&gt;**](String.md)| Filter the results to blog posts based on their status. By default, &#x60;current&#x60; is used. | [optional] [enum: current, deleted, trashed]
 **title** | **String**| Filter the results to blog posts based on their title. | [optional]
 **bodyFormat** | [**PrimaryBodyRepresentation**](.md)| The content format types to be returned in the &#x60;body&#x60; field of the response. If available, the representation will be available under a response field of the same name under the &#x60;body&#x60; field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of blog posts per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultBlogPost**](MultiEntityResultBlogPost.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getLabelBlogPosts"></a>
# **getLabelBlogPosts**
> MultiEntityResultBlogPost getLabelBlogPosts(id, spaceId, bodyFormat, sort, cursor, limit)

Get blog posts for label

Returns the blogposts of specified label. The number of results is limited by the &#x60;limit&#x60; parameter and additional results (if available) will be available through the &#x60;next&#x60; URL present in the &#x60;Link&#x60; response header.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content of the page and its corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.BlogPostApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

BlogPostApi apiInstance = new BlogPostApi();
Long id = 789L; // Long | The ID of the label for which blog posts should be returned.
List<Long> spaceId = Arrays.asList(56L); // List<Long> | Filter the results based on space ids. Multiple space ids can be specified as a comma-separated list.
PrimaryBodyRepresentation bodyFormat = new PrimaryBodyRepresentation(); // PrimaryBodyRepresentation | The content format types to be returned in the `body` field of the response. If available, the representation will be available under a response field of the same name under the `body` field.
BlogPostSortOrder sort = new BlogPostSortOrder(); // BlogPostSortOrder | Used to sort the result by a particular field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of blog posts per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultBlogPost result = apiInstance.getLabelBlogPosts(id, spaceId, bodyFormat, sort, cursor, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BlogPostApi#getLabelBlogPosts");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the label for which blog posts should be returned. |
 **spaceId** | [**List&lt;Long&gt;**](Long.md)| Filter the results based on space ids. Multiple space ids can be specified as a comma-separated list. | [optional]
 **bodyFormat** | [**PrimaryBodyRepresentation**](.md)| The content format types to be returned in the &#x60;body&#x60; field of the response. If available, the representation will be available under a response field of the same name under the &#x60;body&#x60; field. | [optional]
 **sort** | [**BlogPostSortOrder**](.md)| Used to sort the result by a particular field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of blog posts per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultBlogPost**](MultiEntityResultBlogPost.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateBlogPost"></a>
# **updateBlogPost**
> InlineResponse2001 updateBlogPost(body, id)

Update blog post

Update a blog post by id.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the blog post and its corresponding space. Permission to update blog posts in the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.BlogPostApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

BlogPostApi apiInstance = new BlogPostApi();
Object body = null; // Object | 
Long id = 789L; // Long | The ID of the blog post to be updated. If you don't know the blog post ID, use Get Blog Posts and filter the results.
try {
    InlineResponse2001 result = apiInstance.updateBlogPost(body, id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling BlogPostApi#updateBlogPost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Object**](Object.md)|  |
 **id** | **Long**| The ID of the blog post to be updated. If you don&#x27;t know the blog post ID, use Get Blog Posts and filter the results. |

### Return type

[**InlineResponse2001**](InlineResponse2001.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

