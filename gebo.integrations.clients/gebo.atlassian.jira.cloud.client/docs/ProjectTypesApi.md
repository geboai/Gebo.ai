# ProjectTypesApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAccessibleProjectTypeByKey**](ProjectTypesApi.md#getAccessibleProjectTypeByKey) | **GET** /rest/api/3/project/type/{projectTypeKey}/accessible | Get accessible project type by key
[**getAllAccessibleProjectTypes**](ProjectTypesApi.md#getAllAccessibleProjectTypes) | **GET** /rest/api/3/project/type/accessible | Get licensed project types
[**getAllProjectTypes**](ProjectTypesApi.md#getAllProjectTypes) | **GET** /rest/api/3/project/type | Get all project types
[**getProjectTypeByKey**](ProjectTypesApi.md#getProjectTypeByKey) | **GET** /rest/api/3/project/type/{projectTypeKey} | Get project type by key

<a name="getAccessibleProjectTypeByKey"></a>
# **getAccessibleProjectTypeByKey**
> ProjectType getAccessibleProjectTypeByKey(projectTypeKey)

Get accessible project type by key

Returns a [project type](https://confluence.atlassian.com/x/Var1Nw) if it is accessible to the user.  **[Permissions](#permissions) required:** Permission to access Jira.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.ProjectTypesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ProjectTypesApi apiInstance = new ProjectTypesApi();
String projectTypeKey = "projectTypeKey_example"; // String | The key of the project type.
try {
    ProjectType result = apiInstance.getAccessibleProjectTypeByKey(projectTypeKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectTypesApi#getAccessibleProjectTypeByKey");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectTypeKey** | **String**| The key of the project type. | [enum: software, service_desk, business, product_discovery]

### Return type

[**ProjectType**](ProjectType.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAllAccessibleProjectTypes"></a>
# **getAllAccessibleProjectTypes**
> List&lt;ProjectType&gt; getAllAccessibleProjectTypes()

Get licensed project types

Returns all [project types](https://confluence.atlassian.com/x/Var1Nw) with a valid license.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.ProjectTypesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ProjectTypesApi apiInstance = new ProjectTypesApi();
try {
    List<ProjectType> result = apiInstance.getAllAccessibleProjectTypes();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectTypesApi#getAllAccessibleProjectTypes");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;ProjectType&gt;**](ProjectType.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAllProjectTypes"></a>
# **getAllProjectTypes**
> List&lt;ProjectType&gt; getAllProjectTypes()

Get all project types

Returns all [project types](https://confluence.atlassian.com/x/Var1Nw), whether or not the instance has a valid license for each type.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** None.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.ProjectTypesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ProjectTypesApi apiInstance = new ProjectTypesApi();
try {
    List<ProjectType> result = apiInstance.getAllProjectTypes();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectTypesApi#getAllProjectTypes");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;ProjectType&gt;**](ProjectType.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getProjectTypeByKey"></a>
# **getProjectTypeByKey**
> ProjectType getProjectTypeByKey(projectTypeKey)

Get project type by key

Returns a [project type](https://confluence.atlassian.com/x/Var1Nw).  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** None.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.ProjectTypesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ProjectTypesApi apiInstance = new ProjectTypesApi();
String projectTypeKey = "projectTypeKey_example"; // String | The key of the project type.
try {
    ProjectType result = apiInstance.getProjectTypeByKey(projectTypeKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectTypesApi#getProjectTypeByKey");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectTypeKey** | **String**| The key of the project type. | [enum: software, service_desk, business, product_discovery]

### Return type

[**ProjectType**](ProjectType.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

