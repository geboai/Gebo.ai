# BrainClient.RankerModelsControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getRankerModelTypes**](RankerModelsControllerApi.md#getRankerModelTypes) | **GET** /api/admin/RankerModelsController/getRankerModelTypes | 
[**getRuntimeConfiguredRankerModels**](RankerModelsControllerApi.md#getRuntimeConfiguredRankerModels) | **GET** /api/admin/RankerModelsController/getRuntimeConfiguredRankerModels | 

<a name="getRankerModelTypes"></a>
# **getRankerModelTypes**
> [GRankerModelType] getRankerModelTypes()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.RankerModelsControllerApi();
apiInstance.getRankerModelTypes().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GRankerModelType]**](GRankerModelType.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getRuntimeConfiguredRankerModels"></a>
# **getRuntimeConfiguredRankerModels**
> [ConfigurationEntryGBaseRankerModelConfig] getRuntimeConfiguredRankerModels(opts)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.RankerModelsControllerApi();
let opts = { 
  'modelTypeCode': "modelTypeCode_example" // String | 
};
apiInstance.getRuntimeConfiguredRankerModels(opts).then((data) => {
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

[**[ConfigurationEntryGBaseRankerModelConfig]**](ConfigurationEntryGBaseRankerModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

