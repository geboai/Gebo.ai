# SwimlanesPayload

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**customSwimlanes** | [**List&lt;SwimlanePayload&gt;**](SwimlanePayload.md) | The custom swimlane definitions. |  [optional]
**defaultCustomSwimlaneName** | **String** | The name of the custom swimlane to use for work items that don&#x27;t match any other swimlanes. |  [optional]
**swimlaneStrategy** | [**SwimlaneStrategyEnum**](#SwimlaneStrategyEnum) | The swimlane strategy for the board. |  [optional]

<a name="SwimlaneStrategyEnum"></a>
## Enum: SwimlaneStrategyEnum
Name | Value
---- | -----
NONE | &quot;none&quot;
CUSTOM | &quot;custom&quot;
PARENTCHILD | &quot;parentChild&quot;
ASSIGNEE | &quot;assignee&quot;
ASSIGNEEUNASSIGNEDFIRST | &quot;assigneeUnassignedFirst&quot;
EPIC | &quot;epic&quot;
PROJECT | &quot;project&quot;
ISSUEPARENT | &quot;issueparent&quot;
ISSUECHILDREN | &quot;issuechildren&quot;
REQUEST_TYPE | &quot;request_type&quot;
