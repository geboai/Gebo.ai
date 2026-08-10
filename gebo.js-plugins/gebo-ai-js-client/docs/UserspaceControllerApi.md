# GeboAiClient.UserspaceControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteUserKnowledgebase**](UserspaceControllerApi.md#deleteUserKnowledgebase) | **POST** /api/user/UserspaceController/deleteUserKnowledgebase | 
[**deleteUserspaceFiles**](UserspaceControllerApi.md#deleteUserspaceFiles) | **POST** /api/user/UserspaceController/deleteUserspaceFiles | 
[**deleteUserspaceFolder**](UserspaceControllerApi.md#deleteUserspaceFolder) | **POST** /api/user/UserspaceController/deleteUserspaceFolder | 
[**findUserKnowledgebaseByCode**](UserspaceControllerApi.md#findUserKnowledgebaseByCode) | **GET** /api/user/UserspaceController/findUserKnowledgebaseByCode | 
[**findUserspaceFileByCodes**](UserspaceControllerApi.md#findUserspaceFileByCodes) | **POST** /api/user/UserspaceController/findUserspaceFileByCodes | 
[**findUserspaceFolderByCode**](UserspaceControllerApi.md#findUserspaceFolderByCode) | **GET** /api/user/UserspaceController/findUserspaceFolderByCode | 
[**getPersonalKnowledgebases**](UserspaceControllerApi.md#getPersonalKnowledgebases) | **GET** /api/user/UserspaceController/getPersonalKnowledgebases | 
[**getPublishingStatus**](UserspaceControllerApi.md#getPublishingStatus) | **POST** /api/user/UserspaceController/getPublishingStatus | 
[**listChildPersonalKnowledgebases**](UserspaceControllerApi.md#listChildPersonalKnowledgebases) | **POST** /api/user/UserspaceController/listChildPersonalKnowledgebases | 
[**listUserspaceFiles**](UserspaceControllerApi.md#listUserspaceFiles) | **GET** /api/user/UserspaceController/listUserspaceFiles | 
[**listUserspaceFolders**](UserspaceControllerApi.md#listUserspaceFolders) | **GET** /api/user/UserspaceController/listUserspaceFolders | 
[**newUserKnowledgebase**](UserspaceControllerApi.md#newUserKnowledgebase) | **POST** /api/user/UserspaceController/newUserKnowledgebase | 
[**newUserspaceFolder**](UserspaceControllerApi.md#newUserspaceFolder) | **POST** /api/user/UserspaceController/newUserspaceFolder | 
[**publishFolder**](UserspaceControllerApi.md#publishFolder) | **POST** /api/user/UserspaceController/publishFolder | 
[**publishUserspaceProjectEndpoint**](UserspaceControllerApi.md#publishUserspaceProjectEndpoint) | **POST** /api/user/UserspaceController/publishUserspaceProjectEndpoint | 
[**transferUploadsToUserSpaceAndPublish**](UserspaceControllerApi.md#transferUploadsToUserSpaceAndPublish) | **POST** /api/user/UserspaceController/transferUploadsToUserSpaceAndPublish | 
[**updateUserKnowledgebase**](UserspaceControllerApi.md#updateUserKnowledgebase) | **POST** /api/user/UserspaceController/updateUserKnowledgebase | 
[**updateUserspaceFolder**](UserspaceControllerApi.md#updateUserspaceFolder) | **POST** /api/user/UserspaceController/updateUserspaceFolder | 

<a name="deleteUserKnowledgebase"></a>
# **deleteUserKnowledgebase**
> deleteUserKnowledgebase(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UserspaceControllerApi();
let body = new GeboAiClient.UserspaceKnowledgebaseDto(); // UserspaceKnowledgebaseDto | 

apiInstance.deleteUserKnowledgebase(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UserspaceKnowledgebaseDto**](UserspaceKnowledgebaseDto.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="deleteUserspaceFiles"></a>
# **deleteUserspaceFiles**
> deleteUserspaceFiles(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UserspaceControllerApi();
let body = [new GeboAiClient.UserspaceFileDto()]; // [UserspaceFileDto] | 

apiInstance.deleteUserspaceFiles(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**[UserspaceFileDto]**](UserspaceFileDto.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="deleteUserspaceFolder"></a>
# **deleteUserspaceFolder**
> deleteUserspaceFolder(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UserspaceControllerApi();
let body = new GeboAiClient.UserspaceFolderDto(); // UserspaceFolderDto | 

apiInstance.deleteUserspaceFolder(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UserspaceFolderDto**](UserspaceFolderDto.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="findUserKnowledgebaseByCode"></a>
# **findUserKnowledgebaseByCode**
> UserspaceKnowledgebaseDto findUserKnowledgebaseByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UserspaceControllerApi();
let code = "code_example"; // String | 

apiInstance.findUserKnowledgebaseByCode(code).then((data) => {
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

[**UserspaceKnowledgebaseDto**](UserspaceKnowledgebaseDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findUserspaceFileByCodes"></a>
# **findUserspaceFileByCodes**
> [UserspaceFileDto] findUserspaceFileByCodes(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UserspaceControllerApi();
let body = ["body_example"]; // [String] | 

apiInstance.findUserspaceFileByCodes(body).then((data) => {
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

[**[UserspaceFileDto]**](UserspaceFileDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findUserspaceFolderByCode"></a>
# **findUserspaceFolderByCode**
> UserspaceFolderDto findUserspaceFolderByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UserspaceControllerApi();
let code = "code_example"; // String | 

apiInstance.findUserspaceFolderByCode(code).then((data) => {
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

[**UserspaceFolderDto**](UserspaceFolderDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getPersonalKnowledgebases"></a>
# **getPersonalKnowledgebases**
> [UserspaceKnowledgebaseDto] getPersonalKnowledgebases()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UserspaceControllerApi();
apiInstance.getPersonalKnowledgebases().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[UserspaceKnowledgebaseDto]**](UserspaceKnowledgebaseDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getPublishingStatus"></a>
# **getPublishingStatus**
> PublishingStatus getPublishingStatus(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UserspaceControllerApi();
let body = new GeboAiClient.UserspaceFolderDto(); // UserspaceFolderDto | 

apiInstance.getPublishingStatus(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UserspaceFolderDto**](UserspaceFolderDto.md)|  | 

### Return type

[**PublishingStatus**](PublishingStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="listChildPersonalKnowledgebases"></a>
# **listChildPersonalKnowledgebases**
> [UserspaceKnowledgebaseDto] listChildPersonalKnowledgebases(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UserspaceControllerApi();
let body = ["body_example"]; // [String] | 

apiInstance.listChildPersonalKnowledgebases(body).then((data) => {
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

[**[UserspaceKnowledgebaseDto]**](UserspaceKnowledgebaseDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="listUserspaceFiles"></a>
# **listUserspaceFiles**
> [UserspaceFileDto] listUserspaceFiles(userspaceUploadCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UserspaceControllerApi();
let userspaceUploadCode = "userspaceUploadCode_example"; // String | 

apiInstance.listUserspaceFiles(userspaceUploadCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userspaceUploadCode** | **String**|  | 

### Return type

[**[UserspaceFileDto]**](UserspaceFileDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="listUserspaceFolders"></a>
# **listUserspaceFolders**
> [UserspaceFolderDto] listUserspaceFolders(userspaceKnowledgeBase)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UserspaceControllerApi();
let userspaceKnowledgeBase = "userspaceKnowledgeBase_example"; // String | 

apiInstance.listUserspaceFolders(userspaceKnowledgeBase).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userspaceKnowledgeBase** | **String**|  | 

### Return type

[**[UserspaceFolderDto]**](UserspaceFolderDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="newUserKnowledgebase"></a>
# **newUserKnowledgebase**
> UserspaceKnowledgebaseDto newUserKnowledgebase(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UserspaceControllerApi();
let body = new GeboAiClient.UserspaceKnowledgebaseDto(); // UserspaceKnowledgebaseDto | 

apiInstance.newUserKnowledgebase(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UserspaceKnowledgebaseDto**](UserspaceKnowledgebaseDto.md)|  | 

### Return type

[**UserspaceKnowledgebaseDto**](UserspaceKnowledgebaseDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="newUserspaceFolder"></a>
# **newUserspaceFolder**
> UserspaceFolderDto newUserspaceFolder(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UserspaceControllerApi();
let body = new GeboAiClient.UserspaceFolderDto(); // UserspaceFolderDto | 

apiInstance.newUserspaceFolder(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UserspaceFolderDto**](UserspaceFolderDto.md)|  | 

### Return type

[**UserspaceFolderDto**](UserspaceFolderDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="publishFolder"></a>
# **publishFolder**
> OperationStatusPublishingStatus publishFolder(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UserspaceControllerApi();
let body = new GeboAiClient.UserspaceFolderDto(); // UserspaceFolderDto | 

apiInstance.publishFolder(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UserspaceFolderDto**](UserspaceFolderDto.md)|  | 

### Return type

[**OperationStatusPublishingStatus**](OperationStatusPublishingStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="publishUserspaceProjectEndpoint"></a>
# **publishUserspaceProjectEndpoint**
> OperationStatusGJobStatus publishUserspaceProjectEndpoint(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UserspaceControllerApi();
let body = new GeboAiClient.GUserspaceProjectEndpoint(); // GUserspaceProjectEndpoint | 

apiInstance.publishUserspaceProjectEndpoint(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GUserspaceProjectEndpoint**](GUserspaceProjectEndpoint.md)|  | 

### Return type

[**OperationStatusGJobStatus**](OperationStatusGJobStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="transferUploadsToUserSpaceAndPublish"></a>
# **transferUploadsToUserSpaceAndPublish**
> OperationStatusPublishingStatus transferUploadsToUserSpaceAndPublish(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UserspaceControllerApi();
let body = new GeboAiClient.UserUploadToUserSpaceParam(); // UserUploadToUserSpaceParam | 

apiInstance.transferUploadsToUserSpaceAndPublish(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UserUploadToUserSpaceParam**](UserUploadToUserSpaceParam.md)|  | 

### Return type

[**OperationStatusPublishingStatus**](OperationStatusPublishingStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateUserKnowledgebase"></a>
# **updateUserKnowledgebase**
> UserspaceKnowledgebaseDto updateUserKnowledgebase(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UserspaceControllerApi();
let body = new GeboAiClient.UserspaceKnowledgebaseDto(); // UserspaceKnowledgebaseDto | 

apiInstance.updateUserKnowledgebase(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UserspaceKnowledgebaseDto**](UserspaceKnowledgebaseDto.md)|  | 

### Return type

[**UserspaceKnowledgebaseDto**](UserspaceKnowledgebaseDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateUserspaceFolder"></a>
# **updateUserspaceFolder**
> UserspaceFolderDto updateUserspaceFolder(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.UserspaceControllerApi();
let body = new GeboAiClient.UserspaceFolderDto(); // UserspaceFolderDto | 

apiInstance.updateUserspaceFolder(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UserspaceFolderDto**](UserspaceFolderDto.md)|  | 

### Return type

[**UserspaceFolderDto**](UserspaceFolderDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

