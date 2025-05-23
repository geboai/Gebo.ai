# ProjectKeyAndNameValidationApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getValidProjectKey**](ProjectKeyAndNameValidationApi.md#getValidProjectKey) | **GET** /rest/api/3/projectvalidate/validProjectKey | Get valid project key
[**getValidProjectName**](ProjectKeyAndNameValidationApi.md#getValidProjectName) | **GET** /rest/api/3/projectvalidate/validProjectName | Get valid project name
[**validateProjectKey**](ProjectKeyAndNameValidationApi.md#validateProjectKey) | **GET** /rest/api/3/projectvalidate/key | Validate project key

<a name="getValidProjectKey"></a>
# **getValidProjectKey**
> String getValidProjectKey(key)

Get valid project key

Validates a project key and, if the key is invalid or in use, generates a valid random string for the project key.  **[Permissions](#permissions) required:** None.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.ProjectKeyAndNameValidationApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ProjectKeyAndNameValidationApi apiInstance = new ProjectKeyAndNameValidationApi();
String key = "key_example"; // String | The project key.
try {
    String result = apiInstance.getValidProjectKey(key);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectKeyAndNameValidationApi#getValidProjectKey");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **key** | **String**| The project key. | [optional]

### Return type

**String**

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getValidProjectName"></a>
# **getValidProjectName**
> String getValidProjectName(name)

Get valid project name

Checks that a project name isn&#x27;t in use. If the name isn&#x27;t in use, the passed string is returned. If the name is in use, this operation attempts to generate a valid project name based on the one supplied, usually by adding a sequence number. If a valid project name cannot be generated, a 404 response is returned.  **[Permissions](#permissions) required:** None.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.ProjectKeyAndNameValidationApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ProjectKeyAndNameValidationApi apiInstance = new ProjectKeyAndNameValidationApi();
String name = "name_example"; // String | The project name.
try {
    String result = apiInstance.getValidProjectName(name);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectKeyAndNameValidationApi#getValidProjectName");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **name** | **String**| The project name. |

### Return type

**String**

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="validateProjectKey"></a>
# **validateProjectKey**
> ErrorCollection validateProjectKey(key)

Validate project key

Validates a project key by confirming the key is a valid string and not in use.  **[Permissions](#permissions) required:** None.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.ProjectKeyAndNameValidationApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ProjectKeyAndNameValidationApi apiInstance = new ProjectKeyAndNameValidationApi();
String key = "key_example"; // String | The project key.
try {
    ErrorCollection result = apiInstance.validateProjectKey(key);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectKeyAndNameValidationApi#validateProjectKey");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **key** | **String**| The project key. | [optional]

### Return type

[**ErrorCollection**](ErrorCollection.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

