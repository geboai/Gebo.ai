# ContentsResetControllerApi

All URIs are relative to *http://localhost:13015/integration*

Method | HTTP request | Description
------------- | ------------- | -------------
[**resetContentsIngestion**](ContentsResetControllerApi.md#resetContentsIngestion) | **POST** /api/admin/ContentsResetController/resetContentsIngestion | 

<a name="resetContentsIngestion"></a>
# **resetContentsIngestion**
> ResetContentResponse resetContentsIngestion(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.integration.invoker.ApiException;
//import gebo.microservices.api.client.integration.api.ContentsResetControllerApi;


ContentsResetControllerApi apiInstance = new ContentsResetControllerApi();
ResetContentRequest body = new ResetContentRequest(); // ResetContentRequest | 
try {
    ResetContentResponse result = apiInstance.resetContentsIngestion(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentsResetControllerApi#resetContentsIngestion");
    e.printStackTrace();
}
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

