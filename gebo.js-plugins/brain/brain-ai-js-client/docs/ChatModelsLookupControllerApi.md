# BrainClient.ChatModelsLookupControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getChatModelTypesLookup**](ChatModelsLookupControllerApi.md#getChatModelTypesLookup) | **GET** /api/users/ChatModelsLookupController/getChatModelTypesLookup | 
[**getDefaultChatModel**](ChatModelsLookupControllerApi.md#getDefaultChatModel) | **GET** /api/users/ChatModelsLookupController/getDefaultChatModel | 
[**getRuntimeConfiguredChatModelsLookup**](ChatModelsLookupControllerApi.md#getRuntimeConfiguredChatModelsLookup) | **GET** /api/users/ChatModelsLookupController/getRuntimeConfiguredChatModelsLookup | 

<a name="getChatModelTypesLookup"></a>
# **getChatModelTypesLookup**
> Object getChatModelTypesLookup()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.ChatModelsLookupControllerApi();
apiInstance.getChatModelTypesLookup().then((data) => {
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

<a name="getDefaultChatModel"></a>
# **getDefaultChatModel**
> GLookupEntryRefGBaseChatModelConfig getDefaultChatModel()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.ChatModelsLookupControllerApi();
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
> Object getRuntimeConfiguredChatModelsLookup(opts)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.ChatModelsLookupControllerApi();
let opts = { 
  'modelTypeCode': null // Object | 
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
 **modelTypeCode** | [**Object**](.md)|  | [optional] 

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

