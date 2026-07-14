# AuthControllerApi

All URIs are relative to *http://localhost:13018*

Method | HTTP request | Description
------------- | ------------- | -------------
[**authenticateUser**](AuthControllerApi.md#authenticateUser) | **POST** /auth/login | 

<a name="authenticateUser"></a>
# **authenticateUser**
> OperationStatusAuthResponse authenticateUser(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.AuthControllerApi;


AuthControllerApi apiInstance = new AuthControllerApi();
LoginRequest body = new LoginRequest(); // LoginRequest | 
try {
    OperationStatusAuthResponse result = apiInstance.authenticateUser(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AuthControllerApi#authenticateUser");
    e.printStackTrace();
}
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

