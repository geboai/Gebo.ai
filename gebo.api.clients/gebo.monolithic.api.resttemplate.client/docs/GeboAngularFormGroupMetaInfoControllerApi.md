# GeboAngularFormGroupMetaInfoControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**checkDeletableByGObjectRef**](GeboAngularFormGroupMetaInfoControllerApi.md#checkDeletableByGObjectRef) | **POST** /api/admin/AngularFormGroupController/checkDeletableByGObjectRef | 
[**checkDeletableBySimpleObjectRef**](GeboAngularFormGroupMetaInfoControllerApi.md#checkDeletableBySimpleObjectRef) | **POST** /api/admin/AngularFormGroupController/checkDeletableBySimpleGObjectRef | 
[**getFormGroupsMetaInfos**](GeboAngularFormGroupMetaInfoControllerApi.md#getFormGroupsMetaInfos) | **GET** /api/admin/AngularFormGroupController/getFormGroupsMetaInfos | 

<a name="checkDeletableByGObjectRef"></a>
# **checkDeletableByGObjectRef**
> DeletableStatus checkDeletableByGObjectRef(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAngularFormGroupMetaInfoControllerApi;


GeboAngularFormGroupMetaInfoControllerApi apiInstance = new GeboAngularFormGroupMetaInfoControllerApi();
GObjectRef body = new GObjectRef(); // GObjectRef | 
try {
    DeletableStatus result = apiInstance.checkDeletableByGObjectRef(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAngularFormGroupMetaInfoControllerApi#checkDeletableByGObjectRef");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GObjectRef**](GObjectRef.md)|  |

### Return type

[**DeletableStatus**](DeletableStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="checkDeletableBySimpleObjectRef"></a>
# **checkDeletableBySimpleObjectRef**
> DeletableStatus checkDeletableBySimpleObjectRef(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAngularFormGroupMetaInfoControllerApi;


GeboAngularFormGroupMetaInfoControllerApi apiInstance = new GeboAngularFormGroupMetaInfoControllerApi();
SimpleGObjectRef body = new SimpleGObjectRef(); // SimpleGObjectRef | 
try {
    DeletableStatus result = apiInstance.checkDeletableBySimpleObjectRef(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAngularFormGroupMetaInfoControllerApi#checkDeletableBySimpleObjectRef");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SimpleGObjectRef**](SimpleGObjectRef.md)|  |

### Return type

[**DeletableStatus**](DeletableStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getFormGroupsMetaInfos"></a>
# **getFormGroupsMetaInfos**
> List&lt;FormGroupMetaInfo&gt; getFormGroupsMetaInfos()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboAngularFormGroupMetaInfoControllerApi;


GeboAngularFormGroupMetaInfoControllerApi apiInstance = new GeboAngularFormGroupMetaInfoControllerApi();
try {
    List<FormGroupMetaInfo> result = apiInstance.getFormGroupsMetaInfos();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboAngularFormGroupMetaInfoControllerApi#getFormGroupsMetaInfos");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;FormGroupMetaInfo&gt;**](FormGroupMetaInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

