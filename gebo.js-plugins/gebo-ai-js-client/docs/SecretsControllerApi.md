# GeboAiClient.SecretsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createAWSConnectionSecret**](SecretsControllerApi.md#createAWSConnectionSecret) | **POST** /api/admin/SecretsController/createAWSConnectionSecret | 
[**createCustomSecret**](SecretsControllerApi.md#createCustomSecret) | **POST** /api/admin/SecretsController/createCustomSecret | 
[**createGoogleJsonCredentialsSecret**](SecretsControllerApi.md#createGoogleJsonCredentialsSecret) | **POST** /api/admin/SecretsController/createGoogleJsonCredentialsSecret | 
[**createGoogleOauth2Secret**](SecretsControllerApi.md#createGoogleOauth2Secret) | **POST** /api/admin/SecretsController/createGoogleOauth2Secret | 
[**createOauth2StandardSecret**](SecretsControllerApi.md#createOauth2StandardSecret) | **POST** /api/admin/SecretsController/createOauth2StandardSecret | 
[**createSshKeySecret**](SecretsControllerApi.md#createSshKeySecret) | **POST** /api/admin/SecretsController/createSshKeySecret | 
[**createTokenSecret**](SecretsControllerApi.md#createTokenSecret) | **POST** /api/admin/SecretsController/createTokenSecret | 
[**createUsernamePasswordSecret**](SecretsControllerApi.md#createUsernamePasswordSecret) | **POST** /api/admin/SecretsController/createUsernamePasswordSecret | 
[**deleteSecret**](SecretsControllerApi.md#deleteSecret) | **DELETE** /api/admin/SecretsController/deleteSecret | 
[**getSecretsByContextCode**](SecretsControllerApi.md#getSecretsByContextCode) | **GET** /api/admin/SecretsController/getSecretsByContextCode | 

<a name="createAWSConnectionSecret"></a>
# **createAWSConnectionSecret**
> SecretInfo createAWSConnectionSecret(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SecretsControllerApi();
let body = new GeboAiClient.SecretWrapperGeboAwsConnectionCredentials(); // SecretWrapperGeboAwsConnectionCredentials | 

apiInstance.createAWSConnectionSecret(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SecretWrapperGeboAwsConnectionCredentials**](SecretWrapperGeboAwsConnectionCredentials.md)|  | 

### Return type

[**SecretInfo**](SecretInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createCustomSecret"></a>
# **createCustomSecret**
> SecretInfo createCustomSecret(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SecretsControllerApi();
let body = new GeboAiClient.SecretWrapperGeboCustomSecretContent(); // SecretWrapperGeboCustomSecretContent | 

apiInstance.createCustomSecret(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SecretWrapperGeboCustomSecretContent**](SecretWrapperGeboCustomSecretContent.md)|  | 

### Return type

[**SecretInfo**](SecretInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createGoogleJsonCredentialsSecret"></a>
# **createGoogleJsonCredentialsSecret**
> SecretInfo createGoogleJsonCredentialsSecret(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SecretsControllerApi();
let body = new GeboAiClient.SecretWrapperGeboGoogleJsonSecretContent(); // SecretWrapperGeboGoogleJsonSecretContent | 

apiInstance.createGoogleJsonCredentialsSecret(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SecretWrapperGeboGoogleJsonSecretContent**](SecretWrapperGeboGoogleJsonSecretContent.md)|  | 

### Return type

[**SecretInfo**](SecretInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createGoogleOauth2Secret"></a>
# **createGoogleOauth2Secret**
> SecretInfo createGoogleOauth2Secret(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SecretsControllerApi();
let body = new GeboAiClient.SecretWrapperGeboGoogleOauth2SecretContent(); // SecretWrapperGeboGoogleOauth2SecretContent | 

apiInstance.createGoogleOauth2Secret(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SecretWrapperGeboGoogleOauth2SecretContent**](SecretWrapperGeboGoogleOauth2SecretContent.md)|  | 

### Return type

[**SecretInfo**](SecretInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createOauth2StandardSecret"></a>
# **createOauth2StandardSecret**
> SecretInfo createOauth2StandardSecret(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SecretsControllerApi();
let body = new GeboAiClient.SecretWrapperGeboOauth2SecretContent(); // SecretWrapperGeboOauth2SecretContent | 

apiInstance.createOauth2StandardSecret(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SecretWrapperGeboOauth2SecretContent**](SecretWrapperGeboOauth2SecretContent.md)|  | 

### Return type

[**SecretInfo**](SecretInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createSshKeySecret"></a>
# **createSshKeySecret**
> SecretInfo createSshKeySecret(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SecretsControllerApi();
let body = new GeboAiClient.SecretWrapperGeboSshKeySecretContent(); // SecretWrapperGeboSshKeySecretContent | 

apiInstance.createSshKeySecret(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SecretWrapperGeboSshKeySecretContent**](SecretWrapperGeboSshKeySecretContent.md)|  | 

### Return type

[**SecretInfo**](SecretInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createTokenSecret"></a>
# **createTokenSecret**
> SecretInfo createTokenSecret(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SecretsControllerApi();
let body = new GeboAiClient.SecretWrapperGeboTokenContent(); // SecretWrapperGeboTokenContent | 

apiInstance.createTokenSecret(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SecretWrapperGeboTokenContent**](SecretWrapperGeboTokenContent.md)|  | 

### Return type

[**SecretInfo**](SecretInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createUsernamePasswordSecret"></a>
# **createUsernamePasswordSecret**
> SecretInfo createUsernamePasswordSecret(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SecretsControllerApi();
let body = new GeboAiClient.SecretWrapperGeboUsernamePasswordContent(); // SecretWrapperGeboUsernamePasswordContent | 

apiInstance.createUsernamePasswordSecret(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SecretWrapperGeboUsernamePasswordContent**](SecretWrapperGeboUsernamePasswordContent.md)|  | 

### Return type

[**SecretInfo**](SecretInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deleteSecret"></a>
# **deleteSecret**
> deleteSecret(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SecretsControllerApi();
let body = new GeboAiClient.SecretInfo(); // SecretInfo | 

apiInstance.deleteSecret(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SecretInfo**](SecretInfo.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="getSecretsByContextCode"></a>
# **getSecretsByContextCode**
> [SecretInfo] getSecretsByContextCode(context)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SecretsControllerApi();
let context = "context_example"; // String | 

apiInstance.getSecretsByContextCode(context).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **context** | **String**|  | 

### Return type

[**[SecretInfo]**](SecretInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

