# BrainClient.ImageModelsControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getImageModelTypes**](ImageModelsControllerApi.md#getImageModelTypes) | **GET** /api/admin/ImageModelsController/getImageModelTypes | 
[**getRuntimeConfiguredImageModels**](ImageModelsControllerApi.md#getRuntimeConfiguredImageModels) | **GET** /api/admin/ImageModelsController/getRuntimeConfiguredImageModels | 

<a name="getImageModelTypes"></a>
# **getImageModelTypes**
> Object getImageModelTypes()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.ImageModelsControllerApi();
apiInstance.getImageModelTypes().then((data) => {
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

<a name="getRuntimeConfiguredImageModels"></a>
# **getRuntimeConfiguredImageModels**
> Object getRuntimeConfiguredImageModels(opts)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.ImageModelsControllerApi();
let opts = { 
  'modelTypeCode': null // Object | 
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
 **modelTypeCode** | [**Object**](.md)|  | [optional] 

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

