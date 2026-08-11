# GeboAiClient.FileSystemSharesSettingControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**checkCanBeInsertedFileSystemShareReference**](FileSystemSharesSettingControllerApi.md#checkCanBeInsertedFileSystemShareReference) | **POST** /api/admin/FileSystemSharesSettingController/checkCanBeInsertedFileSystemShareReference | 
[**deleteFileSystemShareReference**](FileSystemSharesSettingControllerApi.md#deleteFileSystemShareReference) | **POST** /api/admin/FileSystemSharesSettingController/deleteFileSystemShareReference | 
[**getFileSystemShareReferenceByCode**](FileSystemSharesSettingControllerApi.md#getFileSystemShareReferenceByCode) | **GET** /api/admin/FileSystemSharesSettingController/getFileSystemShareReferenceByCode | 
[**getGFileSystemNodeChildrens**](FileSystemSharesSettingControllerApi.md#getGFileSystemNodeChildrens) | **POST** /api/admin/FileSystemSharesSettingController/getGFileSystemNodeChildrens | 
[**getGFileSystemNodeNavigationStatus**](FileSystemSharesSettingControllerApi.md#getGFileSystemNodeNavigationStatus) | **POST** /api/admin/FileSystemSharesSettingController/getGFileSystemNodeNavigationStatus | 
[**getRootGFileSystemNodes**](FileSystemSharesSettingControllerApi.md#getRootGFileSystemNodes) | **GET** /api/admin/FileSystemSharesSettingController/getRootGFileSystemNodes | 
[**getSharedFileSystemsActualConfiguration**](FileSystemSharesSettingControllerApi.md#getSharedFileSystemsActualConfiguration) | **GET** /api/admin/FileSystemSharesSettingController/getSharedFileSystemsActualConfiguration | 
[**getUsedFilesystemShares**](FileSystemSharesSettingControllerApi.md#getUsedFilesystemShares) | **POST** /api/admin/FileSystemSharesSettingController/getUsedFilesystemShares | 
[**insertFileSystemShareReference**](FileSystemSharesSettingControllerApi.md#insertFileSystemShareReference) | **POST** /api/admin/FileSystemSharesSettingController/insertFileSystemShareReference | 

<a name="checkCanBeInsertedFileSystemShareReference"></a>
# **checkCanBeInsertedFileSystemShareReference**
> OperationStatusGFileSystemShareReference checkCanBeInsertedFileSystemShareReference(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.FileSystemSharesSettingControllerApi();
let body = new GeboAiClient.GFileSystemShareReference(); // GFileSystemShareReference | 

apiInstance.checkCanBeInsertedFileSystemShareReference(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GFileSystemShareReference**](GFileSystemShareReference.md)|  | 

### Return type

[**OperationStatusGFileSystemShareReference**](OperationStatusGFileSystemShareReference.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deleteFileSystemShareReference"></a>
# **deleteFileSystemShareReference**
> deleteFileSystemShareReference(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.FileSystemSharesSettingControllerApi();
let body = new GeboAiClient.GFileSystemShareReference(); // GFileSystemShareReference | 

apiInstance.deleteFileSystemShareReference(body).then(() => {
  console.log('API called successfully.');
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GFileSystemShareReference**](GFileSystemShareReference.md)|  | 

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="getFileSystemShareReferenceByCode"></a>
# **getFileSystemShareReferenceByCode**
> GFileSystemShareReference getFileSystemShareReferenceByCode(code)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.FileSystemSharesSettingControllerApi();
let code = "code_example"; // String | 

apiInstance.getFileSystemShareReferenceByCode(code).then((data) => {
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

[**GFileSystemShareReference**](GFileSystemShareReference.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getGFileSystemNodeChildrens"></a>
# **getGFileSystemNodeChildrens**
> OperationStatusListPathInfo getGFileSystemNodeChildrens(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.FileSystemSharesSettingControllerApi();
let body = new GeboAiClient.BrowseParam(); // BrowseParam | 

apiInstance.getGFileSystemNodeChildrens(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**BrowseParam**](BrowseParam.md)|  | 

### Return type

[**OperationStatusListPathInfo**](OperationStatusListPathInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getGFileSystemNodeNavigationStatus"></a>
# **getGFileSystemNodeNavigationStatus**
> OperationStatusListVirtualFilesystemNavigationTreeStatus getGFileSystemNodeNavigationStatus(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.FileSystemSharesSettingControllerApi();
let body = [new GeboAiClient.VFilesystemReference()]; // [VFilesystemReference] | 

apiInstance.getGFileSystemNodeNavigationStatus(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**[VFilesystemReference]**](VFilesystemReference.md)|  | 

### Return type

[**OperationStatusListVirtualFilesystemNavigationTreeStatus**](OperationStatusListVirtualFilesystemNavigationTreeStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getRootGFileSystemNodes"></a>
# **getRootGFileSystemNodes**
> OperationStatusListGVirtualFilesystemRoot getRootGFileSystemNodes()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.FileSystemSharesSettingControllerApi();
apiInstance.getRootGFileSystemNodes().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**OperationStatusListGVirtualFilesystemRoot**](OperationStatusListGVirtualFilesystemRoot.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getSharedFileSystemsActualConfiguration"></a>
# **getSharedFileSystemsActualConfiguration**
> SharedFilesystemUIConfig getSharedFileSystemsActualConfiguration()



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.FileSystemSharesSettingControllerApi();
apiInstance.getSharedFileSystemsActualConfiguration().then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**SharedFilesystemUIConfig**](SharedFilesystemUIConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getUsedFilesystemShares"></a>
# **getUsedFilesystemShares**
> [FSReference] getUsedFilesystemShares(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.FileSystemSharesSettingControllerApi();
let body = ["body_example"]; // [String] | 

apiInstance.getUsedFilesystemShares(body).then((data) => {
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

[**[FSReference]**](FSReference.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertFileSystemShareReference"></a>
# **insertFileSystemShareReference**
> GFileSystemShareReference insertFileSystemShareReference(body)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.FileSystemSharesSettingControllerApi();
let body = new GeboAiClient.GFileSystemShareReference(); // GFileSystemShareReference | 

apiInstance.insertFileSystemShareReference(body).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GFileSystemShareReference**](GFileSystemShareReference.md)|  | 

### Return type

[**GFileSystemShareReference**](GFileSystemShareReference.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

