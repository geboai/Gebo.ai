# GeboAiClient.GitSystemsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteGitEndpoint**](GitSystemsControllerApi.md#deleteGitEndpoint) | **POST** /api/admin/GITSystemsController/deleteGitEndpoint | 
[**deleteGitSystem**](GitSystemsControllerApi.md#deleteGitSystem) | **POST** /api/admin/GITSystemsController/deleteGitSystem | 
[**findGitEndpointsByProject**](GitSystemsControllerApi.md#findGitEndpointsByProject) | **GET** /api/admin/GITSystemsController/findGitEndpointsByProject | 
[**findGitEndpointsByQbe**](GitSystemsControllerApi.md#findGitEndpointsByQbe) | **POST** /api/admin/GITSystemsController/findGitEndpointsByQbe | 
[**getBranchesList**](GitSystemsControllerApi.md#getBranchesList) | **POST** /api/admin/GITSystemsController/getBranchesList | 
[**getGitSystemTypes**](GitSystemsControllerApi.md#getGitSystemTypes) | **GET** /api/admin/GITSystemsController/getGitSystemTypes | 
[**getGitSystems**](GitSystemsControllerApi.md#getGitSystems) | **GET** /api/admin/GITSystemsController/getGitSystems | 
[**insertGitEndpoint**](GitSystemsControllerApi.md#insertGitEndpoint) | **POST** /api/admin/GITSystemsController/insertGitEndpoint | 
[**insertGitSystem**](GitSystemsControllerApi.md#insertGitSystem) | **POST** /api/admin/GITSystemsController/insertGitSystem | 
[**publishGitEndpoint**](GitSystemsControllerApi.md#publishGitEndpoint) | **POST** /api/admin/GITSystemsController/publishGitEndpoint | 
[**updateGitEndpoint**](GitSystemsControllerApi.md#updateGitEndpoint) | **POST** /api/admin/GITSystemsController/updateGitEndpoint | 
[**updateGitSystem**](GitSystemsControllerApi.md#updateGitSystem) | **POST** /api/admin/GITSystemsController/updateGitSystem | 

<a name="deleteGitEndpoint"></a>
# **deleteGitEndpoint**
> deleteGitEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GitSystemsControllerApi();
let body = new GeboAiClient.GGitProjectEndpoint(); // GGitProjectEndpoint | 

apiInstance.deleteGitEndpoint(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGitProjectEndpoint**](GGitProjectEndpoint.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="deleteGitSystem"></a>
# **deleteGitSystem**
> deleteGitSystem(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GitSystemsControllerApi();
let body = new GeboAiClient.GGitContentManagementSystem(); // GGitContentManagementSystem | 

apiInstance.deleteGitSystem(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGitContentManagementSystem**](GGitContentManagementSystem.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="findGitEndpointsByProject"></a>
# **findGitEndpointsByProject**
> [GGitProjectEndpoint] findGitEndpointsByProject(parentProjectCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GitSystemsControllerApi();
let parentProjectCode = "parentProjectCode_example"; // String | 

apiInstance.findGitEndpointsByProject(parentProjectCode).then((data) => {
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

[**[GGitProjectEndpoint]**](GGitProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="findGitEndpointsByQbe"></a>
# **findGitEndpointsByQbe**
> [GGitProjectEndpoint] findGitEndpointsByQbe(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GitSystemsControllerApi();
let body = new GeboAiClient.GGitProjectEndpoint(); // GGitProjectEndpoint | 

apiInstance.findGitEndpointsByQbe(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGitProjectEndpoint**](GGitProjectEndpoint.md)|  | 

### Return type

[**[GGitProjectEndpoint]**](GGitProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getBranchesList"></a>
# **getBranchesList**
> OperationStatusListString getBranchesList(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GitSystemsControllerApi();
let body = new GeboAiClient.GGitProjectEndpoint(); // GGitProjectEndpoint | 

apiInstance.getBranchesList(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGitProjectEndpoint**](GGitProjectEndpoint.md)|  | 

### Return type

[**OperationStatusListString**](OperationStatusListString.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getGitSystemTypes"></a>
# **getGitSystemTypes**
> [GContentManagementSystemType] getGitSystemTypes()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GitSystemsControllerApi();
apiInstance.getGitSystemTypes().then((data) => {
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

<a name="getGitSystems"></a>
# **getGitSystems**
> [GGitContentManagementSystem] getGitSystems(opts)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GitSystemsControllerApi();
let opts = { 
  'handlerCode': "handlerCode_example" // String | 
};
apiInstance.getGitSystems(opts).then((data) => {
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

[**[GGitContentManagementSystem]**](GGitContentManagementSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="insertGitEndpoint"></a>
# **insertGitEndpoint**
> OperationStatusGGitProjectEndpoint insertGitEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GitSystemsControllerApi();
let body = new GeboAiClient.GGitProjectEndpoint(); // GGitProjectEndpoint | 

apiInstance.insertGitEndpoint(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGitProjectEndpoint**](GGitProjectEndpoint.md)|  | 

### Return type

[**OperationStatusGGitProjectEndpoint**](OperationStatusGGitProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertGitSystem"></a>
# **insertGitSystem**
> GGitContentManagementSystem insertGitSystem(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GitSystemsControllerApi();
let body = new GeboAiClient.GGitContentManagementSystem(); // GGitContentManagementSystem | 

apiInstance.insertGitSystem(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGitContentManagementSystem**](GGitContentManagementSystem.md)|  | 

### Return type

[**GGitContentManagementSystem**](GGitContentManagementSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="publishGitEndpoint"></a>
# **publishGitEndpoint**
> OperationStatusGJobStatus publishGitEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GitSystemsControllerApi();
let body = new GeboAiClient.GGitProjectEndpoint(); // GGitProjectEndpoint | 

apiInstance.publishGitEndpoint(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGitProjectEndpoint**](GGitProjectEndpoint.md)|  | 

### Return type

[**OperationStatusGJobStatus**](OperationStatusGJobStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateGitEndpoint"></a>
# **updateGitEndpoint**
> OperationStatusGGitProjectEndpoint updateGitEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GitSystemsControllerApi();
let body = new GeboAiClient.GGitProjectEndpoint(); // GGitProjectEndpoint | 

apiInstance.updateGitEndpoint(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGitProjectEndpoint**](GGitProjectEndpoint.md)|  | 

### Return type

[**OperationStatusGGitProjectEndpoint**](OperationStatusGGitProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateGitSystem"></a>
# **updateGitSystem**
> GGitContentManagementSystem updateGitSystem(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GitSystemsControllerApi();
let body = new GeboAiClient.GGitContentManagementSystem(); // GGitContentManagementSystem | 

apiInstance.updateGitSystem(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGitContentManagementSystem**](GGitContentManagementSystem.md)|  | 

### Return type

[**GGitContentManagementSystem**](GGitContentManagementSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

