# ClassificationLevelsApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAllUserDataClassificationLevels**](ClassificationLevelsApi.md#getAllUserDataClassificationLevels) | **GET** /rest/api/3/classification-levels | Get all classification levels

<a name="getAllUserDataClassificationLevels"></a>
# **getAllUserDataClassificationLevels**
> DataClassificationLevelsBean getAllUserDataClassificationLevels(status, orderBy)

Get all classification levels

Returns all classification levels.  **[Permissions](#permissions) required:** None.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.ClassificationLevelsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ClassificationLevelsApi apiInstance = new ClassificationLevelsApi();
List<String> status = Arrays.asList("status_example"); // List<String> | Optional set of statuses to filter by.
String orderBy = "orderBy_example"; // String | Ordering of the results by a given field. If not provided, values will not be sorted.
try {
    DataClassificationLevelsBean result = apiInstance.getAllUserDataClassificationLevels(status, orderBy);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ClassificationLevelsApi#getAllUserDataClassificationLevels");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **status** | [**List&lt;String&gt;**](String.md)| Optional set of statuses to filter by. | [optional] [enum: PUBLISHED, ARCHIVED, DRAFT]
 **orderBy** | **String**| Ordering of the results by a given field. If not provided, values will not be sorted. | [optional] [enum: rank, -rank, +rank]

### Return type

[**DataClassificationLevelsBean**](DataClassificationLevelsBean.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

