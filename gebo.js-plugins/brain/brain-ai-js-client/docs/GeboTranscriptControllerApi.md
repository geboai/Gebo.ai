# BrainClient.GeboTranscriptControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**isEnabled**](GeboTranscriptControllerApi.md#isEnabled) | **GET** /api/users/GeboTranscriptController/isEnabled | 
[**transcriptText**](GeboTranscriptControllerApi.md#transcriptText) | **POST** /api/users/GeboTranscriptController/transcriptText | 

<a name="isEnabled"></a>
# **isEnabled**
> Object isEnabled()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboTranscriptControllerApi();
apiInstance.isEnabled().then((data) => {
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

<a name="transcriptText"></a>
# **transcriptText**
> TranscriptResponse transcriptText()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboTranscriptControllerApi();
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

