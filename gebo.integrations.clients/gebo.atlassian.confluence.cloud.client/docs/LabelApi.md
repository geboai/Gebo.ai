# LabelApi

All URIs are relative to *https://{your-domain}/wiki/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAttachmentLabels**](LabelApi.md#getAttachmentLabels) | **GET** /attachments/{id}/labels | Get labels for attachment
[**getBlogPostLabels**](LabelApi.md#getBlogPostLabels) | **GET** /blogposts/{id}/labels | Get labels for blog post
[**getCustomContentLabels**](LabelApi.md#getCustomContentLabels) | **GET** /custom-content/{id}/labels | Get labels for custom content
[**getLabels**](LabelApi.md#getLabels) | **GET** /labels | Get labels
[**getPageLabels**](LabelApi.md#getPageLabels) | **GET** /pages/{id}/labels | Get labels for page
[**getSpaceContentLabels**](LabelApi.md#getSpaceContentLabels) | **GET** /spaces/{id}/content/labels | Get labels for space content
[**getSpaceLabels**](LabelApi.md#getSpaceLabels) | **GET** /spaces/{id}/labels | Get labels for space

<a name="getAttachmentLabels"></a>
# **getAttachmentLabels**
> MultiEntityResultLabel getAttachmentLabels(id, prefix, sort, cursor, limit)

Get labels for attachment

Returns the labels of specific attachment. The number of results is limited by the &#x60;limit&#x60; parameter and additional results (if available) will be available through the &#x60;next&#x60; URL present in the &#x60;Link&#x60; response header.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the parent content of the attachment and its corresponding space. Only labels that the user has permission to view will be returned.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.LabelApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

LabelApi apiInstance = new LabelApi();
Long id = 789L; // Long | The ID of the attachment for which labels should be returned.
String prefix = "prefix_example"; // String | Filter the results to labels based on their prefix.
List<LabelSortOrder> sort = Arrays.asList(new LabelSortOrder()); // List<LabelSortOrder> | Used to sort the result by a particular field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of labels per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultLabel result = apiInstance.getAttachmentLabels(id, prefix, sort, cursor, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling LabelApi#getAttachmentLabels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the attachment for which labels should be returned. |
 **prefix** | **String**| Filter the results to labels based on their prefix. | [optional] [enum: my, team, global, system]
 **sort** | [**List&lt;LabelSortOrder&gt;**](LabelSortOrder.md)| Used to sort the result by a particular field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of labels per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultLabel**](MultiEntityResultLabel.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getBlogPostLabels"></a>
# **getBlogPostLabels**
> MultiEntityResultLabel getBlogPostLabels(id, prefix, sort, cursor, limit)

Get labels for blog post

Returns the labels of specific blog post. The number of results is limited by the &#x60;limit&#x60; parameter and additional results (if available) will be available through the &#x60;next&#x60; URL present in the &#x60;Link&#x60; response header.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content of the blog post and its corresponding space. Only labels that the user has permission to view will be returned.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.LabelApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

LabelApi apiInstance = new LabelApi();
Long id = 789L; // Long | The ID of the blog post for which labels should be returned.
String prefix = "prefix_example"; // String | Filter the results to labels based on their prefix.
List<LabelSortOrder> sort = Arrays.asList(new LabelSortOrder()); // List<LabelSortOrder> | Used to sort the result by a particular field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of labels per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultLabel result = apiInstance.getBlogPostLabels(id, prefix, sort, cursor, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling LabelApi#getBlogPostLabels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the blog post for which labels should be returned. |
 **prefix** | **String**| Filter the results to labels based on their prefix. | [optional] [enum: my, team, global, system]
 **sort** | [**List&lt;LabelSortOrder&gt;**](LabelSortOrder.md)| Used to sort the result by a particular field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of labels per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultLabel**](MultiEntityResultLabel.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getCustomContentLabels"></a>
# **getCustomContentLabels**
> MultiEntityResultLabel getCustomContentLabels(id, prefix, sort, cursor, limit)

Get labels for custom content

Returns the labels for a specific piece of custom content. The number of results is limited by the &#x60;limit&#x60; parameter and additional results (if available) will be available through the &#x60;next&#x60; URL present in the &#x60;Link&#x60; response header.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the custom content and its corresponding space. Only labels that the user has permission to view will be returned.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.LabelApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

LabelApi apiInstance = new LabelApi();
Long id = 789L; // Long | The ID of the custom content for which labels should be returned.
String prefix = "prefix_example"; // String | Filter the results to labels based on their prefix.
List<LabelSortOrder> sort = Arrays.asList(new LabelSortOrder()); // List<LabelSortOrder> | Used to sort the result by a particular field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of labels per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultLabel result = apiInstance.getCustomContentLabels(id, prefix, sort, cursor, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling LabelApi#getCustomContentLabels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the custom content for which labels should be returned. |
 **prefix** | **String**| Filter the results to labels based on their prefix. | [optional] [enum: my, team, global, system]
 **sort** | [**List&lt;LabelSortOrder&gt;**](LabelSortOrder.md)| Used to sort the result by a particular field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of labels per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultLabel**](MultiEntityResultLabel.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getLabels"></a>
# **getLabels**
> MultiEntityResultLabel getLabels(labelId, prefix, cursor, sort, limit)

Get labels

Returns all labels. The number of results is limited by the &#x60;limit&#x60; parameter and additional results (if available) will be available through the &#x60;next&#x60; URL present in the &#x60;Link&#x60; response header.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to access the Confluence site (&#x27;Can use&#x27; global permission). Only labels that the user has permission to view will be returned.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.LabelApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

LabelApi apiInstance = new LabelApi();
List<Long> labelId = Arrays.asList(56L); // List<Long> | Filters on label ID. Multiple IDs can be specified as a comma-separated list.
List<String> prefix = Arrays.asList("prefix_example"); // List<String> | Filters on label prefix. Multiple IDs can be specified as a comma-separated list.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
List<LabelSortOrder> sort = Arrays.asList(new LabelSortOrder()); // List<LabelSortOrder> | Used to sort the result by a particular field.
Integer limit = 25; // Integer | Maximum number of labels per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultLabel result = apiInstance.getLabels(labelId, prefix, cursor, sort, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling LabelApi#getLabels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **labelId** | [**List&lt;Long&gt;**](Long.md)| Filters on label ID. Multiple IDs can be specified as a comma-separated list. | [optional]
 **prefix** | [**List&lt;String&gt;**](String.md)| Filters on label prefix. Multiple IDs can be specified as a comma-separated list. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **sort** | [**List&lt;LabelSortOrder&gt;**](LabelSortOrder.md)| Used to sort the result by a particular field. | [optional]
 **limit** | **Integer**| Maximum number of labels per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultLabel**](MultiEntityResultLabel.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getPageLabels"></a>
# **getPageLabels**
> MultiEntityResultLabel getPageLabels(id, prefix, sort, cursor, limit)

Get labels for page

Returns the labels of specific page. The number of results is limited by the &#x60;limit&#x60; parameter and additional results (if available) will be available through the &#x60;next&#x60; URL present in the &#x60;Link&#x60; response header.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content of the page and its corresponding space. Only labels that the user has permission to view will be returned.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.LabelApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

LabelApi apiInstance = new LabelApi();
Long id = 789L; // Long | The ID of the page for which labels should be returned.
String prefix = "prefix_example"; // String | Filter the results to labels based on their prefix.
List<LabelSortOrder> sort = Arrays.asList(new LabelSortOrder()); // List<LabelSortOrder> | Used to sort the result by a particular field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of labels per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultLabel result = apiInstance.getPageLabels(id, prefix, sort, cursor, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling LabelApi#getPageLabels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the page for which labels should be returned. |
 **prefix** | **String**| Filter the results to labels based on their prefix. | [optional] [enum: my, team, global, system]
 **sort** | [**List&lt;LabelSortOrder&gt;**](LabelSortOrder.md)| Used to sort the result by a particular field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of labels per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultLabel**](MultiEntityResultLabel.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getSpaceContentLabels"></a>
# **getSpaceContentLabels**
> MultiEntityResultLabel getSpaceContentLabels(id, prefix, sort, cursor, limit)

Get labels for space content

Returns the labels of space content (pages, blogposts etc). The number of results is limited by the &#x60;limit&#x60; parameter and additional results (if available) will be available through the &#x60;next&#x60; URL present in the &#x60;Link&#x60; response header.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the space. Only labels that the user has permission to view will be returned.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.LabelApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

LabelApi apiInstance = new LabelApi();
Long id = 789L; // Long | The ID of the space for which labels should be returned.
String prefix = "my, team"; // String | Filter the results to labels based on their prefix.
List<LabelSortOrder> sort = Arrays.asList(new LabelSortOrder()); // List<LabelSortOrder> | Used to sort the result by a particular field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of labels per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultLabel result = apiInstance.getSpaceContentLabels(id, prefix, sort, cursor, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling LabelApi#getSpaceContentLabels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the space for which labels should be returned. |
 **prefix** | **String**| Filter the results to labels based on their prefix. | [optional] [default to my, team] [enum: my, team]
 **sort** | [**List&lt;LabelSortOrder&gt;**](LabelSortOrder.md)| Used to sort the result by a particular field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of labels per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultLabel**](MultiEntityResultLabel.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getSpaceLabels"></a>
# **getSpaceLabels**
> MultiEntityResultLabel getSpaceLabels(id, prefix, sort, cursor, limit)

Get labels for space

Returns the labels of specific space. The number of results is limited by the &#x60;limit&#x60; parameter and additional results (if available) will be available through the &#x60;next&#x60; URL present in the &#x60;Link&#x60; response header.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the space. Only labels that the user has permission to view will be returned.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.LabelApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

LabelApi apiInstance = new LabelApi();
Long id = 789L; // Long | The ID of the space for which labels should be returned.
String prefix = "my, team"; // String | Filter the results to labels based on their prefix.
List<LabelSortOrder> sort = Arrays.asList(new LabelSortOrder()); // List<LabelSortOrder> | Used to sort the result by a particular field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of labels per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultLabel result = apiInstance.getSpaceLabels(id, prefix, sort, cursor, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling LabelApi#getSpaceLabels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the space for which labels should be returned. |
 **prefix** | **String**| Filter the results to labels based on their prefix. | [optional] [default to my, team] [enum: my, team]
 **sort** | [**List&lt;LabelSortOrder&gt;**](LabelSortOrder.md)| Used to sort the result by a particular field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of labels per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultLabel**](MultiEntityResultLabel.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

