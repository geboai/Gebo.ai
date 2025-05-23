# AncestorsApi

All URIs are relative to *https://{your-domain}/wiki/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getDatabaseAncestors**](AncestorsApi.md#getDatabaseAncestors) | **GET** /databases/{id}/ancestors | Get all ancestors of database
[**getFolderAncestors**](AncestorsApi.md#getFolderAncestors) | **GET** /folders/{id}/ancestors | Get all ancestors of folder
[**getPageAncestors**](AncestorsApi.md#getPageAncestors) | **GET** /pages/{id}/ancestors | Get all ancestors of page
[**getSmartLinkAncestors**](AncestorsApi.md#getSmartLinkAncestors) | **GET** /embeds/{id}/ancestors | Get all ancestors of Smart Link in content tree
[**getWhiteboardAncestors**](AncestorsApi.md#getWhiteboardAncestors) | **GET** /whiteboards/{id}/ancestors | Get all ancestors of whiteboard

<a name="getDatabaseAncestors"></a>
# **getDatabaseAncestors**
> MultiEntityResultAncestor getDatabaseAncestors(id, limit)

Get all ancestors of database

Returns all ancestors for a given database by ID in top-to-bottom order (that is, the highest ancestor is the first item in the response payload). The number of results is limited by the &#x60;limit&#x60; parameter and additional results (if available) will be available by calling this endpoint with the ID of first ancestor in the response payload.  This endpoint returns minimal information about each ancestor. To fetch more details, use a related endpoint, such as [Get database by id](https://developer.atlassian.com/cloud/confluence/rest/v2/api-group-database/#api-databases-id-get).  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to access the Confluence site (&#x27;Can use&#x27; global permission). Permission to view the database and its corresponding space

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.AncestorsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

AncestorsApi apiInstance = new AncestorsApi();
Long id = 789L; // Long | The ID of the database.
Integer limit = 25; // Integer | Maximum number of items per result to return. If more results exist, call the endpoint with the highest ancestor's ID to fetch the next set of results.
try {
    MultiEntityResultAncestor result = apiInstance.getDatabaseAncestors(id, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AncestorsApi#getDatabaseAncestors");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the database. |
 **limit** | **Integer**| Maximum number of items per result to return. If more results exist, call the endpoint with the highest ancestor&#x27;s ID to fetch the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultAncestor**](MultiEntityResultAncestor.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getFolderAncestors"></a>
# **getFolderAncestors**
> MultiEntityResultAncestor getFolderAncestors(id, limit)

Get all ancestors of folder

Returns all ancestors for a given folder by ID in top-to-bottom order (that is, the highest ancestor is the first item in the response payload). The number of results is limited by the &#x60;limit&#x60; parameter and additional results  (if available) will be available by calling this endpoint with the ID of first ancestor in the response payload.  This endpoint returns minimal information about each ancestor. To fetch more details, use a related endpoint, such as [Get folder by id](https://developer.atlassian.com/cloud/confluence/rest/v2/api-group-smart-link/#api-folders-id-get).  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to access the Confluence site (&#x27;Can use&#x27; global permission). Permission to view the folder and its corresponding space

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.AncestorsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

AncestorsApi apiInstance = new AncestorsApi();
Long id = 789L; // Long | The ID of the folder.
Integer limit = 25; // Integer | Maximum number of items per result to return. If more results exist, call the endpoint with the highest ancestor's ID to fetch the next set of results.
try {
    MultiEntityResultAncestor result = apiInstance.getFolderAncestors(id, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AncestorsApi#getFolderAncestors");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the folder. |
 **limit** | **Integer**| Maximum number of items per result to return. If more results exist, call the endpoint with the highest ancestor&#x27;s ID to fetch the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultAncestor**](MultiEntityResultAncestor.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getPageAncestors"></a>
# **getPageAncestors**
> MultiEntityResultAncestor1 getPageAncestors(id, limit)

Get all ancestors of page

Returns all ancestors for a given page by ID in top-to-bottom order (that is, the highest ancestor is the first item in the response payload). The number of results is limited by the &#x60;limit&#x60; parameter and additional results (if available) will be available by calling this endpoint with the ID of first ancestor in the response payload.  This endpoint returns minimal information about each ancestor. To fetch more details, use a related endpoint, such as [Get page by id](https://developer.atlassian.com/cloud/confluence/rest/v2/api-group-page/#api-pages-id-get).  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to access the Confluence site (&#x27;Can use&#x27; global permission).

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.AncestorsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

AncestorsApi apiInstance = new AncestorsApi();
Long id = 789L; // Long | The ID of the page.
Integer limit = 25; // Integer | Maximum number of pages per result to return. If more results exist, call this endpoint with the highest ancestor's ID to fetch the next set of results.
try {
    MultiEntityResultAncestor1 result = apiInstance.getPageAncestors(id, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AncestorsApi#getPageAncestors");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the page. |
 **limit** | **Integer**| Maximum number of pages per result to return. If more results exist, call this endpoint with the highest ancestor&#x27;s ID to fetch the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultAncestor1**](MultiEntityResultAncestor1.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getSmartLinkAncestors"></a>
# **getSmartLinkAncestors**
> MultiEntityResultAncestor getSmartLinkAncestors(id, limit)

Get all ancestors of Smart Link in content tree

Returns all ancestors for a given Smart Link in the content tree by ID in top-to-bottom order (that is, the highest ancestor is the first item in the response payload). The number of results is limited by the &#x60;limit&#x60; parameter and additional results  (if available) will be available by calling this endpoint with the ID of first ancestor in the response payload.  This endpoint returns minimal information about each ancestor. To fetch more details, use a related endpoint, such as [Get Smart Link in the content tree by id](https://developer.atlassian.com/cloud/confluence/rest/v2/api-group-smart-link/#api-embeds-id-get).  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to access the Confluence site (&#x27;Can use&#x27; global permission). Permission to view the Smart Link in the content tree and its corresponding space

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.AncestorsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

AncestorsApi apiInstance = new AncestorsApi();
Long id = 789L; // Long | The ID of the Smart Link in the content tree.
Integer limit = 25; // Integer | Maximum number of items per result to return. If more results exist, call the endpoint with the highest ancestor's ID to fetch the next set of results.
try {
    MultiEntityResultAncestor result = apiInstance.getSmartLinkAncestors(id, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AncestorsApi#getSmartLinkAncestors");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the Smart Link in the content tree. |
 **limit** | **Integer**| Maximum number of items per result to return. If more results exist, call the endpoint with the highest ancestor&#x27;s ID to fetch the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultAncestor**](MultiEntityResultAncestor.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getWhiteboardAncestors"></a>
# **getWhiteboardAncestors**
> MultiEntityResultAncestor getWhiteboardAncestors(id, limit)

Get all ancestors of whiteboard

Returns all ancestors for a given whiteboard by ID in top-to-bottom order (that is, the highest ancestor is the first item in the response payload). The number of results is limited by the &#x60;limit&#x60; parameter and additional results (if available) will be available by calling this endpoint with the ID of first ancestor in the response payload.  This endpoint returns minimal information about each ancestor. To fetch more details, use a related endpoint, such as [Get whiteboard by id](https://developer.atlassian.com/cloud/confluence/rest/v2/api-group-whiteboard/#api-whiteboards-id-get).  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to access the Confluence site (&#x27;Can use&#x27; global permission). Permission to view the whiteboard and its corresponding space

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.AncestorsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

AncestorsApi apiInstance = new AncestorsApi();
Long id = 789L; // Long | The ID of the whiteboard.
Integer limit = 25; // Integer | Maximum number of items per result to return. If more results exist, call the endpoint with the highest ancestor's ID to fetch the next set of results.
try {
    MultiEntityResultAncestor result = apiInstance.getWhiteboardAncestors(id, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AncestorsApi#getWhiteboardAncestors");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the whiteboard. |
 **limit** | **Integer**| Maximum number of items per result to return. If more results exist, call the endpoint with the highest ancestor&#x27;s ID to fetch the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultAncestor**](MultiEntityResultAncestor.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

