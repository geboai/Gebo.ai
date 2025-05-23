# SpaceApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createPrivateSpace**](SpaceApi.md#createPrivateSpace) | **POST** /wiki/rest/api/space/_private | Create private space
[**createSpace**](SpaceApi.md#createSpace) | **POST** /wiki/rest/api/space | Create space
[**deleteSpace**](SpaceApi.md#deleteSpace) | **DELETE** /wiki/rest/api/space/{spaceKey} | Delete space
[**getContentByTypeForSpace**](SpaceApi.md#getContentByTypeForSpace) | **GET** /wiki/rest/api/space/{spaceKey}/content/{type} | Get content by type for space
[**getContentForSpace**](SpaceApi.md#getContentForSpace) | **GET** /wiki/rest/api/space/{spaceKey}/content | Get content for space
[**getSpace**](SpaceApi.md#getSpace) | **GET** /wiki/rest/api/space/{spaceKey} | Get space
[**getSpaces**](SpaceApi.md#getSpaces) | **GET** /wiki/rest/api/space | Get spaces
[**updateSpace**](SpaceApi.md#updateSpace) | **PUT** /wiki/rest/api/space/{spaceKey} | Update space

<a name="createPrivateSpace"></a>
# **createPrivateSpace**
> Space createPrivateSpace(body)

Create private space

Creates a new space that is only visible to the creator. This method is the same as the [Create space](#api-space-post) method with permissions set to the current user only. Note, currently you cannot set space labels when creating a space.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Create Space(s)&#x27; global permission.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.SpaceApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

SpaceApi apiInstance = new SpaceApi();
Map<String, Object> body = new Map(); // Map<String, Object> | The space to be created.
try {
    Space result = apiInstance.createPrivateSpace(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SpaceApi#createPrivateSpace");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Map&lt;String, Object&gt;**](Map.md)| The space to be created. |

### Return type

[**Space**](Space.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createSpace"></a>
# **createSpace**
> Space createSpace(body)

Create space

Creates a new space. Note, currently you cannot set space labels when creating a space.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Create Space(s)&#x27; global permission.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.SpaceApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

SpaceApi apiInstance = new SpaceApi();
Map<String, Object> body = new Map(); // Map<String, Object> | The space to be created.
try {
    Space result = apiInstance.createSpace(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SpaceApi#createSpace");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Map&lt;String, Object&gt;**](Map.md)| The space to be created. |

### Return type

[**Space**](Space.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deleteSpace"></a>
# **deleteSpace**
> LongTask deleteSpace(spaceKey)

Delete space

Permanently deletes a space without sending it to the trash. Note, the space will be deleted in a long running task. Therefore, the space may not be deleted yet when this method has returned. Clients should poll the status link that is returned in the response until the task completes.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Admin&#x27; permission for the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.SpaceApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

SpaceApi apiInstance = new SpaceApi();
String spaceKey = "spaceKey_example"; // String | The key of the space to delete.
try {
    LongTask result = apiInstance.deleteSpace(spaceKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SpaceApi#deleteSpace");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **spaceKey** | **String**| The key of the space to delete. |

### Return type

[**LongTask**](LongTask.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getContentByTypeForSpace"></a>
# **getContentByTypeForSpace**
> ContentArray getContentByTypeForSpace(spaceKey, type, depth, expand, start, limit)

Get content by type for space

Deprecated, use [Confluence&#x27;s v2 API](https://developer.atlassian.com/cloud/confluence/rest/v2/intro/).  Returns all content of a given type, in a space. The returned content is ordered by content ID in ascending order.  Starting on Dec 10, 2024, if the expand query parameter is used with the &#x60;body.export_view&#x60; and/or &#x60;body.styled_view&#x60; properties, then the query limit parameter will be restricted to a maximum value of 25.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;View&#x27; permission for the space. Note, the returned list will only contain content that the current user has permission to view.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.SpaceApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

SpaceApi apiInstance = new SpaceApi();
String spaceKey = "spaceKey_example"; // String | The key of the space to be queried for its content.
String type = "type_example"; // String | The type of content to return. `page`, `blogpost`, `<some_customContentType>`.
String depth = "all"; // String | Filter the results to content at the root level of the space or all content.
List<String> expand = Arrays.asList("expand_example"); // List<String> | A multi-value parameter indicating which properties of the content to expand.  - `childTypes.all` returns whether the content has attachments, comments, or child pages/whiteboards. Use this if you only need to check whether the content has children of a particular type. - `childTypes.attachment` returns whether the content has attachments. - `childTypes.comment` returns whether the content has comments. - `childTypes.page` returns whether the content has child pages. - `childTypes.whiteboard` returns whether the content has child whiteboards. - `childTypes.database` returns whether the content has child databases. - `childTypes.embed` returns whether the content has child embeds (smartlinks). - `childTypes.folder` returns whether the content has child folders. - `container` returns the space that the content is in. This is the same as the information returned by [Get space](#api-space-spaceKey-get). - `metadata.currentuser` returns information about the current user in relation to the content, including when they last viewed it, modified it, contributed to it, or added it as a favorite. - `metadata.properties` returns content properties that have been set via the Confluence REST API. - `metadata.labels` returns the labels that have been added to the content. - `metadata.frontend` this property is only used by Atlassian. - `operations` returns the operations for the content, which are used when setting permissions. - `children.page` returns pages that are descendants at the level immediately below the content. - `children.whiteboard` returns whiteboards that are descendants at the level immediately below the content. - `children.database` returns databases that are descendants at the level immediately below the content. - `children.embed` returns embeds (smartlinks) that are descendants at the level immediately below the content. - `children.folder` returns folders that are descendants at the level immediately below the content. - `children.attachment` returns all attachments for the content. - `children.comment` returns all comments on the content. - `restrictions.read.restrictions.user` returns the users that have permission to read the content. - `restrictions.read.restrictions.group` returns the groups that have permission to read the content. Note that this may return deleted groups, because deleting a group doesn't remove associated restrictions. - `restrictions.update.restrictions.user` returns the users that have permission to update the content. - `restrictions.update.restrictions.group` returns the groups that have permission to update the content. Note that this may return deleted groups because deleting a group doesn't remove associated restrictions. - `history` returns the history of the content, including the date it was created. - `history.lastUpdated` returns information about the most recent update of the content, including who updated it and when it was updated. - `history.previousVersion` returns information about the update prior to the current content update. - `history.contributors` returns all of the users who have contributed to the content. - `history.nextVersion` returns information about the update after to the current content update. - `ancestors` returns the parent content, if the content is a page or whiteboard. - `body` returns the body of the content in different formats, including the editor format, view format, and export format. - `body.storage` returns the body of content in storage format. - `body.view` returns the body of content in view format. - `version` returns information about the most recent update of the content, including who updated it and when it was updated. - `descendants.page` returns pages that are descendants at any level below the content. - `descendants.whiteboard` returns whiteboards that are descendants at any level below the content. - `descendants.database` returns databases that are descendants at any level below the content. - `descendants.embed` returns embeds (smartlinks) that are descendants at any level below the content. - `descendants.folder` returns folders that are descendants at any level below the content. - `descendants.attachment` returns all attachments for the content, same as `children.attachment`. - `descendants.comment` returns all comments on the content, same as `children.comment`. - `space` returns the space that the content is in. This is the same as the information returned by [Get space](#api-space-spaceKey-get).  In addition, the following comment-specific expansions can be used: - `extensions.inlineProperties` returns inline comment-specific properties. - `extensions.resolution` returns the resolution status of each comment.
Integer start = 0; // Integer | The starting index of the returned content.
Integer limit = 25; // Integer | The maximum number of content objects to return per page. Note, this may be restricted by fixed system limits.
try {
    ContentArray result = apiInstance.getContentByTypeForSpace(spaceKey, type, depth, expand, start, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SpaceApi#getContentByTypeForSpace");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **spaceKey** | **String**| The key of the space to be queried for its content. |
 **type** | **String**| The type of content to return. &#x60;page&#x60;, &#x60;blogpost&#x60;, &#x60;&lt;some_customContentType&gt;&#x60;. |
 **depth** | **String**| Filter the results to content at the root level of the space or all content. | [optional] [default to all] [enum: all, root]
 **expand** | [**List&lt;String&gt;**](String.md)| A multi-value parameter indicating which properties of the content to expand.  - &#x60;childTypes.all&#x60; returns whether the content has attachments, comments, or child pages/whiteboards. Use this if you only need to check whether the content has children of a particular type. - &#x60;childTypes.attachment&#x60; returns whether the content has attachments. - &#x60;childTypes.comment&#x60; returns whether the content has comments. - &#x60;childTypes.page&#x60; returns whether the content has child pages. - &#x60;childTypes.whiteboard&#x60; returns whether the content has child whiteboards. - &#x60;childTypes.database&#x60; returns whether the content has child databases. - &#x60;childTypes.embed&#x60; returns whether the content has child embeds (smartlinks). - &#x60;childTypes.folder&#x60; returns whether the content has child folders. - &#x60;container&#x60; returns the space that the content is in. This is the same as the information returned by [Get space](#api-space-spaceKey-get). - &#x60;metadata.currentuser&#x60; returns information about the current user in relation to the content, including when they last viewed it, modified it, contributed to it, or added it as a favorite. - &#x60;metadata.properties&#x60; returns content properties that have been set via the Confluence REST API. - &#x60;metadata.labels&#x60; returns the labels that have been added to the content. - &#x60;metadata.frontend&#x60; this property is only used by Atlassian. - &#x60;operations&#x60; returns the operations for the content, which are used when setting permissions. - &#x60;children.page&#x60; returns pages that are descendants at the level immediately below the content. - &#x60;children.whiteboard&#x60; returns whiteboards that are descendants at the level immediately below the content. - &#x60;children.database&#x60; returns databases that are descendants at the level immediately below the content. - &#x60;children.embed&#x60; returns embeds (smartlinks) that are descendants at the level immediately below the content. - &#x60;children.folder&#x60; returns folders that are descendants at the level immediately below the content. - &#x60;children.attachment&#x60; returns all attachments for the content. - &#x60;children.comment&#x60; returns all comments on the content. - &#x60;restrictions.read.restrictions.user&#x60; returns the users that have permission to read the content. - &#x60;restrictions.read.restrictions.group&#x60; returns the groups that have permission to read the content. Note that this may return deleted groups, because deleting a group doesn&#x27;t remove associated restrictions. - &#x60;restrictions.update.restrictions.user&#x60; returns the users that have permission to update the content. - &#x60;restrictions.update.restrictions.group&#x60; returns the groups that have permission to update the content. Note that this may return deleted groups because deleting a group doesn&#x27;t remove associated restrictions. - &#x60;history&#x60; returns the history of the content, including the date it was created. - &#x60;history.lastUpdated&#x60; returns information about the most recent update of the content, including who updated it and when it was updated. - &#x60;history.previousVersion&#x60; returns information about the update prior to the current content update. - &#x60;history.contributors&#x60; returns all of the users who have contributed to the content. - &#x60;history.nextVersion&#x60; returns information about the update after to the current content update. - &#x60;ancestors&#x60; returns the parent content, if the content is a page or whiteboard. - &#x60;body&#x60; returns the body of the content in different formats, including the editor format, view format, and export format. - &#x60;body.storage&#x60; returns the body of content in storage format. - &#x60;body.view&#x60; returns the body of content in view format. - &#x60;version&#x60; returns information about the most recent update of the content, including who updated it and when it was updated. - &#x60;descendants.page&#x60; returns pages that are descendants at any level below the content. - &#x60;descendants.whiteboard&#x60; returns whiteboards that are descendants at any level below the content. - &#x60;descendants.database&#x60; returns databases that are descendants at any level below the content. - &#x60;descendants.embed&#x60; returns embeds (smartlinks) that are descendants at any level below the content. - &#x60;descendants.folder&#x60; returns folders that are descendants at any level below the content. - &#x60;descendants.attachment&#x60; returns all attachments for the content, same as &#x60;children.attachment&#x60;. - &#x60;descendants.comment&#x60; returns all comments on the content, same as &#x60;children.comment&#x60;. - &#x60;space&#x60; returns the space that the content is in. This is the same as the information returned by [Get space](#api-space-spaceKey-get).  In addition, the following comment-specific expansions can be used: - &#x60;extensions.inlineProperties&#x60; returns inline comment-specific properties. - &#x60;extensions.resolution&#x60; returns the resolution status of each comment. | [optional]
 **start** | **Integer**| The starting index of the returned content. | [optional] [default to 0] [enum: 0]
 **limit** | **Integer**| The maximum number of content objects to return per page. Note, this may be restricted by fixed system limits. | [optional] [default to 25] [enum: 0]

### Return type

[**ContentArray**](ContentArray.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getContentForSpace"></a>
# **getContentForSpace**
> InlineResponse2001 getContentForSpace(spaceKey, depth, expand, start, limit)

Get content for space

Deprecated, use [Confluence&#x27;s v2 API](https://developer.atlassian.com/cloud/confluence/rest/v2/intro/).  Returns all content in a space. The returned content is grouped by type (pages then blogposts), then ordered by content ID in ascending order.  Starting on Dec 10, 2024, if the expand query parameter is used with the &#x60;body.export_view&#x60; and/or &#x60;body.styled_view&#x60; properties, then the query limit parameter will be restricted to a maximum value of 25.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;View&#x27; permission for the space. Note, the returned list will only contain content that the current user has permission to view.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.SpaceApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

SpaceApi apiInstance = new SpaceApi();
String spaceKey = "spaceKey_example"; // String | The key of the space to be queried for its content.
String depth = "all"; // String | Filter the results to content at the root level of the space or all content.
List<String> expand = Arrays.asList("expand_example"); // List<String> | A multi-value parameter indicating which properties of the content to expand.  - `childTypes.all` returns whether the content has attachments, comments, or child pages/whiteboards. Use this if you only need to check whether the content has children of a particular type. - `childTypes.attachment` returns whether the content has attachments. - `childTypes.comment` returns whether the content has comments. - `childTypes.page` returns whether the content has child pages. - `childTypes.whiteboard` returns whether the content has child whiteboards. - `childTypes.database` returns whether the content has child databases. - `childTypes.embed` returns whether the content has child embeds (smartlinks). - `childTypes.folder` returns whether the content has child folders. - `container` returns the space that the content is in. This is the same as the information returned by [Get space](#api-space-spaceKey-get). - `metadata.currentuser` returns information about the current user in relation to the content, including when they last viewed it, modified it, contributed to it, or added it as a favorite. - `metadata.properties` returns content properties that have been set via the Confluence REST API. - `metadata.labels` returns the labels that have been added to the content. - `metadata.frontend` this property is only used by Atlassian. - `operations` returns the operations for the content, which are used when setting permissions. - `children.page` returns pages that are descendants at the level immediately below the content. - `children.whiteboard` returns whiteboards that are descendants at the level immediately below the content. - `children.database` returns databases that are descendants at the level immediately below the content. - `children.embed` returns embeds (smartlinks) that are descendants at the level immediately below the content. - `children.folder` returns folders that are descendants at the level immediately below the content. - `children.attachment` returns all attachments for the content. - `children.comment` returns all comments on the content. - `restrictions.read.restrictions.user` returns the users that have permission to read the content. - `restrictions.read.restrictions.group` returns the groups that have permission to read the content. Note that this may return deleted groups, because deleting a group doesn't remove associated restrictions. - `restrictions.update.restrictions.user` returns the users that have permission to update the content. - `restrictions.update.restrictions.group` returns the groups that have permission to update the content. Note that this may return deleted groups because deleting a group doesn't remove associated restrictions. - `history` returns the history of the content, including the date it was created. - `history.lastUpdated` returns information about the most recent update of the content, including who updated it and when it was updated. - `history.previousVersion` returns information about the update prior to the current content update. - `history.contributors` returns all of the users who have contributed to the content. - `history.nextVersion` returns information about the update after to the current content update. - `ancestors` returns the parent content, if the content is a page or whiteboard. - `body` returns the body of the content in different formats, including the editor format, view format, and export format. - `body.storage` returns the body of content in storage format. - `body.view` returns the body of content in view format. - `version` returns information about the most recent update of the content, including who updated it and when it was updated. - `descendants.page` returns pages that are descendants at any level below the content. - `descendants.whiteboard` returns whiteboards that are descendants at any level below the content. - `descendants.database` returns databases that are descendants at any level below the content. - `descendants.embed` returns embeds (smartlinks) that are descendants at any level below the content. - `descendants.folder` returns folders that are descendants at any level below the content. - `descendants.attachment` returns all attachments for the content, same as `children.attachment`. - `descendants.comment` returns all comments on the content, same as `children.comment`. - `space` returns the space that the content is in. This is the same as the information returned by [Get space](#api-space-spaceKey-get).  In addition, the following comment-specific expansions can be used: - `extensions.inlineProperties` returns inline comment-specific properties. - `extensions.resolution` returns the resolution status of each comment.
Integer start = 0; // Integer | The starting index of the returned content.
Integer limit = 25; // Integer | The maximum number of content objects to return per page. Note, this may be restricted by fixed system limits.
try {
    InlineResponse2001 result = apiInstance.getContentForSpace(spaceKey, depth, expand, start, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SpaceApi#getContentForSpace");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **spaceKey** | **String**| The key of the space to be queried for its content. |
 **depth** | **String**| Filter the results to content at the root level of the space or all content. | [optional] [default to all] [enum: all, root]
 **expand** | [**List&lt;String&gt;**](String.md)| A multi-value parameter indicating which properties of the content to expand.  - &#x60;childTypes.all&#x60; returns whether the content has attachments, comments, or child pages/whiteboards. Use this if you only need to check whether the content has children of a particular type. - &#x60;childTypes.attachment&#x60; returns whether the content has attachments. - &#x60;childTypes.comment&#x60; returns whether the content has comments. - &#x60;childTypes.page&#x60; returns whether the content has child pages. - &#x60;childTypes.whiteboard&#x60; returns whether the content has child whiteboards. - &#x60;childTypes.database&#x60; returns whether the content has child databases. - &#x60;childTypes.embed&#x60; returns whether the content has child embeds (smartlinks). - &#x60;childTypes.folder&#x60; returns whether the content has child folders. - &#x60;container&#x60; returns the space that the content is in. This is the same as the information returned by [Get space](#api-space-spaceKey-get). - &#x60;metadata.currentuser&#x60; returns information about the current user in relation to the content, including when they last viewed it, modified it, contributed to it, or added it as a favorite. - &#x60;metadata.properties&#x60; returns content properties that have been set via the Confluence REST API. - &#x60;metadata.labels&#x60; returns the labels that have been added to the content. - &#x60;metadata.frontend&#x60; this property is only used by Atlassian. - &#x60;operations&#x60; returns the operations for the content, which are used when setting permissions. - &#x60;children.page&#x60; returns pages that are descendants at the level immediately below the content. - &#x60;children.whiteboard&#x60; returns whiteboards that are descendants at the level immediately below the content. - &#x60;children.database&#x60; returns databases that are descendants at the level immediately below the content. - &#x60;children.embed&#x60; returns embeds (smartlinks) that are descendants at the level immediately below the content. - &#x60;children.folder&#x60; returns folders that are descendants at the level immediately below the content. - &#x60;children.attachment&#x60; returns all attachments for the content. - &#x60;children.comment&#x60; returns all comments on the content. - &#x60;restrictions.read.restrictions.user&#x60; returns the users that have permission to read the content. - &#x60;restrictions.read.restrictions.group&#x60; returns the groups that have permission to read the content. Note that this may return deleted groups, because deleting a group doesn&#x27;t remove associated restrictions. - &#x60;restrictions.update.restrictions.user&#x60; returns the users that have permission to update the content. - &#x60;restrictions.update.restrictions.group&#x60; returns the groups that have permission to update the content. Note that this may return deleted groups because deleting a group doesn&#x27;t remove associated restrictions. - &#x60;history&#x60; returns the history of the content, including the date it was created. - &#x60;history.lastUpdated&#x60; returns information about the most recent update of the content, including who updated it and when it was updated. - &#x60;history.previousVersion&#x60; returns information about the update prior to the current content update. - &#x60;history.contributors&#x60; returns all of the users who have contributed to the content. - &#x60;history.nextVersion&#x60; returns information about the update after to the current content update. - &#x60;ancestors&#x60; returns the parent content, if the content is a page or whiteboard. - &#x60;body&#x60; returns the body of the content in different formats, including the editor format, view format, and export format. - &#x60;body.storage&#x60; returns the body of content in storage format. - &#x60;body.view&#x60; returns the body of content in view format. - &#x60;version&#x60; returns information about the most recent update of the content, including who updated it and when it was updated. - &#x60;descendants.page&#x60; returns pages that are descendants at any level below the content. - &#x60;descendants.whiteboard&#x60; returns whiteboards that are descendants at any level below the content. - &#x60;descendants.database&#x60; returns databases that are descendants at any level below the content. - &#x60;descendants.embed&#x60; returns embeds (smartlinks) that are descendants at any level below the content. - &#x60;descendants.folder&#x60; returns folders that are descendants at any level below the content. - &#x60;descendants.attachment&#x60; returns all attachments for the content, same as &#x60;children.attachment&#x60;. - &#x60;descendants.comment&#x60; returns all comments on the content, same as &#x60;children.comment&#x60;. - &#x60;space&#x60; returns the space that the content is in. This is the same as the information returned by [Get space](#api-space-spaceKey-get).  In addition, the following comment-specific expansions can be used: - &#x60;extensions.inlineProperties&#x60; returns inline comment-specific properties. - &#x60;extensions.resolution&#x60; returns the resolution status of each comment. | [optional]
 **start** | **Integer**| The starting index of the returned content. | [optional] [default to 0] [enum: 0]
 **limit** | **Integer**| The maximum number of content objects to return per page. Note, this may be restricted by fixed system limits. | [optional] [default to 25] [enum: 0]

### Return type

[**InlineResponse2001**](InlineResponse2001.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getSpace"></a>
# **getSpace**
> Space getSpace(spaceKey, expand)

Get space

Deprecated, use [Confluence&#x27;s v2 API](https://developer.atlassian.com/cloud/confluence/rest/v2/intro/).  Returns a space. This includes information like the name, description, and permissions, but not the content in the space.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;View&#x27; permission for the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.SpaceApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

SpaceApi apiInstance = new SpaceApi();
String spaceKey = "spaceKey_example"; // String | The key of the space to be returned.
List<String> expand = Arrays.asList("expand_example"); // List<String> | A multi-value parameter indicating which properties of the spaces to expand, where:    - `settings` returns the settings for the space, similar to [Get space settings](#api-space-spaceKey-settings-get).   - `metadata` returns the space metadata.   - `metadata.labels` returns the space labels, which are used to categorize the space.   - `operations` returns the operations for a space, which are used when setting permissions.   - `lookAndFeel` returns information about the look and feel of the space, including the color scheme.   - `permissions` returns the permissions for the space. Note that this may return permissions for deleted groups,   because deleting a group doesn't remove associated space permissions.   - `icon` returns information about space icon.   - `description` returns the description of the space.   - `description.plain` returns the description of the space, the plain format.   - `description.view` returns the description of the space, the view format.   - `theme` returns information about the space theme.   - `homepage` returns information about the space homepage.   - `history` returns information about the history of the space.
try {
    Space result = apiInstance.getSpace(spaceKey, expand);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SpaceApi#getSpace");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **spaceKey** | **String**| The key of the space to be returned. |
 **expand** | [**List&lt;String&gt;**](String.md)| A multi-value parameter indicating which properties of the spaces to expand, where:    - &#x60;settings&#x60; returns the settings for the space, similar to [Get space settings](#api-space-spaceKey-settings-get).   - &#x60;metadata&#x60; returns the space metadata.   - &#x60;metadata.labels&#x60; returns the space labels, which are used to categorize the space.   - &#x60;operations&#x60; returns the operations for a space, which are used when setting permissions.   - &#x60;lookAndFeel&#x60; returns information about the look and feel of the space, including the color scheme.   - &#x60;permissions&#x60; returns the permissions for the space. Note that this may return permissions for deleted groups,   because deleting a group doesn&#x27;t remove associated space permissions.   - &#x60;icon&#x60; returns information about space icon.   - &#x60;description&#x60; returns the description of the space.   - &#x60;description.plain&#x60; returns the description of the space, the plain format.   - &#x60;description.view&#x60; returns the description of the space, the view format.   - &#x60;theme&#x60; returns information about the space theme.   - &#x60;homepage&#x60; returns information about the space homepage.   - &#x60;history&#x60; returns information about the history of the space. | [optional] [enum: settings, metadata, metadata.labels, operations, lookAndFeel, permissions, icon, description, description.plain, description.view, theme, homepage, history]

### Return type

[**Space**](Space.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getSpaces"></a>
# **getSpaces**
> SpaceArray getSpaces(spaceKey, spaceId, type, status, label, favourite, favouriteUserKey, expand, start, limit)

Get spaces

Deprecated, use [Confluence&#x27;s v2 API](https://developer.atlassian.com/cloud/confluence/rest/v2/intro/).  Returns all spaces. The returned spaces are ordered alphabetically in ascending order by space key.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to access the Confluence site (&#x27;Can use&#x27; global permission). Note, the returned list will only contain spaces that the current user has permission to view.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.SpaceApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

SpaceApi apiInstance = new SpaceApi();
List<String> spaceKey = Arrays.asList("spaceKey_example"); // List<String> | The key of the space to be returned. To return multiple spaces, specify this parameter multiple times with different values.
List<Long> spaceId = Arrays.asList(56L); // List<Long> | The id of the space to be returned. To return multiple spaces, specify this parameter multiple times with different values.
String type = "type_example"; // String | Filter the results to spaces based on their type.
String status = "status_example"; // String | Filter the results to spaces based on their status.
List<String> label = Arrays.asList("label_example"); // List<String> | Filter the results to spaces based on their label.
Boolean favourite = true; // Boolean | Filter the results to the favourite spaces of the user specified by `favouriteUserKey`. Note, 'favourite' spaces are also known as 'saved for later' spaces.
String favouriteUserKey = "favouriteUserKey_example"; // String | The userKey of the user, whose favourite spaces are used to filter the results when using the `favourite` parameter.  Leave blank for the current user. Use [Get user](#api-user-get) to get the userKey for a user.
List<String> expand = Arrays.asList("expand_example"); // List<String> | A multi-value parameter indicating which properties of the spaces to expand, where:    - `settings` returns the settings for the space, similar to [Get space settings](#api-space-spaceKey-settings-get).   - `metadata` returns the space metadata.   - `metadata.labels` returns the space labels, which are used to categorize the space.   - `operations` returns the operations for a space, which are used when setting permissions.   - `lookAndFeel` returns information about the look and feel of the space, including the color scheme.   - `permissions` returns the permissions for the space. Note that this may return permissions for deleted groups,   because deleting a group doesn't remove associated space permissions.   - `icon` returns information about space icon.   - `description` returns the description of the space.   - `description.plain` returns the description of the space, the plain format.   - `description.view` returns the description of the space, the view format.   - `theme` returns information about the space theme.   - `homepage` returns information about the space homepage.   - `history` returns information about the history of the space.
Integer start = 0; // Integer | The starting index of the returned spaces.
Integer limit = 25; // Integer | The maximum number of spaces to return per page. Note, this may be restricted by fixed system limits.
try {
    SpaceArray result = apiInstance.getSpaces(spaceKey, spaceId, type, status, label, favourite, favouriteUserKey, expand, start, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SpaceApi#getSpaces");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **spaceKey** | [**List&lt;String&gt;**](String.md)| The key of the space to be returned. To return multiple spaces, specify this parameter multiple times with different values. | [optional]
 **spaceId** | [**List&lt;Long&gt;**](Long.md)| The id of the space to be returned. To return multiple spaces, specify this parameter multiple times with different values. | [optional]
 **type** | **String**| Filter the results to spaces based on their type. | [optional] [enum: global, collaboration, knowledge_base, personal]
 **status** | **String**| Filter the results to spaces based on their status. | [optional] [enum: current, archived]
 **label** | [**List&lt;String&gt;**](String.md)| Filter the results to spaces based on their label. | [optional]
 **favourite** | **Boolean**| Filter the results to the favourite spaces of the user specified by &#x60;favouriteUserKey&#x60;. Note, &#x27;favourite&#x27; spaces are also known as &#x27;saved for later&#x27; spaces. | [optional]
 **favouriteUserKey** | **String**| The userKey of the user, whose favourite spaces are used to filter the results when using the &#x60;favourite&#x60; parameter.  Leave blank for the current user. Use [Get user](#api-user-get) to get the userKey for a user. | [optional]
 **expand** | [**List&lt;String&gt;**](String.md)| A multi-value parameter indicating which properties of the spaces to expand, where:    - &#x60;settings&#x60; returns the settings for the space, similar to [Get space settings](#api-space-spaceKey-settings-get).   - &#x60;metadata&#x60; returns the space metadata.   - &#x60;metadata.labels&#x60; returns the space labels, which are used to categorize the space.   - &#x60;operations&#x60; returns the operations for a space, which are used when setting permissions.   - &#x60;lookAndFeel&#x60; returns information about the look and feel of the space, including the color scheme.   - &#x60;permissions&#x60; returns the permissions for the space. Note that this may return permissions for deleted groups,   because deleting a group doesn&#x27;t remove associated space permissions.   - &#x60;icon&#x60; returns information about space icon.   - &#x60;description&#x60; returns the description of the space.   - &#x60;description.plain&#x60; returns the description of the space, the plain format.   - &#x60;description.view&#x60; returns the description of the space, the view format.   - &#x60;theme&#x60; returns information about the space theme.   - &#x60;homepage&#x60; returns information about the space homepage.   - &#x60;history&#x60; returns information about the history of the space. | [optional] [enum: settings, metadata, metadata.labels, operations, lookAndFeel, permissions, icon, description, description.plain, description.view, theme, homepage, history]
 **start** | **Integer**| The starting index of the returned spaces. | [optional] [default to 0] [enum: 0]
 **limit** | **Integer**| The maximum number of spaces to return per page. Note, this may be restricted by fixed system limits. | [optional] [default to 25] [enum: 0]

### Return type

[**SpaceArray**](SpaceArray.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateSpace"></a>
# **updateSpace**
> Space updateSpace(body, spaceKey)

Update space

Updates the name, description, or homepage of a space.  -   For security reasons, permissions cannot be updated via the API and must be changed via the user interface instead. -   Currently you cannot set space labels when updating a space.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Admin&#x27; permission for the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.SpaceApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

SpaceApi apiInstance = new SpaceApi();
Map<String, Object> body = new Map(); // Map<String, Object> | The updated space.
String spaceKey = "spaceKey_example"; // String | The key of the space to update.
try {
    Space result = apiInstance.updateSpace(body, spaceKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SpaceApi#updateSpace");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Map&lt;String, Object&gt;**](Map.md)| The updated space. |
 **spaceKey** | **String**| The key of the space to update. |

### Return type

[**Space**](Space.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

