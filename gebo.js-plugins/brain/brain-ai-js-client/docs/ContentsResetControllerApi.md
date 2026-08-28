# BrainClient.ContentsResetControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**resetContentsIngestion**](ContentsResetControllerApi.md#resetContentsIngestion) | **POST** /api/admin/ContentsResetController/resetContentsIngestion | 

<a name="resetContentsIngestion"></a>
# **resetContentsIngestion**
> ResetContentResponse resetContentsIngestion(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.ContentsResetControllerApi();
let body = new BrainClient.ResetContentRequest(); // ResetContentRequest | 

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

