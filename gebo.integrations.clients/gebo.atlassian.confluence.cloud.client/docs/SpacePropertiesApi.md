# SpacePropertiesApi

All URIs are relative to *https://{your-domain}/wiki/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createSpaceProperty**](SpacePropertiesApi.md#createSpaceProperty) | **POST** /spaces/{space-id}/properties | Create space property in space
[**deleteSpacePropertyById**](SpacePropertiesApi.md#deleteSpacePropertyById) | **DELETE** /spaces/{space-id}/properties/{property-id} | Delete space property by id
[**getSpaceProperties**](SpacePropertiesApi.md#getSpaceProperties) | **GET** /spaces/{space-id}/properties | Get space properties in space
[**getSpacePropertyById**](SpacePropertiesApi.md#getSpacePropertyById) | **GET** /spaces/{space-id}/properties/{property-id} | Get space property by id
[**updateSpacePropertyById**](SpacePropertiesApi.md#updateSpacePropertyById) | **PUT** /spaces/{space-id}/properties/{property-id} | Update space property by id

<a name="createSpaceProperty"></a>
# **createSpaceProperty**
> SpaceProperty createSpaceProperty(body, spaceId)

Create space property in space

Creates a new space property.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to access the Confluence site (&#x27;Can use&#x27; global permission) and &#x27;Admin&#x27; permission for the space.

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
SpacePropertyCreateRequest body = new SpacePropertyCreateRequest(); // SpacePropertyCreateRequest | The space property to be created
Long spaceId = 789L; // Long | The ID of the space for which space properties should be returned.
try {
    SpaceProperty result = apiInstance.createSpaceProperty(body, spaceId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SpacePropertiesApi#createSpaceProperty");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SpacePropertyCreateRequest**](SpacePropertyCreateRequest.md)| The space property to be created |
 **spaceId** | **Long**| The ID of the space for which space properties should be returned. |

### Return type

[**SpaceProperty**](SpaceProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deleteSpacePropertyById"></a>
# **deleteSpacePropertyById**
> deleteSpacePropertyById(spaceId, propertyId)

Delete space property by id

Deletes a space property by its id.   **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to access the Confluence site (&#x27;Can use&#x27; global permission) and &#x27;Admin&#x27; permission for the space.

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
Long spaceId = 789L; // Long | The ID of the space the property belongs to.
Long propertyId = 789L; // Long | The ID of the property to be deleted.
try {
    apiInstance.deleteSpacePropertyById(spaceId, propertyId);
} catch (ApiException e) {
    System.err.println("Exception when calling SpacePropertiesApi#deleteSpacePropertyById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **spaceId** | **Long**| The ID of the space the property belongs to. |
 **propertyId** | **Long**| The ID of the property to be deleted. |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="getSpaceProperties"></a>
# **getSpaceProperties**
> MultiEntityResultSpaceProperty getSpaceProperties(spaceId, key, cursor, limit)

Get space properties in space

Returns all properties for the given space. Space properties are a key-value storage associated with a space. The limit parameter specifies the maximum number of results returned in a single response. Use the &#x60;link&#x60; response header to paginate through additional results.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to access the Confluence site (&#x27;Can use&#x27; global permission) and &#x27;View&#x27; permission for the space.

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
Long spaceId = 789L; // Long | The ID of the space for which space properties should be returned.
String key = "key_example"; // String | The key of the space property to retrieve. This should be used when a user knows the key of their property, but needs to retrieve the id for use in other methods.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of pages per result to return. If more results exist, use the `Link` header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultSpaceProperty result = apiInstance.getSpaceProperties(spaceId, key, cursor, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SpacePropertiesApi#getSpaceProperties");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **spaceId** | **Long**| The ID of the space for which space properties should be returned. |
 **key** | **String**| The key of the space property to retrieve. This should be used when a user knows the key of their property, but needs to retrieve the id for use in other methods. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of pages per result to return. If more results exist, use the &#x60;Link&#x60; header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultSpaceProperty**](MultiEntityResultSpaceProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getSpacePropertyById"></a>
# **getSpacePropertyById**
> SpaceProperty getSpacePropertyById(spaceId, propertyId)

Get space property by id

Retrieve a space property by its id.   **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to access the Confluence site (&#x27;Can use&#x27; global permission) and &#x27;View&#x27; permission for the space.

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
Long spaceId = 789L; // Long | The ID of the space the property belongs to.
Long propertyId = 789L; // Long | The ID of the property to be retrieved.
try {
    SpaceProperty result = apiInstance.getSpacePropertyById(spaceId, propertyId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SpacePropertiesApi#getSpacePropertyById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **spaceId** | **Long**| The ID of the space the property belongs to. |
 **propertyId** | **Long**| The ID of the property to be retrieved. |

### Return type

[**SpaceProperty**](SpaceProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateSpacePropertyById"></a>
# **updateSpacePropertyById**
> SpaceProperty updateSpacePropertyById(body, spaceId, propertyId)

Update space property by id

Update a space property by its id.   **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to access the Confluence site (&#x27;Can use&#x27; global permission) and &#x27;Admin&#x27; permission for the space.

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
SpacePropertyUpdateRequest body = new SpacePropertyUpdateRequest(); // SpacePropertyUpdateRequest | The space property to be updated.
Long spaceId = 789L; // Long | The ID of the space the property belongs to.
Long propertyId = 789L; // Long | The ID of the property to be updated.
try {
    SpaceProperty result = apiInstance.updateSpacePropertyById(body, spaceId, propertyId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SpacePropertiesApi#updateSpacePropertyById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SpacePropertyUpdateRequest**](SpacePropertyUpdateRequest.md)| The space property to be updated. |
 **spaceId** | **Long**| The ID of the space the property belongs to. |
 **propertyId** | **Long**| The ID of the property to be updated. |

### Return type

[**SpaceProperty**](SpaceProperty.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

