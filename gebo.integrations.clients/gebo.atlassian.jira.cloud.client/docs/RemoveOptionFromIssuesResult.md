# RemoveOptionFromIssuesResult

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**errors** | **AllOfRemoveOptionFromIssuesResultErrors** | A collection of errors related to unchanged issues. The collection size is limited, which means not all errors may be returned. |  [optional]
**modifiedIssues** | **List&lt;Long&gt;** | The IDs of the modified issues. |  [optional]
**unmodifiedIssues** | **List&lt;Long&gt;** | The IDs of the unchanged issues, those issues where errors prevent modification. |  [optional]
