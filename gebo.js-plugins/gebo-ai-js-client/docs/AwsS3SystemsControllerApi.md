# GeboAiClient.AwsS3SystemsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteAwsS3ProjectEndpoint**](AwsS3SystemsControllerApi.md#deleteAwsS3ProjectEndpoint) | **POST** /api/admin/AwsS3SystemsController/deleteAwsS3ProjectEndpoint | 
[**deleteAwsS3System**](AwsS3SystemsControllerApi.md#deleteAwsS3System) | **POST** /api/admin/AwsS3SystemsController/deleteAwsS3System | 
[**fastAwsS3Config**](AwsS3SystemsControllerApi.md#fastAwsS3Config) | **POST** /api/admin/AwsS3SystemsController/fastAwsS3Config | 
[**findAwsS3EndpointsByProject**](AwsS3SystemsControllerApi.md#findAwsS3EndpointsByProject) | **GET** /api/admin/AwsS3SystemsController/findAwsS3EndpointsByProject | 
[**findAwsS3EndpointsByQbe**](AwsS3SystemsControllerApi.md#findAwsS3EndpointsByQbe) | **POST** /api/admin/AwsS3SystemsController/findAwsS3EndpointsByQbe | 
[**findAwsS3ProjectEndpointByCode**](AwsS3SystemsControllerApi.md#findAwsS3ProjectEndpointByCode) | **GET** /api/admin/AwsS3SystemsController/findAwsS3ProjectEndpointByCode | 
[**findAwsS3SystemByCode**](AwsS3SystemsControllerApi.md#findAwsS3SystemByCode) | **GET** /api/admin/AwsS3SystemsController/findAwsS3SystemByCode | 
[**getAwsS3SystemType**](AwsS3SystemsControllerApi.md#getAwsS3SystemType) | **GET** /api/admin/AwsS3SystemsController/getAwsS3SystemType | 
[**getAwsS3Systems**](AwsS3SystemsControllerApi.md#getAwsS3Systems) | **GET** /api/admin/AwsS3SystemsController/getAwsS3Systems | 
[**insertAwsS3ProjectEndpoint**](AwsS3SystemsControllerApi.md#insertAwsS3ProjectEndpoint) | **POST** /api/admin/AwsS3SystemsController/insertAwsS3ProjectEndpoint | 
[**insertAwsS3System**](AwsS3SystemsControllerApi.md#insertAwsS3System) | **POST** /api/admin/AwsS3SystemsController/insertAwsS3System | 
[**publishAwsS3ProjectEndpoint**](AwsS3SystemsControllerApi.md#publishAwsS3ProjectEndpoint) | **POST** /api/admin/AwsS3SystemsController/publishAwsS3ProjectEndpoint | 
[**updateAwsS3ProjectEndpoint**](AwsS3SystemsControllerApi.md#updateAwsS3ProjectEndpoint) | **POST** /api/admin/AwsS3SystemsController/updateAwsS3ProjectEndpoint | 
[**updateAwsS3System**](AwsS3SystemsControllerApi.md#updateAwsS3System) | **POST** /api/admin/AwsS3SystemsController/updateAwsS3System | 

<a name="deleteAwsS3ProjectEndpoint"></a>
# **deleteAwsS3ProjectEndpoint**
> deleteAwsS3ProjectEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.AwsS3SystemsControllerApi();
let body = new GeboAiClient.GAwsS3ProjectEndpoint(); // GAwsS3ProjectEndpoint | 

apiInstance.deleteAwsS3ProjectEndpoint(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAwsS3ProjectEndpoint**](GAwsS3ProjectEndpoint.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="deleteAwsS3System"></a>
# **deleteAwsS3System**
> deleteAwsS3System(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.AwsS3SystemsControllerApi();
let body = new GeboAiClient.GAwsS3System(); // GAwsS3System | 

apiInstance.deleteAwsS3System(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAwsS3System**](GAwsS3System.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="fastAwsS3Config"></a>
# **fastAwsS3Config**
> OperationStatusGAwsS3System fastAwsS3Config(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.AwsS3SystemsControllerApi();
let body = new GeboAiClient.FastAwsS3SystemInsertRequest(); // FastAwsS3SystemInsertRequest | 

apiInstance.fastAwsS3Config(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**FastAwsS3SystemInsertRequest**](FastAwsS3SystemInsertRequest.md)|  | 

### Return type

[**OperationStatusGAwsS3System**](OperationStatusGAwsS3System.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findAwsS3EndpointsByProject"></a>
# **findAwsS3EndpointsByProject**
> [GAwsS3ProjectEndpoint] findAwsS3EndpointsByProject(parentProjectCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.AwsS3SystemsControllerApi();
let parentProjectCode = "parentProjectCode_example"; // String | 

apiInstance.findAwsS3EndpointsByProject(parentProjectCode).then((data) => {
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

[**[GAwsS3ProjectEndpoint]**](GAwsS3ProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="findAwsS3EndpointsByQbe"></a>
# **findAwsS3EndpointsByQbe**
> [GAwsS3ProjectEndpoint] findAwsS3EndpointsByQbe(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.AwsS3SystemsControllerApi();
let body = new GeboAiClient.GAwsS3ProjectEndpoint(); // GAwsS3ProjectEndpoint | 

apiInstance.findAwsS3EndpointsByQbe(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAwsS3ProjectEndpoint**](GAwsS3ProjectEndpoint.md)|  | 

### Return type

[**[GAwsS3ProjectEndpoint]**](GAwsS3ProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findAwsS3ProjectEndpointByCode"></a>
# **findAwsS3ProjectEndpointByCode**
> GAwsS3ProjectEndpoint findAwsS3ProjectEndpointByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.AwsS3SystemsControllerApi();
let code = "code_example"; // String | 

apiInstance.findAwsS3ProjectEndpointByCode(code).then((data) => {
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

[**GAwsS3ProjectEndpoint**](GAwsS3ProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findAwsS3SystemByCode"></a>
# **findAwsS3SystemByCode**
> GAwsS3System findAwsS3SystemByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.AwsS3SystemsControllerApi();
let code = "code_example"; // String | 

apiInstance.findAwsS3SystemByCode(code).then((data) => {
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

[**GAwsS3System**](GAwsS3System.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAwsS3SystemType"></a>
# **getAwsS3SystemType**
> GContentManagementSystemType getAwsS3SystemType()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.AwsS3SystemsControllerApi();
apiInstance.getAwsS3SystemType().then((data) => {
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

<a name="getAwsS3Systems"></a>
# **getAwsS3Systems**
> [GAwsS3System] getAwsS3Systems()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.AwsS3SystemsControllerApi();
apiInstance.getAwsS3Systems().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GAwsS3System]**](GAwsS3System.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="insertAwsS3ProjectEndpoint"></a>
# **insertAwsS3ProjectEndpoint**
> GAwsS3ProjectEndpoint insertAwsS3ProjectEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.AwsS3SystemsControllerApi();
let body = new GeboAiClient.GAwsS3ProjectEndpoint(); // GAwsS3ProjectEndpoint | 

apiInstance.insertAwsS3ProjectEndpoint(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAwsS3ProjectEndpoint**](GAwsS3ProjectEndpoint.md)|  | 

### Return type

[**GAwsS3ProjectEndpoint**](GAwsS3ProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertAwsS3System"></a>
# **insertAwsS3System**
> OperationStatusGAwsS3System insertAwsS3System(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.AwsS3SystemsControllerApi();
let body = new GeboAiClient.GAwsS3System(); // GAwsS3System | 

apiInstance.insertAwsS3System(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAwsS3System**](GAwsS3System.md)|  | 

### Return type

[**OperationStatusGAwsS3System**](OperationStatusGAwsS3System.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="publishAwsS3ProjectEndpoint"></a>
# **publishAwsS3ProjectEndpoint**
> OperationStatusGJobStatus publishAwsS3ProjectEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.AwsS3SystemsControllerApi();
let body = new GeboAiClient.GAwsS3ProjectEndpoint(); // GAwsS3ProjectEndpoint | 

apiInstance.publishAwsS3ProjectEndpoint(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAwsS3ProjectEndpoint**](GAwsS3ProjectEndpoint.md)|  | 

### Return type

[**OperationStatusGJobStatus**](OperationStatusGJobStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateAwsS3ProjectEndpoint"></a>
# **updateAwsS3ProjectEndpoint**
> GAwsS3ProjectEndpoint updateAwsS3ProjectEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.AwsS3SystemsControllerApi();
let body = new GeboAiClient.GAwsS3ProjectEndpoint(); // GAwsS3ProjectEndpoint | 

apiInstance.updateAwsS3ProjectEndpoint(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAwsS3ProjectEndpoint**](GAwsS3ProjectEndpoint.md)|  | 

### Return type

[**GAwsS3ProjectEndpoint**](GAwsS3ProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateAwsS3System"></a>
# **updateAwsS3System**
> OperationStatusGAwsS3System updateAwsS3System(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.AwsS3SystemsControllerApi();
let body = new GeboAiClient.GAwsS3System(); // GAwsS3System | 

apiInstance.updateAwsS3System(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAwsS3System**](GAwsS3System.md)|  | 

### Return type

[**OperationStatusGAwsS3System**](OperationStatusGAwsS3System.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

