# GeboAiClient.GeboA2AServerAdminControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**callDelete**](GeboA2AServerAdminControllerApi.md#callDelete) | **DELETE** /api/admin/GeboA2AServerAdminController/deleteA2AServer | 
[**findAll1**](GeboA2AServerAdminControllerApi.md#findAll1) | **GET** /api/admin/GeboA2AServerAdminController/findAll | 
[**findByCode1**](GeboA2AServerAdminControllerApi.md#findByCode1) | **GET** /api/admin/GeboA2AServerAdminController/findByCode | 
[**insert**](GeboA2AServerAdminControllerApi.md#insert) | **POST** /api/admin/GeboA2AServerAdminController/insertA2AServer | 
[**update1**](GeboA2AServerAdminControllerApi.md#update1) | **POST** /api/admin/GeboA2AServerAdminController/updateA2AServer | 

<a name="callDelete"></a>
# **callDelete**
> OperationStatusBoolean callDelete(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboA2AServerAdminControllerApi();
let code = "code_example"; // String | 

apiInstance.callDelete(code).then((data) => {
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

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findAll1"></a>
# **findAll1**
> [A2AServerConfig] findAll1()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboA2AServerAdminControllerApi();
apiInstance.findAll1().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[A2AServerConfig]**](A2AServerConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findByCode1"></a>
# **findByCode1**
> OperationStatusA2AServerConfig findByCode1(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboA2AServerAdminControllerApi();
let code = "code_example"; // String | 

apiInstance.findByCode1(code).then((data) => {
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

[**OperationStatusA2AServerConfig**](OperationStatusA2AServerConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="insert"></a>
# **insert**
> OperationStatusA2AServerConfig insert(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboA2AServerAdminControllerApi();
let body = new GeboAiClient.A2AServerConfig(); // A2AServerConfig | 

apiInstance.insert(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**A2AServerConfig**](A2AServerConfig.md)|  | 

### Return type

[**OperationStatusA2AServerConfig**](OperationStatusA2AServerConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="update1"></a>
# **update1**
> OperationStatusA2AServerConfig update1(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboA2AServerAdminControllerApi();
let body = new GeboAiClient.A2AServerConfig(); // A2AServerConfig | 

apiInstance.update1(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**A2AServerConfig**](A2AServerConfig.md)|  | 

### Return type

[**OperationStatusA2AServerConfig**](OperationStatusA2AServerConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

