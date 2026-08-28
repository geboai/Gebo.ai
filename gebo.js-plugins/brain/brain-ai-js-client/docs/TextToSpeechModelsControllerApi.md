# BrainClient.TextToSpeechModelsControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getRuntimeConfiguredTextToSpeechModels**](TextToSpeechModelsControllerApi.md#getRuntimeConfiguredTextToSpeechModels) | **GET** /api/admin/TextToSpeechModelsController/getRuntimeConfiguredTextToSpeechModels | 
[**getTextToSpeechModelTypes**](TextToSpeechModelsControllerApi.md#getTextToSpeechModelTypes) | **GET** /api/admin/TextToSpeechModelsController/getTextToSpeechModelTypes | 

<a name="getRuntimeConfiguredTextToSpeechModels"></a>
# **getRuntimeConfiguredTextToSpeechModels**
> Object getRuntimeConfiguredTextToSpeechModels(opts)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.TextToSpeechModelsControllerApi();
let opts = { 
  'modelTypeCode': null // Object | 
};
apiInstance.getRuntimeConfiguredTextToSpeechModels(opts).then((data) => {
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

<a name="getTextToSpeechModelTypes"></a>
# **getTextToSpeechModelTypes**
> Object getTextToSpeechModelTypes()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.TextToSpeechModelsControllerApi();
apiInstance.getTextToSpeechModelTypes().then((data) => {
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

