# BoardPayload

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**boardFilterJQL** | **String** | Takes in a JQL string to create a new filter. If no value is provided, it&#x27;ll default to a JQL filter for the project creating |  [optional]
**cardColorStrategy** | [**CardColorStrategyEnum**](#CardColorStrategyEnum) | Card color settings of the board |  [optional]
**cardLayout** | [**CardLayout**](CardLayout.md) |  |  [optional]
**cardLayouts** | [**List&lt;CardLayoutField&gt;**](CardLayoutField.md) | Card layout settings of the board |  [optional]
**columns** | [**List&lt;BoardColumnPayload&gt;**](BoardColumnPayload.md) | The columns of the board |  [optional]
**features** | [**List&lt;BoardFeaturePayload&gt;**](BoardFeaturePayload.md) | Feature settings for the board |  [optional]
**name** | **String** | The name of the board |  [optional]
**pcri** | [**ProjectCreateResourceIdentifier**](ProjectCreateResourceIdentifier.md) |  |  [optional]
**quickFilters** | [**List&lt;QuickFilterPayload&gt;**](QuickFilterPayload.md) | The quick filters for the board. |  [optional]
**supportsSprint** | **Boolean** | Whether sprints are supported on the board |  [optional]
**swimlanes** | [**SwimlanesPayload**](SwimlanesPayload.md) |  |  [optional]
**workingDaysConfig** | [**WorkingDaysConfig**](WorkingDaysConfig.md) |  |  [optional]

<a name="CardColorStrategyEnum"></a>
## Enum: CardColorStrategyEnum
Name | Value
---- | -----
ISSUE_TYPE | &quot;ISSUE_TYPE&quot;
REQUEST_TYPE | &quot;REQUEST_TYPE&quot;
ASSIGNEE | &quot;ASSIGNEE&quot;
PRIORITY | &quot;PRIORITY&quot;
NONE | &quot;NONE&quot;
CUSTOM | &quot;CUSTOM&quot;
