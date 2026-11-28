# RedmineIssuesSearchFilter

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**projects** | **List&lt;String&gt;** |  |  [optional]
**issueIds** | **List&lt;Integer&gt;** |  |  [optional]
**textTerms** | **List&lt;String&gt;** |  |  [optional]
**textTermsMatchMode** | [**TextTermsMatchModeEnum**](#TextTermsMatchModeEnum) |  |  [optional]
**trackers** | **List&lt;String&gt;** |  |  [optional]
**statuses** | **List&lt;String&gt;** |  |  [optional]
**open** | **Boolean** |  |  [optional]
**priorities** | **List&lt;String&gt;** |  |  [optional]
**assignedToMe** | **Boolean** |  |  [optional]
**createdFrom** | **String** |  |  [optional]
**createdTo** | **String** |  |  [optional]
**updatedFrom** | **String** |  |  [optional]
**updatedTo** | **String** |  |  [optional]

<a name="TextTermsMatchModeEnum"></a>
## Enum: TextTermsMatchModeEnum
Name | Value
---- | -----
ANY | &quot;ANY&quot;
ALL | &quot;ALL&quot;
