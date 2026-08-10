# BrainClient.KnowledgeBaseControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteKnowledgeBase**](KnowledgeBaseControllerApi.md#deleteKnowledgeBase) | **POST** /api/admin/KnowledgeBaseController/deleteKnowledgeBase | 
[**findKnowledgeBaseByCode**](KnowledgeBaseControllerApi.md#findKnowledgeBaseByCode) | **GET** /api/admin/KnowledgeBaseController/findKnowledgeBaseByCode | 
[**findKnowledgeBasesByQbe**](KnowledgeBaseControllerApi.md#findKnowledgeBasesByQbe) | **POST** /api/admin/KnowledgeBaseController/findKnowledgeBasesByQbe | 
[**getChildKnowledgeBases**](KnowledgeBaseControllerApi.md#getChildKnowledgeBases) | **GET** /api/admin/KnowledgeBaseController/getChildKnowledgeBases | 
[**getKnowledgeBases**](KnowledgeBaseControllerApi.md#getKnowledgeBases) | **GET** /api/admin/KnowledgeBaseController/getKnowledgeBases | 
[**insertKnowledgeBase**](KnowledgeBaseControllerApi.md#insertKnowledgeBase) | **POST** /api/admin/KnowledgeBaseController/insertKnowledgeBase | 
[**updateKnowledgeBase**](KnowledgeBaseControllerApi.md#updateKnowledgeBase) | **POST** /api/admin/KnowledgeBaseController/updateKnowledgeBase | 

<a name="deleteKnowledgeBase"></a>
# **deleteKnowledgeBase**
> deleteKnowledgeBase(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.KnowledgeBaseControllerApi();
let body = new BrainClient.GKnowledgeBase(); // GKnowledgeBase | 

apiInstance.deleteKnowledgeBase(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GKnowledgeBase**](GKnowledgeBase.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="findKnowledgeBaseByCode"></a>
# **findKnowledgeBaseByCode**
> GKnowledgeBase findKnowledgeBaseByCode(code)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.KnowledgeBaseControllerApi();
let code = null; // Object | 

apiInstance.findKnowledgeBaseByCode(code).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | [**Object**](.md)|  | 

### Return type

[**GKnowledgeBase**](GKnowledgeBase.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findKnowledgeBasesByQbe"></a>
# **findKnowledgeBasesByQbe**
> Object findKnowledgeBasesByQbe(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.KnowledgeBaseControllerApi();
let body = new BrainClient.GKnowledgeBase(); // GKnowledgeBase | 

apiInstance.findKnowledgeBasesByQbe(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GKnowledgeBase**](GKnowledgeBase.md)|  | 

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getChildKnowledgeBases"></a>
# **getChildKnowledgeBases**
> Object getChildKnowledgeBases(code)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.KnowledgeBaseControllerApi();
let code = null; // Object | 

apiInstance.getChildKnowledgeBases(code).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | [**Object**](.md)|  | 

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getKnowledgeBases"></a>
# **getKnowledgeBases**
> Object getKnowledgeBases()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.KnowledgeBaseControllerApi();
apiInstance.getKnowledgeBases().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="insertKnowledgeBase"></a>
# **insertKnowledgeBase**
> GKnowledgeBase insertKnowledgeBase(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.KnowledgeBaseControllerApi();
let body = new BrainClient.GKnowledgeBase(); // GKnowledgeBase | 

apiInstance.insertKnowledgeBase(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GKnowledgeBase**](GKnowledgeBase.md)|  | 

### Return type

[**GKnowledgeBase**](GKnowledgeBase.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateKnowledgeBase"></a>
# **updateKnowledgeBase**
> GKnowledgeBase updateKnowledgeBase(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.KnowledgeBaseControllerApi();
let body = new BrainClient.GKnowledgeBase(); // GKnowledgeBase | 

apiInstance.updateKnowledgeBase(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GKnowledgeBase**](GKnowledgeBase.md)|  | 

### Return type

[**GKnowledgeBase**](GKnowledgeBase.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

