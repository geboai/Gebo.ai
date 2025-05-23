# LabelInfoApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAllLabelContent**](LabelInfoApi.md#getAllLabelContent) | **GET** /wiki/rest/api/label | Get label information

<a name="getAllLabelContent"></a>
# **getAllLabelContent**
> LabelDetails getAllLabelContent(name, type, start, limit)

Get label information

Returns label information and a list of contents associated with the label.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Permission to access the Confluence site (&#x27;Can use&#x27; global permission). Only contents that the user is permitted to view is returned.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.LabelInfoApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

LabelInfoApi apiInstance = new LabelInfoApi();
String name = "name_example"; // String | Name of the label to query.
String type = "type_example"; // String | The type of contents that are to be returned.
Integer start = 0; // Integer | The starting offset for the results.
Integer limit = 200; // Integer | The number of results to be returned.
try {
    LabelDetails result = apiInstance.getAllLabelContent(name, type, start, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling LabelInfoApi#getAllLabelContent");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **name** | **String**| Name of the label to query. |
 **type** | **String**| The type of contents that are to be returned. | [optional] [enum: page, blogpost, attachment, page_template]
 **start** | **Integer**| The starting offset for the results. | [optional] [default to 0]
 **limit** | **Integer**| The number of results to be returned. | [optional] [default to 200]

### Return type

[**LabelDetails**](LabelDetails.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

