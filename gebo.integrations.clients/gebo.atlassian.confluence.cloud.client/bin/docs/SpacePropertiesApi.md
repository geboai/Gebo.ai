# SpacePropertiesApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createSpaceProperty**](SpacePropertiesApi.md#createSpaceProperty) | **POST** /wiki/rest/api/space/{spaceKey}/property | Create space property
[**createSpacePropertyForKey**](SpacePropertiesApi.md#createSpacePropertyForKey) | **POST** /wiki/rest/api/space/{spaceKey}/property/{key} | Create space property for key
[**deleteSpaceProperty**](SpacePropertiesApi.md#deleteSpaceProperty) | **DELETE** /wiki/rest/api/space/{spaceKey}/property/{key} | Delete space property
[**getSpaceProperties**](SpacePropertiesApi.md#getSpaceProperties) | **GET** /wiki/rest/api/space/{spaceKey}/property | Get space properties
[**getSpaceProperty**](SpacePropertiesApi.md#getSpaceProperty) | **GET** /wiki/rest/api/space/{spaceKey}/property/{key} | Get space property
[**updateSpaceProperty**](SpacePropertiesApi.md#updateSpaceProperty) | **PUT** /wiki/rest/api/space/{spaceKey}/property/{key} | Update space property

<a name="createSpaceProperty"></a>
# **createSpaceProperty**
> SpaceProperty createSpaceProperty(body, spaceKey)

Create space property

Deprecated, use [Confluence&#x27;s v2 API](https://developer.atlassian.com/cloud/confluence/rest/v2/intro/).  Creates a new space property.  **[Permissions required](https://confluence.atlassian.com/x/_AozKw)**: ‘Admin’ permission for the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.SpacePropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

SpacePropertiesApi apiInstance = new SpacePropertiesApi();
SpacePropertyCreate body = new SpacePropertyCreate(); // SpacePropertyCreate | The space property to be created.
String spaceKey = "spaceKey_example"; // String | The key of the space that the property will be created in.
try {
    SpaceProperty result = apiInstance.createSpaceProperty(body, spaceKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SpacePropertiesApi#createSpaceProperty");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SpacePropertyCreate**](SpacePropertyCreate.md)| The space property to be created. |
 **spaceKey** | **String**| The key of the space that the property will be created in. |

### Return type

[**SpaceProperty**](SpaceProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createSpacePropertyForKey"></a>
# **createSpacePropertyForKey**
> SpaceProperty createSpacePropertyForKey(body, spaceKey, key)

Create space property for key

Deprecated, use [Confluence&#x27;s v2 API](https://developer.atlassian.com/cloud/confluence/rest/v2/intro/).  Creates a new space property. This is the same as &#x60;POST /wiki/rest/api/space/{spaceKey}/property&#x60; but the key for the property is passed as a path parameter, rather than in the request body.  **[Permissions required](https://confluence.atlassian.com/x/_AozKw)**: ‘Admin’ permission for the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.SpacePropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

SpacePropertiesApi apiInstance = new SpacePropertiesApi();
SpacePropertyCreateNoKey body = new SpacePropertyCreateNoKey(); // SpacePropertyCreateNoKey | The space property to be created.
String spaceKey = "spaceKey_example"; // String | The key of the space that the property will be created in.
String key = "key_example"; // String | The key of the property to be created.
try {
    SpaceProperty result = apiInstance.createSpacePropertyForKey(body, spaceKey, key);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SpacePropertiesApi#createSpacePropertyForKey");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SpacePropertyCreateNoKey**](SpacePropertyCreateNoKey.md)| The space property to be created. |
 **spaceKey** | **String**| The key of the space that the property will be created in. |
 **key** | **String**| The key of the property to be created. |

### Return type

[**SpaceProperty**](SpaceProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deleteSpaceProperty"></a>
# **deleteSpaceProperty**
> deleteSpaceProperty(spaceKey, key)

Delete space property

Deprecated, use [Confluence&#x27;s v2 API](https://developer.atlassian.com/cloud/confluence/rest/v2/intro/).  Deletes a space property.  **[Permissions required](https://confluence.atlassian.com/x/_AozKw)**: ‘Admin’ permission for the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.SpacePropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

SpacePropertiesApi apiInstance = new SpacePropertiesApi();
String spaceKey = "spaceKey_example"; // String | The key of the space that the property is in.
String key = "key_example"; // String | The key of the property to be deleted.
try {
    apiInstance.deleteSpaceProperty(spaceKey, key);
} catch (ApiException e) {
    System.err.println("Exception when calling SpacePropertiesApi#deleteSpaceProperty");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **spaceKey** | **String**| The key of the space that the property is in. |
 **key** | **String**| The key of the property to be deleted. |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="getSpaceProperties"></a>
# **getSpaceProperties**
> SpacePropertyArray getSpaceProperties(spaceKey, expand, start, limit)

Get space properties

Deprecated, use [Confluence&#x27;s v2 API](https://developer.atlassian.com/cloud/confluence/rest/v2/intro/).  Returns all properties for the given space. Space properties are a key-value storage associated with a space.  **[Permissions required](https://confluence.atlassian.com/x/_AozKw)**: ‘View’ permission for the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.SpacePropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

SpacePropertiesApi apiInstance = new SpacePropertiesApi();
String spaceKey = "spaceKey_example"; // String | The key of the space to be queried for its properties.
List<String> expand = Arrays.asList("expand_example"); // List<String> | A multi-value parameter indicating which properties of the space property to expand. By default, the `version` object is expanded.  - `version` returns information about the version of the content. - `space` returns the space that the properties are in.
Integer start = 0; // Integer | The starting index of the returned objects.
Integer limit = 10; // Integer | The maximum number of properties to return per page. Note, this may be restricted by fixed system limits.
try {
    SpacePropertyArray result = apiInstance.getSpaceProperties(spaceKey, expand, start, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SpacePropertiesApi#getSpaceProperties");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **spaceKey** | **String**| The key of the space to be queried for its properties. |
 **expand** | [**List&lt;String&gt;**](String.md)| A multi-value parameter indicating which properties of the space property to expand. By default, the &#x60;version&#x60; object is expanded.  - &#x60;version&#x60; returns information about the version of the content. - &#x60;space&#x60; returns the space that the properties are in. | [optional] [enum: version, space]
 **start** | **Integer**| The starting index of the returned objects. | [optional] [default to 0] [enum: 0]
 **limit** | **Integer**| The maximum number of properties to return per page. Note, this may be restricted by fixed system limits. | [optional] [default to 10] [enum: 0]

### Return type

[**SpacePropertyArray**](SpacePropertyArray.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getSpaceProperty"></a>
# **getSpaceProperty**
> SpaceProperty getSpaceProperty(spaceKey, key, expand)

Get space property

Deprecated, use [Confluence&#x27;s v2 API](https://developer.atlassian.com/cloud/confluence/rest/v2/intro/).  Returns a space property.  **[Permissions required](https://confluence.atlassian.com/x/_AozKw)**: ‘View’ permission for the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.SpacePropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

SpacePropertiesApi apiInstance = new SpacePropertiesApi();
String spaceKey = "spaceKey_example"; // String | The key of the space that the property is in.
String key = "key_example"; // String | The key of the space property.
List<String> expand = Arrays.asList("expand_example"); // List<String> | A multi-value parameter indicating which properties of the space property to expand. By default, the `version` object is expanded.  - `version` returns information about the version of the content. - `space` returns the space that the properties are in.
try {
    SpaceProperty result = apiInstance.getSpaceProperty(spaceKey, key, expand);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SpacePropertiesApi#getSpaceProperty");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **spaceKey** | **String**| The key of the space that the property is in. |
 **key** | **String**| The key of the space property. |
 **expand** | [**List&lt;String&gt;**](String.md)| A multi-value parameter indicating which properties of the space property to expand. By default, the &#x60;version&#x60; object is expanded.  - &#x60;version&#x60; returns information about the version of the content. - &#x60;space&#x60; returns the space that the properties are in. | [optional] [enum: version, space]

### Return type

[**SpaceProperty**](SpaceProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateSpaceProperty"></a>
# **updateSpaceProperty**
> SpaceProperty updateSpaceProperty(body, spaceKey, key)

Update space property

Deprecated, use [Confluence&#x27;s v2 API](https://developer.atlassian.com/cloud/confluence/rest/v2/intro/).  Updates a space property. Note, you cannot update the key of a space property, only the value.  **[Permissions required](https://confluence.atlassian.com/x/_AozKw)**: ‘Admin’ permission for the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.SpacePropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

SpacePropertiesApi apiInstance = new SpacePropertiesApi();
SpacePropertyUpdate body = new SpacePropertyUpdate(); // SpacePropertyUpdate | The space property being updated.
String spaceKey = "spaceKey_example"; // String | The key of the space that the property is in.
String key = "key_example"; // String | The key of the property to be updated.
try {
    SpaceProperty result = apiInstance.updateSpaceProperty(body, spaceKey, key);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SpacePropertiesApi#updateSpaceProperty");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SpacePropertyUpdate**](SpacePropertyUpdate.md)| The space property being updated. |
 **spaceKey** | **String**| The key of the space that the property is in. |
 **key** | **String**| The key of the property to be updated. |

### Return type

[**SpaceProperty**](SpaceProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

