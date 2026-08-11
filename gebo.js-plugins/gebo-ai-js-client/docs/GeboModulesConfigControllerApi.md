# GeboAiClient.GeboModulesConfigControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAllModules**](GeboModulesConfigControllerApi.md#getAllModules) | **GET** /api/users/GeboModulesConfigController/getAllModules | 
[**getModuleInfo**](GeboModulesConfigControllerApi.md#getModuleInfo) | **GET** /api/users/GeboModulesConfigController/getModuleInfo | 

<a name="getAllModules"></a>
# **getAllModules**
> [GeboModuleInfo] getAllModules()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboModulesConfigControllerApi();
apiInstance.getAllModules().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GeboModuleInfo]**](GeboModuleInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getModuleInfo"></a>
# **getModuleInfo**
> GeboModuleInfo getModuleInfo(moduleId)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboModulesConfigControllerApi();
let moduleId = "moduleId_example"; // String | 

apiInstance.getModuleInfo(moduleId).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **moduleId** | **String**|  | 

### Return type

[**GeboModuleInfo**](GeboModuleInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

