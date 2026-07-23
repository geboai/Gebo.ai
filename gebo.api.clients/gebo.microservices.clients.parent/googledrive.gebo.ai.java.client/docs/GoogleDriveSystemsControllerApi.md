# GoogleDriveSystemsControllerApi

All URIs are relative to *http://localhost:13013/googledrive*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteGoogleDriveProjectEndpoint**](GoogleDriveSystemsControllerApi.md#deleteGoogleDriveProjectEndpoint) | **POST** /api/admin/GoogleDriveSystemsController/deleteGoogleDriveProjectEndpoint | 
[**deleteGoogleDriveSystem**](GoogleDriveSystemsControllerApi.md#deleteGoogleDriveSystem) | **POST** /api/admin/GoogleDriveSystemsController/deleteGoogleDriveSystem | 
[**fastGoogleDriveConfig**](GoogleDriveSystemsControllerApi.md#fastGoogleDriveConfig) | **POST** /api/admin/GoogleDriveSystemsController/fastGoogleDriveConfig | 
[**findGoogleDriveEndpointsByProject**](GoogleDriveSystemsControllerApi.md#findGoogleDriveEndpointsByProject) | **GET** /api/admin/GoogleDriveSystemsController/findGoogleDriveEndpointsByProject | 
[**findGoogleDriveEndpointsByQbe**](GoogleDriveSystemsControllerApi.md#findGoogleDriveEndpointsByQbe) | **POST** /api/admin/GoogleDriveSystemsController/findGoogleDriveEndpointsByQbe | 
[**findGoogleDriveProjectEndpointByCode**](GoogleDriveSystemsControllerApi.md#findGoogleDriveProjectEndpointByCode) | **GET** /api/admin/GoogleDriveSystemsController/findGoogleDriveProjectEndpointByCode | 
[**findGoogleDriveSystemByCode**](GoogleDriveSystemsControllerApi.md#findGoogleDriveSystemByCode) | **GET** /api/admin/GoogleDriveSystemsController/findGoogleDriveSystemByCode | 
[**getGoogleDriveSystemType**](GoogleDriveSystemsControllerApi.md#getGoogleDriveSystemType) | **GET** /api/admin/GoogleDriveSystemsController/getGoogleDriveSystemType | 
[**getGoogleDriveSystems**](GoogleDriveSystemsControllerApi.md#getGoogleDriveSystems) | **GET** /api/admin/GoogleDriveSystemsController/getGoogleDriveSystems | 
[**insertGoogleDriveProjectEndpoint**](GoogleDriveSystemsControllerApi.md#insertGoogleDriveProjectEndpoint) | **POST** /api/admin/GoogleDriveSystemsController/insertGoogleDriveProjectEndpoint | 
[**insertGoogleDriveSystem**](GoogleDriveSystemsControllerApi.md#insertGoogleDriveSystem) | **POST** /api/admin/GoogleDriveSystemsController/insertGoogleDriveSystem | 
[**publishGoogleDriveProjectEndpoint**](GoogleDriveSystemsControllerApi.md#publishGoogleDriveProjectEndpoint) | **POST** /api/admin/GoogleDriveSystemsController/publishGoogleDriveProjectEndpoint | 
[**updateGoogleDriveProjectEndpoint**](GoogleDriveSystemsControllerApi.md#updateGoogleDriveProjectEndpoint) | **POST** /api/admin/GoogleDriveSystemsController/updateGoogleDriveProjectEndpoint | 
[**updateGoogleDriveSystem**](GoogleDriveSystemsControllerApi.md#updateGoogleDriveSystem) | **POST** /api/admin/GoogleDriveSystemsController/updateGoogleDriveSystem | 

<a name="deleteGoogleDriveProjectEndpoint"></a>
# **deleteGoogleDriveProjectEndpoint**
> deleteGoogleDriveProjectEndpoint(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.googledrive.invoker.ApiException;
//import gebo.microservices.api.client.googledrive.api.GoogleDriveSystemsControllerApi;


GoogleDriveSystemsControllerApi apiInstance = new GoogleDriveSystemsControllerApi();
GGoogleDriveProjectEndpoint body = new GGoogleDriveProjectEndpoint(); // GGoogleDriveProjectEndpoint | 
try {
    apiInstance.deleteGoogleDriveProjectEndpoint(body);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSystemsControllerApi#deleteGoogleDriveProjectEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGoogleDriveProjectEndpoint**](GGoogleDriveProjectEndpoint.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="deleteGoogleDriveSystem"></a>
# **deleteGoogleDriveSystem**
> deleteGoogleDriveSystem(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.googledrive.invoker.ApiException;
//import gebo.microservices.api.client.googledrive.api.GoogleDriveSystemsControllerApi;


GoogleDriveSystemsControllerApi apiInstance = new GoogleDriveSystemsControllerApi();
GGoogleDriveSystem body = new GGoogleDriveSystem(); // GGoogleDriveSystem | 
try {
    apiInstance.deleteGoogleDriveSystem(body);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSystemsControllerApi#deleteGoogleDriveSystem");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGoogleDriveSystem**](GGoogleDriveSystem.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="fastGoogleDriveConfig"></a>
# **fastGoogleDriveConfig**
> OperationStatusGGoogleDriveSystem fastGoogleDriveConfig(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.googledrive.invoker.ApiException;
//import gebo.microservices.api.client.googledrive.api.GoogleDriveSystemsControllerApi;


GoogleDriveSystemsControllerApi apiInstance = new GoogleDriveSystemsControllerApi();
FastGoogleDriveSystemInsert body = new FastGoogleDriveSystemInsert(); // FastGoogleDriveSystemInsert | 
try {
    OperationStatusGGoogleDriveSystem result = apiInstance.fastGoogleDriveConfig(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSystemsControllerApi#fastGoogleDriveConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**FastGoogleDriveSystemInsert**](FastGoogleDriveSystemInsert.md)|  |

### Return type

[**OperationStatusGGoogleDriveSystem**](OperationStatusGGoogleDriveSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findGoogleDriveEndpointsByProject"></a>
# **findGoogleDriveEndpointsByProject**
> Object findGoogleDriveEndpointsByProject(parentProjectCode)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.googledrive.invoker.ApiException;
//import gebo.microservices.api.client.googledrive.api.GoogleDriveSystemsControllerApi;


GoogleDriveSystemsControllerApi apiInstance = new GoogleDriveSystemsControllerApi();
Object parentProjectCode = null; // Object | 
try {
    Object result = apiInstance.findGoogleDriveEndpointsByProject(parentProjectCode);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSystemsControllerApi#findGoogleDriveEndpointsByProject");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **parentProjectCode** | [**Object**](.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="findGoogleDriveEndpointsByQbe"></a>
# **findGoogleDriveEndpointsByQbe**
> Object findGoogleDriveEndpointsByQbe(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.googledrive.invoker.ApiException;
//import gebo.microservices.api.client.googledrive.api.GoogleDriveSystemsControllerApi;


GoogleDriveSystemsControllerApi apiInstance = new GoogleDriveSystemsControllerApi();
GGoogleDriveProjectEndpoint body = new GGoogleDriveProjectEndpoint(); // GGoogleDriveProjectEndpoint | 
try {
    Object result = apiInstance.findGoogleDriveEndpointsByQbe(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSystemsControllerApi#findGoogleDriveEndpointsByQbe");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGoogleDriveProjectEndpoint**](GGoogleDriveProjectEndpoint.md)|  |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="findGoogleDriveProjectEndpointByCode"></a>
# **findGoogleDriveProjectEndpointByCode**
> GGoogleDriveProjectEndpoint findGoogleDriveProjectEndpointByCode(code)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.googledrive.invoker.ApiException;
//import gebo.microservices.api.client.googledrive.api.GoogleDriveSystemsControllerApi;


GoogleDriveSystemsControllerApi apiInstance = new GoogleDriveSystemsControllerApi();
Object code = null; // Object | 
try {
    GGoogleDriveProjectEndpoint result = apiInstance.findGoogleDriveProjectEndpointByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSystemsControllerApi#findGoogleDriveProjectEndpointByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | [**Object**](.md)|  |

### Return type

[**GGoogleDriveProjectEndpoint**](GGoogleDriveProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="findGoogleDriveSystemByCode"></a>
# **findGoogleDriveSystemByCode**
> GGoogleDriveSystem findGoogleDriveSystemByCode(code)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.googledrive.invoker.ApiException;
//import gebo.microservices.api.client.googledrive.api.GoogleDriveSystemsControllerApi;


GoogleDriveSystemsControllerApi apiInstance = new GoogleDriveSystemsControllerApi();
Object code = null; // Object | 
try {
    GGoogleDriveSystem result = apiInstance.findGoogleDriveSystemByCode(code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSystemsControllerApi#findGoogleDriveSystemByCode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | [**Object**](.md)|  |

### Return type

[**GGoogleDriveSystem**](GGoogleDriveSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getGoogleDriveSystemType"></a>
# **getGoogleDriveSystemType**
> GContentManagementSystemType getGoogleDriveSystemType()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.googledrive.invoker.ApiException;
//import gebo.microservices.api.client.googledrive.api.GoogleDriveSystemsControllerApi;


GoogleDriveSystemsControllerApi apiInstance = new GoogleDriveSystemsControllerApi();
try {
    GContentManagementSystemType result = apiInstance.getGoogleDriveSystemType();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSystemsControllerApi#getGoogleDriveSystemType");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**GContentManagementSystemType**](GContentManagementSystemType.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getGoogleDriveSystems"></a>
# **getGoogleDriveSystems**
> Object getGoogleDriveSystems()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.googledrive.invoker.ApiException;
//import gebo.microservices.api.client.googledrive.api.GoogleDriveSystemsControllerApi;


GoogleDriveSystemsControllerApi apiInstance = new GoogleDriveSystemsControllerApi();
try {
    Object result = apiInstance.getGoogleDriveSystems();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSystemsControllerApi#getGoogleDriveSystems");
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
 - **Accept**: */*

<a name="insertGoogleDriveProjectEndpoint"></a>
# **insertGoogleDriveProjectEndpoint**
> GGoogleDriveProjectEndpoint insertGoogleDriveProjectEndpoint(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.googledrive.invoker.ApiException;
//import gebo.microservices.api.client.googledrive.api.GoogleDriveSystemsControllerApi;


GoogleDriveSystemsControllerApi apiInstance = new GoogleDriveSystemsControllerApi();
GGoogleDriveProjectEndpoint body = new GGoogleDriveProjectEndpoint(); // GGoogleDriveProjectEndpoint | 
try {
    GGoogleDriveProjectEndpoint result = apiInstance.insertGoogleDriveProjectEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSystemsControllerApi#insertGoogleDriveProjectEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGoogleDriveProjectEndpoint**](GGoogleDriveProjectEndpoint.md)|  |

### Return type

[**GGoogleDriveProjectEndpoint**](GGoogleDriveProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="insertGoogleDriveSystem"></a>
# **insertGoogleDriveSystem**
> OperationStatusGGoogleDriveSystem insertGoogleDriveSystem(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.googledrive.invoker.ApiException;
//import gebo.microservices.api.client.googledrive.api.GoogleDriveSystemsControllerApi;


GoogleDriveSystemsControllerApi apiInstance = new GoogleDriveSystemsControllerApi();
GGoogleDriveSystem body = new GGoogleDriveSystem(); // GGoogleDriveSystem | 
try {
    OperationStatusGGoogleDriveSystem result = apiInstance.insertGoogleDriveSystem(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSystemsControllerApi#insertGoogleDriveSystem");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGoogleDriveSystem**](GGoogleDriveSystem.md)|  |

### Return type

[**OperationStatusGGoogleDriveSystem**](OperationStatusGGoogleDriveSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="publishGoogleDriveProjectEndpoint"></a>
# **publishGoogleDriveProjectEndpoint**
> OperationStatusGJobStatus publishGoogleDriveProjectEndpoint(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.googledrive.invoker.ApiException;
//import gebo.microservices.api.client.googledrive.api.GoogleDriveSystemsControllerApi;


GoogleDriveSystemsControllerApi apiInstance = new GoogleDriveSystemsControllerApi();
GGoogleDriveProjectEndpoint body = new GGoogleDriveProjectEndpoint(); // GGoogleDriveProjectEndpoint | 
try {
    OperationStatusGJobStatus result = apiInstance.publishGoogleDriveProjectEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSystemsControllerApi#publishGoogleDriveProjectEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGoogleDriveProjectEndpoint**](GGoogleDriveProjectEndpoint.md)|  |

### Return type

[**OperationStatusGJobStatus**](OperationStatusGJobStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateGoogleDriveProjectEndpoint"></a>
# **updateGoogleDriveProjectEndpoint**
> GGoogleDriveProjectEndpoint updateGoogleDriveProjectEndpoint(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.googledrive.invoker.ApiException;
//import gebo.microservices.api.client.googledrive.api.GoogleDriveSystemsControllerApi;


GoogleDriveSystemsControllerApi apiInstance = new GoogleDriveSystemsControllerApi();
GGoogleDriveProjectEndpoint body = new GGoogleDriveProjectEndpoint(); // GGoogleDriveProjectEndpoint | 
try {
    GGoogleDriveProjectEndpoint result = apiInstance.updateGoogleDriveProjectEndpoint(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSystemsControllerApi#updateGoogleDriveProjectEndpoint");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGoogleDriveProjectEndpoint**](GGoogleDriveProjectEndpoint.md)|  |

### Return type

[**GGoogleDriveProjectEndpoint**](GGoogleDriveProjectEndpoint.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateGoogleDriveSystem"></a>
# **updateGoogleDriveSystem**
> OperationStatusGGoogleDriveSystem updateGoogleDriveSystem(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.googledrive.invoker.ApiException;
//import gebo.microservices.api.client.googledrive.api.GoogleDriveSystemsControllerApi;


GoogleDriveSystemsControllerApi apiInstance = new GoogleDriveSystemsControllerApi();
GGoogleDriveSystem body = new GGoogleDriveSystem(); // GGoogleDriveSystem | 
try {
    OperationStatusGGoogleDriveSystem result = apiInstance.updateGoogleDriveSystem(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleDriveSystemsControllerApi#updateGoogleDriveSystem");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**GGoogleDriveSystem**](GGoogleDriveSystem.md)|  |

### Return type

[**OperationStatusGGoogleDriveSystem**](OperationStatusGGoogleDriveSystem.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

