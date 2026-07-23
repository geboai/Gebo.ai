# AuthProvidersControllerApi

All URIs are relative to *http://localhost:13018/heimdall*

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
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.AuthProvidersControllerApi;


AuthProvidersControllerApi apiInstance = new AuthProvidersControllerApi();
Object registrationId = null; // Object | 
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
 **registrationId** | [**Object**](.md)|  |

### Return type

[**Oauth2ClientConfig**](Oauth2ClientConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="listAuthProviders"></a>
# **listAuthProviders**
> Object listAuthProviders()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.AuthProvidersControllerApi;


AuthProvidersControllerApi apiInstance = new AuthProvidersControllerApi();
try {
    Object result = apiInstance.listAuthProviders();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AuthProvidersControllerApi#listAuthProviders");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="listAvailableProvidersConfig"></a>
# **listAvailableProvidersConfig**
> Object listAvailableProvidersConfig()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.AuthProvidersControllerApi;


AuthProvidersControllerApi apiInstance = new AuthProvidersControllerApi();
try {
    Object result = apiInstance.listAvailableProvidersConfig();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AuthProvidersControllerApi#listAvailableProvidersConfig");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

