# BrainClient.GeboFastVectorStoreSetupControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createVectorStoreConfiguration**](GeboFastVectorStoreSetupControllerApi.md#createVectorStoreConfiguration) | **POST** /api/admin/GeboFastVectorStoreSetupController/createVectorStoreConfiguration | 
[**getVectorStoreStatus**](GeboFastVectorStoreSetupControllerApi.md#getVectorStoreStatus) | **GET** /api/admin/GeboFastVectorStoreSetupController/getVectorStoreStatus | 

<a name="createVectorStoreConfiguration"></a>
# **createVectorStoreConfiguration**
> OperationStatusComponentVectorStoreStatus createVectorStoreConfiguration(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboFastVectorStoreSetupControllerApi();
let body = new BrainClient.FastVectorStoreSetupData(); // FastVectorStoreSetupData | 

apiInstance.createVectorStoreConfiguration(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**FastVectorStoreSetupData**](FastVectorStoreSetupData.md)|  | 

### Return type

[**OperationStatusComponentVectorStoreStatus**](OperationStatusComponentVectorStoreStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getVectorStoreStatus"></a>
# **getVectorStoreStatus**
> ComponentVectorStoreStatus getVectorStoreStatus()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboFastVectorStoreSetupControllerApi();
apiInstance.getVectorStoreStatus().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**ComponentVectorStoreStatus**](ComponentVectorStoreStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

