# ConfluenceContentAttributeFilter

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**spaceKeys** | **List&lt;String&gt;** |  |  [optional]
**contentTypes** | **List&lt;String&gt;** |  |  [optional]
**contentIds** | **List&lt;Long&gt;** |  |  [optional]
**titleTerms** | **List&lt;String&gt;** |  |  [optional]
**titleTermsMatchMode** | [**TitleTermsMatchModeEnum**](#TitleTermsMatchModeEnum) |  |  [optional]
**textTerms** | **List&lt;String&gt;** |  |  [optional]
**textTermsMatchMode** | [**TextTermsMatchModeEnum**](#TextTermsMatchModeEnum) |  |  [optional]
**labels** | **List&lt;String&gt;** |  |  [optional]
**labelsMatchMode** | [**LabelsMatchModeEnum**](#LabelsMatchModeEnum) |  |  [optional]
**ancestorIds** | **List&lt;Long&gt;** |  |  [optional]

<a name="TitleTermsMatchModeEnum"></a>
## Enum: TitleTermsMatchModeEnum
Name | Value
---- | -----
ANY | &quot;ANY&quot;
ALL | &quot;ALL&quot;

<a name="TextTermsMatchModeEnum"></a>
## Enum: TextTermsMatchModeEnum
Name | Value
---- | -----
ANY | &quot;ANY&quot;
ALL | &quot;ALL&quot;

<a name="LabelsMatchModeEnum"></a>
## Enum: LabelsMatchModeEnum
Name | Value
---- | -----
ANY | &quot;ANY&quot;
ALL | &quot;ALL&quot;
