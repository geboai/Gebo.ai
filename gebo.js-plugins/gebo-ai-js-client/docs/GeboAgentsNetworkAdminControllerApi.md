# GeboAiClient.GeboAgentsNetworkAdminControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteAgentsNetwork**](GeboAgentsNetworkAdminControllerApi.md#deleteAgentsNetwork) | **POST** /api/admin/GeboAgentsNetworkAdminController/deleteAgentsNetwork | 
[**getAgentConfigs**](GeboAgentsNetworkAdminControllerApi.md#getAgentConfigs) | **GET** /api/admin/GeboAgentsNetworkAdminController/getAgentConfigs | 
[**getAgentConfigsByServiceId**](GeboAgentsNetworkAdminControllerApi.md#getAgentConfigsByServiceId) | **GET** /api/admin/GeboAgentsNetworkAdminController/getAgentConfigsByServiceId | 
[**getAgentServices**](GeboAgentsNetworkAdminControllerApi.md#getAgentServices) | **GET** /api/admin/GeboAgentsNetworkAdminController/getAgentServices | 
[**getAgentsNetwork**](GeboAgentsNetworkAdminControllerApi.md#getAgentsNetwork) | **GET** /api/admin/GeboAgentsNetworkAdminController/getAgentsNetwork | 
[**getAgentsNetworkByCode**](GeboAgentsNetworkAdminControllerApi.md#getAgentsNetworkByCode) | **GET** /api/admin/GeboAgentsNetworkAdminController/getAgentsNetworkByCode | 
[**getCompatibleNextServices**](GeboAgentsNetworkAdminControllerApi.md#getCompatibleNextServices) | **GET** /api/admin/GeboAgentsNetworkAdminController/getCompatibleNextServices | 
[**getCompatiblePreviousServices**](GeboAgentsNetworkAdminControllerApi.md#getCompatiblePreviousServices) | **GET** /api/admin/GeboAgentsNetworkAdminController/getCompatiblePreviousServices | 
[**getNetworkAdapterServices**](GeboAgentsNetworkAdminControllerApi.md#getNetworkAdapterServices) | **GET** /api/admin/GeboAgentsNetworkAdminController/getNetworkAdapterServices | 
[**insertAgentsNetwork**](GeboAgentsNetworkAdminControllerApi.md#insertAgentsNetwork) | **POST** /api/admin/GeboAgentsNetworkAdminController/insertAgentsNetwork | 
[**updateAgentsNetwork**](GeboAgentsNetworkAdminControllerApi.md#updateAgentsNetwork) | **POST** /api/admin/GeboAgentsNetworkAdminController/updateAgentsNetwork | 
[**validateAgentsNetwork**](GeboAgentsNetworkAdminControllerApi.md#validateAgentsNetwork) | **POST** /api/admin/GeboAgentsNetworkAdminController/validateAgentsNetwork | 

<a name="deleteAgentsNetwork"></a>
# **deleteAgentsNetwork**
> OperationStatusGAgentsNetwork deleteAgentsNetwork(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboAgentsNetworkAdminControllerApi();
let body = new GeboAiClient.GAgentsNetwork(); // GAgentsNetwork | 

apiInstance.deleteAgentsNetwork(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAgentsNetwork**](GAgentsNetwork.md)|  | 

### Return type

[**OperationStatusGAgentsNetwork**](OperationStatusGAgentsNetwork.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getAgentConfigs"></a>
# **getAgentConfigs**
> [GBaseObject] getAgentConfigs()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboAgentsNetworkAdminControllerApi();
apiInstance.getAgentConfigs().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GBaseObject]**](GBaseObject.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAgentConfigsByServiceId"></a>
# **getAgentConfigsByServiceId**
> [GAgentConfig] getAgentConfigsByServiceId(serviceId)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboAgentsNetworkAdminControllerApi();
let serviceId = "serviceId_example"; // String | 

apiInstance.getAgentConfigsByServiceId(serviceId).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **serviceId** | **String**|  | 

### Return type

[**[GAgentConfig]**](GAgentConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAgentServices"></a>
# **getAgentServices**
> [AgentServiceDescriptor] getAgentServices()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboAgentsNetworkAdminControllerApi();
apiInstance.getAgentServices().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[AgentServiceDescriptor]**](AgentServiceDescriptor.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAgentsNetwork"></a>
# **getAgentsNetwork**
> [GBaseObject] getAgentsNetwork()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboAgentsNetworkAdminControllerApi();
apiInstance.getAgentsNetwork().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GBaseObject]**](GBaseObject.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAgentsNetworkByCode"></a>
# **getAgentsNetworkByCode**
> GAgentsNetwork getAgentsNetworkByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboAgentsNetworkAdminControllerApi();
let code = "code_example"; // String | 

apiInstance.getAgentsNetworkByCode(code).then((data) => {
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

[**GAgentsNetwork**](GAgentsNetwork.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getCompatibleNextServices"></a>
# **getCompatibleNextServices**
> [AgentServiceDescriptor] getCompatibleNextServices(serviceId)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboAgentsNetworkAdminControllerApi();
let serviceId = "serviceId_example"; // String | 

apiInstance.getCompatibleNextServices(serviceId).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **serviceId** | **String**|  | 

### Return type

[**[AgentServiceDescriptor]**](AgentServiceDescriptor.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getCompatiblePreviousServices"></a>
# **getCompatiblePreviousServices**
> [AgentServiceDescriptor] getCompatiblePreviousServices(serviceId)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboAgentsNetworkAdminControllerApi();
let serviceId = "serviceId_example"; // String | 

apiInstance.getCompatiblePreviousServices(serviceId).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **serviceId** | **String**|  | 

### Return type

[**[AgentServiceDescriptor]**](AgentServiceDescriptor.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getNetworkAdapterServices"></a>
# **getNetworkAdapterServices**
> [AgentServiceDescriptor] getNetworkAdapterServices()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboAgentsNetworkAdminControllerApi();
apiInstance.getNetworkAdapterServices().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[AgentServiceDescriptor]**](AgentServiceDescriptor.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="insertAgentsNetwork"></a>
# **insertAgentsNetwork**
> OperationStatusGAgentsNetwork insertAgentsNetwork(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboAgentsNetworkAdminControllerApi();
let body = new GeboAiClient.GAgentsNetwork(); // GAgentsNetwork | 

apiInstance.insertAgentsNetwork(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAgentsNetwork**](GAgentsNetwork.md)|  | 

### Return type

[**OperationStatusGAgentsNetwork**](OperationStatusGAgentsNetwork.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateAgentsNetwork"></a>
# **updateAgentsNetwork**
> OperationStatusGAgentsNetwork updateAgentsNetwork(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboAgentsNetworkAdminControllerApi();
let body = new GeboAiClient.GAgentsNetwork(); // GAgentsNetwork | 

apiInstance.updateAgentsNetwork(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAgentsNetwork**](GAgentsNetwork.md)|  | 

### Return type

[**OperationStatusGAgentsNetwork**](OperationStatusGAgentsNetwork.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="validateAgentsNetwork"></a>
# **validateAgentsNetwork**
> OperationStatusGAgentsNetwork validateAgentsNetwork(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboAgentsNetworkAdminControllerApi();
let body = new GeboAiClient.GAgentsNetwork(); // GAgentsNetwork | 

apiInstance.validateAgentsNetwork(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAgentsNetwork**](GAgentsNetwork.md)|  | 

### Return type

[**OperationStatusGAgentsNetwork**](OperationStatusGAgentsNetwork.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

