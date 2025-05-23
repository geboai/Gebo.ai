# DatabaseApi

All URIs are relative to *https://{your-domain}/wiki/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createDatabase**](DatabaseApi.md#createDatabase) | **POST** /databases | Create database
[**deleteDatabase**](DatabaseApi.md#deleteDatabase) | **DELETE** /databases/{id} | Delete database
[**getDatabaseById**](DatabaseApi.md#getDatabaseById) | **GET** /databases/{id} | Get database by id

<a name="createDatabase"></a>
# **createDatabase**
> InlineResponse2004 createDatabase(body, _private)

Create database

Creates a database in the space.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the corresponding space. Permission to create a database in the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.DatabaseApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

DatabaseApi apiInstance = new DatabaseApi();
Object body = null; // Object | 
Boolean _private = false; // Boolean | The database will be private. Only the user who creates this database will have permission to view and edit one.
try {
    InlineResponse2004 result = apiInstance.createDatabase(body, _private);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DatabaseApi#createDatabase");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Object**](Object.md)|  |
 **_private** | **Boolean**| The database will be private. Only the user who creates this database will have permission to view and edit one. | [optional] [default to false]

### Return type

[**InlineResponse2004**](InlineResponse2004.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deleteDatabase"></a>
# **deleteDatabase**
> deleteDatabase(id)

Delete database

Delete a database by id.  Deleting a database moves the database to the trash, where it can be restored later  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the database and its corresponding space. Permission to delete databases in the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.DatabaseApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

DatabaseApi apiInstance = new DatabaseApi();
Long id = 789L; // Long | The ID of the database to be deleted.
try {
    apiInstance.deleteDatabase(id);
} catch (ApiException e) {
    System.err.println("Exception when calling DatabaseApi#deleteDatabase");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the database to be deleted. |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="getDatabaseById"></a>
# **getDatabaseById**
> InlineResponse2004 getDatabaseById(id)

Get database by id

Returns a specific database.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to view the database and its corresponding space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.DatabaseApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

DatabaseApi apiInstance = new DatabaseApi();
Long id = 789L; // Long | The ID of the database to be returned
try {
    InlineResponse2004 result = apiInstance.getDatabaseById(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DatabaseApi#getDatabaseById");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| The ID of the database to be returned |

### Return type

[**InlineResponse2004**](InlineResponse2004.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

