# GeboAiClient.ImageModelsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getImageModelTypes**](ImageModelsControllerApi.md#getImageModelTypes) | **GET** /api/admin/ImageModelsController/getImageModelTypes | 
[**getRuntimeConfiguredImageModels**](ImageModelsControllerApi.md#getRuntimeConfiguredImageModels) | **GET** /api/admin/ImageModelsController/getRuntimeConfiguredImageModels | 

<a name="getImageModelTypes"></a>
# **getImageModelTypes**
> [GImageModelType] getImageModelTypes()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ImageModelsControllerApi();
apiInstance.getImageModelTypes().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GImageModelType]**](GImageModelType.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getRuntimeConfiguredImageModels"></a>
# **getRuntimeConfiguredImageModels**
> [ConfigurationEntryGBaseImageModelConfig] getRuntimeConfiguredImageModels(opts)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ImageModelsControllerApi();
let opts = { 
  'modelTypeCode': "modelTypeCode_example" // String | 
};
apiInstance.getRuntimeConfiguredImageModels(opts).then((data) => {
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

[**[ConfigurationEntryGBaseImageModelConfig]**](ConfigurationEntryGBaseImageModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

