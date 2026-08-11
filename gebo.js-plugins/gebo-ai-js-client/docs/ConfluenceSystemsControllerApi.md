# GeboAiClient.ConfluenceSystemsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteConfluenceEndpoint**](ConfluenceSystemsControllerApi.md#deleteConfluenceEndpoint) | **POST** /api/admin/ConfluenceSystemsController/deleteConfluenceEndpoint | 
[**deleteConfluenceSystem**](ConfluenceSystemsControllerApi.md#deleteConfluenceSystem) | **POST** /api/admin/ConfluenceSystemsController/deleteConfluenceSystem | 
[**fastConfluenceConfig**](ConfluenceSystemsControllerApi.md#fastConfluenceConfig) | **POST** /api/admin/ConfluenceSystemsController/fastConfluenceConfig | 
[**findConfluenceEndpointsByCode**](ConfluenceSystemsControllerApi.md#findConfluenceEndpointsByCode) | **GET** /api/admin/ConfluenceSystemsController/findConfluenceEndpointsByCode | 
[**findConfluenceEndpointsByProject**](ConfluenceSystemsControllerApi.md#findConfluenceEndpointsByProject) | **GET** /api/admin/ConfluenceSystemsController/findConfluenceEndpointsByProject | 
[**findConfluenceEndpointsByQbe**](ConfluenceSystemsControllerApi.md#findConfluenceEndpointsByQbe) | **POST** /api/admin/ConfluenceSystemsController/findConfluenceEndpointsByQbe | 
[**findConfluenceSystemByCode**](ConfluenceSystemsControllerApi.md#findConfluenceSystemByCode) | **GET** /api/admin/ConfluenceSystemsController/findConfluenceSystemByCode | 
[**getConfluenceSystemTypes**](ConfluenceSystemsControllerApi.md#getConfluenceSystemTypes) | **GET** /api/admin/ConfluenceSystemsController/getConfluenceSystemType | 
[**getConfluenceSystems**](ConfluenceSystemsControllerApi.md#getConfluenceSystems) | **GET** /api/admin/ConfluenceSystemsController/getConfluenceSystems | 
[**insertConfluenceEndpoint**](ConfluenceSystemsControllerApi.md#insertConfluenceEndpoint) | **POST** /api/admin/ConfluenceSystemsController/insertConfluenceEndpoint | 
[**insertConfluenceSystem**](ConfluenceSystemsControllerApi.md#insertConfluenceSystem) | **POST** /api/admin/ConfluenceSystemsController/insertConfluenceSystem | 
[**publishConfluenceEndpoint**](ConfluenceSystemsControllerApi.md#publishConfluenceEndpoint) | **POST** /api/admin/ConfluenceSystemsController/publishConfluenceEndpoint | 
[**testConfluenceSystem**](ConfluenceSystemsControllerApi.md#testConfluenceSystem) | **POST** /api/admin/ConfluenceSystemsController/testConfluenceSystem | 
[**updateConfluenceEndpoint**](ConfluenceSystemsControllerApi.md#updateConfluenceEndpoint) | **POST** /api/admin/ConfluenceSystemsController/updateConfluenceEndpoint | 
[**updateConfluenceSystem**](ConfluenceSystemsControllerApi.md#updateConfluenceSystem) | **POST** /api/admin/ConfluenceSystemsController/updateConfluenceSystem | 

<a name="deleteConfluenceEndpoint"></a>
# **deleteConfluenceEndpoint**
> deleteConfluenceEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ConfluenceSystemsControllerApi();
let body = new GeboAiClient.GConfluenceProjectEndpoint(); // GConfluenceProjectEndpoint | 

apiInstance.deleteConfluenceEndpoint(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GConfluenceProjectEndpoint**](GConfluenceProjectEndpoint.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="deleteConfluenceSystem"></a>
# **deleteConfluenceSystem**
> deleteConfluenceSystem(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ConfluenceSystemsControllerApi();
let body = new GeboAiClient.GConfluenceSystem(); // GConfluenceSystem | 

apiInstance.deleteConfluenceSystem(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GConfluenceSystem**](GConfluenceSystem.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="fastConfluenceConfig"></a>
# **fastConfluenceConfig**
> OperationStatusGConfluenceSystem fastConfluenceConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ConfluenceSystemsControllerApi();
let body = new GeboAiClient.FastConfluenceSystemInsertRequest(); // FastConfluenceSystemInsertRequest | 

apiInstance.fastConfluenceConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**FastConfluenceSystemInsertRequest**](FastConfluenceSystemInsertRequest.md)|  | 

### Return type

[**OperationStatusGConfluenceSystem**](OperationStatusGConfluenceSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findConfluenceEndpointsByCode"></a>
# **findConfluenceEndpointsByCode**
> GConfluenceProjectEndpoint findConfluenceEndpointsByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ConfluenceSystemsControllerApi();
let code = "code_example"; // String | 

apiInstance.findConfluenceEndpointsByCode(code).then((data) => {
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

[**GConfluenceProjectEndpoint**](GConfluenceProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="findConfluenceEndpointsByProject"></a>
# **findConfluenceEndpointsByProject**
> [GConfluenceProjectEndpoint] findConfluenceEndpointsByProject(parentProjectCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ConfluenceSystemsControllerApi();
let parentProjectCode = "parentProjectCode_example"; // String | 

apiInstance.findConfluenceEndpointsByProject(parentProjectCode).then((data) => {
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

[**[GConfluenceProjectEndpoint]**](GConfluenceProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="findConfluenceEndpointsByQbe"></a>
# **findConfluenceEndpointsByQbe**
> [GConfluenceProjectEndpoint] findConfluenceEndpointsByQbe(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ConfluenceSystemsControllerApi();
let body = new GeboAiClient.GConfluenceProjectEndpoint(); // GConfluenceProjectEndpoint | 

apiInstance.findConfluenceEndpointsByQbe(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GConfluenceProjectEndpoint**](GConfluenceProjectEndpoint.md)|  | 

### Return type

[**[GConfluenceProjectEndpoint]**](GConfluenceProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findConfluenceSystemByCode"></a>
# **findConfluenceSystemByCode**
> GConfluenceSystem findConfluenceSystemByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ConfluenceSystemsControllerApi();
let code = "code_example"; // String | 

apiInstance.findConfluenceSystemByCode(code).then((data) => {
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

[**GConfluenceSystem**](GConfluenceSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="getConfluenceSystemTypes"></a>
# **getConfluenceSystemTypes**
> GContentManagementSystemType getConfluenceSystemTypes()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ConfluenceSystemsControllerApi();
apiInstance.getConfluenceSystemTypes().then((data) => {
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

<a name="getConfluenceSystems"></a>
# **getConfluenceSystems**
> [GConfluenceSystem] getConfluenceSystems()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ConfluenceSystemsControllerApi();
apiInstance.getConfluenceSystems().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GConfluenceSystem]**](GConfluenceSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="insertConfluenceEndpoint"></a>
# **insertConfluenceEndpoint**
> GConfluenceProjectEndpoint insertConfluenceEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ConfluenceSystemsControllerApi();
let body = new GeboAiClient.GConfluenceProjectEndpoint(); // GConfluenceProjectEndpoint | 

apiInstance.insertConfluenceEndpoint(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GConfluenceProjectEndpoint**](GConfluenceProjectEndpoint.md)|  | 

### Return type

[**GConfluenceProjectEndpoint**](GConfluenceProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertConfluenceSystem"></a>
# **insertConfluenceSystem**
> OperationStatusGConfluenceSystem insertConfluenceSystem(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ConfluenceSystemsControllerApi();
let body = new GeboAiClient.GConfluenceSystem(); // GConfluenceSystem | 

apiInstance.insertConfluenceSystem(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GConfluenceSystem**](GConfluenceSystem.md)|  | 

### Return type

[**OperationStatusGConfluenceSystem**](OperationStatusGConfluenceSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="publishConfluenceEndpoint"></a>
# **publishConfluenceEndpoint**
> OperationStatusGJobStatus publishConfluenceEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ConfluenceSystemsControllerApi();
let body = new GeboAiClient.GConfluenceProjectEndpoint(); // GConfluenceProjectEndpoint | 

apiInstance.publishConfluenceEndpoint(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GConfluenceProjectEndpoint**](GConfluenceProjectEndpoint.md)|  | 

### Return type

[**OperationStatusGJobStatus**](OperationStatusGJobStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="testConfluenceSystem"></a>
# **testConfluenceSystem**
> OperationStatusGConfluenceSystem testConfluenceSystem(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ConfluenceSystemsControllerApi();
let body = new GeboAiClient.GConfluenceSystem(); // GConfluenceSystem | 

apiInstance.testConfluenceSystem(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GConfluenceSystem**](GConfluenceSystem.md)|  | 

### Return type

[**OperationStatusGConfluenceSystem**](OperationStatusGConfluenceSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateConfluenceEndpoint"></a>
# **updateConfluenceEndpoint**
> GConfluenceProjectEndpoint updateConfluenceEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ConfluenceSystemsControllerApi();
let body = new GeboAiClient.GConfluenceProjectEndpoint(); // GConfluenceProjectEndpoint | 

apiInstance.updateConfluenceEndpoint(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GConfluenceProjectEndpoint**](GConfluenceProjectEndpoint.md)|  | 

### Return type

[**GConfluenceProjectEndpoint**](GConfluenceProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateConfluenceSystem"></a>
# **updateConfluenceSystem**
> OperationStatusGConfluenceSystem updateConfluenceSystem(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ConfluenceSystemsControllerApi();
let body = new GeboAiClient.GConfluenceSystem(); // GConfluenceSystem | 

apiInstance.updateConfluenceSystem(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GConfluenceSystem**](GConfluenceSystem.md)|  | 

### Return type

[**OperationStatusGConfluenceSystem**](OperationStatusGConfluenceSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

