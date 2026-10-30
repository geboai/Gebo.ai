# OAuth2AdminControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteOauth2ProviderRegistration**](OAuth2AdminControllerApi.md#deleteOauth2ProviderRegistration) | **DELETE** /api/admin/OAuth2AdminController/deleteOauth2ProviderRegistration | 
[**findOauth2ProviderRegistrationByRegistrationId**](OAuth2AdminControllerApi.md#findOauth2ProviderRegistrationByRegistrationId) | **GET** /api/admin/OAuth2AdminController/findOauth2ProviderRegistrationByRegistrationId | 
[**getProviders**](OAuth2AdminControllerApi.md#getProviders) | **GET** /api/admin/OAuth2AdminController/getProviders | 
[**insertOauth2ProviderRegistration**](OAuth2AdminControllerApi.md#insertOauth2ProviderRegistration) | **POST** /api/admin/OAuth2AdminController/insertOauth2ProviderRegistration | 
[**updateOauth2ProviderRegistration**](OAuth2AdminControllerApi.md#updateOauth2ProviderRegistration) | **POST** /api/admin/OAuth2AdminController/updateOauth2ProviderRegistration | 

<a name="deleteOauth2ProviderRegistration"></a>
# **deleteOauth2ProviderRegistration**
> deleteOauth2ProviderRegistration(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OAuth2AdminControllerApi;


OAuth2AdminControllerApi apiInstance = new OAuth2AdminControllerApi();
Oauth2ProviderModifiableData body = new Oauth2ProviderModifiableData(); // Oauth2ProviderModifiableData | 
try {
    apiInstance.deleteOauth2ProviderRegistration(body);
} catch (ApiException e) {
    System.err.println("Exception when calling OAuth2AdminControllerApi#deleteOauth2ProviderRegistration");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Oauth2ProviderModifiableData**](Oauth2ProviderModifiableData.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="findOauth2ProviderRegistrationByRegistrationId"></a>
# **findOauth2ProviderRegistrationByRegistrationId**
> Oauth2ProviderModifiableData findOauth2ProviderRegistrationByRegistrationId(registrationId)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OAuth2AdminControllerApi;


OAuth2AdminControllerApi apiInstance = new OAuth2AdminControllerApi();
String registrationId = "registrationId_example"; // String | 
try {
    Oauth2ProviderModifiableData result = apiInstance.findOauth2ProviderRegistrationByRegistrationId(registrationId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OAuth2AdminControllerApi#findOauth2ProviderRegistrationByRegistrationId");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **registrationId** | **String**|  |

### Return type

[**Oauth2ProviderModifiableData**](Oauth2ProviderModifiableData.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getProviders"></a>
# **getProviders**
> List&lt;AuthProviderDto&gt; getProviders()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OAuth2AdminControllerApi;


OAuth2AdminControllerApi apiInstance = new OAuth2AdminControllerApi();
try {
    List<AuthProviderDto> result = apiInstance.getProviders();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OAuth2AdminControllerApi#getProviders");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;AuthProviderDto&gt;**](AuthProviderDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="insertOauth2ProviderRegistration"></a>
# **insertOauth2ProviderRegistration**
> Oauth2ProviderModifiableData insertOauth2ProviderRegistration(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OAuth2AdminControllerApi;


OAuth2AdminControllerApi apiInstance = new OAuth2AdminControllerApi();
Oauth2ProviderModifiableData body = new Oauth2ProviderModifiableData(); // Oauth2ProviderModifiableData | 
try {
    Oauth2ProviderModifiableData result = apiInstance.insertOauth2ProviderRegistration(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OAuth2AdminControllerApi#insertOauth2ProviderRegistration");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Oauth2ProviderModifiableData**](Oauth2ProviderModifiableData.md)|  |

### Return type

[**Oauth2ProviderModifiableData**](Oauth2ProviderModifiableData.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateOauth2ProviderRegistration"></a>
# **updateOauth2ProviderRegistration**
> Oauth2ProviderModifiableData updateOauth2ProviderRegistration(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.OAuth2AdminControllerApi;


OAuth2AdminControllerApi apiInstance = new OAuth2AdminControllerApi();
Oauth2ProviderModifiableData body = new Oauth2ProviderModifiableData(); // Oauth2ProviderModifiableData | 
try {
    Oauth2ProviderModifiableData result = apiInstance.updateOauth2ProviderRegistration(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OAuth2AdminControllerApi#updateOauth2ProviderRegistration");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Oauth2ProviderModifiableData**](Oauth2ProviderModifiableData.md)|  |

### Return type

[**Oauth2ProviderModifiableData**](Oauth2ProviderModifiableData.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

