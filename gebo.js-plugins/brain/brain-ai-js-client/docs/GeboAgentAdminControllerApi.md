# BrainClient.GeboAgentAdminControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteAgent**](GeboAgentAdminControllerApi.md#deleteAgent) | **DELETE** /api/admin/GeboAgentAdminController/deleteAgent | 
[**getAgentByCode**](GeboAgentAdminControllerApi.md#getAgentByCode) | **GET** /api/admin/GeboAgentAdminController/getAgentByCode | 
[**getAgents**](GeboAgentAdminControllerApi.md#getAgents) | **GET** /api/admin/GeboAgentAdminController/getAgents | 
[**getAgentsChoices**](GeboAgentAdminControllerApi.md#getAgentsChoices) | **GET** /api/admin/GeboAgentAdminController/getAgentsChoices | 
[**getPromptTemplatesByAgentId**](GeboAgentAdminControllerApi.md#getPromptTemplatesByAgentId) | **GET** /api/admin/GeboAgentAdminController/getPromptTemplateByAgentId | 
[**insertAgent**](GeboAgentAdminControllerApi.md#insertAgent) | **POST** /api/admin/GeboAgentAdminController/insertAgent | 
[**updateAgent**](GeboAgentAdminControllerApi.md#updateAgent) | **POST** /api/admin/GeboAgentAdminController/updateAgent | 

<a name="deleteAgent"></a>
# **deleteAgent**
> deleteAgent(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboAgentAdminControllerApi();
let body = new BrainClient.GAgentConfig(); // GAgentConfig | 

apiInstance.deleteAgent(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAgentConfig**](GAgentConfig.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="getAgentByCode"></a>
# **getAgentByCode**
> GAgentConfig getAgentByCode(code)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboAgentAdminControllerApi();
let code = null; // Object | 

apiInstance.getAgentByCode(code).then((data) => {
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

[**GAgentConfig**](GAgentConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAgents"></a>
# **getAgents**
> Object getAgents()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboAgentAdminControllerApi();
apiInstance.getAgents().then((data) => {
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

<a name="getAgentsChoices"></a>
# **getAgentsChoices**
> Object getAgentsChoices()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboAgentAdminControllerApi();
apiInstance.getAgentsChoices().then((data) => {
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

<a name="getPromptTemplatesByAgentId"></a>
# **getPromptTemplatesByAgentId**
> Object getPromptTemplatesByAgentId(agentId)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboAgentAdminControllerApi();
let agentId = null; // Object | 

apiInstance.getPromptTemplatesByAgentId(agentId).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **agentId** | [**Object**](.md)|  | 

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="insertAgent"></a>
# **insertAgent**
> GAgentConfig insertAgent(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboAgentAdminControllerApi();
let body = new BrainClient.GAgentConfig(); // GAgentConfig | 

apiInstance.insertAgent(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAgentConfig**](GAgentConfig.md)|  | 

### Return type

[**GAgentConfig**](GAgentConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateAgent"></a>
# **updateAgent**
> GAgentConfig updateAgent(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboAgentAdminControllerApi();
let body = new BrainClient.GAgentConfig(); // GAgentConfig | 

apiInstance.updateAgent(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GAgentConfig**](GAgentConfig.md)|  | 

### Return type

[**GAgentConfig**](GAgentConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

