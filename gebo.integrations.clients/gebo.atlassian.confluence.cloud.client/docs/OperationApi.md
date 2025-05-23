# OperationApi

All URIs are relative to *https://{your-domain}/wiki/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAttachmentOperations**](OperationApi.md#getAttachmentOperations) | **GET** /attachments/{id}/operations | Get permitted operations for attachment
[**getBlogPostOperations**](OperationApi.md#getBlogPostOperations) | **GET** /blogposts/{id}/operations | Get permitted operations for blog post
[**getCustomContentOperations**](OperationApi.md#getCustomContentOperations) | **GET** /custom-content/{id}/operations | Get permitted operations for custom content
[**getDatabaseOperations**](OperationApi.md#getDatabaseOperations) | **GET** /databases/{id}/operations | Get permitted operations for a database
[**getFolderOperations**](OperationApi.md#getFolderOperations) | **GET** /folders/{id}/operations | Get permitted operations for a folder
[**getFooterCommentOperations**](OperationApi.md#getFooterCommentOperations) | **GET** /footer-comments/{id}/operations | Get permitted operations for footer comment
[**getInlineCommentOperations**](OperationApi.md#getInlineCommentOperations) | **GET** /inline-comments/{id}/operations | Get permitted operations for inline comment
[**getPageOperations**](OperationApi.md#getPageOperations) | **GET** /pages/{id}/operations | Get permitted operations for page
[**getSmartLinkOperations**](OperationApi.md#getSmartLinkOperations) | **GET** /embeds/{id}/operations | Get permitted operations for a Smart Link in the content tree
[**getSpaceOperations**](OperationApi.md#getSpaceOperations) | **GET** /spaces/{id}/operations | Get permitted operations for space
[**getWhiteboardOperations**](OperationApi.md#getWhiteboardOperations) | **GET** /whiteboards/{id}/operations | Get permitted operations for a whiteboard

<a name="getAttachmentOperations"></a>
# **getAttachmentOperations**
> PermittedOperationsResponse getAttachmentOperations(id)

Get permitted operations for attachment

Returns the permitted operations on specific attachment.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the parent content of the attachment and its corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.OperationApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

OperationApi apiInstance = new OperationApi();
String id = "id_example"; // String | The ID of the attachment for which operations should be returned.
try {
    PermittedOperationsResponse result = apiInstance.getAttachmentOperations(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OperationApi#getAttachmentOperations");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The ID of the attachment for which operations should be returned. |

### Return type

[**PermittedOperationsResponse**](PermittedOperationsResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getBlogPostOperations"></a>
# **getBlogPostOperations**
> PermittedOperationsResponse getBlogPostOperations(id)

Get permitted operations for blog post

Returns the permitted operations on specific blog post.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the parent content of the blog post and its corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.OperationApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

OperationApi apiInstance = new OperationApi();
Long id = 789L; // Long | The ID of the blog post for which operations should be returned.
try {
    PermittedOperationsResponse result = apiInstance.getBlogPostOperations(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OperationApi#getBlogPostOperations");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the blog post for which operations should be returned. |

### Return type

[**PermittedOperationsResponse**](PermittedOperationsResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getCustomContentOperations"></a>
# **getCustomContentOperations**
> PermittedOperationsResponse getCustomContentOperations(id)

Get permitted operations for custom content

Returns the permitted operations on specific custom content.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the parent content of the custom content and its corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.OperationApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

OperationApi apiInstance = new OperationApi();
Long id = 789L; // Long | The ID of the custom content for which operations should be returned.
try {
    PermittedOperationsResponse result = apiInstance.getCustomContentOperations(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OperationApi#getCustomContentOperations");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the custom content for which operations should be returned. |

### Return type

[**PermittedOperationsResponse**](PermittedOperationsResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getDatabaseOperations"></a>
# **getDatabaseOperations**
> PermittedOperationsResponse getDatabaseOperations(id)

Get permitted operations for a database

Returns the permitted operations on specific database.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the database and its corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.OperationApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

OperationApi apiInstance = new OperationApi();
Long id = 789L; // Long | The ID of the database for which operations should be returned.
try {
    PermittedOperationsResponse result = apiInstance.getDatabaseOperations(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OperationApi#getDatabaseOperations");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the database for which operations should be returned. |

### Return type

[**PermittedOperationsResponse**](PermittedOperationsResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getFolderOperations"></a>
# **getFolderOperations**
> PermittedOperationsResponse getFolderOperations(id)

Get permitted operations for a folder

Returns the permitted operations on specific folder.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the folder and its corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.OperationApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

OperationApi apiInstance = new OperationApi();
Long id = 789L; // Long | The ID of the folder for which operations should be returned.
try {
    PermittedOperationsResponse result = apiInstance.getFolderOperations(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OperationApi#getFolderOperations");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the folder for which operations should be returned. |

### Return type

[**PermittedOperationsResponse**](PermittedOperationsResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getFooterCommentOperations"></a>
# **getFooterCommentOperations**
> PermittedOperationsResponse getFooterCommentOperations(id)

Get permitted operations for footer comment

Returns the permitted operations on specific footer comment.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the parent content of the footer comment and its corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.OperationApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

OperationApi apiInstance = new OperationApi();
Long id = 789L; // Long | The ID of the footer comment for which operations should be returned.
try {
    PermittedOperationsResponse result = apiInstance.getFooterCommentOperations(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OperationApi#getFooterCommentOperations");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the footer comment for which operations should be returned. |

### Return type

[**PermittedOperationsResponse**](PermittedOperationsResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getInlineCommentOperations"></a>
# **getInlineCommentOperations**
> PermittedOperationsResponse getInlineCommentOperations(id)

Get permitted operations for inline comment

Returns the permitted operations on specific inline comment.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the parent content of the inline comment and its corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.OperationApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

OperationApi apiInstance = new OperationApi();
Long id = 789L; // Long | The ID of the inline comment for which operations should be returned.
try {
    PermittedOperationsResponse result = apiInstance.getInlineCommentOperations(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OperationApi#getInlineCommentOperations");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the inline comment for which operations should be returned. |

### Return type

[**PermittedOperationsResponse**](PermittedOperationsResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getPageOperations"></a>
# **getPageOperations**
> PermittedOperationsResponse getPageOperations(id)

Get permitted operations for page

Returns the permitted operations on specific page.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the parent content of the page and its corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.OperationApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

OperationApi apiInstance = new OperationApi();
Long id = 789L; // Long | The ID of the page for which operations should be returned.
try {
    PermittedOperationsResponse result = apiInstance.getPageOperations(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OperationApi#getPageOperations");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the page for which operations should be returned. |

### Return type

[**PermittedOperationsResponse**](PermittedOperationsResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getSmartLinkOperations"></a>
# **getSmartLinkOperations**
> PermittedOperationsResponse getSmartLinkOperations(id)

Get permitted operations for a Smart Link in the content tree

Returns the permitted operations on specific Smart Link in the content tree.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the Smart Link in the content tree and its corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.OperationApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

OperationApi apiInstance = new OperationApi();
Long id = 789L; // Long | The ID of the Smart Link in the content tree for which operations should be returned.
try {
    PermittedOperationsResponse result = apiInstance.getSmartLinkOperations(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OperationApi#getSmartLinkOperations");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the Smart Link in the content tree for which operations should be returned. |

### Return type

[**PermittedOperationsResponse**](PermittedOperationsResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getSpaceOperations"></a>
# **getSpaceOperations**
> PermittedOperationsResponse getSpaceOperations(id)

Get permitted operations for space

Returns the permitted operations on specific space.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.OperationApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

OperationApi apiInstance = new OperationApi();
Long id = 789L; // Long | The ID of the space for which operations should be returned.
try {
    PermittedOperationsResponse result = apiInstance.getSpaceOperations(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OperationApi#getSpaceOperations");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the space for which operations should be returned. |

### Return type

[**PermittedOperationsResponse**](PermittedOperationsResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getWhiteboardOperations"></a>
# **getWhiteboardOperations**
> PermittedOperationsResponse getWhiteboardOperations(id)

Get permitted operations for a whiteboard

Returns the permitted operations on specific whiteboard.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the whiteboard and its corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.OperationApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

OperationApi apiInstance = new OperationApi();
Long id = 789L; // Long | The ID of the whiteboard for which operations should be returned.
try {
    PermittedOperationsResponse result = apiInstance.getWhiteboardOperations(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OperationApi#getWhiteboardOperations");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the whiteboard for which operations should be returned. |

### Return type

[**PermittedOperationsResponse**](PermittedOperationsResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

