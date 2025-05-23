# ProjectFeaturesApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getFeaturesForProject**](ProjectFeaturesApi.md#getFeaturesForProject) | **GET** /rest/api/3/project/{projectIdOrKey}/features | Get project features
[**toggleFeatureForProject**](ProjectFeaturesApi.md#toggleFeatureForProject) | **PUT** /rest/api/3/project/{projectIdOrKey}/features/{featureKey} | Set project feature state

<a name="getFeaturesForProject"></a>
# **getFeaturesForProject**
> ContainerForProjectFeatures getFeaturesForProject(projectIdOrKey)

Get project features

Returns the list of features for a project.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.ProjectFeaturesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ProjectFeaturesApi apiInstance = new ProjectFeaturesApi();
String projectIdOrKey = "projectIdOrKey_example"; // String | The ID or (case-sensitive) key of the project.
try {
    ContainerForProjectFeatures result = apiInstance.getFeaturesForProject(projectIdOrKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectFeaturesApi#getFeaturesForProject");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectIdOrKey** | **String**| The ID or (case-sensitive) key of the project. |

### Return type

[**ContainerForProjectFeatures**](ContainerForProjectFeatures.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="toggleFeatureForProject"></a>
# **toggleFeatureForProject**
> ContainerForProjectFeatures toggleFeatureForProject(body, projectIdOrKey, featureKey)

Set project feature state

Sets the state of a project feature.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.ProjectFeaturesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ProjectFeaturesApi apiInstance = new ProjectFeaturesApi();
ProjectFeatureState body = new ProjectFeatureState(); // ProjectFeatureState | Details of the feature state change.
String projectIdOrKey = "projectIdOrKey_example"; // String | The ID or (case-sensitive) key of the project.
String featureKey = "featureKey_example"; // String | The key of the feature.
try {
    ContainerForProjectFeatures result = apiInstance.toggleFeatureForProject(body, projectIdOrKey, featureKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectFeaturesApi#toggleFeatureForProject");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ProjectFeatureState**](ProjectFeatureState.md)| Details of the feature state change. |
 **projectIdOrKey** | **String**| The ID or (case-sensitive) key of the project. |
 **featureKey** | **String**| The key of the feature. |

### Return type

[**ContainerForProjectFeatures**](ContainerForProjectFeatures.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

