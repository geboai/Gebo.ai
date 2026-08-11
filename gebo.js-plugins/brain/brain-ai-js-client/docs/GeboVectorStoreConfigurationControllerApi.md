# BrainClient.GeboVectorStoreConfigurationControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getActualVectorStoreConfiguration**](GeboVectorStoreConfigurationControllerApi.md#getActualVectorStoreConfiguration) | **GET** /api/admin/GeboVectorStoreConfigurationController/getActualVectorStoreConfiguration | 
[**vectorStoreConfigurationApplyAndSave**](GeboVectorStoreConfigurationControllerApi.md#vectorStoreConfigurationApplyAndSave) | **POST** /api/admin/GeboVectorStoreConfigurationController/vectorStoreConfigurationApplyAndSave | 

<a name="getActualVectorStoreConfiguration"></a>
# **getActualVectorStoreConfiguration**
> GeboMongoVectorStoreConfig getActualVectorStoreConfiguration()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboVectorStoreConfigurationControllerApi();
apiInstance.getActualVectorStoreConfiguration().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**GeboMongoVectorStoreConfig**](GeboMongoVectorStoreConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="vectorStoreConfigurationApplyAndSave"></a>
# **vectorStoreConfigurationApplyAndSave**
> OperationStatusGeboMongoVectorStoreConfig vectorStoreConfigurationApplyAndSave(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboVectorStoreConfigurationControllerApi();
let body = new BrainClient.GeboMongoVectorStoreConfig(); // GeboMongoVectorStoreConfig | 

apiInstance.vectorStoreConfigurationApplyAndSave(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GeboMongoVectorStoreConfig**](GeboMongoVectorStoreConfig.md)|  | 

### Return type

[**OperationStatusGeboMongoVectorStoreConfig**](OperationStatusGeboMongoVectorStoreConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

