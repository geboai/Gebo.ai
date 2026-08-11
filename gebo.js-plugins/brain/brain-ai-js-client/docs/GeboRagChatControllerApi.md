# BrainClient.GeboRagChatControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getChatModelUserInfoByChatProfileCode**](GeboRagChatControllerApi.md#getChatModelUserInfoByChatProfileCode) | **GET** /api/users/GeboChatController/getChatModelUserInfoByChatProfileCode | 
[**getChatProfileModelMetaInfos**](GeboRagChatControllerApi.md#getChatProfileModelMetaInfos) | **GET** /api/users/GeboChatController/getChatProfileModelMetaInfos | 
[**getChatProfiles**](GeboRagChatControllerApi.md#getChatProfiles) | **GET** /api/users/GeboChatController/profiles | 
[**getProfileProviderModelCapabilities**](GeboRagChatControllerApi.md#getProfileProviderModelCapabilities) | **GET** /api/users/GeboChatController/getProfileProviderModelCapabilities | 
[**getVisibleKnowledgeBasesByProfileCode**](GeboRagChatControllerApi.md#getVisibleKnowledgeBasesByProfileCode) | **GET** /api/users/GeboChatController/getVisibleKnowledgeBasesByProfileCode | 
[**ragChat**](GeboRagChatControllerApi.md#ragChat) | **POST** /api/users/GeboChatController/ragChat | 
[**streamRagResponse**](GeboRagChatControllerApi.md#streamRagResponse) | **POST** /api/users/GeboChatController/streamRagResponse | 

<a name="getChatModelUserInfoByChatProfileCode"></a>
# **getChatModelUserInfoByChatProfileCode**
> GeboChatUserInfo getChatModelUserInfoByChatProfileCode(chatProfileCode)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboRagChatControllerApi();
let chatProfileCode = null; // Object | 

apiInstance.getChatModelUserInfoByChatProfileCode(chatProfileCode).then((data) => {
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

[**GeboChatUserInfo**](GeboChatUserInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getChatProfileModelMetaInfos"></a>
# **getChatProfileModelMetaInfos**
> GBaseChatModelChoice getChatProfileModelMetaInfos(chatProfileCode)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboRagChatControllerApi();
let chatProfileCode = null; // Object | 

apiInstance.getChatProfileModelMetaInfos(chatProfileCode).then((data) => {
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

[**GBaseChatModelChoice**](GBaseChatModelChoice.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getChatProfiles"></a>
# **getChatProfiles**
> Object getChatProfiles()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboRagChatControllerApi();
apiInstance.getChatProfiles().then((data) => {
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
 - **Accept**: */*

<a name="getProfileProviderModelCapabilities"></a>
# **getProfileProviderModelCapabilities**
> ModelProviderCapabilities getProfileProviderModelCapabilities(chatProfileCode)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboRagChatControllerApi();
let chatProfileCode = null; // Object | 

apiInstance.getProfileProviderModelCapabilities(chatProfileCode).then((data) => {
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

[**ModelProviderCapabilities**](ModelProviderCapabilities.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getVisibleKnowledgeBasesByProfileCode"></a>
# **getVisibleKnowledgeBasesByProfileCode**
> Object getVisibleKnowledgeBasesByProfileCode(profileCode)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboRagChatControllerApi();
let profileCode = null; // Object | 

apiInstance.getVisibleKnowledgeBasesByProfileCode(profileCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **profileCode** | [**Object**](.md)|  | 

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="ragChat"></a>
# **ragChat**
> GeboChatResponse ragChat(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboRagChatControllerApi();
let body = new BrainClient.GeboChatRequest(); // GeboChatRequest | 

apiInstance.ragChat(body).then((data) => {
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

<a name="streamRagResponse"></a>
# **streamRagResponse**
> Object streamRagResponse(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboRagChatControllerApi();
let body = new BrainClient.GeboChatRequest(); // GeboChatRequest | 

apiInstance.streamRagResponse(body).then((data) => {
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

