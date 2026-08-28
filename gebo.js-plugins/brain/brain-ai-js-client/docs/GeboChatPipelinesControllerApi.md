# BrainClient.GeboChatPipelinesControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**executeChatPipeline**](GeboChatPipelinesControllerApi.md#executeChatPipeline) | **POST** /api/users/GeboChatPipelinesController/executeChatPipeline | 
[**executeDefaultChatPipeline**](GeboChatPipelinesControllerApi.md#executeDefaultChatPipeline) | **POST** /api/users/GeboChatPipelinesController/executeDefaultChatPipeline | 
[**getDefaultPersonalPipelinesChatMenu**](GeboChatPipelinesControllerApi.md#getDefaultPersonalPipelinesChatMenu) | **GET** /api/users/GeboChatPipelinesController/defaultPersonalPipelinesChatMenu | 
[**getPersonalPipelinesChatMenu**](GeboChatPipelinesControllerApi.md#getPersonalPipelinesChatMenu) | **GET** /api/users/GeboChatPipelinesController/personalPipelinesChatMenu | 
[**stopChatPipeline**](GeboChatPipelinesControllerApi.md#stopChatPipeline) | **GET** /api/users/GeboChatPipelinesController/stopChatPipeline | 
[**streamChatPipeline**](GeboChatPipelinesControllerApi.md#streamChatPipeline) | **POST** /api/users/GeboChatPipelinesController/streamChatPipeline | 
[**streamDefaultChatPipeline**](GeboChatPipelinesControllerApi.md#streamDefaultChatPipeline) | **POST** /api/users/GeboChatPipelinesController/streamDefaultChatPipeline | 

<a name="executeChatPipeline"></a>
# **executeChatPipeline**
> GeboChatResponse executeChatPipeline(body, opts)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboChatPipelinesControllerApi();
let body = new BrainClient.PipelineRequestBody(); // PipelineRequestBody | 
let opts = { 
  'pipelineCode': null // Object | 
};
apiInstance.executeChatPipeline(body, opts).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**PipelineRequestBody**](PipelineRequestBody.md)|  | 
 **pipelineCode** | [**Object**](.md)|  | [optional] 

### Return type

[**GeboChatResponse**](GeboChatResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="executeDefaultChatPipeline"></a>
# **executeDefaultChatPipeline**
> GeboChatResponse executeDefaultChatPipeline(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboChatPipelinesControllerApi();
let body = new BrainClient.PipelineRequestBody(); // PipelineRequestBody | 

apiInstance.executeDefaultChatPipeline(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**PipelineRequestBody**](PipelineRequestBody.md)|  | 

### Return type

[**GeboChatResponse**](GeboChatResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getDefaultPersonalPipelinesChatMenu"></a>
# **getDefaultPersonalPipelinesChatMenu**
> Object getDefaultPersonalPipelinesChatMenu(chatProfileCode)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboChatPipelinesControllerApi();
let chatProfileCode = null; // Object | 

apiInstance.getDefaultPersonalPipelinesChatMenu(chatProfileCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **chatProfileCode** | [**Object**](.md)|  | 

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getPersonalPipelinesChatMenu"></a>
# **getPersonalPipelinesChatMenu**
> Object getPersonalPipelinesChatMenu(chatProfileCode, opts)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboChatPipelinesControllerApi();
let chatProfileCode = null; // Object | 
let opts = { 
  'pipelineCode': null // Object | 
};
apiInstance.getPersonalPipelinesChatMenu(chatProfileCode, opts).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **chatProfileCode** | [**Object**](.md)|  | 
 **pipelineCode** | [**Object**](.md)|  | [optional] 

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="stopChatPipeline"></a>
# **stopChatPipeline**
> stopChatPipeline(userChatContextCode)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboChatPipelinesControllerApi();
let userChatContextCode = null; // Object | 

apiInstance.stopChatPipeline(userChatContextCode).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userChatContextCode** | [**Object**](.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="streamChatPipeline"></a>
# **streamChatPipeline**
> Object streamChatPipeline(body, opts)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboChatPipelinesControllerApi();
let body = new BrainClient.PipelineRequestBody(); // PipelineRequestBody | 
let opts = { 
  'pipelineCode': null // Object | 
};
apiInstance.streamChatPipeline(body, opts).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**PipelineRequestBody**](PipelineRequestBody.md)|  | 
 **pipelineCode** | [**Object**](.md)|  | [optional] 

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: text/event-stream

<a name="streamDefaultChatPipeline"></a>
# **streamDefaultChatPipeline**
> Object streamDefaultChatPipeline(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboChatPipelinesControllerApi();
let body = new BrainClient.PipelineRequestBody(); // PipelineRequestBody | 

apiInstance.streamDefaultChatPipeline(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**PipelineRequestBody**](PipelineRequestBody.md)|  | 

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: text/event-stream

