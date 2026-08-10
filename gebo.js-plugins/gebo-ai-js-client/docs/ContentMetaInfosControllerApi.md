# GeboAiClient.ContentMetaInfosControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**findDocumentReferenceViewByCode**](ContentMetaInfosControllerApi.md#findDocumentReferenceViewByCode) | **POST** /api/users/ContentMetaInfosController/findDocumentReferenceViewByCode | 
[**getContentMetaInfos**](ContentMetaInfosControllerApi.md#getContentMetaInfos) | **GET** /api/users/ContentMetaInfosController/getContentMetaInfos | 
[**getContentObject**](ContentMetaInfosControllerApi.md#getContentObject) | **GET** /api/users/ContentMetaInfosController/getContentObject | 
[**searchByDocumentName**](ContentMetaInfosControllerApi.md#searchByDocumentName) | **POST** /api/users/ContentMetaInfosController/searchByDocumentName | 
[**searchByDocumentNamePaged**](ContentMetaInfosControllerApi.md#searchByDocumentNamePaged) | **POST** /api/users/ContentMetaInfosController/searchByDocumentNamePaged | 

<a name="findDocumentReferenceViewByCode"></a>
# **findDocumentReferenceViewByCode**
> [DocumentReferenceView] findDocumentReferenceViewByCode(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ContentMetaInfosControllerApi();
let body = ["body_example"]; // [String] | 

apiInstance.findDocumentReferenceViewByCode(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**[String]**](String.md)|  | 

### Return type

[**[DocumentReferenceView]**](DocumentReferenceView.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getContentMetaInfos"></a>
# **getContentMetaInfos**
> ContentMetaInfo getContentMetaInfos(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ContentMetaInfosControllerApi();
let code = "code_example"; // String | 

apiInstance.getContentMetaInfos(code).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  | 

### Return type

[**ContentMetaInfo**](ContentMetaInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getContentObject"></a>
# **getContentObject**
> ContentObject getContentObject(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ContentMetaInfosControllerApi();
let code = "code_example"; // String | 

apiInstance.getContentObject(code).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  | 

### Return type

[**ContentObject**](ContentObject.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="searchByDocumentName"></a>
# **searchByDocumentName**
> [DocumentReferenceView] searchByDocumentName(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ContentMetaInfosControllerApi();
let body = new GeboAiClient.SearchDocumentByNameParam(); // SearchDocumentByNameParam | 

apiInstance.searchByDocumentName(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SearchDocumentByNameParam**](SearchDocumentByNameParam.md)|  | 

### Return type

[**[DocumentReferenceView]**](DocumentReferenceView.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="searchByDocumentNamePaged"></a>
# **searchByDocumentNamePaged**
> PagedModelDocumentReferenceView searchByDocumentNamePaged(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.ContentMetaInfosControllerApi();
let body = new GeboAiClient.SearchDocumentByNamePagedParam(); // SearchDocumentByNamePagedParam | 

apiInstance.searchByDocumentNamePaged(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SearchDocumentByNamePagedParam**](SearchDocumentByNamePagedParam.md)|  | 

### Return type

[**PagedModelDocumentReferenceView**](PagedModelDocumentReferenceView.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

