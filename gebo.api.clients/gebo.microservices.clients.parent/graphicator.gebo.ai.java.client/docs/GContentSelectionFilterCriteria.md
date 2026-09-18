# GContentSelectionFilterCriteria

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**mimeContentTypes** | **List&lt;String&gt;** |  |  [optional]
**extensions** | **List&lt;String&gt;** |  |  [optional]
**nameFilter** | **String** |  |  [optional]
**nameFilterCriteria** | [**NameFilterCriteriaEnum**](#NameFilterCriteriaEnum) |  |  [optional]
**maxFileSize** | **Long** |  |  [optional]
**maxTokenSize** | **Long** |  |  [optional]
**maxModificationAgeInDays** | **Integer** |  |  [optional]
**empty** | **Boolean** |  |  [optional]

<a name="NameFilterCriteriaEnum"></a>
## Enum: NameFilterCriteriaEnum
Name | Value
---- | -----
CONTAINS | &quot;CONTAINS&quot;
EQUALS | &quot;EQUALS&quot;
STARTS_WITH | &quot;STARTS_WITH&quot;
ENDS_WITH | &quot;ENDS_WITH&quot;
