# GeboAiClient.A2AClientConfigControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**delete1**](A2AClientConfigControllerApi.md#delete1) | **DELETE** /api/admin/A2AClientConfigController/deleteA2AAgent | 
[**findByCode2**](A2AClientConfigControllerApi.md#findByCode2) | **GET** /api/admin/A2AClientConfigController/findByCode | 
[**insert1**](A2AClientConfigControllerApi.md#insert1) | **POST** /api/admin/A2AClientConfigController/insertA2AAgent | 
[**list**](A2AClientConfigControllerApi.md#list) | **GET** /api/admin/A2AClientConfigController/list | 
[**testAndDiscovery1**](A2AClientConfigControllerApi.md#testAndDiscovery1) | **POST** /api/admin/A2AClientConfigController/testAndDiscovery | 
[**update2**](A2AClientConfigControllerApi.md#update2) | **POST** /api/admin/A2AClientConfigController/updateA2AAgent | 

<a name="delete1"></a>
# **delete1**
> OperationStatusBoolean delete1(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.A2AClientConfigControllerApi();
let body = new GeboAiClient.A2ARemoteAgentConfig(); // A2ARemoteAgentConfig | 

apiInstance.delete1(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**A2ARemoteAgentConfig**](A2ARemoteAgentConfig.md)|  | 

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findByCode2"></a>
# **findByCode2**
> OperationStatusA2ARemoteAgentConfig findByCode2(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.A2AClientConfigControllerApi();
let code = "code_example"; // String | 

apiInstance.findByCode2(code).then((data) => {
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

[**OperationStatusA2ARemoteAgentConfig**](OperationStatusA2ARemoteAgentConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="insert1"></a>
# **insert1**
> OperationStatusA2ARemoteAgentConfig insert1(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.A2AClientConfigControllerApi();
let body = new GeboAiClient.A2ARemoteAgentConfig(); // A2ARemoteAgentConfig | 

apiInstance.insert1(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**A2ARemoteAgentConfig**](A2ARemoteAgentConfig.md)|  | 

### Return type

[**OperationStatusA2ARemoteAgentConfig**](OperationStatusA2ARemoteAgentConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="list"></a>
# **list**
> PagedModelA2ARemoteAgentConfig list(opts)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.A2AClientConfigControllerApi();
let opts = { 
  'page': 0, // Number | 
  'size': 20 // Number | 
};
apiInstance.list(opts).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **page** | **Number**|  | [optional] [default to 0]
 **size** | **Number**|  | [optional] [default to 20]

### Return type

[**PagedModelA2ARemoteAgentConfig**](PagedModelA2ARemoteAgentConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="testAndDiscovery1"></a>
# **testAndDiscovery1**
> OperationStatusA2ARemoteAgentConfig testAndDiscovery1(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.A2AClientConfigControllerApi();
let body = new GeboAiClient.A2ARemoteAgentConfig(); // A2ARemoteAgentConfig | 

apiInstance.testAndDiscovery1(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**A2ARemoteAgentConfig**](A2ARemoteAgentConfig.md)|  | 

### Return type

[**OperationStatusA2ARemoteAgentConfig**](OperationStatusA2ARemoteAgentConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="update2"></a>
# **update2**
> OperationStatusA2ARemoteAgentConfig update2(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.A2AClientConfigControllerApi();
let body = new GeboAiClient.A2ARemoteAgentConfig(); // A2ARemoteAgentConfig | 

apiInstance.update2(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**A2ARemoteAgentConfig**](A2ARemoteAgentConfig.md)|  | 

### Return type

[**OperationStatusA2ARemoteAgentConfig**](OperationStatusA2ARemoteAgentConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

