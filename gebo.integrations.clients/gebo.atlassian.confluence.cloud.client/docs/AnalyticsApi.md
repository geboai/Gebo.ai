# AnalyticsApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getViewers**](AnalyticsApi.md#getViewers) | **GET** /wiki/rest/api/analytics/content/{contentId}/viewers | Get viewers
[**getViews**](AnalyticsApi.md#getViews) | **GET** /wiki/rest/api/analytics/content/{contentId}/views | Get views

<a name="getViewers"></a>
# **getViewers**
> InlineResponse2003 getViewers(contentId, fromDate)

Get viewers

Get the total number of distinct viewers a piece of content has.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.AnalyticsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

AnalyticsApi apiInstance = new AnalyticsApi();
String contentId = "contentId_example"; // String | The ID of the content to get the viewers for.
String fromDate = "fromDate_example"; // String | The number of views for the content since the date.
try {
    InlineResponse2003 result = apiInstance.getViewers(contentId, fromDate);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AnalyticsApi#getViewers");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **contentId** | **String**| The ID of the content to get the viewers for. |
 **fromDate** | **String**| The number of views for the content since the date. | [optional]

### Return type

[**InlineResponse2003**](InlineResponse2003.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getViews"></a>
# **getViews**
> InlineResponse2002 getViews(contentId, fromDate)

Get views

Get the total number of views a piece of content has.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.AnalyticsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

AnalyticsApi apiInstance = new AnalyticsApi();
String contentId = "contentId_example"; // String | The ID of the content to get the views for.
String fromDate = "fromDate_example"; // String | The number of views for the content since the date.
try {
    InlineResponse2002 result = apiInstance.getViews(contentId, fromDate);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AnalyticsApi#getViews");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **contentId** | **String**| The ID of the content to get the views for. |
 **fromDate** | **String**| The number of views for the content since the date. | [optional]

### Return type

[**InlineResponse2002**](InlineResponse2002.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

