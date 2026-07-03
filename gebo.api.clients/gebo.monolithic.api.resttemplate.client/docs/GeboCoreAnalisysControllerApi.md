# GeboCoreAnalisysControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**coreDrillDown**](GeboCoreAnalisysControllerApi.md#coreDrillDown) | **POST** /api/admin/GeboCoreAnalisysController/drillDown | 
[**getTopLevelKnowledgeBaseCategory**](GeboCoreAnalisysControllerApi.md#getTopLevelKnowledgeBaseCategory) | **GET** /api/admin/GeboCoreAnalisysController/getTopLevelKnowledgeBaseCategory | 

<a name="coreDrillDown"></a>
# **coreDrillDown**
> List&lt;GStatsHolder&gt; coreDrillDown(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboCoreAnalisysControllerApi;


GeboCoreAnalisysControllerApi apiInstance = new GeboCoreAnalisysControllerApi();
GStatsHolder body = new GStatsHolder(); // GStatsHolder | 
try {
    List<GStatsHolder> result = apiInstance.coreDrillDown(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboCoreAnalisysControllerApi#coreDrillDown");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GStatsHolder**](GStatsHolder.md)|  |

### Return type

[**List&lt;GStatsHolder&gt;**](GStatsHolder.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getTopLevelKnowledgeBaseCategory"></a>
# **getTopLevelKnowledgeBaseCategory**
> GStatsHolder getTopLevelKnowledgeBaseCategory()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GeboCoreAnalisysControllerApi;


GeboCoreAnalisysControllerApi apiInstance = new GeboCoreAnalisysControllerApi();
try {
    GStatsHolder result = apiInstance.getTopLevelKnowledgeBaseCategory();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GeboCoreAnalisysControllerApi#getTopLevelKnowledgeBaseCategory");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**GStatsHolder**](GStatsHolder.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

