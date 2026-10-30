# AuthProvidersControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getProviderClientConfig**](AuthProvidersControllerApi.md#getProviderClientConfig) | **GET** /public/AuthProvidersController/getProviderClientConfig | 
[**listAuthProviders**](AuthProvidersControllerApi.md#listAuthProviders) | **GET** /public/AuthProvidersController/listAuthProviders | 
[**listAvailableProvidersConfig**](AuthProvidersControllerApi.md#listAvailableProvidersConfig) | **GET** /public/AuthProvidersController/listAvailableProvidersConfig | 

<a name="getProviderClientConfig"></a>
# **getProviderClientConfig**
> Oauth2ClientConfig getProviderClientConfig(registrationId)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.AuthProvidersControllerApi;


AuthProvidersControllerApi apiInstance = new AuthProvidersControllerApi();
String registrationId = "registrationId_example"; // String | 
try {
    Oauth2ClientConfig result = apiInstance.getProviderClientConfig(registrationId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AuthProvidersControllerApi#getProviderClientConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **registrationId** | **String**|  |

### Return type

[**Oauth2ClientConfig**](Oauth2ClientConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="listAuthProviders"></a>
# **listAuthProviders**
> List&lt;AuthProviderDto&gt; listAuthProviders()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.AuthProvidersControllerApi;


AuthProvidersControllerApi apiInstance = new AuthProvidersControllerApi();
try {
    List<AuthProviderDto> result = apiInstance.listAuthProviders();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AuthProvidersControllerApi#listAuthProviders");
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

<a name="listAvailableProvidersConfig"></a>
# **listAvailableProvidersConfig**
> List&lt;Oauth2ClientAuthorizativeInfo&gt; listAvailableProvidersConfig()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.AuthProvidersControllerApi;


AuthProvidersControllerApi apiInstance = new AuthProvidersControllerApi();
try {
    List<Oauth2ClientAuthorizativeInfo> result = apiInstance.listAvailableProvidersConfig();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AuthProvidersControllerApi#listAvailableProvidersConfig");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;Oauth2ClientAuthorizativeInfo&gt;**](Oauth2ClientAuthorizativeInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

