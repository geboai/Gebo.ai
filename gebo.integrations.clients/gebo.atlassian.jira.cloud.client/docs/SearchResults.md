# SearchResults

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**expand** | **String** | Expand options that include additional search result details in the response. |  [optional]
**issues** | [**List&lt;IssueBean&gt;**](IssueBean.md) | The list of issues found by the search. |  [optional]
**maxResults** | **Integer** | The maximum number of results that could be on the page. |  [optional]
**names** | **Map&lt;String, String&gt;** | The ID and name of each field in the search results. |  [optional]
**schema** | [**Map&lt;String, JsonTypeBean&gt;**](JsonTypeBean.md) | The schema describing the field types in the search results. |  [optional]
**startAt** | **Integer** | The index of the first item returned on the page. |  [optional]
**total** | **Integer** | The number of results on the page. |  [optional]
**warningMessages** | **List&lt;String&gt;** | Any warnings related to the JQL query. |  [optional]
