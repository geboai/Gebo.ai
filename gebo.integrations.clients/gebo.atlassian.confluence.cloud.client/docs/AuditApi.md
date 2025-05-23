# AuditApi

All URIs are relative to *https://your-domain.atlassian.net*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createAuditRecord**](AuditApi.md#createAuditRecord) | **POST** /wiki/rest/api/audit | Create audit record
[**exportAuditRecords**](AuditApi.md#exportAuditRecords) | **GET** /wiki/rest/api/audit/export | Export audit records
[**getAuditRecords**](AuditApi.md#getAuditRecords) | **GET** /wiki/rest/api/audit | Get audit records
[**getAuditRecordsForTimePeriod**](AuditApi.md#getAuditRecordsForTimePeriod) | **GET** /wiki/rest/api/audit/since | Get audit records for time period
[**getRetentionPeriod**](AuditApi.md#getRetentionPeriod) | **GET** /wiki/rest/api/audit/retention | Get retention period
[**setRetentionPeriod**](AuditApi.md#setRetentionPeriod) | **PUT** /wiki/rest/api/audit/retention | Set retention period

<a name="createAuditRecord"></a>
# **createAuditRecord**
> AuditRecord createAuditRecord(body)

Create audit record

Creates a record in the audit log.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Confluence Administrator&#x27; global permission.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.AuditApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

AuditApi apiInstance = new AuditApi();
AuditRecordCreate body = new AuditRecordCreate(); // AuditRecordCreate | The record to be created in the audit log.
try {
    AuditRecord result = apiInstance.createAuditRecord(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AuditApi#createAuditRecord");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**AuditRecordCreate**](AuditRecordCreate.md)| The record to be created in the audit log. |

### Return type

[**AuditRecord**](AuditRecord.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="exportAuditRecords"></a>
# **exportAuditRecords**
> File exportAuditRecords(startDate, endDate, searchString, format)

Export audit records

Exports audit records as a CSV file or ZIP file.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Confluence Administrator&#x27; global permission.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.AuditApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

AuditApi apiInstance = new AuditApi();
String startDate = "startDate_example"; // String | Filters the exported results to the records on or after the `startDate`. The `startDate` must be specified as [epoch time](https://www.epochconverter.com/) in milliseconds.
String endDate = "endDate_example"; // String | Filters the exported results to the records on or before the `endDate`. The `endDate` must be specified as [epoch time](https://www.epochconverter.com/) in milliseconds.
String searchString = "searchString_example"; // String | Filters the exported results to records that have string property values matching the `searchString`.
String format = "csv"; // String | The format of the export file for the audit records.
try {
    File result = apiInstance.exportAuditRecords(startDate, endDate, searchString, format);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AuditApi#exportAuditRecords");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **startDate** | **String**| Filters the exported results to the records on or after the &#x60;startDate&#x60;. The &#x60;startDate&#x60; must be specified as [epoch time](https://www.epochconverter.com/) in milliseconds. | [optional]
 **endDate** | **String**| Filters the exported results to the records on or before the &#x60;endDate&#x60;. The &#x60;endDate&#x60; must be specified as [epoch time](https://www.epochconverter.com/) in milliseconds. | [optional]
 **searchString** | **String**| Filters the exported results to records that have string property values matching the &#x60;searchString&#x60;. | [optional]
 **format** | **String**| The format of the export file for the audit records. | [optional] [default to csv] [enum: csv, zip]

### Return type

[**File**](File.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/zip, text/csv

<a name="getAuditRecords"></a>
# **getAuditRecords**
> AuditRecordArray getAuditRecords(startDate, endDate, searchString, start, limit)

Get audit records

Returns all records in the audit log, optionally for a certain date range. This contains information about events like space exports, group membership changes, app installations, etc. For more information, see [Audit log](https://confluence.atlassian.com/confcloud/audit-log-802164269.html) in the Confluence administrator&#x27;s guide.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Confluence Administrator&#x27; global permission.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.AuditApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

AuditApi apiInstance = new AuditApi();
String startDate = "startDate_example"; // String | Filters the results to the records on or after the `startDate`. The `startDate` must be specified as [epoch time](https://www.epochconverter.com/) in milliseconds.
String endDate = "endDate_example"; // String | Filters the results to the records on or before the `endDate`. The `endDate` must be specified as [epoch time](https://www.epochconverter.com/) in milliseconds.
String searchString = "searchString_example"; // String | Filters the results to records that have string property values matching the `searchString`.
Integer start = 0; // Integer | The starting index of the returned records.
Integer limit = 1000; // Integer | The maximum number of records to return per page. Note, this may be restricted by fixed system limits.
try {
    AuditRecordArray result = apiInstance.getAuditRecords(startDate, endDate, searchString, start, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AuditApi#getAuditRecords");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **startDate** | **String**| Filters the results to the records on or after the &#x60;startDate&#x60;. The &#x60;startDate&#x60; must be specified as [epoch time](https://www.epochconverter.com/) in milliseconds. | [optional]
 **endDate** | **String**| Filters the results to the records on or before the &#x60;endDate&#x60;. The &#x60;endDate&#x60; must be specified as [epoch time](https://www.epochconverter.com/) in milliseconds. | [optional]
 **searchString** | **String**| Filters the results to records that have string property values matching the &#x60;searchString&#x60;. | [optional]
 **start** | **Integer**| The starting index of the returned records. | [optional] [default to 0] [enum: 0]
 **limit** | **Integer**| The maximum number of records to return per page. Note, this may be restricted by fixed system limits. | [optional] [default to 1000] [enum: 0]

### Return type

[**AuditRecordArray**](AuditRecordArray.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAuditRecordsForTimePeriod"></a>
# **getAuditRecordsForTimePeriod**
> AuditRecordArray getAuditRecordsForTimePeriod(number, units, searchString, start, limit)

Get audit records for time period

Returns records from the audit log, for a time period back from the current date. For example, you can use this method to get the last 3 months of records.  This contains information about events like space exports, group membership changes, app installations, etc. For more information, see [Audit log](https://confluence.atlassian.com/confcloud/audit-log-802164269.html) in the Confluence administrator&#x27;s guide.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Confluence Administrator&#x27; global permission.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.AuditApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

AuditApi apiInstance = new AuditApi();
Long number = 3L; // Long | The number of units for the time period.
String units = "MONTHS"; // String | The unit of time that the time period is measured in.
String searchString = "searchString_example"; // String | Filters the results to records that have string property values matching the `searchString`.
Integer start = 0; // Integer | The starting index of the returned records.
Integer limit = 1000; // Integer | The maximum number of records to return per page. Note, this may be restricted by fixed system limits.
try {
    AuditRecordArray result = apiInstance.getAuditRecordsForTimePeriod(number, units, searchString, start, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AuditApi#getAuditRecordsForTimePeriod");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **number** | **Long**| The number of units for the time period. | [optional] [default to 3]
 **units** | **String**| The unit of time that the time period is measured in. | [optional] [default to MONTHS] [enum: NANOS, MICROS, MILLIS, SECONDS, MINUTES, HOURS, HALF_DAYS, DAYS, WEEKS, MONTHS, YEARS, DECADES, CENTURIES]
 **searchString** | **String**| Filters the results to records that have string property values matching the &#x60;searchString&#x60;. | [optional]
 **start** | **Integer**| The starting index of the returned records. | [optional] [default to 0] [enum: 0]
 **limit** | **Integer**| The maximum number of records to return per page. Note, this may be restricted by fixed system limits. | [optional] [default to 1000] [enum: 0]

### Return type

[**AuditRecordArray**](AuditRecordArray.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getRetentionPeriod"></a>
# **getRetentionPeriod**
> RetentionPeriod getRetentionPeriod()

Get retention period

Returns the retention period for records in the audit log. The retention period is how long an audit record is kept for, from creation date until it is deleted.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Confluence Administrator&#x27; global permission.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.AuditApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

AuditApi apiInstance = new AuditApi();
try {
    RetentionPeriod result = apiInstance.getRetentionPeriod();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AuditApi#getRetentionPeriod");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**RetentionPeriod**](RetentionPeriod.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="setRetentionPeriod"></a>
# **setRetentionPeriod**
> RetentionPeriod setRetentionPeriod(body)

Set retention period

Sets the retention period for records in the audit log. The retention period can be set to a maximum of 1 year.  **[Permissions](https://confluence.atlassian.com/x/_AozKw) required**: &#x27;Confluence Administrator&#x27; global permission.

### Example
```java
// Import classes:
//import ai.gebo.atlassian.confluence.api.invoker.ApiClient;
//import ai.gebo.atlassian.confluence.api.invoker.ApiException;
//import ai.gebo.atlassian.confluence.api.invoker.Configuration;
//import ai.gebo.atlassian.confluence.api.invoker.auth.*;
//import ai.gebo.atlassian.confluence.api.AuditApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();
// Configure HTTP basic authorization: basicAuth
HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
basicAuth.setUsername("YOUR USERNAME");
basicAuth.setPassword("YOUR PASSWORD");

// Configure OAuth2 access token for authorization: oAuthDefinitions
OAuth oAuthDefinitions = (OAuth) defaultClient.getAuthentication("oAuthDefinitions");
oAuthDefinitions.setAccessToken("YOUR ACCESS TOKEN");

AuditApi apiInstance = new AuditApi();
RetentionPeriod body = new RetentionPeriod(); // RetentionPeriod | The updated retention period.
try {
    RetentionPeriod result = apiInstance.setRetentionPeriod(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AuditApi#setRetentionPeriod");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**RetentionPeriod**](RetentionPeriod.md)| The updated retention period. |

### Return type

[**RetentionPeriod**](RetentionPeriod.md)

### Authorization

[basicAuth](../README.md#basicAuth)[oAuthDefinitions](../README.md#oAuthDefinitions)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

