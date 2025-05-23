# AttachmentApi

All URIs are relative to *https://{your-domain}/wiki/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteAttachment**](AttachmentApi.md#deleteAttachment) | **DELETE** /attachments/{id} | Delete attachment
[**getAttachmentById**](AttachmentApi.md#getAttachmentById) | **GET** /attachments/{id} | Get attachment by id
[**getAttachments**](AttachmentApi.md#getAttachments) | **GET** /attachments | Get attachments
[**getBlogpostAttachments**](AttachmentApi.md#getBlogpostAttachments) | **GET** /blogposts/{id}/attachments | Get attachments for blog post
[**getCustomContentAttachments**](AttachmentApi.md#getCustomContentAttachments) | **GET** /custom-content/{id}/attachments | Get attachments for custom content
[**getLabelAttachments**](AttachmentApi.md#getLabelAttachments) | **GET** /labels/{id}/attachments | Get attachments for label
[**getPageAttachments**](AttachmentApi.md#getPageAttachments) | **GET** /pages/{id}/attachments | Get attachments for page

<a name="deleteAttachment"></a>
# **deleteAttachment**
> deleteAttachment(id, purge)

Delete attachment

Delete an attachment by id.  Deleting an attachment moves the attachment to the trash, where it can be restored later. To permanently delete an attachment (or \&quot;purge\&quot; it), the endpoint must be called on a **trashed** attachment with the following param &#x60;purge&#x3D;true&#x60;.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the container of the attachment. Permission to delete attachments in the space. Permission to administer the space (if attempting to purge).

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.AttachmentApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

AttachmentApi apiInstance = new AttachmentApi();
Long id = 789L; // Long | The ID of the attachment to be deleted.
Boolean purge = false; // Boolean | If attempting to purge the attachment.
try {
    apiInstance.deleteAttachment(id, purge);
} catch (ApiException e) {
    System.err.println("Exception when calling AttachmentApi#deleteAttachment");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the attachment to be deleted. |
 **purge** | **Boolean**| If attempting to purge the attachment. | [optional] [default to false]

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="getAttachmentById"></a>
# **getAttachmentById**
> InlineResponse200 getAttachmentById(id, version, includeLabels, includeProperties, includeOperations, includeVersions, includeVersion)

Get attachment by id

Returns a specific attachment.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the attachment&#x27;s container.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.AttachmentApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

AttachmentApi apiInstance = new AttachmentApi();
String id = "id_example"; // String | The ID of the attachment to be returned. If you don't know the attachment's ID, use Get attachments for page/blogpost/custom content.
Integer version = 56; // Integer | Allows you to retrieve a previously published version. Specify the previous version's number to retrieve its details.
Boolean includeLabels = false; // Boolean | Includes labels associated with this attachment in the response. The number of results will be limited to 50 and sorted in the default sort order.  A `meta` and `_links` property will be present to indicate if more results are available and a link to retrieve the rest of the results.
Boolean includeProperties = false; // Boolean | Includes content properties associated with this attachment in the response. The number of results will be limited to 50 and sorted in the default sort order.  A `meta` and `_links` property will be present to indicate if more results are available and a link to retrieve the rest of the results.
Boolean includeOperations = false; // Boolean | Includes operations associated with this attachment in the response. The number of results will be limited to 50 and sorted in the default sort order.  A `meta` and `_links` property will be present to indicate if more results are available and a link to retrieve the rest of the results.
Boolean includeVersions = false; // Boolean | Includes versions associated with this attachment in the response. The number of results will be limited to 50 and sorted in the default sort order.  A `meta` and `_links` property will be present to indicate if more results are available and a link to retrieve the rest of the results.
Boolean includeVersion = true; // Boolean | Includes the current version associated with this attachment in the response. By default this is included and can be omitted by setting the value to `false`.
try {
    InlineResponse200 result = apiInstance.getAttachmentById(id, version, includeLabels, includeProperties, includeOperations, includeVersions, includeVersion);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AttachmentApi#getAttachmentById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The ID of the attachment to be returned. If you don&#x27;t know the attachment&#x27;s ID, use Get attachments for page/blogpost/custom content. |
 **version** | **Integer**| Allows you to retrieve a previously published version. Specify the previous version&#x27;s number to retrieve its details. | [optional]
 **includeLabels** | **Boolean**| Includes labels associated with this attachment in the response. The number of results will be limited to 50 and sorted in the default sort order.  A &#x60;meta&#x60; and &#x60;_links&#x60; property will be present to indicate if more results are available and a link to retrieve the rest of the results. | [optional] [default to false]
 **includeProperties** | **Boolean**| Includes content properties associated with this attachment in the response. The number of results will be limited to 50 and sorted in the default sort order.  A &#x60;meta&#x60; and &#x60;_links&#x60; property will be present to indicate if more results are available and a link to retrieve the rest of the results. | [optional] [default to false]
 **includeOperations** | **Boolean**| Includes operations associated with this attachment in the response. The number of results will be limited to 50 and sorted in the default sort order.  A &#x60;meta&#x60; and &#x60;_links&#x60; property will be present to indicate if more results are available and a link to retrieve the rest of the results. | [optional] [default to false]
 **includeVersions** | **Boolean**| Includes versions associated with this attachment in the response. The number of results will be limited to 50 and sorted in the default sort order.  A &#x60;meta&#x60; and &#x60;_links&#x60; property will be present to indicate if more results are available and a link to retrieve the rest of the results. | [optional] [default to false]
 **includeVersion** | **Boolean**| Includes the current version associated with this attachment in the response. By default this is included and can be omitted by setting the value to &#x60;false&#x60;. | [optional] [default to true]

### Return type

[**InlineResponse200**](InlineResponse200.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAttachments"></a>
# **getAttachments**
> MultiEntityResultAttachment getAttachments(sort, cursor, status, mediaType, filename, limit)

Get attachments

Returns all attachments. The number of results is limited by the &#x60;limit&#x60; parameter and additional results (if available) will be available through the &#x60;next&#x60; URL present in the &#x60;Link&#x60; response header.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the container of the attachment.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.AttachmentApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

AttachmentApi apiInstance = new AttachmentApi();
AttachmentSortOrder sort = new AttachmentSortOrder(); // AttachmentSortOrder | Used to sort the result by a particular field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
List<String> status = Arrays.asList("status_example"); // List<String> | Filter the results to attachments based on their status. By default, `current` and `archived` are used.
String mediaType = "mediaType_example"; // String | Filters on the mediaType of attachments. Only one may be specified.
String filename = "filename_example"; // String | Filters on the file-name of attachments. Only one may be specified.
Integer limit = 50; // Integer | Maximum number of attachments per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultAttachment result = apiInstance.getAttachments(sort, cursor, status, mediaType, filename, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AttachmentApi#getAttachments");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **sort** | [**AttachmentSortOrder**](.md)| Used to sort the result by a particular field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **status** | [**List&lt;String&gt;**](String.md)| Filter the results to attachments based on their status. By default, &#x60;current&#x60; and &#x60;archived&#x60; are used. | [optional] [enum: current, archived, trashed]
 **mediaType** | **String**| Filters on the mediaType of attachments. Only one may be specified. | [optional]
 **filename** | **String**| Filters on the file-name of attachments. Only one may be specified. | [optional]
 **limit** | **Integer**| Maximum number of attachments per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 50] [enum: 1, 250]

### Return type

[**MultiEntityResultAttachment**](MultiEntityResultAttachment.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getBlogpostAttachments"></a>
# **getBlogpostAttachments**
> MultiEntityResultAttachment getBlogpostAttachments(id, sort, cursor, status, mediaType, filename, limit)

Get attachments for blog post

Returns the attachments of specific blog post. The number of results is limited by the &#x60;limit&#x60; parameter and additional results (if available) will be available through the &#x60;next&#x60; URL present in the &#x60;Link&#x60; response header.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content of the blog post and its corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.AttachmentApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

AttachmentApi apiInstance = new AttachmentApi();
Long id = 789L; // Long | The ID of the blog post for which attachments should be returned.
AttachmentSortOrder sort = new AttachmentSortOrder(); // AttachmentSortOrder | Used to sort the result by a particular field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
List<String> status = Arrays.asList("status_example"); // List<String> | Filter the results to attachments based on their status. By default, `current` and `archived` are used.
String mediaType = "mediaType_example"; // String | Filters on the mediaType of attachments. Only one may be specified.
String filename = "filename_example"; // String | Filters on the file-name of attachments. Only one may be specified.
Integer limit = 50; // Integer | Maximum number of attachments per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultAttachment result = apiInstance.getBlogpostAttachments(id, sort, cursor, status, mediaType, filename, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AttachmentApi#getBlogpostAttachments");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the blog post for which attachments should be returned. |
 **sort** | [**AttachmentSortOrder**](.md)| Used to sort the result by a particular field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **status** | [**List&lt;String&gt;**](String.md)| Filter the results to attachments based on their status. By default, &#x60;current&#x60; and &#x60;archived&#x60; are used. | [optional] [enum: current, archived, trashed]
 **mediaType** | **String**| Filters on the mediaType of attachments. Only one may be specified. | [optional]
 **filename** | **String**| Filters on the file-name of attachments. Only one may be specified. | [optional]
 **limit** | **Integer**| Maximum number of attachments per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 50] [enum: 1, 250]

### Return type

[**MultiEntityResultAttachment**](MultiEntityResultAttachment.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getCustomContentAttachments"></a>
# **getCustomContentAttachments**
> MultiEntityResultAttachment getCustomContentAttachments(id, sort, cursor, status, mediaType, filename, limit)

Get attachments for custom content

Returns the attachments of specific custom content. The number of results is limited by the &#x60;limit&#x60; parameter and additional results (if available) will be available through the &#x60;next&#x60; URL present in the &#x60;Link&#x60; response header.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content of the custom content and its corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.AttachmentApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

AttachmentApi apiInstance = new AttachmentApi();
Long id = 789L; // Long | The ID of the custom content for which attachments should be returned.
AttachmentSortOrder sort = new AttachmentSortOrder(); // AttachmentSortOrder | Used to sort the result by a particular field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
List<String> status = Arrays.asList("status_example"); // List<String> | Filter the results to attachments based on their status. By default, `current` and `archived` are used.
String mediaType = "mediaType_example"; // String | Filters on the mediaType of attachments. Only one may be specified.
String filename = "filename_example"; // String | Filters on the file-name of attachments. Only one may be specified.
Integer limit = 50; // Integer | Maximum number of attachments per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultAttachment result = apiInstance.getCustomContentAttachments(id, sort, cursor, status, mediaType, filename, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AttachmentApi#getCustomContentAttachments");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the custom content for which attachments should be returned. |
 **sort** | [**AttachmentSortOrder**](.md)| Used to sort the result by a particular field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **status** | [**List&lt;String&gt;**](String.md)| Filter the results to attachments based on their status. By default, &#x60;current&#x60; and &#x60;archived&#x60; are used. | [optional] [enum: current, archived, trashed]
 **mediaType** | **String**| Filters on the mediaType of attachments. Only one may be specified. | [optional]
 **filename** | **String**| Filters on the file-name of attachments. Only one may be specified. | [optional]
 **limit** | **Integer**| Maximum number of attachments per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 50] [enum: 1, 250]

### Return type

[**MultiEntityResultAttachment**](MultiEntityResultAttachment.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getLabelAttachments"></a>
# **getLabelAttachments**
> MultiEntityResultAttachment getLabelAttachments(id, sort, cursor, limit)

Get attachments for label

Returns the attachments of specified label. The number of results is limited by the &#x60;limit&#x60; parameter and additional results (if available) will be available through the &#x60;next&#x60; URL present in the &#x60;Link&#x60; response header.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the attachment and its corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.AttachmentApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

AttachmentApi apiInstance = new AttachmentApi();
Long id = 789L; // Long | The ID of the label for which attachments should be returned.
AttachmentSortOrder sort = new AttachmentSortOrder(); // AttachmentSortOrder | Used to sort the result by a particular field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of pages per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultAttachment result = apiInstance.getLabelAttachments(id, sort, cursor, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AttachmentApi#getLabelAttachments");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the label for which attachments should be returned. |
 **sort** | [**AttachmentSortOrder**](.md)| Used to sort the result by a particular field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of pages per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultAttachment**](MultiEntityResultAttachment.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getPageAttachments"></a>
# **getPageAttachments**
> MultiEntityResultAttachment getPageAttachments(id, sort, cursor, status, mediaType, filename, limit)

Get attachments for page

Returns the attachments of specific page. The number of results is limited by the &#x60;limit&#x60; parameter and additional results (if available) will be available through the &#x60;next&#x60; URL present in the &#x60;Link&#x60; response header.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content of the page and its corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.AttachmentApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

AttachmentApi apiInstance = new AttachmentApi();
Long id = 789L; // Long | The ID of the page for which attachments should be returned.
AttachmentSortOrder sort = new AttachmentSortOrder(); // AttachmentSortOrder | Used to sort the result by a particular field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
List<String> status = Arrays.asList("status_example"); // List<String> | Filter the results to attachments based on their status. By default, `current` and `archived` are used.
String mediaType = "mediaType_example"; // String | Filters on the mediaType of attachments. Only one may be specified.
String filename = "filename_example"; // String | Filters on the file-name of attachments. Only one may be specified.
Integer limit = 50; // Integer | Maximum number of attachments per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultAttachment result = apiInstance.getPageAttachments(id, sort, cursor, status, mediaType, filename, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AttachmentApi#getPageAttachments");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the page for which attachments should be returned. |
 **sort** | [**AttachmentSortOrder**](.md)| Used to sort the result by a particular field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **status** | [**List&lt;String&gt;**](String.md)| Filter the results to attachments based on their status. By default, &#x60;current&#x60; and &#x60;archived&#x60; are used. | [optional] [enum: current, archived, trashed]
 **mediaType** | **String**| Filters on the mediaType of attachments. Only one may be specified. | [optional]
 **filename** | **String**| Filters on the file-name of attachments. Only one may be specified. | [optional]
 **limit** | **Integer**| Maximum number of attachments per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 50] [enum: 1, 250]

### Return type

[**MultiEntityResultAttachment**](MultiEntityResultAttachment.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

