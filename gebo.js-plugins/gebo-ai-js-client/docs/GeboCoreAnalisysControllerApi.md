# GeboAiClient.GeboCoreAnalisysControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**coreDrillDown**](GeboCoreAnalisysControllerApi.md#coreDrillDown) | **POST** /api/admin/GeboCoreAnalisysController/drillDown | 
[**getTopLevelKnowledgeBaseCategory**](GeboCoreAnalisysControllerApi.md#getTopLevelKnowledgeBaseCategory) | **GET** /api/admin/GeboCoreAnalisysController/getTopLevelKnowledgeBaseCategory | 

<a name="coreDrillDown"></a>
# **coreDrillDown**
> [GStatsHolder] coreDrillDown(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboCoreAnalisysControllerApi();
let body = new GeboAiClient.GStatsHolder(); // GStatsHolder | 

apiInstance.coreDrillDown(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GStatsHolder**](GStatsHolder.md)|  | 

### Return type

[**[GStatsHolder]**](GStatsHolder.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getTopLevelKnowledgeBaseCategory"></a>
# **getTopLevelKnowledgeBaseCategory**
> GStatsHolder getTopLevelKnowledgeBaseCategory()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GeboCoreAnalisysControllerApi();
apiInstance.getTopLevelKnowledgeBaseCategory().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**GStatsHolder**](GStatsHolder.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

