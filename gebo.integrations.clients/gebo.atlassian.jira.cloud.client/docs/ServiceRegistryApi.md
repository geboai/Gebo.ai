# ServiceRegistryApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**serviceRegistryResourceServicesGet**](ServiceRegistryApi.md#serviceRegistryResourceServicesGet) | **GET** /rest/atlassian-connect/1/service-registry | Retrieve the attributes of service registries

<a name="serviceRegistryResourceServicesGet"></a>
# **serviceRegistryResourceServicesGet**
> List&lt;ServiceRegistry&gt; serviceRegistryResourceServicesGet(serviceIds)

Retrieve the attributes of service registries

Retrieve the attributes of given service registries.  **[Permissions](#permissions) required:** Only Connect apps can make this request and the servicesIds belong to the tenant you are requesting

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.api.ServiceRegistryApi;


ServiceRegistryApi apiInstance = new ServiceRegistryApi();
List<String> serviceIds = Arrays.asList("serviceIds_example"); // List<String> | The ID of the services (the strings starting with \"b:\" need to be decoded in Base64).
try {
    List<ServiceRegistry> result = apiInstance.serviceRegistryResourceServicesGet(serviceIds);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ServiceRegistryApi#serviceRegistryResourceServicesGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **serviceIds** | [**List&lt;String&gt;**](String.md)| The ID of the services (the strings starting with \&quot;b:\&quot; need to be decoded in Base64). |

### Return type

[**List&lt;ServiceRegistry&gt;**](ServiceRegistry.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

