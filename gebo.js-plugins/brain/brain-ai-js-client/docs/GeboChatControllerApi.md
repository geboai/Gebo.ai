# BrainClient.GeboChatControllerApi

All URIs are relative to *http://localhost:13001/brain*

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
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboChatControllerApi();
let body = new BrainClient.GeboChatRequest(); // GeboChatRequest | 

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
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboChatControllerApi();
let modelCode = null; // Object | 

apiInstance.getChatModelMetaInfos(modelCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **modelCode** | [**Object**](.md)|  | 

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
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboChatControllerApi();
let modelCode = null; // Object | 

apiInstance.getChatModelUserInfo(modelCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **modelCode** | [**Object**](.md)|  | 

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
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboChatControllerApi();
let modelCode = null; // Object | 

apiInstance.getProviderCapabilities(modelCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **modelCode** | [**Object**](.md)|  | 

### Return type

[**ModelProviderCapabilities**](ModelProviderCapabilities.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getVisibleKnowledgeBases"></a>
# **getVisibleKnowledgeBases**
> Object getVisibleKnowledgeBases()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboChatControllerApi();
apiInstance.getVisibleKnowledgeBases().then((data) => {
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

<a name="streamResponse"></a>
# **streamResponse**
> Object streamResponse(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboChatControllerApi();
let body = new BrainClient.GeboChatRequest(); // GeboChatRequest | 

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

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: text/event-stream

