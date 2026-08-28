# BrainClient.GeboUserChatsControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**changeChatDescription**](GeboUserChatsControllerApi.md#changeChatDescription) | **POST** /api/users/GeboUserChatsController/changeChatDescription | 
[**createCleanChatByChatProfileCode**](GeboUserChatsControllerApi.md#createCleanChatByChatProfileCode) | **GET** /api/users/GeboUserChatsController/createCleanChatByChatProfileCode | 
[**createCleanChatByModelCode**](GeboUserChatsControllerApi.md#createCleanChatByModelCode) | **GET** /api/users/GeboUserChatsController/createCleanChatByModelCode | 
[**deleteChat**](GeboUserChatsControllerApi.md#deleteChat) | **DELETE** /api/users/GeboUserChatsController/deleteChat | 
[**exportResponse2file**](GeboUserChatsControllerApi.md#exportResponse2file) | **GET** /api/users/GeboUserChatsController/exportResponse2file | 
[**getChatHistory**](GeboUserChatsControllerApi.md#getChatHistory) | **GET** /api/users/GeboUserChatsController/getChatHistory | 
[**getChatInfosByCode**](GeboUserChatsControllerApi.md#getChatInfosByCode) | **GET** /api/users/GeboUserChatsController/getChatInfosByCode | 
[**getChatInfosByQbe**](GeboUserChatsControllerApi.md#getChatInfosByQbe) | **POST** /api/users/GeboUserChatsController/getChatInfosByQbe | 
[**getMyChats**](GeboUserChatsControllerApi.md#getMyChats) | **GET** /api/users/GeboUserChatsController/getMyChats | 
[**getMyChatsPaged**](GeboUserChatsControllerApi.md#getMyChatsPaged) | **GET** /api/users/GeboUserChatsController/getMyChatsPaged | 
[**getUIConfig**](GeboUserChatsControllerApi.md#getUIConfig) | **GET** /api/users/GeboUserChatsController/getUIConfig | 
[**isMinimalLLMSSetupDone**](GeboUserChatsControllerApi.md#isMinimalLLMSSetupDone) | **GET** /api/users/GeboUserChatsController/isMinimalLLMSSetupDone | 
[**suggestChatDescription**](GeboUserChatsControllerApi.md#suggestChatDescription) | **GET** /api/users/GeboUserChatsController/suggestChatDescription | 

<a name="changeChatDescription"></a>
# **changeChatDescription**
> GLookupEntry changeChatDescription(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboUserChatsControllerApi();
let body = new BrainClient.GLookupEntry(); // GLookupEntry | 

apiInstance.changeChatDescription(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GLookupEntry**](GLookupEntry.md)|  | 

### Return type

[**GLookupEntry**](GLookupEntry.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createCleanChatByChatProfileCode"></a>
# **createCleanChatByChatProfileCode**
> GUserChatInfo createCleanChatByChatProfileCode(chatProfileCode)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboUserChatsControllerApi();
let chatProfileCode = null; // Object | 

apiInstance.createCleanChatByChatProfileCode(chatProfileCode).then((data) => {
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

[**GUserChatInfo**](GUserChatInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="createCleanChatByModelCode"></a>
# **createCleanChatByModelCode**
> GUserChatInfo createCleanChatByModelCode(modelCode)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboUserChatsControllerApi();
let modelCode = null; // Object | 

apiInstance.createCleanChatByModelCode(modelCode).then((data) => {
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

[**GUserChatInfo**](GUserChatInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="deleteChat"></a>
# **deleteChat**
> deleteChat(userChatContextCode)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboUserChatsControllerApi();
let userChatContextCode = null; // Object | 

apiInstance.deleteChat(userChatContextCode).then(() => {
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

<a name="exportResponse2file"></a>
# **exportResponse2file**
> exportResponse2file(userContextCode, responseId, format)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboUserChatsControllerApi();
let userContextCode = null; // Object | 
let responseId = null; // Object | 
let format = null; // Object | 

apiInstance.exportResponse2file(userContextCode, responseId, format).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userContextCode** | [**Object**](.md)|  | 
 **responseId** | [**Object**](.md)|  | 
 **format** | [**Object**](.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="getChatHistory"></a>
# **getChatHistory**
> UserChatHistory getChatHistory(code)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboUserChatsControllerApi();
let code = null; // Object | 

apiInstance.getChatHistory(code).then((data) => {
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

[**UserChatHistory**](UserChatHistory.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getChatInfosByCode"></a>
# **getChatInfosByCode**
> GUserChatInfo getChatInfosByCode(id)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboUserChatsControllerApi();
let id = null; // Object | 

apiInstance.getChatInfosByCode(id).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | [**Object**](.md)|  | 

### Return type

[**GUserChatInfo**](GUserChatInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getChatInfosByQbe"></a>
# **getChatInfosByQbe**
> PageGUserChatInfo getChatInfosByQbe(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboUserChatsControllerApi();
let body = new BrainClient.ChatInfosByQbeParam(); // ChatInfosByQbeParam | 

apiInstance.getChatInfosByQbe(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ChatInfosByQbeParam**](ChatInfosByQbeParam.md)|  | 

### Return type

[**PageGUserChatInfo**](PageGUserChatInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMyChats"></a>
# **getMyChats**
> Object getMyChats()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboUserChatsControllerApi();
apiInstance.getMyChats().then((data) => {
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

<a name="getMyChatsPaged"></a>
# **getMyChatsPaged**
> PageGUserChatInfo getMyChatsPaged(page, pageSize)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboUserChatsControllerApi();
let page = null; // Object | 
let pageSize = null; // Object | 

apiInstance.getMyChatsPaged(page, pageSize).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **page** | [**Object**](.md)|  | 
 **pageSize** | [**Object**](.md)|  | 

### Return type

[**PageGUserChatInfo**](PageGUserChatInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getUIConfig"></a>
# **getUIConfig**
> ChatUIOptions getUIConfig()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboUserChatsControllerApi();
apiInstance.getUIConfig().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**ChatUIOptions**](ChatUIOptions.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="isMinimalLLMSSetupDone"></a>
# **isMinimalLLMSSetupDone**
> Object isMinimalLLMSSetupDone()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboUserChatsControllerApi();
apiInstance.isMinimalLLMSSetupDone().then((data) => {
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

<a name="suggestChatDescription"></a>
# **suggestChatDescription**
> GUserChatInfo suggestChatDescription(userChatContextCode)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboUserChatsControllerApi();
let userChatContextCode = null; // Object | 

apiInstance.suggestChatDescription(userChatContextCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userChatContextCode** | [**Object**](.md)|  | 

### Return type

[**GUserChatInfo**](GUserChatInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

