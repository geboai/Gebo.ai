# ContentMacroBodyApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAndAsyncConvertMacroBodyByMacroId**](ContentMacroBodyApi.md#getAndAsyncConvertMacroBodyByMacroId) | **GET** /wiki/rest/api/content/{id}/history/{version}/macro/id/{macroId}/convert/async/{to} | Get macro body by macro ID and convert representation Asynchronously
[**getAndConvertMacroBodyByMacroId**](ContentMacroBodyApi.md#getAndConvertMacroBodyByMacroId) | **GET** /wiki/rest/api/content/{id}/history/{version}/macro/id/{macroId}/convert/{to} | Get macro body by macro ID and convert the representation synchronously
[**getMacroBodyByMacroId**](ContentMacroBodyApi.md#getMacroBodyByMacroId) | **GET** /wiki/rest/api/content/{id}/history/{version}/macro/id/{macroId} | Get macro body by macro ID

<a name="getAndAsyncConvertMacroBodyByMacroId"></a>
# **getAndAsyncConvertMacroBodyByMacroId**
> AsyncId getAndAsyncConvertMacroBodyByMacroId(id, version, macroId, to, expand, allowCache, spaceKeyContext, embeddedContentRender)

Get macro body by macro ID and convert representation Asynchronously

Returns Async Id of the conversion task which will convert the macro into a content body of the desired format. The result will be available for 5 minutes after completion of the conversion.  About the macro ID: When a macro is created in a new version of content, Confluence will generate a random ID for it, unless an ID is specified (by an app). The macro ID will look similar to this: &#x27;884bd9-0cb8-41d5-98be-f80943c14f96&#x27;. The ID is then persisted as new versions of content are created, and is only modified by Confluence if there are conflicting IDs.  Note, to preserve backwards compatibility this resource will also match on the hash of the macro body, even if a macro ID is found. This check will eventually become redundant, as macro IDs are generated for pages and transparently propagate out to all instances.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content that the macro is in.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentMacroBodyApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentMacroBodyApi apiInstance = new ContentMacroBodyApi();
String id = "id_example"; // String | The ID for the content that contains the macro.
Integer version = 56; // Integer | The version of the content that contains the macro. Specifying `0` as the `version` will return the macro body for the latest content version.
String macroId = "macroId_example"; // String | The ID of the macro. For apps, this is passed to the macro by the Connect/Forge framework. Otherwise, find the macro ID by querying the desired content and version, then expanding the body in storage format. For example, '/content/196611/version/7?expand=content.body.storage'.
String to = "to_example"; // String | The content representation to return the macro in. Currently, the following conversions are allowed:  - `export_view` - `styled_view` - `view`
List<String> expand = Arrays.asList("expand_example"); // List<String> | A multi-value parameter indicating which properties of the content to expand and populate. Expands are dependent on the `to` conversion format and may be irrelevant for certain conversions (e.g. `macroRenderedOutput` is redundant when converting to `view` format).   If rendering to `view` format, and the body content being converted includes arbitrary nested content (such as macros); then it is  necessary to include webresource expands in the request. Webresources for content body are the batched JS and CSS dependencies for any nested dynamic content (i.e. macros).  - `embeddedContent` returns metadata for nested content (e.g. page included using page include macro) - `mediaToken` returns JWT token for retrieving attachment data from Media API - `macroRenderedOutput` additionally converts body to view format - `webresource.superbatch.uris.js` returns all common JS dependencies as static URLs - `webresource.superbatch.uris.css` returns all common CSS dependencies as static URLs - `webresource.superbatch.uris.all` returns all common dependencies as static URLs - `webresource.superbatch.tags.all` returns all common JS dependencies as html `<script>` tags - `webresource.superbatch.tags.css` returns all common CSS dependencies as html `<style>` tags - `webresource.superbatch.tags.js` returns all common dependencies as html `<script>` and `<style>` tags - `webresource.uris.js` returns JS dependencies specific to conversion - `webresource.uris.css` returns CSS dependencies specific to conversion - `webresource.uris.all` returns all dependencies specific to conversion      - `webresource.tags.all` returns common JS dependencies as html `<script>` tags - `webresource.tags.css` returns common CSS dependencies as html `<style>` tags - `webresource.tags.js` returns common dependencies as html `<script>` and `<style>` tags
Boolean allowCache = false; // Boolean | If this field is false, the cache will erase its current value and begin a conversion. If this field is true, the cache will not erase its current value, and will set the status of the result in cache to RERUNNING. Once the data is updated, the status will change to COMPLETED.  Large macros that take long to convert, and who want to show intermediate, but potentially stale data, immediately should set this field to true. Cache values are stored per macro per user per content and expansions.
String spaceKeyContext = "spaceKeyContext_example"; // String | The space key used for resolving embedded content (page includes, files, and links) in the content body. For example, if the source content contains the link `<ac:link><ri:page ri:content-title=\"Example page\" /><ac:link>` and the `spaceKeyContext=TEST` parameter is provided, then the link will be converted to a link to the \"Example page\" page in the \"TEST\" space.
String embeddedContentRender = "current"; // String | Mode used for rendering embedded content, like attachments.  - `current` renders the embedded content using the latest version. - `version-at-save` renders the embedded content using the version at the time of save.
try {
    AsyncId result = apiInstance.getAndAsyncConvertMacroBodyByMacroId(id, version, macroId, to, expand, allowCache, spaceKeyContext, embeddedContentRender);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentMacroBodyApi#getAndAsyncConvertMacroBodyByMacroId");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The ID for the content that contains the macro. |
 **version** | **Integer**| The version of the content that contains the macro. Specifying &#x60;0&#x60; as the &#x60;version&#x60; will return the macro body for the latest content version. |
 **macroId** | **String**| The ID of the macro. For apps, this is passed to the macro by the Connect/Forge framework. Otherwise, find the macro ID by querying the desired content and version, then expanding the body in storage format. For example, &#x27;/content/196611/version/7?expand&#x3D;content.body.storage&#x27;. |
 **to** | **String**| The content representation to return the macro in. Currently, the following conversions are allowed:  - &#x60;export_view&#x60; - &#x60;styled_view&#x60; - &#x60;view&#x60; | [enum: export_view, view, styled_view]
 **expand** | [**List&lt;String&gt;**](String.md)| A multi-value parameter indicating which properties of the content to expand and populate. Expands are dependent on the &#x60;to&#x60; conversion format and may be irrelevant for certain conversions (e.g. &#x60;macroRenderedOutput&#x60; is redundant when converting to &#x60;view&#x60; format).   If rendering to &#x60;view&#x60; format, and the body content being converted includes arbitrary nested content (such as macros); then it is  necessary to include webresource expands in the request. Webresources for content body are the batched JS and CSS dependencies for any nested dynamic content (i.e. macros).  - &#x60;embeddedContent&#x60; returns metadata for nested content (e.g. page included using page include macro) - &#x60;mediaToken&#x60; returns JWT token for retrieving attachment data from Media API - &#x60;macroRenderedOutput&#x60; additionally converts body to view format - &#x60;webresource.superbatch.uris.js&#x60; returns all common JS dependencies as static URLs - &#x60;webresource.superbatch.uris.css&#x60; returns all common CSS dependencies as static URLs - &#x60;webresource.superbatch.uris.all&#x60; returns all common dependencies as static URLs - &#x60;webresource.superbatch.tags.all&#x60; returns all common JS dependencies as html &#x60;&lt;script&gt;&#x60; tags - &#x60;webresource.superbatch.tags.css&#x60; returns all common CSS dependencies as html &#x60;&lt;style&gt;&#x60; tags - &#x60;webresource.superbatch.tags.js&#x60; returns all common dependencies as html &#x60;&lt;script&gt;&#x60; and &#x60;&lt;style&gt;&#x60; tags - &#x60;webresource.uris.js&#x60; returns JS dependencies specific to conversion - &#x60;webresource.uris.css&#x60; returns CSS dependencies specific to conversion - &#x60;webresource.uris.all&#x60; returns all dependencies specific to conversion      - &#x60;webresource.tags.all&#x60; returns common JS dependencies as html &#x60;&lt;script&gt;&#x60; tags - &#x60;webresource.tags.css&#x60; returns common CSS dependencies as html &#x60;&lt;style&gt;&#x60; tags - &#x60;webresource.tags.js&#x60; returns common dependencies as html &#x60;&lt;script&gt;&#x60; and &#x60;&lt;style&gt;&#x60; tags | [optional]
 **allowCache** | **Boolean**| If this field is false, the cache will erase its current value and begin a conversion. If this field is true, the cache will not erase its current value, and will set the status of the result in cache to RERUNNING. Once the data is updated, the status will change to COMPLETED.  Large macros that take long to convert, and who want to show intermediate, but potentially stale data, immediately should set this field to true. Cache values are stored per macro per user per content and expansions. | [optional] [default to false]
 **spaceKeyContext** | **String**| The space key used for resolving embedded content (page includes, files, and links) in the content body. For example, if the source content contains the link &#x60;&lt;ac:link&gt;&lt;ri:page ri:content-title&#x3D;\&quot;Example page\&quot; /&gt;&lt;ac:link&gt;&#x60; and the &#x60;spaceKeyContext&#x3D;TEST&#x60; parameter is provided, then the link will be converted to a link to the \&quot;Example page\&quot; page in the \&quot;TEST\&quot; space. | [optional]
 **embeddedContentRender** | **String**| Mode used for rendering embedded content, like attachments.  - &#x60;current&#x60; renders the embedded content using the latest version. - &#x60;version-at-save&#x60; renders the embedded content using the version at the time of save. | [optional] [default to current] [enum: current, version-at-save]

### Return type

[**AsyncId**](AsyncId.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAndConvertMacroBodyByMacroId"></a>
# **getAndConvertMacroBodyByMacroId**
> ContentBody getAndConvertMacroBodyByMacroId(id, version, macroId, to, expand, spaceKeyContext, embeddedContentRender)

Get macro body by macro ID and convert the representation synchronously

Returns the body of a macro in format specified in path, for the given macro ID. This includes information like the name of the macro, the body of the macro, and any macro parameters.  About the macro ID: When a macro is created in a new version of content, Confluence will generate a random ID for it, unless an ID is specified (by an app). The macro ID will look similar to this: &#x27;50884bd9-0cb8-41d5-98be-f80943c14f96&#x27;. The ID is then persisted as new versions of content are created, and is only modified by Confluence if there are conflicting IDs.  Note, to preserve backwards compatibility this resource will also match on the hash of the macro body, even if a macro ID is found. This check will eventually become redundant, as macro IDs are generated for pages and transparently propagate out to all instances.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content that the macro is in.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentMacroBodyApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentMacroBodyApi apiInstance = new ContentMacroBodyApi();
String id = "id_example"; // String | The ID for the content that contains the macro.
Integer version = 56; // Integer | The version of the content that contains the macro. Specifying `0` as the `version` will return the macro body for the latest content version.
String macroId = "macroId_example"; // String | The ID of the macro. This is usually passed by the app that the macro is in. Otherwise, find the macro ID by querying the desired content and version, then expanding the body in storage format. For example, '/content/196611/version/7?expand=content.body.storage'.
String to = "to_example"; // String | The content representation to return the macro in.
List<String> expand = Arrays.asList("expand_example"); // List<String> | A multi-value parameter indicating which properties of the content to expand and populate. Expands are dependent on the `to` conversion format and may be irrelevant for certain conversions (e.g. `macroRenderedOutput` is redundant when converting to `view` format).   If rendering to `view` format, and the body content being converted includes arbitrary nested content (such as macros); then it is  necessary to include webresource expands in the request. Webresources for content body are the batched JS and CSS dependencies for any nested dynamic content (i.e. macros).  - `embeddedContent` returns metadata for nested content (e.g. page included using page include macro) - `mediaToken` returns JWT token for retrieving attachment data from Media API - `macroRenderedOutput` additionally converts body to view format - `webresource.superbatch.uris.js` returns all common JS dependencies as static URLs - `webresource.superbatch.uris.css` returns all common CSS dependencies as static URLs - `webresource.superbatch.uris.all` returns all common dependencies as static URLs - `webresource.superbatch.tags.all` returns all common JS dependencies as html `<script>` tags - `webresource.superbatch.tags.css` returns all common CSS dependencies as html `<style>` tags - `webresource.superbatch.tags.js` returns all common dependencies as html `<script>` and `<style>` tags - `webresource.uris.js` returns JS dependencies specific to conversion - `webresource.uris.css` returns CSS dependencies specific to conversion - `webresource.uris.all` returns all dependencies specific to conversion      - `webresource.tags.all` returns common JS dependencies as html `<script>` tags - `webresource.tags.css` returns common CSS dependencies as html `<style>` tags - `webresource.tags.js` returns common dependencies as html `<script>` and `<style>` tags
String spaceKeyContext = "spaceKeyContext_example"; // String | The space key used for resolving embedded content (page includes, files, and links) in the content body. For example, if the source content contains the link `<ac:link><ri:page ri:content-title=\"Example page\" /><ac:link>` and the `spaceKeyContext=TEST` parameter is provided, then the link will be converted to a link to the \"Example page\" page in the \"TEST\" space.
String embeddedContentRender = "current"; // String | Mode used for rendering embedded content, like attachments.  - `current` renders the embedded content using the latest version. - `version-at-save` renders the embedded content using the version at the time of save.
try {
    ContentBody result = apiInstance.getAndConvertMacroBodyByMacroId(id, version, macroId, to, expand, spaceKeyContext, embeddedContentRender);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentMacroBodyApi#getAndConvertMacroBodyByMacroId");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The ID for the content that contains the macro. |
 **version** | **Integer**| The version of the content that contains the macro. Specifying &#x60;0&#x60; as the &#x60;version&#x60; will return the macro body for the latest content version. |
 **macroId** | **String**| The ID of the macro. This is usually passed by the app that the macro is in. Otherwise, find the macro ID by querying the desired content and version, then expanding the body in storage format. For example, &#x27;/content/196611/version/7?expand&#x3D;content.body.storage&#x27;. |
 **to** | **String**| The content representation to return the macro in. |
 **expand** | [**List&lt;String&gt;**](String.md)| A multi-value parameter indicating which properties of the content to expand and populate. Expands are dependent on the &#x60;to&#x60; conversion format and may be irrelevant for certain conversions (e.g. &#x60;macroRenderedOutput&#x60; is redundant when converting to &#x60;view&#x60; format).   If rendering to &#x60;view&#x60; format, and the body content being converted includes arbitrary nested content (such as macros); then it is  necessary to include webresource expands in the request. Webresources for content body are the batched JS and CSS dependencies for any nested dynamic content (i.e. macros).  - &#x60;embeddedContent&#x60; returns metadata for nested content (e.g. page included using page include macro) - &#x60;mediaToken&#x60; returns JWT token for retrieving attachment data from Media API - &#x60;macroRenderedOutput&#x60; additionally converts body to view format - &#x60;webresource.superbatch.uris.js&#x60; returns all common JS dependencies as static URLs - &#x60;webresource.superbatch.uris.css&#x60; returns all common CSS dependencies as static URLs - &#x60;webresource.superbatch.uris.all&#x60; returns all common dependencies as static URLs - &#x60;webresource.superbatch.tags.all&#x60; returns all common JS dependencies as html &#x60;&lt;script&gt;&#x60; tags - &#x60;webresource.superbatch.tags.css&#x60; returns all common CSS dependencies as html &#x60;&lt;style&gt;&#x60; tags - &#x60;webresource.superbatch.tags.js&#x60; returns all common dependencies as html &#x60;&lt;script&gt;&#x60; and &#x60;&lt;style&gt;&#x60; tags - &#x60;webresource.uris.js&#x60; returns JS dependencies specific to conversion - &#x60;webresource.uris.css&#x60; returns CSS dependencies specific to conversion - &#x60;webresource.uris.all&#x60; returns all dependencies specific to conversion      - &#x60;webresource.tags.all&#x60; returns common JS dependencies as html &#x60;&lt;script&gt;&#x60; tags - &#x60;webresource.tags.css&#x60; returns common CSS dependencies as html &#x60;&lt;style&gt;&#x60; tags - &#x60;webresource.tags.js&#x60; returns common dependencies as html &#x60;&lt;script&gt;&#x60; and &#x60;&lt;style&gt;&#x60; tags | [optional]
 **spaceKeyContext** | **String**| The space key used for resolving embedded content (page includes, files, and links) in the content body. For example, if the source content contains the link &#x60;&lt;ac:link&gt;&lt;ri:page ri:content-title&#x3D;\&quot;Example page\&quot; /&gt;&lt;ac:link&gt;&#x60; and the &#x60;spaceKeyContext&#x3D;TEST&#x60; parameter is provided, then the link will be converted to a link to the \&quot;Example page\&quot; page in the \&quot;TEST\&quot; space. | [optional]
 **embeddedContentRender** | **String**| Mode used for rendering embedded content, like attachments.  - &#x60;current&#x60; renders the embedded content using the latest version. - &#x60;version-at-save&#x60; renders the embedded content using the version at the time of save. | [optional] [default to current] [enum: current, version-at-save]

### Return type

[**ContentBody**](ContentBody.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getMacroBodyByMacroId"></a>
# **getMacroBodyByMacroId**
> MacroInstance getMacroBodyByMacroId(id, version, macroId)

Get macro body by macro ID

Returns the body of a macro in storage format, for the given macro ID. This includes information like the name of the macro, the body of the macro, and any macro parameters. This method is mainly used by Cloud apps.  About the macro ID: When a macro is created in a new version of content, Confluence will generate a random ID for it, unless an ID is specified (by an app). The macro ID will look similar to this: &#x27;50884bd9-0cb8-41d5-98be-f80943c14f96&#x27;. The ID is then persisted as new versions of content are created, and is only modified by Confluence if there are conflicting IDs.  Note, to preserve backwards compatibility this resource will also match on the hash of the macro body, even if a macro ID is found. This check will eventually become redundant, as macro IDs are generated for pages and transparently propagate out to all instances.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content that the macro is in.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentMacroBodyApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentMacroBodyApi apiInstance = new ContentMacroBodyApi();
String id = "id_example"; // String | The ID for the content that contains the macro.
Integer version = 56; // Integer | The version of the content that contains the macro. Specifying `0` as the `version` will return the macro body for the latest content version.
String macroId = "macroId_example"; // String | The ID of the macro. This is usually passed by the app that the macro is in. Otherwise, find the macro ID by querying the desired content and version, then expanding the body in storage format. For example, '/content/196611/version/7?expand=content.body.storage'.
try {
    MacroInstance result = apiInstance.getMacroBodyByMacroId(id, version, macroId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentMacroBodyApi#getMacroBodyByMacroId");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The ID for the content that contains the macro. |
 **version** | **Integer**| The version of the content that contains the macro. Specifying &#x60;0&#x60; as the &#x60;version&#x60; will return the macro body for the latest content version. |
 **macroId** | **String**| The ID of the macro. This is usually passed by the app that the macro is in. Otherwise, find the macro ID by querying the desired content and version, then expanding the body in storage format. For example, &#x27;/content/196611/version/7?expand&#x3D;content.body.storage&#x27;. |

### Return type

[**MacroInstance**](MacroInstance.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

