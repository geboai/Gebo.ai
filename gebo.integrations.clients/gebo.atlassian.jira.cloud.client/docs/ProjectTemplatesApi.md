# ProjectTemplatesApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createProjectWithCustomTemplate**](ProjectTemplatesApi.md#createProjectWithCustomTemplate) | **POST** /rest/api/3/project-template | Create custom project

<a name="createProjectWithCustomTemplate"></a>
# **createProjectWithCustomTemplate**
> createProjectWithCustomTemplate(body)

Create custom project

Creates a project based on a custom template provided in the request.  The request body should contain the project details and the capabilities that comprise the project:   *  &#x60;details&#x60; \\- represents the project details settings  *  &#x60;template&#x60; \\- represents a list of capabilities responsible for creating specific parts of a project  A capability is defined as a unit of configuration for the project you want to create.  This operation is:   *  [asynchronous](#async). Follow the &#x60;Location&#x60; link in the response header to determine the status of the task and use [Get task](#api-rest-api-3-task-taskId-get) to obtain subsequent updates.  ***Note: This API is only supported for Jira Enterprise edition.***

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.ProjectTemplatesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ProjectTemplatesApi apiInstance = new ProjectTemplatesApi();
ProjectCustomTemplateCreateRequestDTO body = new ProjectCustomTemplateCreateRequestDTO(); // ProjectCustomTemplateCreateRequestDTO | The JSON payload containing the project details and capabilities
try {
    apiInstance.createProjectWithCustomTemplate(body);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectTemplatesApi#createProjectWithCustomTemplate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ProjectCustomTemplateCreateRequestDTO**](ProjectCustomTemplateCreateRequestDTO.md)| The JSON payload containing the project details and capabilities |

### Return type

null (empty response body)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

