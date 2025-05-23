# SpaceSettingsApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getSpaceSettings**](SpaceSettingsApi.md#getSpaceSettings) | **GET** /wiki/rest/api/space/{spaceKey}/settings | Get space settings
[**updateSpaceSettings**](SpaceSettingsApi.md#updateSpaceSettings) | **PUT** /wiki/rest/api/space/{spaceKey}/settings | Update space settings

<a name="getSpaceSettings"></a>
# **getSpaceSettings**
> SpaceSettings getSpaceSettings(spaceKey)

Get space settings

Returns the settings of a space. Currently only the &#x60;routeOverrideEnabled&#x60; setting can be returned.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;View&#x27; permission for the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.SpaceSettingsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

SpaceSettingsApi apiInstance = new SpaceSettingsApi();
String spaceKey = "spaceKey_example"; // String | The key of the space to be queried for its settings.
try {
    SpaceSettings result = apiInstance.getSpaceSettings(spaceKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SpaceSettingsApi#getSpaceSettings");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **spaceKey** | **String**| The key of the space to be queried for its settings. |

### Return type

[**SpaceSettings**](SpaceSettings.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateSpaceSettings"></a>
# **updateSpaceSettings**
> SpaceSettings updateSpaceSettings(body, spaceKey)

Update space settings

Updates the settings for a space. Currently only the &#x60;routeOverrideEnabled&#x60; setting can be updated.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Admin&#x27; permission for the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.SpaceSettingsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

SpaceSettingsApi apiInstance = new SpaceSettingsApi();
SpaceSettingsUpdate body = new SpaceSettingsUpdate(); // SpaceSettingsUpdate | The space settings to update.
String spaceKey = "spaceKey_example"; // String | The key of the space whose settings will be updated.
try {
    SpaceSettings result = apiInstance.updateSpaceSettings(body, spaceKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SpaceSettingsApi#updateSpaceSettings");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SpaceSettingsUpdate**](SpaceSettingsUpdate.md)| The space settings to update. |
 **spaceKey** | **String**| The key of the space whose settings will be updated. |

### Return type

[**SpaceSettings**](SpaceSettings.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

