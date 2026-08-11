# GeboAiClient.GeboChatPipelinesControllerApi

All URIs are relative to *http://localhost:12999*

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
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboChatPipelinesControllerApi();
let body = new GeboAiClient.PipelineRequestBody(); // PipelineRequestBody | 
let opts = { 
  'pipelineCode': "pipelineCode_example" // String | 
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
 **pipelineCode** | **String**|  | [optional] 

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
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboChatPipelinesControllerApi();
let body = new GeboAiClient.PipelineRequestBody(); // PipelineRequestBody | 

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
> [PipelineChatMenu] getDefaultPersonalPipelinesChatMenu(chatProfileCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboChatPipelinesControllerApi();
let chatProfileCode = "chatProfileCode_example"; // String | 

apiInstance.getDefaultPersonalPipelinesChatMenu(chatProfileCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **chatProfileCode** | **String**|  | 

### Return type

[**[PipelineChatMenu]**](PipelineChatMenu.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getPersonalPipelinesChatMenu"></a>
# **getPersonalPipelinesChatMenu**
> [PipelineChatMenu] getPersonalPipelinesChatMenu(chatProfileCode, opts)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboChatPipelinesControllerApi();
let chatProfileCode = "chatProfileCode_example"; // String | 
let opts = { 
  'pipelineCode': "pipelineCode_example" // String | 
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
 **chatProfileCode** | **String**|  | 
 **pipelineCode** | **String**|  | [optional] 

### Return type

[**[PipelineChatMenu]**](PipelineChatMenu.md)

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
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboChatPipelinesControllerApi();
let userChatContextCode = "userChatContextCode_example"; // String | 

apiInstance.stopChatPipeline(userChatContextCode).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userChatContextCode** | **String**|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="streamChatPipeline"></a>
# **streamChatPipeline**
> [GeboChatMessageEnvelope] streamChatPipeline(body, opts)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboChatPipelinesControllerApi();
let body = new GeboAiClient.PipelineRequestBody(); // PipelineRequestBody | 
let opts = { 
  'pipelineCode': "pipelineCode_example" // String | 
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
 **pipelineCode** | **String**|  | [optional] 

### Return type

[**[GeboChatMessageEnvelope]**](GeboChatMessageEnvelope.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: text/event-stream

<a name="streamDefaultChatPipeline"></a>
# **streamDefaultChatPipeline**
> [GeboChatMessageEnvelope] streamDefaultChatPipeline(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboChatPipelinesControllerApi();
let body = new GeboAiClient.PipelineRequestBody(); // PipelineRequestBody | 

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

[**[GeboChatMessageEnvelope]**](GeboChatMessageEnvelope.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: text/event-stream

