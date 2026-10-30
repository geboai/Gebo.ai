# TokenRenewControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**renew**](TokenRenewControllerApi.md#renew) | **GET** /api/users/TokenRenewController/renew | 

<a name="renew"></a>
# **renew**
> SecurityHeaderData renew()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.TokenRenewControllerApi;


TokenRenewControllerApi apiInstance = new TokenRenewControllerApi();
try {
    SecurityHeaderData result = apiInstance.renew();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TokenRenewControllerApi#renew");
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

