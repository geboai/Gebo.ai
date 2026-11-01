# FileSystemSharesSettingControllerApi

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
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.FileSystemSharesSettingControllerApi;


FileSystemSharesSettingControllerApi apiInstance = new FileSystemSharesSettingControllerApi();
GFileSystemShareReference body = new GFileSystemShareReference(); // GFileSystemShareReference | 
try {
    OperationStatusGFileSystemShareReference result = apiInstance.checkCanBeInsertedFileSystemShareReference(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FileSystemSharesSettingControllerApi#checkCanBeInsertedFileSystemShareReference");
    e.printStackTrace();
}
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
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.FileSystemSharesSettingControllerApi;


FileSystemSharesSettingControllerApi apiInstance = new FileSystemSharesSettingControllerApi();
GFileSystemShareReference body = new GFileSystemShareReference(); // GFileSystemShareReference | 
try {
    apiInstance.deleteFileSystemShareReference(body);
} catch (ApiException e) {
    System.err.println("Exception when calling FileSystemSharesSettingControllerApi#deleteFileSystemShareReference");
    e.printStackTrace();
}
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
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.FileSystemSharesSettingControllerApi;


FileSystemSharesSettingControllerApi apiInstance = new FileSystemSharesSettingControllerApi();
String code = "code_example"; // String | 
try {
    GFileSystemShareReference result = apiInstance.getFileSystemShareReferenceByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FileSystemSharesSettingControllerApi#getFileSystemShareReferenceByCode");
    e.printStackTrace();
}
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
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.FileSystemSharesSettingControllerApi;


FileSystemSharesSettingControllerApi apiInstance = new FileSystemSharesSettingControllerApi();
BrowseParam body = new BrowseParam(); // BrowseParam | 
try {
    OperationStatusListPathInfo result = apiInstance.getGFileSystemNodeChildrens(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FileSystemSharesSettingControllerApi#getGFileSystemNodeChildrens");
    e.printStackTrace();
}
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
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.FileSystemSharesSettingControllerApi;


FileSystemSharesSettingControllerApi apiInstance = new FileSystemSharesSettingControllerApi();
List<VFilesystemReference> body = Arrays.asList(new VFilesystemReference()); // List<VFilesystemReference> | 
try {
    OperationStatusListVirtualFilesystemNavigationTreeStatus result = apiInstance.getGFileSystemNodeNavigationStatus(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FileSystemSharesSettingControllerApi#getGFileSystemNodeNavigationStatus");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**List&lt;VFilesystemReference&gt;**](VFilesystemReference.md)|  |

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
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.FileSystemSharesSettingControllerApi;


FileSystemSharesSettingControllerApi apiInstance = new FileSystemSharesSettingControllerApi();
try {
    OperationStatusListGVirtualFilesystemRoot result = apiInstance.getRootGFileSystemNodes();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FileSystemSharesSettingControllerApi#getRootGFileSystemNodes");
    e.printStackTrace();
}
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
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.FileSystemSharesSettingControllerApi;


FileSystemSharesSettingControllerApi apiInstance = new FileSystemSharesSettingControllerApi();
try {
    SharedFilesystemUIConfig result = apiInstance.getSharedFileSystemsActualConfiguration();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FileSystemSharesSettingControllerApi#getSharedFileSystemsActualConfiguration");
    e.printStackTrace();
}
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
> List&lt;FSReference&gt; getUsedFilesystemShares(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.FileSystemSharesSettingControllerApi;


FileSystemSharesSettingControllerApi apiInstance = new FileSystemSharesSettingControllerApi();
List<String> body = Arrays.asList("body_example"); // List<String> | 
try {
    List<FSReference> result = apiInstance.getUsedFilesystemShares(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FileSystemSharesSettingControllerApi#getUsedFilesystemShares");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**List&lt;String&gt;**](String.md)|  |

### Return type

[**List&lt;FSReference&gt;**](FSReference.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertFileSystemShareReference"></a>
# **insertFileSystemShareReference**
> GFileSystemShareReference insertFileSystemShareReference(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.FileSystemSharesSettingControllerApi;


FileSystemSharesSettingControllerApi apiInstance = new FileSystemSharesSettingControllerApi();
GFileSystemShareReference body = new GFileSystemShareReference(); // GFileSystemShareReference | 
try {
    GFileSystemShareReference result = apiInstance.insertFileSystemShareReference(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FileSystemSharesSettingControllerApi#insertFileSystemShareReference");
    e.printStackTrace();
}
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

