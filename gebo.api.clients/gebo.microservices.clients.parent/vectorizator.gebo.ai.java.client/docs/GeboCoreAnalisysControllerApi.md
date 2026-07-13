# GeboCoreAnalisysControllerApi

All URIs are relative to *http://localhost:13002*

Method | HTTP request | Description
------------- | ------------- | -------------
[**coreDrillDown**](GeboCoreAnalisysControllerApi.md#coreDrillDown) | **POST** /api/admin/GeboCoreAnalisysController/drillDown | 
[**getTopLevelKnowledgeBaseCategory**](GeboCoreAnalisysControllerApi.md#getTopLevelKnowledgeBaseCategory) | **GET** /api/admin/GeboCoreAnalisysController/getTopLevelKnowledgeBaseCategory | 

<a name="coreDrillDown"></a>
# **coreDrillDown**
> Object coreDrillDown(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.vectorizator.invoker.ApiException;
//import gebo.microservices.api.client.vectorizator.api.GeboCoreAnalisysControllerApi;


GeboCoreAnalisysControllerApi apiInstance = new GeboCoreAnalisysControllerApi();
GStatsHolder body = new GStatsHolder(); // GStatsHolder | 
try {
    Object result = apiInstance.coreDrillDown(body);
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

**Object**

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
//import gebo.microservices.api.client.vectorizator.invoker.ApiException;
//import gebo.microservices.api.client.vectorizator.api.GeboCoreAnalisysControllerApi;


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

