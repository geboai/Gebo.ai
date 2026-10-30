# SecurityHeaderDataCompletionControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**complete**](SecurityHeaderDataCompletionControllerApi.md#complete) | **GET** /api/users/SecurityHeaderDataCompletionController/complete | 

<a name="complete"></a>
# **complete**
> SecurityHeaderData complete()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.SecurityHeaderDataCompletionControllerApi;


SecurityHeaderDataCompletionControllerApi apiInstance = new SecurityHeaderDataCompletionControllerApi();
try {
    SecurityHeaderData result = apiInstance.complete();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SecurityHeaderDataCompletionControllerApi#complete");
    e.printStackTrace();
}
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

