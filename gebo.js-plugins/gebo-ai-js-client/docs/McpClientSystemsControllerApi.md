# GeboAiClient.McpClientSystemsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteMCPClientEndpoint**](McpClientSystemsControllerApi.md#deleteMCPClientEndpoint) | **POST** /api/admin/MCPClientSystemsController/deleteMCPClientEndpoint | 
[**findMCPClientEndpointsByCode**](McpClientSystemsControllerApi.md#findMCPClientEndpointsByCode) | **GET** /api/admin/MCPClientSystemsController/findMCPClientEndpointsByCode | 
[**findMCPClientEndpointsByProject**](McpClientSystemsControllerApi.md#findMCPClientEndpointsByProject) | **GET** /api/admin/MCPClientSystemsController/findMCPClientEndpointsByProject | 
[**findMCPClientEndpointsByQbe**](McpClientSystemsControllerApi.md#findMCPClientEndpointsByQbe) | **POST** /api/admin/MCPClientSystemsController/findMCPClientEndpointsByQbe | 
[**getMCPClientSystemType**](McpClientSystemsControllerApi.md#getMCPClientSystemType) | **GET** /api/admin/MCPClientSystemsController/getMCPClientSystemType | 
[**insertMCPClientEndpoint**](McpClientSystemsControllerApi.md#insertMCPClientEndpoint) | **POST** /api/admin/MCPClientSystemsController/insertMCPClientEndpoint | 
[**publishMCPClientEndpoint**](McpClientSystemsControllerApi.md#publishMCPClientEndpoint) | **POST** /api/admin/MCPClientSystemsController/publishMCPClientEndpoint | 
[**updateMCPClientEndpoint**](McpClientSystemsControllerApi.md#updateMCPClientEndpoint) | **POST** /api/admin/MCPClientSystemsController/updateMCPClientEndpoint | 

<a name="deleteMCPClientEndpoint"></a>
# **deleteMCPClientEndpoint**
> deleteMCPClientEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.McpClientSystemsControllerApi();
let body = new GeboAiClient.MCPClientProjectEndpoint(); // MCPClientProjectEndpoint | 

apiInstance.deleteMCPClientEndpoint(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**MCPClientProjectEndpoint**](MCPClientProjectEndpoint.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="findMCPClientEndpointsByCode"></a>
# **findMCPClientEndpointsByCode**
> MCPClientProjectEndpoint findMCPClientEndpointsByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.McpClientSystemsControllerApi();
let code = "code_example"; // String | 

apiInstance.findMCPClientEndpointsByCode(code).then((data) => {
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

[**MCPClientProjectEndpoint**](MCPClientProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="findMCPClientEndpointsByProject"></a>
# **findMCPClientEndpointsByProject**
> [MCPClientProjectEndpoint] findMCPClientEndpointsByProject(parentProjectCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.McpClientSystemsControllerApi();
let parentProjectCode = "parentProjectCode_example"; // String | 

apiInstance.findMCPClientEndpointsByProject(parentProjectCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **parentProjectCode** | **String**|  | 

### Return type

[**[MCPClientProjectEndpoint]**](MCPClientProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="findMCPClientEndpointsByQbe"></a>
# **findMCPClientEndpointsByQbe**
> [MCPClientProjectEndpoint] findMCPClientEndpointsByQbe(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.McpClientSystemsControllerApi();
let body = new GeboAiClient.MCPClientProjectEndpoint(); // MCPClientProjectEndpoint | 

apiInstance.findMCPClientEndpointsByQbe(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**MCPClientProjectEndpoint**](MCPClientProjectEndpoint.md)|  | 

### Return type

[**[MCPClientProjectEndpoint]**](MCPClientProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMCPClientSystemType"></a>
# **getMCPClientSystemType**
> GContentManagementSystemType getMCPClientSystemType()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.McpClientSystemsControllerApi();
apiInstance.getMCPClientSystemType().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**GContentManagementSystemType**](GContentManagementSystemType.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="insertMCPClientEndpoint"></a>
# **insertMCPClientEndpoint**
> MCPClientProjectEndpoint insertMCPClientEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.McpClientSystemsControllerApi();
let body = new GeboAiClient.MCPClientProjectEndpoint(); // MCPClientProjectEndpoint | 

apiInstance.insertMCPClientEndpoint(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**MCPClientProjectEndpoint**](MCPClientProjectEndpoint.md)|  | 

### Return type

[**MCPClientProjectEndpoint**](MCPClientProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="publishMCPClientEndpoint"></a>
# **publishMCPClientEndpoint**
> OperationStatusGJobStatus publishMCPClientEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.McpClientSystemsControllerApi();
let body = new GeboAiClient.MCPClientProjectEndpoint(); // MCPClientProjectEndpoint | 

apiInstance.publishMCPClientEndpoint(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**MCPClientProjectEndpoint**](MCPClientProjectEndpoint.md)|  | 

### Return type

[**OperationStatusGJobStatus**](OperationStatusGJobStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateMCPClientEndpoint"></a>
# **updateMCPClientEndpoint**
> MCPClientProjectEndpoint updateMCPClientEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.McpClientSystemsControllerApi();
let body = new GeboAiClient.MCPClientProjectEndpoint(); // MCPClientProjectEndpoint | 

apiInstance.updateMCPClientEndpoint(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**MCPClientProjectEndpoint**](MCPClientProjectEndpoint.md)|  | 

### Return type

[**MCPClientProjectEndpoint**](MCPClientProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

