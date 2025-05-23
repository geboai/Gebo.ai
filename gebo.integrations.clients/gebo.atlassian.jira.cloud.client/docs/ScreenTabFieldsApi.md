# ScreenTabFieldsApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addScreenTabField**](ScreenTabFieldsApi.md#addScreenTabField) | **POST** /rest/api/3/screens/{screenId}/tabs/{tabId}/fields | Add screen tab field
[**getAllScreenTabFields**](ScreenTabFieldsApi.md#getAllScreenTabFields) | **GET** /rest/api/3/screens/{screenId}/tabs/{tabId}/fields | Get all screen tab fields
[**moveScreenTabField**](ScreenTabFieldsApi.md#moveScreenTabField) | **POST** /rest/api/3/screens/{screenId}/tabs/{tabId}/fields/{id}/move | Move screen tab field
[**removeScreenTabField**](ScreenTabFieldsApi.md#removeScreenTabField) | **DELETE** /rest/api/3/screens/{screenId}/tabs/{tabId}/fields/{id} | Remove screen tab field

<a name="addScreenTabField"></a>
# **addScreenTabField**
> ScreenableField addScreenTabField(body, screenId, tabId)

Add screen tab field

Adds a field to a screen tab.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.ScreenTabFieldsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ScreenTabFieldsApi apiInstance = new ScreenTabFieldsApi();
AddFieldBean body = new AddFieldBean(); // AddFieldBean | 
Long screenId = 789L; // Long | The ID of the screen.
Long tabId = 789L; // Long | The ID of the screen tab.
try {
    ScreenableField result = apiInstance.addScreenTabField(body, screenId, tabId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ScreenTabFieldsApi#addScreenTabField");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**AddFieldBean**](AddFieldBean.md)|  |
 **screenId** | **Long**| The ID of the screen. |
 **tabId** | **Long**| The ID of the screen tab. |

### Return type

[**ScreenableField**](ScreenableField.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getAllScreenTabFields"></a>
# **getAllScreenTabFields**
> List&lt;ScreenableField&gt; getAllScreenTabFields(screenId, tabId, projectKey)

Get all screen tab fields

Returns all fields for a screen tab.  **[Permissions](#permissions) required:**   *  *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).  *  *Administer projects* [project permission](https://confluence.atlassian.com/x/yodKLg) when the project key is specified, providing that the screen is associated with the project through a Screen Scheme and Issue Type Screen Scheme.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.ScreenTabFieldsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ScreenTabFieldsApi apiInstance = new ScreenTabFieldsApi();
Long screenId = 789L; // Long | The ID of the screen.
Long tabId = 789L; // Long | The ID of the screen tab.
String projectKey = "projectKey_example"; // String | The key of the project.
try {
    List<ScreenableField> result = apiInstance.getAllScreenTabFields(screenId, tabId, projectKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ScreenTabFieldsApi#getAllScreenTabFields");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **screenId** | **Long**| The ID of the screen. |
 **tabId** | **Long**| The ID of the screen tab. |
 **projectKey** | **String**| The key of the project. | [optional]

### Return type

[**List&lt;ScreenableField&gt;**](ScreenableField.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="moveScreenTabField"></a>
# **moveScreenTabField**
> Object moveScreenTabField(body, screenId, tabId, id)

Move screen tab field

Moves a screen tab field.  If &#x60;after&#x60; and &#x60;position&#x60; are provided in the request, &#x60;position&#x60; is ignored.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.ScreenTabFieldsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ScreenTabFieldsApi apiInstance = new ScreenTabFieldsApi();
MoveFieldBean body = new MoveFieldBean(); // MoveFieldBean | 
Long screenId = 789L; // Long | The ID of the screen.
Long tabId = 789L; // Long | The ID of the screen tab.
String id = "id_example"; // String | The ID of the field.
try {
    Object result = apiInstance.moveScreenTabField(body, screenId, tabId, id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ScreenTabFieldsApi#moveScreenTabField");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**MoveFieldBean**](MoveFieldBean.md)|  |
 **screenId** | **Long**| The ID of the screen. |
 **tabId** | **Long**| The ID of the screen tab. |
 **id** | **String**| The ID of the field. |

### Return type

**Object**

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="removeScreenTabField"></a>
# **removeScreenTabField**
> removeScreenTabField(screenId, tabId, id)

Remove screen tab field

Removes a field from a screen tab.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.ScreenTabFieldsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ScreenTabFieldsApi apiInstance = new ScreenTabFieldsApi();
Long screenId = 789L; // Long | The ID of the screen.
Long tabId = 789L; // Long | The ID of the screen tab.
String id = "id_example"; // String | The ID of the field.
try {
    apiInstance.removeScreenTabField(screenId, tabId, id);
} catch (ApiException e) {
    System.err.println("Exception when calling ScreenTabFieldsApi#removeScreenTabField");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **screenId** | **Long**| The ID of the screen. |
 **tabId** | **Long**| The ID of the screen tab. |
 **id** | **String**| The ID of the field. |

### Return type

null (empty response body)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

