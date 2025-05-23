# IssueLayoutPayload

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**containerId** | [**ProjectCreateResourceIdentifier**](ProjectCreateResourceIdentifier.md) |  |  [optional]
**issueLayoutType** | [**IssueLayoutTypeEnum**](#IssueLayoutTypeEnum) | The issue layout type |  [optional]
**items** | [**List&lt;IssueLayouItemtPayload&gt;**](IssueLayouItemtPayload.md) | The configuration of items in the issue layout |  [optional]
**pcri** | [**ProjectCreateResourceIdentifier**](ProjectCreateResourceIdentifier.md) |  |  [optional]

<a name="IssueLayoutTypeEnum"></a>
## Enum: IssueLayoutTypeEnum
Name | Value
---- | -----
ISSUE_VIEW | &quot;ISSUE_VIEW&quot;
ISSUE_CREATE | &quot;ISSUE_CREATE&quot;
REQUEST_FORM | &quot;REQUEST_FORM&quot;
