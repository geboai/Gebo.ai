# GeboAiClient.GeneratedUserApiKeyControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteUserGeneratedApiKey**](GeneratedUserApiKeyControllerApi.md#deleteUserGeneratedApiKey) | **POST** /api/users/GeneratedUserApiKeyController/deleteUserGeneratedApiKey | 
[**generateUserGeneratedApiKey**](GeneratedUserApiKeyControllerApi.md#generateUserGeneratedApiKey) | **POST** /api/users/GeneratedUserApiKeyController/generateUserGeneratedApiKey | 
[**getUserGeneratedApiKeyPagedList**](GeneratedUserApiKeyControllerApi.md#getUserGeneratedApiKeyPagedList) | **POST** /api/users/GeneratedUserApiKeyController/getUserGeneratedApiKeyPagedList | 
[**isUserGeneratedApiKeyGenerationAllowed**](GeneratedUserApiKeyControllerApi.md#isUserGeneratedApiKeyGenerationAllowed) | **GET** /api/users/GeneratedUserApiKeyController/isUserGeneratedApiKeyGenerationAllowed | 

<a name="deleteUserGeneratedApiKey"></a>
# **deleteUserGeneratedApiKey**
> deleteUserGeneratedApiKey(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeneratedUserApiKeyControllerApi();
let code = "code_example"; // String | 

apiInstance.deleteUserGeneratedApiKey(code).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="generateUserGeneratedApiKey"></a>
# **generateUserGeneratedApiKey**
> GeneratedApiKey generateUserGeneratedApiKey(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeneratedUserApiKeyControllerApi();
let body = new GeboAiClient.GenerateUserGeneratedApiKeyParam(); // GenerateUserGeneratedApiKeyParam | 

apiInstance.generateUserGeneratedApiKey(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenerateUserGeneratedApiKeyParam**](GenerateUserGeneratedApiKeyParam.md)|  | 

### Return type

[**GeneratedApiKey**](GeneratedApiKey.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getUserGeneratedApiKeyPagedList"></a>
# **getUserGeneratedApiKeyPagedList**
> PagedModelGeneratedApiKeyInfo getUserGeneratedApiKeyPagedList(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeneratedUserApiKeyControllerApi();
let body = new GeboAiClient.DataPage(); // DataPage | 

apiInstance.getUserGeneratedApiKeyPagedList(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**DataPage**](DataPage.md)|  | 

### Return type

[**PagedModelGeneratedApiKeyInfo**](PagedModelGeneratedApiKeyInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="isUserGeneratedApiKeyGenerationAllowed"></a>
# **isUserGeneratedApiKeyGenerationAllowed**
> &#x27;Boolean&#x27; isUserGeneratedApiKeyGenerationAllowed()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeneratedUserApiKeyControllerApi();
apiInstance.isUserGeneratedApiKeyGenerationAllowed().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

**&#x27;Boolean&#x27;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

