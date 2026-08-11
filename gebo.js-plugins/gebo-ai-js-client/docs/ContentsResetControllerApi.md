# GeboAiClient.ContentsResetControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**resetContentsIngestion**](ContentsResetControllerApi.md#resetContentsIngestion) | **POST** /api/admin/ContentsResetController/resetContentsIngestion | 

<a name="resetContentsIngestion"></a>
# **resetContentsIngestion**
> ResetContentResponse resetContentsIngestion(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ContentsResetControllerApi();
let body = new GeboAiClient.ResetContentRequest(); // ResetContentRequest | 

apiInstance.resetContentsIngestion(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ResetContentRequest**](ResetContentRequest.md)|  | 

### Return type

[**ResetContentResponse**](ResetContentResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

