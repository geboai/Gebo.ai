# SettingsApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getLookAndFeelSettings**](SettingsApi.md#getLookAndFeelSettings) | **GET** /wiki/rest/api/settings/lookandfeel | Get look and feel settings
[**getSystemInfo**](SettingsApi.md#getSystemInfo) | **GET** /wiki/rest/api/settings/systemInfo | Get system info
[**resetLookAndFeelSettings**](SettingsApi.md#resetLookAndFeelSettings) | **DELETE** /wiki/rest/api/settings/lookandfeel/custom | Reset look and feel settings
[**setLookAndFeelSettings**](SettingsApi.md#setLookAndFeelSettings) | **PUT** /wiki/rest/api/settings/lookandfeel/selected | Set look and feel settings
[**updateLookAndFeel**](SettingsApi.md#updateLookAndFeel) | **PUT** /wiki/rest/api/settings/lookandfeel | Select look and feel settings
[**updateLookAndFeelSettings**](SettingsApi.md#updateLookAndFeelSettings) | **POST** /wiki/rest/api/settings/lookandfeel/custom | Update look and feel settings

<a name="getLookAndFeelSettings"></a>
# **getLookAndFeelSettings**
> LookAndFeelSettings getLookAndFeelSettings(spaceKey)

Get look and feel settings

Returns the look and feel settings for the site or a single space. This includes attributes such as the color scheme, padding, and border radius.  The look and feel settings for a space can be inherited from the global look and feel settings or provided by a theme.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: None

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.SettingsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

SettingsApi apiInstance = new SettingsApi();
String spaceKey = "spaceKey_example"; // String | The key of the space for which the look and feel settings will be returned. If this is not set, only the global look and feel settings are returned.
try {
    LookAndFeelSettings result = apiInstance.getLookAndFeelSettings(spaceKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SettingsApi#getLookAndFeelSettings");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **spaceKey** | **String**| The key of the space for which the look and feel settings will be returned. If this is not set, only the global look and feel settings are returned. | [optional]

### Return type

[**LookAndFeelSettings**](LookAndFeelSettings.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getSystemInfo"></a>
# **getSystemInfo**
> SystemInfoEntity getSystemInfo()

Get system info

Returns the system information for the Confluence Cloud tenant. This information is used by Atlassian.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to access the Confluence site (&#x27;Can use&#x27; global permission).

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.SettingsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

SettingsApi apiInstance = new SettingsApi();
try {
    SystemInfoEntity result = apiInstance.getSystemInfo();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SettingsApi#getSystemInfo");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**SystemInfoEntity**](SystemInfoEntity.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="resetLookAndFeelSettings"></a>
# **resetLookAndFeelSettings**
> resetLookAndFeelSettings(spaceKey)

Reset look and feel settings

Resets the custom look and feel settings for the site or a single space. This changes the values of the custom settings to be the same as the default settings. It does not change which settings (default or custom) are selected. Note, the default space settings are inherited from the current global settings.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Admin&#x27; permission for the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.SettingsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

SettingsApi apiInstance = new SettingsApi();
String spaceKey = "spaceKey_example"; // String | The key of the space for which the look and feel settings will be reset. If this is not set, the global look and feel settings will be reset.
try {
    apiInstance.resetLookAndFeelSettings(spaceKey);
} catch (ApiException e) {
    System.err.println("Exception when calling SettingsApi#resetLookAndFeelSettings");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **spaceKey** | **String**| The key of the space for which the look and feel settings will be reset. If this is not set, the global look and feel settings will be reset. | [optional]

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="setLookAndFeelSettings"></a>
# **setLookAndFeelSettings**
> String setLookAndFeelSettings(body, spaceKey)

Set look and feel settings

Sets the look and feel settings to either the default settings or the custom settings, for the site or a single space. Note, the default space settings are inherited from the current global settings.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Admin&#x27; permission for the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.SettingsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

SettingsApi apiInstance = new SettingsApi();
String body = "body_example"; // String | The look and feel type to be set.
String spaceKey = "spaceKey_example"; // String | The key of the space for which the look and feel settings will be set. If this is not set, the global look and feel settings will be set.
try {
    String result = apiInstance.setLookAndFeelSettings(body, spaceKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SettingsApi#setLookAndFeelSettings");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**String**](String.md)| The look and feel type to be set. |
 **spaceKey** | **String**| The key of the space for which the look and feel settings will be set. If this is not set, the global look and feel settings will be set. | [optional]

### Return type

**String**

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateLookAndFeel"></a>
# **updateLookAndFeel**
> LookAndFeelSelection updateLookAndFeel(body)

Select look and feel settings

Sets the look and feel settings to the default (global) settings, the custom settings, or the current theme&#x27;s settings for a space. The custom and theme settings can only be selected if there is already a theme set for a space. Note, the default space settings are inherited from the current global settings.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Admin&#x27; permission for the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.SettingsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

SettingsApi apiInstance = new SettingsApi();
LookAndFeelSelection body = new LookAndFeelSelection(); // LookAndFeelSelection | The look and feel type to be set.
try {
    LookAndFeelSelection result = apiInstance.updateLookAndFeel(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SettingsApi#updateLookAndFeel");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**LookAndFeelSelection**](LookAndFeelSelection.md)| The look and feel type to be set. |

### Return type

[**LookAndFeelSelection**](LookAndFeelSelection.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateLookAndFeelSettings"></a>
# **updateLookAndFeelSettings**
> LookAndFeelWithLinks updateLookAndFeelSettings(body, spaceKey)

Update look and feel settings

Updates the look and feel settings for the site or for a single space. If custom settings exist, they are updated. If no custom settings exist, then a set of custom settings is created.  Note, if a theme is selected for a space, the space look and feel settings are provided by the theme and cannot be overridden.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Admin&#x27; permission for the space.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.SettingsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

SettingsApi apiInstance = new SettingsApi();
LookAndFeel body = new LookAndFeel(); // LookAndFeel | The updated settings. All values for the settings must be included,
regardless of whether they are being changed.

One way to create the request body is to copy the settings from the
response body of [Get look and feel settings](#api-settings-lookandfeel-get)
and modify it as needed.
String spaceKey = "spaceKey_example"; // String | The key of the space for which the look and feel settings will be updated. If this is not set, the global look and feel settings will be updated.
try {
    LookAndFeelWithLinks result = apiInstance.updateLookAndFeelSettings(body, spaceKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SettingsApi#updateLookAndFeelSettings");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**LookAndFeel**](LookAndFeel.md)| The updated settings. All values for the settings must be included,
regardless of whether they are being changed.

One way to create the request body is to copy the settings from the
response body of [Get look and feel settings](#api-settings-lookandfeel-get)
and modify it as needed. |
 **spaceKey** | **String**| The key of the space for which the look and feel settings will be updated. If this is not set, the global look and feel settings will be updated. | [optional]

### Return type

[**LookAndFeelWithLinks**](LookAndFeelWithLinks.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

