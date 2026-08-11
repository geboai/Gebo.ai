# BrainClient.McpClientConfigControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteMCPClientConfig**](McpClientConfigControllerApi.md#deleteMCPClientConfig) | **DELETE** /api/admin/McpClientConfigController/deleteMCPClientConfig | 
[**findMCPClientConfigByCode**](McpClientConfigControllerApi.md#findMCPClientConfigByCode) | **GET** /api/admin/McpClientConfigController/findMCPClientConfigByCode | 
[**findMCPClientConfigByQbe**](McpClientConfigControllerApi.md#findMCPClientConfigByQbe) | **POST** /api/admin/McpClientConfigController/findMCPClientConfigByQbe | 
[**insertMCPClientConfig**](McpClientConfigControllerApi.md#insertMCPClientConfig) | **POST** /api/admin/McpClientConfigController/insertMCPClientConfig | 
[**listMCPClientConfig**](McpClientConfigControllerApi.md#listMCPClientConfig) | **POST** /api/admin/McpClientConfigController/listMCPClientConfig | 
[**testAndDiscovery**](McpClientConfigControllerApi.md#testAndDiscovery) | **POST** /api/admin/McpClientConfigController/testAndDiscovery | 
[**updateMCPClientConfig**](McpClientConfigControllerApi.md#updateMCPClientConfig) | **POST** /api/admin/McpClientConfigController/updateMCPClientConfig | 

<a name="deleteMCPClientConfig"></a>
# **deleteMCPClientConfig**
> OperationStatusBoolean deleteMCPClientConfig(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.McpClientConfigControllerApi();
let body = new BrainClient.MCPClientConfig(); // MCPClientConfig | 

apiInstance.deleteMCPClientConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**MCPClientConfig**](MCPClientConfig.md)|  | 

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findMCPClientConfigByCode"></a>
# **findMCPClientConfigByCode**
> OperationStatusMCPClientConfig findMCPClientConfigByCode(code)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.McpClientConfigControllerApi();
let code = null; // Object | 

apiInstance.findMCPClientConfigByCode(code).then((data) => {
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

[**OperationStatusMCPClientConfig**](OperationStatusMCPClientConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findMCPClientConfigByQbe"></a>
# **findMCPClientConfigByQbe**
> PageMCPClientConfig findMCPClientConfigByQbe(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.McpClientConfigControllerApi();
let body = new BrainClient.FindByMCPClientConfigQbeParam(); // FindByMCPClientConfigQbeParam | 

apiInstance.findMCPClientConfigByQbe(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**FindByMCPClientConfigQbeParam**](FindByMCPClientConfigQbeParam.md)|  | 

### Return type

[**PageMCPClientConfig**](PageMCPClientConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertMCPClientConfig"></a>
# **insertMCPClientConfig**
> OperationStatusMCPClientConfig insertMCPClientConfig(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.McpClientConfigControllerApi();
let body = new BrainClient.MCPClientConfig(); // MCPClientConfig | 

apiInstance.insertMCPClientConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**MCPClientConfig**](MCPClientConfig.md)|  | 

### Return type

[**OperationStatusMCPClientConfig**](OperationStatusMCPClientConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="listMCPClientConfig"></a>
# **listMCPClientConfig**
> PageMCPClientConfig listMCPClientConfig(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.McpClientConfigControllerApi();
let body = new BrainClient.DataPage(); // DataPage | 

apiInstance.listMCPClientConfig(body).then((data) => {
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

[**PageMCPClientConfig**](PageMCPClientConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="testAndDiscovery"></a>
# **testAndDiscovery**
> OperationStatusMCPClientConfig testAndDiscovery(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.McpClientConfigControllerApi();
let body = new BrainClient.MCPClientConfig(); // MCPClientConfig | 

apiInstance.testAndDiscovery(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**MCPClientConfig**](MCPClientConfig.md)|  | 

### Return type

[**OperationStatusMCPClientConfig**](OperationStatusMCPClientConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateMCPClientConfig"></a>
# **updateMCPClientConfig**
> OperationStatusMCPClientConfig updateMCPClientConfig(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.McpClientConfigControllerApi();
let body = new BrainClient.MCPClientConfig(); // MCPClientConfig | 

apiInstance.updateMCPClientConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**MCPClientConfig**](MCPClientConfig.md)|  | 

### Return type

[**OperationStatusMCPClientConfig**](OperationStatusMCPClientConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

