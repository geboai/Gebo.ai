# GeboAiClient.GeboFastInstallationSetupControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createSetup**](GeboFastInstallationSetupControllerApi.md#createSetup) | **POST** /public/GeboFastSetupController/createSetup | 
[**getInstallationStatus**](GeboFastInstallationSetupControllerApi.md#getInstallationStatus) | **GET** /public/GeboFastSetupController/getInstallationStatus | 

<a name="createSetup"></a>
# **createSetup**
> OperationStatusBoolean createSetup(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboFastInstallationSetupControllerApi();
let body = new GeboAiClient.FastInstallationSetupData(); // FastInstallationSetupData | 

apiInstance.createSetup(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**FastInstallationSetupData**](FastInstallationSetupData.md)|  | 

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getInstallationStatus"></a>
# **getInstallationStatus**
> OperationStatusBoolean getInstallationStatus()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboFastInstallationSetupControllerApi();
apiInstance.getInstallationStatus().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

