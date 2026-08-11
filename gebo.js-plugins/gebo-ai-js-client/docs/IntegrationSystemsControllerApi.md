# GeboAiClient.IntegrationSystemsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteIntegrationProjectEndpoint**](IntegrationSystemsControllerApi.md#deleteIntegrationProjectEndpoint) | **POST** /api/admin/IntegrationSystemsController/deleteIntegrationProjectEndpoint | 
[**findIntegrationEndpointsByProject**](IntegrationSystemsControllerApi.md#findIntegrationEndpointsByProject) | **GET** /api/admin/IntegrationSystemsController/findIntegrationEndpointsByProject | 
[**insertIntegrationProjectEndpoint**](IntegrationSystemsControllerApi.md#insertIntegrationProjectEndpoint) | **POST** /api/admin/IntegrationSystemsController/insertIntegrationProjectEndpoint | 
[**publishIntegrationProjectEndpoint**](IntegrationSystemsControllerApi.md#publishIntegrationProjectEndpoint) | **POST** /api/admin/IntegrationSystemsController/publishIntegrationProjectEndpoint | 
[**updateIntegrationProjectEndpoint**](IntegrationSystemsControllerApi.md#updateIntegrationProjectEndpoint) | **POST** /api/admin/IntegrationSystemsController/updateIntegrationProjectEndpoint | 

<a name="deleteIntegrationProjectEndpoint"></a>
# **deleteIntegrationProjectEndpoint**
> deleteIntegrationProjectEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.IntegrationSystemsControllerApi();
let body = new GeboAiClient.GIntegrationProjectEndpoint(); // GIntegrationProjectEndpoint | 

apiInstance.deleteIntegrationProjectEndpoint(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GIntegrationProjectEndpoint**](GIntegrationProjectEndpoint.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="findIntegrationEndpointsByProject"></a>
# **findIntegrationEndpointsByProject**
> [GIntegrationProjectEndpoint] findIntegrationEndpointsByProject(parentProjectCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.IntegrationSystemsControllerApi();
let parentProjectCode = "parentProjectCode_example"; // String | 

apiInstance.findIntegrationEndpointsByProject(parentProjectCode).then((data) => {
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

[**[GIntegrationProjectEndpoint]**](GIntegrationProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="insertIntegrationProjectEndpoint"></a>
# **insertIntegrationProjectEndpoint**
> GIntegrationProjectEndpoint insertIntegrationProjectEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.IntegrationSystemsControllerApi();
let body = new GeboAiClient.GIntegrationProjectEndpoint(); // GIntegrationProjectEndpoint | 

apiInstance.insertIntegrationProjectEndpoint(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GIntegrationProjectEndpoint**](GIntegrationProjectEndpoint.md)|  | 

### Return type

[**GIntegrationProjectEndpoint**](GIntegrationProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="publishIntegrationProjectEndpoint"></a>
# **publishIntegrationProjectEndpoint**
> OperationStatusGJobStatus publishIntegrationProjectEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.IntegrationSystemsControllerApi();
let body = new GeboAiClient.GIntegrationProjectEndpoint(); // GIntegrationProjectEndpoint | 

apiInstance.publishIntegrationProjectEndpoint(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GIntegrationProjectEndpoint**](GIntegrationProjectEndpoint.md)|  | 

### Return type

[**OperationStatusGJobStatus**](OperationStatusGJobStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateIntegrationProjectEndpoint"></a>
# **updateIntegrationProjectEndpoint**
> GIntegrationProjectEndpoint updateIntegrationProjectEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.IntegrationSystemsControllerApi();
let body = new GeboAiClient.GIntegrationProjectEndpoint(); // GIntegrationProjectEndpoint | 

apiInstance.updateIntegrationProjectEndpoint(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GIntegrationProjectEndpoint**](GIntegrationProjectEndpoint.md)|  | 

### Return type

[**GIntegrationProjectEndpoint**](GIntegrationProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

