# ContentLabelsApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addLabelsToContent**](ContentLabelsApi.md#addLabelsToContent) | **POST** /wiki/rest/api/content/{id}/label | Add labels to content
[**getLabelsForContent**](ContentLabelsApi.md#getLabelsForContent) | **GET** /wiki/rest/api/content/{id}/label | Get labels for content
[**removeLabelFromContent**](ContentLabelsApi.md#removeLabelFromContent) | **DELETE** /wiki/rest/api/content/{id}/label/{label} | Remove label from content
[**removeLabelFromContentUsingQueryParameter**](ContentLabelsApi.md#removeLabelFromContentUsingQueryParameter) | **DELETE** /wiki/rest/api/content/{id}/label | Remove label from content using query parameter

<a name="addLabelsToContent"></a>
# **addLabelsToContent**
> LabelArray addLabelsToContent(body, id)

Add labels to content

Adds labels to a piece of content. Does not modify the existing labels.  Notes:  - Labels can also be added when creating content ([Create content](#api-content-post)). - Labels can be updated when updating content ([Update content](#api-content-id-put)). This will delete the existing labels and replace them with the labels in the request.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to update the content.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentLabelsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentLabelsApi apiInstance = new ContentLabelsApi();
IdLabelBody body = new IdLabelBody(); // IdLabelBody | The labels to add to the content.
String id = "id_example"; // String | The ID of the content that will have labels added to it.
try {
    LabelArray result = apiInstance.addLabelsToContent(body, id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentLabelsApi#addLabelsToContent");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**IdLabelBody**](IdLabelBody.md)| The labels to add to the content. |
 **id** | **String**| The ID of the content that will have labels added to it. |

### Return type

[**LabelArray**](LabelArray.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getLabelsForContent"></a>
# **getLabelsForContent**
> LabelArray getLabelsForContent(id, prefix, start, limit)

Get labels for content

Deprecated, use [Confluence&#x27;s v2 API](https://developer.atlassian.com/cloud/confluence/rest/v2/intro/).  Returns the labels on a piece of content.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;View&#x27; permission for the space and permission to view the content if it is a page.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentLabelsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentLabelsApi apiInstance = new ContentLabelsApi();
String id = "id_example"; // String | The ID of the content to be queried for its labels.
String prefix = "prefix_example"; // String | Filters the results to labels with the specified prefix. If this parameter is not specified, then labels with any prefix will be returned.  - `global` prefix is used by default when a user adds a label via the UI. - `my` prefix can be explicitly added by a user when adding a label via the UI, e.g. 'my:example-label'. Also, when a page is selected as a favourite, the 'my:favourite' label is automatically added. - `team` can used when adding labels via [Add labels to content](#api-content-id-label-post) but is not used in the UI.
Integer start = 0; // Integer | The starting index of the returned labels.
Integer limit = 200; // Integer | The maximum number of labels to return per page. Note, this may be restricted by fixed system limits.
try {
    LabelArray result = apiInstance.getLabelsForContent(id, prefix, start, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentLabelsApi#getLabelsForContent");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The ID of the content to be queried for its labels. |
 **prefix** | **String**| Filters the results to labels with the specified prefix. If this parameter is not specified, then labels with any prefix will be returned.  - &#x60;global&#x60; prefix is used by default when a user adds a label via the UI. - &#x60;my&#x60; prefix can be explicitly added by a user when adding a label via the UI, e.g. &#x27;my:example-label&#x27;. Also, when a page is selected as a favourite, the &#x27;my:favourite&#x27; label is automatically added. - &#x60;team&#x60; can used when adding labels via [Add labels to content](#api-content-id-label-post) but is not used in the UI. | [optional] [enum: global, my, team]
 **start** | **Integer**| The starting index of the returned labels. | [optional] [default to 0] [enum: 0]
 **limit** | **Integer**| The maximum number of labels to return per page. Note, this may be restricted by fixed system limits. | [optional] [default to 200] [enum: 0]

### Return type

[**LabelArray**](LabelArray.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="removeLabelFromContent"></a>
# **removeLabelFromContent**
> removeLabelFromContent(id, label)

Remove label from content

Removes a label from a piece of content. Labels can&#x27;t be deleted from archived content. This is similar to [Remove label from content using query parameter](#api-content-id-label-delete) except that the label name is specified via a path parameter.  Use this method if the label name does not have \&quot;/\&quot; characters, as the path parameter does not accept \&quot;/\&quot; characters for security reasons. Otherwise, use [Remove label from content using query parameter](#api-content-id-label-delete).  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to update the content.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentLabelsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentLabelsApi apiInstance = new ContentLabelsApi();
String id = "id_example"; // String | The ID of the content that the label will be removed from.
String label = "label_example"; // String | The name of the label to be removed.
try {
    apiInstance.removeLabelFromContent(id, label);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentLabelsApi#removeLabelFromContent");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The ID of the content that the label will be removed from. |
 **label** | **String**| The name of the label to be removed. |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="removeLabelFromContentUsingQueryParameter"></a>
# **removeLabelFromContentUsingQueryParameter**
> removeLabelFromContentUsingQueryParameter(id, name)

Remove label from content using query parameter

Removes a label from a piece of content. Labels can&#x27;t be deleted from archived content. This is similar to [Remove label from content](#api-content-id-label-label-delete) except that the label name is specified via a query parameter.  Use this method if the label name has \&quot;/\&quot; characters, as [Remove label from content using query parameter](#api-content-id-label-delete) does not accept \&quot;/\&quot; characters for the label name.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to update the content.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentLabelsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentLabelsApi apiInstance = new ContentLabelsApi();
String id = "id_example"; // String | The ID of the content that the label will be removed from.
String name = "name_example"; // String | The name of the label to be removed.
try {
    apiInstance.removeLabelFromContentUsingQueryParameter(id, name);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentLabelsApi#removeLabelFromContentUsingQueryParameter");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The ID of the content that the label will be removed from. |
 **name** | **String**| The name of the label to be removed. |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

