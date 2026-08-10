# GeboAiClient.GoogleSearchControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**googleSearch**](GoogleSearchControllerApi.md#googleSearch) | **POST** /api/users/GoogleSearchController/googleSearch | 

<a name="googleSearch"></a>
# **googleSearch**
> GoogleSearchResults googleSearch(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleSearchControllerApi();
let body = new GeboAiClient.GoogleSearchRequest(); // GoogleSearchRequest | 

apiInstance.googleSearch(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

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

