# GoogleSearchControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**googleSearch**](GoogleSearchControllerApi.md#googleSearch) | **POST** /api/users/GoogleSearchController/googleSearch | 

<a name="googleSearch"></a>
# **googleSearch**
> GoogleSearchResults googleSearch(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleSearchControllerApi;


GoogleSearchControllerApi apiInstance = new GoogleSearchControllerApi();
GoogleSearchRequest body = new GoogleSearchRequest(); // GoogleSearchRequest | 
try {
    GoogleSearchResults result = apiInstance.googleSearch(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleSearchControllerApi#googleSearch");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GoogleSearchRequest**](GoogleSearchRequest.md)|  |

### Return type

[**GoogleSearchResults**](GoogleSearchResults.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

