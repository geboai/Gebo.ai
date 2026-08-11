# GeboAiClient.LlmsUsageUserLevelControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**userDrillDown**](LlmsUsageUserLevelControllerApi.md#userDrillDown) | **POST** /api/users/LLMSUsageUserLevelController/drillDown | 

<a name="userDrillDown"></a>
# **userDrillDown**
> LLMUsageDrillDownResult userDrillDown(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.LlmsUsageUserLevelControllerApi();
let body = new GeboAiClient.LLMUsageDrillDownLevel(); // LLMUsageDrillDownLevel | 

apiInstance.userDrillDown(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**LLMUsageDrillDownLevel**](LLMUsageDrillDownLevel.md)|  | 

### Return type

[**LLMUsageDrillDownResult**](LLMUsageDrillDownResult.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

