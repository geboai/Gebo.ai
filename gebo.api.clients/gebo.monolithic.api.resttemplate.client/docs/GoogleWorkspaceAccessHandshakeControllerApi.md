# GoogleWorkspaceAccessHandshakeControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**googleWorkspaceRedirect**](GoogleWorkspaceAccessHandshakeControllerApi.md#googleWorkspaceRedirect) | **GET** /oauth2/google-workspace-redirect | 
[**startWorkspaceAccess**](GoogleWorkspaceAccessHandshakeControllerApi.md#startWorkspaceAccess) | **GET** /oauth2/start-workspace-access-go | 
[**tryGoogleWorkspaceAccess**](GoogleWorkspaceAccessHandshakeControllerApi.md#tryGoogleWorkspaceAccess) | **POST** /api/users/start-workspace-access | 

<a name="googleWorkspaceRedirect"></a>
# **googleWorkspaceRedirect**
> googleWorkspaceRedirect()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleWorkspaceAccessHandshakeControllerApi;


GoogleWorkspaceAccessHandshakeControllerApi apiInstance = new GoogleWorkspaceAccessHandshakeControllerApi();
try {
    apiInstance.googleWorkspaceRedirect();
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleWorkspaceAccessHandshakeControllerApi#googleWorkspaceRedirect");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="startWorkspaceAccess"></a>
# **startWorkspaceAccess**
> startWorkspaceAccess()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleWorkspaceAccessHandshakeControllerApi;


GoogleWorkspaceAccessHandshakeControllerApi apiInstance = new GoogleWorkspaceAccessHandshakeControllerApi();
try {
    apiInstance.startWorkspaceAccess();
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleWorkspaceAccessHandshakeControllerApi#startWorkspaceAccess");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="tryGoogleWorkspaceAccess"></a>
# **tryGoogleWorkspaceAccess**
> StartGooglWorkspaceAccessRespose tryGoogleWorkspaceAccess(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.GoogleWorkspaceAccessHandshakeControllerApi;


GoogleWorkspaceAccessHandshakeControllerApi apiInstance = new GoogleWorkspaceAccessHandshakeControllerApi();
StartGooglWorkspaceAccessRequest body = new StartGooglWorkspaceAccessRequest(); // StartGooglWorkspaceAccessRequest | 
try {
    StartGooglWorkspaceAccessRespose result = apiInstance.tryGoogleWorkspaceAccess(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GoogleWorkspaceAccessHandshakeControllerApi#tryGoogleWorkspaceAccess");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**StartGooglWorkspaceAccessRequest**](StartGooglWorkspaceAccessRequest.md)|  |

### Return type

[**StartGooglWorkspaceAccessRespose**](StartGooglWorkspaceAccessRespose.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

