# BrainClient.ChatModelsControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getChatModelTypes**](ChatModelsControllerApi.md#getChatModelTypes) | **GET** /api/admin/ChatModelsController/getChatModelTypes | 
[**getRuntimeConfiguredChatModels**](ChatModelsControllerApi.md#getRuntimeConfiguredChatModels) | **GET** /api/admin/ChatModelsController/getRuntimeConfiguredChatModels | 

<a name="getChatModelTypes"></a>
# **getChatModelTypes**
> Object getChatModelTypes()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.ChatModelsControllerApi();
apiInstance.getChatModelTypes().then((data) => {
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

<a name="getRuntimeConfiguredChatModels"></a>
# **getRuntimeConfiguredChatModels**
> Object getRuntimeConfiguredChatModels(opts)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.ChatModelsControllerApi();
let opts = { 
  'modelTypeCode': null // Object | 
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
 **modelTypeCode** | [**Object**](.md)|  | [optional] 

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

