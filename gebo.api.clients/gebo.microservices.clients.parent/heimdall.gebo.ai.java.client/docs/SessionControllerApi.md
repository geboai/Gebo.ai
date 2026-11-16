# SessionControllerApi

All URIs are relative to *http://localhost:13018/heimdall*

Method | HTTP request | Description
------------- | ------------- | -------------
[**logout**](SessionControllerApi.md#logout) | **POST** /api/users/SessionController/logout | 

<a name="logout"></a>
# **logout**
> Boolean logout()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.heimdall.invoker.ApiException;
//import gebo.microservices.api.client.heimdall.api.SessionControllerApi;


SessionControllerApi apiInstance = new SessionControllerApi();
try {
    Boolean result = apiInstance.logout();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SessionControllerApi#logout");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**Boolean**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

