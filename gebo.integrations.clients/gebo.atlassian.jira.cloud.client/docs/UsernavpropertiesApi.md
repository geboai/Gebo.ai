# UsernavpropertiesApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getUserNavProperty**](UsernavpropertiesApi.md#getUserNavProperty) | **GET** /rest/api/3/user/nav4-opt-property/{propertyKey} | Get user nav property
[**setUserNavProperty**](UsernavpropertiesApi.md#setUserNavProperty) | **PUT** /rest/api/3/user/nav4-opt-property/{propertyKey} | Set user nav property

<a name="getUserNavProperty"></a>
# **getUserNavProperty**
> UserNavPropertyJsonBean getUserNavProperty(propertyKey, accountId)

Get user nav property

Returns the value of a user nav preference.  Note: This operation fetches the property key value directly from RbacClient.  **[Permissions](#permissions) required:**   *  *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg), to get a property from any user.  *  Access to Jira, to get a property from the calling user&#x27;s record.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.UsernavpropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

UsernavpropertiesApi apiInstance = new UsernavpropertiesApi();
String propertyKey = "propertyKey_example"; // String | The key of the user's property.
String accountId = "accountId_example"; // String | The account ID of the user, which uniquely identifies the user across all Atlassian products. For example, *5b10ac8d82e05b22cc7d4ef5*.
try {
    UserNavPropertyJsonBean result = apiInstance.getUserNavProperty(propertyKey, accountId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsernavpropertiesApi#getUserNavProperty");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **propertyKey** | **String**| The key of the user&#x27;s property. |
 **accountId** | **String**| The account ID of the user, which uniquely identifies the user across all Atlassian products. For example, *5b10ac8d82e05b22cc7d4ef5*. | [optional]

### Return type

[**UserNavPropertyJsonBean**](UserNavPropertyJsonBean.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="setUserNavProperty"></a>
# **setUserNavProperty**
> Object setUserNavProperty(body, propertyKey, accountId)

Set user nav property

Sets the value of a Nav4 preference. Use this resource to store Nav4 preference data against a user in the Identity service.  **[Permissions](#permissions) required:**   *  *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg), to set a property on any user.  *  Access to Jira, to set a property on the calling user&#x27;s record.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.UsernavpropertiesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

UsernavpropertiesApi apiInstance = new UsernavpropertiesApi();
Object body = null; // Object | The value of the property. The value has to be a boolean [JSON](https://tools.ietf.org/html/rfc4627) value. The maximum length of the property value is 32768 bytes.
String propertyKey = "propertyKey_example"; // String | The key of the nav property. The maximum length is 255 characters.
String accountId = "accountId_example"; // String | The account ID of the user, which uniquely identifies the user across all Atlassian products. For example, *5b10ac8d82e05b22cc7d4ef5*.
try {
    Object result = apiInstance.setUserNavProperty(body, propertyKey, accountId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UsernavpropertiesApi#setUserNavProperty");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Object**](Object.md)| The value of the property. The value has to be a boolean [JSON](https://tools.ietf.org/html/rfc4627) value. The maximum length of the property value is 32768 bytes. |
 **propertyKey** | **String**| The key of the nav property. The maximum length is 255 characters. |
 **accountId** | **String**| The account ID of the user, which uniquely identifies the user across all Atlassian products. For example, *5b10ac8d82e05b22cc7d4ef5*. | [optional]

### Return type

**Object**

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

