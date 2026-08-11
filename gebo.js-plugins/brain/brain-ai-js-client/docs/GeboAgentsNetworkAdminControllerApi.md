# BrainClient.GeboAgentsNetworkAdminControllerApi

All URIs are relative to *http://localhost:13001/brain*

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
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboAgentsNetworkAdminControllerApi();
let body = new BrainClient.GAgentsNetwork(); // GAgentsNetwork | 

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
> Object getAgentConfigs()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboAgentsNetworkAdminControllerApi();
apiInstance.getAgentConfigs().then((data) => {
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

<a name="getAgentConfigsByServiceId"></a>
# **getAgentConfigsByServiceId**
> Object getAgentConfigsByServiceId(serviceId)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboAgentsNetworkAdminControllerApi();
let serviceId = null; // Object | 

apiInstance.getAgentConfigsByServiceId(serviceId).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **serviceId** | [**Object**](.md)|  | 

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAgentServices"></a>
# **getAgentServices**
> Object getAgentServices()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboAgentsNetworkAdminControllerApi();
apiInstance.getAgentServices().then((data) => {
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

<a name="getAgentsNetwork"></a>
# **getAgentsNetwork**
> Object getAgentsNetwork()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboAgentsNetworkAdminControllerApi();
apiInstance.getAgentsNetwork().then((data) => {
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

<a name="getAgentsNetworkByCode"></a>
# **getAgentsNetworkByCode**
> GAgentsNetwork getAgentsNetworkByCode(code)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboAgentsNetworkAdminControllerApi();
let code = null; // Object | 

apiInstance.getAgentsNetworkByCode(code).then((data) => {
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

[**GAgentsNetwork**](GAgentsNetwork.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getCompatibleNextServices"></a>
# **getCompatibleNextServices**
> Object getCompatibleNextServices(serviceId)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboAgentsNetworkAdminControllerApi();
let serviceId = null; // Object | 

apiInstance.getCompatibleNextServices(serviceId).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **serviceId** | [**Object**](.md)|  | 

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getCompatiblePreviousServices"></a>
# **getCompatiblePreviousServices**
> Object getCompatiblePreviousServices(serviceId)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboAgentsNetworkAdminControllerApi();
let serviceId = null; // Object | 

apiInstance.getCompatiblePreviousServices(serviceId).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **serviceId** | [**Object**](.md)|  | 

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getNetworkAdapterServices"></a>
# **getNetworkAdapterServices**
> Object getNetworkAdapterServices()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboAgentsNetworkAdminControllerApi();
apiInstance.getNetworkAdapterServices().then((data) => {
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

<a name="insertAgentsNetwork"></a>
# **insertAgentsNetwork**
> OperationStatusGAgentsNetwork insertAgentsNetwork(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboAgentsNetworkAdminControllerApi();
let body = new BrainClient.GAgentsNetwork(); // GAgentsNetwork | 

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
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboAgentsNetworkAdminControllerApi();
let body = new BrainClient.GAgentsNetwork(); // GAgentsNetwork | 

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
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboAgentsNetworkAdminControllerApi();
let body = new BrainClient.GAgentsNetwork(); // GAgentsNetwork | 

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

