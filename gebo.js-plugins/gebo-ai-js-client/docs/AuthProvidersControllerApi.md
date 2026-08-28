# GeboAiClient.AuthProvidersControllerApi

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
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.AuthProvidersControllerApi();
let registrationId = "registrationId_example"; // String | 

apiInstance.getProviderClientConfig(registrationId).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

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
> [AuthProviderDto] listAuthProviders()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.AuthProvidersControllerApi();
apiInstance.listAuthProviders().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[AuthProviderDto]**](AuthProviderDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="listAvailableProvidersConfig"></a>
# **listAvailableProvidersConfig**
> [Oauth2ClientAuthorizativeInfo] listAvailableProvidersConfig()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.AuthProvidersControllerApi();
apiInstance.listAvailableProvidersConfig().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[Oauth2ClientAuthorizativeInfo]**](Oauth2ClientAuthorizativeInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

