# GeboAiClient.AwsS3BrowsingControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**browseAwsS3Path**](AwsS3BrowsingControllerApi.md#browseAwsS3Path) | **POST** /api/admin/AwsS3BrowsingController/browseAwsS3Path | 
[**getAwsS3Roots**](AwsS3BrowsingControllerApi.md#getAwsS3Roots) | **GET** /api/admin/AwsS3BrowsingController/getAwsS3Roots | 

<a name="browseAwsS3Path"></a>
# **browseAwsS3Path**
> OperationStatusListPathInfo browseAwsS3Path(body, s3SystemCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.AwsS3BrowsingControllerApi();
let body = new GeboAiClient.BrowseParam(); // BrowseParam | 
let s3SystemCode = "s3SystemCode_example"; // String | 

apiInstance.browseAwsS3Path(body, s3SystemCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**BrowseParam**](BrowseParam.md)|  | 
 **s3SystemCode** | **String**|  | 

### Return type

[**OperationStatusListPathInfo**](OperationStatusListPathInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getAwsS3Roots"></a>
# **getAwsS3Roots**
> OperationStatusListGVirtualFilesystemRoot getAwsS3Roots(s3SystemCode)



### Example
```javascript
import {GeboAiClient} from 'gebo.ai.client.js';

let apiInstance = new GeboAiClient.AwsS3BrowsingControllerApi();
let s3SystemCode = "s3SystemCode_example"; // String | 

apiInstance.getAwsS3Roots(s3SystemCode).then((data) => {
  console.log('API called successfully. Returned data: ' + data);
}, (error) => {
  console.error(error);
});

```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **s3SystemCode** | **String**|  | 

### Return type

[**OperationStatusListGVirtualFilesystemRoot**](OperationStatusListGVirtualFilesystemRoot.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

