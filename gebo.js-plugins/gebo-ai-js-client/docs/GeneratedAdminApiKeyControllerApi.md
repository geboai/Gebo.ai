# GeboAiClient.GeneratedAdminApiKeyControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteAdminGeneratedApiKey**](GeneratedAdminApiKeyControllerApi.md#deleteAdminGeneratedApiKey) | **POST** /api/admin/GeneratedAdminApiKeyController/deleteAdminGeneratedApiKey | 
[**generateAdminGeneratedApiKey**](GeneratedAdminApiKeyControllerApi.md#generateAdminGeneratedApiKey) | **POST** /api/admin/GeneratedAdminApiKeyController/generateAdminGeneratedApiKey | 
[**getAdminGeneratedApiKeyPagedList**](GeneratedAdminApiKeyControllerApi.md#getAdminGeneratedApiKeyPagedList) | **POST** /api/admin/GeneratedAdminApiKeyController/getAdminGeneratedApiKeyPagedList | 
[**isAdminGeneratedApiKeyGenerationAllowed**](GeneratedAdminApiKeyControllerApi.md#isAdminGeneratedApiKeyGenerationAllowed) | **GET** /api/admin/GeneratedAdminApiKeyController/isAdminGeneratedApiKeyGenerationAllowed | 

<a name="deleteAdminGeneratedApiKey"></a>
# **deleteAdminGeneratedApiKey**
> deleteAdminGeneratedApiKey(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeneratedAdminApiKeyControllerApi();
let code = "code_example"; // String | 

apiInstance.deleteAdminGeneratedApiKey(code).then(() => {
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

<a name="generateAdminGeneratedApiKey"></a>
# **generateAdminGeneratedApiKey**
> GeneratedApiKey generateAdminGeneratedApiKey(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeneratedAdminApiKeyControllerApi();
let body = new GeboAiClient.GenerateAdminGeneratedApiKeyParam(); // GenerateAdminGeneratedApiKeyParam | 

apiInstance.generateAdminGeneratedApiKey(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GenerateAdminGeneratedApiKeyParam**](GenerateAdminGeneratedApiKeyParam.md)|  | 

### Return type

[**GeneratedApiKey**](GeneratedApiKey.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getAdminGeneratedApiKeyPagedList"></a>
# **getAdminGeneratedApiKeyPagedList**
> PagedModelGeneratedApiKeyInfo getAdminGeneratedApiKeyPagedList(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeneratedAdminApiKeyControllerApi();
let body = new GeboAiClient.DataPage(); // DataPage | 

apiInstance.getAdminGeneratedApiKeyPagedList(body).then((data) => {
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

<a name="isAdminGeneratedApiKeyGenerationAllowed"></a>
# **isAdminGeneratedApiKeyGenerationAllowed**
> &#x27;Boolean&#x27; isAdminGeneratedApiKeyGenerationAllowed()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeneratedAdminApiKeyControllerApi();
apiInstance.isAdminGeneratedApiKeyGenerationAllowed().then((data) => {
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

