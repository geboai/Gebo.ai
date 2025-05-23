# ThemesApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getGlobalTheme**](ThemesApi.md#getGlobalTheme) | **GET** /wiki/rest/api/settings/theme/selected | Get global theme
[**getSpaceTheme**](ThemesApi.md#getSpaceTheme) | **GET** /wiki/rest/api/space/{spaceKey}/theme | Get space theme
[**getTheme**](ThemesApi.md#getTheme) | **GET** /wiki/rest/api/settings/theme/{themeKey} | Get theme
[**getThemes**](ThemesApi.md#getThemes) | **GET** /wiki/rest/api/settings/theme | Get themes
[**resetSpaceTheme**](ThemesApi.md#resetSpaceTheme) | **DELETE** /wiki/rest/api/space/{spaceKey}/theme | Reset space theme
[**setSpaceTheme**](ThemesApi.md#setSpaceTheme) | **PUT** /wiki/rest/api/space/{spaceKey}/theme | Set space theme

<a name="getGlobalTheme"></a>
# **getGlobalTheme**
> Theme getGlobalTheme()

Get global theme

Returns the globally assigned theme.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: None

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ThemesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ThemesApi apiInstance = new ThemesApi();
try {
    Theme result = apiInstance.getGlobalTheme();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ThemesApi#getGlobalTheme");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**Theme**](Theme.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getSpaceTheme"></a>
# **getSpaceTheme**
> Theme getSpaceTheme(spaceKey)

Get space theme

Returns the theme selected for a space, if one is set. If no space theme is set, this means that the space is inheriting the global look and feel settings.  **[Permissions required](https://confluence.atlassian.com/x/_AozKw)**: ‘View’ permission for the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ThemesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ThemesApi apiInstance = new ThemesApi();
String spaceKey = "spaceKey_example"; // String | The key of the space to be queried for its theme.
try {
    Theme result = apiInstance.getSpaceTheme(spaceKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ThemesApi#getSpaceTheme");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **spaceKey** | **String**| The key of the space to be queried for its theme. |

### Return type

[**Theme**](Theme.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getTheme"></a>
# **getTheme**
> Theme getTheme(themeKey)

Get theme

Returns a theme. This includes information about the theme name, description, and icon.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: None

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ThemesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ThemesApi apiInstance = new ThemesApi();
String themeKey = "themeKey_example"; // String | The key of the theme to be returned.
try {
    Theme result = apiInstance.getTheme(themeKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ThemesApi#getTheme");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **themeKey** | **String**| The key of the theme to be returned. |

### Return type

[**Theme**](Theme.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getThemes"></a>
# **getThemes**
> ThemeArray getThemes(start, limit)

Get themes

Returns all themes, not including the default theme.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: None

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ThemesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ThemesApi apiInstance = new ThemesApi();
Integer start = 0; // Integer | The starting index of the returned themes.
Integer limit = 100; // Integer | The maximum number of themes to return per page. Note, this may be restricted by fixed system limits.
try {
    ThemeArray result = apiInstance.getThemes(start, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ThemesApi#getThemes");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **start** | **Integer**| The starting index of the returned themes. | [optional] [default to 0] [enum: 0]
 **limit** | **Integer**| The maximum number of themes to return per page. Note, this may be restricted by fixed system limits. | [optional] [default to 100] [enum: 0]

### Return type

[**ThemeArray**](ThemeArray.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="resetSpaceTheme"></a>
# **resetSpaceTheme**
> resetSpaceTheme(spaceKey)

Reset space theme

Resets the space theme. This means that the space will inherit the global look and feel settings  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Admin&#x27; permission for the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ThemesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ThemesApi apiInstance = new ThemesApi();
String spaceKey = "spaceKey_example"; // String | The key of the space to reset the theme for.
try {
    apiInstance.resetSpaceTheme(spaceKey);
} catch (ApiException e) {
    System.err.println("Exception when calling ThemesApi#resetSpaceTheme");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **spaceKey** | **String**| The key of the space to reset the theme for. |

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="setSpaceTheme"></a>
# **setSpaceTheme**
> Theme setSpaceTheme(body, spaceKey)

Set space theme

Sets the theme for a space. Note, if you want to reset the space theme to the default Confluence theme, use the &#x27;Reset space theme&#x27; method instead of this method.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Admin&#x27; permission for the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.ThemesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

ThemesApi apiInstance = new ThemesApi();
ThemeUpdate body = new ThemeUpdate(); // ThemeUpdate | 
String spaceKey = "spaceKey_example"; // String | The key of the space to set the theme for.
try {
    Theme result = apiInstance.setSpaceTheme(body, spaceKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ThemesApi#setSpaceTheme");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ThemeUpdate**](ThemeUpdate.md)|  |
 **spaceKey** | **String**| The key of the space to set the theme for. |

### Return type

[**Theme**](Theme.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

