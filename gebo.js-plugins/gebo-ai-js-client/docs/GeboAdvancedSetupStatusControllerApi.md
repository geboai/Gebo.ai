# GeboAiClient.GeboAdvancedSetupStatusControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getFirstKnowledgeBaseSetupStatus**](GeboAdvancedSetupStatusControllerApi.md#getFirstKnowledgeBaseSetupStatus) | **GET** /api/admin/GeboAdvancedSetupStatusController/getFirstKnowledgeBaseSetupStatus | 
[**getMinimalContentsSetupStatus**](GeboAdvancedSetupStatusControllerApi.md#getMinimalContentsSetupStatus) | **GET** /api/admin/GeboAdvancedSetupStatusController/getMinimalContentsSetupStatus | 

<a name="getFirstKnowledgeBaseSetupStatus"></a>
# **getFirstKnowledgeBaseSetupStatus**
> ComponentSetupStatus getFirstKnowledgeBaseSetupStatus()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboAdvancedSetupStatusControllerApi();
apiInstance.getFirstKnowledgeBaseSetupStatus().then((data) => {
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

<a name="getMinimalContentsSetupStatus"></a>
# **getMinimalContentsSetupStatus**
> ComponentSetupStatus getMinimalContentsSetupStatus()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboAdvancedSetupStatusControllerApi();
apiInstance.getMinimalContentsSetupStatus().then((data) => {
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

