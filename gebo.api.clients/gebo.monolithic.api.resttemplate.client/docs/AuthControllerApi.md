# AuthControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**authenticateUser**](AuthControllerApi.md#authenticateUser) | **POST** /auth/login | 

<a name="authenticateUser"></a>
# **authenticateUser**
> OperationStatusAuthResponse authenticateUser(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.AuthControllerApi;


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

