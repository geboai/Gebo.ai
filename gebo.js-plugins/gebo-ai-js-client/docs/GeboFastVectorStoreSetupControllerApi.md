# GeboAiClient.GeboFastVectorStoreSetupControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createVectorStoreConfiguration**](GeboFastVectorStoreSetupControllerApi.md#createVectorStoreConfiguration) | **POST** /api/admin/GeboFastVectorStoreSetupController/createVectorStoreConfiguration | 
[**getVectorStoreStatus**](GeboFastVectorStoreSetupControllerApi.md#getVectorStoreStatus) | **GET** /api/admin/GeboFastVectorStoreSetupController/getVectorStoreStatus | 

<a name="createVectorStoreConfiguration"></a>
# **createVectorStoreConfiguration**
> OperationStatusComponentVectorStoreStatus createVectorStoreConfiguration(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboFastVectorStoreSetupControllerApi();
let body = new GeboAiClient.FastVectorStoreSetupData(); // FastVectorStoreSetupData | 

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
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboFastVectorStoreSetupControllerApi();
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

