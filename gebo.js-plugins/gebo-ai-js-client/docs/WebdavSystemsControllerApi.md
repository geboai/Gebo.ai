# GeboAiClient.WebdavSystemsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteWebdavEndpoint**](WebdavSystemsControllerApi.md#deleteWebdavEndpoint) | **POST** /api/admin/WebdavSystemsController/deleteWebdavEndpoint | 
[**deleteWebdavSystem**](WebdavSystemsControllerApi.md#deleteWebdavSystem) | **POST** /api/admin/WebdavSystemsController/deleteWebdavSystem | 
[**fastWebdavConfig**](WebdavSystemsControllerApi.md#fastWebdavConfig) | **POST** /api/admin/WebdavSystemsController/fastWebdavConfig | 
[**findWebdavEndpointsByCode**](WebdavSystemsControllerApi.md#findWebdavEndpointsByCode) | **GET** /api/admin/WebdavSystemsController/findWebdavEndpointsByCode | 
[**findWebdavEndpointsByProject**](WebdavSystemsControllerApi.md#findWebdavEndpointsByProject) | **GET** /api/admin/WebdavSystemsController/findWebdavEndpointsByProject | 
[**findWebdavEndpointsByQbe**](WebdavSystemsControllerApi.md#findWebdavEndpointsByQbe) | **POST** /api/admin/WebdavSystemsController/findWebdavEndpointsByQbe | 
[**findWebdavSystemByCode**](WebdavSystemsControllerApi.md#findWebdavSystemByCode) | **GET** /api/admin/WebdavSystemsController/findWebdavSystemByCode | 
[**getWebdavSystemTypes**](WebdavSystemsControllerApi.md#getWebdavSystemTypes) | **GET** /api/admin/WebdavSystemsController/getWebdavSystemType | 
[**getWebdavSystems**](WebdavSystemsControllerApi.md#getWebdavSystems) | **GET** /api/admin/WebdavSystemsController/getWebdavSystems | 
[**insertWebdavEndpoint**](WebdavSystemsControllerApi.md#insertWebdavEndpoint) | **POST** /api/admin/WebdavSystemsController/insertWebdavEndpoint | 
[**insertWebdavSystem**](WebdavSystemsControllerApi.md#insertWebdavSystem) | **POST** /api/admin/WebdavSystemsController/insertWebdavSystem | 
[**publishWebdavEndpoint**](WebdavSystemsControllerApi.md#publishWebdavEndpoint) | **POST** /api/admin/WebdavSystemsController/publishWebdavEndpoint | 
[**testWebdavSystem**](WebdavSystemsControllerApi.md#testWebdavSystem) | **POST** /api/admin/WebdavSystemsController/testWebdavSystem | 
[**updateWebdavEndpoint**](WebdavSystemsControllerApi.md#updateWebdavEndpoint) | **POST** /api/admin/WebdavSystemsController/updateWebdavEndpoint | 
[**updateWebdavSystem**](WebdavSystemsControllerApi.md#updateWebdavSystem) | **POST** /api/admin/WebdavSystemsController/updateWebdavSystem | 

<a name="deleteWebdavEndpoint"></a>
# **deleteWebdavEndpoint**
> deleteWebdavEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.WebdavSystemsControllerApi();
let body = new GeboAiClient.GWebdavProjectEndpoint(); // GWebdavProjectEndpoint | 

apiInstance.deleteWebdavEndpoint(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GWebdavProjectEndpoint**](GWebdavProjectEndpoint.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="deleteWebdavSystem"></a>
# **deleteWebdavSystem**
> deleteWebdavSystem(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.WebdavSystemsControllerApi();
let body = new GeboAiClient.GWebdavContentManagementSystem(); // GWebdavContentManagementSystem | 

apiInstance.deleteWebdavSystem(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GWebdavContentManagementSystem**](GWebdavContentManagementSystem.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="fastWebdavConfig"></a>
# **fastWebdavConfig**
> OperationStatusGWebdavContentManagementSystem fastWebdavConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.WebdavSystemsControllerApi();
let body = new GeboAiClient.FastWebdavSystemInsertRequest(); // FastWebdavSystemInsertRequest | 

apiInstance.fastWebdavConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**FastWebdavSystemInsertRequest**](FastWebdavSystemInsertRequest.md)|  | 

### Return type

[**OperationStatusGWebdavContentManagementSystem**](OperationStatusGWebdavContentManagementSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findWebdavEndpointsByCode"></a>
# **findWebdavEndpointsByCode**
> GWebdavProjectEndpoint findWebdavEndpointsByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.WebdavSystemsControllerApi();
let code = "code_example"; // String | 

apiInstance.findWebdavEndpointsByCode(code).then((data) => {
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

[**GWebdavProjectEndpoint**](GWebdavProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="findWebdavEndpointsByProject"></a>
# **findWebdavEndpointsByProject**
> [GWebdavProjectEndpoint] findWebdavEndpointsByProject(parentProjectCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.WebdavSystemsControllerApi();
let parentProjectCode = "parentProjectCode_example"; // String | 

apiInstance.findWebdavEndpointsByProject(parentProjectCode).then((data) => {
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

[**[GWebdavProjectEndpoint]**](GWebdavProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="findWebdavEndpointsByQbe"></a>
# **findWebdavEndpointsByQbe**
> [GWebdavProjectEndpoint] findWebdavEndpointsByQbe(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.WebdavSystemsControllerApi();
let body = new GeboAiClient.GWebdavProjectEndpoint(); // GWebdavProjectEndpoint | 

apiInstance.findWebdavEndpointsByQbe(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GWebdavProjectEndpoint**](GWebdavProjectEndpoint.md)|  | 

### Return type

[**[GWebdavProjectEndpoint]**](GWebdavProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findWebdavSystemByCode"></a>
# **findWebdavSystemByCode**
> GWebdavContentManagementSystem findWebdavSystemByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.WebdavSystemsControllerApi();
let code = "code_example"; // String | 

apiInstance.findWebdavSystemByCode(code).then((data) => {
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

[**GWebdavContentManagementSystem**](GWebdavContentManagementSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="getWebdavSystemTypes"></a>
# **getWebdavSystemTypes**
> GContentManagementSystemType getWebdavSystemTypes()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.WebdavSystemsControllerApi();
apiInstance.getWebdavSystemTypes().then((data) => {
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

<a name="getWebdavSystems"></a>
# **getWebdavSystems**
> [GWebdavContentManagementSystem] getWebdavSystems()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.WebdavSystemsControllerApi();
apiInstance.getWebdavSystems().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GWebdavContentManagementSystem]**](GWebdavContentManagementSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="insertWebdavEndpoint"></a>
# **insertWebdavEndpoint**
> GWebdavProjectEndpoint insertWebdavEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.WebdavSystemsControllerApi();
let body = new GeboAiClient.GWebdavProjectEndpoint(); // GWebdavProjectEndpoint | 

apiInstance.insertWebdavEndpoint(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GWebdavProjectEndpoint**](GWebdavProjectEndpoint.md)|  | 

### Return type

[**GWebdavProjectEndpoint**](GWebdavProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertWebdavSystem"></a>
# **insertWebdavSystem**
> OperationStatusGWebdavContentManagementSystem insertWebdavSystem(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.WebdavSystemsControllerApi();
let body = new GeboAiClient.GWebdavContentManagementSystem(); // GWebdavContentManagementSystem | 

apiInstance.insertWebdavSystem(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GWebdavContentManagementSystem**](GWebdavContentManagementSystem.md)|  | 

### Return type

[**OperationStatusGWebdavContentManagementSystem**](OperationStatusGWebdavContentManagementSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="publishWebdavEndpoint"></a>
# **publishWebdavEndpoint**
> OperationStatusGJobStatus publishWebdavEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.WebdavSystemsControllerApi();
let body = new GeboAiClient.GWebdavProjectEndpoint(); // GWebdavProjectEndpoint | 

apiInstance.publishWebdavEndpoint(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GWebdavProjectEndpoint**](GWebdavProjectEndpoint.md)|  | 

### Return type

[**OperationStatusGJobStatus**](OperationStatusGJobStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="testWebdavSystem"></a>
# **testWebdavSystem**
> OperationStatusGWebdavContentManagementSystem testWebdavSystem(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.WebdavSystemsControllerApi();
let body = new GeboAiClient.GWebdavContentManagementSystem(); // GWebdavContentManagementSystem | 

apiInstance.testWebdavSystem(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GWebdavContentManagementSystem**](GWebdavContentManagementSystem.md)|  | 

### Return type

[**OperationStatusGWebdavContentManagementSystem**](OperationStatusGWebdavContentManagementSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateWebdavEndpoint"></a>
# **updateWebdavEndpoint**
> GWebdavProjectEndpoint updateWebdavEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.WebdavSystemsControllerApi();
let body = new GeboAiClient.GWebdavProjectEndpoint(); // GWebdavProjectEndpoint | 

apiInstance.updateWebdavEndpoint(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GWebdavProjectEndpoint**](GWebdavProjectEndpoint.md)|  | 

### Return type

[**GWebdavProjectEndpoint**](GWebdavProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateWebdavSystem"></a>
# **updateWebdavSystem**
> OperationStatusGWebdavContentManagementSystem updateWebdavSystem(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.WebdavSystemsControllerApi();
let body = new GeboAiClient.GWebdavContentManagementSystem(); // GWebdavContentManagementSystem | 

apiInstance.updateWebdavSystem(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GWebdavContentManagementSystem**](GWebdavContentManagementSystem.md)|  | 

### Return type

[**OperationStatusGWebdavContentManagementSystem**](OperationStatusGWebdavContentManagementSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

