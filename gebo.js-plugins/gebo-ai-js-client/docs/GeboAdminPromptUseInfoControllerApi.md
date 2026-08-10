# GeboAiClient.GeboAdminPromptUseInfoControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**findAll**](GeboAdminPromptUseInfoControllerApi.md#findAll) | **GET** /api/admin/GeboAdminPromptUseController/findAll | 
[**findByCode**](GeboAdminPromptUseInfoControllerApi.md#findByCode) | **GET** /api/admin/GeboAdminPromptUseController/findByCode | 
[**findByModule**](GeboAdminPromptUseInfoControllerApi.md#findByModule) | **GET** /api/admin/GeboAdminPromptUseController/findByModule | 

<a name="findAll"></a>
# **findAll**
> [GPromptUseInfo] findAll()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboAdminPromptUseInfoControllerApi();
apiInstance.findAll().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GPromptUseInfo]**](GPromptUseInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findByCode"></a>
# **findByCode**
> GPromptUseInfo findByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboAdminPromptUseInfoControllerApi();
let code = "code_example"; // String | 

apiInstance.findByCode(code).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  | 

### Return type

[**GPromptUseInfo**](GPromptUseInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findByModule"></a>
# **findByModule**
> [GPromptUseInfo] findByModule(module)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboAdminPromptUseInfoControllerApi();
let module = "module_example"; // String | 

apiInstance.findByModule(module).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **module** | **String**|  | 

### Return type

[**[GPromptUseInfo]**](GPromptUseInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

