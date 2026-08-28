# BrainClient.GoogleSearchControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**googleSearch**](GoogleSearchControllerApi.md#googleSearch) | **POST** /api/users/GoogleSearchController/googleSearch | 

<a name="googleSearch"></a>
# **googleSearch**
> GoogleSearchResults googleSearch(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GoogleSearchControllerApi();
let body = new BrainClient.GoogleSearchRequest(); // GoogleSearchRequest | 

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

