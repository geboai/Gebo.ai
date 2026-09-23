# BrainClient.TranscriptModelsControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getRuntimeConfiguredTranscriptModels**](TranscriptModelsControllerApi.md#getRuntimeConfiguredTranscriptModels) | **GET** /api/admin/TranscriptModelsController/getRuntimeConfiguredTranscriptModels | 
[**getTranscriptModelTypes**](TranscriptModelsControllerApi.md#getTranscriptModelTypes) | **GET** /api/admin/TranscriptModelsController/getTranscriptModelTypes | 

<a name="getRuntimeConfiguredTranscriptModels"></a>
# **getRuntimeConfiguredTranscriptModels**
> [ConfigurationEntryGBaseTranscriptModelConfig] getRuntimeConfiguredTranscriptModels(opts)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.TranscriptModelsControllerApi();
let opts = { 
  'modelTypeCode': "modelTypeCode_example" // String | 
};
apiInstance.getRuntimeConfiguredTranscriptModels(opts).then((data) => {
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

[**[ConfigurationEntryGBaseTranscriptModelConfig]**](ConfigurationEntryGBaseTranscriptModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getTranscriptModelTypes"></a>
# **getTranscriptModelTypes**
> [GTranscriptModelType] getTranscriptModelTypes()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.TranscriptModelsControllerApi();
apiInstance.getTranscriptModelTypes().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GTranscriptModelType]**](GTranscriptModelType.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

