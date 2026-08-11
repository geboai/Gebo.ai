# GeboAiClient.GeboFastWorkFolderSetupControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**configureWorkDirectory**](GeboFastWorkFolderSetupControllerApi.md#configureWorkDirectory) | **POST** /api/admin/GeboFastWorkFolderSetupController/configureWorkDirectory | 
[**getWorkDirectorySetupEnabled**](GeboFastWorkFolderSetupControllerApi.md#getWorkDirectorySetupEnabled) | **GET** /api/admin/GeboFastWorkFolderSetupController/getWorkDirectorySetupEnabled | 
[**getWorkDirectorySetupStatus**](GeboFastWorkFolderSetupControllerApi.md#getWorkDirectorySetupStatus) | **GET** /api/admin/GeboFastWorkFolderSetupController/getWorkDirectorySetupStatus | 

<a name="configureWorkDirectory"></a>
# **configureWorkDirectory**
> OperationStatusWorkFolderSetupStatus configureWorkDirectory(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboFastWorkFolderSetupControllerApi();
let body = new GeboAiClient.FastWorkDirectorySetupData(); // FastWorkDirectorySetupData | 

apiInstance.configureWorkDirectory(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**FastWorkDirectorySetupData**](FastWorkDirectorySetupData.md)|  | 

### Return type

[**OperationStatusWorkFolderSetupStatus**](OperationStatusWorkFolderSetupStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getWorkDirectorySetupEnabled"></a>
# **getWorkDirectorySetupEnabled**
> ComponentEnabledStatus getWorkDirectorySetupEnabled()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboFastWorkFolderSetupControllerApi();
apiInstance.getWorkDirectorySetupEnabled().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**ComponentEnabledStatus**](ComponentEnabledStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getWorkDirectorySetupStatus"></a>
# **getWorkDirectorySetupStatus**
> WorkFolderSetupStatus getWorkDirectorySetupStatus()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboFastWorkFolderSetupControllerApi();
apiInstance.getWorkDirectorySetupStatus().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**WorkFolderSetupStatus**](WorkFolderSetupStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

