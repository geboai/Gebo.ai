# AclSettingsAdminControllerApi

All URIs are relative to *http://localhost:12999*

Method | HTTP request | Description
------------- | ------------- | -------------
[**encodeAssignments**](AclSettingsAdminControllerApi.md#encodeAssignments) | **POST** /api/admin/AclSettingsAdminController/encodeAssignments | 
[**getSelectableOwners**](AclSettingsAdminControllerApi.md#getSelectableOwners) | **GET** /api/admin/AclSettingsAdminController/getSelectableOwners | 
[**getSystemAclMode**](AclSettingsAdminControllerApi.md#getSystemAclMode) | **GET** /api/admin/AclSettingsAdminController/getSystemAclMode | 
[**resolveAliases**](AclSettingsAdminControllerApi.md#resolveAliases) | **POST** /api/admin/AclSettingsAdminController/resolveAliases | 

<a name="encodeAssignments"></a>
# **encodeAssignments**
> List&lt;Integer&gt; encodeAssignments(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.AclSettingsAdminControllerApi;


AclSettingsAdminControllerApi apiInstance = new AclSettingsAdminControllerApi();
List<AclGrantAssignment> body = Arrays.asList(new AclGrantAssignment()); // List<AclGrantAssignment> | 
try {
    List<Integer> result = apiInstance.encodeAssignments(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AclSettingsAdminControllerApi#encodeAssignments");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**List&lt;AclGrantAssignment&gt;**](AclGrantAssignment.md)|  |

### Return type

**List&lt;Integer&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getSelectableOwners"></a>
# **getSelectableOwners**
> AclSelectableOwners getSelectableOwners()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.AclSettingsAdminControllerApi;


AclSettingsAdminControllerApi apiInstance = new AclSettingsAdminControllerApi();
try {
    AclSelectableOwners result = apiInstance.getSelectableOwners();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AclSettingsAdminControllerApi#getSelectableOwners");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**AclSelectableOwners**](AclSelectableOwners.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getSystemAclMode"></a>
# **getSystemAclMode**
> AclSystemMode getSystemAclMode()



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.AclSettingsAdminControllerApi;


AclSettingsAdminControllerApi apiInstance = new AclSettingsAdminControllerApi();
try {
    AclSystemMode result = apiInstance.getSystemAclMode();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AclSettingsAdminControllerApi#getSystemAclMode");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**AclSystemMode**](AclSystemMode.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="resolveAliases"></a>
# **resolveAliases**
> List&lt;AclGrantAssignment&gt; resolveAliases(body)



### Example
```java
// Import classes:
//import ai.gebo.monolithic.api.client.invoker.ApiException;
//import ai.gebo.monolithic.api.client.api.AclSettingsAdminControllerApi;


AclSettingsAdminControllerApi apiInstance = new AclSettingsAdminControllerApi();
List<Integer> body = Arrays.asList(56); // List<Integer> | 
try {
    List<AclGrantAssignment> result = apiInstance.resolveAliases(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AclSettingsAdminControllerApi#resolveAliases");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**List&lt;Integer&gt;**](Integer.md)|  |

### Return type

[**List&lt;AclGrantAssignment&gt;**](AclGrantAssignment.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

