# AwsS3BrowsingControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**browseAwsS3Path**](AwsS3BrowsingControllerApi.md#browseAwsS3Path) | **POST** /api/admin/AwsS3BrowsingController/browseAwsS3Path | 
[**getAwsS3Roots**](AwsS3BrowsingControllerApi.md#getAwsS3Roots) | **GET** /api/admin/AwsS3BrowsingController/getAwsS3Roots | 

<a name="browseAwsS3Path"></a>
# **browseAwsS3Path**
> OperationStatusListPathInfo browseAwsS3Path(body, s3SystemCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.AwsS3BrowsingControllerApi;


AwsS3BrowsingControllerApi apiInstance = new AwsS3BrowsingControllerApi();
BrowseParam body = new BrowseParam(); // BrowseParam | 
String s3SystemCode = "s3SystemCode_example"; // String | 
try {
    OperationStatusListPathInfo result = apiInstance.browseAwsS3Path(body, s3SystemCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AwsS3BrowsingControllerApi#browseAwsS3Path");
    e.printStackTrace();
}
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
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.AwsS3BrowsingControllerApi;


AwsS3BrowsingControllerApi apiInstance = new AwsS3BrowsingControllerApi();
String s3SystemCode = "s3SystemCode_example"; // String | 
try {
    OperationStatusListGVirtualFilesystemRoot result = apiInstance.getAwsS3Roots(s3SystemCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AwsS3BrowsingControllerApi#getAwsS3Roots");
    e.printStackTrace();
}
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

