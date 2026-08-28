# GeboAiClient.Oauth2ModuleStatusControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getStatus**](Oauth2ModuleStatusControllerApi.md#getStatus) | **GET** /api/admin/Oauth2ModuleStatusController | 

<a name="getStatus"></a>
# **getStatus**
> Oauth2ModuleStatus getStatus()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.Oauth2ModuleStatusControllerApi();
apiInstance.getStatus().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**Oauth2ModuleStatus**](Oauth2ModuleStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

