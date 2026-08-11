# BrainClient.GeboFastChatProfileStatusControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getChatProfilesSetupStatus**](GeboFastChatProfileStatusControllerApi.md#getChatProfilesSetupStatus) | **GET** /api/admin/GeboFastChatProfileStatusController/getChatProfilesSetupStatus | 

<a name="getChatProfilesSetupStatus"></a>
# **getChatProfilesSetupStatus**
> ComponentSetupStatus getChatProfilesSetupStatus()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboFastChatProfileStatusControllerApi();
apiInstance.getChatProfilesSetupStatus().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**ComponentSetupStatus**](ComponentSetupStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

