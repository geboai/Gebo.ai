# DataPoliciesApi

All URIs are relative to *https://{your-domain}/wiki/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getDataPolicyMetadata**](DataPoliciesApi.md#getDataPolicyMetadata) | **GET** /data-policies/metadata | Get data policy metadata for the workspace
[**getDataPolicySpaces**](DataPoliciesApi.md#getDataPolicySpaces) | **GET** /data-policies/spaces | Get spaces with data policies

<a name="getDataPolicyMetadata"></a>
# **getDataPolicyMetadata**
> DataPolicyMetadata getDataPolicyMetadata()

Get data policy metadata for the workspace

Returns data policy metadata for the workspace.  **[Permissions](#permissions) required:** Only apps can make this request. Permission to access the Confluence site (&#x27;Can use&#x27; global permission).

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.DataPoliciesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

DataPoliciesApi apiInstance = new DataPoliciesApi();
try {
    DataPolicyMetadata result = apiInstance.getDataPolicyMetadata();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DataPoliciesApi#getDataPolicyMetadata");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**DataPolicyMetadata**](DataPolicyMetadata.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getDataPolicySpaces"></a>
# **getDataPolicySpaces**
> MultiEntityResultDataPolicySpace getDataPolicySpaces(ids, keys, sort, cursor, limit)

Get spaces with data policies

Returns all spaces. The results will be sorted by id ascending. The number of results is limited by the &#x60;limit&#x60; parameter and additional results (if available) will be available through the &#x60;next&#x60; URL present in the &#x60;Link&#x60; response header.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: Only apps can make this request. Permission to access the Confluence site (&#x27;Can use&#x27; global permission). Only spaces that the app has permission to view will be returned.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.DataPoliciesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

DataPoliciesApi apiInstance = new DataPoliciesApi();
List<Long> ids = Arrays.asList(56L); // List<Long> | Filter the results to spaces based on their IDs. Multiple IDs can be specified as a comma-separated list.
List<String> keys = Arrays.asList("keys_example"); // List<String> | Filter the results to spaces based on their keys. Multiple keys can be specified as a comma-separated list.
SpaceSortOrder sort = new SpaceSortOrder(); // SpaceSortOrder | Used to sort the result by a particular field.
String cursor = "cursor_example"; // String | Used for pagination, this opaque cursor will be returned in the `next` URL in the `Link` response header. Use the relative URL in the `Link` header to retrieve the `next` set of results.
Integer limit = 25; // Integer | Maximum number of spaces per result to return. If more results exist, use the `Link` response header to retrieve a relative URL that will return the next set of results.
try {
    MultiEntityResultDataPolicySpace result = apiInstance.getDataPolicySpaces(ids, keys, sort, cursor, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DataPoliciesApi#getDataPolicySpaces");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ids** | [**List&lt;Long&gt;**](Long.md)| Filter the results to spaces based on their IDs. Multiple IDs can be specified as a comma-separated list. | [optional]
 **keys** | [**List&lt;String&gt;**](String.md)| Filter the results to spaces based on their keys. Multiple keys can be specified as a comma-separated list. | [optional]
 **sort** | [**SpaceSortOrder**](.md)| Used to sort the result by a particular field. | [optional]
 **cursor** | **String**| Used for pagination, this opaque cursor will be returned in the &#x60;next&#x60; URL in the &#x60;Link&#x60; response header. Use the relative URL in the &#x60;Link&#x60; header to retrieve the &#x60;next&#x60; set of results. | [optional]
 **limit** | **Integer**| Maximum number of spaces per result to return. If more results exist, use the &#x60;Link&#x60; response header to retrieve a relative URL that will return the next set of results. | [optional] [default to 25] [enum: 1, 250]

### Return type

[**MultiEntityResultDataPolicySpace**](MultiEntityResultDataPolicySpace.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

