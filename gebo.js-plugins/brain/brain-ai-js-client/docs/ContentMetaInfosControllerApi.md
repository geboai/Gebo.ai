# BrainClient.ContentMetaInfosControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**findDocumentReferenceViewByCode**](ContentMetaInfosControllerApi.md#findDocumentReferenceViewByCode) | **POST** /api/users/ContentMetaInfosController/findDocumentReferenceViewByCode | 
[**getContentMetaInfos**](ContentMetaInfosControllerApi.md#getContentMetaInfos) | **GET** /api/users/ContentMetaInfosController/getContentMetaInfos | 
[**getContentObject**](ContentMetaInfosControllerApi.md#getContentObject) | **GET** /api/users/ContentMetaInfosController/getContentObject | 
[**searchByDocumentName**](ContentMetaInfosControllerApi.md#searchByDocumentName) | **POST** /api/users/ContentMetaInfosController/searchByDocumentName | 
[**searchByDocumentNamePaged**](ContentMetaInfosControllerApi.md#searchByDocumentNamePaged) | **POST** /api/users/ContentMetaInfosController/searchByDocumentNamePaged | 

<a name="findDocumentReferenceViewByCode"></a>
# **findDocumentReferenceViewByCode**
> Object findDocumentReferenceViewByCode(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.ContentMetaInfosControllerApi();
let body = null; // Object | 

apiInstance.findDocumentReferenceViewByCode(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Object**](Object.md)|  | 

### Return type

**Object**

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
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.ContentMetaInfosControllerApi();
let code = null; // Object | 

apiInstance.getContentMetaInfos(code).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | [**Object**](.md)|  | 

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
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.ContentMetaInfosControllerApi();
let code = null; // Object | 

apiInstance.getContentObject(code).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | [**Object**](.md)|  | 

### Return type

[**ContentObject**](ContentObject.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="searchByDocumentName"></a>
# **searchByDocumentName**
> Object searchByDocumentName(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.ContentMetaInfosControllerApi();
let body = new BrainClient.SearchDocumentByNameParam(); // SearchDocumentByNameParam | 

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

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="searchByDocumentNamePaged"></a>
# **searchByDocumentNamePaged**
> PageDocumentReferenceView searchByDocumentNamePaged(body)



### Example
```javascript
import {BrainClient} from 'gebo.brain.client.js';

let apiInstance = new BrainClient.ContentMetaInfosControllerApi();
let body = new BrainClient.SearchDocumentByNamePagedParam(); // SearchDocumentByNamePagedParam | 

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

[**PageDocumentReferenceView**](PageDocumentReferenceView.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

