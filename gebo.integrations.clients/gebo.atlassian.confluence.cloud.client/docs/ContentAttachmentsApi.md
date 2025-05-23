# ContentAttachmentsApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createAttachment**](ContentAttachmentsApi.md#createAttachment) | **POST** /wiki/rest/api/content/{id}/child/attachment | Create attachment
[**createOrUpdateAttachments**](ContentAttachmentsApi.md#createOrUpdateAttachments) | **PUT** /wiki/rest/api/content/{id}/child/attachment | Create or update attachment
[**downloadAttatchment**](ContentAttachmentsApi.md#downloadAttatchment) | **GET** /wiki/rest/api/content/{id}/child/attachment/{attachmentId}/download | Get URI to download attachment
[**getAttachments**](ContentAttachmentsApi.md#getAttachments) | **GET** /wiki/rest/api/content/{id}/child/attachment | Get attachments
[**updateAttachmentData**](ContentAttachmentsApi.md#updateAttachmentData) | **POST** /wiki/rest/api/content/{id}/child/attachment/{attachmentId}/data | Update attachment data
[**updateAttachmentProperties**](ContentAttachmentsApi.md#updateAttachmentProperties) | **PUT** /wiki/rest/api/content/{id}/child/attachment/{attachmentId} | Update attachment properties

<a name="createAttachment"></a>
# **createAttachment**
> ContentArray createAttachment(file, comment, minorEdit, id, status)

Create attachment

Adds an attachment to a piece of content. This method only adds a new attachment. If you want to update an existing attachment, use [Create or update attachments](#api-content-id-child-attachment-put).  Note, you must set a &#x60;X-Atlassian-Token: nocheck&#x60; header on the request for this method, otherwise it will be blocked. This protects against XSRF attacks, which is necessary as this method accepts multipart/form-data.  The media type &#x27;multipart/form-data&#x27; is defined in [RFC 7578](https://www.ietf.org/rfc/rfc7578.txt). Most client libraries have classes that make it easier to implement multipart posts, like the [MultipartEntityBuilder](https://hc.apache.org/httpcomponents-client-5.1.x/current/httpclient5/apidocs/) Java class provided by Apache HTTP Components.  Note, according to [RFC 7578](https://tools.ietf.org/html/rfc7578#section-4.5), in the case where the form data is text, the charset parameter for the \&quot;text/plain\&quot; Content-Type may be used to indicate the character encoding used in that part. In the case of this API endpoint, the &#x60;comment&#x60; body parameter should be sent with &#x60;type&#x3D;text/plain&#x60; and &#x60;charset&#x3D;utf-8&#x60; values. This will force the charset to be UTF-8.  Example: This curl command attaches a file (&#x27;example.txt&#x27;) to a container (id&#x3D;&#x27;123&#x27;) with a comment and &#x60;minorEdits&#x60;&#x3D;true.  &#x60;&#x60;&#x60; bash curl -D- \\   -u admin:admin \\   -X POST \\   -H &#x27;X-Atlassian-Token: nocheck&#x27; \\   -F &#x27;file&#x3D;@\&quot;example.txt\&quot;&#x27; \\   -F &#x27;minorEdit&#x3D;\&quot;true\&quot;&#x27; \\   -F &#x27;comment&#x3D;\&quot;Example attachment comment\&quot;; type&#x3D;text/plain; charset&#x3D;utf-8&#x27; \\   http://myhost/rest/api/content/123/child/attachment &#x60;&#x60;&#x60; **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to update the content.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentAttachmentsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentAttachmentsApi apiInstance = new ContentAttachmentsApi();
File file = new File("file_example"); // File | 
File comment = new File("comment_example"); // File | 
File minorEdit = new File("minorEdit_example"); // File | 
String id = "id_example"; // String | The ID of the content to add the attachment to.
String status = "current"; // String | The status of the content that the attachment is being added to.
try {
    ContentArray result = apiInstance.createAttachment(file, comment, minorEdit, id, status);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentAttachmentsApi#createAttachment");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **file** | **File**|  |
 **comment** | **File**|  |
 **minorEdit** | **File**|  |
 **id** | **String**| The ID of the content to add the attachment to. |
 **status** | **String**| The status of the content that the attachment is being added to. | [optional] [default to current] [enum: current, draft]

### Return type

[**ContentArray**](ContentArray.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: application/json

<a name="createOrUpdateAttachments"></a>
# **createOrUpdateAttachments**
> ContentArray createOrUpdateAttachments(file, comment, minorEdit, id, status)

Create or update attachment

Adds an attachment to a piece of content. If the attachment already exists for the content, then the attachment is updated (i.e. a new version of the attachment is created).  Note, you must set a &#x60;X-Atlassian-Token: nocheck&#x60; header on the request for this method, otherwise it will be blocked. This protects against XSRF attacks, which is necessary as this method accepts multipart/form-data.  The media type &#x27;multipart/form-data&#x27; is defined in [RFC 7578](https://www.ietf.org/rfc/rfc7578.txt). Most client libraries have classes that make it easier to implement multipart posts, like the [MultipartEntityBuilder](https://hc.apache.org/httpcomponents-client-5.1.x/current/httpclient5/apidocs/) Java class provided by Apache HTTP Components.  Note, according to [RFC 7578](https://tools.ietf.org/html/rfc7578#section-4.5), in the case where the form data is text, the charset parameter for the \&quot;text/plain\&quot; Content-Type may be used to indicate the character encoding used in that part. In the case of this API endpoint, the &#x60;comment&#x60; body parameter should be sent with &#x60;type&#x3D;text/plain&#x60; and &#x60;charset&#x3D;utf-8&#x60; values. This will force the charset to be UTF-8.  Example: This curl command attaches a file (&#x27;example.txt&#x27;) to a piece of content (id&#x3D;&#x27;123&#x27;) with a comment and &#x60;minorEdits&#x60;&#x3D;true. If the &#x27;example.txt&#x27; file already exists, it will update it with a new version of the attachment.  &#x60;&#x60;&#x60; bash curl -D- \\   -u admin:admin \\   -X PUT \\   -H &#x27;X-Atlassian-Token: nocheck&#x27; \\   -F &#x27;file&#x3D;@\&quot;example.txt\&quot;&#x27; \\   -F &#x27;minorEdit&#x3D;\&quot;true\&quot;&#x27; \\   -F &#x27;comment&#x3D;\&quot;Example attachment comment\&quot;; type&#x3D;text/plain; charset&#x3D;utf-8&#x27; \\   http://myhost/rest/api/content/123/child/attachment &#x60;&#x60;&#x60; **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to update the content.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentAttachmentsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentAttachmentsApi apiInstance = new ContentAttachmentsApi();
File file = new File("file_example"); // File | 
File comment = new File("comment_example"); // File | 
File minorEdit = new File("minorEdit_example"); // File | 
String id = "id_example"; // String | The ID of the content to add the attachment to.
String status = "current"; // String | The status of the content that the attachment is being added to. This should always be set to 'current'.
try {
    ContentArray result = apiInstance.createOrUpdateAttachments(file, comment, minorEdit, id, status);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentAttachmentsApi#createOrUpdateAttachments");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **file** | **File**|  |
 **comment** | **File**|  |
 **minorEdit** | **File**|  |
 **id** | **String**| The ID of the content to add the attachment to. |
 **status** | **String**| The status of the content that the attachment is being added to. This should always be set to &#x27;current&#x27;. | [optional] [default to current] [enum: current, draft]

### Return type

[**ContentArray**](ContentArray.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: application/json

<a name="downloadAttatchment"></a>
# **downloadAttatchment**
> downloadAttatchment(id, attachmentId, version)

Get URI to download attachment

Redirects the client to a URL that serves an attachment&#x27;s binary data.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentAttachmentsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentAttachmentsApi apiInstance = new ContentAttachmentsApi();
String id = "id_example"; // String | The ID of the content that the attachment is attached to.
String attachmentId = "attachmentId_example"; // String | The ID of the attachment to download.
Integer version = 56; // Integer | The version of the attachment. If this parameter is absent, the redirect URI will download the latest version of the attachment.
try {
    apiInstance.downloadAttatchment(id, attachmentId, version);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentAttachmentsApi#downloadAttatchment");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The ID of the content that the attachment is attached to. |
 **attachmentId** | **String**| The ID of the attachment to download. |
 **version** | **Integer**| The version of the attachment. If this parameter is absent, the redirect URI will download the latest version of the attachment. | [optional]

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="getAttachments"></a>
# **getAttachments**
> ContentArray getAttachments(id, expand, start, limit, filename, mediaType)

Get attachments

Deprecated, use [Confluence&#x27;s v2 API](https://developer.atlassian.com/cloud/confluence/rest/v2/intro/).  Returns the attachments for a piece of content.  By default, the following objects are expanded: &#x60;metadata&#x60;.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content. If the content is a blog post, &#x27;View&#x27; permission for the space is required.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentAttachmentsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentAttachmentsApi apiInstance = new ContentAttachmentsApi();
String id = "id_example"; // String | The ID of the content to be queried for its attachments.
List<String> expand = Arrays.asList("expand_example"); // List<String> | A multi-value parameter indicating which properties of the content to expand.  - `childTypes.all` returns whether the content has attachments, comments, or child pages/whiteboards. Use this if you only need to check whether the content has children of a particular type. - `childTypes.attachment` returns whether the content has attachments. - `childTypes.comment` returns whether the content has comments. - `childTypes.page` returns whether the content has child pages. - `childTypes.whiteboard` returns whether the content has child whiteboards. - `childTypes.database` returns whether the content has child databases. - `childTypes.embed` returns whether the content has child embeds (smartlinks). - `childTypes.folder` returns whether the content has child folders. - `container` returns the space that the content is in. This is the same as the information returned by [Get space](#api-space-spaceKey-get). - `metadata.currentuser` returns information about the current user in relation to the content, including when they last viewed it, modified it, contributed to it, or added it as a favorite. - `metadata.properties` returns content properties that have been set via the Confluence REST API. - `metadata.labels` returns the labels that have been added to the content. - `metadata.frontend` this property is only used by Atlassian. - `operations` returns the operations for the content, which are used when setting permissions. - `children.page` returns pages that are descendants at the level immediately below the content. - `children.whiteboard` returns whiteboards that are descendants at the level immediately below the content. - `children.database` returns databases that are descendants at the level immediately below the content. - `children.embed` returns embeds (smartlinks) that are descendants at the level immediately below the content. - `children.folder` returns folders that are descendants at the level immediately below the content. - `children.attachment` returns all attachments for the content. - `children.comment` returns all comments on the content. - `restrictions.read.restrictions.user` returns the users that have permission to read the content. - `restrictions.read.restrictions.group` returns the groups that have permission to read the content. Note that this may return deleted groups, because deleting a group doesn't remove associated restrictions. - `restrictions.update.restrictions.user` returns the users that have permission to update the content. - `restrictions.update.restrictions.group` returns the groups that have permission to update the content. Note that this may return deleted groups because deleting a group doesn't remove associated restrictions. - `history` returns the history of the content, including the date it was created. - `history.lastUpdated` returns information about the most recent update of the content, including who updated it and when it was updated. - `history.previousVersion` returns information about the update prior to the current content update. - `history.contributors` returns all of the users who have contributed to the content. - `history.nextVersion` returns information about the update after to the current content update. - `ancestors` returns the parent content, if the content is a page or whiteboard. - `body` returns the body of the content in different formats, including the editor format, view format, and export format. - `body.storage` returns the body of content in storage format. - `body.view` returns the body of content in view format. - `version` returns information about the most recent update of the content, including who updated it and when it was updated. - `descendants.page` returns pages that are descendants at any level below the content. - `descendants.whiteboard` returns whiteboards that are descendants at any level below the content. - `descendants.database` returns databases that are descendants at any level below the content. - `descendants.embed` returns embeds (smartlinks) that are descendants at any level below the content. - `descendants.folder` returns folders that are descendants at any level below the content. - `descendants.attachment` returns all attachments for the content, same as `children.attachment`. - `descendants.comment` returns all comments on the content, same as `children.comment`. - `space` returns the space that the content is in. This is the same as the information returned by [Get space](#api-space-spaceKey-get).  In addition, the following comment-specific expansions can be used: - `extensions.inlineProperties` returns inline comment-specific properties. - `extensions.resolution` returns the resolution status of each comment.
Integer start = 0; // Integer | The starting index of the returned attachments.
Integer limit = 25; // Integer | The maximum number of attachments to return per page. Note, this may be restricted by fixed system limits.
String filename = "filename_example"; // String | Filter the results to attachments that match the filename.
String mediaType = "mediaType_example"; // String | Filter the results to attachments that match the media type.
try {
    ContentArray result = apiInstance.getAttachments(id, expand, start, limit, filename, mediaType);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentAttachmentsApi#getAttachments");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The ID of the content to be queried for its attachments. |
 **expand** | [**List&lt;String&gt;**](String.md)| A multi-value parameter indicating which properties of the content to expand.  - &#x60;childTypes.all&#x60; returns whether the content has attachments, comments, or child pages/whiteboards. Use this if you only need to check whether the content has children of a particular type. - &#x60;childTypes.attachment&#x60; returns whether the content has attachments. - &#x60;childTypes.comment&#x60; returns whether the content has comments. - &#x60;childTypes.page&#x60; returns whether the content has child pages. - &#x60;childTypes.whiteboard&#x60; returns whether the content has child whiteboards. - &#x60;childTypes.database&#x60; returns whether the content has child databases. - &#x60;childTypes.embed&#x60; returns whether the content has child embeds (smartlinks). - &#x60;childTypes.folder&#x60; returns whether the content has child folders. - &#x60;container&#x60; returns the space that the content is in. This is the same as the information returned by [Get space](#api-space-spaceKey-get). - &#x60;metadata.currentuser&#x60; returns information about the current user in relation to the content, including when they last viewed it, modified it, contributed to it, or added it as a favorite. - &#x60;metadata.properties&#x60; returns content properties that have been set via the Confluence REST API. - &#x60;metadata.labels&#x60; returns the labels that have been added to the content. - &#x60;metadata.frontend&#x60; this property is only used by Atlassian. - &#x60;operations&#x60; returns the operations for the content, which are used when setting permissions. - &#x60;children.page&#x60; returns pages that are descendants at the level immediately below the content. - &#x60;children.whiteboard&#x60; returns whiteboards that are descendants at the level immediately below the content. - &#x60;children.database&#x60; returns databases that are descendants at the level immediately below the content. - &#x60;children.embed&#x60; returns embeds (smartlinks) that are descendants at the level immediately below the content. - &#x60;children.folder&#x60; returns folders that are descendants at the level immediately below the content. - &#x60;children.attachment&#x60; returns all attachments for the content. - &#x60;children.comment&#x60; returns all comments on the content. - &#x60;restrictions.read.restrictions.user&#x60; returns the users that have permission to read the content. - &#x60;restrictions.read.restrictions.group&#x60; returns the groups that have permission to read the content. Note that this may return deleted groups, because deleting a group doesn&#x27;t remove associated restrictions. - &#x60;restrictions.update.restrictions.user&#x60; returns the users that have permission to update the content. - &#x60;restrictions.update.restrictions.group&#x60; returns the groups that have permission to update the content. Note that this may return deleted groups because deleting a group doesn&#x27;t remove associated restrictions. - &#x60;history&#x60; returns the history of the content, including the date it was created. - &#x60;history.lastUpdated&#x60; returns information about the most recent update of the content, including who updated it and when it was updated. - &#x60;history.previousVersion&#x60; returns information about the update prior to the current content update. - &#x60;history.contributors&#x60; returns all of the users who have contributed to the content. - &#x60;history.nextVersion&#x60; returns information about the update after to the current content update. - &#x60;ancestors&#x60; returns the parent content, if the content is a page or whiteboard. - &#x60;body&#x60; returns the body of the content in different formats, including the editor format, view format, and export format. - &#x60;body.storage&#x60; returns the body of content in storage format. - &#x60;body.view&#x60; returns the body of content in view format. - &#x60;version&#x60; returns information about the most recent update of the content, including who updated it and when it was updated. - &#x60;descendants.page&#x60; returns pages that are descendants at any level below the content. - &#x60;descendants.whiteboard&#x60; returns whiteboards that are descendants at any level below the content. - &#x60;descendants.database&#x60; returns databases that are descendants at any level below the content. - &#x60;descendants.embed&#x60; returns embeds (smartlinks) that are descendants at any level below the content. - &#x60;descendants.folder&#x60; returns folders that are descendants at any level below the content. - &#x60;descendants.attachment&#x60; returns all attachments for the content, same as &#x60;children.attachment&#x60;. - &#x60;descendants.comment&#x60; returns all comments on the content, same as &#x60;children.comment&#x60;. - &#x60;space&#x60; returns the space that the content is in. This is the same as the information returned by [Get space](#api-space-spaceKey-get).  In addition, the following comment-specific expansions can be used: - &#x60;extensions.inlineProperties&#x60; returns inline comment-specific properties. - &#x60;extensions.resolution&#x60; returns the resolution status of each comment. | [optional]
 **start** | **Integer**| The starting index of the returned attachments. | [optional] [default to 0] [enum: 0]
 **limit** | **Integer**| The maximum number of attachments to return per page. Note, this may be restricted by fixed system limits. | [optional] [default to 25] [enum: 0]
 **filename** | **String**| Filter the results to attachments that match the filename. | [optional]
 **mediaType** | **String**| Filter the results to attachments that match the media type. | [optional]

### Return type

[**ContentArray**](ContentArray.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateAttachmentData"></a>
# **updateAttachmentData**
> Content updateAttachmentData(file, comment, minorEdit, id, attachmentId)

Update attachment data

Updates the binary data of an attachment, given the attachment ID, and optionally the comment and the minor edit field.  This method is essentially the same as [Create or update attachments](#api-content-id-child-attachment-put), except that it matches the attachment ID rather than the name.  Note, you must set a &#x60;X-Atlassian-Token: nocheck&#x60; header on the request for this method, otherwise it will be blocked. This protects against XSRF attacks, which is necessary as this method accepts multipart/form-data.  The media type &#x27;multipart/form-data&#x27; is defined in [RFC 7578](https://www.ietf.org/rfc/rfc7578.txt). Most client libraries have classes that make it easier to implement multipart posts, like the [MultipartEntityBuilder](https://hc.apache.org/httpcomponents-client-5.1.x/current/httpclient5/apidocs/) Java class provided by Apache HTTP Components.  Note, according to [RFC 7578](https://tools.ietf.org/html/rfc7578#section-4.5), in the case where the form data is text, the charset parameter for the \&quot;text/plain\&quot; Content-Type may be used to indicate the character encoding used in that part. In the case of this API endpoint, the &#x60;comment&#x60; body parameter should be sent with &#x60;type&#x3D;text/plain&#x60; and &#x60;charset&#x3D;utf-8&#x60; values. This will force the charset to be UTF-8.  Example: This curl command updates an attachment (id&#x3D;&#x27;att456&#x27;) that is attached to a piece of content (id&#x3D;&#x27;123&#x27;) with a comment and &#x60;minorEdits&#x60;&#x3D;true.  &#x60;&#x60;&#x60; bash curl -D- \\   -u admin:admin \\   -X POST \\   -H &#x27;X-Atlassian-Token: nocheck&#x27; \\   -F &#x27;file&#x3D;@\&quot;example.txt\&quot;&#x27; \\   -F &#x27;minorEdit&#x3D;\&quot;true\&quot;&#x27; \\   -F &#x27;comment&#x3D;\&quot;Example attachment comment\&quot;; type&#x3D;text/plain; charset&#x3D;utf-8&#x27; \\   http://myhost/rest/api/content/123/child/attachment/att456/data &#x60;&#x60;&#x60; **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to update the content.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentAttachmentsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentAttachmentsApi apiInstance = new ContentAttachmentsApi();
File file = new File("file_example"); // File | 
File comment = new File("comment_example"); // File | 
File minorEdit = new File("minorEdit_example"); // File | 
String id = "id_example"; // String | The ID of the content that the attachment is attached to.
String attachmentId = "attachmentId_example"; // String | The ID of the attachment to update.
try {
    Content result = apiInstance.updateAttachmentData(file, comment, minorEdit, id, attachmentId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentAttachmentsApi#updateAttachmentData");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **file** | **File**|  |
 **comment** | **File**|  |
 **minorEdit** | **File**|  |
 **id** | **String**| The ID of the content that the attachment is attached to. |
 **attachmentId** | **String**| The ID of the attachment to update. |

### Return type

[**Content**](Content.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: application/json

<a name="updateAttachmentProperties"></a>
# **updateAttachmentProperties**
> Content updateAttachmentProperties(body, id, attachmentId)

Update attachment properties

Updates the attachment properties, i.e. the non-binary data of an attachment like the filename, media-type, comment, and parent container.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to update the content.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentAttachmentsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentAttachmentsApi apiInstance = new ContentAttachmentsApi();
Map<String, Object> body = new Map(); // Map<String, Object> | The details of the attachment to be updated.
String id = "id_example"; // String | The ID of the content that the attachment is attached to.
String attachmentId = "attachmentId_example"; // String | The ID of the attachment to update.
try {
    Content result = apiInstance.updateAttachmentProperties(body, id, attachmentId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentAttachmentsApi#updateAttachmentProperties");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Map&lt;String, Object&gt;**](Map.md)| The details of the attachment to be updated. |
 **id** | **String**| The ID of the content that the attachment is attached to. |
 **attachmentId** | **String**| The ID of the attachment to update. |

### Return type

[**Content**](Content.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

