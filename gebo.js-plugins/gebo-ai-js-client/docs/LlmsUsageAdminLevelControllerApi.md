# GeboAiClient.LlmsUsageAdminLevelControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**adminDrillDown**](LlmsUsageAdminLevelControllerApi.md#adminDrillDown) | **POST** /api/admin/LLMSUsageAdminLevelController/drillDown | 

<a name="adminDrillDown"></a>
# **adminDrillDown**
> LLMUsageDrillDownResult adminDrillDown(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.LlmsUsageAdminLevelControllerApi();
let body = new GeboAiClient.LLMUsageDrillDownLevel(); // LLMUsageDrillDownLevel | 

apiInstance.adminDrillDown(body).then((data) => {
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

