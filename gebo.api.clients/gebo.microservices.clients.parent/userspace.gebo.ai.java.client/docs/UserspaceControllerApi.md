# UserspaceControllerApi

All URIs are relative to *http://localhost:13008/userspace*

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
[**publishUserspaceProjectEndpoint**](UserspaceControllerApi.md#publishUserspaceProjectEndpoint) | **POST** /api/user/UserspaceController/publishUserspaceProjectEndpoint | 
[**transferUploadsToUserSpaceAndPublish**](UserspaceControllerApi.md#transferUploadsToUserSpaceAndPublish) | **POST** /api/user/UserspaceController/transferUploadsToUserSpaceAndPublish | 
[**updateUserKnowledgebase**](UserspaceControllerApi.md#updateUserKnowledgebase) | **POST** /api/user/UserspaceController/updateUserKnowledgebase | 
[**updateUserspaceFolder**](UserspaceControllerApi.md#updateUserspaceFolder) | **POST** /api/user/UserspaceController/updateUserspaceFolder | 

<a name="deleteUserKnowledgebase"></a>
# **deleteUserKnowledgebase**
> deleteUserKnowledgebase(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.userspace.invoker.ApiException;
//import gebo.microservices.api.client.userspace.api.UserspaceControllerApi;


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
//import gebo.microservices.api.client.userspace.invoker.ApiException;
//import gebo.microservices.api.client.userspace.api.UserspaceControllerApi;


UserspaceControllerApi apiInstance = new UserspaceControllerApi();
Object body = null; // Object | 
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
 **body** | [**Object**](Object.md)|  |

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
//import gebo.microservices.api.client.userspace.invoker.ApiException;
//import gebo.microservices.api.client.userspace.api.UserspaceControllerApi;


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
//import gebo.microservices.api.client.userspace.invoker.ApiException;
//import gebo.microservices.api.client.userspace.api.UserspaceControllerApi;


UserspaceControllerApi apiInstance = new UserspaceControllerApi();
Object code = null; // Object | 
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
 **code** | [**Object**](.md)|  |

### Return type

[**UserspaceKnowledgebaseDto**](UserspaceKnowledgebaseDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findUserspaceFileByCodes"></a>
# **findUserspaceFileByCodes**
> Object findUserspaceFileByCodes(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.userspace.invoker.ApiException;
//import gebo.microservices.api.client.userspace.api.UserspaceControllerApi;


UserspaceControllerApi apiInstance = new UserspaceControllerApi();
Object body = null; // Object | 
try {
    Object result = apiInstance.findUserspaceFileByCodes(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserspaceControllerApi#findUserspaceFileByCodes");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Object**](Object.md)|  |

### Return type

**Object**

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
//import gebo.microservices.api.client.userspace.invoker.ApiException;
//import gebo.microservices.api.client.userspace.api.UserspaceControllerApi;


UserspaceControllerApi apiInstance = new UserspaceControllerApi();
Object code = null; // Object | 
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
 **code** | [**Object**](.md)|  |

### Return type

[**UserspaceFolderDto**](UserspaceFolderDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getPersonalKnowledgebases"></a>
# **getPersonalKnowledgebases**
> Object getPersonalKnowledgebases()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.userspace.invoker.ApiException;
//import gebo.microservices.api.client.userspace.api.UserspaceControllerApi;


UserspaceControllerApi apiInstance = new UserspaceControllerApi();
try {
    Object result = apiInstance.getPersonalKnowledgebases();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserspaceControllerApi#getPersonalKnowledgebases");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getPublishingStatus"></a>
# **getPublishingStatus**
> PublishingStatus getPublishingStatus(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.userspace.invoker.ApiException;
//import gebo.microservices.api.client.userspace.api.UserspaceControllerApi;


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
> Object listChildPersonalKnowledgebases(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.userspace.invoker.ApiException;
//import gebo.microservices.api.client.userspace.api.UserspaceControllerApi;


UserspaceControllerApi apiInstance = new UserspaceControllerApi();
Object body = null; // Object | 
try {
    Object result = apiInstance.listChildPersonalKnowledgebases(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserspaceControllerApi#listChildPersonalKnowledgebases");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Object**](Object.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="listUserspaceFiles"></a>
# **listUserspaceFiles**
> Object listUserspaceFiles(userspaceUploadCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.userspace.invoker.ApiException;
//import gebo.microservices.api.client.userspace.api.UserspaceControllerApi;


UserspaceControllerApi apiInstance = new UserspaceControllerApi();
Object userspaceUploadCode = null; // Object | 
try {
    Object result = apiInstance.listUserspaceFiles(userspaceUploadCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserspaceControllerApi#listUserspaceFiles");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userspaceUploadCode** | [**Object**](.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="listUserspaceFolders"></a>
# **listUserspaceFolders**
> Object listUserspaceFolders(userspaceKnowledgeBase)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.userspace.invoker.ApiException;
//import gebo.microservices.api.client.userspace.api.UserspaceControllerApi;


UserspaceControllerApi apiInstance = new UserspaceControllerApi();
Object userspaceKnowledgeBase = null; // Object | 
try {
    Object result = apiInstance.listUserspaceFolders(userspaceKnowledgeBase);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserspaceControllerApi#listUserspaceFolders");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userspaceKnowledgeBase** | [**Object**](.md)|  |

### Return type

**Object**

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
//import gebo.microservices.api.client.userspace.invoker.ApiException;
//import gebo.microservices.api.client.userspace.api.UserspaceControllerApi;


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
//import gebo.microservices.api.client.userspace.invoker.ApiException;
//import gebo.microservices.api.client.userspace.api.UserspaceControllerApi;


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
//import gebo.microservices.api.client.userspace.invoker.ApiException;
//import gebo.microservices.api.client.userspace.api.UserspaceControllerApi;


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

<a name="publishUserspaceProjectEndpoint"></a>
# **publishUserspaceProjectEndpoint**
> OperationStatusGJobStatus publishUserspaceProjectEndpoint(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.userspace.invoker.ApiException;
//import gebo.microservices.api.client.userspace.api.UserspaceControllerApi;


UserspaceControllerApi apiInstance = new UserspaceControllerApi();
GUserspaceProjectEndpoint body = new GUserspaceProjectEndpoint(); // GUserspaceProjectEndpoint | 
try {
    OperationStatusGJobStatus result = apiInstance.publishUserspaceProjectEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserspaceControllerApi#publishUserspaceProjectEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GUserspaceProjectEndpoint**](GUserspaceProjectEndpoint.md)|  |

### Return type

[**OperationStatusGJobStatus**](OperationStatusGJobStatus.md)

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
//import gebo.microservices.api.client.userspace.invoker.ApiException;
//import gebo.microservices.api.client.userspace.api.UserspaceControllerApi;


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
//import gebo.microservices.api.client.userspace.invoker.ApiException;
//import gebo.microservices.api.client.userspace.api.UserspaceControllerApi;


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
//import gebo.microservices.api.client.userspace.invoker.ApiException;
//import gebo.microservices.api.client.userspace.api.UserspaceControllerApi;


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

