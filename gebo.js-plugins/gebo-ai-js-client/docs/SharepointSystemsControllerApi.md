# GeboAiClient.SharepointSystemsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteSharepointEndpoint**](SharepointSystemsControllerApi.md#deleteSharepointEndpoint) | **POST** /api/admin/SharepointSystemsController/deleteSharepointEndpoint | 
[**deleteSharepointSystem**](SharepointSystemsControllerApi.md#deleteSharepointSystem) | **POST** /api/admin/SharepointSystemsController/deleteSharepointSystem | 
[**fastSharepointConfig**](SharepointSystemsControllerApi.md#fastSharepointConfig) | **POST** /api/admin/SharepointSystemsController/fastSharepointConfig | 
[**findSharepointEndpointsByCode**](SharepointSystemsControllerApi.md#findSharepointEndpointsByCode) | **GET** /api/admin/SharepointSystemsController/findSharepointEndpointsByCode | 
[**findSharepointEndpointsByProject**](SharepointSystemsControllerApi.md#findSharepointEndpointsByProject) | **GET** /api/admin/SharepointSystemsController/findSharepointEndpointsByProject | 
[**findSharepointEndpointsByQbe**](SharepointSystemsControllerApi.md#findSharepointEndpointsByQbe) | **POST** /api/admin/SharepointSystemsController/findSharepointEndpointsByQbe | 
[**findSharepointSystemByCode**](SharepointSystemsControllerApi.md#findSharepointSystemByCode) | **GET** /api/admin/SharepointSystemsController/findSharepointSystemByCode | 
[**getSharepointSystemTypes**](SharepointSystemsControllerApi.md#getSharepointSystemTypes) | **GET** /api/admin/SharepointSystemsController/getSharepointSystemType | 
[**getSharepointSystems**](SharepointSystemsControllerApi.md#getSharepointSystems) | **GET** /api/admin/SharepointSystemsController/getSharepointSystems | 
[**insertSharepointEndpoint**](SharepointSystemsControllerApi.md#insertSharepointEndpoint) | **POST** /api/admin/SharepointSystemsController/insertSharepointEndpoint | 
[**insertSharepointSystem**](SharepointSystemsControllerApi.md#insertSharepointSystem) | **POST** /api/admin/SharepointSystemsController/insertSharepointSystem | 
[**publishSharepointEndpoint**](SharepointSystemsControllerApi.md#publishSharepointEndpoint) | **POST** /api/admin/SharepointSystemsController/publishSharepointEndpoint | 
[**testSharepointSystem**](SharepointSystemsControllerApi.md#testSharepointSystem) | **POST** /api/admin/SharepointSystemsController/testSharepointSystem | 
[**updateSharepointEndpoint**](SharepointSystemsControllerApi.md#updateSharepointEndpoint) | **POST** /api/admin/SharepointSystemsController/updateSharepointEndpoint | 
[**updateSharepointSystem**](SharepointSystemsControllerApi.md#updateSharepointSystem) | **POST** /api/admin/SharepointSystemsController/updateSharepointSystem | 

<a name="deleteSharepointEndpoint"></a>
# **deleteSharepointEndpoint**
> deleteSharepointEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SharepointSystemsControllerApi();
let body = new GeboAiClient.GSharepointProjectEndpoint(); // GSharepointProjectEndpoint | 

apiInstance.deleteSharepointEndpoint(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GSharepointProjectEndpoint**](GSharepointProjectEndpoint.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="deleteSharepointSystem"></a>
# **deleteSharepointSystem**
> deleteSharepointSystem(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SharepointSystemsControllerApi();
let body = new GeboAiClient.GSharepointContentManagementSystem(); // GSharepointContentManagementSystem | 

apiInstance.deleteSharepointSystem(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GSharepointContentManagementSystem**](GSharepointContentManagementSystem.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="fastSharepointConfig"></a>
# **fastSharepointConfig**
> OperationStatusGSharepointContentManagementSystem fastSharepointConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SharepointSystemsControllerApi();
let body = new GeboAiClient.FastSharepointSystemInsertRequest(); // FastSharepointSystemInsertRequest | 

apiInstance.fastSharepointConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**FastSharepointSystemInsertRequest**](FastSharepointSystemInsertRequest.md)|  | 

### Return type

[**OperationStatusGSharepointContentManagementSystem**](OperationStatusGSharepointContentManagementSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findSharepointEndpointsByCode"></a>
# **findSharepointEndpointsByCode**
> GSharepointProjectEndpoint findSharepointEndpointsByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SharepointSystemsControllerApi();
let code = "code_example"; // String | 

apiInstance.findSharepointEndpointsByCode(code).then((data) => {
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

[**GSharepointProjectEndpoint**](GSharepointProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="findSharepointEndpointsByProject"></a>
# **findSharepointEndpointsByProject**
> [GSharepointProjectEndpoint] findSharepointEndpointsByProject(parentProjectCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SharepointSystemsControllerApi();
let parentProjectCode = "parentProjectCode_example"; // String | 

apiInstance.findSharepointEndpointsByProject(parentProjectCode).then((data) => {
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

[**[GSharepointProjectEndpoint]**](GSharepointProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="findSharepointEndpointsByQbe"></a>
# **findSharepointEndpointsByQbe**
> [GSharepointProjectEndpoint] findSharepointEndpointsByQbe(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SharepointSystemsControllerApi();
let body = new GeboAiClient.GSharepointProjectEndpoint(); // GSharepointProjectEndpoint | 

apiInstance.findSharepointEndpointsByQbe(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GSharepointProjectEndpoint**](GSharepointProjectEndpoint.md)|  | 

### Return type

[**[GSharepointProjectEndpoint]**](GSharepointProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findSharepointSystemByCode"></a>
# **findSharepointSystemByCode**
> GSharepointContentManagementSystem findSharepointSystemByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SharepointSystemsControllerApi();
let code = "code_example"; // String | 

apiInstance.findSharepointSystemByCode(code).then((data) => {
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

[**GSharepointContentManagementSystem**](GSharepointContentManagementSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="getSharepointSystemTypes"></a>
# **getSharepointSystemTypes**
> GContentManagementSystemType getSharepointSystemTypes()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SharepointSystemsControllerApi();
apiInstance.getSharepointSystemTypes().then((data) => {
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

<a name="getSharepointSystems"></a>
# **getSharepointSystems**
> [GSharepointContentManagementSystem] getSharepointSystems()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SharepointSystemsControllerApi();
apiInstance.getSharepointSystems().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GSharepointContentManagementSystem]**](GSharepointContentManagementSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="insertSharepointEndpoint"></a>
# **insertSharepointEndpoint**
> GSharepointProjectEndpoint insertSharepointEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SharepointSystemsControllerApi();
let body = new GeboAiClient.GSharepointProjectEndpoint(); // GSharepointProjectEndpoint | 

apiInstance.insertSharepointEndpoint(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GSharepointProjectEndpoint**](GSharepointProjectEndpoint.md)|  | 

### Return type

[**GSharepointProjectEndpoint**](GSharepointProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertSharepointSystem"></a>
# **insertSharepointSystem**
> OperationStatusGSharepointContentManagementSystem insertSharepointSystem(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SharepointSystemsControllerApi();
let body = new GeboAiClient.GSharepointContentManagementSystem(); // GSharepointContentManagementSystem | 

apiInstance.insertSharepointSystem(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GSharepointContentManagementSystem**](GSharepointContentManagementSystem.md)|  | 

### Return type

[**OperationStatusGSharepointContentManagementSystem**](OperationStatusGSharepointContentManagementSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="publishSharepointEndpoint"></a>
# **publishSharepointEndpoint**
> OperationStatusGJobStatus publishSharepointEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SharepointSystemsControllerApi();
let body = new GeboAiClient.GSharepointProjectEndpoint(); // GSharepointProjectEndpoint | 

apiInstance.publishSharepointEndpoint(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GSharepointProjectEndpoint**](GSharepointProjectEndpoint.md)|  | 

### Return type

[**OperationStatusGJobStatus**](OperationStatusGJobStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="testSharepointSystem"></a>
# **testSharepointSystem**
> OperationStatusGSharepointContentManagementSystem testSharepointSystem(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SharepointSystemsControllerApi();
let body = new GeboAiClient.GSharepointContentManagementSystem(); // GSharepointContentManagementSystem | 

apiInstance.testSharepointSystem(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GSharepointContentManagementSystem**](GSharepointContentManagementSystem.md)|  | 

### Return type

[**OperationStatusGSharepointContentManagementSystem**](OperationStatusGSharepointContentManagementSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateSharepointEndpoint"></a>
# **updateSharepointEndpoint**
> GSharepointProjectEndpoint updateSharepointEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SharepointSystemsControllerApi();
let body = new GeboAiClient.GSharepointProjectEndpoint(); // GSharepointProjectEndpoint | 

apiInstance.updateSharepointEndpoint(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GSharepointProjectEndpoint**](GSharepointProjectEndpoint.md)|  | 

### Return type

[**GSharepointProjectEndpoint**](GSharepointProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateSharepointSystem"></a>
# **updateSharepointSystem**
> OperationStatusGSharepointContentManagementSystem updateSharepointSystem(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SharepointSystemsControllerApi();
let body = new GeboAiClient.GSharepointContentManagementSystem(); // GSharepointContentManagementSystem | 

apiInstance.updateSharepointSystem(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GSharepointContentManagementSystem**](GSharepointContentManagementSystem.md)|  | 

### Return type

[**OperationStatusGSharepointContentManagementSystem**](OperationStatusGSharepointContentManagementSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

