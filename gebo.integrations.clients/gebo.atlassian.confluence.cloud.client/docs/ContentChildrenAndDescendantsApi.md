# ContentChildrenAndDescendantsApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**copyPage**](ContentChildrenAndDescendantsApi.md#copyPage) | **POST** /wiki/rest/api/content/{id}/copy | Copy single page
[**copyPageHierarchy**](ContentChildrenAndDescendantsApi.md#copyPageHierarchy) | **POST** /wiki/rest/api/content/{id}/pagehierarchy/copy | Copy page hierarchy
[**getContentChildren**](ContentChildrenAndDescendantsApi.md#getContentChildren) | **GET** /wiki/rest/api/content/{id}/child | Get content children
[**getContentChildrenByType**](ContentChildrenAndDescendantsApi.md#getContentChildrenByType) | **GET** /wiki/rest/api/content/{id}/child/{type} | Get content children by type
[**getContentDescendants**](ContentChildrenAndDescendantsApi.md#getContentDescendants) | **GET** /wiki/rest/api/content/{id}/descendant | Get content descendants
[**getDescendantsOfType**](ContentChildrenAndDescendantsApi.md#getDescendantsOfType) | **GET** /wiki/rest/api/content/{id}/descendant/{type} | Get content descendants by type
[**movePage**](ContentChildrenAndDescendantsApi.md#movePage) | **PUT** /wiki/rest/api/content/{pageId}/move/{position}/{targetId} | Move a page to a new location relative to a target page

<a name="copyPage"></a>
# **copyPage**
> Content copyPage(body, id, expand)

Copy single page

Copies a single page and its associated properties, permissions, attachments, and custom contents.  The &#x60;id&#x60; path parameter refers to the content ID of the page to copy. The target of the page to be copied  is defined using the &#x60;destination&#x60; in the request body and can be one of the following types.    - &#x60;space&#x60;: page will be copied to the specified space as a root page on the space   - &#x60;parent_page&#x60;: page will be copied as a child of the specified parent page   - &#x60;existing_page&#x60;: page will be copied and replace the specified page  By default, the following objects are expanded: &#x60;space&#x60;, &#x60;history&#x60;, &#x60;version&#x60;.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Add&#x27; permission for the space that the content will be copied in and permission to update the content if copying to an &#x60;existing_page&#x60;.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentChildrenAndDescendantsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentChildrenAndDescendantsApi apiInstance = new ContentChildrenAndDescendantsApi();
CopyPageRequest body = new CopyPageRequest(); // CopyPageRequest | Request object from json post body
String id = "id_example"; // String | 
List<String> expand = Arrays.asList("expand_example"); // List<String> | A multi-value parameter indicating which properties of the content to expand. Maximum sub-expansions allowed is `8`.  - `childTypes.all` returns whether the content has attachments, comments, or child pages/whiteboards. Use this if you only need to check whether the content has children of a particular type. - `childTypes.attachment` returns whether the content has attachments. - `childTypes.comment` returns whether the content has comments. - `childTypes.page` returns whether the content has child pages. - `childTypes.whiteboard` returns whether the content has child whiteboards. - `childTypes.database` returns whether the content has child databases. - `childTypes.embed` returns whether the content has child embeds (smartlinks). - `childTypes.folder` returns whether the content has child folder. - `container` returns the space that the content is in. This is the same as the information returned by [Get space](#api-space-spaceKey-get). - `metadata.currentuser` returns information about the current user in relation to the content, including when they last viewed it, modified it, contributed to it, or added it as a favorite. - `metadata.properties` returns content properties that have been set via the Confluence REST API. - `metadata.labels` returns the labels that have been added to the content. - `metadata.frontend` this property is only used by Atlassian. - `operations` returns the operations for the content, which are used when setting permissions. - `children.page` returns pages that are descendants at the level immediately below the content. - `children.whiteboard` returns whiteboards that are descendants at the level immediately below the content. - `children.database` returns databases that are descendants at the level immediately below the content. - `children.embed` returns embeds (smartlinks) that are descendants at the level immediately below the content. - `children.folder` returns folders that are descendants at the level immediately below the content. - `children.attachment` returns all attachments for the content. - `children.comment` returns all comments on the content. - `restrictions.read.restrictions.user` returns the users that have permission to read the content. - `restrictions.read.restrictions.group` returns the groups that have permission to read the content. Note that this may return deleted groups, because deleting a group doesn't remove associated restrictions. - `restrictions.update.restrictions.user` returns the users that have permission to update the content. - `restrictions.update.restrictions.group` returns the groups that have permission to update the content. Note that this may return deleted groups because deleting a group doesn't remove associated restrictions. - `history` returns the history of the content, including the date it was created. - `history.lastUpdated` returns information about the most recent update of the content, including who updated it and when it was updated. - `history.previousVersion` returns information about the update prior to the current content update. - `history.contributors` returns all of the users who have contributed to the content. - `history.nextVersion` returns information about the update after to the current content update. - `ancestors` returns the parent content, if the content is a page or whiteboard. - `body` returns the body of the content in different formats, including the editor format, view format, and export format. - `body.storage` returns the body of content in storage format. - `body.view` returns the body of content in view format. - `version` returns information about the most recent update of the content, including who updated it and when it was updated. - `descendants.page` returns pages that are descendants at any level below the content. - `descendants.whiteboard` returns whiteboards that are descendants at any level below the content. - `descendants.database` returns databases that are descendants at any level below the content. - `descendants.embed` returns embeds (smartlinks) that are descendants at any level below the content. - `descendants.folder` returns folders that are descendants at any level below the content. - `descendants.attachment` returns all attachments for the content, same as `children.attachment`. - `descendants.comment` returns all comments on the content, same as `children.comment`. - `space` returns the space that the content is in. This is the same as the information returned by [Get space](#api-space-spaceKey-get).  In addition, the following comment-specific expansions can be used: - `extensions.inlineProperties` returns inline comment-specific properties. - `extensions.resolution` returns the resolution status of each comment.
try {
    Content result = apiInstance.copyPage(body, id, expand);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentChildrenAndDescendantsApi#copyPage");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**CopyPageRequest**](CopyPageRequest.md)| Request object from json post body |
 **id** | **String**|  |
 **expand** | [**List&lt;String&gt;**](String.md)| A multi-value parameter indicating which properties of the content to expand. Maximum sub-expansions allowed is &#x60;8&#x60;.  - &#x60;childTypes.all&#x60; returns whether the content has attachments, comments, or child pages/whiteboards. Use this if you only need to check whether the content has children of a particular type. - &#x60;childTypes.attachment&#x60; returns whether the content has attachments. - &#x60;childTypes.comment&#x60; returns whether the content has comments. - &#x60;childTypes.page&#x60; returns whether the content has child pages. - &#x60;childTypes.whiteboard&#x60; returns whether the content has child whiteboards. - &#x60;childTypes.database&#x60; returns whether the content has child databases. - &#x60;childTypes.embed&#x60; returns whether the content has child embeds (smartlinks). - &#x60;childTypes.folder&#x60; returns whether the content has child folder. - &#x60;container&#x60; returns the space that the content is in. This is the same as the information returned by [Get space](#api-space-spaceKey-get). - &#x60;metadata.currentuser&#x60; returns information about the current user in relation to the content, including when they last viewed it, modified it, contributed to it, or added it as a favorite. - &#x60;metadata.properties&#x60; returns content properties that have been set via the Confluence REST API. - &#x60;metadata.labels&#x60; returns the labels that have been added to the content. - &#x60;metadata.frontend&#x60; this property is only used by Atlassian. - &#x60;operations&#x60; returns the operations for the content, which are used when setting permissions. - &#x60;children.page&#x60; returns pages that are descendants at the level immediately below the content. - &#x60;children.whiteboard&#x60; returns whiteboards that are descendants at the level immediately below the content. - &#x60;children.database&#x60; returns databases that are descendants at the level immediately below the content. - &#x60;children.embed&#x60; returns embeds (smartlinks) that are descendants at the level immediately below the content. - &#x60;children.folder&#x60; returns folders that are descendants at the level immediately below the content. - &#x60;children.attachment&#x60; returns all attachments for the content. - &#x60;children.comment&#x60; returns all comments on the content. - &#x60;restrictions.read.restrictions.user&#x60; returns the users that have permission to read the content. - &#x60;restrictions.read.restrictions.group&#x60; returns the groups that have permission to read the content. Note that this may return deleted groups, because deleting a group doesn&#x27;t remove associated restrictions. - &#x60;restrictions.update.restrictions.user&#x60; returns the users that have permission to update the content. - &#x60;restrictions.update.restrictions.group&#x60; returns the groups that have permission to update the content. Note that this may return deleted groups because deleting a group doesn&#x27;t remove associated restrictions. - &#x60;history&#x60; returns the history of the content, including the date it was created. - &#x60;history.lastUpdated&#x60; returns information about the most recent update of the content, including who updated it and when it was updated. - &#x60;history.previousVersion&#x60; returns information about the update prior to the current content update. - &#x60;history.contributors&#x60; returns all of the users who have contributed to the content. - &#x60;history.nextVersion&#x60; returns information about the update after to the current content update. - &#x60;ancestors&#x60; returns the parent content, if the content is a page or whiteboard. - &#x60;body&#x60; returns the body of the content in different formats, including the editor format, view format, and export format. - &#x60;body.storage&#x60; returns the body of content in storage format. - &#x60;body.view&#x60; returns the body of content in view format. - &#x60;version&#x60; returns information about the most recent update of the content, including who updated it and when it was updated. - &#x60;descendants.page&#x60; returns pages that are descendants at any level below the content. - &#x60;descendants.whiteboard&#x60; returns whiteboards that are descendants at any level below the content. - &#x60;descendants.database&#x60; returns databases that are descendants at any level below the content. - &#x60;descendants.embed&#x60; returns embeds (smartlinks) that are descendants at any level below the content. - &#x60;descendants.folder&#x60; returns folders that are descendants at any level below the content. - &#x60;descendants.attachment&#x60; returns all attachments for the content, same as &#x60;children.attachment&#x60;. - &#x60;descendants.comment&#x60; returns all comments on the content, same as &#x60;children.comment&#x60;. - &#x60;space&#x60; returns the space that the content is in. This is the same as the information returned by [Get space](#api-space-spaceKey-get).  In addition, the following comment-specific expansions can be used: - &#x60;extensions.inlineProperties&#x60; returns inline comment-specific properties. - &#x60;extensions.resolution&#x60; returns the resolution status of each comment. | [optional]

### Return type

[**Content**](Content.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json;charset=UTF-8

<a name="copyPageHierarchy"></a>
# **copyPageHierarchy**
> LongTask copyPageHierarchy(body, id)

Copy page hierarchy

Copy page hierarchy allows the copying of an entire hierarchy of pages and their associated properties, permissions and attachments.  The id path parameter refers to the content id of the page to copy, and the new parent of this copied page is defined using the destinationPageId in the request body.  The titleOptions object defines the rules of renaming page titles during the copy;  for example, search and replace can be used in conjunction to rewrite the copied page titles.   Response example:  &lt;pre&gt;&lt;code&gt;  {       \&quot;id\&quot; : \&quot;1180606\&quot;,       \&quot;links\&quot; : {            \&quot;status\&quot; : \&quot;/rest/api/longtask/1180606\&quot;       }  }  &lt;/code&gt;&lt;/pre&gt;  Use the /longtask/&lt;taskId&gt; REST API to get the copy task status.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentChildrenAndDescendantsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentChildrenAndDescendantsApi apiInstance = new ContentChildrenAndDescendantsApi();
CopyPageHierarchyRequest body = new CopyPageHierarchyRequest(); // CopyPageHierarchyRequest | Request object from json post body
String id = "id_example"; // String | 
try {
    LongTask result = apiInstance.copyPageHierarchy(body, id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentChildrenAndDescendantsApi#copyPageHierarchy");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**CopyPageHierarchyRequest**](CopyPageHierarchyRequest.md)| Request object from json post body |
 **id** | **String**|  |

### Return type

[**LongTask**](LongTask.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getContentChildren"></a>
# **getContentChildren**
> ContentChildren getContentChildren(id, expand, parentVersion)

Get content children

Deprecated, use [Confluence&#x27;s v2 API](https://developer.atlassian.com/cloud/confluence/rest/v2/intro/).  Returns a map of the direct children of a piece of content. A piece of content has different types of child content, depending on its type. These are the default parent-child content type relationships:  - &#x60;page&#x60;: child content is &#x60;page&#x60;, &#x60;whiteboard&#x60;, &#x60;database&#x60;, &#x60;embed&#x60;, &#x60;folder&#x60;, &#x60;comment&#x60;, &#x60;attachment&#x60; - &#x60;whiteboard&#x60;: child content is &#x60;page&#x60;, &#x60;whiteboard&#x60;, &#x60;database&#x60;, &#x60;embed&#x60;, &#x60;folder&#x60;, &#x60;comment&#x60;, &#x60;attachment&#x60; - &#x60;database&#x60;: child content is &#x60;page&#x60;, &#x60;whiteboard&#x60;, &#x60;database&#x60;, &#x60;embed&#x60;, &#x60;folder&#x60;, &#x60;comment&#x60;, &#x60;attachment&#x60; - &#x60;embed&#x60;: child content is &#x60;page&#x60;, &#x60;whiteboard&#x60;, &#x60;database&#x60;, &#x60;embed&#x60;, &#x60;folder&#x60;, &#x60;comment&#x60;, &#x60;attachment&#x60; - &#x60;folder&#x60;: child content is &#x60;page&#x60;, &#x60;whiteboard&#x60;, &#x60;database&#x60;, &#x60;embed&#x60;, &#x60;folder&#x60;, &#x60;comment&#x60;, &#x60;attachment&#x60; - &#x60;blogpost&#x60;: child content is &#x60;comment&#x60;, &#x60;attachment&#x60; - &#x60;attachment&#x60;: child content is &#x60;comment&#x60; - &#x60;comment&#x60;: child content is &#x60;attachment&#x60;  Apps can override these default relationships. Apps can also introduce new content types that create new parent-child content relationships.  Note, the map will always include all child content types that are valid for the content. However, if the content has no instances of a child content type, the map will contain an empty array for that child content type.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;View&#x27; permission for the space, and permission to view the content if it is a page.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentChildrenAndDescendantsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentChildrenAndDescendantsApi apiInstance = new ContentChildrenAndDescendantsApi();
String id = "id_example"; // String | The ID of the content to be queried for its children.
List<String> expand = Arrays.asList("expand_example"); // List<String> | A multi-value parameter indicating which properties of the children to expand, where:  - `attachment` returns all attachments for the content. - `comments` returns all comments for the content. - `page` returns all child pages of the content. - `whiteboard` returns all child whiteboards of the content. - `database` returns all child databases of the content. - `embed` returns all child embeds of the content. - `folder` returns all child folders of the content. - Custom content types that are provided by apps are also supported.
Integer parentVersion = 0; // Integer | The version of the parent content to retrieve children for. Currently, this only works for the latest version.
try {
    ContentChildren result = apiInstance.getContentChildren(id, expand, parentVersion);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentChildrenAndDescendantsApi#getContentChildren");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The ID of the content to be queried for its children. |
 **expand** | [**List&lt;String&gt;**](String.md)| A multi-value parameter indicating which properties of the children to expand, where:  - &#x60;attachment&#x60; returns all attachments for the content. - &#x60;comments&#x60; returns all comments for the content. - &#x60;page&#x60; returns all child pages of the content. - &#x60;whiteboard&#x60; returns all child whiteboards of the content. - &#x60;database&#x60; returns all child databases of the content. - &#x60;embed&#x60; returns all child embeds of the content. - &#x60;folder&#x60; returns all child folders of the content. - Custom content types that are provided by apps are also supported. | [optional]
 **parentVersion** | **Integer**| The version of the parent content to retrieve children for. Currently, this only works for the latest version. | [optional] [default to 0]

### Return type

[**ContentChildren**](ContentChildren.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getContentChildrenByType"></a>
# **getContentChildrenByType**
> ContentArray getContentChildrenByType(id, type, expand, parentVersion, start, limit)

Get content children by type

Deprecated, use [Confluence&#x27;s v2 API](https://developer.atlassian.com/cloud/confluence/rest/v2/intro/).  Returns all children of a given type, for a piece of content. A piece of content has different types of child content, depending on its type:  - &#x60;page&#x60;: child content is &#x60;page&#x60;, &#x60;whiteboard&#x60;, &#x60;database&#x60;, &#x60;embed&#x60;, &#x60;folder&#x60;, &#x60;comment&#x60;, &#x60;attachment&#x60; - &#x60;whiteboard&#x60;: child content is &#x60;page&#x60;, &#x60;whiteboard&#x60;, &#x60;database&#x60;, &#x60;embed&#x60;, &#x60;folder&#x60;, &#x60;comment&#x60;, &#x60;attachment&#x60; - &#x60;database&#x60;: child content is &#x60;page&#x60;, &#x60;whiteboard&#x60;, &#x60;database&#x60;, &#x60;embed&#x60;, &#x60;folder&#x60;, &#x60;comment&#x60;, &#x60;attachment&#x60; - &#x60;embed&#x60;: child content is &#x60;page&#x60;, &#x60;whiteboard&#x60;, &#x60;database&#x60;, &#x60;embed&#x60;, &#x60;folder&#x60;, &#x60;comment&#x60;, &#x60;attachment&#x60; - &#x60;folder&#x60;: child content is &#x60;page&#x60;, &#x60;whiteboard&#x60;, &#x60;database&#x60;, &#x60;embed&#x60;, &#x60;folder&#x60;, &#x60;comment&#x60;, &#x60;attachment&#x60; - &#x60;blogpost&#x60;: child content is &#x60;comment&#x60;, &#x60;attachment&#x60; - &#x60;attachment&#x60;: child content is &#x60;comment&#x60; - &#x60;comment&#x60;: child content is &#x60;attachment&#x60;  Custom content types that are provided by apps can also be returned.  Note, this method only returns direct children. To return children at all levels, use [Get descendants by type](#api-content-id-descendant-type-get).  Starting on Dec 10, 2024, if the expand query parameter is used with the &#x60;body.export_view&#x60; and/or &#x60;body.styled_view&#x60; properties, then the query limit parameter will be restricted to a maximum value of 25.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;View&#x27; permission for the space, and permission to view the content if it is a page.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentChildrenAndDescendantsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentChildrenAndDescendantsApi apiInstance = new ContentChildrenAndDescendantsApi();
String id = "id_example"; // String | The ID of the content to be queried for its children.
String type = "type_example"; // String | The type of children to return.
List<String> expand = Arrays.asList("expand_example"); // List<String> | A multi-value parameter indicating which properties of the content to expand.  - `childTypes.all` returns whether the content has attachments, comments, or child pages/whiteboards. Use this if you only need to check whether the content has children of a particular type. - `childTypes.attachment` returns whether the content has attachments. - `childTypes.comment` returns whether the content has comments. - `childTypes.page` returns whether the content has child pages. - `childTypes.whiteboard` returns whether the content has child whiteboards. - `childTypes.database` returns whether the content has child databases. - `childTypes.embed` returns whether the content has child embeds (smartlinks). - `childTypes.folder` returns whether the content has child folders. - `container` returns the space that the content is in. This is the same as the information returned by [Get space](#api-space-spaceKey-get). - `metadata.currentuser` returns information about the current user in relation to the content, including when they last viewed it, modified it, contributed to it, or added it as a favorite. - `metadata.properties` returns content properties that have been set via the Confluence REST API. - `metadata.labels` returns the labels that have been added to the content. - `metadata.frontend` this property is only used by Atlassian. - `operations` returns the operations for the content, which are used when setting permissions. - `children.page` returns pages that are descendants at the level immediately below the content. - `children.whiteboard` returns whiteboards that are descendants at the level immediately below the content. - `children.database` returns databases that are descendants at the level immediately below the content. - `children.embed` returns embeds (smartlinks) that are descendants at the level immediately below the content. - `children.folder` returns folders that are descendants at the level immediately below the content. - `children.attachment` returns all attachments for the content. - `children.comment` returns all comments on the content. - `restrictions.read.restrictions.user` returns the users that have permission to read the content. - `restrictions.read.restrictions.group` returns the groups that have permission to read the content. Note that this may return deleted groups, because deleting a group doesn't remove associated restrictions. - `restrictions.update.restrictions.user` returns the users that have permission to update the content. - `restrictions.update.restrictions.group` returns the groups that have permission to update the content. Note that this may return deleted groups because deleting a group doesn't remove associated restrictions. - `history` returns the history of the content, including the date it was created. - `history.lastUpdated` returns information about the most recent update of the content, including who updated it and when it was updated. - `history.previousVersion` returns information about the update prior to the current content update. - `history.contributors` returns all of the users who have contributed to the content. - `history.nextVersion` returns information about the update after to the current content update. - `ancestors` returns the parent content, if the content is a page or whiteboard. - `body` returns the body of the content in different formats, including the editor format, view format, and export format. - `body.storage` returns the body of content in storage format. - `body.view` returns the body of content in view format. - `version` returns information about the most recent update of the content, including who updated it and when it was updated. - `descendants.page` returns pages that are descendants at any level below the content. - `descendants.whiteboard` returns whiteboards that are descendants at any level below the content. - `descendants.database` returns databases that are descendants at any level below the content. - `descendants.embed` returns embeds (smartlinks) that are descendants at any level below the content. - `descendants.folder` returns folders that are descendants at any level below the content. - `descendants.attachment` returns all attachments for the content, same as `children.attachment`. - `descendants.comment` returns all comments on the content, same as `children.comment`. - `space` returns the space that the content is in. This is the same as the information returned by [Get space](#api-space-spaceKey-get).  In addition, the following comment-specific expansions can be used: - `extensions.inlineProperties` returns inline comment-specific properties. - `extensions.resolution` returns the resolution status of each comment.
Integer parentVersion = 0; // Integer | The version of the parent content to retrieve children for. Currently, this only works for the latest version.
Integer start = 56; // Integer | The starting index of the returned content.
Integer limit = 25; // Integer | The maximum number of content to return per page. Note, this may be restricted by fixed system limits.
try {
    ContentArray result = apiInstance.getContentChildrenByType(id, type, expand, parentVersion, start, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentChildrenAndDescendantsApi#getContentChildrenByType");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The ID of the content to be queried for its children. |
 **type** | **String**| The type of children to return. |
 **expand** | [**List&lt;String&gt;**](String.md)| A multi-value parameter indicating which properties of the content to expand.  - &#x60;childTypes.all&#x60; returns whether the content has attachments, comments, or child pages/whiteboards. Use this if you only need to check whether the content has children of a particular type. - &#x60;childTypes.attachment&#x60; returns whether the content has attachments. - &#x60;childTypes.comment&#x60; returns whether the content has comments. - &#x60;childTypes.page&#x60; returns whether the content has child pages. - &#x60;childTypes.whiteboard&#x60; returns whether the content has child whiteboards. - &#x60;childTypes.database&#x60; returns whether the content has child databases. - &#x60;childTypes.embed&#x60; returns whether the content has child embeds (smartlinks). - &#x60;childTypes.folder&#x60; returns whether the content has child folders. - &#x60;container&#x60; returns the space that the content is in. This is the same as the information returned by [Get space](#api-space-spaceKey-get). - &#x60;metadata.currentuser&#x60; returns information about the current user in relation to the content, including when they last viewed it, modified it, contributed to it, or added it as a favorite. - &#x60;metadata.properties&#x60; returns content properties that have been set via the Confluence REST API. - &#x60;metadata.labels&#x60; returns the labels that have been added to the content. - &#x60;metadata.frontend&#x60; this property is only used by Atlassian. - &#x60;operations&#x60; returns the operations for the content, which are used when setting permissions. - &#x60;children.page&#x60; returns pages that are descendants at the level immediately below the content. - &#x60;children.whiteboard&#x60; returns whiteboards that are descendants at the level immediately below the content. - &#x60;children.database&#x60; returns databases that are descendants at the level immediately below the content. - &#x60;children.embed&#x60; returns embeds (smartlinks) that are descendants at the level immediately below the content. - &#x60;children.folder&#x60; returns folders that are descendants at the level immediately below the content. - &#x60;children.attachment&#x60; returns all attachments for the content. - &#x60;children.comment&#x60; returns all comments on the content. - &#x60;restrictions.read.restrictions.user&#x60; returns the users that have permission to read the content. - &#x60;restrictions.read.restrictions.group&#x60; returns the groups that have permission to read the content. Note that this may return deleted groups, because deleting a group doesn&#x27;t remove associated restrictions. - &#x60;restrictions.update.restrictions.user&#x60; returns the users that have permission to update the content. - &#x60;restrictions.update.restrictions.group&#x60; returns the groups that have permission to update the content. Note that this may return deleted groups because deleting a group doesn&#x27;t remove associated restrictions. - &#x60;history&#x60; returns the history of the content, including the date it was created. - &#x60;history.lastUpdated&#x60; returns information about the most recent update of the content, including who updated it and when it was updated. - &#x60;history.previousVersion&#x60; returns information about the update prior to the current content update. - &#x60;history.contributors&#x60; returns all of the users who have contributed to the content. - &#x60;history.nextVersion&#x60; returns information about the update after to the current content update. - &#x60;ancestors&#x60; returns the parent content, if the content is a page or whiteboard. - &#x60;body&#x60; returns the body of the content in different formats, including the editor format, view format, and export format. - &#x60;body.storage&#x60; returns the body of content in storage format. - &#x60;body.view&#x60; returns the body of content in view format. - &#x60;version&#x60; returns information about the most recent update of the content, including who updated it and when it was updated. - &#x60;descendants.page&#x60; returns pages that are descendants at any level below the content. - &#x60;descendants.whiteboard&#x60; returns whiteboards that are descendants at any level below the content. - &#x60;descendants.database&#x60; returns databases that are descendants at any level below the content. - &#x60;descendants.embed&#x60; returns embeds (smartlinks) that are descendants at any level below the content. - &#x60;descendants.folder&#x60; returns folders that are descendants at any level below the content. - &#x60;descendants.attachment&#x60; returns all attachments for the content, same as &#x60;children.attachment&#x60;. - &#x60;descendants.comment&#x60; returns all comments on the content, same as &#x60;children.comment&#x60;. - &#x60;space&#x60; returns the space that the content is in. This is the same as the information returned by [Get space](#api-space-spaceKey-get).  In addition, the following comment-specific expansions can be used: - &#x60;extensions.inlineProperties&#x60; returns inline comment-specific properties. - &#x60;extensions.resolution&#x60; returns the resolution status of each comment. | [optional]
 **parentVersion** | **Integer**| The version of the parent content to retrieve children for. Currently, this only works for the latest version. | [optional] [default to 0] [enum: 0]
 **start** | **Integer**| The starting index of the returned content. | [optional]
 **limit** | **Integer**| The maximum number of content to return per page. Note, this may be restricted by fixed system limits. | [optional] [default to 25] [enum: 0]

### Return type

[**ContentArray**](ContentArray.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getContentDescendants"></a>
# **getContentDescendants**
> ContentChildren getContentDescendants(id, expand)

Get content descendants

Returns a map of the descendants of a piece of content. This is similar to [Get content children](#api-content-id-child-get), except that this method returns child pages at all levels, rather than just the direct child pages.  A piece of content has different types of descendants, depending on its type:  - &#x60;page&#x60;: descendant is &#x60;page&#x60;, &#x60;whiteboard&#x60;, &#x60;database&#x60;, &#x60;embed&#x60;, &#x60;folder&#x60;, &#x60;comment&#x60;, &#x60;attachment&#x60; - &#x60;whiteboard&#x60;: descendant is &#x60;page&#x60;, &#x60;whiteboard&#x60;, &#x60;database&#x60;, &#x60;embed&#x60;, &#x60;folder&#x60;, &#x60;comment&#x60;, &#x60;attachment&#x60; - &#x60;database&#x60;: descendant is &#x60;page&#x60;, &#x60;whiteboard&#x60;, &#x60;database&#x60;, &#x60;embed&#x60;, &#x60;folder&#x60;, &#x60;comment&#x60;, &#x60;attachment&#x60; - &#x60;embed&#x60;: descendant is &#x60;page&#x60;, &#x60;whiteboard&#x60;, &#x60;database&#x60;, &#x60;embed&#x60;, &#x60;folder&#x60;, &#x60;comment&#x60;, &#x60;attachment&#x60; - &#x60;folder&#x60;: descendant is &#x60;page&#x60;, &#x60;whiteboard&#x60;, &#x60;database&#x60;, &#x60;embed&#x60;, &#x60;folder&#x60;, &#x60;comment&#x60;, &#x60;attachment&#x60; - &#x60;blogpost&#x60;: descendant is &#x60;comment&#x60;, &#x60;attachment&#x60; - &#x60;attachment&#x60;: descendant is &#x60;comment&#x60; - &#x60;comment&#x60;: descendant is &#x60;attachment&#x60;  The map will always include all descendant types that are valid for the content. However, if the content has no instances of a descendant type, the map will contain an empty array for that descendant type.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;View&#x27; permission for the space, and permission to view the content if it is a page.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentChildrenAndDescendantsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentChildrenAndDescendantsApi apiInstance = new ContentChildrenAndDescendantsApi();
String id = "id_example"; // String | The ID of the content to be queried for its descendants.
List<String> expand = Arrays.asList("expand_example"); // List<String> | A multi-value parameter indicating which properties of the children to expand, where:  - `attachment` returns all attachments for the content. - `comments` returns all comments for the content. - `page` returns all child pages of the content. - `whiteboard` returns all child whiteboards of the content. - `database` returns all child databases of the content. - `embed` returns all child embeds of the content. - `folder` returns all child folders of the content.
try {
    ContentChildren result = apiInstance.getContentDescendants(id, expand);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentChildrenAndDescendantsApi#getContentDescendants");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The ID of the content to be queried for its descendants. |
 **expand** | [**List&lt;String&gt;**](String.md)| A multi-value parameter indicating which properties of the children to expand, where:  - &#x60;attachment&#x60; returns all attachments for the content. - &#x60;comments&#x60; returns all comments for the content. - &#x60;page&#x60; returns all child pages of the content. - &#x60;whiteboard&#x60; returns all child whiteboards of the content. - &#x60;database&#x60; returns all child databases of the content. - &#x60;embed&#x60; returns all child embeds of the content. - &#x60;folder&#x60; returns all child folders of the content. | [optional] [enum: attachment, comment, page]

### Return type

[**ContentChildren**](ContentChildren.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getDescendantsOfType"></a>
# **getDescendantsOfType**
> ContentArray getDescendantsOfType(id, type, depth, expand, start, limit)

Get content descendants by type

Returns all descendants of a given type, for a piece of content. This is similar to [Get content children by type](#api-content-id-child-type-get), except that this method returns child pages at all levels, rather than just the direct child pages.  A piece of content has different types of descendants, depending on its type:  - &#x60;page&#x60;: descendant is &#x60;page&#x60;, &#x60;whiteboard&#x60;, &#x60;database&#x60;, &#x60;embed&#x60;, &#x60;folder&#x60;, &#x60;comment&#x60;, &#x60;attachment&#x60; - &#x60;whiteboard&#x60;: descendant is &#x60;page&#x60;, &#x60;whiteboard&#x60;, &#x60;database&#x60;, &#x60;embed&#x60;, &#x60;folder&#x60;, &#x60;comment&#x60;, &#x60;attachment&#x60; - &#x60;database&#x60;: descendant is &#x60;page&#x60;, &#x60;whiteboard&#x60;, &#x60;database&#x60;, &#x60;embed&#x60;, &#x60;folder&#x60;, &#x60;comment&#x60;, &#x60;attachment&#x60; - &#x60;embed&#x60;: descendant is &#x60;page&#x60;, &#x60;whiteboard&#x60;, &#x60;database&#x60;, &#x60;embed&#x60;, &#x60;folder&#x60;, &#x60;comment&#x60;, &#x60;attachment&#x60; - &#x60;folder&#x60;: descendant is &#x60;page&#x60;, &#x60;whiteboard&#x60;, &#x60;database&#x60;, &#x60;embed&#x60;, &#x60;folder&#x60;, &#x60;comment&#x60;, &#x60;attachment&#x60; - &#x60;blogpost&#x60;: descendant is &#x60;comment&#x60;, &#x60;attachment&#x60; - &#x60;attachment&#x60;: descendant is &#x60;comment&#x60; - &#x60;comment&#x60;: descendant is &#x60;attachment&#x60;  Custom content types that are provided by apps can also be returned.  Starting on Dec 10, 2024, if the expand query parameter is used with the &#x60;body.export_view&#x60; and/or &#x60;body.styled_view&#x60; properties, then the query limit parameter will be restricted to a maximum value of 25.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;View&#x27; permission for the space, and permission to view the content if it is a page.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentChildrenAndDescendantsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentChildrenAndDescendantsApi apiInstance = new ContentChildrenAndDescendantsApi();
String id = "id_example"; // String | The ID of the content to be queried for its descendants.
String type = "type_example"; // String | The type of descendants to return.
String depth = "all"; // String | Filter the results to descendants upto a desired level of the content. Note, the maximum value supported is 100. root level of the content means immediate (level 1) descendants of the type requested. all represents returning all descendants of the type requested.
List<String> expand = Arrays.asList("expand_example"); // List<String> | A multi-value parameter indicating which properties of the content to expand.  - `childTypes.all` returns whether the content has attachments, comments, or child pages/whiteboards. Use this if you only need to check whether the content has children of a particular type. - `childTypes.attachment` returns whether the content has attachments. - `childTypes.comment` returns whether the content has comments. - `childTypes.page` returns whether the content has child pages. - `childTypes.whiteboard` returns whether the content has child whiteboards. - `childTypes.database` returns whether the content has child databases. - `childTypes.embed` returns whether the content has child embeds (smartlinks). - `childTypes.folder` returns whether the content has child folders. - `container` returns the space that the content is in. This is the same as the information returned by [Get space](#api-space-spaceKey-get). - `metadata.currentuser` returns information about the current user in relation to the content, including when they last viewed it, modified it, contributed to it, or added it as a favorite. - `metadata.properties` returns content properties that have been set via the Confluence REST API. - `metadata.labels` returns the labels that have been added to the content. - `metadata.frontend` this property is only used by Atlassian. - `operations` returns the operations for the content, which are used when setting permissions. - `children.page` returns pages that are descendants at the level immediately below the content. - `children.whiteboard` returns whiteboards that are descendants at the level immediately below the content. - `children.database` returns databases that are descendants at the level immediately below the content. - `children.embed` returns embeds (smartlinks) that are descendants at the level immediately below the content. - `children.folder` returns folders that are descendants at the level immediately below the content. - `children.attachment` returns all attachments for the content. - `children.comment` returns all comments on the content. - `restrictions.read.restrictions.user` returns the users that have permission to read the content. - `restrictions.read.restrictions.group` returns the groups that have permission to read the content. Note that this may return deleted groups, because deleting a group doesn't remove associated restrictions. - `restrictions.update.restrictions.user` returns the users that have permission to update the content. - `restrictions.update.restrictions.group` returns the groups that have permission to update the content. Note that this may return deleted groups because deleting a group doesn't remove associated restrictions. - `history` returns the history of the content, including the date it was created. - `history.lastUpdated` returns information about the most recent update of the content, including who updated it and when it was updated. - `history.previousVersion` returns information about the update prior to the current content update. - `history.contributors` returns all of the users who have contributed to the content. - `history.nextVersion` returns information about the update after to the current content update. - `ancestors` returns the parent content, if the content is a page or whiteboard. - `body` returns the body of the content in different formats, including the editor format, view format, and export format. - `body.storage` returns the body of content in storage format. - `body.view` returns the body of content in view format. - `version` returns information about the most recent update of the content, including who updated it and when it was updated. - `descendants.page` returns pages that are descendants at any level below the content. - `descendants.whiteboard` returns whiteboards that are descendants at any level below the content. - `descendants.database` returns databases that are descendants at any level below the content. - `descendants.embed` returns embeds (smartlinks) that are descendants at any level below the content. - `descendants.folder` returns folders that are descendants at any level below the content. - `descendants.attachment` returns all attachments for the content, same as `children.attachment`. - `descendants.comment` returns all comments on the content, same as `children.comment`. - `space` returns the space that the content is in. This is the same as the information returned by [Get space](#api-space-spaceKey-get).  In addition, the following comment-specific expansions can be used: - `extensions.inlineProperties` returns inline comment-specific properties. - `extensions.resolution` returns the resolution status of each comment.
Integer start = 0; // Integer | The starting index of the returned content.
Integer limit = 25; // Integer | The maximum number of content to return per page. Note, this may be restricted by fixed system limits.
try {
    ContentArray result = apiInstance.getDescendantsOfType(id, type, depth, expand, start, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentChildrenAndDescendantsApi#getDescendantsOfType");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The ID of the content to be queried for its descendants. |
 **type** | **String**| The type of descendants to return. | [enum: page, comment, attachment]
 **depth** | **String**| Filter the results to descendants upto a desired level of the content. Note, the maximum value supported is 100. root level of the content means immediate (level 1) descendants of the type requested. all represents returning all descendants of the type requested. | [optional] [default to all] [enum: all, root, <any positive integer argument in the range of 1 and 100>]
 **expand** | [**List&lt;String&gt;**](String.md)| A multi-value parameter indicating which properties of the content to expand.  - &#x60;childTypes.all&#x60; returns whether the content has attachments, comments, or child pages/whiteboards. Use this if you only need to check whether the content has children of a particular type. - &#x60;childTypes.attachment&#x60; returns whether the content has attachments. - &#x60;childTypes.comment&#x60; returns whether the content has comments. - &#x60;childTypes.page&#x60; returns whether the content has child pages. - &#x60;childTypes.whiteboard&#x60; returns whether the content has child whiteboards. - &#x60;childTypes.database&#x60; returns whether the content has child databases. - &#x60;childTypes.embed&#x60; returns whether the content has child embeds (smartlinks). - &#x60;childTypes.folder&#x60; returns whether the content has child folders. - &#x60;container&#x60; returns the space that the content is in. This is the same as the information returned by [Get space](#api-space-spaceKey-get). - &#x60;metadata.currentuser&#x60; returns information about the current user in relation to the content, including when they last viewed it, modified it, contributed to it, or added it as a favorite. - &#x60;metadata.properties&#x60; returns content properties that have been set via the Confluence REST API. - &#x60;metadata.labels&#x60; returns the labels that have been added to the content. - &#x60;metadata.frontend&#x60; this property is only used by Atlassian. - &#x60;operations&#x60; returns the operations for the content, which are used when setting permissions. - &#x60;children.page&#x60; returns pages that are descendants at the level immediately below the content. - &#x60;children.whiteboard&#x60; returns whiteboards that are descendants at the level immediately below the content. - &#x60;children.database&#x60; returns databases that are descendants at the level immediately below the content. - &#x60;children.embed&#x60; returns embeds (smartlinks) that are descendants at the level immediately below the content. - &#x60;children.folder&#x60; returns folders that are descendants at the level immediately below the content. - &#x60;children.attachment&#x60; returns all attachments for the content. - &#x60;children.comment&#x60; returns all comments on the content. - &#x60;restrictions.read.restrictions.user&#x60; returns the users that have permission to read the content. - &#x60;restrictions.read.restrictions.group&#x60; returns the groups that have permission to read the content. Note that this may return deleted groups, because deleting a group doesn&#x27;t remove associated restrictions. - &#x60;restrictions.update.restrictions.user&#x60; returns the users that have permission to update the content. - &#x60;restrictions.update.restrictions.group&#x60; returns the groups that have permission to update the content. Note that this may return deleted groups because deleting a group doesn&#x27;t remove associated restrictions. - &#x60;history&#x60; returns the history of the content, including the date it was created. - &#x60;history.lastUpdated&#x60; returns information about the most recent update of the content, including who updated it and when it was updated. - &#x60;history.previousVersion&#x60; returns information about the update prior to the current content update. - &#x60;history.contributors&#x60; returns all of the users who have contributed to the content. - &#x60;history.nextVersion&#x60; returns information about the update after to the current content update. - &#x60;ancestors&#x60; returns the parent content, if the content is a page or whiteboard. - &#x60;body&#x60; returns the body of the content in different formats, including the editor format, view format, and export format. - &#x60;body.storage&#x60; returns the body of content in storage format. - &#x60;body.view&#x60; returns the body of content in view format. - &#x60;version&#x60; returns information about the most recent update of the content, including who updated it and when it was updated. - &#x60;descendants.page&#x60; returns pages that are descendants at any level below the content. - &#x60;descendants.whiteboard&#x60; returns whiteboards that are descendants at any level below the content. - &#x60;descendants.database&#x60; returns databases that are descendants at any level below the content. - &#x60;descendants.embed&#x60; returns embeds (smartlinks) that are descendants at any level below the content. - &#x60;descendants.folder&#x60; returns folders that are descendants at any level below the content. - &#x60;descendants.attachment&#x60; returns all attachments for the content, same as &#x60;children.attachment&#x60;. - &#x60;descendants.comment&#x60; returns all comments on the content, same as &#x60;children.comment&#x60;. - &#x60;space&#x60; returns the space that the content is in. This is the same as the information returned by [Get space](#api-space-spaceKey-get).  In addition, the following comment-specific expansions can be used: - &#x60;extensions.inlineProperties&#x60; returns inline comment-specific properties. - &#x60;extensions.resolution&#x60; returns the resolution status of each comment. | [optional]
 **start** | **Integer**| The starting index of the returned content. | [optional] [default to 0] [enum: 0]
 **limit** | **Integer**| The maximum number of content to return per page. Note, this may be restricted by fixed system limits. | [optional] [default to 25] [enum: 0]

### Return type

[**ContentArray**](ContentArray.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="movePage"></a>
# **movePage**
> InlineResponse200 movePage(pageId, position, targetId)

Move a page to a new location relative to a target page

Move a page to a new location relative to a target page:  * &#x60;before&#x60; - move the page under the same parent as the target, before the target in the list of children * &#x60;after&#x60; - move the page under the same parent as the target, after the target in the list of children * &#x60;append&#x60; - move the page to be a child of the target  Caution: This API can move pages to the top level of a space. Top-level pages are difficult to find in the UI because they do not show up in the page tree display. To avoid this, never use &#x60;before&#x60; or &#x60;after&#x60; positions when the &#x60;targetId&#x60; is a top-level page.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentChildrenAndDescendantsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentChildrenAndDescendantsApi apiInstance = new ContentChildrenAndDescendantsApi();
String pageId = "pageId_example"; // String | The ID of the page to be moved
String position = "position_example"; // String | The position to move the page to relative to the target page: * `before` - move the page under the same parent as the target, before the target in the list of children * `after` - move the page under the same parent as the target, after the target in the list of children * `append` - move the page to be a child of the target
String targetId = "targetId_example"; // String | The ID of the target page for this operation
try {
    InlineResponse200 result = apiInstance.movePage(pageId, position, targetId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentChildrenAndDescendantsApi#movePage");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **pageId** | **String**| The ID of the page to be moved |
 **position** | **String**| The position to move the page to relative to the target page: * &#x60;before&#x60; - move the page under the same parent as the target, before the target in the list of children * &#x60;after&#x60; - move the page under the same parent as the target, after the target in the list of children * &#x60;append&#x60; - move the page to be a child of the target | [enum: before, after, append]
 **targetId** | **String**| The ID of the target page for this operation |

### Return type

[**InlineResponse200**](InlineResponse200.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

