# GeboAiClient.ChatModelsLookupControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getChatModelTypesLookup**](ChatModelsLookupControllerApi.md#getChatModelTypesLookup) | **GET** /api/users/ChatModelsLookupController/getChatModelTypesLookup | 
[**getDefaultChatModel**](ChatModelsLookupControllerApi.md#getDefaultChatModel) | **GET** /api/users/ChatModelsLookupController/getDefaultChatModel | 
[**getRuntimeConfiguredChatModelsLookup**](ChatModelsLookupControllerApi.md#getRuntimeConfiguredChatModelsLookup) | **GET** /api/users/ChatModelsLookupController/getRuntimeConfiguredChatModelsLookup | 

<a name="getChatModelTypesLookup"></a>
# **getChatModelTypesLookup**
> [GLookupEntry] getChatModelTypesLookup()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ChatModelsLookupControllerApi();
apiInstance.getChatModelTypesLookup().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GLookupEntry]**](GLookupEntry.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getDefaultChatModel"></a>
# **getDefaultChatModel**
> GLookupEntryRefGBaseChatModelConfig getDefaultChatModel()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ChatModelsLookupControllerApi();
apiInstance.getDefaultChatModel().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**GLookupEntryRefGBaseChatModelConfig**](GLookupEntryRefGBaseChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getRuntimeConfiguredChatModelsLookup"></a>
# **getRuntimeConfiguredChatModelsLookup**
> [GLookupEntryRefGBaseChatModelConfig] getRuntimeConfiguredChatModelsLookup(opts)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ChatModelsLookupControllerApi();
let opts = { 
  'modelTypeCode': "modelTypeCode_example" // String | 
};
apiInstance.getRuntimeConfiguredChatModelsLookup(opts).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **modelTypeCode** | **String**|  | [optional] 

### Return type

[**[GLookupEntryRefGBaseChatModelConfig]**](GLookupEntryRefGBaseChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

