# GeboAiClient.TextToSpeechModelsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getRuntimeConfiguredTextToSpeechModels**](TextToSpeechModelsControllerApi.md#getRuntimeConfiguredTextToSpeechModels) | **GET** /api/admin/TextToSpeechModelsController/getRuntimeConfiguredTextToSpeechModels | 
[**getTextToSpeechModelTypes**](TextToSpeechModelsControllerApi.md#getTextToSpeechModelTypes) | **GET** /api/admin/TextToSpeechModelsController/getTextToSpeechModelTypes | 

<a name="getRuntimeConfiguredTextToSpeechModels"></a>
# **getRuntimeConfiguredTextToSpeechModels**
> [ConfigurationEntryGBaseTextToSpeachModelConfig] getRuntimeConfiguredTextToSpeechModels(opts)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.TextToSpeechModelsControllerApi();
let opts = { 
  'modelTypeCode': "modelTypeCode_example" // String | 
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
 **modelTypeCode** | **String**|  | [optional] 

### Return type

[**[ConfigurationEntryGBaseTextToSpeachModelConfig]**](ConfigurationEntryGBaseTextToSpeachModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getTextToSpeechModelTypes"></a>
# **getTextToSpeechModelTypes**
> [GTextToSpeechModelType] getTextToSpeechModelTypes()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.TextToSpeechModelsControllerApi();
apiInstance.getTextToSpeechModelTypes().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GTextToSpeechModelType]**](GTextToSpeechModelType.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

