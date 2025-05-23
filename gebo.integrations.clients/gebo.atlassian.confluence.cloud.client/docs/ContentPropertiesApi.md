# ContentPropertiesApi

All URIs are relative to *https://{your-domain}/wiki/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createAttachmentProperty**](ContentPropertiesApi.md#createAttachmentProperty) | **POST** /attachments/{attachment-id}/properties | Create content property for attachment
[**createBlogpostProperty**](ContentPropertiesApi.md#createBlogpostProperty) | **POST** /blogposts/{blogpost-id}/properties | Create content property for blog post
[**createCommentProperty**](ContentPropertiesApi.md#createCommentProperty) | **POST** /comments/{comment-id}/properties | Create content property for comment
[**createCustomContentProperty**](ContentPropertiesApi.md#createCustomContentProperty) | **POST** /custom-content/{custom-content-id}/properties | Create content property for custom content
[**createDatabaseProperty**](ContentPropertiesApi.md#createDatabaseProperty) | **POST** /databases/{id}/properties | Create content property for database
[**createFolderProperty**](ContentPropertiesApi.md#createFolderProperty) | **POST** /folders/{id}/properties | Create content property for folder
[**createPageProperty**](ContentPropertiesApi.md#createPageProperty) | **POST** /pages/{page-id}/properties | Create content property for page
[**createSmartLinkProperty**](ContentPropertiesApi.md#createSmartLinkProperty) | **POST** /embeds/{id}/properties | Create content property for Smart Link in the content tree
[**createWhiteboardProperty**](ContentPropertiesApi.md#createWhiteboardProperty) | **POST** /whiteboards/{id}/properties | Create content property for whiteboard
[**deleteAttachmentPropertyById**](ContentPropertiesApi.md#deleteAttachmentPropertyById) | **DELETE** /attachments/{attachment-id}/properties/{property-id} | Delete content property for attachment by id
[**deleteBlogpostPropertyById**](ContentPropertiesApi.md#deleteBlogpostPropertyById) | **DELETE** /blogposts/{blogpost-id}/properties/{property-id} | Delete content property for blogpost by id
[**deleteCommentPropertyById**](ContentPropertiesApi.md#deleteCommentPropertyById) | **DELETE** /comments/{comment-id}/properties/{property-id} | Delete content property for comment by id
[**deleteCustomContentPropertyById**](ContentPropertiesApi.md#deleteCustomContentPropertyById) | **DELETE** /custom-content/{custom-content-id}/properties/{property-id} | Delete content property for custom content by id
[**deleteDatabasePropertyById**](ContentPropertiesApi.md#deleteDatabasePropertyById) | **DELETE** /databases/{database-id}/properties/{property-id} | Delete content property for database by id
[**deleteFolderPropertyById**](ContentPropertiesApi.md#deleteFolderPropertyById) | **DELETE** /folders/{folder-id}/properties/{property-id} | Delete content property for folder by id
[**deletePagePropertyById**](ContentPropertiesApi.md#deletePagePropertyById) | **DELETE** /pages/{page-id}/properties/{property-id} | Delete content property for page by id
[**deleteSmartLinkPropertyById**](ContentPropertiesApi.md#deleteSmartLinkPropertyById) | **DELETE** /embeds/{embed-id}/properties/{property-id} | Delete content property for Smart Link in the content tree by id
[**deleteWhiteboardPropertyById**](ContentPropertiesApi.md#deleteWhiteboardPropertyById) | **DELETE** /whiteboards/{whiteboard-id}/properties/{property-id} | Delete content property for whiteboard by id
[**getAttachmentContentProperties**](ContentPropertiesApi.md#getAttachmentContentProperties) | **GET** /attachments/{attachment-id}/properties | Get content properties for attachment
[**getAttachmentContentPropertiesById**](ContentPropertiesApi.md#getAttachmentContentPropertiesById) | **GET** /attachments/{attachment-id}/properties/{property-id} | Get content property for attachment by id
[**getBlogpostContentProperties**](ContentPropertiesApi.md#getBlogpostContentProperties) | **GET** /blogposts/{blogpost-id}/properties | Get content properties for blog post
[**getBlogpostContentPropertiesById**](ContentPropertiesApi.md#getBlogpostContentPropertiesById) | **GET** /blogposts/{blogpost-id}/properties/{property-id} | Get content property for blog post by id
[**getCommentContentProperties**](ContentPropertiesApi.md#getCommentContentProperties) | **GET** /comments/{comment-id}/properties | Get content properties for comment
[**getCommentContentPropertiesById**](ContentPropertiesApi.md#getCommentContentPropertiesById) | **GET** /comments/{comment-id}/properties/{property-id} | Get content property for comment by id
[**getCustomContentContentProperties**](ContentPropertiesApi.md#getCustomContentContentProperties) | **GET** /custom-content/{custom-content-id}/properties | Get content properties for custom content
[**getCustomContentContentPropertiesById**](ContentPropertiesApi.md#getCustomContentContentPropertiesById) | **GET** /custom-content/{custom-content-id}/properties/{property-id} | Get content property for custom content by id
[**getDatabaseContentProperties**](ContentPropertiesApi.md#getDatabaseContentProperties) | **GET** /databases/{id}/properties | Get content properties for database
[**getDatabaseContentPropertiesById**](ContentPropertiesApi.md#getDatabaseContentPropertiesById) | **GET** /databases/{database-id}/properties/{property-id} | Get content property for database by id
[**getFolderContentProperties**](ContentPropertiesApi.md#getFolderContentProperties) | **GET** /folders/{id}/properties | Get content properties for folder
[**getFolderContentPropertiesById**](ContentPropertiesApi.md#getFolderContentPropertiesById) | **GET** /folders/{folder-id}/properties/{property-id} | Get content property for folder by id
[**getPageContentProperties**](ContentPropertiesApi.md#getPageContentProperties) | **GET** /pages/{page-id}/properties | Get content properties for page
[**getPageContentPropertiesById**](ContentPropertiesApi.md#getPageContentPropertiesById) | **GET** /pages/{page-id}/properties/{property-id} | Get content property for page by id
[**getSmartLinkContentProperties**](ContentPropertiesApi.md#getSmartLinkContentProperties) | **GET** /embeds/{id}/properties | Get content properties for Smart Link in the content tree
[**getSmartLinkContentPropertiesById**](ContentPropertiesApi.md#getSmartLinkContentPropertiesById) | **GET** /embeds/{embed-id}/properties/{property-id} | Get content property for Smart Link in the content tree by id
[**getWhiteboardContentProperties**](ContentPropertiesApi.md#getWhiteboardContentProperties) | **GET** /whiteboards/{id}/properties | Get content properties for whiteboard
[**getWhiteboardContentPropertiesById**](ContentPropertiesApi.md#getWhiteboardContentPropertiesById) | **GET** /whiteboards/{whiteboard-id}/properties/{property-id} | Get content property for whiteboard by id
[**updateAttachmentPropertyById**](ContentPropertiesApi.md#updateAttachmentPropertyById) | **PUT** /attachments/{attachment-id}/properties/{property-id} | Update content property for attachment by id
[**updateBlogpostPropertyById**](ContentPropertiesApi.md#updateBlogpostPropertyById) | **PUT** /blogposts/{blogpost-id}/properties/{property-id} | Update content property for blog post by id
[**updateCommentPropertyById**](ContentPropertiesApi.md#updateCommentPropertyById) | **PUT** /comments/{comment-id}/properties/{property-id} | Update content property for comment by id
[**updateCustomContentPropertyById**](ContentPropertiesApi.md#updateCustomContentPropertyById) | **PUT** /custom-content/{custom-content-id}/properties/{property-id} | Update content property for custom content by id
[**updateDatabasePropertyById**](ContentPropertiesApi.md#updateDatabasePropertyById) | **PUT** /databases/{database-id}/properties/{property-id} | Update content property for database by id
[**updateFolderPropertyById**](ContentPropertiesApi.md#updateFolderPropertyById) | **PUT** /folders/{folder-id}/properties/{property-id} | Update content property for folder by id
[**updatePagePropertyById**](ContentPropertiesApi.md#updatePagePropertyById) | **PUT** /pages/{page-id}/properties/{property-id} | Update content property for page by id
[**updateSmartLinkPropertyById**](ContentPropertiesApi.md#updateSmartLinkPropertyById) | **PUT** /embeds/{embed-id}/properties/{property-id} | Update content property for Smart Link in the content tree by id
[**updateWhiteboardPropertyById**](ContentPropertiesApi.md#updateWhiteboardPropertyById) | **PUT** /whiteboards/{whiteboard-id}/properties/{property-id} | Update content property for whiteboard by id

<a name="createAttachmentProperty"></a>
# **createAttachmentProperty**
> ContentProperty createAttachmentProperty(body, attachmentId)

Create content property for attachment

Creates a new content property for an attachment.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to update the attachment.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
ContentPropertyCreateRequest body = new ContentPropertyCreateRequest(); // ContentPropertyCreateRequest | The content property to be created
String attachmentId = "attachmentId_example"; // String | The ID of the attachment to create a property for.
try {
    ContentProperty result = apiInstance.createAttachmentProperty(body, attachmentId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#createAttachmentProperty");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ContentPropertyCreateRequest**](ContentPropertyCreateRequest.md)| The content property to be created |
 **attachmentId** | **String**| The ID of the attachment to create a property for. |

### Return type

[**ContentProperty**](ContentProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createBlogpostProperty"></a>
# **createBlogpostProperty**
> ContentProperty createBlogpostProperty(body, blogpostId)

Create content property for blog post

Creates a new property for a blogpost.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to update the blog post.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
ContentPropertyCreateRequest body = new ContentPropertyCreateRequest(); // ContentPropertyCreateRequest | The content property to be created
Long blogpostId = 789L; // Long | The ID of the blog post to create a property for.
try {
    ContentProperty result = apiInstance.createBlogpostProperty(body, blogpostId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#createBlogpostProperty");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ContentPropertyCreateRequest**](ContentPropertyCreateRequest.md)| The content property to be created |
 **blogpostId** | **Long**| The ID of the blog post to create a property for. |

### Return type

[**ContentProperty**](ContentProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createCommentProperty"></a>
# **createCommentProperty**
> ContentProperty createCommentProperty(body, commentId)

Create content property for comment

Creates a new content property for a comment.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to update the comment.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
ContentPropertyCreateRequest body = new ContentPropertyCreateRequest(); // ContentPropertyCreateRequest | The content property to be created
Long commentId = 789L; // Long | The ID of the comment to create a property for.
try {
    ContentProperty result = apiInstance.createCommentProperty(body, commentId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#createCommentProperty");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ContentPropertyCreateRequest**](ContentPropertyCreateRequest.md)| The content property to be created |
 **commentId** | **Long**| The ID of the comment to create a property for. |

### Return type

[**ContentProperty**](ContentProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createCustomContentProperty"></a>
# **createCustomContentProperty**
> ContentProperty createCustomContentProperty(body, customContentId)

Create content property for custom content

Creates a new content property for a piece of custom content.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to update the custom content.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
ContentPropertyCreateRequest body = new ContentPropertyCreateRequest(); // ContentPropertyCreateRequest | The content property to be created
Long customContentId = 789L; // Long | The ID of the custom content to create a property for.
try {
    ContentProperty result = apiInstance.createCustomContentProperty(body, customContentId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#createCustomContentProperty");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ContentPropertyCreateRequest**](ContentPropertyCreateRequest.md)| The content property to be created |
 **customContentId** | **Long**| The ID of the custom content to create a property for. |

### Return type

[**ContentProperty**](ContentProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createDatabaseProperty"></a>
# **createDatabaseProperty**
> ContentProperty createDatabaseProperty(body, id)

Create content property for database

Creates a new content property for a database.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to update the database.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
ContentPropertyCreateRequest body = new ContentPropertyCreateRequest(); // ContentPropertyCreateRequest | The content property to be created
Long id = 789L; // Long | The ID of the database to create a property for.
try {
    ContentProperty result = apiInstance.createDatabaseProperty(body, id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#createDatabaseProperty");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ContentPropertyCreateRequest**](ContentPropertyCreateRequest.md)| The content property to be created |
 **id** | **Long**| The ID of the database to create a property for. |

### Return type

[**ContentProperty**](ContentProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createFolderProperty"></a>
# **createFolderProperty**
> ContentProperty createFolderProperty(body, id)

Create content property for folder

Creates a new content property for a folder.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to update the folder.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
ContentPropertyCreateRequest body = new ContentPropertyCreateRequest(); // ContentPropertyCreateRequest | The content property to be created
Long id = 789L; // Long | The ID of the folder to create a property for.
try {
    ContentProperty result = apiInstance.createFolderProperty(body, id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#createFolderProperty");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ContentPropertyCreateRequest**](ContentPropertyCreateRequest.md)| The content property to be created |
 **id** | **Long**| The ID of the folder to create a property for. |

### Return type

[**ContentProperty**](ContentProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createPageProperty"></a>
# **createPageProperty**
> ContentProperty createPageProperty(body, pageId)

Create content property for page

Creates a new content property for a page.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to update the page.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
ContentPropertyCreateRequest body = new ContentPropertyCreateRequest(); // ContentPropertyCreateRequest | The content property to be created
Long pageId = 789L; // Long | The ID of the page to create a property for.
try {
    ContentProperty result = apiInstance.createPageProperty(body, pageId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#createPageProperty");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ContentPropertyCreateRequest**](ContentPropertyCreateRequest.md)| The content property to be created |
 **pageId** | **Long**| The ID of the page to create a property for. |

### Return type

[**ContentProperty**](ContentProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createSmartLinkProperty"></a>
# **createSmartLinkProperty**
> ContentProperty createSmartLinkProperty(body, id)

Create content property for Smart Link in the content tree

Creates a new content property for a Smart Link in the content tree.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to update the Smart Link in the content tree.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
ContentPropertyCreateRequest body = new ContentPropertyCreateRequest(); // ContentPropertyCreateRequest | The content property to be created
Long id = 789L; // Long | The ID of the Smart Link in the content tree to create a property for.
try {
    ContentProperty result = apiInstance.createSmartLinkProperty(body, id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#createSmartLinkProperty");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ContentPropertyCreateRequest**](ContentPropertyCreateRequest.md)| The content property to be created |
 **id** | **Long**| The ID of the Smart Link in the content tree to create a property for. |

### Return type

[**ContentProperty**](ContentProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createWhiteboardProperty"></a>
# **createWhiteboardProperty**
> ContentProperty createWhiteboardProperty(body, id)

Create content property for whiteboard

Creates a new content property for a whiteboard.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to update the whiteboard.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
ContentPropertyCreateRequest body = new ContentPropertyCreateRequest(); // ContentPropertyCreateRequest | The content property to be created
Long id = 789L; // Long | The ID of the whiteboard to create a property for.
try {
    ContentProperty result = apiInstance.createWhiteboardProperty(body, id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#createWhiteboardProperty");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ContentPropertyCreateRequest**](ContentPropertyCreateRequest.md)| The content property to be created |
 **id** | **Long**| The ID of the whiteboard to create a property for. |

### Return type

[**ContentProperty**](ContentProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deleteAttachmentPropertyById"></a>
# **deleteAttachmentPropertyById**
> deleteAttachmentPropertyById(attachmentId, propertyId)

Delete content property for attachment by id

Deletes a content property for an attachment by its id.   **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to attachment the page.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
String attachmentId = "attachmentId_example"; // String | The ID of the attachment the property belongs to.
Long propertyId = 789L; // Long | The ID of the property to be deleted.
try {
    apiInstance.deleteAttachmentPropertyById(attachmentId, propertyId);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#deleteAttachmentPropertyById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **attachmentId** | **String**| The ID of the attachment the property belongs to. |
 **propertyId** | **Long**| The ID of the property to be deleted. |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="deleteBlogpostPropertyById"></a>
# **deleteBlogpostPropertyById**
> deleteBlogpostPropertyById(blogpostId, propertyId)

Delete content property for blogpost by id

Deletes a content property for a blogpost by its id.   **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to edit the blog post.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
Long blogpostId = 789L; // Long | The ID of the blog post the property belongs to.
Long propertyId = 789L; // Long | The ID of the property to be deleted.
try {
    apiInstance.deleteBlogpostPropertyById(blogpostId, propertyId);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#deleteBlogpostPropertyById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **blogpostId** | **Long**| The ID of the blog post the property belongs to. |
 **propertyId** | **Long**| The ID of the property to be deleted. |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="deleteCommentPropertyById"></a>
# **deleteCommentPropertyById**
> deleteCommentPropertyById(commentId, propertyId)

Delete content property for comment by id

Deletes a content property for a comment by its id.   **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to edit the comment.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
Long commentId = 789L; // Long | The ID of the comment the property belongs to.
Long propertyId = 789L; // Long | The ID of the property to be deleted.
try {
    apiInstance.deleteCommentPropertyById(commentId, propertyId);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#deleteCommentPropertyById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **commentId** | **Long**| The ID of the comment the property belongs to. |
 **propertyId** | **Long**| The ID of the property to be deleted. |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="deleteCustomContentPropertyById"></a>
# **deleteCustomContentPropertyById**
> deleteCustomContentPropertyById(customContentId, propertyId)

Delete content property for custom content by id

Deletes a content property for a piece of custom content by its id.   **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to edit the custom content.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
Long customContentId = 789L; // Long | The ID of the custom content the property belongs to.
Long propertyId = 789L; // Long | The ID of the property to be deleted.
try {
    apiInstance.deleteCustomContentPropertyById(customContentId, propertyId);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#deleteCustomContentPropertyById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **customContentId** | **Long**| The ID of the custom content the property belongs to. |
 **propertyId** | **Long**| The ID of the property to be deleted. |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="deleteDatabasePropertyById"></a>
# **deleteDatabasePropertyById**
> deleteDatabasePropertyById(databaseId, propertyId)

Delete content property for database by id

Deletes a content property for a database by its id.   **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to edit the database.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
Long databaseId = 789L; // Long | The ID of the database the property belongs to.
Long propertyId = 789L; // Long | The ID of the property to be deleted.
try {
    apiInstance.deleteDatabasePropertyById(databaseId, propertyId);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#deleteDatabasePropertyById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **databaseId** | **Long**| The ID of the database the property belongs to. |
 **propertyId** | **Long**| The ID of the property to be deleted. |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="deleteFolderPropertyById"></a>
# **deleteFolderPropertyById**
> deleteFolderPropertyById(folderId, propertyId)

Delete content property for folder by id

Deletes a content property for a folder by its id.   **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to edit the folder.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
Long folderId = 789L; // Long | The ID of the folder the property belongs to.
Long propertyId = 789L; // Long | The ID of the property to be deleted.
try {
    apiInstance.deleteFolderPropertyById(folderId, propertyId);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#deleteFolderPropertyById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **folderId** | **Long**| The ID of the folder the property belongs to. |
 **propertyId** | **Long**| The ID of the property to be deleted. |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="deletePagePropertyById"></a>
# **deletePagePropertyById**
> deletePagePropertyById(pageId, propertyId)

Delete content property for page by id

Deletes a content property for a page by its id.   **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to edit the page.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
Long pageId = 789L; // Long | The ID of the page the property belongs to.
Long propertyId = 789L; // Long | The ID of the property to be deleted.
try {
    apiInstance.deletePagePropertyById(pageId, propertyId);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#deletePagePropertyById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **pageId** | **Long**| The ID of the page the property belongs to. |
 **propertyId** | **Long**| The ID of the property to be deleted. |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="deleteSmartLinkPropertyById"></a>
# **deleteSmartLinkPropertyById**
> deleteSmartLinkPropertyById(embedId, propertyId)

Delete content property for Smart Link in the content tree by id

Deletes a content property for a Smart Link in the content tree by its id.   **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to edit the Smart Link in the content tree.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
Long embedId = 789L; // Long | The ID of the Smart Link in the content tree the property belongs to.
Long propertyId = 789L; // Long | The ID of the property to be deleted.
try {
    apiInstance.deleteSmartLinkPropertyById(embedId, propertyId);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#deleteSmartLinkPropertyById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **embedId** | **Long**| The ID of the Smart Link in the content tree the property belongs to. |
 **propertyId** | **Long**| The ID of the property to be deleted. |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="deleteWhiteboardPropertyById"></a>
# **deleteWhiteboardPropertyById**
> deleteWhiteboardPropertyById(whiteboardId, propertyId)

Delete content property for whiteboard by id

Deletes a content property for a whiteboard by its id.   **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to edit the whiteboard.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
Long whiteboardId = 789L; // Long | The ID of the whiteboard the property belongs to.
Long propertyId = 789L; // Long | The ID of the property to be deleted.
try {
    apiInstance.deleteWhiteboardPropertyById(whiteboardId, propertyId);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#deleteWhiteboardPropertyById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **whiteboardId** | **Long**| The ID of the whiteboard the property belongs to. |
 **propertyId** | **Long**| The ID of the property to be deleted. |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="getAttachmentContentProperties"></a>
# **getAttachmentContentProperties**
> MultiEntityResultContentProperty getAttachmentContentProperties(attachmentId, key, sort, cursor, limit)

Get content properties for attachment

Retrieves all Content Properties tied to a specified attachment.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the attachment.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
String attachmentId = "attachmentId_example"; // String | The ID of the attachment for which content properties should be returned.
String key = "key_example"; // String | Filters the response to return a specific content property with matching key (case sensitive).
ContentPropertySortOrder sort = new ContentPropertySortOrder(); // ContentPropertySortOrder | Used to sort the result by a particular field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of attachments per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultContentProperty result = apiInstance.getAttachmentContentProperties(attachmentId, key, sort, cursor, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#getAttachmentContentProperties");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **attachmentId** | **String**| The ID of the attachment for which content properties should be returned. |
 **key** | **String**| Filters the response to return a specific content property with matching key (case sensitive). | [optional]
 **sort** | [**ContentPropertySortOrder**](.md)| Used to sort the result by a particular field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of attachments per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultContentProperty**](MultiEntityResultContentProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAttachmentContentPropertiesById"></a>
# **getAttachmentContentPropertiesById**
> ContentProperty getAttachmentContentPropertiesById(attachmentId, propertyId)

Get content property for attachment by id

Retrieves a specific Content Property by ID that is attached to a specified attachment.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the attachment.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
String attachmentId = "attachmentId_example"; // String | The ID of the attachment for which content properties should be returned.
Long propertyId = 789L; // Long | The ID of the content property to be returned
try {
    ContentProperty result = apiInstance.getAttachmentContentPropertiesById(attachmentId, propertyId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#getAttachmentContentPropertiesById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **attachmentId** | **String**| The ID of the attachment for which content properties should be returned. |
 **propertyId** | **Long**| The ID of the content property to be returned |

### Return type

[**ContentProperty**](ContentProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getBlogpostContentProperties"></a>
# **getBlogpostContentProperties**
> MultiEntityResultContentProperty getBlogpostContentProperties(blogpostId, key, sort, cursor, limit)

Get content properties for blog post

Retrieves all Content Properties tied to a specified blog post.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the blog post.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
Long blogpostId = 789L; // Long | The ID of the blog post for which content properties should be returned.
String key = "key_example"; // String | Filters the response to return a specific content property with matching key (case sensitive).
ContentPropertySortOrder sort = new ContentPropertySortOrder(); // ContentPropertySortOrder | Used to sort the result by a particular field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of attachments per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultContentProperty result = apiInstance.getBlogpostContentProperties(blogpostId, key, sort, cursor, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#getBlogpostContentProperties");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **blogpostId** | **Long**| The ID of the blog post for which content properties should be returned. |
 **key** | **String**| Filters the response to return a specific content property with matching key (case sensitive). | [optional]
 **sort** | [**ContentPropertySortOrder**](.md)| Used to sort the result by a particular field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of attachments per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultContentProperty**](MultiEntityResultContentProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getBlogpostContentPropertiesById"></a>
# **getBlogpostContentPropertiesById**
> ContentProperty getBlogpostContentPropertiesById(blogpostId, propertyId)

Get content property for blog post by id

Retrieves a specific Content Property by ID that is attached to a specified blog post.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the blog post.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
Long blogpostId = 789L; // Long | The ID of the blog post for which content properties should be returned.
Long propertyId = 789L; // Long | The ID of the property being requested
try {
    ContentProperty result = apiInstance.getBlogpostContentPropertiesById(blogpostId, propertyId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#getBlogpostContentPropertiesById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **blogpostId** | **Long**| The ID of the blog post for which content properties should be returned. |
 **propertyId** | **Long**| The ID of the property being requested |

### Return type

[**ContentProperty**](ContentProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getCommentContentProperties"></a>
# **getCommentContentProperties**
> MultiEntityResultContentProperty getCommentContentProperties(commentId, key, sort, cursor, limit)

Get content properties for comment

Retrieves Content Properties attached to a specified comment.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the comment.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
Long commentId = 789L; // Long | The ID of the comment for which content properties should be returned.
String key = "key_example"; // String | Filters the response to return a specific content property with matching key (case sensitive).
ContentPropertySortOrder sort = new ContentPropertySortOrder(); // ContentPropertySortOrder | Used to sort the result by a particular field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of attachments per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultContentProperty result = apiInstance.getCommentContentProperties(commentId, key, sort, cursor, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#getCommentContentProperties");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **commentId** | **Long**| The ID of the comment for which content properties should be returned. |
 **key** | **String**| Filters the response to return a specific content property with matching key (case sensitive). | [optional]
 **sort** | [**ContentPropertySortOrder**](.md)| Used to sort the result by a particular field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of attachments per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultContentProperty**](MultiEntityResultContentProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getCommentContentPropertiesById"></a>
# **getCommentContentPropertiesById**
> ContentProperty getCommentContentPropertiesById(commentId, propertyId)

Get content property for comment by id

Retrieves a specific Content Property by ID that is attached to a specified comment.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the comment.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
Long commentId = 789L; // Long | The ID of the comment for which content properties should be returned.
Long propertyId = 789L; // Long | The ID of the content property being requested.
try {
    ContentProperty result = apiInstance.getCommentContentPropertiesById(commentId, propertyId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#getCommentContentPropertiesById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **commentId** | **Long**| The ID of the comment for which content properties should be returned. |
 **propertyId** | **Long**| The ID of the content property being requested. |

### Return type

[**ContentProperty**](ContentProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getCustomContentContentProperties"></a>
# **getCustomContentContentProperties**
> MultiEntityResultContentProperty getCustomContentContentProperties(customContentId, key, sort, cursor, limit)

Get content properties for custom content

Retrieves Content Properties tied to a specified custom content.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the custom content.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
Long customContentId = 789L; // Long | The ID of the custom content for which content properties should be returned.
String key = "key_example"; // String | Filters the response to return a specific content property with matching key (case sensitive).
ContentPropertySortOrder sort = new ContentPropertySortOrder(); // ContentPropertySortOrder | Used to sort the result by a particular field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of attachments per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultContentProperty result = apiInstance.getCustomContentContentProperties(customContentId, key, sort, cursor, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#getCustomContentContentProperties");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **customContentId** | **Long**| The ID of the custom content for which content properties should be returned. |
 **key** | **String**| Filters the response to return a specific content property with matching key (case sensitive). | [optional]
 **sort** | [**ContentPropertySortOrder**](.md)| Used to sort the result by a particular field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of attachments per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultContentProperty**](MultiEntityResultContentProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getCustomContentContentPropertiesById"></a>
# **getCustomContentContentPropertiesById**
> ContentProperty getCustomContentContentPropertiesById(customContentId, propertyId)

Get content property for custom content by id

Retrieves a specific Content Property by ID that is attached to a specified custom content.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the page.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
Long customContentId = 789L; // Long | The ID of the custom content for which content properties should be returned.
Long propertyId = 789L; // Long | The ID of the content property being requested.
try {
    ContentProperty result = apiInstance.getCustomContentContentPropertiesById(customContentId, propertyId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#getCustomContentContentPropertiesById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **customContentId** | **Long**| The ID of the custom content for which content properties should be returned. |
 **propertyId** | **Long**| The ID of the content property being requested. |

### Return type

[**ContentProperty**](ContentProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getDatabaseContentProperties"></a>
# **getDatabaseContentProperties**
> MultiEntityResultContentProperty getDatabaseContentProperties(id, key, sort, cursor, limit)

Get content properties for database

Retrieves Content Properties tied to a specified database.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the database.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
Long id = 789L; // Long | The ID of the database for which content properties should be returned.
String key = "key_example"; // String | Filters the response to return a specific content property with matching key (case sensitive).
ContentPropertySortOrder sort = new ContentPropertySortOrder(); // ContentPropertySortOrder | Used to sort the result by a particular field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of attachments per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultContentProperty result = apiInstance.getDatabaseContentProperties(id, key, sort, cursor, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#getDatabaseContentProperties");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the database for which content properties should be returned. |
 **key** | **String**| Filters the response to return a specific content property with matching key (case sensitive). | [optional]
 **sort** | [**ContentPropertySortOrder**](.md)| Used to sort the result by a particular field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of attachments per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultContentProperty**](MultiEntityResultContentProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getDatabaseContentPropertiesById"></a>
# **getDatabaseContentPropertiesById**
> ContentProperty getDatabaseContentPropertiesById(databaseId, propertyId)

Get content property for database by id

Retrieves a specific Content Property by ID that is attached to a specified database.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the database.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
Long databaseId = 789L; // Long | The ID of the database for which content properties should be returned.
Long propertyId = 789L; // Long | The ID of the content property being requested.
try {
    ContentProperty result = apiInstance.getDatabaseContentPropertiesById(databaseId, propertyId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#getDatabaseContentPropertiesById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **databaseId** | **Long**| The ID of the database for which content properties should be returned. |
 **propertyId** | **Long**| The ID of the content property being requested. |

### Return type

[**ContentProperty**](ContentProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getFolderContentProperties"></a>
# **getFolderContentProperties**
> MultiEntityResultContentProperty getFolderContentProperties(id, key, sort, cursor, limit)

Get content properties for folder

Retrieves Content Properties tied to a specified folder.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the folder.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
Long id = 789L; // Long | The ID of the folder for which content properties should be returned.
String key = "key_example"; // String | Filters the response to return a specific content property with matching key (case sensitive).
ContentPropertySortOrder sort = new ContentPropertySortOrder(); // ContentPropertySortOrder | Used to sort the result by a particular field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of attachments per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultContentProperty result = apiInstance.getFolderContentProperties(id, key, sort, cursor, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#getFolderContentProperties");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the folder for which content properties should be returned. |
 **key** | **String**| Filters the response to return a specific content property with matching key (case sensitive). | [optional]
 **sort** | [**ContentPropertySortOrder**](.md)| Used to sort the result by a particular field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of attachments per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultContentProperty**](MultiEntityResultContentProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getFolderContentPropertiesById"></a>
# **getFolderContentPropertiesById**
> ContentProperty getFolderContentPropertiesById(folderId, propertyId)

Get content property for folder by id

Retrieves a specific Content Property by ID that is attached to a specified folder.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the folder.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
Long folderId = 789L; // Long | The ID of the folder for which content properties should be returned.
Long propertyId = 789L; // Long | The ID of the content property being requested.
try {
    ContentProperty result = apiInstance.getFolderContentPropertiesById(folderId, propertyId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#getFolderContentPropertiesById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **folderId** | **Long**| The ID of the folder for which content properties should be returned. |
 **propertyId** | **Long**| The ID of the content property being requested. |

### Return type

[**ContentProperty**](ContentProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getPageContentProperties"></a>
# **getPageContentProperties**
> MultiEntityResultContentProperty getPageContentProperties(pageId, key, sort, cursor, limit)

Get content properties for page

Retrieves Content Properties tied to a specified page.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the page.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
Long pageId = 789L; // Long | The ID of the page for which content properties should be returned.
String key = "key_example"; // String | Filters the response to return a specific content property with matching key (case sensitive).
ContentPropertySortOrder sort = new ContentPropertySortOrder(); // ContentPropertySortOrder | Used to sort the result by a particular field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of attachments per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultContentProperty result = apiInstance.getPageContentProperties(pageId, key, sort, cursor, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#getPageContentProperties");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **pageId** | **Long**| The ID of the page for which content properties should be returned. |
 **key** | **String**| Filters the response to return a specific content property with matching key (case sensitive). | [optional]
 **sort** | [**ContentPropertySortOrder**](.md)| Used to sort the result by a particular field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of attachments per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultContentProperty**](MultiEntityResultContentProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getPageContentPropertiesById"></a>
# **getPageContentPropertiesById**
> ContentProperty getPageContentPropertiesById(pageId, propertyId)

Get content property for page by id

Retrieves a specific Content Property by ID that is attached to a specified page.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the page.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
Long pageId = 789L; // Long | The ID of the page for which content properties should be returned.
Long propertyId = 789L; // Long | The ID of the content property being requested.
try {
    ContentProperty result = apiInstance.getPageContentPropertiesById(pageId, propertyId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#getPageContentPropertiesById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **pageId** | **Long**| The ID of the page for which content properties should be returned. |
 **propertyId** | **Long**| The ID of the content property being requested. |

### Return type

[**ContentProperty**](ContentProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getSmartLinkContentProperties"></a>
# **getSmartLinkContentProperties**
> MultiEntityResultContentProperty getSmartLinkContentProperties(id, key, sort, cursor, limit)

Get content properties for Smart Link in the content tree

Retrieves Content Properties tied to a specified Smart Link in the content tree.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the Smart Link in the content tree.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
Long id = 789L; // Long | The ID of the Smart Link in the content tree for which content properties should be returned.
String key = "key_example"; // String | Filters the response to return a specific content property with matching key (case sensitive).
ContentPropertySortOrder sort = new ContentPropertySortOrder(); // ContentPropertySortOrder | Used to sort the result by a particular field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of attachments per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultContentProperty result = apiInstance.getSmartLinkContentProperties(id, key, sort, cursor, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#getSmartLinkContentProperties");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the Smart Link in the content tree for which content properties should be returned. |
 **key** | **String**| Filters the response to return a specific content property with matching key (case sensitive). | [optional]
 **sort** | [**ContentPropertySortOrder**](.md)| Used to sort the result by a particular field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of attachments per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultContentProperty**](MultiEntityResultContentProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getSmartLinkContentPropertiesById"></a>
# **getSmartLinkContentPropertiesById**
> ContentProperty getSmartLinkContentPropertiesById(embedId, propertyId)

Get content property for Smart Link in the content tree by id

Retrieves a specific Content Property by ID that is attached to a specified Smart Link in the content tree.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the Smart Link in the content tree.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
Long embedId = 789L; // Long | The ID of the Smart Link in the content tree for which content properties should be returned.
Long propertyId = 789L; // Long | The ID of the content property being requested.
try {
    ContentProperty result = apiInstance.getSmartLinkContentPropertiesById(embedId, propertyId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#getSmartLinkContentPropertiesById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **embedId** | **Long**| The ID of the Smart Link in the content tree for which content properties should be returned. |
 **propertyId** | **Long**| The ID of the content property being requested. |

### Return type

[**ContentProperty**](ContentProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getWhiteboardContentProperties"></a>
# **getWhiteboardContentProperties**
> MultiEntityResultContentProperty getWhiteboardContentProperties(id, key, sort, cursor, limit)

Get content properties for whiteboard

Retrieves Content Properties tied to a specified whiteboard.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the whiteboard.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
Long id = 789L; // Long | The ID of the whiteboard for which content properties should be returned.
String key = "key_example"; // String | Filters the response to return a specific content property with matching key (case sensitive).
ContentPropertySortOrder sort = new ContentPropertySortOrder(); // ContentPropertySortOrder | Used to sort the result by a particular field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of attachments per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultContentProperty result = apiInstance.getWhiteboardContentProperties(id, key, sort, cursor, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#getWhiteboardContentProperties");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the whiteboard for which content properties should be returned. |
 **key** | **String**| Filters the response to return a specific content property with matching key (case sensitive). | [optional]
 **sort** | [**ContentPropertySortOrder**](.md)| Used to sort the result by a particular field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of attachments per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultContentProperty**](MultiEntityResultContentProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getWhiteboardContentPropertiesById"></a>
# **getWhiteboardContentPropertiesById**
> ContentProperty getWhiteboardContentPropertiesById(whiteboardId, propertyId)

Get content property for whiteboard by id

Retrieves a specific Content Property by ID that is attached to a specified whiteboard.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the whiteboard.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
Long whiteboardId = 789L; // Long | The ID of the whiteboard for which content properties should be returned.
Long propertyId = 789L; // Long | The ID of the content property being requested.
try {
    ContentProperty result = apiInstance.getWhiteboardContentPropertiesById(whiteboardId, propertyId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#getWhiteboardContentPropertiesById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **whiteboardId** | **Long**| The ID of the whiteboard for which content properties should be returned. |
 **propertyId** | **Long**| The ID of the content property being requested. |

### Return type

[**ContentProperty**](ContentProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateAttachmentPropertyById"></a>
# **updateAttachmentPropertyById**
> ContentProperty updateAttachmentPropertyById(body, attachmentId, propertyId)

Update content property for attachment by id

Update a content property for attachment by its id.   **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to edit the attachment.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
ContentPropertyUpdateRequest body = new ContentPropertyUpdateRequest(); // ContentPropertyUpdateRequest | The content property to be updated.
String attachmentId = "attachmentId_example"; // String | The ID of the attachment the property belongs to.
Long propertyId = 789L; // Long | The ID of the property to be updated.
try {
    ContentProperty result = apiInstance.updateAttachmentPropertyById(body, attachmentId, propertyId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#updateAttachmentPropertyById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ContentPropertyUpdateRequest**](ContentPropertyUpdateRequest.md)| The content property to be updated. |
 **attachmentId** | **String**| The ID of the attachment the property belongs to. |
 **propertyId** | **Long**| The ID of the property to be updated. |

### Return type

[**ContentProperty**](ContentProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateBlogpostPropertyById"></a>
# **updateBlogpostPropertyById**
> ContentProperty updateBlogpostPropertyById(body, blogpostId, propertyId)

Update content property for blog post by id

Update a content property for blog post by its id.   **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to edit the blog post.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
ContentPropertyUpdateRequest body = new ContentPropertyUpdateRequest(); // ContentPropertyUpdateRequest | The content property to be updated.
Long blogpostId = 789L; // Long | The ID of the blog post the property belongs to.
Long propertyId = 789L; // Long | The ID of the property to be updated.
try {
    ContentProperty result = apiInstance.updateBlogpostPropertyById(body, blogpostId, propertyId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#updateBlogpostPropertyById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ContentPropertyUpdateRequest**](ContentPropertyUpdateRequest.md)| The content property to be updated. |
 **blogpostId** | **Long**| The ID of the blog post the property belongs to. |
 **propertyId** | **Long**| The ID of the property to be updated. |

### Return type

[**ContentProperty**](ContentProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateCommentPropertyById"></a>
# **updateCommentPropertyById**
> ContentProperty updateCommentPropertyById(body, commentId, propertyId)

Update content property for comment by id

Update a content property for a comment by its id.   **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to edit the comment.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
ContentPropertyUpdateRequest body = new ContentPropertyUpdateRequest(); // ContentPropertyUpdateRequest | The content property to be updated.
Long commentId = 789L; // Long | The ID of the comment the property belongs to.
Long propertyId = 789L; // Long | The ID of the property to be updated.
try {
    ContentProperty result = apiInstance.updateCommentPropertyById(body, commentId, propertyId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#updateCommentPropertyById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ContentPropertyUpdateRequest**](ContentPropertyUpdateRequest.md)| The content property to be updated. |
 **commentId** | **Long**| The ID of the comment the property belongs to. |
 **propertyId** | **Long**| The ID of the property to be updated. |

### Return type

[**ContentProperty**](ContentProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateCustomContentPropertyById"></a>
# **updateCustomContentPropertyById**
> ContentProperty updateCustomContentPropertyById(body, customContentId, propertyId)

Update content property for custom content by id

Update a content property for a piece of custom content by its id.   **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to edit the custom content.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
ContentPropertyUpdateRequest body = new ContentPropertyUpdateRequest(); // ContentPropertyUpdateRequest | The content property to be updated.
Long customContentId = 789L; // Long | The ID of the custom content the property belongs to.
Long propertyId = 789L; // Long | The ID of the property to be updated.
try {
    ContentProperty result = apiInstance.updateCustomContentPropertyById(body, customContentId, propertyId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#updateCustomContentPropertyById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ContentPropertyUpdateRequest**](ContentPropertyUpdateRequest.md)| The content property to be updated. |
 **customContentId** | **Long**| The ID of the custom content the property belongs to. |
 **propertyId** | **Long**| The ID of the property to be updated. |

### Return type

[**ContentProperty**](ContentProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateDatabasePropertyById"></a>
# **updateDatabasePropertyById**
> ContentProperty updateDatabasePropertyById(body, databaseId, propertyId)

Update content property for database by id

Update a content property for a database by its id.   **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to edit the database.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
ContentPropertyUpdateRequest body = new ContentPropertyUpdateRequest(); // ContentPropertyUpdateRequest | The content property to be updated.
Long databaseId = 789L; // Long | The ID of the database the property belongs to.
Long propertyId = 789L; // Long | The ID of the property to be updated.
try {
    ContentProperty result = apiInstance.updateDatabasePropertyById(body, databaseId, propertyId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#updateDatabasePropertyById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ContentPropertyUpdateRequest**](ContentPropertyUpdateRequest.md)| The content property to be updated. |
 **databaseId** | **Long**| The ID of the database the property belongs to. |
 **propertyId** | **Long**| The ID of the property to be updated. |

### Return type

[**ContentProperty**](ContentProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateFolderPropertyById"></a>
# **updateFolderPropertyById**
> ContentProperty updateFolderPropertyById(body, folderId, propertyId)

Update content property for folder by id

Update a content property for a folder by its id.   **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to edit the folder.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
ContentPropertyUpdateRequest body = new ContentPropertyUpdateRequest(); // ContentPropertyUpdateRequest | The content property to be updated.
Long folderId = 789L; // Long | The ID of the folder the property belongs to.
Long propertyId = 789L; // Long | The ID of the property to be updated.
try {
    ContentProperty result = apiInstance.updateFolderPropertyById(body, folderId, propertyId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#updateFolderPropertyById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ContentPropertyUpdateRequest**](ContentPropertyUpdateRequest.md)| The content property to be updated. |
 **folderId** | **Long**| The ID of the folder the property belongs to. |
 **propertyId** | **Long**| The ID of the property to be updated. |

### Return type

[**ContentProperty**](ContentProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updatePagePropertyById"></a>
# **updatePagePropertyById**
> ContentProperty updatePagePropertyById(body, pageId, propertyId)

Update content property for page by id

Update a content property for a page by its id.   **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to edit the page.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
ContentPropertyUpdateRequest body = new ContentPropertyUpdateRequest(); // ContentPropertyUpdateRequest | The content property to be updated.
Long pageId = 789L; // Long | The ID of the page the property belongs to.
Long propertyId = 789L; // Long | The ID of the property to be updated.
try {
    ContentProperty result = apiInstance.updatePagePropertyById(body, pageId, propertyId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#updatePagePropertyById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ContentPropertyUpdateRequest**](ContentPropertyUpdateRequest.md)| The content property to be updated. |
 **pageId** | **Long**| The ID of the page the property belongs to. |
 **propertyId** | **Long**| The ID of the property to be updated. |

### Return type

[**ContentProperty**](ContentProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateSmartLinkPropertyById"></a>
# **updateSmartLinkPropertyById**
> ContentProperty updateSmartLinkPropertyById(body, embedId, propertyId)

Update content property for Smart Link in the content tree by id

Update a content property for a Smart Link in the content tree by its id.   **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to edit the Smart Link in the content tree.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
ContentPropertyUpdateRequest body = new ContentPropertyUpdateRequest(); // ContentPropertyUpdateRequest | The content property to be updated.
Long embedId = 789L; // Long | The ID of the Smart Link in the content tree the property belongs to.
Long propertyId = 789L; // Long | The ID of the property to be updated.
try {
    ContentProperty result = apiInstance.updateSmartLinkPropertyById(body, embedId, propertyId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#updateSmartLinkPropertyById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ContentPropertyUpdateRequest**](ContentPropertyUpdateRequest.md)| The content property to be updated. |
 **embedId** | **Long**| The ID of the Smart Link in the content tree the property belongs to. |
 **propertyId** | **Long**| The ID of the property to be updated. |

### Return type

[**ContentProperty**](ContentProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateWhiteboardPropertyById"></a>
# **updateWhiteboardPropertyById**
> ContentProperty updateWhiteboardPropertyById(body, whiteboardId, propertyId)

Update content property for whiteboard by id

Update a content property for a whiteboard by its id.   **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to edit the whiteboard.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentPropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentPropertiesApi apiInstance = new ContentPropertiesApi();
ContentPropertyUpdateRequest body = new ContentPropertyUpdateRequest(); // ContentPropertyUpdateRequest | The content property to be updated.
Long whiteboardId = 789L; // Long | The ID of the whiteboard the property belongs to.
Long propertyId = 789L; // Long | The ID of the property to be updated.
try {
    ContentProperty result = apiInstance.updateWhiteboardPropertyById(body, whiteboardId, propertyId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#updateWhiteboardPropertyById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ContentPropertyUpdateRequest**](ContentPropertyUpdateRequest.md)| The content property to be updated. |
 **whiteboardId** | **Long**| The ID of the whiteboard the property belongs to. |
 **propertyId** | **Long**| The ID of the property to be updated. |

### Return type

[**ContentProperty**](ContentProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

