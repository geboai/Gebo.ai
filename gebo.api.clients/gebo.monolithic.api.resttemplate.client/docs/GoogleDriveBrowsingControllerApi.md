# GoogleDriveBrowsingControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**browseGoogleDrivePath**](GoogleDriveBrowsingControllerApi.md#browseGoogleDrivePath) | **POST** /api/admin/GoogleDriveBrowsingController/browseGoogleDrivePath | 
[**getGoogleDriveRoots**](GoogleDriveBrowsingControllerApi.md#getGoogleDriveRoots) | **GET** /api/admin/GoogleDriveBrowsingController/getGoogleDriveRoots | 

<a name="browseGoogleDrivePath"></a>
# **browseGoogleDrivePath**
> OperationStatusListPathInfo browseGoogleDrivePath(body, driveSystemCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleDriveBrowsingControllerApi;


GoogleDriveBrowsingControllerApi apiInstance = new GoogleDriveBrowsingControllerApi();
BrowseParam body = new BrowseParam(); // BrowseParam | 
String driveSystemCode = "driveSystemCode_example"; // String | 
try {
    OperationStatusListPathInfo result = apiInstance.browseGoogleDrivePath(body, driveSystemCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveBrowsingControllerApi#browseGoogleDrivePath");
    e.printStackTrace();
}
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
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleDriveBrowsingControllerApi;


GoogleDriveBrowsingControllerApi apiInstance = new GoogleDriveBrowsingControllerApi();
String driveSystemCode = "driveSystemCode_example"; // String | 
try {
    OperationStatusListGVirtualFilesystemRoot result = apiInstance.getGoogleDriveRoots(driveSystemCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveBrowsingControllerApi#getGoogleDriveRoots");
    e.printStackTrace();
}
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

