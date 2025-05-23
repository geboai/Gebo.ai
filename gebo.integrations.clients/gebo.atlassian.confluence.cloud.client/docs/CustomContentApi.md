# CustomContentApi

All URIs are relative to *https://{your-domain}/wiki/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createCustomContent**](CustomContentApi.md#createCustomContent) | **POST** /custom-content | Create custom content
[**deleteCustomContent**](CustomContentApi.md#deleteCustomContent) | **DELETE** /custom-content/{id} | Delete custom content
[**getCustomContentById**](CustomContentApi.md#getCustomContentById) | **GET** /custom-content/{id} | Get custom content by id
[**getCustomContentByType**](CustomContentApi.md#getCustomContentByType) | **GET** /custom-content | Get custom content by type
[**getCustomContentByTypeInBlogPost**](CustomContentApi.md#getCustomContentByTypeInBlogPost) | **GET** /blogposts/{id}/custom-content | Get custom content by type in blog post
[**getCustomContentByTypeInPage**](CustomContentApi.md#getCustomContentByTypeInPage) | **GET** /pages/{id}/custom-content | Get custom content by type in page
[**getCustomContentByTypeInSpace**](CustomContentApi.md#getCustomContentByTypeInSpace) | **GET** /spaces/{id}/custom-content | Get custom content by type in space
[**updateCustomContent**](CustomContentApi.md#updateCustomContent) | **PUT** /custom-content/{id} | Update custom content

<a name="createCustomContent"></a>
# **createCustomContent**
> InlineResponse201 createCustomContent(body)

Create custom content

Creates a new custom content in the given space, page, blogpost or other custom content.  Only one of &#x60;spaceId&#x60;, &#x60;pageId&#x60;, &#x60;blogPostId&#x60;, or &#x60;customContentId&#x60; is required in the request body. **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content of the page or blogpost and its corresponding space. Permission to create custom content in the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.CustomContentApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

CustomContentApi apiInstance = new CustomContentApi();
Object body = null; // Object | 
try {
    InlineResponse201 result = apiInstance.createCustomContent(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CustomContentApi#createCustomContent");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Object**](Object.md)|  |

### Return type

[**InlineResponse201**](InlineResponse201.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deleteCustomContent"></a>
# **deleteCustomContent**
> deleteCustomContent(id, purge)

Delete custom content

Delete a custom content by id.  Deleting a custom content will either move it to the trash or permanently delete it (purge it), depending on the apiSupport. To permanently delete a **trashed** custom content, the endpoint must be called with the following param &#x60;purge&#x3D;true&#x60;.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content of the page or blogpost and its corresponding space. Permission to delete custom content in the space. Permission to administer the space (if attempting to purge).

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.CustomContentApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

CustomContentApi apiInstance = new CustomContentApi();
Long id = 789L; // Long | The ID of the custom content to be deleted.
Boolean purge = false; // Boolean | If attempting to purge the custom content.
try {
    apiInstance.deleteCustomContent(id, purge);
} catch (ApiException e) {
    System.err.println("Exception when calling CustomContentApi#deleteCustomContent");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the custom content to be deleted. |
 **purge** | **Boolean**| If attempting to purge the custom content. | [optional] [default to false]

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="getCustomContentById"></a>
# **getCustomContentById**
> InlineResponse201 getCustomContentById(id, bodyFormat, version, includeLabels, includeProperties, includeOperations, includeVersions, includeVersion)

Get custom content by id

Returns a specific piece of custom content.   **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the custom content, the container of the custom content, and the corresponding space (if different from the container).

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.CustomContentApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

CustomContentApi apiInstance = new CustomContentApi();
Long id = 789L; // Long | The ID of the custom content to be returned. If you don't know the custom content ID, use Get Custom Content by Type and filter the results.
CustomContentBodyRepresentationSingle bodyFormat = new CustomContentBodyRepresentationSingle(); // CustomContentBodyRepresentationSingle | The content format types to be returned in the `body` field of the response. If available, the representation will be available under a response field of the same name under the `body` field.  Note: If the custom content body type is `storage`, the `storage` and `atlas_doc_format` body formats are able to be returned. If the custom content body type is `raw`, only the `raw` body format is able to be returned.
Integer version = 56; // Integer | Allows you to retrieve a previously published version. Specify the previous version's number to retrieve its details.
Boolean includeLabels = false; // Boolean | Includes labels associated with this custom content in the response. The number of results will be limited to 50 and sorted in the default sort order.  A `meta` and `_links` property will be present to indicate if more results are available and a link to retrieve the rest of the results.
Boolean includeProperties = false; // Boolean | Includes content properties associated with this custom content in the response. The number of results will be limited to 50 and sorted in the default sort order.  A `meta` and `_links` property will be present to indicate if more results are available and a link to retrieve the rest of the results.
Boolean includeOperations = false; // Boolean | Includes operations associated with this custom content in the response. The number of results will be limited to 50 and sorted in the default sort order.  A `meta` and `_links` property will be present to indicate if more results are available and a link to retrieve the rest of the results.
Boolean includeVersions = false; // Boolean | Includes versions associated with this custom content in the response. The number of results will be limited to 50 and sorted in the default sort order.  A `meta` and `_links` property will be present to indicate if more results are available and a link to retrieve the rest of the results.
Boolean includeVersion = true; // Boolean | Includes the current version associated with this custom content in the response. By default this is included and can be omitted by setting the value to `false`.
try {
    InlineResponse201 result = apiInstance.getCustomContentById(id, bodyFormat, version, includeLabels, includeProperties, includeOperations, includeVersions, includeVersion);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CustomContentApi#getCustomContentById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the custom content to be returned. If you don&#x27;t know the custom content ID, use Get Custom Content by Type and filter the results. |
 **bodyFormat** | [**CustomContentBodyRepresentationSingle**](.md)| The content format types to be returned in the &#x60;body&#x60; field of the response. If available, the representation will be available under a response field of the same name under the &#x60;body&#x60; field.  Note: If the custom content body type is &#x60;storage&#x60;, the &#x60;storage&#x60; and &#x60;atlas_doc_format&#x60; body formats are able to be returned. If the custom content body type is &#x60;raw&#x60;, only the &#x60;raw&#x60; body format is able to be returned. | [optional]
 **version** | **Integer**| Allows you to retrieve a previously published version. Specify the previous version&#x27;s number to retrieve its details. | [optional]
 **includeLabels** | **Boolean**| Includes labels associated with this custom content in the response. The number of results will be limited to 50 and sorted in the default sort order.  A &#x60;meta&#x60; and &#x60;_links&#x60; property will be present to indicate if more results are available and a link to retrieve the rest of the results. | [optional] [default to false]
 **includeProperties** | **Boolean**| Includes content properties associated with this custom content in the response. The number of results will be limited to 50 and sorted in the default sort order.  A &#x60;meta&#x60; and &#x60;_links&#x60; property will be present to indicate if more results are available and a link to retrieve the rest of the results. | [optional] [default to false]
 **includeOperations** | **Boolean**| Includes operations associated with this custom content in the response. The number of results will be limited to 50 and sorted in the default sort order.  A &#x60;meta&#x60; and &#x60;_links&#x60; property will be present to indicate if more results are available and a link to retrieve the rest of the results. | [optional] [default to false]
 **includeVersions** | **Boolean**| Includes versions associated with this custom content in the response. The number of results will be limited to 50 and sorted in the default sort order.  A &#x60;meta&#x60; and &#x60;_links&#x60; property will be present to indicate if more results are available and a link to retrieve the rest of the results. | [optional] [default to false]
 **includeVersion** | **Boolean**| Includes the current version associated with this custom content in the response. By default this is included and can be omitted by setting the value to &#x60;false&#x60;. | [optional] [default to true]

### Return type

[**InlineResponse201**](InlineResponse201.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getCustomContentByType"></a>
# **getCustomContentByType**
> MultiEntityResultCustomContent getCustomContentByType(type, id, spaceId, sort, cursor, limit, bodyFormat)

Get custom content by type

Returns all custom content for a given type. The number of results is limited by the &#x60;limit&#x60; parameter and additional results (if available) will be available through the &#x60;next&#x60; URL present in the &#x60;Link&#x60; response header.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the custom content, the container of the custom content, and the corresponding space (if different from the container).

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.CustomContentApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

CustomContentApi apiInstance = new CustomContentApi();
String type = "type_example"; // String | The type of custom content being requested. See: https://developer.atlassian.com/cloud/confluence/custom-content/ for additional details on custom content.
List<Long> id = Arrays.asList(56L); // List<Long> | Filter the results based on custom content ids. Multiple custom content ids can be specified as a comma-separated list.
List<Long> spaceId = Arrays.asList(56L); // List<Long> | Filter the results based on space ids. Multiple space ids can be specified as a comma-separated list.
CustomContentSortOrder sort = new CustomContentSortOrder(); // CustomContentSortOrder | Used to sort the result by a particular field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of pages per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
CustomContentBodyRepresentation bodyFormat = new CustomContentBodyRepresentation(); // CustomContentBodyRepresentation | The content format types to be returned in the `body` field of the response. If available, the representation will be available under a response field of the same name under the `body` field.  Note: If the custom content body type is `storage`, the `storage` and `atlas_doc_format` body formats are able to be returned. If the custom content body type is `raw`, only the `raw` body format is able to be returned.
try {
    MultiEntityResultCustomContent result = apiInstance.getCustomContentByType(type, id, spaceId, sort, cursor, limit, bodyFormat);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CustomContentApi#getCustomContentByType");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **type** | **String**| The type of custom content being requested. See: https://developer.atlassian.com/cloud/confluence/custom-content/ for additional details on custom content. |
 **id** | [**List&lt;Long&gt;**](Long.md)| Filter the results based on custom content ids. Multiple custom content ids can be specified as a comma-separated list. | [optional]
 **spaceId** | [**List&lt;Long&gt;**](Long.md)| Filter the results based on space ids. Multiple space ids can be specified as a comma-separated list. | [optional]
 **sort** | [**CustomContentSortOrder**](.md)| Used to sort the result by a particular field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of pages per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]
 **bodyFormat** | [**CustomContentBodyRepresentation**](.md)| The content format types to be returned in the &#x60;body&#x60; field of the response. If available, the representation will be available under a response field of the same name under the &#x60;body&#x60; field.  Note: If the custom content body type is &#x60;storage&#x60;, the &#x60;storage&#x60; and &#x60;atlas_doc_format&#x60; body formats are able to be returned. If the custom content body type is &#x60;raw&#x60;, only the &#x60;raw&#x60; body format is able to be returned. | [optional]

### Return type

[**MultiEntityResultCustomContent**](MultiEntityResultCustomContent.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getCustomContentByTypeInBlogPost"></a>
# **getCustomContentByTypeInBlogPost**
> MultiEntityResultCustomContent getCustomContentByTypeInBlogPost(id, type, sort, cursor, limit, bodyFormat)

Get custom content by type in blog post

Returns all custom content for a given type within a given blogpost. The number of results is limited by the &#x60;limit&#x60; parameter and additional results (if available) will be available through the &#x60;next&#x60; URL present in the &#x60;Link&#x60; response header.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the custom content, the container of the custom content (blog post), and the corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.CustomContentApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

CustomContentApi apiInstance = new CustomContentApi();
Long id = 789L; // Long | The ID of the blog post for which custom content should be returned.
String type = "type_example"; // String | The type of custom content being requested. See: https://developer.atlassian.com/cloud/confluence/custom-content/ for additional details on custom content.
CustomContentSortOrder sort = new CustomContentSortOrder(); // CustomContentSortOrder | Used to sort the result by a particular field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of pages per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
CustomContentBodyRepresentation bodyFormat = new CustomContentBodyRepresentation(); // CustomContentBodyRepresentation | The content format types to be returned in the `body` field of the response. If available, the representation will be available under a response field of the same name under the `body` field.  Note: If the custom content body type is `storage`, the `storage` and `atlas_doc_format` body formats are able to be returned. If the custom content body type is `raw`, only the `raw` body format is able to be returned.
try {
    MultiEntityResultCustomContent result = apiInstance.getCustomContentByTypeInBlogPost(id, type, sort, cursor, limit, bodyFormat);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CustomContentApi#getCustomContentByTypeInBlogPost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the blog post for which custom content should be returned. |
 **type** | **String**| The type of custom content being requested. See: https://developer.atlassian.com/cloud/confluence/custom-content/ for additional details on custom content. |
 **sort** | [**CustomContentSortOrder**](.md)| Used to sort the result by a particular field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of pages per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]
 **bodyFormat** | [**CustomContentBodyRepresentation**](.md)| The content format types to be returned in the &#x60;body&#x60; field of the response. If available, the representation will be available under a response field of the same name under the &#x60;body&#x60; field.  Note: If the custom content body type is &#x60;storage&#x60;, the &#x60;storage&#x60; and &#x60;atlas_doc_format&#x60; body formats are able to be returned. If the custom content body type is &#x60;raw&#x60;, only the &#x60;raw&#x60; body format is able to be returned. | [optional]

### Return type

[**MultiEntityResultCustomContent**](MultiEntityResultCustomContent.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getCustomContentByTypeInPage"></a>
# **getCustomContentByTypeInPage**
> MultiEntityResultCustomContent getCustomContentByTypeInPage(id, type, sort, cursor, limit, bodyFormat)

Get custom content by type in page

Returns all custom content for a given type within a given page. The number of results is limited by the &#x60;limit&#x60; parameter and additional results (if available) will be available through the &#x60;next&#x60; URL present in the &#x60;Link&#x60; response header.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the custom content, the container of the custom content (page), and the corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.CustomContentApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

CustomContentApi apiInstance = new CustomContentApi();
Long id = 789L; // Long | The ID of the page for which custom content should be returned.
String type = "type_example"; // String | The type of custom content being requested. See: https://developer.atlassian.com/cloud/confluence/custom-content/ for additional details on custom content.
CustomContentSortOrder sort = new CustomContentSortOrder(); // CustomContentSortOrder | Used to sort the result by a particular field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of pages per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
CustomContentBodyRepresentation bodyFormat = new CustomContentBodyRepresentation(); // CustomContentBodyRepresentation | The content format types to be returned in the `body` field of the response. If available, the representation will be available under a response field of the same name under the `body` field.  Note: If the custom content body type is `storage`, the `storage` and `atlas_doc_format` body formats are able to be returned. If the custom content body type is `raw`, only the `raw` body format is able to be returned.
try {
    MultiEntityResultCustomContent result = apiInstance.getCustomContentByTypeInPage(id, type, sort, cursor, limit, bodyFormat);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CustomContentApi#getCustomContentByTypeInPage");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the page for which custom content should be returned. |
 **type** | **String**| The type of custom content being requested. See: https://developer.atlassian.com/cloud/confluence/custom-content/ for additional details on custom content. |
 **sort** | [**CustomContentSortOrder**](.md)| Used to sort the result by a particular field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of pages per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]
 **bodyFormat** | [**CustomContentBodyRepresentation**](.md)| The content format types to be returned in the &#x60;body&#x60; field of the response. If available, the representation will be available under a response field of the same name under the &#x60;body&#x60; field.  Note: If the custom content body type is &#x60;storage&#x60;, the &#x60;storage&#x60; and &#x60;atlas_doc_format&#x60; body formats are able to be returned. If the custom content body type is &#x60;raw&#x60;, only the &#x60;raw&#x60; body format is able to be returned. | [optional]

### Return type

[**MultiEntityResultCustomContent**](MultiEntityResultCustomContent.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getCustomContentByTypeInSpace"></a>
# **getCustomContentByTypeInSpace**
> MultiEntityResultCustomContent getCustomContentByTypeInSpace(id, type, cursor, limit, bodyFormat)

Get custom content by type in space

Returns all custom content for a given type within a given space. The number of results is limited by the &#x60;limit&#x60; parameter and additional results (if available) will be available through the &#x60;next&#x60; URL present in the &#x60;Link&#x60; response header.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the custom content and the corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.CustomContentApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

CustomContentApi apiInstance = new CustomContentApi();
Long id = 789L; // Long | The ID of the space for which custom content should be returned.
String type = "type_example"; // String | The type of custom content being requested. See: https://developer.atlassian.com/cloud/confluence/custom-content/ for additional details on custom content.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of pages per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
CustomContentBodyRepresentation bodyFormat = new CustomContentBodyRepresentation(); // CustomContentBodyRepresentation | The content format types to be returned in the `body` field of the response. If available, the representation will be available under a response field of the same name under the `body` field.  Note: If the custom content body type is `storage`, the `storage` and `atlas_doc_format` body formats are able to be returned. If the custom content body type is `raw`, only the `raw` body format is able to be returned.
try {
    MultiEntityResultCustomContent result = apiInstance.getCustomContentByTypeInSpace(id, type, cursor, limit, bodyFormat);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CustomContentApi#getCustomContentByTypeInSpace");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the space for which custom content should be returned. |
 **type** | **String**| The type of custom content being requested. See: https://developer.atlassian.com/cloud/confluence/custom-content/ for additional details on custom content. |
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of pages per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]
 **bodyFormat** | [**CustomContentBodyRepresentation**](.md)| The content format types to be returned in the &#x60;body&#x60; field of the response. If available, the representation will be available under a response field of the same name under the &#x60;body&#x60; field.  Note: If the custom content body type is &#x60;storage&#x60;, the &#x60;storage&#x60; and &#x60;atlas_doc_format&#x60; body formats are able to be returned. If the custom content body type is &#x60;raw&#x60;, only the &#x60;raw&#x60; body format is able to be returned. | [optional]

### Return type

[**MultiEntityResultCustomContent**](MultiEntityResultCustomContent.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateCustomContent"></a>
# **updateCustomContent**
> InlineResponse201 updateCustomContent(body, id)

Update custom content

Update a custom content by id.  &#x60;spaceId&#x60; is always required and maximum one of &#x60;pageId&#x60;, &#x60;blogPostId&#x60;, or &#x60;customContentId&#x60; is allowed in the request body. **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content of the page or blogpost and its corresponding space. Permission to update custom content in the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.CustomContentApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

CustomContentApi apiInstance = new CustomContentApi();
Object body = null; // Object | 
Long id = 789L; // Long | The ID of the custom content to be updated. If you don't know the custom content ID, use Get Custom Content by Type and filter the results.
try {
    InlineResponse201 result = apiInstance.updateCustomContent(body, id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CustomContentApi#updateCustomContent");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Object**](Object.md)|  |
 **id** | **Long**| The ID of the custom content to be updated. If you don&#x27;t know the custom content ID, use Get Custom Content by Type and filter the results. |

### Return type

[**InlineResponse201**](InlineResponse201.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

