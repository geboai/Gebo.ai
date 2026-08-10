# GeboAiClient.GeboMcpServerAdminControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteMcpServer**](GeboMcpServerAdminControllerApi.md#deleteMcpServer) | **POST** /api/admin/GeboMCPServerAdminController/deleteMcpServer | 
[**findMcpServerByCode**](GeboMcpServerAdminControllerApi.md#findMcpServerByCode) | **GET** /api/admin/GeboMCPServerAdminController/findMcpServerByCode | 
[**getAllMcpServers**](GeboMcpServerAdminControllerApi.md#getAllMcpServers) | **GET** /api/admin/GeboMCPServerAdminController/getAllMcpServers | 
[**getMcpServerPagedList**](GeboMcpServerAdminControllerApi.md#getMcpServerPagedList) | **POST** /api/admin/GeboMCPServerAdminController/getMcpServerPagedList | 
[**insertMcpServer**](GeboMcpServerAdminControllerApi.md#insertMcpServer) | **POST** /api/admin/GeboMCPServerAdminController/insertMcpServer | 
[**setMcpServerAccessAcls**](GeboMcpServerAdminControllerApi.md#setMcpServerAccessAcls) | **POST** /api/admin/GeboMCPServerAdminController/setMcpServerAccessAcls | 
[**updateMcpServer**](GeboMcpServerAdminControllerApi.md#updateMcpServer) | **POST** /api/admin/GeboMCPServerAdminController/updateMcpServer | 

<a name="deleteMcpServer"></a>
# **deleteMcpServer**
> deleteMcpServer(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboMcpServerAdminControllerApi();
let code = "code_example"; // String | 

apiInstance.deleteMcpServer(code).then(() => {
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

<a name="findMcpServerByCode"></a>
# **findMcpServerByCode**
> GeboMCPServerConfig findMcpServerByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboMcpServerAdminControllerApi();
let code = "code_example"; // String | 

apiInstance.findMcpServerByCode(code).then((data) => {
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

[**GeboMCPServerConfig**](GeboMCPServerConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAllMcpServers"></a>
# **getAllMcpServers**
> [GeboMCPServerConfig] getAllMcpServers()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboMcpServerAdminControllerApi();
apiInstance.getAllMcpServers().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GeboMCPServerConfig]**](GeboMCPServerConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getMcpServerPagedList"></a>
# **getMcpServerPagedList**
> PagedModelGeboMCPServerConfig getMcpServerPagedList(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboMcpServerAdminControllerApi();
let body = new GeboAiClient.DataPage(); // DataPage | 

apiInstance.getMcpServerPagedList(body).then((data) => {
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

[**PagedModelGeboMCPServerConfig**](PagedModelGeboMCPServerConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertMcpServer"></a>
# **insertMcpServer**
> GeboMCPServerConfig insertMcpServer(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboMcpServerAdminControllerApi();
let body = new GeboAiClient.GeboMCPServerConfig(); // GeboMCPServerConfig | 

apiInstance.insertMcpServer(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GeboMCPServerConfig**](GeboMCPServerConfig.md)|  | 

### Return type

[**GeboMCPServerConfig**](GeboMCPServerConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="setMcpServerAccessAcls"></a>
# **setMcpServerAccessAcls**
> GeboMCPServerConfig setMcpServerAccessAcls(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboMcpServerAdminControllerApi();
let body = new GeboAiClient.SetMcpServerAclsParam(); // SetMcpServerAclsParam | 

apiInstance.setMcpServerAccessAcls(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SetMcpServerAclsParam**](SetMcpServerAclsParam.md)|  | 

### Return type

[**GeboMCPServerConfig**](GeboMCPServerConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateMcpServer"></a>
# **updateMcpServer**
> GeboMCPServerConfig updateMcpServer(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboMcpServerAdminControllerApi();
let body = new GeboAiClient.GeboMCPServerConfig(); // GeboMCPServerConfig | 

apiInstance.updateMcpServer(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GeboMCPServerConfig**](GeboMCPServerConfig.md)|  | 

### Return type

[**GeboMCPServerConfig**](GeboMCPServerConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

