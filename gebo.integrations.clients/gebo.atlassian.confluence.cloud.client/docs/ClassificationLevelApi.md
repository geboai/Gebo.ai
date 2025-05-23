# ClassificationLevelApi

All URIs are relative to *https://{your-domain}/wiki/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteSpaceDefaultClassificationLevel**](ClassificationLevelApi.md#deleteSpaceDefaultClassificationLevel) | **DELETE** /spaces/{id}/classification-level/default | Delete space default classification level
[**getBlogPostClassificationLevel**](ClassificationLevelApi.md#getBlogPostClassificationLevel) | **GET** /blogposts/{id}/classification-level | Get blog post classification level
[**getClassificationLevels**](ClassificationLevelApi.md#getClassificationLevels) | **GET** /classification-levels | Get list of classification levels
[**getDatabaseClassificationLevel**](ClassificationLevelApi.md#getDatabaseClassificationLevel) | **GET** /databases/{id}/classification-level | Get database classification level
[**getPageClassificationLevel**](ClassificationLevelApi.md#getPageClassificationLevel) | **GET** /pages/{id}/classification-level | Get page classification level
[**getSpaceDefaultClassificationLevel**](ClassificationLevelApi.md#getSpaceDefaultClassificationLevel) | **GET** /spaces/{id}/classification-level/default | Get space default classification level
[**getWhiteboardClassificationLevel**](ClassificationLevelApi.md#getWhiteboardClassificationLevel) | **GET** /whiteboards/{id}/classification-level | Get whiteboard classification level
[**postBlogPostClassificationLevel**](ClassificationLevelApi.md#postBlogPostClassificationLevel) | **POST** /blogposts/{id}/classification-level/reset | Reset blog post classification level
[**postDatabaseClassificationLevel**](ClassificationLevelApi.md#postDatabaseClassificationLevel) | **POST** /databases/{id}/classification-level/reset | Reset database classification level
[**postPageClassificationLevel**](ClassificationLevelApi.md#postPageClassificationLevel) | **POST** /pages/{id}/classification-level/reset | Reset page classification level
[**postWhiteboardClassificationLevel**](ClassificationLevelApi.md#postWhiteboardClassificationLevel) | **POST** /whiteboards/{id}/classification-level/reset | Reset whiteboard classification level
[**putBlogPostClassificationLevel**](ClassificationLevelApi.md#putBlogPostClassificationLevel) | **PUT** /blogposts/{id}/classification-level | Update blog post classification level
[**putDatabaseClassificationLevel**](ClassificationLevelApi.md#putDatabaseClassificationLevel) | **PUT** /databases/{id}/classification-level | Update database classification level
[**putPageClassificationLevel**](ClassificationLevelApi.md#putPageClassificationLevel) | **PUT** /pages/{id}/classification-level | Update page classification level
[**putSpaceDefaultClassificationLevel**](ClassificationLevelApi.md#putSpaceDefaultClassificationLevel) | **PUT** /spaces/{id}/classification-level/default | Update space default classification level
[**putWhiteboardClassificationLevel**](ClassificationLevelApi.md#putWhiteboardClassificationLevel) | **PUT** /whiteboards/{id}/classification-level | Update whiteboard classification level

<a name="deleteSpaceDefaultClassificationLevel"></a>
# **deleteSpaceDefaultClassificationLevel**
> deleteSpaceDefaultClassificationLevel(id)

Delete space default classification level

Returns the [default classification level](https://support.atlassian.com/security-and-access-policies/docs/what-is-a-default-classification-level/)  for a specific space.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Permission to access the Confluence site (&#x27;Can use&#x27; global permission) and &#x27;Admin&#x27; permission for the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ClassificationLevelApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ClassificationLevelApi apiInstance = new ClassificationLevelApi();
Long id = 789L; // Long | The ID of the space for which default classification level should be deleted.
try {
    apiInstance.deleteSpaceDefaultClassificationLevel(id);
} catch (ApiException e) {
    System.err.println("Exception when calling ClassificationLevelApi#deleteSpaceDefaultClassificationLevel");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the space for which default classification level should be deleted. |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="getBlogPostClassificationLevel"></a>
# **getBlogPostClassificationLevel**
> ClassificationLevel getBlogPostClassificationLevel(id, status)

Get blog post classification level

Returns the [classification level](https://developer.atlassian.com/cloud/admin/dlp/rest/intro/#Classification%20level) for a specific blog post.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Permission to access the Confluence site (&#x27;Can use&#x27; global permission) and permission to view the blog post. &#x27;Permission to edit the blog post is required if trying to view classification level for a draft.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ClassificationLevelApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ClassificationLevelApi apiInstance = new ClassificationLevelApi();
Long id = 789L; // Long | The ID of the blog post for which classification level should be returned.
String status = "current"; // String | Status of blog post from which classification level will fetched.
try {
    ClassificationLevel result = apiInstance.getBlogPostClassificationLevel(id, status);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ClassificationLevelApi#getBlogPostClassificationLevel");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the blog post for which classification level should be returned. |
 **status** | **String**| Status of blog post from which classification level will fetched. | [optional] [default to current] [enum: current, draft, archived]

### Return type

[**ClassificationLevel**](ClassificationLevel.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getClassificationLevels"></a>
# **getClassificationLevels**
> List&lt;ClassificationLevel&gt; getClassificationLevels()

Get list of classification levels

Returns a list of [classification levels](https://developer.atlassian.com/cloud/admin/dlp/rest/intro/#Classification%20level)  available.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Permission to access the Confluence site (&#x27;Can use&#x27; global permission).

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ClassificationLevelApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ClassificationLevelApi apiInstance = new ClassificationLevelApi();
try {
    List<ClassificationLevel> result = apiInstance.getClassificationLevels();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ClassificationLevelApi#getClassificationLevels");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;ClassificationLevel&gt;**](ClassificationLevel.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getDatabaseClassificationLevel"></a>
# **getDatabaseClassificationLevel**
> ClassificationLevel getDatabaseClassificationLevel(id)

Get database classification level

Returns the [classification level](https://developer.atlassian.com/cloud/admin/dlp/rest/intro/#Classification%20level) for a specific database.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Permission to access the Confluence site (&#x27;Can use&#x27; global permission) and permission to view the database.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ClassificationLevelApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ClassificationLevelApi apiInstance = new ClassificationLevelApi();
Long id = 789L; // Long | The ID of the database for which classification level should be returned.
try {
    ClassificationLevel result = apiInstance.getDatabaseClassificationLevel(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ClassificationLevelApi#getDatabaseClassificationLevel");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the database for which classification level should be returned. |

### Return type

[**ClassificationLevel**](ClassificationLevel.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getPageClassificationLevel"></a>
# **getPageClassificationLevel**
> ClassificationLevel getPageClassificationLevel(id, status)

Get page classification level

Returns the [classification level](https://developer.atlassian.com/cloud/admin/dlp/rest/intro/#Classification%20level) for a specific page.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Permission to access the Confluence site (&#x27;Can use&#x27; global permission) and permission to view the page. &#x27;Permission to edit the page is required if trying to view classification level for a draft.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ClassificationLevelApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ClassificationLevelApi apiInstance = new ClassificationLevelApi();
Long id = 789L; // Long | The ID of the page for which classification level should be returned.
String status = "current"; // String | Status of page from which classification level will fetched.
try {
    ClassificationLevel result = apiInstance.getPageClassificationLevel(id, status);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ClassificationLevelApi#getPageClassificationLevel");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the page for which classification level should be returned. |
 **status** | **String**| Status of page from which classification level will fetched. | [optional] [default to current] [enum: current, draft, archived]

### Return type

[**ClassificationLevel**](ClassificationLevel.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getSpaceDefaultClassificationLevel"></a>
# **getSpaceDefaultClassificationLevel**
> ClassificationLevel getSpaceDefaultClassificationLevel(id)

Get space default classification level

Returns the [default classification level](https://support.atlassian.com/security-and-access-policies/docs/what-is-a-default-classification-level/)  for a specific space.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Permission to access the Confluence site (&#x27;Can use&#x27; global permission) and permission to view the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ClassificationLevelApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ClassificationLevelApi apiInstance = new ClassificationLevelApi();
Long id = 789L; // Long | The ID of the space for which default classification level should be returned.
try {
    ClassificationLevel result = apiInstance.getSpaceDefaultClassificationLevel(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ClassificationLevelApi#getSpaceDefaultClassificationLevel");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the space for which default classification level should be returned. |

### Return type

[**ClassificationLevel**](ClassificationLevel.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getWhiteboardClassificationLevel"></a>
# **getWhiteboardClassificationLevel**
> ClassificationLevel getWhiteboardClassificationLevel(id)

Get whiteboard classification level

Returns the [classification level](https://developer.atlassian.com/cloud/admin/dlp/rest/intro/#Classification%20level) for a specific whiteboard.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Permission to access the Confluence site (&#x27;Can use&#x27; global permission) and permission to view the whiteboard.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ClassificationLevelApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ClassificationLevelApi apiInstance = new ClassificationLevelApi();
Long id = 789L; // Long | The ID of the whiteboard for which classification level should be returned.
try {
    ClassificationLevel result = apiInstance.getWhiteboardClassificationLevel(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ClassificationLevelApi#getWhiteboardClassificationLevel");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the whiteboard for which classification level should be returned. |

### Return type

[**ClassificationLevel**](ClassificationLevel.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="postBlogPostClassificationLevel"></a>
# **postBlogPostClassificationLevel**
> postBlogPostClassificationLevel(body, id)

Reset blog post classification level

Resets the [classification level](https://developer.atlassian.com/cloud/admin/dlp/rest/intro/#Classification%20level) for a specific blog post for the space   [default classification level](https://support.atlassian.com/security-and-access-policies/docs/what-is-a-default-classification-level/).  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Permission to access the Confluence site (&#x27;Can use&#x27; global permission) and permission to view the blog post.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ClassificationLevelApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ClassificationLevelApi apiInstance = new ClassificationLevelApi();
Object body = null; // Object | 
Long id = 789L; // Long | The ID of the blog post for which classification level should be updated.
try {
    apiInstance.postBlogPostClassificationLevel(body, id);
} catch (ApiException e) {
    System.err.println("Exception when calling ClassificationLevelApi#postBlogPostClassificationLevel");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Object**](Object.md)|  |
 **id** | **Long**| The ID of the blog post for which classification level should be updated. |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="postDatabaseClassificationLevel"></a>
# **postDatabaseClassificationLevel**
> postDatabaseClassificationLevel(body, id)

Reset database classification level

Resets the [classification level](https://developer.atlassian.com/cloud/admin/dlp/rest/intro/#Classification%20level) for a specific database for the space  [default classification level](https://support.atlassian.com/security-and-access-policies/docs/what-is-a-default-classification-level/).  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Permission to access the Confluence site (&#x27;Can use&#x27; global permission) and permission to view the database.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ClassificationLevelApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ClassificationLevelApi apiInstance = new ClassificationLevelApi();
Object body = null; // Object | 
Long id = 789L; // Long | The ID of the database for which classification level should be updated.
try {
    apiInstance.postDatabaseClassificationLevel(body, id);
} catch (ApiException e) {
    System.err.println("Exception when calling ClassificationLevelApi#postDatabaseClassificationLevel");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Object**](Object.md)|  |
 **id** | **Long**| The ID of the database for which classification level should be updated. |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="postPageClassificationLevel"></a>
# **postPageClassificationLevel**
> postPageClassificationLevel(body, id)

Reset page classification level

Resets the [classification level](https://developer.atlassian.com/cloud/admin/dlp/rest/intro/#Classification%20level) for a specific page for the space  [default classification level](https://support.atlassian.com/security-and-access-policies/docs/what-is-a-default-classification-level/).  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Permission to access the Confluence site (&#x27;Can use&#x27; global permission) and permission to view the page.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ClassificationLevelApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ClassificationLevelApi apiInstance = new ClassificationLevelApi();
Object body = null; // Object | 
Long id = 789L; // Long | The ID of the page for which classification level should be updated.
try {
    apiInstance.postPageClassificationLevel(body, id);
} catch (ApiException e) {
    System.err.println("Exception when calling ClassificationLevelApi#postPageClassificationLevel");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Object**](Object.md)|  |
 **id** | **Long**| The ID of the page for which classification level should be updated. |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="postWhiteboardClassificationLevel"></a>
# **postWhiteboardClassificationLevel**
> postWhiteboardClassificationLevel(body, id)

Reset whiteboard classification level

Resets the [classification level](https://developer.atlassian.com/cloud/admin/dlp/rest/intro/#Classification%20level) for a specific whiteboard for the space  [default classification level](https://support.atlassian.com/security-and-access-policies/docs/what-is-a-default-classification-level/).  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Permission to access the Confluence site (&#x27;Can use&#x27; global permission) and permission to view the whiteboard.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ClassificationLevelApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ClassificationLevelApi apiInstance = new ClassificationLevelApi();
Object body = null; // Object | 
Long id = 789L; // Long | The ID of the whiteboard for which classification level should be updated.
try {
    apiInstance.postWhiteboardClassificationLevel(body, id);
} catch (ApiException e) {
    System.err.println("Exception when calling ClassificationLevelApi#postWhiteboardClassificationLevel");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Object**](Object.md)|  |
 **id** | **Long**| The ID of the whiteboard for which classification level should be updated. |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="putBlogPostClassificationLevel"></a>
# **putBlogPostClassificationLevel**
> putBlogPostClassificationLevel(body, id)

Update blog post classification level

Updates the [classification level](https://developer.atlassian.com/cloud/admin/dlp/rest/intro/#Classification%20level) for a specific blog post.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Permission to access the Confluence site (&#x27;Can use&#x27; global permission) and permission to edit the blog post.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ClassificationLevelApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ClassificationLevelApi apiInstance = new ClassificationLevelApi();
Object body = null; // Object | 
Long id = 789L; // Long | The ID of the blog post for which classification level should be updated.
try {
    apiInstance.putBlogPostClassificationLevel(body, id);
} catch (ApiException e) {
    System.err.println("Exception when calling ClassificationLevelApi#putBlogPostClassificationLevel");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Object**](Object.md)|  |
 **id** | **Long**| The ID of the blog post for which classification level should be updated. |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="putDatabaseClassificationLevel"></a>
# **putDatabaseClassificationLevel**
> putDatabaseClassificationLevel(body, id)

Update database classification level

Updates the [classification level](https://developer.atlassian.com/cloud/admin/dlp/rest/intro/#Classification%20level) for a specific database.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Permission to access the Confluence site (&#x27;Can use&#x27; global permission) and permission to edit the database.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ClassificationLevelApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ClassificationLevelApi apiInstance = new ClassificationLevelApi();
Object body = null; // Object | 
Long id = 789L; // Long | The ID of the database for which classification level should be updated.
try {
    apiInstance.putDatabaseClassificationLevel(body, id);
} catch (ApiException e) {
    System.err.println("Exception when calling ClassificationLevelApi#putDatabaseClassificationLevel");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Object**](Object.md)|  |
 **id** | **Long**| The ID of the database for which classification level should be updated. |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="putPageClassificationLevel"></a>
# **putPageClassificationLevel**
> putPageClassificationLevel(body, id)

Update page classification level

Updates the [classification level](https://developer.atlassian.com/cloud/admin/dlp/rest/intro/#Classification%20level) for a specific page.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Permission to access the Confluence site (&#x27;Can use&#x27; global permission) and permission to edit the page.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ClassificationLevelApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ClassificationLevelApi apiInstance = new ClassificationLevelApi();
Object body = null; // Object | 
Long id = 789L; // Long | The ID of the page for which classification level should be updated.
try {
    apiInstance.putPageClassificationLevel(body, id);
} catch (ApiException e) {
    System.err.println("Exception when calling ClassificationLevelApi#putPageClassificationLevel");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Object**](Object.md)|  |
 **id** | **Long**| The ID of the page for which classification level should be updated. |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="putSpaceDefaultClassificationLevel"></a>
# **putSpaceDefaultClassificationLevel**
> putSpaceDefaultClassificationLevel(body, id)

Update space default classification level

Update the [default classification level](https://support.atlassian.com/security-and-access-policies/docs/what-is-a-default-classification-level/)  for a specific space.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Permission to access the Confluence site (&#x27;Can use&#x27; global permission) and &#x27;Admin&#x27; permission for the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ClassificationLevelApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ClassificationLevelApi apiInstance = new ClassificationLevelApi();
Object body = null; // Object | 
Long id = 789L; // Long | The ID of the space for which default classification level should be updated.
try {
    apiInstance.putSpaceDefaultClassificationLevel(body, id);
} catch (ApiException e) {
    System.err.println("Exception when calling ClassificationLevelApi#putSpaceDefaultClassificationLevel");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Object**](Object.md)|  |
 **id** | **Long**| The ID of the space for which default classification level should be updated. |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="putWhiteboardClassificationLevel"></a>
# **putWhiteboardClassificationLevel**
> putWhiteboardClassificationLevel(body, id)

Update whiteboard classification level

Updates the [classification level](https://developer.atlassian.com/cloud/admin/dlp/rest/intro/#Classification%20level) for a specific whiteboard.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Permission to access the Confluence site (&#x27;Can use&#x27; global permission) and permission to edit the whiteboard.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ClassificationLevelApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ClassificationLevelApi apiInstance = new ClassificationLevelApi();
Object body = null; // Object | 
Long id = 789L; // Long | The ID of the whiteboard for which classification level should be updated.
try {
    apiInstance.putWhiteboardClassificationLevel(body, id);
} catch (ApiException e) {
    System.err.println("Exception when calling ClassificationLevelApi#putWhiteboardClassificationLevel");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Object**](Object.md)|  |
 **id** | **Long**| The ID of the whiteboard for which classification level should be updated. |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

