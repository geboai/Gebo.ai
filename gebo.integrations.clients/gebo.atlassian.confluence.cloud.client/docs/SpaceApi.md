# SpaceApi

All URIs are relative to *https://{your-domain}/wiki/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createSpace**](SpaceApi.md#createSpace) | **POST** /spaces | Create space
[**getSpaceById**](SpaceApi.md#getSpaceById) | **GET** /spaces/{id} | Get space by id
[**getSpaces**](SpaceApi.md#getSpaces) | **GET** /spaces | Get spaces

<a name="createSpace"></a>
# **createSpace**
> InlineResponse2011 createSpace(body)

Create space

Creates a Space as specified in the payload.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to create spaces.

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
Object body = null; // Object | 
try {
    InlineResponse2011 result = apiInstance.createSpace(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SpaceApi#createSpace");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Object**](Object.md)|  |

### Return type

[**InlineResponse2011**](InlineResponse2011.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getSpaceById"></a>
# **getSpaceById**
> InlineResponse2007 getSpaceById(id, descriptionFormat, includeIcon, includeOperations, includeProperties, includePermissions, includeRoleAssignments, includeLabels)

Get space by id

Returns a specific space.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the space.

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
Long id = 789L; // Long | The ID of the space to be returned.
SpaceDescriptionBodyRepresentation descriptionFormat = new SpaceDescriptionBodyRepresentation(); // SpaceDescriptionBodyRepresentation | The content format type to be returned in the `description` field of the response. If available, the representation will be available under a response field of the same name under the `description` field.
Boolean includeIcon = false; // Boolean | If the icon for the space should be fetched or not.
Boolean includeOperations = false; // Boolean | Includes operations associated with this space in the response. The number of results will be limited to 50 and sorted in the default sort order. A `meta` and `_links` property will be present to indicate if more results are available and a link to retrieve the rest of the results.
Boolean includeProperties = false; // Boolean | Includes space properties associated with this space in the response. The number of results will be limited to 50 and sorted in the default sort order. A `meta` and `_links` property will be present to indicate if more results are available and a link to retrieve the rest of the results.
Boolean includePermissions = false; // Boolean | Includes space permissions associated with this space in the response. The number of results will be limited to 50 and sorted in the default sort order. A `meta` and `_links` property will be present to indicate if more results are available and a link to retrieve the rest of the results.
Boolean includeRoleAssignments = false; // Boolean | Includes role assignments associated with this space in the response. This parameter is only accepted for EAP sites. The number of results will be limited to 50 and sorted in the default sort order. A `meta` and `_links` property will be present to indicate if more results are available and a link to retrieve the rest of the results.
Boolean includeLabels = false; // Boolean | Includes labels associated with this space in the response. The number of results will be limited to 50 and sorted in the default sort order. A `meta` and `_links` property will be present to indicate if more results are available and a link to retrieve the rest of the results.
try {
    InlineResponse2007 result = apiInstance.getSpaceById(id, descriptionFormat, includeIcon, includeOperations, includeProperties, includePermissions, includeRoleAssignments, includeLabels);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SpaceApi#getSpaceById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the space to be returned. |
 **descriptionFormat** | [**SpaceDescriptionBodyRepresentation**](.md)| The content format type to be returned in the &#x60;description&#x60; field of the response. If available, the representation will be available under a response field of the same name under the &#x60;description&#x60; field. | [optional]
 **includeIcon** | **Boolean**| If the icon for the space should be fetched or not. | [optional] [default to false]
 **includeOperations** | **Boolean**| Includes operations associated with this space in the response. The number of results will be limited to 50 and sorted in the default sort order. A &#x60;meta&#x60; and &#x60;_links&#x60; property will be present to indicate if more results are available and a link to retrieve the rest of the results. | [optional] [default to false]
 **includeProperties** | **Boolean**| Includes space properties associated with this space in the response. The number of results will be limited to 50 and sorted in the default sort order. A &#x60;meta&#x60; and &#x60;_links&#x60; property will be present to indicate if more results are available and a link to retrieve the rest of the results. | [optional] [default to false]
 **includePermissions** | **Boolean**| Includes space permissions associated with this space in the response. The number of results will be limited to 50 and sorted in the default sort order. A &#x60;meta&#x60; and &#x60;_links&#x60; property will be present to indicate if more results are available and a link to retrieve the rest of the results. | [optional] [default to false]
 **includeRoleAssignments** | **Boolean**| Includes role assignments associated with this space in the response. This parameter is only accepted for EAP sites. The number of results will be limited to 50 and sorted in the default sort order. A &#x60;meta&#x60; and &#x60;_links&#x60; property will be present to indicate if more results are available and a link to retrieve the rest of the results. | [optional] [default to false]
 **includeLabels** | **Boolean**| Includes labels associated with this space in the response. The number of results will be limited to 50 and sorted in the default sort order. A &#x60;meta&#x60; and &#x60;_links&#x60; property will be present to indicate if more results are available and a link to retrieve the rest of the results. | [optional] [default to false]

### Return type

[**InlineResponse2007**](InlineResponse2007.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getSpaces"></a>
# **getSpaces**
> MultiEntityResultSpace getSpaces(ids, keys, type, status, labels, favoritedBy, notFavoritedBy, sort, descriptionFormat, includeIcon, cursor, limit)

Get spaces

Returns all spaces. The results will be sorted by id ascending. The number of results is limited by the &#x60;limit&#x60; parameter and additional results (if available) will be available through the &#x60;next&#x60; URL present in the &#x60;Link&#x60; response header.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to access the Confluence site (&#x27;Can use&#x27; global permission). Only spaces that the user has permission to view will be returned.

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
List<Long> ids = Arrays.asList(56L); // List<Long> | Filter the results to spaces based on their IDs. Multiple IDs can be specified as a comma-separated list.
List<String> keys = Arrays.asList("keys_example"); // List<String> | Filter the results to spaces based on their keys. Multiple keys can be specified as a comma-separated list.
String type = "type_example"; // String | Filter the results to spaces based on their type.
String status = "status_example"; // String | Filter the results to spaces based on their status.
List<String> labels = Arrays.asList("labels_example"); // List<String> | Filter the results to spaces based on their labels. Multiple labels can be specified as a comma-separated list.
String favoritedBy = "favoritedBy_example"; // String | Filter the results to spaces favorited by the user with the specified account ID.
String notFavoritedBy = "notFavoritedBy_example"; // String | Filter the results to spaces NOT favorited by the user with the specified account ID.
SpaceSortOrder sort = new SpaceSortOrder(); // SpaceSortOrder | Used to sort the result by a particular field.
SpaceDescriptionBodyRepresentation descriptionFormat = new SpaceDescriptionBodyRepresentation(); // SpaceDescriptionBodyRepresentation | The content format type to be returned in the `description` field of the response. If available, the representation will be available under a response field of the same name under the `description` field.
Boolean includeIcon = false; // Boolean | If the icon for the space should be fetched or not.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of spaces per result to return. If more results exist, use the `Link` response header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultSpace result = apiInstance.getSpaces(ids, keys, type, status, labels, favoritedBy, notFavoritedBy, sort, descriptionFormat, includeIcon, cursor, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SpaceApi#getSpaces");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ids** | [**List&lt;Long&gt;**](Long.md)| Filter the results to spaces based on their IDs. Multiple IDs can be specified as a comma-separated list. | [optional]
 **keys** | [**List&lt;String&gt;**](String.md)| Filter the results to spaces based on their keys. Multiple keys can be specified as a comma-separated list. | [optional]
 **type** | **String**| Filter the results to spaces based on their type. | [optional] [enum: global, collaboration, knowledge_base, personal]
 **status** | **String**| Filter the results to spaces based on their status. | [optional] [enum: current, archived]
 **labels** | [**List&lt;String&gt;**](String.md)| Filter the results to spaces based on their labels. Multiple labels can be specified as a comma-separated list. | [optional]
 **favoritedBy** | **String**| Filter the results to spaces favorited by the user with the specified account ID. | [optional]
 **notFavoritedBy** | **String**| Filter the results to spaces NOT favorited by the user with the specified account ID. | [optional]
 **sort** | [**SpaceSortOrder**](.md)| Used to sort the result by a particular field. | [optional]
 **descriptionFormat** | [**SpaceDescriptionBodyRepresentation**](.md)| The content format type to be returned in the &#x60;description&#x60; field of the response. If available, the representation will be available under a response field of the same name under the &#x60;description&#x60; field. | [optional]
 **includeIcon** | **Boolean**| If the icon for the space should be fetched or not. | [optional] [default to false]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of spaces per result to return. If more results exist, use the &#x60;Link&#x60; response header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultSpace**](MultiEntityResultSpace.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

