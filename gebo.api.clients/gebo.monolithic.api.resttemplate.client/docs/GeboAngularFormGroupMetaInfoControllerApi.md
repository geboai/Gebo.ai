# GeboAngularFormGroupMetaInfoControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getFormGroupsMetaInfos**](GeboAngularFormGroupMetaInfoControllerApi.md#getFormGroupsMetaInfos) | **GET** /api/admin/AngularFormGroupController/getFormGroupsMetaInfos | 

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

