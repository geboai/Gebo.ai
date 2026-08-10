# GeboAiClient.BuildSystemsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getBuildSystemConfigs**](BuildSystemsControllerApi.md#getBuildSystemConfigs) | **GET** /api/admin/BuildSystemsController/getBuildSystemConfigs | 
[**getBuildSystemTypes**](BuildSystemsControllerApi.md#getBuildSystemTypes) | **GET** /api/admin/BuildSystemsController/getBuildSystemTypes | 

<a name="getBuildSystemConfigs"></a>
# **getBuildSystemConfigs**
> [GBuildSystem] getBuildSystemConfigs(buildSystemTypeCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.BuildSystemsControllerApi();
let buildSystemTypeCode = "buildSystemTypeCode_example"; // String | 

apiInstance.getBuildSystemConfigs(buildSystemTypeCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **buildSystemTypeCode** | **String**|  | 

### Return type

[**[GBuildSystem]**](GBuildSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getBuildSystemTypes"></a>
# **getBuildSystemTypes**
> [GBuildSystemType] getBuildSystemTypes()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.BuildSystemsControllerApi();
apiInstance.getBuildSystemTypes().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GBuildSystemType]**](GBuildSystemType.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

