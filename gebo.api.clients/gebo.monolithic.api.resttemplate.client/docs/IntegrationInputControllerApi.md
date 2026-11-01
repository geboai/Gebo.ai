# IntegrationInputControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**publishContents**](IntegrationInputControllerApi.md#publishContents) | **PUT** /api/application/IntegrationInputController/publishContents | 
[**publishSync**](IntegrationInputControllerApi.md#publishSync) | **GET** /api/application/IntegrationInputController/publishSync | 
[**spoolDocumentEnvelope**](IntegrationInputControllerApi.md#spoolDocumentEnvelope) | **POST** /api/application/IntegrationInputController/spoolDocument | 
[**spoolDocumentFile**](IntegrationInputControllerApi.md#spoolDocumentFile) | **PUT** /api/application/IntegrationInputController/spoolDocument | 

<a name="publishContents"></a>
# **publishContents**
> JobTicket publishContents(body, endpointCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.IntegrationInputControllerApi;


IntegrationInputControllerApi apiInstance = new IntegrationInputControllerApi();
List<JobTicket> body = Arrays.asList(new JobTicket()); // List<JobTicket> | 
String endpointCode = "endpointCode_example"; // String | 
try {
    JobTicket result = apiInstance.publishContents(body, endpointCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IntegrationInputControllerApi#publishContents");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**List&lt;JobTicket&gt;**](JobTicket.md)|  |
 **endpointCode** | **String**|  |

### Return type

[**JobTicket**](JobTicket.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="publishSync"></a>
# **publishSync**
> JobTicket publishSync(endpointCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.IntegrationInputControllerApi;


IntegrationInputControllerApi apiInstance = new IntegrationInputControllerApi();
String endpointCode = "endpointCode_example"; // String | 
try {
    JobTicket result = apiInstance.publishSync(endpointCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IntegrationInputControllerApi#publishSync");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **endpointCode** | **String**|  |

### Return type

[**JobTicket**](JobTicket.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="spoolDocumentEnvelope"></a>
# **spoolDocumentEnvelope**
> JobTicket spoolDocumentEnvelope(body, endpointCode, relativePath)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.IntegrationInputControllerApi;


IntegrationInputControllerApi apiInstance = new IntegrationInputControllerApi();
IntegrationDocumentEnvelop body = new IntegrationDocumentEnvelop(); // IntegrationDocumentEnvelop | 
String endpointCode = "endpointCode_example"; // String | 
String relativePath = "relativePath_example"; // String | 
try {
    JobTicket result = apiInstance.spoolDocumentEnvelope(body, endpointCode, relativePath);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IntegrationInputControllerApi#spoolDocumentEnvelope");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**IntegrationDocumentEnvelop**](IntegrationDocumentEnvelop.md)|  |
 **endpointCode** | **String**|  |
 **relativePath** | **String**|  |

### Return type

[**JobTicket**](JobTicket.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="spoolDocumentFile"></a>
# **spoolDocumentFile**
> JobTicket spoolDocumentFile(file, endpointCode, relativePath)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.IntegrationInputControllerApi;


IntegrationInputControllerApi apiInstance = new IntegrationInputControllerApi();
File file = new File("file_example"); // File | 
String endpointCode = "endpointCode_example"; // String | 
String relativePath = "relativePath_example"; // String | 
try {
    JobTicket result = apiInstance.spoolDocumentFile(file, endpointCode, relativePath);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IntegrationInputControllerApi#spoolDocumentFile");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **file** | **File**|  |
 **endpointCode** | **String**|  |
 **relativePath** | **String**|  |

### Return type

[**JobTicket**](JobTicket.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: application/json

