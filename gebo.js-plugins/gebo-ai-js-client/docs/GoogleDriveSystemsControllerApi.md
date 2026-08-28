# GeboAiClient.GoogleDriveSystemsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteGoogleDriveProjectEndpoint**](GoogleDriveSystemsControllerApi.md#deleteGoogleDriveProjectEndpoint) | **POST** /api/admin/GoogleDriveSystemsController/deleteGoogleDriveProjectEndpoint | 
[**deleteGoogleDriveSystem**](GoogleDriveSystemsControllerApi.md#deleteGoogleDriveSystem) | **POST** /api/admin/GoogleDriveSystemsController/deleteGoogleDriveSystem | 
[**fastGoogleDriveConfig**](GoogleDriveSystemsControllerApi.md#fastGoogleDriveConfig) | **POST** /api/admin/GoogleDriveSystemsController/fastGoogleDriveConfig | 
[**findGoogleDriveEndpointsByProject**](GoogleDriveSystemsControllerApi.md#findGoogleDriveEndpointsByProject) | **GET** /api/admin/GoogleDriveSystemsController/findGoogleDriveEndpointsByProject | 
[**findGoogleDriveEndpointsByQbe**](GoogleDriveSystemsControllerApi.md#findGoogleDriveEndpointsByQbe) | **POST** /api/admin/GoogleDriveSystemsController/findGoogleDriveEndpointsByQbe | 
[**findGoogleDriveProjectEndpointByCode**](GoogleDriveSystemsControllerApi.md#findGoogleDriveProjectEndpointByCode) | **GET** /api/admin/GoogleDriveSystemsController/findGoogleDriveProjectEndpointByCode | 
[**findGoogleDriveSystemByCode**](GoogleDriveSystemsControllerApi.md#findGoogleDriveSystemByCode) | **GET** /api/admin/GoogleDriveSystemsController/findGoogleDriveSystemByCode | 
[**getGoogleDriveSystemType**](GoogleDriveSystemsControllerApi.md#getGoogleDriveSystemType) | **GET** /api/admin/GoogleDriveSystemsController/getGoogleDriveSystemType | 
[**getGoogleDriveSystems**](GoogleDriveSystemsControllerApi.md#getGoogleDriveSystems) | **GET** /api/admin/GoogleDriveSystemsController/getGoogleDriveSystems | 
[**insertGoogleDriveProjectEndpoint**](GoogleDriveSystemsControllerApi.md#insertGoogleDriveProjectEndpoint) | **POST** /api/admin/GoogleDriveSystemsController/insertGoogleDriveProjectEndpoint | 
[**insertGoogleDriveSystem**](GoogleDriveSystemsControllerApi.md#insertGoogleDriveSystem) | **POST** /api/admin/GoogleDriveSystemsController/insertGoogleDriveSystem | 
[**publishGoogleDriveProjectEndpoint**](GoogleDriveSystemsControllerApi.md#publishGoogleDriveProjectEndpoint) | **POST** /api/admin/GoogleDriveSystemsController/publishGoogleDriveProjectEndpoint | 
[**updateGoogleDriveProjectEndpoint**](GoogleDriveSystemsControllerApi.md#updateGoogleDriveProjectEndpoint) | **POST** /api/admin/GoogleDriveSystemsController/updateGoogleDriveProjectEndpoint | 
[**updateGoogleDriveSystem**](GoogleDriveSystemsControllerApi.md#updateGoogleDriveSystem) | **POST** /api/admin/GoogleDriveSystemsController/updateGoogleDriveSystem | 

<a name="deleteGoogleDriveProjectEndpoint"></a>
# **deleteGoogleDriveProjectEndpoint**
> deleteGoogleDriveProjectEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleDriveSystemsControllerApi();
let body = new GeboAiClient.GGoogleDriveProjectEndpoint(); // GGoogleDriveProjectEndpoint | 

apiInstance.deleteGoogleDriveProjectEndpoint(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGoogleDriveProjectEndpoint**](GGoogleDriveProjectEndpoint.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="deleteGoogleDriveSystem"></a>
# **deleteGoogleDriveSystem**
> deleteGoogleDriveSystem(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleDriveSystemsControllerApi();
let body = new GeboAiClient.GGoogleDriveSystem(); // GGoogleDriveSystem | 

apiInstance.deleteGoogleDriveSystem(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGoogleDriveSystem**](GGoogleDriveSystem.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="fastGoogleDriveConfig"></a>
# **fastGoogleDriveConfig**
> OperationStatusGGoogleDriveSystem fastGoogleDriveConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleDriveSystemsControllerApi();
let body = new GeboAiClient.FastGoogleDriveSystemInsert(); // FastGoogleDriveSystemInsert | 

apiInstance.fastGoogleDriveConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**FastGoogleDriveSystemInsert**](FastGoogleDriveSystemInsert.md)|  | 

### Return type

[**OperationStatusGGoogleDriveSystem**](OperationStatusGGoogleDriveSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findGoogleDriveEndpointsByProject"></a>
# **findGoogleDriveEndpointsByProject**
> [GGoogleDriveProjectEndpoint] findGoogleDriveEndpointsByProject(parentProjectCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleDriveSystemsControllerApi();
let parentProjectCode = "parentProjectCode_example"; // String | 

apiInstance.findGoogleDriveEndpointsByProject(parentProjectCode).then((data) => {
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

[**[GGoogleDriveProjectEndpoint]**](GGoogleDriveProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="findGoogleDriveEndpointsByQbe"></a>
# **findGoogleDriveEndpointsByQbe**
> [GGoogleDriveProjectEndpoint] findGoogleDriveEndpointsByQbe(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleDriveSystemsControllerApi();
let body = new GeboAiClient.GGoogleDriveProjectEndpoint(); // GGoogleDriveProjectEndpoint | 

apiInstance.findGoogleDriveEndpointsByQbe(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGoogleDriveProjectEndpoint**](GGoogleDriveProjectEndpoint.md)|  | 

### Return type

[**[GGoogleDriveProjectEndpoint]**](GGoogleDriveProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findGoogleDriveProjectEndpointByCode"></a>
# **findGoogleDriveProjectEndpointByCode**
> GGoogleDriveProjectEndpoint findGoogleDriveProjectEndpointByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleDriveSystemsControllerApi();
let code = "code_example"; // String | 

apiInstance.findGoogleDriveProjectEndpointByCode(code).then((data) => {
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

[**GGoogleDriveProjectEndpoint**](GGoogleDriveProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findGoogleDriveSystemByCode"></a>
# **findGoogleDriveSystemByCode**
> GGoogleDriveSystem findGoogleDriveSystemByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleDriveSystemsControllerApi();
let code = "code_example"; // String | 

apiInstance.findGoogleDriveSystemByCode(code).then((data) => {
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

[**GGoogleDriveSystem**](GGoogleDriveSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getGoogleDriveSystemType"></a>
# **getGoogleDriveSystemType**
> GContentManagementSystemType getGoogleDriveSystemType()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleDriveSystemsControllerApi();
apiInstance.getGoogleDriveSystemType().then((data) => {
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

<a name="getGoogleDriveSystems"></a>
# **getGoogleDriveSystems**
> [GGoogleDriveSystem] getGoogleDriveSystems()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleDriveSystemsControllerApi();
apiInstance.getGoogleDriveSystems().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GGoogleDriveSystem]**](GGoogleDriveSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="insertGoogleDriveProjectEndpoint"></a>
# **insertGoogleDriveProjectEndpoint**
> GGoogleDriveProjectEndpoint insertGoogleDriveProjectEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleDriveSystemsControllerApi();
let body = new GeboAiClient.GGoogleDriveProjectEndpoint(); // GGoogleDriveProjectEndpoint | 

apiInstance.insertGoogleDriveProjectEndpoint(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGoogleDriveProjectEndpoint**](GGoogleDriveProjectEndpoint.md)|  | 

### Return type

[**GGoogleDriveProjectEndpoint**](GGoogleDriveProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertGoogleDriveSystem"></a>
# **insertGoogleDriveSystem**
> OperationStatusGGoogleDriveSystem insertGoogleDriveSystem(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleDriveSystemsControllerApi();
let body = new GeboAiClient.GGoogleDriveSystem(); // GGoogleDriveSystem | 

apiInstance.insertGoogleDriveSystem(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGoogleDriveSystem**](GGoogleDriveSystem.md)|  | 

### Return type

[**OperationStatusGGoogleDriveSystem**](OperationStatusGGoogleDriveSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="publishGoogleDriveProjectEndpoint"></a>
# **publishGoogleDriveProjectEndpoint**
> OperationStatusGJobStatus publishGoogleDriveProjectEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleDriveSystemsControllerApi();
let body = new GeboAiClient.GGoogleDriveProjectEndpoint(); // GGoogleDriveProjectEndpoint | 

apiInstance.publishGoogleDriveProjectEndpoint(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGoogleDriveProjectEndpoint**](GGoogleDriveProjectEndpoint.md)|  | 

### Return type

[**OperationStatusGJobStatus**](OperationStatusGJobStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateGoogleDriveProjectEndpoint"></a>
# **updateGoogleDriveProjectEndpoint**
> GGoogleDriveProjectEndpoint updateGoogleDriveProjectEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleDriveSystemsControllerApi();
let body = new GeboAiClient.GGoogleDriveProjectEndpoint(); // GGoogleDriveProjectEndpoint | 

apiInstance.updateGoogleDriveProjectEndpoint(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGoogleDriveProjectEndpoint**](GGoogleDriveProjectEndpoint.md)|  | 

### Return type

[**GGoogleDriveProjectEndpoint**](GGoogleDriveProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateGoogleDriveSystem"></a>
# **updateGoogleDriveSystem**
> OperationStatusGGoogleDriveSystem updateGoogleDriveSystem(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleDriveSystemsControllerApi();
let body = new GeboAiClient.GGoogleDriveSystem(); // GGoogleDriveSystem | 

apiInstance.updateGoogleDriveSystem(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGoogleDriveSystem**](GGoogleDriveSystem.md)|  | 

### Return type

[**OperationStatusGGoogleDriveSystem**](OperationStatusGGoogleDriveSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

