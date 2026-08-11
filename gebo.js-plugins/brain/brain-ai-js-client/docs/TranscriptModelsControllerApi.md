# BrainClient.TranscriptModelsControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getRuntimeConfiguredTranscriptModels**](TranscriptModelsControllerApi.md#getRuntimeConfiguredTranscriptModels) | **GET** /api/admin/TranscriptModelsController/getRuntimeConfiguredTranscriptModels | 
[**getTranscriptModelTypes**](TranscriptModelsControllerApi.md#getTranscriptModelTypes) | **GET** /api/admin/TranscriptModelsController/getTranscriptModelTypes | 

<a name="getRuntimeConfiguredTranscriptModels"></a>
# **getRuntimeConfiguredTranscriptModels**
> Object getRuntimeConfiguredTranscriptModels(opts)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.TranscriptModelsControllerApi();
let opts = { 
  'modelTypeCode': null // Object | 
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
 **modelTypeCode** | [**Object**](.md)|  | [optional] 

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getTranscriptModelTypes"></a>
# **getTranscriptModelTypes**
> Object getTranscriptModelTypes()



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

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

