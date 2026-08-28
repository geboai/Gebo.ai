# GeboAiClient.ChatModelsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getChatModelTypes**](ChatModelsControllerApi.md#getChatModelTypes) | **GET** /api/admin/ChatModelsController/getChatModelTypes | 
[**getRuntimeConfiguredChatModels**](ChatModelsControllerApi.md#getRuntimeConfiguredChatModels) | **GET** /api/admin/ChatModelsController/getRuntimeConfiguredChatModels | 

<a name="getChatModelTypes"></a>
# **getChatModelTypes**
> [GChatModelType] getChatModelTypes()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ChatModelsControllerApi();
apiInstance.getChatModelTypes().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GChatModelType]**](GChatModelType.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getRuntimeConfiguredChatModels"></a>
# **getRuntimeConfiguredChatModels**
> [ConfigurationEntryGBaseChatModelConfig] getRuntimeConfiguredChatModels(opts)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ChatModelsControllerApi();
let opts = { 
  'modelTypeCode': "modelTypeCode_example" // String | 
};
apiInstance.getRuntimeConfiguredChatModels(opts).then((data) => {
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

[**[ConfigurationEntryGBaseChatModelConfig]**](ConfigurationEntryGBaseChatModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

