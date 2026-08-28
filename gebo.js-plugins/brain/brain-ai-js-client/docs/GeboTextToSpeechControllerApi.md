# BrainClient.GeboTextToSpeechControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**isEnabled1**](GeboTextToSpeechControllerApi.md#isEnabled1) | **GET** /api/users/GeboTextToSpeechController/isEnabled | 
[**speechText**](GeboTextToSpeechControllerApi.md#speechText) | **POST** /api/users/GeboTextToSpeechController/speechText | 

<a name="isEnabled1"></a>
# **isEnabled1**
> Object isEnabled1()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboTextToSpeechControllerApi();
apiInstance.isEnabled1().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="speechText"></a>
# **speechText**
> Object speechText(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboTextToSpeechControllerApi();
let body = new BrainClient.SpeechRequest(); // SpeechRequest | 

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

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/octet-stream

