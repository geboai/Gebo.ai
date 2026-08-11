# GeboAiClient.GeboTranscriptControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**isEnabled**](GeboTranscriptControllerApi.md#isEnabled) | **GET** /api/users/GeboTranscriptController/isEnabled | 
[**transcriptText**](GeboTranscriptControllerApi.md#transcriptText) | **POST** /api/users/GeboTranscriptController/transcriptText | 

<a name="isEnabled"></a>
# **isEnabled**
> &#x27;Boolean&#x27; isEnabled()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboTranscriptControllerApi();
apiInstance.isEnabled().then((data) => {
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

<a name="transcriptText"></a>
# **transcriptText**
> TranscriptResponse transcriptText()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboTranscriptControllerApi();
apiInstance.transcriptText().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**TranscriptResponse**](TranscriptResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

