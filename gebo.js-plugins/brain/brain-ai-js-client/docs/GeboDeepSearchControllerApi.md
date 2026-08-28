# BrainClient.GeboDeepSearchControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getDeepSearchDataSources**](GeboDeepSearchControllerApi.md#getDeepSearchDataSources) | **GET** /api/users/GeboDeepSearchController/getDeepSearchDataSources | 

<a name="getDeepSearchDataSources"></a>
# **getDeepSearchDataSources**
> Object getDeepSearchDataSources()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboDeepSearchControllerApi();
apiInstance.getDeepSearchDataSources().then((data) => {
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

