# UiModificationsAppsApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createUiModification**](UiModificationsAppsApi.md#createUiModification) | **POST** /rest/api/3/uiModifications | Create UI modification
[**deleteUiModification**](UiModificationsAppsApi.md#deleteUiModification) | **DELETE** /rest/api/3/uiModifications/{uiModificationId} | Delete UI modification
[**getUiModifications**](UiModificationsAppsApi.md#getUiModifications) | **GET** /rest/api/3/uiModifications | Get UI modifications
[**updateUiModification**](UiModificationsAppsApi.md#updateUiModification) | **PUT** /rest/api/3/uiModifications/{uiModificationId} | Update UI modification

<a name="createUiModification"></a>
# **createUiModification**
> UiModificationIdentifiers createUiModification(body)

Create UI modification

Creates a UI modification. UI modification can only be created by Forge apps.  Each app can define up to 3000 UI modifications. Each UI modification can define up to 1000 contexts. The same context can be assigned to maximum 100 UI modifications.  **[Permissions](#permissions) required:**   *  *None* if the UI modification is created without contexts.  *  *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for one or more projects, if the UI modification is created with contexts.  The new &#x60;write:app-data:jira&#x60; OAuth scope is 100% optional now, and not using it won&#x27;t break your app. However, we recommend adding it to your app&#x27;s scope list because we will eventually make it mandatory.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.UiModificationsAppsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

UiModificationsAppsApi apiInstance = new UiModificationsAppsApi();
CreateUiModificationDetails body = new CreateUiModificationDetails(); // CreateUiModificationDetails | Details of the UI modification.
try {
    UiModificationIdentifiers result = apiInstance.createUiModification(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UiModificationsAppsApi#createUiModification");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**CreateUiModificationDetails**](CreateUiModificationDetails.md)| Details of the UI modification. |

### Return type

[**UiModificationIdentifiers**](UiModificationIdentifiers.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deleteUiModification"></a>
# **deleteUiModification**
> Object deleteUiModification(uiModificationId)

Delete UI modification

Deletes a UI modification. All the contexts that belong to the UI modification are deleted too. UI modification can only be deleted by Forge apps.  **[Permissions](#permissions) required:** None.  The new &#x60;write:app-data:jira&#x60; OAuth scope is 100% optional now, and not using it won&#x27;t break your app. However, we recommend adding it to your app&#x27;s scope list because we will eventually make it mandatory.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.UiModificationsAppsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

UiModificationsAppsApi apiInstance = new UiModificationsAppsApi();
String uiModificationId = "uiModificationId_example"; // String | The ID of the UI modification.
try {
    Object result = apiInstance.deleteUiModification(uiModificationId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UiModificationsAppsApi#deleteUiModification");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **uiModificationId** | **String**| The ID of the UI modification. |

### Return type

**Object**

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getUiModifications"></a>
# **getUiModifications**
> PageBeanUiModificationDetails getUiModifications(startAt, maxResults, expand)

Get UI modifications

Gets UI modifications. UI modifications can only be retrieved by Forge apps.  **[Permissions](#permissions) required:** None.  The new &#x60;read:app-data:jira&#x60; OAuth scope is 100% optional now, and not using it won&#x27;t break your app. However, we recommend adding it to your app&#x27;s scope list because we will eventually make it mandatory.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.UiModificationsAppsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

UiModificationsAppsApi apiInstance = new UiModificationsAppsApi();
Long startAt = 0L; // Long | The index of the first item to return in a page of results (page offset).
Integer maxResults = 50; // Integer | The maximum number of items to return per page.
String expand = "expand_example"; // String | Use expand to include additional information in the response. This parameter accepts a comma-separated list. Expand options include:   *  `data` Returns UI modification data.  *  `contexts` Returns UI modification contexts.
try {
    PageBeanUiModificationDetails result = apiInstance.getUiModifications(startAt, maxResults, expand);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UiModificationsAppsApi#getUiModifications");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **startAt** | **Long**| The index of the first item to return in a page of results (page offset). | [optional] [default to 0]
 **maxResults** | **Integer**| The maximum number of items to return per page. | [optional] [default to 50]
 **expand** | **String**| Use expand to include additional information in the response. This parameter accepts a comma-separated list. Expand options include:   *  &#x60;data&#x60; Returns UI modification data.  *  &#x60;contexts&#x60; Returns UI modification contexts. | [optional]

### Return type

[**PageBeanUiModificationDetails**](PageBeanUiModificationDetails.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateUiModification"></a>
# **updateUiModification**
> Object updateUiModification(body, uiModificationId)

Update UI modification

Updates a UI modification. UI modification can only be updated by Forge apps.  Each UI modification can define up to 1000 contexts. The same context can be assigned to maximum 100 UI modifications.  **[Permissions](#permissions) required:**   *  *None* if the UI modification is created without contexts.  *  *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for one or more projects, if the UI modification is created with contexts.  The new &#x60;write:app-data:jira&#x60; OAuth scope is 100% optional now, and not using it won&#x27;t break your app. However, we recommend adding it to your app&#x27;s scope list because we will eventually make it mandatory.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.UiModificationsAppsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

UiModificationsAppsApi apiInstance = new UiModificationsAppsApi();
UpdateUiModificationDetails body = new UpdateUiModificationDetails(); // UpdateUiModificationDetails | Details of the UI modification.
String uiModificationId = "uiModificationId_example"; // String | The ID of the UI modification.
try {
    Object result = apiInstance.updateUiModification(body, uiModificationId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UiModificationsAppsApi#updateUiModification");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UpdateUiModificationDetails**](UpdateUiModificationDetails.md)| Details of the UI modification. |
 **uiModificationId** | **String**| The ID of the UI modification. |

### Return type

**Object**

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

