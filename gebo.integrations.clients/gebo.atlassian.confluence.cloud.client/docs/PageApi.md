# PageApi

All URIs are relative to *https://{your-domain}/wiki/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createPage**](PageApi.md#createPage) | **POST** /pages | Create page
[**deletePage**](PageApi.md#deletePage) | **DELETE** /pages/{id} | Delete page
[**getLabelPages**](PageApi.md#getLabelPages) | **GET** /labels/{id}/pages | Get pages for label
[**getPageById**](PageApi.md#getPageById) | **GET** /pages/{id} | Get page by id
[**getPages**](PageApi.md#getPages) | **GET** /pages | Get pages
[**getPagesInSpace**](PageApi.md#getPagesInSpace) | **GET** /spaces/{id}/pages | Get pages in space
[**updatePage**](PageApi.md#updatePage) | **PUT** /pages/{id} | Update page

<a name="createPage"></a>
# **createPage**
> InlineResponse2002 createPage(body, embedded, _private, rootLevel)

Create page

Creates a page in the space.  Pages are created as published by default unless specified as a draft in the status field. If creating a published page, the title must be specified.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the corresponding space. Permission to create a page in the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.PageApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

PageApi apiInstance = new PageApi();
Object body = null; // Object | 
Boolean embedded = false; // Boolean | Tag the content as embedded and content will be created in NCS.
Boolean _private = false; // Boolean | The page will be private. Only the user who creates this page will have permission to view and edit one.
Boolean rootLevel = false; // Boolean | The page will be created at the root level of the space (outside the space homepage tree). If true, then a  value may not be supplied for the `parentId` body parameter.
try {
    InlineResponse2002 result = apiInstance.createPage(body, embedded, _private, rootLevel);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PageApi#createPage");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Object**](Object.md)|  |
 **embedded** | **Boolean**| Tag the content as embedded and content will be created in NCS. | [optional] [default to false]
 **_private** | **Boolean**| The page will be private. Only the user who creates this page will have permission to view and edit one. | [optional] [default to false]
 **rootLevel** | **Boolean**| The page will be created at the root level of the space (outside the space homepage tree). If true, then a  value may not be supplied for the &#x60;parentId&#x60; body parameter. | [optional] [default to false]

### Return type

[**InlineResponse2002**](InlineResponse2002.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deletePage"></a>
# **deletePage**
> deletePage(id, purge, draft)

Delete page

Delete a page by id.  By default this will delete pages that are non-drafts. To delete a page that is a draft, the endpoint must be called on a  draft with the following param &#x60;draft&#x3D;true&#x60;. Discarded drafts are not sent to the trash and are permanently deleted.  Deleting a page moves the page to the trash, where it can be restored later. To permanently delete a page (or \&quot;purge\&quot; it), the endpoint must be called on a **trashed** page with the following param &#x60;purge&#x3D;true&#x60;.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the page and its corresponding space. Permission to delete pages in the space. Permission to administer the space (if attempting to purge).

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.PageApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

PageApi apiInstance = new PageApi();
Long id = 789L; // Long | The ID of the page to be deleted.
Boolean purge = false; // Boolean | If attempting to purge the page.
Boolean draft = false; // Boolean | If attempting to delete a page that is a draft.
try {
    apiInstance.deletePage(id, purge, draft);
} catch (ApiException e) {
    System.err.println("Exception when calling PageApi#deletePage");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the page to be deleted. |
 **purge** | **Boolean**| If attempting to purge the page. | [optional] [default to false]
 **draft** | **Boolean**| If attempting to delete a page that is a draft. | [optional] [default to false]

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="getLabelPages"></a>
# **getLabelPages**
> MultiEntityResultPage getLabelPages(id, spaceId, bodyFormat, sort, cursor, limit)

Get pages for label

Returns the pages of specified label. The number of results is limited by the &#x60;limit&#x60; parameter and additional results (if available) will be available through the &#x60;next&#x60; URL present in the &#x60;Link&#x60; response header.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content of the page and its corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.PageApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

PageApi apiInstance = new PageApi();
Long id = 789L; // Long | The ID of the label for which pages should be returned.
List<Long> spaceId = Arrays.asList(56L); // List<Long> | Filter the results based on space ids. Multiple space ids can be specified as a comma-separated list.
PrimaryBodyRepresentation bodyFormat = new PrimaryBodyRepresentation(); // PrimaryBodyRepresentation | The content format types to be returned in the `body` field of the response. If available, the representation will be available under a response field of the same name under the `body` field.
PageSortOrder sort = new PageSortOrder(); // PageSortOrder | Used to sort the result by a particular field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of pages per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultPage result = apiInstance.getLabelPages(id, spaceId, bodyFormat, sort, cursor, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PageApi#getLabelPages");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the label for which pages should be returned. |
 **spaceId** | [**List&lt;Long&gt;**](Long.md)| Filter the results based on space ids. Multiple space ids can be specified as a comma-separated list. | [optional]
 **bodyFormat** | [**PrimaryBodyRepresentation**](.md)| The content format types to be returned in the &#x60;body&#x60; field of the response. If available, the representation will be available under a response field of the same name under the &#x60;body&#x60; field. | [optional]
 **sort** | [**PageSortOrder**](.md)| Used to sort the result by a particular field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of pages per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultPage**](MultiEntityResultPage.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getPageById"></a>
# **getPageById**
> InlineResponse2002 getPageById(id, bodyFormat, getDraft, status, version, includeLabels, includeProperties, includeOperations, includeLikes, includeVersions, includeVersion, includeFavoritedByCurrentUserStatus)

Get page by id

Returns a specific page.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the page and its corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.PageApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

PageApi apiInstance = new PageApi();
Long id = 789L; // Long | The ID of the page to be returned. If you don't know the page ID, use Get pages and filter the results.
PrimaryBodyRepresentationSingle bodyFormat = new PrimaryBodyRepresentationSingle(); // PrimaryBodyRepresentationSingle | The content format types to be returned in the `body` field of the response. If available, the representation will be available under a response field of the same name under the `body` field.
Boolean getDraft = false; // Boolean | Retrieve the draft version of this page.
List<String> status = Arrays.asList("status_example"); // List<String> | Filter the page being retrieved by its status.
Integer version = 56; // Integer | Allows you to retrieve a previously published version. Specify the previous version's number to retrieve its details.
Boolean includeLabels = false; // Boolean | Includes labels associated with this page in the response. The number of results will be limited to 50 and sorted in the default sort order.  A `meta` and `_links` property will be present to indicate if more results are available and a link to retrieve the rest of the results.
Boolean includeProperties = false; // Boolean | Includes content properties associated with this page in the response. The number of results will be limited to 50 and sorted in the default sort order.  A `meta` and `_links` property will be present to indicate if more results are available and a link to retrieve the rest of the results.
Boolean includeOperations = false; // Boolean | Includes operations associated with this page in the response. The number of results will be limited to 50 and sorted in the default sort order.  A `meta` and `_links` property will be present to indicate if more results are available and a link to retrieve the rest of the results.
Boolean includeLikes = false; // Boolean | Includes likes associated with this page in the response. The number of results will be limited to 50 and sorted in the default sort order.  A `meta` and `_links` property will be present to indicate if more results are available and a link to retrieve the rest of the results.
Boolean includeVersions = false; // Boolean | Includes versions associated with this page in the response. The number of results will be limited to 50 and sorted in the default sort order.  A `meta` and `_links` property will be present to indicate if more results are available and a link to retrieve the rest of the results.
Boolean includeVersion = true; // Boolean | Includes the current version associated with this page in the response. By default this is included and can be omitted by setting the value to `false`.
Boolean includeFavoritedByCurrentUserStatus = false; // Boolean | Includes whether this page has been favorited by the current user.
try {
    InlineResponse2002 result = apiInstance.getPageById(id, bodyFormat, getDraft, status, version, includeLabels, includeProperties, includeOperations, includeLikes, includeVersions, includeVersion, includeFavoritedByCurrentUserStatus);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PageApi#getPageById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the page to be returned. If you don&#x27;t know the page ID, use Get pages and filter the results. |
 **bodyFormat** | [**PrimaryBodyRepresentationSingle**](.md)| The content format types to be returned in the &#x60;body&#x60; field of the response. If available, the representation will be available under a response field of the same name under the &#x60;body&#x60; field. | [optional]
 **getDraft** | **Boolean**| Retrieve the draft version of this page. | [optional] [default to false]
 **status** | [**List&lt;String&gt;**](String.md)| Filter the page being retrieved by its status. | [optional] [enum: current, archived, trashed, deleted, historical, draft]
 **version** | **Integer**| Allows you to retrieve a previously published version. Specify the previous version&#x27;s number to retrieve its details. | [optional]
 **includeLabels** | **Boolean**| Includes labels associated with this page in the response. The number of results will be limited to 50 and sorted in the default sort order.  A &#x60;meta&#x60; and &#x60;_links&#x60; property will be present to indicate if more results are available and a link to retrieve the rest of the results. | [optional] [default to false]
 **includeProperties** | **Boolean**| Includes content properties associated with this page in the response. The number of results will be limited to 50 and sorted in the default sort order.  A &#x60;meta&#x60; and &#x60;_links&#x60; property will be present to indicate if more results are available and a link to retrieve the rest of the results. | [optional] [default to false]
 **includeOperations** | **Boolean**| Includes operations associated with this page in the response. The number of results will be limited to 50 and sorted in the default sort order.  A &#x60;meta&#x60; and &#x60;_links&#x60; property will be present to indicate if more results are available and a link to retrieve the rest of the results. | [optional] [default to false]
 **includeLikes** | **Boolean**| Includes likes associated with this page in the response. The number of results will be limited to 50 and sorted in the default sort order.  A &#x60;meta&#x60; and &#x60;_links&#x60; property will be present to indicate if more results are available and a link to retrieve the rest of the results. | [optional] [default to false]
 **includeVersions** | **Boolean**| Includes versions associated with this page in the response. The number of results will be limited to 50 and sorted in the default sort order.  A &#x60;meta&#x60; and &#x60;_links&#x60; property will be present to indicate if more results are available and a link to retrieve the rest of the results. | [optional] [default to false]
 **includeVersion** | **Boolean**| Includes the current version associated with this page in the response. By default this is included and can be omitted by setting the value to &#x60;false&#x60;. | [optional] [default to true]
 **includeFavoritedByCurrentUserStatus** | **Boolean**| Includes whether this page has been favorited by the current user. | [optional] [default to false]

### Return type

[**InlineResponse2002**](InlineResponse2002.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getPages"></a>
# **getPages**
> MultiEntityResultPage getPages(id, spaceId, sort, status, title, bodyFormat, cursor, limit)

Get pages

Returns all pages. The number of results is limited by the &#x60;limit&#x60; parameter and additional results (if available) will be available through the &#x60;next&#x60; URL present in the &#x60;Link&#x60; response header.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to access the Confluence site (&#x27;Can use&#x27; global permission). Only pages that the user has permission to view will be returned.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.PageApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

PageApi apiInstance = new PageApi();
List<Long> id = Arrays.asList(56L); // List<Long> | Filter the results based on page ids. Multiple page ids can be specified as a comma-separated list.
List<Long> spaceId = Arrays.asList(56L); // List<Long> | Filter the results based on space ids. Multiple space ids can be specified as a comma-separated list.
PageSortOrder sort = new PageSortOrder(); // PageSortOrder | Used to sort the result by a particular field.
List<String> status = Arrays.asList("status_example"); // List<String> | Filter the results to pages based on their status. By default, `current` and `archived` are used.
String title = "title_example"; // String | Filter the results to pages based on their title.
PrimaryBodyRepresentation bodyFormat = new PrimaryBodyRepresentation(); // PrimaryBodyRepresentation | The content format types to be returned in the `body` field of the response. If available, the representation will be available under a response field of the same name under the `body` field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of pages per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultPage result = apiInstance.getPages(id, spaceId, sort, status, title, bodyFormat, cursor, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PageApi#getPages");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | [**List&lt;Long&gt;**](Long.md)| Filter the results based on page ids. Multiple page ids can be specified as a comma-separated list. | [optional]
 **spaceId** | [**List&lt;Long&gt;**](Long.md)| Filter the results based on space ids. Multiple space ids can be specified as a comma-separated list. | [optional]
 **sort** | [**PageSortOrder**](.md)| Used to sort the result by a particular field. | [optional]
 **status** | [**List&lt;String&gt;**](String.md)| Filter the results to pages based on their status. By default, &#x60;current&#x60; and &#x60;archived&#x60; are used. | [optional] [enum: current, archived, deleted, trashed]
 **title** | **String**| Filter the results to pages based on their title. | [optional]
 **bodyFormat** | [**PrimaryBodyRepresentation**](.md)| The content format types to be returned in the &#x60;body&#x60; field of the response. If available, the representation will be available under a response field of the same name under the &#x60;body&#x60; field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of pages per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultPage**](MultiEntityResultPage.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getPagesInSpace"></a>
# **getPagesInSpace**
> MultiEntityResultPage getPagesInSpace(id, depth, sort, status, title, bodyFormat, cursor, limit)

Get pages in space

Returns all pages in a space. The number of results is limited by the &#x60;limit&#x60; parameter and additional results (if available) will be available through the &#x60;next&#x60; URL present in the &#x60;Link&#x60; response header.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to access the Confluence site (&#x27;Can use&#x27; global permission) and &#x27;View&#x27; permission for the space. Only pages that the user has permission to view will be returned.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.PageApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

PageApi apiInstance = new PageApi();
Long id = 789L; // Long | The ID of the space for which pages should be returned.
String depth = "all"; // String | Filter the results to pages at the root level of the space or to all pages in the space.
PageSortOrder sort = new PageSortOrder(); // PageSortOrder | Used to sort the result by a particular field.
List<String> status = Arrays.asList("status_example"); // List<String> | Filter the results to pages based on their status. By default, `current` and `archived` are used.
String title = "title_example"; // String | Filter the results to pages based on their title.
PrimaryBodyRepresentation bodyFormat = new PrimaryBodyRepresentation(); // PrimaryBodyRepresentation | The content format types to be returned in the `body` field of the response. If available, the representation will be available under a response field of the same name under the `body` field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of pages per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultPage result = apiInstance.getPagesInSpace(id, depth, sort, status, title, bodyFormat, cursor, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PageApi#getPagesInSpace");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the space for which pages should be returned. |
 **depth** | **String**| Filter the results to pages at the root level of the space or to all pages in the space. | [optional] [default to all] [enum: all, root]
 **sort** | [**PageSortOrder**](.md)| Used to sort the result by a particular field. | [optional]
 **status** | [**List&lt;String&gt;**](String.md)| Filter the results to pages based on their status. By default, &#x60;current&#x60; and &#x60;archived&#x60; are used. | [optional] [enum: current, archived, deleted, trashed]
 **title** | **String**| Filter the results to pages based on their title. | [optional]
 **bodyFormat** | [**PrimaryBodyRepresentation**](.md)| The content format types to be returned in the &#x60;body&#x60; field of the response. If available, the representation will be available under a response field of the same name under the &#x60;body&#x60; field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of pages per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultPage**](MultiEntityResultPage.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updatePage"></a>
# **updatePage**
> InlineResponse2002 updatePage(body, id)

Update page

Update a page by id.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the page and its corresponding space. Permission to update pages in the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.PageApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

PageApi apiInstance = new PageApi();
Object body = null; // Object | 
Long id = 789L; // Long | The ID of the page to be updated. If you don't know the page ID, use Get Pages and filter the results.
try {
    InlineResponse2002 result = apiInstance.updatePage(body, id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PageApi#updatePage");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Object**](Object.md)|  |
 **id** | **Long**| The ID of the page to be updated. If you don&#x27;t know the page ID, use Get Pages and filter the results. |

### Return type

[**InlineResponse2002**](InlineResponse2002.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

