# BrainClient.EmbeddingModelsControllersApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getEmbeddingModelTypes**](EmbeddingModelsControllersApi.md#getEmbeddingModelTypes) | **GET** /api/admin/EmbeddingModelsControllers/getEmbeddingModelTypes | 
[**getRuntimeConfiguredEmbeddingModels**](EmbeddingModelsControllersApi.md#getRuntimeConfiguredEmbeddingModels) | **GET** /api/admin/EmbeddingModelsControllers/getRuntimeConfiguredEmbeddingModels | 

<a name="getEmbeddingModelTypes"></a>
# **getEmbeddingModelTypes**
> Object getEmbeddingModelTypes()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.EmbeddingModelsControllersApi();
apiInstance.getEmbeddingModelTypes().then((data) => {
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

<a name="getRuntimeConfiguredEmbeddingModels"></a>
# **getRuntimeConfiguredEmbeddingModels**
> Object getRuntimeConfiguredEmbeddingModels(opts)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.EmbeddingModelsControllersApi();
let opts = { 
  'modelTypeCode': null // Object | 
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
 **modelTypeCode** | [**Object**](.md)|  | [optional] 

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

