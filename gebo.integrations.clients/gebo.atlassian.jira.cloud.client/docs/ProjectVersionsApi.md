# ProjectVersionsApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createRelatedWork**](ProjectVersionsApi.md#createRelatedWork) | **POST** /rest/api/3/version/{id}/relatedwork | Create related work
[**createVersion**](ProjectVersionsApi.md#createVersion) | **POST** /rest/api/3/version | Create version
[**deleteAndReplaceVersion**](ProjectVersionsApi.md#deleteAndReplaceVersion) | **POST** /rest/api/3/version/{id}/removeAndSwap | Delete and replace version
[**deleteRelatedWork**](ProjectVersionsApi.md#deleteRelatedWork) | **DELETE** /rest/api/3/version/{versionId}/relatedwork/{relatedWorkId} | Delete related work
[**deleteVersion**](ProjectVersionsApi.md#deleteVersion) | **DELETE** /rest/api/3/version/{id} | Delete version
[**getProjectVersions**](ProjectVersionsApi.md#getProjectVersions) | **GET** /rest/api/3/project/{projectIdOrKey}/versions | Get project versions
[**getProjectVersionsPaginated**](ProjectVersionsApi.md#getProjectVersionsPaginated) | **GET** /rest/api/3/project/{projectIdOrKey}/version | Get project versions paginated
[**getRelatedWork**](ProjectVersionsApi.md#getRelatedWork) | **GET** /rest/api/3/version/{id}/relatedwork | Get related work
[**getVersion**](ProjectVersionsApi.md#getVersion) | **GET** /rest/api/3/version/{id} | Get version
[**getVersionRelatedIssues**](ProjectVersionsApi.md#getVersionRelatedIssues) | **GET** /rest/api/3/version/{id}/relatedIssueCounts | Get version&#x27;s related issues count
[**getVersionUnresolvedIssues**](ProjectVersionsApi.md#getVersionUnresolvedIssues) | **GET** /rest/api/3/version/{id}/unresolvedIssueCount | Get version&#x27;s unresolved issues count
[**mergeVersions**](ProjectVersionsApi.md#mergeVersions) | **PUT** /rest/api/3/version/{id}/mergeto/{moveIssuesTo} | Merge versions
[**moveVersion**](ProjectVersionsApi.md#moveVersion) | **POST** /rest/api/3/version/{id}/move | Move version
[**updateRelatedWork**](ProjectVersionsApi.md#updateRelatedWork) | **PUT** /rest/api/3/version/{id}/relatedwork | Update related work
[**updateVersion**](ProjectVersionsApi.md#updateVersion) | **PUT** /rest/api/3/version/{id} | Update version

<a name="createRelatedWork"></a>
# **createRelatedWork**
> VersionRelatedWork createRelatedWork(body, id)

Create related work

Creates a related work for the given version. You can only create a generic link type of related works via this API. relatedWorkId will be auto-generated UUID, that does not need to be provided.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** *Resolve issues:* and *Edit issues* [Managing project permissions](https://confluence.atlassian.com/adminjiraserver/managing-project-permissions-938847145.html) for the project that contains the version.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.ProjectVersionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ProjectVersionsApi apiInstance = new ProjectVersionsApi();
VersionRelatedWork body = new VersionRelatedWork(); // VersionRelatedWork | 
String id = "id_example"; // String | 
try {
    VersionRelatedWork result = apiInstance.createRelatedWork(body, id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectVersionsApi#createRelatedWork");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**VersionRelatedWork**](VersionRelatedWork.md)|  |
 **id** | **String**|  |

### Return type

[**VersionRelatedWork**](VersionRelatedWork.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createVersion"></a>
# **createVersion**
> Version createVersion(body)

Create version

Creates a project version.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg) or *Administer Projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project the version is added to.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.ProjectVersionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ProjectVersionsApi apiInstance = new ProjectVersionsApi();
Version body = new Version(); // Version | 
try {
    Version result = apiInstance.createVersion(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectVersionsApi#createVersion");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Version**](Version.md)|  |

### Return type

[**Version**](Version.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deleteAndReplaceVersion"></a>
# **deleteAndReplaceVersion**
> Object deleteAndReplaceVersion(body, id)

Delete and replace version

Deletes a project version.  Alternative versions can be provided to update issues that use the deleted version in &#x60;fixVersion&#x60;, &#x60;affectedVersion&#x60;, or any version picker custom fields. If alternatives are not provided, occurrences of &#x60;fixVersion&#x60;, &#x60;affectedVersion&#x60;, and any version picker custom field, that contain the deleted version, are cleared. Any replacement version must be in the same project as the version being deleted and cannot be the version being deleted.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg) or *Administer Projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project that contains the version.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.ProjectVersionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ProjectVersionsApi apiInstance = new ProjectVersionsApi();
DeleteAndReplaceVersionBean body = new DeleteAndReplaceVersionBean(); // DeleteAndReplaceVersionBean | 
String id = "id_example"; // String | The ID of the version.
try {
    Object result = apiInstance.deleteAndReplaceVersion(body, id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectVersionsApi#deleteAndReplaceVersion");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**DeleteAndReplaceVersionBean**](DeleteAndReplaceVersionBean.md)|  |
 **id** | **String**| The ID of the version. |

### Return type

**Object**

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deleteRelatedWork"></a>
# **deleteRelatedWork**
> deleteRelatedWork(versionId, relatedWorkId)

Delete related work

Deletes the given related work for the given version.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** *Resolve issues:* and *Edit issues* [Managing project permissions](https://confluence.atlassian.com/adminjiraserver/managing-project-permissions-938847145.html) for the project that contains the version.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.ProjectVersionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ProjectVersionsApi apiInstance = new ProjectVersionsApi();
String versionId = "versionId_example"; // String | The ID of the version that the target related work belongs to.
String relatedWorkId = "relatedWorkId_example"; // String | The ID of the related work to delete.
try {
    apiInstance.deleteRelatedWork(versionId, relatedWorkId);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectVersionsApi#deleteRelatedWork");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **versionId** | **String**| The ID of the version that the target related work belongs to. |
 **relatedWorkId** | **String**| The ID of the related work to delete. |

### Return type

null (empty response body)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="deleteVersion"></a>
# **deleteVersion**
> deleteVersion(id, moveFixIssuesTo, moveAffectedIssuesTo)

Delete version

Deletes a project version.  Deprecated, use [ Delete and replace version](#api-rest-api-3-version-id-removeAndSwap-post) that supports swapping version values in custom fields, in addition to the swapping for &#x60;fixVersion&#x60; and &#x60;affectedVersion&#x60; provided in this resource.  Alternative versions can be provided to update issues that use the deleted version in &#x60;fixVersion&#x60; or &#x60;affectedVersion&#x60;. If alternatives are not provided, occurrences of &#x60;fixVersion&#x60; and &#x60;affectedVersion&#x60; that contain the deleted version are cleared.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg) or *Administer Projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project that contains the version.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.ProjectVersionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ProjectVersionsApi apiInstance = new ProjectVersionsApi();
String id = "id_example"; // String | The ID of the version.
String moveFixIssuesTo = "moveFixIssuesTo_example"; // String | The ID of the version to update `fixVersion` to when the field contains the deleted version. The replacement version must be in the same project as the version being deleted and cannot be the version being deleted.
String moveAffectedIssuesTo = "moveAffectedIssuesTo_example"; // String | The ID of the version to update `affectedVersion` to when the field contains the deleted version. The replacement version must be in the same project as the version being deleted and cannot be the version being deleted.
try {
    apiInstance.deleteVersion(id, moveFixIssuesTo, moveAffectedIssuesTo);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectVersionsApi#deleteVersion");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The ID of the version. |
 **moveFixIssuesTo** | **String**| The ID of the version to update &#x60;fixVersion&#x60; to when the field contains the deleted version. The replacement version must be in the same project as the version being deleted and cannot be the version being deleted. | [optional]
 **moveAffectedIssuesTo** | **String**| The ID of the version to update &#x60;affectedVersion&#x60; to when the field contains the deleted version. The replacement version must be in the same project as the version being deleted and cannot be the version being deleted. | [optional]

### Return type

null (empty response body)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="getProjectVersions"></a>
# **getProjectVersions**
> List&lt;Version&gt; getProjectVersions(projectIdOrKey, expand)

Get project versions

Returns all versions in a project. The response is not paginated. Use [Get project versions paginated](#api-rest-api-3-project-projectIdOrKey-version-get) if you want to get the versions in a project with pagination.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** *Browse Projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.ProjectVersionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ProjectVersionsApi apiInstance = new ProjectVersionsApi();
String projectIdOrKey = "projectIdOrKey_example"; // String | The project ID or project key (case sensitive).
String expand = "expand_example"; // String | Use [expand](#expansion) to include additional information in the response. This parameter accepts `operations`, which returns actions that can be performed on the version.
try {
    List<Version> result = apiInstance.getProjectVersions(projectIdOrKey, expand);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectVersionsApi#getProjectVersions");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectIdOrKey** | **String**| The project ID or project key (case sensitive). |
 **expand** | **String**| Use [expand](#expansion) to include additional information in the response. This parameter accepts &#x60;operations&#x60;, which returns actions that can be performed on the version. | [optional]

### Return type

[**List&lt;Version&gt;**](Version.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getProjectVersionsPaginated"></a>
# **getProjectVersionsPaginated**
> PageBeanVersion getProjectVersionsPaginated(projectIdOrKey, startAt, maxResults, orderBy, query, status, expand)

Get project versions paginated

Returns a [paginated](#pagination) list of all versions in a project. See the [Get project versions](#api-rest-api-3-project-projectIdOrKey-versions-get) resource if you want to get a full list of versions without pagination.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** *Browse Projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.ProjectVersionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ProjectVersionsApi apiInstance = new ProjectVersionsApi();
String projectIdOrKey = "projectIdOrKey_example"; // String | The project ID or project key (case sensitive).
Long startAt = 0L; // Long | The index of the first item to return in a page of results (page offset).
Integer maxResults = 50; // Integer | The maximum number of items to return per page.
String orderBy = "orderBy_example"; // String | [Order](#ordering) the results by a field:   *  `description` Sorts by version description.  *  `name` Sorts by version name.  *  `releaseDate` Sorts by release date, starting with the oldest date. Versions with no release date are listed last.  *  `sequence` Sorts by the order of appearance in the user interface.  *  `startDate` Sorts by start date, starting with the oldest date. Versions with no start date are listed last.
String query = "query_example"; // String | Filter the results using a literal string. Versions with matching `name` or `description` are returned (case insensitive).
String status = "status_example"; // String | A list of status values used to filter the results by version status. This parameter accepts a comma-separated list. The status values are `released`, `unreleased`, and `archived`.
String expand = "expand_example"; // String | Use [expand](#expansion) to include additional information in the response. This parameter accepts a comma-separated list. Expand options include:   *  `issuesstatus` Returns the number of issues in each status category for each version.  *  `operations` Returns actions that can be performed on the specified version.  *  `driver` Returns the Atlassian account ID of the version driver.  *  `approvers` Returns a list containing the approvers for this version.
try {
    PageBeanVersion result = apiInstance.getProjectVersionsPaginated(projectIdOrKey, startAt, maxResults, orderBy, query, status, expand);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectVersionsApi#getProjectVersionsPaginated");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **projectIdOrKey** | **String**| The project ID or project key (case sensitive). |
 **startAt** | **Long**| The index of the first item to return in a page of results (page offset). | [optional] [default to 0]
 **maxResults** | **Integer**| The maximum number of items to return per page. | [optional] [default to 50]
 **orderBy** | **String**| [Order](#ordering) the results by a field:   *  &#x60;description&#x60; Sorts by version description.  *  &#x60;name&#x60; Sorts by version name.  *  &#x60;releaseDate&#x60; Sorts by release date, starting with the oldest date. Versions with no release date are listed last.  *  &#x60;sequence&#x60; Sorts by the order of appearance in the user interface.  *  &#x60;startDate&#x60; Sorts by start date, starting with the oldest date. Versions with no start date are listed last. | [optional] [enum: description, -description, +description, name, -name, +name, releaseDate, -releaseDate, +releaseDate, sequence, -sequence, +sequence, startDate, -startDate, +startDate]
 **query** | **String**| Filter the results using a literal string. Versions with matching &#x60;name&#x60; or &#x60;description&#x60; are returned (case insensitive). | [optional]
 **status** | **String**| A list of status values used to filter the results by version status. This parameter accepts a comma-separated list. The status values are &#x60;released&#x60;, &#x60;unreleased&#x60;, and &#x60;archived&#x60;. | [optional]
 **expand** | **String**| Use [expand](#expansion) to include additional information in the response. This parameter accepts a comma-separated list. Expand options include:   *  &#x60;issuesstatus&#x60; Returns the number of issues in each status category for each version.  *  &#x60;operations&#x60; Returns actions that can be performed on the specified version.  *  &#x60;driver&#x60; Returns the Atlassian account ID of the version driver.  *  &#x60;approvers&#x60; Returns a list containing the approvers for this version. | [optional]

### Return type

[**PageBeanVersion**](PageBeanVersion.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getRelatedWork"></a>
# **getRelatedWork**
> List&lt;VersionRelatedWork&gt; getRelatedWork(id)

Get related work

Returns related work items for the given version id.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project containing the version.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.ProjectVersionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ProjectVersionsApi apiInstance = new ProjectVersionsApi();
String id = "id_example"; // String | The ID of the version.
try {
    List<VersionRelatedWork> result = apiInstance.getRelatedWork(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectVersionsApi#getRelatedWork");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The ID of the version. |

### Return type

[**List&lt;VersionRelatedWork&gt;**](VersionRelatedWork.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getVersion"></a>
# **getVersion**
> Version getVersion(id, expand)

Get version

Returns a project version.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** *Browse projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project containing the version.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.ProjectVersionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ProjectVersionsApi apiInstance = new ProjectVersionsApi();
String id = "id_example"; // String | The ID of the version.
String expand = "expand_example"; // String | Use [expand](#expansion) to include additional information about version in the response. This parameter accepts a comma-separated list. Expand options include:   *  `operations` Returns the list of operations available for this version.  *  `issuesstatus` Returns the count of issues in this version for each of the status categories *to do*, *in progress*, *done*, and *unmapped*. The *unmapped* property represents the number of issues with a status other than *to do*, *in progress*, and *done*.  *  `driver` Returns the Atlassian account ID of the version driver.  *  `approvers` Returns a list containing the Atlassian account IDs of approvers for this version.
try {
    Version result = apiInstance.getVersion(id, expand);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectVersionsApi#getVersion");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The ID of the version. |
 **expand** | **String**| Use [expand](#expansion) to include additional information about version in the response. This parameter accepts a comma-separated list. Expand options include:   *  &#x60;operations&#x60; Returns the list of operations available for this version.  *  &#x60;issuesstatus&#x60; Returns the count of issues in this version for each of the status categories *to do*, *in progress*, *done*, and *unmapped*. The *unmapped* property represents the number of issues with a status other than *to do*, *in progress*, and *done*.  *  &#x60;driver&#x60; Returns the Atlassian account ID of the version driver.  *  &#x60;approvers&#x60; Returns a list containing the Atlassian account IDs of approvers for this version. | [optional]

### Return type

[**Version**](Version.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getVersionRelatedIssues"></a>
# **getVersionRelatedIssues**
> VersionIssueCounts getVersionRelatedIssues(id)

Get version&#x27;s related issues count

Returns the following counts for a version:   *  Number of issues where the &#x60;fixVersion&#x60; is set to the version.  *  Number of issues where the &#x60;affectedVersion&#x60; is set to the version.  *  Number of issues where a version custom field is set to the version.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** *Browse projects* project permission for the project that contains the version.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.ProjectVersionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ProjectVersionsApi apiInstance = new ProjectVersionsApi();
String id = "id_example"; // String | The ID of the version.
try {
    VersionIssueCounts result = apiInstance.getVersionRelatedIssues(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectVersionsApi#getVersionRelatedIssues");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The ID of the version. |

### Return type

[**VersionIssueCounts**](VersionIssueCounts.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getVersionUnresolvedIssues"></a>
# **getVersionUnresolvedIssues**
> VersionUnresolvedIssuesCount getVersionUnresolvedIssues(id)

Get version&#x27;s unresolved issues count

Returns counts of the issues and unresolved issues for the project version.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** *Browse projects* project permission for the project that contains the version.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.ProjectVersionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ProjectVersionsApi apiInstance = new ProjectVersionsApi();
String id = "id_example"; // String | The ID of the version.
try {
    VersionUnresolvedIssuesCount result = apiInstance.getVersionUnresolvedIssues(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectVersionsApi#getVersionUnresolvedIssues");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The ID of the version. |

### Return type

[**VersionUnresolvedIssuesCount**](VersionUnresolvedIssuesCount.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="mergeVersions"></a>
# **mergeVersions**
> Object mergeVersions(id, moveIssuesTo)

Merge versions

Merges two project versions. The merge is completed by deleting the version specified in &#x60;id&#x60; and replacing any occurrences of its ID in &#x60;fixVersion&#x60; with the version ID specified in &#x60;moveIssuesTo&#x60;.  Consider using [ Delete and replace version](#api-rest-api-3-version-id-removeAndSwap-post) instead. This resource supports swapping version values in &#x60;fixVersion&#x60;, &#x60;affectedVersion&#x60;, and custom fields.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg) or *Administer Projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project that contains the version.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.ProjectVersionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ProjectVersionsApi apiInstance = new ProjectVersionsApi();
String id = "id_example"; // String | The ID of the version to delete.
String moveIssuesTo = "moveIssuesTo_example"; // String | The ID of the version to merge into.
try {
    Object result = apiInstance.mergeVersions(id, moveIssuesTo);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectVersionsApi#mergeVersions");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The ID of the version to delete. |
 **moveIssuesTo** | **String**| The ID of the version to merge into. |

### Return type

**Object**

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="moveVersion"></a>
# **moveVersion**
> Version moveVersion(body, id)

Move version

Modifies the version&#x27;s sequence within the project, which affects the display order of the versions in Jira.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** *Browse projects* project permission for the project that contains the version.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.ProjectVersionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ProjectVersionsApi apiInstance = new ProjectVersionsApi();
VersionMoveBean body = new VersionMoveBean(); // VersionMoveBean | 
String id = "id_example"; // String | The ID of the version to be moved.
try {
    Version result = apiInstance.moveVersion(body, id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectVersionsApi#moveVersion");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**VersionMoveBean**](VersionMoveBean.md)|  |
 **id** | **String**| The ID of the version to be moved. |

### Return type

[**Version**](Version.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateRelatedWork"></a>
# **updateRelatedWork**
> VersionRelatedWork updateRelatedWork(body, id)

Update related work

Updates the given related work. You can only update generic link related works via Rest APIs. Any archived version related works can&#x27;t be edited.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** *Resolve issues:* and *Edit issues* [Managing project permissions](https://confluence.atlassian.com/adminjiraserver/managing-project-permissions-938847145.html) for the project that contains the version.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.ProjectVersionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ProjectVersionsApi apiInstance = new ProjectVersionsApi();
VersionRelatedWork body = new VersionRelatedWork(); // VersionRelatedWork | 
String id = "id_example"; // String | The ID of the version to update the related work on. For the related work id, pass it to the input JSON.
try {
    VersionRelatedWork result = apiInstance.updateRelatedWork(body, id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectVersionsApi#updateRelatedWork");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**VersionRelatedWork**](VersionRelatedWork.md)|  |
 **id** | **String**| The ID of the version to update the related work on. For the related work id, pass it to the input JSON. |

### Return type

[**VersionRelatedWork**](VersionRelatedWork.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateVersion"></a>
# **updateVersion**
> Version updateVersion(body, id)

Update version

Updates a project version.  This operation can be accessed anonymously.  **[Permissions](#permissions) required:** *Administer Jira* [global permission](https://confluence.atlassian.com/x/x4dKLg) or *Administer Projects* [project permission](https://confluence.atlassian.com/x/yodKLg) for the project that contains the version.

### Example
```java
// Import classes:
//import ai.gebo.jira.cloud.client.invoker.ApiClient;
//import ai.gebo.jira.cloud.client.invoker.ApiException;
//import ai.gebo.jira.cloud.client.invoker.Configuration;
//import ai.gebo.jira.cloud.client.invoker.auth.*;
//import ai.gebo.jira.cloud.client.api.ProjectVersionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

ProjectVersionsApi apiInstance = new ProjectVersionsApi();
Version body = new Version(); // Version | 
String id = "id_example"; // String | The ID of the version.
try {
    Version result = apiInstance.updateVersion(body, id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProjectVersionsApi#updateVersion");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Version**](Version.md)|  |
 **id** | **String**| The ID of the version. |

### Return type

[**Version**](Version.md)

### Authorization

[OAuth2](../README.md#OAuth2)[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

