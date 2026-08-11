# GeboAiClient.GeboUserChatsControllerApi

All URIs are relative to *http://localhost:12999*

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
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboUserChatsControllerApi();
let body = new GeboAiClient.GLookupEntry(); // GLookupEntry | 

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
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboUserChatsControllerApi();
let chatProfileCode = "chatProfileCode_example"; // String | 

apiInstance.createCleanChatByChatProfileCode(chatProfileCode).then((data) => {
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
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboUserChatsControllerApi();
let modelCode = "modelCode_example"; // String | 

apiInstance.createCleanChatByModelCode(modelCode).then((data) => {
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
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboUserChatsControllerApi();
let userChatContextCode = "userChatContextCode_example"; // String | 

apiInstance.deleteChat(userChatContextCode).then(() => {
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

<a name="exportResponse2file"></a>
# **exportResponse2file**
> exportResponse2file(userContextCode, responseId, format)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboUserChatsControllerApi();
let userContextCode = "userContextCode_example"; // String | 
let responseId = "responseId_example"; // String | 
let format = "format_example"; // String | 

apiInstance.exportResponse2file(userContextCode, responseId, format).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userContextCode** | **String**|  | 
 **responseId** | **String**|  | 
 **format** | **String**|  | 

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
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboUserChatsControllerApi();
let code = "code_example"; // String | 

apiInstance.getChatHistory(code).then((data) => {
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
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboUserChatsControllerApi();
let id = "id_example"; // String | 

apiInstance.getChatInfosByCode(id).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  | 

### Return type

[**GUserChatInfo**](GUserChatInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getChatInfosByQbe"></a>
# **getChatInfosByQbe**
> PagedModelGUserChatInfo getChatInfosByQbe(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboUserChatsControllerApi();
let body = new GeboAiClient.ChatInfosByQbeParam(); // ChatInfosByQbeParam | 

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

[**PagedModelGUserChatInfo**](PagedModelGUserChatInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getMyChats"></a>
# **getMyChats**
> [GUserChatInfo] getMyChats()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboUserChatsControllerApi();
apiInstance.getMyChats().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GUserChatInfo]**](GUserChatInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getMyChatsPaged"></a>
# **getMyChatsPaged**
> PagedModelGUserChatInfo getMyChatsPaged(page, pageSize)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboUserChatsControllerApi();
let page = 56; // Number | 
let pageSize = 56; // Number | 

apiInstance.getMyChatsPaged(page, pageSize).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **page** | **Number**|  | 
 **pageSize** | **Number**|  | 

### Return type

[**PagedModelGUserChatInfo**](PagedModelGUserChatInfo.md)

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
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboUserChatsControllerApi();
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
> &#x27;Boolean&#x27; isMinimalLLMSSetupDone()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboUserChatsControllerApi();
apiInstance.isMinimalLLMSSetupDone().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

**&#x27;Boolean&#x27;**

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
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboUserChatsControllerApi();
let userChatContextCode = "userChatContextCode_example"; // String | 

apiInstance.suggestChatDescription(userChatContextCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userChatContextCode** | **String**|  | 

### Return type

[**GUserChatInfo**](GUserChatInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

