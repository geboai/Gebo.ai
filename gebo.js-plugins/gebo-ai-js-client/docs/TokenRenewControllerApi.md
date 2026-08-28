# GeboAiClient.TokenRenewControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**renew**](TokenRenewControllerApi.md#renew) | **GET** /api/users/TokenRenewController/renew | 

<a name="renew"></a>
# **renew**
> SecurityHeaderData renew()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.TokenRenewControllerApi();
apiInstance.renew().then((data) => {
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

