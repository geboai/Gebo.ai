# GeboAiClient.JiraSystemsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteJiraEndpoint**](JiraSystemsControllerApi.md#deleteJiraEndpoint) | **POST** /api/admin/JiraSystemsController/deleteJiraEndpoint | 
[**deleteJiraSystem**](JiraSystemsControllerApi.md#deleteJiraSystem) | **POST** /api/admin/JiraSystemsController/deleteJiraSystem | 
[**fastJiraConfig**](JiraSystemsControllerApi.md#fastJiraConfig) | **POST** /api/admin/JiraSystemsController/fastJiraConfig | 
[**findJiraEndpointsByCode**](JiraSystemsControllerApi.md#findJiraEndpointsByCode) | **GET** /api/admin/JiraSystemsController/findJiraEndpointsByCode | 
[**findJiraEndpointsByProject**](JiraSystemsControllerApi.md#findJiraEndpointsByProject) | **GET** /api/admin/JiraSystemsController/findJiraEndpointsByProject | 
[**findJiraEndpointsByQbe**](JiraSystemsControllerApi.md#findJiraEndpointsByQbe) | **POST** /api/admin/JiraSystemsController/findJiraEndpointsByQbe | 
[**findJiraSystemByCode**](JiraSystemsControllerApi.md#findJiraSystemByCode) | **GET** /api/admin/JiraSystemsController/findJiraSystemByCode | 
[**getJiraSystemTypes**](JiraSystemsControllerApi.md#getJiraSystemTypes) | **GET** /api/admin/JiraSystemsController/getJiraSystemType | 
[**getJiraSystems**](JiraSystemsControllerApi.md#getJiraSystems) | **GET** /api/admin/JiraSystemsController/getJiraSystems | 
[**insertJiraEndpoint**](JiraSystemsControllerApi.md#insertJiraEndpoint) | **POST** /api/admin/JiraSystemsController/insertJiraEndpoint | 
[**insertJiraSystem**](JiraSystemsControllerApi.md#insertJiraSystem) | **POST** /api/admin/JiraSystemsController/insertJiraSystem | 
[**publishJiraEndpoint**](JiraSystemsControllerApi.md#publishJiraEndpoint) | **POST** /api/admin/JiraSystemsController/publishJiraEndpoint | 
[**testJiraSystem**](JiraSystemsControllerApi.md#testJiraSystem) | **POST** /api/admin/JiraSystemsController/testJiraSystem | 
[**updateJiraEndpoint**](JiraSystemsControllerApi.md#updateJiraEndpoint) | **POST** /api/admin/JiraSystemsController/updateJiraEndpoint | 
[**updateJiraSystem**](JiraSystemsControllerApi.md#updateJiraSystem) | **POST** /api/admin/JiraSystemsController/updateJiraSystem | 

<a name="deleteJiraEndpoint"></a>
# **deleteJiraEndpoint**
> deleteJiraEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JiraSystemsControllerApi();
let body = new GeboAiClient.GJiraProjectEndpoint(); // GJiraProjectEndpoint | 

apiInstance.deleteJiraEndpoint(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GJiraProjectEndpoint**](GJiraProjectEndpoint.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="deleteJiraSystem"></a>
# **deleteJiraSystem**
> deleteJiraSystem(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JiraSystemsControllerApi();
let body = new GeboAiClient.GJiraSystem(); // GJiraSystem | 

apiInstance.deleteJiraSystem(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GJiraSystem**](GJiraSystem.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="fastJiraConfig"></a>
# **fastJiraConfig**
> OperationStatusGJiraSystem fastJiraConfig(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JiraSystemsControllerApi();
let body = new GeboAiClient.FastJiraSystemInsertRequest(); // FastJiraSystemInsertRequest | 

apiInstance.fastJiraConfig(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**FastJiraSystemInsertRequest**](FastJiraSystemInsertRequest.md)|  | 

### Return type

[**OperationStatusGJiraSystem**](OperationStatusGJiraSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findJiraEndpointsByCode"></a>
# **findJiraEndpointsByCode**
> GJiraProjectEndpoint findJiraEndpointsByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JiraSystemsControllerApi();
let code = "code_example"; // String | 

apiInstance.findJiraEndpointsByCode(code).then((data) => {
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

[**GJiraProjectEndpoint**](GJiraProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="findJiraEndpointsByProject"></a>
# **findJiraEndpointsByProject**
> [GJiraProjectEndpoint] findJiraEndpointsByProject(parentProjectCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JiraSystemsControllerApi();
let parentProjectCode = "parentProjectCode_example"; // String | 

apiInstance.findJiraEndpointsByProject(parentProjectCode).then((data) => {
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

[**[GJiraProjectEndpoint]**](GJiraProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="findJiraEndpointsByQbe"></a>
# **findJiraEndpointsByQbe**
> [GJiraProjectEndpoint] findJiraEndpointsByQbe(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JiraSystemsControllerApi();
let body = new GeboAiClient.GJiraProjectEndpoint(); // GJiraProjectEndpoint | 

apiInstance.findJiraEndpointsByQbe(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GJiraProjectEndpoint**](GJiraProjectEndpoint.md)|  | 

### Return type

[**[GJiraProjectEndpoint]**](GJiraProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findJiraSystemByCode"></a>
# **findJiraSystemByCode**
> GJiraSystem findJiraSystemByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JiraSystemsControllerApi();
let code = "code_example"; // String | 

apiInstance.findJiraSystemByCode(code).then((data) => {
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

[**GJiraSystem**](GJiraSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="getJiraSystemTypes"></a>
# **getJiraSystemTypes**
> GContentManagementSystemType getJiraSystemTypes()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JiraSystemsControllerApi();
apiInstance.getJiraSystemTypes().then((data) => {
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

<a name="getJiraSystems"></a>
# **getJiraSystems**
> [GJiraSystem] getJiraSystems()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JiraSystemsControllerApi();
apiInstance.getJiraSystems().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GJiraSystem]**](GJiraSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="insertJiraEndpoint"></a>
# **insertJiraEndpoint**
> GJiraProjectEndpoint insertJiraEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JiraSystemsControllerApi();
let body = new GeboAiClient.GJiraProjectEndpoint(); // GJiraProjectEndpoint | 

apiInstance.insertJiraEndpoint(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GJiraProjectEndpoint**](GJiraProjectEndpoint.md)|  | 

### Return type

[**GJiraProjectEndpoint**](GJiraProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertJiraSystem"></a>
# **insertJiraSystem**
> OperationStatusGJiraSystem insertJiraSystem(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JiraSystemsControllerApi();
let body = new GeboAiClient.GJiraSystem(); // GJiraSystem | 

apiInstance.insertJiraSystem(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GJiraSystem**](GJiraSystem.md)|  | 

### Return type

[**OperationStatusGJiraSystem**](OperationStatusGJiraSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="publishJiraEndpoint"></a>
# **publishJiraEndpoint**
> OperationStatusGJobStatus publishJiraEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JiraSystemsControllerApi();
let body = new GeboAiClient.GJiraProjectEndpoint(); // GJiraProjectEndpoint | 

apiInstance.publishJiraEndpoint(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GJiraProjectEndpoint**](GJiraProjectEndpoint.md)|  | 

### Return type

[**OperationStatusGJobStatus**](OperationStatusGJobStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="testJiraSystem"></a>
# **testJiraSystem**
> OperationStatusGJiraSystem testJiraSystem(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JiraSystemsControllerApi();
let body = new GeboAiClient.GJiraSystem(); // GJiraSystem | 

apiInstance.testJiraSystem(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GJiraSystem**](GJiraSystem.md)|  | 

### Return type

[**OperationStatusGJiraSystem**](OperationStatusGJiraSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateJiraEndpoint"></a>
# **updateJiraEndpoint**
> GJiraProjectEndpoint updateJiraEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JiraSystemsControllerApi();
let body = new GeboAiClient.GJiraProjectEndpoint(); // GJiraProjectEndpoint | 

apiInstance.updateJiraEndpoint(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GJiraProjectEndpoint**](GJiraProjectEndpoint.md)|  | 

### Return type

[**GJiraProjectEndpoint**](GJiraProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateJiraSystem"></a>
# **updateJiraSystem**
> OperationStatusGJiraSystem updateJiraSystem(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.JiraSystemsControllerApi();
let body = new GeboAiClient.GJiraSystem(); // GJiraSystem | 

apiInstance.updateJiraSystem(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GJiraSystem**](GJiraSystem.md)|  | 

### Return type

[**OperationStatusGJiraSystem**](OperationStatusGJiraSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

