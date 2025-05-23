# ContentPropertiesApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createContentProperty**](ContentPropertiesApi.md#createContentProperty) | **POST** /wiki/rest/api/content/{id}/property | Create content property
[**createContentPropertyForKey**](ContentPropertiesApi.md#createContentPropertyForKey) | **POST** /wiki/rest/api/content/{id}/property/{key} | Create content property for key
[**deleteContentProperty**](ContentPropertiesApi.md#deleteContentProperty) | **DELETE** /wiki/rest/api/content/{id}/property/{key} | Delete content property
[**getContentProperties**](ContentPropertiesApi.md#getContentProperties) | **GET** /wiki/rest/api/content/{id}/property | Get content properties
[**getContentProperty**](ContentPropertiesApi.md#getContentProperty) | **GET** /wiki/rest/api/content/{id}/property/{key} | Get content property
[**updateContentProperty**](ContentPropertiesApi.md#updateContentProperty) | **PUT** /wiki/rest/api/content/{id}/property/{key} | Update content property

<a name="createContentProperty"></a>
# **createContentProperty**
> ContentProperty createContentProperty(body, id)

Create content property

Creates a property for an existing piece of content. For more information about content properties, see [Confluence entity properties](https://developer.atlassian.com/cloud/confluence/confluence-entity-properties/).  This is the same as [Create content property for key](#api-content-id-property-key-post) except that the key is specified in the request body instead of as a path parameter.  Content properties can also be added when creating a new piece of content by including them in the &#x60;metadata.properties&#x60; of the request.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to update the content.

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
Map<String, Object> body = new Map(); // Map<String, Object> | The content property to be created.
String id = "id_example"; // String | The ID of the content to add the property to.
try {
    ContentProperty result = apiInstance.createContentProperty(body, id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#createContentProperty");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Map&lt;String, Object&gt;**](Map.md)| The content property to be created. |
 **id** | **String**| The ID of the content to add the property to. |

### Return type

[**ContentProperty**](ContentProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createContentPropertyForKey"></a>
# **createContentPropertyForKey**
> ContentProperty createContentPropertyForKey(body, id, key)

Create content property for key

Creates a property for an existing piece of content. For more information about content properties, see [Confluence entity properties](https://developer.atlassian.com/cloud/confluence/confluence-entity-properties/).  This is the same as [Create content property](#api-content-id-property-post) except that the key is specified as a path parameter instead of in the request body.  Content properties can also be added when creating a new piece of content by including them in the &#x60;metadata.properties&#x60; of the request.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to update the content.

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
Map<String, Object> body = new Map(); // Map<String, Object> | The content property to be created.
String id = "id_example"; // String | The ID of the content to add the property to.
String key = "key_example"; // String | The key of the content property. Required.
try {
    ContentProperty result = apiInstance.createContentPropertyForKey(body, id, key);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#createContentPropertyForKey");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Map&lt;String, Object&gt;**](Map.md)| The content property to be created. |
 **id** | **String**| The ID of the content to add the property to. |
 **key** | **String**| The key of the content property. Required. |

### Return type

[**ContentProperty**](ContentProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deleteContentProperty"></a>
# **deleteContentProperty**
> deleteContentProperty(id, key)

Delete content property

Deletes a content property. For more information about content properties, see [Confluence entity properties](https://developer.atlassian.com/cloud/confluence/confluence-entity-properties/).  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to update the content.

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
String id = "id_example"; // String | The ID of the content that the property belongs to.
String key = "key_example"; // String | The key of the property.
try {
    apiInstance.deleteContentProperty(id, key);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#deleteContentProperty");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The ID of the content that the property belongs to. |
 **key** | **String**| The key of the property. |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="getContentProperties"></a>
# **getContentProperties**
> ContentPropertyArray getContentProperties(id, key, expand, start, limit)

Get content properties

Returns the properties for a piece of content. For more information about content properties, see [Confluence entity properties](https://developer.atlassian.com/cloud/confluence/confluence-entity-properties/).  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;View&#x27; permission for the space, and permission to view the content if it is a page.

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
String id = "id_example"; // String | The ID of the content to be queried for its properties.
List<String> key = Arrays.asList("key_example"); // List<String> | The key of the content property.
List<String> expand = Arrays.asList("expand_example"); // List<String> | A multi-value parameter indicating which properties of the content to expand. By default, the `version` object is expanded.  - `content` returns the content that the property is stored against. - `version` returns information about the version of the property, such as the version number, when it was created, etc.
Integer start = 0; // Integer | The starting index of the returned properties.
Integer limit = 10; // Integer | The maximum number of properties to return per page. Note, this may be restricted by fixed system limits.
try {
    ContentPropertyArray result = apiInstance.getContentProperties(id, key, expand, start, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#getContentProperties");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The ID of the content to be queried for its properties. |
 **key** | [**List&lt;String&gt;**](String.md)| The key of the content property. | [optional]
 **expand** | [**List&lt;String&gt;**](String.md)| A multi-value parameter indicating which properties of the content to expand. By default, the &#x60;version&#x60; object is expanded.  - &#x60;content&#x60; returns the content that the property is stored against. - &#x60;version&#x60; returns information about the version of the property, such as the version number, when it was created, etc. | [optional] [enum: content, version]
 **start** | **Integer**| The starting index of the returned properties. | [optional] [default to 0] [enum: 0]
 **limit** | **Integer**| The maximum number of properties to return per page. Note, this may be restricted by fixed system limits. | [optional] [default to 10] [enum: 0]

### Return type

[**ContentPropertyArray**](ContentPropertyArray.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getContentProperty"></a>
# **getContentProperty**
> ContentProperty getContentProperty(id, key, expand, status)

Get content property

Returns a content property for a piece of content. For more information, see [Confluence entity properties](https://developer.atlassian.com/cloud/confluence/confluence-entity-properties/).  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;View&#x27; permission for the space, and permission to view the content if it is a page.

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
String id = "id_example"; // String | The ID of the content to be queried for the property.
String key = "key_example"; // String | The key of the content property.
List<String> expand = Arrays.asList("expand_example"); // List<String> | A multi-value parameter indicating which properties of the content to expand. By default, the `version` object is expanded.  - `content` returns the content that the property is stored against. - `version` returns information about the version of the property, such as the version number, when it was created, etc.
List<String> status = Arrays.asList("[\"current\",\"archived\"]"); // List<String> | Filter the results to a set of content based on their status. If set to `any`, content with any status is returned. By default it will fetch current and archived statuses `?status=current&status=archived`. All supported statuses  - any - archived - current - deleted - draft - trashed
try {
    ContentProperty result = apiInstance.getContentProperty(id, key, expand, status);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#getContentProperty");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The ID of the content to be queried for the property. |
 **key** | **String**| The key of the content property. |
 **expand** | [**List&lt;String&gt;**](String.md)| A multi-value parameter indicating which properties of the content to expand. By default, the &#x60;version&#x60; object is expanded.  - &#x60;content&#x60; returns the content that the property is stored against. - &#x60;version&#x60; returns information about the version of the property, such as the version number, when it was created, etc. | [optional] [enum: content, version]
 **status** | [**List&lt;String&gt;**](String.md)| Filter the results to a set of content based on their status. If set to &#x60;any&#x60;, content with any status is returned. By default it will fetch current and archived statuses &#x60;?status&#x3D;current&amp;status&#x3D;archived&#x60;. All supported statuses  - any - archived - current - deleted - draft - trashed | [optional] [default to [&quot;current&quot;,&quot;archived&quot;]]

### Return type

[**ContentProperty**](ContentProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateContentProperty"></a>
# **updateContentProperty**
> ContentProperty updateContentProperty(body, id, key)

Update content property

Updates an existing content property. This method will also create a new property for a piece of content, if the property key does not exist and the property version is 1. For more information about content properties, see [Confluence entity properties](https://developer.atlassian.com/cloud/confluence/confluence-entity-properties/).  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to update the content.

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
Map<String, Object> body = new Map(); // Map<String, Object> | The content property being updated.
String id = "id_example"; // String | The ID of the content that the property belongs to.
String key = "key_example"; // String | The key of the property.
try {
    ContentProperty result = apiInstance.updateContentProperty(body, id, key);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentPropertiesApi#updateContentProperty");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Map&lt;String, Object&gt;**](Map.md)| The content property being updated. |
 **id** | **String**| The ID of the content that the property belongs to. |
 **key** | **String**| The key of the property. |

### Return type

[**ContentProperty**](ContentProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

