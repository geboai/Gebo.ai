# ContentStatesApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**bulkRemoveContentStates**](ContentStatesApi.md#bulkRemoveContentStates) | **POST** /wiki/rest/api/content-states/delete | Bulk remove content states from content
[**getAvailableContentStates**](ContentStatesApi.md#getAvailableContentStates) | **GET** /wiki/rest/api/content/{id}/state/available | Gets available content states for content.
[**getContentState**](ContentStatesApi.md#getContentState) | **GET** /wiki/rest/api/content/{id}/state | Get content state
[**getContentStateSettings**](ContentStatesApi.md#getContentStateSettings) | **GET** /wiki/rest/api/space/{spaceKey}/state/settings | Get content state settings for space
[**getContentsWithState**](ContentStatesApi.md#getContentsWithState) | **GET** /wiki/rest/api/space/{spaceKey}/state/content | Get content in space with given content state
[**getCustomContentStates**](ContentStatesApi.md#getCustomContentStates) | **GET** /wiki/rest/api/content-states | Get Custom Content States
[**getSpaceContentStates**](ContentStatesApi.md#getSpaceContentStates) | **GET** /wiki/rest/api/space/{spaceKey}/state | Get space suggested content states
[**getTaskUpdate**](ContentStatesApi.md#getTaskUpdate) | **GET** /wiki/rest/api/content-states/task/{taskId} | Get update on long running task for setting of content state.
[**removeContentState**](ContentStatesApi.md#removeContentState) | **DELETE** /wiki/rest/api/content/{id}/state | Removes the content state of a content and publishes a new version.
[**setContentState**](ContentStatesApi.md#setContentState) | **PUT** /wiki/rest/api/content/{id}/state | Set the content state of a content and publishes a new version of the content.

<a name="bulkRemoveContentStates"></a>
# **bulkRemoveContentStates**
> AsyncId bulkRemoveContentStates(status, body)

Bulk remove content states from content

Creates a long running task that Removes content state from draft or published versions of pages specified.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required** Content Edit Permission for a content to have its state removed via this endpoint.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentStatesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentStatesApi apiInstance = new ContentStatesApi();
String status = "status_example"; // String | Set status to one of [current,draft].
ContentstatesDeleteBody body = new ContentstatesDeleteBody(); // ContentstatesDeleteBody | 
try {
    AsyncId result = apiInstance.bulkRemoveContentStates(status, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentStatesApi#bulkRemoveContentStates");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **status** | **String**| Set status to one of [current,draft]. | [enum: current, draft]
 **body** | [**ContentstatesDeleteBody**](ContentstatesDeleteBody.md)|  | [optional]

### Return type

[**AsyncId**](AsyncId.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getAvailableContentStates"></a>
# **getAvailableContentStates**
> AvailableContentStates getAvailableContentStates(id)

Gets available content states for content.

Gets content states that are available for the content to be set as. Will return all enabled Space Content States. Will only return most the 3 most recently published custom content states to match UI editor list. To get all custom content states, use the /content-states endpoint.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to edit the content.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentStatesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentStatesApi apiInstance = new ContentStatesApi();
String id = "id_example"; // String | id of content to get available states for
try {
    AvailableContentStates result = apiInstance.getAvailableContentStates(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentStatesApi#getAvailableContentStates");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| id of content to get available states for |

### Return type

[**AvailableContentStates**](AvailableContentStates.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getContentState"></a>
# **getContentState**
> ContentStateResponse getContentState(id, status)

Get content state

Gets the current content state of the draft or current version of content. To specify the draft version, set the parameter status to draft, otherwise archived or current will get the relevant published state. **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the content.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentStatesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentStatesApi apiInstance = new ContentStatesApi();
String id = "id_example"; // String | The id of the content whose content state is of interest.
String status = "current"; // String | Set status to one of [current,draft,archived]. Default value is current.
try {
    ContentStateResponse result = apiInstance.getContentState(id, status);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentStatesApi#getContentState");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The id of the content whose content state is of interest. |
 **status** | **String**| Set status to one of [current,draft,archived]. Default value is current. | [optional] [default to current] [enum: current, draft, archived]

### Return type

[**ContentStateResponse**](ContentStateResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getContentStateSettings"></a>
# **getContentStateSettings**
> ContentStateSettings getContentStateSettings(spaceKey)

Get content state settings for space

Get object describing whether content states are allowed at all, if custom content states or space content states are restricted, and a list of space content states allowed for the space if they are not restricted.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Admin&#x27; permission for the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentStatesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentStatesApi apiInstance = new ContentStatesApi();
String spaceKey = "spaceKey_example"; // String | The key of the space to be queried for its content state settings.
try {
    ContentStateSettings result = apiInstance.getContentStateSettings(spaceKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentStatesApi#getContentStateSettings");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **spaceKey** | **String**| The key of the space to be queried for its content state settings. |

### Return type

[**ContentStateSettings**](ContentStateSettings.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getContentsWithState"></a>
# **getContentsWithState**
> ContentArray getContentsWithState(spaceKey, stateId, expand, limit, start)

Get content in space with given content state

Returns all content that has the provided content state in a space.  Starting on Dec 10, 2024, if the expand query parameter is used with the &#x60;body.export_view&#x60; and/or &#x60;body.styled_view&#x60; properties, then the query limit parameter will be restricted to a maximum value of 25.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;View&#x27; permission for the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentStatesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentStatesApi apiInstance = new ContentStatesApi();
String spaceKey = "spaceKey_example"; // String | The key of the space to be queried for its content state settings.
Integer stateId = 56; // Integer | The id of the content state to filter content by
List<String> expand = Arrays.asList("expand_example"); // List<String> | A multi-value parameter indicating which properties of the content to expand. Options include: space, version, history, children, etc.  Ex: space,version
Integer limit = 25; // Integer | Maximum number of results to return
Integer start = 56; // Integer | Number of result to start returning. (0 indexed)
try {
    ContentArray result = apiInstance.getContentsWithState(spaceKey, stateId, expand, limit, start);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentStatesApi#getContentsWithState");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **spaceKey** | **String**| The key of the space to be queried for its content state settings. |
 **stateId** | **Integer**| The id of the content state to filter content by |
 **expand** | [**List&lt;String&gt;**](String.md)| A multi-value parameter indicating which properties of the content to expand. Options include: space, version, history, children, etc.  Ex: space,version | [optional]
 **limit** | **Integer**| Maximum number of results to return | [optional] [default to 25] [enum: 0, 100]
 **start** | **Integer**| Number of result to start returning. (0 indexed) | [optional] [enum: 0]

### Return type

[**ContentArray**](ContentArray.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getCustomContentStates"></a>
# **getCustomContentStates**
> List&lt;ContentState&gt; getCustomContentStates()

Get Custom Content States

Get custom content states that authenticated user has created.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required** Must have user authentication.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentStatesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentStatesApi apiInstance = new ContentStatesApi();
try {
    List<ContentState> result = apiInstance.getCustomContentStates();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentStatesApi#getCustomContentStates");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;ContentState&gt;**](ContentState.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getSpaceContentStates"></a>
# **getSpaceContentStates**
> List&lt;ContentState&gt; getSpaceContentStates(spaceKey)

Get space suggested content states

Get content states that are suggested in the space.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;View&#x27; permission for the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentStatesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentStatesApi apiInstance = new ContentStatesApi();
String spaceKey = "spaceKey_example"; // String | The key of the space to be queried for its content state settings.
try {
    List<ContentState> result = apiInstance.getSpaceContentStates(spaceKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentStatesApi#getSpaceContentStates");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **spaceKey** | **String**| The key of the space to be queried for its content state settings. |

### Return type

[**List&lt;ContentState&gt;**](ContentState.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getTaskUpdate"></a>
# **getTaskUpdate**
> ContentStateBulkSetTaskUpdate getTaskUpdate(taskId)

Get update on long running task for setting of content state.

Get Status of long running task that was previously created to remove content states from content. User must first create a task by passing in details to the /wiki/rest/api/content-states DELETE endpoint.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required** Must have created long running task

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentStatesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentStatesApi apiInstance = new ContentStatesApi();
String taskId = "taskId_example"; // String | taskId returned by delete requests to /wiki/rest/api/content-states
try {
    ContentStateBulkSetTaskUpdate result = apiInstance.getTaskUpdate(taskId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentStatesApi#getTaskUpdate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **taskId** | **String**| taskId returned by delete requests to /wiki/rest/api/content-states |

### Return type

[**ContentStateBulkSetTaskUpdate**](ContentStateBulkSetTaskUpdate.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="removeContentState"></a>
# **removeContentState**
> ContentStateResponse removeContentState(id, status)

Removes the content state of a content and publishes a new version.

Removes the content state of the content specified and creates a new version (publishes the content without changing the body) of the content with the new status.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to edit the content.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentStatesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentStatesApi apiInstance = new ContentStatesApi();
String id = "id_example"; // String | The Id of the content whose content state is to be set.
String status = "status_example"; // String | status of content state from which to delete state. Can be draft or archived
try {
    ContentStateResponse result = apiInstance.removeContentState(id, status);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentStatesApi#removeContentState");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The Id of the content whose content state is to be set. |
 **status** | **String**| status of content state from which to delete state. Can be draft or archived | [optional] [enum: current, draft]

### Return type

[**ContentStateResponse**](ContentStateResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="setContentState"></a>
# **setContentState**
> ContentStateResponse setContentState(body, id, status)

Set the content state of a content and publishes a new version of the content.

Sets the content state of the content specified and creates a new version (publishes the content without changing the body) of the content with the new state.  You may pass in either an id of a state, or the name and color of a desired new state. If all 3 are passed in, id will be used. If the name and color passed in already exist under the current user&#x27;s existing custom states, the existing state will be reused. If custom states are disabled in the space of the content (which can be determined by getting the content state space settings of the content&#x27;s space) then this set will fail.  You may not remove a content state via this PUT request. You must use the DELETE method. A specified state is required in the body of this request.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to edit the content.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ContentStatesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ContentStatesApi apiInstance = new ContentStatesApi();
ContentStateRestInput body = new ContentStateRestInput(); // ContentStateRestInput | Content state fields for state. Pass in id for an existing state, or new name and color for best matching existing state, or new state if allowed in space.
String id = "id_example"; // String | The Id of the content whose content state is to be set.
String status = "status_example"; // String | Status of content onto which state will be placed. If draft, then draft state will change. If current, state will be placed onto a new version of the content with same body as previous version.
try {
    ContentStateResponse result = apiInstance.setContentState(body, id, status);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentStatesApi#setContentState");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ContentStateRestInput**](ContentStateRestInput.md)| Content state fields for state. Pass in id for an existing state, or new name and color for best matching existing state, or new state if allowed in space. |
 **id** | **String**| The Id of the content whose content state is to be set. |
 **status** | **String**| Status of content onto which state will be placed. If draft, then draft state will change. If current, state will be placed onto a new version of the content with same body as previous version. | [optional] [enum: current, draft]

### Return type

[**ContentStateResponse**](ContentStateResponse.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

