# GeboAiClient.GeboDeepSearchControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getDeepSearchDataSources**](GeboDeepSearchControllerApi.md#getDeepSearchDataSources) | **GET** /api/users/GeboDeepSearchController/getDeepSearchDataSources | 

<a name="getDeepSearchDataSources"></a>
# **getDeepSearchDataSources**
> [GBaseObject] getDeepSearchDataSources()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboDeepSearchControllerApi();
apiInstance.getDeepSearchDataSources().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[GBaseObject]**](GBaseObject.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

