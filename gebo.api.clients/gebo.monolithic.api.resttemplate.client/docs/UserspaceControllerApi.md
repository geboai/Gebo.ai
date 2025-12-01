# UserspaceControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteUserKnowledgebase**](UserspaceControllerApi.md#deleteUserKnowledgebase) | **POST** /api/user/UserspaceController/deleteUserKnowledgebase | 
[**deleteUserspaceFiles**](UserspaceControllerApi.md#deleteUserspaceFiles) | **POST** /api/user/UserspaceController/deleteUserspaceFiles | 
[**deleteUserspaceFolder**](UserspaceControllerApi.md#deleteUserspaceFolder) | **POST** /api/user/UserspaceController/deleteUserspaceFolder | 
[**findUserKnowledgebaseByCode**](UserspaceControllerApi.md#findUserKnowledgebaseByCode) | **GET** /api/user/UserspaceController/findUserKnowledgebaseByCode | 
[**findUserspaceFileByCodes**](UserspaceControllerApi.md#findUserspaceFileByCodes) | **POST** /api/user/UserspaceController/findUserspaceFileByCodes | 
[**findUserspaceFolderByCode**](UserspaceControllerApi.md#findUserspaceFolderByCode) | **GET** /api/user/UserspaceController/findUserspaceFolderByCode | 
[**getPersonalKnowledgebases**](UserspaceControllerApi.md#getPersonalKnowledgebases) | **GET** /api/user/UserspaceController/getPersonalKnowledgebases | 
[**getPublishingStatus**](UserspaceControllerApi.md#getPublishingStatus) | **POST** /api/user/UserspaceController/getPublishingStatus | 
[**listChildPersonalKnowledgebases**](UserspaceControllerApi.md#listChildPersonalKnowledgebases) | **POST** /api/user/UserspaceController/listChildPersonalKnowledgebases | 
[**listUserspaceFiles**](UserspaceControllerApi.md#listUserspaceFiles) | **GET** /api/user/UserspaceController/listUserspaceFiles | 
[**listUserspaceFolders**](UserspaceControllerApi.md#listUserspaceFolders) | **GET** /api/user/UserspaceController/listUserspaceFolders | 
[**newUserKnowledgebase**](UserspaceControllerApi.md#newUserKnowledgebase) | **POST** /api/user/UserspaceController/newUserKnowledgebase | 
[**newUserspaceFolder**](UserspaceControllerApi.md#newUserspaceFolder) | **POST** /api/user/UserspaceController/newUserspaceFolder | 
[**publishFolder**](UserspaceControllerApi.md#publishFolder) | **POST** /api/user/UserspaceController/publishFolder | 
[**transferUploadsToUserSpaceAndPublish**](UserspaceControllerApi.md#transferUploadsToUserSpaceAndPublish) | **POST** /api/user/UserspaceController/transferUploadsToUserSpaceAndPublish | 
[**updateUserKnowledgebase**](UserspaceControllerApi.md#updateUserKnowledgebase) | **POST** /api/user/UserspaceController/updateUserKnowledgebase | 
[**updateUserspaceFolder**](UserspaceControllerApi.md#updateUserspaceFolder) | **POST** /api/user/UserspaceController/updateUserspaceFolder | 

<a name="deleteUserKnowledgebase"></a>
# **deleteUserKnowledgebase**
> deleteUserKnowledgebase(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UserspaceControllerApi;


UserspaceControllerApi apiInstance = new UserspaceControllerApi();
UserspaceKnowledgebaseDto body = new UserspaceKnowledgebaseDto(); // UserspaceKnowledgebaseDto | 
try {
    apiInstance.deleteUserKnowledgebase(body);
} catch (ApiException e) {
    System.err.println("Exception when calling UserspaceControllerApi#deleteUserKnowledgebase");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UserspaceKnowledgebaseDto**](UserspaceKnowledgebaseDto.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="deleteUserspaceFiles"></a>
# **deleteUserspaceFiles**
> deleteUserspaceFiles(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UserspaceControllerApi;


UserspaceControllerApi apiInstance = new UserspaceControllerApi();
List<UserspaceFileDto> body = Arrays.asList(new UserspaceFileDto()); // List<UserspaceFileDto> | 
try {
    apiInstance.deleteUserspaceFiles(body);
} catch (ApiException e) {
    System.err.println("Exception when calling UserspaceControllerApi#deleteUserspaceFiles");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**List&lt;UserspaceFileDto&gt;**](UserspaceFileDto.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="deleteUserspaceFolder"></a>
# **deleteUserspaceFolder**
> deleteUserspaceFolder(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UserspaceControllerApi;


UserspaceControllerApi apiInstance = new UserspaceControllerApi();
UserspaceFolderDto body = new UserspaceFolderDto(); // UserspaceFolderDto | 
try {
    apiInstance.deleteUserspaceFolder(body);
} catch (ApiException e) {
    System.err.println("Exception when calling UserspaceControllerApi#deleteUserspaceFolder");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UserspaceFolderDto**](UserspaceFolderDto.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="findUserKnowledgebaseByCode"></a>
# **findUserKnowledgebaseByCode**
> UserspaceKnowledgebaseDto findUserKnowledgebaseByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UserspaceControllerApi;


UserspaceControllerApi apiInstance = new UserspaceControllerApi();
String code = "code_example"; // String | 
try {
    UserspaceKnowledgebaseDto result = apiInstance.findUserKnowledgebaseByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserspaceControllerApi#findUserKnowledgebaseByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**UserspaceKnowledgebaseDto**](UserspaceKnowledgebaseDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findUserspaceFileByCodes"></a>
# **findUserspaceFileByCodes**
> List&lt;UserspaceFileDto&gt; findUserspaceFileByCodes(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UserspaceControllerApi;


UserspaceControllerApi apiInstance = new UserspaceControllerApi();
List<String> body = Arrays.asList("body_example"); // List<String> | 
try {
    List<UserspaceFileDto> result = apiInstance.findUserspaceFileByCodes(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserspaceControllerApi#findUserspaceFileByCodes");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**List&lt;String&gt;**](String.md)|  |

### Return type

[**List&lt;UserspaceFileDto&gt;**](UserspaceFileDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findUserspaceFolderByCode"></a>
# **findUserspaceFolderByCode**
> UserspaceFolderDto findUserspaceFolderByCode(code)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UserspaceControllerApi;


UserspaceControllerApi apiInstance = new UserspaceControllerApi();
String code = "code_example"; // String | 
try {
    UserspaceFolderDto result = apiInstance.findUserspaceFolderByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserspaceControllerApi#findUserspaceFolderByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **String**|  |

### Return type

[**UserspaceFolderDto**](UserspaceFolderDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getPersonalKnowledgebases"></a>
# **getPersonalKnowledgebases**
> List&lt;UserspaceKnowledgebaseDto&gt; getPersonalKnowledgebases()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UserspaceControllerApi;


UserspaceControllerApi apiInstance = new UserspaceControllerApi();
try {
    List<UserspaceKnowledgebaseDto> result = apiInstance.getPersonalKnowledgebases();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserspaceControllerApi#getPersonalKnowledgebases");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;UserspaceKnowledgebaseDto&gt;**](UserspaceKnowledgebaseDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="getPublishingStatus"></a>
# **getPublishingStatus**
> PublishingStatus getPublishingStatus(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UserspaceControllerApi;


UserspaceControllerApi apiInstance = new UserspaceControllerApi();
UserspaceFolderDto body = new UserspaceFolderDto(); // UserspaceFolderDto | 
try {
    PublishingStatus result = apiInstance.getPublishingStatus(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserspaceControllerApi#getPublishingStatus");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UserspaceFolderDto**](UserspaceFolderDto.md)|  |

### Return type

[**PublishingStatus**](PublishingStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="listChildPersonalKnowledgebases"></a>
# **listChildPersonalKnowledgebases**
> List&lt;UserspaceKnowledgebaseDto&gt; listChildPersonalKnowledgebases(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UserspaceControllerApi;


UserspaceControllerApi apiInstance = new UserspaceControllerApi();
List<String> body = Arrays.asList("body_example"); // List<String> | 
try {
    List<UserspaceKnowledgebaseDto> result = apiInstance.listChildPersonalKnowledgebases(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserspaceControllerApi#listChildPersonalKnowledgebases");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**List&lt;String&gt;**](String.md)|  |

### Return type

[**List&lt;UserspaceKnowledgebaseDto&gt;**](UserspaceKnowledgebaseDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="listUserspaceFiles"></a>
# **listUserspaceFiles**
> List&lt;UserspaceFileDto&gt; listUserspaceFiles(userspaceUploadCode)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UserspaceControllerApi;


UserspaceControllerApi apiInstance = new UserspaceControllerApi();
String userspaceUploadCode = "userspaceUploadCode_example"; // String | 
try {
    List<UserspaceFileDto> result = apiInstance.listUserspaceFiles(userspaceUploadCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserspaceControllerApi#listUserspaceFiles");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userspaceUploadCode** | **String**|  |

### Return type

[**List&lt;UserspaceFileDto&gt;**](UserspaceFileDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="listUserspaceFolders"></a>
# **listUserspaceFolders**
> List&lt;UserspaceFolderDto&gt; listUserspaceFolders(userspaceKnowledgeBase)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UserspaceControllerApi;


UserspaceControllerApi apiInstance = new UserspaceControllerApi();
String userspaceKnowledgeBase = "userspaceKnowledgeBase_example"; // String | 
try {
    List<UserspaceFolderDto> result = apiInstance.listUserspaceFolders(userspaceKnowledgeBase);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserspaceControllerApi#listUserspaceFolders");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userspaceKnowledgeBase** | **String**|  |

### Return type

[**List&lt;UserspaceFolderDto&gt;**](UserspaceFolderDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="newUserKnowledgebase"></a>
# **newUserKnowledgebase**
> UserspaceKnowledgebaseDto newUserKnowledgebase(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UserspaceControllerApi;


UserspaceControllerApi apiInstance = new UserspaceControllerApi();
UserspaceKnowledgebaseDto body = new UserspaceKnowledgebaseDto(); // UserspaceKnowledgebaseDto | 
try {
    UserspaceKnowledgebaseDto result = apiInstance.newUserKnowledgebase(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserspaceControllerApi#newUserKnowledgebase");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UserspaceKnowledgebaseDto**](UserspaceKnowledgebaseDto.md)|  |

### Return type

[**UserspaceKnowledgebaseDto**](UserspaceKnowledgebaseDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="newUserspaceFolder"></a>
# **newUserspaceFolder**
> UserspaceFolderDto newUserspaceFolder(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UserspaceControllerApi;


UserspaceControllerApi apiInstance = new UserspaceControllerApi();
UserspaceFolderDto body = new UserspaceFolderDto(); // UserspaceFolderDto | 
try {
    UserspaceFolderDto result = apiInstance.newUserspaceFolder(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserspaceControllerApi#newUserspaceFolder");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UserspaceFolderDto**](UserspaceFolderDto.md)|  |

### Return type

[**UserspaceFolderDto**](UserspaceFolderDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="publishFolder"></a>
# **publishFolder**
> OperationStatusPublishingStatus publishFolder(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UserspaceControllerApi;


UserspaceControllerApi apiInstance = new UserspaceControllerApi();
UserspaceFolderDto body = new UserspaceFolderDto(); // UserspaceFolderDto | 
try {
    OperationStatusPublishingStatus result = apiInstance.publishFolder(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserspaceControllerApi#publishFolder");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UserspaceFolderDto**](UserspaceFolderDto.md)|  |

### Return type

[**OperationStatusPublishingStatus**](OperationStatusPublishingStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="transferUploadsToUserSpaceAndPublish"></a>
# **transferUploadsToUserSpaceAndPublish**
> OperationStatusPublishingStatus transferUploadsToUserSpaceAndPublish(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UserspaceControllerApi;


UserspaceControllerApi apiInstance = new UserspaceControllerApi();
UserUploadToUserSpaceParam body = new UserUploadToUserSpaceParam(); // UserUploadToUserSpaceParam | 
try {
    OperationStatusPublishingStatus result = apiInstance.transferUploadsToUserSpaceAndPublish(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserspaceControllerApi#transferUploadsToUserSpaceAndPublish");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UserUploadToUserSpaceParam**](UserUploadToUserSpaceParam.md)|  |

### Return type

[**OperationStatusPublishingStatus**](OperationStatusPublishingStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateUserKnowledgebase"></a>
# **updateUserKnowledgebase**
> UserspaceKnowledgebaseDto updateUserKnowledgebase(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UserspaceControllerApi;


UserspaceControllerApi apiInstance = new UserspaceControllerApi();
UserspaceKnowledgebaseDto body = new UserspaceKnowledgebaseDto(); // UserspaceKnowledgebaseDto | 
try {
    UserspaceKnowledgebaseDto result = apiInstance.updateUserKnowledgebase(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserspaceControllerApi#updateUserKnowledgebase");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UserspaceKnowledgebaseDto**](UserspaceKnowledgebaseDto.md)|  |

### Return type

[**UserspaceKnowledgebaseDto**](UserspaceKnowledgebaseDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateUserspaceFolder"></a>
# **updateUserspaceFolder**
> UserspaceFolderDto updateUserspaceFolder(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.UserspaceControllerApi;


UserspaceControllerApi apiInstance = new UserspaceControllerApi();
UserspaceFolderDto body = new UserspaceFolderDto(); // UserspaceFolderDto | 
try {
    UserspaceFolderDto result = apiInstance.updateUserspaceFolder(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserspaceControllerApi#updateUserspaceFolder");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UserspaceFolderDto**](UserspaceFolderDto.md)|  |

### Return type

[**UserspaceFolderDto**](UserspaceFolderDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

