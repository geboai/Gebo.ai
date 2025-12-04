# ContentMetaInfosControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**findDocumentReferenceViewByCode**](ContentMetaInfosControllerApi.md#findDocumentReferenceViewByCode) | **POST** /api/users/ContentMetaInfosController/findDocumentReferenceViewByCode | 
[**getContentMetaInfos**](ContentMetaInfosControllerApi.md#getContentMetaInfos) | **GET** /api/users/ContentMetaInfosController/getContentMetaInfos | 
[**getContentObject**](ContentMetaInfosControllerApi.md#getContentObject) | **GET** /api/users/ContentMetaInfosController/getContentObject | 
[**searchByDocumentName**](ContentMetaInfosControllerApi.md#searchByDocumentName) | **POST** /api/users/ContentMetaInfosController/searchByDocumentName | 
[**searchByDocumentNamePaged**](ContentMetaInfosControllerApi.md#searchByDocumentNamePaged) | **POST** /api/users/ContentMetaInfosController/searchByDocumentNamePaged | 

<a name="findDocumentReferenceViewByCode"></a>
# **findDocumentReferenceViewByCode**
> List&lt;DocumentReferenceView&gt; findDocumentReferenceViewByCode(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ContentMetaInfosControllerApi;


ContentMetaInfosControllerApi apiInstance = new ContentMetaInfosControllerApi();
List<String> body = Arrays.asList("body_example"); // List<String> | 
try {
    List<DocumentReferenceView> result = apiInstance.findDocumentReferenceViewByCode(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentMetaInfosControllerApi#findDocumentReferenceViewByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**List&lt;String&gt;**](String.md)|  |

### Return type

[**List&lt;DocumentReferenceView&gt;**](DocumentReferenceView.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getContentMetaInfos"></a>
# **getContentMetaInfos**
> ContentMetaInfo getContentMetaInfos(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ContentMetaInfosControllerApi;


ContentMetaInfosControllerApi apiInstance = new ContentMetaInfosControllerApi();
String code = "code_example"; // String | 
try {
    ContentMetaInfo result = apiInstance.getContentMetaInfos(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentMetaInfosControllerApi#getContentMetaInfos");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**ContentMetaInfo**](ContentMetaInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getContentObject"></a>
# **getContentObject**
> ContentObject getContentObject(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ContentMetaInfosControllerApi;


ContentMetaInfosControllerApi apiInstance = new ContentMetaInfosControllerApi();
String code = "code_example"; // String | 
try {
    ContentObject result = apiInstance.getContentObject(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentMetaInfosControllerApi#getContentObject");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**ContentObject**](ContentObject.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="searchByDocumentName"></a>
# **searchByDocumentName**
> List&lt;DocumentReferenceView&gt; searchByDocumentName(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ContentMetaInfosControllerApi;


ContentMetaInfosControllerApi apiInstance = new ContentMetaInfosControllerApi();
SearchDocumentByNameParam body = new SearchDocumentByNameParam(); // SearchDocumentByNameParam | 
try {
    List<DocumentReferenceView> result = apiInstance.searchByDocumentName(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentMetaInfosControllerApi#searchByDocumentName");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SearchDocumentByNameParam**](SearchDocumentByNameParam.md)|  |

### Return type

[**List&lt;DocumentReferenceView&gt;**](DocumentReferenceView.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="searchByDocumentNamePaged"></a>
# **searchByDocumentNamePaged**
> PageDocumentReferenceView searchByDocumentNamePaged(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.ContentMetaInfosControllerApi;


ContentMetaInfosControllerApi apiInstance = new ContentMetaInfosControllerApi();
SearchDocumentByNamePagedParam body = new SearchDocumentByNamePagedParam(); // SearchDocumentByNamePagedParam | 
try {
    PageDocumentReferenceView result = apiInstance.searchByDocumentNamePaged(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ContentMetaInfosControllerApi#searchByDocumentNamePaged");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SearchDocumentByNamePagedParam**](SearchDocumentByNamePagedParam.md)|  |

### Return type

[**PageDocumentReferenceView**](PageDocumentReferenceView.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

