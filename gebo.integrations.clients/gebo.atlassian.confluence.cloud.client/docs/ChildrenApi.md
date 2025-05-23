# ChildrenApi

All URIs are relative to *https://{your-domain}/wiki/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getChildCustomContent**](ChildrenApi.md#getChildCustomContent) | **GET** /custom-content/{id}/children | Get child custom content
[**getChildPages**](ChildrenApi.md#getChildPages) | **GET** /pages/{id}/children | Get child pages

<a name="getChildCustomContent"></a>
# **getChildCustomContent**
> MultiEntityResultChildCustomContent getChildCustomContent(id, cursor, limit, sort)

Get child custom content

Returns all child custom content for given custom content id. The number of results is limited by the &#x60;limit&#x60; parameter and additional results (if available) will be available through the &#x60;next&#x60; URL present in the &#x60;Link&#x60; response header.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to access the Confluence site (&#x27;Can use&#x27; global permission). Only custom content that the user has permission to view will be returned.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ChildrenApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ChildrenApi apiInstance = new ChildrenApi();
Long id = 789L; // Long | The ID of the parent custom content. If you don't know the custom content ID, use Get custom-content and filter the results.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of pages per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
List<ChildCustomContentSortOrder> sort = Arrays.asList(new ChildCustomContentSortOrder()); // List<ChildCustomContentSortOrder> | Used to sort the result by a particular field.
try {
    MultiEntityResultChildCustomContent result = apiInstance.getChildCustomContent(id, cursor, limit, sort);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ChildrenApi#getChildCustomContent");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the parent custom content. If you don&#x27;t know the custom content ID, use Get custom-content and filter the results. |
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of pages per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]
 **sort** | [**List&lt;ChildCustomContentSortOrder&gt;**](ChildCustomContentSortOrder.md)| Used to sort the result by a particular field. | [optional]

### Return type

[**MultiEntityResultChildCustomContent**](MultiEntityResultChildCustomContent.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getChildPages"></a>
# **getChildPages**
> MultiEntityResultChildPage getChildPages(id, cursor, limit, sort)

Get child pages

Returns all child pages for given page id. The number of results is limited by the &#x60;limit&#x60; parameter and additional results (if available) will be available through the &#x60;next&#x60; URL present in the &#x60;Link&#x60; response header.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to access the Confluence site (&#x27;Can use&#x27; global permission). Only pages that the user has permission to view will be returned.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ChildrenApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ChildrenApi apiInstance = new ChildrenApi();
Long id = 789L; // Long | The ID of the parent page. If you don't know the page ID, use Get pages and filter the results.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of pages per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
List<ChildPageSortOrder> sort = Arrays.asList(new ChildPageSortOrder()); // List<ChildPageSortOrder> | Used to sort the result by a particular field.
try {
    MultiEntityResultChildPage result = apiInstance.getChildPages(id, cursor, limit, sort);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ChildrenApi#getChildPages");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the parent page. If you don&#x27;t know the page ID, use Get pages and filter the results. |
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of pages per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]
 **sort** | [**List&lt;ChildPageSortOrder&gt;**](ChildPageSortOrder.md)| Used to sort the result by a particular field. | [optional]

### Return type

[**MultiEntityResultChildPage**](MultiEntityResultChildPage.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

