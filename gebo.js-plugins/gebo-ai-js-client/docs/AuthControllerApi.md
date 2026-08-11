# GeboAiClient.AuthControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**authenticateUser**](AuthControllerApi.md#authenticateUser) | **POST** /auth/login | 

<a name="authenticateUser"></a>
# **authenticateUser**
> OperationStatusAuthResponse authenticateUser(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.AuthControllerApi();
let body = new GeboAiClient.LoginRequest(); // LoginRequest | 

apiInstance.authenticateUser(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**LoginRequest**](LoginRequest.md)|  | 

### Return type

[**OperationStatusAuthResponse**](OperationStatusAuthResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

