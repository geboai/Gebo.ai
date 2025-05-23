# PrioritySchemesApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createPriorityScheme**](PrioritySchemesApi.md#createPriorityScheme) | **POST** /rest/api/3/priorityscheme | Create priority scheme
[**deletePriorityScheme**](PrioritySchemesApi.md#deletePriorityScheme) | **DELETE** /rest/api/3/priorityscheme/{schemeId} | Delete priority scheme
[**getAvailablePrioritiesByPriorityScheme**](PrioritySchemesApi.md#getAvailablePrioritiesByPriorityScheme) | **GET** /rest/api/3/priorityscheme/priorities/available | Get available priorities by priority scheme
[**getPrioritiesByPriorityScheme**](PrioritySchemesApi.md#getPrioritiesByPriorityScheme) | **GET** /rest/api/3/priorityscheme/{schemeId}/priorities | Get priorities by priority scheme
[**getPrioritySchemes**](PrioritySchemesApi.md#getPrioritySchemes) | **GET** /rest/api/3/priorityscheme | Get priority schemes
[**getProjectsByPriorityScheme**](PrioritySchemesApi.md#getProjectsByPriorityScheme) | **GET** /rest/api/3/priorityscheme/{schemeId}/projects | Get projects by priority scheme
[**suggestedPrioritiesForMappings**](PrioritySchemesApi.md#suggestedPrioritiesForMappings) | **POST** /rest/api/3/priorityscheme/mappings | Suggested priorities for mappings
[**updatePriorityScheme**](PrioritySchemesApi.md#updatePriorityScheme) | **PUT** /rest/api/3/priorityscheme/{schemeId} | Update priority scheme

<a name="createPriorityScheme"></a>
# **createPriorityScheme**
> PrioritySchemeId createPriorityScheme(body)

Create priority scheme

Creates a new priority scheme.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.PrioritySchemesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

PrioritySchemesApi apiInstance = new PrioritySchemesApi();
CreatePrioritySchemeDetails body = new CreatePrioritySchemeDetails(); // CreatePrioritySchemeDetails | 
try {
    PrioritySchemeId result = apiInstance.createPriorityScheme(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PrioritySchemesApi#createPriorityScheme");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**CreatePrioritySchemeDetails**](CreatePrioritySchemeDetails.md)|  |

### Return type

[**PrioritySchemeId**](PrioritySchemeId.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deletePriorityScheme"></a>
# **deletePriorityScheme**
> Object deletePriorityScheme(schemeId)

Delete priority scheme

Deletes a priority scheme.  This operation is only available for priority schemes without any associated projects. Any associated projects must be removed from the priority scheme before this operation can be performed.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.PrioritySchemesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

PrioritySchemesApi apiInstance = new PrioritySchemesApi();
Long schemeId = 789L; // Long | The priority scheme ID.
try {
    Object result = apiInstance.deletePriorityScheme(schemeId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PrioritySchemesApi#deletePriorityScheme");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **schemeId** | **Long**| The priority scheme ID. |

### Return type

**Object**

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAvailablePrioritiesByPriorityScheme"></a>
# **getAvailablePrioritiesByPriorityScheme**
> PageBeanPriorityWithSequence getAvailablePrioritiesByPriorityScheme(schemeId, startAt, maxResults, query, exclude)

Get available priorities by priority scheme

Returns a [paginated](#pagination) list of priorities available for adding to a priority scheme.  **[Permissions](#permissions) required:** Permission to access Jira.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.PrioritySchemesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

PrioritySchemesApi apiInstance = new PrioritySchemesApi();
String schemeId = "schemeId_example"; // String | The priority scheme ID.
String startAt = "0"; // String | The index of the first item to return in a page of results (page offset).
String maxResults = "50"; // String | The maximum number of items to return per page.
String query = ""; // String | The string to query priorities on by name.
List<String> exclude = Arrays.asList("exclude_example"); // List<String> | A list of priority IDs to exclude from the results.
try {
    PageBeanPriorityWithSequence result = apiInstance.getAvailablePrioritiesByPriorityScheme(schemeId, startAt, maxResults, query, exclude);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PrioritySchemesApi#getAvailablePrioritiesByPriorityScheme");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **schemeId** | **String**| The priority scheme ID. |
 **startAt** | **String**| The index of the first item to return in a page of results (page offset). | [optional] [default to 0]
 **maxResults** | **String**| The maximum number of items to return per page. | [optional] [default to 50]
 **query** | **String**| The string to query priorities on by name. | [optional]
 **exclude** | [**List&lt;String&gt;**](String.md)| A list of priority IDs to exclude from the results. | [optional]

### Return type

[**PageBeanPriorityWithSequence**](PageBeanPriorityWithSequence.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getPrioritiesByPriorityScheme"></a>
# **getPrioritiesByPriorityScheme**
> PageBeanPriorityWithSequence getPrioritiesByPriorityScheme(schemeId, startAt, maxResults)

Get priorities by priority scheme

Returns a [paginated](#pagination) list of priorities by scheme.  **[Permissions](#permissions) required:** Permission to access Jira.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.PrioritySchemesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

PrioritySchemesApi apiInstance = new PrioritySchemesApi();
String schemeId = "schemeId_example"; // String | The priority scheme ID.
String startAt = "0"; // String | The index of the first item to return in a page of results (page offset).
String maxResults = "50"; // String | The maximum number of items to return per page.
try {
    PageBeanPriorityWithSequence result = apiInstance.getPrioritiesByPriorityScheme(schemeId, startAt, maxResults);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PrioritySchemesApi#getPrioritiesByPriorityScheme");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **schemeId** | **String**| The priority scheme ID. |
 **startAt** | **String**| The index of the first item to return in a page of results (page offset). | [optional] [default to 0]
 **maxResults** | **String**| The maximum number of items to return per page. | [optional] [default to 50]

### Return type

[**PageBeanPriorityWithSequence**](PageBeanPriorityWithSequence.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getPrioritySchemes"></a>
# **getPrioritySchemes**
> PageBeanPrioritySchemeWithPaginatedPrioritiesAndProjects getPrioritySchemes(startAt, maxResults, priorityId, schemeId, schemeName, onlyDefault, orderBy, expand)

Get priority schemes

Returns a [paginated](#pagination) list of priority schemes.  **[Permissions](#permissions) required:** Permission to access Jira.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.PrioritySchemesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

PrioritySchemesApi apiInstance = new PrioritySchemesApi();
String startAt = "0"; // String | The index of the first item to return in a page of results (page offset).
String maxResults = "50"; // String | The maximum number of items to return per page.
List<Long> priorityId = Arrays.asList(56L); // List<Long> | A set of priority IDs to filter by. To include multiple IDs, provide an ampersand-separated list. For example, `priorityId=10000&priorityId=10001`.
List<Long> schemeId = Arrays.asList(56L); // List<Long> | A set of priority scheme IDs. To include multiple IDs, provide an ampersand-separated list. For example, `schemeId=10000&schemeId=10001`.
String schemeName = ""; // String | The name of scheme to search for.
Boolean onlyDefault = false; // Boolean | Whether only the default priority is returned.
String orderBy = "+name"; // String | The ordering to return the priority schemes by.
String expand = "expand_example"; // String | A comma separated list of additional information to return. \"priorities\" will return priorities associated with the priority scheme. \"projects\" will return projects associated with the priority scheme. `expand=priorities,projects`.
try {
    PageBeanPrioritySchemeWithPaginatedPrioritiesAndProjects result = apiInstance.getPrioritySchemes(startAt, maxResults, priorityId, schemeId, schemeName, onlyDefault, orderBy, expand);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PrioritySchemesApi#getPrioritySchemes");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **startAt** | **String**| The index of the first item to return in a page of results (page offset). | [optional] [default to 0]
 **maxResults** | **String**| The maximum number of items to return per page. | [optional] [default to 50]
 **priorityId** | [**List&lt;Long&gt;**](Long.md)| A set of priority IDs to filter by. To include multiple IDs, provide an ampersand-separated list. For example, &#x60;priorityId&#x3D;10000&amp;priorityId&#x3D;10001&#x60;. | [optional]
 **schemeId** | [**List&lt;Long&gt;**](Long.md)| A set of priority scheme IDs. To include multiple IDs, provide an ampersand-separated list. For example, &#x60;schemeId&#x3D;10000&amp;schemeId&#x3D;10001&#x60;. | [optional]
 **schemeName** | **String**| The name of scheme to search for. | [optional]
 **onlyDefault** | **Boolean**| Whether only the default priority is returned. | [optional] [default to false]
 **orderBy** | **String**| The ordering to return the priority schemes by. | [optional] [default to +name] [enum: name, +name, -name]
 **expand** | **String**| A comma separated list of additional information to return. \&quot;priorities\&quot; will return priorities associated with the priority scheme. \&quot;projects\&quot; will return projects associated with the priority scheme. &#x60;expand&#x3D;priorities,projects&#x60;. | [optional]

### Return type

[**PageBeanPrioritySchemeWithPaginatedPrioritiesAndProjects**](PageBeanPrioritySchemeWithPaginatedPrioritiesAndProjects.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getProjectsByPriorityScheme"></a>
# **getProjectsByPriorityScheme**
> PageBeanProject getProjectsByPriorityScheme(schemeId, startAt, maxResults, projectId, query)

Get projects by priority scheme

Returns a [paginated](#pagination) list of projects by scheme.  **[Permissions](#permissions) required:** Permission to access Jira.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.PrioritySchemesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

PrioritySchemesApi apiInstance = new PrioritySchemesApi();
String schemeId = "schemeId_example"; // String | The priority scheme ID.
String startAt = "0"; // String | The index of the first item to return in a page of results (page offset).
String maxResults = "50"; // String | The maximum number of items to return per page.
List<Long> projectId = Arrays.asList(56L); // List<Long> | The project IDs to filter by. For example, `projectId=10000&projectId=10001`.
String query = ""; // String | The string to query projects on by name.
try {
    PageBeanProject result = apiInstance.getProjectsByPriorityScheme(schemeId, startAt, maxResults, projectId, query);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PrioritySchemesApi#getProjectsByPriorityScheme");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **schemeId** | **String**| The priority scheme ID. |
 **startAt** | **String**| The index of the first item to return in a page of results (page offset). | [optional] [default to 0]
 **maxResults** | **String**| The maximum number of items to return per page. | [optional] [default to 50]
 **projectId** | [**List&lt;Long&gt;**](Long.md)| The project IDs to filter by. For example, &#x60;projectId&#x3D;10000&amp;projectId&#x3D;10001&#x60;. | [optional]
 **query** | **String**| The string to query projects on by name. | [optional]

### Return type

[**PageBeanProject**](PageBeanProject.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="suggestedPrioritiesForMappings"></a>
# **suggestedPrioritiesForMappings**
> PageBeanPriorityWithSequence suggestedPrioritiesForMappings(body)

Suggested priorities for mappings

Returns a [paginated](#pagination) list of priorities that would require mapping, given a change in priorities or projects associated with a priority scheme.  **[Permissions](#permissions) required:** Permission to access Jira.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.PrioritySchemesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

PrioritySchemesApi apiInstance = new PrioritySchemesApi();
SuggestedMappingsRequestBean body = new SuggestedMappingsRequestBean(); // SuggestedMappingsRequestBean | 
try {
    PageBeanPriorityWithSequence result = apiInstance.suggestedPrioritiesForMappings(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PrioritySchemesApi#suggestedPrioritiesForMappings");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SuggestedMappingsRequestBean**](SuggestedMappingsRequestBean.md)|  |

### Return type

[**PageBeanPriorityWithSequence**](PageBeanPriorityWithSequence.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updatePriorityScheme"></a>
# **updatePriorityScheme**
> UpdatePrioritySchemeResponseBean updatePriorityScheme(body, schemeId)

Update priority scheme

Updates a priority scheme. This includes its details, the lists of priorities and projects in it  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg).

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.PrioritySchemesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

PrioritySchemesApi apiInstance = new PrioritySchemesApi();
UpdatePrioritySchemeRequestBean body = new UpdatePrioritySchemeRequestBean(); // UpdatePrioritySchemeRequestBean | 
Long schemeId = 789L; // Long | The ID of the priority scheme.
try {
    UpdatePrioritySchemeResponseBean result = apiInstance.updatePriorityScheme(body, schemeId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PrioritySchemesApi#updatePriorityScheme");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UpdatePrioritySchemeRequestBean**](UpdatePrioritySchemeRequestBean.md)|  |
 **schemeId** | **Long**| The ID of the priority scheme. |

### Return type

[**UpdatePrioritySchemeResponseBean**](UpdatePrioritySchemeResponseBean.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

