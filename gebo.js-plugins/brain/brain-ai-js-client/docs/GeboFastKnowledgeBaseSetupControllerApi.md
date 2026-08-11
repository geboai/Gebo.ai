# BrainClient.GeboFastKnowledgeBaseSetupControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getCompleteKnowledgeBaseSetupStatus**](GeboFastKnowledgeBaseSetupControllerApi.md#getCompleteKnowledgeBaseSetupStatus) | **GET** /api/admin/GeboFastKnowledgeBaseSetupController/getCompleteKnowledgeBaseSetupStatus | 
[**getContentProcessRows**](GeboFastKnowledgeBaseSetupControllerApi.md#getContentProcessRows) | **GET** /api/admin/GeboFastKnowledgeBaseSetupController/getContentProcessRows | 

<a name="getCompleteKnowledgeBaseSetupStatus"></a>
# **getCompleteKnowledgeBaseSetupStatus**
> GeboKnowledgeBaseSetupStatus getCompleteKnowledgeBaseSetupStatus()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboFastKnowledgeBaseSetupControllerApi();
apiInstance.getCompleteKnowledgeBaseSetupStatus().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**GeboKnowledgeBaseSetupStatus**](GeboKnowledgeBaseSetupStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getContentProcessRows"></a>
# **getContentProcessRows**
> Object getContentProcessRows()



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.GeboFastKnowledgeBaseSetupControllerApi();
apiInstance.getContentProcessRows().then((data) => {
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

