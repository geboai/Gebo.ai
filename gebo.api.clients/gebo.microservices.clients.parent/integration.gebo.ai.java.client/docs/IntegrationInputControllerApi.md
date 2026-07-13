# IntegrationInputControllerApi

All URIs are relative to *http://localhost:13015*

Method | HTTP request | Description
------------- | ------------- | -------------
[**publishContents**](IntegrationInputControllerApi.md#publishContents) | **PUT** /api/application/IntegrationInputController/publishContents | 
[**publishSync**](IntegrationInputControllerApi.md#publishSync) | **GET** /api/application/IntegrationInputController/publishSync | 
[**spoolDocument**](IntegrationInputControllerApi.md#spoolDocument) | **POST** /api/application/IntegrationInputController/spoolDocument | 
[**spoolDocument1**](IntegrationInputControllerApi.md#spoolDocument1) | **PUT** /api/application/IntegrationInputController/spoolDocument | 

<a name="publishContents"></a>
# **publishContents**
> JobTicket publishContents(body, endpointCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.integration.invoker.ApiException;
//import gebo.microservices.api.client.integration.api.IntegrationInputControllerApi;


IntegrationInputControllerApi apiInstance = new IntegrationInputControllerApi();
Object body = null; // Object | 
Object endpointCode = null; // Object | 
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
 **body** | [**Object**](Object.md)|  |
 **endpointCode** | [**Object**](.md)|  |

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
//import gebo.microservices.api.client.integration.invoker.ApiException;
//import gebo.microservices.api.client.integration.api.IntegrationInputControllerApi;


IntegrationInputControllerApi apiInstance = new IntegrationInputControllerApi();
Object endpointCode = null; // Object | 
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
 **endpointCode** | [**Object**](.md)|  |

### Return type

[**JobTicket**](JobTicket.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="spoolDocument"></a>
# **spoolDocument**
> JobTicket spoolDocument(body, endpointCode, relativePath)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.integration.invoker.ApiException;
//import gebo.microservices.api.client.integration.api.IntegrationInputControllerApi;


IntegrationInputControllerApi apiInstance = new IntegrationInputControllerApi();
IntegrationDocumentEnvelop body = new IntegrationDocumentEnvelop(); // IntegrationDocumentEnvelop | 
Object endpointCode = null; // Object | 
Object relativePath = null; // Object | 
try {
    JobTicket result = apiInstance.spoolDocument(body, endpointCode, relativePath);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IntegrationInputControllerApi#spoolDocument");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**IntegrationDocumentEnvelop**](IntegrationDocumentEnvelop.md)|  |
 **endpointCode** | [**Object**](.md)|  |
 **relativePath** | [**Object**](.md)|  |

### Return type

[**JobTicket**](JobTicket.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="spoolDocument1"></a>
# **spoolDocument1**
> JobTicket spoolDocument1(file, endpointCode, relativePath)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.integration.invoker.ApiException;
//import gebo.microservices.api.client.integration.api.IntegrationInputControllerApi;


IntegrationInputControllerApi apiInstance = new IntegrationInputControllerApi();
Object file = null; // Object | 
Object endpointCode = null; // Object | 
Object relativePath = null; // Object | 
try {
    JobTicket result = apiInstance.spoolDocument1(file, endpointCode, relativePath);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IntegrationInputControllerApi#spoolDocument1");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **file** | [**Object**](.md)|  |
 **endpointCode** | [**Object**](.md)|  |
 **relativePath** | [**Object**](.md)|  |

### Return type

[**JobTicket**](JobTicket.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: application/json

