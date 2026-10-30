# SessionControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**logout**](SessionControllerApi.md#logout) | **POST** /api/users/SessionController/logout | 

<a name="logout"></a>
# **logout**
> Boolean logout()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SessionControllerApi;


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

