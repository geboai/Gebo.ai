# GeboAiClient.IntegrationInputControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**publishContents**](IntegrationInputControllerApi.md#publishContents) | **PUT** /api/application/IntegrationInputController/publishContents | 
[**publishSync**](IntegrationInputControllerApi.md#publishSync) | **GET** /api/application/IntegrationInputController/publishSync | 
[**spoolDocument**](IntegrationInputControllerApi.md#spoolDocument) | **POST** /api/application/IntegrationInputController/spoolDocument | 
[**spoolDocument1**](IntegrationInputControllerApi.md#spoolDocument1) | **PUT** /api/application/IntegrationInputController/spoolDocument | 

<a name="publishContents"></a>
# **publishContents**
> JobTicket publishContents(body, endpointCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.IntegrationInputControllerApi();
let body = [new GeboAiClient.JobTicket()]; // [JobTicket] | 
let endpointCode = "endpointCode_example"; // String | 

apiInstance.publishContents(body, endpointCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**[JobTicket]**](JobTicket.md)|  | 
 **endpointCode** | **String**|  | 

### Return type

[**JobTicket**](JobTicket.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="publishSync"></a>
# **publishSync**
> JobTicket publishSync(endpointCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.IntegrationInputControllerApi();
let endpointCode = "endpointCode_example"; // String | 

apiInstance.publishSync(endpointCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **endpointCode** | **String**|  | 

### Return type

[**JobTicket**](JobTicket.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="spoolDocument"></a>
# **spoolDocument**
> JobTicket spoolDocument(body, endpointCode, relativePath)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.IntegrationInputControllerApi();
let body = new GeboAiClient.IntegrationDocumentEnvelop(); // IntegrationDocumentEnvelop | 
let endpointCode = "endpointCode_example"; // String | 
let relativePath = "relativePath_example"; // String | 

apiInstance.spoolDocument(body, endpointCode, relativePath).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**IntegrationDocumentEnvelop**](IntegrationDocumentEnvelop.md)|  | 
 **endpointCode** | **String**|  | 
 **relativePath** | **String**|  | 

### Return type

[**JobTicket**](JobTicket.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="spoolDocument1"></a>
# **spoolDocument1**
> JobTicket spoolDocument1(file, endpointCode, relativePath)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.IntegrationInputControllerApi();
let file = "file_example"; // Blob | 
let endpointCode = "endpointCode_example"; // String | 
let relativePath = "relativePath_example"; // String | 

apiInstance.spoolDocument1(file, endpointCode, relativePath).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **file** | **Blob**|  | 
 **endpointCode** | **String**|  | 
 **relativePath** | **String**|  | 

### Return type

[**JobTicket**](JobTicket.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: application/json

