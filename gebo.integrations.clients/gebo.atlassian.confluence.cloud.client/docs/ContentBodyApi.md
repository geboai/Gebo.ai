# ContentBodyApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**asyncConvertContentBodyRequest**](ContentBodyApi.md#asyncConvertContentBodyRequest) | **POST** /wiki/rest/api/contentbody/convert/async/{to} | Asynchronously convert content body
[**asyncConvertContentBodyResponse**](ContentBodyApi.md#asyncConvertContentBodyResponse) | **GET** /wiki/rest/api/contentbody/convert/async/{id} | Get asynchronously converted content body from the id or the current status of the task.
[**bulkAsyncConvertContentBodyRequest**](ContentBodyApi.md#bulkAsyncConvertContentBodyRequest) | **POST** /wiki/rest/api/contentbody/convert/async/bulk/tasks | Create asynchronous content body conversion tasks in bulk
[**bulkAsyncConvertContentBodyResponse**](ContentBodyApi.md#bulkAsyncConvertContentBodyResponse) | **GET** /wiki/rest/api/contentbody/convert/async/bulk/tasks | Get asynchronous content body conversion task result in bulk
[**convertContentBody**](ContentBodyApi.md#convertContentBody) | **POST** /wiki/rest/api/contentbody/convert/{to} | Convert content body

<a name="asyncConvertContentBodyRequest"></a>
# **asyncConvertContentBodyRequest**
> AsyncId asyncConvertContentBodyRequest(body, to, expand, spaceKeyContext, contentIdContext, allowCache, embeddedContentRender)

Asynchronously convert content body

Converts a content body from one format to another format asynchronously. Returns the asyncId for the asynchronous task.  Supported conversions:  - storage: editor, export_view, styled_view, view - editor: storage  No other conversions are supported at the moment. Once a conversion is completed, it will be available for 5 minutes at the result endpoint.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: If request specifies &#x27;contentIdContext&#x27;, &#x27;View&#x27; permission for the space, and permission to view the content.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentBodyApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentBodyApi apiInstance = new ContentBodyApi();
Map<String, Object> body = new Map(); // Map<String, Object> | The content body to convert.
String to = "to_example"; // String | The name of the target format for the content body.
List<String> expand = Arrays.asList("expand_example"); // List<String> | A multi-value parameter indicating which properties of the content to expand and populate. Expands are dependent on the `to` conversion format and may be irrelevant for certain conversions (e.g. `macroRenderedOutput` is redundant when converting to `view` format).   If rendering to `view` format, and the body content being converted includes arbitrary nested content (such as macros); then it is  necessary to include webresource expands in the request. Webresources for content body are the batched JS and CSS dependencies for any nested dynamic content (i.e. macros).  - `embeddedContent` returns metadata for nested content (e.g. page included using page include macro) - `mediaToken` returns JWT token for retrieving attachment data from Media API - `macroRenderedOutput` additionally converts body to view format - `webresource.superbatch.uris.js` returns all common JS dependencies as static URLs - `webresource.superbatch.uris.css` returns all common CSS dependencies as static URLs - `webresource.superbatch.uris.all` returns all common dependencies as static URLs - `webresource.superbatch.tags.all` returns all common JS dependencies as html `<script>` tags - `webresource.superbatch.tags.css` returns all common CSS dependencies as html `<style>` tags - `webresource.superbatch.tags.js` returns all common dependencies as html `<script>` and `<style>` tags - `webresource.uris.js` returns JS dependencies specific to conversion - `webresource.uris.css` returns CSS dependencies specific to conversion - `webresource.uris.all` returns all dependencies specific to conversion      - `webresource.tags.all` returns common JS dependencies as html `<script>` tags - `webresource.tags.css` returns common CSS dependencies as html `<style>` tags - `webresource.tags.js` returns common dependencies as html `<script>` and `<style>` tags
String spaceKeyContext = "spaceKeyContext_example"; // String | The space key used for resolving embedded content (page includes, files, and links) in the content body. For example, if the source content contains the link `<ac:link><ri:page ri:content-title=\"Example page\" /><ac:link>` and the `spaceKeyContext=TEST` parameter is provided, then the link will be converted to a link to the \"Example page\" page in the \"TEST\" space.
String contentIdContext = "contentIdContext_example"; // String | The content ID used to find the space for resolving embedded content (page includes, files, and links) in the content body. For example, if the source content contains the link `<ac:link><ri:page ri:content-title=\"Example page\" /><ac:link>` and the `contentIdContext=123` parameter is provided, then the link will be converted to a link to the \"Example page\" page in the same space that has the content with ID=123. Note, `spaceKeyContext` will be ignored if this parameter is provided.
Boolean allowCache = false; // Boolean | If this field is false, the cache will erase its current value and begin a new conversion. If this field is true, the cache will not erase its current value, and will set the status of the async conversion to RERUNNING. Once the data is updated, the status will change to COMPLETED.  Large macros that take long to convert, and whose data need not immediately up to date (same as previous conversion's result within last 5 minutes) should set this fields to true. Cache values are stored per user per content body and expansions.
String embeddedContentRender = "current"; // String | Mode used for rendering embedded content, like attachments.  - `current` renders the embedded content using the latest version. - `version-at-save` renders the embedded content using the version at the time of save.
try {
    AsyncId result = apiInstance.asyncConvertContentBodyRequest(body, to, expand, spaceKeyContext, contentIdContext, allowCache, embeddedContentRender);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentBodyApi#asyncConvertContentBodyRequest");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Map&lt;String, Object&gt;**](Map.md)| The content body to convert. |
 **to** | **String**| The name of the target format for the content body. | [enum: export_view]
 **expand** | [**List&lt;String&gt;**](String.md)| A multi-value parameter indicating which properties of the content to expand and populate. Expands are dependent on the &#x60;to&#x60; conversion format and may be irrelevant for certain conversions (e.g. &#x60;macroRenderedOutput&#x60; is redundant when converting to &#x60;view&#x60; format).   If rendering to &#x60;view&#x60; format, and the body content being converted includes arbitrary nested content (such as macros); then it is  necessary to include webresource expands in the request. Webresources for content body are the batched JS and CSS dependencies for any nested dynamic content (i.e. macros).  - &#x60;embeddedContent&#x60; returns metadata for nested content (e.g. page included using page include macro) - &#x60;mediaToken&#x60; returns JWT token for retrieving attachment data from Media API - &#x60;macroRenderedOutput&#x60; additionally converts body to view format - &#x60;webresource.superbatch.uris.js&#x60; returns all common JS dependencies as static URLs - &#x60;webresource.superbatch.uris.css&#x60; returns all common CSS dependencies as static URLs - &#x60;webresource.superbatch.uris.all&#x60; returns all common dependencies as static URLs - &#x60;webresource.superbatch.tags.all&#x60; returns all common JS dependencies as html &#x60;&lt;script&gt;&#x60; tags - &#x60;webresource.superbatch.tags.css&#x60; returns all common CSS dependencies as html &#x60;&lt;style&gt;&#x60; tags - &#x60;webresource.superbatch.tags.js&#x60; returns all common dependencies as html &#x60;&lt;script&gt;&#x60; and &#x60;&lt;style&gt;&#x60; tags - &#x60;webresource.uris.js&#x60; returns JS dependencies specific to conversion - &#x60;webresource.uris.css&#x60; returns CSS dependencies specific to conversion - &#x60;webresource.uris.all&#x60; returns all dependencies specific to conversion      - &#x60;webresource.tags.all&#x60; returns common JS dependencies as html &#x60;&lt;script&gt;&#x60; tags - &#x60;webresource.tags.css&#x60; returns common CSS dependencies as html &#x60;&lt;style&gt;&#x60; tags - &#x60;webresource.tags.js&#x60; returns common dependencies as html &#x60;&lt;script&gt;&#x60; and &#x60;&lt;style&gt;&#x60; tags | [optional]
 **spaceKeyContext** | **String**| The space key used for resolving embedded content (page includes, files, and links) in the content body. For example, if the source content contains the link &#x60;&lt;ac:link&gt;&lt;ri:page ri:content-title&#x3D;\&quot;Example page\&quot; /&gt;&lt;ac:link&gt;&#x60; and the &#x60;spaceKeyContext&#x3D;TEST&#x60; parameter is provided, then the link will be converted to a link to the \&quot;Example page\&quot; page in the \&quot;TEST\&quot; space. | [optional]
 **contentIdContext** | **String**| The content ID used to find the space for resolving embedded content (page includes, files, and links) in the content body. For example, if the source content contains the link &#x60;&lt;ac:link&gt;&lt;ri:page ri:content-title&#x3D;\&quot;Example page\&quot; /&gt;&lt;ac:link&gt;&#x60; and the &#x60;contentIdContext&#x3D;123&#x60; parameter is provided, then the link will be converted to a link to the \&quot;Example page\&quot; page in the same space that has the content with ID&#x3D;123. Note, &#x60;spaceKeyContext&#x60; will be ignored if this parameter is provided. | [optional]
 **allowCache** | **Boolean**| If this field is false, the cache will erase its current value and begin a new conversion. If this field is true, the cache will not erase its current value, and will set the status of the async conversion to RERUNNING. Once the data is updated, the status will change to COMPLETED.  Large macros that take long to convert, and whose data need not immediately up to date (same as previous conversion&#x27;s result within last 5 minutes) should set this fields to true. Cache values are stored per user per content body and expansions. | [optional] [default to false]
 **embeddedContentRender** | **String**| Mode used for rendering embedded content, like attachments.  - &#x60;current&#x60; renders the embedded content using the latest version. - &#x60;version-at-save&#x60; renders the embedded content using the version at the time of save. | [optional] [default to current] [enum: current, version-at-save]

### Return type

[**AsyncId**](AsyncId.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="asyncConvertContentBodyResponse"></a>
# **asyncConvertContentBodyResponse**
> AsyncContentBody asyncConvertContentBodyResponse(id)

Get asynchronously converted content body from the id or the current status of the task.

Returns the asynchronous content body for the corresponding id if the task is complete  or returns the status of the task.  After the task is completed, the result can be obtained for 5 minutes, or until an identical conversion request is made again, with allowCache query param set to false.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: If request specifies &#x27;contentIdContext&#x27;, &#x27;View&#x27; permission for the space, and permission to view the content.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentBodyApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentBodyApi apiInstance = new ContentBodyApi();
String id = "id_example"; // String | The asyncId of the macro task to get the converted body.
try {
    AsyncContentBody result = apiInstance.asyncConvertContentBodyResponse(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentBodyApi#asyncConvertContentBodyResponse");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The asyncId of the macro task to get the converted body. |

### Return type

[**AsyncContentBody**](AsyncContentBody.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="bulkAsyncConvertContentBodyRequest"></a>
# **bulkAsyncConvertContentBodyRequest**
> AsyncIdArray bulkAsyncConvertContentBodyRequest(body)

Create asynchronous content body conversion tasks in bulk

Asynchronously converts content bodies from one format to another format in bulk. Use the Content body REST API to get the status of conversion tasks. Note that there is a maximum limit of 10 conversions per request to this endpoint.  Supported conversions:  - storage: editor, export_view, styled_view, view - editor: storage  Once a conversion task is completed, it is available for polling for up to 5 minutes.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;View&#x27; permission for the space, and permission to view the content if the &#x60;spaceKeyContext&#x60; or &#x60;contentIdContext&#x60; are present.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentBodyApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentBodyApi apiInstance = new ContentBodyApi();
List<ContentBodyConversionInput> body = Arrays.asList(new ContentBodyConversionInput()); // List<ContentBodyConversionInput> | An array of parameters to create content body conversion tasks.
try {
    AsyncIdArray result = apiInstance.bulkAsyncConvertContentBodyRequest(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentBodyApi#bulkAsyncConvertContentBodyRequest");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**List&lt;ContentBodyConversionInput&gt;**](ContentBodyConversionInput.md)| An array of parameters to create content body conversion tasks. |

### Return type

[**AsyncIdArray**](AsyncIdArray.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="bulkAsyncConvertContentBodyResponse"></a>
# **bulkAsyncConvertContentBodyResponse**
> AsyncContentBodyArray bulkAsyncConvertContentBodyResponse(ids)

Get asynchronous content body conversion task result in bulk

Returns the content body for the corresponding &#x60;asyncId&#x60; of a completed conversion task. If the task is not completed, the task status is returned instead.  Once a conversion task is completed, the result can be obtained for up to 5 minutes, or until an identical conversion request is made again with the &#x60;allowCache&#x60; parameter set to false.  Note that there is a maximum limit of 50 task results per request to this endpoint.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to access the Confluence site (&#x27;Can use&#x27; global permission).

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentBodyApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentBodyApi apiInstance = new ContentBodyApi();
List<String> ids = Arrays.asList("ids_example"); // List<String> | The asyncIds of the conversion tasks.
try {
    AsyncContentBodyArray result = apiInstance.bulkAsyncConvertContentBodyResponse(ids);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentBodyApi#bulkAsyncConvertContentBodyResponse");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ids** | [**List&lt;String&gt;**](String.md)| The asyncIds of the conversion tasks. |

### Return type

[**AsyncContentBodyArray**](AsyncContentBodyArray.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="convertContentBody"></a>
# **convertContentBody**
> ContentBody convertContentBody(body, to, spaceKeyContext, contentIdContext, embeddedContentRender, expand)

Convert content body

Deprecated, use the [single asynchronous endpoint](https://developer.atlassian.com/cloud/confluence/rest/v1/api-group-content-body/#api-wiki-rest-api-contentbody-convert-async-to-post) or the [bulk asynchronous endpoint](https://developer.atlassian.com/cloud/confluence/rest/v1/api-group-content-body/#api-wiki-rest-api-contentbody-convert-async-bulk-tasks-post) instead.  Converts a content body from one format to another format.  Supported conversions:  - storage: view, export_view, styled_view, editor - editor: storage - view: none - export_view: none - styled_view: none  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: If request specifies &#x27;contentIdContext&#x27;, &#x27;View&#x27; permission for the space, and permission to view the content.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentBodyApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentBodyApi apiInstance = new ContentBodyApi();
Map<String, Object> body = new Map(); // Map<String, Object> | The content body to convert.
String to = "to_example"; // String | The name of the target format for the content body.
String spaceKeyContext = "spaceKeyContext_example"; // String | The space key used for resolving embedded content (page includes, files, and links) in the content body. For example, if the source content contains the link `<ac:link><ri:page ri:content-title=\"Example page\" /><ac:link>` and the `spaceKeyContext=TEST` parameter is provided, then the link will be converted to a link to the \"Example page\" page in the \"TEST\" space.
String contentIdContext = "contentIdContext_example"; // String | The content ID used to find the space for resolving embedded content (page includes, files, and links) in the content body. For example, if the source content contains the link `<ac:link><ri:page ri:content-title=\"Example page\" /><ac:link>` and the `contentIdContext=123` parameter is provided, then the link will be converted to a link to the \"Example page\" page in the same space that has the content with ID=123. Note, `spaceKeyContext` will be ignored if this parameter is provided.
String embeddedContentRender = "current"; // String | Mode used for rendering embedded content, like attachments.  - `current` renders the embedded content using the latest version. - `version-at-save` renders the embedded content using the version at the time of save.
List<String> expand = Arrays.asList("expand_example"); // List<String> | A multi-value parameter indicating which properties of the content to expand and populate. Expands are dependent on the `to` conversion format and may be irrelevant for certain conversions (e.g. `macroRenderedOutput` is redundant when converting to `view` format).   If rendering to `view` format, and the body content being converted includes arbitrary nested content (such as macros); then it is  necessary to include webresource expands in the request. Webresources for content body are the batched JS and CSS dependencies for any nested dynamic content (i.e. macros).  - `embeddedContent` returns metadata for nested content (e.g. page included using page include macro) - `mediaToken` returns JWT token for retrieving attachment data from Media API - `macroRenderedOutput` additionally converts body to view format - `webresource.superbatch.uris.js` returns all common JS dependencies as static URLs - `webresource.superbatch.uris.css` returns all common CSS dependencies as static URLs - `webresource.superbatch.uris.all` returns all common dependencies as static URLs - `webresource.superbatch.tags.all` returns all common JS dependencies as html `<script>` tags - `webresource.superbatch.tags.css` returns all common CSS dependencies as html `<style>` tags - `webresource.superbatch.tags.js` returns all common dependencies as html `<script>` and `<style>` tags - `webresource.uris.js` returns JS dependencies specific to conversion - `webresource.uris.css` returns CSS dependencies specific to conversion - `webresource.uris.all` returns all dependencies specific to conversion      - `webresource.tags.all` returns common JS dependencies as html `<script>` tags - `webresource.tags.css` returns common CSS dependencies as html `<style>` tags - `webresource.tags.js` returns common dependencies as html `<script>` and `<style>` tags
try {
    ContentBody result = apiInstance.convertContentBody(body, to, spaceKeyContext, contentIdContext, embeddedContentRender, expand);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentBodyApi#convertContentBody");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Map&lt;String, Object&gt;**](Map.md)| The content body to convert. |
 **to** | **String**| The name of the target format for the content body. |
 **spaceKeyContext** | **String**| The space key used for resolving embedded content (page includes, files, and links) in the content body. For example, if the source content contains the link &#x60;&lt;ac:link&gt;&lt;ri:page ri:content-title&#x3D;\&quot;Example page\&quot; /&gt;&lt;ac:link&gt;&#x60; and the &#x60;spaceKeyContext&#x3D;TEST&#x60; parameter is provided, then the link will be converted to a link to the \&quot;Example page\&quot; page in the \&quot;TEST\&quot; space. | [optional]
 **contentIdContext** | **String**| The content ID used to find the space for resolving embedded content (page includes, files, and links) in the content body. For example, if the source content contains the link &#x60;&lt;ac:link&gt;&lt;ri:page ri:content-title&#x3D;\&quot;Example page\&quot; /&gt;&lt;ac:link&gt;&#x60; and the &#x60;contentIdContext&#x3D;123&#x60; parameter is provided, then the link will be converted to a link to the \&quot;Example page\&quot; page in the same space that has the content with ID&#x3D;123. Note, &#x60;spaceKeyContext&#x60; will be ignored if this parameter is provided. | [optional]
 **embeddedContentRender** | **String**| Mode used for rendering embedded content, like attachments.  - &#x60;current&#x60; renders the embedded content using the latest version. - &#x60;version-at-save&#x60; renders the embedded content using the version at the time of save. | [optional] [default to current] [enum: current, version-at-save]
 **expand** | [**List&lt;String&gt;**](String.md)| A multi-value parameter indicating which properties of the content to expand and populate. Expands are dependent on the &#x60;to&#x60; conversion format and may be irrelevant for certain conversions (e.g. &#x60;macroRenderedOutput&#x60; is redundant when converting to &#x60;view&#x60; format).   If rendering to &#x60;view&#x60; format, and the body content being converted includes arbitrary nested content (such as macros); then it is  necessary to include webresource expands in the request. Webresources for content body are the batched JS and CSS dependencies for any nested dynamic content (i.e. macros).  - &#x60;embeddedContent&#x60; returns metadata for nested content (e.g. page included using page include macro) - &#x60;mediaToken&#x60; returns JWT token for retrieving attachment data from Media API - &#x60;macroRenderedOutput&#x60; additionally converts body to view format - &#x60;webresource.superbatch.uris.js&#x60; returns all common JS dependencies as static URLs - &#x60;webresource.superbatch.uris.css&#x60; returns all common CSS dependencies as static URLs - &#x60;webresource.superbatch.uris.all&#x60; returns all common dependencies as static URLs - &#x60;webresource.superbatch.tags.all&#x60; returns all common JS dependencies as html &#x60;&lt;script&gt;&#x60; tags - &#x60;webresource.superbatch.tags.css&#x60; returns all common CSS dependencies as html &#x60;&lt;style&gt;&#x60; tags - &#x60;webresource.superbatch.tags.js&#x60; returns all common dependencies as html &#x60;&lt;script&gt;&#x60; and &#x60;&lt;style&gt;&#x60; tags - &#x60;webresource.uris.js&#x60; returns JS dependencies specific to conversion - &#x60;webresource.uris.css&#x60; returns CSS dependencies specific to conversion - &#x60;webresource.uris.all&#x60; returns all dependencies specific to conversion      - &#x60;webresource.tags.all&#x60; returns common JS dependencies as html &#x60;&lt;script&gt;&#x60; tags - &#x60;webresource.tags.css&#x60; returns common CSS dependencies as html &#x60;&lt;style&gt;&#x60; tags - &#x60;webresource.tags.js&#x60; returns common dependencies as html &#x60;&lt;script&gt;&#x60; and &#x60;&lt;style&gt;&#x60; tags | [optional]

### Return type

[**ContentBody**](ContentBody.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

