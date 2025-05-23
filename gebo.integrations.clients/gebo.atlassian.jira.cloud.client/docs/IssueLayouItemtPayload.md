# IssueLayouItemtPayload

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**itemKey** | [**ProjectCreateResourceIdentifier**](ProjectCreateResourceIdentifier.md) |  |  [optional]
**sectionType** | [**SectionTypeEnum**](#SectionTypeEnum) | The item section type |  [optional]
**type** | [**TypeEnum**](#TypeEnum) | The item type. Currently only support FIELD |  [optional]

<a name="SectionTypeEnum"></a>
## Enum: SectionTypeEnum
Name | Value
---- | -----
CONTENT | &quot;content&quot;
PRIMARYCONTEXT | &quot;primaryContext&quot;
SECONDARYCONTEXT | &quot;secondaryContext&quot;

<a name="TypeEnum"></a>
## Enum: TypeEnum
Name | Value
---- | -----
FIELD | &quot;FIELD&quot;
