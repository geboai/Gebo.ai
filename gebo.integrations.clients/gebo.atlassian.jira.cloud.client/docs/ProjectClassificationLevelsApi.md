# ProjectClassificationLevelsApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getDefaultProjectClassification**](ProjectClassificationLevelsApi.md#getDefaultProjectClassification) | **GET** /rest/api/3/project/{projectIdOrKey}/classification-level/default | Get the default data classification level of a project
[**removeDefaultProjectClassification**](ProjectClassificationLevelsApi.md#removeDefaultProjectClassification) | **DELETE** /rest/api/3/project/{projectIdOrKey}/classification-level/default | Remove the default data classification level from a project
[**updateDefaultProjectClassification**](ProjectClassificationLevelsApi.md#updateDefaultProjectClassification) | **PUT** /rest/api/3/project/{projectIdOrKey}/classification-level/default | Update the default data classification level of a project

<a name="getDefaultProjectClassification"></a>
# **getDefaultProjectClassification**
> Object getDefaultProjectClassification(projectIdOrKey)

Get the default data classification level of a project

Returns the default data classification for a project.  **[Permissions](#permissions) required:**   *  *Browse Projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project.  *  *Administer projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project.  *  *Administer jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.ProjectClassificationLevelsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ProjectClassificationLevelsApi apiInstance = new ProjectClassificationLevelsApi();
String projectIdOrKey = "projectIdOrKey_example"; // String | The project ID or project key (case-sensitive).
try {
    Object result = apiInstance.getDefaultProjectClassification(projectIdOrKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectClassificationLevelsApi#getDefaultProjectClassification");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectIdOrKey** | **String**| The project ID or project key (case-sensitive). |

### Return type

**Object**

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="removeDefaultProjectClassification"></a>
# **removeDefaultProjectClassification**
> Object removeDefaultProjectClassification(projectIdOrKey)

Remove the default data classification level from a project

Remove the default data classification level for a project.  **[Permissions](#permissions) required:**   *  *Administer projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project.  *  *Administer jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.ProjectClassificationLevelsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ProjectClassificationLevelsApi apiInstance = new ProjectClassificationLevelsApi();
String projectIdOrKey = "projectIdOrKey_example"; // String | The project ID or project key (case-sensitive).
try {
    Object result = apiInstance.removeDefaultProjectClassification(projectIdOrKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectClassificationLevelsApi#removeDefaultProjectClassification");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectIdOrKey** | **String**| The project ID or project key (case-sensitive). |

### Return type

**Object**

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateDefaultProjectClassification"></a>
# **updateDefaultProjectClassification**
> Object updateDefaultProjectClassification(body, projectIdOrKey)

Update the default data classification level of a project

Updates the default data classification level for a project.  **[Permissions](#permissions) required:**   *  *Administer projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project.  *  *Administer jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.ProjectClassificationLevelsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ProjectClassificationLevelsApi apiInstance = new ProjectClassificationLevelsApi();
UpdateDefaultProjectClassificationBean body = new UpdateDefaultProjectClassificationBean(); // UpdateDefaultProjectClassificationBean | 
String projectIdOrKey = "projectIdOrKey_example"; // String | The project ID or project key (case-sensitive).
try {
    Object result = apiInstance.updateDefaultProjectClassification(body, projectIdOrKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectClassificationLevelsApi#updateDefaultProjectClassification");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UpdateDefaultProjectClassificationBean**](UpdateDefaultProjectClassificationBean.md)|  |
 **projectIdOrKey** | **String**| The project ID or project key (case-sensitive). |

### Return type

**Object**

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

