# GeboAiClient.SecurityHeaderDataCompletionControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**complete**](SecurityHeaderDataCompletionControllerApi.md#complete) | **GET** /api/users/SecurityHeaderDataCompletionController/complete | 

<a name="complete"></a>
# **complete**
> SecurityHeaderData complete()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.SecurityHeaderDataCompletionControllerApi();
apiInstance.complete().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**SecurityHeaderData**](SecurityHeaderData.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

