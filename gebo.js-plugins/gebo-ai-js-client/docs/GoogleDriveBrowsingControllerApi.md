# GeboAiClient.GoogleDriveBrowsingControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**browseGoogleDrivePath**](GoogleDriveBrowsingControllerApi.md#browseGoogleDrivePath) | **POST** /api/admin/GoogleDriveBrowsingController/browseGoogleDrivePath | 
[**getGoogleDriveRoots**](GoogleDriveBrowsingControllerApi.md#getGoogleDriveRoots) | **GET** /api/admin/GoogleDriveBrowsingController/getGoogleDriveRoots | 

<a name="browseGoogleDrivePath"></a>
# **browseGoogleDrivePath**
> OperationStatusListPathInfo browseGoogleDrivePath(body, driveSystemCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleDriveBrowsingControllerApi();
let body = new GeboAiClient.BrowseParam(); // BrowseParam | 
let driveSystemCode = "driveSystemCode_example"; // String | 

apiInstance.browseGoogleDrivePath(body, driveSystemCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**BrowseParam**](BrowseParam.md)|  | 
 **driveSystemCode** | **String**|  | 

### Return type

[**OperationStatusListPathInfo**](OperationStatusListPathInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getGoogleDriveRoots"></a>
# **getGoogleDriveRoots**
> OperationStatusListGVirtualFilesystemRoot getGoogleDriveRoots(driveSystemCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.GoogleDriveBrowsingControllerApi();
let driveSystemCode = "driveSystemCode_example"; // String | 

apiInstance.getGoogleDriveRoots(driveSystemCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **driveSystemCode** | **String**|  | 

### Return type

[**OperationStatusListGVirtualFilesystemRoot**](OperationStatusListGVirtualFilesystemRoot.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

