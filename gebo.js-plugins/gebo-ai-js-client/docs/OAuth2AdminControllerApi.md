# GeboAiClient.OAuth2AdminControllerApi

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
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.OAuth2AdminControllerApi();
let body = new GeboAiClient.Oauth2ProviderModifiableData(); // Oauth2ProviderModifiableData | 

apiInstance.deleteOauth2ProviderRegistration(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

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
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.OAuth2AdminControllerApi();
let registrationId = "registrationId_example"; // String | 

apiInstance.findOauth2ProviderRegistrationByRegistrationId(registrationId).then((data) => {
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

[**Oauth2ProviderModifiableData**](Oauth2ProviderModifiableData.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getProviders"></a>
# **getProviders**
> [AuthProviderDto] getProviders()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.OAuth2AdminControllerApi();
apiInstance.getProviders().then((data) => {
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

<a name="insertOauth2ProviderRegistration"></a>
# **insertOauth2ProviderRegistration**
> Oauth2ProviderModifiableData insertOauth2ProviderRegistration(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.OAuth2AdminControllerApi();
let body = new GeboAiClient.Oauth2ProviderModifiableData(); // Oauth2ProviderModifiableData | 

apiInstance.insertOauth2ProviderRegistration(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

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
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.OAuth2AdminControllerApi();
let body = new GeboAiClient.Oauth2ProviderModifiableData(); // Oauth2ProviderModifiableData | 

apiInstance.updateOauth2ProviderRegistration(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

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

