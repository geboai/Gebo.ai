# AppDataPoliciesApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getPolicies**](AppDataPoliciesApi.md#getPolicies) | **GET** /rest/api/3/data-policy/project | Get data policy for projects
[**getPolicy**](AppDataPoliciesApi.md#getPolicy) | **GET** /rest/api/3/data-policy | Get data policy for the workspace

<a name="getPolicies"></a>
# **getPolicies**
> ProjectDataPolicies getPolicies(ids)

Get data policy for projects

Returns data policies for the projects specified in the request.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.AppDataPoliciesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AppDataPoliciesApi apiInstance = new AppDataPoliciesApi();
String ids = "ids_example"; // String | A list of project identifiers. This parameter accepts a comma-separated list.
try {
    ProjectDataPolicies result = apiInstance.getPolicies(ids);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AppDataPoliciesApi#getPolicies");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ids** | **String**| A list of project identifiers. This parameter accepts a comma-separated list. | [optional]

### Return type

[**ProjectDataPolicies**](ProjectDataPolicies.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getPolicy"></a>
# **getPolicy**
> WorkspaceDataPolicy getPolicy()

Get data policy for the workspace

Returns data policy for the workspace.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.AppDataPoliciesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

AppDataPoliciesApi apiInstance = new AppDataPoliciesApi();
try {
    WorkspaceDataPolicy result = apiInstance.getPolicy();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AppDataPoliciesApi#getPolicy");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**WorkspaceDataPolicy**](WorkspaceDataPolicy.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

