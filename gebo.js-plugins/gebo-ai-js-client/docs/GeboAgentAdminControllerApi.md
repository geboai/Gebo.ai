# GeboAiClient.GeboAgentAdminControllerApi

All URIs are relative to *http://localhost:12999*

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
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboAgentAdminControllerApi();
let body = new GeboAiClient.GAgentConfig(); // GAgentConfig | 

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
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboAgentAdminControllerApi();
let code = "code_example"; // String | 

apiInstance.getAgentByCode(code).then((data) => {
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

[**GAgentConfig**](GAgentConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAgents"></a>
# **getAgents**
> [GBaseObject] getAgents()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboAgentAdminControllerApi();
apiInstance.getAgents().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GBaseObject]**](GBaseObject.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAgentsChoices"></a>
# **getAgentsChoices**
> [GBaseObject] getAgentsChoices()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboAgentAdminControllerApi();
apiInstance.getAgentsChoices().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GBaseObject]**](GBaseObject.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getPromptTemplatesByAgentId"></a>
# **getPromptTemplatesByAgentId**
> [GPromptTemplateConfig] getPromptTemplatesByAgentId(agentId)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboAgentAdminControllerApi();
let agentId = "agentId_example"; // String | 

apiInstance.getPromptTemplatesByAgentId(agentId).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **agentId** | **String**|  | 

### Return type

[**[GPromptTemplateConfig]**](GPromptTemplateConfig.md)

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
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboAgentAdminControllerApi();
let body = new GeboAiClient.GAgentConfig(); // GAgentConfig | 

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
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboAgentAdminControllerApi();
let body = new GeboAiClient.GAgentConfig(); // GAgentConfig | 

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

