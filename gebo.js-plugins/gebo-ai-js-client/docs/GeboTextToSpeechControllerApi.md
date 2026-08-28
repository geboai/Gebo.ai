# GeboAiClient.GeboTextToSpeechControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**isEnabled1**](GeboTextToSpeechControllerApi.md#isEnabled1) | **GET** /api/users/GeboTextToSpeechController/isEnabled | 
[**speechText**](GeboTextToSpeechControllerApi.md#speechText) | **POST** /api/users/GeboTextToSpeechController/speechText | 

<a name="isEnabled1"></a>
# **isEnabled1**
> &#x27;Boolean&#x27; isEnabled1()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboTextToSpeechControllerApi();
apiInstance.isEnabled1().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

**&#x27;Boolean&#x27;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="speechText"></a>
# **speechText**
> &#x27;Blob&#x27; speechText(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboTextToSpeechControllerApi();
let body = new GeboAiClient.SpeechRequest(); // SpeechRequest | 

apiInstance.speechText(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SpeechRequest**](SpeechRequest.md)|  | 

### Return type

**&#x27;Blob&#x27;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/octet-stream

