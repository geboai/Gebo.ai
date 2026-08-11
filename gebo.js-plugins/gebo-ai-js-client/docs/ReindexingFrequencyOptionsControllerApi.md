# GeboAiClient.ReindexingFrequencyOptionsControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**displayTimeValues**](ReindexingFrequencyOptionsControllerApi.md#displayTimeValues) | **POST** /api/users/ReindexingFrequencyOptionsController/displayTimeValues | 
[**getAllTimeStructureMetaInfos**](ReindexingFrequencyOptionsControllerApi.md#getAllTimeStructureMetaInfos) | **GET** /api/users/ReindexingFrequencyOptionsController/getAllTimeStructureMetaInfos | 
[**getTimeStructureMetaInfo**](ReindexingFrequencyOptionsControllerApi.md#getTimeStructureMetaInfo) | **GET** /api/users/ReindexingFrequencyOptionsController/getTimeStructureMetaInfo | 

<a name="displayTimeValues"></a>
# **displayTimeValues**
> [&#x27;String&#x27;] displayTimeValues(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ReindexingFrequencyOptionsControllerApi();
let body = [new GeboAiClient.ReindexingProgrammedTable()]; // [ReindexingProgrammedTable] | 

apiInstance.displayTimeValues(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**[ReindexingProgrammedTable]**](ReindexingProgrammedTable.md)|  | 

### Return type

**[&#x27;String&#x27;]**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getAllTimeStructureMetaInfos"></a>
# **getAllTimeStructureMetaInfos**
> [ReindexTimeStructureMetaInfo] getAllTimeStructureMetaInfos()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ReindexingFrequencyOptionsControllerApi();
apiInstance.getAllTimeStructureMetaInfos().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[ReindexTimeStructureMetaInfo]**](ReindexTimeStructureMetaInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getTimeStructureMetaInfo"></a>
# **getTimeStructureMetaInfo**
> ReindexTimeStructureMetaInfo getTimeStructureMetaInfo(frequency)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ReindexingFrequencyOptionsControllerApi();
let frequency = "frequency_example"; // String | 

apiInstance.getTimeStructureMetaInfo(frequency).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **frequency** | **String**|  | 

### Return type

[**ReindexTimeStructureMetaInfo**](ReindexTimeStructureMetaInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

