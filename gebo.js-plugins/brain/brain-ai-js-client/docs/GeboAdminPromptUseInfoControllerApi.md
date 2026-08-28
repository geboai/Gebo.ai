# BrainClient.GeboAdminPromptUseInfoControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**findAll**](GeboAdminPromptUseInfoControllerApi.md#findAll) | **GET** /api/admin/GeboAdminPromptUseController/findAll | 
[**findByCode**](GeboAdminPromptUseInfoControllerApi.md#findByCode) | **GET** /api/admin/GeboAdminPromptUseController/findByCode | 
[**findByModule**](GeboAdminPromptUseInfoControllerApi.md#findByModule) | **GET** /api/admin/GeboAdminPromptUseController/findByModule | 

<a name="findAll"></a>
# **findAll**
> Object findAll()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboAdminPromptUseInfoControllerApi();
apiInstance.findAll().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

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

<a name="findByCode"></a>
# **findByCode**
> GPromptUseInfo findByCode(code)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboAdminPromptUseInfoControllerApi();
let code = null; // Object | 

apiInstance.findByCode(code).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | [**Object**](.md)|  | 

### Return type

[**GPromptUseInfo**](GPromptUseInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findByModule"></a>
# **findByModule**
> Object findByModule(module)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboAdminPromptUseInfoControllerApi();
let module = null; // Object | 

apiInstance.findByModule(module).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **module** | [**Object**](.md)|  | 

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

