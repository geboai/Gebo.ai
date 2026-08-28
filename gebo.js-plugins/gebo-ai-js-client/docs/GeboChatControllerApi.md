# GeboAiClient.GeboChatControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**chat**](GeboChatControllerApi.md#chat) | **POST** /api/users/GeboDirectModelChatController/chat | 
[**getChatModelMetaInfos**](GeboChatControllerApi.md#getChatModelMetaInfos) | **GET** /api/users/GeboDirectModelChatController/getChatModelMetaInfos | 
[**getChatModelUserInfo**](GeboChatControllerApi.md#getChatModelUserInfo) | **GET** /api/users/GeboDirectModelChatController/getChatModelUserInfo | 
[**getProviderCapabilities**](GeboChatControllerApi.md#getProviderCapabilities) | **GET** /api/users/GeboDirectModelChatController/getProviderCapabilities | 
[**getVisibleKnowledgeBases**](GeboChatControllerApi.md#getVisibleKnowledgeBases) | **GET** /api/users/GeboDirectModelChatController/getVisibleKnowledgeBases | 
[**streamResponse**](GeboChatControllerApi.md#streamResponse) | **POST** /api/users/GeboDirectModelChatController/streamResponse | 

<a name="chat"></a>
# **chat**
> GeboChatResponse chat(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboChatControllerApi();
let body = new GeboAiClient.GeboChatRequest(); // GeboChatRequest | 

apiInstance.chat(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GeboChatRequest**](GeboChatRequest.md)|  | 

### Return type

[**GeboChatResponse**](GeboChatResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getChatModelMetaInfos"></a>
# **getChatModelMetaInfos**
> GBaseChatModelChoice getChatModelMetaInfos(modelCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboChatControllerApi();
let modelCode = "modelCode_example"; // String | 

apiInstance.getChatModelMetaInfos(modelCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **modelCode** | **String**|  | 

### Return type

[**GBaseChatModelChoice**](GBaseChatModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getChatModelUserInfo"></a>
# **getChatModelUserInfo**
> GeboChatUserInfo getChatModelUserInfo(modelCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboChatControllerApi();
let modelCode = "modelCode_example"; // String | 

apiInstance.getChatModelUserInfo(modelCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **modelCode** | **String**|  | 

### Return type

[**GeboChatUserInfo**](GeboChatUserInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getProviderCapabilities"></a>
# **getProviderCapabilities**
> ModelProviderCapabilities getProviderCapabilities(modelCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboChatControllerApi();
let modelCode = "modelCode_example"; // String | 

apiInstance.getProviderCapabilities(modelCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **modelCode** | **String**|  | 

### Return type

[**ModelProviderCapabilities**](ModelProviderCapabilities.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getVisibleKnowledgeBases"></a>
# **getVisibleKnowledgeBases**
> [GBaseObject] getVisibleKnowledgeBases()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboChatControllerApi();
apiInstance.getVisibleKnowledgeBases().then((data) => {
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

<a name="streamResponse"></a>
# **streamResponse**
> [ServerSentEventString] streamResponse(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboChatControllerApi();
let body = new GeboAiClient.GeboChatRequest(); // GeboChatRequest | 

apiInstance.streamResponse(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GeboChatRequest**](GeboChatRequest.md)|  | 

### Return type

[**[ServerSentEventString]**](ServerSentEventString.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: text/event-stream

