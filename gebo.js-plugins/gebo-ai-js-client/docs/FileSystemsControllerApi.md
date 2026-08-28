# GeboAiClient.FileSystemsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteFilesystemEndpoint**](FileSystemsControllerApi.md#deleteFilesystemEndpoint) | **POST** /api/admin/FileSystemsController/deleteFilesystemEndpoint | 
[**findFileSystemEndpointsByProject**](FileSystemsControllerApi.md#findFileSystemEndpointsByProject) | **GET** /api/admin/FileSystemsController/findFileSystemEndpointsByProject | 
[**findFileSystemEndpointsByQbe**](FileSystemsControllerApi.md#findFileSystemEndpointsByQbe) | **POST** /api/admin/FileSystemsController/findFileSystemEndpointsByQbe | 
[**getFileSystemSystemTypes1**](FileSystemsControllerApi.md#getFileSystemSystemTypes1) | **GET** /api/admin/FileSystemsController/getFileSystemSystemTypes | 
[**getFileSystemSystems**](FileSystemsControllerApi.md#getFileSystemSystems) | **GET** /api/admin/FileSystemsController/getFileSystemSystems | 
[**insertFilesystemEndpoint**](FileSystemsControllerApi.md#insertFilesystemEndpoint) | **POST** /api/admin/FileSystemsController/insertFilesystemEndpoint | 
[**publishFilesystemEndpoint**](FileSystemsControllerApi.md#publishFilesystemEndpoint) | **POST** /api/admin/FileSystemsController/publishFilesystemEndpoint | 
[**updateFilesystemEndpoint**](FileSystemsControllerApi.md#updateFilesystemEndpoint) | **POST** /api/admin/FileSystemsController/updateFilesystemEndpoint | 

<a name="deleteFilesystemEndpoint"></a>
# **deleteFilesystemEndpoint**
> deleteFilesystemEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.FileSystemsControllerApi();
let body = new GeboAiClient.GFilesystemProjectEndpoint(); // GFilesystemProjectEndpoint | 

apiInstance.deleteFilesystemEndpoint(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GFilesystemProjectEndpoint**](GFilesystemProjectEndpoint.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="findFileSystemEndpointsByProject"></a>
# **findFileSystemEndpointsByProject**
> [GFilesystemProjectEndpoint] findFileSystemEndpointsByProject(parentProjectCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.FileSystemsControllerApi();
let parentProjectCode = "parentProjectCode_example"; // String | 

apiInstance.findFileSystemEndpointsByProject(parentProjectCode).then((data) => {
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

[**[GFilesystemProjectEndpoint]**](GFilesystemProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="findFileSystemEndpointsByQbe"></a>
# **findFileSystemEndpointsByQbe**
> [GFilesystemProjectEndpoint] findFileSystemEndpointsByQbe(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.FileSystemsControllerApi();
let body = new GeboAiClient.GFilesystemProjectEndpoint(); // GFilesystemProjectEndpoint | 

apiInstance.findFileSystemEndpointsByQbe(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GFilesystemProjectEndpoint**](GFilesystemProjectEndpoint.md)|  | 

### Return type

[**[GFilesystemProjectEndpoint]**](GFilesystemProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getFileSystemSystemTypes1"></a>
# **getFileSystemSystemTypes1**
> [GContentManagementSystemType] getFileSystemSystemTypes1()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.FileSystemsControllerApi();
apiInstance.getFileSystemSystemTypes1().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GContentManagementSystemType]**](GContentManagementSystemType.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getFileSystemSystems"></a>
# **getFileSystemSystems**
> [GFilesystemContentManagementSystem] getFileSystemSystems(opts)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.FileSystemsControllerApi();
let opts = { 
  'handlerCode': "handlerCode_example" // String | 
};
apiInstance.getFileSystemSystems(opts).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **handlerCode** | **String**|  | [optional] 

### Return type

[**[GFilesystemContentManagementSystem]**](GFilesystemContentManagementSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="insertFilesystemEndpoint"></a>
# **insertFilesystemEndpoint**
> GFilesystemProjectEndpoint insertFilesystemEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.FileSystemsControllerApi();
let body = new GeboAiClient.GFilesystemProjectEndpoint(); // GFilesystemProjectEndpoint | 

apiInstance.insertFilesystemEndpoint(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GFilesystemProjectEndpoint**](GFilesystemProjectEndpoint.md)|  | 

### Return type

[**GFilesystemProjectEndpoint**](GFilesystemProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="publishFilesystemEndpoint"></a>
# **publishFilesystemEndpoint**
> OperationStatusGJobStatus publishFilesystemEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.FileSystemsControllerApi();
let body = new GeboAiClient.GFilesystemProjectEndpoint(); // GFilesystemProjectEndpoint | 

apiInstance.publishFilesystemEndpoint(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GFilesystemProjectEndpoint**](GFilesystemProjectEndpoint.md)|  | 

### Return type

[**OperationStatusGJobStatus**](OperationStatusGJobStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateFilesystemEndpoint"></a>
# **updateFilesystemEndpoint**
> GFilesystemProjectEndpoint updateFilesystemEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.FileSystemsControllerApi();
let body = new GeboAiClient.GFilesystemProjectEndpoint(); // GFilesystemProjectEndpoint | 

apiInstance.updateFilesystemEndpoint(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GFilesystemProjectEndpoint**](GFilesystemProjectEndpoint.md)|  | 

### Return type

[**GFilesystemProjectEndpoint**](GFilesystemProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

