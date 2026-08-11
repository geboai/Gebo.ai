# GeboAiClient.EmbeddingModelsControllersApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getEmbeddingModelTypes**](EmbeddingModelsControllersApi.md#getEmbeddingModelTypes) | **GET** /api/admin/EmbeddingModelsControllers/getEmbeddingModelTypes | 
[**getRuntimeConfiguredEmbeddingModels**](EmbeddingModelsControllersApi.md#getRuntimeConfiguredEmbeddingModels) | **GET** /api/admin/EmbeddingModelsControllers/getRuntimeConfiguredEmbeddingModels | 

<a name="getEmbeddingModelTypes"></a>
# **getEmbeddingModelTypes**
> [GEmbeddingModelType] getEmbeddingModelTypes()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.EmbeddingModelsControllersApi();
apiInstance.getEmbeddingModelTypes().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GEmbeddingModelType]**](GEmbeddingModelType.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getRuntimeConfiguredEmbeddingModels"></a>
# **getRuntimeConfiguredEmbeddingModels**
> [ConfigurationEntryGBaseEmbeddingModelConfig] getRuntimeConfiguredEmbeddingModels(opts)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.EmbeddingModelsControllersApi();
let opts = { 
  'modelTypeCode': "modelTypeCode_example" // String | 
};
apiInstance.getRuntimeConfiguredEmbeddingModels(opts).then((data) => {
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

[**[ConfigurationEntryGBaseEmbeddingModelConfig]**](ConfigurationEntryGBaseEmbeddingModelConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

