# GeboAiClient.FileUploadsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteUploadsEndpoint**](FileUploadsControllerApi.md#deleteUploadsEndpoint) | **POST** /api/admin/FileUploadsController/deleteUploadsEndpoint | 
[**findUploadsEndpointsByProject**](FileUploadsControllerApi.md#findUploadsEndpointsByProject) | **GET** /api/admin/FileUploadsController/findUploadsEndpointsByProject | 
[**findUploadsEndpointsByQbe**](FileUploadsControllerApi.md#findUploadsEndpointsByQbe) | **POST** /api/admin/FileUploadsController/findUploadsEndpointsByQbe | 
[**getFileSystemSystemTypes**](FileUploadsControllerApi.md#getFileSystemSystemTypes) | **GET** /api/admin/FileUploadsController/getFileSystemSystemTypes | 
[**getUploadableFilesExtensions**](FileUploadsControllerApi.md#getUploadableFilesExtensions) | **GET** /api/admin/FileUploadsController/getUploadableFilesExtensions | 
[**getUploadsSystems**](FileUploadsControllerApi.md#getUploadsSystems) | **GET** /api/admin/FileUploadsController/getUploadsSystems | 
[**insertUploadsEndpoint**](FileUploadsControllerApi.md#insertUploadsEndpoint) | **POST** /api/admin/FileUploadsController/insertUploadsEndpoint | 
[**publishUploadsEndpoint**](FileUploadsControllerApi.md#publishUploadsEndpoint) | **POST** /api/admin/FileUploadsController/publishUploadsEndpoint | 
[**updateUploadsEndpoint**](FileUploadsControllerApi.md#updateUploadsEndpoint) | **POST** /api/admin/FileUploadsController/updateUploadsEndpoint | 

<a name="deleteUploadsEndpoint"></a>
# **deleteUploadsEndpoint**
> deleteUploadsEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.FileUploadsControllerApi();
let body = new GeboAiClient.GUploadsProjectEndpoint(); // GUploadsProjectEndpoint | 

apiInstance.deleteUploadsEndpoint(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GUploadsProjectEndpoint**](GUploadsProjectEndpoint.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="findUploadsEndpointsByProject"></a>
# **findUploadsEndpointsByProject**
> [GUploadsProjectEndpoint] findUploadsEndpointsByProject(parentProjectCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.FileUploadsControllerApi();
let parentProjectCode = "parentProjectCode_example"; // String | 

apiInstance.findUploadsEndpointsByProject(parentProjectCode).then((data) => {
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

[**[GUploadsProjectEndpoint]**](GUploadsProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findUploadsEndpointsByQbe"></a>
# **findUploadsEndpointsByQbe**
> [GUploadsProjectEndpoint] findUploadsEndpointsByQbe(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.FileUploadsControllerApi();
let body = new GeboAiClient.GUploadsProjectEndpoint(); // GUploadsProjectEndpoint | 

apiInstance.findUploadsEndpointsByQbe(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GUploadsProjectEndpoint**](GUploadsProjectEndpoint.md)|  | 

### Return type

[**[GUploadsProjectEndpoint]**](GUploadsProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getFileSystemSystemTypes"></a>
# **getFileSystemSystemTypes**
> [GContentManagementSystemType] getFileSystemSystemTypes()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.FileUploadsControllerApi();
apiInstance.getFileSystemSystemTypes().then((data) => {
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

<a name="getUploadableFilesExtensions"></a>
# **getUploadableFilesExtensions**
> [&#x27;String&#x27;] getUploadableFilesExtensions()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.FileUploadsControllerApi();
apiInstance.getUploadableFilesExtensions().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

**[&#x27;String&#x27;]**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getUploadsSystems"></a>
# **getUploadsSystems**
> [GUploadsContentManagementSystem] getUploadsSystems(opts)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.FileUploadsControllerApi();
let opts = { 
  'handlerCode': "handlerCode_example" // String | 
};
apiInstance.getUploadsSystems(opts).then((data) => {
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

[**[GUploadsContentManagementSystem]**](GUploadsContentManagementSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="insertUploadsEndpoint"></a>
# **insertUploadsEndpoint**
> GUploadsProjectEndpoint insertUploadsEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.FileUploadsControllerApi();
let body = new GeboAiClient.GUploadsProjectEndpoint(); // GUploadsProjectEndpoint | 

apiInstance.insertUploadsEndpoint(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GUploadsProjectEndpoint**](GUploadsProjectEndpoint.md)|  | 

### Return type

[**GUploadsProjectEndpoint**](GUploadsProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="publishUploadsEndpoint"></a>
# **publishUploadsEndpoint**
> OperationStatusGJobStatus publishUploadsEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.FileUploadsControllerApi();
let body = new GeboAiClient.GUploadsProjectEndpoint(); // GUploadsProjectEndpoint | 

apiInstance.publishUploadsEndpoint(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GUploadsProjectEndpoint**](GUploadsProjectEndpoint.md)|  | 

### Return type

[**OperationStatusGJobStatus**](OperationStatusGJobStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateUploadsEndpoint"></a>
# **updateUploadsEndpoint**
> GUploadsProjectEndpoint updateUploadsEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.FileUploadsControllerApi();
let body = new GeboAiClient.GUploadsProjectEndpoint(); // GUploadsProjectEndpoint | 

apiInstance.updateUploadsEndpoint(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GUploadsProjectEndpoint**](GUploadsProjectEndpoint.md)|  | 

### Return type

[**GUploadsProjectEndpoint**](GUploadsProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

